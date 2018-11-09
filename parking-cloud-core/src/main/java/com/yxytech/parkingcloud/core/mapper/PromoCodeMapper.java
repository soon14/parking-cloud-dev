package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.PromoCode;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-11-22
 */
public interface PromoCodeMapper extends SuperMapper<PromoCode> {
    List<PromoCode> selectBatchIds(List<? extends Serializable> idList);
}