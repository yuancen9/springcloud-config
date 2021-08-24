package om.zh.springcloud.controller;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import om.zh.springcloud.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
/*
@DefaultProperties 全局降级注解
当方法上标注的注解@HystrixCommand没有特别指明方法名,就用统一@DefaultProperties方法降级
 */
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){

        String result=paymentHystrixService.paymentInfo_OK(id);
        return result;
    }


    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
  /*  @HystrixCommand(fallbackMethod = "paymentTimeoutFallbackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result=paymentHystrixService.paymentInfo_TimeOut(id);
        return result;

    }

    public String paymentTimeoutFallbackMethod(@PathVariable("id") Integer id){
        return  "我是消费者80,对象支付系统繁忙请10秒钟后再试或自己运行出错请检查自己≧ ﹏ ≦";
    }


    //这是全局fallback方法
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息,请稍后再试。";
    }

}
