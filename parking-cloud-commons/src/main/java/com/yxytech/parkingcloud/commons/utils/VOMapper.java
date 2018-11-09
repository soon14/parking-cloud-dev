package com.yxytech.parkingcloud.commons.utils;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class VOMapper {

    private ImmutableMap.Builder<String, Object> m;

    public VOMapper() {
        m = ImmutableMap.<String, Object>builder();
    }

    public VOMapper put(String k, Object v) {
        if (Objects.isNull(v)) {
            m.put(k, "");
        } else {
            m.put(k, v);
        }

        return this;
    }

    public VOMapper copy(Object obj, String ...attrs) {
        if (obj instanceof Map) {
            copyFromMap((Map)obj, attrs);
        } else {
            copyFromBean(obj, attrs);
        }
        return this;
    }

    private void copyFromMap(Map map, String ...attrs) {
        Arrays.asList(attrs).forEach(attr -> put(attr, map.get(attr)));
    }

    private void copyFromBean(Object obj, String ...attrs) {
        BeanWrapper o = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Arrays.asList(attrs).forEach(attr -> {
            try {
                put(attr, o.getPropertyValue(attr));
            } catch (NotReadablePropertyException ex) {
                //TODO: log
                put(attr, "");
            }

        });
    }

    public ImmutableMap<String, Object> build() {
        return m.build();
    }
}
