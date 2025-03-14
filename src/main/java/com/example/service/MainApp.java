package com.example.service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // 加载 Spring 配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取 Bean 实例
        Api apiService = (Api) context.getBean("apiService");
        // 获取 Bean 实例
        Api apiService2 = (Api) context.getBean("apiService2");
        // 调用方法
        String result = apiService.process(123);
        System.out.println("Result: " + result);
        System.out.println("Result2: " + apiService2.process(456));
    }
}