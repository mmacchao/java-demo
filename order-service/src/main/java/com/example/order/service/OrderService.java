package com.example.order.service;

import com.example.common.entity.Order;
import com.example.common.response.CommonResult;
import com.example.order.feign.StorageClient;
import com.example.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private StorageClient storageClient;

    @GlobalTransactional
    public void create(Order order) {
        log.info("开始创建订单");
        orderMapper.insert(order);

        log.info("订单创建成功，开始调用库存服务扣减库存");

        // 方法2：查询参数（推荐）
//        String url2 = UriComponentsBuilder.fromHttpUrl("http://storage-service/storage/decrease")
//                .queryParam("productId", order.getProductId())
//                .queryParam("count", order.getCount())
//                .toUriString();
        // 通过服务名调用提供者接口
//        restTemplate.getForObject(url2, CommonResult.class);
        storageClient.decrease(order.getProductId(), order.getCount());

        log.info("订单创建完成");
    }
}