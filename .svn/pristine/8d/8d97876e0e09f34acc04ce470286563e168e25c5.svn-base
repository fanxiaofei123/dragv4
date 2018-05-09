package org.com.drag.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModel

 * @Description: 共享模型

 * @author: jiaonanyue

 * @date: 2016年11月4日 下午3:42:47
 */
public class FacadeModel implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 工作流的id
	 */
	private Integer flowId;

	/**
	 * 模型名称
	 */
    private String name;
    
    /**
     * 创建时间
     */
    private Date creatime;
    private String createTimes;
    /**
     * 说明
     */
    private String flowExplain;

    /**
     * 接口调用数
     */
    private Integer interfacenum;

    /**
     * 提供者
     */
    private String userName;

    /**
     * 内容
     */
    private String modelContext;

    /**
     * 状态     3：待发布  4：已经发布 5:下线  7：取消发布 8：审核通过
     */
    private Integer type;
    private String typeName;
    /**
     * 树的id
     */
    private Integer treeid;

    /**
     * 工作空间名称
     */
    private String workspaceName;
    
    /**
     * 模型前端传过来的名称
     */
    
    private String ModelName;
    /**
     * type in查询
     */
    private Integer[] idList;
    
    /**
     * treeid in查询
     */
    private Integer[] idListTree;
    
    public Integer[] getIdList() {
		return idList;
	}

	public void setIdList(Integer[] idList) {
		this.idList = idList;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }


    public String getFlowExplain() {
		return flowExplain;
	}

	public void setFlowExplain(String flowExplain) {
		this.flowExplain = flowExplain;
	}

	public Integer getInterfacenum() {
        return interfacenum;
    }

    public void setInterfacenum(Integer interfacenum) {
        this.interfacenum = interfacenum;
    }



    public String getModelContext() {
		return modelContext;
	}

	public void setModelContext(String modelContext) {
		this.modelContext = modelContext;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTreeid() {
        return treeid;
    }

    public void setTreeid(Integer treeid) {
        this.treeid = treeid;
    }


	public Integer getFlowId() {
		return flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer[] getIdListTree() {
		return idListTree;
	}

	public void setIdListTree(Integer[] idListTree) {
		this.idListTree = idListTree;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

	@Override
	public String toString() {
		return "FacadeModel [id=" + id + ", flowId=" + flowId + ", name=" + name + ", creatime=" + creatime
				+ ", createTimes=" + createTimes + ", flowExplain=" + flowExplain + ", interfacenum=" + interfacenum
				+ ", userName=" + userName + ", modelContext=" + modelContext + ", type=" + type + ", typeName="
				+ typeName + ", treeid=" + treeid + ", workspaceName=" + workspaceName + ", ModelName=" + ModelName
				+ ", idList=" + Arrays.toString(idList) + ", idListTree=" + Arrays.toString(idListTree) + "]";
	}


}