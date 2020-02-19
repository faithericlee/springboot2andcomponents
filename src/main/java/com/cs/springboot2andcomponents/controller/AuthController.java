package com.cs.springboot2andcomponents.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.cs.springboot2andcomponents.aop.Log;
import com.cs.springboot2andcomponents.components.TokenProvider;
import com.cs.springboot2andcomponents.exception.BadRequestException;
import com.cs.springboot2andcomponents.model.AuthUser;
import com.cs.springboot2andcomponents.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "JWT示例")
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 私钥
     */
    @Value("${rsa.private_key}")
    private String privateKey;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Log("用户登录-仅使用jwt")
    @ApiOperation("用户登录-仅使用jwt")
    @PostMapping(value = "/jwt")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request){
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
        if(!valid(authUser.getUsername(),password)){
            throw new BadRequestException("用户名和密码不匹配");
        }
        //redis+验证码
//        // 查询验证码
//        String code = (String) redisUtil.get(authUser.getUuid());
//        // 清除验证码
//        redisUtil.del(authUser.getUuid());
//        if (StringUtils.isBlank(code)) {
//            throw new BadRequestException("验证码不存在或已过期");
//        }
//        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
//            throw new BadRequestException("验证码错误");
//        }
        //spring securiyt认证（后续实现）
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authUser.getUsername());
//        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 保存在线信息
//        onlineUserService.save(jwtUser, token, request);
        // 返回 token 与 用户信息
//        Map<String,Object> authInfo = new HashMap<String,Object>(2){{
//            put("token", properties.getTokenStartWith() + token);
//            put("user", jwtUser);
//        }};
//        if(singleLogin){
//            //踢掉之前已经登录的token
//            onlineUserService.checkLoginOnUser(authUser.getUsername(),token);
//        }
        return ResponseEntity.ok("");
    }

    /**
     * 验证用户名和密码，直接返回true
     * @param username
     * @param password
     * @return
     */
    private boolean valid(String username,String password){
        return true;
    }
}
