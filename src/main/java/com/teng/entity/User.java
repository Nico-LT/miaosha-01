package com.teng.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor//idea左边可以看  1.点击Structure
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String tel;

    //mybatisplus  插入
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version//乐观所  根据版本号
    private int version;

    @TableLogic//逻辑删除  注解  deleed删除==1
    private int deleted;

}
