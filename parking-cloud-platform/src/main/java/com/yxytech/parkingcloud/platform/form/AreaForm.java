package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AreaForm implements Serializable{

    private Long id;

    @NotBlank(message = "区域名称不能为空")
    private String name;

    @NotBlank(message = "区域编码不能为空")
    private String code;

    @NotNull(message = "行政级别不能为空")
    private String executiveLevel;

    @NotNull(message = "上级区域不能为空")
    private Long parentId;

    @NotBlank(message = "序号不能为空")
    private String sortNumber;

    @NotNull(message = "层级不能为空")
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutiveLevel() {
        return executiveLevel;
    }

    public void setExecutiveLevel(String executiveLevel) {
        this.executiveLevel = executiveLevel;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
