package com.lonely7yk.service;

import com.lonely7yk.pojo.po.Role;

import java.util.List;

public interface RoleService {
	List<Role> getAllRoles();

	int addRole(Role role);

	Role getRoleById(int id);

	int updateRole(Role role);

	int deleteRole(int id);

	int updateRolePerms(Role role);

	int deleteRolePerms(int roleId, int permsId);
}
