package com.sanrenxing.shop.db.admin.bean;

import com.sanrenxing.shop.db.annotation.Table;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Data
@Table("sys_role")
public class Role {


    private Integer id; //编号


    @NotNull(message = "角色名称不可空", groups = {Create.class})
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,4}$", message = "昵称为2到4位中文", groups = {Create.class, Update.class})
    private String role; //角色标识 程序中判断使用,如"admin"

    @NotNull(message = "角色描述不可空", groups = {Create.class})
    @Length(max = 32, message = "角色描述信息不超过32个字符", groups = {Create.class, Update.class})
    private String description; //角色描述,UI界面显示使用


    private Set<Integer> resourceIds; //拥有的资源

    public Set<Integer> getResourceIds() {
        if(resourceIds == null) {
            resourceIds = new HashSet<>();
        }
        return resourceIds;
    }

}
