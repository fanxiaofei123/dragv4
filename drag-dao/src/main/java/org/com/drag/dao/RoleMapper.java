package org.com.drag.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.Role;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: RoleMapper

 * @Description: 角色mapper

 * @author: jiaonanyue

 * @date: 2016年11月19日 上午11:27:24
 */
public interface RoleMapper extends BaseDao<Role>{
	
	
	 List<Map<Integer, String>> selectResourceListByRoleId(@Param("id") Integer id);
}