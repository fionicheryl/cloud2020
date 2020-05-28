package com.fion.springcloud.payment.controller;

import com.fion.springcloud.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        return paymentService.ok(id);
    }

    @GetMapping("/timeout/{id}")
    public String timeout(@PathVariable("id") Integer id) {
        return paymentService.timeout(id);
    }

    @GetMapping("/error")
    public String error() {
        return paymentService.error();
    }

    @GetMapping("/circuit/{id}")
    public String circuitBreaker(@PathVariable("id") Integer id) {
        return paymentService.circuitBreaker(id);
    }
}
