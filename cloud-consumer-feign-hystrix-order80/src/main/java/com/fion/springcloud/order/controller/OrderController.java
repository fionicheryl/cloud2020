package com.fion.springcloud.order.controller;

import com.fion.springcloud.order.feign.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
// @DefaultProperties(defaultFallback = "globalHandle")
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("payment/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        return paymentFeignService.ok(id);
    }

    /*@HystrixCommand(fallbackMethod = "timeoutHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })*/
    @GetMapping("payment/timeout/{id}")
    //@HystrixCommand
    public String timeout(@PathVariable("id") Integer id) {
        // int x = 10 /0;
        return paymentFeignService.timeout(id);
    }

    //@HystrixCommand
    @GetMapping("payment/error")
    public String error() {
        return paymentFeignService.error();
    }

    public String timeoutHandle(Integer id) {
        return "consumer hystrix handle, id is " + id + ".";
    }

    public String globalHandle() {
        return "consumer global hystrix handle.";
    }
}
