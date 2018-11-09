package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
@TableName("yxy_customer_cars")
public class CustomerCars extends SuperModel<CustomerCars> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("plate_number")
	private String plateNumber;
	@TableField("plate_color")
	private ColorsEnum plateColor;
	@TableField("car_type")
	private CarTypeEnum carType;
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField("is_green_energy")
	private Boolean isGreenEnergy;
    private transient String authImage;
    private transient CustomerBindCars bindRelation;

	public Boolean getGreenEnergy() {
		return isGreenEnergy;
	}

	public void setGreenEnergy(Boolean greenEnergy) {
		isGreenEnergy = greenEnergy;
	}

	public CustomerBindCars getBindRelation() {
        return bindRelation;
    }

    public void setBindRelation(CustomerBindCars bindRelation) {
        this.bindRelation = bindRelation;
    }

    public String getAuthImage() {
        return authImage;
    }

    public void setAuthImage(String authImage) {
        this.authImage = authImage;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public ColorsEnum getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(ColorsEnum plateColor) {
		this.plateColor = plateColor;
	}

	public CarTypeEnum getCarType() {
		return carType;
	}

	public void setCarType(CarTypeEnum carType) {
		this.carType = carType;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CustomerCars{" +
			"id=" + id +
			", plateNumber=" + plateNumber +
			", plateColor=" + plateColor +
			", carType=" + carType +
			", createdBy=" + createdBy +
			"}";
	}
}
