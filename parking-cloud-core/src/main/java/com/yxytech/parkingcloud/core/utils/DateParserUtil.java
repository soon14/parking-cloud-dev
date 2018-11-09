package com.yxytech.parkingcloud.core.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class  DateParserUtil {
    /**
     * 开始结束查询日期解析
     * @param date
     * @param type
     * @return
     */
    public static Date parseDate(String date, Boolean type) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Integer hour = type ? 0 : 23;
        Integer minute = type ? 0 : 59;

        try {
            calendar.setTime(dateFormat.parse(date));

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, minute);
        } catch (ParseException e) {
            throw new Exception("解析失败!");
        }

        return calendar.getTime();
    }

    /**
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseDateToDay(String date) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            throw new Exception("解析失败!");
        }

        return calendar.getTime();
    }

    /**
     * 格式化日
     * @param date
     * @param type
     * @return
     * @throws Exception
     */
    public static Date parseDateToMonth(String date, Boolean type) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        calendar.setTime(dateFormat.parse(date));
        try {
            if(type) {
                calendar.set(Calendar.DAY_OF_MONTH,1);
            }else{
                if(calendar.get(Calendar.MONTH) + 1 > 12){
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
                    calendar.set(calendar.get(Calendar.MONTH),1);
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                }else{
                    calendar.set(calendar.get(Calendar.MONTH),calendar.get(Calendar.MONTH) + 1);
                    calendar.set(Calendar.DAY_OF_MONTH,1);
                }
            }
        } catch (Exception e) {
            throw new Exception("解析失败!");
        }


        return calendar.getTime();
    }

    /**
     * 格式化年
     * @param date
     * @param type
     * @return
     * @throws Exception
     */
    public static Date parseDateYear(String date, Boolean type) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        calendar.setTime(dateFormat.parse(date));

        if(type) {
            calendar.set(calendar.get(Calendar.MONTH), 1);
            calendar.set(calendar.get(Calendar.DAY_OF_MONTH), 1);
        }else{
            calendar.set(calendar.get(Calendar.MONTH), 12);
            calendar.set(calendar.get(Calendar.DAY_OF_MONTH), 31);
        }
        return calendar.getTime();
    }


    /**
     * 解析时间
     * @param date
     * @return
     * @throws Exception
     */
    public static Date formatDate(String date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            throw new Exception("解析失败!");
        }

        return calendar.getTime();
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return dateFormat.format(date);
    }
}
