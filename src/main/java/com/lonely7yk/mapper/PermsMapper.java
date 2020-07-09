package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Perms;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermsMapper {
	public List<Integer> getAllPermsIds();

	public List<Perms> getMenuPerms();

	public List<Perms> getAllPerms();
}
