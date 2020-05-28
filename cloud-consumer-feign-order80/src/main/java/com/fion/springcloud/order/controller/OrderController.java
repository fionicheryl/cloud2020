package com.fion.springcloud.order.controller;

import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.order.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consumer/order")
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @PostMapping(value = "/payment/save")
    Result<Integer> save(@RequestBody Payment payment) {
        return paymentFeignService.save(payment);
    }

    @GetMapping(value = "/payment/{id}")
    Result<Payment> find(@PathVariable("id") Long id) {
        return paymentFeignService.find(id);
    }

    @GetMapping(value = "/payment")
    Result<List<Payment>> findAll() {
        return paymentFeignService.findAll();
    }
}
