package com.lonely7yk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonely7yk.pojo.po.Perms;
import com.lonely7yk.pojo.po.Role;
import com.lonely7yk.service.PermsService;
import com.lonely7yk.service.RoleService;
import com.lonely7yk.utils.JsonUtils;
import com.lonely7yk.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermsService permsService;

	@GetMapping("/roles")
	public Map<String, Object> roles() throws JsonProcessingException {
		List<Role> roles = roleService.getAllRoles();
		List<Perms> permsList = permsService.getAllPerms();

		List<Map<String, Object>> roleMapList = new ArrayList<>();

		for (Role role : roles) {
			List<Perms> copyPermsList = new ArrayList<>();

			// 将所有数据进行拷贝，然后存储到一个新的 list 中
			for (Perms perms : permsList) {
				Perms copy = (Perms) JsonUtils.jacksonCopy(perms, Perms.class);
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

			roleMapList.add(roleMap);
		}

		return MapUtils.generateMap(roleMapList, "获取成功", 200);
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
}
