package org.com.drag.dao;


import org.com.drag.model.SchedulerJob;
import org.com.drag.model.WorkFlow;

import java.util.List;
import java.util.Map;

public interface SchedulerJobDao extends BaseDao<SchedulerJob> {
    List<SchedulerJob> selectAllSchedulerJob(Map<String, Object> schJobMap);

   List<WorkFlow> selectWorkFlowList(Map<String, Object> param);

    Integer insertSchedulerJob(SchedulerJob schedulerJob);

    SchedulerJob selectSchedulerJob(Integer schJobId);
    
    Integer editSchedulerJob(SchedulerJob schedulerJob);

    Integer deleteSchedulerJob(Integer schJobId);

    Integer batchDeleteSchedulerJob(List<Integer> list);

    List<SchedulerJob> getSchedulerDefaultDate(Integer userId);

	List<SchedulerJob> selectTodoSchedulerJobs();

    String selectUserNameByJobId(Integer schJobId);

    Integer findFlowIdBySchJobId(Integer schJobId);

    Integer findSchJobIdBySchJobWfId(Integer schJobWfId);

    Integer getUserBySchJobId(Integer schJobId);

    /**
     * 查询调度用工作空间id
     * @param wpid
     * @return
     */
    List<SchedulerJob> findByWorkSpaceId(Integer wpid);

    /**
     * 用Jobid查询调度名
     * @param id
     * @return
     */
    String findJobNameById(Integer id);

    void editSchedulerJobStatus(Map<String, Object> map);
}