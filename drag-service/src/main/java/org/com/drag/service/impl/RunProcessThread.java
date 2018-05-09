package org.com.drag.service.impl;

import email.EmailSendTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowAction;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.SpringContextUtil;
import org.com.drag.model.CalculationHistory;
import org.com.drag.model.TipConfig;
import org.com.drag.model.TipRecord;
import org.com.drag.model.UserRunningInfo;
import org.com.drag.service.CalculationHistoryService;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.service.oozie.bean.WorkflowInfomation;
import org.com.drag.websock.TextMessageHandler;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cdyoue on 2016/11/21.
 */
public class RunProcessThread  implements Runnable{

    // 日志对象
    private static Log logger = LogFactory.getLog(RunProcessThread.class);
    private ScheduledExecutorService scheduledExecutorService;
    private TipRecordServiceImpl  tipRecordService;
    private String jobId;
   // private OozieClientServiceImpl oozieClientService;
    @Autowired
    private IOozieApi  iOozieApi;
    
    private Integer usrId;
    private Integer hisId;
    private Integer modelSize;
    private Integer flowId;
    private String lastJobId;
    private List<TipConfig> list;
    private ConcurrentHashMap<Integer,UserRunningInfo> mapList ;
    private ReentrantLock queueLock ;

    @Override
    public void run() {
        try {
            WorkflowInfomation workflowInfo = iOozieApi.getWorkflowInfo(jobId,modelSize,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
            List<WorkflowAction> actions = workflowInfo.getActions();
            CalculationHistoryService calculationHistoryMapper = (CalculationHistoryService) SpringContextUtil.getBean("calculationHistoryService");
            StringBuffer stringBuffer = new StringBuffer();
            CalculationHistory calculationHistory = new CalculationHistory();
            for (WorkflowAction action : actions) {
                String status = action.getStatus().toString();
                logger.info("aciton status :"+status);

                if(action.getName().equalsIgnoreCase("fail")){
                    if(action.getEndTime()!=null){
                        stringBuffer.append("<p  style=\"color: red;font-size: large;\" >").append(getTime(action.getEndTime())).append(action.getName()).append(":").append("运行失败");
                    } else {
                        stringBuffer.append("<p  style=\"color: red;font-size: large;\" >").append(getTime(new Date())).append(action.getName()).append(":").append("运行失败");
                    }
                    break;
                }
                if(status.equalsIgnoreCase("OK")){
                    logger.info("action 完成....."+jobId+"flowID"+flowId.toString());
                    stringBuffer.append("<p  style=\"color: #47A447;font-size: large;\" >").append(getTime(action.getEndTime())).append(action.getName()).append(":").append("运行成功");
                }else if(status.equalsIgnoreCase("RUNNING")){
                    logger.info("RUNNING......"+jobId+"flowID"+flowId.toString());
                    stringBuffer.append("<p  style=\"color: #C7254E;font-size: large;\" >").append(getTime(action.getStartTime())).append(action.getName()).append(":").append("开始启动");
                }else if (status.equalsIgnoreCase("ERROR")){
                    logger.info("action ERROR.....");
                    stringBuffer.append("<p  style=\"color: #C7254E;font-size: large;\" >").append(getTime(action.getEndTime())).append(action.getName()).append(":").append("运行出错");}

            }
            TextMessageHandler textMessageHandler = new TextMessageHandler();
            JSONObject jsonObject = new JSONObject();
            String progressStatus = workflowInfo.getRateOfJob();
            logger.info("process:"+progressStatus);
            jsonObject.put("progressStatus", progressStatus);
            int status = workflowInfo.getStatus();
            //提醒设置发送邮件
            for (TipConfig config : list) {
                if(config.getTipConfigEnable() == true){
                    if(config.getTipConfigType() == 1){
                        if(status == 2){
                            logger.info("###############运行成功！请查看！#############");
                            //写完成
                            EmailSendTool sendEmail = new EmailSendTool(MailAuthorSetting.USERHOST, MailAuthorSetting.USERNAME, MailAuthorSetting.PASSWORD, config.getTipConfigReceiver(),
                                    "工作流运行状态", "工作流"+config.getSchJobName()+":运行成功！请查看/t"+config.getTipConfigContent(), "可视化平台", "可视化平台", "可视化平台");
                            try {
                                sendEmail.send();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            TipRecord tipRecord = new TipRecord();
                            tipRecord.setTipConfigId(config.getTipConfigId());
                            tipRecord.setTipRecordStatus((short) 1);
                            tipRecord.setTipRecordSendTime(new Date());
                            tipRecordService.insert(tipRecord);
                        }
                    }
                    if(config.getTipConfigType() == 0){
                        if(status == -1){
                            System.out.println("###############运行失败，你想说什么！#############");
                            //写失败
                            EmailSendTool sendEmail = new EmailSendTool(MailAuthorSetting.USERHOST, MailAuthorSetting.USERNAME, MailAuthorSetting.PASSWORD, config.getTipConfigReceiver(),
                                    "工作流运行状态", "工作流"+config.getSchJobName()+":运行失败！请查看/t"+config.getTipConfigContent(), "可视化平台", "可视化平台", "可视化平台");
                            try {
                                sendEmail.send();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            TipRecord tipRecord = new TipRecord();
                            tipRecord.setTipConfigId(config.getTipConfigId());
                            tipRecord.setTipRecordStatus((short) 0);
                            tipRecord.setTipRecordSendTime(new Date());
                            tipRecordService.insert(tipRecord);
                        }
                    }
                }

            }

            queueLock.lock();
            try {
                if(status == 2 ){
                    if (mapList.containsKey(usrId)){
                        logger.info("任务成功提交的任务数量减1");
                        UserRunningInfo userRunningInfo = mapList.get(usrId);
                        if(userRunningInfo.getNum()>0){
                            Integer num = userRunningInfo.getNum() - 1;
                            userRunningInfo.setNum(num);
                            userRunningInfo.getWorkRunMap().remove(flowId);
                        }

                    }
                    logger.info("###############运行成功！请查看！#############");
                    calculationHistory.setId(hisId);
                    logger.info("jobId:"+jobId+"     运行成功");
                    calculationHistory.setStatus(2);
                    calculationHistory.setUserid(usrId);
                    calculationHistory.setJobId(jobId);
                    /* calculationHistory.setWay("用户");*/

                    String jobLogs = iOozieApi.getJobLogs(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                    calculationHistory.setResason(jobLogs);
                    calculationHistoryMapper.updateByPrimaryKeySelective(calculationHistory);
                    stringBuffer.append("<p style=\"color: #47A447;font-size: large;\" >工作流运行成功!");
                    scheduledExecutorService.shutdown();

                }else if(status == -1 ){
                    if (mapList.containsKey(usrId)){
                        logger.info("任务失败提交的任务数量减1");
                        UserRunningInfo userRunningInfo = mapList.get(usrId);
                        if(userRunningInfo.getNum()>0){
                            Integer num = userRunningInfo.getNum() - 1;
                            userRunningInfo.setNum(num);
                            userRunningInfo.getWorkRunMap().remove(flowId);
                        }

                    }
                    logger.error("jobId:"+jobId+"     运行失败");
                    calculationHistory.setId(hisId);
                    calculationHistory.setStatus(-1);
                    calculationHistory.setUserid(usrId);
                    calculationHistory.setJobId(jobId);
                   /* calculationHistory.setWay("用户");*/
                    String jobLogs = iOozieApi.getJobLogs(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
                    calculationHistory.setResason(jobLogs);
                    calculationHistoryMapper.updateByPrimaryKeySelective(calculationHistory);
                    scheduledExecutorService.shutdown();
                }

            } catch (OozieClientException e) {
                logger.error("获取日志错误"+e.getMessage());
            }
            finally {
                queueLock.unlock();
            }
            jsonObject.put("consoleMsg", stringBuffer.toString());
            jsonObject.put("jobId", jobId);
            jsonObject.put("usrId", usrId);
            TextMessage message = new TextMessage(jsonObject.toJSONString());
//        textMessageHandler.sendMessageToUser(usrId,message);
            textMessageHandler.sendMessageToJobId(jobId,message);
        }catch (Exception e){
            if(e instanceof IllegalStateException) {
                ;
            } else {
                logger.info("其他异常++++++++++++");
                e.printStackTrace();
            }

        }


    }

    public String getTime(Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb = new StringBuffer("[");
        sb.append(sdf.format(date)).append("]  ");
        return sb.toString();
    }

    public RunProcessThread(ScheduledExecutorService scheduledExecutorService,String jobId,IOozieApi oozieClientService,
                            Integer usrId ,Integer hisId,int modelSize,Integer flowId,List<TipConfig> list,ConcurrentHashMap<Integer,UserRunningInfo> mapList, ReentrantLock queueLock) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.jobId = jobId;
        this.iOozieApi = oozieClientService;
        this.usrId = usrId;
        this.hisId = hisId;
        this.modelSize = modelSize;
        this.flowId = flowId;
        this.list = list;
        this.mapList=mapList;
        this.queueLock=queueLock;
    }
}
