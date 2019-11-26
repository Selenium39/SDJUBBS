package com.selenium.sdjubbs.common.bean;

import com.selenium.sdjubbs.common.util.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户
 */
@Data
public class User implements Serializable {
    private Integer id;
    @NotBlank(message = Constant.REGISTER_USER_USERNAME_NULL)
    @Length(min = 4, max = 12, message = Constant.REGISTER_USER_USERNAME_LENGTH)
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$", message = Constant.REGISTER_USER_USERNAME_SUPPORT)
    private String username;
    @NotBlank(message = Constant.REGISTER_USER_PASSWORD_NULL)
    private String password;
    private String salt;
    private Integer age;
    private Integer gender;
    @NotBlank(message = Constant.REGISTER_USER_EMAIL_NULL)
    @Email(message = Constant.REGISTER_USER_EMAIL_FORMAT)
    private String email;
    private String phone;
    private String headPicture;
    private String registerTime;
    private String lastLoginTime;
    private Integer status;

}
