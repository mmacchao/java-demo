package com.example.bean;

// 响应结果包装类
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
    // 全参构造器 + getters
    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}