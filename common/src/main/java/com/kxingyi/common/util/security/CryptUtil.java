package com.kxingyi.common.util.security;

public class CryptUtil {

	/**
	 * 解密函数
	 *
	 * @param encryptString String 已经加密的字符串
	 * @return String 解密后的字符串
	 */
	public static String decrypt(String encryptString) {
		if (AES.getInstance() != null) {
			try {
				return AES.getInstance().decrypt(encryptString);
			} catch (Exception e) {
				return encryptString;
			}
		} else {
			return encryptString;
		}
	}


	/**
	 * 加密函数
	 *
	 * @param originString String 原始字符串，即需要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String encrypt(String originString) {
		if (AES.getInstance() != null) {
			try {
				return AES.getInstance().encrypt(originString);
			} catch (Exception e) {
				return originString;
			}
		} else {
			return originString;
		}
	}

	public static void main(String[] args) {
		System.out.println(CryptUtil.decrypt("ea277bd635bfc00d1480a6ca33aee251"));

//		String t0 = "ec895b103e184afda21406a7e6d9ce05de6af24067d3e701fbe93b008115d4fe";
//		String t1 = "44ce8e005327f3caec08d47b9eb4a917de6af24067d3e701fbe93b008115d4fe";
//		String t2 = "cee24dd1612c37e96388dc738326864749ce2f0e978ec3999f62856fc66d3a96";
//		String t3 = "722c19e054b4b0d7ffe5a760a07dd562c428812abce53322166bff706d1dbd2a8901f0e6b9e94a250dd6cccb9d54a639d0227f914fb8a2b596a90b81192ede4d";
//		String t4 = "4133bf111179b843374eef65aa6ceeca703957495e4138028cca283f0e6bdfbd391a3dccbf8c9f223ee20ad9189829e8796902de9534d8fab76c4769397167774293214bf22c5f93babccaffa225c76691d0d003e39491ed83653f34f14ea1dc";
		
		/*try {
			System.out.println(URLDecoder.decode(CryptUtil.decrypt(t2.trim()), "utf-8"));;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println(CryptUtil.encrypt(""));
	}
}
