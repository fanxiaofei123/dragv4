package org.com.drag.dao;

import java.util.List;

import org.com.drag.model.User;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: UserMapper

 * @Description: 用户dao接口UserMapper

 * @author: jiaonanyue

 * @date: 2016年10月19日 下午6:06:53
 */
public interface UserMapper extends BaseDao<User>{
	
	/**
	 * 通过用户信息查询用户
	 * @param user
	 * @return
	 */
	public User selectByUser(User user);
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<User> selectAll();
	
	/**
	 * 通过用户名模糊查询
	 * @return
	 */
	public List<User> selectNameAll(User user);
	
	/**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();

	/**
	 * 通过用户名查询
	 * @param username
	 * @return
	 */
	User selectByLoginName(String username);


	User selectById(Integer userId);
}