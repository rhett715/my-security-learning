package org.rhett.mysecurity.utils.rsa;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * Rsa算法工具类
 */
public class RsaUtil {
    private static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 从文件中读取公钥
     * @param fileName 公钥保存路径，相对于classpath
     * @return 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String fileName) throws Exception {
        byte[] bytes = readFile(fileName);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取私钥
     * @param fileName 私钥保存路径，相对于classpath
     * @return 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String fileName) throws Exception {
        byte[] bytes = readFile(fileName);
        return getPrivateKey(bytes);
    }

    /**
     * 根据密文，生成rsa公钥和私钥，并写入指定文件
     * @param publicKeyFilename 公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret 生成密钥的密文
     * @param keySize 密文尺寸
     * @throws Exception 异常
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws Exception {
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        rsa.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = rsa.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }


    /**
     * 读取文件
     * @param fileName 保存路径
     * @return 文件内容字节表示
     * @throws IOException 异常
     */
    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    /**
     * 获取公钥
     * @param bytes 公钥的字节形式
     * @return 公钥
     * @throws Exception 异常
     */
    private static PublicKey getPublicKey(byte[] bytes) throws Exception {
        byte[] decode = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return rsa.generatePublic(spec);
    }

    /**
     * 获取私钥
     * @param bytes 私钥的字节形式
     * @return 私钥
     * @throws Exception 异常
     */
    private static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        byte[] decode = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return rsa.generatePrivate(spec);
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File file = new File(destPath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), bytes);
    }
}
