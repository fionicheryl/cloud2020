package com.fion.springcloud.stream.service;

public interface MessageProvider {

    boolean send(String content);
}
