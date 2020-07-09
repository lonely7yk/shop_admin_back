package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleMapperTest {
	@Autowired
	private RoleMapper mapper;

	@Test
	public void queryManagerByName() {
		// System.out.println(mapper);
		Role role = mapper.getRoleById(30);
		System.out.println(role);
	}
}
