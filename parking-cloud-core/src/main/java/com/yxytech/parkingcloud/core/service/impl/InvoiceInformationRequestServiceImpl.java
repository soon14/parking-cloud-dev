package com.yxytech.parkingcloud.core.service.impl;

import com.baiwang.baiwangcloud.client.BaiwangCouldAPIClient;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.commons.utils.YxyStringUtils;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.BillStatusEnum;
import com.yxytech.parkingcloud.core.mapper.InvoiceInformationRequestMapper;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import com.yxytech.parkingcloud.core.utils.Pdf2image;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-02
 */
@Service
public class InvoiceInformationRequestServiceImpl extends ServiceImpl<InvoiceInformationRequestMapper, InvoiceInformationRequest> implements IInvoiceInformationRequestService {
    @Autowired
    private ISalesInformationService iSalesInformationService;

    @Autowired
    private IInvoiceInformationResultService iInvoiceInformationResultService;

    @Autowired
    private IInvoiceDetailService iInvoiceDetailService;

    @Autowired
    private IOrderInvoiceService iOrderInvoiceService;

    @Autowired
    private IOrderInfoService iOrderInfoService;

    @Value("classpath:vm/invoice_query_request.vm")
    private Resource inoviceQueryRequest;

    @Value("${baiwangUrl}")
    private  String baiwangUrl;

