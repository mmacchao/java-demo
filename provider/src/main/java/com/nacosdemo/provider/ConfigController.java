package com.nacosdemo.provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope  // 启用配置动态刷新
public class ConfigController {
  @Value("${custom.message:默认值}")  // 冒号后为默认值
  private String message;

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/message")
  public String getMessage() {
    return "当前配置: " + message;
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello from Nacos Provider!";
  }
  @GetMapping("/call")
  public String callProvider() {
    // 通过服务名调用提供者接口
    return restTemplate.getForObject(
        "http://consumer/hello", String.class
    );
  }
}
