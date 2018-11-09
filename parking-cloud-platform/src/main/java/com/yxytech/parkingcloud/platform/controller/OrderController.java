package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.*;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import com.yxytech.parkingcloud.platform.form.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IOrderParkingInfoService orderParkingInfoService;

    @Autowired
    private IOrderVoucherService orderVoucherService;

    @Autowired
    private IFreelistService freelistService;

    @Autowired
    private IWhitelistService whitelistService;

    @Autowired
    private IBlacklistService blacklistService;

    @Autowired
    private ICustomerBindCarsService customerBindCarsService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IPushMessageService pushMessageService;

    @Autowired
    private ICustomerCarsService customerCarsService;

    @Autowired
    private OrderInfoUtil orderInfoUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                             @RequestParam(value = "parking_id", required = false) Integer parkingId,
                             @RequestParam(value = "status", required = false) Boolean status,
                             @RequestParam(value = "plate_color", required = false) Integer plateColor,
                             @RequestParam(value = "plate_number", defaultValue = "", required = false) String plateNumber,
                             @RequestParam(value = "start_time", required = false) String start,
                             @RequestParam(value = "end_time", required = false) String end) {
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        Page<OrderInfo> orderInfoPage = new Page<>(page, size);
        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();

        orderInfoWrapper.where(parkingId != null, "parking_id = {0}", parkingId)
                .where(plateColor != null, "plate_color = {0}", plateColor)
                .where(! plateNumber.equals(""), "plate_number = {0}", plateNumber)
                .where(startTime != null, "created_at >= {0}", startTime)
                .where(endTime != null, "created_at <= {0}", endTime)
                .where(status != null, "car_status = {0}", status)
                .orderBy("id", true);

        return this.apiSuccess(orderInfoService.selectPage(orderInfoPage, orderInfoWrapper));
    }

    @PostMapping("/create")
    @Transactional
    public ApiResponse create(@Valid @RequestBody CreateOrderForm params, BindingResult br) throws BindException {
        validate(br);

        if (params.getCarType().equals(CarTypeEnum.ALL)) {
            return this.apiFail("错误的车辆类型!");
        }

        try {
            OrderInfo orderInfo = this.createOrder(params);

            pushMessageService.createAndSendOrderCreatedMessage(orderInfo);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("ok");
    }

    /**
     * 地磁设备创建订单
     * @param params
     * @return
     */
    @PostMapping("/createForGeomagnetism")
    @Transactional
    public ApiResponse createForGeomagnetism(@Valid @RequestBody CreateOrderByGeoForm params, BindingResult br) throws BindException {
        validate(br);

        if (params.getCarType().equals(CarTypeEnum.ALL)) {
            return this.apiFail("错误的车辆类型!");
        }

        OrderInfo orderInfo = new OrderInfo();
        OrderParkingInfo orderParkingInfo = new OrderParkingInfo();

        BeanUtils.copyProperties(params, orderInfo);
        BeanUtils.copyProperties(params, orderParkingInfo);

        orderInfo.setFromType(OrderFromTypeEnum.GEO);

        if (orderParkingInfo.getParkingCellId() == null && orderParkingInfo.getParkingCellCode() == null) {
            return this.apiFail("车位信息为空!");
        }

        Parking parking = parkingService.selectById(orderInfo.getParkingId());

        orderInfo = this.setInfoToOrder(orderInfo, parking, params.getDeviceSn());

        try {
            this.validParkingInfo(orderInfo, orderParkingInfo, parking);
            orderParkingInfo = orderInfoService.validateParams(orderInfo.getParkingId(), orderParkingInfo);

            this.insertOrder(orderInfo, orderParkingInfo, null, parking);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("ok");
    }

    @PostMapping("/remove/{id}")
    public ApiResponse remove(@PathVariable Integer id, @RequestBody OrderInfo orderInfoParam) {
        OrderInfo orderInfo = orderInfoService.selectById(id);

        if (orderInfo == null || (! orderInfo.getValid())) {
            return this.apiFail("订单作废失败，请检查订单状态!");
        }

        // 需要传递作废原因过来
        if (orderInfoParam == null || orderInfoParam.getRemark().equals("")) {
            return this.apiFail("请输入订单作废原因!");
        }

        orderInfo.setRemark(orderInfoParam.getRemark());
        orderInfo.setIsValid(false);

        Boolean ret = orderInfoService.updateById(orderInfo);
        parkingService.decreaseParkingCellUsedCount(orderInfo.getParkingId());

        if (! ret) {
            return this.apiFail("订单作废失败，请检查订单状态!");
        }

        return this.apiSuccess("ok");
    }

    @PostMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody OrderForm orderInfo) throws NotFoundException {
        // 仅可以修改入场时间，出场时间，车位，车牌号，车牌颜色，收费金额
        OrderInfo info = orderInfoService.selectById(id);
        notFound(orderInfo.getRemark(), "请输入修改原因!");

        if (info == null || (! info.getValid())) {
            return this.apiFail("错误的订单!");
        }

        if (orderInfo.getEnterAt() != null) {
            info.setEnterAt(orderInfo.getEnterAt());
        }

        if (orderInfo.getLeaveAt() != null) {
            info.setLeaveAt(orderInfo.getLeaveAt());
        }

        if (orderInfo.getPlateNumber() != null) {
            if (orderInfo.getPlateColor() == null) {
                return this.apiFail("车牌号和车牌颜色必须同时传递!");
            }

            if (! info.getPlateNumber().equals(orderInfo.getPlateNumber())) {
                if (info.getLastPaymentTime() != null) {
                    return this.apiFail("该订单可能已经存在支付信息，请联系客服处理！");
                }

                if (info.getStatus().equals(OrderStatusEnum.FINISHED)) {
                    return this.apiFail("已完成订单不能修改车牌号和车牌颜色!");
                }

                info.setPlateNumber(orderInfo.getPlateNumber());
                info.setPlateColor(orderInfo.getPlateColor());

                Map<String, Long> carInfo = this.getCarInfo(info.getPlateNumber(), info.getPlateColor());

                info.setUserId(carInfo.get("userId"));
                info.setCustomerCarId(carInfo.get("carId"));
            } else {
                info.setPlateNumber(orderInfo.getPlateNumber());
                info.setPlateColor(orderInfo.getPlateColor());
            }
        }

        if (orderInfo.getReceivableAmount() != null) {
            info.setReceivableAmount(orderInfo.getReceivableAmount());
        }

        if (orderInfo.getParkingCellId() != null) {
            Wrapper<OrderParkingInfo> orderParkingInfoWrapper = new EntityWrapper<>();
            OrderParkingInfo orderParkingInfo = new OrderParkingInfo();
            orderParkingInfo.setParkingId(orderInfo.getParkingId());

            try {
                orderParkingInfo = orderInfoService.validateParams(id, orderParkingInfo);
                orderParkingInfoWrapper.eq("order_id", id);

                orderParkingInfoService.update(orderParkingInfo, orderParkingInfoWrapper);
            } catch (Throwable throwable) {
                return this.apiFail(throwable.getMessage());
            }
        }

        info.setRemark(orderInfo.getRemark());
        orderInfoService.updateById(info);

        return this.apiSuccess("ok");
    }

    /**
     * 地磁设备的更新
     * @param params
     * @return
     */
    @PostMapping("/updateForGeomagnetism")
    @Transactional
    public ApiResponse updateForGeomagnetism(@Valid @RequestBody UpdateOrderForGeoForm params, BindingResult br) throws NotFoundException, BindException {
        validate(br);

        Long orderId = params.getOrderId();
        OrderInfo orderInfo = orderInfoService.selectById(orderId);
        notFound(orderInfo, "非法访问!");

        if (! orderInfo.getParkingId().equals(params.getParkingId())) {
            return this.apiFail("非法访问!");
        }

        if (orderInfo.getStatus().equals(OrderStatusEnum.FINISHED)) {
            return this.apiFail("只能更新在场订单!");
        }

        Wrapper<OrderParkingInfo> orderParkingInfoWrapper = new EntityWrapper<>();
        orderParkingInfoWrapper.eq("order_id", orderId);
        OrderParkingInfo orderParkingInfo = orderParkingInfoService.selectOne(orderParkingInfoWrapper);

        notFound(orderParkingInfo, "错误的停车信息!");

        if (! orderParkingInfo.getParkingCellId().equals(params.getParkingCellId())) {
            return this.apiFail("错误的停车信息!");
        }

        BeanUtils.copyProperties(params, orderInfo);
        BeanUtils.copyProperties(params, orderParkingInfo);

        Parking parking = parkingService.selectById(orderInfo.getParkingId());
        notFound(parking, "非法的停车场信息!");

        orderInfo = this.setInfoToOrder(orderInfo, parking, params.getDeviceSn());
        orderInfo.setRemark(params.getRemark());

        try {
            orderInfoUtil.updateOrder(orderId, orderInfo, orderParkingInfo, null);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("");
    }

    /**
     * 离场
     * @param params
     * @return
     */
    @PostMapping("/finish")
    @Transactional
    public ApiResponse finish(@Valid @RequestBody FinishOrderForm params, BindingResult br) throws BindException {
        validate(br);
        OrderInfo orderInfo = orderInfoService.selectById(params.getOrderId());
        OrderParkingInfo orderParkingInfo = new OrderParkingInfo();
        BeanUtils.copyProperties(params, orderParkingInfo);

        if (orderInfo == null || (! orderInfo.getParkingId().equals(params.getParkingId()))) {
            return this.apiFail("非法访问!");
        }

        if ((! orderInfo.getStatus().equals(OrderStatusEnum.CREATED)) || (! orderInfo.getValid())) {
            return this.apiFail("订单状态错误!");
        }

        orderInfo.setCarStatus(OrderCarStatusType.LEAVE);
        orderInfo.setStatus(OrderStatusEnum.FINISHED);
        orderInfo.setLeaveAt(params.getLeaveAt());

        String deviceSn = params.getDeviceSn();
        if (StringUtils.isNotEmpty(deviceSn)) orderInfo.setOutDeviceSn(deviceSn);

        try {
            orderInfoUtil.getAmountInfo(orderInfo, params.getLeaveAt(), true);

            if (orderInfo.getPaidAmount() > orderInfo.getReceivableAmount()) {
                // 进行退款
                orderInfo = orderInfoUtil.refundAmount(orderInfo);
            }

            orderInfoUtil.updateOrder(orderInfo.getId(), orderInfo, orderParkingInfo, null);
            parkingService.decreaseParkingCellUsedCount(orderInfo.getParkingId());
        } catch (Exception e) {
            return this.apiFail("订单完成失败: " + e.getMessage());
        }

        return this.apiSuccess("");
    }

    /**
     * 后台查询订单金额信息
     * @param orderForm
     * @param orderId
     * @return
     */
    @PostMapping("/detail/{orderId}")
    public ApiResponse getOrderInfo(@RequestBody OrderForm orderForm, @PathVariable Long orderId) throws NotFoundException {
        if (orderForm.getParkingId() == null) {
            return this.apiFail("停车场id必须填写!");
        }

        OrderInfo orderInfo = orderInfoService.getDetail(orderId);

        if (orderInfo == null || (! orderForm.getParkingId().equals(orderInfo.getParkingId()))) {
            return this.apiFail("非法访问!");
        }

        notFound(orderInfo.getPlateNumber(), "订单还未更新不能计算金额");
        notFound(orderInfo.getPlateColor(), "订单还未更新不能计算金额");

        Date date = new Date();
        // 为了测试方便
        date = orderForm.getLeaveAt() == null ? date : orderForm.getLeaveAt();

        try {
            orderInfo = orderInfoUtil.getAmountInfo(orderInfo, date, false);

            return this.apiSuccess(orderInfo);
        } catch (ParseException e) {
            return this.apiFail("获取订单详情失败: " + e.getMessage());
        }
    }

    /**
     * 更新订单出入场照片
     * @param id
     * @param orderVouchers
     * @return
     */
    @PostMapping("/updateVouchers/{id}")
    public ApiResponse updateVouchers(@PathVariable Long id, @RequestBody List<OrderVoucher> orderVouchers) throws NotFoundException {
        OrderInfo orderInfo = orderInfoService.selectById(id);
        notFound(orderInfo, "非法的订单信息!");

        for (int i = 0; i < orderVouchers.size(); i++) {
            OrderVoucher orderVoucher = orderVouchers.get(i);

            orderVoucher.setOrderId(id);

            orderVouchers.set(i, orderVoucher);
        }

        orderVoucherService.insertBatch(orderVouchers, orderVouchers.size());

        return this.apiSuccess("");
    }

    /**
     * 地磁设备结束停车
     * @param params
     * @return
     */
    @PostMapping("/finishByGeomagnetism")
    @Transactional
    public ApiResponse finishByGeomagnetism(@RequestBody OrderForm params) {
        OrderInfo orderInfo = new OrderInfo();
        OrderParkingInfo orderParkingInfo = new OrderParkingInfo();
        BeanUtils.copyProperties(params, orderInfo);
        BeanUtils.copyProperties(params, orderParkingInfo);

        if (orderInfo.getPlateNumber() == null || orderInfo.getPlateColor() == null) {
            return this.apiFail("非法的参数!");
        }

        Wrapper<OrderParkingInfo> orderParkingInfoWrapper = new EntityWrapper<>();

        orderParkingInfoWrapper.eq("parking_id", orderInfo.getParkingId())
                .andNew()
                .eq("parking_cell_id", orderParkingInfo.getParkingCellId())
                .or()
                .eq("parking_cell_code", orderParkingInfo.getParkingCellCode())
                .setSqlSelect("order_id");

        if (orderInfo.getLeaveAt() == null) {
            Date date = new Date();

            orderInfo.setLeaveAt(date);
        }

        if (orderInfo.getCarStatus() == null) {
            orderInfo.setCarStatus(OrderCarStatusType.LEAVE);
        }

        OrderParkingInfo orderParkingInfoResult = orderParkingInfoService.selectOne(orderParkingInfoWrapper);

        if (orderParkingInfoResult == null) {
            return this.apiFail("非法的参数!");
        }


        if (StringUtils.isNotEmpty(params.getDeviceSn())) {
            orderInfo.setOutDeviceSn(params.getDeviceSn());
        }

        try {
            orderInfoService.update(orderParkingInfoResult.getOrderId(), orderInfo, orderParkingInfo, null);
        } catch (Throwable throwable) {
            return this.apiFail(throwable.getMessage());
        }

        try {
            this.setAmountToOrderInfo(orderInfo);
        } catch (ParseException e) {
            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess(orderInfoService.selectById(orderParkingInfoResult.getOrderId()));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse detail(@PathVariable Long id) {
        try {
            return this.apiSuccess(orderInfoService.getDetail(id));
        } catch (Exception e) {
            return this.apiFail("获取订单详情失败!");
        }
    }

    private Boolean isInBlackList(String plateNumber, Integer plateColor, Long parkingId) {
        return blacklistService.isInBlackList(plateNumber, plateColor, parkingId);
    }

    /**
     * 获取运营单位id
     * @param parkingId
     * @return
     */
    private Long getOrganizationIdByParkingId(Long parkingId) {
        Wrapper<Parking> parkingWrapper = new EntityWrapper<>();

        parkingWrapper.eq("id", parkingId)
                .setSqlSelect("operator_org_id");

        Parking parking = parkingService.selectOne(parkingWrapper);

        return (parking == null) ? null : parking.getOperatorOrgId();
    }

    /**
     * 创建订单
     * @param params
     * @throws Exception
     */
    private OrderInfo createOrder(CreateOrderForm params) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        OrderParkingInfo orderParkingInfo = new OrderParkingInfo();
        BeanUtils.copyProperties(params, orderInfo);
        BeanUtils.copyProperties(params, orderParkingInfo);

        Parking parking = parkingService.selectById(orderInfo.getParkingId());

        this.validParkingInfo(orderInfo, orderParkingInfo, parking);

        orderInfo = this.setInfoToOrder(orderInfo, parking, params.getDeviceSn());

        this.insertOrder(orderInfo, orderParkingInfo, null, parking);

        return orderInfo;
    }

    /**
     * 判断停车信息
     * @param orderInfo
     * @param orderParkingInfo
     * @return
     */
    private void validParkingInfo(OrderInfo orderInfo, OrderParkingInfo orderParkingInfo, Parking parking) throws Exception {
        if (orderInfo.getPlateNumber() != null) {
            if (isInBlackList(orderInfo.getPlateNumber(), (Integer) orderInfo.getPlateColor().getValue(),
                    orderParkingInfo.getParkingId())) {
                throw new Exception("该车在黑名单中，不能进场!");
            }
        }

        if (parking == null) {
            throw new Exception("停车场信息错误!");
        }

        Long organizationId = parking.getOperatorOrgId();

        if (organizationId == null) {
            throw new Exception("错误的停车场信息: 运营单位不存在!");
        }

        if (orderInfo.getPlateNumber() != null && orderInfo.getPlateColor() != null) {

            // 判断当前停车场是否存在该辆车已经停车的记录
            Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();

            orderInfoWrapper.eq("car_status", OrderCarStatusType.PARKING)
                    .eq("plate_number", orderInfo.getPlateNumber())
                    .eq("plate_color", orderInfo.getPlateColor())
                    .eq("is_valid", true);

            Integer isInParking = orderInfoService.selectCount(orderInfoWrapper);

            if (isInParking > 0) {
                throw new Exception("错误的停车信息: 同一辆车多次创建订单!");
            }
        } else {
            // 根据车位查找
            Integer isInParking = orderInfoService.getInParkingCount(orderInfo.getParkingId(), orderParkingInfo.getParkingCellId());

            if (isInParking > 0) {
                throw new Exception("错误的停车信息: 该车位上已经存在在停车辆!");
            }
        }
    }

    /**
     * 创建订单
     * @param orderInfo
     * @param orderParkingInfo
     * @param orderVouchers
     * @param parking
     */
    private void insertOrder(OrderInfo orderInfo, OrderParkingInfo orderParkingInfo,
                             List<OrderVoucher> orderVouchers, Parking parking) {

        orderInfoService.insert(orderInfo);
        parkingService.increaseParkingCellUsedCount(orderInfo.getParkingId());

        if (orderParkingInfo == null) {
            orderParkingInfo = new OrderParkingInfo();
        }

        orderParkingInfo.setOrderId(orderInfo.getId());
        orderParkingInfo.setParkingId(parking.getId());
        orderParkingInfo.setParkingName(parking.getFullName());
        orderParkingInfo.setParkingAddress(parking.getAddress());

        orderParkingInfoService.insert(orderParkingInfo);

        if (orderVouchers != null) {
            List<OrderVoucher> orderVoucherList = new ArrayList<>();

            for (OrderVoucher orderVoucher : orderVouchers) {
                orderVoucher.setOrderId(orderInfo.getId());

                orderVoucherList.add(orderVoucher);
            }

            if (orderVoucherList.size() > 0) {
                orderVoucherService.insertBatch(orderVoucherList, orderVoucherList.size());
            }
        }
    }

    /**
     * 设置订单信息
     * @param orderInfo
     * @return
     */
    private OrderInfo setInfoToOrder(OrderInfo orderInfo, Parking parking, String deviceSn) {
        String plateNumber = orderInfo.getPlateNumber();
        ColorsEnum plateColor = orderInfo.getPlateColor();
        CarTypeEnum carType = orderInfo.getCarType();
        Long userId = 0L;
        Long carId = 0L;

        if (StringUtils.isNotEmpty(deviceSn)) {
            orderInfo.setInDeviceSn(deviceSn);
        }

        // 获取车辆的信息
        if (plateNumber != null && plateColor != null) {
            // 获取车辆所属用户id，或车辆id进行插入
            Map<String, Long> carInfo = this.getCarInfo(plateNumber, plateColor);

            userId = carInfo.get("userId");
            carId = carInfo.get("carId");
        }

        orderInfo.setCarStatus(OrderCarStatusType.PARKING);
        orderInfo.setParkingId(parking.getId());
        orderInfo.setOrganizationId(parking.getOperatorOrgId());
        orderInfo.setCustomerCarId(carId);
        orderInfo.setUserId(userId);
        orderInfo.setParkingName(parking.getFullName());
        String orderNumber = UniqueCode.generateId(redisTemplate, "order");
        orderInfo.setOrderNumber(orderNumber);

        return orderInfo;
    }

    private void setAmountToOrderInfo(OrderInfo orderInfo) throws ParseException {
        orderInfo = orderInfoUtil.getAmountInfo(orderInfo, orderInfo.getLeaveAt(), false);

        orderInfoService.updateById(orderInfo);
    }

    private Map<String, Long> getCarInfo(String plateNumber, ColorsEnum plateColor) {
        CustomerBindCars customerCars = customerBindCarsService.getByPlateInfo(plateNumber, plateColor);
        Map<String, Long> ret = new HashMap<>();

        if (customerCars == null) {
            // 创建车辆
            CustomerCars customerCarsParam = new CustomerCars();

            customerCarsParam.setPlateNumber(plateNumber);
            customerCarsParam.setPlateColor(plateColor);
            customerCarsParam.setCarType(CarTypeEnum.ALL);
            customerCarsParam.setCreatedBy(0L);

            customerCarsService.insert(customerCarsParam);

            ret.put("carId", customerCarsParam.getId());
            ret.put("userId", 0L);
        } else {
            ret.put("userId", customerCars.getUserId());
            ret.put("carId", customerCars.getCarId());
        }

        return ret;
    }
}
