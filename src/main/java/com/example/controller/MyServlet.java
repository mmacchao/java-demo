package com.example.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MyServlet extends HttpServlet {
    // 初始化方法（可选）
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet初始化完成");
    }

    // 处理 GET 请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");  // 设置响应编码
        PrintWriter out = response.getWriter();
        out.println("<h1>Hello World! (GET请求)</h1>");
    }

    // 处理 POST 请求（可选）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");  // 获取请求参数
        response.getWriter().write("Received username: " + username);
    }

    // 销毁方法（可选）
    @Override
    public void destroy() {
        System.out.println("Servlet已销毁");
    }
}