package com.fion.springcloud.sentinel.controller;

import com.alibaba.fastjson.JSON;
import com.fion.springcloud.common.Result;
import com.fion.springcloud.entity.Payment;
import com.fion.springcloud.sentinel.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
@Slf4j
public class PaymentController {

    @Value("server.port")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/save")
    public Result save(@RequestBody Payment payment) {
        int result = paymentService.save(payment);
        log.info("[save result] result = {}, serverPort = {}", result, serverPort);
        if (result > 0) {
            return Result.wrapSuccess();
        } else {
            return Result.wrapError(444, "插入失败");
        }
    }

    @GetMapping(value = "/{id}")
    public Result find(@PathVariable("id") Long id) {
        Payment payment = paymentService.getById(id);
        log.info("[find result] payment = {}, serverPort = {}", JSON.toJSONString(payment), serverPort);
        if (null != payment) {
            return Result.wrapSuccess(payment, "服务端口号：" + serverPort);
        } else {
            return Result.wrapError(444, "没有对应结果");
        }
    }

    @GetMapping()
    public Result findAll() {
        List<Payment> payments = paymentService.getAll();
        log.info("[find result] payments = {}, serverPort = {}", JSON.toJSONString(payments), serverPort);
        if (!CollectionUtils.isEmpty(payments)) {
            return Result.wrapSuccess(payments, "服务端口号：" + serverPort);
        } else {
            return Result.wrapError(444, "没有对应结果");
        }
    }
}
