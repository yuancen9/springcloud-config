package com.zh.springcloud.service.impl;

import com.zh.springcloud.dao.PaymentDao;
import com.zh.springcloud.entities.Payment;
import com.zh.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class PaymentServiceImpl implements PaymentService {

    //java自带的注入   @AutowiredSpring的注入
    @Resource
    private PaymentDao paymentDao;




    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
