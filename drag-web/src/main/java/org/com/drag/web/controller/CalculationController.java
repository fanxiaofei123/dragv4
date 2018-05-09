package org.com.drag.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.poi.hssf.usermodel.*;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.*;
import org.com.drag.model.*;
import org.com.drag.service.*;
import org.com.drag.service.impl.JobServiceImpl;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.web.common.ModelUtils;
import org.com.drag.web.common.WorkFlowXmlUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import org.apache.hadoop.fs.FileSystem;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cdyoue on 2016/11/21.
 */
@Controller
@RequestMapping("drag/calculation")
public class CalculationController {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private CalculationHistoryService calculationHistoryService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private JobService jobService;
    @Autowired
    private IHdfsApi iHdfsApi;
    @Autowired
    private IOozieApi  iOozieApi;

    @Autowired
    private DatabaseConnectService databaseConnectService;

    @Autowired
    private DatabaseTypeService databaseTypeService;

    @Autowired
    private ModelTrainedService modelTrainedService;

    @Autowired
    private JobServiceImpl jobServiceImpl;
    @Autowired
    ServletContext servletContext;

    /**
     * 工作流的运行
     * @param session
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "run", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseResult runCalculation(HttpServletRequest request,HttpSession session, String modelStr, String connectStr,String blocksStr,String notesStr, final Integer flowId,String flowName,String workSpaceName) {
        ResponseResult responseResult = new ResponseResult(HttpStatus.OK);
        User user = (User)session.getAttribute(Constants.USER_KEY);
        Integer userId=user.getId();
        //保存工作流
        WorkFlow workFlow = new WorkFlow();
        workFlow.setId(flowId);
        workFlow.setUpdatTime(new Date());
        JSONObject contextJson = new JSONObject();
        contextJson.put("models", modelStr);
        contextJson.put("connects", connectStr);
        contextJson.put("blocks", blocksStr);
        contextJson.put("notes", notesStr);
        workFlow.setContext(contextJson.toString());
        workFlowService.updateByPrimaryKeySelective(workFlow);

        WorkFlow workFlows = workFlowService.selectByPrimaryKey(flowId);
        WorkSpace workSpace = workSpaceService.selectByPrimaryKey(workFlows.getWorkspid());
        JSONArray jsonArr = JSONArray.parseArray(modelStr);
        List<Model> models =JSON.parseArray(jsonArr.toJSONString(),Model.class);
        //生成配置xml文件，方法都在这里面写
        Properties xmlProperties=getPropertiesXML(jsonArr, models, connectStr, user, flowId, workSpaceName, flowName, workSpace,this.servletContext,iHdfsApi,databaseConnectService,databaseTypeService,modelTrainedService);
        if(xmlProperties==null){
            return new ResponseResult(HttpStatus.BAD_REQUEST, "配置信息错误，运行失败!");
        }
        //加锁
        ConcurrentHashMap<Integer,UserRunningInfo> mapList=new  ConcurrentHashMap<Integer,UserRunningInfo>();
        Integer total=0;
        String jobId="";
        ReentrantLock queueLock = jobServiceImpl.getReentrantLock();
        queueLock.lock();
        //判断用户的任务量
        try {
            mapList=jobServiceImpl.getMap();
            Iterator<Integer> keys=mapList.keySet().iterator();

            while (keys.hasNext()) {
                Integer m = mapList.get(keys.next()).getNum();
                total += m;
            }
            if(total<= Integer.valueOf(MailAuthorSetting.SUBMIT_TASK_TOTAL_LIMIT)){
                //判断个人用户的任务量
                Boolean containUserId=mapList.containsKey(user.getId());
                if(containUserId){
                    Integer number=mapList.get(user.getId()).getNum();
                    if(number >=Integer.valueOf(MailAuthorSetting.USER_TASK_NUM_LIMIT)){
                        return new ResponseResult(HttpStatus.BAD_REQUEST, "超过用户的运行个数!");
                    }else {
                        UserRunningInfo userRI=mapList.get(user.getId());
                        HashMap  workRunMap=userRI.getWorkRunMap();

                        if(workRunMap.containsKey(flowId)){
                            return new ResponseResult(HttpStatus.BAD_REQUEST, "重复提交了相同未完成任务!");

                        }else {
                            //运行任务
                            jobId = jobService.run(xmlProperties,user.getId(),flowId,workFlows.getName(),null,models.size(),session,0,null,null);
                            if(jobId!=null){
                                UserRunningInfo userRunningInfo = userRI;
                                Integer num = userRunningInfo.getNum() + 1;
                                userRunningInfo.setNum(num);
                                userRunningInfo.getWorkRunMap().put(flowId,jobId);
                            }else {
                                return new ResponseResult(HttpStatus.BAD_REQUEST, "运行失败!");
                            }

                        }
                    }
                }else {
                    //用户第一次运行的数量初始化
                    //运行任务
                    jobId = jobService.run(xmlProperties,user.getId(),flowId,workFlows.getName(),null,models.size(),session,0,null,null);
                    if(jobId!=null) {
                        UserRunningInfo userRInfo= new UserRunningInfo();
                        userRInfo.setNum(1);
                        HashMap map=new HashMap();
                        map.put(flowId,jobId);
                        userRInfo.setWorkRunMap(map);
                        //userRInfo.getWorkRunMap().put(flowId,"NONE");
                        mapList.put(userId,userRInfo);
                    }else {
                        return new ResponseResult(HttpStatus.BAD_REQUEST, "运行失败!");
                    }

                }
            }else{
                return new ResponseResult(HttpStatus.BAD_REQUEST, "超过最大可同时运行任务量了");
            }
        }catch (Exception e){
            LOGGER.error("加锁判断用户提交的任务量是否满足条件", e);
        }finally {
            queueLock.unlock();
        }



        //运行任务
       // String jobId = jobService.run(xmlProperties,user.getId(),flowId,workFlows.getName(),null,models.size(),session,0,null,null);

        session.setAttribute("jobId",jobId);
        //查询当前工作空间的历史记录
        CalculationHistory ch = new CalculationHistory();
        ch.setUserid(user.getId());
        ch.setFlowId(flowId);

        List<CalculationHistory> results =  calculationHistoryService.selectBySelectiveKey(ch);


        Map map = new HashMap();
        map.put("historys", results);
        map.put("jobId", jobId);
        responseResult.setObj(map);
        return responseResult;
    }
    public Properties getPropertiesXML(JSONArray jsonArr,List<Model> models,String connectStr,User user,Integer flowId,String workSpaceName,String flowName,WorkSpace workSpace,ServletContext servletContext,IHdfsApi iHdfsApi,DatabaseConnectService databaseConnectService,DatabaseTypeService databaseTypeService,ModelTrainedService modelTrainedService){
        Iterator<Object> arrIterator = jsonArr.iterator();
        String filterOptValue = "";
        String logicRules = "";
        String filterBlockId = "";
        while (arrIterator.hasNext()){
            Object object = arrIterator.next();
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject)object;
            if(Integer.parseInt(jsonObject.get("id").toString()) == 125){
                filterBlockId = jsonObject.get("BlockId").toString();
                JSONArray jsonArr1 = (JSONArray)jsonObject.get("data");
                Iterator<Object> iterator = jsonArr1.iterator();
                while (iterator.hasNext()){
                    JSONArray jsonArray = (JSONArray)iterator.next();
                    Iterator<Object> iterator1 = jsonArray.iterator();
                    String optSingle = "";
                    String filterFun = "";
                    while(iterator1.hasNext()){
                        com.alibaba.fastjson.JSONObject dataObject = (com.alibaba.fastjson.JSONObject)iterator1.next();
                        if(dataObject.get("configName").toString().indexOf("filterFun") != -1){
                            filterFun = dataObject.get("configSelectVal").toString();
                            break;
                        }
                    }
                    Iterator<Object> iterator2 = jsonArray.iterator();
                    while (iterator2.hasNext()){
                        com.alibaba.fastjson.JSONObject dataObject = (com.alibaba.fastjson.JSONObject)iterator2.next();
                        if(!"<=>".equals(filterFun) && !"∉".equals(filterFun)){
                            if(dataObject.get("configName").toString().indexOf("labelCol") != -1){
                                optSingle += "labelCol|" + dataObject.get("configVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("filterFun") != -1){
                                optSingle += "filterFun|" + dataObject.get("configSelectVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("filterType") != -1){
                                optSingle += "filterType|" + dataObject.get("configSelectVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("logicRule") != -1){
                                logicRules += dataObject.get("configVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().contains("UpperThresholdVal")){
                                if(dataObject.get("configVal") == null || "".equals(dataObject.get("configVal").toString().trim())){
                                    optSingle += "UpperThresholdVal|" + "null" + ",";
                                }else{
                                    optSingle += "UpperThresholdVal|" + dataObject.get("configVal").toString() + ",";
                                }
                            }
                            if(dataObject.get("configName").toString().contains("LowerThresholdVal")){
                                if(dataObject.get("configVal") == null || "".equals(dataObject.get("configVal").toString().trim())){
                                    optSingle += "LowerThresholdVal|" + "null" + ";";
                                }else{
                                    optSingle += "LowerThresholdVal|" + dataObject.get("configVal").toString() + ";";
                                }
                            }
                            if(dataObject.get("configName").toString().contains("threshold")){
                                if(dataObject.get("configVal") == null || "".equals(dataObject.get("configVal").toString().trim())){
                                    optSingle += "threshold|" + "null" + ",";
                                }else{
                                    optSingle += "threshold|" + dataObject.get("configVal").toString() + ",";
                                }
                            }
                        }else{
                            if(dataObject.get("configName").toString().indexOf("labelCol") != -1){
                                optSingle += "labelCol|" + dataObject.get("configVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("filterFun") != -1){
                                optSingle += "filterFun|" + dataObject.get("configSelectVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("LowerThreshold") != -1 && !dataObject.get("configName").toString().contains("LowerThresholdVal")){
                                optSingle += "LowerThreshold|" + dataObject.get("configSelectVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("UpperThreshold") != -1 && !dataObject.get("configName").toString().contains("UpperThresholdVal")){
                                optSingle += "UpperThreshold|" + dataObject.get("configSelectVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().indexOf("logicRule") != -1){
                                logicRules += dataObject.get("configVal").toString() + ",";
                            }
                            if(dataObject.get("configName").toString().contains("UpperThresholdVal")){
                                if(dataObject.get("configVal") == null || "".equals(dataObject.get("configVal").toString().trim())){
                                    optSingle += "UpperThresholdVal|" + "null" + ",";
                                }else{
                                    optSingle += "UpperThresholdVal|" + dataObject.get("configVal").toString() + ",";
                                }
                            }
                            if(dataObject.get("configName").toString().contains("LowerThresholdVal")){
                                if(dataObject.get("configVal") == null || "".equals(dataObject.get("configVal").toString().trim())){
                                    optSingle += "LowerThresholdVal|" + "null" + ";";
                                }else{
                                    optSingle += "LowerThresholdVal|" + dataObject.get("configVal").toString() + ";";
                                }
                            }
                        }
                    }
                    filterOptValue += optSingle;
                }
                jsonArr.remove(object);
                break;
            }
        }
        if(StringUtils.hasText(filterOptValue)){
            filterOptValue = filterOptValue.substring(0,filterOptValue.length()-1);
        }
        if(StringUtils.hasText(logicRules)){
            logicRules = logicRules.substring(0,logicRules.length()-1);
        }

        if(StringUtils.hasText(filterOptValue) && StringUtils.hasText(logicRules)){
            String filterJsonStr = "{\"BlockName\":\"条件过滤(V4)\",\"BlockId\":\"" + filterBlockId +
                    "\",\"data\":[{\"configName\":\"option\",\"configVal\":\"" + filterOptValue +
                    "\"},{\"configName\":\"logicRule\",\"configVal\":\"" + logicRules + "\"}],\"id\":\"125\"}";
            Object filterObject = JSONArray.parse(filterJsonStr);
            jsonArr.add(filterObject);
        }
        List<Connect> connects = JSON.parseArray(connectStr, Connect.class);
        //循环connects，设置connectType。
        for(Connect connect : connects){
            String sourceId = connect.getSourceId();
            int modelId = 0;
            for(Model model : models){
                if(sourceId.equals(model.getBlockId())){
                    modelId = model.getId();
                }
            }
            if(Setting.getDoubleOutputDataModelIds().contains(modelId + "")){
                if(Double.parseDouble(connect.getSourceAnchor().get(0)) < Double.parseDouble(connect.getTargetAnchor().get(0))){
                    connect.setConnectType("Square");
                }else {
                    connect.setConnectType("Circle");
                }
            }

            if(Setting.getPythonAndRIds().contains(modelId + "")){
                if(Double.parseDouble(connect.getSourceAnchor().get(0)) < Double.parseDouble(connect.getTargetAnchor().get(0))){
                    connect.setConnectType("Square");
                }else {
                    connect.setConnectType("Circle");
                }
            }

            if(Setting.getDoubleOutputDataIds().contains(modelId + "")){
                if(Double.parseDouble(connect.getSourceAnchor().get(0)) == 0.5){
                    connect.setConnectSourcePosition("Left");
                }else{
                    connect.setConnectSourcePosition("Right");
                }
            }
        }

        for(Connect connect : connects){
            String targetId = connect.getTargetId();
            int modelId = 0;
            for(Model model : models){
                if(targetId.equals(model.getBlockId())){
                    modelId = model.getId();
                }
            }
            if(Setting.getOrdinaryApplyV4Ids().contains(modelId + "")){
                if(Double.parseDouble(connect.getTargetAnchor().get(0)) == 0.5){
                    connect.setConnectTargetPosition("Left");
                }else{
                    connect.setConnectTargetPosition("Right");
                }
            }
        }

        //用一个flag记录是否有python或者r算子。
        boolean containsPythonR = false;
        boolean containsSinglePythonR = false;
        for(Model model : models){
            //判断是否是自定义算子Py R
            if(model.getId()==83 || model.getId()==84){
                containsPythonR = true;
                String modelId =model.getBlockId();
                List<ModelAttribute> data1 = model.getData();
                Iterator<ModelAttribute> iterator = data1.iterator();

                for(ModelAttribute modelAttribute:data1){
                    if(modelAttribute.getMattribute().equals("textarea")){
                        String context=modelAttribute.getMvalue();
                        System.out.println(context);
                        String uuid = UUID.randomUUID().toString();
                        //自定义算子路径
                        String customPyPath=savePyFile(flowId,modelId,context,model.getId(),uuid,servletContext);
                        String scriptName;
                        if(model.getId() == 83){
                            scriptName = flowId.toString()+"_"+modelId.substring(10) + "_" + uuid + ".py";
                        }else{
                            scriptName = flowId.toString()+"_"+modelId.substring(10) + "_" + uuid + ".r";
                        }
                        String commonPythonRPath = MailAuthorSetting.PYTHON_R_DIR_PATH + scriptName;
                        String userName = user.getLoginname();
                        iHdfsApi.uploadWorkFlowFile(customPyPath, commonPythonRPath, userName);
                        FileSystem fs = iHdfsApi.getFileSystem("hdfs");
                        try {
                            fs.setPermission(new Path(commonPythonRPath),new FsPermission("777"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ModelAttribute scriptPathAttr = new ModelAttribute();
                        scriptPathAttr.setMattribute("scriptPath");
                        scriptPathAttr.setMvalue(commonPythonRPath);
                        model.getData().add(scriptPathAttr);

                        ModelAttribute scriptNameAttr = new ModelAttribute();
                        scriptNameAttr.setMattribute("scriptName");
                        scriptNameAttr.setMvalue(scriptName);
                        model.getData().add(scriptNameAttr);

                        ModelAttribute shellScriptName = new ModelAttribute();
                        shellScriptName.setMattribute("shellScriptName");
                        shellScriptName.setMvalue(PythonRUtils.getPythonRScriptName(model.getId()) + ".sh");
                        model.getData().add(shellScriptName);
                        break;
                    }
                }
            }else if(Setting.getPythonAndRSingleIds().contains(model.getId() + "")){
                containsSinglePythonR = true;
                ModelAttribute shellScriptName = new ModelAttribute();
                shellScriptName.setMattribute("shellScriptName");
                shellScriptName.setMvalue(PythonRUtils.getPythonRScriptName(model.getId()) + ".sh");
                model.getData().add(shellScriptName);
            }

            List<ModelAttribute> data = model.getData();
            for(ModelAttribute modelAttribute : data){
                if(modelAttribute.getConfigSelectVal() != null){
                    modelAttribute.setMvalue(modelAttribute.getConfigSelectVal());
                }
            }

            //py文本->写文件->路径记录下来->

            if(model.getId() == 55){
                Integer trainedModelId = model.getTrainedModelId();
                String inputPath = modelTrainedService.getInputPathById(trainedModelId);
                ModelAttribute modelAttribute = new ModelAttribute();
                modelAttribute.setMattribute("inputPath");
                modelAttribute.setMvalue(inputPath);
                model.getData().add(modelAttribute);
                String dataModelName=modelTrainedService.getDataModelNameById(trainedModelId);
                ModelAttribute modelAttribute2 = new ModelAttribute();
                modelAttribute2.setMattribute("dataModelName");
                modelAttribute2.setMvalue(dataModelName);
                model.getData().add(modelAttribute2);
            }
            //格式化model
            model=ModelUtils.updateModel(model);

        }
        String userName = user.getLoginname();


//简要检查数据格式
        if(connects.size()==0 || models.size()==0){
            return null;
        }


//生成xml和properties
        ModelUtils modelUtils = new ModelUtils();
        ModelXmlInfo modelXmlInfo;
//        boolean oldVersion = false;
        String version = Constants.OOZIE_GENXML_V4;
        Properties xmlProperties = null;
      //  Properties prop = new Properties();
//        Map<String,String> oozieVersionMap = new HashMap<>();
//        try {
//            InputStream in = new BufferedInputStream (new FileInputStream("oozieVersion.properties"));
//            prop.load(in);
//            Iterator<String> it=prop.stringPropertyNames().iterator();
//            while(it.hasNext()){
//                String key=it.next();
//                oozieVersionMap.put(key,prop.getProperty(key));
//            }
//            for(String key : oozieVersionMap.keySet()){
//                if(key.equals(Constants.OOZIE_GEN_VERSION)){
//                    version = oozieVersionMap.get(key);
//                }
//            }
//            in.close();
//        } catch (Exception e) {
//            LOGGER.info(e.getMessage());
//            e.printStackTrace();
//        }

        if(version.equals(Constants.OOZIE_GENXML_V3)){
            modelXmlInfo = modelUtils.getSortingFormatModels(models, connects,new StringBuffer(workSpaceName).append("/").append(flowName).toString(),userName,user.getId(),databaseConnectService,databaseTypeService);
            String pathPrefix = servletContext.getRealPath("/")+"upload" + System.getProperty("file.separator");

            String tmpoFolderName = new StringBuffer(Constants.WORK_FLOW).append(UUID.randomUUID().toString()).toString();

            String folderPath = new StringBuffer(pathPrefix).append(tmpoFolderName).toString();
            File file = null;
            boolean uploadBooler;
            try {
                file = new File(folderPath);
                boolean mkdir = file.mkdir();
                if(!mkdir){
                    return null;
                }
                String  path = new StringBuffer(folderPath).append("/").append(Constants.WORK_FLOW).append(".xml").toString();
                xmlProperties = WorkFlowXmlUtils.buildXml(modelXmlInfo, path, new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX).append(userName).append("/workspaces/").append(workSpace.getName()).append("/").append(flowName).toString(),flowName, userName);
                WorkFlowXmlUtils.updateXml(path, path);
                //上传xml到hdfs
                // HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
                uploadBooler = iHdfsApi.uploadWorkFlowFile(path, new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append("workflow.xml").toString(), userName);
            } finally {
                if(file != null){
                    removedir(file);
                }
            }

            if(!uploadBooler){
                return null;
            }
        }else if(version.equals(Constants.OOZIE_GENXML_V4)){
            WorkflowNode startWorkflowNode = modelUtils.getStartNode(models, connects,new StringBuffer(workSpaceName).append("/")
                    .append(flowName).toString(),userName,user.getId(),databaseConnectService,databaseTypeService);
            //把中间输出等属性添加上去
            Map<String, WorkflowNode> nodeMap = modelUtils.getNodeMap(new HashMap<String, WorkflowNode>(), startWorkflowNode);

            Map<String, Integer> usedDoubleOutputPathCountMapOutputPath = ModelUtils.getUsedDoubleOutputPathCountMap(nodeMap);
            Map<String,Object> formatAttrMapOutputPath = new HashMap<>();
            List<String> formatedIdsOutputPath = new ArrayList<>();
            formatAttrMapOutputPath.put("usedDoubleOutputPathCountMap",usedDoubleOutputPathCountMapOutputPath);
            formatAttrMapOutputPath.put("formatedIds",formatedIdsOutputPath);
            ModelUtils.formatWorkflowNodeAttributeOutputPath(formatAttrMapOutputPath,nodeMap,startWorkflowNode,userName,user.getId(),flowId.toString(),databaseConnectService,databaseTypeService);

            Map<String, Integer> usedDoubleOutputPathCountMapInputPath = ModelUtils.getUsedDoubleOutputPathCountMap(nodeMap);
            Map<String,Object> formatAttrMapInputPath = new HashMap<>();
            List<String> formatedIdsInputPath = new ArrayList<>();
            formatAttrMapInputPath.put("usedDoubleOutputPathCountMap",usedDoubleOutputPathCountMapInputPath);
            formatAttrMapInputPath.put("formatedIds",formatedIdsInputPath);
            ModelUtils.formatWorkflowNodeAttributeInputPath(formatAttrMapInputPath,nodeMap,startWorkflowNode,userName,user.getId(),flowId.toString(),databaseConnectService,databaseTypeService);


            //给每个算子模型名称后面加上function名称
            ModelUtils.addFuctionToStatModel(startWorkflowNode,new ArrayList<WorkflowNode>());
            String pathPrefix =servletContext.getRealPath("/")+"upload" + System.getProperty("file.separator");
            String tmpoFolderName = new StringBuffer(Constants.WORK_FLOW).append(UUID.randomUUID().toString()).toString();

            String folderPath = new StringBuffer(pathPrefix).append(tmpoFolderName).toString();
            File file = null;
            boolean uploadBooler = false;
            boolean uploadShell = false;
            boolean uploadSingleShell = false;
            try {
                file = new File(folderPath);
                boolean mkdir = file.mkdir();
                if(!mkdir){
                    return null;
                }
                String  path = new StringBuffer(folderPath).append("/").append(Constants.WORK_FLOW).append(".xml").toString();
                xmlProperties = WorkFlowXmlUtils.builNewXml(startWorkflowNode, path, new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX).append(userName).append("/workspaces/").append(workSpace.getName()).append("/").append(flowName).toString().replace(" ",""),flowName, userName);
                WorkFlowXmlUtils.updateXml(path, path);

                //上传xml到hdfs
                // HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
//                String xml = new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append("workflow.xml").toString();
                String workflowPath =  new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append("workflow.xml").toString().replace(" ","");
                uploadBooler = iHdfsApi.uploadWorkFlowFile(path, workflowPath, userName);
                FileSystem fs = iHdfsApi.getFileSystem(userName);
                if(containsPythonR){
                    String shellPath = servletContext.getRealPath("/") + "shell" + System.getProperty("file.separator") + Constants.PYTHON_OR_R_SHELL_NAME;
                    String uploadPath = new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append(Constants.PYTHON_OR_R_SHELL_NAME).toString().replace(" ","");
                    try {
                        iHdfsApi.deleteFileOrDir(uploadPath,userName,false);
                        boolean exists = fs.exists(new Path(uploadPath));
                        if(!exists){
                            uploadShell =  iHdfsApi.uploadFile(shellPath,uploadPath,userName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(containsSinglePythonR){
                    for(Model model : models){
                        if(Setting.getPythonAndRSingleIds().contains(model.getId() + "")){
                            String uploadPath = new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append(PythonRUtils.getPythonRScriptName(model.getId())).append(".sh").toString().replace(" ","");
                            String shellPath = servletContext.getRealPath("/") + "shell" + System.getProperty("file.separator") + PythonRUtils.getPythonRScriptName(model.getId()) + ".sh";
                            try {
                                iHdfsApi.deleteFileOrDir(uploadPath,userName,false);
                                boolean exists = fs.exists(new Path(uploadPath));
                                if(!exists){
                                    uploadSingleShell = iHdfsApi.uploadFile(shellPath,new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append(PythonRUtils.getPythonRScriptName(model.getId())).append(".sh").toString().replace(" ",""),userName);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
          } finally {
                if(file != null){
                    removedir(file);
                }
            }

            if(containsPythonR){
                if(!uploadShell){
                    return null;
                }
            }
            if(!uploadBooler){
                return null;
            }

            if(containsSinglePythonR){
                if(!uploadSingleShell){
                    return null;
                }
            }
        }
        return xmlProperties;
    }

   public String savePyFile(Integer flowId,String BlockId,String context,Integer ModelId,String uuid,ServletContext servletContext){
        //python头部文件地址
       String pythonHead=servletContext.getRealPath("/")+"templateFile"+ System.getProperty("file.separator")+"python_head.py";
       String pythonEnd=servletContext.getRealPath("/")+"templateFile"+ System.getProperty("file.separator")+"python_end.py";
       String rHead=servletContext.getRealPath("/")+"templateFile"+ System.getProperty("file.separator")+"r_head.r";
       String rEnd=servletContext.getRealPath("/")+"templateFile"+ System.getProperty("file.separator")+"r_end.r";

       String pathPrefix = servletContext.getRealPath("/")+"pythonR" + System.getProperty("file.separator");
       String fileName=flowId.toString()+"_"+BlockId.substring(10);
       String customPyPath ="";
       if(ModelId==83){
           //拼接python文件
           context=(readFileToString(pythonHead)+"\n"+context+"\n"+readFileToString(pythonEnd)).replaceAll("\t","    ");
           customPyPath = new StringBuffer(pathPrefix).append(fileName).append("_").append(uuid).append(".py").toString();
       }else {
           //拼接r文件
           context=readFileToString(rHead)+context+readFileToString(rEnd);
           customPyPath = new StringBuffer(pathPrefix).append(fileName).append("_").append(uuid).append(".r").toString();
       }
       try {
           File f=new File(customPyPath);
           if(f.exists()){
               System.out.println("文件存在");
           }else {
               System.out.println("文件不存在");
               f.createNewFile();

           }
           PrintWriter w = new PrintWriter(new OutputStreamWriter(
                   new FileOutputStream(f), "UTF-8"));

           w.write(context);
           System.out.println("input");
           w.close();

       }catch (Exception e){
           e.printStackTrace();
       }

        return customPyPath;
   }
   public String readFileToString(String filePath){
       File f=new File(filePath);
       BufferedReader bfr=null;
       StringBuffer sb=new StringBuffer();
       try {
           bfr = new BufferedReader(new FileReader(f));
           String line = null;
           while ((line = bfr.readLine()) != null) {
               sb.append(line+System.getProperty("line.separator"));
           }
           return sb.toString();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (bfr != null) {
               try {
                   bfr.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return "";
   }

    public  void removedir(File file)
    {
        File[] files=file.listFiles();
        for(File f:files)
        {
            if(f.isDirectory())//递归调用
            {
                removedir(f);
            }
            else {
                f.delete();
            }
        }
        //一层目录下的内容都删除以后，删除掉这个文件夹
        file.delete();
    }

    @RequestMapping(value = "pause",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseResult calculationPause(String jobId,Integer workFlowId){
        // OozieClientServiceImpl oc = new OozieClientServiceImpl(MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        boolean b = iOozieApi.oozieWorkflowSuspend(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        if(b){
            WorkFlow workFlow = new WorkFlow();
            workFlow.setId(workFlowId);
            workFlow.setJobstatus(3);

            workFlowService.updateByPrimaryKeySelective(workFlow);
        }

        return b ? new ResponseResult(HttpStatus.OK):new ResponseResult(HttpStatus.FORBIDDEN);
    }


    @RequestMapping(value = "ifRun",method = RequestMethod.GET)
    @ResponseBody
    public Boolean ifRun(){
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setStatus(1);
        List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveKey(calculationHistory);
        return calculationHistories.size() == 0 ? false : true;
    }

    
    
    /**
     * 分页查询用户的运行记录
     * @return
     */
    @RequestMapping(value = "history",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistory(Integer page ,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveKey(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
				 ws.setUserNmae(user.getLoginname());
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    
    /**
     * 查询后端所有用户的日志记录
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyAll",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryAll(Integer page ,HttpSession session){
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveAll();
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    
    /**
     * 查询前段所有用户日志
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyFAll",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryFAll(Integer page ,HttpSession session){
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveF();
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    /**
     * 查询用户后台日志
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyHAll",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryHAll(Integer page ,HttpSession session){
    	
    	User user = (User) session.getAttribute(Constants.USER_KEY);
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelective(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    
    
    /**
     * 通过时间段查询后台日志记录
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyTime",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryTime(Integer page ,HttpSession session,CalculationHistory calculationHistory){
        
    	if(null != calculationHistory ){
			if(null != calculationHistory.getStartTime()){
				calculationHistory.setStartTime(calculationHistory.getStartTime().toString().trim());
			}
			if(null != calculationHistory.getEndTime()){
				calculationHistory.setEndTime(calculationHistory.getEndTime().toString().trim());
			}
			
		}
    	
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveTime(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal((int)pageInfos.getTotal());
        pageBean.setTotalPage(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }

   /* *
     * 通过时间段查询前台日志记录
     * @param page
     * @param session
     * @return*/

    @RequestMapping(value = "historyTimeF",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryTimeF(Integer page ,HttpSession session,CalculationHistory calculationHistory){
        if(null != calculationHistory ){
            if(null != calculationHistory.getStartTime()){
                calculationHistory.setStartTime(calculationHistory.getStartTime().toString().trim());
            }
            if(null != calculationHistory.getEndTime()){
                calculationHistory.setEndTime(calculationHistory.getEndTime().toString().trim());
            }

        }

        PageBean pageBean = new PageBean();
        if(page == null || page == 0){
            page = 1;
        }
        PageHelper.startPage(page,10); // 核心分页代码  测试
        List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveFTime(calculationHistory);
        //设置返回的总记录数
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
            pageBean.setPrevious(page);
        }else{
            pageBean.setPrevious(page-1);
        }

        if(page < pageInfos.getPages()){
            pageBean.setNext(page+1);
        }else{
            pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
            for(CalculationHistory ws :calculationHistories){
                if(ws.getStatus() == 1){
                    ws.setStatusName("运行中");
                }else if(ws.getStatus() == 2){
                    ws.setStatusName("运行成功");
                }else{
                    ws.setStatusName("运行失败");
                }
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                ws.setCreateTimes(dateformat.format(ws.getCreateTime()));

            }
        }
        pageBean.setRows(calculationHistories);

        return pageBean;
    }

    /**
     * 通过时间段查询用户日志记录
     * @param page
     * @param session
     * @return*/

    @RequestMapping(value = "historyHTime",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryHTime(Integer page ,HttpSession session,CalculationHistory calculationHistory){

        if(null != calculationHistory ){
            if(null != calculationHistory.getStartTime()){
                calculationHistory.setStartTime(calculationHistory.getStartTime().toString().trim());
            }
            if(null != calculationHistory.getEndTime()){
                calculationHistory.setEndTime(calculationHistory.getEndTime().toString().trim());
            }

        }

        User user = (User) session.getAttribute(Constants.USER_KEY);
        calculationHistory.setUserid(user.getId());

        PageBean pageBean = new PageBean();
        if(page == null || page == 0){
            page = 1;
        }
        PageHelper.startPage(page,10); // 核心分页代码  测试
        List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveHTime(calculationHistory);
        //设置返回的总记录数
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
        if(page == 1){
            pageBean.setPrevious(page);
        }else{
            pageBean.setPrevious(page-1);
        }

        if(page < pageInfos.getPages()){
            pageBean.setNext(page+1);
        }else{
            pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
            for(CalculationHistory ws :calculationHistories){
                if(ws.getStatus() == 1){
                    ws.setStatusName("运行中");
                }else if(ws.getStatus() == 2){
                    ws.setStatusName("运行成功");
                }else{
                    ws.setStatusName("运行失败");
                }
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                ws.setCreateTimes(dateformat.format(ws.getCreateTime()));

            }
        }
        pageBean.setRows(calculationHistories);

        return pageBean;
    }
    
    
    /**
     * 批量导出
     * @param request
     * @param resp
     * @param cal
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value="exportExcelAll")
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse resp,CalculationHistory cal) throws IOException
	{
		try
		{
			if (null == request || null == resp)
			{
				return;
			}
			if(null != cal ){
				if(null != cal.getStartTime()){
					if(cal.getStartTime().equals("")){
						cal.setStartTime(null);
					}else{
						cal.setStartTime(cal.getStartTime().toString().trim());	
					}
					
				}
				if(null != cal.getEndTime()){
					if(cal.getEndTime().equals("")){
						cal.setEndTime(null);
					}else{
						cal.setEndTime(cal.getEndTime().toString().trim());
					}
					
				}
				
			}
			List<CalculationHistory> listContent = calculationHistoryService.selectBySelectiveTime(cal);
			//生成Excel文件
			exportExcel(request, resp, listContent);
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
    
    
    /**
     * 导出指定的
     * @param request
     * @param resp
     * @param cal
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value="exportExcel")
	public void exportExcels(HttpServletRequest request, HttpServletResponse resp,CalculationHistory cal) throws IOException
	{
		try
		{
			if (null == request || null == resp)
			{
				return;
			}
			List<CalculationHistory> listContent = calculationHistoryService.selectBySelectiveKey(cal);
			//生成Excel文件
			exportExcel(request, resp, listContent);
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
    
    
    
    /**
	 * excel导出交易记录
	 * @param request
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	public void exportExcel(HttpServletRequest request,HttpServletResponse resp,List<CalculationHistory> listContent) throws UnsupportedEncodingException
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		request.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/x-download");

		String fileName = "日志记录.xls";
		fileName = URLEncoder.encode(fileName, "UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		HSSFSheet sheet = wb.createSheet("日志记录");
		sheet.setDefaultRowHeight((short) (2 * 256));
		sheet.setColumnWidth(0, 50 * 160);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		HSSFRow row = sheet.createRow((int) 0);
		sheet.createRow((int) 1);
		sheet.createRow((int) 2);
		sheet.createRow((int) 3);
		sheet.createRow((int) 4);
		sheet.createRow((int) 5);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("执行者 ");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("工作流名称 ");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("结果状态");
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("时间");
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("原因");
		cell = row.createCell(5);

		List<CalculationHistory> vUserOrder = listContent;

		for (int i = 0; i < vUserOrder.size(); i++)
		{
			HSSFRow row1 = sheet.createRow((int) i + 1);
			CalculationHistory vuserOrder = vUserOrder.get(i);
			/*row1.createCell(0).setCellValue(i + 1);*/
			
			if(vuserOrder.getStatus() == 1){
				vuserOrder.setStatusName("运行中");
			}else if(vuserOrder.getStatus() == 2){
				vuserOrder.setStatusName("运行成功");
			}else{
				vuserOrder.setStatusName("运行失败");
			}
			 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 vuserOrder.setCreateTimes(dateformat.format(vuserOrder.getCreateTime()));
			
			row1.createCell(0).setCellValue(vuserOrder.getEmail());//执行者
			row1.createCell(1).setCellValue(vuserOrder.getWay());//工作流名称
			row1.createCell(2).setCellValue(vuserOrder.getStatusName());//结果
			row1.createCell(3).setCellValue(vuserOrder.getCreateTimes());//时间
			row1.createCell(4).setCellValue(vuserOrder.getResason());//原因
		}
		try
		{
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
	
}
