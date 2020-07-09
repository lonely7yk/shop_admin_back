package com.lonely7yk.utils;

import org.springframework.validation.BindingResult;

import java.util.Map;

public class ValidUtils {
	/**
	 * 根据 result，生成对应的错误 json （map 形式）用于返回给客户端
	 * @param result
	 * @return
	 */
	public static Map<String, Object> generateInValidJson(BindingResult result) {
		String msg = result.getFieldError().getDefaultMessage();

		// Map<String, Object> map = new HashMap<>();
		// map.put("data", null);
		// Map<String, Object> meta = new HashMap<>();
		// meta.put("msg", msg);
		// meta.put("status", 400);
		// map.put("meta", meta);

		return MapUtils.generateErrorMap(msg);
	}
}
