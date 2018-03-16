package com.sanrenxing.shop.controller.permission;

import com.sanrenxing.shop.db.admin.bean.Role;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.RoleService;
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
 * Created on 2017/8/9
 * @author tony
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 创建角色
     *
     * @param role  角色
     * @return      请求结果
     */
    @RequestMapping("create")
    public SRXResponse createRole(@Validated({Create.class}) @RequestBody Role role) {
        if (roleService.createRole(role) == 1)
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        else
            return new SRXResponse(SRXResponse.Status.FAILURE);
    }

    /**
     * 更新角色
     * @param role  角色
     * @return      请求结果
     */
    @RequestMapping("update")
    public SRXResponse updateRole(@Validated({Update.class}) @RequestBody Role role) {
        if (role.getId() == null) {
            return new SRXResponse(SRXResponse.Status.REQUEST_PARAMETER_ERROR);
        } else {
            if (roleService.updateRole(role) == 1)
                return new SRXResponse(SRXResponse.Status.SUCCESS);
            else
                return new SRXResponse(SRXResponse.Status.FAILURE);
        }
    }

    /**
     * 删除角色，同时删除用户该角色的用户的该角色
     * @param id   角色id
     * @return     请求结果
     */
    @RequestMapping("delete")
    public SRXResponse deleteRole(@NotNull @RequestParam("id") Integer id) {
        if (roleService.deleteRole(id) == 1) {
            userService.removeUserRole(id);
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        } else {
            return new SRXResponse(SRXResponse.Status.FAILURE);
        }
    }

    /**
     * 查询角色
     * @param pageNum     第几页
     * @param pageSize    页面长度
     * @return            查询结果
     */
    @RequestMapping("find")
    public SRXResponse findRole(@Range(max = 10000) @NotNull @RequestParam("page_num") Integer pageNum,
                                @Range(max = 100) @NotNull @RequestParam("page_size") Integer pageSize) {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(roleService.findByPage(pageNum, pageSize));
    }

}
