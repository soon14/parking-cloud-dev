package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeSchemaParking;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-27
 */
public interface IFeeSchemaParkingService extends IService<FeeSchemaParking> {

    String bindSchema(Long parkingId,List<Long> schemaIds) throws Exception;
}
