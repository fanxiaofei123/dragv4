package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;

public class TipRecord  implements Serializable {
    private static final long serialVersionUID = 521081285864719811L;

    private Integer tipRecordId;

    /**
     * 设置提醒表的id
     */
    private Integer tipConfigId;

    /**
     * 记录状态
     */
    private Short tipRecordStatus;
    private String recordStatus;

    /**
     * 记录发送时间
     */
    private Date tipRecordSendTime;
    private String recordSendTime;

    public String getRecordSendTime() {
        return recordSendTime;
    }

    public void setRecordSendTime(String recordSendTime) {
        this.recordSendTime = recordSendTime;
    }

    /**
     * 任务名称
     * @return
     */
    private String  schJobName;
    /**
     * 提醒名称
     */
    private String tipConfigName;
    /**
     * 接收人
     */
    private String tipConfigReceiver;
    /**
     * 提醒发送方式
     0:邮箱
     1:手机
     */
    private Short tipConfigSendType;
    private String configSendType;
    /**
     * 提醒类型
     0:出错
     1:完成

     */
    private Short tipConfigType;
    private String configType;

    /**
     * 登录名
     * @return
     */
    private String loginName;
    /**
     * 搜索任务名和提醒名输入框
     */
    private String inputName;
    /**
     * 提醒内容
     */
    private String tipContent;
    /**
     * 时间控件开始时间
     */
    private String startTime;
    /**
     * 时间控件结束时间
     */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getConfigSendType() {
        return configSendType;
    }

    public void setConfigSendType(String configSendType) {
        this.configSendType = configSendType;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getSchJobName() {
        return schJobName;
    }

    public void setSchJobName(String schJobName) {
        this.schJobName = schJobName;
    }

    public String getTipConfigName() {
        return tipConfigName;
    }

    public void setTipConfigName(String tipConfigName) {
        this.tipConfigName = tipConfigName;
    }

    public String getTipConfigReceiver() {
        return tipConfigReceiver;
    }

    public void setTipConfigReceiver(String tipConfigReceiver) {
        this.tipConfigReceiver = tipConfigReceiver;
    }

    public Short getTipConfigSendType() {
        return tipConfigSendType;
    }

    public void setTipConfigSendType(Short tipConfigSendType) {
        this.tipConfigSendType = tipConfigSendType;
    }

    public Short getTipConfigType() {
        return tipConfigType;
    }

    public void setTipConfigType(Short tipConfigType) {
        this.tipConfigType = tipConfigType;
    }

    public Integer getTipRecordId() {
        return tipRecordId;
    }

    public void setTipRecordId(Integer tipRecordId) {
        this.tipRecordId = tipRecordId;
    }

    /**
     * @return 设置提醒表的id
     */
    public Integer getTipConfigId() {
        return tipConfigId;
    }

    /**
     * @param tipConfigId
     *            设置提醒表的id
     */
    public void setTipConfigId(Integer tipConfigId) {
        this.tipConfigId = tipConfigId;
    }

    /**
     * @return 记录状态
     */
    public Short getTipRecordStatus() {
        return tipRecordStatus;
    }

    /**
     * @param tipRecordStatus
     *            记录状态
     */
    public void setTipRecordStatus(Short tipRecordStatus) {
        this.tipRecordStatus = tipRecordStatus;
    }

    /**
     * @return 记录发送时间
     */
    public Date getTipRecordSendTime() {
        return tipRecordSendTime;
    }

    /**
     * @param tipRecordSendTime
     *            记录发送时间
     */
    public void setTipRecordSendTime(Date tipRecordSendTime) {
        this.tipRecordSendTime = tipRecordSendTime;
    }
}