package com.lonely7yk.controller;

import com.lonely7yk.utils.EncodeUtils;
import com.lonely7yk.annotation.PassToken;
import com.lonely7yk.pojo.po.Manager;
import com.lonely7yk.pojo.dto.UserLogin;
import com.lonely7yk.service.ManagerService;
import com.lonely7yk.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
	@Autowired
	private ManagerService managerService;

	@PassToken
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody UserLogin user) {
		Map<String, Object> map = new HashMap<>();

		String username = user.getUsername();
		String password = user.getPassword();
		Manager manager = managerService.queryManagerByName(username);

		// 账户不存在
		if (manager == null) {
			map.put("data", null);
			Map<String, Object> meta = new HashMap<>();
			meta.put("msg", "用户名不存在");
			meta.put("status", 400);
			map.put("meta", meta);
			return map;
		}

		// 密码错误
		if (password == null || !EncodeUtils.MD5Encode(password).equals(manager.getMgPwd())) {
			map.put("data", null);
			Map<String, Object> meta = new HashMap<>();
			meta.put("msg", "密码错误");
			meta.put("status", 400);
			map.put("meta", meta);
			return map;
		}

		// data
		Map<String, Object> data = new HashMap<>();
		data.put("id", manager.getMgId());
		data.put("rid", manager.getRole().getRoleId());
		data.put("username", manager.getMgName());
		data.put("mobile", manager.getMgMobile());
		data.put("email", manager.getMgEmail());
		data.put("token", TokenUtils.createToken(user));
		map.put("data", data);
		// meta
		Map<String, Object> meta = new HashMap<>();
		meta.put("msg", "登录成功");
		meta.put("status", 200);
		map.put("meta", meta);
		return map;
	}
}
