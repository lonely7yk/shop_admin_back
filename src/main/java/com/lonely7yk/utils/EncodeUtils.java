package com.lonely7yk.utils;

import org.springframework.util.DigestUtils;

public class EncodeUtils {
	public static String MD5Encode(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}
}
