package org.com.drag.service.impl;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.SchedulerJob;
import org.com.drag.model.SchedulerJobHistory;
import org.com.drag.service.SchedulerJobHistoryService;
import org.com.drag.service.SchedulerJobService;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.service.oozie.bean.WorkflowInfomation;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by luojun on 2017/9/1.
 */
public class RunSchedulerProcessImpl implements Runnable{

    // 日志对象
    private static Log logger = LogFactory.getLog(RunSchedulerProcessImpl.class);
    private static final Byte OOZIE_STATUS_FAIL=-1;//运行失败
    private static final Byte OOZIE_STATUS_KILL=-1;//s算子被kill
    private static final Byte OOZIE_STATUS_SUCCESSFUL=2;//成功
    private static final Byte OOZIE_STATUS_RUNNING=1;//oozie运行中


    private ScheduledExecutorService scheduledExecutorService;
    private String jobId;

    private Integer schJobHisId;

    private  Integer schJobId;
    @Autowired
    private IOozieApi iOozieApi;

    private SchedulerJobHistoryService schedulerJobHistoryService;

    private SchedulerJobService schedulerJobService;


    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run() {
        WorkflowInfomation workflowInfo = iOozieApi.getWorkflowInfo(jobId,0, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        int status = workflowInfo.getStatus();
        SchedulerJobHistory schedulerJobHistory=schedulerJobHistoryService.selectSchedulerHistoryJobById(schJobHisId);
        //查出任务的历史开始时间
        String schStartTime=schedulerJobHistory.getSchJobHisStartTime().substring(0,19);
        SchedulerJob schedulerJob=schedulerJobService.selectSchedulerJob(schJobId);
        try {
            if(status == OOZIE_STATUS_SUCCESSFUL ){
                schedulerJobHistory.setSchJobId(schJobId);
                schedulerJobHistory.setSchJobHisStatus( Constants.SCHEDULER_JOB_SUCCESSFUL);
                schedulerJobHistory.setSchJobHisEndTime(sdf.format(new Date()));
                logger.info("jobId:"+jobId+"     运行成功");
                String jobLogs = iOozieApi.getJobLogs(jobId, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                schedulerJobHistory.setSchJobHisLog(jobLogs);
                schedulerJobHistory.setSchJobHisRunTime(getSchdulerJobRunTime(schStartTime));
                schedulerJobHistoryService.updateSchedulerJobHistory(schedulerJobHistory);

                if(schedulerJob.getSchJobMode()==Constants.SCHEDULER_JOB_MODE_MULTI){//如果调度没有结束时间就插入一条记录，如果有结束时间，但是结束时间比现在的时间大，就插入记录，否则结束时间比现在的时间小，就不插入
                    if(schedulerJob.getEditSchedulerJob()!=null && schedulerJob.getEditSchedulerJob()==Constants.SCHEDULER_JOB_NOT_EDIT){
                        if(schedulerJob.getSchJobEndTime()==null){
                            insertSchedulerJobHistory(schJobId,schedulerJob);
                        }else  if(schedulerJob.getSchJobEndTime()!=null && (schedulerJob.getSchJobEndTime().getTime() >new Date().getTime())){
                            insertSchedulerJobHistory(schJobId,schedulerJob);
                        }
                    }else {
                        Map<String,Object> map=new HashedMap();
                        map.put("editSchedulerJob",Constants.SCHEDULER_JOB_NOT_EDIT);
                        map.put("schJobId",schJobId);
                        schedulerJobService.editSchedulerJobStatus(map);
                    }
                }
                scheduledExecutorService.shutdown();

            }else if(status == OOZIE_STATUS_FAIL ){
                logger.error("jobId:"+jobId+"     运行失败");
                schedulerJobHistory.setSchJobId(schJobId);
                schedulerJobHistory.setSchJobHisStatus( Constants.SCHEDULER_JOB_FAIL);
                schedulerJobHistory.setSchJobHisEndTime(sdf.format(new Date()));
                String jobLogs = iOozieApi.getJobLogs(jobId, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                schedulerJobHistory.setSchJobHisLog(jobLogs);
                schedulerJobHistory.setSchJobHisRunTime(getSchdulerJobRunTime(schStartTime));
                schedulerJobHistoryService.updateSchedulerJobHistory(schedulerJobHistory);
                /*
                * 插入一条调度待运行的记录，开始时间加间隔时间
                * */
                if(schedulerJob.getSchJobMode()==Constants.SCHEDULER_JOB_MODE_MULTI){//如果调度没有结束时间就插入一条记录，如果有结束时间，但是结束时间比现在的时间大，就插入记录，否则结束时间比现在的时间小，就不插入
                    if(schedulerJob.getEditSchedulerJob()!=null && schedulerJob.getEditSchedulerJob()==Constants.SCHEDULER_JOB_NOT_EDIT){
                        if(schedulerJob.getSchJobEndTime()==null){
                            insertSchedulerJobHistory(schJobId,schedulerJob);
                        }else  if(schedulerJob.getSchJobEndTime()!=null && (schedulerJob.getSchJobEndTime().getTime() >new Date().getTime())){
                            insertSchedulerJobHistory(schJobId,schedulerJob);
                        }
                    }else {
                        Map<String,Object> map=new HashedMap();
                        map.put("editSchedulerJob",Constants.SCHEDULER_JOB_NOT_EDIT);
                        map.put("schJobId",schJobId);
                        schedulerJobService.editSchedulerJobStatus(map);
                    }
                }

                scheduledExecutorService.shutdown();
            }else if(status == OOZIE_STATUS_RUNNING){
                logger.error("jobId:"+jobId+"     运行中");
                schedulerJobHistory.setSchJobId(schJobId);
                schedulerJobHistory.setSchJobHisStatus( Constants.SCHEDULER_JOB_RUNNING);
                schedulerJobHistory.setSchJobHisEndTime(sdf.format(new Date()));
                String jobLogs = iOozieApi.getJobLogs(jobId, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                schedulerJobHistory.setSchJobHisLog(jobLogs);
                schedulerJobHistory.setSchJobHisRunTime(getSchdulerJobRunTime(schStartTime));
                schedulerJobHistoryService.updateSchedulerJobHistory(schedulerJobHistory);
            }
        } catch (Exception e) {
            logger.error("获取日志错误"+e.getMessage());
        }
    }
    public Integer insertSchedulerJobHistory(Integer schJobId,SchedulerJob schedulerJob){

        Integer id=schedulerJobService.getUserBySchJobId(schJobId);
        SchedulerJobHistory schedulerJobHistory=new SchedulerJobHistory();
        schedulerJobHistory.setSchJobHisUserId(id);
        schedulerJobHistory.setCreateTime(sdf.format(schedulerJob.getSchJobCreateTime()));
        schedulerJobHistory.setSjName(schedulerJob.getSchJobName());
        schedulerJobHistory.setSchJobHisStatus( Constants.SCHEDULER_JOB_NOT);
        schedulerJobHistory.setSchJobId(schJobId);
        if(schedulerJob.getSchJobIntervalUnit()==Constants.SCHEDULER_JOB_INTERVAL_UNIT_MINUTE){
            schedulerJobHistory.setSchJobHisStartTime(addSchJobInterval(schedulerJob.getSchJobInterval()));
            schedulerJobHistory.setSchJobHisEndTime(addSchJobInterval(schedulerJob.getSchJobInterval()));
        }else if (schedulerJob.getSchJobIntervalUnit()==Constants.SCHEDULER_JOB_INTERVAL_UNIT_HOUR){
            schedulerJobHistory.setSchJobHisStartTime(addSchJobInterval(schedulerJob.getSchJobInterval()*60));
            schedulerJobHistory.setSchJobHisEndTime(addSchJobInterval(schedulerJob.getSchJobInterval()*60));
        }else {
            schedulerJobHistory.setSchJobHisStartTime(addSchJobInterval(schedulerJob.getSchJobInterval()*60*24));
            schedulerJobHistory.setSchJobHisEndTime(addSchJobInterval(schedulerJob.getSchJobInterval()*60*24));
        }
        schedulerJobHistory.setSchJobHisLog("暂无");
        schedulerJobHistory.setSchJobHisRunTime(0);
        Integer schJobHisId=schedulerJobHistoryService.insertSchedulerJobHistory(schedulerJobHistory);
        logger.info("第二222222222222222222次插入数据-----------------------------------------------------------------------------------------------------------");

        return schedulerJobHistory.getSchJobHisId();

    }
    public static String addSchJobInterval( int x){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, x);// 分钟
        String date=format.format(cal.getTime());
        return date;
    }
    //获取调度的运行时间
    public static Integer getSchdulerJobRunTime(String SchStartTime) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime=null;
        Integer runTime=null;
        try {
            if (SchStartTime!=null){
                startTime=format.parse(SchStartTime);
            }
            runTime= Math.toIntExact(((new Date()).getTime() - startTime.getTime()) / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return runTime;
    }

    public RunSchedulerProcessImpl(ScheduledExecutorService scheduledExecutorService, SchedulerJobHistoryService schedulerJobHistoryService,String jobId, Integer schJobId,Integer schJobHisId,IOozieApi oozieClientService,SchedulerJobService schedulerJobService) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.jobId = jobId;
        this.iOozieApi = oozieClientService;
        this.schJobHisId=schJobHisId;
        this.schedulerJobHistoryService=schedulerJobHistoryService;
        this.schedulerJobService=schedulerJobService;
        this.schJobId=schJobId;
    }
}
