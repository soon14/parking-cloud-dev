package com.yxytech.parkingcloud.app.controller;

import com.alibaba.fastjson.JSON;
import com.baiwang.baiwangcloud.client.BaiwangCouldAPIClient;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.app.entity.InvoiceInformationRequestQuery;
import com.yxytech.parkingcloud.app.entity.InvoiceVO;
import com.yxytech.parkingcloud.app.task.InoviceTask;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.commons.utils.YxyStringUtils;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.BillStatusEnum;
import com.yxytech.parkingcloud.core.enums.BillTypeEnum;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import io.netty.util.internal.ObjectUtil;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
@RequestMapping("/invoiceInformationRequest")
public class InvoiceInformationRequestController extends BaseController {

    @Autowired
    private IInvoiceInformationRequestService invoiceInformationRequestService;

    @Autowired
    private IInvoiceInformationResultService iInvoiceInformationResultService;

    @Autowired
    private IInvoiceDetailService invoiceDetailService;

    @Autowired
    private IBuyerInformationService buyerInformationService;

    @Autowired
    private IInvoiceInformationResultService invoiceInformationResultService;

    @Autowired
    private IBuyerInformationService iBuyerInformationService;

    @Autowired
    private ISalesInformationService iSalesInformationService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private IOrderInfoService iOrderInfoService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IOrderInvoiceService orderInvoiceService;

    @Autowired
    private ICustomerAccountService iCustomerAccountService;

    @Autowired
    private IOrderTransactionService iOrderTransactionService;


    @Value("${baiwangUrl}")
    private  String baiwangUrl;

    @Value("${baiwangDownload}")
    private String baiwangDownload;

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    @Value("${parkingcloud.upload-context}")
    private String uploadContext;

    @Autowired
    private InoviceTask inoviceTask;

    @Value("classpath:vm/invoice_create_request.vm")
    private Resource inoviceCreateRequest;

    @Value("classpath:vm/invoice_cancel_request.vm")
    private Resource invoiceCancelRequest;


    @GetMapping("/getPdfFileUrl/{invoice_id}")
    public ApiResponse<Object> getPdfFileUrl(@PathVariable Long invoice_id)
    {
        Map<String, Object> map = new HashMap<>();
        EntityWrapper<InvoiceInformationResult> ew = new EntityWrapper<>();
        ew.eq("invoice_id", invoice_id);
        InvoiceInformationResult invoiceInformationResult = iInvoiceInformationResultService.selectOne(ew);

        if(invoiceInformationResult == null){
            return apiFail("未找到发票");
        }
        map.put("taxInvoiceUrl", invoiceInformationResult.getTaxInvoiceUrl());
        map.put("platformUrl", invoiceInformationResult.getPlatformInvoiceUrl());
        map.put("cloudPlatformUrl", invoiceInformationResult.getCloudPlatformInvoiceUrl());
        map.put("cloudPlatformImage", invoiceInformationResult.getCloudPlatformInvoiceImage());
        return apiSuccess(map);
    }


    /**
     * 开票记录查询
     * @return
     */
    @GetMapping("/invoiceRequestSearch")
    public ApiResponse<Object> invoiceRequestSearch(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                                    @RequestParam(value = "size",defaultValue = "10", required = false) Integer size)
    {
        Page<InvoiceInformationRequest> p  = new Page<InvoiceInformationRequest>(page, size);
        Long customer_id = getCurrentUser().getId();
        //用customer_id去查开票记录
        EntityWrapper<InvoiceInformationRequest> ew = new EntityWrapper<>();
        ew.eq("customer_id", customer_id);
        p = invoiceInformationRequestService.selectRequestPage(p, ew);

        List<InvoiceInformationRequest> invoiceInformationRequests = p.getRecords();
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        for (InvoiceInformationRequest invoiceInformationRequest:invoiceInformationRequests) {
            Map<Object, Object> tmpmap = new HashMap<>();
            tmpmap.put("id", invoiceInformationRequest.getInvoiceId());
            tmpmap.put("flowNumber", invoiceInformationRequest.getInvoiceFlowNumber());
            tmpmap.put("createAt", invoiceInformationRequest.getApplyBillDate());
            tmpmap.put("requestAt", invoiceInformationRequest.getCreatedAt());
            tmpmap.put("status", invoiceInformationRequest.getBillStatus().getValue());
            tmpmap.put("money", invoiceInformationRequest.getPriceTaxTotal());

            list.add(tmpmap);
        }

        Page<Object> newPage = new Page<>();
        BeanUtils.copyProperties(p, newPage);
        newPage.setRecords(list);

        return apiSuccess(newPage);
    }

    @GetMapping("/invocieSearchTest")
    public ApiResponse<Object> invocieSearchTest() throws InterruptedException {
        Map<String, Object> map = invoiceInformationRequestService.invoiceResultOne(33L);
        return apiSuccess(map);
    }


