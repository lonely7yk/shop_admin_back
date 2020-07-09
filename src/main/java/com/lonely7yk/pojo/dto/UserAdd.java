package com.lonely7yk.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdd {
	@NotNull(message = "username 不能为空")
	private String username;
	@NotNull(message = "password 不能为空")
	private String password;
	@Email(message = "email 格式不正确")
	private String email;
	private String mobile;
}
