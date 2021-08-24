package com.zh.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
用于前后端分离，返回给前端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    //404  not_found

    private Integer code;  //编码
    private String message; //消息

    private T       data;

    //泛型T可能是个null 这样定义一个俩个参数的构造方法
    public CommonResult(Integer code,String message){
      /*  this.code=code;
        this.message=message;*/

      this(code,message,null);
    }

}
