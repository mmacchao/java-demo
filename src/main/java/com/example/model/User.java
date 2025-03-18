package com.example.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")  // 指定表名（若表名与类名不一致时必加）
public class User {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Integer id;
    private String username;
    private String password;
    // getters & setters
}