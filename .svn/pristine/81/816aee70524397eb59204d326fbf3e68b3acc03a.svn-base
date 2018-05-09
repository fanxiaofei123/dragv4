package org.com.drag.service;

import java.util.List;

import org.com.drag.model.User;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: UserService

 * @Description: 用户操作接口

 * @author: jiaonanyue

 * @date: 2016年10月20日 上午10:33:15
 */
public interface UserService extends BaseService<User>{
	
	
	/**
	 * 用户注册接口
	 * @param user
	 * @return
	 */
	public int register(User user);
	
	
	public int registerUser(User user);
	
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
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();
	
	/**
	 * 通过用户名模糊查询
	 * @return
	 */
	public List<User> selectNameAll(User user);

	/**
	 * 通过用户名查询
	 * @param userName
	 * @return
	 */
	public User selectByLoginName(String userName);


	User selectById(Integer userId);
}
