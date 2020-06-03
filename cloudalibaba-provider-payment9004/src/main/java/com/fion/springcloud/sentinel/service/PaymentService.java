package com.fion.springcloud.sentinel.service;

import com.fion.springcloud.entity.Payment;

import java.util.List;

public interface PaymentService {
    int save(Payment payment);

    Payment getById(Long id);

    List<Payment> getAll();
}
