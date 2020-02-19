package com.cs.springboot2andcomponents;

import com.cs.springboot2andcomponents.components.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class JwtTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void contextLoads() {
        String signingKey="ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=";
        String token = tokenProvider.createToken("Jack");
        log.info("token:{}",token);
        assert tokenProvider.validateToken(token);
        assert Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject().equals("Jack");
        Jws<Claims> jws = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
        log.info(jws.getHeader().toString());
        log.info(jws.getBody().toString());
        log.info(jws.getBody().get("payload1").toString());
        log.info(jws.getBody().get("payload2").toString());
        log.info(jws.getSignature());

    }
}
