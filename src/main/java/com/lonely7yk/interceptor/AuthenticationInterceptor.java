package com.lonely7yk.interceptor;

import com.auth0.jwt.JWT;
import com.lonely7yk.annotation.PassToken;
import com.lonely7yk.utils.JsonUtils;
import com.lonely7yk.utils.TokenUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 不是方法直接放行
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 如果有 PassToken 注解，直接放行
		Method method = ((HandlerMethod) handler).getMethod();
		if (method.isAnnotationPresent(PassToken.class)) {
			return true;
		}

		// 验证 token
		String token = request.getHeader("Authorization");
		if (token == null || !TokenUtils.verifyToken(token)) {
			Map<String, Object> map = new HashMap<>();
			map.put("data", null);
			Map<String, Object> meta = new HashMap<>();
			meta.put("msg", "无效token");
			meta.put("status", 400);
			map.put("meta", meta);

			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(JsonUtils.toJson(map));
			return false;
		}

		// 从 token 中获取 username
		String username = JWT.decode(token).getClaim("username").asString();
		request.setAttribute("username", username);

		return true;
	}
}
