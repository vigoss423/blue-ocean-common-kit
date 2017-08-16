package com.blueocean.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


public class StringUtil {

	/**
	 * hxw 返回当前年
	 * 
	 * @return
	 */
	public static String getYear() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(1));
	}

	/**
	 * hxw 返回当前月
	 * 
	 * @return
	 */
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		String temp = String.valueOf(calendar.get(2) + 1);
		if (temp.length() < 2)
			temp = "0" + temp;
		return temp;
	}
	
	 /**
	  * list转成以,分隔的字符串
	 * @param stringList
	 * @return String
	 */
	public static String listToString(List<String> stringList){
	        if (stringList==null) {
	            return null;
	        }
	        StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        }
	        return result.toString();
	    }

	/**
	 * 按长度分割字符串
	 * 
	 * @param content
	 * @param len
	 * @return
	 */
	public static String[] split(String content, int len) {
		if (content == null || content.equals("")) {
			return null;
		}
		int len2 = content.length();
		if (len2 <= len) {
			return new String[] { content };
		} else {
			int i = len2 / len + 1;
			String[] strA = new String[i];
			int j = 0;
			int begin = 0;
			int end = 0;
			while (j < i) {
				begin = j * len;
				end = (j + 1) * len;
				if (end > len2)
					end = len2;
				strA[j] = content.substring(begin, end);
				// logger.info(strA[j]+"<br/>");
				j = j + 1;
			}
			return strA;
		}
	}

	public static boolean isEmail(String email) {
		boolean retval = false;
		String emailPattern = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+[.]([a-zA-Z0-9_-])+";
		retval = email.matches(emailPattern);
		// String msg = "NO MATCH: pattern:" + email + " regex: " +
		// emailPattern;

		if (retval) {
			// msg = "MATCH : pattern:" + email + " regex: " + emailPattern;
		}
		// logger.info(msg + " ");
		return retval;
	}

	/**
	 * 字节码转换成16进制字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * 字节码转换成自定义字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2string(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static byte[] string2byte(String hs) {
		byte[] b = new byte[hs.length() / 2];
		for (int i = 0; i < hs.length(); i = i + 2) {
			String sub = hs.substring(i, i + 2);
			byte bb = new Integer(Integer.parseInt(sub, 16)).byteValue();
			b[i / 2] = bb;
		}
		return b;
	}

	public static boolean empty(String param) {
		return param == null || param.trim().length() < 1;
	}

	public static String encode(String str, String code) {
		try {
			return URLEncoder.encode(str, code);
		} catch (Exception ex) {
			ex.fillInStackTrace();
			return "";
		}
	}

	public static String decode(String str, String code) {
		try {
			return URLDecoder.decode(str, code);
		} catch (Exception ex) {
			ex.fillInStackTrace();
			return "";
		}
	}

	public static String nvl(String param) {
		return param != null ? param.trim() : "";
	}

	public static int parseInt(String param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(param);
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseInt(String param) {
		return parseInt(param, 0);
	}

	public static long parseLong(String param) {
		long l = 0L;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
		}
		return l;
	}

	public static double parseDouble(String param) {
		return parseDouble(param, 1.0);
	}

	public static double parseDouble(String param, double t) {
		double tmp = 0.0;
		try {
			tmp = Double.parseDouble(param.trim());
		} catch (Exception e) {
			tmp = t;
		}
		return tmp;
	}

	public static boolean parseBoolean(String param) {
		if (empty(param))
			return false;
		switch (param.charAt(0)) {
		case 49: // '1'
		case 84: // 'T'
		case 89: // 'Y'
		case 116: // 't'
		case 121: // 'y'
			return true;

		}
		return false;
	}

	/**
	 * public static String replace(String mainString, String oldString, String
	 * newString) { if(mainString == null) return null; int i =
	 * mainString.lastIndexOf(oldString); if(i < 0) return mainString;
	 * StringBuffer mainSb = new StringBuffer(mainString); for(; i >= 0; i =
	 * mainString.lastIndexOf(oldString, i - 1)) mainSb.replace(i, i +
	 * oldString.length(), newString);
	 * 
	 * return mainSb.toString(); }
	 * 
	 */

	public static final String[] split(String str, String delims) {
		StringTokenizer st = new StringTokenizer(str, delims);
		ArrayList list = new ArrayList();
		for (; st.hasMoreTokens(); list.add(st.nextToken()))
			;
		return (String[]) list.toArray(new String[list.size()]);
	}

	// 英文字母或数据
	public static boolean isLetterOrDigit(String str) {
		java.util.regex.Pattern p = null; // 正则表达式
		java.util.regex.Matcher m = null; // 操作的字符串
		boolean value = true;
		try {
			p = java.util.regex.Pattern.compile("[^0-9A-Za-z]");
			m = p.matcher(str);
			if (m.find()) {

				value = false;
			}
		} catch (Exception e) {
		}
		return value;
	}

	// 汉字也为true
	public static boolean isLetterorDigit(String s) {
		if (s.equals("") || s == null) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				// if (!Character.isLetter(s.charAt(i))){
				return false;
			}
		}
		// Character.isJavaLetter()
		return true;
	}

	/**
	 * validate a int string
	 * 
	 * @param str
	 *            the Integer string.
	 * @return true if the str is invalid otherwise false.
	 */
	public static boolean validateInt(String str) {
		if (str == null || str.trim().equals(""))
			return false;

		char c;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				return false;
		}

		return true;
	}

	public static String substring(String str, int length) {
		if (str == null)
			return null;

		if (str.length() > length)
			return (str.substring(0, length - 2) + "...");
		else
			return str;
	}

	public static boolean validateDouble(String str) throws RuntimeException {
		if (str == null)
			// throw new RuntimeException("null input");
			return false;
		char c;
		int k = 0;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				if (!(i == 0 && (c == '-' || c == '+')))
					if (!(c == '.' && i < l - 1 && k < 1))
						// throw new RuntimeException("invalid number");
						return false;
					else
						k++;
		}
		return true;
	}

	public static boolean validateMobile(String str, boolean includeUnicom) {
		if (str == null || str.trim().equals(""))
			return false;
		str = str.trim();

		if (str.length() != 11 || !validateInt(str))
			return false;

		if (includeUnicom
				&& (str.startsWith("130") || str.startsWith("133") || str.startsWith("131")))
			return true;

		if (!(str.startsWith("139") || str.startsWith("138") || str.startsWith("137")
				|| str.startsWith("136") || str.startsWith("135")))
			return false;
		return true;
	}

	public static boolean validateMobile(String str) {
		return validateMobile(str, false);
	}

	/**
	 * delete file
	 * 
	 * @param fileName
	 * @return -1 exception,1 success,0 false,2 there is no one directory of the
	 *         same name in system
	 */
	public static int deleteFile(String fileName) {
		File file = null;
		int returnValue = -1;
		try {
			file = new File(fileName);
			if (file.exists())
				if (file.delete())
					returnValue = 1;
				else
					returnValue = 0;
			else
				returnValue = 2;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file = null;
			// return returnValue;
		}
		return returnValue;
	}

	public static String gbToIso(String s) throws UnsupportedEncodingException {
		return covertCode(s, "GB2312", "ISO8859-1");
	}

	public static String covertCode(String s, String code1, String code2)
			throws UnsupportedEncodingException {
		if (s == null)
			return null;
		else if (s.trim().equals(""))
			return "";
		else
			return new String(s.getBytes(code1), code2);
	}

	public static String transCode(String s0) throws UnsupportedEncodingException {
		if (s0 == null || s0.trim().equals(""))
			return null;
		else {
			s0 = s0.trim();
			return new String(s0.getBytes("GBK"), "ISO8859-1");
		}
	}

	public static String GBToUTF8(String s) {
		try {
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2) {
				out.append("\\u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					out.append("0");
				}
				out.append(str);
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				for (int j = str1.length(); j < 2; j++) {
					out.append("0");
				}

				out.append(str1);
			}
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String[] replaceAll(String[] obj, String oldString, String newString) {
		if (obj == null) {
			return null;
		}
		int length = obj.length;
		String[] returnStr = new String[length];
		String str = null;
		for (int i = 0; i < length; i++) {
			returnStr[i] = replaceAll(obj[i], oldString, newString);
		}
		return returnStr;
	}

	public static String replaceAll(String s0, String oldstr, String newstr) {
		if (s0 == null || s0.trim().equals(""))
			return null;
		StringBuffer sb = new StringBuffer(s0);
		int begin = 0;
		// int from = 0;
		begin = s0.indexOf(oldstr);
		while (begin > -1) {
			sb = sb.replace(begin, begin + oldstr.length(), newstr);
			s0 = sb.toString();
			begin = s0.indexOf(oldstr, begin + newstr.length());
		}
		return s0;
	}

	public static String toHtml(String str) {
		String html = str;
		if (str == null || str.length() == 0) {
			return str;
		}
		html = replaceAll(html, "&", "&amp;");
		html = replaceAll(html, "<", "&lt;");
		html = replaceAll(html, ">", "&gt;");
		html = replaceAll(html, "\r\n", "\n");
		html = replaceAll(html, "\n", "<br>\n");
		html = replaceAll(html, "\t", "         ");
		html = replaceAll(html, " ", "&nbsp;");
		return html;
	}

	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String escapeHTMLTags(String input) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}
		// Use a StringBuffer in lieu of String concatenation -- it is
		// much more efficient this way.
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * Returns a random String of numbers and letters of the specified length.
	 * The method uses the Random class that is built-in to Java which is
	 * suitable for low to medium grade security uses. This means that the
	 * output is only pseudo random, i.e., each number is mathematically
	 * generated so is not truly random.
	 * <p>
	 * 
	 * For every character in the returned String, there is an equal chance that
	 * it will be a letter or number. If a letter, there is an equal chance that
	 * it will be lower or upper case.
	 * <p>
	 * 
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */

	private static Random randGen = null;
	private static Object initLock = new Object();
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Init of pseudo random number generator.
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					// Also initialize the numbersAndLetters array
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static String getSubstring(String content, int i) {
		int varsize = 10;
		String strContent = content;
		if (strContent.length() < varsize + 1) {
			return strContent;
		} else {
			int max = (int) Math.ceil((double) strContent.length() / varsize);
			if (i < max - 1) {
				return strContent.substring(i * varsize, (i + 1) * varsize);
			} else {
				return strContent.substring(i * varsize);
			}
		}
	}

	public static String now(String pattern) {
		return dateToString(new Date(), pattern);
	}

	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}

	public static synchronized String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	public static java.sql.Date stringToDate(String strDate, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse(strDate);
		long lngTime = date.getTime();
		return new java.sql.Date(lngTime);
	}

	public static java.util.Date stringToUtilDate(String strDate, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(strDate);
	}

	public static String formatHTMLOutput(String s) {
		if (s == null)
			return null;

		if (s.trim().equals(""))
			return "";

		String formatStr;
		formatStr = replaceAll(s, " ", "&nbsp;");
		formatStr = replaceAll(formatStr, "\n", "<br />");

		return formatStr;
	}

	/*
	 * 4舍5入 @param num 要调整的数 @param x 要保留的小数位
	 */
	public static double myround(double num, int x) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(x, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/*
	 * public static String getSubstring(String content,int i){ int varsize=10;
	 * String strContent=content; if(strContent.length()<varsize+1){ return
	 * strContent; }else{ int
	 * max=(int)Math.ceil((double)strContent.length()/varsize); if(i<max-1){
	 * return strContent.substring(i*varsize,(i+1)*varsize); }else{ return
	 * strContent.substring(i*varsize); } } }
	 */

	private static boolean isLowerLetter(String str) {
		java.util.regex.Pattern p = null; // 正则表达式
		java.util.regex.Matcher m = null; // 操作的字符串
		boolean value = true;
		try {
			p = java.util.regex.Pattern.compile("[^a-z]");
			m = p.matcher(str);
			if (m.find()) {
				value = false;
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * liuqs
	 * 
	 * @param param
	 * @param d
	 * @return
	 */
	public static int parseLongInt(Long param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(String.valueOf(param));
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseLongInt(Long param) {
		return parseLongInt(param, 0);
	}

	/**
	 * hxw 返回套餐类型
	 * 
	 * @param flag
	 * @param code
	 * @return
	 */
	public static String getTaoCanName(String flag, String codeTemp) {
		String temp = "";
		String str[] = codeTemp.split("[+]");
		String code1 = str[0];
		String code2 = str[1];
		if (flag.equals("1")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("SWLBX333")) {
				temp = "333元商务礼包";
			} else if (code1.equals("SWLBX288")) {
				temp = "288元商务礼包";
			} else if (code1.equals("SWLBX188")) {
				temp = "188元商务礼包";
			} else if (code1.equals("SWLBX133")) {
				temp = "133元商务礼包";
			} else if (code1.equals("SWLBX88")) {
				temp = "88元商务礼包";
			} else if (code1.equals("SWZYX")) {
				temp = "商务自由型";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("SWLBX333")) {
				temp += "+333元商务礼包";
			} else if (code2.equals("SWLBX288")) {
				temp += "+288元商务礼包";
			} else if (code2.equals("SWLBX188")) {
				temp += "+188元商务礼包";
			} else if (code2.equals("SWLBX133")) {
				temp += "+133元商务礼包";
			} else if (code2.equals("SWLBX88")) {
				temp += "+88元商务礼包";
			} else if (code2.equals("SWZYX")) {
				temp += "+商务自由型";
			}

		}
		if (flag.equals("2") || flag.equals("5")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("UNI5")) {
				temp = "手机上网流量5套餐";
			} else if (code1.equals("UNI10")) {
				temp = "手机上网流量10套餐";
			} else if (code1.equals("UNI25")) {
				temp = "手机上网流量25套餐";
			} else if (code1.equals("UNI35")) {
				temp = "手机上网流量35套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("UNI5")) {
				temp += "+手机上网流量5套餐";
			} else if (code2.equals("UNI10")) {
				temp += "+手机上网流量10套餐";
			} else if (code2.equals("UNI25")) {
				temp += "+手机上网流量25套餐";
			} else if (code2.equals("UNI35")) {
				temp += "+手机上网流量35套餐";
			}
		}
		if (flag.equals("3")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("CTW8")) {
				temp = "8元接听500分钟畅听王套餐";
			} else if (code1.equals("CTW16")) {
				temp = "16元接听1000分钟畅听王套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("CTW8")) {
				temp += "+8元接听500分钟畅听王套餐";
			} else if (code2.equals("CTW16")) {
				temp += "+16元接听1000分钟畅听王套餐";
			}
		}
		if (flag.equals("4")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("CG50Z")) {
				temp = "炒股50-钻石版套餐";
			} else if (code1.equals("CG50T")) {
				temp = "炒股50-同花顺套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("CG50Z")) {
				temp += "+炒股50-钻石版套餐";
			} else if (code2.equals("CG50T")) {
				temp += "+炒股50-同花顺套餐";
			}
		}
		return temp;
	}

	/**
	 * hxw 返回套餐类型
	 * 
	 * @param flag
	 * @param code
	 * @return
	 */
	public static String getTaoCanName189(String flag, String codeTemp) {
		String temp = "";
		String str[] = codeTemp.split("[+]");
		String code1 = str[0];
		String code2 = str[1];
		if (flag.equals("1")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("620051")) {
				temp = "畅聊服务套餐49元包";
			} else if (code1.equals("620052")) {
				temp = "畅聊服务套餐89元包";
			} else if (code1.equals("620053")) {
				temp = "畅聊服务套餐129元包";
			} else if (code1.equals("620054")) {
				temp = "畅聊服务套餐189元包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("620051")) {
				temp += "+畅聊服务套餐49元包";
			} else if (code2.equals("620052")) {
				temp += "+畅聊服务套餐89元包";
			} else if (code2.equals("620053")) {
				temp += "+畅聊服务套餐129元包";
			} else if (code2.equals("620054")) {
				temp += "+畅聊服务套餐189元包";
			}
		}
		if (flag.equals("2")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("600006")) {
				temp = "商旅服务套餐89元包";
			} else if (code1.equals("600007")) {
				temp = "商旅服务套餐129元包";
			} else if (code1.equals("600008")) {
				temp = "商旅服务套餐189元包";
			} else if (code1.equals("600009")) {
				temp = "商旅服务套餐289元包";
			} else if (code1.equals("600010")) {
				temp = "商旅服务套餐589元包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("600006")) {
				temp += "+商旅服务套餐89元包";
			} else if (code2.equals("600007")) {
				temp += "+商旅服务套餐129元包";
			} else if (code2.equals("600008")) {
				temp += "+商旅服务套餐189元包";
			} else if (code2.equals("600009")) {
				temp += "+商旅服务套餐289元包";
			} else if (code2.equals("600010")) {
				temp += "+商旅服务套餐589元包";
			}
		}
		if (flag.equals("3")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610007")) {
				temp = "手机上网5元流量套餐";
			} else if (code1.equals("610008")) {
				temp = "手机上网10元流量套餐";
			} else if (code1.equals("610009")) {
				temp = "手机上网20元流量套餐";
			} else if (code1.equals("610010")) {
				temp = "手机上网50元流量套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610007")) {
				temp += "+手机上网5元流量套餐";
			} else if (code2.equals("610008")) {
				temp += "+手机上网10元流量套餐";
			} else if (code2.equals("610009")) {
				temp += "+手机上网20元流量套餐";
			} else if (code2.equals("610010")) {
				temp += "+手机上网50元流量套餐";
			}
		}
		if (flag.equals("4")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610031")) {
				temp = "彩信5元套餐";
			} else if (code1.equals("610032")) {
				temp = "彩信10元套餐";
			} else if (code1.equals("610033")) {
				temp = "彩信20元套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610031")) {
				temp += "+彩信5元套餐";
			} else if (code2.equals("610032")) {
				temp += "+彩信10元套餐";
			} else if (code2.equals("610033")) {
				temp += "+彩信20元套餐";
			}
		}
		if (flag.equals("5")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610021")) {
				temp = "短信5元套餐";
			} else if (code1.equals("610022")) {
				temp = "短信10元套餐";
			} else if (code1.equals("610023")) {
				temp = "短信15元套餐";
			} else if (code1.equals("610024")) {
				temp = "短信20元套餐";
			} else if (code1.equals("610025")) {
				temp = "短信30元套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610021")) {
				temp += "+短信5元套餐";
			} else if (code2.equals("610022")) {
				temp += "+短信10元套餐";
			} else if (code2.equals("610023")) {
				temp += "+短信15元套餐";
			} else if (code2.equals("610024")) {
				temp += "+短信20元套餐";
			} else if (code2.equals("610025")) {
				temp += "+短信30元套餐";
			}
		}
		if (flag.equals("6")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610041")) {
				temp = "七彩铃音半年包";
			} else if (code1.equals("610042")) {
				temp = "七彩铃音一年包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610041")) {
				temp += "+七彩铃音半年包";
			} else if (code2.equals("610042")) {
				temp += "+七彩铃音一年包";
			}
		}
		if (flag.equals("7")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610052")) {
				temp = "商务包";
			} else if (code1.equals("610053")) {
				temp = "娱乐包";
			} else if (code1.equals("610054")) {
				temp = "生活包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610052")) {
				temp += "+商务包";
			} else if (code2.equals("610053")) {
				temp += "+娱乐包";
			} else if (code2.equals("610054")) {
				temp += "+生活包";
			}
		}
		if (flag.equals("8")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610055")) {
				temp = "大众生活包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610055")) {
				temp += "+大众生活包";
			}
		}
		if (flag.equals("9")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("610043")) {
				temp = "七彩铃音特惠包";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("610043")) {
				temp += "+七彩铃音特惠包";
			}
		}
		if (flag.equals("0")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("600000")) {
				temp = "天翼标准套餐";
			}

			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("600000")) {
				temp += "+天翼标准套餐";
			}
		}
		if (flag.equals("10")) {
			if (code1.equals("0")) {
				temp = "无";
			} else if (code1.equals("601003")) {
				temp = "2G-50元套餐";
			} else if (code1.equals("601004")) {
				temp = "2G-100元套餐";
			} else if (code1.equals("630001")) {
				temp = "3G-160元套餐";
			} else if (code1.equals("630002")) {
				temp = "3G-200元套餐";
			} else if (code1.equals("630003")) {
				temp = "3G-300元套餐";
			}
			if (code2.equals("0")) {
				temp += "+无";
			} else if (code2.equals("601003")) {
				temp += "+2G-50元套餐";
			} else if (code2.equals("601004")) {
				temp += "+2G-100元套餐";
			} else if (code2.equals("630001")) {
				temp += "+3G-160元套餐";
			} else if (code2.equals("630002")) {
				temp += "+3G-200元套餐";
			} else if (code2.equals("630003")) {
				temp += "+3G-300元套餐";
			}
		}
		return temp;
	}

	public static String returnString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 判断某字符串是否为null，如果长度大于256，则返回256长度的子字符串，反之返回原串
	 * @param str
	 * @return
	 */
	public static String checkStr(String str){
		if(str==null){
			return "";
		}else if(str.length()>256){
			return str.substring(256);
		}else{
			return str;
		}
	}
	
	/**
     * 修改敏感字符编码
     * 
     * @param value
     * @return
     */
	public static String htmlEncode(String value){
	    String re[][] = {{"<","&lt;"},
	                     {">","&gt;"},
	                     {"\"","&quot;"},
	                     {"\\′","&acute;"},
	                     {"&","&amp;"}
	                    };
	   
	    for(int i=0; i<4; i++){
	        value = value.replaceAll(re[i][0], re[i][1]);
	    }
	   
	    return value;
	}
	/**
     * 防SQL注入
     * 
     * @param str
     * @return
     */
	public static boolean sql_inj(String str) 
	 {
		 String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		 String inj_stra[] = inj_str.split("|");
		 for (int i=0 ; i < inj_stra.length ; i++ )
		 {
			 if (str.indexOf(inj_stra[i])>=0)
			 {
			 	return true;
			 }
		 }
		 return false;
	 }
	
	/**
	 * 检查输入项是否有不合法字符
	 * 返回false代表输入字符不合法，反之相反
	 * @param str
	 * @return
	 */
	public static boolean checkString(String str) {
		String strtmp = "%\\/()><;#$&[]{}'-";
		boolean bl = true;
		if (str != null && !str.equals("")) 
			for (int i = 0; i < str.length(); i++) {
				if (strtmp.indexOf(str.charAt(i)) > -1) {
					bl = false;
					break;
				}
			}
		else{
			bl=false;
		}
		return bl;

	}
	/**
	 * 检查输入项是否有不合法字符(除掉"-")
	 * 返回false代表输入字符不合法，反之相反
	 * @param str
	 * @return
	 */
	public static boolean checkStrExceptSign(String str) {
		String strtmp = "%\\/()><;#$&[]{}'";
		boolean bl = true;
		if (str != null && !str.equals("")) 
			for (int i = 0; i < str.length(); i++) {
				if (strtmp.indexOf(str.charAt(i)) > -1) {
					//logger.info("str:"+str.charAt(i));
					bl = false;
					break;
				}
			}
		else{
			bl=false;
		}
		return bl;

	}
	
	public static void main(String[] args) {
		String s = "<中国>delete";
		//logger.info(sql_inj(s));
		
//		logger.info("4移动用户:"+StringUtil.GBToUTF8("移动用户"));
//		logger.info("2固话用户:"+StringUtil.GBToUTF8("固话用户"));
//		logger.info("5宽带用户:"+StringUtil.GBToUTF8("宽带用户"));
//		logger.info("10注册帐号:"+StringUtil.GBToUTF8("注册帐号"));
//		logger.info("1客户编码:"+StringUtil.GBToUTF8("客户编码"));
			
		//logger.info("支付宝:"+StringUtil.GBToUTF8("支付宝"));
		
		/*logger.info("1身份证:"+StringUtil.GBToUTF8("身份证"));
		logger.info("4护照:"+StringUtil.GBToUTF8("护照"));
		logger.info("2军官证:"+StringUtil.GBToUTF8("军官证"));
		logger.info("12其他:"+StringUtil.GBToUTF8("其他"));*/
	}
	
	/**
     * 判断是否为为空值
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if(str==null||str.length()==0 || "null".equalsIgnoreCase(str))
        return true; else return false;
    }
    /**
     * 判断数组里面是否存在空值
     * @param str
     * @return
     */
    public static boolean isNullArray(String[] str) {
    	if(str==null)return true;
        for(int i=0;i<str.length;i++){
    	   if(str[i]==null||str[i].length()==0)return true;
        }
        return false;
    }
    
    public static String isNullStr(String str) {
        if(str==null||str.length()==0 || "null".equalsIgnoreCase(str))	{
        	return "";
        }else	{
        	return str;
        }
    }
    

	/**
	 * 
	 * @param obj
	 * @return 为空返回true
	 * @Description:判断对象是否是空，字符串空，字符序列空，集合空，map空，对象数组空
	 * @Author:Dable
	 * @Since:2014年7月9日
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String) {
			if ("".equals(obj) || "null".equals(obj) || "undefined".equals(obj)) {
				return true;
			}
		}
		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof List) {
			return ((List) obj).size() == 0;
		}
		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			boolean empty = true;
			for (int i = 0; i < object.length; i++)
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			return empty;
		}
		return false;
	}
	
	/**
	  * 
	  * @param text 目标字符串
	  * @param length 截取长度
	  * @param encode 采用的编码方式
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 public static String substring(String text, int length, String encode)
	   throws UnsupportedEncodingException {
	  if (text == null) {
	   return null;
	  }
	  StringBuilder sb = new StringBuilder();
	  int currentLength = 0;
	  for (char c : text.toCharArray()) {
	   currentLength += String.valueOf(c).getBytes(encode).length;
	   if (currentLength <= length) {
	    sb.append(c);
	   } else {
	    break;
	   }
	  }
	  return sb.toString();

	 }
	 
	     /**
	     * 动态解析“|”隔开的字符
	     */
	    public static List<String> parseStrToList(String paramStr) 
	    {
	        Scanner s = new Scanner(paramStr);
	        List<String>  list=new ArrayList<String>();
	        s.useDelimiter("\\|");
	        while (s.hasNext())
	        { 
	            String tmp=s.next();
	            list.add(tmp);
	        }
	        return list;
	    }
}
