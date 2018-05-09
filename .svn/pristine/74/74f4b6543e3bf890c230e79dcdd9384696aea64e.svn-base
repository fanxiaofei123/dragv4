package org.com.drag.dao;


import org.com.drag.model.SchedulerJobHistory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SchedulerJobHistoryDao extends BaseDao<SchedulerJobHistory> {

    SchedulerJobHistory selectSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory);

    Integer getCountSuccessStatus(Map param);


    Date getMinTimeDate(Map paramHis);


    List<Map<String,Object>> getTodayEachHourCount(Map param);


    List<Map<String,Object>> getWeekEachDayCount(Map param);

    Integer getTodayTaskNum(Map todayMap);

    List<SchedulerJobHistory> getTaskFailList(Map failMap);

    List<SchedulerJobHistory> getTaskTimeLongList(Map timeMap);

    Integer getTaskStatusCount(Map param);

    List<SchedulerJobHistory> selectSchedulerHistoryJobList(Integer id);

    List<SchedulerJobHistory> selectSchHisJobListBySearch(Map<String, Object> mapParam);

    String lookSchedulerHistoryJobLogById(Integer schJobHisId);

    List<SchedulerJobHistory> getSchedulerHisJobDefaultDate(Integer userId);

    Integer insertSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory);

    void updateSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory);

    SchedulerJobHistory selectSchedulerHistoryJobById(Integer schJobHisId);

    SchedulerJobHistory getMaxTimeFindSchedulerHistory(int schJobId);

    void deleteSchJobHisById(Integer schJobHisId);
}