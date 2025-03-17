package com.example.controller;

import com.example.bean.ApiResponse;
import com.example.bean.LoginRequest;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
@Controller
public class Login {
    private final Gson gson = new Gson();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/base";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ApiResponse> doPost(@RequestBody User loginRequest)
            throws IOException {
        ApiResponse apiResponse;
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null || "".equals(loginRequest.getPassword()) || "".equals(loginRequest.getUsername())) {
            apiResponse = new ApiResponse(400, "用户名和密码不能为空", null);
        } else {
            // 初始化 SqlSessionFactory
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            // 执行 SQL
            try (SqlSession session = sqlSessionFactory.openSession()) {
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = mapper.selectUserByName(loginRequest.getUsername());
                if (user != null) {
                    if (user.getPassword().equals(loginRequest.getPassword())) {
                        apiResponse = new ApiResponse(200, "欢迎回来", null);
                    } else {
                        apiResponse = new ApiResponse(400, "密码错误", null);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
                    }
                } else {
                    // 插入用户
                    int id = mapper.insertUser(loginRequest);
                    session.commit();
                    session.close();
                    HashMap data = new HashMap();
                    data.put("id", id);
                    apiResponse = new ApiResponse(200, "注册成功", data);
                }
            }
        }
        return ResponseEntity.ok(apiResponse);
    }

    // 获取自增 ID（可选）
    private static int getLastInsertId(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        }
    }

    // 读取请求体工具方法
    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }
}
