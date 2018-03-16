package com.sanrenxing.shop.db.admin.dao;

import com.github.pagehelper.PageHelper;
import com.sanrenxing.shop.db.admin.bean.Role;
import com.sanrenxing.shop.db.admin.mapper.RoleMapper;
import com.sanrenxing.shop.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Dao
 *
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Repository
public class RoleDao {

    @Autowired
    private RoleMapper roleMapper;

    public int createRole(Role role) {
        return roleMapper.createRole(role);
    }

    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    public int deleteRole(Integer roleId) {
        return roleMapper.deleteRole(roleId);
    }

    public Role findOne(Integer roleId) {
        return roleMapper.findOne(roleId);
    }

    /**
     * 角色分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @return             角色对象集合
     */
    public PageList<Role> findByPage(int currPage, int pageSize) {
        PageHelper.startPage(currPage, pageSize);
        Role role = new Role();
        List<Role> roles = roleMapper.find(role);
        PageList<Role> pageList = new PageList<>(currPage, pageSize, roleMapper.count(role));
        pageList.addAll(roles);
        return pageList;
    }

    public void removeRoleResource(Integer resourceId) {
        List<Role> roles = getRoles(resourceId);
        roles.forEach(a -> {
            a.getResourceIds().remove(resourceId);
            updateRole(a);
        });

    }

    private List<Role> getRoles(Integer resourceId) {
        return roleMapper.getRoles(resourceId);
    }
}
