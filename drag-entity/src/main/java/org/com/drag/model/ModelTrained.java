package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;

public class ModelTrained implements Serializable {
    private static final long serialVersionUID = 166311949255415172L;

    private Integer modelTrainedId;

    public Integer getModelTrainedId() {
        return modelTrainedId;
    }

    public void setModelTrainedId(Integer modelTrainedId) {
        this.modelTrainedId = modelTrainedId;
    }
    /**
     * 已训练模型的名称
     */
    private String modelTrainedName;
    /**
     * data_model表的id
     */
    private Integer modelId;


    private Integer userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getModelTrainedName() {
        return modelTrainedName;
    }

    public void setModelTrainedName(String modelTrainedName) {
        this.modelTrainedName = modelTrainedName;
    }

    public Date getModelTrainedUpdateTime() {
        return modelTrainedUpdateTime;
    }

    public void setModelTrainedUpdateTime(Date modelTrainedUpdateTime) {
        this.modelTrainedUpdateTime = modelTrainedUpdateTime;
    }

    public Integer getModelTrainedStatus() {
        return modelTrainedStatus;
    }

    public void setModelTrainedStatus(Integer modelTrainedStatus) {
        this.modelTrainedStatus = modelTrainedStatus;
    }

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * 工作流ID
     */
    private Integer workflowId;

    private String workflowName;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    /**
     * 修改时间
     */
    private Date modelTrainedUpdateTime;
    /**
     * 前台展示的修改时间
     */
    private String modificationTime;

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getShowStatus() {
        return ShowStatus;
    }

    public void setShowStatus(String showStatus) {
        ShowStatus = showStatus;
    }

    /**
     * 当前状态，0，已启用   1，已停用
     */
    private Integer modelTrainedStatus;
    /**
     * 状态：负责前台展示
     */
    private String ShowStatus;

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    /**
     *  状态修改的属性
     */
    private String switchStatus;



    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * 算子路径
     */
    private String modelTrainedPath;

    public String getModelTrainedPath() {
        return modelTrainedPath;
    }

    public void setModelTrainedPath(String modelTrainedPath) {
        this.modelTrainedPath = modelTrainedPath;
    }

    /**
     * 原算子名称
     */
    private String dataModelName;

    public String getDataModelName() {
        return dataModelName;
    }

    public void setDataModelName(String dataModelName) {
        this.dataModelName = dataModelName;
    }
}