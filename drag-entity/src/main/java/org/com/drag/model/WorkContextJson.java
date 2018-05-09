package org.com.drag.model;

import java.io.Serializable;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkContextJson

 *   接收前端传过来的连接信息实体
 *   
 * @Description: TODO [{"ConnectionId":"con_22","PageSourceId":"state_start1","PageTargetId":"state_flow2",
 * "SourceText":"开始","TargetText":"流程","SourceAnchor":"RightMiddle","TargetAnchor":"LeftMiddle"}]
 * &[{"BlockId":"state_start1","BlockContent":"开始","BlockX":300,"BlockY":106,"Name":"node1"},
 * {"BlockId":"state_flow2","BlockContent":"流程","BlockX":610,"BlockY":114,"Name":"node2"}]

 * @author: jiaonanyue

 * @date: 2016年10月28日 上午10:58:31
 */
public class WorkContextJson implements Serializable{

	/**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 8948649009150747341L;
	private String ConnectionId;
	private String PageSourceId;
	private String PageTargetId;
	private String SourceText;
	private String TargetText;
	private String SourceAnchor;
	private String TargetAnchor;
	
	
	
	public WorkContextJson(){
		
	}
	public String getConnectionId() {
		return ConnectionId;
	}
	public void setConnectionId(String connectionId) {
		ConnectionId = connectionId;
	}
	public String getPageSourceId() {
		return PageSourceId;
	}
	public void setPageSourceId(String pageSourceId) {
		PageSourceId = pageSourceId;
	}
	public String getPageTargetId() {
		return PageTargetId;
	}
	public void setPageTargetId(String pageTargetId) {
		PageTargetId = pageTargetId;
	}
	public String getSourceText() {
		return SourceText;
	}
	public void setSourceText(String sourceText) {
		SourceText = sourceText;
	}
	public String getTargetText() {
		return TargetText;
	}
	public void setTargetText(String targetText) {
		TargetText = targetText;
	}
	public String getSourceAnchor() {
		return SourceAnchor;
	}
	public void setSourceAnchor(String sourceAnchor) {
		SourceAnchor = sourceAnchor;
	}
	public String getTargetAnchor() {
		return TargetAnchor;
	}
	public void setTargetAnchor(String targetAnchor) {
		TargetAnchor = targetAnchor;
	}
	@Override
	public String toString() {
		return "WorkContextJson [ConnectionId=" + ConnectionId + ", PageSourceId=" + PageSourceId + ", PageTargetId="
				+ PageTargetId + ", SourceText=" + SourceText + ", TargetText=" + TargetText + ", SourceAnchor="
				+ SourceAnchor + ", TargetAnchor=" + TargetAnchor + "]";
	}
	
	
	
	
}
