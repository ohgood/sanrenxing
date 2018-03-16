package com.sanrenxing.shop.service;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.util.PageList;

import java.util.Set;

/**
 * Created on 2017/2/23.
 *
 * @author tony
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param user  user对象
     * @return   注册后用户
     */
    int createUser(User user);

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPassword 新密码
     */
    void changePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 更新用户
     * @param user 用户
     * @return     1： 更新成功 0：用户不存在或更新失败
     */
    int updateUser(User user);

    /**
     * 更新用户-角色关系
     * @param userId    用户id
     * @param roleIds   新的用户规则
     */
    boolean updateRoles(Integer userId, String username, String password, String realName, String nickName, Set<Integer> roleIds);

    /**
     * 锁定／解锁 用户
     * @param userId   用户id
     * @param locked   true： 锁定    false： 解锁
     */
    boolean lockUser(Integer userId, Boolean locked);

    /**
     * 根据用户名查找用户
     * @param username 用户名称
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username 用户名称
     * @return 角色名称
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username 用户名称
     * @return 权限名称
     */
    Set<String> findPermissions(String username);

    /**
     * 删除一个用户
     * @param id 用户id
     */
    void deleteUser(Integer id);

    /**
     * 用户分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @param locked       过滤条件： 用户是否锁定
     * @return             用户对象集合
     */
    PageList findByPage(int currPage, int pageSize, Boolean locked);

    /**
     * 删除所有拥有指定角色的用户的该角色

     */
    void removeUserRole(Integer roleId);

}