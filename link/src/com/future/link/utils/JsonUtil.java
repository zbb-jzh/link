package com.future.link.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	
	/**
	 *  转换前台传来的json对象
	 * @param json
	 * @param clazz
	 * @param <T>
     * @return
     */
	public static <T> T parseJsonToObject(String json,Class<T> clazz){
		return JSON.parseObject(json,clazz);
	}

}
