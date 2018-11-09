package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-24
 */
@TableName("yxy_user_push_token")
public class UserPushToken extends SuperModel<UserPushToken> {

    private static final long serialVersionUID = 1L;

	@TableField("push_token")
	private String pushToken;
	@TableField("user_id")
	private Long userId;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
    /**
     * 1启动 2注销
     */
	@TableField("in_use")
	private InUseEnum inUse;
	@TableField("used_time")
	private Date usedTime;
	@TableField("unused_time")
	private Date unusedTime;
	private Long id;


	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public InUseEnum getInUse() {
		return inUse;
	}

	public void setInUse(InUseEnum inUse) {
		this.inUse = inUse;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Date getUnusedTime() {
		return unusedTime;
	}

	public void setUnusedTime(Date unusedTime) {
		this.unusedTime = unusedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "UserPushToken{" +
			"pushToken=" + pushToken +
			", userId=" + userId +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", inUse=" + inUse +
			", usedTime=" + usedTime +
			", unusedTime=" + unusedTime +
			", id=" + id +
			"}";
	}
}
