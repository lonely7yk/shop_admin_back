package com.lonely7yk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lonely7yk.constant.Constant;
import com.lonely7yk.pojo.dto.UserLogin;

public class TokenUtils {
	private static JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(Constant.TOKEN_SECRET)).build();

	/**
	 * 创建 token，把 user 的 username 存储到 token 中
	 * @param user
	 * @return
	 */
	public static String createToken(UserLogin user) {
		String token = "";
		token = JWT.create().withClaim("username", user.getUsername()).sign(Algorithm.HMAC256(Constant.TOKEN_SECRET));
		return token;
	}

	/**
	 * 验证 token 签名是否合法
	 * @param token
	 * @return
	 */
	public static boolean verifyToken(String token) {
		try {
			jwtVerifier.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}
}
