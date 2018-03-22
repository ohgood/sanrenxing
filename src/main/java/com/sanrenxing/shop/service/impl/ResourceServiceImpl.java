package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.controller.dto.ResourceDTO;
import com.sanrenxing.shop.db.admin.bean.Resource;
import com.sanrenxing.shop.db.admin.dao.ResourceDao;
import com.sanrenxing.shop.service.ResourceService;
import com.sanrenxing.shop.util.PageList;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2017/2/28.
 * @author tony
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    /**
     * 创建一个资源
     *
     * @param resource  资源对象
     * @return          0: 创建失败  1： 创建成功
     */
    @Override
    public int createResource(Resource resource) {
        String fullPermission = formatFullPermission(resource.getPermission() + ":*", resource.getParentId());
        if (fullPermission != null) {
            resource.setFullPermission(fullPermission);
            return resourceDao.createResource(resource);
        } else {
            return 0;
        }
    }

    /**
     * 格式化资源
     * @param fullPermission 当前的完整权限表示符
     * @param resourceId     父权限id
     * @return               最终的完整权限表示符
     */
    private String formatFullPermission(String fullPermission, Integer resourceId) {
        if (resourceId == 0) {
            return fullPermission;
        } else {
            Resource parentResource = findOne(resourceId);
            if (parentResource == null) {
                return null;
            } else {
                return formatFullPermission(parentResource.getPermission() + ":" + fullPermission, parentResource.getParentId());
            }
        }
    }

    /**
     * 更新一个资源
     *
     * @param resource  资源对象
     * @return 资源
     */
    @Override
    public int updateResource(Resource resource) {
        if (resource.getParentId() != null) {
            String fullPermission = formatFullPermission(resource.getPermission() + ":*", resource.getParentId());
            if (fullPermission != null) {
                resource.setFullPermission(fullPermission);
                return resourceDao.updateResource(resource);
            } else {
                return 0;
            }
        }
        return resourceDao.updateResource(resource);
    }

    /**
     * 删除一个资源
     * @param resourceId 资源id
     */
    @Override
    public int deleteResource(Integer resourceId) {
        return resourceDao.deleteResource(resourceId);
    }

    /**
     * 查询一个资源
     * @param resourceId 资源id
     * @return 资源
     */
    @Override
    public Resource findOne(Integer resourceId) {
        return resourceDao.findOne(resourceId);
    }

    /**
     * 查询一个资源
     * @param permission 权限字符串
     * @return 资源
     */
    @Override
    public Resource findByPermission(String permission) {
        return resourceDao.findByPermission(permission);
    }

    /**
     * 查询所有资源
     * @return 所有资源
     */
    @Override
    public List<Resource> findAll() {
        return resourceDao.findByPage(1, 0, null);
    }

    /**
     * 资源分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @param type         过滤条件： 资源类型
     * @return             用户对象集合
     */
    @Override
    public PageList findByPage(int currPage, int pageSize, Integer type) {
        return resourceDao.findByPage(currPage, pageSize, type);
    }

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds 资源id
     * @return 权限set
     */
    @Override
    public Set<String> findPermissions(Set<Integer> resourceIds) {
        List<Resource> resources = resourceDao.findByPage(1, 0, null);
        if (resources != null) {
            return resources.stream().filter(a -> resourceIds.contains(a.getId()))
                    .map(Resource::getFullPermission).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    /**
     * 根据用户权限得到菜单
     * @param permissions 权限set
     * @return menus
     */
    @Override
    public List<ResourceDTO> findMenus(Set<String> permissions) {
        List<Resource> allResources = findAll();
        List<ResourceDTO> menus = new ArrayList<>();
        allResources.stream().filter(a -> hasPermission(permissions, a) && Objects.equals(a.getType(), 0))
                .forEach(b -> {
                    ResourceDTO bean = new ResourceDTO();
                    bean.setResource(b);
                    menus.add(bean);
                });
        allResources.stream().filter(a -> hasPermission(permissions, a) && Objects.equals(a.getType(), 1))
                .forEach(b -> {
                    for(ResourceDTO bean : menus) {
                        if (Objects.equals(bean.getResource().getId(), b.getParentId())) {
                            bean.addChildResource(b);
                            break;
                        }
                    }
                });
        //Collections.sort(menus);
        //menus.forEach(a -> Collections.sort(a.getChildResource()));
        return menus;
    }

    /**
     * 权限校验
     * @param permissions 权限名称
     * @param resource 资源
     * @return true: 包含权限  false: 不包含权限
     */
    @Override
    public boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }



}