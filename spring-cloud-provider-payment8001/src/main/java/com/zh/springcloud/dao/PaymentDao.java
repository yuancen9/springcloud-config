package com.zh.springcloud.dao;

import com.zh.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PaymentDao {

    public int create(Payment payment); //插入

    public Payment getPaymentById(@Param("id") Long id); //查询
}
