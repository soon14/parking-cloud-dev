package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;

import java.io.Serializable;
import java.util.List;

public class MultistepForm implements Serializable{

    List<FeeRateMultistep> steps;


    public List<FeeRateMultistep> getSteps() {
        return steps;
    }

    public void setSteps(List<FeeRateMultistep> steps) {
        this.steps = steps;
    }
}
