package org.com.drag.service;

import java.util.List;

import org.com.drag.model.UploadHistory;

/**
 * Created by cdyoue on 2016/11/17.
 */
public interface UploadHistoryService extends BaseService<UploadHistory> {
    List<UploadHistory> selectByUserKey(Integer id,Integer limit);

    List<UploadHistory> selectAll();
    
    /**
	 * 查询全部数据数量
	 * @return
	 */
	public int countAll();
}
