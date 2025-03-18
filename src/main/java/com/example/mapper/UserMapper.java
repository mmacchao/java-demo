package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.User;
import org.apache.ibatis.annotations.Param;
public interface UserMapper extends BaseMapper<User> {
    User selectUserById(@Param("id") Integer id);
    User selectUserByName(@Param("username") String username);
    int insertUser(User user);
}