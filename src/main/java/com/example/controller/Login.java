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

public class Login extends HttpServlet {
    private final Gson gson = new Gson();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/base";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 统一设置请求/响应编码和Content-Type
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            ApiResponse apiResponse;
            try {
                // 1. 读取请求体中的JSON数据
                String jsonBody = readRequestBody(request);

                // 2. 将JSON解析为Java对象
                User loginRequest = gson.fromJson(jsonBody, User.class);
                String password = loginRequest.getPassword();

                // 3. 校验必要参数是否存在
                if (loginRequest.getUsername() == null || loginRequest.getPassword() == null || "".equals(loginRequest.getPassword()) || "".equals(loginRequest.getUsername())) {
                    apiResponse = new ApiResponse(400, "用户名和密码不能为空", null);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                   /* // 3. 查询数据库
                    String sql = "SELECT * FROM user WHERE username = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, loginRequest.getUsername());
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                // 4. 比对密码
                                String storedPassword = rs.getString("password_hash");
                                if (password.equals(storedPassword)) {
//                                    result.put("code", 200);
//                                    result.put("message", "登录成功");
                                    apiResponse = new ApiResponse(200, "欢迎回来", null);
                                } else {
                                    apiResponse = new ApiResponse(400, "密码错误或用户名已存在", null);
                                }
                            } else {
                                // 未注册，注册
                                String inserSql = "INSERT INTO user (username, password_hash) VALUES (?, ?)";
                                PreparedStatement stmt2 = conn.prepareStatement(inserSql);
                                stmt2.setString(1, loginRequest.getUsername());
                                stmt2.setString(2, loginRequest.getPassword());
                                // 执行插入操作
                                int affectedRows = stmt2.executeUpdate();
                                HashMap data = new HashMap();
                                if (affectedRows > 0) {
                                    System.out.println("插入成功！新用户 ID: " + getLastInsertId(conn));
                                    data.put("id", getLastInsertId(conn));
                                }
                                apiResponse = new ApiResponse(200, "注册成功", data);
                            }
                        }
                    }*/

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

                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (JsonSyntaxException e) {
                // JSON解析失败
                apiResponse = new ApiResponse(400, "无效的JSON格式", null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (Exception e) {
                // 其他未知异常
                apiResponse = new ApiResponse(500, "服务器内部错误", null);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }

            // 5. 将响应对象序列化为JSON
            response.getWriter().write(gson.toJson(apiResponse));

        } catch (SQLException e) {
            // 数据库异常
            ApiResponse apiResponse = new ApiResponse(400, "数据库异常", null);
            // 5. 将响应对象序列化为JSON
            response.getWriter().write(gson.toJson(apiResponse));
        }


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
