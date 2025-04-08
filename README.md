# Seata 分布式事务示例项目

这是一个使用 Seata 实现分布式事务的示例项目，包含订单服务和库存服务两个微服务，演示了在分布式环境下如何保证事务的一致性。

## 技术栈

- Spring Boot 2.3.12.RELEASE
- Spring Cloud Hoxton.SR12
- Spring Cloud Alibaba 2.2.7.RELEASE
- Seata 1.5.2
- MyBatis-Plus 3.4.3
- MySQL 8.0.23
- Nacos 2.x

## 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Nacos 2.x
- Seata Server 1.5.2

## 项目结构

```
seata-demo
├── common               # 公共模块
├── order-service        # 订单服务
├── storage-service      # 库存服务
└── sql                  # 数据库脚本
```

## 快速开始

### 1. 环境准备

1. 启动 Nacos 服务
2. 启动 Seata Server，并确保已注册到 Nacos
3. 执行 `sql/init.sql` 脚本，创建所需的数据库和表

### 2. 启动服务

1. 启动订单服务（OrderServiceApplication）
2. 启动库存服务（StorageServiceApplication）

### 3. 测试

发送以下请求创建订单：

```bash
POST http://localhost:8081/order/create
Content-Type: application/json

{
    "userId": "user1",
    "productId": 1,
    "count": 2,
    "money": 20
}
```

## 分布式事务演示

1. 正常下单：按上述步骤发送请求，观察订单创建和库存扣减是否都成功
2. 异常场景：在库存服务中模拟异常（如库存不足），观察订单是否回滚

## 注意事项

1. 确保 Nacos 和 Seata 服务已正确启动
2. 检查数据库连接信息是否正确
3. 确保 Seata 配置中的事务组配置与服务配置一致