package com.lonely7yk.controller;

import com.lonely7yk.pojo.po.Perms;
import com.lonely7yk.service.PermsService;
import com.lonely7yk.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

	/**
	 * 获取所有权限列表
	 * @param type list 或 tree
	 * @return
	 */
	@GetMapping("/rights/{type}")
	public Map<String, Object> perms(@PathVariable String type) {
		if (type.equals("tree")) {
			List<Perms> permsList = permsService.getAllPermsTree();
			return MapUtils.generateMap(permsList, "获取权限列表成功", 200);
		} else if (type.equals("list")) {
			List<Map<String, Object>> permsList = permsService.getAllPermsList();
			return MapUtils.generateMap(permsList, "获取权限列表成功", 200);
		} else {
			return MapUtils.generateErrorMap("参数错误");
		}
	}
}
