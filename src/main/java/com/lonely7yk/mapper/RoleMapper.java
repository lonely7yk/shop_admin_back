package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
	public Role getRoleById(int id);

	List<Role> getAllRoles();
}
