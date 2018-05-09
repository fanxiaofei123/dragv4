package org.com.drag.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.StringUtils;
import org.com.drag.model.*;
import org.com.drag.service.*;
import org.com.drag.web.util.LoadDatabaseLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: ModelAttributeController

 * @Description: 模型属性Controller

 * @author: jiaonanyue

 * @date: 2016年11月7日 下午4:38:53
 */
@Controller
@RequestMapping("/drag/modelattri")
public class ModelAttributeController {
	
	@Autowired
	private ModelAttributeService modelAttributeService;
	@Autowired
	private DatabaseConnectService databaseConnectService;
	@Autowired
	private DatabaseTypeService databaseTypeService;
	@Autowired
	private ReadResourceService<ResourceInfo> readResourceService;
	/**
	 * 通过条件查询模型属性
	 * @param modelAttribute
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult getModelAttrbute(Model model,ModelAttribute modelAttribute,HttpSession session,ModelMap modelMap){

		User user = (User)session.getAttribute(Constants.USER_KEY);

		//拿到所有连接名，初始化连接名下拉框。
		List<DatabaseConnect> databaseConnects = databaseConnectService.selectByUserID(user.getId());
		String connNames = "";
		String formatConnNames = "";
//		String tables = "";
//		String formatTables = "";
//		int size = databaseConnects.size();
		if(databaseConnects.size() != 0){
			for (DatabaseConnect databaseConnect : databaseConnects) {
				if(databaseConnect.getDbConEnable()){
					connNames += databaseConnect.getDbConName() + ",";
				}
			}
			formatConnNames = connNames.substring(0,connNames.length()-1);
//			DatabaseConnect connect = new DatabaseConnect();
//			connect.setDbConName(formatConnNames.split(",")[0]);
//			connect.setDbConUserId(user.getId());
//			DatabaseConnect result = databaseConnectService.findByUserIdAndConnName(connect);
//			String databaseType = databaseTypeService.selectByPrimaryKey(result.getDbConTypeId()).getDbTypeName();
//
//			ResourceInfo resourceInfo = new ResourceInfo();
//			resourceInfo.setPassWord(result.getDbConPassword());
//			resourceInfo.setDatabaseName(result.getDbConDbname());
//			resourceInfo.setType(databaseType);
//			resourceInfo.setHostPort(result.getDbConPort().toString());
//			resourceInfo.setHostIp(result.getDbConIp());
//			resourceInfo.setUserName(result.getDbConUser());
//			List<String> tableNames = null;
//			try {
//				tableNames = readResourceService.getTableNames(resourceInfo);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if(tableNames != null){
//				for (String tableName : tableNames) {
//					tables += tableName + ",";
//				}
//				formatTables = tables.substring(0,tables.length()-1);
//			}
		}
		List<ModelAttribute> ModelAttributeList = modelAttributeService.getModelAttribute(modelAttribute);

		model.addAttribute("ModelAttributeList", ModelAttributeList);
		Map<String,String> map = new HashMap<String,String>();
		map.put("connNames",formatConnNames);
//		map.put("tables",formatTables);
		String databaseJson = new Gson().toJson(map);
		ResponseResult responseResult = new ResponseResult(200,databaseJson,ModelAttributeList);
		return responseResult;
	}

	@RequestMapping(value = "loadTables", method = RequestMethod.POST)
	@ResponseBody
	public List<String> loadTables(HttpSession session,@RequestParam String connName){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		DatabaseConnect databaseConnect = new DatabaseConnect();
		databaseConnect.setDbConName(connName);
		databaseConnect.setDbConUserId(user.getId());
		DatabaseConnect result = databaseConnectService.findByUserIdAndConnName(databaseConnect);
		String databaseType = databaseTypeService.selectByPrimaryKey(result.getDbConTypeId()).getDbTypeName();

		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setPassWord(result.getDbConPassword());
		resourceInfo.setDatabaseName(result.getDbConDbname());
		resourceInfo.setType(databaseType);
		resourceInfo.setHostPort(result.getDbConPort().toString());
		resourceInfo.setHostIp(result.getDbConIp());
		resourceInfo.setUserName(result.getDbConUser());
		List<String> tableNames = null;
		try {
			tableNames = readResourceService.getTableNames(resourceInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return tableNames;
		}
		return tableNames;
	}

	@RequestMapping(value = "LoadEnableConnNames", method = RequestMethod.GET)
	@ResponseBody
	public String LoadEnableConnNames(HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		List<DatabaseConnect> databaseConnects = databaseConnectService.selectByUserID(user.getId());
		String connNames = "";
		String formatConnNames = "";
		for (DatabaseConnect connect: databaseConnects) {
			if(connect.getDbConEnable()){
				connNames += connect.getDbConName() + ",";
			}
		}
		if(StringUtils.hasText(connNames)){
			formatConnNames = connNames.substring(0,connNames.length()-1);
		}
		return formatConnNames;
	}

	@RequestMapping(value = "savelink", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult saveLink(ResourceInfo resourceInfo, HttpSession session){
		String msg = "";
		ResponseResult responseResult;
		User user = (User) session.getAttribute(Constants.USER_KEY);

		//Ip和端口的后端验证
		String hostIp = resourceInfo.getHostIp();
		String hostPort = resourceInfo.getHostPort();
		String connName = resourceInfo.getConnectName();

		boolean isIp = LoadDatabaseLinkUtil.isIp(hostIp);
		boolean isPort = LoadDatabaseLinkUtil.isPort(hostPort);

		if(!StringUtils.hasText(connName)){
			msg = "连接名不能为空！";
			responseResult = new ResponseResult(200,msg);
			return responseResult;
		}
		if(!isIp){
			msg = "请输入正确格式的ip地址！";
			responseResult = new ResponseResult(200,msg);
			return responseResult;
		}
		if(!isPort){
			msg = "请输入正确格式的端口号！";
			responseResult = new ResponseResult(200,msg);
			return responseResult;
		}

		DatabaseConnect databaseConnect = new DatabaseConnect();
		databaseConnect.setDbConDbname(resourceInfo.getDatabaseName());
		databaseConnect.setDbConEnable(true);
		databaseConnect.setDbConIp(resourceInfo.getHostIp());
		databaseConnect.setDbConPort(Integer.parseInt(resourceInfo.getHostPort().trim()));
		databaseConnect.setDbConName(resourceInfo.getConnectName());
		databaseConnect.setDbConPassword(resourceInfo.getPassWord());
		databaseConnect.setDbConUser(resourceInfo.getUserName());
		databaseConnect.setDbConUserId(user.getId());

		/**
		 * 判断访问的源数据类型
		 */
		if("mysql".equals(resourceInfo.getType())){
			databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_MYSQL_ID);
		}
		if("oracle".equals(resourceInfo.getType())){
			databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_ORACLE_ID);
		}
		if("hive".equals(resourceInfo.getType())){
			databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_HIVE_ID);
		}

		/**
		 * 判断要保存的连接名是否已经存在,如果存在，将错误信息信息返回到前台。
		 */
		int countForConnName = databaseConnectService.selectByUserIdAndConnName(databaseConnect);
		int code = 0;
		if(countForConnName > 0){
			msg = "连接名已存在，请使用其他的连接名！";
			responseResult = new ResponseResult(200,msg);
			return responseResult;
		}
		int insertResult = databaseConnectService.insert(databaseConnect);
		if(insertResult != 0){
			code = 200;
		}
		responseResult = new ResponseResult(code,msg);
		return responseResult;
	}

	@RequestMapping(value = "LoadConnNames", method = RequestMethod.GET)
	@ResponseBody
	public String loadConnNames(HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		List<DatabaseConnect> databaseConnects = databaseConnectService.selectByUserID(user.getId());
		String connNames = "";
		String formatConnNames = "";
		for (DatabaseConnect connect: databaseConnects) {
			connNames += connect.getDbConName() + ",";
		}
		if(StringUtils.hasText(connNames)){
			formatConnNames = connNames.substring(0,connNames.length()-1);
		}
		return formatConnNames;
	}
}
