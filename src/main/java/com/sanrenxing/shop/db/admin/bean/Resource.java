package com.sanrenxing.shop.db.admin.bean;

import com.sanrenxing.shop.db.annotation.Table;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Data
@Table("sys_resource")
public class Resource {

    @NotNull(message = "资源id不可空", groups = {Update.class})
    private Integer id;                            //编号

    @NotNull(message = "资源名称不可空", groups = {Create.class})
    @Length(min = 2, max = 20, message = "资源名称长度2-20", groups = {Create.class, Update.class})
    private String name;                           //资源名称

    @NotNull(message = "资源类型不可空", groups = {Create.class})
    @Range(max = 2, message = "资源类型取值只能是0，1，2", groups = {Create.class, Update.class})
    private Integer type;                          //资源类型   0: 一级菜单    1： 二级菜单   2： 页面具体功能

    @NotNull(message = "权限表示符不可空", groups = {Create.class})
    @Length(min = 1, max = 30, message = "权限长度1-30", groups = {Create.class, Update.class})
    private String permission;                     //权限字符串

    @NotNull(message = "父权限不可空", groups = {Create.class})
    private Integer parentId;                      //父编号

    private String fullPermission;


}
