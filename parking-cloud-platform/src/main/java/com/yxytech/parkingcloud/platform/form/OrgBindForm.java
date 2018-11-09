package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class OrgBindForm implements Serializable {

    private List<Long> parkingIds;

    @NotNull(message = "单位不能为空")
    private Long orgId;

    private List<Long> areaIds;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<Long> getParkingIds() {
        return parkingIds;
    }

    public void setParkingIds(List<Long> parkingIds) {
        this.parkingIds = parkingIds;
    }

    public List<Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }
}
