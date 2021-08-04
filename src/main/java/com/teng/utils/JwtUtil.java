package com.teng.utils;

import com.teng.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    //设置过期时间30分钟
    // public static final long EXPIRE_DATE= 30 * 60 * 1000;

    //这个是一分钟的时间注意1 * 60 * 1000

    public static final long EXPIRE_DATE= 10 * 60 * 1000;//10分钟
    // private static final long EXPIRE_DATE=30*60*100000;

    //token秘钥
    private static final String TOKEN_SECRET = "aaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccc";


    /**
     * 从token中获取claim
     *
     * @param token token
     * @return claim
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();

    }

    /**
     * 获取token的过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public static  Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token)
                .getExpiration();
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return 已过期返回true，未过期返回false
     */
    public static Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 计算token的过期时间
     *key
     * @return 过期时间
     */
    public static Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + EXPIRE_DATE );
    }

    /**
     * 为指定用户生成token
     *
     * @param claims 用户信息
     * @return token
     */
    public static String generateToken(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = getExpirationTime();


        byte[] keyBytes = TOKEN_SECRET.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

       // System.out.println("key=" + key.toString());

        return Jwts.builder()

                .setClaims(claims)//payload
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)//过期时间
                // 你也可以改用你喜欢的算法
                .signWith(key, SignatureAlgorithm.HS256)//header
                .compact();
    }
    public static String generateToken(Claims claims) {
        Date createdTime = new Date();
        Date expirationTime = getExpirationTime();


        byte[] keyBytes = TOKEN_SECRET.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        System.out.println("key=" + key.toString());
        return Jwts.builder()
                .setClaims(claims)//payload
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)//过期时间
                // 你也可以改用你喜欢的算法
                .signWith(key, SignatureAlgorithm.HS256)//header
                .compact();
    }

    /**
     * 判断token是否非法
     *
     * @param token token
     * @return 未过期返回true，否则返回false
     */
    public static Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",123);
        map.put("userName","灰狼");
        map.put("admin",true);

        User user = new User();
        user.setUsername("ceshi");
        user.setPassword("ceshi");



        String token1 = JwtUtil.generateToken(map);
        Claims claimsFromToken = JwtUtil.getClaimsFromToken(token1);

        System.out.println("过期时间："+claimsFromToken.getExpiration());

        System.out.println("解析过后的token1："+claimsFromToken);
        System.out.println(token1);


        try {
          /*  Boolean aBoolean1 = JwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJhZG1pbiI6dHJ1ZSwidXNlck5hbWUiOiLngbDlpKrni7wiLCJ1c2VySWQiOjEyMywiaWF0IjoxNjI1NjQ4OTUzLCJleHAiOjE2MjU2NDkwMTN9.BfEi6zs-vyjMgST5bhKPSCPUIEAiuC-VqOmtZ_w1xXw");
            System.out.println("token没有异常的:" + aBoolean1);*/
            Boolean aBoolean =
                    JwtUtil.
                            validateToken("eyJhbGciOiJIUzI1NiJ9.eyJhZG1pbiI6dHJ1ZSwidXNlck5hbWUiOiLngbDni7wiLCJ1c2VySWQiOjEyMywiaWF0IjoxNjI3ODAzMzU5LCJleHAiOjE2Mjc4MDM0MTl9.fco1IuEK8Bl1JRy-lHUCXvbdKKofXZti-7B3n2KYaqk" );
            //Boolean aBoolean = JwtUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJhZG1pbiI6dHJ1ZSwidXNlck5hbWUiOiLngbDlpKrni7wiLCJ1c2VySWQiOjEyMywiaWF0IjoxNjI1NjQ4OTUzLCJleHAiOjE2MjU2NDkwMTN9.BfEi6zs-vyjMgST5bhKPSCPUIEAiuC-VqOmtZ_w1xXw");
            System.out.println("token1:"+aBoolean);
        } catch (ExpiredJwtException e
        ) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e.getClaims().get("userId"));
        }


    }
}