    /**
     * 发票记录详情查询
     * @return
     */
    @GetMapping("/invoiceDetail/{id}")
    public ApiResponse<Object> invoiceDetail(@PathVariable Long id, HttpServletRequest request)
    {
        //存放返回结果信息
        Map<String, Object> map = new HashMap<>();
        //发票请求数据
        InvoiceInformationRequest invoiceInformationRequest = invoiceInformationRequestService.selectById(id);
        if(invoiceInformationRequest == null){
            return apiFail("未找到发票");
        }

        Long invoice_id = invoiceInformationRequest.getInvoiceId();
        //发票详情数据
        EntityWrapper<InvoiceDetail> invoiceDetailEntityWrapper = new EntityWrapper<>();
        invoiceDetailEntityWrapper.eq("invoice_id", invoice_id);
        List<InvoiceDetail> invoiceDetailList = invoiceDetailService.selectList(invoiceDetailEntityWrapper);
        if(invoiceDetailList == null){
            return apiFail("找不到此发票");
        }
        //发票结果数据
        EntityWrapper<InvoiceInformationResult> ew = new EntityWrapper<>();
        ew.eq("invoice_id", invoice_id);
        InvoiceInformationResult result = invoiceInformationResultService.selectOne(ew);
        //订单Id
        String orderIds = "0";
        for (InvoiceDetail invoiceDetail:invoiceDetailList) {
            orderIds = orderIds + "," + invoiceDetail.getOrderId();
        }
        String[] orderArray = orderIds.split(",");
        Set<Long> orderArrs = new HashSet<>();
        for (String s : orderArray) {
            orderArrs.add(Long.valueOf(s));
        }
        //订单列表
        EntityWrapper<OrderInfo> orderInfoEntityWrapper = new EntityWrapper<>();
        orderInfoEntityWrapper.in("id", orderArrs);
        List<OrderInfo> orderInfos = iOrderInfoService.selectList(orderInfoEntityWrapper);

        Map<String, Object> map1 = new HashMap<>();

        if(result == null){
            return apiFail("没有查到相关发票");
        }

        InvoiceVO invoiceVO = new InvoiceVO(invoiceInformationRequest, result);
        String prefix = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath() + uploadContext + File.separator;
        String imageUrl = invoiceVO.getInvoiceImageUrl().replace(uploadPath, prefix);
        invoiceVO.setInvoiceImageUrl(imageUrl);

        map.put("invoiceData", invoiceVO);
        map.put("buyerInformation", JSON.parse(invoiceInformationRequest.getRequestData()));
        map.put("orderInfos", orderInfos);
        return apiSuccess(map);
    }

    /**
     * 开票接口
     * @return
     * @throws BindException
     */
    @RequestMapping(value = "/InvoiceRequest", method = RequestMethod.POST)
    @Transactional
    public ApiResponse<Object> InvoiceRequest(@Valid @RequestBody InvoiceInformationRequestQuery query,BindingResult bindingResult) throws BindException, IOException {
        validate(bindingResult);
        //参数定义
        String flowNumber = UniqueCode.generateUniqueCode(1234);
        Long organzation_id = 1L;
        Double hasOrderedTotalAmont = 0.0;
        Long sale_id = 1L;
        Double totalAmout = 0.0;//销方信息写死  //统计总金额

        //把字符串的id处理成列表
        List<Long> orderArrs = this.arrIds(query);

        //参数校验
        Map<String, Object> map = this.validateInvoice(query.getCustomer_type(), query.getHeader(), query.getTax_id_number(), query.getLocation(), query.getBank(), query.getBank_account(), query.getTelephone(), query.getMobile(), query.getEmail(), orderArrs);
        if (!(Boolean) map.get("success"))
            return apiFail((String) map.get("message"));

        //是否开票校验 加 计算 订单总金额
        Map<String, Object> mapt = this.invoicedAndTotalAmout(orderArrs, totalAmout);
        if (!(Boolean) mapt.get("success"))
            return apiFail((String) mapt.get("message"));
        totalAmout = (Double) mapt.get("total");

        //开票中判重


        //通过orderid查询ordertransction
        List<OrderTransaction> orderTransactions = this.getOrderTranscation(orderArrs);

        //交易金额和可开票金额验证
        Map<String, Object> mapTandO = this.validateTransctionAndOrder(hasOrderedTotalAmont, totalAmout, orderTransactions);


        //获取到经营单位id 查看是否为同一个单位的订单
        organzation_id = this.getOrganzationId(map);

        //获取销方信息
        EntityWrapper<SalesInformation> ews = new EntityWrapper<>();
        ews.eq("org_id", organzation_id);
        SalesInformation salesInformation1 = iSalesInformationService.selectOne(ews);
        if(salesInformation1 == null){
            return apiFail("未找到销方信息");
        }

        // 获取购方用户关联用户
        // 获取销方信息
        SalesInformation salesInformation = iSalesInformationService.selectById(salesInformation1.getId());

        Customer customer = iCustomerService.selectById(getCurrentUser().getId());//buyerInformation.getCustomerId()

        if(customer == null){
            return apiFail("未找到注册用户");
        }

        //用模板生成xml
        String xmls = this.makeXml(salesInformation, flowNumber, totalAmout, orderTransactions, query, customer);

        //查询开过的票
        EntityWrapper<InvoiceInformationRequest> ewrequset = new EntityWrapper<>();

        List<InvoiceInformationRequest> irs = invoiceInformationRequestService.selectLastOne(ewrequset);

//        //获取最后的发票,以发票开票状态进行判重
//        InvoiceInformationRequest invoiceInformationRequestnow = irs.get(0);
//        if(invoiceInformationRequestnow.getBillStatus().equals(BillStatusEnum.ING)){
//            return apiFail("正在开票中，请不要重复开");
//        }

        if (irs != null && (! irs.isEmpty())) {
            String originbeforeXml = irs.get(0).getRequestXml();
            String finalXml = "";

            finalXml = originbeforeXml.replaceFirst("<fpqqlsh>(.*)</fpqqlsh>","<fpqqlsh>"+flowNumber+"</fpqqlsh>");

            if(finalXml.equals(xmls)){
                return apiFail("这张票正在开，请不要重复开");
            }
        }

//        // 开发票
        try {
            Map<String, Object> mapBaiwang = this.baiWangInvoice(xmls, query, salesInformation, flowNumber, sale_id, totalAmout, organzation_id);
            if(!(Boolean) mapBaiwang.get("success"))

                return apiFail((String) mapBaiwang.get("message"));
            return apiSuccess("");//invoiceInformationRequest.getInvoiceId()
        } catch (GeneralSecurityException e) {
            return apiFail(e.getMessage());
        } catch (IOException e) {
            return apiFail(e.getMessage());
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }
    }


