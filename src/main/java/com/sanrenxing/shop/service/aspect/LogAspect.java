package com.sanrenxing.shop.service.aspect;

import com.sanrenxing.shop.util.JsonUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 记录请求日志
 *
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Aspect
@Component
public class LogAspect {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

//    @AfterThrowing(value = "execution(* cn.juhe.recharge.controller.*.*(..)) ", throwing = "e")
//    public void catchSystemError(Throwable e) throws Throwable {
//        if (e instanceof ConstraintViolationException) {
//            throw e;
//        }
//        throw new InternalServerErrorException("服务器内部有异常啦", e);
//    }


    @Before(value = "execution(* com.sanrenxing.shop.controller.*.*(..)) ")
    public void recordLog() throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        threadPool.execute(() -> {
            String url = String.valueOf(request.getRequestURL());
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            logger.info("接收到请求---> url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        });
    }


    @AfterReturning(value = "execution(* com.sanrenxing.shop.controller.*.*(..)) ", returning="rvt")
    public Object recordResult(Object rvt) throws Throwable {
        if (rvt != null) {
            threadPool.execute(() -> {
                String jsonResult = JsonUtils.parseObject2String(rvt);
                logger.info("请求结束, controller的返回值是: " + jsonResult);
            });
        }
        return rvt;
    }

}
