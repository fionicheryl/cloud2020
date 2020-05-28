package com.fion.springcloud.payment.service.impl;

import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.payment.dao.PaymentDao;
import com.fion.springcloud.payment.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int save(Payment payment) {
        return paymentDao.insert(payment);
    }

    @Override
    public Payment getById(Long id) {
        return paymentDao.selectById(id);
    }

    @Override
    public List<Payment> getAll() {
        return paymentDao.selectAll();
    }
}
