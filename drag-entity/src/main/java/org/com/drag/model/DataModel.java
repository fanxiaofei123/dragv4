package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: DataModel

 * @Description: 数据模型-算法模块模型

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午3:39:19
 */
public class DataModel implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -8607371572643752176L;

	private Integer id;

	/**
	 * 模型名称
	 */
    private String name;
    
    
    private String nameEnglish;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 模型排序
     */
    private Byte modelSort;

    /**
     * 模块分级 0：第一级  1：第二级  2：第三极
     */
    private Byte type;

    /**
     * 父类id   及树形结构用
     */
    private Integer pid;
    
    /**
     * 模型说明
     */
    private String flowDetails;

    /**
     * 模型父类类型
     */
    private String pidName;
    
    /**
     * 左连接模型样式1：园 2：正方形 3：三角形
     */
    private String leftNumber;
    
    /**
     * 右连接模型样式1：园 2：正方形 3：三角形
     */
    private String rightNumber;


    public Integer getModelTrainedId() {
        return modelTrainedId;
    }

    public void setModelTrainedId(Integer modelTrainedId) {
        this.modelTrainedId = modelTrainedId;
    }

    /**
     * 额外字段
     * @return
     */

    private Integer modelTrainedId;


    private String nameRmNull;
    
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getModelSort() {
        return modelSort;
    }

    public void setModelSort(Byte modelSort) {
        this.modelSort = modelSort;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	public String getFlowDetails() {
		return flowDetails;
	}

	public void setFlowDetails(String flowDetails) {
		this.flowDetails = flowDetails;
	}

	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}

	public String getNameRmNull() {
		return nameRmNull;
	}

	public void setNameRmNull(String nameRmNull) {
		this.nameRmNull = nameRmNull;
	}

	public String getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(String leftNumber) {
		this.leftNumber = leftNumber;
	}

	public String getRightNumber() {
		return rightNumber;
	}

	public void setRightNumber(String rightNumber) {
		this.rightNumber = rightNumber;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@Override
	public String toString() {
		return "DataModel [id=" + id + ", name=" + name + ", nameEnglish=" + nameEnglish + ", createTime=" + createTime
				+ ", modelSort=" + modelSort + ", type=" + type + ", pid=" + pid + ", flowDetails=" + flowDetails
				+ ", pidName=" + pidName + ", leftNumber=" + leftNumber + ", rightNumber=" + rightNumber
				+ ", nameRmNull=" + nameRmNull + "]";
	}

	

	


    
}