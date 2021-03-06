package org.com.drag.web.controller;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.SchedulerJob;
import org.com.drag.model.SchedulerJobHistory;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.service.SchedulerJobHistoryService;
import org.com.drag.service.SchedulerJobService;

import org.com.drag.web.quartz.JobContext;
import org.com.drag.web.quartz.JobInitializer;
import org.com.drag.web.quartz.JobSchedulerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sky on 2017/8/15.
 */
@Controller
@RequestMapping("drag/scheduler")
public class SchedulerJobController   {

    @Autowired
    private SchedulerJobService schedulerJobService;
    @Autowired
    private SchedulerJobHistoryService schedulerJobHistoryService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    /**
     * 获取调度默认最大和最小的时间
     * @param session
     * @return
     */


    @RequestMapping("getDefaultDate")
    @ResponseBody
    public ResponseResult getDefaultDate(HttpSession session){
        ResponseResult result;
        User user = (User) session.getAttribute(Constants.USER_KEY);
        String defaultDate=schedulerJobService.getDefaultDate(user.getId());
        if(defaultDate!=null){
            result = new ResponseResult(HttpStatus.OK,defaultDate);
        }else {
            result = new ResponseResult(HttpStatus.NOT_FOUND, "");
        }
        return result;
    }
    /**
     * 分页显示调度
     */

