package org.com.drag.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Copyright © 2016优易数据. All rights reserved.
 *
 * @ClassName: CalculationHistory
 * @Description: TODO
 * @author: jiaonanyue
 * @date: 2016年11月22日 下午5:26:21
 */
public class CalculationHistory implements Serializable {
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 5755615063688684847L;

    private Integer id;

    private Integer userid;
    private String email;
    private String way;

    private Integer status; // -1：失败  1：运行中  2：成功

    private String resason;
    private String jobId;
    private Date createTime;
    private Integer flowId;
    
    private String frontusername;//前段用户名称
    
    
    /**
	 * 模型id
	 */
    private Integer modelId;


    /**
     * 文件上传到hdfs路径
     */
    private String updateUrl;

    /**
     * 生成的结果路径
     */
    private String downUrl;
    
    private String createTimes;
    private String statusName;
    
    private String userNmae;
    
    
    private String startTime;
    private String endTime;
    
    
    private Long[] idList;
    
    /**
     * 预测名称
     * */
    private String name;
    
    


	public Long[] getIdList() {
		return idList;
	}

	public void setIdList(Long[] idList) {
		this.idList = idList;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUserNmae() {
		return userNmae;
	}

	public void setUserNmae(String userNmae) {
		this.userNmae = userNmae;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way == null ? null : way.trim();
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResason() {
        return resason;
    }

    public void setResason(String resason) {
        this.resason = resason == null ? null : resason.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Date getCreateTime() {

        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @SuppressWarnings({ "unused", "deprecation" })
	public  String getFormatTime (){
        String formatTime = "暂无";
        if(createTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat();
            formatTime = createTime.toLocaleString();
        }
        return formatTime;
    }

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFrontusername() {
		return frontusername;
	}

	public void setFrontusername(String frontusername) {
		this.frontusername = frontusername;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	@Override
	public String toString() {
		return "CalculationHistory [id=" + id + ", userid=" + userid + ", email=" + email + ", way=" + way + ", status="
				+ status + ", resason=" + resason + ", jobId=" + jobId + ", createTime=" + createTime + ", flowId="
				+ flowId + ", frontusername=" + frontusername + ", modelId=" + modelId + ", updateUrl=" + updateUrl
				+ ", downUrl=" + downUrl + ", createTimes=" + createTimes + ", statusName=" + statusName + ", userNmae="
				+ userNmae + ", startTime=" + startTime + ", endTime=" + endTime + ", idList=" + Arrays.toString(idList)
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

   
}