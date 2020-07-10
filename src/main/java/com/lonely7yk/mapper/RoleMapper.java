package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
	Role getRoleById(int id);

	List<Role> getAllRoles();

	int addRole(Role role);

	int updateRole(Role role);

	int deleteRole(int id);

	int updateRolePerms(Role role);
}
