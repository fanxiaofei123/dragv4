package org.com.drag.service;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.model.User;
import org.com.drag.model.WorkSpace;

import java.util.List;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkSpaceService

 * @Description: 工作空间接口

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午4:30:57
 */
public interface WorkSpaceService extends BaseService<WorkSpace>{
	
	
	
	/**
	 * 
	 * @param workSpace
	 * @return
	 */
	public List<WorkSpace> selectWorkSpace(WorkSpace workSpace);
	
	public List<WorkSpace> selectWorkSpaceName(WorkSpace workSpace);
	
	/**
	 * 具有业务逻辑删除工作空间
	 * @return
	 */
	public ResponseResult DeleteWorkSpace(WorkSpace workSpace, User user);
	
	/**
	 * 具有业务逻辑的更新方法
	 * @param workSpace
	 * @param user
	 * @return
	 */
	public ResponseResult updateWorkSpace(WorkSpace workSpace, User user);
	
	/**
	 * 批量删除工作空间
	 * @param workSpace
	 * @param user
	 * @return
	 */
	public ResponseResult BatchDeleteWorkSpace(WorkSpace workSpace, User user);
	
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
	List<WorkSpace> selectZtree(Integer userid, Integer pId, Integer isParent);

	String folderAllPath(Integer pid);
}
