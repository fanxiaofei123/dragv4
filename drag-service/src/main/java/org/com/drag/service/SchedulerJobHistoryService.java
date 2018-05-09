package org.com.drag.service;


import org.com.drag.common.page.PageBean;
import org.com.drag.model.SchedulerJobHistory;
import org.com.drag.model.User;

import java.util.List;
import java.util.Map;

public interface SchedulerJobHistoryService extends BaseService<SchedulerJobHistory> {


    Map<String,Object> getSchedulerJobMap(User user);

    Map<String,Object> getSchedulerJobRunMap(User user);

    Map<String,Object> getTaskCountMap(User user);


    List<SchedulerJobHistory> getTaskFailList(User user, SchedulerJobHistory schedulerJobHistory);

    List<SchedulerJobHistory> getTaskTimeLongList(User user, SchedulerJobHistory schedulerJobHistory);

    PageBean selectSchedulerHistoryJobList(Integer page, Integer rowCount, User user);

    Map<String,Object> getTaskStatusCountMap(User user);

    PageBean selectSchHisJobListBySearch(Integer[] array, Integer sort, Integer page, Integer rowCount, String schJobName, String firstTime, String lastTime, Integer id);

    String lookSchedulerHistoryJobLogById(Integer schJobHisId);

    String getSchedulerHisJobDefaultDate(Integer id);

    Integer insertSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory);

    void updateSchedulerJobHistory(SchedulerJobHistory schedulerJobHistory);

    SchedulerJobHistory selectSchedulerHistoryJobById(Integer schJobHisId);

    SchedulerJobHistory getMaxTimeFindSchedulerHistory(int schJobId);

    void deleteSchJobHisById(Integer schJobHisId);
}