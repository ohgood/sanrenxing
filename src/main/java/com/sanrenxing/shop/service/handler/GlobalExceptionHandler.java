package com.sanrenxing.shop.service.handler;

import com.sanrenxing.shop.rest.SRXResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * Created on 2017/7/19.
 * @author tony
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public SRXResponse exceptionHandler(Exception e, HttpServletResponse response) {
        if (e instanceof ConstraintViolationException) {           //处理请求字段校验不通过异常
            Map<String, String> map = ((ConstraintViolationException)e).getConstraintViolations().stream()
                    .map(a -> new HashMap.SimpleEntry<>((String)a.getInvalidValue(), a.getMessage()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return new SRXResponse(SRXResponse.Status.REQUEST_PARAMETER_ERROR).result(map);
        } else if (e instanceof UnauthorizedException || e instanceof AuthorizationException) {     //权限异常
//            if (CheckUtil.isAjaxRequest(request)) {
                return new SRXResponse(SRXResponse.Status.UNAUTHORITY);
//            } else {     //如果不是ajax请求，当非法访问处理，
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return new Response(Response.Status.ERROR_403);
//            }
        } else if (e instanceof MissingServletRequestParameterException) {     //访问特定路径时缺少必要请求参数
            return new SRXResponse(SRXResponse.Status.REQUEST_PARAMETER_ERROR);
        } else if (e instanceof NoHandlerFoundException) {     //访问路径不存在  404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new SRXResponse(SRXResponse.Status.ERROR_404);
        } else if (e instanceof MethodArgumentNotValidException) {
            return new SRXResponse(SRXResponse.Status.REQUEST_PARAMETER_ERROR).result(e.getMessage());
        } else {      // 其它统一处理成系统异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new SRXResponse(SRXResponse.Status.SYSTEM_ERROR);
        }
    }

}