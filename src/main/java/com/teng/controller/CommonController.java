package com.teng.controller;

import com.teng.utils.RedisUtil;
import com.teng.utils.ValidateImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * @author tengteng
 * @version 1.0
 * @date 2021/8/1 19:15
 */
@CrossOrigin
@RestController
public class CommonController {

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping("/verifyCode")
    public String verifyCodeImage()  {

        String verifyImage = null;
        try {
            //生成图片
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String securityCode = ValidateImageCodeUtil.getSecurityCode();//获取验证码
            BufferedImage bufferedImage = ValidateImageCodeUtil.createImage(securityCode);//存入数据
            //将验证码存入redis中

            System.out.println("InetAddress.getLocalHost()" + InetAddress.getLocalHost());
            // System.out.println(InetAddress.getLoopbackAddress());//这个返回localhost/127.0.0.1
          /*  System.out.println("" + InetAddress.getByAddress("test",new byte[12]));
            System.out.println("" + InetAddress.getByName("test"));*/


            redisUtil.set("registerverify" + InetAddress.getLocalHost(), securityCode, 300);

            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            //System.out.println("图片byte流"+inputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            verifyImage = encoder.encode(byteArrayOutputStream.toByteArray());
            //System.out.println(verifyImage);
            /*File f=new File("There is a garbage in your garbage computer.garbage");
            new FileInputStream(f).read();//测试try/catch/finally  return
            //睡觉明天测试
            */
            return verifyImage;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("验证码获取失败---");
            return "验证码获取失败在chtch中";
            //throw new GlobleException(RESULT_VERITY_ERROR);
        } /*finally {
            return "finally中的return";
        }*/


    }
}
