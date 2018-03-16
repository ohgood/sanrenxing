package com.sanrenxing.shop.db.admin.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created on 2017/8/2.
 * @author tony
 */
public class RoleProvider {

    public String findByResourceId(String resourceId) {
        return new SQL() {{
            SELECT("*");
            FROM("`sys_role`");
            WHERE("`resource_ids` REGEXP (^|,)#{resourceId}($|,)");
        }}.toString();
    }

}
