package com.sanrenxing.shop.util;

/**
 * Created on 2016/12/19
 *
 * @author annpeter.it@gmail.com
 */
public interface IntEnum<E extends Enum<E>> {
    int getIntValue();

    static <E extends IntEnum> E convert(Class<E> type, long intValue) {
        for (E em : type.getEnumConstants()) {
            if (em.getIntValue() == intValue) {
                return em;
            }
        }
        throw new IndexOutOfBoundsException("超出该enum类型范围");
    }
}