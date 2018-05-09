package org.com.drag.dao;

import org.com.drag.model.TipConfig;

import java.util.List;

public interface TipConfigDao extends BaseDao<TipConfig> {
    int deleteById(String[] id);

    List<TipConfig> selectByJobName(String userLoginName);
}