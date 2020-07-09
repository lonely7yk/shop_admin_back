package com.lonely7yk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonely7yk.pojo.po.Perms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PermsServiceTest {
	@Autowired
	private PermsService permsService;

	@Test
	public void getAllPermsIds() {
		String allPermsIds = permsService.getAllPermsIds();
		System.out.println(allPermsIds);
	}

	@Test
	public void getAllPerms() throws JsonProcessingException {
		List<Perms> permsList = permsService.getAllPerms();

		System.out.println(permsList);
	}
}
