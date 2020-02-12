package com.cs.springboot2andcomponents.controller;

import com.cs.springboot2andcomponents.aop.Log;
import com.cs.springboot2andcomponents.util.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * EasyCaptcha示例
 */
@Api(tags = "EasyCaptcha示例")
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private RedisUtil redisUtil;

    @Log("生成默认验证码")
    @ApiOperation(value = "生成默认验证码", notes = "生成默认验证码")
    @GetMapping("/default")
    public void captchaDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    @Log("生成gif验证码")
    @ApiOperation(value = "gif验证码", notes = "gif验证码，包含简单配置")
    @GetMapping("/gif")
    public void captchaGif(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);
        // 设置宽、高、位数
        CaptchaUtil.out(130, 48, 5, request, response);

        // 使用gif验证码
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }

    @Log("生成验证码Base64")
    @ApiOperation(value = "生成验证码", notes = "生成验证码，base64返回给前端")
    @ResponseBody
    @GetMapping("/spec64")
    public ResponseEntity<Object> specCaptcha() throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // base64返回给前端
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", specCaptcha.toBase64());
        }};
        return ResponseEntity.ok(imgResult);
    }

    @Log("生成算数验证码")
    @ApiOperation(value = "生成算数验证码", notes = "生成算数验证码，base64返回给前端")
    @ResponseBody
    @GetMapping("/arithmetic64")
    public ResponseEntity<Object> arithmeticCaptcha() throws Exception {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // base64返回给前端
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
        }};
        return ResponseEntity.ok(imgResult);
    }

}
