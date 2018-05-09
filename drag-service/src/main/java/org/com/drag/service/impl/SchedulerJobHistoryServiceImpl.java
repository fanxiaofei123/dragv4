package org.com.drag.service.impl;


import org.apache.commons.collections.map.HashedMap;
import org.com.drag.common.page.PageBean;
import org.com.drag.dao.BaseDao;
import org.com.drag.dao.SchedulerJobHistoryDao;

import org.com.drag.model.SchedulerJobHistory;

import org.com.drag.model.User;
import org.com.drag.service.SchedulerJobHistoryService;
import org.com.drag.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchedulerJobHistoryServiceImpl extends BaseServiceImpl<SchedulerJobHistory> implements SchedulerJobHistoryService {
    //0:失败1:成功2:运行中3:待运行
    private static final Integer TASK_FAIL=0;
    private static final Integer TASK_SUCCESSFUL=1;
    private static final Integer TASK_RUNNING=2;
    private static final Integer TASK_NOT=3;

    //排序 desc 0,asc 1;
    private static final Integer DESC=0;
    private static final Integer ASC=1;

    private static final String SORT_DESC="DESC";
    private static final String SORT_ASC="ASC";
    //page
    private static final Integer PAGE_NUMBER=10;


    private static final Logger logger = LoggerFactory.getLogger(SchedulerJobHistoryServiceImpl.class);

    @Autowired
    private SchedulerJobHistoryDao schedulerJobHistoryDao;
    @Autowired
    private UserService userService;


    public BaseDao<SchedulerJobHistory> getBaseDao() {
        return schedulerJobHistoryDao;
    }


    @Override
    public Map<String, Object> getSchedulerJobMap(User user) {
        Map result=new HashedMap();
        Integer id=null;
        if(user!=null){
            id=user.getId();
        }
        try {
            //今天的显示情况
            List<Long> todayData=getEachData(0,id,new Date(),new Date(),TASK_SUCCESSFUL);
            result.put("todayData",todayData);
            //昨天的显示情况
            List<Long> yesterdayData=getEachData(-24,id,new Date(),new Date(),TASK_SUCCESSFUL);
            result.put("yesterdayData",yesterdayData);
            Date minDate=getFirstDate(id);
//            //历史平均
            Integer totalDay=Math.abs(daysOfTwo(minDate));

            List<String> avgData=new ArrayList<>();

            String hisData="";
            if(totalDay==0||todayData==null){
                for(int i=0;i<24;i++){
                    avgData.add("0");
                }
            }else {
                List<Long> historyData=getEachData(0,id,minDate,new Date(new Date().getTime() - 24*3600*1000),TASK_SUCCESSFUL);
                float count=0;
                for(int i=0;i<24;i++){
                    if(totalDay==0){
                        count= (((float)historyData.get(i))*100)/(100);
                    }else {
                        count= (((float)historyData.get(i))*100)/(totalDay*100);
                    }
                    hisData=new DecimalFormat("###,###,###.##").format(count);
                    avgData.add(hisData);
                }

            }
            result.put("avgData",avgData);
        }catch (Exception e){
            e.printStackTrace();
            result.put("Exception",e.getMessage());
        }
        return result;
    }

    /**
     * 任务今天的运行情况
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> getSchedulerJobRunMap(User user) {
        Map result=new HashedMap();
        Integer id=null;
        if(user!=null){
            id=user.getId();
        }
        try {
            List<Long> runData=getEachData(0,id,new Date(),new Date(),TASK_RUNNING);
            result.put("runData",runData);
        }catch (Exception e){
            e.printStackTrace();
            result.put("Exception",e.getMessage());
        }
        return result;
    }

    /**
     * 任务情况的数量
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> getTaskCountMap(User user) {
        Map result=new HashedMap();
        Integer id=null;
        if(user!=null){
            id=user.getId();
        }
        try {
            //获取一周的日期数组
            List<String> weekData=getOneWeekNameList();
            //一周日期数据数组
            List<Object> resultData=geDataCount(id);
            //同比今天yoy
            Integer todayNum=getTaskNum(id,0,new Date(),new Date());
            Integer yesterNum=getTaskNum(id,-24,new Date(),new Date());
            if(yesterNum==0){
                yesterNum+=1;
            }
            if(todayNum==0){
                todayNum+=1;
            }
            String yoyToday=new DecimalFormat("###,###,###.##").format(((float)((todayNum-yesterNum))/(float)(yesterNum)));
            //同比上周
            Integer thisWeekNum=getTaskNum(id,-144,0);
            Integer earlyWeekNum=getTaskNum(id,-312,-168);
            if(earlyWeekNum==0){
                earlyWeekNum+=1;
            }
            if(thisWeekNum==0){
                thisWeekNum+=1;
            }
            String yoyWeek=new DecimalFormat("###,###,###.##").format(((float)((thisWeekNum-earlyWeekNum))/(float)(earlyWeekNum)));
            //同比上个月
            Integer thisMonthNum=getTaskNum(id,-696,0);
            Integer earlyMonthNum=getTaskNum(id,-1416,-696);
            if(earlyMonthNum==0){
                earlyMonthNum+=1;
            }
            if(thisMonthNum==0){
                thisMonthNum+=1;
            }
            String yoyMonth=new DecimalFormat("###,###,###.##").format(((float)((thisMonthNum-earlyMonthNum))/(float)(earlyMonthNum)));

            result.put("weekData",weekData);
            result.put("resultData",resultData);
            result.put("yoyToday",yoyToday);
            result.put("yoyWeek",yoyWeek);
            result.put("yoyMonth",yoyMonth);
            //当前任务总数
            result.put("todayNum",todayNum);
        }catch (Exception e){
            e.printStackTrace();
            result.put("Exception",e.getMessage());
        }
        return  result;
    }

    /**
     * 出错排行
     * @param user
     * @return
     */
    @Override
    public List<SchedulerJobHistory> getTaskFailList(User user,SchedulerJobHistory schedulerJobHistory) {
        Map failMap=new HashedMap();
        Integer id=null;
        if(user!=null){
            id=user.getId();
        }
        //排序的参数
        String startTime=schedulerJobHistory.getSchJobHisStartTime();
        String endTime=schedulerJobHistory.getSchJobHisEndTime();
        String createTime=schedulerJobHistory.getCreateTime();
        String firstTime=schedulerJobHistory.getFirstTime();
        String lastTime=schedulerJobHistory.getLastTime();
        Integer num=schedulerJobHistory.getNum();
        //传参desc asc给order
        Integer order=schedulerJobHistory.getOrder();
        String squence="desc";
        if(order==DESC){
            squence="desc";
        }else if(order==ASC) {
            squence="asc";
        }

        failMap.put("id",id);
        failMap.put("status",TASK_FAIL);
        failMap.put("startTime",startTime);
        failMap.put("endTime",endTime);
        failMap.put("createTime",createTime);
        failMap.put("firstTime",firstTime);
        failMap.put("lastTime",lastTime);
        failMap.put("num",num);
        failMap.put("order",squence);

        List<SchedulerJobHistory> schedulerJobHistoryList=schedulerJobHistoryDao.getTaskFailList(failMap);
        List<SchedulerJobHistory> list=new ArrayList<>();
        for (SchedulerJobHistory s:schedulerJobHistoryList) {
            SchedulerJobHistory schedulerJobHistory1= new SchedulerJobHistory();
            //截取日期字符串
            schedulerJobHistory1.setCreateTime( s.getCreateTime().substring(0,19));
            schedulerJobHistory1.setFirstTime(s.getFirstTime().substring(0,19));
            schedulerJobHistory1.setLastTime( s.getLastTime().substring(0,19));
            schedulerJobHistory1.setLoginName(user.getLoginname());
            schedulerJobHistory1.setNum(s.getNum());
            schedulerJobHistory1.setSjName(s.getSjName());
            list.add(schedulerJobHistory1);
        }
        return list;
    }

    /**
     * 任务时长排行
     * @param user
     * @param schedulerJobHistory
     * @return
     */
    @Override
    public List<SchedulerJobHistory> getTaskTimeLongList(User user, SchedulerJobHistory schedulerJobHistory) {
        Map timeMap=new HashedMap();
        Integer id=null;
        if(user!=null){
            id=user.getId();
        }
        //排序的参数
        String startTime=schedulerJobHistory.getSchJobHisStartTime();
        String endTime=schedulerJobHistory.getSchJobHisEndTime();
        String createTime=schedulerJobHistory.getCreateTime();
        String firstTime=schedulerJobHistory.getFirstTime();
        String lastTime=schedulerJobHistory.getLastTime();
        Integer schJobHisRunTime=schedulerJobHistory.getSchJobHisRunTime();
        //传参desc asc给order
        Integer order=schedulerJobHistory.getOrder();
        String squence="desc";
        if(order==DESC){
            squence="desc";
        }else if(order==ASC) {
            squence="asc";
        }

        timeMap.put("id",id);
        timeMap.put("startTime",startTime);
        timeMap.put("endTime",endTime);
        timeMap.put("createTime",createTime);
        timeMap.put("firstTime",firstTime);
        timeMap.put("lastTime",lastTime);
        timeMap.put("schJobHisRunTime",schJobHisRunTime);
        timeMap.put("order",squence);

        List<SchedulerJobHistory> schedulerJobHistoryList=schedulerJobHistoryDao.getTaskTimeLongList(timeMap);
        List<SchedulerJobHistory> list=new ArrayList<>();
        for (SchedulerJobHistory s:schedulerJobHistoryList) {
            SchedulerJobHistory schedulerJobHistory1= new SchedulerJobHistory();
            //截取日期字符串
            schedulerJobHistory1.setCreateTime( s.getCreateTime().substring(0,19));
            schedulerJobHistory1.setFirstTime(s.getFirstTime().substring(0,19));
            if(s.getLastTime()!=null){
                schedulerJobHistory1.setLastTime( s.getLastTime().substring(0,19));
            }
            schedulerJobHistory1.setLoginName(user.getLoginname());
            schedulerJobHistory1.setSchJobHisRunTime(s.getSchJobHisRunTime());
            schedulerJobHistory1.setSjName(s.getSjName());
            schedulerJobHistory1.setSchJobHisStatus(s.getSchJobHisStatus());
            list.add(schedulerJobHistory1);
        }
        return list;
    }



    /**
     * 不同时间段返回任务数量
     * @param id
     * @param hours
     * @param date1
     * @param date2
     * @return
     */
    public Integer getTaskNum(int id, int hours, Date date1, Date date2){
        Map timeMap=getStartEndTimes(hours,date1,date2);
        timeMap.put("id",id);
        Integer num=schedulerJobHistoryDao.getTodayTaskNum(timeMap);
        return num;
    }

    /**
     * 不同时间段返回任务数量
     * @param id
     * @param firstHour
     * @param lastHour
     * @return
     */
    public Integer getTaskNum(int id,int firstHour,int lastHour){
        Map timeMap=getWeekStartEndTimes(firstHour,lastHour);
        timeMap.put("id",id);
        Integer num=schedulerJobHistoryDao.getTodayTaskNum(timeMap);
        return num;
    }

    /**
     * 获取一周的任务情况数量日期数组
     * @param id
     * @return
     */
    public List<Object> geDataCount(int id){
        Map param=getWeekStartEndTimes(-144,24);
        param.put("id",id);
        List<Map<String,Object>> dateList =schedulerJobHistoryDao.getWeekEachDayCount(param);
        Map<String,Object> dateMap=new HashedMap();
        for (Map map:dateList) {
            dateMap.put((String) map.get("times"), map.get("cut"));
        }
        List<Object> resultData=new ArrayList<>();

        List<String> weekList=getOneWeekNameList();
        for (int i=0;i<weekList.size();i++){
            if(dateMap.get(weekList.get(i))!=null){
                resultData.add( dateMap.get(weekList.get(i)));
            }else {
                resultData.add("0");
            }
        }
        return resultData;
    }
    //返回一周的开始时间，和结束时间
    public Map<String,Object> getWeekStartEndTimes(int firstHour,int lastHour){
        String startTime;
        String endTime="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar1.set(Calendar.HOUR_OF_DAY, firstHour);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        Date zero = calendar1.getTime();
        startTime=format.format(zero);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.set(Calendar.HOUR_OF_DAY, lastHour);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        Date end = calendar2.getTime();
        endTime=format.format(end);


        HashMap map=new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return  map;
    }
    //返回一周的日期时间2017-01-01格式数组
    public List<String> getOneWeekNameList(){
        List<String> weekList=new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int hours=0;
        String oneDay="";
        for(int i=6;i>=0;i--){
            hours=i*24;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY,-hours);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date time = calendar.getTime();
            oneDay=format.format(time).substring(0,11).trim();
            weekList.add(oneDay);
        }
        return  weekList;
    }

    //返回一天的开始时间，和结束时间的数组
    public List<Long> getEachData(int hours,Integer id,Date date1,Date date2,Integer state){
        Map param=getStartEndTimes(hours,date1,date2);
        param.put("id",id);
        param.put("status",state);
        List<Map<String,Object>> dateList =schedulerJobHistoryDao.getTodayEachHourCount(param);
        Map<String,Object> dateMap=new HashedMap();
        for (Map map:dateList) {
            dateMap.put((String) map.get("times"), map.get("cut"));
        }
        List<Long> resultData=new ArrayList<>();

        for(int i = 0; i<24; i++){
            String timeNum=i+"";
            if(i<10){
                timeNum="0"+i;
            }
            if(dateMap.get(timeNum)!=null){
                resultData.add((Long) dateMap.get(timeNum));
            }else {
                resultData.add(0l);
            }
        }
        return resultData;
    }

    /**
     * 返回一天的开始时间，和结束时间
     * @param hours
     * @param date1
     * @param date2
     * @return
     */
    public Map<String,Object> getStartEndTimes(int hours,Date date1,Date date2){
        String startTime;
        String endTime="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, hours);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        Date zero = calendar1.getTime();
        startTime=format.format(zero);


        hours+=24;

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, hours);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        Date end = calendar2.getTime();
        endTime=format.format(end);


        HashMap map=new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return  map;
    }

    //返回最早日期
    public  Date getFirstDate(Integer id){
        Map paramHis=new HashedMap();
        paramHis.put("id",id);
        paramHis.put("status",1);
        Date minTimeDate=schedulerJobHistoryDao.getMinTimeDate(paramHis);
        return minTimeDate;

    }

    /**
     * 最早的一天和最晚的一天的天数之差
     * @param minTimeDate
     * @return
     */
    public  int daysOfTwo(Date minTimeDate) {
        Calendar aCalendar = Calendar.getInstance();
       //最早的一天
        if(minTimeDate!=null){
            aCalendar.setTime(minTimeDate);

            int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
            //昨天
            aCalendar.setTime(new Date(new Date().getTime() - 24*3600*1000));

            int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

            return day2 - day1;
        }else {
            return 0;
        }

    }

    /**
     *  调度的任务历史分页显示
     * @param page
     * @param rowCount
     * @param user
     * @return
     */
    @Override
    public PageBean selectSchedulerHistoryJobList(Integer page, Integer rowCount, User user) {
        if (page==null|| page==0){
            page=1;
        }
        if (rowCount==null){
            rowCount=PAGE_NUMBER;
        }
        List<SchedulerJobHistory> schedulerHistoryJobList=schedulerJobHistoryDao.selectSchedulerHistoryJobList(user.getId());
        if(schedulerHistoryJobList!=null && schedulerHistoryJobList.size()>0){
            for(SchedulerJobHistory sj:schedulerHistoryJobList){
                if(sj.getSchJobHisStartTime()!=null){
                    sj.setFirstTime(sj.getSchJobHisStartTime().substring(0,19));
                }
                if(sj.getSchJobHisEndTime()!=null){
                    sj.setLastTime(sj.getSchJobHisEndTime().substring(0,19));
                }
                if(sj.getCreateTime()!=null){
                    sj.setCreateTime(sj.getCreateTime().substring(0,19));
                }
            }
        }
        List sjList=new ArrayList<>();
        int startItem=(page-1)*rowCount;
        int endItem=page*rowCount-1;
        int totalPage=0;
        if(rowCount>schedulerHistoryJobList.size()){
            totalPage=1;
        }else {
            totalPage=schedulerHistoryJobList.size()%rowCount==0?schedulerHistoryJobList.size()/rowCount:schedulerHistoryJobList.size()/rowCount+1;

        }
        if(endItem>schedulerHistoryJobList.size()-1){
            endItem=schedulerHistoryJobList.size()-1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(schedulerHistoryJobList.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);
        System.out.println(schedulerHistoryJobList);
        for (int i=startItem;i<=endItem;i++){
            sjList.add(schedulerHistoryJobList.get(i));
        }
        pageBean.setRows(sjList);
        return pageBean;
    }

    /**
     * 查询出所有任务的运行状态
     * @param user
     * @return
     */
    @Override
    public Map<String, Object> getTaskStatusCountMap(User user) {
        Map param=new HashMap();
        Map<String, Object> statusCount = new HashedMap();
        param.put("id",user.getId());
        for(int i=0;i<=3;i++){
            param.put("state",i);
            try {
                Integer count=schedulerJobHistoryDao.getTaskStatusCount(param);
                //state0 失败，state1 成功，state2 运行中 state3 待运行
                statusCount.put("state"+i,count);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return statusCount;
    }

    /**
     * 根据以下参数来查询分页显示
     * @param array
     * @param sort
     * @param page
     * @param rowCount
     * @param schJobName
     * @param firstTime
     * @param lastTime
     * @param id
     * @return
     */
    @Override
    public PageBean selectSchHisJobListBySearch(Integer[] array, Integer sort, Integer page, Integer rowCount, String schJobName, String firstTime, String lastTime, Integer id) {
        Map<String,Object> mapParam=getSearchMap(array,sort,schJobName,firstTime,lastTime,id);

        if (page==null|| page==0){
            page=1;
        }
        if (rowCount==null){
            rowCount=PAGE_NUMBER;
        }
        List<SchedulerJobHistory> schedulerHistoryJobList=schedulerJobHistoryDao.selectSchHisJobListBySearch(mapParam);
        if(schedulerHistoryJobList!=null && schedulerHistoryJobList.size()>0){
            for(SchedulerJobHistory sj:schedulerHistoryJobList){
                if(sj.getSchJobHisStartTime()!=null){
                    sj.setFirstTime(sj.getSchJobHisStartTime().substring(0,19));
                }
                if(sj.getSchJobHisEndTime()!=null){
                    sj.setLastTime(sj.getSchJobHisEndTime().substring(0,19));
                }
                if(sj.getCreateTime()!=null){
                    sj.setCreateTime(sj.getCreateTime().substring(0,19));
                }
            }
        }
        List sjList=new ArrayList<>();
        int startItem=(page-1)*rowCount;
        int endItem=page*rowCount-1;
        int totalPage=0;
        if(rowCount>schedulerHistoryJobList.size()){
            totalPage=1;
        }else {
            totalPage=schedulerHistoryJobList.size()%rowCount==0?schedulerHistoryJobList.size()/rowCount:schedulerHistoryJobList.size()/rowCount+1;

        }
        if(endItem>schedulerHistoryJobList.size()-1){
            endItem=schedulerHistoryJobList.size()-1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(schedulerHistoryJobList.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);
        System.out.println(schedulerHistoryJobList);
        for (int i=startItem;i<=endItem;i++){
            sjList.add(schedulerHistoryJobList.get(i));
        }
        pageBean.setRows(sjList);
        return pageBean;
    }



    /**
     * 封装好参数去数据库查询
     * @param array
     * @param sort
     * @param schJobName
     * @param firstTime
     * @param lastTime
     * @param id
     * @return
     */
    public Map<String,Object> getSearchMap(Integer[] array, Integer sort, String schJobName, String firstTime, String lastTime, Integer id){
        Map<String,Object> mapParam=new HashedMap();
        try {
            if(array!=null){
                for (int i=0;i<array.length;i++){
                    mapParam.put("state"+i,array[i]);
                }
            }
            mapParam.put("schJobName",schJobName);
            if(firstTime!=null){
                mapParam.put("firstTime",firstTime.trim()+" 0:00:00");
            }
            if(lastTime!=null){
                mapParam.put("lastTime",lastTime.trim()+" 23:59:59");
            }

            if(sort==0){
                mapParam.put("sort",SORT_DESC);
            }else {
                mapParam.put("sort",SORT_ASC);
            }
            mapParam.put("userId",id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapParam;
    }

    /**
     * 查看任务历史日志
     * @param schJobHisId
     * @return
     */
    @Override
    public String lookSchedulerHistoryJobLogById(Integer schJobHisId) {
        return schedulerJobHistoryDao.lookSchedulerHistoryJobLogById(schJobHisId);
    }

    /**
     * 获取任务历史的开始的最早时间和最晚的时间
     * @param userId
     * @return
     */
    @Override
    public String getSchedulerHisJobDefaultDate(Integer userId) {
        List<SchedulerJobHistory> schedulerJobList=schedulerJobHistoryDao.getSchedulerHisJobDefaultDate(userId);
        String defaultDate=null;
        if(schedulerJobList.size()>0 && schedulerJobList!=null){
            String minDate=schedulerJobList.get(0).getSchJobHisEndTime().substring(0,10);
            String maxDate=schedulerJobList.get(schedulerJobList.size()-1).getSchJobHisEndTime().substring(0,10);
            defaultDate=minDate+" 至 "+maxDate;
        }
        return defaultDate;
    }

    @Override
    public Integer insertSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory) {

        return schedulerJobHistoryDao.insertSchedulerJobHistory(schedulerJobHistory);
    }

    @Override
    public void updateSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory) {
        schedulerJobHistoryDao.updateSchedulerJobHistory(schedulerJobHistory);
    }

    @Override
    public SchedulerJobHistory selectSchedulerHistoryJobById(Integer schJobHisId) {
        return schedulerJobHistoryDao.selectSchedulerHistoryJobById(schJobHisId);
    }

    @Override
    public SchedulerJobHistory getMaxTimeFindSchedulerHistory(int schJobId) {
        return schedulerJobHistoryDao.getMaxTimeFindSchedulerHistory(schJobId);
    }

    @Override
    public void deleteSchJobHisById(Integer schJobHisId) {
       schedulerJobHistoryDao.deleteSchJobHisById(schJobHisId);
    }
}