package com.blueocean.common.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {
	/**
	 * 验证是否为null或空串
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrBlank(Object obj) {
		return obj == null || "".equals(obj);
	}

	/**
	 * 验证集合是否为空
	 * 
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Collection<? extends Object> coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * 校验手机号合法性
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
	    Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}
	/**
	 * 判断一个对象是否为空：对象为null 或 对象的所有属性为null或""
	 * @param mobiles
	 * @return
	 */
	public static boolean checkFieldValueNull(Object bean){  
		boolean result = true;
        if (bean == null) {
            return true;
        }
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
        	field.setAccessible(true);
    	    try {
				if (field.get(bean) != null && !"".equals(field.get(bean))) {//判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					result = false;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return result;  
	}
	/**
	 * 判断Map集合不为空（或null）
	 * @param classFileMap
	 * @return
	 */
	public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * 判断字符串中是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
