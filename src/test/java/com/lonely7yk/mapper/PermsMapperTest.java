package com.lonely7yk.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lonely7yk.pojo.po.Perms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PermsMapperTest {
	@Autowired
	private PermsMapper permsMapper;

	@Test
	public void getMenuPerms() {
		List<Perms> menuPerms = permsMapper.getMenuPerms();
	}

	@Test
	public void getAllPerms() throws JsonProcessingException {
		List<Perms> allPerms = permsMapper.getAllPermsTree();
		ObjectMapper objectMapper = new ObjectMapper();
		String s = objectMapper.writeValueAsString(allPerms);
		System.out.println(s);
	}
}
