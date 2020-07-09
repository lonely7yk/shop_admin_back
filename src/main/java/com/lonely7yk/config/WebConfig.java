package com.lonely7yk.config;

import com.lonely7yk.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	// 配置拦截器，用于 token 验证
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/swagger-ui.html")
				.excludePathPatterns("/swagger-resources/**")
				.excludePathPatterns("/error")
				.excludePathPatterns("/webjars/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjar/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
