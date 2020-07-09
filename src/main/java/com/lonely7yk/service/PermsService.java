package com.lonely7yk.service;

import com.lonely7yk.pojo.po.Perms;

import java.util.List;

public interface PermsService {
	public String getAllPermsIds();

	public List<Perms> getMenuPerms();

	List<Perms> getAllPerms();
}
