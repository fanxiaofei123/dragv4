package org.com.drag.service.oozie.scheduler.oozie.impl;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.service.oozie.bean.WorkflowInfomation;
import org.com.drag.service.oozie.scheduler.oozie.HandleResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 工作流客户端实现类
 * Created by zhh on 2016/9/8.
 */
@Service("iOozieApi")
public class OozieClientServiceImpl implements IOozieApi {

    private Logger logger = LoggerFactory.getLogger(OozieClientServiceImpl.class);

    //private OozieClient oozieClient;


    public OozieClientServiceImpl() {
		super();
	}
    
	/*public OozieClientServiceImpl(String oozieServerUrl) {
        this.oozieClient = new OozieClient(oozieServerUrl);
    }*/

	
	public OozieClient getOozieClient(String oozieServerUrl){
		
		OozieClient  oozieClient = new OozieClient(oozieServerUrl);
		return oozieClient;
	}
	
	
    public String oozieWorkflowSubmit(Properties conf,String oozieServerUrl) {
        if(conf==null){
            logger.error(HandleResultEnum.CONF_INVALID.getMessage());
            return HandleResultEnum.FAIL.getMessage();
        }
        String jobId = "";
        try {
            jobId = getOozieClient(oozieServerUrl).submit(conf);
        }catch (OozieClientException e){
            logger.error("OozieClient submit workflow job failed",e.getMessage());
            return HandleResultEnum.FAIL.getMessage();
        }
        return jobId;
    }

