package com.fion.springcloud.order.controller;

import com.alibaba.fastjson.JSON;
import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.order.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/consumer/order")
public class OrderController {

    //private final static String PAYMENT_URL = "http://localhost:8001";
    private final static String PAYMENT_URL = "http://cloud-payment-service";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancer loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/save")
    public Result savePayment(@RequestBody Payment payment) {
        log.info("[consumer order] call payment save, payment = {}", JSON.toJSONString(payment));
        return restTemplate.postForObject(PAYMENT_URL + "/payment/save", payment, Result.class);
    }

    @GetMapping("/payment/{id}")
    public Result findPayment(@PathVariable("id") Long id, HttpServletRequest request) {
        log.info("[consumer order] call payment find, id = {}, content-type1: = {}, content-type2: = {}", id, request.getContentType(), request.getHeader("Content-Type"));
        return restTemplate.getForObject(PAYMENT_URL + "/payment/" + id, Result.class);
    }

    @GetMapping("/payment/entity/{id}")
    public Result findPayment2(@PathVariable("id") Long id) {
        log.info("[consumer order] call payment find, id = {}", id);
        return restTemplate.getForEntity(PAYMENT_URL + "/payment/" + id, Result.class).getBody();
    }

    @GetMapping("/payment/lb")
    public String paymentLB() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("cloud-payment-service");
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return null;
        }

        ServiceInstance instance = loadBalancer.instances(serviceInstances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }
}
