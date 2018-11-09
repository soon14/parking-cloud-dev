package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.CarTypeEnum;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BillForm implements Serializable{


    Long leaveTime;
    Long enterTime;
    Long payTime;
    CarTypeEnum vehicle;
    Long parkingId;
    Integer freeTimes;
    Map<String,Date> freeValidTime;
    List<Map<String,String>> freeTimeList;

    List<Map<String,Object>> freeTimesList;

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.ROOT);
    Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA);


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    {
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        sdf.setCalendar(calendar);
        sdf2.setCalendar(calendar2);
    }


    public Long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) throws ParseException {
        this.leaveTime = sdf.parse(leaveTime).getTime();
    }

    public Long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) throws ParseException {
        this.enterTime =sdf.parse(enterTime).getTime();
        System.out.println(this.enterTime);
    }


    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) throws ParseException {
        this.payTime = sdf.parse(payTime).getTime();
    }

    public CarTypeEnum getVehicle() {
        return vehicle;
    }

    public void setVehicle(CarTypeEnum vehicle) {
        this.vehicle = vehicle;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getFreeTimes() {
        return freeTimes;
    }

    public void setFreeTimes(Integer freeTimes) {
        this.freeTimes = freeTimes;
    }

    public Map<String, Date> getFreeValidTime() {
        return freeValidTime;
    }

    public void setFreeValidTime(Map<String, Date> freeValidTime) {
        this.freeValidTime = freeValidTime;
    }

    public List<Map<String, String>> getFreeTimeList() {
        return freeTimeList;
    }

    public void setFreeTimeList(List<Map<String, String>> freeTimeList) {
        this.freeTimeList = freeTimeList;
    }

    public List<Map<String, Object>> getFreeTimesList() {
        return freeTimesList;
    }

    public void setFreeTimesList(List<Map<String, Object>> freeTimesList) {
        this.freeTimesList = freeTimesList;
    }
}
