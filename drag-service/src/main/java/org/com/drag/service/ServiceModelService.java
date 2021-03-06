package org.com.drag.service;

import org.com.drag.model.ServiceModel;

import java.util.List;

public interface ServiceModelService extends BaseService<ServiceModel> {
    Integer deleteById(String[] templateId);

    List<ServiceModel> selectAllSerAnyThing(ServiceModel serviceModel);

    /**
     * 查询模型是否部署
     * @param flowId
     * @return
     */
    Integer selectFlowIdByStatus(Integer flowId);

    /**
     * 查询工作流最后一次运行状态
     * @param flowId
     * @return
     */
    Integer selectFlowIdByRunStatus(Integer flowId);

    /**
     * 添加部署
     * @param serviceModel
     * @return
     */
    Integer insertServiceModel(ServiceModel serviceModel);

    /**
     * 删除已有的部署
     * @param serviceModelFlowId
     * @return
     */
    Integer deleteByServiceModelFlowId(Integer serviceModelFlowId);

    ServiceModel selectByServiceModelFlowId(Integer serviceModelFlowId);

    Integer updateByFlowId(ServiceModel serviceModel1);

    /**
     * 字段描述修改
     * @param serviceModel
     */
    Integer updateModelContext(ServiceModel serviceModel);

    List selectByTypeEight();
    List selectByTypeEleven();

    Integer updateModelCount(ServiceModel serviceModel);
}