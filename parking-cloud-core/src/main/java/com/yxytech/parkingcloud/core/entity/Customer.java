package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-27
 */
@TableName("yxy_customer")
public class Customer extends SuperModel<Customer> {

    private static final long serialVersionUID = 1L;

    @TableId
	private Long id;
	private String name;
	private String gender;
	private Date birth;
	private String avatar;
	private String nickname;
	@TableField("driving_license_image")
	private String drivingLicenseImage;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE, strategy = FieldStrategy.NOT_EMPTY)
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDrivingLicenseImage() {
		return drivingLicenseImage;
	}

	public void setDrivingLicenseImage(String drivingLicenseImage) {
		this.drivingLicenseImage = drivingLicenseImage;
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

	public Customer() {
	}

	public Customer(WechatUser wechatUser) {
		super();
		this.nickname = wechatUser.getNickname();
		this.avatar = wechatUser.getHeadimgurl();
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Customer{" +
			"id=" + id +
			", name=" + name +
			", gender=" + gender +
			", birth=" + birth +
			", avatar=" + avatar +
			", nickname=" + nickname +
			", drivingLicenseImage=" + drivingLicenseImage +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