    public boolean oozieWorkflowStart(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return false;
        }
        boolean res = true;
        try {
        	getOozieClient(oozieServerUrl).start(jobId);
        }catch (OozieClientException e){
            res = false;
            logger.error("OozieClient start workflow job failed. ",e.getMessage());
        }
        return res;
    }

    public boolean oozieWorkflowSuspend(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return false;
        }
        boolean res = true;
        try {
        	getOozieClient(oozieServerUrl).suspend(jobId);
        }catch (OozieClientException e){
            res = false;
            logger.error("OozieClient Suspend workflow job failed. ",e.getMessage());
        }
        return res;
    }

    public boolean oozieWorkflowResume(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return false;
        }
        boolean res = true;
        try {
        	getOozieClient(oozieServerUrl).resume(jobId);
        } catch (OozieClientException e) {
            res = false;
            logger.error(e.getMessage());
        }
        return res;
    }

    public String oozieWorkflowRun(Properties conf,String oozieServerUrl) throws OozieClientException {
        if(conf==null){
            logger.error(HandleResultEnum.CONF_INVALID.getMessage());
            return HandleResultEnum.FAIL.getMessage();
        }
        String jobId = null;
        jobId = getOozieClient(oozieServerUrl).run(conf);
        return jobId;
    }

    public boolean oozieWorkflowStop(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return false;
        }
        boolean res = true;
        try {
        	getOozieClient(oozieServerUrl).kill(jobId);
        }catch (OozieClientException e){
            res = false;
            logger.error("OozieClient kill workflow job failed. ",e.getMessage());
        }
        return res;
    }

    public WorkflowJob getOozieWorkflowInfo(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return null;
        }
        WorkflowJob workflowJobInfo = null;
        try {
           workflowJobInfo = getOozieClient(oozieServerUrl).getJobInfo(jobId);
        }catch (OozieClientException e){
            logger.error("Get workflow job information failed",e.getMessage());
        }
        return workflowJobInfo;
    }

    public WorkflowInfomation getWorkflowInfo(String jobId,int totalAction,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return null;
        }
        WorkflowInfomation workflowInfo = new WorkflowInfomation();
        try {
            WorkflowJob jobInfo = getOozieClient(oozieServerUrl).getJobInfo(jobId);
            workflowInfo.setJobOwner(jobInfo.getUser());
            workflowInfo.setJobConf(jobInfo.getConf());
            workflowInfo.setJobApplicationPath(jobInfo.getAppPath());
            workflowInfo.setJobCreateTime(jobInfo.getCreatedTime());
            workflowInfo.setJobStartTime(jobInfo.getStartTime());
            workflowInfo.setJobFinishTime(jobInfo.getEndTime());
            String status = jobInfo.getStatus().toString();

            if("SUCCEEDED".equalsIgnoreCase(status)||"KILLED".equalsIgnoreCase(status)||"FAILED".equalsIgnoreCase(status)){
                    workflowInfo.setJobStatus(true);
            }else{
                workflowInfo.setJobStatus(false);
            }
            if("RUNNING".equalsIgnoreCase(status)){
                workflowInfo.setStatus(1);
            }else if("KILLED".equalsIgnoreCase(status)||"FAILED".equalsIgnoreCase(status)){
                workflowInfo.setStatus(-1);
            }else if("SUCCEEDED".equalsIgnoreCase(status)){
                workflowInfo.setStatus(2);
            }
            List<WorkflowAction> actions = jobInfo.getActions();
            workflowInfo.setActions(actions);
            workflowInfo.setRateOfJob(getRateOfWorkflow(actions,totalAction));

        }catch (OozieClientException e){


            logger.error("Get workflow job information failed",e.getMessage());
        }
        return workflowInfo;

    }

    private String getRateOfWorkflow(List<WorkflowAction> actions,int totalAction){
        int countOk = 0;
        int tmpCount = 2;
        for (WorkflowAction action : actions) {
            if(action.getType().equalsIgnoreCase(":FORK:")||action.getType().equalsIgnoreCase(":JOIN:")){
                tmpCount++;
            }
            if("OK".equalsIgnoreCase(action.getStatus().toString())){
                countOk++;
            }
        }
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        double result = ((double)countOk/(totalAction+tmpCount));
        return nt.format(result);
    }

    public String getWorkflowStatus(String jobId,String oozieServerUrl) {
        if(jobId==null||"".equals(jobId)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return null;
        }
        String jobStatus = null;
        try {
            jobStatus = getOozieClient(oozieServerUrl).getStatus(jobId);
        }catch (OozieClientException e){
            logger.error("Get workflow job status failed. ",e.getMessage());
        }
        return jobStatus;
    }

    /**
     * 按照条件对任务进行过滤查询
     * @param filterKey 该参数必须是
     * @param filterValue
     * @return
     */
    public  List<WorkflowJob> getJobsWithFilter(String filterKey,String filterValue,String oozieServerUrl) {
        if(filterKey==null||"".equals(filterKey)||filterValue==null||"".equals(filterValue)){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return null;
        }
        List<WorkflowJob> jobsInfo = null;
        try {
            jobsInfo = getOozieClient(oozieServerUrl).getJobsInfo(filterKey+"="+filterValue);
        }catch (OozieClientException e){
            logger.error("Get workflow job information failed",e.getMessage());
        }
        return jobsInfo;
    }

    /**
     * 按多个过滤条件对任务进行查询
     * @param filters：多个过滤条件
     * @return List<WorkflowJob>
     */
    public  List<WorkflowJob> getJobsWithMultiFilter(HashMap<String,String> filters,String oozieServerUrl) {
        if(filters.isEmpty()){
            logger.error(HandleResultEnum.JOB_ID_INVALID.getMessage());
            return null;
        }
        List<WorkflowJob> jobsInfo = null;
        try {
            Set<Map.Entry<String, String>> entries = filters.entrySet();
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> entry : entries){
                sb.append(entry.getKey()+"="+entry.getValue());
                sb.append(";");
            }
            jobsInfo = getOozieClient(oozieServerUrl).getJobsInfo(sb.toString().substring(0,sb.toString().length()-1));
        }catch (OozieClientException e){
            logger.error("Get workflow job information failed",e.getMessage());
        }
        return jobsInfo;
    }

    public String getJobLogs(String jobId,String oozieServerUrl) throws OozieClientException {
         return getOozieClient(oozieServerUrl).getJobLog(jobId);
    }

    public static void main(String[] args) {
      /*  OozieClientServiceImpl oozieClient = new OozieClientServiceImpl("http://hadoop2:11000/oozie/");
        String jobId = "0000001-170203161401802-oozie-oozi-W";
        WorkflowInfomation workflow = oozieClient.getWorkflowInfo(jobId,5);
        System.out.println("rate="+workflow.getRateOfJob());
       // WorkflowJob jobInfo = oozieClient.getJobInfo(jobId,5);

        try {
            System.out.println(oozieClient.getJobLogs(jobId));
        } catch (OozieClientException e) {
            e.printStackTrace();
        }*/
    }


}
