package com.fion.springcloud.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.sentinel.handler.CustomBlockExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rate/limit")
public class RateLimitController {

    @GetMapping("resource")
    @SentinelResource(value = "By Resource Limit", blockHandler = "resourceHandler")
    public Result resource() {
        return Result.wrapSuccess(new Payment(2020L, "serial2020"), "按资源名称限流测试OK");
    }

    public Result resourceHandler(BlockException exception) {
        return Result.wrapError(444, exception.getClass().getCanonicalName() + " 服务不可用");
    }

    @GetMapping("custom1")
    @SentinelResource(value = "Custom Limit Handle 1", blockHandlerClass = CustomBlockExceptionHandler.class, blockHandler = "handlerException_1")
    public Result custom1() {
        return Result.wrapSuccess(new Payment(2020L, "serial2020"), "自定义限流处理");
    }

    @GetMapping("custom2")
    @SentinelResource(value = "Custom Limit Handle 2", blockHandlerClass = CustomBlockExceptionHandler.class, blockHandler = "handlerException_2")
    public Result custom2() {
        return Result.wrapSuccess(new Payment(2020L, "serial2020"), "自定义限流处理");
    }
}
