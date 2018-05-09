package org.com.drag.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.oozie.client.OozieClientException;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.dao.CalculationHistoryMapper;
import org.com.drag.dao.WorkFlowMapper;
import org.com.drag.model.*;
import org.com.drag.service.JobService;
import org.com.drag.service.SchedulerJobHistoryService;
import org.com.drag.service.SchedulerJobService;
import org.com.drag.service.TipConfigService;
import org.com.drag.service.oozie.api.IOozieApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by luojun on 2017/9/1.
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private CalculationHistoryMapper calculationHistoryMapper;
    @Autowired
    private WorkFlowMapper workFlowMapper;
    @Autowired
    private IOozieApi iOozieApi;
    @Autowired
    public TipConfigService tipConfigService;
    @Autowired
    private SchedulerJobHistoryService schedulerJobHistoryService;
    @Autowired
    private SchedulerJobService schedulerJobService;

    private ReentrantLock queueLock = new ReentrantLock();
    private List<String> jobIds = new ArrayList<>();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public  ConcurrentHashMap<Integer,UserRunningInfo> mapList=new ConcurrentHashMap<Integer,UserRunningInfo>() ;

    public  ConcurrentHashMap getMap(){
       return mapList;
    }
    public  ReentrantLock getReentrantLock(){
        return queueLock;
    }

    /**
     *
     * @param xmlProperties
     * @param userId
     * @param flowId
     * @param nameFlow
     * @param resultAddress
     * @param modelSize
     * @param session
     * @param runMethod 算子运行的方式，拖拉拽为0，调度运行为其他
     * @param schJobId 工作调度的id
     * @param schJobHisId 调度任务历史的id
     * @return
     */
    @Override
    public String run(Properties xmlProperties, final Integer userId, final Integer flowId, final String nameFlow, final String resultAddress, int modelSize,
                      HttpSession session,Integer runMethod,Integer schJobId,Integer schJobHisId ) {

        // final OozieClientServiceImpl oc = new OozieClientServiceImpl(MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        String jobId = null;
        try {
            jobId = iOozieApi.oozieWorkflowRun(xmlProperties, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        } catch (OozieClientException e) {
            e.printStackTrace();
            return null;
        }
        if(runMethod==0){

            final CalculationHistory calculationHistory = new CalculationHistory();
            calculationHistory.setStatus(1);
            calculationHistory.setWay(nameFlow);
            calculationHistory.setResason("暂无");
            calculationHistory.setUserid(userId);
            calculationHistory.setCreateTime(new Date());

            CalculationHistory cal = new CalculationHistory();
            cal.setStatus(1);
            WorkFlow workFlow = new WorkFlow() {{
                setId(flowId);
                setJobstatus(1);
                setResultContect(resultAddress);
            }};


            workFlowMapper.updateByPrimaryKeySelective(workFlow);
            calculationHistory.setFlowId(flowId);
            calculationHistory.setJobId(jobId);
            //插入历史数据
            int insert = calculationHistoryMapper.insertSelective(calculationHistory);

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            TipConfig newTipConfig = new TipConfig();
            newTipConfig.setFlowId(flowId);
            List<TipConfig> list = tipConfigService.selectAll(newTipConfig);
            executor.scheduleWithFixedDelay(
                    new RunProcessThread(executor, jobId, iOozieApi, userId, calculationHistory.getId(), modelSize, flowId, list,mapList,queueLock),
                    0,
                    1400,
                    TimeUnit.MILLISECONDS);

            return jobId;
        }else  {
            SchedulerJobHistory schedulerJobHistory=schedulerJobHistoryService.selectSchedulerHistoryJobById(schJobHisId);
            schedulerJobHistory.setJobId(jobId);
            schedulerJobHistoryService.updateSchedulerJobHistory(schedulerJobHistory);

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleWithFixedDelay(
                    new RunSchedulerProcessImpl(executor,schedulerJobHistoryService, jobId,schJobId,schJobHisId,iOozieApi,schedulerJobService),
                    0,
                    1400,
                    TimeUnit.MILLISECONDS);
            return jobId;
        }

    }

}