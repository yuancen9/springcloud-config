package com.zh.springcloud.controller;

import com.zh.springcloud.entities.CommonResult;
import com.zh.springcloud.entities.Payment;
import com.zh.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    //服务端口
    @Value("${server.port}")
    private String serverPort;



    //对于注册进Eureka里面的微服务，可以通过服务发现来获得该服务的信息
    @Resource
    private DiscoveryClient discoveryClient;



    @PostMapping(value = "/payment/create")
                              //要注意加上@RequestBody 获取请求体中的数据并完成绑定
    public CommonResult  create(@RequestBody Payment payment){
     int result=paymentService.create(payment);
     log.info("***********插入结果: " +result);

     if (result>0){
         return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,result);

     }
     else {
         return new CommonResult(404,"出入数据库失败",null);

     }
    }


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){

        Payment payment=paymentService.getPaymentById(id);

        log.info("---------------查询结果： " + payment);
        if (payment !=null){
            return new CommonResult(200,"查询成功, serverPort: "+serverPort,payment);
        }
        else {
            return  new CommonResult(404,"没有对应记录,查询id是： " + id,null);
        }


    }


    @GetMapping(value = "/payment/discovery",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object discovery(){
        //服务列表清单
        List<String> services = discoveryClient.getServices();
        for (String element:services){
            log.info("**************element" + element);
        }

        //一个微服务下的所有具体实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances){
            //                                        主机名称                  端口号
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"
                              //uri地址
                    +instance.getUri());
        }

        return  this.discoveryClient;

    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return  serverPort;
    }


    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //暂停几秒钟线程
        try{
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return serverPort;
    }

}
