package com.yxytech.parkingcloud.core.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumToMap<T> {
    public List<Map<String, Object>> translateEnum(Class<T> c) {
        return this.translateEnumToMap(c, false);
    }

    public List<Map<String, Object>> translateEnum(Class<T> c, Boolean dealZero) {
        return this.translateEnumToMap(c, dealZero);
    }

    private List<Map<String, Object>> translateEnumToMap(Class<T> c, Boolean dealZero) {
        List<Map<String, Object>> ret = new ArrayList<>();

        for (T o : c.getEnumConstants()) {
            Map<String, Object> tmp = new HashMap<>();

            try {
                Method descMethod = o.getClass().getMethod("getDesc");
                Method valueMethod = o.getClass().getMethod("getValue");

                Object value = valueMethod.invoke(o);
                Object desc = descMethod.invoke(o);

                if (dealZero && value.equals(0)) {
                    continue;
                }

                tmp.put("value", value);
                tmp.put("desc", desc);

                ret.add(tmp);
            } catch (Exception e) {
                return ret;
            }
        }

        return ret;
    }
}
