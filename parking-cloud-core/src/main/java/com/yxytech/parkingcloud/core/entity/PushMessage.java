package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.PushMessageStatusEnum;
import com.yxytech.parkingcloud.core.enums.PushMessageTypeEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-12-06
 */
@TableName("yxy_push_message")
public class PushMessage extends SuperModel<PushMessage> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private PushMessageTypeEnum type;
	private String title;
	private String content;
	private PushMessageStatusEnum status;
	@TableField("customer_id")
	private Long customerId;
	@TableField("umeng_push_token")
	private String umengPushToken;
	@TableField("umeng_sent_at")
	private Date umengSentAt;

	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public PushMessageTypeEnum getType() {
		return type;
	}

	public void setType(PushMessageTypeEnum type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Transient
	public PushMessageStatusEnum getStatus() {
		return status;
	}

	@JsonProperty("status")
	public int getStatusValue() {
		return (int)status.getValue();
	}

	public void setStatus(PushMessageStatusEnum status) {
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getUmengSentAt() {
		return umengSentAt;
	}

	public void setUmengSentAt(Date umengSentAt) {
		this.umengSentAt = umengSentAt;
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

	public String getUmengPushToken() {
		return umengPushToken;
	}

	public void setUmengPushToken(String umengPushToken) {
		this.umengPushToken = umengPushToken;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PushMessage{" +
			"id=" + id +
			", type=" + type +
			", title=" + title +
			", content=" + content +
			", status=" + status +
			", customerId=" + customerId +
			", umengSentAt=" + umengSentAt +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}

	public PushMessage() {
	}

	public PushMessage(String title, String content, Long customerId, String umengPushToken) {
		this.title = title;
		this.content = content;
		this.customerId = customerId;
		this.umengPushToken = umengPushToken;
		this.type = PushMessageTypeEnum.ANDROID_NOTIFICATION;
		this.status = PushMessageStatusEnum.UNREAD;
	}
}
