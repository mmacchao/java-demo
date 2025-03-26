package com.nacosdemo.provider;

import com.nacosdemo.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService  // 暴露 Dubbo 服务
public class UserServiceImpl implements UserService {
  @Override
  public String getUserName(Long userId) {
    return "User-" + userId;
  }
}
