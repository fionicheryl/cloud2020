package com.fion.springcloud.sentinel.controller;

import com.fion.springcloud.common.Result;
import com.fion.springcloud.sentinel.service.PaymentFeignAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/feign")
public class OrderFeignController {

    @Autowired
    private PaymentFeignAllService paymentFeignAllService;

    @GetMapping("payment/all")
    public Result paymentAll() {
        return paymentFeignAllService.findAll();
    }

    @GetMapping("payment/{id}")
    public Result payment(@PathVariable("id") Long id) {
        return paymentFeignAllService.find(id);
    }
}
