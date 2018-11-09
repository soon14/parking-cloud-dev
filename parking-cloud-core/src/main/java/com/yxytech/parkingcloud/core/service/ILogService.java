package com.yxytech.parkingcloud.core.service;

import com.yxytech.parkingcloud.core.entity.Log;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2018-01-22
 */
public interface ILogService extends IService<Log>,YXYIService<Log> {

    List<String> getUserIds(String name);

}
