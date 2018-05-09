package org.com.drag.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.com.drag.dao.RoleMapper;
import org.com.drag.dao.UserRoleMapper;
import org.com.drag.model.Role;
import org.com.drag.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: RoleServiceImpl

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月19日 下午2:01:50
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public Map<String, Set<String>> selectResourceMapByUserId(Integer userId) {

		 Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
	        List<Integer> roleIdList = userRoleMapper.selectRoleIdListByUserId(userId);
	        Set<String> urlSet = new HashSet<String>();
	        Set<String> roles = new HashSet<String>();
	        for (Integer roleId : roleIdList) {
	            List<Map<Integer, String>> resourceList = roleDao.selectResourceListByRoleId(roleId);
	            if (resourceList != null) {
	                for (Map<Integer, String> map : resourceList) {
	                    if (StringUtils.isNotBlank(map.get("url"))) {
	                        urlSet.add(map.get("url"));
	                    }
	                }
	            }
	            Role role = roleDao.selectByPrimaryKey(roleId);
	            if (role != null) {
	                roles.add(role.getName());
	            }
	        }
	        resourceMap.put("urls", urlSet);
	        resourceMap.put("roles", roles);
	        
		return resourceMap;
	}

}
