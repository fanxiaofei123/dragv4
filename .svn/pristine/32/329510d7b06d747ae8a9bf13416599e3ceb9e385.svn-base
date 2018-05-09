package org.com.drag.service;

import java.util.List;
import java.util.Map;

import org.com.drag.model.DataModel;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: DataModelService

 * @Description: 数据模型接口

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午4:08:10
 */
public interface DataModelService extends BaseService<DataModel>{
	
	/**
	 * 通过条件查询   and
	 * @param dataModel
	 * @return
	 */
	public List<DataModel> selectByDataModel(DataModel dataModel);
	
	/**
	 * 查询模型结构
	 * @return
	 */
	public Map<String,List<DataModel>> selectDataModel();

    Integer selectByName(String dataModelName);
}
