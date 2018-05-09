package org.com.drag.service.impl;

import org.com.drag.dao.ReadResourceDao;
import org.com.drag.model.ResourceInfo;
import org.com.drag.service.ReadResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25/025.
 */
@Service
public class ReadResourceServiceImpl implements ReadResourceService<ResourceInfo> {

    @Autowired
    private ReadResourceDao<ResourceInfo> readResourceDao;

    @Override
    public String testConnection(ResourceInfo resourceInfo) {
        return readResourceDao.testConnection(resourceInfo);
    }

    @Override
    public List<String> getContentResultSet(ResourceInfo resourceInfo) {
        return readResourceDao.getContentData(resourceInfo);
    }

    @Override
    public List<String> getTableNames(ResourceInfo resourceInfo) throws Exception {
        return readResourceDao.getTableNames(resourceInfo);
    }

    @Override
    public String  getMetaResultSet(ResourceInfo resourceInfo) {
        return readResourceDao.getMetaData(resourceInfo);
    }

    @Override
    public List<String> getContentData(ResourceInfo resourceInfo) {
        return readResourceDao.getContentData(resourceInfo);
    }

    @Override
    public String getMetaData(ResourceInfo resourceInfo) {
        return readResourceDao.getMetaData(resourceInfo);
    }
}
