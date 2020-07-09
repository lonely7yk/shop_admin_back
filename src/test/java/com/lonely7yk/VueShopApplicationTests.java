package com.lonely7yk;

import com.lonely7yk.mapper.ManagerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VueShopApplicationTests {
	@Autowired
	private ManagerMapper mapper;

	@Test
	void contextLoads() {
		System.out.println(mapper);
	}

}
