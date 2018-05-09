package org.com.drag.service.impl;

import java.util.Collections;
import java.util.List;

import org.com.drag.dao.CalculationHistoryMapper;
import org.com.drag.model.CalculationHistory;
import org.com.drag.service.CalculationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: CalculationHistoryServiceImpl

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月22日 下午5:30:50
 */
@Service("calculationHistoryService")
public class CalculationHistoryServiceImpl extends BaseServiceImpl<CalculationHistory> implements CalculationHistoryService{
	
	
    @Autowired
    private CalculationHistoryMapper calculationHistoryMapper;

    @Override
    public List<CalculationHistory> selectBySelectiveKey(CalculationHistory cal) {
        List<CalculationHistory> calculationHistories = calculationHistoryMapper.selectBySelectiveKey(cal);
        return calculationHistories.size() == 0? Collections.EMPTY_LIST:calculationHistories;
    }

	@Override
	public List<CalculationHistory> selectBySelective(CalculationHistory cal) {
		 List<CalculationHistory> calculationHistories = calculationHistoryMapper.selectBySelective(cal);
	     return calculationHistories.size() == 0? Collections.EMPTY_LIST:calculationHistories;
	}

	@Override
	public List<CalculationHistory> selectBySelectiveTime(CalculationHistory cal) {
		
		return calculationHistoryMapper.selectBySelectiveTime(cal);
	}

	@Override
	public List<CalculationHistory> selectBySelectiveFTime(CalculationHistory calculationHistory) {
		return calculationHistoryMapper.selectBySelectiveFTime(calculationHistory);
	}

	@Override
	public List<CalculationHistory> selectBySelectiveHTime(CalculationHistory calculationHistory) {
		return calculationHistoryMapper.selectBySelectiveHTime(calculationHistory);
	}

	@Override
	public List<CalculationHistory> selectByFlowId(String flowId) {
		return calculationHistoryMapper.selectByFlowId(flowId);
	}

	@Override
	public List<CalculationHistory> selectBySelectiveF() {

		return calculationHistoryMapper.selectBySelectiveF();
	}

	@Override
	public List<CalculationHistory> selectBySelectiveAll() {

		return calculationHistoryMapper.selectBySelectiveAll();
	}
}
