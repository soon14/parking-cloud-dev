package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingCell;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
public interface IParkingCellService extends IService<ParkingCell> {

    public String validate(Long parkingId,String roadBedCode,String code);

    public void createParkingCell(ParkingCell parkingCell);

    public void updateParkingCell(ParkingCell parkingCell);

    void updateBatch(List<ParkingCell> list);

    ParkingCell detail(Long id);

    String updateValidate(ParkingCell parkingCell);

}
