package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@TableName("yxy_etc_version")
public class EtcVersion extends SuperModel<EtcVersion> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("received_at")
	private Date receivedAt;
	@TableField("updated_at")
	private Date updatedAt;
	@TableField("is_valid")
	private Boolean isValid;
	@TableField("started_at")
	private Date startedAt;
	private Integer version;
	@TableField("table_name")
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EtcVersion{" +
			"id=" + id +
			", receivedAt=" + receivedAt +
			", updatedAt=" + updatedAt +
			", isValid=" + isValid +
			", startedAt=" + startedAt +
			"}";
	}
}
