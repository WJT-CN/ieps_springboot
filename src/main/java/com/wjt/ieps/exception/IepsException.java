package com.wjt.ieps.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IepsException extends RuntimeException{
    private Integer code;
    private String meg;
}
