package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelRecord

 * @Description: 我的申请记录

 * @author: jiaonanyue

 * @date: 2016年11月26日 下午5:05:51
 */
public class FacadeModelRecord implements Serializable{
	
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 2202096999386204192L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 模型id
	 */
    private Integer modelId;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimes;

    /**
     * 文件上传到hdfs路径
     */
    private String updateUrl;

    /**
     * 生成的结果路径
     */
    private String downUrl;

    /**
     * 前台用户名
     */
    private String fName;
    

    /**
     * 后端用户名
     */
    private String userName;
    
    /**
     * 1:没审核 2:审核通过 3：审核不通
     */
    private Integer type;
    private String typeName;
    
    /**
     * 订阅理由
     */
    private String reason;
    
    /**
     * 电话号码
     */
    private String tel;
    
    /**
     * 用户邮箱信息
     */
    private String mail;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl == null ? null : updateUrl.trim();
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl == null ? null : downUrl.trim();
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName == null ? null : fName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "FacadeModelRecord [id=" + id + ", modelId=" + modelId + ", createTime=" + createTime + ", createTimes="
				+ createTimes + ", updateUrl=" + updateUrl + ", downUrl=" + downUrl + ", fName=" + fName + ", userName="
				+ userName + ", type=" + type + ", typeName=" + typeName + ", reason=" + reason + ", tel=" + tel
				+ ", mail=" + mail + "]";
	}



    
    
}