package org.com.drag.common.quartz;

import org.com.drag.common.util.Constants;
import org.quartz.Scheduler;

/** 
 * job context which store basic info about scheduler job.
 * 
 * @author raojun@youedata.com 
 * 2017年8月21日 
 */
public class JobContext {
	Scheduler scheduler;
	int jobId;
	String groupName;
	String jobName;
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public JobContext(Scheduler scheduler, int jobId, String userName) {
		this.scheduler = scheduler;
		this.jobId = jobId;
		this.groupName = Constants.SCHEDULER_JOB_QUARTZ_GROUP_PREFIX+userName;
		this.jobName = Constants.SCHEDULER_JOB_QUARTZ_NAME_PREFIX+jobId;
	}
}
