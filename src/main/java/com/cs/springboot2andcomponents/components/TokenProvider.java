package com.cs.springboot2andcomponents.components;

import com.cs.springboot2andcomponents.aop.Log;
import com.cs.springboot2andcomponents.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * jwt token生成工具
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    @Autowired
    private JwtConfig jwtConfig;
    /**
     * 指定key
     */
    private static final String AUTHORITIES_KEY = "auth";
    /**
     * 保存秘钥
     */
    private SecretKey key;

    @Log("初始化jwt配置")
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 创建token
     * @param username
     * @return
     */
    public String createToken(String username) {

        //有效期
        long now = Calendar.getInstance().getTime().getTime();
        Date validity = new Date(now + jwtConfig.getTokenValidityInSeconds());

        Map<String,Object> header = new HashMap<>();

//        header.put("alg", "HS1256"); //可以省略，会根据签名加密算法自动生成，设置不对也没关系
        header.put("typ", "JWT");

        return Jwts.builder()
                .setHeader(header)    //header
                .setIssuer("com.cs") //发行人
                .setExpiration(validity) //到期时间
                .setSubject(username) //主题
                .setAudience(username) //用户
                .setNotBefore(Calendar.getInstance().getTime()) //在此之前不可用
                .setIssuedAt(Calendar.getInstance().getTime()) //发布时间
                .setId(""+System.currentTimeMillis()) //ID
                .claim("payload1", "other payload1")//其他内容
                .claim("payload2", "other payload2")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.warn("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从请求header中获取tokent
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request){
        final String requestHeader = request.getHeader(jwtConfig.getHeader());
        if (requestHeader != null && requestHeader.startsWith(jwtConfig.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }
}
