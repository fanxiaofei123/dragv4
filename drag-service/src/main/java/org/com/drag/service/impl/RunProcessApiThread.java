package org.com.drag.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowAction;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.SpringContextUtil;
import org.com.drag.model.CalculationHistory;
import org.com.drag.service.CalculationHistoryService;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.service.oozie.bean.WorkflowInfomation;
import org.com.drag.websock.TextMessageHandler;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

/**
 * Created by cdyoue on 2016/11/21.
 */
public class RunProcessApiThread  implements Runnable{

    // 日志对象
    private static Log logger = LogFactory.getLog(RunProcessApiThread.class);
    private ScheduledExecutorService scheduledExecutorService;
    private String jobId;
    @Autowired
    private IOozieApi  iOozieApi;
    
    private Integer usrId;
    private Integer hisId;
    private Integer modelSzie;

    @Override
    public void run() {
    	//查询工作流运行情况
        WorkflowInfomation workflowInfo = iOozieApi.getWorkflowInfo(jobId,modelSzie,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        
        List<WorkflowAction> actions = workflowInfo.getActions();
        //获取spring容器中的CalculationHistoryService Bean
        CalculationHistoryService calculationHistoryMapper = (CalculationHistoryService) SpringContextUtil.getBean("calculationHistoryService");
        
        StringBuffer stringBuffer = new StringBuffer();
        CalculationHistory calculationHistory = new CalculationHistory();
        for (WorkflowAction action : actions) {
           String status = action.getStatus().toString();
            logger.info("aciton status :"+status);

            if(action.getName().equalsIgnoreCase("fail")){
                stringBuffer.append("<p  style=\"color: red;font-size: large;\" >").append(getTime(action.getEndTime())).append(action.getName()).append(":").append("运行失败");

                break;
            }
            if(status.equalsIgnoreCase("OK")){
                logger.info("action 完成.....");
                stringBuffer.append("<p  style=\"color: #1FAD97;font-size: large;\" >").append(getTime(action.getEndTime())).append(action.getName()).append(":").append("运行成功");
            }else if(status.equalsIgnoreCase("RUNNING")){
                logger.info("RUNNING......");
                stringBuffer.append("<p  style=\"color: #F82B2B;font-size: large;\" >").append(getTime(action.getStartTime())).append(action.getName()).append(":").append("开始启动");
            }
        }
        TextMessageHandler textMessageHandler = new TextMessageHandler();
        JSONObject jsonObject = new JSONObject();
        String progressStatus = workflowInfo.getRateOfJob();
        logger.info("process:"+progressStatus);
        jsonObject.put("progressStatus", progressStatus);
        int status = workflowInfo.getStatus();
        try {
            if(status == 2 ){
                     calculationHistory.setId(hisId);
                     logger.info("jobId:"+jobId+"     运行成功");
                     calculationHistory.setStatus(2);
                     calculationHistory.setUserid(usrId);
                    /* calculationHistory.setWay("用户");*/

                String jobLogs = iOozieApi.getJobLogs(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                calculationHistory.setResason(jobLogs);
                calculationHistoryMapper.updateByPrimaryKeySelective(calculationHistory);
                     stringBuffer.append("<p style=\"color: #47A447;font-size: large;\" >工作流运行成功!");
                     scheduledExecutorService.shutdown();
            }else if(status == -1 ){
                    logger.error("jobId:"+jobId+"     运行失败");
                    calculationHistory.setId(hisId);
                    calculationHistory.setStatus(-1);
                    calculationHistory.setUserid(usrId);
                   /* calculationHistory.setWay("用户");*/
                    String jobLogs = iOozieApi.getJobLogs(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                    calculationHistory.setResason(jobLogs);
                    calculationHistoryMapper.updateByPrimaryKeySelective(calculationHistory);
                  //并不是终止线程的运行，而是禁止在这个Executor中添加新的任务 
                    //关闭线程池来结束所有池中的线程
                    scheduledExecutorService.shutdown();
            }
        } catch (OozieClientException e) {
            logger.error("获取日志错误"+e.getMessage());
        }
        jsonObject.put("consoleMsg", stringBuffer.toString());
        TextMessage message = new TextMessage(jsonObject.toJSONString());
        textMessageHandler.sendMessageToUser(usrId,message);

    }
    public String getTime(Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb = new StringBuffer("[");
        sb.append(sdf.format(date)).append("]  ");
        return sb.toString();
    }

    public RunProcessApiThread(ScheduledExecutorService scheduledExecutorService,String jobId,IOozieApi oozieClientService,Integer usrId ,Integer hisId,int modelSzie) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.jobId = jobId;
        this.iOozieApi = oozieClientService;
        this.usrId = usrId;
        this.hisId = hisId;
        this.modelSzie = modelSzie;
    }
}
