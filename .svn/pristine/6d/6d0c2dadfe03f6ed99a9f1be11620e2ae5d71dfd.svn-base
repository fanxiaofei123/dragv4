package org.com.drag.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.com.drag.dao.UserMapper;
import org.com.drag.dao.UserRoleMapper;
import org.com.drag.model.User;
import org.com.drag.model.UserRole;
import org.com.drag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: UserServiceImpl

 * @Description: 用户操作实现类

 * @author: jiaonanyue

 * @date: 2016年10月20日 上午10:34:09
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	@Autowired
	private UserMapper userDAO;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	/**
	 * 用户注册实现类
	 * 具有业务逻辑
	 */
	@Override
	@Transactional
	public int register(User user) {

		//查询用户是否注册
		User users = userDAO.selectByUser(user);
		if(users != null){
			
			return 0;
		}else{
			//创建hdfs路径
			
			//用户信息保存
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			user.setCreatedate(new Date());
			int a = userDAO.insert(user);
			UserRole userRole = new UserRole();
			userRole.setRoleId(Long.parseLong(user.getRoleIds()));
			userRole.setUserId(Long.parseLong(user.getId().toString()));
			userRoleMapper.insertSelective(userRole);
			
			return a;
		}
		
	}
	
	/**
	 * 用户注册实现类
	 * 具有业务逻辑
	 */
	@Transactional
	public int registerUser(User user) {

		//查询用户是否注册
		User users = userDAO.selectByUser(user);
		if(users != null){
			
			return 0;
		}else{
			//创建hdfs路径
			
			//用户信息保存
			user.setCreatedate(new Date());
			int a = userDAO.insert(user);
			return a;
		}
		
	}
	

	/**
	 * 通过用户信息查询用户
	 */
	@Override
	public User selectByUser(User user) {

		return userDAO.selectByUser(user);
	}

	@Override
	public List<User> selectAll() {

		return userDAO.selectAll();
	}

	@Override
	public int countAll() {

		return userDAO.countAll();
	}

	@Override
	public List<User> selectNameAll(User user) {

		return userDAO.selectNameAll(user);
	}

	@Override
	public User selectByLoginName(String userName) {
		return userDAO.selectByLoginName(userName);
	}

	@Override
	public User selectById(Integer userId) {
		return userDAO.selectById(userId);
	}


}
