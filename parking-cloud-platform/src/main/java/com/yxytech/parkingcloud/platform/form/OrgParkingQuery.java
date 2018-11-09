package com.yxytech.parkingcloud.platform.form;

public class OrgParkingQuery {

    private Integer page;

    private Integer size;

    private Long orgId;

    private Long parkingId;

    private Boolean isRegulatory;

    private Boolean isOwner;

    private Boolean isOperator;

    public Integer getPage() {
        if(page == null || page < 1){
            return 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if(size == null || size < 1){
            return  10;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Boolean getRegulatory() {
        return isRegulatory;
    }

    public void setRegulatory(Boolean regulatory) {
        isRegulatory = regulatory;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public Boolean getOperator() {
        return isOperator;
    }

    public void setOperator(Boolean operator) {
        isOperator = operator;
    }
}
