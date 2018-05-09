package org.com.drag.service;

import org.com.drag.model.TipConfig;

import java.util.List;

public interface TipConfigService extends BaseService<TipConfig> {
    int deleteById(String[] id);

    List<TipConfig> selectByJobName(String userLoginName);
}