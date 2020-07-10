package com.lonely7yk.service;

import com.lonely7yk.mapper.PermsMapper;
import com.lonely7yk.mapper.RoleMapper;
import com.lonely7yk.pojo.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermsMapper permsMapper;

	@Override
	public List<Role> getAllRoles() {
		return roleMapper.getAllRoles();
	}

	@Override
	public int addRole(Role role) {
		return roleMapper.addRole(role);
	}

	@Override
	public Role getRoleById(int id) {
		return roleMapper.getRoleById(id);
	}

	@Override
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	@Override
	public int deleteRole(int id) {
		return roleMapper.deleteRole(id);
	}

	@Override
	public int updateRolePerms(Role role) {
		return roleMapper.updateRolePerms(role);
	}

	/**
	 * 删除用户 permsId，以及其所有子 ids
	 * @param roleId
	 * @param permsId
	 * @return
	 */
	@Override
	public int deleteRolePerms(int roleId, int permsId) {
		// 先查询到 role，取出里面的 permsId
		Role role = roleMapper.getRoleById(roleId);
		String[] permsIds = role.getPermsIds().split(",");  // role 中有的 ids

		// 找到 permsId 中的所有子 id
		List<Integer> childrenIds = new ArrayList<>();
		findAllChildrenIds(childrenIds, permsId);
		// 转成 string 类型，并存储在 set 中
		Set<String> set = new HashSet<>();
		for (Integer childrenId : childrenIds) {
			set.add(Integer.toString(childrenId));
		}
		set.add(Integer.toString(permsId));  // 添加上 permsId 本身

		// 将不在 set 中的 permIds 添加到一个新的 list 中
		List<String> newPermsIds = new ArrayList<>();
		for (String id : permsIds) {
			if (!set.contains(id)) {
				newPermsIds.add(id);
			}
		}

		// 用新的 permsId 更新 role
		role.setPermsIds(String.join(",", newPermsIds));
		return roleMapper.updateRolePerms(role);
	}

	private void findAllChildrenIds(List<Integer> childrenIds, int permsId) {
		List<Integer> permsIds = permsMapper.getChildrenPermsIds(permsId);
		childrenIds.addAll(permsIds);

		for (Integer id : permsIds) {
			findAllChildrenIds(childrenIds, id);
		}
	}
}
