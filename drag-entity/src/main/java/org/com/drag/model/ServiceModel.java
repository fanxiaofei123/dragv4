package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;

public class ServiceModel implements Serializable {
    private static final long serialVersionUID = 629261518669515221L;

    private Integer serviceModelId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 登录用户id
     */
    private Integer userId;

    public String getBiaoqianlie() {
        return biaoqianlie;
    }

    public void setBiaoqianlie(String biaoqianlie) {
        this.biaoqianlie = biaoqianlie;
    }

    public String getXuanzelie() {
        return xuanzelie;
    }

    public void setXuanzelie(String xuanzelie) {
        this.xuanzelie = xuanzelie;
    }

    public String getSelectTheColumn() {
        return selectTheColumn;
    }

    public void setSelectTheColumn(String selectTheColumn) {
        this.selectTheColumn = selectTheColumn;
    }

    public String getLabelColumn() {
        return labelColumn;
    }

    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn;
    }
    /**标签列*/
    private String selectTheColumn;
    /**选择列*/
    private String labelColumn;
    private String biaoqianlie;
    private String xuanzelie;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 工作流最后一次运行状态
     * -1：失败  1：运行中  2：成功
     */
    private Integer status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 登陆用户token用于对应KEY
     */
    private String token;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    /**
     * 工作流连线内容
     */
    private String context;

    public Integer getServiceModelCount() {
        return serviceModelCount;
    }

    public void setServiceModelCount(Integer serviceModelCount) {
        this.serviceModelCount = serviceModelCount;
    }

    /**
     * 服务调用次数
     */
    private Integer serviceModelCount;

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    /**
     * 工作流名称
     */
    private String workFlowName;
    /**
     * 工作空间名称
     */
    private String workSpaceName;
    /**
     * 算子输出路径
     */
    private String outputPath;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * 编辑字段描述信息
     */
    private String serviceModelContext;

    public String getServiceModelContext() {
        return serviceModelContext;
    }

    public void setServiceModelContext(String serviceModelContext) {
        this.serviceModelContext = serviceModelContext;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 登陆用户名
     */
    private String userName;


    /**
     * 模型服务名称
     */
    private String serviceModelName;

    public String getServiceModelStatus() {
        return serviceModelStatus;
    }

    public void setServiceModelStatus(String serviceModelStatus) {
        this.serviceModelStatus = serviceModelStatus;
    }

    /**
     * 1.可以调用 2.工作流训练失败不可以调用 3.在重新训练中不可调用
     */
    private String serviceModelStatus;

    /**
     * 工作流id
     */
    private Integer serviceModelFlowId;

    /**
     * 模型服务描述
     */
    private String serviceModelDescription;

    public String getServiceCreateTimes() {
        return serviceCreateTimes;
    }

    public void setServiceCreateTimes(String serviceCreateTimes) {
        this.serviceCreateTimes = serviceCreateTimes;
    }

    public String getServiceUpdateTimes() {
        return serviceUpdateTimes;
    }

    public void setServiceUpdateTimes(String serviceUpdateTimes) {
        this.serviceUpdateTimes = serviceUpdateTimes;
    }

    private String serviceCreateTimes;

    private String serviceUpdateTimes;

    private Date serviceCreateTime;

    private Date serviceUpdateTime;

    public Integer getServiceModelId() {
        return serviceModelId;
    }

    public void setServiceModelId(Integer serviceModelId) {
        this.serviceModelId = serviceModelId;
    }

    /**
     * @return 模型服务名称
     */
    public String getServiceModelName() {
        return serviceModelName;
    }

    /**
     * @param serviceModelName
     *            模型服务名称
     */
    public void setServiceModelName(String serviceModelName) {
        this.serviceModelName = serviceModelName;
    }

    /**
     * @return 工作流id
     */
    public Integer getServiceModelFlowId() {
        return serviceModelFlowId;
    }

    /**
     * @param serviceModelFlowId
     *            工作流id
     */
    public void setServiceModelFlowId(Integer serviceModelFlowId) {
        this.serviceModelFlowId = serviceModelFlowId;
    }

    /**
     * @return 模型服务描述
     */
    public String getServiceModelDescription() {
        return serviceModelDescription;
    }

    /**
     * @param serviceModelDescription
     *            模型服务描述
     */
    public void setServiceModelDescription(String serviceModelDescription) {
        this.serviceModelDescription = serviceModelDescription;
    }

    public Date getServiceCreateTime() {
        return serviceCreateTime;
    }

    public void setServiceCreateTime(Date serviceCreateTime) {
        this.serviceCreateTime = serviceCreateTime;
    }

    public Date getServiceUpdateTime() {
        return serviceUpdateTime;
    }

    public void setServiceUpdateTime(Date serviceUpdateTime) {
        this.serviceUpdateTime = serviceUpdateTime;
    }
}