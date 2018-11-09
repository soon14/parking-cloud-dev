package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingApproveForm implements Serializable{

    @NotNull(message = "经营单位不能为空")
    private Long operatorOrgId;


    @NotBlank(message = "经营证明不能为空")
    private String manageProve;

    @NotNull(message = "产权单位不能为空")
    private Long ownerOrgId;

    @NotBlank(message = "经营备案证不能为空")
    private String businessNumber;

    private ApproveEnum approveStatus;

    @NotBlank(message = "产权证明不能为空")
    private String proveImages;

    private String chargeLicence;

    private Boolean isUsing;

    public String getChargeLicence() {
        return chargeLicence;
    }

    public void setChargeLicence(String chargeLicence) {
        this.chargeLicence = chargeLicence;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public ApproveEnum getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveEnum approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getProveImages() {
        return proveImages;
    }

    public void setProveImages(String proveImages) {
        this.proveImages = proveImages;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }

    public Long getOperatorOrgId() {
        return operatorOrgId;
    }

    public void setOperatorOrgId(Long operatorOrgId) {
        this.operatorOrgId = operatorOrgId;
    }

    public String getManageProve() {
        return manageProve;
    }

    public void setManageProve(String manageProve) {
        this.manageProve = manageProve;
    }

}
