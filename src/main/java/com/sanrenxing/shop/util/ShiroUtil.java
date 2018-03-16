package com.sanrenxing.shop.util;

import com.sanrenxing.shop.db.admin.bean.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created on 2017/7/3.
 * @author tony
 */
public class ShiroUtil {

    public static String  getUsername() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        return user.getUsername();
    }
}
