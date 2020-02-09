package com.cs.springboot2andcomponents.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/apiwithswagger")
public class ApiWithSwaggerController {

    @ApiOperation(value = "一个测试API", notes = "第一个测试api")
    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {
        return "apiwithswagger demo";
    }
}
