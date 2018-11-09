package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.BuyerInformation;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-03
 */
public interface BuyerInformationMapper extends SuperMapper<BuyerInformation> {
    List<Object> selectPage(Pagination page, @Param("ew") Wrapper<Customer> wrapper);
    List<Object> selectList(Pagination page, @Param("ew") Wrapper<Customer> wrapper);
}