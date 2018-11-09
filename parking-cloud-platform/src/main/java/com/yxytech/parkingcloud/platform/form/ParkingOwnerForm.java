package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingOwnerForm implements Serializable {

    private Long id;

    @NotNull(message = "产权单位不能为空")
    private Long ownerOrgId;

    @NotNull(message = "停车场不能为空")
    private Long parkingId;

    private ApproveEnum approveStatus;

    @NotBlank(message = "产权证明不能为空")
    private String proveImages;

    private Boolean isUsing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getProveImages() {
        return proveImages;
    }

    public void setProveImages(String proveImages) {
        this.proveImages = proveImages;
    }

    public ApproveEnum getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveEnum approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }
}
