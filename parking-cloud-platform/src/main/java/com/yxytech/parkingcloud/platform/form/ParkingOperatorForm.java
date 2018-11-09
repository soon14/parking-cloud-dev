package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingOperatorForm implements Serializable{

    private Integer id;

    @NotNull(message = "经营单位不能为空")
    private Long operatorOrgId;

    @NotNull(message = "关联停车场不能为空")
    private Long parkingId;

    @NotBlank(message = "经营证明不能为空")
    private String manageProve;

    private ApproveEnum approveStatus;

    private Boolean isUsing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOperatorOrgId() {
        return operatorOrgId;
    }

    public void setOperatorOrgId(Long operatorOrgId) {
        this.operatorOrgId = operatorOrgId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getManageProve() {
        return manageProve;
    }

    public void setManageProve(String manageProve) {
        this.manageProve = manageProve;
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
