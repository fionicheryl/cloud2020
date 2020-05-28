package com.fion.springcloud.payment.service;

public interface PaymentService {

    /**
     * 正常访问，肯定OK
     * @param id
     * @return
     */
    String ok(Integer id);

    /**
     * 超时访问
     * @param id
     * @return
     */
    String timeout(Integer id);

    String error();

    String circuitBreaker(Integer id);
}
