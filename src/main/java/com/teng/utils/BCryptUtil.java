package com.teng.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author tengteng
 * @version 1.0
 * @date 2021/8/2 21:43
 */

public class BCryptUtil {


    private  static  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    //加密
    public static String encryption(String password) {
        return passwordEncoder.encode(password);
    }

    //没有解密这一说
    public static boolean matches(String rawpassword,String encode){

        return passwordEncoder.matches(rawpassword, encode);

    }

}
