package org.com.drag.dao;
import org.com.drag.model.ModelTrained;

import java.util.List;
import java.util.Map;

public interface ModelTrainedMapper extends BaseDao<ModelTrained> {

    int deleteById(Integer modelTrainedId);

    int batchDelete(String[] modelTrainedIds);

    String getInputPathById(Integer modelTrainedId);

    String getDataModelNameById(Integer modelTrainedId);

    List<ModelTrained> selectByIdAndName(Map<String, Object> params);
}