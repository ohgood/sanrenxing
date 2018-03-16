package com.sanrenxing.shop.config;

import com.sanrenxing.shop.shiro.SpringCacheManagerWrapper;
import com.sanrenxing.shop.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.sanrenxing.shop.shiro.filter.SysUserFilter;
import com.sanrenxing.shop.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/7/17.
 *
 * @author tony
 */
@Configuration
@EnableTransactionManagement
public class ShiroConfig {

    @Bean
    public EhCacheManagerFactoryBean ehcacheManager() {
        EhCacheManagerFactoryBean ehCache = new EhCacheManagerFactoryBean();
        ehCache.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCache;
    }

    @Bean
    public EhCacheCacheManager springCacheManager() {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        EhCacheManagerFactoryBean ehCache = ehcacheManager();
        cacheManager.setCacheManager(ehCache.getObject());
        return cacheManager;
    }

    @Bean
    public SpringCacheManagerWrapper cacheManager() {
        SpringCacheManagerWrapper wrapper = new SpringCacheManagerWrapper();
        wrapper.setCacheManager(springCacheManager());
        return wrapper;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(cacheManager());
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        userRealm.setCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        userRealm.setAuthenticationCacheName("authenticationCache");
        return userRealm;
    }

    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sanrenxing.session.id");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return sessionDAO;
    }

    @Bean
    public QuartzSessionValidationScheduler sessionValidationScheduler() {
        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
        scheduler.setSessionValidationInterval(1800000);
        return scheduler;
    }


    @Bean
    @DependsOn("sessionValidationScheduler")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(604800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        QuartzSessionValidationScheduler scheduler = sessionValidationScheduler();
        scheduler.setSessionManager(sessionManager);
        sessionManager.setSessionValidationScheduler(scheduler);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }


    /**
     * 安全管理器
     * @return  安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public SysUserFilter sysUserFilter() {
        return new SysUserFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SysUserFilter sysUserFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/forbidden");
        shiroFilterFactoryBean.setUnauthorizedUrl("/state/unauthorized");
        Map<String, Filter> map = new HashMap<>();
        map.put("sysUser", sysUserFilter);
        shiroFilterFactoryBean.setFilters(map);
        shiroFilterFactoryBean.setFilterChainDefinitions("/logout = user\n" +
                "/login = anon\n" +
                "/register = anon\n" +
                "/forbidden = anon\n" +
                "/test/** = sysUser\n" +
                "/ajax/** = sysUser\n" +
                "/** = sysUser");
        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        SecurityUtils.setSecurityManager(securityManager());
        return advisor;
    }

}
