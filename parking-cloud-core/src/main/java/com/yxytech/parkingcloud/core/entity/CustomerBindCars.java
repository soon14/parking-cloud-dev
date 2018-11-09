package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
@TableName("yxy_customer_bind_cars")
public class CustomerBindCars extends SuperModel<CustomerBindCars> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("user_id")
	private Long userId;
	@TableField("car_id")
	private Long carId;
	@TableField("bind_at")
	private Date bindAt;
	@TableField("unbind_at")
	private Date unbindAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField("is_valid")
	private Boolean isValid;
	@TableField("is_certification")
	private Boolean isCertification;
	@TableField("auth_image_url")
	private String authImageUrl;
	@TableField("auth_time")
	private Date authTime;
	private CarBindStatus status;
	private String remark;
	private transient CustomerCars carInfo;

	public CustomerCars getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CustomerCars carInfo) {
		this.carInfo = carInfo;
	}

	public void setValid(Boolean valid) {
		isValid = valid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public CarBindStatus getStatus() {
		return status;
	}

	public void setStatus(CarBindStatus status) {
		this.status = status;
	}

	public Boolean getCertification() {
		return isCertification;
	}

	public void setCertification(Boolean certification) {
		isCertification = certification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Date getBindAt() {
		return bindAt;
	}

	public void setBindAt(Date bindAt) {
		this.bindAt = bindAt;
	}

	public Date getUnbindAt() {
		return unbindAt;
	}

	public void setUnbindAt(Date unbindAt) {
		this.unbindAt = unbindAt;
	}

	public Boolean getValid() {
		return isValid;
	}

	public String getAuthImageUrl() {
		return authImageUrl;
	}

	public void setAuthImageUrl(String authImageUrl) {
		this.authImageUrl = authImageUrl;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CustomerBindCars{" +
			"id=" + id +
			", userId=" + userId +
			", carId=" + carId +
			", bindAt=" + bindAt +
			", unbindAt=" + unbindAt +
			", isValid=" + isValid +
			", authImageUrl=" + authImageUrl +
			", authTime=" + authTime +
			"}";
	}
}
