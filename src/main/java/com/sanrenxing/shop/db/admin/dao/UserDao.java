package com.sanrenxing.shop.db.admin.dao;

import com.github.pagehelper.PageHelper;
import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.db.admin.mapper.UserMapper;
import com.sanrenxing.shop.helper.RedisConnector;
import com.sanrenxing.shop.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2017/2/24.
 * @author tony
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisConnector redisConnector;

    public int createUser(User user) {
        return userMapper.createUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }

    public User findOne(Integer userId) {
        User user = userMapper.findOne(userId);
        redisConnector.set(userId.toString(), user);
        System.out.println(((User) redisConnector.get(userId.toString())).getUsername() + "==================================================================");
        return user;
    }

    /**
     * 用户分页查询
     * @param currPage     查询页
     * @param pageSize     查询数据量
     * @param locked       过滤条件： 用户是否锁定
     * @return             用户对象集合
     */
    public PageList<User> findByPage(int currPage, int pageSize, Boolean locked) {
        PageHelper.startPage(currPage, pageSize);
        User user = new User();
        user.setLocked(locked);
        List<User> users = userMapper.find(user);
        PageHelper.clearPage();
        PageList<User> pageList = new PageList<>(currPage, pageSize, userMapper.count(user));
        pageList.addAll(users);
        return pageList;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public List<User> getUsers (Integer roleId) {
        return userMapper.getUsers(roleId);
    }

    public void removeUserRole(Integer roleId) {
        List<User> user = getUsers(roleId);
        user.forEach(a -> {
            a.getRoleIds().remove(roleId);
            updateUser(a);
        });
    }

}
