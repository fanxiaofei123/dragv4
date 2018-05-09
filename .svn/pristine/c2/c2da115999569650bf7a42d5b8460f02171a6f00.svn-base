package org.com.drag.service;

import java.util.List;

import org.com.drag.model.FacadeModel;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelMapper

 * @Description: 模型dao

 * @author: jiaonanyue

 * @date: 2016年11月4日 下午3:46:23
 */
public interface FacadeModelService extends BaseService<FacadeModel>{
	
	/**
	 * 条件查询数据
	 * @param fm
	 * @return
	 */
	public List<FacadeModel> selectFacadeModel(FacadeModel fm);
	
	/**
	 * 通过状态查询
	 * @param fm
	 * @return
	 */
	public List<FacadeModel>  selectFacadeModelByType(FacadeModel fm);

	/**
	 * 通过状态和输入的信息模糊查询
	 * @param facadeModel
	 * @return
	 */
	List<FacadeModel> selectFacadeModelByTypeName(FacadeModel facadeModel);
}