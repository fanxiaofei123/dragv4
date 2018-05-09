package org.com.drag.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: WorkBlockJson

 *   接收前端传过来的模块实体
 *   
 * @Description: [{"BlockId":"state_start1","BlockContent":"开始","BlockX":300,"BlockY":106,"Name":"node1"},
 * {"BlockId":"state_flow2","BlockContent":"流程","BlockX":610,"BlockY":114,"Name":"node2"}]

 * @author: jiaonanyue

 * @date: 2016年11月1日 上午11:05:50
 */
public class WorkBlockJson implements Serializable {

	
	/**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = 4135694434444303710L;
	
	private String BlockId;
	private String BlockContent;
	private String BlockX;
	private String BlockY;
	private String Name;
	private String XmlStart; //连接开始
	private String XmlEnd;  //连接结束
	private List<ModelAttribute> args = new ArrayList();

	
	
	/**
	 * 各个模块的参数
	 */
	//ReadCS的参数
	private String ReadCSVinputPath;
	private String ReadCSdelimiter=",";
	private String ReadCSheader;
	private String ReadCSinferSchema;
	//fieldExtract的参数
	private String fieldExtractinput;
	private String fieldExtractOutput;
	private String fieldExtractField;
	private String fieldExtractdelimiter=",";
	//kmeans的参数
	private String kmeansdata;
	private int kmeansk;
	private int kmeansmaxIterations;
	private String kmeansruns;
	private String kmeansinitializationmoel;
	private String kmeansseed;
	private String kmeansinitializationsteps;
	private String kmeansinitialmoel;
	private Float kmeansepsilon;
	//applymodel的参数
	private String applymodelfeature;
	
	
	public WorkBlockJson(){
		
	}
	public String getBlockId() {
		return BlockId;
	}
	public void setBlockId(String blockId) {
		BlockId = blockId;
	}
	public String getBlockContent() {
		return BlockContent;
	}
	public void setBlockContent(String blockContent) {
		BlockContent = blockContent;
	}
	public String getBlockX() {
		return BlockX;
	}
	public void setBlockX(String blockX) {
		BlockX = blockX;
	}
	public String getBlockY() {
		return BlockY;
	}
	public void setBlockY(String blockY) {
		BlockY = blockY;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getXmlStart() {
		return XmlStart;
	}
	public void setXmlStart(String xmlStart) {
		XmlStart = xmlStart;
	}
	public String getXmlEnd() {
		return XmlEnd;
	}
	public void setXmlEnd(String xmlEnd) {
		XmlEnd = xmlEnd;
	}
	public String getReadCSVinputPath() {
		return ReadCSVinputPath;
	}
	public void setReadCSVinputPath(String readCSVinputPath) {
		ReadCSVinputPath = readCSVinputPath;
	}
	public String getReadCSdelimiter() {
		return ReadCSdelimiter;
	}
	public void setReadCSdelimiter(String readCSdelimiter) {
		ReadCSdelimiter = readCSdelimiter;
	}
	public String getReadCSheader() {
		return ReadCSheader;
	}
	public void setReadCSheader(String readCSheader) {
		ReadCSheader = readCSheader;
	}
	public String getReadCSinferSchema() {
		return ReadCSinferSchema;
	}
	public void setReadCSinferSchema(String readCSinferSchema) {
		ReadCSinferSchema = readCSinferSchema;
	}
	public String getFieldExtractinput() {
		return fieldExtractinput;
	}
	public void setFieldExtractinput(String fieldExtractinput) {
		this.fieldExtractinput = fieldExtractinput;
	}
	public String getFieldExtractOutput() {
		return fieldExtractOutput;
	}
	public void setFieldExtractOutput(String fieldExtractOutput) {
		this.fieldExtractOutput = fieldExtractOutput;
	}
	public String getFieldExtractField() {
		return fieldExtractField;
	}
	public void setFieldExtractField(String fieldExtractField) {
		this.fieldExtractField = fieldExtractField;
	}
	public String getFieldExtractdelimiter() {
		return fieldExtractdelimiter;
	}
	public void setFieldExtractdelimiter(String fieldExtractdelimiter) {
		this.fieldExtractdelimiter = fieldExtractdelimiter;
	}
	public String getKmeansdata() {
		return kmeansdata;
	}
	public void setKmeansdata(String kmeansdata) {
		this.kmeansdata = kmeansdata;
	}
	public int getKmeansk() {
		return kmeansk;
	}
	public void setKmeansk(int kmeansk) {
		this.kmeansk = kmeansk;
	}
	public int getKmeansmaxIterations() {
		return kmeansmaxIterations;
	}
	public void setKmeansmaxIterations(int kmeansmaxIterations) {
		this.kmeansmaxIterations = kmeansmaxIterations;
	}
	public String getKmeansruns() {
		return kmeansruns;
	}
	public void setKmeansruns(String kmeansruns) {
		this.kmeansruns = kmeansruns;
	}
	public String getKmeansinitializationmoel() {
		return kmeansinitializationmoel;
	}
	public void setKmeansinitializationmoel(String kmeansinitializationmoel) {
		this.kmeansinitializationmoel = kmeansinitializationmoel;
	}
	public String getKmeansseed() {
		return kmeansseed;
	}
	public void setKmeansseed(String kmeansseed) {
		this.kmeansseed = kmeansseed;
	}
	public String getKmeansinitializationsteps() {
		return kmeansinitializationsteps;
	}
	public void setKmeansinitializationsteps(String kmeansinitializationsteps) {
		this.kmeansinitializationsteps = kmeansinitializationsteps;
	}
	public String getKmeansinitialmoel() {
		return kmeansinitialmoel;
	}
	public void setKmeansinitialmoel(String kmeansinitialmoel) {
		this.kmeansinitialmoel = kmeansinitialmoel;
	}
	public Float getKmeansepsilon() {
		return kmeansepsilon;
	}
	public void setKmeansepsilon(Float kmeansepsilon) {
		this.kmeansepsilon = kmeansepsilon;
	}
	public String getApplymodelfeature() {
		return applymodelfeature;
	}
	public void setApplymodelfeature(String applymodelfeature) {
		this.applymodelfeature = applymodelfeature;
	}

	public List getArgs() {
		return args;
	}

	public void setArgs(List args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "WorkBlockJson [BlockId=" + BlockId + ", BlockContent=" + BlockContent + ", BlockX=" + BlockX
				+ ", BlockY=" + BlockY + ", Name=" + Name + ", XmlStart=" + XmlStart + ", XmlEnd=" + XmlEnd
				+ ", ReadCSVinputPath=" + ReadCSVinputPath + ", ReadCSdelimiter=" + ReadCSdelimiter + ", ReadCSheader="
				+ ReadCSheader + ", ReadCSinferSchema=" + ReadCSinferSchema + ", fieldExtractinput=" + fieldExtractinput
				+ ", fieldExtractOutput=" + fieldExtractOutput + ", fieldExtractField=" + fieldExtractField
				+ ", fieldExtractdelimiter=" + fieldExtractdelimiter + ", kmeansdata=" + kmeansdata + ", kmeansk="
				+ kmeansk + ", kmeansmaxIterations=" + kmeansmaxIterations + ", kmeansruns=" + kmeansruns
				+ ", kmeansinitializationmoel=" + kmeansinitializationmoel + ", kmeansseed=" + kmeansseed
				+ ", kmeansinitializationsteps=" + kmeansinitializationsteps + ", kmeansinitialmoel="
				+ kmeansinitialmoel + ", kmeansepsilon=" + kmeansepsilon + ", applymodelfeature=" + applymodelfeature
				+ "]";
	}
	
	
	
	
	
}
