package org.com.drag.web.controller;

import com.alibaba.fastjson.JSON;
import com.youe.yc.valuebeans.InvokeResult;
import com.youe.yc.valuebeans.RequestBean;
import com.youedata.service.Predict;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.StringUtils;
import org.com.drag.model.*;
import org.com.drag.service.CalculationHistoryService;
import org.com.drag.service.ServiceModelService;
import org.com.drag.service.UserService;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.oozie.api.IOozieApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Guzhendong on 2017/10/19 0019.
 */
@Controller
@RequestMapping("/service")
public class ServiceModelController {
    /**
     * 每页显示数据条数
     */
    private static final Integer PAGE_NUMBER = 6;
    private static final String BLOCK_MODEL_ID = "76";
    /**选择列*/
    private static final Integer LABEL_COLUMN = 8;
    /**选择标签列*/
    private static final Integer SELECT_THE_COLUMN = 11;
    @Autowired
    private ServiceModelService serviceModelService;
    @Autowired
    private WorkFlowService workFolwService;
    @Autowired
    private CalculationHistoryService calculationHistoryService;
    @Autowired
    IOozieApi iOozieApi;
    @Autowired
    private UserService userService;
    /**
     * 模型服务列表
     * @param page
     * @param rowCount
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("selectAllByAnyThing")
    public PageBean selectAllSerAnyThing(Integer page, Integer rowCount, ServiceModel serviceModel,
                                         HttpSession session) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (rowCount == null) {
            rowCount = PAGE_NUMBER;
        }

        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<ServiceModel> list = new ArrayList<>();
        serviceModel.setUserId(user.getId());
        list =   serviceModelService.selectAllSerAnyThing(serviceModel);
        for (ServiceModel serviceModel1 : list) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if(null != serviceModel1.getServiceUpdateTime()){
                serviceModel1.setServiceUpdateTimes(dateFormat.format(serviceModel1.getServiceUpdateTime()));
            }else {
                serviceModel1.setServiceUpdateTimes("--     --");
            }
            serviceModel1.setServiceCreateTimes(dateFormat.format(serviceModel1.getServiceCreateTime()));
        }


        List fileList = new ArrayList<>();
        int startItem = (page - 1) * rowCount;
        int endItem = page * rowCount - 1;
        int totalPage = 0;
        if (rowCount > list.size()) {
            totalPage = 1;
        } else {
            totalPage = list.size() % rowCount == 0 ? list.size() / rowCount : list.size() / rowCount + 1;
        }
        if (endItem > list.size() - 1) {
            endItem = list.size() - 1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(list.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);

        for (int i = startItem; i <= endItem; i++) {
            fileList.add(list.get(i));
        }
        pageBean.setRows(fileList);
        return pageBean;
    }

    @RequestMapping("selectFlowNameByFlowId")
    @ResponseBody
    public String selectFlowNameByFlowId(Integer flowId){
      WorkFlow workFlow = workFolwService.selectByPrimaryKey(flowId);
        return workFlow.getName();
    }



    /**
     * web模型删除
     * @param serviceModelIds 传入的id
     * @return
     */
    @ResponseBody
    @RequestMapping("delServiceModel")
    public ResponseResult delServiceModel(String serviceModelIds){
        String[] serviceModelId = serviceModelIds.split(",");
        Integer integer = serviceModelService.deleteById(serviceModelId);
        if (0 != integer) {
            return new ResponseResult(HttpStatus.OK, "删除成功");
        } else {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除失败");
        }
    }


    /**
     * 查询模型是否部署
     * @param flowId
     * @return
     */
    @RequestMapping("selectFlowIdByStatus")
    @ResponseBody
    public ResponseResult selectFlowIdByStatus(Integer flowId){
        Integer integer = serviceModelService.selectFlowIdByStatus(flowId);
        if(integer != null){
            return new ResponseResult(HttpStatus.OK, "已经部署");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "未部署");
    }

