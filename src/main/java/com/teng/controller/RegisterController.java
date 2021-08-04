package com.teng.controller;


import com.teng.common.ALLResult;
import com.teng.dao.mapper.UserMapper;
import com.teng.entity.User;
import com.teng.exception.GlobleException;
import com.teng.service.UserService;
import com.teng.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.teng.common.enums.ResultStatus.*;

@CrossOrigin
@RestController
public class RegisterController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/register")
    public ALLResult register(@RequestParam("verifycode") String verifycode, @RequestBody User user) throws UnknownHostException {//

        ALLResult allResult = new ALLResult();//全局异常

        System.out.println(user.toString());

        String redisverify = (String) redisUtil.get("registerverify"+ InetAddress.getLocalHost());

        if (redisverify == null || redisverify == "") {
            throw new GlobleException(RESULT_VERIFY_EXPIRATION);
        }

        System.out.println("redisverify:"+redisverify);
        System.out.println("verifycode:"+verifycode);

        //
        boolean b = userService.checkVerify(verifycode);//验证码是否正确
        if (!b) {//验证码错误
            return allResult.ERROR(RESULT_REGISTERVERIFY_ERROR);
        }
        userService.getUserByTel(user);//判断用户是否存在
        boolean register = userService.register(user);//插入数据
        if (!register) {//注册失败
            return allResult.ERROR(RESULT_REGISTER_ERROR);
        }
        return allResult.SUCCESS(RESULT_REGISTER_SUCCESS);
    }

}
