package com.sanrenxing.shop.db.admin.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created on 2017/8/2.
 *
 * @author tony
 */
public class UserProvider {

    public String findByRoleId(String roleId) {
        return new SQL() {{
            SELECT("*");
            FROM("`sys_user`");
            WHERE("`role_ids` REGEXP (^|,)#{roleId}($|,)");
        }}.toString();
    }

}
