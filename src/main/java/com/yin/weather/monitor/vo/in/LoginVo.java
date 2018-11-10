package com.yin.weather.monitor.vo.in;

import com.yin.weather.monitor.common.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 登录VO
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Setter
@Getter
public class LoginVo extends BaseVo {

    @NotBlank(message = "账号不能为空")
    @Length(min = 1, max = 32, message = "账号长度为1到32")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 1, max = 32, message = "密码长度为1到32")
    private String password;
}
