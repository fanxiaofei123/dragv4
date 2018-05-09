package org.com.drag.model;

import java.io.Serializable;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: UserRole

 * @Description: 用户角色中间表

 * @author: jiaonanyue

 * @date: 2016年11月19日 上午11:25:49
 */
public class UserRole implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -3335277440197924890L;

	private Long id;

    private Long userId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}