package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


public class UserGroupParkingForm implements Serializable {

    @NotNull(message = "用户组不能为空")
    private Long groupId;

    @NotNull(message = "停车场不能为空")
    private List<Long> parkingId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getParkingId() {
        return parkingId;
    }

    public void setParkingId(List<Long> parkingId) {
        this.parkingId = parkingId;
    }
}
