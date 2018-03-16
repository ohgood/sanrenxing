package com.sanrenxing.shop.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/24 0024.
 */
@WebFilter(filterName="crossOrignFilter", urlPatterns={"/*"}, initParams={@WebInitParam(name="AccessControlAllowOrigin",value="*"),
        @WebInitParam(name="AccessControlAllowMethods", value="POST, GET, DELETE, PUT"),
        @WebInitParam(name="AccessControlMaxAge", value="3628800"),
        @WebInitParam(name="AccessControlAllowHeaders", value="x-requested-with")})
public class CrossOriginFilter implements Filter {
    private FilterConfig config = null;

    public CrossOriginFilter() {
    }
    public void destroy() {
        this.config = null;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", config.getInitParameter("AccessControlAllowOrigin"));
        httpResponse.setHeader("Access-Control-Allow-Methods", config.getInitParameter("AccessControlAllowMethods"));
        httpResponse.setHeader("Access-Control-Max-Age", config.getInitParameter("AccessControlMaxAge"));
        httpResponse.setHeader("Access-Control-Allow-Headers", config.getInitParameter("AccessControlAllowHeaders"));
        chain.doFilter(request, response);
    }
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }
}