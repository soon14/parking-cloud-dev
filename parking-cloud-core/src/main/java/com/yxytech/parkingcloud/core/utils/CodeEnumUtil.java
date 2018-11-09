package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.enums.IEnum;

public class CodeEnumUtil {
    /**
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & IEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if ((int)e.getValue()== code)
                return e;
        }
        return null;
    }
}