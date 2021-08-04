package com.teng.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loginvo {
    @NotNull(message = "用户不为空  是")//不为空  但是可以“”是这个空格之类的
    private String username;

   /* @NotNull(message = "手机号不为空")//不为空
    private String tel;*/

    @NotNull(message = "密码不为空")//不为空
    private String password;

}
