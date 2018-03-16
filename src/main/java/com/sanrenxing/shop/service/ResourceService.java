package com.sanrenxing.shop.service;

import com.sanrenxing.shop.bean.ResourceBean;
import com.sanrenxing.shop.db.admin.bean.Resource;
import com.sanrenxing.shop.util.PageList;

import java.util.List;
import java.util.Set;

/**
 * Created on 2017/2/28.
 * @author tony
 */
public interface ResourceService {

    /**
     * 创建一个资源
     *
     * @param resource   资源对象
     * @return           1： 创建完成    0： 创建失败
     */
    int createResource(Resource resource);

    /**
     * 更新一个资源
     *
     * @param resource   资源对象
     * @return 资源       1： 更新完成    0： 更新失败
     */
    int updateResource(Resource resource);

    /**
     * 删除一个资源
     * @param resourceId 资源id
     */
    int deleteResource(Integer resourceId);

    /**
     * 查询一个资源
     * @param resourceId 资源id
     * @return 资源
     */
    Resource findOne(Integer resourceId);

    /**
     * 查询一个资源
     * @param permission 权限字符串
     * @return 资源
     */
    Resource findByPermission(String permission);

    /**
     * 查询所有资源
     * @return 所有资源
     */
    List<Resource> findAll();

    /**
     * 资源分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @param type         过滤条件： 资源类型
     * @return             用户对象集合
     */
    PageList findByPage(int currPage, int pageSize, Integer type);

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds 资源id
     * @return 权限set
     */
    Set<String> findPermissions(Set<Integer> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions 权限set
     * @return menus
     */
    List<ResourceBean> findMenus(Set<String> permissions);

    /**
     * 权限校验
     * @param permissions 权限名称
     * @param resource 资源
     * @return true: 包含权限  false: 不包含权限
     */
    boolean hasPermission(Set<String> permissions, Resource resource);

}