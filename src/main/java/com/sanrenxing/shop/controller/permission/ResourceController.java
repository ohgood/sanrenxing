package com.sanrenxing.shop.controller.permission;

import com.sanrenxing.shop.db.admin.bean.Resource;
import com.sanrenxing.shop.rest.SRXResponse;
import com.sanrenxing.shop.service.ResourceService;
import com.sanrenxing.shop.service.RoleService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created on 2017/8/9
 * @author tony
 */
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    /**
     * 创建资源
     *
     * @param resource  资源
     * @return          请求结果
     */
    @RequestMapping("create")
    public SRXResponse createResource(@Valid @RequestBody Resource resource) {
        if (resourceService.createResource(resource) == 1)
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        else
            return new SRXResponse(SRXResponse.Status.FAILURE);
    }






    /**
     * 更新资源
     * @param resource  资源
     * @return          请求结果
     */
    @RequestMapping("update")
    public SRXResponse updateResource(@Valid @RequestBody Resource resource) {
        if ((resource.getPermission() == null && resource.getParentId() != null) ||
                (resource.getPermission() != null && resource.getParentId() == null)) {
            return new SRXResponse(SRXResponse.Status.REQUEST_PARAMETER_ERROR).message("权限表示符合和父权限id只能同时为空或者同时不为空");
        } else {
            if (resourceService.updateResource(resource) == 1)
                return new SRXResponse(SRXResponse.Status.SUCCESS);
            else
                return new SRXResponse(SRXResponse.Status.FAILURE);
        }
    }

    /**
     * 删除资源，同时删除拥有该资源的用户的该资源
     * @param id   资源id
     * @return     请求结果
     */
    @RequestMapping("delete")
    public SRXResponse deleteResource(@RequestParam("id") Integer id) {
        if (resourceService.deleteResource(id) == 1) {
            roleService.removeRoleResource(id);
            return new SRXResponse(SRXResponse.Status.SUCCESS);
        } else {
            return new SRXResponse(SRXResponse.Status.FAILURE);
        }
    }


    /**
     * 查询资源
     * @param pageNum     第几页
     * @param pageSize    页面长度
     * @param type        类型
     * @return            查询结果
     */
    @RequestMapping("find")
    public SRXResponse findSource(@Range(max = 10000) @NotNull @RequestParam("page_num") Integer pageNum,
                                  @Range(max = 100) @NotNull @RequestParam("page_size") Integer pageSize,
                                  @RequestParam("type") Integer type) {
        return new SRXResponse(SRXResponse.Status.SUCCESS).result(resourceService.findByPage(pageNum, pageSize, type));
    }


}
