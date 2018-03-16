package com.sanrenxing.shop.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义权限过滤器, 实际不执行任何过滤
 * Created on 2017/3/16.
 * @author tony
 */
public class SysUserFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = ThreadContext.getSubject();
        System.out.println("----------------->" + subject);
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        System.out.println("----------------->" + username);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

}
