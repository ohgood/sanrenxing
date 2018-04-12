package com.sanrenxing.shop.db.admin.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanrenxing.shop.db.annotation.Table;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created on 2017/2/16.
 *
 * @author tony
 */
@Data
@Table("sys_resource")
@JsonSerialize(using = Resource.ResourceSerializer.class)
public class Resource {

    @NotNull(message = "资源id不可空", groups = {Update.class})
    private Integer id;                            //编号

    @NotNull(message = "资源名称不可空", groups = {Create.class})
    @Length(min = 2, max = 20, message = "资源名称长度2-20", groups = {Create.class, Update.class})
    private String name;                           //资源名称

    @Length(min = 2, max = 100, message = "请求路径长度2-100", groups = {Create.class, Update.class})
    private String url;

    @NotNull(message = "资源类型不可空", groups = {Create.class})
    @Range(max = 2, message = "资源类型取值只能是0，1，2", groups = {Create.class, Update.class})
    private Integer type;                          //资源类型   0: 一级菜单    1： 二级菜单   2： 页面具体功能

    @NotNull(message = "权限表示符不可空", groups = {Create.class})
    @Length(min = 1, max = 30, message = "权限长度1-30", groups = {Create.class, Update.class})
    private String permission;                     //权限字符串

    @NotNull(message = "父权限不可空", groups = {Create.class})
    private Integer parentId;                      //父编号

    static class ResourceSerializer extends JsonSerializer<Resource> {

        @Override
        public void serialize(Resource bean, JsonGenerator jGen, SerializerProvider provider)
                throws IOException {
            jGen.writeStartObject();
            jGen.writeNumberField("id", bean.getId());
            jGen.writeStringField("name", bean.getName());
            jGen.writeStringField("url", bean.getUrl());
            jGen.writeEndObject();
        }

    }

}
