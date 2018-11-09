package com.yxytech.parkingcloud.app.task;

import com.yxytech.parkingcloud.core.service.IInvoiceInformationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InoviceTask {

    @Autowired
    private IInvoiceInformationRequestService iInvoiceInformationRequestService;

    @Scheduled(fixedRate = 300000)
    public void invoiceResult(){//300000
        System.out.println("开始查询正在开票的发票");
        iInvoiceInformationRequestService.invoiceResult();
        System.out.println("操作结束");
    }

    @Async
    public void doTaskOne(Long invoice_id) throws InterruptedException {
        Integer times = 3;
        while (times > 0) {
            System.out.println("查询发票开始");
            Map<String, Object> map = iInvoiceInformationRequestService.invoiceResultOne(invoice_id);
            if((Boolean) map.get("success")){
                times = 0;
            }
            Thread.sleep(5000);
            times--;
            System.out.println("查询发票结束");
        }

    }

    @Async
    public void doHongChong(Long invoice_id) throws InterruptedException {
        Integer times = 3;
        while (times > 0) {
            System.out.println("查询发票开始");
            Map<String, Object> map = iInvoiceInformationRequestService.invoiceHongchong(invoice_id);
            if((Boolean) map.get("success")){
                times = 0;
            }
            Thread.sleep(5000);
            times--;
            System.out.println("查询发票结束");
        }
    }



}
