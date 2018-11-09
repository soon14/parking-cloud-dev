package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderParkingInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.OrderVoucher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-27
 */
public interface IOrderInfoService extends IService<OrderInfo> {
	OrderInfo getDetail(Long id);

	OrderInfo getDetail(OrderInfo orderInfo);

	OrderParkingInfo validateParams(Long parkingId, OrderParkingInfo orderParkingInfo) throws Exception;

	Boolean update(Long id, OrderInfo orderInfo, OrderParkingInfo orderParkingInfo,
				   List<OrderVoucher> orderVouchers) throws Exception;

	Integer getInParkingCount(Long parkingId, Long cellId);

	Page<OrderInfo> invoiceSelectPage(Page<OrderInfo> page, @Param("ew") Wrapper<OrderInfo> wrapper);

	List<OrderInfo> invoiceSelectList(@Param("ew") Wrapper<OrderInfo> wrapper);

	List<OrderInfo> organzationSelectList(@Param("ew") Wrapper<OrderInfo> wrapper);

	OrderInfo paymentSuccess(OrderInfo orderInfo, OrderTransaction orderTransaction);

	Boolean isWithinTheUncertainty(Date calculateTime);

	/**
	 * 查询单位可开票信息列表，按单位分组统计
	 * @param id
	 * @return
	 */
    List<Map<String, Object>> selectOrganizationInvoiceInfo(Long id);

	List<Map<String, Integer>> selectInUseParkingCells();
}
