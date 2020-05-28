package com.fion.springcloud.nacos.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("nacos/order")
public class OrderController {

    @Value("${service-url.nacos-payment-service}")
    private String paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("payment/{id}")
    public String payment(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(paymentService + "/nacos/payment/" + id, String.class);
    }
}
