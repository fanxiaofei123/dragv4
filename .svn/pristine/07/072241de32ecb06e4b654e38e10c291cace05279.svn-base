package org.com.drag.service;


import org.com.drag.common.page.PageBean;
import org.com.drag.model.SchedulerJob;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;

import java.util.List;
import java.util.Map;

public interface SchedulerJobService extends BaseService<SchedulerJob> {
	List<WorkFlow> selectWorkFlowList(User user, String workFlowName);

    Integer insertSchedulerJob(SchedulerJob schedulerJob);

    SchedulerJob selectSchedulerJob(Integer schJobId);

    Integer editSchedulerJob(SchedulerJob schedulerJob);

    PageBean selectAllSchedulerJob(Integer page, Integer rowCount, String schJobName, String startTime, String endTime, User user);

    Integer deleteSchedulerJob(Integer schJobId);

    Integer batchDeleteSchedulerJob(Integer[] array);

    String getDefaultDate(Integer id);
    
    List<SchedulerJob> selectTodoSchedulerJobs();

    String selectUserNameByJobId(Integer schJobId);

    Integer findFlowIdBySchJobId(Integer schJobId);

    Integer findSchJobIdBySchJobWfId(Integer schJobWfId);

    Integer getUserBySchJobId(Integer schJobId);

    List<SchedulerJob> findByWorkSpaceId(Integer wpid);

    String findJobNameById(Integer wfid);


    void editSchedulerJobStatus(Map<String, Object> map);
}