package com.kxingyi.common.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: wu_chao
 * @date: 2020/10/12
 * @time: 19:44
 */
public class RsaUtil {

    public static final String RSA_ALGORITHM_KEY = "RSA";
    public static final Charset UTF8 = Charset.forName("UTF-8");

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM_KEY);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }


    public static String decrypt(String privateKey, String ciper) throws Exception {
        byte[] buffer = base64Decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_KEY);
        PrivateKey pkObj = keyFactory.generatePrivate(keySpec);
        byte[] encrypted = base64Decode(ciper);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_KEY);
        cipher.init(Cipher.DECRYPT_MODE, pkObj);
        byte[] re = cipher.doFinal(encrypted);
        return new String(re, UTF8);
    }

    public static String encrypt(String publicKey, String message) throws Exception {
        //base64编码的公钥
        byte[] decoded = base64Decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA_ALGORITHM_KEY)
                .generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_KEY);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(message.getBytes(UTF8)));
        return outStr;
    }


    public static String base64Encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    public static byte[] base64Decode(String data) throws IOException {
        return new BASE64Decoder().decodeBuffer(data);
    }
}
