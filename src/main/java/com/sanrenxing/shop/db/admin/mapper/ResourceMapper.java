package com.sanrenxing.shop.db.admin.mapper;

import com.sanrenxing.shop.db.admin.bean.Resource;
import com.sanrenxing.shop.db.shop.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017/2/27.
 *
 * @author xuwenjun
 */
@Component
public interface ResourceMapper {

    /**
     * 创建资源
     * @param resource 资源
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int createResource(Resource resource);

    /**
     * 更新资源
     * @param resource 资源
     */
    @UpdateProvider(type = SqlProvider.class, method = "update")
    int updateResource(Resource resource);

    /**
     * 删除资源
     * @param resourceId 资源id
     */
    @Delete("DELETE FROM sys_resource WHERE id = #{resourceId}")
    int deleteResource(Integer resourceId);

    /**
     * 查询一个资源
     * @param resourceId 资源id
     * @return 资源对象
     */
    @Select("SELECT * FROM sys_resource WHERE id = #{resourceId}")
    Resource findOne(Integer resourceId);

    /**
     * 查询一个资源
     * @param permission 权限名称
     * @return 资源对象
     */
    @Select("SELECT * FROM sys_resource WHERE permission = #{permission}")
    Resource findByPermission(String permission);

    /**
     * 资源分页查询
     * @param resource    过滤条件
     * @return            资源集合
     */
    @SelectProvider(type = SqlProvider.class, method = "find")
    List<Resource> find(Resource resource);

    /**
     * 获取符合条件的数据量
     * @return 符合条件的数据量
     */
    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(Resource resource);

}
