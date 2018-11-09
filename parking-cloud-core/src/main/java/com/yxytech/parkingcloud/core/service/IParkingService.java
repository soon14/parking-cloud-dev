package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.utils.YXYIService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface IParkingService extends IService<Parking> ,YXYIService<Parking>{

    public String validate(String fullName);

    public void createParking(Parking parking);

    List<ParkingInfoForApp> getNearParking(Double longitude, Double latitude, Double radius);


    public void updateParking(Parking parking);

    Parking getParkingInfo(Long id);

    public Page<Parking> selectByPage(Page<Parking> page, @Param("ew") Wrapper<Parking> wrapper);

    List<Parking> findByArea(Long areaId);

    List<Parking> customSelectOrderList(Wrapper<OrderInfo> wrapper);

    List<Parking> customSelectTransactionList(Wrapper<OrderTransaction> wrapper);

    String validateParkingIds(List<Long> parkingIds);

    String examine(Parking parking);

    Double getRadius(Double longitude, Double latitude, Double leftTopLongitude, Double leftTopLatitude);

    Page<ParkingInfoForApp> getNearParkingPagination(Page<ParkingInfoForApp> page, Double longitude,
                                                     Double latitude, Double radius);

    void approve(Parking parking, ParkingOwner parkingOwner,ParkingOperator parkingOperator);

    void updateBatch(List<Parking> list);

    Parking detail(Long id);

    String getAreas(Long streetAreaId);

    String updateValidate(Parking parking);

    List<Parking> findAll(String parking);

    void increaseParkingCellUsedCount(Long parkingId);

    void decreaseParkingCellUsedCount(Long parkingId);

    void syncParkingCellUsedCount(List<Map<String, Integer>> cellUsedMap);
}
