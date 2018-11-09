package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ParkingBindFeeschemaForm {

    private List<Long> schemaIds;

    @NotNull(message = "停车场不能为空")
    private Long parkingId;

    public List<Long> getSchemaIds() {
        return schemaIds;
    }

    public void setSchemaIds(List<Long> schemaIds) {
        this.schemaIds = schemaIds;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }
}
