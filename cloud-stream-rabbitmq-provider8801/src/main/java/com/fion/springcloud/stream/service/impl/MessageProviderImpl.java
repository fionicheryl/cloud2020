package com.fion.springcloud.stream.service.impl;

import com.fion.springcloud.stream.service.MessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Source.class)    // 定义消息的推送管道
@Slf4j
public class MessageProviderImpl implements MessageProvider {

    /**
     * 消息发送管道
     */
    @Autowired
    private MessageChannel output;

    @Override
    public boolean send(String content) {
        boolean result = output.send(MessageBuilder.withPayload(content).build());
        log.info("[send msg] send content is {}, send result is {}", content, result);
        return result;
    }
}
