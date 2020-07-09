package com.lonely7yk.controller;

import com.lonely7yk.service.PermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PermsController {
	@Autowired
	private PermsService permsService;

	/**
	 * 获取侧边栏的 Permissions
	 * @return
	 */
	@GetMapping("/menus")
	public Map<String, Object> menus() {
		Map<String, Object> map = new HashMap<>();
		map.put("data", permsService.getMenuPerms());

		Map<String, Object> meta = new HashMap<>();
		meta.put("msg", "获取菜单列表成功");
		meta.put("status", 200);
		map.put("meta", meta);

		return map;
	}
}
