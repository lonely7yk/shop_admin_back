package com.lonely7yk.service;

import com.lonely7yk.pojo.dto.QueryInfo;
import com.lonely7yk.pojo.po.Manager;

import java.util.List;

public interface ManagerService {
	Manager queryManagerByName(String name);

	List<Manager> queryManagersByQueryInfo(QueryInfo queryInfo);

	Integer countManagers();

	int addManager(Manager manager);

	int updateManagerType(int uid, boolean type);

	Manager queryManagerById(int id);

	int updateManager(Manager manager);

	int deleteManager(int id);

	int updateManagerRole(int id, int rid);
}
