package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.FacadeModelMapper;
import org.com.drag.model.FacadeModel;
import org.com.drag.service.FacadeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelServiceImpl

 * @Description: 模型实现类

 * @author: jiaonanyue

 * @date: 2016年11月5日 上午10:35:36
 */
@Service
public class FacadeModelServiceImpl extends BaseServiceImpl<FacadeModel> implements FacadeModelService {

	@Autowired
	private FacadeModelMapper FacadeModelDao;
	
	@Override
	public List<FacadeModel> selectFacadeModel(FacadeModel fm) {

		return FacadeModelDao.selectFacadeModel(fm);
	}

	@Override
	public List<FacadeModel> selectFacadeModelByType(FacadeModel fm) {

		return FacadeModelDao.selectFacadeModelByType(fm);
	}

	@Override
	public List<FacadeModel> selectFacadeModelByTypeName(FacadeModel facadeModel) {
		return FacadeModelDao.selectFacadeModelByTypeName(facadeModel);
	}


}
