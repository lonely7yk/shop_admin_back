package com.lonely7yk.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
	private int mgId;
	private String mgName;
	private String mgPwd;
	private Long mgTime;
	private Role role;
	private String mgMobile;
	private String mgEmail;
	private boolean mgState;
}
