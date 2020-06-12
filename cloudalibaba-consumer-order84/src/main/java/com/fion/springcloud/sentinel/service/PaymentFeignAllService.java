package com.fion.springcloud.sentinel.service;

import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.sentinel.config.ConstantConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(value = ConstantConfig.PAYMENT_SERVICE_NAME, fallback = PaymentFallbackService.class)
public interface PaymentFeignAllService {

    @GetMapping(value = "/payment")
    Result<List<Payment>> findAll();

    @PostMapping(value = "payment/save")
    Result<Integer> save(@RequestBody Payment payment);

    @GetMapping(value = "/payment/{id}")
    Result<Payment> find(@PathVariable("id") Long id);
}
