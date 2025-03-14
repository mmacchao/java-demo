package com.example.bean;

// 请求参数映射类
public class LoginRequest {
    private String password;
    private String username;
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


    // getters & setters 必须提供（否则Gson无法解析）
}

