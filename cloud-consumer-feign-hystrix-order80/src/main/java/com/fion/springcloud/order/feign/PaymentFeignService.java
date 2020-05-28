package com.fion.springcloud.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE", fallback = PaymentFallbackService.class)
public interface PaymentFeignService {

    @GetMapping("/payment/ok/{id}")
    String ok(@PathVariable("id") Integer id);

    @GetMapping("/payment/timeout/{id}")
    String timeout(@PathVariable("id") Integer id);

    @GetMapping("/payment/error")
    String error();
}
