package com.sanrenxing.shop.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2017/7/14.
 *
 * @author tony
 */
public class CheckUtil {

    /**
     * 判断请求是否是ajax请求
     *
     * @param request   请求
     * @return          true： 是ajax请求  false： 不是ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

}