    /**
     * 发票红冲
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/cancleInovicent/{invoice_id}")
    @Transactional
    public ApiResponse<Object> cancleInovicent(@PathVariable Long invoice_id
    ) throws IOException
    {
        //数据验证
        Long customer_id = getCurrentUser().getId();
        EntityWrapper<InvoiceInformationResult> ew = new EntityWrapper<InvoiceInformationResult>();
        ew.eq("invoice_id", invoice_id);

        InvoiceInformationResult invoiceInformationResult = iInvoiceInformationResultService.selectOne(ew);

        if(invoiceInformationResult == null)
            return apiFail("查不到相应的发票信息");

        InvoiceInformationRequest invoiceInformationRequest = invoiceInformationRequestService.selectById(invoiceInformationResult.getInvoiceId());

        if(customer_id != invoiceInformationRequest.getCustomerId())
            return apiFail("您无权红冲这张发票");

        String flowNumber = UniqueCode.generateUniqueCode(1234);

        String xml = invoiceInformationRequest.getRequestXml();
        xml = xml.replaceFirst("<fpqqlsh>(.*)</fpqqlsh>","<fpqqlsh>"+flowNumber+"</fpqqlsh>");
        xml = xml.replaceFirst("<kplx>0</kplx>","<kplx>1</kplx>");
        xml = xml.replaceFirst("<yfpdm/>", "<yfpdm>"+ invoiceInformationResult.getInvocieCode() +"</yfpdm>");
        xml = xml.replaceFirst("<yfphm/>", "<yfphm>"+ invoiceInformationResult.getInvoiceNumber() +"</yfphm>");
        xml = xml.replaceFirst("<jshj>(.*)</jshj>","<jshj>-$1</jshj>");
        xml = xml.replaceFirst("<je>(.*)</je>","<je>-$1</je>");

        try {
            BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
            String msg = client.rpc2(baiwangUrl, xml, "");
            InvoiceReturnResponse resultResponse = this.delXml(msg);

            if(resultResponse == null){
                return apiFail("发票请求失败");
            }

            if (!resultResponse.getRtncode().equals("0000")) {
                return apiFail("开票失败,失败原因：" + resultResponse.getRtnmsg());
            }

            //插入红冲发票
            InvoiceInformationRequest invoiceInformationRequest_hongchong = new InvoiceInformationRequest();
            BeanUtils.copyProperties(invoiceInformationRequest, invoiceInformationRequest_hongchong);
            invoiceInformationRequest_hongchong.setInvoiceId(null);
            invoiceInformationRequest_hongchong.setOriginalInvoiceId(invoiceInformationRequest.getInvoiceId());
            invoiceInformationRequest_hongchong.setBillType(BillTypeEnum.HONG_CHONG);
            invoiceInformationRequest_hongchong.setInvoiceFlowNumber(flowNumber);
            invoiceInformationRequest_hongchong.setOriginalInvoiceNumber(String.valueOf(invoiceInformationResult.getInvoiceNumber()));
            invoiceInformationRequest_hongchong.setOriginalInvoiceCode(invoiceInformationResult.getInvocieCode());
            invoiceInformationRequest_hongchong.setRequestXml(xml);
            invoiceInformationRequest_hongchong.setBillStatus(BillStatusEnum.ING);

            invoiceInformationRequestService.insert(invoiceInformationRequest_hongchong);


            //成功后修改原发票
            invoiceInformationRequest.setBillType(BillTypeEnum.HONG_CHONG);
            invoiceInformationRequest.setOriginalInvoiceNumber(String.valueOf(invoiceInformationResult.getInvoiceNumber()));
            invoiceInformationRequest.setOriginalInvoiceCode(invoiceInformationResult.getInvocieCode());

            invoiceInformationRequestService.updateById(invoiceInformationRequest);

            //执行异步,查询发票
            inoviceTask.doHongChong(invoiceInformationRequest_hongchong.getInvoiceId());

            return apiSuccess("");

        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

    }

    private String makeXml(SalesInformation salesInformation, String flowNumber, Double totalAmout,
                           List<OrderTransaction> orderTransactions, InvoiceInformationRequestQuery query,
                           Customer customer
    ) throws IOException {
        String xmls = "";
        String template = IOUtils.toString(inoviceCreateRequest.getInputStream(), "UTF-8");
        template = template.replace("\n", "");
        template = template.replaceAll(">( *)<", "><");;
        Map<String, Object> model = new HashMap<>();

        model.put("salesInformation", salesInformation);

        if((query.getHeader() == null || query.getHeader() == "") && query.getCustomer_type() == 0){
            query.setHeader("个人");
        }

        model.put("query", query);
        model.put("flowNumber", flowNumber);
//        model.put("customer", customer);
        model.put("totalAmout", totalAmout);
        model.put("orderTransactions", orderTransactions);
        model.put("mobile", query.getMobile());
        model.put("email", query.getEmail());
        xmls = YxyStringUtils.velocityRender(template, model);
        return xmls;
    }

    @Transient
    private Map<String, Object> baiWangInvoice(String xmls, InvoiceInformationRequestQuery query, SalesInformation salesInformation,
                                               String flowNumber, Long sale_id, Double totalAmout, Long organzation_id
                                               )
                                                throws Exception {
        Map<String, Object> map = new HashMap<>();

        BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
        String msg = client.rpc2(baiwangUrl, xmls, "");
        InvoiceReturnResponse resultResponse = this.delXml(msg);

        if(resultResponse == null){
            map.put("success", false);
            map.put("message", "发票请求失败");
            return map;
        }

        if (!resultResponse.getRtncode().equals("0000")) {
            map.put("success", false);
            map.put("message", "开票失败,失败原因：" + resultResponse.getRtnmsg());
            return map;
        }

        //生成购方信息
        EntityWrapper<BuyerInformation> buyerInformationEntityWrapper = new EntityWrapper<>();

        buyerInformationEntityWrapper.eq("customer_id", getCurrentUser().getId());
        Map<String, Object> buyerInformation1 = iBuyerInformationService.selectMap(buyerInformationEntityWrapper);
        if (buyerInformation1 == null && query.getCustomer_type() == 1) {//只有单位才可以存
            BuyerInformation buyerInformation2 = this.InsertBuyerInformation(query.getCustomer_type(), query.getHeader(), query.getTax_id_number(), query.getLocation(), query.getBank(), query.getBank_account(), query.getTelephone(), query.getMobile(), query.getEmail());
            iBuyerInformationService.insert(buyerInformation2);
        }

        String orderIds = query.getOrderids();
        String[] orderArray  = orderIds.split(",");
        List<Long> orderArrs = new ArrayList<>();

        for (String s : orderArray) {
            orderArrs.add(Long.valueOf(s));
        }
        //更改订单状态
        for (Long orderid:orderArrs) {
            OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
            orderInfo.setRequestedBill(true);
            iOrderInfoService.updateById(orderInfo);
        }
        //生成invoiceInformationRequest
        InvoiceInformationRequest invoiceInformationRequest = this.makeInvoiceRequest(query.getCustomer_type(), query.getHeader(), query.getTax_id_number(), query.getLocation(), query.getBank(), query.getBank_account(), query.getTelephone(), query.getMobile(),
                query.getEmail(), query.getType(), salesInformation, flowNumber, sale_id, totalAmout, organzation_id, xmls,getCurrentUser().getId());

        //插入发票信息
        try {
            invoiceInformationRequestService.insert(invoiceInformationRequest);
        } catch (Exception e) {
            return null;
//            return this.apiFail(e.getMessage());
        }

        //插入发票detail
        InvoiceDetail invoiceDetail = this.makeInvoiceDetail(invoiceInformationRequest, flowNumber, totalAmout, salesInformation, query.getOrderids());
        invoiceDetailService.insert(invoiceDetail);

        //执行异步,查询发票
        inoviceTask.doTaskOne(invoiceInformationRequest.getInvoiceId());

        map.put("success", true);
        map.put("message", "");
        return map;
    }


    private Map<String, Object> validateTransctionAndOrder(Double hasOrderedTotalAmont, Double totalAmout, List<OrderTransaction> orderTransactions)
    {
        Map<String, Object> map = new HashMap<>();
        for (OrderTransaction orderTransaction:orderTransactions) {
            hasOrderedTotalAmont += orderTransaction.getAmount();
        }
        if(! hasOrderedTotalAmont.equals(totalAmout)) {
            map.put("success", false);
            map.put("message", "可开票金额与交易金额不同,可开票金额:" + hasOrderedTotalAmont + "交易金额:" + totalAmout);
            return map;
        }
        map.put("success", true);
        map.put("message", "");
        return map;
    }

    /**
     * 获取订单的交易记录
     * @param orderArrs
     * @return
     */
    private List<OrderTransaction> getOrderTranscation(List<Long> orderArrs)
    {
        List<OrderTransaction> orderTransactions = new ArrayList<>();
        EntityWrapper<OrderTransaction> ew1 = new EntityWrapper<>();
        ew1.in("order_id", orderArrs);
        orderTransactions = iOrderTransactionService.selectList(ew1);
        return orderTransactions;
    }


