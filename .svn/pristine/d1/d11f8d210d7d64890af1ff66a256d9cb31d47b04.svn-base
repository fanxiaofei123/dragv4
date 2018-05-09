package org.com.drag.service.oozie.bean;

import org.apache.oozie.client.WorkflowAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 有关工作流任务的相关信息
 * Created by zhh on 2016/9/8.
 */
public class WorkflowInfomation implements Serializable{

    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -5704518321745714881L;
	/**工作流id*/
    private String jobId;
    /**工作流的执行状态*/
    private boolean jobStatus;
    private int status;
    /**工作流创建时间*/
    private Date jobCreateTime;
    /**工作流完成时间*/
    private Date jobStartTime;
    /**工作流完成时间*/
    private Date jobFinishTime;
    /**当前工作流的所属者*/
    private String jobOwner;
    /**工作流的配置信息*/
    private String jobConf;
    /**工作流的workflow.xml所在路径*/
    private String jobApplicationPath;
    /**获取工作流的执行进度*/
    private String rateOfJob;

    /** 返回workflow中所有的action节点信息*/
    private List<WorkflowAction> actions = new ArrayList<WorkflowAction>();

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public boolean getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(boolean jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getJobCreateTime() {
        return jobCreateTime;
    }

    public void setJobCreateTime(Date jobCreateTime) {
        this.jobCreateTime = jobCreateTime;
    }

    public Date getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(Date jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public Date getJobFinishTime() {
        return jobFinishTime;
    }

    public void setJobFinishTime(Date jobFinishTime) {
        this.jobFinishTime = jobFinishTime;
    }

    public String getJobOwner() {
        return jobOwner;
    }

    public void setJobOwner(String jobOwner) {
        this.jobOwner = jobOwner;
    }

    public String getJobConf() {
        return jobConf;
    }

    public void setJobConf(String jobConf) {
        this.jobConf = jobConf;
    }

    public String getJobApplicationPath() {
        return jobApplicationPath;
    }

    public void setJobApplicationPath(String jobApplicationPath) {
        this.jobApplicationPath = jobApplicationPath;
    }

    public List<WorkflowAction> getActions() {
        return actions;
    }

    public void setActions(List<WorkflowAction> actions) {
        this.actions = actions;
    }

    public String getRateOfJob() {
        return rateOfJob;
    }

    public void setRateOfJob(String rateOfJob) {
        this.rateOfJob = rateOfJob;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
