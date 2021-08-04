package com.teng.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teng.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository//你要的操作去baseMapper里面看
public interface UserMapper extends  BaseMapper<User> {


    @Select("select * from user where username=#{username}")
    User selectByusername(@Param("username") String username);

}
