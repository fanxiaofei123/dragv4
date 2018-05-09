package org.com.drag.web.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.com.drag.model.SchedulerJob;
import org.com.drag.service.SchedulerJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/** 
 * scheduler job initializer which invoked by spring context listener.
 * 
 * @author raojun@youedata.com 
 * 2017年8月21日 
 */
@Component
public class JobInitializer implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = LoggerFactory.getLogger(JobInitializer.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
    public  SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if(event.getApplicationContext().getParent() == null){
	        logger.info("Job Initilizer started.");
	        
	        // load all available scheduler jobs
	        List<SchedulerJob> list = schedulerJobService.selectTodoSchedulerJobs();
			String userName=null;
	        for (SchedulerJob schedulerJob : list) {
				userName=schedulerJobService.selectUserNameByJobId(schedulerJob.getSchJobId());
	        	JobContext context = new JobContext(schedulerFactoryBean.getScheduler(), schedulerJob, userName);
	        	Date date = JobSchedulerUtil.schedule(context);
	        	if(date!=null)
	        		logger.info("Scheduler job(groupName: "+context.getGroupName()+", jobName: "+context.getJobName()+") will launched at: "+sdf.format(date));
	        	else
	        		logger.warn("Scheduler job(groupName: "+context.getGroupName()+", jobName: "+context.getJobName()+") failed.");
			}
	        
	        logger.info("Job Initilizer ended.");
    	}
    }
}
