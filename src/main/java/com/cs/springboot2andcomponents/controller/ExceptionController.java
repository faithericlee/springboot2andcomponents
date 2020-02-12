package com.cs.springboot2andcomponents.controller;

import com.cs.springboot2andcomponents.aop.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "异常示例")
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @Log("算数错误")
    @ApiOperation("算数错误")
    @GetMapping("/arithmetic")
    public Integer getArithmeticException() {
        int a = 6/0;
        return a;
    }

    @Log("超出边界")
    @ApiOperation("超出边界")
    @GetMapping("/indexoutofbounds")
    public Integer getException() {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0;i < 5; i++) {
            list.add(i);
        }
        return list.get(6);
    }

    @Log("抛出异常")
    @ApiOperation("抛出异常")
    @GetMapping("/throw")
    public Integer throwexception() throws Exception {
        throw new Exception("主动抛出的异常");
    }

}
