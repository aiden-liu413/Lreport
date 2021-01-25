package com.kxingyi.common.util.security;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesTool {

	public static final String KEY_ALGORITHM = "AES";
	public static final String CIPHER_ALGORITHM = "AES/CTR/PKCS5Padding";
	public static final String ivParameter = "1234567890abcdef";

	/**
	 * 加密
	 * @param key 接口约定密钥
	 * @param text 接口传递的json参数
	 * @return
	 */
	public static String Encrypt(String key, String text) {
		try {
			byte[] keyBytes = key.getBytes();
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			SecretKeySpec sKeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
			byte[] encrypted = cipher.doFinal(text.getBytes("utf-8"));
			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	/**
	 * 解密
	 * @param Key 接口约定密钥
	 * @param text 密文字符串
	 * @return
	 */
	public static String Decrypt(String Key, String text) {
		try {
			byte[] raw = Key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());			
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(text);			
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 用于生成加密的key
	 * @return
	 */
	public static String generateKey(){
		String storeStr="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
		int len=16;
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < len; i++) {
			double index=Math.floor(Math.random()*storeStr.length());
			sb.append(storeStr.charAt((int)index));
		}
		return sb.toString();
	}
	 
	 public static void main(String[] args) throws Exception {
		 String value = AesTool.Encrypt("HdOtBiBfhi5FD1CJ", "{\n" +
				 "\t\"user\":[],\n" +
				 "\t\"account\":[],\n" +
				 "\t\"resource\":[],\n" +
				 "\t\"accessLimit\":[],\n" +
				 "\t\"timeLimit\":[],\n" +
				 "\t\"accessAuthorization\":{\"id\":[\"2348\"],\"type\":\"USER\"},\n" +
				 "\t\"action\":\"UPDATE\"\n" +
				 "}");
		 System.out.println(value);
		 System.out.println(AesTool.Decrypt("dl0OpCsuOmMr4JOj","imIHojenOQ7FCxMIeC9svqX9ciWjff1xuzxTYPTeZnPw4hBL0Poon494qMF4edtCV6DbcdmFThRE\n" +
				 "Xd9ItK1WBACJzCJYqbjuadDy2ZQ7/MhRa5eW/jZQf1i10bx0PZxZPz9UEU1XGJgl/yrdY6aOizOk\n" +
				 "jPqT4lNz7sV3DVofnViZWI4URIxKeaRwMaQJGhbxXxR58ud21rIom4o="));
	}
}
