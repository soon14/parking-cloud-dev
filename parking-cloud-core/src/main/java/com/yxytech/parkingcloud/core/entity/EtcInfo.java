package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@TableName("yxy_etc_info")
public class EtcInfo extends SuperModel<EtcInfo> {

	@TableField("etc_number")
	private String etcNumber;
	@TableField("etc_net_id")
	private String etcNetId;
	private Integer version;

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
	public String toString() {
		return "EtcInfo{" +
			", etcNumber=" + etcNumber +
			", etcNetId=" + etcNetId +
			", version=" + version +
			"}";
	}
}
