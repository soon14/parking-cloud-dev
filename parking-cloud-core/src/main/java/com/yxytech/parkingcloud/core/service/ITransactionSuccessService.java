package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.TransactionSuccess;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-06
 */
public interface ITransactionSuccessService extends IService<TransactionSuccess> {
	TransactionSuccess setInfo(OrderTransaction orderTransaction, Double totalFeii);
}
