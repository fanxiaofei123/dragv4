package org.com.drag.model;

import java.io.Serializable;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelMenu

 * @Description: 模型与树关联表

 * @author: jiaonanyue

 * @date: 2016年11月4日 下午3:47:37
 */
public class FacadeModelMenu implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer modelid;

    private Integer menuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelid() {
        return modelid;
    }

    public void setModelid(Integer modelid) {
        this.modelid = modelid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

	@Override
	public String toString() {
		return "FacadeModelMenu [id=" + id + ", modelid=" + modelid + ", menuid=" + menuid + "]";
	}
    
    
}