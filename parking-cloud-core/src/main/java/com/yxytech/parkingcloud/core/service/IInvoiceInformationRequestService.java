package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-02
 */
public interface IInvoiceInformationRequestService extends IService<InvoiceInformationRequest> {

    public Map<String, Object> setRequestXml(
                                InvoiceRequestData invoiceRequestData,
                                InvoiceInformationRequestForm invoiceInformationRequestForm,
                                BuyerInformation buyerInformation,
                                SalesInformation salesInformation,
                                Organization organization,
                                Customer customer,
                                InvoiceRequestDataList invoiceRequestDataList
                                );

    public Page<InvoiceInformationRequest> selectRequestPage(Page<InvoiceInformationRequest> page, @Param("ew") Wrapper<InvoiceInformationRequest> wrapper);

    public List<InvoiceInformationRequest> selectRequestList(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper);

    public Map<String, Object> invoiceResult();

    public Map<String, Object> invoiceResultOne(Long invoice_id);

    public Map<String, Object> invoiceHongchong(Long invoice_id);


    public List<InvoiceInformationRequest> selectLastOne(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper);

    String getLastUsedInformation(Long userId);
}
