package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.db.admin.bean.Role;
import com.sanrenxing.shop.db.admin.dao.RoleDao;
import com.sanrenxing.shop.service.ResourceService;
import com.sanrenxing.shop.service.RoleService;
import com.sanrenxing.shop.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色Service实现类
 *
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceService resourceService;

    /**
     * 创建角色
     * @param role     角色
     * @return         1： 创建完成   0： 创建失败
     */
    @Override
    public int createRole(Role role) {
        return roleDao.createRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public int deleteRole(Integer id) {
        return roleDao.deleteRole(id);
    }

    /**
     * 根据roleId查询角色名称
     * @param roleIds  角色id集合
     * @return 角色名称集合
     */
    @Override
    public Set<String> findRoles(Integer... roleIds) {
        return Arrays.stream(roleIds).map(roleDao::findOne).filter(Objects::nonNull)
                .map(Role::getRole).collect(Collectors.toSet());
    }

    /**
     * 根据roleId查询所有资源名称
     * @param roleIds    角色id集合
     * @return           符合条件的所有权限
     */
    @Override
    public Set<String> findPermissions(Integer[] roleIds) {
        Set<Integer> resourceIds = Arrays.stream(roleIds).map(roleDao::findOne).filter(Objects::nonNull)
                .flatMap(a -> a.getResourceIds().stream()).collect(Collectors.toSet());
        return resourceService.findPermissions(resourceIds);
    }


    /**
     * 角色分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @return             角色对象集合
     */
    @Override
    public PageList findByPage(int currPage, int pageSize) {
        return roleDao.findByPage(currPage, pageSize);
    }

    @Override
    public void removeRoleResource(Integer resourceId) {
        roleDao.removeRoleResource(resourceId);
    }


}
