package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.EtcHistory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
public interface IEtcHistoryService extends IService<EtcHistory> {
	boolean upsert(EtcHistory etcHistory);

	Page<EtcHistory> getAllByVersion(Page<EtcHistory> page, Integer version);

	String getAllVersion(String etcNumber);
}
