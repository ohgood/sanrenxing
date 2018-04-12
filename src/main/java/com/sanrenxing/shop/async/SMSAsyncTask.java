package com.sanrenxing.shop.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created on 2018/1/3.
 * @author wukaitong
 */
@Component
public class SMSAsyncTask {

    /**
     * 异步短信通知
     * @param contact  手机号
     * @param pwd      密码
     */
    @Async
    public void sendMessage(String contact, String pwd) {
        //SMSUtil.getRequest2(contact, pwd);
    }

}
