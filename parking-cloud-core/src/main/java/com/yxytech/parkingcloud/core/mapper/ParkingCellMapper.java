package com.yxytech.parkingcloud.core.mapper;


import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
public interface ParkingCellMapper extends SuperMapper<ParkingCell> {

    public Integer createParkingCell(ParkingCell parkingCell);

    public Integer update(ParkingCell parkingCell);

    Integer updateBatch(List<ParkingCell> list);
}