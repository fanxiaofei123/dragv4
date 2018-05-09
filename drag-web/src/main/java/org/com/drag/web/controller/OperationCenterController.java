package org.com.drag.web.controller;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.SchedulerJob;
import org.com.drag.model.SchedulerJobHistory;
import org.com.drag.model.User;
import org.com.drag.service.SchedulerJobHistoryService;
import org.com.drag.service.SchedulerJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运维中心
 * Created by luojun on 2017/7/17.
 */
@Controller
@RequestMapping("drag/ops")
public class OperationCenterController {

    @Autowired
    private SchedulerJobHistoryService schedulerJobHistoryService;
    /**
     * 失败的情况
     * @return
     */
    @RequestMapping(value = "taskMisCount")
    public String taskMisCount(Model model){
        //给失败的情况的页面的隐藏域标签给个状态为fail
        model.addAttribute("taskStatus","fail");
        return "ops/taskCount";
    }

    /**
     * 已完成情况
     * @return
     */
    @RequestMapping(value = "taskRightCount")
    public String taskRightCount(Model model){
        //给已完成情况的页面的隐藏域标签给个状态为success
        model.addAttribute("taskStatus","success");
        return "ops/taskCount";
    }

    /**
     * 任务历史分页显示
     * @param session
     * @return
     */
    @RequestMapping(value = "taskStatusCount",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult statusCountMap(HttpSession session){
        ResponseResult responseResult=new ResponseResult();

        Map<String,Object> pageMap=new HashMap<>();
        User user = (User) session.getAttribute(Constants.USER_KEY);
        Map<String,Object> statusCountMap=schedulerJobHistoryService.getTaskStatusCountMap(user);
        pageMap.put("statusCount",statusCountMap);
        //运行状态情况
        responseResult.setObj(pageMap);
        return responseResult;
    }
    /**
     * 任务完成情况 今天，昨天，历史平均的24不同时间段完成情况
     * @param session
     * @return
     */
    @RequestMapping(value = "taskFinish",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity taskFinish(HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        //System.out.println(user.getId());
       Map<String,Object> dataMap=schedulerJobHistoryService.getSchedulerJobMap(user);
       return new ResponseEntity(dataMap, HttpStatus.OK);
    }

    /**
     * 任务今天的运行情况
     * @param session
     * @return
     */
    @RequestMapping(value = "taskRun",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity taskRun(HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        Map<String,Object> dataMap=schedulerJobHistoryService.getSchedulerJobRunMap(user);
        return new ResponseEntity(dataMap, HttpStatus.OK);
    }

    /**
     * 每周的任务的数量
     * @param session
     * @return
     */
    @RequestMapping(value = "taskCount",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity taskCount(HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        Map<String,Object> dataMap=schedulerJobHistoryService.getTaskCountMap(user);
        return new ResponseEntity(dataMap, HttpStatus.OK);
    }

    /**
     * 失败任务排序
     * @param schedulerJobHistory
     * @param session
     * @return
     */
    @RequestMapping(value = "failList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity failList(SchedulerJobHistory schedulerJobHistory,HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        List<SchedulerJobHistory> failList=schedulerJobHistoryService.getTaskFailList(user,schedulerJobHistory);
        return new ResponseEntity(failList, HttpStatus.OK);
    }

    /**
     * 时长排序
     * @param schedulerJobHistory
     * @param session
     * @return
     */
    @RequestMapping(value = "timeList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity timeLongList(SchedulerJobHistory schedulerJobHistory,HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        List<SchedulerJobHistory> timeList=schedulerJobHistoryService.getTaskTimeLongList(user,schedulerJobHistory);
        return new ResponseEntity(timeList, HttpStatus.OK);
    }


}
