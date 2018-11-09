package com.yxytech.parkingcloud.platform.controller;
import com.alibaba.fastjson.JSON;
import com.baiwang.baiwangcloud.client.BaiwangCouldAPIClient;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.BillStatusEnum;
import com.yxytech.parkingcloud.core.enums.BillTypeEnum;
import com.yxytech.parkingcloud.core.service.IInvoiceInformationRequestService;
import com.yxytech.parkingcloud.core.service.IInvoiceInformationResultService;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.task.InoviceTask;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/invoiceInformation")
public class InvoiceInformationController extends BaseController{

    @Autowired
    private IInvoiceInformationRequestService invoiceInformationRequestService;

    @Autowired
    private IInvoiceInformationResultService iInvoiceInformationResultService;

    @Autowired
    private IInvoiceInformationRequestService iInvoiceInformationRequestService;

    @Autowired
    private IOrderInfoService iOrderInfoService;

    @Value("${baiwangUrl}")
    private  String baiwangUrl;

    @Autowired
    private InoviceTask inoviceTask;

    @GetMapping("/avilableInvoiceOrder")
    @Access(permissionName = "发票列表",permissionCode = "INVOICE_LIST",moduleCode = "invoice_manage")
    public ApiResponse<Object> avilableInvoiceOrder(@RequestParam(value = "invoice_flow_number", defaultValue = "", required = false) String invoice_flow_number,
                                                    @RequestParam(value = "start_time",defaultValue = "",required = false) String start_time,
                                                    @RequestParam(value = "end_time",defaultValue = "",required = false) String end_time,
                                                    @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                                    @RequestParam(value = "size",required = false) Integer size

    )
    {
        // 创建分页对象
        Page<InvoiceInformationRequest> p  = new Page<InvoiceInformationRequest>(page, size);

        EntityWrapper<InvoiceInformationRequest> invoiceInformationRequestWrapper = new EntityWrapper<>();
        Date startTime = null;
        Date endTime = null;

        try {
            startTime = DateParserUtil.parseDate(start_time, true);
            endTime = DateParserUtil.parseDate(end_time, false);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        //invoiceInformationRequestWrapper.where("yxy_order_info.requested_bill is FALSE");//查没开过的

        invoiceInformationRequestWrapper.like(invoice_flow_number!=null,"yxy_invoice_information_request.invoice_flow_number", invoice_flow_number);
        //invoiceInformationRequestWrapper.eq("yxy_invoice_information_request.bill_type", 0);
        invoiceInformationRequestWrapper.ne("yxy_invoice_information_request.bill_type",1);
        invoiceInformationRequestWrapper.where(startTime != null, "yxy_invoice_information_request.apply_bill_date >= {0}", startTime);
        invoiceInformationRequestWrapper.where(endTime != null, "yxy_invoice_information_request.apply_bill_date <= {0}", endTime);

        p = iInvoiceInformationRequestService.selectPage(p, invoiceInformationRequestWrapper);

        List<InvoiceInformationRequest> invoiceInformationRequests = p.getRecords();
//        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
//        map
        for (InvoiceInformationRequest invoiceInformationRequest:invoiceInformationRequests) {
            Map<String, Object> tmpmap = new HashMap<>();
            tmpmap.put("request", invoiceInformationRequest);
            tmpmap.put("buyInformation", JSON.parse(invoiceInformationRequest.getRequestData()));
            list.add(tmpmap);
        }

        Page<Object> p1 = new Page<Object>(page, size);

        BeanUtils.copyProperties(p, p1);
        p1.setRecords(list);

        return this.apiSuccess(p1);
    }

    /**
     * 缺一个查询红冲发票的接口
     */

    /**
     *
     * @param order_number
     * @param invoice_flow_number
     * @param start_time
     * @param end_time
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/orderInvoice")
    @Access(permissionName = "订单发票列表",permissionCode = "ORDER_INVOICE_LIST",moduleCode = "invoice_manage")
    public ApiResponse<Object> orderInvoice(@RequestParam(value = "order_number", defaultValue = "", required = false) String order_number,
                                            @RequestParam(value = "invoice_flow_number", defaultValue = "", required = false) String invoice_flow_number,
                                            @RequestParam(value = "start_time",defaultValue = "",required = false) String start_time,
                                            @RequestParam(value = "end_time",defaultValue = "",required = false) String end_time,
                                            @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                            @RequestParam(value = "size",required = false) Integer size
                                            )
    {
        // 创建分页对象
        Date startTime = null;
        Date endTime = null;
        Page<OrderInfo> p  = new Page<OrderInfo>(page, size);


        try {
            startTime = DateParserUtil.parseDate(start_time, true);
            endTime = DateParserUtil.parseDate(end_time, false);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        EntityWrapper<OrderInfo> orderInfoEntityWrapper = new EntityWrapper<>();
        orderInfoEntityWrapper.like(order_number!=null,"yxy_order_info.order_number", order_number);
        orderInfoEntityWrapper.like(invoice_flow_number!=null, "yxy_invoice_information_request.invoice_flow_number", invoice_flow_number);
        orderInfoEntityWrapper.where(startTime!=null,"yxy_invoice_information_request.apply_bill_date >= {0}", startTime);
        orderInfoEntityWrapper.where(endTime!=null,"yxy_invoice_information_request.apply_bill_date <= {0}", endTime);
        p = iOrderInfoService.invoiceSelectPage(p, orderInfoEntityWrapper);

        List<OrderInfo> orderInfos = p.getRecords();
        List<Object> list = new ArrayList<>();
        for (OrderInfo orderInfo:orderInfos) {
            Map<String, Object> tmpmap = new HashMap<>();
            tmpmap.put("orderInfo", orderInfo);
            tmpmap.put("buyerInfo", JSON.parse(orderInfo.getInvoiceInformationRequest().getRequestData()));
            list.add(tmpmap);
        }

        Page<Object> p1 = new Page<Object>(page, size);
        BeanUtils.copyProperties(p, p1);
        p1.setRecords(list);
        return apiSuccess(p1);
    }

    @GetMapping("/getDetail/{invoice_id}")
    public ApiResponse<Object> getDetail(@PathVariable Integer invoice_id)
    {
        Wrapper<InvoiceInformationRequest> ew = new EntityWrapper<InvoiceInformationRequest>();
        ew.eq("yxy_invoice_information_request.invoice_id", invoice_id);

        InvoiceInformationRequest invoiceInformationRequest = iInvoiceInformationRequestService.selectOne(ew);

        if(invoiceInformationRequest == null){
            return apiFail("找不到该发票");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("invoice", invoiceInformationRequest);
        Object buyerInformation = JSON.parse(invoiceInformationRequest.getRequestData());
        map.put("buyerInformation", buyerInformation);
        return apiSuccess(map);
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

        Long orgId = invoiceInformationRequest.getOrganizationId();

        UserAccount userAccount = (UserAccount)this.userInfo().get("userAccount");

        Long userOrgId = userAccount.getOrgId();


       // return apiSuccess(orgId);

        if(orgId != userOrgId){
            return apiFail("您无权红冲这张发票");
        }

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
            invoiceInformationRequest.setBillType(BillTypeEnum.BEI_HONG_CHONG);
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

    @GetMapping("getCancelInvoice/{invoice_id}")
    public ApiResponse<Object> getCancelInvoice(@PathVariable Long invoice_id)
    {

        EntityWrapper<InvoiceInformationRequest> ew = new EntityWrapper<InvoiceInformationRequest>();
        ew.eq("original_invoice_id", invoice_id);
        List<InvoiceInformationRequest> invoiceInformationRequest = invoiceInformationRequestService.selectLastOne(ew);
        if(invoiceInformationRequest.size() < 0 || invoiceInformationRequest.size() == 0){
            return apiFail("查不到这张发票");
        }

        EntityWrapper<InvoiceInformationResult> ew2 = new EntityWrapper<InvoiceInformationResult>();
        ew2.eq("invoice_id", invoiceInformationRequest.get(0).getInvoiceId());

        InvoiceInformationResult invoiceInformationResult = iInvoiceInformationResultService.selectOne(ew2);

        if(invoiceInformationResult == null){
            return apiFail("发票结果不存在");
        }

        return apiSuccess(invoiceInformationResult);
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



}


