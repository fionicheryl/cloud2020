package com.fion.springcloud.alibaba.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nacos/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("{id}")
    public String payment(@PathVariable("id") Integer id) {
        return "nacos registry, server port: " + serverPort + ", id: " + id;
    }
}
