package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: OriginalData

 * @Description: 原始数据

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午3:42:25
 */
public class OriginalData implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -2578038476303413959L;

	private Integer id;

	/**
	 * 用户id
	 */
    private Integer userid;

    /**
     * 上传时间
     */
    private Date updateTime;

    /**
     * 上传后到hdfs上的路径
     */
    private String updateUrl;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl == null ? null : updateUrl.trim();
    }

	@Override
	public String toString() {
		return "OriginalData [id=" + id + ", userid=" + userid + ", updateTime=" + updateTime + ", updateUrl="
				+ updateUrl + "]";
	}
    
}