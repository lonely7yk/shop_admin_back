package com.lonely7yk.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryInfo {
	private String query;
	@NotNull(message = "pagenum 不能为空")
	private Integer pagenum;
	@NotNull(message = "pagesize 不能为空")
	private Integer pagesize;
}
