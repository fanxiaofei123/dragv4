package org.com.drag.dao;


import java.util.List;
import java.util.Map;

import org.com.drag.model.UploadHistory;

public interface UploadHistoryMapper extends  BaseDao<UploadHistory> {

    @SuppressWarnings("rawtypes")
	List<UploadHistory> selectByUserKey(Map condition);

    List<UploadHistory> selectAll();
    
    
    /**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();
}