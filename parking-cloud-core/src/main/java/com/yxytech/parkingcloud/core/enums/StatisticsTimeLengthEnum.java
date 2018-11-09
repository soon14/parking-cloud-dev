package com.yxytech.parkingcloud.core.enums;

public enum StatisticsTimeLengthEnum {
    lt15("Lt15", new Integer[]{0, 15}, "小于15分钟"),
    gt15lt30("Gt15lt30", new Integer[]{15, 30}, "大于等于15分钟小于30分钟"),
    gt30lt45("Gt30lt45", new Integer[]{30, 45}, "大于等于30分钟小于45分钟"),
    gt45lt60("Gt45lt60", new Integer[]{30, 45}, "大于等于45分钟小于1小时"),
    gt60lt90("Gt60lt90", new Integer[]{30, 45}, "大于等于1小时小于1.5小时"),
    gt90lt120("Gt90lt120", new Integer[]{30, 45}, "大于等于1.5小时小于2小时"),
    gt120lt150("Gt120lt150", new Integer[]{30, 45}, "大于等于2小时小于2.5小时"),
    gt150lt180("Gt150lt180", new Integer[]{30, 45}, "大于等于2.5小时小于3小时"),
    gt180lt240("Gt180lt240", new Integer[]{30, 45}, "大于等于3小时小于4小时"),
    gt240("Gt240", new Integer[]{30, 45}, "大于等于4小时");

    private String key;
    private Integer[] value;
    private String desc;

    StatisticsTimeLengthEnum(String key, Integer[] value, String desc) {
        this.key = key;
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getKey() {
        return key;
    }

    public Integer[] getValue() {
        return value;
    }
}
