package com.sanrenxing.shop.service.impl;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.db.admin.dao.UserDao;
import com.sanrenxing.shop.service.RoleService;
import com.sanrenxing.shop.service.UserService;
import com.sanrenxing.shop.util.PageList;
import com.sanrenxing.shop.util.PasswordHelper;
import com.sanrenxing.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 2017/2/23.
 * @author tony
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 创建用户
     *
     * @param user  User对象
     * @return      1： 注册成功   0： 账号已经存在
     */
    @Override
    public int createUser(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return 0;
        }
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPassword 新密码
     */
    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userDao.findOne(userId);
        String pwd = passwordHelper.encryptPassword(user.getUsername(), oldPassword, user.getSalt());
        if (pwd.equals(user.getPassword())) {
            user.setPassword(newPassword);
            passwordHelper.encryptPassword(user);
            userDao.updateUser(user);
        }
    }

    @Override
    public int updateUser(User user) {
        if (!StringUtil.isNullOrEmpty(user.getPassword()))
            passwordHelper.encryptPassword(user);
        return userDao.updateUser(user);
    }

    /**
     * 更新用户-角色关系
     * @param userId    用户id
     * @param roleIds   新的用户规则
     */
    @Override
    public boolean updateRoles(Integer userId, String username, String password, String realName, String nickName, Set<Integer> roleIds) {
        User user = userDao.findOne(userId);
        if(user != null) {
            user.setUsername(username);
            user.setRealName(realName);
            user.setNickName(nickName);
            user.setRoleIds(roleIds);
            if (!Objects.equals(password, "")) {
                user.setPassword(password);
                passwordHelper.encryptPassword(user);
            }

            return userDao.updateUser(user) > 0;
        }
        return false;
    }

    /**
     * 锁定／解锁 用户
     * @param userId   用户id
     * @param locked   true： 锁定    false： 解锁
     */
    @Override
    public boolean lockUser(Integer userId, Boolean locked) {

        User user = userDao.findOne(userId);
        if(user != null) {
            user.setLocked(locked);
            return userDao.updateUser(user) > 0;
        }
        return false;
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名称
     * @return 用户对象
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username 用户名称
     * @return 角色名称
     */
    @Override
    public Set<String> findRoles(String username) {
        User user = findByUsername(username);
        if(user == null) {
            return new HashSet<>();
        }
        return roleService.findRoles(user.getRoleIds().toArray(new Integer[0]));
    }

    /**
     * 根据用户名查找其权限
     * @param username 用户名称
     * @return 权限名称
     */
    @Override
    public Set<String> findPermissions(String username) {
        User user = findByUsername(username);
        if(user == null) {
            return new HashSet<>();
        }
        return roleService.findPermissions(user.getRoleIds().toArray(new Integer[user.getRoleIds().size()]));
    }

    /**
     * 删除一个用户
     * @param id 用户id
     */
    @Override
    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    /**
     * 用户分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @param locked       过滤条件： 用户是否锁定
     * @return             用户对象集合
     */
    @Override
    public PageList findByPage(int currPage, int pageSize, Boolean locked) {
        PageList<User> users = userDao.findByPage(currPage, pageSize, locked);
        users.forEach(user -> user.setRoles(StringUtil.join(findRoles(user.getUsername()), ",")));
        return users;
    }


    /**
     * 删除所有拥有指定角色的用户的该角色
     */
    @Override
    public void removeUserRole(Integer roleId) {
        userDao.removeUserRole(roleId);
    }

}