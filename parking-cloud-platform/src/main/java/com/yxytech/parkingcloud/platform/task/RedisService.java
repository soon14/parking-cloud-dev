package com.yxytech.parkingcloud.platform.task;

import com.alibaba.fastjson.JSON;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 排行榜前十
     * @param s
     * @param keyName
     */
    public void setRankList(List<ParkingStatisticsInfo> s, String keyName) {
        stringRedisTemplate.delete(keyName);

        if (s.size() > 10) {
            // 存储所有前十
            for (int i = 0; i < 10; i++) {
                ParkingStatisticsInfo parkingStatisticsInfo = s.get(i);

                stringRedisTemplate.opsForZSet().add(keyName, JSON.toJSONString(parkingStatisticsInfo),
                        parkingStatisticsInfo.getParkingId());
            }
        } else {
            for (ParkingStatisticsInfo parkingStatisticsInfo : s) {
                stringRedisTemplate.opsForZSet().add(keyName, JSON.toJSONString(parkingStatisticsInfo),
                        parkingStatisticsInfo.getParkingId());
            }
        }
    }

    /**
     * 插入所有的数据
     * @param s
     */
    public void setData(List<ParkingStatisticsInfo> s) {
        for (ParkingStatisticsInfo parkingStatisticsInfo : s) {
            stringRedisTemplate.opsForValue().set("parking:" + parkingStatisticsInfo.getParkingId(),
                    JSON.toJSONString(parkingStatisticsInfo));
        }
    }

    /**
     * 获取排行榜
     * @param parkingIds
     * @param keyName
     * @return
     */
    public List<ParkingStatisticsInfo> getData(List<String> parkingIds, String keyName) {
        if (parkingIds == null) {
            Set<String> result = stringRedisTemplate.opsForZSet().range(keyName, 0, 10);

            List<ParkingStatisticsInfo> parkingStatisticsInfos = new ArrayList<>();

            for (String s : result) {
                parkingStatisticsInfos.add(JSON.parseObject(s, ParkingStatisticsInfo.class));
            }

            return parkingStatisticsInfos;
        }

        // 首先要根据parkingIds获取排序后的结果，然后再去获取这些key

        List<String> result = stringRedisTemplate.opsForValue().multiGet(parkingIds);
        List<ParkingStatisticsInfo> parkingStatisticsInfos = new ArrayList<>();

        for (String ratio : result) {
            ParkingStatisticsInfo parkingStatisticsInfo = JSON.parseObject(ratio, ParkingStatisticsInfo.class);

            parkingStatisticsInfos.add(parkingStatisticsInfo);
        }

        // 排序
        for (int i = 0; i < result.size(); i++) {
            for (int j = i + 1; j < result.size(); j++) {
                ParkingStatisticsInfo iParkingStatisticsInfo = parkingStatisticsInfos.get(i);
                ParkingStatisticsInfo jParkingStatisticsInfo = parkingStatisticsInfos.get(j);

                try {
                    Method method = ParkingStatisticsInfo.class.getMethod(keyName);
                    Double iRatio = (Double) method.invoke(iParkingStatisticsInfo);
                    Double jRatio = (Double) method.invoke(jParkingStatisticsInfo);

                    if (iRatio < jRatio) {
                        ParkingStatisticsInfo tmp = parkingStatisticsInfos.get(i);
                        parkingStatisticsInfos.set(i, parkingStatisticsInfos.get(j));
                        parkingStatisticsInfos.set(j, tmp);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        // 取前十
        if (parkingStatisticsInfos.size() > 10) {
            return parkingStatisticsInfos.subList(0, 10);
        }

        return parkingStatisticsInfos;
    }
}
