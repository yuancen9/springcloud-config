package com.zh.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.Path;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常能访问的方法
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "当前线程池名称: " +Thread.currentThread().getName() + "paymentInfo_OK,id "+ id + "\t"+
                "O(∩_∩)O";
    }


    /**
     * 表示这个方法出现问题,找paymentInfo_TimeOutHandler这个方法
     * execution.isolation.thread.timeoutInMilliseconds 线程的超时时间是三秒钟
     * 正常的超时时间在三秒钟认为是正常的逻辑，如果超出时间走paymentInfo_TimeOutHandler方法
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){

        int timeNumber=3;

        //暂停3秒钟线程
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return "当前线程池名称: "+ Thread.currentThread().getName() + "paymentInfo_TimeOut,id" + id +
                "\t"+"耗时"+timeNumber+"秒钟┭┮﹏┭┮";
    }


    public String paymentInfo_TimeOutHandler(Integer id){

        return  "线程池: "+ Thread.currentThread().getName() + "paymentInfo_TimeOutHandler,id" + id+
                "\t"+ "≧ ﹏ ≦";
    }


    //--------------------------------服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),  //请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //时间窗口期(时间范围)
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),
            //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("*********id 不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号" + serialNumber;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能是负数请稍后再试, id是： " + id;
    }
}
