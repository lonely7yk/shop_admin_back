package com.lonely7yk.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	private Integer roleId;
	private String roleName;
	private String permsIds;
	private String permsCate;
	private String roleDesc;
}
