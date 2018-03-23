package com.sanrenxing.shop.controller.permission;

import com.sanrenxing.shop.db.admin.bean.User;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created on 2017/8/8
 * @author tony
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户查询接口
     * @param pageNum     第几页
     * @param pageSize    每页数量
     * @return            查询到的用户数据
     */
    @RequestMapping("/find")
    public SRXResponse findUsers(@Range(min = 1, max = 10000) @NotNull @RequestParam("page_num") Integer pageNum,
                                 @Range(max = 100) @NotNull @RequestParam("page_size") Integer pageSize) {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(userService.findByPage(pageNum, pageSize, null));
    }

    /**
     * 用户更新／代注册   如果User对象id字段有值，执行更新操作；没有值，执行注册操作
     *
     * @param user  User对象
     * @return      返回更新／代注册结果
     */
    @RequestMapping("/create")
    public SRXResponse createUser(@Validated({Create.class}) @RequestBody User user) {
        if (userService.createUser(user) == 1)
            return new SRXResponse(SRXResponse.Status.SUCCESS).result("用户注册成功");
        else
            return new SRXResponse(SRXResponse.Status.FAILURE).result("用户已经存在");
    }

    @RequestMapping("/update")
    public SRXResponse updateUser(@Validated({Update.class}) @RequestBody User user) {
        if (userService.updateUser(user) == 1)
            return new SRXResponse(SRXResponse.Status.SUCCESS).result("用户更新成功");
        else
            return new SRXResponse(SRXResponse.Status.FAILURE).result("用户更新失败或用户不存在");
    }

    /**
     * 锁定解锁用户
     *
     * @param id            用户id
     * @param locked        true： 锁定    false： 解锁
     * @return              操作结果
     */
    @RequestMapping("/lock")
    public SRXResponse forbiddenUser(@NotNull @RequestParam("id") Integer id,
                                     @NotNull @RequestParam("locked") Boolean locked) {
        if (userService.lockUser(id, locked))
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        else
            return new SRXResponse(SRXResponse.Status.FAILURE);
    }


    /**
     * 根据用户名查找用户
     * @param username 用户名称
     * @return 用户对象
     */
    @RequestMapping("/findByUsername")
    public SRXResponse findByUsername(String username) {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(userService.findByUsername(username));
    }

}
