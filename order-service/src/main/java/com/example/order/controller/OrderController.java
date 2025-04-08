package com.example.order.controller;

import com.example.common.entity.Order;
import com.example.common.response.CommonResult;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody Order order) {
        orderService.create(order);
        return CommonResult.success(null);
    }
}