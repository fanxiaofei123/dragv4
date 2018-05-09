package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.FacadeModelMapper;
import org.com.drag.model.FacadeModel;
import org.com.drag.model.ModelDetail;
import org.com.drag.model.ModelFrontUser;
import org.com.drag.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

/**
 * Created by sky on 2017/4/10.
 */
@Service
public class ModelServiceImpl extends BaseServiceImpl<FacadeModel> implements ModelService {
	@Autowired
	private FacadeModelMapper modelMapper;

	/**
	 * */
	@Transactional
	public List<? extends FacadeModel> getByTreeId(Long treeId, int page, String frontName, String type) {
		PageHelper.startPage(page, 10);
		List<? extends FacadeModel> models = null;
		if(frontName==null){
			models = modelMapper.selectByFid(treeId, type);
		}else{
			models = modelMapper.selectByFidWithModelRecord(treeId, frontName, type);
		}
		return models;
	}
	
	@Transactional
	public ModelDetail getModel(int id){
		ModelDetail model = modelMapper.selectWithDetail(id);
		return model;
	}
	
	/**
	 * */
	@Transactional
	public List<ModelFrontUser> getMyModels(Long treeId, int page, String frontName,String type) {
		PageHelper.startPage(page, 5);
		List<ModelFrontUser> models = modelMapper.selectMyModels(treeId, frontName, type);
		return models;
	}
	
	@Transactional
	public List<ModelFrontUser> getMyApply(Long treeId, int page, String frontName,String type) {
		PageHelper.startPage(page, 5);
		List<ModelFrontUser> models = modelMapper.selectMyApply(treeId,frontName,type);
		return models;
	}
}
