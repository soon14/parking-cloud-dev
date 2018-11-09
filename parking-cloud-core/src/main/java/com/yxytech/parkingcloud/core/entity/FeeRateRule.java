package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleFeeTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleTypeEnum;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@TableName("yxy_fee_rate_rule")
public class FeeRateRule extends SuperModel<FeeRateRule> {

    private static final long serialVersionUID = 1L;

    private Long id;
    @TableField("vehicle_type")
    private CarTypeEnum vehicleType;

    @TableField("org_id")
    private Long organizationId;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @TableField("on_festival")
    private Boolean onFestival;
    @TableField("on_mon")
    private Boolean onMon;
    @TableField("on_tue")
    private Boolean onTue;
    @TableField("on_wed")
    private Boolean onWed;
    @TableField("on_thu")
    private Boolean onThu;
    @TableField("on_fri")
    private Boolean onFri;
    @TableField("on_sat")
    private Boolean onSat;
    @TableField("on_sun")
    private Boolean onSun;
    @TableField("free_in_duration")
    private Integer freeInDuration;
    @TableField("free_out_duration")
    private Integer freeOutDuration;
    @TableField("is_valid")
    private Boolean isValid;
    @TableField("created_at")
    private Date createdAt;
    @TableField("updated_at")
    private Date updatedAt;
    @TableField("created_by")
    private Long createdBy;
    @TableField("updated_by")
    private Long updatedBy;
    private String note;
    @TableField("desc")
    private String desc;


    private FeeRateEnum type;

    @TableField("is_cycled")
    private Boolean isCycled;

    @TableField("cycle_type")
    private CycleTypeEnum cycleType;

    @TableField("cycle_start")
    private String cycleStart;

    @TableField("cycle_fee_type")
    private CycleFeeTypeEnum cycleFeeType;

    @TableField("max_fee")
    private Double maxFee;

//    @TableField("fee_start_time")
//    private Long feeStartTime;
//
//    @TableField("fee_end_time")
//    private Long feeEndTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarTypeEnum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(CarTypeEnum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getOnFestival() {
        return onFestival;
    }

    public void setOnFestival(Boolean onFestival) {
        this.onFestival = onFestival;
    }

    public Boolean getOnMon() {
        return onMon;
    }

    public void setOnMon(Boolean onMon) {
        this.onMon = onMon;
    }

    public Boolean getOnTue() {
        return onTue;
    }

    public void setOnTue(Boolean onTue) {
        this.onTue = onTue;
    }

    public Boolean getOnWed() {
        return onWed;
    }

    public void setOnWed(Boolean onWed) {
        this.onWed = onWed;
    }

    public Boolean getOnThu() {
        return onThu;
    }

    public void setOnThu(Boolean onThu) {
        this.onThu = onThu;
    }

    public Boolean getOnFri() {
        return onFri;
    }

    public void setOnFri(Boolean onFri) {
        this.onFri = onFri;
    }

    public Boolean getOnSat() {
        return onSat;
    }

    public void setOnSat(Boolean onSat) {
        this.onSat = onSat;
    }

    public Boolean getOnSun() {
        return onSun;
    }

    public void setOnSun(Boolean onSun) {
        this.onSun = onSun;
    }

    public Integer getFreeInDuration() {
        return freeInDuration;
    }

    public void setFreeInDuration(Integer freeInDuration) {
        this.freeInDuration = freeInDuration;
    }

    public Integer getFreeOutDuration() {
        return freeOutDuration;
    }

    public void setFreeOutDuration(Integer freeOutDuration) {
        this.freeOutDuration = freeOutDuration;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean valid) {
        isValid = valid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public FeeRateEnum getType() {
        return type;
    }

    public void setType(FeeRateEnum type) {
        this.type = type;
    }

    public Boolean getIsCycled() {
        return isCycled;
    }

    public void setIsCycled(Boolean cycled) {
        isCycled = cycled;
    }

    public CycleTypeEnum getCycleType() {
        return cycleType;
    }

    public void setCycleType(CycleTypeEnum cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycleStart() {
        return cycleStart;
    }

    public void setCycleStart(String cycleStart) {
        this.cycleStart = cycleStart;
    }


    public CycleFeeTypeEnum getCycleFeeType() {
        return cycleFeeType;
    }

    public void setCycleFeeType(CycleFeeTypeEnum cycleFeeType) {
        this.cycleFeeType = cycleFeeType;
    }

    public Double getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(Double maxFee) {
        this.maxFee = maxFee;
    }

//    public Long getFeeStartTime() {
//        return feeStartTime;
//    }
//
//    public void setFeeStartTime(Long feeStartTime) {
//        this.feeStartTime = feeStartTime;
//    }
//
//    public Long getFeeEndTime() {
//        return feeEndTime;
//    }
//
//    public void setFeeEndTime(Long feeEndTime) {
//        this.feeEndTime = feeEndTime;
//    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FeeRateRule{" +
                "id=" + id +
                ", vehicleType=" + vehicleType +
                ", onFestival=" + onFestival +
                ", onMon=" + onMon +
                ", onTue=" + onTue +
                ", onWed=" + onWed +
                ", onThu=" + onThu +
                ", onFri=" + onFri +
                ", onSat=" + onSat +
                ", onSun=" + onSun +
                ", freeInDuration=" + freeInDuration +
                ", freeOutDuration=" + freeOutDuration +
                ", isValid=" + isValid +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", note=" + note +
                ", type=" + type +
                "}";
    }
}
