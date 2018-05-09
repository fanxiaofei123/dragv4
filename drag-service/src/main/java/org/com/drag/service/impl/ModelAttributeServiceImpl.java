package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.ModelAttributeMapper;
import org.com.drag.model.ModelAttribute;
import org.com.drag.service.ModelAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: ModelAttributeServiceImpl

 * @Description: 模型属性实现类

 * @author: jiaonanyue

 * @date: 2016年11月7日 下午4:30:37
 */
@Service
public class ModelAttributeServiceImpl extends BaseServiceImpl<ModelAttribute> implements ModelAttributeService{

	@Autowired
	private ModelAttributeMapper modelAttributeDao;
	
	

	@Override
	public List<ModelAttribute> getModelAttribute(ModelAttribute modelAttribute) {
		
		return modelAttributeDao.getModelAttribute(modelAttribute);
	}

}
