package com.yxytech.parkingcloud.core.utils;

import java.io.Serializable;
import java.util.Date;

public class FreeEntity implements Serializable {
    private Long id;
    private Integer type;
    private Date startedAt;
    private Date endAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public FreeEntity() {
    }
}
