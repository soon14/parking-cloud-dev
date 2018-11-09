package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.enums.AdvertisementTypeEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-11-03
 */
@TableName("yxy_advertisement")
public class Advertisement extends SuperModel<Advertisement> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String cover;
	private String link;
	private AdvertisementTypeEnum position;
	private String remarks;
	private Boolean status;
	private Integer sort;
	private String content;
	@TableField("created_at")
	private Date createdAt;
	@TableField("update_at")
	private Date updateAt;
	@TableField("created_by")
	private Long createdBy;
	@TableField("update_by")
	private Long updateBy;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public AdvertisementTypeEnum getPosition() {
		return position;
	}

	public void setPosition(AdvertisementTypeEnum position) {
		this.position = position;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Advertisement{" +
			"id=" + id +
			", title=" + title +
			", cover=" + cover +
			", link=" + link +
			", position=" + position +
			", remarks=" + remarks +
			", status=" + status +
			", sort=" + sort +
			", content=" + content +
			", createdAt=" + createdAt +
			", updateAt=" + updateAt +
			", createdBy=" + createdBy +
			", updateBy=" + updateBy +
			"}";
	}
}
