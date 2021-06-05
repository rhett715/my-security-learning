package org.rhett.mysecurity.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * kaptcha验证码生成器配置
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha producer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //验证码是否带边框 No
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        // 验证码文本字体颜色默认为 Color.black
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        //验证码文本字符大小，默认为40
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
        //验证码图片宽度，默认为200
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "160");
        //验证码图片高度，默认为50
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //kaptcha session key
        properties.setProperty(Constants.KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
        //验证码文本字符长度，默认为5
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        //验证码文本字体样式
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        //图片样式 阴影com.google.code.kaptcha.impl.ShadowGimpy 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
