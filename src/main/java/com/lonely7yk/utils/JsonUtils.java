package com.lonely7yk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lonely7yk.pojo.po.Perms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

	public static String toJson(Object object) {
		return toJson(object, "yyyy-MM-dd HH:mm:ss");
	}

	public static String toJson(Object object, String dateFormat) {
		ObjectMapper objectMapper = new ObjectMapper();
		//不使用时间戳的方式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//指定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		objectMapper.setDateFormat(sdf);

		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 使用 jackson 实现对象的深拷贝
	 * @param origin 原始对象
	 * @param clz 对象的反射类型
	 * @return
	 * @throws JsonProcessingException
	 */
	public static Object jacksonCopy(Object origin, Class<?> clz) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(objectMapper.writeValueAsString(origin), clz);
	}

	public static void main(String[] args) throws JsonProcessingException {
		Perms perms = new Perms();
		perms.setAuthName("111");
		List<Perms> children = new ArrayList<>();
		Perms tmp = new Perms();
		tmp.setAuthName("111_1");
		children.add(tmp);
		perms.setChildren(children);

		Perms copy = (Perms) jacksonCopy(perms, Perms.class);
		copy.setAuthName("222");
		// copy.getChildren().get(0).setAuthName("222_2");

		System.out.println(perms);
		System.out.println(copy);
	}
}