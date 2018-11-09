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
 * @since 2017-10-19
 */
@TableName("yxy_etc_info_bak")
public class EtcInfoBak extends SuperModel<EtcInfoBak> {

	@TableField("etc_number")
	private String etcNumber;
	@TableField("etc_net_id")
	private String etcNetId;
	private Long version;


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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "EtcInfoBak{" +
			"etcNumber=" + etcNumber +
			", etcNetId=" + etcNetId +
			", version=" + version +
			"}";
	}
}
