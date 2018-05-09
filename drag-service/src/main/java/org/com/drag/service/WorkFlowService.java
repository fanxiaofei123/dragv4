package org.com.drag.service;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;

import java.util.List;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkFlowService

 * @Description: 工作空间电子流

 * @author: jiaonanyue

 * @date: 2016年11月3日 上午9:53:16
 */
public interface WorkFlowService extends BaseService<WorkFlow>{

	/**
	 *
	 * @return
	 */
	public int insert(WorkFlow workFlow);
	/**
	 * 查询最新创建的一条工作流记录
	 * @param userName
	 * @return
	 */
	public WorkFlow selectByCreateTime(String userName);
	/**
	 * 通过条件查询工作流
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow> selectWorkFlow(WorkFlow workFlow);
	public List<WorkFlow> selectAllWorkFlow(WorkFlow workFlow);

	public List<WorkFlow> selectWorkFlowName(WorkFlow workFlow);
	/**
	 * 通过workspid修改所有hdfs
	 * @return
	 */
	public Integer updateByWorkspid(WorkFlow workFlow);
	/**
	 * 批量删除工作流
	 * @param workFlow
	 * @param user
	 * @return
	 */
	public ResponseResult  BatchDeleteWorkSpace(WorkFlow workFlow, User user);
	
	/**
	 * 批量查询工作流
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow>  batchSelectWorkFlow(WorkFlow workFlow);
	
	
	/**
	 * 通过工作空间id查询工作流
	 * @param workSpaceId
	 * @return
	 */
	public List<WorkFlow> selectWorkFlowBySpace(int workSpaceId);
	
	/**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();
	
	/**
	 * 查询所有要审核的数据
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow> selectModelAudit(WorkFlow workFlow);
	
	/**
	 * 通过id查询有关联的数据
	 * @param workFlow
	 * @return
	 */
	public WorkFlow selectModelRelease(WorkFlow workFlow);


	List<WorkFlow> selectModelAuditName(WorkFlow workFlow);
}