    @Value("${baiwangDownload}")
    private String baiwangDownload;

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    public Map<String, Object> setRequestXml(
            InvoiceRequestData invoiceRequestData,
            InvoiceInformationRequestForm invoiceInformationRequestForm,
            BuyerInformation buyerInformation,
            SalesInformation salesInformation,
            Organization organization,
            Customer customer,
            InvoiceRequestDataList invoiceRequestDataList
    ){
        invoiceRequestData.setFpqqlsh(invoiceInformationRequestForm.getInvoiceFlowNumber());
        invoiceRequestData.setKpddm(invoiceInformationRequestForm.getBillPointCode());
        invoiceRequestData.setFplxdm(invoiceInformationRequestForm.getInvoiceTypeCode());

        //插入购方信息
        invoiceRequestData.setGhdwmc(customer.getName());
        invoiceRequestData.setGhdwdm(buyerInformation.getTaxpayerIdentificationNumber());//
        invoiceRequestData.setGhdwdzdh(buyerInformation.getLocation() + buyerInformation.getMobile());//
        invoiceRequestData.setGhdwyhzh(buyerInformation.getBank() + buyerInformation.getBankAccount());//
        invoiceRequestData.setGfkhdh(invoiceInformationRequestForm.getMobile());//这个信息是手填的  13777445036
        invoiceRequestData.setGfkhyx(invoiceInformationRequestForm.getEmail());//这个信息是手填的

        //插入销方信息 ff808081594466e70159446722550000
        invoiceRequestData.setXsdwmc(salesInformation.getInvoiceHeader());
        invoiceRequestData.setXsdwdm(salesInformation.getTaxpayerIdentificationNumber());
        invoiceRequestData.setXsdwdzdh(salesInformation.getLocation() + salesInformation.getMobile());
        invoiceRequestData.setXsdwyhzh("");//salesInformation.getBank() + salesInformation.getBankAccount()

        //设置其他信息
        invoiceRequestData.setSkr(invoiceInformationRequestForm.getPayee());//这个信息是手填
        invoiceRequestData.setKpr(invoiceInformationRequestForm.getSsuer());//这个信息是手填
        invoiceRequestData.setFhr(invoiceInformationRequestForm.getManager());//这个信息是手填
        invoiceRequestData.setKplx((Integer) invoiceInformationRequestForm.getBillType().getValue());//这个信息是手填
        invoiceRequestData.setYfpdm(invoiceInformationRequestForm.getOriginalInvoiceCode());//这个信息是手填
        invoiceRequestData.setYfphm(invoiceInformationRequestForm.getOriginalInvoiceNumber());//这个信息是手填
        invoiceRequestData.setHjje(invoiceInformationRequestForm.getTotalAmout());//这个信息是手填
        invoiceRequestData.setHjse(invoiceInformationRequestForm.getTotalTaxAmout());//这个信息是手填
        invoiceRequestData.setJshj(invoiceInformationRequestForm.getPriceTaxTotal());//这个信息是手填
        invoiceRequestData.setBz("备注信息");
        invoiceRequestData.setByzd1("");
        invoiceRequestData.setByzd2("");
        invoiceRequestData.setByzd3("");
        invoiceRequestData.setBbh("spbbm");//备用字段暂时不加

        List<InvoiceRequestDataList> lists = new ArrayList<>();
        List i = invoiceInformationRequestForm.getInvoiceLists();

        //循环插入商品明细信息
        for (InvoiceDetail invoiceDetail :invoiceInformationRequestForm.getInvoiceLists()) {
            if(invoiceDetail.getRowNumber()!=null){
                invoiceRequestDataList.setFpmxxh(invoiceDetail.getRowNumber());//行号
            }
            if(invoiceDetail.getInvoiceProperty()!=null){
                invoiceRequestDataList.setFphxz(invoiceDetail.getInvoiceProperty());//发票性质
            }
            if(invoiceDetail.getProductCode()!=null){
                invoiceRequestDataList.setSpbm(invoiceDetail.getProductCode());//商品编码
            }
            if(invoiceDetail.getProductName()!=null){
                invoiceRequestDataList.setSpmc(invoiceDetail.getProductName());//商品名称
            }
            if(invoiceDetail.getAmout()!=null){
                invoiceRequestDataList.setJe(invoiceDetail.getAmout());//金额
            }
            if(invoiceDetail.getTaxAmout()!=null){
                invoiceRequestDataList.setSe(invoiceDetail.getTaxAmout());//税额
            }
            if(invoiceDetail.getTaxRate()!=null){
                invoiceRequestDataList.setSl(invoiceDetail.getTaxRate());//税率
            }
            if(invoiceDetail.getPreferentialPolicieMark()!=null){
                invoiceRequestDataList.setYhzcbs(invoiceDetail.getPreferentialPolicieMark());//优惠政策
            }
            if(invoiceDetail.getCotainTaxMark()!=null){
                invoiceRequestDataList.setHsbz(invoiceDetail.getCotainTaxMark());
            }

            if(invoiceDetail.getSelfDefinationCode()!=null){
                invoiceRequestDataList.setZxbm(invoiceDetail.getSelfDefinationCode());
            }
            invoiceRequestDataList.setSpsm("");
            invoiceRequestDataList.setGgxh("");
            invoiceRequestDataList.setDw("");
            invoiceRequestDataList.setSpsl("");
            invoiceRequestDataList.setSpdj("");
            invoiceRequestDataList.setKce(0.0);
            invoiceRequestDataList.setZhdyhh("");
            if(invoiceDetail.getVatSpecialManage()!=null){
                invoiceRequestDataList.setZzstsgl(invoiceDetail.getVatSpecialManage());
            }
            invoiceRequestDataList.setLslbs("");
            invoiceRequestDataList.setByzd1("");
            invoiceRequestDataList.setByzd2("");
            lists.add(invoiceRequestDataList);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("invoiceRequestData", invoiceRequestData);
        map.put("list", lists);
        map.put("jylshlist", invoiceInformationRequestForm.getTransactionSerialNumberList());
        return map;
    }

    @Override
    public Page<InvoiceInformationRequest> selectRequestPage(Page<InvoiceInformationRequest> page, @Param("ew") Wrapper<InvoiceInformationRequest> wrapper)
    {
        page.setRecords(baseMapper.selectRequestPage(page,wrapper));

        return page;
    }

    @Override
    public List<InvoiceInformationRequest> selectRequestList(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper){
        return baseMapper.selectRequestList(wrapper);
    }

    @Override
    public List<InvoiceInformationRequest> selectLastOne(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper){
        return baseMapper.selectLastOne(wrapper);
    }

    @Override
    public String getLastUsedInformation(Long userId) {
        return baseMapper.getLastUsedInformation(userId);
    }


    @Override
    @Transient
    public Map<String, Object> invoiceResult(){

        Map<String, Object> map = new HashMap<>();

       //查找正在开票中的发票
        EntityWrapper<InvoiceInformationRequest> ew = new EntityWrapper<>();
        ew.eq("bill_status", 0);//正在开票中的

        List<InvoiceInformationRequest> invoiceInformationRequests = new ArrayList<>();
        invoiceInformationRequests = this.selectRequestList(ew);


        List<InvoiceInformationRequest> invoiceInformationRequests1 = new ArrayList<>();
        List<InvoiceInformationResult> invoiceInformationResults = new ArrayList<>();//存放批量修改后的list
        List<OrderInvoice> orderInvoices = new ArrayList<>();
        List<OrderInfo> orderInfos = new ArrayList<>();


        for (InvoiceInformationRequest invoiceInformationRequest:invoiceInformationRequests) {
            try {
                String xml = this.makeResult(invoiceInformationRequest);

                BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
                String msg = client.rpc2(baiwangUrl, xml,"");
                if (msg.startsWith("\"")) {
                    msg = msg.substring(1);
                }
                if (msg.endsWith("\"")) {
                    msg = msg.substring(0, msg.length() - 1);
                }
                msg = msg.replace("\\", "");
                InvoiceResultReturnResponse resultResponse = JaxbXMLUtil.xmlToBean(msg, InvoiceResultReturnResponse.class);

                if(resultResponse == null){
                    map.put("success", false);
                    map.put("message",  "发票请求接口失败");
                    return map;
                }

                if(!resultResponse.getRtncode().equals("0000")){//请求失败
                    EntityWrapper<InvoiceDetail> ew2 = new EntityWrapper<>();
                    ew2.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                    InvoiceDetail invoiceDetail = iInvoiceDetailService.selectOne(ew2);


                    String orderIds = invoiceDetail.getOrderId();
                    String[] orderArray  = orderIds.split(",");
                    List<Long> orderArrs = new ArrayList<>();
                    for (String s : orderArray) {
                        orderArrs.add(Long.valueOf(s));
                    }
                    //不成功，就设置成未开票
                    for (Long orderid:orderArrs) {
                        OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
                        orderInfo.setRequestedBill(false);
                        orderInfos.add(orderInfo);
                        iOrderInfoService.updateById(orderInfo);
                    }
                    map.put("success", false);
                    map.put("message",  resultResponse.getRtnmsg());
                    return map;
                }
//                //下载pdf
                String download = resultResponse.getPdfurl();
                if (StringUtils.isNotBlank(baiwangDownload)) {
                    download = download.replace("http://36.110.112.203:8095", baiwangDownload);
                }

                String pdfStorePath = uploadPath + "/invoice/";

                URL website = null;
                String flowNumber = UniqueCode.generateUniqueCode(4567);
                File file = new File(pdfStorePath + flowNumber + "yxyinovice.pdf");

                if (! file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                Path target = file.toPath();
                website = new URL(download);
                InputStream in = website.openStream();
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);

                Pdf2image.pdf2img(pdfStorePath + flowNumber + "yxyinovice.pdf", pdfStorePath+ flowNumber + "yxyinovice.png","png");

//                //讲道理如果，已经插入了结果，就不搞了
                EntityWrapper<InvoiceInformationResult> ewResult = new EntityWrapper<>();
                ewResult.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                InvoiceInformationResult invoiceInformationResult1 = iInvoiceInformationResultService.selectOne(ewResult);

                if(invoiceInformationResult1 == null){//没有信息插入信息
                    //插入结果表
                    InvoiceInformationResult invoiceInformationResult = new InvoiceInformationResult();
                    invoiceInformationResult.setInvoiceId(invoiceInformationRequest.getInvoiceId());
                    invoiceInformationResult.setInvoiceFlowNumber(resultResponse.getFpqqlsh());
                    invoiceInformationResult.setBillDate(resultResponse.getKprq());
                    invoiceInformationResult.setInvocieCode(resultResponse.getFpdm());
                    invoiceInformationResult.setInvoiceNumber(resultResponse.getFphm());
                    invoiceInformationResult.setPlatformInvoiceUrl(resultResponse.getPdfurl());
                    invoiceInformationResult.setTaxInvoiceUrl(resultResponse.getPdfurl());
                    invoiceInformationResult.setCloudPlatformInvoiceUrl(pdfStorePath + flowNumber + "yxyinovice.pdf");
                    invoiceInformationResult.setCloudPlatformInvoiceImage(pdfStorePath+ flowNumber + "yxyinovice.png");

//                    invoiceInformationResults.add(invoiceInformationResult);//插入列表中
                    iInvoiceInformationResultService.insert(invoiceInformationResult);

                    EntityWrapper<InvoiceDetail> ew1 = new EntityWrapper<>();
                    ew1.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                    InvoiceDetail invoiceDetail = iInvoiceDetailService.selectOne(ew1);


                    String orderIds = invoiceDetail.getOrderId();
                    String[] orderArray  = orderIds.split(",");
                    List<Long> orderArrs = new ArrayList<>();
                    for (String s : orderArray) {
                        orderArrs.add(Long.valueOf(s));
                    }
                    //循环插入关系 修改订单状态
                    for (Long orderid:orderArrs) {
                        OrderInvoice orderInvoice = new OrderInvoice();
                        orderInvoice.setInvoiceId(invoiceInformationRequest.getInvoiceId());
                        orderInvoice.setOrderId(orderid);
                        orderInvoice.setInvoiceAmout(invoiceInformationRequest.getPriceTaxTotal());
                        orderInvoice.setWhetherTogether(1);//是
                        if(orderArrs.size() > 1){
                            orderInvoice.setWhetherTogether(2);//2表示合开
                        }else{
                            orderInvoice.setWhetherTogether(1);//1表示未合开
                        }

                        orderInvoices.add(orderInvoice);//批量插入订单关系列表
//                      iOrderInvoiceService.insert(orderInvoice);
//                        //修改订单
//                        OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
//                        orderInfo.setRequestedBill(true);
//                        orderInfos.add(orderInfo);
//                        iOrderInfoService.updateById(orderInfo);

                        invoiceInformationRequest.setTotalAmout(resultResponse.getHjje());
                        invoiceInformationRequest.setTotalTaxAmout(resultResponse.getHjse());
                        invoiceInformationRequest.setPriceTaxTotal(resultResponse.getJshj());
                        invoiceInformationRequest.setBillStatus(BillStatusEnum.COMPLETE);//开票完成

                        //invoiceInformationRequests1.add(invoiceInformationRequest);
                        this.updateById(invoiceInformationRequest);
                    }
                }
            } catch (IOException e){
                map.put("success", false);
                map.put("message", e.getMessage());
                return map;
            } catch (Exception e) {
                map.put("success", false);
                map.put("message", e.getMessage());
                return map;
            }

        }

//        //批量 插入发票结果
//        iInvoiceInformationResultService.insertBatch(invoiceInformationResults);
//
//        //批量插入订单关系列表
//        iOrderInvoiceService.insertBatch(orderInvoices);

        //批量修改订单开票状态
//        iOrderInfoService.update();
        //iOrderInfoService.update(orderInfos);

        map.put("success", true);
        map.put("message",  "");
        return map;


    }


    private String makeResult(InvoiceInformationRequest invoiceInformationRequest) throws IOException{
        String xml = "";
        String template = IOUtils.toString(inoviceQueryRequest.getInputStream(), "UTF-8");
        Map<String, Object> model = new HashMap<>();
        SalesInformation salesInformation = iSalesInformationService.selectById(invoiceInformationRequest.getOrganizationId());//这块还是写死的
        model.put("salesInformation", salesInformation);
        model.put("invoiceInformationRequest", invoiceInformationRequest);
        xml = YxyStringUtils.velocityRender(template, model);
        return xml;
    }


    @Override
    public Map<String, Object> invoiceHongchong(Long invoice_id) {

        String pdfStorePath = uploadPath + "/invoice/";
        Map<String, Object> map1 = new HashMap<>();
        InvoiceInformationRequest invoiceInformationRequest = this.selectById(invoice_id);
       try {
            String xml = this.makeResult(invoiceInformationRequest);
            BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
            String msg = client.rpc2(baiwangUrl, xml,"");
            if (msg.startsWith("\"")) {
                msg = msg.substring(1);
            }
            if (msg.endsWith("\"")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            msg = msg.replace("\\", "");
            InvoiceResultReturnResponse resultResponse = JaxbXMLUtil.xmlToBean(msg, InvoiceResultReturnResponse.class);

    //
            if(!resultResponse.getRtncode().equals("0000")){
                map1.put("success", false);
                map1.put("message",  resultResponse.getRtnmsg());
                return map1;
            }
            //下载pdf
            String download = resultResponse.getPdfurl();
            if (StringUtils.isNotBlank(baiwangDownload)) {
                download = download.replace("http://36.110.112.203:8095", baiwangDownload);
            }

            URL website = null;
            String flowNumber = UniqueCode.generateUniqueCode(4567);
            File file = new File(pdfStorePath + flowNumber + "yxyinovice.pdf");
            Path target = file.toPath();
            website = new URL(download);
            InputStream in = website.openStream();
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);

            Pdf2image.pdf2img(pdfStorePath + flowNumber + "yxyinovice.pdf", pdfStorePath+ flowNumber + "yxyinovice.png","png");

            //查询关联订单
            EntityWrapper<InvoiceDetail> ew2 = new EntityWrapper<>();
            ew2.eq("invoice_id", invoiceInformationRequest.getOriginalInvoiceId());
            List<InvoiceDetail> invoiceDetails = iInvoiceDetailService.selectList(ew2);

            //把字符串的id处理成列表
            List<Long> orderArrs = new ArrayList<>();

            for (InvoiceDetail tmpInvoiceDetail : invoiceDetails) {
                //orderArrs.add(Long.valueOf(s));
                String[] orderArray = tmpInvoiceDetail.getOrderId().split(",");
                for (String s : orderArray) {
                    orderArrs.add(Long.valueOf(s));
                }
            }

            //把这些order 都改回没开票
            EntityWrapper<OrderInfo> ewOrder = new EntityWrapper<>();
            ewOrder.in("id", orderArrs);
            List<OrderInfo> orderInfos = iOrderInfoService.selectList(ewOrder);

            for (OrderInfo o:orderInfos) {
                o.setRequestedBill(false);
            }

           EntityWrapper<InvoiceInformationResult> ewResult = new EntityWrapper<>();
           ewResult.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
           InvoiceInformationResult invoiceInformationResult1 = iInvoiceInformationResultService.selectOne(ewResult);
           if(invoiceInformationResult1==null) {//没有信息插入信息
               //插入结果表
               InvoiceInformationResult invoiceInformationResult = new InvoiceInformationResult();
               invoiceInformationResult.setInvoiceId(invoiceInformationRequest.getInvoiceId());
               invoiceInformationResult.setInvoiceFlowNumber(resultResponse.getFpqqlsh());
               invoiceInformationResult.setBillDate(resultResponse.getKprq());
               invoiceInformationResult.setInvocieCode(resultResponse.getFpdm());
               invoiceInformationResult.setInvoiceNumber(resultResponse.getFphm());
               invoiceInformationResult.setPlatformInvoiceUrl(resultResponse.getPdfurl());
               invoiceInformationResult.setTaxInvoiceUrl(resultResponse.getPdfurl());
               invoiceInformationResult.setCloudPlatformInvoiceUrl(pdfStorePath + flowNumber + "yxyinovice.pdf");
               invoiceInformationResult.setCloudPlatformInvoiceImage(pdfStorePath+ flowNumber + "yxyinovice.png");
               iInvoiceInformationResultService.insert(invoiceInformationResult);
           }

           invoiceInformationRequest.setCancelImg(pdfStorePath+ flowNumber + "yxyinovice.png");
           this.updateById(invoiceInformationRequest);

            //修改订单的开票状态
            iOrderInfoService.updateAllColumnBatchById(orderInfos);
            map1.put("success", true);
            map1.put("message",  "");
            return map1;
        } catch (IOException e){
            map1.put("success", false);
            map1.put("message", e.getMessage());
            return map1;
        } catch (Exception e) {
            map1.put("success", false);
            map1.put("message", e.getMessage());
            return map1;
        }

    }


    @Override
    public Map<String, Object> invoiceResultOne(Long invoice_id){
        Map<String, Object> map1 = new HashMap<>();
        InvoiceInformationRequest invoiceInformationRequest = this.selectById(invoice_id);
        try {
                String xml = this.makeResult(invoiceInformationRequest);

                BaiwangCouldAPIClient client = new BaiwangCouldAPIClient();
                String msg = client.rpc2(baiwangUrl, xml,"");


                if (msg.startsWith("\"")) {
                    msg = msg.substring(1);
                }
                if (msg.endsWith("\"")) {
                    msg = msg.substring(0, msg.length() - 1);
                }
                msg = msg.replace("\\", "");
                InvoiceResultReturnResponse resultResponse = JaxbXMLUtil.xmlToBean(msg, InvoiceResultReturnResponse.class);
//
                if(!resultResponse.getRtncode().equals("0000")){

                    EntityWrapper<InvoiceDetail> ew2 = new EntityWrapper<>();
                    ew2.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                    InvoiceDetail invoiceDetail = iInvoiceDetailService.selectOne(ew2);

                    String orderIds = invoiceDetail.getOrderId();
                    String[] orderArray  = orderIds.split(",");
                    List<Long> orderArrs = new ArrayList<>();
                    for (String s : orderArray) {
                        orderArrs.add(Long.valueOf(s));
                    }
                    //不成功，就设置成未开票
                    for (Long orderid:orderArrs) {
                        OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
                        orderInfo.setRequestedBill(false);
                        iOrderInfoService.updateById(orderInfo);
                    }

                    map1.put("success", false);
                    map1.put("message",  resultResponse.getRtnmsg());
                    return map1;
                }
                //修改发票请求为开票完成
                invoiceInformationRequest.setBillStatus(BillStatusEnum.COMPLETE);
                this.updateById(invoiceInformationRequest);

//                //下载pdf
                String download = resultResponse.getPdfurl();
                if (StringUtils.isNotBlank(baiwangDownload)) {
                    download = download.replace("http://36.110.112.203:8095", baiwangDownload);
                }

                URL website = null;
                String pdfStorePath = uploadPath + "/invoice/";
                String flowNumber = UniqueCode.generateUniqueCode(4567);
                File file = new File(pdfStorePath + flowNumber + "yxyinovice.pdf");

                if (! file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                Path target = file.toPath();
                website = new URL(download);
                InputStream in = website.openStream();
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);

                Pdf2image.pdf2img(pdfStorePath + flowNumber + "yxyinovice.pdf", pdfStorePath+ flowNumber + "yxyinovice.png","png");

//
//                //讲道理如果，已经插入了结果，就不搞了
                EntityWrapper<InvoiceInformationResult> ewResult = new EntityWrapper<>();
                ewResult.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                InvoiceInformationResult invoiceInformationResult1 = iInvoiceInformationResultService.selectOne(ewResult);
                if(invoiceInformationResult1==null){//没有信息插入信息
                    //插入结果表
                    InvoiceInformationResult invoiceInformationResult = new InvoiceInformationResult();
                    invoiceInformationResult.setInvoiceId(invoiceInformationRequest.getInvoiceId());
                    invoiceInformationResult.setInvoiceFlowNumber(resultResponse.getFpqqlsh());
                    invoiceInformationResult.setBillDate(resultResponse.getKprq());
                    invoiceInformationResult.setInvocieCode(resultResponse.getFpdm());
                    invoiceInformationResult.setInvoiceNumber(resultResponse.getFphm());
                    invoiceInformationResult.setPlatformInvoiceUrl(resultResponse.getPdfurl());
                    invoiceInformationResult.setTaxInvoiceUrl(resultResponse.getPdfurl());
                    invoiceInformationResult.setCloudPlatformInvoiceUrl(pdfStorePath + flowNumber + "yxyinovice.pdf");
                    invoiceInformationResult.setCloudPlatformInvoiceImage(pdfStorePath+ flowNumber + "yxyinovice.png");
                    iInvoiceInformationResultService.insert(invoiceInformationResult);

                    EntityWrapper<InvoiceDetail> ew1 = new EntityWrapper<>();
                    ew1.eq("invoice_id", invoiceInformationRequest.getInvoiceId());
                    InvoiceDetail invoiceDetail = iInvoiceDetailService.selectOne(ew1);
                    String orderIds = invoiceDetail.getOrderId();
                    String[] orderArray  = orderIds.split(",");
                    List<Long> orderArrs = new ArrayList<>();
                    for (String s : orderArray) {
                        orderArrs.add(Long.valueOf(s));
                    }
                    //循环插入关系 修改订单状态
                    for (Long orderid:orderArrs) {
                        OrderInvoice orderInvoice = new OrderInvoice();
                        orderInvoice.setInvoiceId(invoiceInformationRequest.getInvoiceId());
                        orderInvoice.setOrderId(orderid);
                        orderInvoice.setInvoiceAmout(invoiceInformationRequest.getPriceTaxTotal());
                        orderInvoice.setWhetherTogether(1);//是
                        if(orderArrs.size() > 1){
                            orderInvoice.setWhetherTogether(2);//2表示合开
                        }else{
                            orderInvoice.setWhetherTogether(1);//1表示未合开
                        }
                        iOrderInvoiceService.insert(orderInvoice);
//                        //修改订单
//                        OrderInfo orderInfo = iOrderInfoService.selectById(orderid);
//                        orderInfo.setRequestedBill(true);
//                        iOrderInfoService.updateById(orderInfo);

                        invoiceInformationRequest.setTotalAmout(resultResponse.getHjje());
                        invoiceInformationRequest.setTotalTaxAmout(resultResponse.getHjse());
                        invoiceInformationRequest.setPriceTaxTotal(resultResponse.getJshj());
                        invoiceInformationRequest.setBillStatus(BillStatusEnum.COMPLETE);//开票完成
                        this.updateById(invoiceInformationRequest);
                    }
                }
                map1.put("success", true);
                map1.put("message",  "");
                return map1;
            } catch (IOException e){
                map1.put("success", false);
                map1.put("message", e.getMessage());
                return map1;
            } catch (Exception e) {
                map1.put("success", false);
                map1.put("message", e.getMessage());
                return map1;
            }
    }
}
