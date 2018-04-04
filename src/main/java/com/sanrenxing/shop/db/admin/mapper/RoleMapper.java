package com.sanrenxing.shop.db.admin.mapper;

import com.sanrenxing.shop.db.admin.bean.Role;
import com.sanrenxing.shop.db.admin.provider.RoleProvider;
import com.sanrenxing.shop.db.shop.SqlProvider;
import com.sanrenxing.shop.util.SetTypeHandler;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017/2/23.
 *
 * @author xuwenjun
 */
@Component
public interface RoleMapper {

    /**
     * 创建角色
     * @param role 角色
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int createRole(Role role);

    /**
     * 更新角色
     * @param role 角色
     */
    @UpdateProvider(type = SqlProvider.class, method = "update")
    int updateRole(Role role);

    /**
     * 删除角色
     * @param id 角色id
     */
    @Delete("DELETE FROM sys_role WHERE id = #{id}")
    int deleteRole(Integer id);

    /**
     * 查询一个角色
     * @param id 角色id
     * @return 角色对象
     */
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    @Results(value = {
            @Result(column = "resource_ids", property = "resourceIds", typeHandler = SetTypeHandler.class)
    })
    Role findOne(Integer id);

    /**
     * 角色分页查询
     * @param role        过滤条件
     * @return            用户列表
     */
    @SelectProvider(type = SqlProvider.class, method = "find")
    @Results(value = {
            @Result(column = "resource_ids", property = "resourceIds", typeHandler = SetTypeHandler.class)
    })
    List<Role> find(Role role);

    /**
     * 获取符合条件的数据量
     * @param role        过滤条件
     * @return 符合条件的数据量
     */
    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(Role role);

    /**
     * 查询所有拥有指定资源权限的角色
     * @param resourceId     资源id
     * @return               角色集合
     */
    @SelectProvider(type = RoleProvider.class, method = "findByResourceId")
    List<Role> getRoles(@Param("resourceId") Integer resourceId);

}
