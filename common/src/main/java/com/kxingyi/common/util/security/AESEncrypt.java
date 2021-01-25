package com.kxingyi.common.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AESEncrypt {
    private final static String ENCODE = "UTF-8";

    /**
     * 由服务端提供给调用者的一个用于数据加密的共享密钥
     */
    private String _publicKey = null;

    /**
     * @param publicKey AES密钥
     */
    public AESEncrypt(String publicKey) {
        _publicKey = publicKey;
    }

    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String ivParameter = "1234567890abcdef";

    public String decrypt(String data) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(
                    _publicKey.getBytes("ASCII"), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String encrypt(String data) {
        try {
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            SecretKeySpec sKeySpec = new SecretKeySpec(_publicKey.getBytes(),
                    KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
		//该密钥为4A平台提供的公钥
        AESEncrypt aes = new AESEncrypt("S1tV8tUQdtXGrPge");
        try {
            String s =URLEncoder.encode(aes.encrypt("{\n" +
                    "\t\"resName\":\"105.131_mysql\"\n" +
                    "}"),"UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