    /**
     * 获取单位id
     * @return
     */
    private Long getOrganzationId(Map<String, Object> map)
    {
        Long organzation_id = 1L;
        Set<Long> organzationids = (Set<Long>) map.get("organzationids");
        InvoiceRequestData invoiceRequestData = new InvoiceRequestData();
        InvoiceRequestDataList invoiceRequestDataList = new InvoiceRequestDataList();
        for (Long v : organzationids) {
            organzation_id = v;
        }
        return organzation_id;
    }


    /**
     * 获取列表形式的订单id
     * @param query
     * @return
     */
    private List<Long> arrIds(InvoiceInformationRequestQuery query)
    {
        String[] orderArray = query.getOrderids().split(",");
        List<Long> orderArrs = new ArrayList<>();
        for (String s : orderArray) {
            orderArrs.add(Long.valueOf(s));
        }
        return orderArrs;
    }


    private Long returnOrganzationId(Map<String, Object> map, Long organzation_id)
    {

////     正式用的
////      EntityWrapper<SalesInformation> ewSale = new EntityWrapper<>();
////      ewSale.eq("organzation_id", organzation_id);
////      SalesInformation salesInformation =  iSalesInformationService.selectOne(ewSale);
////      Organization organization = iOrganizationService.selectById(organzation_id);
//        //创建购方信息
//        Long buyerId = 1L;//初始化buyerId

        Set<Long> organzationids = (Set<Long>) map.get("organzationids");
        InvoiceRequestData invoiceRequestData = new InvoiceRequestData();
        InvoiceRequestDataList invoiceRequestDataList = new InvoiceRequestDataList();
        for (Long v : organzationids) {
            organzation_id = v;
        }
        return organzation_id;
    }


