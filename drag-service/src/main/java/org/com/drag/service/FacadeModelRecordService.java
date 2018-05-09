package org.com.drag.service;

import java.util.List;

import org.com.drag.model.FacadeModelRecord;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelRecordService

 * @Description: 我的订阅service层

 * @author: jiaonanyue

 * @date: 2016年11月26日 下午5:12:15
 */
public interface FacadeModelRecordService extends BaseService<FacadeModelRecord>{

	/**
	 * 通过条件查询我的订阅信息
	 * @param fr
	 * @return
	 */
	public List<FacadeModelRecord> selectByFacadeModelRecord(FacadeModelRecord fr);

}