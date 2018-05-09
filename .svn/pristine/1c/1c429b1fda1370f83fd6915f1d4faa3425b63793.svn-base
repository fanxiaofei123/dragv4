package org.com.drag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.com.drag.model.UserRole;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: UserRoleMapper

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月19日 下午2:12:57
 */
public interface UserRoleMapper extends BaseDao<UserRole>{

	 @Select("select role_id AS roleId from user_role where user_id = #{userId}")
	 @ResultType(Integer.class)
	 List<Integer> selectRoleIdListByUserId(@Param("userId") Integer userId);
}