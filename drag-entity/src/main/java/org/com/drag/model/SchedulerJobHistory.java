package org.com.drag.model;
import java.io.Serializable;
import java.util.Date;

public class SchedulerJobHistory  implements Serializable {
    private static final long serialVersionUID = 147400191483079050L;

    private Integer schJobHisId;

    private Integer schJobId;

    private String schJobHisStartTime;

    private String schJobHisEndTime;

    private Byte schJobHisStatus;

    private Integer schJobHisRunTime;

    private String schJobHisLog;

    private Integer schJobHisUserId;

    private String jobId;//oozie上的jobId;


    private String sjName;

    private String loginName;

    private String createTime;



    private String firstTime;

    private String lastTime;

    private Integer num;

    private Integer order;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public Integer getSchJobHisUserId() {
        return schJobHisUserId;
    }

    public void setSchJobHisUserId(Integer schJobHisUserId) {
        this.schJobHisUserId = schJobHisUserId;
    }

    public Integer getSchJobHisId() {
        return schJobHisId;
    }

    public void setSchJobHisId(Integer schJobHisId) {
        this.schJobHisId = schJobHisId;
    }

    public Integer getSchJobId() {
        return schJobId;
    }

    public void setSchJobId(Integer schJobId) {
        this.schJobId = schJobId;
    }

    public String getSchJobHisStartTime() {
        return schJobHisStartTime;
    }

    public void setSchJobHisStartTime(String schJobHisStartTime) {
        this.schJobHisStartTime = schJobHisStartTime;
    }

    public String getSchJobHisEndTime() {
        return schJobHisEndTime;
    }

    public void setSchJobHisEndTime(String schJobHisEndTime) {
        this.schJobHisEndTime = schJobHisEndTime;
    }

    public Byte getSchJobHisStatus() {
        return schJobHisStatus;
    }

    public void setSchJobHisStatus(Byte schJobHisStatus) {
        this.schJobHisStatus = schJobHisStatus;
    }

    public Integer getSchJobHisRunTime() {
        return schJobHisRunTime;
    }

    public void setSchJobHisRunTime(Integer schJobHisRunTime) {
        this.schJobHisRunTime = schJobHisRunTime;
    }

    public String getSchJobHisLog() {
        return schJobHisLog;
    }

    public void setSchJobHisLog(String schJobHisLog) {
        this.schJobHisLog = schJobHisLog;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}