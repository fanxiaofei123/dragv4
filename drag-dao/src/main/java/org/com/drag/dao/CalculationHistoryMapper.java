package org.com.drag.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.CalculationHistory;
import org.com.drag.model.CalculationHistoryDetail;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: CalculationHistoryMapper

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年11月22日 下午5:29:01
 */
public interface CalculationHistoryMapper extends BaseDao<CalculationHistory>{

	/**
	 * 带有条件查询
	 * @param cal
	 * @return
	 */
    List<CalculationHistory> selectBySelectiveKey(CalculationHistory cal);
    
    /**
     * 无条件查询后台日志
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

    List<CalculationHistoryDetail> selectByOptions(Map<String,String> map);

    List<CalculationHistory> selectByFlowId(String flowId);

    //List<CalculationHistoryDetail> selectByOptions(@Param("frontusername")String frontusername,@Param("status") String status, @Param("name")String name,@Param("type")String type);
}