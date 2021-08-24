package com.zh.springcloud.controller;

import com.zh.springcloud.entities.CommonResult;
import com.zh.springcloud.entities.Payment;
import com.zh.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;


@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL="http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";



    //Ribbon
    @Autowired
    private RestTemplate restTemplate;



    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;


    /**
     * 用户端80调用服务者8001端口   新增功能
     *
     * @param payment
     * @return
     */
    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {

        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }


    //getForObject 返回对象 可以理解为 返回的是 json
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }




    //getForEntity 返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头，响应状态码，响应体等。
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id,CommonResult.class);

        // 如果是200 代表成功，否则返回报错失败404，440
        if (entity.getStatusCode().is2xxSuccessful()){
           return entity.getBody();
        }
        else {
            return new CommonResult<>(444,"操作失败");
        }
    }


    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){

        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <=0){
            return null;
        }

        ServiceInstance serviceInstance=loadBalancer.instances(instances);

        URI uri= serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb",String.class);

    }



}
