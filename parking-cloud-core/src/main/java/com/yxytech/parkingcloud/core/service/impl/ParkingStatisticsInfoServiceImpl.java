package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import com.yxytech.parkingcloud.core.mapper.ParkingStatisticsInfoMapper;
import com.yxytech.parkingcloud.core.service.IParkingStatisticsInfoService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwd
 * @since 2017-11-13
 */
@Service
public class ParkingStatisticsInfoServiceImpl extends ServiceImpl<ParkingStatisticsInfoMapper, ParkingStatisticsInfo> implements IParkingStatisticsInfoService {

    @Override
    public Map<String, Date> dateSearch(Integer cycle, String time, String startTime, String endTime) throws Exception {
        Date timeDate = null;
        Date startTimeDate = null;
        Date endTimeDate = null;

        // 实时查询
        if (cycle == 1) {

                timeDate = DateParserUtil.parseDateToDay(time);

        }
        // 根据日期范围查询
        if(cycle == 2) {
                startTimeDate = DateParserUtil.parseDateToDay(startTime);
                endTimeDate = DateParserUtil.parseDateToDay(endTime);
        }
        // 根据指定月份查询
        if(cycle == 3) {
            startTimeDate = DateParserUtil.parseDateToMonth(startTime, true);
            endTimeDate = DateParserUtil.parseDateToMonth(endTime, false);
        }
        // 根据指定年份查询
        if(cycle == 4) {
            startTimeDate = DateParserUtil.parseDateYear(startTime, true);
            endTimeDate = DateParserUtil.parseDateYear(endTime, false);
        }

        Map<String, Date> map = new HashMap<>();
        map.put("timeDate", timeDate);
        map.put("startTimeDate", startTimeDate);
        map.put("endTimeDate", endTimeDate);
        return map;
    }

