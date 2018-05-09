package org.com.drag.dao;

import org.com.drag.model.ServiceModel;

import java.util.List;

public interface ServiceModelDao extends BaseDao<ServiceModel> {
    Integer deleteById(String[] templateId);

    List<ServiceModel> selectAllSerAnyThing(ServiceModel serviceModel);

    Integer selectFlowIdByStatus(Integer flowId);

    Integer selectFlowIdByRunStatus(Integer flowId);

    Integer insertServiceModel(ServiceModel serviceModel);

    Integer deleteByServiceModelFlowId(Integer serviceModelFlowId);

    ServiceModel selectByServiceModelFlowId(Integer serviceModelFlowId);

    Integer updateByFlowId(ServiceModel serviceModel1);

    Integer updateModelContext(ServiceModel serviceModel);

    List selectByTypeEight();

    List selectByTypeEleven();

    Integer updateModelCount(ServiceModel serviceModel);
}