package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.WorkFlow;
import org.com.drag.model.WorkSpace;

import java.util.List;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkFlowMapper

 * @Description: 工作流程dao

 * @author: jiaonanyue

 * @date: 2016年11月2日 下午5:52:46
 */
public interface WorkFlowMapper extends BaseDao<WorkFlow>{

	
	public WorkSpace selectByName(String name);
	
	
	/**
	 * 通过条件查询电子流 and
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow> selectWorkFlow(@Param("w")WorkFlow workFlow);
	
	/**
	 * 通过条件删除工作流
	 * @param workFlow
	 * @return
	 */
	public int deleteWorkFlow(@Param("w")WorkFlow workFlow);
	
	/**
	 * 通过id查询有关联的数据
	 * @param workFlow
	 * @return
	 */
	public WorkFlow selectModelRelease(@Param("w")WorkFlow workFlow);
	
	/**
	 * 通过工作空间id查询工作流
	 * @return
	 */
	public List<WorkFlow> selectWorkFlowBySpace(int workSpaceId);
	
	/**
	 * 查询所有要审核的数据
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow> selectModelAudit(WorkFlow workFlow);

	/**
	 * 模糊查询要审核的数据
	 * @param workFlow
	 * @return
	 */
	List<WorkFlow> selectModelAuditName(WorkFlow workFlow);


	/**
	 * 批量查询工作流
	 * @param workFlow
	 * @return
	 */
	public List<WorkFlow>  batchSelectWorkFlow(WorkFlow workFlow);

	/**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();

	public List<WorkFlow> selectWorkFlowName(@Param("w")WorkFlow workFlow);

	/**
	 * 通过workspid修改所有hdfs
	 * @return
	 */
	public Integer updateByWorkspid(WorkFlow workFlow);

    List<WorkFlow> selectAllWorkFlow(WorkFlow workFlow);
	//查询最新创建的一条工作流记录
	WorkFlow selectByCreateTime(String userName);
}