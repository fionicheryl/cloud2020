package com.fion.springcloud.order.feign;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignService {
    @Override
    public String ok(Integer id) {
        return "ok fallback";
    }

    @Override
    public String timeout(Integer id) {
        return "timeout fallback";
    }

    @Override
    public String error() {
        return "error fallback";
    }
}
