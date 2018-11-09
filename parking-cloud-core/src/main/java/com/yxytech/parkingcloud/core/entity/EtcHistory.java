package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@TableName("yxy_etc_history")
public class EtcHistory extends SuperModel<EtcHistory> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("etc_number")
	private String etcNumber;
	@TableField("etc_net_id")
	private String etcNetId;
	@TableField("etc_version")
	private Integer version;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtcNumber() {
		return etcNumber;
	}

	public void setEtcNumber(String etcNumber) {
		this.etcNumber = etcNumber;
	}

	public String getEtcNetId() {
		return etcNetId;
	}

	public void setEtcNetId(String etcNetId) {
		this.etcNetId = etcNetId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EtcHistory{" +
			"id=" + id +
			", etcNumber=" + etcNumber +
			", etcNetId=" + etcNetId +
			", version=" + version +
			"}";
	}
}
