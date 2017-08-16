package com.blueocean.common.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	private SecretKey sSecretKey = null;
	private Cipher sCipher = null; 
	private String sKeyString = "3A025A80429AD2AC";// 秘钥

	public AES() {
		try {
			sSecretKey = new SecretKeySpec(sKeyString.getBytes(), "AES");
			sCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    


    public static AES getInstance() {
        return Nested.instance;
    }
    //于内部静态类只会被加载一次，故该实现方式时线程安全的！
    static class Nested {
        private static AES instance = new AES();
    }

    public static String GetMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = null;
		try {
			byteArray = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	public String aesEncrypt(String message) {
		String result = "";
		String newResult = "";
		try {
			sCipher.init(Cipher.ENCRYPT_MODE, sSecretKey); 
			byte[] resultBytes = sCipher.doFinal(message.getBytes("UTF-8")); 
			result = new String(Base64.encode(resultBytes,Base64.DEFAULT));
			newResult = filter(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newResult;
	}

	public String aesDecrypt(String message) {
		String result = "";
		try {
			byte[] messageBytes = Base64.decode(message, Base64.DEFAULT); 
			sCipher.init(Cipher.DECRYPT_MODE, sSecretKey); 
			byte[] resultBytes = sCipher.doFinal(messageBytes);
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String filter(String str) {
		String output = "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc != 10 && asc != 13) {
				sb.append(str.subSequence(i, i + 1));
			}
		}
		output = new String(sb);
		return output;
	}
	public static void main(String[] args) {
		  // 需要加密的字串
        String cSrc = "{\"userName\": \"qwe2\"," + 
        		"\"passWord\": \"123456\"," + 
        		"\"udid\": \"92834387239874\"}";
        String cSrc1 = "FVvyqxukO+P86cKWvYhJlFdXI2lY9ENMYXgKj8AtM9HIQNf0So70dpm6ncG8E9v3kEhc3YXR3K5I105pGfet8yeLYC1DrL8R8W3F+wXYeQn/kEnhRYZTIb/M4GR5ThL5K1eTJHI4fLEtr9Kj37/jvmHcmsalCPgFAlNx7oxH7Y801Hvlsooq6oH5fkGWBQJ/djhhxhMn93vunlP2Y5xutWsYev7XeON5DOsbt/gbzAYrm/ZuxkCWoKkCT8QePkQX";
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AES.getInstance().aesEncrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AES.getInstance().aesDecrypt(cSrc1);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
	}

}