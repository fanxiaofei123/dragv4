package org.com.drag.service.impl;

import org.com.drag.dao.TipConfigDao;
import org.com.drag.model.TipConfig;
import org.com.drag.service.TipConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tipConfigService")
public class TipConfigServiceImpl extends BaseServiceImpl<TipConfig> implements TipConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TipConfigServiceImpl.class);

    @Autowired
    private TipConfigDao tipConfigDao;

    @Override
    public int deleteById(String[] id) {

        return tipConfigDao.deleteById(id);
    }

    @Override
    public List<TipConfig> selectByJobName(String userLoginName) {
        return tipConfigDao.selectByJobName(userLoginName);
    }
}