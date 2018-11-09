package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class UserGroupAreaForm implements Serializable {

    @NotNull(message = "用户组不能为空")
    private Long groupId;

    @NotNull(message = "区域不能为空")
    private List<Long> areaId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getAreaId() {
        return areaId;
    }

    public void setAreaId(List<Long> areaId) {
        this.areaId = areaId;
    }
}
