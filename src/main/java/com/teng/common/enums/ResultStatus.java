package com.teng.common.enums;

/**
 * 返回数据控制
 */
public enum ResultStatus {
    RESULT_SUCCESS(0, "成功"),
    RESULT_HASUSER(-2, "手机号码已注册"),
    RESULT_VERIFY_EXPIRATION(-3,"验证码过期，请点击验证码刷新！"),
    RESULT_REGISTER_SUCCESS(11, "注册成功"),
    RESULT_REGISTER_ERROR(-11, "注册失败"),
    RESULT_REGISTERVERIFY_ERROR(-12, "验证码错误"),

    RESULT_ERROR(-1,"操作失败"),
    RESULT_STATUS(1,""),
    RESULT_SYSTEM_ERROR(10001, "系统错误"),

    //登录
    RESULT_LOGIN_SUCCESS(100, "登录成功"),
    RESULT_LOGIN_ERROR(-100, "登录失败"),

    RESULT_LOGIN_NOUSER(-101, "用户不存在"),
    RESULT_LOGIN_PW_ERROR(-102, "密码错误"),
    RESULT_LOGIN_INPUT_ERROR(-103, "输入有误"),
    RESULT_TOKEN_ERROR(10000, "token失效"),


    RESULT_VERITY_ERROR(-10000,"获取验证码失败"),

    RESULT_REDIS_ERROR(-100001,"redis没有连接或异常")

    ;

    private int code;
    private String message;

   private ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
