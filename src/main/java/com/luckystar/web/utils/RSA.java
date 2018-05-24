package com.luckystar.web.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 1、公钥加密，私钥解密用于信息加密
 * 2、私钥加密，公钥解密用于数字签名
 */
public class RSA {

	static String publicKey;
    static String privateKey;

    static {
    	publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCg5vDzgK2CeS3g9Jm7a33diyjrKo5xcoE2A0A3jKC9+dNxszj++E2Tg7++LBMm5YW/6hNsrWjLWUzB7GVtfLZGU3wY+9ln81kiz9LS24qaXUpSZN+C1/pTlj4WSq5MjXlPRFRmVsDaGrh3eJ68Jt0ec5Cgffl7Sko2eQSpkjoruwIDAQAB";
    	privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKDm8POArYJ5LeD0mbtrfd2LKOsqjnFygTYDQDeMoL3503GzOP74TZODv74sEyblhb/qE2ytaMtZTMHsZW18tkZTfBj72WfzWSLP0tLbippdSlJk34LX+lOWPhZKrkyNeU9EVGZWwNoauHd4nrwm3R5zkKB9+XtKSjZ5BKmSOiu7AgMBAAECgYB66pFtY4KpJotuy0zZiS843EMhC3yPm+qraWZYSTzOhBgMRt6moOcaZs5GqALldvdq+ZVnMz3YAlsJ1d5R+rB+50jd9D1QXgd7DIzqYDYrE0pNdgXflUYQJslzH9AjYr5IRqqm8RZmVeZbYzQKxgXzq66cj+mi/FECTuJXTrA+qQJBAOBV3vB31YN36dxbaxCKEes0CSlLrijl1hh3cBSZC/7c53PFge6VMuHwlhW7GF91rbaVcLenGW8y1Z8hovgmh1cCQQC3nPkQPMnfzoXEzqj6M15UaY7rwu1CB4VDrhsKMEuxztAXb0ngq4fn9fClk0s0gQOKEOwXeD312CtCNMRRO/Q9AkEA0pvrlEh8nFN8q5Lr3fgxwGX2390cIWs9Z3TiNYRFvbz4UjkvtaQ+w92Rmc8vn5ckQhDkZJatCzdnVGFI1b2eOwJBAIHYpmGPO4vkNsEpDGJztT+oroOXg8VwIBPuqfyYjs8dXBdWgj45z5CAYkAVW9ezbNdUhEFMrTSQPHdDbZms9lkCQBXn/6oPM16O3TApRj0+m3I03BkUtdhCbtV9qj6sV06fmBLZbCuzzokT1vgQAz2GduklNGJ1Ud8EH81wHpgKnGU=";
    }

    //定义加密方式
    public static final String KEY_RSA = "RSA";
    //定义公钥关键词
    public static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    //定义私钥关键词
    public static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";
    //定义签名算法
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";


    /**
     * 生成公私密钥对
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(1024,random);

            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥对封装为Map
            map = new HashMap<String, Object>();
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取Base64编码的公钥字符串
     */
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
        str = encryptBase64(key.getEncoded());
        return str;
    }

    /**
     * 获取Base64编码的私钥字符串
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
        str = encryptBase64(key.getEncoded());
        return str;
    }

    /**
     * BASE64 解码
     * @param key 需要Base64解码的字符串
     * @return 字节数组
     */
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * BASE64 编码
     * @param key 需要Base64编码的字节数组
     * @return 字符串
     */
    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    /**
     * 公钥加密
     * @param encryptingStr
     * @return
     */
    public static String encryptByPublic(String encryptingStr){
        try {
            // 将公钥由字符串转为UTF-8格式的字节数组
            byte[] publicKeyBytes = decryptBase64(publicKey);
            // 获得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes("UTF-8");
            KeyFactory factory;
            factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 返回加密后由Base64编码的加密信息
            return encryptBase64(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    /**
     * 私钥解密
     * @param encryptedStr
     * @return
     */
    public static String decryptByPrivate(String encryptedStr){
        try {
            // 对私钥解密
            byte[] privateKeyBytes = decryptBase64(privateKey);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 获得待解密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 返回UTF-8编码的解密信息
            return new String(cipher.doFinal(data), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    
    /**
     * 用私钥对加密数据进行签名
     * @param encryptedStr
     * @param privateKey
     * @return
     */
    public static String sign(String encryptedStr, String privateKey) {
        String str = "";
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的私钥
            byte[] bytes = decryptBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取私钥对象
            PrivateKey key = factory.generatePrivate(pkcs);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = encryptBase64(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 校验数字签名
     * @param encryptedStr
     * @param publicKey
     * @param sign
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String encryptedStr, String publicKey, String sign) {
        boolean flag = false;
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的公钥
            byte[] bytes = decryptBase64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取公钥对象
            PublicKey key = factory.generatePublic(keySpec);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
