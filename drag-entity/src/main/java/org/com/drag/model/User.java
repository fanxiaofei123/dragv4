package org.com.drag.model;

import org.com.drag.common.util.Constants;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: User

 * @Description: 用户实体

 * @author: jiaonanyue

 * @date: 2016年10月19日 下午6:06:28
 */
public class User implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 7251472378922223412L;

	
	private Integer id;
	
	private String ids;

	/*
	 * 用户登录用户名
	 */
    private String loginname;

    /*
     * 用户名
     */
    private String name;

    /*
     * 登录密码
     */
    private String password;

    /*
     * 用户类型
     */
    private Byte usertype;

    /*
     * 用户状态
     */
    private Byte status;

    /*
     * 创建时间
     * 
     */
    private Date createdate;
    private String createdates;

    /*
     * 用户手机号码
     */
    private String email;


    private String token;
    
    /**
     * hdfs保存路径
     */
    private String hdfsUrl;

    //上传到hdfs的提醒
    private Integer tips;

    /**
     * 性别  1：男 2：女
     */
    private String userSex;
    private String userSexName;

    /**
     * 学历 0：博士 1：硕士 2：本科 3：专科 4：高中
     */
    private String education;
    private String educationName;

    /**
     * 学校
     */
    private String school;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 个人描述
     */
    private String describe;
    
    /**
     * 角色：0：使用者1：开发者  2：管理者
     */
    private Integer developer;
    private String developerName;

    /**
     * 结构id
     */
    private Integer organizationId;
	/**
	 * 密码加密盐
	 */
	private String salt;
	
	private List<Role> rolesList;

	private String organizationName;

	private String roleIds;
	
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getUsertype() {
        return usertype;
    }

    public void setUsertype(Byte usertype) {
        this.usertype = usertype;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email == null ? null : email.trim();
    }

	public String getHdfsUrl() {
		return hdfsUrl;
	}
	public String getHdfsUrl(User user) {
		return Constants.getHdfsUrl().toString()+user.getHdfsUrl();
	}
	public void setHdfsUrl(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTips() {
        return tips;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

	public String getCreatedates() {
		return createdates;
	}

	public void setCreatedates(String createdates) {
		this.createdates = createdates;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Integer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Integer developer) {
		this.developer = developer;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserSexName() {
		return userSexName;
	}

	public void setUserSexName(String userSexName) {
		this.userSexName = userSexName;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Role> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ids=" + ids + ", loginname=" + loginname + ", name=" + name + ", password="
				+ password + ", usertype=" + usertype + ", status=" + status + ", createdate=" + createdate
				+ ", createdates=" + createdates + ", email=" + email + ", token=" + token + ", hdfsUrl=" + hdfsUrl
				+ ", tips=" + tips + ", userSex=" + userSex + ", userSexName=" + userSexName + ", education="
				+ education + ", educationName=" + educationName + ", school=" + school + ", occupation=" + occupation
				+ ", describe=" + describe + ", developer=" + developer + ", developerName=" + developerName
				+ ", organizationId=" + organizationId + ", salt=" + salt + ", rolesList=" + rolesList
				+ ", organizationName=" + organizationName + ", roleIds=" + roleIds + "]";
	}



	

   
}