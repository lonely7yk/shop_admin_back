package com.lonely7yk.mapper;

import com.lonely7yk.pojo.po.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ManagerMapper {
	Manager queryManagerByName(String name);

	List<Manager> queryManagersByQueryInfo(Map<String, Object> map);

	Integer countManagers();

	int addManager(Manager manager);

	int updateManagerType(@Param("id") int uid, @Param("state") boolean type);

	Manager queryManagerById(int id);

	int updateManager(Manager manager);

	int deleteManager(int id);

	int updateManagerRole(@Param("id") int id, @Param("rid") int rid);
}
