package com.fion.springcloud.order.lb.impl;

import com.fion.springcloud.order.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class LoadBalancerImpl implements LoadBalancer {

    /**
     * rest请求计数器
     */
    private AtomicInteger restCounter = new AtomicInteger(0);

    public final int getAndIncrement() {
        for (;;) {
            int courrent = this.restCounter.get();
            int next = courrent >= Integer.MAX_VALUE ? 0 : courrent + 1;
            if (this.restCounter.compareAndSet(courrent, next)) {
                log.info("[LoadBalancer] next = {}", next);
                return next;
            }
        }
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
