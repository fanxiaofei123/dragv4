package org.com.drag.service.oozie.api;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.com.drag.service.oozie.bean.WorkflowInfomation;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: OozieApi

 * @Description: oozie的api接口

 * @author: jiaonanyue

 * @date: 2016年10月4日 下午4:21:33
 */
public interface IOozieApi {
	
	 /**
     * 提交oozie工作流任务，不会立即执行，需要手动start才执行
     * @param conf job configuration
     * @return job Id
     */
    String oozieWorkflowSubmit(Properties conf,String oozieServerUrl);

    /**
     *  开始执行工作流任务
     * @param jobId
     * @return boolean 启动成功返回true
     */
    boolean oozieWorkflowStart(String jobId,String oozieServerUrl);

    /**
     * 根据jobId暂停工作流任务的执行
     * @param jobId
     * @return
     */
    boolean oozieWorkflowSuspend(String jobId,String oozieServerUrl);

    /**
     * 继续执行暂停的工作流任务
     * @param jobId
     * @return boolean
     */
    boolean oozieWorkflowResume(String jobId,String oozieServerUrl);

    /**
     * 直接执行工作流任务
     * @param conf
     * @return
     */
    String oozieWorkflowRun(Properties conf,String oozieServerUrl) throws OozieClientException;

    /**
     * 停止正在执行的工作流任务
     * @param jobId
     * @return
     */
    boolean oozieWorkflowStop(String jobId,String oozieServerUrl);

    /**
     * 根据jobId获取工作流的信息
     * @param jobId
     * @return WorkflowJob
     */
    WorkflowJob getOozieWorkflowInfo(String jobId,String oozieServerUrl);

    /**
     * 获取指定工作流任务的状态
     * @param jobId
     * @return job status
     */
    String getWorkflowStatus(String jobId,String oozieServerUrl);

    /**
     * 按照条件对任务进行过滤查询
     * @param filterKey 该参数必须是
     * @param filterValue
     * @return
     */
    public List<WorkflowJob> getJobsWithFilter(String filterKey, String filterValue,String oozieServerUrl);


    /**
     * 按多个过滤条件对任务进行查询
     * @param filters：多个过滤条件
     * @return List<WorkflowJob>
     */
    public  List<WorkflowJob> getJobsWithMultiFilter(HashMap<String, String> filters,String oozieServerUrl);


    public WorkflowInfomation getWorkflowInfo(String jobId,int totalAction,String oozieServerUrl) ;
    
    public String getJobLogs(String jobId,String oozieServerUrl) throws OozieClientException;
}
