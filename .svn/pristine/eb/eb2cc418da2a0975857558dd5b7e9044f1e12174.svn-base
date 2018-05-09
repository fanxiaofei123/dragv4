package org.com.drag.service;


import java.util.List;
import java.util.Map;

import org.com.drag.model.CalculationFrontHistory;
import org.com.drag.model.CalculationHistoryDetail;

/**
 * 前台history日志
 */
public interface CalculationFrontHistoryService extends BaseService<CalculationFrontHistory> {
	
	int i = 0;

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

    List<CalculationHistoryDetail> selectByOptions(int page,Map<String,String> map);
}
