package com.teng.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author tengteng
 * @version 1.0
 * @date 2021/8/2 20:55
 */
public class MD5Util {
    public static void main(String[] args) {
        //https://blog.csdn.net/weixin_44634197/article/details/108404580?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-0.control&spm=1001.2101.3001.4242


        String s = DigestUtils.md5Hex("12345");
        System.out.println(s);
        String s1 = DigestUtils.md5Hex("12 345");
        System.out.println(s1);
        //使用指定盐值生成MD5
        String salt1 = Md5Crypt.md5Crypt("12345".getBytes(), "$1$asd");
        System.out.println(salt1);
        String salt2 = Md5Crypt.md5Crypt("12345".getBytes(), "$1$asdf");
        System.out.println(salt2);
        System.out.println("================================");



        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用encode方法对原密码进行加密,每次加密可以产生不同的结果
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        String encode1 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        //使用用户提交的原密码和数据库中的encode对比
        boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
        boolean matches1 = passwordEncoder.matches("123456", encode1);
        System.out.println(matches1);

    }



}
