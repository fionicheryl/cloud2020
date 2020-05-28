package com.fion.springcloud.payment.service;

import com.fion.springcloud.entity.Payment;

import java.util.List;

public interface PaymentService {

    int save(Payment payment);

    Payment getById(Long id);

    List<Payment> getAll();
}
