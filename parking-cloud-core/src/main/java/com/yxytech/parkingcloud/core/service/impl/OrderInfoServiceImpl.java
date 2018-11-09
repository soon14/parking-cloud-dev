package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.mapper.OrderInfoMapper;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-27
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Value("${error.time.length}")
    private Long errorTimeLength;

    @Value("${server.env}")
    private String env;

    @Autowired
    private IOrderVoucherService orderVoucherService;

    @Autowired
    private IParkingCellService parkingCellService;

    @Autowired
    private IParkingLaneService parkingLaneService;

    @Autowired
    private IParkingPortService parkingPortService;

    @Autowired
    private ICustomerCarsService customerCarsService;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private ITransactionSuccessService transactionSuccessService;

    @Autowired
    private IPushMessageService pushMessageService;

    private static final Integer inType = 1;
    private static final Integer outType = 0;

    @Override
    public OrderInfo getDetail(Long id) {
        OrderInfo orderInfo = baseMapper.getDetail(id);

        if (orderInfo == null) {
            return null;
        }

        Wrapper<OrderVoucher> orderVoucherWrapper = new EntityWrapper<>();
        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();

        orderVoucherWrapper.eq("order_id", id);
        orderTransactionWrapper.eq("order_id", id);

        orderInfo.setVoucherList(orderVoucherService.selectList(orderVoucherWrapper));
        orderInfo.setOrderTransactionList(orderTransactionService.selectList(orderTransactionWrapper));

        return orderInfo;
    }

    @Override
    public OrderInfo getDetail(OrderInfo orderInfo) {
        EntityWrapper<CustomerCars> ew = new EntityWrapper<>();
        ew.eq("", orderInfo.getPlateColor());
        ew.eq("", orderInfo.getPlateNumber());

        // TODO: 为空处理
        CustomerCars customerCars = customerCarsService.selectOne(ew);
        orderInfo.setCarType(customerCars.getCarType());

        return orderInfo;
    }

    /**
     * 停车信息的校验
     * @param parkingId
     * @param orderParkingInfo
     * @return
     * @throws Throwable
     */
    @Override
    public OrderParkingInfo validateParams(Long parkingId, OrderParkingInfo orderParkingInfo) throws Exception {
        if (orderParkingInfo == null) {
            return null;
        }

        if (orderParkingInfo.getParkingCellId() != null) {
            ParkingCell parkingCell = parkingCellService.selectById(orderParkingInfo.getParkingCellId());

            if (parkingCell == null || ! parkingId.equals(parkingCell.getParkingId())) {
                throw new Exception("非法的车位!");
            }

            orderParkingInfo.setParkingCellCode(parkingCell.getCode());
        }

        if (orderParkingInfo.getInParkingLaneId() != null) {
            ParkingLane parkingLane = parkingLaneService.selectById(orderParkingInfo.getInParkingLaneId());

            if (parkingLane == null || parkingId.equals(parkingLane.getParkingId())
                    || parkingLane.getLaneType().equals(outType)) {
                throw new Exception("非法的入口车道!");
            }

            orderParkingInfo.setInParkingLaneCode(parkingLane.getCode());
        }

        if (orderParkingInfo.getInParkingPortId() != null) {
            ParkingPort parkingPort = parkingPortService.selectById(orderParkingInfo.getInParkingPortId());

            if (parkingPort == null || parkingId.equals(parkingPort.getParkingId())
                    || parkingPort.getPortType().equals(outType)) {
                throw new Exception("非法的入口!");
            }

            orderParkingInfo.setInParkingPortCode(parkingPort.getCode());
        }

        if (orderParkingInfo.getOutParkingLaneId() != null) {
            ParkingLane parkingLane = parkingLaneService.selectById(orderParkingInfo.getOutParkingPortId());

            if (parkingLane == null || parkingId.equals(parkingLane.getParkingId())
                    || parkingLane.getLaneType().equals(inType)) {
                throw new Exception("非法的出口车道!");
            }

            orderParkingInfo.setOutParkingLaneCode(parkingLane.getCode());
        }

        if (orderParkingInfo.getOutParkingLaneId() != null) {
            ParkingPort parkingPort = parkingPortService.selectById(orderParkingInfo.getOutParkingPortId());

            if (parkingPort == null || parkingId.equals(parkingPort.getParkingId())
                    || parkingPort.getPortType().equals(inType)) {
                throw new Exception("非法的出口!");
            }

            orderParkingInfo.setOutParkingLaneCode(parkingPort.getCode());
        }

        return orderParkingInfo;
    }

    /**
     * 更新订单信息
     * @param id
     * @param orderInfo
     * @param orderParkingInfo
     * @param orderVouchers
     * @return
     * @throws Throwable
     */
    @Override
    @Transactional
    public Boolean update(Long id, OrderInfo orderInfo, OrderParkingInfo orderParkingInfo,
                          List<OrderVoucher> orderVouchers) throws Exception {
        return true;
    }

    @Override
    public Integer getInParkingCount(Long parkingId, Long cellId) {
        return baseMapper.getInParkingByCell(parkingId, cellId);
    }

    @Override
    public Page<OrderInfo> invoiceSelectPage(Page<OrderInfo> page, Wrapper<OrderInfo> wrapper) {
        page.setRecords(baseMapper.invoiceSelectPage(page, wrapper));

        return page;
    }

    @Override
    public List<OrderInfo> invoiceSelectList(@Param("ew") Wrapper<OrderInfo> wrapper) {
        List<OrderInfo> list = baseMapper.invoiceSelectList(wrapper);
        return list;
    }

    @Override
    public List<OrderInfo> organzationSelectList(@Param("ew") Wrapper<OrderInfo> wrapper){
        List<OrderInfo> list = baseMapper.organzationSelectList(wrapper);
        return list;
    }

    @Override
    public OrderInfo paymentSuccess(OrderInfo orderInfo, OrderTransaction orderTransaction) {
        Double totalFee = OrderInfoUtil.formatAmount(orderTransaction.getAmount());
        // 交易信息
        TransactionSuccess transactionSuccess = transactionSuccessService.setInfo(orderTransaction, totalFee);
        transactionSuccessService.insert(transactionSuccess);

        // 交易成功消息推送
        pushMessageService.createAndSendOrderTransactionMessage(transactionSuccess, orderInfo.getParkingName());

//        if (env.toUpperCase().equals("DEV") && orderTransaction.getPayWay().equals(TransactionPayWay.WECHAT)) {
//            orderInfo.setInvoiceAmount(orderInfo.getReceivableAmount());
//            orderInfo.setPaidAmount(orderInfo.getReceivableAmount());
//            orderInfo.setLastPaymentTime(orderTransaction.getCreatedAt());
//
//            return orderInfo;
//        }

        orderInfo.setInvoiceAmount(orderInfo.getInvoiceAmount() + orderTransaction.getAmount());
        orderInfo.setPaidAmount(orderInfo.getPaidAmount() + orderTransaction.getAmount());
        orderInfo.setLastPaymentTime(orderTransaction.getCreatedAt());

        return orderInfo;
    }

    @Override
    public Boolean isWithinTheUncertainty(Date calculateTime) {
        Date date = new Date();

        return (date.getTime() - calculateTime.getTime()) > (errorTimeLength * 60 * 1000);
    }

    @Override
    public List<Map<String, Object>> selectOrganizationInvoiceInfo(Long id) {
        return baseMapper.selectOrganizationInvoiceInfo(id);
    }

    @Override
    public List<Map<String, Integer>> selectInUseParkingCells() {
        return baseMapper.selectInUseParkingCells();
    }

}