    @RequestMapping(value = "selectSchedulerJobList",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectAllSchedulerJob(Integer page,Integer rowCount,String schJobName,String startTime,String endTime,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        PageBean pageBean=schedulerJobService.selectAllSchedulerJob(page,rowCount,schJobName,startTime,endTime,user);
        return pageBean;
    }

    /**
     * 查询工作流
     * @param workFlowName
     * @param session
     * @return
     */
   @RequestMapping(value = "workFlowNameList",method = RequestMethod.POST)
   @ResponseBody
    public ResponseResult getWorkFlowNameList(String workFlowName,HttpSession session){
       User user = (User) session.getAttribute(Constants.USER_KEY);
       List<WorkFlow> workFlowList=schedulerJobService.selectWorkFlowList(user,workFlowName);
       return new ResponseResult(HttpStatus.OK, "查询成功",workFlowList);
   }

    /**
     * 添加调度任务
     * @param schedulerJob
     * @return
     */
    @RequestMapping(value = "insertScheduler",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult insertSchedulerJob(SchedulerJob schedulerJob,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        Integer oneScheduler=schedulerJobService.insertSchedulerJob(schedulerJob);
        Date date=null;
        if(oneScheduler>0){
            try {
                SchedulerJob schedulerJob1=getSchdulerJob(schedulerJob);
                schedulerJob1.setYesOrNo(false);//不是编辑的，添加的
                JobContext context = new JobContext(schedulerFactoryBean.getScheduler(),schedulerJob1, user.getLoginname());
                date = JobSchedulerUtil.schedule(context);

            }catch (Exception e){
                e.printStackTrace();
            }
            if(date!=null){
                return new ResponseResult(HttpStatus.OK, "添加成功");
            }else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "添加失败");
            }
        }else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "添加失败");
        }
    }
    public static SchedulerJob getSchdulerJob(SchedulerJob schedulerJob) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if(schedulerJob.getCreateTime()!=null ){
                schedulerJob.setSchJobCreateTime(format.parse(schedulerJob.getCreateTime()));
            }
            if (schedulerJob.getStartTime()!=null){
                schedulerJob.setSchJobStartTime(format.parse(schedulerJob.getStartTime()));
            }
            if(schedulerJob.getEndTime()!=null && !schedulerJob.getEndTime().equals("")){
                schedulerJob.setSchJobEndTime(format.parse(schedulerJob.getEndTime()));
            }
            schedulerJob.setSchJobLastModify(new Date());

        } catch (ParseException e) {
            e.printStackTrace();
        }
       return schedulerJob;
    }
    /**
     * 查询单个调度
     * @param schJobId
     * @return
     */
    @RequestMapping(value = "selectSchedulerJob",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult selectSchedulerJob(Integer schJobId){
       SchedulerJob schedulerJob=schedulerJobService.selectSchedulerJob(schJobId);
       if(schedulerJob!=null){
           return new ResponseResult(HttpStatus.OK, "查询成功",schedulerJob);
       }
        return new ResponseResult(HttpStatus.BAD_REQUEST, "查询失败");
    }

    /**
     * 编辑调度
     * @param schedulerJob
     * @return
     */
    @RequestMapping(value = "editSchedulerJob",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult editSchedulerJob(SchedulerJob schedulerJob,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        Integer oneScheduler=schedulerJobService.editSchedulerJob(schedulerJob);
        SchedulerJob schedulerJob1=schedulerJobService.selectSchedulerJob(schedulerJob.getSchJobId());
        Boolean whether=false;
        if(oneScheduler>0 && schedulerJob1!=null){
            try {
                schedulerJob1.setYesOrNo(true);//被编辑的
                JobContext context = new JobContext(schedulerFactoryBean.getScheduler(),schedulerJob1, user.getLoginname());
                whether = JobSchedulerUtil.reschedule(context);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(whether==true){
                return new ResponseResult(HttpStatus.OK, "编辑调度成功");
            }else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "编辑调度失败");
            }
        }else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "编辑调度失败");
        }

    }

    /**
     * 删除调度，存在外键关系，所以首先要删除调度任务的历史，然后在删除调度
     * @param schJobId
     * @return
     */
    @RequestMapping(value = "deleteSchedulerJob",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteSchedulerJob(Integer schJobId,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if(schJobId!=null){
            SchedulerJob schedulerJob=schedulerJobService.selectSchedulerJob(schJobId);
            Integer oneScheduler=schedulerJobService.deleteSchedulerJob(schJobId);
            Boolean whether=false;
            if(oneScheduler>0 && schedulerJob!=null){
                try {
                    JobContext context = new JobContext(schedulerFactoryBean.getScheduler(),schedulerJob, user.getLoginname());
                    whether = JobSchedulerUtil.cancel(context);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(whether==true){
                    return new ResponseResult(HttpStatus.OK, "删除成功");
                }else {
                    return new ResponseResult(HttpStatus.BAD_REQUEST, "删除失败");
                }
            }else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "删除失败");
            }
        }else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "删除失败");
        }
    }

    /**
     * 批量删除
     * @param array
     * @return
     */
    @RequestMapping(value = "batchDeleteSchedulerJob",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult batchDeleteSchedulerJob(@RequestParam(value ="array[]",required = true) Integer[] array,HttpSession session){
        User user=(User) session.getAttribute(Constants.USER_KEY);
        if(array!=null){
            SchedulerJob schedulerJob=null;
            boolean whether=false;
            for (int i = 0; i<array.length; i++){
                schedulerJob=schedulerJobService.selectSchedulerJob(array[i]);
                if( schedulerJob!=null){
                    try {
                        JobContext context = new JobContext(schedulerFactoryBean.getScheduler(),schedulerJob, user.getLoginname());
                        whether = JobSchedulerUtil.cancel(context);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            Integer jobCount=schedulerJobService.batchDeleteSchedulerJob(array);
            if(jobCount>0 && whether==true){
                return new ResponseResult(HttpStatus.OK, "删除成功");
            }else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "删除失败");
            }
        }else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "删除失败");
        }
    }

    /**
     * 判断工作流在调度中的设置存不存在，schJobId可以新建调度 0，否则只有编辑 1
     * @param schJobWfId
     * @return
     */
    @RequestMapping("getSchJobExist")
    @ResponseBody
    public Integer getSchJobExist(Integer schJobWfId){
        Integer schJobId=schedulerJobService.findSchJobIdBySchJobWfId(schJobWfId);
        //schJobId可以新建调度 0，否则只有编辑 1
       return schJobId;
    }
    /**
     * 获取调度默认最大和最小的时间
     * @param session
     * @return
     */
    @RequestMapping("getSchedulerHisJobDefaultDate")
    @ResponseBody
    public ResponseResult getSchedulerHisJobDefaultDate(HttpSession session){
        ResponseResult result;
        User user = (User) session.getAttribute(Constants.USER_KEY);
        String defaultDate=schedulerJobHistoryService.getSchedulerHisJobDefaultDate(user.getId());
        if(defaultDate!=null){
            result = new ResponseResult(HttpStatus.OK,defaultDate);
        }else {
            result = new ResponseResult(HttpStatus.NOT_FOUND, "");
        }
        return result;
    }
    /**
     * 任务历史分页显示
     * @param page
     * @param rowCount
     * @param session
     * @return
     */
    @RequestMapping(value = "selectSchJobHisListByPage",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult selectSchJobHisListByPage(Integer page,Integer rowCount,HttpSession session){
        ResponseResult responseResult=new ResponseResult();

        Map<String,Object> pageMap=new HashMap<>();
        User user = (User) session.getAttribute(Constants.USER_KEY);
        PageBean pageBean=schedulerJobHistoryService.selectSchedulerHistoryJobList(page,rowCount,user);

        Map<String,Object> statusCountMap=schedulerJobHistoryService.getTaskStatusCountMap(user);
        pageMap.put("pageBean",pageBean);
        pageMap.put("statusCount",statusCountMap);
        //运行状态情况
        responseResult.setObj(pageMap);
        return responseResult;
    }

    /**
     * 根据搜索的内容来列表分页显示
     * @param array
     * @param sort
     * @param page
     * @param rowCount
     * @param schJobName
     * @param firstTime
     * @param lastTime
     * @param session
     * @return
     */
    @RequestMapping(value = "selectSchHisJobListBySearch",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectSchHisJobListBySearch(@RequestParam(value ="statusArray[]",required = true) Integer[] array,Integer sort,Integer page,Integer rowCount,String schJobName,String firstTime,String lastTime,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        PageBean pageBean=schedulerJobHistoryService.selectSchHisJobListBySearch(array,sort,page,rowCount,schJobName,firstTime,lastTime,user.getId());
        return pageBean;
    }

    /**
     * 查看任务历史的日志
     * @param schJobHisId
     * @return
     */
    @RequestMapping(value = "lookSchedulerHisJobLog",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult lookSchedulerHisJobLog(Integer schJobHisId){
        if(schJobHisId!=null){
            String SchedulerHisJobLog=schedulerJobHistoryService.lookSchedulerHistoryJobLogById(schJobHisId);
            return new ResponseResult(HttpStatus.OK, "查看成功",SchedulerHisJobLog);
        }else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "查看失败");
        }


    }

    /**
     * 判断调度任务中的算子是否是该拖拽的运行的算子
     * @param flowId
     * @return
     */
    @RequestMapping(value = "checkSchJobRunning",method = RequestMethod.POST)
    @ResponseBody
    public  Boolean checkSchJobRunning(Integer flowId){
        Integer schJobId=schedulerJobService.findSchJobIdBySchJobWfId(flowId);
        //调度任务是否启用生效
        if(schJobId==null){
            return false;
        }else {
            try {
                SchedulerJob schedulerJob=schedulerJobService.selectSchedulerJob(schJobId);
                Boolean enable=schedulerJob.getSchJobEnable();
                return enable;
            }catch ( Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}
