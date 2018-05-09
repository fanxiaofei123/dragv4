package org.com.drag.model;

import java.io.Serializable;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: Role

 * @Description: 角色实体

 * @author: jiaonanyue

 * @date: 2016年11月19日 上午11:24:25
 */
public class Role implements Serializable{
	
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -5205683760642808005L;

	private Integer id;

    private String name;

    private Byte seq;

    private String description;

    private Byte status;

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

    public Byte getSeq() {
        return seq;
    }

    public void setSeq(Byte seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}