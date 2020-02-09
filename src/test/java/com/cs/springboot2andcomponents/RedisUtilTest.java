package com.cs.springboot2andcomponents;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.cs.springboot2andcomponents.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
        String token = UUID.randomUUID().toString();
        redisUtil.set("token", token, 10, TimeUnit.SECONDS);
        log.info("token is {}", redisUtil.get("token"));
        log.info("token expire in {}s", redisUtil.getExpire("token"));

        String undefined = (String) redisUtil.get("undefined");
        Assert.isNull(undefined);
    }
}
