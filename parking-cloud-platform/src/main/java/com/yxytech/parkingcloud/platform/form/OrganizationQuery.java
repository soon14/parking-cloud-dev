package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.OrgNatureEnum;

import java.io.Serializable;

public class OrganizationQuery implements Serializable{

    private Integer page;
    private Integer size;
    private Long areaId;
    private String  fullName;
    private String shortName;
    private OrgNatureEnum orgNature;
    private Boolean isPropertyOrg;
    private Boolean isManageOrg;
    private Boolean isFacilityOrg;
    private Integer approveStatus;
    private String start_time;
    private String end_time;

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
            return 10;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public OrgNatureEnum getOrgNature() {
        return orgNature;
    }

    public void setOrgNature(OrgNatureEnum orgNature) {
        this.orgNature = orgNature;
    }

    public Boolean getPropertyOrg() {
        return isPropertyOrg;
    }

    public void setPropertyOrg(Boolean propertyOrg) {
        isPropertyOrg = propertyOrg;
    }

    public Boolean getManageOrg() {
        return isManageOrg;
    }

    public void setManageOrg(Boolean manageOrg) {
        isManageOrg = manageOrg;
    }

    public Boolean getFacilityOrg() {
        return isFacilityOrg;
    }

    public void setFacilityOrg(Boolean facilityOrg) {
        isFacilityOrg = facilityOrg;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
