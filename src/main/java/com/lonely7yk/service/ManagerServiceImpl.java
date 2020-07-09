package com.lonely7yk.service;

import com.lonely7yk.mapper.ManagerMapper;
import com.lonely7yk.mapper.RoleMapper;
import com.lonely7yk.pojo.dto.QueryInfo;
import com.lonely7yk.pojo.po.Manager;
import com.lonely7yk.pojo.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
	@Autowired
	private ManagerMapper managerMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermsService permsService;

	/**
	 * 根据姓名查询 manager，如果是超级管理员手动将所有权限添加
	 * @param name 用户名
	 * @return
	 */
	public Manager queryManagerByName(String name) {
		Manager manager = managerMapper.queryManagerByName(name);
		if (manager == null) return null;

		int id = manager.getRole().getRoleId();
		if (id != 0) {
			// 非超级管理员可直接从数据库查询
			manager.setRole(roleMapper.getRoleById(id));
		} else {
			// 超级管理员，拥有所有权限
			Role role = manager.getRole();
			role.setRoleName("超级管理员");
			role.setPermsIds(permsService.getAllPermsIds());
			role.setRoleDesc("超级管理员");

			manager.setRole(role);
		}

		return manager;
	}

	/**
	 * 通过 queryInfo 查询，把 queryInfo 重新封装成 map，保存 query, startIdx 和 pageSize
	 * @param queryInfo 查询信息
	 * @return
	 */
	public List<Manager> queryManagersByQueryInfo(QueryInfo queryInfo) {
		Map<String, Object> map = new HashMap<>();
		// 只有当 query 不为 null 时在前后加上 "%"
		if (queryInfo.getQuery() != null) {
			map.put("query", "%" + queryInfo.getQuery() + "%");
		} else {
			map.put("query", null);
		}
		map.put("startIdx", (queryInfo.getPagenum() - 1) * queryInfo.getPagesize());
		map.put("pageSize", queryInfo.getPagesize());

		// 如果 role id 为 0，则为超级超级管理员
		List<Manager> managers = managerMapper.queryManagersByQueryInfo(map);
		for (Manager manager : managers) {
			if (manager.getRole().getRoleId() == 0) {
				manager.getRole().setRoleName("超级管理员");
			}
		}

		return managers;
	}

	/**
	 * 计算所有管理员的个数
	 * @return
	 */
	@Override
	public Integer countManagers() {
		return managerMapper.countManagers();
	}

	/**
	 * 添加管理员
	 * @param manager 更新的管理员
	 * @return
	 */
	@Override
	public int addManager(Manager manager) {
		return managerMapper.addManager(manager);
	}

	/**
	 * 更新管理员的类型
	 * @param uid 用户 id
	 * @param type 更新后的类型
	 * @return
	 */
	@Override
	public int updateManagerType(int uid, boolean type) {
		return managerMapper.updateManagerType(uid, type);
	}

	/**
	 * 根据 id 查询 manager
	 * @param id 用户 id
	 * @return
	 */
	@Override
	public Manager queryManagerById(int id) {
		return managerMapper.queryManagerById(id);
	}

	/**
	 * 更新管理员
	 * @param manager 管理员
	 * @return
	 */
	@Override
	public int updateManager(Manager manager) {
		return managerMapper.updateManager(manager);
	}

	/**
	 * 删除管理员
	 * @param id 管理员 id
	 * @return
	 */
	@Override
	public int deleteManager(int id) {
		return managerMapper.deleteManager(id);
	}

	/**
	 * 更新管理员角色
	 * @param id 管理员 id
	 * @param rid 角色 id
	 * @return
	 */
	@Override
	public int updateManagerRole(int id, int rid) {
		return managerMapper.updateManagerRole(id, rid);
	}
}
