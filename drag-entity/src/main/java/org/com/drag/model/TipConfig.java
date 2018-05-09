package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;

public class TipConfig implements Serializable {
    private static final long serialVersionUID = 703287329388861312L;

    private Integer tipConfigId;

    /**
     * 工作调度id
     */
    private Integer schJobId;

    /**
     * 提醒名称
     */
    private String tipConfigName;

    /**
     * 提醒类型
     0:出错
     1:完成
     2:未按时完成
     */
    private Short tipConfigType;

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    private String configType;

    /**
     * 提醒发送方式
     0:邮箱
     1:手机
     */
    private Short tipConfigSendType;

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    private String sendType;

    /**
     * 接收人
     */
    private String tipConfigReceiver;

    private Date tipConfigCreateTime;

    public String getTipConfigCreateTimes() {
        return tipConfigCreateTimes;
    }

    public void setTipConfigCreateTimes(String tipConfigCreateTimes) {
        this.tipConfigCreateTimes = tipConfigCreateTimes;
    }

    private String tipConfigCreateTimes;

    /**
     * 提醒是否启用 0.未启用 1.启用
     */
    private Boolean tipConfigEnable;

    public String getTipConfigEnables() {
        return tipConfigEnables;
    }

    public void setTipConfigEnables(String tipConfigEnables) {
        this.tipConfigEnables = tipConfigEnables;
    }

    private String tipConfigEnables;

    /**
     * 内容
     */
    private String tipConfigContent;

    @Override
    public String toString() {
        return "TipConfig{" +
                "tipConfigId=" + tipConfigId +
                ", schJobId=" + schJobId +
                ", tipConfigName='" + tipConfigName + '\'' +
                ", tipConfigType=" + tipConfigType +
                ", configType='" + configType + '\'' +
                ", tipConfigSendType=" + tipConfigSendType +
                ", sendType='" + sendType + '\'' +
                ", tipConfigReceiver='" + tipConfigReceiver + '\'' +
                ", tipConfigCreateTime=" + tipConfigCreateTime +
                ", tipConfigCreateTimes='" + tipConfigCreateTimes + '\'' +
                ", tipConfigEnable=" + tipConfigEnable +
                ", tipConfigEnables='" + tipConfigEnables + '\'' +
                ", tipConfigContent='" + tipConfigContent + '\'' +
                ", loginname='" + loginname + '\'' +
                ", schJobName='" + schJobName + '\'' +
                '}';
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    /**
     * 登陆人名
     */
    private String loginname;

    public String getSchJobName() {
        return schJobName;
    }

    public void setSchJobName(String schJobName) {
        this.schJobName = schJobName;
    }

    /**
     * 任务名称
     * @return
     */
    private String  schJobName;

    public Integer getTipConfigId() {
        return tipConfigId;
    }

    public void setTipConfigId(Integer tipConfigId) {
        this.tipConfigId = tipConfigId;
    }

    /**
     * @return 工作调度id
     */
    public Integer getSchJobId() {
        return schJobId;
    }

    /**
     * @param schJobId
     *            工作调度id
     */
    public void setSchJobId(Integer schJobId) {
        this.schJobId = schJobId;
    }

    /**
     * @return 提醒名称
     */
    public String getTipConfigName() {
        return tipConfigName;
    }

    /**
     * @param tipConfigName
     *            提醒名称
     */
    public void setTipConfigName(String tipConfigName) {
        this.tipConfigName = tipConfigName;
    }

    /**
     * @return 提醒方式 1.邮箱
     */
    public Short getTipConfigType() {
        return tipConfigType;
    }

    /**
     * @param tipConfigType
     *            提醒方式 1.邮箱
     */
    public void setTipConfigType(Short tipConfigType) {
        this.tipConfigType = tipConfigType;
    }

    /**
     * @return 提醒发送方式
    0:邮箱
    1:手机
     */
    public Short getTipConfigSendType() {
        return tipConfigSendType;
    }

    /**
     * @param tipConfigSendType
     * 提醒发送方式
    0:邮箱
    1:手机
     */
    public void setTipConfigSendType(Short tipConfigSendType) {
        this.tipConfigSendType = tipConfigSendType;
    }

    /**
     * @return 接收人
     */
    public String getTipConfigReceiver() {
        return tipConfigReceiver;
    }

    /**
     * @param tipConfigReceiver
     *            接收人
     */
    public void setTipConfigReceiver(String tipConfigReceiver) {
        this.tipConfigReceiver = tipConfigReceiver;
    }

    public Date getTipConfigCreateTime() {
        return tipConfigCreateTime;
    }

    public void setTipConfigCreateTime(Date tipConfigCreateTime) {
        this.tipConfigCreateTime = tipConfigCreateTime;
    }

    /**
     * @return 提醒是否启用 0.未启用 1.启用
     */
    public Boolean getTipConfigEnable() {
        return tipConfigEnable;
    }

    /**
     * @param tipConfigEnable
     *          提醒是否启用 0.未启用 1.启用
     */
    public void setTipConfigEnable(Boolean tipConfigEnable) {
        this.tipConfigEnable = tipConfigEnable;
    }

    /**
     * @return 内容
     */
    public String getTipConfigContent() {
        return tipConfigContent;
    }

    /**
     * @param tipConfigContent
     *            内容
     */
    public void setTipConfigContent(String tipConfigContent) {
        this.tipConfigContent = tipConfigContent;
    }

    public Integer getFlowId() {
        return flowId;
    }
    //工作流id
    private Integer flowId;
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }
}