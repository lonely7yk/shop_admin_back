package com.lonely7yk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonely7yk.pojo.po.Perms;
import com.lonely7yk.pojo.po.Role;
import com.lonely7yk.service.PermsService;
import com.lonely7yk.service.RoleService;
import com.lonely7yk.utils.JsonUtils;
import com.lonely7yk.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermsService permsService;

	/**
	 * 获取角色列表，角色中包含对应的权限
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/roles")
	public Map<String, Object> roles() {
		List<Role> roles = roleService.getAllRoles();
		List<Perms> permsList = permsService.getAllPermsTree();

		List<Map<String, Object>> roleMapList = new ArrayList<>();

		for (Role role : roles) {
			// List<Perms> copyPermsList = new ArrayList<>();
			//
			// // 将所有数据进行拷贝，然后存储到一个新的 list 中
			// for (Perms perms : permsList) {
			// 	Perms copy = (Perms) JsonUtils.jacksonCopy(perms, Perms.class);
			// 	copyPermsList.add(copy);
			// }
			//
			// String[] permsIds = !role.getPermsIds().equals("") ? role.getPermsIds().split(",") : new String[0];
			// Set<Integer> permsIdSet = new HashSet<>();
			// for (String permsId : permsIds) {
			// 	permsIdSet.add(Integer.parseInt(permsId));
			// }
			//
			// removePermsOutOfSet(copyPermsList, permsIdSet);
			//
			// Map<String, Object> roleMap = new HashMap<>();
			// roleMap.put("id", role.getRoleId());
			// roleMap.put("roleName", role.getRoleName());
			// roleMap.put("roleDesc", role.getRoleDesc());
			// roleMap.put("children", copyPermsList);

			Map<String, Object> roleMap = generateRoleTree(role, permsList);
			roleMapList.add(roleMap);
		}

		return MapUtils.generateMap(roleMapList, "获取成功", 200);
	}

	/**
	 * 根据 role 和 permsList(所有的 perms)，生成一个 roleMap
	 * @param role 角色对象（对应数据库）
	 * @param permsList 所有 perms 生成的 perms tree
	 * @return
	 */
	private Map<String, Object> generateRoleTree(Role role, List<Perms> permsList) {
		List<Perms> copyPermsList = new ArrayList<>();

		// 将所有数据进行拷贝，然后存储到一个新的 list 中
		for (Perms perms : permsList) {
			Perms copy = null;
			try {
				copy = (Perms) JsonUtils.jacksonCopy(perms, Perms.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			copyPermsList.add(copy);
		}

		String[] permsIds = !role.getPermsIds().equals("") ? role.getPermsIds().split(",") : new String[0];
		Set<Integer> permsIdSet = new HashSet<>();
		for (String permsId : permsIds) {
			permsIdSet.add(Integer.parseInt(permsId));
		}

		removePermsOutOfSet(copyPermsList, permsIdSet);

		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("id", role.getRoleId());
		roleMap.put("roleName", role.getRoleName());
		roleMap.put("roleDesc", role.getRoleDesc());
		roleMap.put("children", copyPermsList);

		return roleMap;
	}

	/**
	 * 在 copyPermsList 中删除不在 permsIdSet 中的节点
	 * @param copyPermsList
	 * @param permsIdSet
	 */
	private void removePermsOutOfSet(List<Perms> copyPermsList, Set<Integer> permsIdSet) {
		for (int i = copyPermsList.size() - 1; i >= 0; i--) {
			int currId = copyPermsList.get(i).getId();
			if (!permsIdSet.contains(currId)) {
				copyPermsList.remove(i);
			}
		}

		for (Perms perms : copyPermsList) {
			removePermsOutOfSet(perms.getChildren(), permsIdSet);
		}
	}

	/**
	 * 添加角色
	 * @param role 添加的角色（只有 roleName 和 roleDesc 属性）
	 * @return
	 */
	@PostMapping("/roles")
	public Map<String, Object> addRole(@RequestBody Role role) {
		int res = roleService.addRole(role);
		if (res == 0) {
			return MapUtils.generateMap(null, "添加角色失败", 500);
		}

		return MapUtils.generateMap(null, "添加角色成功", 201);
	}

	/**
	 * 根据 id 查询角色
	 * @param id 角色 id
	 * @return
	 */
	@GetMapping("/roles/{id}")
	public Map<String, Object> getRole(@PathVariable int id) {
		Role role = roleService.getRoleById(id);
		if (role == null) {
			return MapUtils.generateErrorMap("获取角色失败");
		}

		Map<String, Object> roleMap = new HashMap<>();
		roleMap.put("roleId", role.getRoleId());
		roleMap.put("roleName", role.getRoleName());
		roleMap.put("roleDesc", role.getRoleDesc());
		return MapUtils.generateMap(roleMap, "获取角色成功", 200);
	}

	/**
	 * 更新角色
	 * @param id 角色 id
	 * @param role 更新的角色对象
	 * @return
	 */
	@PutMapping("/roles/{id}")
	public Map<String, Object> updateRole(@PathVariable int id, @RequestBody Role role) {
		role.setRoleId(id);
		int res = roleService.updateRole(role);
		if (res == 0) {
			return MapUtils.generateMap(null, "修改角色失败", 500);
		}

		return MapUtils.generateMap(null, "修改角色成功", 200);
	}

	/**
	 * 删除角色
	 * @param id 角色 id
	 * @return
	 */
	@DeleteMapping("/roles/{id}")
	public Map<String, Object> deleteRole(@PathVariable int id) {
		int res = roleService.deleteRole(id);
		if (res == 0) {
			return MapUtils.generateMap(null, "删除角色失败", 500);
		}

		return MapUtils.generateMap(null, "删除角色成功", 200);
	}

	/**
	 * 为角色分配权限
	 * @param roleId 角色 id
	 * @param permsIdsMap 存储权限 ids 的 map
	 * @return
	 */
	@PostMapping("/roles/{roleId}/rights")
	public Map<String, Object> assignRolePerms(@PathVariable int roleId, @RequestBody Map<String, Object> permsIdsMap) {
		Role role = new Role();
		String permsIds = (String) permsIdsMap.get("rids");
		role.setRoleId(roleId);
		role.setPermsIds(permsIds);

		int res = roleService.updateRolePerms(role);
		if (res == 0) {
			return MapUtils.generateMap(null, "更新失败", 500);
		}

		return MapUtils.generateMap(null, "更新成功", 200);
	}

	/**
	 * 删除角色的某个权限（会删除其所有子权限）
	 * @param roleId 角色 id
	 * @param permsId 权限 id
	 * @return
	 */
	@DeleteMapping("/roles/{roleId}/rights/{permsId}")
	public Map<String, Object> deleteRolePerms(@PathVariable int roleId, @PathVariable int permsId) {
		int res = roleService.deleteRolePerms(roleId, permsId);
		if (res == 0) {
			return MapUtils.generateMap(null, "删除失败", 500);
		}

		Role role = roleService.getRoleById(roleId);    // 获取角色对象
		List<Perms> permsList = permsService.getAllPermsTree(); // 获取权限树
		Map<String, Object> data = generateRoleTree(role, permsList);   // 生成角色对应的权限树

		return MapUtils.generateMap(data.get("children"), "删除成功", 200);
	}
}
