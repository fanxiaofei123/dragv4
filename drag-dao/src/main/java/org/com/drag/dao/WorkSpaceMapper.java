package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.WorkSpace;

import java.util.List;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkSpaceMapper

 * @Description: 工作空间

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午4:04:43
 */
public interface WorkSpaceMapper extends BaseDao<WorkSpace>{
	
	
	/**
	 * 通过名称查询
	 * @param name
	 * @return
	 */
	public WorkSpace selectByName(String name);
	

	/**
	 * 
	 * @param workSpace
	 * @return
	 */
	public List<WorkSpace> selectWorkSpace(@Param("w")WorkSpace workSpace);
	
	public List<WorkSpace> selectWorkSpaceName(@Param("w")WorkSpace workSpace);
	
	/**
	 * 通过用户id查询所有的工作空间数量
	 * @param userId
	 * @return
	 */
	List<WorkSpace>  UserCountWorkSpace(int userId);
	
	/**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();

	/**
	 * 查询ztree
	 * @return
	 */
	List<WorkSpace> selectZtree(@Param("userid")Integer userid,@Param("pid") Integer pid,@Param("isParent")Integer isParent);

	/**
	 * 查询hdfs文件夹全路径
	 * @param pid
	 * @return
	 */
    String folderAllPath(Integer pid);
}