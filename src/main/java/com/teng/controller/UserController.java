package com.teng.controller;


import com.teng.common.ALLResult;
import com.teng.service.UserService;
import com.teng.vo.Loginvo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.teng.common.enums.ResultStatus.*;
@Slf4j


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ALLResult login(@Valid @RequestBody  Loginvo loginvo) {
        ALLResult allResult = new ALLResult();
       // System.out.println(loginvo.toString());
        String token = userService.login(loginvo);
       // allResult.setData(token);
        return  allResult.SUCCESS(RESULT_LOGIN_SUCCESS,token);
    }

}
