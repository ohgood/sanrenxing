package com.sanrenxing.shop.controller;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.UserService;
import com.sanrenxing.shop.shiro.realm.UserRealm;
import com.sanrenxing.shop.util.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

/**
 * Created on 2017/7/13
 * @author tony
 */
@RestController
@Validated
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRealm userRealm;


    @RequestMapping(value = "/login")
    public SRXResponse showLoginForm(@RequestParam("username") String username,
                                     @RequestParam("password") String password) throws InterruptedException {
        String error;
        User user;
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            return new SRXResponse(SRXResponse.Status.SUCCESS).result(currentUser.getSession().getAttribute(Constants.CURRENT_USER));
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);
        try {
            currentUser.login(token);
            user = userService.findByUsername(username);
            currentUser.getSession().setAttribute(Constants.CURRENT_USER, user);
        } catch (UnknownAccountException uae) {
            error = "没有该用户： " + token.getPrincipal();
            logger.info(error, uae);
            return new SRXResponse(SRXResponse.Status.USER_NOT_EXISTS, error);
        } catch (IncorrectCredentialsException ice) {
            error = token.getPrincipal() + " 的密码不正确!";
            logger.info(error, ice);
            return new SRXResponse(SRXResponse.Status.LOGIN_FAIL, error);
        } catch (LockedAccountException lae) {
            error = token.getPrincipal() + " 被锁定 ，请联系管理员";
            logger.info(error, lae);
            return new SRXResponse(SRXResponse.Status.USER_LOCKED, error);
        } catch (AuthenticationException ae) {
            error = "未知异常";
            logger.info(error, ae);
            return new SRXResponse(SRXResponse.Status.UNKNOWN_ERROR, error);
        } finally {
            userRealm.clearCachedAuthorizationInfo(currentUser.getPrincipals());   // 用户重新登录后，清除之前的权限数据
        }

        return new SRXResponse(SRXResponse.Status.SUCCESS).result(user);
    }

    @RequestMapping(value = "/register")
    public SRXResponse ajaxRegister(@NotEmpty @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$", message = "请输入正确的手机号")
                                    @RequestParam("username") String username,
                                    @NotEmpty @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9a-zA-Z_]{6,32}$", message = "密码为6到32位字母数字下划线组合，必须包含字母数字")
                                    @RequestParam("password") String password,
                                    @NotEmpty  @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "真实姓名为2到10位中文")
                                    @RequestParam("realname") String realName) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealName(realName);
        if (userService.createUser(user) == 1) {
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        } else {
            return new SRXResponse(SRXResponse.Status.USER_EXISTS);
        }
    }

    @RequestMapping(value = "/logout")
    public SRXResponse logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return new SRXResponse(SRXResponse.Status.LOGOUT);
    }

    /**
     * 无权限，禁止访问
     */
    @RequestMapping(value = "/forbidden")
    public SRXResponse forbidden() {
        return new SRXResponse(SRXResponse.Status.UNAUTHORITY);
    }
}
