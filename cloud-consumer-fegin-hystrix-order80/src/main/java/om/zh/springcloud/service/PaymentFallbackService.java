package om.zh.springcloud.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

/*
根据cloud-consumer-feign-hystrix-order80已经有的PaymentHystrixService接口,
重新新建一个类(PaymentFallbackService)实现该接口,统一为接口里面的方法进行异常处理
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "----------PaymentFallbackService fall back---------paymentInfo_OK, ≧﹏≦";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----------PaymentFallbackService fall back*********paymentInfo_TimeOut, ≧﹏≦";
    }
}
