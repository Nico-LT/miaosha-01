package com.teng.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teng.dao.mapper.UserMapper;
import com.teng.entity.User;
import com.teng.exception.GlobleException;
import com.teng.utils.BCryptUtil;
import com.teng.utils.JwtUtil;
import com.teng.utils.RedisUtil;
import com.teng.vo.Loginvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static com.teng.common.enums.ResultStatus.*;
@Service
public class UserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    public String login(Loginvo loginvo) {
        if (loginvo == null) {
            System.out.println("按理来说是能传过来的  如果传不过来系统错误");
            throw new GlobleException(RESULT_SYSTEM_ERROR);
        }
        String username = loginvo.getUsername();//获取用户名
        String password = loginvo.getPassword();//获取密码 用户端输入的密码
        User user=getUserByusername(username);//1.先判断redis中 是否有数据 2.再去数据库里面查数据  3.是否有这个用户
        if (user == null) {
            //根据用户名查询  不存在
            System.out.println("用户名不存在");
            throw new GlobleException(RESULT_LOGIN_NOUSER);
        }
        String doublePass = user.getPassword();//获取用户数据库中的密码
        boolean matches = BCryptUtil.matches(password, doublePass);//
        //md5指令  String saltDb = user.getSalt();

        if (!matches) {
            System.out.println("密码错误");
            throw new GlobleException(RESULT_LOGIN_PW_ERROR);
        }
        //生成token返回前端

           // String token = UUIDUtil.uuid();
        Map<String, Object> map = new HashMap<>();
        String s = JSON.toJSONString(user);
        map.put("user", s);


        //设置token过期时间为10分钟
        //redis过期时间为20分钟
        String token = JwtUtil.generateToken(map);

        System.out.println(JwtUtil.getExpirationDateFromToken(token));
        //System.out.println(System.currentTimeMillis() - JwtUtil.getExpirationDateFromToken(token));

        addTokentoRedis(token,user);
            return token;

    }
    public User getUserByusername(String username) {
        //取缓存
        String o = (String) redisUtil.get("username:" + username);

        //转化
        User user = JSON.parseObject(o, User.class);
        if (user != null) {
            return user;
        }
        //redis中没有数据
        //向数据库中获取数据
        user = userMapper.selectByusername(username);

        if (user != null) {//user不为空 存数据
            //向redis中存入数据
            String userString = JSON.toJSONString(user);//转化为json存入
            redisUtil.set("username:" + username, userString);
        }
        return user;
    }

    public boolean checkVerify(String verifycode) {
        try {
        String redisverify = (String) redisUtil.get("registerverify"+InetAddress.getLocalHost());

        if (redisverify==null||!redisverify.equalsIgnoreCase(verifycode)) {//不区分大小写
            return false;
        }


            redisUtil.del("registerverify"+ InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.out.println("获取本地ip地址失败");
            e.printStackTrace();
        }

        return true;
    }

    public void addTokentoRedis(String token, User user) {
        String s = JSON.toJSONString(user);

        int TOKEN_EXPIRE = 60 *20;//20分钟 单位是秒
        //
        redisUtil.set("doubleusername:"+token, s, TOKEN_EXPIRE);

    }

    public boolean register(User user) {
        //String encryption_password = BCryptUtil.encryption(user.getPassword());
        user.setPassword(BCryptUtil.encryption(user.getPassword()));//加密密码

        try {

            int insert = userMapper.insert(user);
           // System.out.println("insert返回数字"+insert);//int 为收影响行数
            if (insert == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("插入异常 注册失败");
            e.printStackTrace();
            throw new GlobleException(RESULT_REGISTER_ERROR);
        }
        return true;
    }
    public void getUserByTel(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("tel", user.getTel());

        User user0 = new User();
        user0.setTel(user.getTel());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("tel", user.getTel());
        User user1 = userMapper.selectOne(userQueryWrapper);

        if (user1 != null) {
            throw new GlobleException(RESULT_HASUSER);
        }else{
            //输出该用户
            System.out.println(user1);
        }
    }
}
