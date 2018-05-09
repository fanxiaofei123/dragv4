package org.com.drag.dao;


import org.com.drag.model.CalculationFrontHistory;

import java.util.List;

public interface CalculationFrontHistoryMapper extends BaseDao<CalculationFrontHistory>{

	/**
	 * 带有条件查询
	 * @param cal
	 * @return
	 */
    List<CalculationFrontHistory> selectBySelectiveKey(CalculationFrontHistory cal);
    
    /**
     * 无条件查询
     * @param cal
     * @return
     */
    List<CalculationFrontHistory> selectBySelective(CalculationFrontHistory cal);
    
    /**
     * 通过时间查询日志
     * @param cal
     * @return
     */
    List<CalculationFrontHistory> selectBySelectiveTime(CalculationFrontHistory cal);
    
}