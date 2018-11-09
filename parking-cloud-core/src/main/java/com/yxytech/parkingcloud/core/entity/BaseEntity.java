package com.yxytech.parkingcloud.core.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: BaseEntity
 * @Description: 分页通用
 * @author haiidragon 348133476@qq.com
 * @date 2017年11月02日 下午2:56:35
 *
 */
public class BaseEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = -5037079496216066045L;

	/**
	 * 第几页
	 */
	private Integer page = 0;
	private String start = "0";

	/**
	 * 每页数量
	 */
	private Integer rows = 20;
	private String limit = "20";
	
	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		if(page > 0) {
			this.page = (page - 1) * rows;
		}
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		if(start != null && start.matches("\\d{1,}")) {
			this.page = Integer.valueOf(start);
		}
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
		if(null!= limit && limit.matches("\\d{1,}")) {
			this.rows = Integer.valueOf(limit);
		}
	}
	
}
