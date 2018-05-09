package org.com.drag.service.impl;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.dao.DataModelMapper;
import org.com.drag.dao.FacadeUploadInfoMapper;
import org.com.drag.dao.ModelAttributeMapper;
import org.com.drag.dao.WorkFlowMapper;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkFlowServiceImpl

 * @Description: 工作空间电子流实现类

 * @author: jiaonanyue

 * @date: 2016年11月3日 上午9:55:58
 */
@Service
public class WorkFlowServiceImpl extends BaseServiceImpl<WorkFlow> implements WorkFlowService {
	
	@Autowired
	private WorkFlowMapper workFlowDao;
	@Autowired
	DataModelMapper dataModelMapper;
	@Autowired
	ModelAttributeMapper modelAttributeMapper;
	@Autowired
	private IHdfsApi iHdfsApi;
	@Autowired
	private FacadeUploadInfoMapper facadeUploadInfoMapper;


	@Override
	public int insert(WorkFlow workFlow) {
		workFlowDao.insert(workFlow);
		return workFlow.getId();
	}

	@Override
	public WorkFlow selectByCreateTime(String userName) {
		return workFlowDao.selectByCreateTime(userName);
	}

	@Override
	public List<WorkFlow> selectWorkFlow(WorkFlow workFlow) {

		
		return workFlowDao.selectWorkFlow(workFlow);
	}

	@Override
	public List<WorkFlow> selectAllWorkFlow(WorkFlow workFlow) {
		return workFlowDao.selectAllWorkFlow(workFlow);
	}

	@Override
	public ResponseResult BatchDeleteWorkSpace(WorkFlow workFlow, User user) {

		if(workFlow != null && workFlow.getIds() != null && workFlow.getIds().size() > 0){
			
			//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
			List<Integer> idList = workFlow.getIds();
			for(Integer ids : idList){
				WorkFlow workFlows = workFlowDao.selectByPrimaryKey(ids);
				if(workFlows == null){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作流删除失败");
				}
				Boolean a = iHdfsApi.deleteFileOrDir(workFlows.getHdfsUrl(workFlows),user.getLoginname(),true);
				if(a){
					int b = workFlowDao.deleteByPrimaryKey(ids);
					if(b != 1){
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作流删除失败");
					}
				}else{
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作流删除失败");
				}
			}
			return new ResponseResult(HttpStatus.OK,"工作流删除成功");
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"工作流删除失败");
		}
		
	}

	@Override
	public List<WorkFlow> selectWorkFlowBySpace(int workSpaceId) {

		return workFlowDao.selectWorkFlowBySpace(workSpaceId);
	}

	@Override
	public int countAll() {

		return workFlowDao.countAll();
	}

	@Override
	public List<WorkFlow> selectWorkFlowName(WorkFlow workFlow) {

		return workFlowDao.selectWorkFlowName(workFlow);
	}

	@Override
	public Integer updateByWorkspid(WorkFlow workFlow) {
		return workFlowDao.updateByWorkspid(workFlow);
	}

	@Override
	public List<WorkFlow> batchSelectWorkFlow(WorkFlow workFlow) {

		
		return workFlowDao.batchSelectWorkFlow(workFlow);
	}

	@Override
	public List<WorkFlow> selectModelAudit(WorkFlow workFlow) {

		return workFlowDao.selectModelAudit(workFlow);
	}

	@Override
	public WorkFlow selectModelRelease(WorkFlow workFlow) {

		return workFlowDao.selectModelRelease(workFlow);
	}

	@Override
	public List<WorkFlow> selectModelAuditName(WorkFlow workFlow) {
		return workFlowDao.selectModelAuditName(workFlow);
	}


}