    /**
     * 查询工作流最后一次运行状态
     * @param flowId
     * @return
     */
    @RequestMapping("selectFlowIdByRunStatus")
    @ResponseBody
    public ResponseResult selectFlowIdByRunStatus(Integer flowId){
        Integer integer = serviceModelService.selectFlowIdByRunStatus(flowId);
        if(null == integer){
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "工作流未成功运行");
        }
        else if(integer == 2){
            return new ResponseResult(HttpStatus.OK, "工作流成功运行");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "工作流未成功运行");
    }

    /**
     * 部署模型
     * @return
     */
    @RequestMapping("insertServiceModel")
    @ResponseBody
    public ResponseResult insertServiceModel(ServiceModel  serviceModel){
        serviceModelService.deleteByServiceModelFlowId(serviceModel.getServiceModelFlowId());
        serviceModel.setServiceCreateTime(new Date());
        Integer integer = serviceModelService.insertServiceModel(serviceModel);
        if(integer == 1){
            return new ResponseResult(HttpStatus.OK, "成功");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "失败");
    }

    /**
     * 重新训练
     * @return
     */
    @RequestMapping("updateByFlowId")
    @ResponseBody
    public ResponseResult updateByFlowId(ServiceModel  serviceModel){
       ServiceModel serviceModel1 =  serviceModelService.selectByServiceModelFlowId(serviceModel.getServiceModelFlowId());
       if(null == serviceModel1){
           return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "不存在。");
       }
        serviceModel1.setServiceUpdateTime(new Date());
        serviceModel1.setServiceModelStatus(serviceModel.getServiceModelStatus());
        Integer integer = serviceModelService.updateByFlowId(serviceModel1);
        if(integer == 1){
            return new ResponseResult(HttpStatus.OK, "成功");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "失败");
    }

    /**
     * 查询是否满足重新训练
     * @return
     */
    @RequestMapping("selectRebootModel")
    @ResponseBody
    public ResponseResult selectRebootModel(ServiceModel  serviceModel){
       ServiceModel serviceModel1 =  serviceModelService.selectByServiceModelFlowId(serviceModel.getServiceModelFlowId());
        if(null != serviceModel1){
            return new ResponseResult(HttpStatus.OK, "满足重新训练");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "不满足重新训练");
    }

    /**
     * 查询详情
     * @return
     */
    @RequestMapping("selectByPrimaryKey")
    public String selectByPrimaryKey(ServiceModel  serviceModel,ModelMap modelMap){
       ServiceModel serviceModel1 =  serviceModelService.selectByPrimaryKey(serviceModel.getServiceModelId());
        if(null != serviceModel1){
            String context =  serviceModel1.getContext();
            JSONObject object =   JSONObject.fromObject(context);
            if(null != object.get("fields")){
                String str = object.getString("fields");
                String s2 =  str.substring(1,str.length()-1);
                if(!s2.equals("")){
                    JSONObject object1 = JSONObject.fromObject(s2);
                    String str2 = object1.getString("data");
                    String s3 = str2.substring(1,str2.length()-1);
                    String[] arrayList = s3.split("},");
                    for (int i = 0; i <arrayList.length-1 ; i++) {
                        arrayList[i] = arrayList[i]+"}";
                    }
                    String[] arrayList1 = arrayList;
                    List list = new ArrayList();
                    for (int i = 0; i <arrayList1.length ; i++) {
                        list.add(JSONObject.fromObject(arrayList1[i]));
                    }
                    modelMap.put("arrayList1",list);
                }
            }

            String BlockId = null;
            //查询BlockId
            if(null !=object.get("blocks") ) {
                String block1 = object.getString("blocks");
                String blocks = block1.substring(1,block1.length()-1);
                String[] strings1 = blocks.split("},");
                for (int i = 0; i < strings1.length - 1; i++) {
                    strings1[i] = strings1[i] + "}";
                }
                String[] context2 = strings1;
                List list2= new ArrayList();
                for (int i = 0; i < context2.length; i++) {
                    list2.add(JSONObject.fromObject(context2[i]));
                }
                for (int i = 0; i <list2.size() ; i++) {
                    JSONObject s = JSONObject.fromObject(list2.get(i));
                    for (int j = 0; j <s.size() ; j++) {
                        if(s.get("BlockModelId").equals(BLOCK_MODEL_ID)){
                            BlockId = String.valueOf(s.get("BlockId"));
                        }
                    }
                }
            }


            String PageSourceId = null;
            //start----获取模型预测链接的方形modelid----------
            if(null !=object.get("connects") ) {
                String blocksStr = object.getString("connects");
                String block = blocksStr.substring(1, blocksStr.length() - 1);
                String[] strings = block.split("},");
                for (int i = 0; i < strings.length - 1; i++) {
                    strings[i] = strings[i] + "}";
                }
                String[] context1 = strings;
                List list1 = new ArrayList();
                for (int i = 0; i < context1.length; i++) {
                    list1.add(JSONObject.fromObject(context1[i]));
                }
                for (int i = 0; i < list1.size(); i++) {
                    String s = list1.get(i).toString();
                    JSONObject object1 = JSONObject.fromObject(s);
                    for (int j = 0; j < object1.size(); j++) {
                        String s1 = String.valueOf(object1.get("PageTargetId"));
                        if (s1.equals(BlockId)) {
                            String s2 = String.valueOf(object1.get("TargetAnchor"));
                            s2 = s2.substring(1, s2.length() - 1);
                            String[] s3 = s2.split(",");
                            if (Double.parseDouble(s3[0]) > 0.5) {
                                PageSourceId = String.valueOf(object1.get("PageSourceId"));
                            }
                        }
                    }
                }
            }


            List labelColumnList = new ArrayList();
            List selectTheColumnList = new ArrayList();
            //end----获取模型预测链接的方形modelid----------
            //选择列
            String xuanzelie = null;
            //标签列
            String biaoqianlie = null;
            if(null !=object.get("connects") ) {
                String modelsStr = object.getString("models");
                List<Model> models = JSON.parseArray(modelsStr,Model.class);
                for (int i = 0; i <models.size() ; i++) {
                    if(models.get(i).getBlockId().equals(PageSourceId)){
                        for (int j = 0; j <models.get(i).getData().size() ; j++) {
                            Integer xuanz = models.get(i).getData().get(j).getType();
                            if(xuanz.equals(LABEL_COLUMN)){
                                xuanzelie =  models.get(i).getData().get(j).getMvalue();
                            }
                            if(xuanz.equals(SELECT_THE_COLUMN)){
                                biaoqianlie =  models.get(i).getData().get(j).getMvalue();
                            }
                        }
                    }
                }
                if(null != xuanzelie){
                    String[] xuanzelies = xuanzelie.split(",");
                    for (int k = 0; k <xuanzelies.length ; k++) {
                        labelColumnList.add(xuanzelies[k]);
                    }
                }

                selectTheColumnList.add(biaoqianlie);
                modelMap.put("labelColumnList",labelColumnList);
                modelMap.put("selectTheColumnList",selectTheColumnList);
            }
            String modelContext = serviceModel1.getServiceModelContext();
            JSONObject jsonObject = JSONObject.fromObject(modelContext);
            List list = new ArrayList();
            list.add(JSONObject.fromObject(jsonObject));

            modelMap.put("modelContext",list);
            modelMap.put("serviceModel",serviceModel1);
            modelMap.put("serviceModelAdds","/service/serviceModelByApi.do");
            return "serviceModel/serviceModelDetails";
        }
        return "../errorpage/500";
    }

    /**
     * 编辑字段信息
     * @param serviceModel
     * @param modelMap
     * @return
     */
    @RequestMapping("editField")
    @ResponseBody
    public ResponseResult editField(ServiceModel  serviceModel,ModelMap modelMap){
       ServiceModel serviceModel1 =  serviceModelService.selectByPrimaryKey(serviceModel.getServiceModelId());
        if(null != serviceModel1){
            String context =  serviceModel1.getContext();
            JSONObject object =   JSONObject.fromObject(context);
            if(null != object.get("fields")){
                String str = object.getString("fields");
                String s2 =  str.substring(1,str.length()-1);
                if(!s2.equals("")){
                    JSONObject object1 = JSONObject.fromObject(s2);
                    String str2 = object1.getString("data");
                    String s3 = str2.substring(1,str2.length()-1);
                    String[] arrayList = s3.split("},");
                    for (int i = 0; i <arrayList.length-1 ; i++) {
                        arrayList[i] = arrayList[i]+"}";
                    }
                    String[] arrayList1 = arrayList;
                    List list = new ArrayList();
                    for (int i = 0; i <arrayList1.length ; i++) {
                        list.add(JSONObject.fromObject(arrayList1[i]));
                    }
                    modelMap.put("arrayList1",list);
                }

            }
            String PageSourceId = null;


            String BlockId = null;
            //查询BlockId
            if(null !=object.get("blocks") ) {
                String block1 = object.getString("blocks");
                String blocks = block1.substring(1,block1.length()-1);
                String[] strings1 = blocks.split("},");
                for (int i = 0; i < strings1.length - 1; i++) {
                    strings1[i] = strings1[i] + "}";
                }
                String[] context2 = strings1;
                List list2= new ArrayList();
                for (int i = 0; i < context2.length; i++) {
                    list2.add(JSONObject.fromObject(context2[i]));
                }
                for (int i = 0; i <list2.size() ; i++) {
                    JSONObject s = JSONObject.fromObject(list2.get(i));
                    for (int j = 0; j <s.size() ; j++) {
                        if(s.get("BlockModelId").equals(BLOCK_MODEL_ID)){
                            BlockId = String.valueOf(s.get("BlockId"));
                        }
                    }
                }
            }

            //start----获取模型预测链接的方形modelid----------
            if(null !=object.get("connects") ) {
                String blocksStr = object.getString("connects");
                String block = blocksStr.substring(1, blocksStr.length() - 1);
                String[] strings = block.split("},");
                for (int i = 0; i < strings.length - 1; i++) {
                    strings[i] = strings[i] + "}";
                }
                String[] context1 = strings;
                List list1 = new ArrayList();
                for (int i = 0; i < context1.length; i++) {
                    list1.add(JSONObject.fromObject(context1[i]));
                }
                for (int i = 0; i < list1.size(); i++) {
                    String s = list1.get(i).toString();
                    JSONObject object1 = JSONObject.fromObject(s);
                    for (int j = 0; j < object1.size(); j++) {
                        String s1 = String.valueOf(object1.get("PageTargetId"));
                        if (s1.equals(BlockId)) {
                            String s2 = String.valueOf(object1.get("TargetAnchor"));
                            s2 = s2.substring(1, s2.length() - 1);
                            String[] s3 = s2.split(",");
                            if (Double.parseDouble(s3[0]) > 0.5) {
                                PageSourceId = String.valueOf(object1.get("PageSourceId"));
                            }
                        }
                    }
                }
            }
            //获取算子输出路径并写入数据库
            WorkflowAction workflowAction= selectActionByFlowId(serviceModel1.getServiceModelFlowId(),PageSourceId);
            String s = workflowAction.getConf();
            String[] s2 = s.split("<arg>outputPath=");
            String[] s3 = s2[1].split("</arg>");
            System.out.println(s3[0]);
            serviceModel1.setOutputPath(s3[0]);
            serviceModel1.setServiceUpdateTime(new Date());
            serviceModelService.updateByFlowId(serviceModel1);


            List labelColumnList = new ArrayList();
            List selectTheColumnList = new ArrayList();
            //end----获取模型预测链接的方形modelid----------
            //选择列
            String xuanzelie = null;
            //标签列
            String biaoqianlie = null;
            if(null !=object.get("connects") ) {
                String modelsStr = object.getString("models");
                List<Model> models = JSON.parseArray(modelsStr,Model.class);
                for (int i = 0; i <models.size() ; i++) {
                    if(models.get(i).getBlockId().equals(PageSourceId)){
                        for (int j = 0; j <models.get(i).getData().size() ; j++) {
                            Integer xuanz = models.get(i).getData().get(j).getType();
                            if(xuanz.equals(LABEL_COLUMN)){
                                xuanzelie =  models.get(i).getData().get(j).getMvalue();
                            }
                            if(xuanz.equals(SELECT_THE_COLUMN)){
                                biaoqianlie =  models.get(i).getData().get(j).getMvalue();
                            }
                        }
                    }
                }
                if(null != xuanzelie){
                    String[] xuanzelies = xuanzelie.split(",");
                    for (int k = 0; k <xuanzelies.length ; k++) {
                        labelColumnList.add(xuanzelies[k]);
                    }
                }

                selectTheColumnList.add(biaoqianlie);
                modelMap.put("labelColumnList",labelColumnList);
                modelMap.put("selectTheColumnList",selectTheColumnList);
            }
            modelMap.put("serviceModel",serviceModel1);
            return new ResponseResult(HttpStatus.OK,"查询成功",modelMap);
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"查询失败");
    }

    /**
     * 接口调试
     * @return
     */
    @RequestMapping("serviceModelApi")
    public String serviceModelApi(ServiceModel  serviceModel,ModelMap modelMap){
       ServiceModel serviceModel1 =  serviceModelService.selectByPrimaryKey(serviceModel.getServiceModelId());
        if(null != serviceModel1){
            String context =  serviceModel1.getContext();
            JSONObject object =   JSONObject.fromObject(context);
            if(null != object.get("fields")){
                String str = object.getString("fields");
                String s2 =  str.substring(1,str.length()-1);
                if(!s2.equals("")){
                    JSONObject object1 = JSONObject.fromObject(s2);
                    String str2 = object1.getString("data");
                    String s3 = str2.substring(1,str2.length()-1);
                    String[] arrayList = s3.split("},");
                    for (int i = 0; i <arrayList.length-1 ; i++) {
                        arrayList[i] = arrayList[i]+"}";
                    }
                    String[] arrayList1 = arrayList;
                    List list = new ArrayList();
                    for (int i = 0; i <arrayList1.length ; i++) {
                        list.add(JSONObject.fromObject(arrayList1[i]));
                    }
                    modelMap.put("arrayList1",list);
                }
            }

            String BlockId = null;
            //查询BlockId
            if(null !=object.get("blocks") ) {
                String block1 = object.getString("blocks");
                String blocks = block1.substring(1,block1.length()-1);
                String[] strings1 = blocks.split("},");
                for (int i = 0; i < strings1.length - 1; i++) {
                    strings1[i] = strings1[i] + "}";
                }
                String[] context2 = strings1;
                List list2= new ArrayList();
                for (int i = 0; i < context2.length; i++) {
                    list2.add(JSONObject.fromObject(context2[i]));
                }
                for (int i = 0; i <list2.size() ; i++) {
                    JSONObject s = JSONObject.fromObject(list2.get(i));
                    for (int j = 0; j <s.size() ; j++) {
                        if(s.get("BlockModelId").equals(BLOCK_MODEL_ID)){
                            BlockId = String.valueOf(s.get("BlockId"));
                        }
                    }
                }
            }

            String PageSourceId = null;
            //start----获取模型预测链接的方形modelid----------
            if(null !=object.get("connects") ) {
                String blocksStr = object.getString("connects");
                String block = blocksStr.substring(1, blocksStr.length() - 1);
                String[] strings = block.split("},");
                for (int i = 0; i < strings.length - 1; i++) {
                    strings[i] = strings[i] + "}";
                }
                String[] context1 = strings;
                List list1 = new ArrayList();
                for (int i = 0; i < context1.length; i++) {
                    list1.add(JSONObject.fromObject(context1[i]));
                }
                for (int i = 0; i < list1.size(); i++) {
                    String s = list1.get(i).toString();
                    JSONObject object1 = JSONObject.fromObject(s);
                    for (int j = 0; j < object1.size(); j++) {
                        String s1 = String.valueOf(object1.get("PageTargetId"));
                        if (s1.equals(BlockId)) {
                            String s2 = String.valueOf(object1.get("TargetAnchor"));
                            s2 = s2.substring(1, s2.length() - 1);
                            String[] s3 = s2.split(",");
                            if (Double.parseDouble(s3[0]) > 0.5) {
                                PageSourceId = String.valueOf(object1.get("PageSourceId"));
                            }
                        }
                    }
                }
            }

            //获取算子输出路径并写入数据库
            WorkflowAction workflowAction= selectActionByFlowId(serviceModel1.getServiceModelFlowId(),PageSourceId);
            String s = workflowAction.getConf();
            String[] s2 = s.split("<arg>outputPath=");
            String[] s3 = s2[1].split("</arg>");
            System.out.println(s3[0]);
            serviceModel1.setOutputPath(s3[0]);
            serviceModel1.setServiceUpdateTime(new Date());
            serviceModelService.updateByFlowId(serviceModel1);

            List labelColumnList = new ArrayList();
            List selectTheColumnList = new ArrayList();
            //end----获取模型预测链接的方形modelid----------
            //选择列
            String xuanzelie = null;
            //标签列
            String biaoqianlie = null;
            if(null !=object.get("connects") ) {
                String modelsStr = object.getString("models");
                List<Model> models = JSON.parseArray(modelsStr,Model.class);
                for (int i = 0; i <models.size() ; i++) {
                    if(models.get(i).getBlockId().equals(PageSourceId)){
                        for (int j = 0; j <models.get(i).getData().size() ; j++) {
                            Integer xuanz = models.get(i).getData().get(j).getType();
                            if(xuanz.equals(LABEL_COLUMN)){
                                xuanzelie =  models.get(i).getData().get(j).getMvalue();
                            }
                            if(xuanz.equals(SELECT_THE_COLUMN)){
                                biaoqianlie =  models.get(i).getData().get(j).getMvalue();
                            }
                        }
                    }
                }
                if(null != xuanzelie){
                    String[] xuanzelies = xuanzelie.split(",");
                    for (int k = 0; k <xuanzelies.length ; k++) {
                        labelColumnList.add(xuanzelies[k]);
                    }
                }

                selectTheColumnList.add(biaoqianlie);
                modelMap.put("labelColumnList",labelColumnList);
                modelMap.put("selectTheColumnList",selectTheColumnList);
            }
            modelMap.put("serviceModel",serviceModel1);
            modelMap.put("serviceModelAdds","/service/serviceModelByApi.do");
            return "serviceModel/serviceModelApi";
        }
         return "../errorpage/500";
    }

    /**
     * 查看接口调用示例
     * @param serviceModel
     * @param modelMap
     * @return
     */
    @RequestMapping("serviceModelSample")
    public String serviceModelSample(ServiceModel  serviceModel,ModelMap modelMap){
       ServiceModel serviceModel1 =  serviceModelService.selectByPrimaryKey(serviceModel.getServiceModelId());
        if(null != serviceModel1){
            String context =  serviceModel1.getContext();
            JSONObject object =   JSONObject.fromObject(context);
            String BlockId = null;
            //查询BlockId
            if(null !=object.get("blocks") ) {
                String block1 = object.getString("blocks");
                String blocks = block1.substring(1,block1.length()-1);
                String[] strings1 = blocks.split("},");
                for (int i = 0; i < strings1.length - 1; i++) {
                    strings1[i] = strings1[i] + "}";
                }
                String[] context2 = strings1;
                List list2= new ArrayList();
                for (int i = 0; i < context2.length; i++) {
                    list2.add(JSONObject.fromObject(context2[i]));
                }
                for (int i = 0; i <list2.size() ; i++) {
                    JSONObject s = JSONObject.fromObject(list2.get(i));
                    for (int j = 0; j <s.size() ; j++) {
                        if(s.get("BlockModelId").equals(BLOCK_MODEL_ID)){
                            BlockId = String.valueOf(s.get("BlockId"));
                        }
                    }
                }
            }


            String PageSourceId = null;
            //start----获取模型预测链接的方形stateModel----------
            if(null !=object.get("connects") ) {
                String blocksStr = object.getString("connects");
                String block = blocksStr.substring(1, blocksStr.length() - 1);
                String[] strings = block.split("},");
                for (int i = 0; i < strings.length - 1; i++) {
                    strings[i] = strings[i] + "}";
                }
                String[] context1 = strings;
                List list1 = new ArrayList();
                for (int i = 0; i < context1.length; i++) {
                    list1.add(JSONObject.fromObject(context1[i]));
                }
                for (int i = 0; i < list1.size(); i++) {
                    String s = list1.get(i).toString();
                    JSONObject object1 = JSONObject.fromObject(s);
                    for (int j = 0; j < object1.size(); j++) {
                        String s1 = String.valueOf(object1.get("PageTargetId"));
                        if (s1.equals(BlockId)) {
                            String s2 = String.valueOf(object1.get("TargetAnchor"));
                            s2 = s2.substring(1, s2.length() - 1);
                            String[] s3 = s2.split(",");
                            if (Double.parseDouble(s3[0]) > 0.5) {
                                PageSourceId = String.valueOf(object1.get("PageSourceId"));
                            }
                        }
                    }
                }
            }
            List labelColumnList = new ArrayList();
            List selectTheColumnList = new ArrayList();
            //end----获取模型预测链接的方形modelid----------
            //选择列
            String xuanzelie = null;
            //标签列
            String biaoqianlie = null;
            if(null !=object.get("connects") ) {
                String modelsStr = object.getString("models");
                List<Model> models = JSON.parseArray(modelsStr,Model.class);
                for (int i = 0; i <models.size() ; i++) {
                    if(models.get(i).getBlockId().equals(PageSourceId)){
                        for (int j = 0; j <models.get(i).getData().size() ; j++) {
                            Integer xuanz = models.get(i).getData().get(j).getType();
                            if(xuanz.equals(LABEL_COLUMN)){
                                xuanzelie =  models.get(i).getData().get(j).getMvalue();
                            }
                            if(xuanz.equals(SELECT_THE_COLUMN)){
                                biaoqianlie =  models.get(i).getData().get(j).getMvalue();
                            }
                        }
                    }
                }
                if(null != xuanzelie){
                    String[] xuanzelies = xuanzelie.split(",");
                    for (int k = 0; k <xuanzelies.length ; k++) {
                        labelColumnList.add(xuanzelies[k]);
                    }
                }

                selectTheColumnList.add(biaoqianlie);
                modelMap.put("labelColumnList",labelColumnList);
                modelMap.put("selectTheColumnList",selectTheColumnList);
            }
            modelMap.put("serviceModel",serviceModel1);
            modelMap.put("serviceModelAdds","/service/serviceModelByApi.do");
            return "serviceModel/serviceModelSample";
        }
         return "../errorpage/500";
    }

    @ResponseBody
    @RequestMapping("updateModelContext")
    public ResponseResult updateModelContext(ServiceModel serviceModel){
       Integer integer =  serviceModelService.updateModelContext(serviceModel);
        WorkflowAction workflowAction= selectActionByFlowId(321,"stateModel4");
        String s = workflowAction.getConf();
        String[] s2 = s.split("<arg>outputPath=");
        String[] s3 = s2[1].split("</arg>");
        System.out.println(s3[0]);
        if(integer == 1){
            return new ResponseResult(HttpStatus.OK,"修改成功");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"修改失败");
    }

    /**
     * 使用API
     * @return
     */
    @ResponseBody
    @RequestMapping("serviceModelApiUse")
    public ResponseResult serviceModelApiUse(@Param("labelColumn") String labelColumn,@Param("outputPath") String outputPath){
        RequestBean rb = new RequestBean();
        String[] labelColumn2 = labelColumn.split(",");
//        String[] xuanzelie2 = "111,233,23233,323245,6".split(",");
        rb.setModelPath(outputPath+"/model");
//        rb.setData(new String[][] { { "1","1","1","1","1.0"}});
        rb.setData(new String[][] {labelColumn2});
        InvokeResult res = null;
        try{
             res = (InvokeResult) Predict.predictService(rb, 60);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"失败",res.log());
        }
        System.out.println(res.status());
        if(res.status().equals("200")){
            return new ResponseResult(HttpStatus.OK,"成功",res.data());
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"失败",res.log());

    }

    /**
     * 外部调用接口
     * @param outputPath
     * @return
     */
    @ResponseBody
    @RequestMapping("serviceModelByApi")
    public String serviceModelByApi(@Param("labelColumn") String labelColumn,@Param("outputPath") String outputPath,
                                            @Param("key") String key,ServiceModel serviceModel){
        try{
            String[] kTokenAndUid = key.split("_");
            String token = null;
            Integer userId = null;
            token = kTokenAndUid[0];
            userId = Integer.valueOf(kTokenAndUid[1]);
            User user = userService.selectById(userId);
            if(user.getToken().equals(token)){
                System.out.println("外部调试开启");
                serviceModelService.updateModelCount(serviceModel);
                RequestBean rb = new RequestBean();
                String[] labelColumn2 = labelColumn.split(",");
                rb.setModelPath(outputPath+"/model");
                rb.setData(new String[][] {labelColumn2});
                InvokeResult res = null;
                try{
                    res = (InvokeResult) Predict.predictService(rb, 60);
                }catch (Exception e){
                    e.printStackTrace();
                    return res.log();
                }
                System.out.println(res.status());
                if(res.status().equals("200")){
                    return res.data();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return "失败";
    }


    /**
     * 通过工作流ID和算子ID获得当前算子的公用方法
     * @param flowId
     * @param blockId
     * @return
     */
    private WorkflowAction selectActionByFlowId(Integer flowId,String blockId){
        WorkflowAction workflowAction = null;
        List<CalculationHistory> calculationHistoryList = new ArrayList<>();

        calculationHistoryList = calculationHistoryService.selectByFlowId(String.valueOf(flowId));
        if (calculationHistoryList.size()!=0 && calculationHistoryList!=null){
            String jobId=calculationHistoryList.get(0).getJobId();// 根据flowId查出最近一次记录的jobId
            if (!StringUtils.isEmpty(jobId)) {
                WorkflowJob workflowJob = iOozieApi.getOozieWorkflowInfo(jobId, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);//根据jobid获得这个工作流的运行记录
                List<WorkflowAction> actions = workflowJob.getActions();//拿到每一个算子
                for (int i = actions.size() - 2; i > 0; i--) {//去掉第一个和最后一个
                    if (actions.get(i).getName().split("_")[0].equals(blockId)){
                        workflowAction=actions.get(i);
                        break;
                    }
                }
            }
        }
        return workflowAction;
    }

//    public static void main(String[] args) {
//            RequestBean rb = new RequestBean();
//            rb.setModelPath("hdfs://172.16.0.132:8020/user/GZD/workspaces/399/stateModel2_tmp_output_0ae26ca7-45ae-4178-a980-32584336dabf/model");
//            rb.setData(new String[][] { { "2","3","7057","9810","1.0"}});
//            InvokeResult res = (InvokeResult) Predict.predictService(rb, 60);
//            System.out.println(res);
//
//    }

}
