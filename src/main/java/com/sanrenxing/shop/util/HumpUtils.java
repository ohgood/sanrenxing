package com.sanrenxing.shop.util;

/**
 * 字符串  驼峰形式跟下划线形式互转
 * Created on 2017/7/28.
 * @author tony
 */
public class HumpUtils {

    public static String humpToUnderLine(String s) {
        if (StringUtil.isNullOrEmpty(s)) {
            return null;
        } else {
            s = s.substring(0, 1).toLowerCase() + s.substring(1);
            StringBuilder sb = new StringBuilder();

            char[] chs = s.toCharArray();

            for (char c : chs) {
                if (Character.isUpperCase(c)) {
                    sb.append("_");
                    sb.append(Character.toString(c).toLowerCase());
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }


    public static String underLineToHump(String s) {
        if (StringUtil.isNullOrEmpty(s)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String[] arr = s.split("_");


        boolean first = true;
        for(String item : arr) {
            if (first && !item.equals("")) {
                sb.append(item);
                first = false;
            } else if (!first && !item.equals("")) {
                sb.append(Character.toString(item.charAt(0)).toUpperCase());
                sb.append(item.substring(1));
            }
        }

        return sb.toString();
    }

}

