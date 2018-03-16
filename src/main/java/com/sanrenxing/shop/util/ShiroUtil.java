package com.sanrenxing.shop.util;

import com.sanrenxing.shop.db.admin.bean.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created by Dell on 2017/7/3.
 */
public class ShiroUtil {

    public static String  getUsername() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        return user.getUsername();
    }
}
