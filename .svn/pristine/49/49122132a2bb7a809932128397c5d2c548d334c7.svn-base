package org.com.drag.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.httpclient.HttpStatus;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.*;
import org.com.drag.service.DataModelService;
import org.com.drag.service.ModelTrainedService;
import org.com.drag.service.UserService;
import org.com.drag.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by huangyu on 2017/10/19.
 */
@Controller
@RequestMapping("drag/modeltrained")
public class ModelTrainedController {
    public static final int DEFAULT_ROWCOUNT=5;
    public static final int DEFAULT_PAGENUM=1;
    /**
     * 已训练的模型
     */
    private static final String MODEL_NAME="已训练的模型";
    @Autowired
    private ModelTrainedService modelTrainedService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private DataModelService dataModelService;

    /**
     * 获取已训练的模型的列表
     * @param page   页码
     * @param inputName 搜索框输入的内容
     * @param session
     * @return
     */
    @RequestMapping("trainedmodellist")
    @ResponseBody
    public PageBean getTrainedModelList(Integer page,String inputName,HttpSession session){
        if (page==null|| page==0){
            page=DEFAULT_PAGENUM;
        }
        User user = (User) session.getAttribute(Constants.USER_KEY);
        ModelTrained modelTrained = new ModelTrained();
        modelTrained.setUserId(user.getId());
        modelTrained.setModelTrainedName(inputName);
        PageHelper.startPage(page,DEFAULT_ROWCOUNT);//分页
        List<ModelTrained> modelTrainedList = modelTrainedService.selectAll(modelTrained);
        for (ModelTrained trained : modelTrainedList) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            trained.setModificationTime(format.format(trained.getModelTrainedUpdateTime()));
            if(trained.getModelTrainedStatus()==0){
                trained.setShowStatus("启用");
                trained.setSwitchStatus("停用");
            }else {
                trained.setShowStatus("停用");
                trained.setSwitchStatus("启用");
            }
            User modelUser = userService.selectById(trained.getUserId());
            trained.setUserName(modelUser.getLoginname());
            WorkFlow workFlow=workFlowService.selectByPrimaryKey(trained.getWorkflowId());
            trained.setWorkflowName(workFlow.getName());
        }
        PageInfo<ModelTrained> pageInfo = new PageInfo<>(modelTrainedList);
        PageBean pageBean = new PageBean();
        pageBean.setRows(modelTrainedList);
        pageBean.setCurPage(page);
        pageBean.setTotal(modelTrainedList.size());
        pageBean.setTotalPage(pageInfo.getPages());
        return  pageBean;
    }

    /**
     * 重命名
     * @param modelTrainedId
     * @param newName
     * @return
     */
    @RequestMapping("rename")
    @ResponseBody
    public ResponseResult reName(Integer modelTrainedId, String newName,HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        Map<String,Object> params= new HashMap<>();
        params.put("userId",user.getId());
        params.put("modelTrainedName",newName);
        List<ModelTrained> modelTrainedList  = modelTrainedService.selectByIdAndName(params);
        if (modelTrainedList.size()==0){
            ModelTrained modelTrained = new ModelTrained();
            modelTrained.setModelTrainedId(modelTrainedId);
            modelTrained.setModelTrainedName(newName);
            modelTrained.setModelTrainedUpdateTime(new Date());
            int status=modelTrainedService.updateByPrimaryKeySelective(modelTrained);
            return status==0? new ResponseResult(HttpStatus.SC_EXPECTATION_FAILED,"重命名失败") :new ResponseResult(HttpStatus.SC_OK,"重命名成功");
        }else {
            return  new ResponseResult(HttpStatus.SC_EXPECTATION_FAILED,"该模型名称已存在");
        }
    }

    /**
     * 停用或者启用
     * @param modelTrainedId
     * @param switchStatus
     */
    @RequestMapping("disableorenable")
    @ResponseBody
    public void changeStatus(Integer modelTrainedId,String switchStatus){
        ModelTrained modelTrained = new ModelTrained();
        modelTrained.setModelTrainedId(modelTrainedId);
        Integer status=0;
        if ("启用".equals(switchStatus)){
            status=0;//当启用被点击时，状态变为启用
        }else if ("停用".equals(switchStatus)){
            status=1;
        }
        modelTrained.setModelTrainedStatus(status);
        modelTrained.setModelTrainedUpdateTime(new Date());
        int changeRows=modelTrainedService.updateByPrimaryKeySelective(modelTrained);
    }

    /**
     *
     * 删除
     * @param modelTrainedId
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseResult deleteModelTrained(Integer modelTrainedId){
        int delStatus=modelTrainedService.deleteById(modelTrainedId);
        return delStatus==0? new ResponseResult(HttpStatus.SC_EXPECTATION_FAILED,"删除失败") :new ResponseResult(HttpStatus.SC_OK,"删除成功");
    }

    /**
     * 批量删除
     * @param modelTrainedIds
     * @return
     */
    @RequestMapping("batchdelete")
    @ResponseBody
    public ResponseResult batchDeleteModelTrained(String modelTrainedIds) {
        String[] ids = modelTrainedIds.split(",");
        int delStatus = modelTrainedService.batchDelete(ids);
        return delStatus == 0 ? new ResponseResult(HttpStatus.SC_EXPECTATION_FAILED, "删除失败") : new ResponseResult(HttpStatus.SC_OK, "删除成功");
    }

    @RequestMapping("gettrainedmodel")
    @ResponseBody
    public List<DataModel> getTrainedModel(HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<DataModel> modelList = new ArrayList<DataModel>();
        ModelTrained modelTrained = new ModelTrained();
        modelTrained.setUserId(user.getId());
        modelTrained.setModelTrainedStatus(0);
        DataModel dataModel = new DataModel();
        dataModel.setName(MODEL_NAME);
        List<DataModel> dataModels=dataModelService.selectByDataModel(dataModel);//拿到DataModel表中的已训练模型算子
        List<ModelTrained> modelTrainedList = modelTrainedService.selectAll(modelTrained);
        for (ModelTrained trained : modelTrainedList) {
                DataModel dataModel1= new DataModel();
                dataModel1.setId(dataModels.get(0).getId());
                dataModel1.setModelTrainedId(trained.getModelTrainedId());
                dataModel1.setName(trained.getModelTrainedName());
                dataModel1.setCreateTime(trained.getModelTrainedUpdateTime());
                dataModel1.setFlowDetails(dataModels.get(0).getFlowDetails());
                dataModel1.setNameEnglish(dataModels.get(0).getNameEnglish());
                dataModel1.setNameRmNull(dataModels.get(0).getNameEnglish().replaceAll("\\s*", ""));
                dataModel1.setRightNumber(dataModels.get(0).getRightNumber());
                dataModel1.setLeftNumber("");
                modelList.add(dataModel1);
            }
        return modelList;
    }
}
