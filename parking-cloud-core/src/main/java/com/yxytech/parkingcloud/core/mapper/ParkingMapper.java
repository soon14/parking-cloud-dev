package com.yxytech.parkingcloud.core.mapper;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingInfoForApp;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface ParkingMapper extends SuperMapper<Parking> {

    public Integer createParking(Parking parking);

    List<ParkingInfoForApp> getAllNear(@Param("longitude") Double longitude, @Param("latitude") Double latitude,
                                       @Param("radius") Double radius);

    List<ParkingInfoForApp> getAllNear(Pagination pagination, @Param("longitude") Double longitude,
                                       @Param("latitude") Double latitude, @Param("radius") Double radius);

    public Integer update(Parking parking);

    Parking getParkingInfo(Long id);

    List<Parking> selectByPage(Pagination page, @Param("ew") Wrapper<Parking> wrapper);

    List<Parking> customSelectOrderList(@Param("ew")Wrapper<OrderInfo> wrapper);

    List<Parking> customSelectTransactionList(@Param("ew")Wrapper<OrderTransaction> wrapper);

    Integer updateBatch(List<Parking> list);

    List<Parking> findAll(String parking);
}