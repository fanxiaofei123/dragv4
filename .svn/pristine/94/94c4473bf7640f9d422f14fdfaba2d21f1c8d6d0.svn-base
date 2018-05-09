package org.com.drag.service.impl;

import org.com.drag.dao.ServiceModelDao;
import org.com.drag.model.ServiceModel;
import org.com.drag.service.ServiceModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("serviceModelService")
public class ServiceModelServiceImpl extends BaseServiceImpl<ServiceModel>  implements ServiceModelService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceModelServiceImpl.class);

    @Autowired
    private ServiceModelDao serviceModelDao;

    @Override
    public Integer deleteById(String[] templateId) {
        return serviceModelDao.deleteById(templateId);
    }

    @Override
    public List<ServiceModel> selectAllSerAnyThing(ServiceModel serviceModel) {

        return  serviceModelDao.selectAllSerAnyThing(serviceModel);
    }

    @Override
    public Integer selectFlowIdByStatus(Integer flowId) {
        return serviceModelDao.selectFlowIdByStatus(flowId);
    }

    @Override
    public Integer selectFlowIdByRunStatus(Integer flowId) {
        return serviceModelDao.selectFlowIdByRunStatus(flowId);
    }

    @Override
    public Integer insertServiceModel(ServiceModel serviceModel) {
        return serviceModelDao.insertServiceModel(serviceModel);
    }

    @Override
    public Integer deleteByServiceModelFlowId(Integer serviceModelFlowId) {
        return serviceModelDao.deleteByServiceModelFlowId(serviceModelFlowId);
    }

    @Override
    public ServiceModel selectByServiceModelFlowId(Integer serviceModelFlowId) {
        return serviceModelDao.selectByServiceModelFlowId(serviceModelFlowId);
    }

    @Override
    public Integer updateByFlowId(ServiceModel serviceModel1) {
        return serviceModelDao.updateByFlowId(serviceModel1);
    }

    @Override
    public Integer updateModelContext(ServiceModel serviceModel) {

        return  serviceModelDao.updateModelContext(serviceModel);
    }

    @Override
    public List selectByTypeEight() {
        return serviceModelDao.selectByTypeEight();
    }

    @Override
    public List selectByTypeEleven() {
        return serviceModelDao.selectByTypeEleven();
    }

    @Override
    public Integer updateModelCount(ServiceModel serviceModel) {
       return serviceModelDao.updateModelCount(serviceModel);
    }
}