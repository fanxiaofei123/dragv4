package org.com.drag.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.com.drag.dao.UploadHistoryMapper;
import org.com.drag.model.UploadHistory;
import org.com.drag.service.UploadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cdyoue on 2016/11/17.
 */
@Service
public class UploadHistoryServiceImpl extends BaseServiceImpl<UploadHistory> implements UploadHistoryService {
    @Autowired
    private UploadHistoryMapper uploadHistoryMapper;
    @Override
    public List<UploadHistory> selectByUserKey(Integer id,Integer limit) {
        Map<String, Integer> condition = new HashedMap();
        condition.put("id", id);
        condition.put("limit", limit);
        List<UploadHistory> uploadHistories = uploadHistoryMapper.selectByUserKey(condition);
        for (UploadHistory s :uploadHistories) {
            s.setHdfsUrl(s.getHdfsUrl(s));
        }
        return uploadHistories.size() == 0 ? Collections.EMPTY_LIST:uploadHistories ;
    }

    @Override
    public List<UploadHistory> selectAll() {
        return uploadHistoryMapper.selectAll();
    }

	@Override
	public int countAll() {

		
		return uploadHistoryMapper.countAll();
	}
}
