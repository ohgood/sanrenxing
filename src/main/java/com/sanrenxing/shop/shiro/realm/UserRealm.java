package com.sanrenxing.shop.shiro.realm;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;

/**
 *  用户验证Realm
 *
 *  过滤器名称	过滤器类	描述
 *  anon	org.apache.shiro.web.filter.authc.AnonymousFilter	匿名过滤器，可以认为是不过滤
 *  authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter	如果继续操作，需要做对应的表单验证否则不能通过
 *  authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter	基本http验证过滤，如果不通过，跳转到登录页面
 *  logout	org.apache.shiro.web.filter.authc.LogoutFilter	登录退出过滤器
 *  noSessionCreation	org.apache.shiro.web.filter.session.NoSessionCreationFilter	没有session创建过滤器
 *  perms	org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter	权限过滤器
 *  port	org.apache.shiro.web.filter.authz.PortFilter	端口过滤器，可以设置是否是指定端口如果不是跳转到登录页面
 *  rest	org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter	http方法过滤器，可以指定如post不能进行访问等
 *  roles	org.apache.shiro.web.filter.authz.RolesAuthorizationFilter	角色过滤器，判断当前用户是否指定角色
 *  ssl	org.apache.shiro.web.filter.authz.SslFilter	请求需要通过ssl，如果不是跳转回登录页
 *  user	org.apache.shiro.web.filter.authc.UserFilter	如果访问一个已知用户，比如记住我功能，走这个过滤器
 */
public class UserRealm extends AuthorizingRealm {


    private static Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    @Lazy
    private UserService userService;

    /**
     * 授权操作，决定那些角色可以使用那些资源
     * @param principals 权限关系
     * @return 授权信息
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    /**
     * 认证操作，判断一个请求是否被允许进入系统
     * @param token 登录后的token
     * @return 认证信息
     * @throws AuthenticationException 认证失败异常
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        log.info("doGetAuthenticationInfo = " + username);
        User user = userService.findByUsername(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表

        //清除该用户以前登录时保存的session
        sessions.stream().forEach(session -> {
            if(username.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                sessionManager.getSessionDAO().delete(session);
            }
        });


        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        return new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
    }

    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        if(principals != null)
            super.clearCachedAuthorizationInfo(principals);
    }

    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
