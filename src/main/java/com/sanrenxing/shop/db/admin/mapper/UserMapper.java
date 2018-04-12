package com.sanrenxing.shop.db.admin.mapper;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.db.admin.provider.UserProvider;
import com.sanrenxing.shop.db.shop.SqlProvider;
import com.sanrenxing.shop.util.SetTypeHandler;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017/2/24.
 *
 * @author tony
 */
@Component
public interface UserMapper {

    /**
     * 创建用户
     * @param user 用户
     */
    @InsertProvider(type = SqlProvider.class, method = "insert")
    int createUser(User user);

    /**
     * 更新用户
     * @param user 用户
     */
    @UpdateProvider(type = SqlProvider.class, method = "update")
    int updateUser(User user);

    /**
     * 删除用户
     * @param userId 用户id
     */
    @CacheEvict(value = "user", key="'user:' + #userId")
    @Delete("DELETE FROM sys_user WHERE id = #{userId}")
    int deleteUser(Integer userId);

    /**
     * 查询一个用户
     * @param userId 用户id
     * @return 用户
     */
    @Cacheable(value="user", key="'user:' + #userId")
    @Select("SELECT * FROM sys_user WHERE id = #{userId}")
    @Results(value = {
            @Result(column = "role_ids", property = "roleIds", typeHandler = SetTypeHandler.class)
    })
    User findOne(Integer userId);

    /**
     * 批量查询
     * @param user        过滤条件
     * @return            用户列表
     */
    @SelectProvider(type = SqlProvider.class, method = "find")
    @Results(value = {
            @Result(column = "role_ids", property = "roleIds", typeHandler = SetTypeHandler.class)
    })
    List<User> find(User user);

    /**
     * 获取符合条件的数据量
     * @param user        过滤条件
     * @return 符合条件的数据量
     */
    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(User user);

    /**
     * 根据用户名查询用户
     * @param username 用户名称
     * @return 用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    @Results(value = {
            @Result(column = "role_ids", property = "roleIds", typeHandler = SetTypeHandler.class)
    })
    User findByUsername(String username);

    /**
     * 查询用户信息
     * @param roleId      过滤条件：  用户是否锁定
     * @return            符合条件数据量
     */
    @SelectProvider(type = UserProvider.class, method = "findByRoleId")
    @Results(value = {
            @Result(column = "role_ids", property = "roleIds", typeHandler = SetTypeHandler.class)
    })
    List<User> getUsers(@Param("roleId") Integer roleId);


    @Select("SELECT `username` FROM `sys_user`")
    List<String> getUsernameAll();


    @SelectProvider(type = UserProvider.class, method = "getUsernames")
    List<String> getUsernames(@Param("roleIds") List<String> roleIds);


}
