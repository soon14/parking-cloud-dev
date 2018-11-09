package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public class FeeRuleRateForm implements Serializable{

    @NotBlank(message = "费率描述不可为空!")
    String desc;
    FeeRateRule rule;

    List<FeeRateMultistep> steps;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public FeeRateRule getRule() {
        return rule;
    }

    public void setRule(FeeRateRule rule) {
        this.rule = rule;
    }

    public List<FeeRateMultistep> getSteps() {
        return steps;
    }

    public void setSteps(List<FeeRateMultistep> steps) {
        this.steps = steps;
    }
}
