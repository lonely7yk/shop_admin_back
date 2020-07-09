package com.lonely7yk.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	public static Map<String, Object> generateErrorMap(String errorMsg) {
		return generateMap(null, errorMsg, 400);
	}

	public static Map<String, Object> generateMap(Object data, String msg, int statusCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("data", data);
		Map<String, Object> meta = new HashMap<>();
		meta.put("msg", msg);
		meta.put("status", statusCode);
		map.put("meta", meta);
		return map;
	}
}
