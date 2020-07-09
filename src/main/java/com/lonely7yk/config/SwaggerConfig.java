package com.lonely7yk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2     // 开启 Swagger2
public class SwaggerConfig {
	// 配置 Swagger 的 Docket 的 Bean 实例
	@Bean
	public Docket docket(Environment environment) {
		// 判断是否使用了 dev 环境
		Profiles profiles = Profiles.of("dev");
		boolean flag = environment.acceptsProfiles(profiles);

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("lonely7yk")
				.select()
				// RequestHandlerSelectors：配置要扫描接口的方式
				// basePackage()：指定要扫描的包
				.apis(RequestHandlerSelectors
						.basePackage("com.lonely7yk.controller"))
				// paths()：过滤 url 路径
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(security());
	}

	private List<ApiKey> security() {
		List<ApiKey> list = new ArrayList<>();
		list.add(new ApiKey("Authorization", "Authorization", "header"));
		return list;
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("lonely7yk", "http://www.lonely7yk.top", "yk1056345153@gmail.com");

		return new ApiInfo("Vue shop 的 API 文档",
				"Api Documentation",
				"v1.0",
				"http://www.lonely7yk.top",
				contact,
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}

}