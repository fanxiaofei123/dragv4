package org.com.drag.service.impl;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.oozie.client.OozieClientException;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.dao.CalculationHistoryMapper;
import org.com.drag.model.CalculationHistory;
import org.com.drag.model.FacadeModel;
import org.com.drag.service.JobServiceApi;
import org.com.drag.service.oozie.api.IOozieApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cdyoue on 2016/12/5.
 */
@Service
public class JobServiceApiImpl implements JobServiceApi {
    @Autowired
    private CalculationHistoryMapper calculationHistoryMapper;
    @Autowired
    private IOozieApi iOozieApi;
    

    @Override
    public Integer run(Properties xmlProperties, final Integer userId, FacadeModel workFlow, final String resultAddress,int modelSzie) {
    	
        String jobId = null;
        try {
        	//运行工作流
            jobId = iOozieApi.oozieWorkflowRun(xmlProperties,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        } catch (OozieClientException e) {
            e.printStackTrace();
        }
        final CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setStatus(1);
        calculationHistory.setWay(workFlow.getName());
        calculationHistory.setResason("暂无");
        calculationHistory.setUserid(userId);
        calculationHistory.setCreateTime(new Date());
        calculationHistory.setDownUrl(resultAddress);
        calculationHistory.setModelId(workFlow.getId());
        calculationHistory.setName(workFlow.getModelName());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        calculationHistory.setFlowId(workFlow.getFlowId());
        //插入历史数据
        int insert = calculationHistoryMapper.insertSelective(calculationHistory);
        executor.scheduleWithFixedDelay(
                        new RunProcessApiThread(executor, jobId, iOozieApi, userId,calculationHistory.getId(),modelSzie),
                        0,
                        1400,
                        TimeUnit.MILLISECONDS);
        return calculationHistory.getId();
        //return jobId;
    }

}