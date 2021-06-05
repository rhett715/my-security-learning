package org.rhett.mysecurity.controller;

import com.google.code.kaptcha.Producer;
import org.rhett.mysecurity.common.Result;
import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.utils.IDUtil;
import org.rhett.mysecurity.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 验证码操作处理
 */
@RestController
public class CaptchaController {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 生成验证码
     * @param response 响应
     * @return 结果封装
     */
    @GetMapping("/captchaImage")
    public Result getCode(HttpServletResponse response) {
        //禁止页面缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        // 保存验证码信息
        String uuid = IDUtil.randomUUID();
        String verifyKey = SysConstant.CAPTCHA_CODE_KEY;

        //设置响应格式为png图片
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //将生成的文字验证码保存到redis缓存中，过期时间为1分钟
        redisUtil.set(verifyKey, text, 60);
        //转成图片验证码
        BufferedImage image = producer.createImage(text);
        //ServletOutputStream out = response.getOutputStream();
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("jpg", Base64Utils.encode(out.toByteArray()));
        return Result.ok().data(data);
    }
}
