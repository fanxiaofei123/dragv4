package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: OperationResult

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年12月27日 下午4:11:12
 */
public class OperationResult implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 5439313253349258969L;

	private Integer id;

	/**
	 * 用户id
	 */
    private Integer userid;

    /**
     * 工作流id
     */
    private Integer workflowid;

    /**
     * 结果时间
     */
    private Date resultTime;

    /**
     * 结果内容
     */
    private String resultCont;

    /**
     * 结果名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Integer workflowid) {
        this.workflowid = workflowid;
    }

    public Date getResultTime() {
        return resultTime;
    }

    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
    }

    public String getResultCont() {
        return resultCont;
    }

    public void setResultCont(String resultCont) {
        this.resultCont = resultCont == null ? null : resultCont.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	@Override
	public String toString() {
		return "OperationResult [id=" + id + ", userid=" + userid + ", workflowid=" + workflowid + ", resultTime="
				+ resultTime + ", resultCont=" + resultCont + ", name=" + name + "]";
	}
    
    
}