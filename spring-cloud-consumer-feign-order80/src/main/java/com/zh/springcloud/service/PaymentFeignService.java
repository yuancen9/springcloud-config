package com.zh.springcloud.service;


import com.zh.springcloud.entities.CommonResult;
import com.zh.springcloud.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//找那个名称的微服务
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    // CommonResult<Payment> getPaymentById(@Param("id") Long id);


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);



    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout();

}
