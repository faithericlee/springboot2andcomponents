package com.cs.springboot2andcomponents.controller;

import com.cs.springboot2andcomponents.aop.Log;
import com.cs.springboot2andcomponents.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "Validated示例")
@RestController
@RequestMapping("/validated")
public class ValidatedController {

    @Log("验证User数据")
    @ApiOperation("验证User")
    @PostMapping(value = "/user")
    public ResponseEntity<Object> user(@Validated @RequestBody User user, HttpServletRequest request){

        return ResponseEntity.ok(user);
    }
}