    private Map<String, Object> invoicedAndTotalAmout(List<Long>orderArrs, Double totalAmout)
    {
        Map<String, Object> map = new HashMap<>();

        //是否开票校验 加 计算 订单总金额
        EntityWrapper<OrderInfo> ew = new EntityWrapper<>();
        ew.in("id", orderArrs);
        List<OrderInfo> orderInfos = iOrderInfoService.selectList(ew);
        for (OrderInfo orderInfo : orderInfos) {
            if (orderInfo.getRequestedBill()) {
                map.put("success", false);
                map.put("message", "开过票了");
                return map;
            }
            totalAmout += orderInfo.getInvoiceAmount();
        }

        map.put("success", true);
        map.put("message", "");
        map.put("total", totalAmout);
        return map;
    }

    /**
     * IDS 返回成list
     * @param orderids
     * @return
     */
    private List<Long> idsToList(String orderids)
    {
        //把字符串的id处理成列表
        String[] orderArray = orderids.split(",");
        List<Long> orderArrs = new ArrayList<>();
        for (String s : orderArray) {
            orderArrs.add(Long.valueOf(s));
        }
        return orderArrs;
    }

    /**
     * 处理返回的xml
     * @param msg
     * @return
     */
    private InvoiceReturnResponse delXml(String msg)
    {
        if (msg.startsWith("\"")) {
            msg = msg.substring(1);
        }
        if (msg.endsWith("\"")) {
            msg = msg.substring(0, msg.length() - 1);
        }
        msg = msg.replace("\\", "");
        InvoiceReturnResponse resultResponse = JaxbXMLUtil.xmlToBean(msg, InvoiceReturnResponse.class);
        return resultResponse;
    }


    /**
     * 生成插入详情表的数据
     * @param invoiceInformationRequest
     * @param flowNumber
     * @param totalAmout
     * @param salesInformation
     * @param orderids
     * @return
     */
    private InvoiceDetail makeInvoiceDetail(InvoiceInformationRequest invoiceInformationRequest,
                                            String flowNumber, Double totalAmout, SalesInformation salesInformation,
                                            String orderids
    )
    {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoiceId(invoiceInformationRequest.getInvoiceId());
        invoiceDetail.setInvoiceFlowNumber(flowNumber);
        invoiceDetail.setRowNumber("1");
        invoiceDetail.setInvoiceProperty("0");
        invoiceDetail.setProductCode("3040502020200000000");
        invoiceDetail.setSelfDefinationCode("01");
        invoiceDetail.setProductName("停车费");
        invoiceDetail.setAmout(totalAmout);
        invoiceDetail.setCotainTaxMark(1);
        invoiceDetail.setVatSpecialManage(salesInformation.getVatTaxSpecialManage());
        invoiceDetail.setPreferentialPolicieMark(1);
        invoiceDetail.setTaxRate(salesInformation.getTaxRate());
        invoiceDetail.setOrderId(orderids);

        return invoiceDetail;
    }


