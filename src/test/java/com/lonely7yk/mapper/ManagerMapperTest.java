package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ManagerMapperTest {
	@Autowired
	private ManagerMapper mapper;

	@Test
	public void queryManagerByName() {
		// System.out.println(mapper);
		Manager manager = mapper.queryManagerByName("admin");
		System.out.println(manager);
	}

	@Test
	public void countManagers() {
		Integer cnt = mapper.countManagers();
		System.out.println(cnt);
	}
}
