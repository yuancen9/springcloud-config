package com.zh.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
业务类实现负载均衡的算法
 */
@Component
public class MyLB implements LoadBalancer {

    //初始值为0
    private AtomicInteger atomicInteger=new AtomicInteger(0);


    public final int getAndIncrement(){
        int current;
        int next; //当前值
        do {
            //得到当前的值
            current=this.atomicInteger.get();
            //2147483647 是Integer整形最大值
            next = current >= 2147483647 ? 0 : current+1;

        }
        //如果当前值和我们的期望值一致就修改 this.atomicInteger.compareAndSet(current,next) 代表为true
        //取反(!) 就为false
        while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*********** next : " + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index= getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}