    /**
     * 生成插入请求表的数据
     * @param customer_type
     * @param header
     * @param tax_id_number
     * @param location
     * @param bank
     * @param bank_account
     * @param telephone
     * @param mobile
     * @param email
     * @param type
     * @param salesInformation
     * @param flowNumber
     * @param sale_id
     * @param totalAmout
     * @return
     */
    private InvoiceInformationRequest makeInvoiceRequest(Integer customer_type, String header, String tax_id_number,
                                                         String location, String bank, String bank_account,
                                                         String telephone, String mobile, String email, Integer type, SalesInformation salesInformation,
                                                         String flowNumber, Long sale_id, Double totalAmout, Long organzation_id,
                                                         String xmls,
                                                         Long customerId
    )
    {
        InvoiceInformationRequest invoiceInformationRequest = new InvoiceInformationRequest();
        invoiceInformationRequest.setInvoiceFlowNumber(flowNumber);//流水号
        invoiceInformationRequest.setBillPointCode(salesInformation.getBillCode());//开票点
        invoiceInformationRequest.setBuyId(customerId);
        invoiceInformationRequest.setInvoiceTypeCode("026");//发票类型代码
        invoiceInformationRequest.setApplyWay(String.valueOf(salesInformation.getBillChannel()));//申请途经
        invoiceInformationRequest.setCustomerId(getCurrentUser().getId());
        invoiceInformationRequest.setSaleId(sale_id);//销方
        invoiceInformationRequest.setMobile(mobile);//电话
        invoiceInformationRequest.setEmail(email);
        invoiceInformationRequest.setPayee(salesInformation.getPayee());//收款人
        invoiceInformationRequest.setSsuer(salesInformation.getIssuer());//开票人
        invoiceInformationRequest.setManager(salesInformation.getManager());//审核人
        invoiceInformationRequest.setBillStatus(BillStatusEnum.ING);//设置开票中
        if (type == 1) {
            invoiceInformationRequest.setBillType(BillTypeEnum.KAI_PIAO);//0 开票 1红冲
        }
        if (type == 2) {//红冲需要穿这两个字段
            invoiceInformationRequest.setBillType(BillTypeEnum.HONG_CHONG);//0 开票 1红冲
            invoiceInformationRequest.setOriginalInvoiceCode("1231");//原发票号码
            invoiceInformationRequest.setOriginalInvoiceNumber("");//原发票代码
        }
        invoiceInformationRequest.setRemarks("");//备注设为空
        invoiceInformationRequest.setBillChannel(String.valueOf(salesInformation.getBillChannel()));//
        invoiceInformationRequest.setApplyBillDate(new Date());
        invoiceInformationRequest.setPriceTaxTotal(totalAmout);//价税合计

        //添加冗余字段org_id
        invoiceInformationRequest.setOrganizationId(organzation_id);

        //添加冗余字段 request_xml
        invoiceInformationRequest.setRequestXml(xmls);

        Map<String, Object> map3 = new HashMap<>();//购方信息
        map3.put("bank", bank);//购方开户行
        map3.put("bank_account", bank_account);//购方开户行账户
        map3.put("mobile", mobile);//购方接收发票电话
        map3.put("email", email);//购方开户行账户
        map3.put("header", header);//购方开户行账户
        map3.put("location", location);//购方开户行账户
        map3.put("tax_id_number", tax_id_number);//购方开户行账户
        map3.put("telephone", telephone);//购方开户行账户
        map3.put("customer_type", customer_type);//用户类型

        String request_data = JSON.toJSONString(map3);
        invoiceInformationRequest.setRequestData(request_data);

        return invoiceInformationRequest;
    }

//    public ApiResponse<Object> invoiceRequestSearch()
//    {
//        //查询BUYERID
//        EntityWrapper<BuyerInformation> ew = new EntityWrapper<>();
//        ew.eq("customer_id", getCurrentUser().getId());
//        Map<String, Object> map = iBuyerInformationService.selectMap(ew);
//
//        Long buyerId = (Long) map.get("id");
//
//        //用buyer_id去查开票记录
//        EntityWrapper<InvoiceInformationRequest> ew2 = new EntityWrapper<>();
//        //ew2.
//        List<InvoiceInformationRequest> list = new ArrayList<>();
//        return apiSuccess("");
//    }


    /**
     * 生成购方信息
     * @param customer_type
     * @param header
     * @param tax_id_number
     * @param location
     * @param bank
     * @param bank_account
     * @param telephone
     * @param mobile
     * @param email
     * @return
     */
    private BuyerInformation InsertBuyerInformation(Integer customer_type, String header, String tax_id_number,
                                                    String location, String bank, String bank_account,
                                                    String telephone, String mobile, String email

    )
    {

        BuyerInformation buyerInformation2 = new BuyerInformation();
        buyerInformation2.setInvoiceHeader(header);
        buyerInformation2.setTaxpayerIdentificationNumber(tax_id_number);
        buyerInformation2.setLocation(location);
        buyerInformation2.setBank(bank);
        buyerInformation2.setBankAccount(bank_account);
        buyerInformation2.setMobile(mobile);
        buyerInformation2.setCustomerId(getCurrentUser().getId());
        buyerInformation2.setEmail(email);
        buyerInformation2.setInUse(InUseEnum.IN_USE);

        return buyerInformation2;
    }



    /**
     * 校验字段
     * @param customer_type
     * @param header
     * @param tax_id_number
     * @param location
     * @param bank
     * @param bank_account
     * @param telephone
     * @param mobile
     * @param email
     * @param orderArrs
     * @return
     */
    private Map<String, Object> validateInvoice(Integer customer_type, String header, String tax_id_number,
                                                String location, String bank, String bank_account,
                                                String telephone, String mobile, String email,
                                                List<Long> orderArrs
    )
    {
        Map<String, Object> map = new HashMap<>();

        //企业用户的校验
        if(customer_type == 1){
            if(header == null){
                map.put("success", false);
                map.put("message", "抬头不得为空");
                return map;
            }
            if(tax_id_number == null || tax_id_number.length() <10 || tax_id_number.length()> 30){
                map.put("success", false);
                map.put("message", "企业用户纳税人识别号不得为空,不得小于10位，不得大于30位");
                return map;
            }
            if(location == null){
                map.put("success", false);
                map.put("message", "企业用户地址不得为空");
                return map;
            }
            if(bank == null){
                map.put("success", false);
                map.put("message", "企业用户开户行不得为空");
                return map;
            }
            if(bank_account == null){
                map.put("success", false);
                map.put("message", "企业用户开户行账号不得为空");
                return map;
            }
            if(telephone == null){
                map.put("success", false);
                map.put("message", "企业用户电话(telephone)不得为空，电话格式不正确");
                return map;
            }
        }

//        if(mobile == null || !mobile.matches("^\\d{11}$|^$")){
//            map.put("success", false);
//            map.put("message", "mobile不得为空,电话格式不正确");
//            return map;
//        }
//        if(email == null){
//            map.put("success", false);
//            map.put("message", "邮箱不得为空");
//            return map;
//        }

        //如果
        EntityWrapper<OrderInfo> ewOrder = new EntityWrapper<>();
        ewOrder.in("id", orderArrs);
        List<OrderInfo> orderInfos1 = iOrderInfoService.selectList(ewOrder);

        Set<Long> organzationids = new HashSet<>();
        for (OrderInfo orderInfo:orderInfos1) {
            organzationids.add(orderInfo.getOrganizationId());
        }
        if(organzationids.size()>1){
            map.put("success", false);
            map.put("message", "不同单位的订单不可开同一张发票");
            return map;
        }
        map.put("success", true);
        map.put("message", "");
        map.put("organzationids", organzationids);

        return map;
    }




