package org.com.drag.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.drag.common.util.Constants;
import org.com.drag.dao.DataModelMapper;
import org.com.drag.dao.ModelTrainedMapper;
import org.com.drag.model.DataModel;
import org.com.drag.model.Model;
import org.com.drag.model.ModelTrained;
import org.com.drag.model.User;
import org.com.drag.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.Context;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import javax.xml.ws.spi.http.HttpContext;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: DataModelServiceImpl

 * @Description: 数据模型实现类

 * @author: jiaonanyue

 * @date: 2016年10月25日 下午4:18:55
 */
@Service
public class DataModelServiceImpl extends BaseServiceImpl<DataModel> implements DataModelService{

	@Autowired
	private DataModelMapper  dataModelDao;
	@Autowired
	private ModelTrainedMapper modelTrainedMapper;
	
	

	@Override
	public List<DataModel> selectByDataModel(DataModel dataModel) {

		return dataModelDao.selectByDataModel(dataModel);
	}



	@Override
	public Map<String, List<DataModel>> selectDataModel() {
		List<DataModel> dataModelList = dataModelDao.selectByDataModel(null);
		List<DataModel> dataModelImport = new ArrayList<DataModel>();
		List<DataModel> dataModelExport = new ArrayList<DataModel>();
		List<DataModel> dataModelTransfer = new ArrayList<DataModel>();
		List<DataModel> dataModelAttribution = new ArrayList<DataModel>();
		List<DataModel> dataModelClassification = new ArrayList<DataModel>();
		List<DataModel> dataModelClustering = new ArrayList<DataModel>();
		List<DataModel> dataModelPredict = new ArrayList<DataModel>();
		List<DataModel> dataModelRegression = new ArrayList<DataModel>();
		List<DataModel> dataModelPerformance = new ArrayList<DataModel>();
		List<DataModel> dataModelText = new ArrayList<DataModel>();
		List<DataModel> dataModelAssociate = new ArrayList<DataModel>();
		List<DataModel> dataModelTrained = new ArrayList<DataModel>();
		List<DataModel> dataModelCustom = new ArrayList<DataModel>();
		List<DataModel> dataModelAbnormal = new ArrayList<DataModel>();

		if(dataModelList != null && dataModelList.size() > 0){
			for(DataModel dataModel :dataModelList){
				if(dataModel.getNameEnglish() != null){
					dataModel.setNameRmNull(dataModel.getNameEnglish().replaceAll("\\s*", ""));
				}
				
				if(dataModel.getPidName().equals("import-simple")){
					dataModelImport.add(dataModel);
				}
                if(dataModel.getPidName().equals("export-simple")){
                	dataModelExport.add(dataModel);
				}
                if(dataModel.getPidName().equals("transfer-simple")){
                	dataModelTransfer.add(dataModel);
				}
                if(dataModel.getPidName().equals("attribution-simple")){
                	dataModelAttribution.add(dataModel);
                }
                if(dataModel.getPidName().equals("classification-simple")){
                	dataModelClassification.add(dataModel);
                }
				if(dataModel.getPidName().equals("clustering-simple")){
					dataModelClustering.add(dataModel);
				}
				if (dataModel.getPidName().equalsIgnoreCase("predict-simple")) {
					dataModelPredict.add(dataModel);
				}
				if(dataModel.getPidName().equals("regression-simple")){
					dataModelRegression.add(dataModel);
				}
				if(dataModel.getPidName().equals("performance-simple")){
					dataModelPerformance.add(dataModel);
				}
				if(dataModel.getPidName().equals("abnormal-simple")){
					dataModelAbnormal.add(dataModel);
				}
				if(dataModel.getPidName().equals("text-simple")){
					dataModelText.add(dataModel);
				}
				if(dataModel.getPidName().equals("associate-simple")){
					dataModelAssociate.add(dataModel);
				}
				if (dataModel.getPidName().equals("trained-simple")){
					ModelTrained modelTrained = new ModelTrained();
					HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
					HttpSession session = request.getSession();
					User user= (User) session.getAttribute(Constants.USER_KEY);
					modelTrained.setModelTrainedStatus(0);
					modelTrained.setUserId(user.getId());
					List<ModelTrained> modelTrainedList=modelTrainedMapper.selectAll(modelTrained);
					for (ModelTrained trained : modelTrainedList) {
						DataModel dataModel1= new DataModel();
						dataModel1.setId(dataModel.getId());
						dataModel1.setName(trained.getModelTrainedName());
						dataModel1.setCreateTime(trained.getModelTrainedUpdateTime());
						dataModel1.setFlowDetails(dataModel.getFlowDetails());
						dataModel1.setNameEnglish(dataModel.getNameEnglish());
						dataModel1.setRightNumber(dataModel.getRightNumber());
						dataModel1.setNameRmNull(dataModel.getNameRmNull());
						dataModelTrained.add(dataModel1);
					}
				}
				if(dataModel.getPidName().equals("custom-simple")){
					dataModelCustom.add(dataModel);
				}

			}
		}
		
		
		Map<String, List<DataModel>> dataModelMap = new HashMap<String,List<DataModel>>();
		dataModelMap.put("dataModelImport", dataModelImport);
		dataModelMap.put("dataModelExport", dataModelExport);
		dataModelMap.put("dataModelTransfer", dataModelTransfer);
		dataModelMap.put("dataModelAttribution", dataModelAttribution);
		dataModelMap.put("dataModelClassification", dataModelClassification);
		dataModelMap.put("dataModelClustering", dataModelClustering);
		dataModelMap.put("dataModelPredict", dataModelPredict);
		dataModelMap.put("dataModelRegression",dataModelRegression);
		dataModelMap.put("dataModelPerformance",dataModelPerformance);
		dataModelMap.put("dataModelText",dataModelText);
		dataModelMap.put("dataModelAssociate",dataModelAssociate);
		dataModelMap.put("dataModelTrained",dataModelTrained);
		dataModelMap.put("dataModelCustom",dataModelCustom);
		dataModelMap.put("dataModelAbnormal",dataModelAbnormal);

		return dataModelMap;
	}

	@Override
	public Integer selectByName(String dataModelName) {
		return dataModelDao.selectByName(dataModelName);
	}

}
