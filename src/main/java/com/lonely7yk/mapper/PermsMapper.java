package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Perms;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PermsMapper {
	List<Integer> getAllPermsIds();

	List<Perms> getMenuPerms();

	List<Perms> getAllPermsTree();

	List<Map<String, Object>> getAllPermsList();

	List<Integer> getChildrenPermsIds(int parentRoleId);
}
