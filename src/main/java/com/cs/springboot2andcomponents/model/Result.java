package com.cs.springboot2andcomponents.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * api接口错误
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    /**
     * 默认错误代码
     */
    private Integer code = -1;
    /**
     * 消息
     */
    private String message;
    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private T data;

    /**
     *
     */
    private Result() {
        timestamp = LocalDateTime.now();
    }

    public static Result resultOk(String message){
        Result result = new Result();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }

    public static Result resultOk(String message,Object data){
        Result result = resultOk(message);
        result.setData(data);
        return result;
    }

    public static Result resultError(String message){
        Result result = new Result();
        result.setMessage(message);
        return result;
    }

    public static Result resultError(Integer code,String message){
        Result result = resultError(message);
        result.setCode(code);
        return result;
    }

    public static Result resultError(Integer code,String message,Object data){
        Result result = resultError(code,message);
        result.setCode(code);
        return result;
    }
}


