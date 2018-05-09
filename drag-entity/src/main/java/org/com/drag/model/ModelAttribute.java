package org.com.drag.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: ModelAttribute

 * @Description: 模型属性-值

 * @author: jiaonanyue

 * @date: 2016年11月7日 下午3:50:28
 */
public class ModelAttribute implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -4194517205073152971L;

	private Integer id;

	/**
	 * 模型id
	 */
    private Integer modelId;

    private Integer trainedModelId;

    /**
     * 模型属性
     */
    private String mattribute;
    private String configId;
    /**
     * 模型属性对应的值
     */
    private String mvalue;
    /**
     * 输入框状态 0:代表隐藏 1：代表input 2:代表下拉框  3：代表上传框(input输入)  4:单选框 5：代表上传框(output 输出)
     */
    private Integer type;

    /**
     * 1 为输入 2为输出 默认为 0 不是
     */
    /**
     * 算子参数的提示信息
     */
    private String common;
    /**
     *                         var configDataSource = configObj[i].data[j].configDataSource
     var selectedDataSource = configObj[i].data[j].configSelectedDataSource
     var configDataType = configObj[i].data[j].configDataType
     var configSelectedDataType = configObj[i].data[j].configSelectedDataType
     var configPreparaedScheme = configObj[i].data[j].configPreparaedScheme
     var configSerachName = configObj[i].data[j].configSerachName
     var configSelectedScheme = configObj[i].data[j].configVal
     */

    private String configSelectVal;
    private String configDataSource;
    private String configSelectedDataSource;
    private String configDataType;
    private String configSelectedDataType;
    private String configPreparaedScheme;
    private String configSerachName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getConfigDataSource() {
        return configDataSource;
    }

    public void setConfigDataSource(String configDataSource) {
        this.configDataSource = configDataSource;
    }

    public String getConfigSelectedDataSource() {
        return configSelectedDataSource;
    }

    public void setConfigSelectedDataSource(String configSelectedDataSource) {
        this.configSelectedDataSource = configSelectedDataSource;
    }

    public String getConfigDataType() {
        return configDataType;
    }

    public void setConfigDataType(String configDataType) {
        this.configDataType = configDataType;
    }

    public String getConfigSelectedDataType() {
        return configSelectedDataType;
    }

    public void setConfigSelectedDataType(String configSelectedDataType) {
        this.configSelectedDataType = configSelectedDataType;
    }

    public String getConfigPreparaedScheme() {
        return configPreparaedScheme;
    }

    public void setConfigPreparaedScheme(String configPreparaedScheme) {
        this.configPreparaedScheme = configPreparaedScheme;
    }

    public String getConfigSerachName() {
        return configSerachName;
    }

    public void setConfigSerachName(String configSerachName) {
        this.configSerachName = configSerachName;
    }

    private Integer tmpConfigType = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getMattribute() {
        return mattribute;
    }

    public Integer getTrainedModelId() {
        return trainedModelId;
    }

    public void setTrainedModelId(Integer trainedModelId) {
        this.trainedModelId = trainedModelId;
    }

    public void setMattribute(String mattribute) {

        this.mattribute = mattribute == null ? null : mattribute.trim();
    }

    public String getMvalue() {
        mvalue = StringUtils.isNotBlank(configSelectVal) ? configSelectVal : mvalue;
        return mvalue;
    }

    public void setMvalue(String mvalue) {
        this.mvalue = mvalue == null ? null : mvalue.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    //前台不对应参数处理


    public void setConfigName(String mattribute){
        this.mattribute = mattribute;
    }


    public  void  setConfigType(Integer type){
        this.type = type;
    }


    public void  setConfigVal(String mvalue){
        this.mvalue = mvalue;
    }

    public Integer getTmpConfigType() {
        return tmpConfigType;
    }

    public void setTmpConfigType(Integer tmpConfigType) {
        this.tmpConfigType = tmpConfigType;
    }

    public String getConfigSelectVal() {
        return configSelectVal;
    }

    public void setConfigSelectVal(String configSelectVal) {
        this.configSelectVal = configSelectVal;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    @Override
	public String toString() {
		return "ModelAttribute [id=" + id + ", modelId=" + modelId + ", mattribute=" + mattribute + ", mvalue=" + mvalue
				+ ", type=" + type +", common=" + common + "]";
	}
    
    
}