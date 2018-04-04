package com.sanrenxing.shop.db.admin.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created on 2017/8/2.
 *
 * @author xuwenjun
 */
public class RoleProvider {

    public String findByResourceId(Map<String, Integer> map) {
        return new SQL() {{
            Integer resourceId = map.get("resourceId");
            SELECT("*");
            FROM("`sys_role`");
            WHERE("`resource_ids` REGEXP '(^|,)" + resourceId + "($|,)'");
        }}.toString();
    }

}
