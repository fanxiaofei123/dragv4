package org.com.drag.model;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SchedulerJob implements Serializable {
    private static final long serialVersionUID = 528885254005035288L;

    private Integer schJobId;

    private Integer schJobWfId;

    private String schJobName;

    private Boolean schJobEnable;

    private Date schJobStartTime;

    private Date schJobEndTime;

    private Byte schJobMode;

    private Integer schJobInterval;

    private Byte schJobIntervalUnit;

    private Date schJobCreateTime;

    private Date schJobLastModify;

    private String schJobDesc;
    //把用户的ID和工作流的名称定义到SchedulerJob
    private Integer userId;

    private String workFlowName;

    private String startTime;

    private String endTime;

    private String jobModify;

    private String createTime;
    //调度的状态间隔时间存在不
    private String runStatusTimes;
    //判断调度是否是编辑的
    private Boolean yesOrNo;
   //编辑过的为true
    private Byte editSchedulerJob=0;
    //判断调度是否跑过一次，如果跑过一次为1，否则没跑为0
    private Byte runningOver;

    public Byte getEditSchedulerJob() {
        return editSchedulerJob;
    }

    public void setEditSchedulerJob(Byte editSchedulerJob) {
        this.editSchedulerJob = editSchedulerJob;
    }

    private Map<String,Object> schedulerStatusMap=new HashedMap();

    public Byte getRunningOver() {
        return runningOver;
    }

    public void setRunningOver(Byte runningOver) {
        this.runningOver = runningOver;
    }

    public Map<String, Object> getSchedulerStatusMap() {
        return schedulerStatusMap;
    }

    public void setSchedulerStatusMap(Map<String, Object> schedulerStatusMap) {
        this.schedulerStatusMap = schedulerStatusMap;
    }

    public Boolean getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(Boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public String getRunStatusTimes() {
        return runStatusTimes;
    }

    public void setRunStatusTimes(String runStatusTimes) {
        this.runStatusTimes = runStatusTimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getJobModify() {
        return jobModify;
    }

    public void setJobModify(String jobModify) {
        this.jobModify = jobModify;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public Integer getSchJobId() {
        return schJobId;
    }

    public void setSchJobId(Integer schJobId) {
        this.schJobId = schJobId;
    }

    public Integer getSchJobWfId() {
        return schJobWfId;
    }

    public void setSchJobWfId(Integer schJobWfId) {
        this.schJobWfId = schJobWfId;
    }

    public String getSchJobName() {
        return schJobName;
    }

    public void setSchJobName(String schJobName) {
        this.schJobName = schJobName;
    }

    public Boolean getSchJobEnable() {
        return schJobEnable;
    }

    public void setSchJobEnable(Boolean schJobEnable) {
        this.schJobEnable = schJobEnable;
    }

    public Date getSchJobStartTime() {
        return schJobStartTime;
    }

    public void setSchJobStartTime(Date schJobStartTime) {
        this.schJobStartTime = schJobStartTime;
    }

    public Date getSchJobEndTime() {
        return schJobEndTime;
    }

    public void setSchJobEndTime(Date schJobEndTime) {
        this.schJobEndTime = schJobEndTime;
    }

    public Byte getSchJobMode() {
        return schJobMode;
    }

    public void setSchJobMode(Byte schJobMode) {
        this.schJobMode = schJobMode;
    }

    public Integer getSchJobInterval() {
        return schJobInterval;
    }

    public void setSchJobInterval(Integer schJobInterval) {
        this.schJobInterval = schJobInterval;
    }

    public Byte getSchJobIntervalUnit() {
        return schJobIntervalUnit;
    }

    public void setSchJobIntervalUnit(Byte schJobIntervalUnit) {
        this.schJobIntervalUnit = schJobIntervalUnit;
    }

    public Date getSchJobCreateTime() {
        return schJobCreateTime;
    }

    public void setSchJobCreateTime(Date schJobCreateTime) {
        this.schJobCreateTime = schJobCreateTime;
    }

    public Date getSchJobLastModify() {
        return schJobLastModify;
    }

    public void setSchJobLastModify(Date schJobLastModify) {
        this.schJobLastModify = schJobLastModify;
    }

    public String getSchJobDesc() {
        return schJobDesc;
    }

    public void setSchJobDesc(String schJobDesc) {
        this.schJobDesc = schJobDesc;
    }


}