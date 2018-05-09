package org.com.drag.model;

import java.io.Serializable;

public class FacadeUploadInfo implements Serializable{
    /**

     * @fieldName: serialVersionUID

     * @fieldType: long

     * @Description: TODO

     */
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer flowid;

    private String telnum;

    private String purpose;

    private String name;

    private String appexplain;

    private String apiexplain;

    /**
     * 模型内容
     */
    private String testresult;

    /**
     * 模型类型
     */
    private String modeltype;
    
    /**
     * 模型上传文件地址
     */
    private String modelUrl;
    
    
    private String currentDir;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum == null ? null : telnum.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAppexplain() {
        return appexplain;
    }

    public void setAppexplain(String appexplain) {
        this.appexplain = appexplain == null ? null : appexplain.trim();
    }

    public String getApiexplain() {
        return apiexplain;
    }

    public void setApiexplain(String apiexplain) {
        this.apiexplain = apiexplain == null ? null : apiexplain.trim();
    }

    public String getTestresult() {
        return testresult;
    }

    public void setTestresult(String testresult) {
        this.testresult = testresult == null ? null : testresult.trim();
    }

	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
	}

	@Override
	public String toString() {
		return "FacadeUploadInfo [id=" + id + ", flowid=" + flowid + ", telnum=" + telnum + ", purpose=" + purpose
				+ ", name=" + name + ", appexplain=" + appexplain + ", apiexplain=" + apiexplain + ", testresult="
				+ testresult + ", modeltype=" + modeltype + ", modelUrl=" + modelUrl + ", currentDir=" + currentDir
				+ "]";
	}



    
    
    
}