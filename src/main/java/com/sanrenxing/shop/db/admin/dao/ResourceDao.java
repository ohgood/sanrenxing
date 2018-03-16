package com.sanrenxing.shop.db.admin.dao;

import com.github.pagehelper.PageHelper;
import com.sanrenxing.shop.db.admin.bean.Resource;
import com.sanrenxing.shop.db.admin.mapper.ResourceMapper;
import com.sanrenxing.shop.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2017/2/24.
 *
 * @author tony
 */
@Repository
public class ResourceDao {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 创建资源
     *
     * @param resource 资源
     */
    public int createResource(Resource resource) {
        return resourceMapper.createResource(resource);
    }

    /**
     * 更新资源
     *
     * @param resource 资源
     */
    public int updateResource(Resource resource) {
        return resourceMapper.updateResource(resource);
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     */
    public int deleteResource(Integer resourceId) {
        return resourceMapper.deleteResource(resourceId);
    }

    /**
     * 查询一个资源
     *
     * @param resourceId 资源id
     * @return 资源对象
     */
    public Resource findOne(Integer resourceId) {
        return resourceMapper.findOne(resourceId);
    }

    /**
     * 查询一个资源
     *
     * @param permission 权限名称
     * @return 资源对象
     */
    public Resource findByPermission(String permission) {
        return resourceMapper.findByPermission(permission);
    }


    /**
     * 资源分页查询
     *
     * @param currPage  查询页
     * @param pageSize  查询数据量
     * @param type      过滤条件： 资源类型
     * @return 用户对象集合
     */
    public PageList<Resource> findByPage(int currPage, int pageSize, Integer type) {
        PageHelper.startPage(currPage, pageSize);
        Resource resource = new Resource();
        resource.setType(type);
        List<Resource> resources = resourceMapper.find(resource);
        PageHelper.clearPage();
        PageList<Resource> pageList = new PageList<>(currPage, pageSize, resourceMapper.count(resource));
        pageList.addAll(resources);
        return pageList;
    }
}
