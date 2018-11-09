package com.yxytech.parkingcloud.platform.task;

import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTransactionStatistics {
    public static Map<Long, ParkingStatisticsInfo> statistics(List<Parking> transactionList, Date start, Date end) {
        Map<Long, ParkingStatisticsInfo> parkingStatisticsInfoMap = new HashMap<>();
        Map<TransactionPayWay, String> paymentWay = getPaymentWay();

        for (Parking parking : transactionList) {
            Long index = parking.getId();
            ParkingStatisticsInfo parkingStatisticsInfo = parkingStatisticsInfoMap.get(index);

            if (parkingStatisticsInfo == null) {
                parkingStatisticsInfo = new ParkingStatisticsInfo();

                parkingStatisticsInfo.setParkingId(index);
                parkingStatisticsInfo.setAlipayIncome(0.0);
                parkingStatisticsInfo.setGrossIncome(0.0);
                parkingStatisticsInfo.setWechatIncome(0.0);
                parkingStatisticsInfo.setDiscountIncome(0.0);
                parkingStatisticsInfo.setUnionPayIncome(0.0);
                parkingStatisticsInfo.setOtherIncome(0.0);
            }

            Class obj = parkingStatisticsInfo.getClass();
            List<OrderTransaction> orderTransactions = parking.getOrderTransactions();

            if (orderTransactions == null) {
                continue;
            }

            for (OrderTransaction orderTransaction : parking.getOrderTransactions()) {
                Boolean flag = false;

                for (Map.Entry<TransactionPayWay, String> payWay : paymentWay.entrySet()) {
                    String desc = payWay.getValue();

                    char[] chars = desc.toCharArray();
                    chars[0] = Character.toUpperCase(chars[0]);

                    desc = new String(chars);

                    if (orderTransaction.getPayWay().compareTo(payWay.getKey()) == 0) {
                        String getMethodName = "get" + desc;
                        String setMethodName = "set" + desc;

                        try {
                            Method method = obj.getMethod(getMethodName);
                            Double value = (Double) method.invoke(parkingStatisticsInfo);
                            Method setMethod = obj.getMethod(setMethodName, Double.class);

                            setMethod.invoke(parkingStatisticsInfo, value + orderTransaction.getAmount());
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                        flag = true;
                    }
                }

                if (! flag) {
                    parkingStatisticsInfo.setOtherIncome(orderTransaction.getAmount());
                }
            }

            Double all = parkingStatisticsInfo.getAlipayIncome() + parkingStatisticsInfo.getWechatIncome() +
                    parkingStatisticsInfo.getDiscountIncome() + parkingStatisticsInfo.getUnionPayIncome() +
                    parkingStatisticsInfo.getOtherIncome();

            parkingStatisticsInfo.setGrossIncome(all);

            parkingStatisticsInfoMap.put(index, parkingStatisticsInfo);
        }

        return parkingStatisticsInfoMap;
    }

    /**
     * 获取支付方式
     *
     * @return
     */
    private static Map<TransactionPayWay, String> getPaymentWay() {
        TransactionPayWay[] transactionPayWays = TransactionPayWay.values();
        Map<TransactionPayWay, String> paymentWay = new HashMap<>();
        Map<String, String> paymentWayAlisa = new HashMap<String, String>() {{
            put("微信支付", "wechatIncome");
            put("支付宝支付", "alipayIncome");
            put("银联支付", "unionPayIncome");
            put("优惠券支付", "discountIncome");
        }};

        for (TransactionPayWay transactionPayWay : transactionPayWays) {
            paymentWayAlisa.forEach((k, v) -> {
                if (transactionPayWay.getDesc().equals(k)) {
                    paymentWay.put(transactionPayWay, v);
                }
            });
        }

        return paymentWay;
    }
}
