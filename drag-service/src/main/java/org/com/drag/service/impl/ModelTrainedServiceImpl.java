package org.com.drag.service.impl;

import org.com.drag.dao.ModelTrainedMapper;
import org.com.drag.model.ModelTrained;
import org.com.drag.service.ModelTrainedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("modelTrainedService")
public class ModelTrainedServiceImpl extends BaseServiceImpl<ModelTrained> implements ModelTrainedService {
    private static final Logger logger = LoggerFactory.getLogger(ModelTrainedServiceImpl.class);

    @Autowired
    private ModelTrainedMapper modelTrainedMapper;


    @Override
    public int deleteById(Integer modelTrainedId) {
        return modelTrainedMapper.deleteById(modelTrainedId);
    }

    @Override
    public int batchDelete(String[] modelTrainedIds) {
        return modelTrainedMapper.batchDelete(modelTrainedIds);
    }

    @Override
    public String getInputPathById(Integer modelTrainedId) {
        return modelTrainedMapper.getInputPathById(modelTrainedId);
    }

    @Override
    public String getDataModelNameById(Integer modelTrainedId) {
        return modelTrainedMapper.getDataModelNameById(modelTrainedId);
    }

    @Override
    public List<ModelTrained> selectByIdAndName(Map<String, Object> params) {
        return modelTrainedMapper.selectByIdAndName(params);
    }
}