package com.teng;

import com.alibaba.fastjson.JSON;
import com.teng.dao.mapper.UserMapper;
import com.teng.entity.User;
import com.teng.exception.GlobleException;
import com.teng.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.teng.common.enums.ResultStatus.*;

@SpringBootTest
class Miaosha01ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtils;

    @Test
    public void testcatch(){
        try {
            File f=new File("There is a garbage in your garbage computer.garbage");
            new FileInputStream(f).read();
        } catch (IOException e) {
            System.out.println("chtch:异常");
            e.printStackTrace();
            throw new GlobleException(RESULT_SYSTEM_ERROR);

        }
    }


    @Test
    public void testredis(){

//        redisUtils.set("ceshi", "ceshi");
//        System.out.println(redisUtils.get("ceshi"));
//        User user = userMapper.selectById(1420633815047454721L);
//
//        //序列化
//        redisUtils.set("user", SerializeUtil.serialize(user));
//
//        User user1 = (User) SerializeUtil.unSerialize((byte[]) redisUtils.get("user"));
//        System.out.println("user"+redisUtils.get("user"));
//        System.out.println("user1"+user1);
        User user = userMapper.selectById(1420633815047454721L);
        String s = JSON.toJSONString(user);
        System.out.println(JSON.toJSONString(user));

        User user1 = JSON.parseObject(s, User.class);
        System.out.println(JSON.parseObject(s, User.class));


        redisUtils.set("usertest", s);
        redisUtils.get("usertest");


    }


    @Test
    void insertUser() {
        User user = new User();

        user.setUsername("admin");
        user.setPassword("123");

        userMapper.insert(user);

    }

    @Test
    void test() throws Exception {


        if (true) {
            throw new Exception("参数越界");
        }
        System.out.println("异常后"); //抛出异常，不会执行
       /* try{
            throw new Exception("参数越界");
        }catch(Exception e) {

            e.printStackTrace();
        }
        System.out.println("异常后");//可以执行
    }*/

    }
}
  /*  public static void test() throws Exception  {

        throw new Exception("参数越界");
        System.out.println("异常后"); //编译错误，「无法访问的语句」
    }*/