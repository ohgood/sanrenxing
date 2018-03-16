package com.sanrenxing.shop.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 声明式事务配置
 * Created on 2018/1/10.
 * @author wukaitong
 */
@Configuration
public class TransactionalConfig {

    @Autowired
    private DataSourceTransactionManager adminTransactionManager;

    @Autowired
    private DataSourceTransactionManager shopTransactionManager;

    @Bean(name = "adminTransactionInterceptor")
    public TransactionInterceptor adminTransactionInterceptor() {
        return createTransactionInterceptor(adminTransactionManager);
    }

    @Bean(name = "shopTransactionInterceptor")
    public TransactionInterceptor shopTransactionInterceptor() {

        return createTransactionInterceptor(shopTransactionManager);
    }

    public TransactionInterceptor createTransactionInterceptor(DataSourceTransactionManager transactionManager) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        // 事物管理器
        transactionInterceptor.setTransactionManager(transactionManager);
        Properties transactionAttributes = new Properties();
        // 新增
        transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable");
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED,-Throwable");
        transactionAttributes.setProperty("upload*", "PROPAGATION_REQUIRED,-Throwable");
        // 修改
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable");
        transactionAttributes.setProperty("pull*", "PROPAGATION_REQUIRED,-Throwable");
        // 删除
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable");

        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        return transactionInterceptor;
    }

    //代理到ServiceImpl的Bean
    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(true);
        transactionAutoProxy.setBeanNames("*ServiceImpl");
        transactionAutoProxy.setInterceptorNames("adminTransactionInterceptor", "shopTransactionInterceptor");
        return transactionAutoProxy;
    }

}
