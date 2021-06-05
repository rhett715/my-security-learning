package org.rhett.mysecurity.utils.rsa;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "rsa.key", ignoreInvalidFields = true)
public class RsaKeyProperties {
    private String publicKeyPath;
    private String privateKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * 该方法用于初始化公钥和私钥的内容
     */
    @PostConstruct
    public void loadRsaKey() throws Exception {
        if (publicKeyPath != null) {
            publicKey = RsaUtil.getPublicKey(publicKeyPath);
        }
        if (privateKeyPath != null) {
            privateKey = RsaUtil.getPrivateKey(privateKeyPath);
        }
    }
}
