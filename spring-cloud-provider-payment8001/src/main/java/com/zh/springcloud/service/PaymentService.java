package com.zh.springcloud.service;

import com.zh.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    public int create(Payment payment); //插入

    public Payment getPaymentById(@Param("id") Long id); //查询
}
