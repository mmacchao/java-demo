package com.example.order.feign;

import com.example.common.response.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service")
public interface StorageClient {
    @GetMapping("/storage/decrease")
    CommonResult<Void> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}