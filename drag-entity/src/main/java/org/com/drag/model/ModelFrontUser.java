package org.com.drag.model;

import java.util.Date;

/**
 * 扩展{@link FacadeModel}，将facade_model_record和facade_model合并在一起之后的结果
 * */
public class ModelFrontUser extends FacadeModel {

	private static final long serialVersionUID = -110529486454343824L;
	
	private int bid;//facade_model_record表的id
	private Date createTime;
	private String updateUrl;
	private String downUrl;
	private int status;//对应facade_model_record表中的type
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
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
		this.updateUrl = updateUrl;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
