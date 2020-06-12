package com.fion.springcloud.sentinel.service;

import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentFallbackService implements PaymentFeignAllService {
    @Override
    public Result<List<Payment>> findAll() {
        return Result.wrapError(444, "findAll fallback");
    }

    @Override
    public Result<Integer> save(Payment payment) {
        return Result.wrapError(444, "save fallback");
    }

    @Override
    public Result<Payment> find(Long id) {
        return Result.wrapError(444, "find fallback");
    }
}
