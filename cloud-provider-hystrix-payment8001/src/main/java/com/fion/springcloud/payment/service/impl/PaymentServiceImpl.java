package com.fion.springcloud.payment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fion.springcloud.payment.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String ok(Integer id) {
        return "ThreadPool: " + Thread.currentThread().getName() + "\t payment ok, id is " + id + ".";
    }

    /**
     * fallbackMethod 指定服务降级的兜底处理方法，超时或异常都会触发
     * @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") 指定3秒是处理时间峰值，超过当做超时处理
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "timeoutHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public String timeout(Integer id) {
        // int x = 10 /0;
        int timeout = 5;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ThreadPool: " + Thread.currentThread().getName() + "\t payment timeout, id is " + id + ". cost time " + timeout + "s.";
    }

    @Override
    public String error() {
        int x = 10 /0;
        return "ThreadPool: " + Thread.currentThread().getName() + "\t payment error";
    }

    public String timeoutHandle(Integer id) {
        return "ThreadPool: " + Thread.currentThread().getName() + "\t payment timeout, id is " + id + ". hystrix handle. ";
    }


    @HystrixCommand(fallbackMethod = "circuitBreakerHandle", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 熔断多少毫秒后去尝试请求，默认值：5000
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), // 失败率达到多少百分比后熔断， 默认值：50
    })
    @Override
    public String circuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        String serial = IdUtil.simpleUUID();
        return "ThreadPool: " + Thread.currentThread().getName() + "\t call success, id is " + id + ", serial is " + serial + ".";
    }

    public String circuitBreakerHandle(Integer id) {
        return "ThreadPool: " + Thread.currentThread().getName() + "\t call failure, id is " + id + " and illegal, please change retry !";
    }
}
