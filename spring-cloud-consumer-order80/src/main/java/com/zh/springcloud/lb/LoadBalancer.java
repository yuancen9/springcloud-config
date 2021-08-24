package com.zh.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/*
将ApplicationContextConfig类中的@LoadBalanced注解注释掉
自己手写一个负载均衡算法
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
