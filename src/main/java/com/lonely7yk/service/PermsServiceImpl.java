package com.lonely7yk.service;

import com.lonely7yk.mapper.PermsMapper;
import com.lonely7yk.pojo.po.Perms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class PermsServiceImpl implements PermsService {
	@Autowired
	private PermsMapper permsMapper;

	/**
	 * 获取所有的权限的 id
	 * @return 所有权限 id 组成的字符串，用逗号分隔
	 */
	@Override
	public String getAllPermsIds() {
		List<Integer> allPermsIds = permsMapper.getAllPermsIds();
		List<String> tmp = new ArrayList<>();
		for (Integer id : allPermsIds) {
			tmp.add(Integer.toString(id));
		}
		return String.join(",", tmp);
	}

	/**
	 * 获取侧边栏的所有 perms（只到 ps_level == 1）
	 * @return
	 */
	@Override
	public List<Perms> getMenuPerms() {
		List<Perms> menuPerms = permsMapper.getMenuPerms();
		menuPerms.sort(new Comparator<Perms>() {
			@Override
			public int compare(Perms o1, Perms o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});

		return menuPerms;
	}

	@Override
	public List<Perms> getAllPermsTree() {
		return permsMapper.getAllPermsTree();
	}

	@Override
	public List<Map<String, Object>> getAllPermsList() {
		return permsMapper.getAllPermsList();
	}

}
