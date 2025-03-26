package com.nacosdemo.consumer;

import com.nacosdemo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @DubboReference  // 引用远程服务
  private UserService userService;

  @GetMapping("/user/{id}")
  public String getUser(@PathVariable Long id) {
    return userService.getUserName(id);
  }
}
