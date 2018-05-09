package org.com.drag.web.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.com.drag.common.util.Constants;
import org.com.drag.model.SchedulerJob;
import org.quartz.*;
import org.quartz.spi.MutableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * util to create, update or cancel the scheduled job.
 * 
 * @author raojun@youedata.com 
 * 2017年8月21日 
 */
public class JobSchedulerUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Logger logger = LoggerFactory.getLogger(JobSchedulerUtil.class);
	/**
	 * create a scheduled job
	 * @param context
	 * @return the time point to execute job
	 */
	public static Date schedule(JobContext context) {

	    JobDetail jobDetails = JobBuilder.newJob(ScheduledWorkflowJob.class).withIdentity(context.getSchedulerJob().getSchJobId()+"").build();
	    jobDetails.getJobDataMap().put("context", context);
	    
	    MutableTrigger trigger = prepareTrigger(context);
	    
	    Date date = null;
	    try {
	        date = context.getScheduler().scheduleJob(jobDetails, trigger);
	        logger.info(context.getSchedulerJob().getSchJobId()+" will start at: "+sdf.format(date));
	    } catch (SchedulerException e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return date;
	}


	
	/**
	 * update a scheduled job
	 * @param context
	 */
	public static boolean reschedule(JobContext context) {
		boolean suc = true;
		Scheduler sched = context.getScheduler();
		try {
			Trigger trigger =  sched.getTrigger(TriggerKey.triggerKey(context.getJobName(), context.getGroupName()));
			if(trigger!=null){
				cancel(context);
			}
			JobDetail jobDetails = JobBuilder.newJob(ScheduledWorkflowJob.class).withIdentity(context.getSchedulerJob().getSchJobId()+"").build();
			jobDetails.getJobDataMap().put("context", context);
//			context.getSchedulerJob().setYesAndNoEdit(false);//预选是用户还没有编辑的

			MutableTrigger trigger1 = prepareTrigger(context);
			Date date = context.getScheduler().scheduleJob(jobDetails, trigger1);
			if(date!=null){
				sched.checkExists(trigger1.getJobKey());
			}

		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return suc;
	}
	
	/**
	 * cancel a scheduled job
	 * @param context
	 */
	public static boolean cancel(JobContext context) {
		boolean suc = true;
		Scheduler sched = context.getScheduler();
		try {
			sched.pauseTrigger(TriggerKey.triggerKey(context.getJobName(), context.getGroupName()));
			sched.unscheduleJob(TriggerKey.triggerKey(context.getJobName(), context.getGroupName()));
			sched.deleteJob(new JobKey(context.getJobName(), context.getGroupName()));
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return suc;
	}

	/**
	 * create trigger by scheduler job
	 * @param context
	 * @return
	 */
	private static MutableTrigger prepareTrigger(JobContext context) {
		MutableTrigger trigger = null;

		SchedulerJob schJob = context.getSchedulerJob();
	    byte schJobMode = schJob.getSchJobMode();
	    if(schJobMode==Constants.SCHEDULER_JOB_MODE_ONCE || schJobMode==Constants.SCHEDULER_JOB_MODE_MULTI){
	    	trigger = SimpleScheduleBuilder.simpleSchedule().build();
	    }
//	    else if(schJobMode==Constants.SCHEDULER_JOB_MODE_MULTI){
//	    	byte schJobIntervalUnit = schJob.getSchJobIntervalUnit();
//	    	int schJobInterval = schJob.getSchJobInterval();
//	    	switch (schJobIntervalUnit) {
//			case Constants.SCHEDULER_JOB_INTERVAL_UNIT_MINUTE:
//				trigger = SimpleScheduleBuilder.repeatMinutelyForever(schJobInterval).build();
//				break;
//			case Constants.SCHEDULER_JOB_INTERVAL_UNIT_HOUR:
//				trigger = SimpleScheduleBuilder.repeatHourlyForever(schJobInterval).build();
//				break;
//			case Constants.SCHEDULER_JOB_INTERVAL_UNIT_DAY:
//				trigger = SimpleScheduleBuilder.repeatHourlyForever(schJobInterval*24).build();
//				break;
//
//			default:
//				throw new RuntimeException("Unsupport scheduler job interval unit: "+schJobIntervalUnit);
//			}
//	    }
//	    else {
//	    	throw new RuntimeException("Unsupport scheduler job mode: "+schJobMode);
//	    }
//
	    Date start = schJob.getSchJobStartTime();
	    Date end = schJob.getSchJobEndTime();

	    if(start!=null){
			trigger.setStartTime(start);
	    }
	    if(end!=null){
	    	try{
				trigger.setEndTime(end);
			}catch (Exception e){
	    		e.printStackTrace();
	    		logger.info("放行startTime>endTime"+e.getMessage(), e);
			}

	    }
	    
	    trigger.setKey(TriggerKey.triggerKey(context.getJobName(), context.getGroupName()));
	    
		return trigger;
	}
}
