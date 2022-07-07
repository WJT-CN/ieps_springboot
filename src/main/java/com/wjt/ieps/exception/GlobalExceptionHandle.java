package com.wjt.ieps.exception;


import com.wjt.ieps.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常处理
@ControllerAdvice
@Slf4j//将错误信息输出到lock back日志文件中
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)//根据异常类型返回不同json数据
    @ResponseBody//返回json数据
    public Result error(Exception e){
        return Result.error().message("全局异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(IepsException.class)//根据异常类型返回不同json数据
    @ResponseBody//返回json数据
    public Result error(IepsException e){
        log.error(e.getMeg());//将异常信息输出到文件中
        return Result.error().code(e.getCode()).message(e.getMeg());
    }
}

