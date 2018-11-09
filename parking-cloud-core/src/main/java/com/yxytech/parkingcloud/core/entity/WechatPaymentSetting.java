package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-11-15
 */
@TableName("yxy_wechat_payment_setting")
public class WechatPaymentSetting extends SuperModel<WechatPaymentSetting> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("app_id")
	private String appId;
	@TableField("mch_id")
	private String mchId;
	@TableField("api_key")
	private String apiKey;
	@TableField("certification_file")
	private String certificationPath;
	@TableField("password")
	private String password;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCertificationPath() {
		return certificationPath;
	}

	public void setCertificationPath(String certificationPath) {
		this.certificationPath = certificationPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WechatPaymentSetting{" +
			"id=" + id +
			", parkingId=" + parkingId +
			", appId=" + appId +
			", mchId=" + mchId +
			", apiKey=" + apiKey +
			", createdAt=" + createdAt +
			", createdBy=" + createdBy +
			", updatedAt=" + updatedAt +
			", updatedBy=" + updatedBy +
			"}";
	}
}
