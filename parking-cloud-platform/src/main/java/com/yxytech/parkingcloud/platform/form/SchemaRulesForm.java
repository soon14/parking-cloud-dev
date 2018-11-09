package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;
import java.util.List;

public class SchemaRulesForm implements Serializable{


    Long schemaId;

    List<Long> rules;


    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public List<Long> getRules() {
        return rules;
    }

    public void setRules(List<Long> rules) {
        this.rules = rules;
    }
}
