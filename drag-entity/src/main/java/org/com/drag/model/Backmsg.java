package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;

public class Backmsg implements Serializable {
    private static final long serialVersionUID = 338506722105141781L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 反馈人
     */
    private String frontname;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系邮件
     */
    private String email;

    /**
     * 反馈时间
     */
    private Date createtime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 反馈内容意见
     */
    private String content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    private String startTime;
    private String endTime;

    /**
     * @return 反馈人
     */
    public String getFrontname() {
        return frontname;
    }

    /**
     * @param frontname
     *            反馈人
     */
    public void setFrontname(String frontname) {
        this.frontname = frontname;
    }

    /**
     * @return 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 联系邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            联系邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 反馈时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     *            反馈时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}