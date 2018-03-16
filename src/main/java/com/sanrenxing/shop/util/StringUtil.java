package com.sanrenxing.shop.util;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2017/7/14.
 * @author tony
 */
public class StringUtil {

    public static String join(String[] array, String separator) {
        return join(Stream.of(array).collect(Collectors.toList()), separator);
    }

    public static String join(Collection<String> collection, String separator) {
        return collection.stream().reduce((a, b) -> a + separator + b).orElse("");
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.equals("");
    }


    /**
     * 判断所有对象是不是都是空对象
     * @param objs 对象集合
     * @return   true： 都是空对象  else false
     */
    public static boolean allNull(Object... objs) {
        for(Object obj : objs) {
            if (obj != null)
                return false;
        }
        return true;
    }

    /**
     * 判断所有对象是否包含空对象
     * @param objs   对象集合
     * @return   true： 包含空对象  else false
     */
    public static boolean hasNull(Object... objs) {
        for(Object obj : objs) {
            if (obj == null)
                return true;
        }
        return false;
    }

}
