package com.fion.springcloud.stream.controller;

import cn.hutool.core.util.IdUtil;
import com.fion.springcloud.stream.service.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message/provider")
public class MessageProviderController {

    @Autowired
    private MessageProvider messageProvider;

    @GetMapping("send")
    public Boolean send() {
        return messageProvider.send(IdUtil.simpleUUID());
    }
}
