package com.lonely7yk.service;

import com.lonely7yk.mapper.RoleMapper;
import com.lonely7yk.pojo.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> getAllRoles() {
		return roleMapper.getAllRoles();
	}
}
