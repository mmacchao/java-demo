package com.nacosdemo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/call")
  public String callProvider() {
    // 通过服务名调用提供者接口
    return restTemplate.getForObject(
        "http://provider/hello", String.class
    );
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello from Consumer provider!";
  }
}
