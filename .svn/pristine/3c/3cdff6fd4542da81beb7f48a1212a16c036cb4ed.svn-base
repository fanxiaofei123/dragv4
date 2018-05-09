package org.com.drag.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.com.drag.dao.CalculationFrontHistoryMapper;
import org.com.drag.dao.CalculationHistoryMapper;
import org.com.drag.model.CalculationFrontHistory;
import org.com.drag.model.CalculationHistoryDetail;
import org.com.drag.service.CalculationFrontHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;


@Service("calculationFrontHistoryService")
public class CalculationFrontHistoryServiceImpl extends BaseServiceImpl<CalculationFrontHistory> implements CalculationFrontHistoryService{
    @Autowired
    private CalculationFrontHistoryMapper calculationFrontHistoryMapper;
    @Autowired
    private CalculationHistoryMapper calculationHistoryMapper; 
    
    private int i = 00;
	@Override
    public List<CalculationFrontHistory> selectBySelectiveKey(CalculationFrontHistory cal) {
        List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryMapper.selectBySelectiveKey(cal);
        return calculationHistories.size() == 0? Collections.EMPTY_LIST:calculationHistories;
    }

	@Override
	public List<CalculationFrontHistory> selectBySelective(CalculationFrontHistory cal) {
		 List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryMapper.selectBySelective(cal);
	     return calculationHistories.size() == 0? Collections.EMPTY_LIST:calculationHistories;
	}

	@Override
	public List<CalculationFrontHistory> selectBySelectiveTime(CalculationFrontHistory cal) {
		return calculationFrontHistoryMapper.selectBySelectiveTime(cal);
	}
	@Override
	public List<CalculationHistoryDetail> selectByOptions(int page,Map<String,String> map) {
		PageHelper.startPage(page, 6);//111
		return calculationHistoryMapper.selectByOptions(map);
	}
}
