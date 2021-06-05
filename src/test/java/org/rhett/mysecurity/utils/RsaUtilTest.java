package org.rhett.mysecurity.utils;

import org.junit.jupiter.api.Test;
import org.rhett.mysecurity.utils.rsa.RsaUtil;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 */
public class RsaUtilTest {
    private final String publicFile = "D:\\auth_key\\rsa_key.pub";
    private final String privateFile = "D:\\auth_key\\rsa_key";
    private final String secret = "rgebeeit32e21grd32d54oe65ryesw23";

    @Test
    public void generateKey() throws Exception {
        RsaUtil.generateKey(publicFile, privateFile, secret, 2048);
    }
}
