package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Param;
public interface UserMapper {
    User selectUserById(@Param("id") Integer id);
    User selectUserByName(@Param("username") String username);
    int insertUser(User user);
}