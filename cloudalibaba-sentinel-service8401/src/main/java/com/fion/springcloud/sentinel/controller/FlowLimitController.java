package com.fion.springcloud.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/limit")
public class FlowLimitController {

    @GetMapping("a")
    public String testA() {
        return "This is Test A";
    }

    @GetMapping("b")
    public String testB() {
        return "This is Test B";
    }
}
