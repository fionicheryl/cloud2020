package com.fion.springcloud.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fion.springcloud.common.Result;
import com.fion.springcloud.sentinel.sentinel.DefaultBlockHandler;
import com.fion.springcloud.sentinel.sentinel.DefaultFallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("order")
public class OrderController {

    @Value("${service.url.payment}")
    private String paymentServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("payment/all")
    public Result paymentAll() {
        return restTemplate.getForObject(paymentServiceUrl + "/payment", Result.class);
    }

    @GetMapping("payment")
    @SentinelResource(value = "/order/payment",
            blockHandler = "paymentBlockHandler",
            fallback = "paymentFallback",
            exceptionsToIgnore = {IllegalArgumentException.class}
    )
    public Result payment(@RequestParam("id") Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("非法参数异常......");
        }
        Result result = restTemplate.getForObject(paymentServiceUrl + "/payment/" + id, Result.class);
        if (null == result.getData()) {
            throw new NullPointerException("没有对应记录......");
        }
        return result;
    }

    public Result paymentFallback(@RequestParam("id") Long id) {
        return Result.wrapError(444, "Sentinel fallback handle.");
    }

    public Result paymentBlockHandler(@RequestParam("id") Long id) {
        return Result.wrapError(446, "Sentinel block handle.");
    }
}
