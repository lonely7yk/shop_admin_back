package com.lonely7yk.controller;

import com.lonely7yk.pojo.dto.QueryInfo;
import com.lonely7yk.pojo.dto.UserAdd;
import com.lonely7yk.pojo.po.Manager;
import com.lonely7yk.service.ManagerService;
import com.lonely7yk.utils.EncodeUtils;
import com.lonely7yk.utils.MapUtils;
import com.lonely7yk.utils.ValidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ManagerController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ManagerService managerService;

	/**
	 * 获取用户列表
	 * @param queryInfo
	 * @param result
	 * @return
	 */
	@GetMapping("/users")
	public Map<String, Object> users(@Valid QueryInfo queryInfo, BindingResult result) {
		// 如果验证出错，返回错误信息的 json
		if (result.hasErrors()) {
			return ValidUtils.generateInValidJson(result);
		}

		List<Manager> managers = managerService.queryManagersByQueryInfo(queryInfo);

		Map<String, Object> map = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		data.put("total", managerService.countManagers());
		data.put("pagenum", queryInfo.getPagenum());
		// 把 Manager 转成 User 的数据格式
		List<Map<String, Object>> users = new ArrayList<>();
		for (Manager manager : managers) {
			Map<String, Object> user = manager2User(manager);
			users.add(user);
		}

		data.put("users", users);
		map.put("data", data);

		Map<String, Object> meta = new HashMap<>();
		meta.put("msg", "获取成功");
		meta.put("status", 200);
		map.put("meta", meta);

		return map;
	}

	/**
	 * 将 manager 转换为 user 字段的 map
	 * @param manager 需要转换为 manager
	 * @return
	 */
	private Map<String, Object> manager2User(Manager manager) {
		Map<String, Object> user = new HashMap<>();
		user.put("id", manager.getMgId());
		user.put("username", manager.getMgName());
		user.put("mobile", manager.getMgMobile());
		user.put("email", manager.getMgEmail());
		user.put("create_time", manager.getMgTime());
		user.put("mg_state", manager.isMgState());
		user.put("role_name", manager.getRole().getRoleName());

		return user;
	}

	/**
	 * 添加用户
	 * @param user 存储了需要添加的用户
	 * @param result 存储 @Valid 的错误信息
	 * @return
	 */
	@PostMapping("/users")
	public Map<String, Object> addUser(@Valid @RequestBody UserAdd user, BindingResult result) {
		if (result.hasErrors()) {
			return ValidUtils.generateInValidJson(result);
		}

		Manager manager = new Manager();
		manager.setMgName(user.getUsername());
		manager.setMgPwd(EncodeUtils.MD5Encode(user.getPassword()));
		manager.setMgEmail(user.getEmail());
		manager.setMgMobile(user.getMobile());
		manager.setMgTime(new Date().getTime());

		int res = managerService.addManager(manager);
		if (res == 0) {
			return MapUtils.generateErrorMap("用户创建失败");
		}

		return MapUtils.generateMap(null, "用户创建成功", 201);
	}

	/**
	 * 更新用户类型
	 * @param uid 用户 id
	 * @param type 状态 true 或 false
	 * @return
	 */
	@PutMapping("/users/{uid}/state/{type}")
	public Map<String, Object> updateUserType(@PathVariable int uid, @PathVariable boolean type) {
		int res = managerService.updateManagerType(uid, type);
		if (res == 0) {
			return MapUtils.generateErrorMap("设置状态失败");
		}

		return MapUtils.generateMap(null, "设置状态成功", 200);
	}

	/**
	 * 获取单个用户
	 * @param id 用户 id
	 * @return
	 */
	@GetMapping("/users/{id}")
	public Map<String, Object> getUser(@PathVariable int id) {
		Manager manager = managerService.queryManagerById(id);
		if (manager == null) {
			return MapUtils.generateErrorMap("用户不存在");
		}

		Map<String, Object> data = new HashMap<>();
		data.put("id", manager.getMgId());
		data.put("username", manager.getMgName());
		data.put("role_id", manager.getRole().getRoleId());
		data.put("mobile", manager.getMgMobile());
		data.put("email", manager.getMgEmail());
		return MapUtils.generateMap(data, "查询成功", 200);
	}

	/**
	 * 更新用户
	 * @param id 用户 id
	 * @param userMap 用户数据
	 * @return
	 */
	@PutMapping("/users/{id}")
	public Map<String, Object> updateUser(@PathVariable int id, @RequestBody Map<String, Object> userMap) {
		Manager manager = new Manager();
		manager.setMgId(id);
		manager.setMgEmail((String) userMap.get("email"));
		manager.setMgMobile((String) userMap.get("mobile"));

		int res = managerService.updateManager(manager);
		if (res == 0) {
			return MapUtils.generateMap(null, "更新失败", 500);
		}

		return MapUtils.generateMap(null, "更新成功", 200);
	}

	/**
	 * 删除用户
	 * @param id 用户 id
	 * @return
	 */
	@DeleteMapping("/users/{id}")
	public Map<String, Object> deleteUser(@PathVariable int id) {
		int res = managerService.deleteManager(id);
		if (res == 0) {
			return MapUtils.generateMap(null, "删除失败", 500);
		}

		return MapUtils.generateMap(null, "删除成功", 200);
	}

	/**
	 * 分配用户角色
	 * @param id 用户 id
	 * @param ridMap 存储角色 id
	 * @return
	 */
	@PutMapping("/users/{id}/role")
	public Map<String, Object> assignUserRole(@PathVariable int id, @RequestBody Map<String, Object> ridMap) {
		int res = managerService.updateManagerRole(id, (Integer) ridMap.get("rid"));
		if (res == 0) {
			return MapUtils.generateMap(null, "设置角色失败", 500);
		}

		return MapUtils.generateMap(null, "设置角色成功", 200);
	}
}
