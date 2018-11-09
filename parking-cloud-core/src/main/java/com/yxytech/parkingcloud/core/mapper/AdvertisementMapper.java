package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.Advertisement;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-11-03
 */
public interface AdvertisementMapper extends SuperMapper<Advertisement> {
    Integer getCount();
}