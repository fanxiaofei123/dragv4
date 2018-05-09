package org.com.drag.web.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** 
 * inject SchedulerFactoryBean into spring context.
 * 
 * @author raojun@youedata.com 
 * 2017年8月21日 
 */
@Configuration
public class SchedulerConfiguration {
	@Autowired
	QuartzJobFactory jobFactory;
	
    @Bean
    public SchedulerFactoryBean schedulerFactory(){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setJobFactory(jobFactory);
        return bean;
    }
}