    @Transactional
    @PostMapping("/avilableInvoiceResult")
    public ApiResponse<Object> avilableInvoiceResult(@RequestParam(value = "invoice_id", defaultValue = "", required = true) Long invoice_id,

                                                     @RequestParam(value = "order_id", defaultValue = "", required = true) Integer order_id
    ) {
        String pdfStorePath = uploadPath + "/invoice/";
        InvoiceInformationRequest invoiceInformationRequest = invoiceInformationRequestService.selectById(invoice_id);
        Long sale_id = 1L;//先写死
        SalesInformation salesInformation = iSalesInformationService.selectById(sale_id);
        Organization organization = iOrganizationService.selectById(salesInformation.getOrganzationId());

        String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<fwpackage>" +
                "<jsnr>" +
                "<fwdm>"+ salesInformation.getSearchServiceCode() +"</fwdm>" +
                "<nsrsbh>" + salesInformation.getTaxpayerIdentificationNumber() + "</nsrsbh>" +
                "<kpqd>"+ salesInformation.getBillChannel() +"</kpqd>" +
                "<jrdm>bwc22345678</jrdm>" +
                "</jsnr>" +
                "<ywnr>" +
                "<![CDATA[<"+
                "?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<request>" +
                "<fpqqlsh>00" + invoiceInformationRequest.getInvoiceFlowNumber() +  "</fpqqlsh>" +
                "<nsrsbh>" + salesInformation.getTaxpayerIdentificationNumber() + "</nsrsbh>" + // salesInformation.getTaxpayerIdentificationNumber() +
                "</request>]]>"+
                "</ywnr>" +
                "</fwpackage>";

        try {
            BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
            String msg = client.rpc2(baiwangUrl, requestXml,"");
            if (msg.startsWith("\"")) {
                msg = msg.substring(1);
            }
            if (msg.endsWith("\"")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            msg = msg.replace("\\", "");
            InvoiceResultReturnResponse resultResponse = JaxbXMLUtil.xmlToBean(msg, InvoiceResultReturnResponse.class);
            if(resultResponse.getRtncode().equals("0000")){
                //成功后，下载pdf
                String download = resultResponse.getPdfurl();
                if (StringUtils.isNotBlank(baiwangDownload)) {
                    download = download.replace("http://36.110.112.203:8095", baiwangDownload);
                }

                URL website = null;
                String flowNumber = UniqueCode.generateUniqueCode(4567);
                try {
                    File file = new File(pdfStorePath + flowNumber + "yxyinovice.pdf");

                    if (! file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }

                    Path target = file.toPath();
                    website = new URL(download);
                    InputStream in = website.openStream();
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //生成返回数据map
                Map<String, Object> map = new HashMap<>();
                //map.put("request_id", invoice_id);
                map.put("status", 2);
                map.put("timestamp", resultResponse.getKprq());
                map.put("invoice_id", invoice_id);
                map.put("invoice_url", resultResponse.getPdfurl());

                //讲道理如果，已经插入了结果，就不搞了
                EntityWrapper<InvoiceInformationResult> ewResult = new EntityWrapper<>();
                ewResult.eq("invoice_id", invoice_id);
                InvoiceInformationResult invoiceInformationResult1 = iInvoiceInformationResultService.selectOne(ewResult);

                if(invoiceInformationResult1 != null){
                    map.put("request_id", invoiceInformationResult1.getId());
                    return apiSuccess(map);
                }
                //插入结果表
                InvoiceInformationResult invoiceInformationResult = new InvoiceInformationResult();
                invoiceInformationResult.setInvoiceId(invoice_id);
                invoiceInformationResult.setInvoiceFlowNumber(resultResponse.getFpqqlsh());
                invoiceInformationResult.setBillDate(resultResponse.getKprq());
                invoiceInformationResult.setInvocieCode(resultResponse.getFpdm());
                invoiceInformationResult.setInvoiceNumber(resultResponse.getFphm());
                invoiceInformationResult.setPlatformInvoiceUrl(resultResponse.getPdfurl());
                invoiceInformationResult.setTaxInvoiceUrl(resultResponse.getPdfurl());
                invoiceInformationResult.setCloudPlatformInvoiceUrl(pdfStorePath + flowNumber + "yxyinovice.pdf");
                invoiceInformationResultService.insert(invoiceInformationResult);
                //插入订单发票关联关系表
                EntityWrapper<InvoiceDetail> ew1 = new EntityWrapper<>();
                ew1.eq("invoice_id", invoice_id);
                InvoiceDetail invoiceDetail = invoiceDetailService.selectOne(ew1);
                String orderIds = invoiceDetail.getOrderId();
                String[] orderArray  = orderIds.split(",");
                List<Long> orderArrs = new ArrayList<>();
                for (String s : orderArray) {
                    orderArrs.add(Long.valueOf(s));
                }
//                //从request获取数据
//                EntityWrapper<InvoiceInformationRequest> ew2 = new EntityWrapper<>();
//                ew2.eq("invoice_id", invoice_id);
                InvoiceInformationRequest invoiceInformationRequest1 = invoiceInformationRequestService.selectById(invoice_id);
                //循环插入关系 修改订单状态
                for (Long orderid:orderArrs) {
                    OrderInvoice orderInvoice = new OrderInvoice();
                    orderInvoice.setInvoiceId(invoice_id);
                    orderInvoice.setOrderId(orderid);
                    orderInvoice.setInvoiceAmout(invoiceInformationRequest1.getPriceTaxTotal());
                    orderInvoice.setWhetherTogether(1);//是
                    if(orderArrs.size() > 1){
                        orderInvoice.setWhetherTogether(2);//2表示合开
                    }else{
                        orderInvoice.setWhetherTogether(1);//1表示未合开
                    }
                    orderInvoiceService.insert(orderInvoice);
                    //修改订单
                    OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
                    orderInfo.setRequestedBill(true);
                    iOrderInfoService.updateById(orderInfo);
                }

                //修改request表
                invoiceInformationRequest1.setTotalAmout(resultResponse.getHjje());
                invoiceInformationRequest1.setTotalTaxAmout(resultResponse.getHjse());
                invoiceInformationRequest1.setPriceTaxTotal(resultResponse.getJshj());
                invoiceInformationRequest1.setBillStatus(BillStatusEnum.COMPLETE);//开票完成
                invoiceInformationRequestService.updateById(invoiceInformationRequest1);

                if(invoiceInformationResult1!=null){
                    Long request_id =  invoiceInformationResult1.getId();
                    map.put("request_id", request_id);
                }
                return apiSuccess("");
            }else{
                return apiFail("开票失败,失败原因：" + resultResponse.getRtnmsg());
            }
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return apiSuccess("");
    }

    /**
     * 可开票的订单 运营单位 => 多少可开票
     * @return
     */
    @GetMapping("/avilableInvoiceOrder")
    public ApiResponse<Object> avilableInvoiceOrder()
    {
        List<Map<String, Object>> data = iOrderInfoService.selectOrganizationInvoiceInfo(getCurrentUser().getId());
        if (data.size() > 0) {
            Set<Long> organizationIds = new HashSet<>();
            data.forEach(item -> organizationIds.add((Long)item.get("organizationId")));
            Map<Long, String> orgNames = iOrganizationService.selectIdNameMap(organizationIds, "id", "full_name");

            data.forEach(item -> item.put("organizationName", orgNames.get(item.get("organizationId"))));
        }

        return apiSuccess(data);
    }

    @GetMapping("/getOrderInfoByOrganazationId/{organzation_id}")
    public ApiResponse<Object> getOrderInfoByOrganazationId(@PathVariable Long organzation_id)
    {

        Organization o = iOrganizationService.selectById(organzation_id);
        if(o == null){
            return apiFail("找不到该单位");
        }

        EntityWrapper<OrderInfo> ew = new EntityWrapper<>();
        ew.eq("requested_bill", false)//查没开过的
            .eq("user_id", getCurrentUser().getId())
            .eq("status", 1)
            .gt("invoice_amount", 0);//已完成的

        List<OrderInfo> list = iOrderInfoService.organzationSelectList(ew);

        if(list == null){
            return apiFail("查不到结果");
        }

        if (list.size() > 0) {
            list.forEach(item -> item.setOrganizationName(o.getFullName()));
        }
        return apiSuccess(list);
    }



    /**
     * 发票下载的邮件的地址
     * @param request_id
     * @return
     */
    @PostMapping("/emailInvoice/{request_id}")
    public ApiResponse<Object> emailInvoice(
            @PathVariable Long request_id
    ){
        Long user_id = getCurrentUser().getId();
        //InvoiceInformationResult invoiceInformationResult = new InvoiceInformationResult();
        Wrapper<InvoiceInformationResult> invoiceInformationResultWrapper = new EntityWrapper<InvoiceInformationResult>();
        invoiceInformationResultWrapper.eq("invoice_id", request_id);
        InvoiceInformationResult invoiceInformationResult1 = iInvoiceInformationResultService.selectOne(invoiceInformationResultWrapper);
        InvoiceInformationRequest invoiceInformationRequest = invoiceInformationRequestService.selectById(invoiceInformationResult1.getInvoiceId());

        if(invoiceInformationResult1 == null){
            return apiFail("没有找到发票");
        }

        if(user_id != invoiceInformationRequest.getCustomerId()){
            return apiFail("发票不属于此用户");
        }
        String message = invoiceInformationResult1.getPlatformInvoiceUrl();

        //查询邮箱
        Wrapper<CustomerAccount> customerAccountWrapper = new EntityWrapper<>();
        customerAccountWrapper.eq("user_id", user_id);
        CustomerAccount customerAccount = iCustomerAccountService.selectOne(customerAccountWrapper);
        if(customerAccount.getEmail() == null || StringUtils.isBlank(customerAccount.getEmail())){
            return apiFail("当前用户未填写邮箱，请完善个人信息");
        }
        mailService.sendSimpleMail(customerAccount.getEmail(),"发票的下载地址", message);//"15029672126@163.com"
        return apiSuccess(customerAccount);

    }

    @GetMapping("/info")
    public ApiResponse getBusinessInvoiceInfo() {
        Customer user = getCurrentUser();

        String info =  invoiceInformationRequestService.getLastUsedInformation(user.getId());

        if (info == null) {
            return this.apiFail("找不到可用信息!");
        } else {
            Map<String, Object> map = new HashMap<>();

            map.put("buyerInformation", JSON.parse(info));
            return this.apiSuccess(map);
        }
    }

}