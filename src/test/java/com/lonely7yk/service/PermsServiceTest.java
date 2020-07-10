package com.lonely7yk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lonely7yk.pojo.po.Perms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class PermsServiceTest {
	@Autowired
	private PermsService permsService;
	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void getAllPermsIds() {
		String allPermsIds = permsService.getAllPermsIds();
		System.out.println(allPermsIds);
	}

	@Test
	public void getAllPerms() throws JsonProcessingException {
		List<Perms> permsList = permsService.getAllPermsTree();

		System.out.println(permsList);
	}

	@Test
	public void getAllPermsList() throws JsonProcessingException {
		List<Map<String, Object>> allPermsList = permsService.getAllPermsList();
		System.out.println(objectMapper.writeValueAsString(allPermsList));
	}
}
