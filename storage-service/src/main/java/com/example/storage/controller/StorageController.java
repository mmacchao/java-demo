package com.example.storage.controller;

import com.example.common.response.CommonResult;
import com.example.storage.service.StorageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    @GetMapping("/decrease")
    public CommonResult<Void> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decrease(productId, count);
        return CommonResult.success(null);
    }
}