package com.fion.springcloud.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderController {

    private final static String CLOUD_PROVIDER_PAYMENT = "http://cloud-provider-payment";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("order/payment/zk")
    public String testZK() {
        return restTemplate.getForObject(CLOUD_PROVIDER_PAYMENT + "/payment/zk", String.class);
    }
}
