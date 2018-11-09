package com.yxytech.parkingcloud.core.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2018-01-22
 */
@TableName("yxy_log")
public class Log extends SuperModel<Log> {

    private static final long serialVersionUID = 1L;

	private Date time;
	@TableField("remote_ip")
	private String remoteIp;
	@TableField("user_id")
	private String userId;
	private String uri;
	@TableField("ret_code")
	private String retCode;
	@TableField("query_string")
	private String queryString;
	private String params;
	private String ret;
	@TableField("executed_time")
	private String executedTime;
	private String level;
	private String message;


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getExecutedTime() {
		return executedTime;
	}

	public void setExecutedTime(String executedTime) {
		this.executedTime = executedTime;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Log{" +
			"time=" + time +
			", remoteIp=" + remoteIp +
			", userId=" + userId +
			", uri=" + uri +
			", retCode=" + retCode +
			", queryString=" + queryString +
			", params=" + params +
			", ret=" + ret +
			", executedTime=" + executedTime +
			", level=" + level +
			", message=" + message +
			"}";
	}
}
