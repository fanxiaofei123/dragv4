package org.com.drag.model;

import org.com.drag.common.util.Constants;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkSpace

 * @Description: 工作空间

 * @author: Guzhendong

 */
public class WorkSpace implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -2751278684909680459L;

	private Integer id;

	/**
	 * 用户id
	 */
    private Integer userid;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimes;
    /**
     * 创建的内容
     */
    private String createContent;

    /**
     * 结果内容-结果的路径
     */
    private String resultContect;

    /**
     * 工作空间名称
     */
    private String name;

    private String hdfsUrl;
    
    /**
     * 额外字段
     * 用户名称
     */
    private String userEmail;
    /**
     * 批量删除传的id
     */
    private List<Integer> ids;
    /**
     * 父节id(如果是0，则是根节点)
     */
    private Integer pid;

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    /**
     * 是否是叶子节点，是：1，否：0
     */
    private Integer isParent;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }




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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateContent() {
        return createContent;
    }

    public void setCreateContent(String createContent) {
        this.createContent = createContent == null ? null : createContent.trim();
    }

    public String getResultContect() {
        return resultContect;
    }

    public void setResultContect(String resultContect) {
        this.resultContect = resultContect == null ? null : resultContect.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHdfsUrl() {
        return hdfsUrl;
    }

    public String getHdfsUrl(WorkSpace workSpace) {
        return Constants.getHdfsUrl().toString()+workSpace.getHdfsUrl();
    }
    public void setHdfsUrl(String hdfsUrl) {
        this.hdfsUrl = hdfsUrl == null ? null : hdfsUrl.trim();
    }


	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

    @Override
    public String toString() {
        return "WorkSpace{" +
                "id=" + id +
                ", userid=" + userid +
                ", createTime=" + createTime +
                ", createTimes='" + createTimes + '\'' +
                ", createContent='" + createContent + '\'' +
                ", resultContect='" + resultContect + '\'' +
                ", name='" + name + '\'' +
                ", hdfsUrl='" + hdfsUrl + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", ids=" + ids +
                ", pid=" + pid +
                ", isParent=" + isParent +
                '}';
    }
}