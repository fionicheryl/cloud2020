package com.fion.springcloud.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("flow/limit")
@Slf4j
public class FlowLimitController {

    @GetMapping("a")
    public String testA() {
        log.info(Thread.currentThread().getName() + "\t" + "This is Test A");
        return "This is Test A";
    }

    @GetMapping("b")
    public String testB() {
        return "This is Test B";
    }

    @GetMapping("rt")
    public String testRT() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        log.info("test rt");
        return "This is Test RT";
    }

    @GetMapping("er")
    public String testER() {
        log.info("test er");
        int a = 10 / 0;
        return "This is Test ER";
    }

    @GetMapping("ec")
    public String testEC() {
        log.info("test ec");
        int a = 10 / 0;
        return "This is Test EC";
    }

    @GetMapping("hot/key")
    @SentinelResource(value = "hotkey", blockHandler = "testHotKeyHandler")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        log.info("test hot key, p1 = {}, p2 = {}", p1, p2);
        return "This is Test Hot Key";
    }

    public String testHotKeyHandler(String p1, String p2, BlockException exception) {
        log.info("Test Hot Key Exception Handle, p1 = {}, p2 = {}", p1, p2);
        return "This is Test Hot Key Exception Handle.";
    }
}
