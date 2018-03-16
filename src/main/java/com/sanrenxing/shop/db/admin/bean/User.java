package com.sanrenxing.shop.db.admin.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanrenxing.shop.db.annotation.Ignore;
import com.sanrenxing.shop.db.annotation.Table;
import com.sanrenxing.shop.db.group.Create;
import com.sanrenxing.shop.db.group.Update;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * Created on 2017/2/16.
 *
 * @author tony
 */
@JsonSerialize(using = User.CustomSerializer.class)
@Data
@Table("sys_user")
public class User implements Serializable {


    @NotNull(groups = {Update.class})
    private Integer id;                        //编号

    @NotEmpty(groups = {Create.class})
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$", message = "请输入正确的手机号", groups = {Create.class, Update.class})
    private String username;                   //用户名

    @NotEmpty(groups = {Create.class})
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9a-zA-Z_]{6,32}$",
            message = "密码为6到32位字母数字下划线组合，必须包含字母数字", groups = {Create.class, Update.class})
    private String password;                   //密码

    @NotEmpty(groups = {Create.class})
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "真实姓名为2到10位中文", groups = {Create.class, Update.class})
    private String realName;                   //用户真实姓名

    @NotEmpty(groups = {Create.class})
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,4}$", message = "昵称为2到4位中文", groups = {Create.class, Update.class})
    private String nickName;                   //用户昵称

    private String salt;                       //随机密钥

    private Set<Integer> roleIds;              //拥有的角色列表

    private Boolean locked = Boolean.FALSE;    //是否锁定状态

    @Ignore
    private String roles;                      //角色名称 如果用户有多个角色，名称用","分割开

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    static class CustomSerializer extends JsonSerializer<User> {

        @Override
        public void serialize(User user, JsonGenerator jGen, SerializerProvider provider)
                throws IOException {
            jGen.writeStartObject();
            jGen.writeStringField("username", user.username);
            jGen.writeStringField("realName", user.realName);
            jGen.writeStringField("nickName", user.nickName);
            jGen.writeObjectField("roleIds", user.roleIds);
            jGen.writeBooleanField("locked", user.locked);
            jGen.writeEndObject();
        }
    }

}
