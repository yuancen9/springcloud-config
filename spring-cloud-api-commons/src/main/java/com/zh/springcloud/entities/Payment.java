package com.zh.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor   //有参
@NoArgsConstructor   //无参
public class Payment implements Serializable {
    private Long id;

    private String serial;
}
