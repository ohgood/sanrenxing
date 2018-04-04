package com.sanrenxing.shop.db.admin.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * Created on 2017/8/2.
 *
 * @author xuwenjun
 */
public class UserProvider {

    public String findByRoleId(Map<String, Integer> map) {
        return new SQL() {{
            Integer roleId = map.get("roleId");
            SELECT("*");
            FROM("`sys_user`");
            WHERE("`role_ids` REGEXP '(^|,)" + roleId + "($|,)'");
        }}.toString();
    }

    public String getUsernames(Map<String, List<String>> map) {
        List<String> roleIds = map.get("roleIds");
        String regexp = roleIds.stream().map(a -> "(^|,)" + a + "($|,)").reduce((b, c) -> b + "|" + c).orElse("error");
        return new SQL() {{
            SELECT("username");
            FROM("`sys_user`");
            WHERE("`role_ids` REGEXP '(" + regexp + ")'");
        }}.toString();
    }

}
