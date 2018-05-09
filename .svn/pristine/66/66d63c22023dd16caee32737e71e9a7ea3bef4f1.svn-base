package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.FacadeModelRecordMapper;
import org.com.drag.model.FacadeModelRecord;
import org.com.drag.service.FacadeModelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelRecordServiceImpl

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月26日 下午5:14:45
 */
@Service
public class FacadeModelRecordServiceImpl extends BaseServiceImpl<FacadeModelRecord> implements FacadeModelRecordService {

	@Autowired
	private FacadeModelRecordMapper facadeModelRecordDao;
	
	
	@Override
	public List<FacadeModelRecord> selectByFacadeModelRecord(FacadeModelRecord fr) {

		return facadeModelRecordDao.selectByFacadeModelRecord(fr);
	}


	
}
