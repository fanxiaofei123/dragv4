package org.com.drag.dao;

import java.util.List;

import org.com.drag.model.ModelAttribute;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: ModelAttributeMapper

 * @Description: 模型属性

 * @author: jiaonanyue

 * @date: 2016年11月7日 下午3:54:53
 */
public interface ModelAttributeMapper extends BaseDao<ModelAttribute>{

	/**
	 * 通过条件查询模型属性
	 * @param modelAttribute
	 * @return
	 */
	public List<ModelAttribute> getModelAttribute(ModelAttribute modelAttribute);

    List<ModelAttribute> selectAttrByModelName(String block);
}