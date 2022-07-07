package com.wjt.ieps.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//同意返回结果
@Data
public class Result {
    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<String, Object>();

    //构造方法私有化
    private Result(){}

    //静态成功
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //静态失败
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public Result success(boolean success){
        this.success = success;
        return this;
    }

    public Result code(Integer code){
        this.code = code;
        return this;
    }
    public Result message(String message){
        this.message = message;
        return this;
    }
    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.data = map;
        return this;
    }
}
