package com.lonely7yk.service;

import com.lonely7yk.pojo.dto.QueryInfo;
import com.lonely7yk.pojo.po.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ManagerServiceTest {
	@Autowired
	private ManagerService managerService;

	@Test
	public void queryManagersByQueryInfo() {
		QueryInfo queryInfo = new QueryInfo("lone", 1, 2);
		List<Manager> managers = managerService.queryManagersByQueryInfo(queryInfo);
		System.out.println(managers);
	}
}
