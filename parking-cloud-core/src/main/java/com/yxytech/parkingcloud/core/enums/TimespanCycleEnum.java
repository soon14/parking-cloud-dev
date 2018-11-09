package com.yxytech.parkingcloud.core.enums;

public enum TimespanCycleEnum {
    DAY(0, 1), WEEK(1, 7);

    public Integer getInterval() {
        return interval;
    }

    public Integer getType() {
        return type;
    }

    TimespanCycleEnum(Integer type, Integer interval) {
        this.type = type;
        this.interval = interval;
    }

    private Integer type;
    private Integer interval;

    public boolean compareTo(Integer cycle) {
        return this.type == cycle;
    }
}
