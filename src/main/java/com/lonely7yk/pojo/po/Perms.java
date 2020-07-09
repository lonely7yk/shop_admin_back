package com.lonely7yk.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perms {
	private Integer id;
	private String authName;
	private String path;
	List<Perms> children;
	@JsonIgnore
	private Integer order;
}
