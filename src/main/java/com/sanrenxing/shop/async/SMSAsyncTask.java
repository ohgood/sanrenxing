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

    /**
     * 系统中心：站内信
     * 请款模块：代办事项、线下线上请款、请款记录
     * 采购模块：充值卡验货、到货、录入、转售，优先级调整、充值卡错卡、冻结/冻结
     * 财务模块：请款查询
     * 运营模块：放单、拆单/拼单、订单处理、卡单查询、订单查询、金额/充值卡查询
     * 供应商模块：供应商管理、策略管理、渠道管理
     * 客户模块： 客户列表、客户业务、客户加款
     * 权限管理
     */
    public void test() {

    }
}
