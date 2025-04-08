package com.example.storage.service;

import com.example.storage.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StorageService {

    @Resource
    private StorageMapper storageMapper;

    public void decrease(Long productId, Integer count) {
        log.info("开始扣减商品{}的库存，数量：{}", productId, count);
        int result = storageMapper.decrease(productId, count);
        if (result <= 0) {
            throw new RuntimeException("库存不足");
        }
        log.info("扣减库存成功");
    }
}