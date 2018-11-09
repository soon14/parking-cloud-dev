package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;
import java.util.List;

public class SchemaParkingForm implements Serializable{


    Long schemaId;

    List<Long> parking;


    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public List<Long> getParking() {
        return parking;
    }

    public void setParking(List<Long> parking) {
        this.parking = parking;
    }
}
