package com.sanrenxing.shop.service;

import com.sanrenxing.shop.db.admin.bean.Role;
import com.sanrenxing.shop.util.PageList;

import java.util.Set;

/**
 * Created on 2017/2/16.
 *
 * @author tony
 */
public interface RoleService {

    /**
     * 创建角色
     * @param role     角色
     * @return         1： 创建完成   0： 创建失败
     */
    int createRole(Role role);

    /**
     * 更新角色
     * @param role     角色
     * @return         1： 更新完成   0： 无需更新或者没有查询待更新数据
     */
    int updateRole(Role role);

    /**
     * 删除角色
     * @param id       角色id
     * @return         1： 删除成功   0： 没有数据被删除
     */
    int deleteRole(Integer id);

    /**
     * 根据roleId查询角色名称
     * @param roleIds  角色id集合
     * @return 角色名称集合
     */
    Set<String> findRoles(Integer... roleIds);

    /**
     * 根据roleId查询所有资源名称
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Integer[] roleIds);

    /**
     * 角色分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @return             角色对象集合
     */
    PageList findByPage(int currPage, int pageSize);

    /**
     * 删除资源，同时删除拥有该资源的用户的该资源
     * @param resourceId  资源id
     */
    void removeRoleResource(Integer resourceId);

}
