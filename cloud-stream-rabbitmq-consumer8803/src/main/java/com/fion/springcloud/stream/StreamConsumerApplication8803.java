package com.fion.springcloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class StreamConsumerApplication8803 {
    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApplication8803.class, args);
    }
}
