package org.com.drag.model;

import org.com.drag.common.util.Constants;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkFlow

 * @Description: 工作流实体类

 * @author: jiaonanyue

 * @date: 2016年11月2日 下午5:49:20
 */
public class WorkFlow implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 8549171391859440096L;

	private Integer id;

	/*
	 * 工作空间id
	 */
    private Integer workspid;
    private String workspaceName;
    
    /**
     * 用户登录名称
     */
    private String userName;

    /**
     * 工作流程内容
     */
    private String context;

    /**
     * 工作留创建时间
     */
    private Date createTime;
    private String createTimes;

    /**
     * 工作留修改时间
     */
    private Date updatTime;

    /**
     * 工作留返回结果-地址
     */
    private String resultContect;

    /**
     * 工作留的名称
     */
    private String name;

    /**
     * 工作流的hdfs地址
     */
    private String hdfsUrl;
    
    /**
     * 电子流说明
     */
    private String flowExplain;
    /**
     * 工作流状态  1：上传的模型（训练完的模型）待上传  2：待审核  3：待发布  4：已经发布 5:下线  6.审核不通过 0或者null ：没有训练完的模型
     */
    private Integer type;
    private String typeName;
    
    
    
    private String userMial;
    
    private String workName;


    private Integer jobstatus;

    
    private List<Integer> ids;
    
    

    private String createContent;
    
    
    private Integer[] idList;
    
    
    private Integer  Modelid;
    


   	public Integer[] getIdList() {
   		return idList;
   	}

   	public void setIdList(Integer[] idList) {
   		this.idList = idList;
   	}
   	
    public String getCreateContent() {
		return createContent;
	}

	public void setCreateContent(String createContent) {
		this.createContent = createContent;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkspid() {
        return workspid;
    }

    public void setWorkspid(Integer workspid) {
        this.workspid = workspid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatTime() {
        return updatTime;
    }

    public Integer getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(Integer jobstatus) {
        this.jobstatus = jobstatus;
    }

    public void setUpdatTime(Date updatTime) {
        this.updatTime = updatTime;
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

    public String getHdfsUrl(WorkFlow workFlow){
		return Constants.getHdfsUrl().toString()+workFlow.getHdfsUrl();
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

	public String getUserMial() {
		return userMial;
	}

	public void setUserMial(String userMial) {
		this.userMial = userMial;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getFlowExplain() {
		return flowExplain;
	}

	public void setFlowExplain(String flowExplain) {
		this.flowExplain = flowExplain;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public Integer getModelid() {
		return Modelid;
	}

	public void setModelid(Integer modelid) {
		Modelid = modelid;
	}

	@Override
	public String toString() {
		return "WorkFlow [id=" + id + ", workspid=" + workspid + ", workspaceName=" + workspaceName + ", userName="
				+ userName + ", context=" + context + ", createTime=" + createTime + ", createTimes=" + createTimes
				+ ", updatTime=" + updatTime + ", resultContect=" + resultContect + ", name=" + name + ", hdfsUrl="
				+ hdfsUrl + ", flowExplain=" + flowExplain + ", type=" + type + ", typeName=" + typeName + ", userMial="
				+ userMial + ", workName=" + workName + ", jobstatus=" + jobstatus + ", ids=" + ids + ", createContent="
				+ createContent + ", idList=" + Arrays.toString(idList) + ", Modelid=" + Modelid + "]";
	}

	

}