package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@TableName("yxy_fee_rate_multistep")
public class FeeRateMultistep extends SuperModel<FeeRateMultistep> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("fee_rate_id")
    private Long feeRateId;

    @TableField("org_id")
    private Long organizationId;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    private Double price;
    @TableField("pricing_unit")
    private Integer pricingUnit;
    @TableField("start_time")
    private Integer startTime;
    @TableField("end_time")
    private Integer endTime;

    @TableField("step_duration")
    private Integer stepDuration;
    @TableField("sort_number")
    private Integer sortNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Long getFeeRateId() {
        return feeRateId;
    }

    public void setFeeRateId(Long feeRateId) {
        this.feeRateId = feeRateId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPricingUnit() {
        return pricingUnit;
    }

    public void setPricingUnit(Integer pricingUnit) {
        this.pricingUnit = pricingUnit;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(Integer stepDuration) {
        this.stepDuration = stepDuration;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FeeRateMultistep{" +
                "id=" + id +
                ", feeRateId=" + feeRateId +
                ", price=" + price +
                ", pricingUnit=" + pricingUnit +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "}";
    }
}
