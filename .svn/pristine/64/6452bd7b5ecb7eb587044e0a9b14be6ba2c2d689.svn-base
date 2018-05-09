package org.com.drag.service;

import java.util.List;

import org.com.drag.model.CalculationHistory;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: CalculationHistoryService

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月22日 下午5:29:58
 */
public interface CalculationHistoryService extends BaseService<CalculationHistory> {

	/**
	 * 带有条件查询
	 * @param cal
	 * @return
	 */
    List<CalculationHistory> selectBySelectiveKey(CalculationHistory cal);
    
    /**
     * 无条件查询
     * @param cal
     * @return
     */
    List<CalculationHistory> selectBySelective(CalculationHistory cal);
    
    /**
     * 无条件查询前台日志
     * @param cal
     * @return
     */
    List<CalculationHistory> selectBySelectiveF();
    
    /**
     * 无条件查询所有日志
     * @param cal
     * @return
     */
    List<CalculationHistory> selectBySelectiveAll();
    

    /**
     * 通过时间查询日志
     * @param cal
     * @return
     */
    List<CalculationHistory> selectBySelectiveTime(CalculationHistory cal);

    List<CalculationHistory> selectBySelectiveFTime(CalculationHistory calculationHistory);

    List<CalculationHistory> selectBySelectiveHTime(CalculationHistory calculationHistory);

    List<CalculationHistory> selectByFlowId(String flowId);
}
