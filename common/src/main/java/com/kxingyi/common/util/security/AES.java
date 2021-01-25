package com.kxingyi.common.util.security;



import com.kxingyi.common.util.security.ext.UnionAesSecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.UUID;


/**
 * AES加密类，支持 128、256位密钥的加密。
 *
 * @author
 */
public class AES {
	private static String strDefaultKey = "9EFFAB31-0438-41BB-A9FD-3B811A6E7D37";
	private String key;
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	private static AES aes = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 *
	 * @param buf 需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	public String byteArr2HexStr(byte[] buf) {
		int iLen = buf.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = buf[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 *
	 * @param strIn 需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 * @author
	 */
	public byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 *
	 * @throws Exception
	 */
	public AES() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 *
	 * @param strKey 指定的密钥
	 * @throws Exception
	 */
	public AES(String strKey) throws Exception {
		if (strKey == null || "".equals(strKey)) {
			strKey = strDefaultKey;
		}
		KeyGenerator kgen = KeyGenerator.getInstance("AES");// 获取密匙生成器
		SecureRandom secureRandom = UnionAesSecureRandom.getInstance();
		secureRandom.setSeed(strKey.getBytes());
		kgen.init(128, secureRandom);
		SecretKey skey = kgen.generateKey(); // 生成密匙
		byte[] raw = skey.getEncoded();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		encryptCipher = Cipher.getInstance("AES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		decryptCipher = Cipher.getInstance("AES");
		decryptCipher.init(Cipher.DECRYPT_MODE, skeySpec);
	}

	public static AES getInstance() {
		if (aes == null) {
			try {
				aes = new AES();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return aes;
	}

	/**
	 * 获取AES加密类实例，使用指定的密钥
	 *
	 * @param key String 密钥
	 * @return AES实例
	 */
	public static AES getInstance(String key) {
		if (aes == null) {
			if (key != null && !key.equals("")) {
				strDefaultKey = key;
			}
			try {
				aes = new AES(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (key != null && !key.equals("") && !key.equals(strDefaultKey)) {
				aes = null;
				strDefaultKey = key;
				try {
					aes = new AES(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return aes;
	}

	/**
	 * 加密字节数组
	 *
	 * @param arrB 需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 *
	 * @param strIn 需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组
	 *
	 * @param arrB 需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 *
	 * @param strIn 需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(UUID.randomUUID().toString());
		// 小于15个长度，加密的密文为32位
		String message = "123qwe";
		AES a = new AES();//AES.getInstance("venustech_tbu_venus4A") ;
		String en = a.encrypt(message);

		System.out.println("加密==长度" + en.length() + "密文=" + en);
		System.out.println("解密==" + a.decrypt(en));
		System.out.println(new String(a.decrypt(a.encrypt(message.getBytes()))));
		System.out.println(a.decrypt(a.encrypt(message)));
	}
}