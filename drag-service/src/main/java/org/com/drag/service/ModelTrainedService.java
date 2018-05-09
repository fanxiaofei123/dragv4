package org.com.drag.service;

import org.com.drag.model.ModelTrained;

import java.util.List;
import java.util.Map;

public interface ModelTrainedService extends BaseService<ModelTrained> {


    int deleteById(Integer modelTrainedId);

    int batchDelete(String[] modelTrainedIds);

    String getInputPathById(Integer modelTrainedId);
    String getDataModelNameById(Integer modelTrainedId);

    List<ModelTrained> selectByIdAndName(Map<String, Object> params);
}