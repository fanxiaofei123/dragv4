package org.com.drag.service.impl;


import org.apache.commons.collections.map.HashedMap;
import org.com.drag.common.page.PageBean;
import org.com.drag.dao.SchedulerJobDao;
import org.com.drag.dao.SchedulerJobHistoryDao;
import org.com.drag.dao.WorkFlowMapper;
import org.com.drag.model.SchedulerJob;

import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.service.SchedulerJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchedulerJobServiceImpl extends BaseServiceImpl<SchedulerJob> implements SchedulerJobService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerJobServiceImpl.class);
    private static final Integer PAGE_NUMBER = 15;
    @Autowired
    private SchedulerJobDao schedulerJobDao;
    @Autowired
    private SchedulerJobHistoryDao schedulerJobHistoryDao;

    /**
     * 获取调度的默认开始的最早时间和最晚的时间
     * @param userId
     * @return
     */
    @Override
    public String getDefaultDate(Integer userId) {
        List<SchedulerJob> schedulerJobList=schedulerJobDao.getSchedulerDefaultDate(userId);
        String defaultDate=null;
        try {
            if(schedulerJobList.size()>0 && schedulerJobList!=null){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if(schedulerJobList.get(0).getSchJobStartTime()!=null &&schedulerJobList.get(schedulerJobList.size()-1).getSchJobStartTime()!=null){
                    String minDate=format.format(schedulerJobList.get(0).getSchJobStartTime());
                    String maxDate=format.format(schedulerJobList.get(schedulerJobList.size()-1).getSchJobStartTime());
                    defaultDate=minDate+" 至 "+maxDate;
                }else {
                    defaultDate="";
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return defaultDate;
    }


    /**
     * 查询出所有的调度
     * @param page
     * @param rowCount
     * @param schJobName
     * @param startTime
     * @param endTime
     * @param user
     * @return
     */
    @Override
    public PageBean selectAllSchedulerJob(Integer page, Integer rowCount, String schJobName, String startTime, String endTime, User user) {

        if (page==null|| page==0){
            page=1;
        }
        if (rowCount==null){
            rowCount=PAGE_NUMBER;
        }

        Map<String,Object> schJobMap=new HashedMap();
        schJobMap.put("userId",user.getId());
        schJobMap.put("schJobName",schJobName);
        if(startTime!=null){
            schJobMap.put("startTime",startTime.trim()+" 0:00:00");
        }
        if(endTime!=null){
            schJobMap.put("endTime",endTime.trim()+" 23:59:59");
        }
        List<SchedulerJob> schedulerJobList=schedulerJobDao.selectAllSchedulerJob(schJobMap);
        if(schedulerJobList!=null && schedulerJobList.size()>0){
            for(SchedulerJob sj:schedulerJobList){
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(sj.getSchJobStartTime()!=null){
                    sj.setStartTime(dateFormat.format(sj.getSchJobStartTime()));
                }
                if(sj.getSchJobEndTime()!=null){
                    sj.setEndTime(dateFormat.format(sj.getSchJobEndTime()));
                }
                if(sj.getSchJobLastModify()!=null){
                    sj.setJobModify(dateFormat.format(sj.getSchJobLastModify()));
                }
            }
        }
        List sjList=new ArrayList<>();
        int startItem=(page-1)*rowCount;
        int endItem=page*rowCount-1;
        int totalPage=0;
        if(rowCount>schedulerJobList.size()){
            totalPage=1;
        }else {
            totalPage=schedulerJobList.size()%rowCount==0?schedulerJobList.size()/rowCount:schedulerJobList.size()/rowCount+1;

        }
        if(endItem>schedulerJobList.size()-1){
            endItem=schedulerJobList.size()-1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(schedulerJobList.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);
        System.out.println(schedulerJobList);
        for (int i=startItem;i<=endItem;i++){
            sjList.add(schedulerJobList.get(i));
        }
        pageBean.setRows(sjList);
        return pageBean;
    }


    /**
     * 查询出工作流的对象
     * @param user
     * @param workFlowName
     * @return
     */
    @Override
    public List<WorkFlow> selectWorkFlowList(User user, String workFlowName) {
        Map<String,Object> param=new HashMap<>();
        param.put("userId",user.getId());
        param.put("workFlowName",workFlowName);
//        查询出不包含调度的工作流
        List<WorkFlow> list=schedulerJobDao.selectWorkFlowList(param);
        return list;
    }

    /**
     * 插入调度到数据库，并启用调度信息
     * @param schedulerJob
     * @return
     */
    @Override
    public Integer insertSchedulerJob(SchedulerJob schedulerJob) {
        Integer one = null;
        try {
            if(schedulerJob.getStartTime().equals("")){
                schedulerJob.setStartTime(null);
            }else {
                schedulerJob.setStartTime(schedulerJob.getStartTime().replaceAll("/","-"));
            }
            if(schedulerJob.getEndTime().equals("")){
                schedulerJob.setEndTime(null);
            }else {
                schedulerJob.setEndTime(schedulerJob.getEndTime().replace("/","-"));
            }
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate=new Date();
            schedulerJob.setCreateTime(dateFormat.format(newDate));
            schedulerJob.setJobModify(dateFormat.format(newDate));
          one=schedulerJobDao.insertSchedulerJob(schedulerJob);
        }catch (Exception e){
            one=0;
            e.printStackTrace();
        }

        return one;
    }

    /**
     * 查询出单个调度任务情况
     * @param schJobId
     * @return
     */
    @Override
    public SchedulerJob selectSchedulerJob(Integer schJobId) {
        SchedulerJob schedulerJob=schedulerJobDao.selectSchedulerJob(schJobId);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(schedulerJob.getSchJobStartTime()!=null){
            schedulerJob.setStartTime(dateFormat.format(schedulerJob.getSchJobStartTime()));
        }
        if(schedulerJob.getSchJobEndTime()!=null){
            schedulerJob.setEndTime(dateFormat.format(schedulerJob.getSchJobEndTime()));
        }
        if(schedulerJob.getSchJobLastModify()!=null){
            schedulerJob.setJobModify(dateFormat.format(schedulerJob.getSchJobLastModify()));
        }
        if(schedulerJob.getSchJobCreateTime()!=null){
            schedulerJob.setCreateTime(dateFormat.format(schedulerJob.getSchJobCreateTime()));
        }
        return schedulerJob;
    }

    /**
     * 编辑调度情况
     * @param schedulerJob
     * @return
     */
    @Override
    public Integer
     editSchedulerJob(SchedulerJob schedulerJob) {
        Integer one = null;

        try {
            if(schedulerJob.getStartTime().equals("")){
                schedulerJob.setStartTime(null);
            }else {
                schedulerJob.setStartTime(schedulerJob.getStartTime().replaceAll("/","-"));
            }
            if(schedulerJob.getEndTime().equals("")){
                schedulerJob.setEndTime(null);
            }else {
                schedulerJob.setEndTime(schedulerJob.getEndTime().replace("/","-"));
            }
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate=new Date();
            schedulerJob.setJobModify(dateFormat.format(newDate));
            one=schedulerJobDao.editSchedulerJob(schedulerJob);
        }catch (Exception e){
            one=0;
            e.printStackTrace();
        }

        return one;
    }

    /**
     * 删除调度
     * @param schJobId
     * @return
     */
    @Override
    public Integer deleteSchedulerJob(Integer schJobId) {
        Integer schedulerJob=null;
        //删除任务
        try {

            //删除调度
            schedulerJob=schedulerJobDao.deleteSchedulerJob(schJobId);
        }catch (Exception e){
            schedulerJob=0;
            e.printStackTrace();
        }

        return schedulerJob;

    }

    /**
     * 批量删除
     * @param array
     * @return
     */
    @Override
    public Integer batchDeleteSchedulerJob(Integer[] array) {
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i<array.length; i++){
            list.add(array[i]);
        }
        Integer count=null;
        try {
            count=schedulerJobDao.batchDeleteSchedulerJob(list);
        }catch (Exception e){
            count=0;
            e.printStackTrace();
        }
        return count;
    }


    @Override
	public List<SchedulerJob> selectTodoSchedulerJobs() {
		 return schedulerJobDao.selectTodoSchedulerJobs();
	}

    @Override
    public String selectUserNameByJobId(Integer schJobId) {
        return schedulerJobDao.selectUserNameByJobId(schJobId);
    }

    @Override
    public Integer findFlowIdBySchJobId(Integer schJobId) {
        Integer id=schedulerJobDao.findFlowIdBySchJobId(schJobId);
        return id;
    }

    @Override
    public Integer findSchJobIdBySchJobWfId(Integer schJobWfId) {
        Integer id=schedulerJobDao.findSchJobIdBySchJobWfId(schJobWfId);
        return id;
    }

    @Override
    public Integer getUserBySchJobId(Integer schJobId) {
        Integer id=schedulerJobDao.getUserBySchJobId(schJobId);
        return id;
    }

    @Override
    public List<SchedulerJob> findByWorkSpaceId(Integer wpid) {
        return schedulerJobDao.findByWorkSpaceId(wpid);
    }

    @Override
    public String findJobNameById(Integer wfid) {
        return schedulerJobDao.findJobNameById(wfid);
    }

    @Override
    public void editSchedulerJobStatus(Map<String, Object> map) {
        schedulerJobDao.editSchedulerJobStatus(map);
    }

}