    @Override
    public Object sort(List<ParkingStatisticsInfo> parkingStatisticsInfos, Integer cycle,Integer type, Date startTime, Date endTime, Integer top)
            throws NotFoundException {
        //实时排序
        Map<String, List<Object>> map = new HashMap<>();

        if(!(parkingStatisticsInfos.size()>0)){
          throw new NotFoundException("没有符合条件的数据");
        }

        Date start = parkingStatisticsInfos.get(0).getDatetime();

        for (ParkingStatisticsInfo parkingStatisticsInfo : parkingStatisticsInfos) {

            if(start.after(parkingStatisticsInfo.getDatetime())){
                start = parkingStatisticsInfo.getDatetime();
            }

            List<Object> objectMap = map.get(parkingStatisticsInfo.getParkingName());

            if (objectMap == null) {
                objectMap = new ArrayList<>();
            }

            switch(type){
                case 1://cellutilization
                    objectMap.add(parkingStatisticsInfo);
                    break;
                case 2://reverserate
                    objectMap.add(parkingStatisticsInfo);
                    break;
                case 3://getCellUsage
                    objectMap.add(parkingStatisticsInfo);
                    break;
            }
            map.put(parkingStatisticsInfo.getParkingName(), objectMap);
        }
        Map<String, Object> result = new HashMap<>();
        List<Object> x = new ArrayList<>();
        List<Object> list = new ArrayList<>();
//
//
        for (String key : map.keySet()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("parking", key);
            tmp.put("all", map.get(key));
            list.add(tmp);
        }

        if(cycle == 1){
            // 实时排序
            x = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        }
        if(cycle == 2){
            // 日期范围排序
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);//start  startTime 是传进来的
            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH ) + 1;
            Integer startDate = calendar.get(Calendar.DATE);
            calendar.setTime(endTime);
            Integer endDate = calendar.get(Calendar.DATE);
            for(int i = startDate;i <= endDate;i++){
                x.add(year + "."+ month + "." + i );
            }
        }
        if(cycle == 3){
            // 按月查询
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            Integer year = calendar.get(Calendar.YEAR);
            Integer startMonth = calendar.get(Calendar.MONTH ) + 1;
            calendar.setTime(endTime);
            Integer endMonth = calendar.get(Calendar.MONTH ) + 1;
            for(int i = startMonth;i < endMonth;i++){
                x.add(year + "."+ i);
            }
        }
        result.put("x", x);
        list = list.size()>top ? list.subList(0, top) : list;
        result.put("datas", list);
        return result;
    }

    @Override
    public Object sortIncome(List<ParkingStatisticsInfo> parkingStatisticsInfos, Integer cycle, Date startTime, Date endTime, Integer top) throws NotFoundException {
        //实时排序
        Map<String, Map<String,List<Object>>> map = new HashMap<>();

        if(!(parkingStatisticsInfos.size()>0)){
            throw new NotFoundException("没有符合条件的数据");
        }

        Date start = parkingStatisticsInfos.get(0).getDatetime();





        for (ParkingStatisticsInfo parkingStatisticsInfo : parkingStatisticsInfos) {

            Map<String,List<Object>> objectMap = map.get(parkingStatisticsInfo.getParkingName());
            if (objectMap == null) {
                objectMap = new HashMap<>();
            }
            //map 套6个map 每个map包含这段时间的list


            if(objectMap.get("grossIncome") == null){
                objectMap.put("grossIncome",new ArrayList<Object>());
            }
            if(objectMap.get("wechatIncome") == null){
                objectMap.put("wechatIncome",new ArrayList<Object>());
            }
            if(objectMap.get("alipayIncome") == null){
                objectMap.put("alipayIncome",new ArrayList<Object>());
            }
            if(objectMap.get("unionPayIncome") == null){
                objectMap.put("unionPayIncome",new ArrayList<Object>());
            }
            if(objectMap.get("discountIncome") == null){
                objectMap.put("discountIncome",new ArrayList<Object>());
            }
            if(objectMap.get("otherIncome") == null){
                objectMap.put("otherIncome",new ArrayList<Object>());
            }

            List<Object> grossIncomeList = objectMap.get("grossIncome");
            List<Object> wechatIncomeList = objectMap.get("wechatIncome");
            List<Object> alipayIncomeList = objectMap.get("alipayIncome");
            List<Object> unionPayIncomeList = objectMap.get("unionPayIncome");
            List<Object> discountIncomeList = objectMap.get("discountIncome");
            List<Object> otherIncomeList = objectMap.get("otherIncome");


            grossIncomeList.add(parkingStatisticsInfo.getGrossIncome());
            wechatIncomeList.add(parkingStatisticsInfo.getWechatIncome());
            alipayIncomeList.add(parkingStatisticsInfo.getAlipayIncome());
            unionPayIncomeList.add(parkingStatisticsInfo.getUnionPayIncome());
            discountIncomeList.add(parkingStatisticsInfo.getDiscountIncome());
            otherIncomeList.add(parkingStatisticsInfo.getOtherIncome());

            //分别保存这6个map
            objectMap.put("grossIncome",grossIncomeList);
            objectMap.put("wechatIncome",wechatIncomeList);
            objectMap.put("alipayIncome",alipayIncomeList);
            objectMap.put("unionPayIncome",unionPayIncomeList);
            objectMap.put("discountIncome",discountIncomeList);
            objectMap.put("otherIncome",otherIncomeList);

            map.put(parkingStatisticsInfo.getParkingName(), objectMap);
        }

        Map<String, Object> result = new HashMap<>();
        List<Object> x = new ArrayList<>();

        if(cycle == 1){
            // 实时排序
            x = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        }
        if(cycle == 2){
            // 日期范围排序
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH ) + 1 ;
            Integer startDate = calendar.get(Calendar.DATE);
            calendar.setTime(endTime);
            Integer endDate = calendar.get(Calendar.DATE);
            for(int i = startDate;i <= endDate;i++){
                x.add(year + "."+ month + "." + i);
            }
        }
        if(cycle == 3){
            // 按月查询
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            Integer year = calendar.get(Calendar.YEAR);
            Integer startMonth = calendar.get(Calendar.MONTH ) + 1;
            calendar.setTime(endTime);
            Integer endMonth = calendar.get(Calendar.MONTH) + 1;
            for(int i = startMonth;i < endMonth;i++){
                x.add(year + "." + i );
            }
        }





        List<Object> list = new ArrayList<>();
        for (String key : map.keySet()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("parking", key);
            tmp.put("grossIncome", map.get(key).get("grossIncome"));
            tmp.put("wechatIncome", map.get(key).get("wechatIncome"));
            tmp.put("alipayIncome", map.get(key).get("alipayIncome"));
            tmp.put("unionPayIncome", map.get(key).get("unionPayIncome"));
            tmp.put("discountIncome", map.get(key).get("discountIncome"));
            tmp.put("otherIncome", map.get(key).get("otherIncome"));
            list.add(tmp);
        }
        result.put("x", x);

        list = list.size()>top ? list.subList(0, top) : list;
        result.put("datas", list);
        return result;
    }


    @Override
    public List<ParkingStatisticsInfo> selectByMonth(Wrapper<ParkingStatisticsInfo> wrapper) {

        return baseMapper.selectByMonth(wrapper);

    }

    @Override
    public List<ParkingStatisticsInfo> selectHistoryRank() {
        return baseMapper.selectHistoryRank();
    }

    @Override
    public List<ParkingStatisticsInfo> selectPassedMonth(Integer length, Wrapper<ParkingStatisticsInfo> wrapper) {
        return baseMapper.selectPassedMonthInfo(length, wrapper);
    }

    @Override
    public List<ParkingStatisticsInfo> selectTopList(Date dateTime){
        return baseMapper.selectTopList(dateTime);
    }

    @Override
    public List<ParkingStatisticsInfo> selectNewTopList(Date dateTime){
        return baseMapper.selectNewTopList(dateTime);
    }


    @Override
    public List<ParkingStatisticsInfo> incomeTop() {
        List<ParkingStatisticsInfo> parkingStatisticsInfos = baseMapper.incomeTop();

        return parkingStatisticsInfos;
    }

}
