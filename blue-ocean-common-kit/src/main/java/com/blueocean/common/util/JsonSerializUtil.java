package com.blueocean.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * json 序列化工具类
 * @author Administrator
 *
 */
public class JsonSerializUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	/**
	 * java对象转为json字符串
	 * @param obj
	 * @return
	 */
	public static String javaToJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {}
		return null;
	}
	/**
	 * java对象转为json字符串
	 * @param obj
	 * @return
	 */
	public static <T> T jsonToJava(String json,Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
