package org.com.drag.web.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.StringUtils;
import org.com.drag.common.util.TreeCache;
import org.com.drag.model.*;
import org.com.drag.service.DatabaseConnectService;
import org.com.drag.service.DatabaseTypeService;
import org.com.drag.service.ReadResourceService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.bean.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: HdfsController

 * @Description: TODO

 * @author: jiaonanyue

 * @date: 2016年12月28日 下午3:55:54
 */
@Controller
@RequestMapping("drag/hdfs")
public class HdfsController {
	

	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Autowired
	 private IHdfsApi iHdfsApi;
	 @Autowired
     private DatabaseConnectService databaseConnectService;
	 @Autowired
     private DatabaseTypeService databaseTypeService;
	 @Autowired
     private ReadResourceService<ResourceInfo> readResourceService;
	/**
	 * hdfs文件下载
	 */
	@RequestMapping(value = "down",method = RequestMethod.GET)
    @ResponseBody
	public void DownFileFromHDFS(String hdfsUrl, HttpServletRequest request,
			HttpServletResponse response,HttpSession session)throws IOException {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
		FSDataInputStream is = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("multipart/form-data");
				String[] names = hdfsUrl.split("/");
				if (names.length > 0) {
					// 设置头部信息
					response.setHeader(
							"Content-disposition",
							"attachment;filename="
							+ URLEncoder.encode(names[names.length - 1], "UTF-8"));
				}
	       
	        if(!fileSystem.exists(new Path(hdfsUrl))){
	                logger.error("File does not exists.");
	                throw new FileNotFoundException("File["+hdfsUrl+"] does not exists.");
	        }
	        is = fileSystem.open(new Path(hdfsUrl));
			// 输出流
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024*1024];
			int bytesRead;
			while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			is.close();
			bos.close();
		} catch (Exception e) {
			 e.printStackTrace();
	         logger.error(e.getMessage());
		}finally {
			fileSystem.close();
			
        }
		
	}
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileConstruct",method = RequestMethod.GET)
    @ResponseBody
    public  List<FileDto> getUserFileConstruct(HttpSession session,String paths) {

    	
    	 User user =    (User)session.getAttribute(Constants.USER_KEY);
         List<FileDto> rootfileDtos =(List<FileDto>)TreeCache.getCache(user.getId() + "", null);
         String selectPath = null;
    	if(paths != null){
            if(paths.toString().equals("1")){
//            	selectPath = Constants.DATAS_SUFFIX+"/"+Constants.INPUT_DATAS;
                selectPath = Constants.DATAS_SUFFIX;
            }else{
//            	selectPath = Constants.DATAS_SUFFIX+"/"+Constants.OUTPUT_DATAS;
                selectPath = Constants.DATAS_SUFFIX;
            }

           // HdfsFileUtil hdfsUtils = new HdfsFileUtil();

           
            if(rootfileDtos == null){
            	 rootfileDtos = new ArrayList<>();
                List<FileInfo> fileStatusList = iHdfsApi.list(selectPath, user.getLoginname());
                
                List<FilefloderInfo> filefloderInfos = transtFileinfoToFileFloderInfo(fileStatusList);
                EncapsulationFile(filefloderInfos,user.getLoginname(),rootfileDtos);
                //整合使用
               /* 
                if(paths.toString().equals("1")){
                	 String xml = HttpXmlClient.get(MailAuthorSetting.HTTPCLINET_PATH+"?userid="+user.getId()); 
                     JSONArray jsonArray = JSONArray.fromObject(xml); 
          	 
           	      List<HdfsUrlModel> list =   (List<HdfsUrlModel>) JSONArray.toCollection(jsonArray,HdfsUrlModel.class);
           	      for (HdfsUrlModel receiptList:list) {
           	       System.out.println(receiptList.getHdfsPath());
           	       List<FileInfo> fileHdfsList = iHdfsApi.list(receiptList.getHdfsPath(), MailAuthorSetting.HDFS_USER);
                      
                      List<FilefloderInfo> filefloderHdfs = transtFileinfoToFileFloderInfo(fileHdfsList);
                      EncapsulationFile(filefloderHdfs,MailAuthorSetting.HDFS_USER,rootfileDtos);
           	       }
                   
                }*/
               
            }

    	}
    	 return rootfileDtos;
    }
    
    /**
     * 对查询出来的hdfs目录公共方法
     * @param filefloderInfos
     * @param Userroot
     * @param rootfileDtos
     * @return
     */
    public  List<FileDto> EncapsulationFile(List<FilefloderInfo> filefloderInfos,String Userroot,List<FileDto> rootfileDtos){
    	
          for (FilefloderInfo fileInfo : filefloderInfos) {
              FileDto fileDto = new FileDto();
              fileDto.setLevel(1);
              fileDto.setFilefloderInfo(fileInfo);
              rootfileDtos.add(fileDto);
                  getFile(fileDto,Userroot);
          }
    	
    	return rootfileDtos;
    }

    @RequestMapping(value = "getColumnScheme",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult getColumnScheme(String datasourceName,String modelStr,HttpSession session){
        //拿到字段的来源
        //拿到字段的类型
        //拿到可选择的字段
        User user = (User) session.getAttribute(Constants.USER_KEY);
        //statModel -> 数据源名
        Map<String,String> statModelToDataSource = new HashMap<>();
        //statModel -> 数据字段名称
        Map<String,String> statModelToDataType = new HashMap<>();
        //statModel_type -> 字段名
        Map<String,String> statModelTypeToScheme = new HashMap<>();
        Map<String,String> statModelToScheme = new HashMap<>();
        //在modelStr中剔除条件过滤的算子
        JSONArray jsonArr = JSONArray.parseArray(modelStr);
        Iterator<Object> iterator = jsonArr.iterator();
        while (iterator.hasNext()){
            Object object = iterator.next();
            JSONObject dataObj = (JSONObject)object;
            if(Integer.parseInt(dataObj.get("id").toString()) == 125){
                jsonArr.remove(object);
                break;
            }
        }
        modelStr = jsonArr.toJSONString();
        List<org.com.drag.model.Model> models = JSON.parseArray(modelStr, org.com.drag.model.Model.class);
        for(org.com.drag.model.Model model : models){
            //输入是csv的情况
            if(model.getId() == 1 || model.getId() == 56){
                String hdfsPath = "";
                boolean header = false;
                for(ModelAttribute modelAttribute : model.getData()){
                    //拿到数据源的名字
                    if(modelAttribute.getMattribute().equals("inputPath")){
                        if(StringUtils.hasText(modelAttribute.getMvalue())){
                            String[] split = modelAttribute.getMvalue().split("/");
                            statModelToDataSource.put(model.getBlockId(),split[split.length-1]);
                            hdfsPath = MailAuthorSetting.HADOOP_NAMENODE + modelAttribute.getMvalue();
                        }
                    }

                    if(modelAttribute.getMattribute().equals("header")){
                        header = Boolean.valueOf(modelAttribute.getMvalue().trim());
                    }
                }

                if(StringUtils.hasText(hdfsPath.trim())){
                    InputStream inputStream = null;
                    //拿到所有的字段名称
                    FileSystem fs = iHdfsApi.getFileSystem(user.getLoginname());
                    InputStreamReader reader;
                    BufferedReader br = null;
                    try {
                        inputStream = fs.open(new Path(hdfsPath));
                        reader = new InputStreamReader(inputStream);
                        String encoding = reader.getEncoding();
                        //拿到字符集的编码
                        br = new BufferedReader(new InputStreamReader(inputStream,encoding));
                        String data = br.readLine();
                        String schemes = "";
                        //读取第一行
                        if(header){
                            for(String scheme : data.split(",")){
                                schemes += scheme.substring(0,scheme.length()) + ",";
                            }
                        }else{
                            for(int i=0;i<data.split(",").length;i++){
                                schemes += "_c" + i + ",";
                            }
                        }
                        statModelToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),schemes.substring(0,schemes.length()-1));
                        statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_STRING",schemes.substring(0,schemes.length()-1));
                        statModelToDataType.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),"STRING");
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }finally {
                        try {
                            if(br != null){
                                br.close();
                            }
                            if(inputStream != null){
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage());
                        }
                    }
                }
            }

            if(model.getId() == 53){
                String tableName = "";
                String tableScheme = "";
                for(ModelAttribute modelAttribute : model.getData()){
                    //拿到数据源的名字
                    if(modelAttribute.getMattribute().equals("tableName")){
                        tableName = modelAttribute.getMvalue().split("\\|")[1];
                        if(!"null".equals(tableName)){
                            statModelToDataSource.put(model.getBlockId(),tableName);
                        }
                    }
                }
                if(!"null".equals(tableName)){
                    for(ModelAttribute modelAttribute : model.getData()){
                        //拿到数据源的字段
                        if(modelAttribute.getMattribute().equals("dbLinkName")){
                            String dbLinkName = modelAttribute.getMvalue().split("\\|")[1];
                            DatabaseConnect databaseConnect = new DatabaseConnect();
                            databaseConnect.setDbConName(dbLinkName);
                            databaseConnect.setDbConUserId(user.getId());
                            DatabaseConnect conn = databaseConnectService.findByUserIdAndConnName(databaseConnect);
                            DatabaseType databaseType = databaseTypeService.selectByPrimaryKey(conn.getDbConTypeId());
                            ResourceInfo resourceInfo = new ResourceInfo();
                            resourceInfo.setUserName(conn.getDbConUser());
                            resourceInfo.setHostIp(conn.getDbConIp());
                            resourceInfo.setHostPort(conn.getDbConPort().toString());
                            resourceInfo.setType(databaseType.getDbTypeName());
                            resourceInfo.setDatabaseName(conn.getDbConDbname());
                            resourceInfo.setPassWord(conn.getDbConPassword());
                            resourceInfo.setTableName(tableName);
                            resourceInfo.setConnectNmae(conn.getDbConName());
//                        System.out.println(resourceInfo);
                            String metaData = readResourceService.getMetaData(resourceInfo);
                            logger.info(metaData);

                            String[] schemeSplits = metaData.split("\\|");

                            for(String str : schemeSplits){
                                tableScheme += str.split(",")[0] + ",";
                            }
                            statModelToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),tableScheme.substring(0,tableScheme.length()-1));
                            Set<String> dbSchemeName = new HashSet<>();
                            String stringScheme = "";
                            String intScheme = "";
                            String booScheme = "";
                            String floatScheme = "";
                            String douScheme = "";
                            String bigScheme = "";
                            String dateScheme = "";
                            String timecheme = "";
                            String timestampScheme = "";
                            String longScheme = "";
                            for(String split : schemeSplits){
                                switch (split.split(",")[1].toUpperCase()){
                                    case "INT":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "VARCHAR":
                                        stringScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("STRING");
                                        break;
                                    case "CHAR":
                                        stringScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("STRING");
                                        break;
                                    case "TEXT":
                                        stringScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("STRING");
                                        break;
                                    case "INTEGER":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "TINYINT":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "SMALLINT":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "MEDIUMINT":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "BIT":
                                        booScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("BOOLEAN");
                                        break;
                                    case "BIGINT":
                                        intScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("INT");
                                        break;
                                    case "FLOAT":
                                        floatScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("FLOAT");
                                        break;
                                    case "DOUBLE":
                                        douScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("DOUBLE");
                                        break;
                                    case "DECIMAL":
                                        bigScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("BIGDECIMAL");
                                        break;
                                    case "DATE":
                                        dateScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("DATE");
                                        break;
                                    case "TIME":
                                        timecheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("TIME");
                                        break;
                                    case "DATETIME":
                                        timestampScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("TIMESTAMP");
                                        break;
                                    case "YEAR":
                                        dateScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("DATE");
                                        break;
                                    case "NUMBER":
                                        bigScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("BIGDECIMAL");
                                        break;
                                    case "VARCHAR2":
                                        stringScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("STRING");
                                        break;
                                    case "LONG":
                                        longScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("LONG");
                                        break;
                                    default:
                                        stringScheme += split.split(",")[0] + ",";
                                        dbSchemeName.add("STRING");
                                        break;
                                }
                            }
                            //每一个数据源中存储的不同的数据类型对应的字段名。
                            if(StringUtils.hasText(stringScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_STRING", stringScheme.substring(0,stringScheme.length()-1));
                            }
                            if(StringUtils.hasText(intScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_INT", intScheme.substring(0,intScheme.length()-1));
                            }
                            if(StringUtils.hasText(douScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DOUBLE", douScheme.substring(0,douScheme.length()-1));
                            }
                            if(StringUtils.hasText(booScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_BOOLEAN", booScheme.substring(0,booScheme.length()-1));
                            }
                            if(StringUtils.hasText(floatScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_FLOAT", floatScheme.substring(0,floatScheme.length()-1));
                            }
                            if(StringUtils.hasText(bigScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DECIMAL",bigScheme.substring(0,bigScheme.length()-1));
                            }
                            if(StringUtils.hasText(dateScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DATE",dateScheme.substring(0,dateScheme.length()-1));
                            }
                            if(StringUtils.hasText(timecheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_TIME",timecheme.substring(0,timecheme.length()-1));
                            }
                            if(StringUtils.hasText(timestampScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_TIMESTAMP",timestampScheme.substring(0,timestampScheme.length()-1));
                            }
                            if(StringUtils.hasText(longScheme)){
                                statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_LONG",longScheme.substring(0,longScheme.length()-1));
                            }

                            String dbSchemeNameStr = "";
                            for(String type : dbSchemeName){
                                dbSchemeNameStr += type + ",";
                            }
                            if(!"".equals(dbSchemeNameStr)){
                                dbSchemeNameStr = dbSchemeNameStr.substring(0,dbSchemeNameStr.length()-1);
                            }
                            statModelToDataType.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),dbSchemeNameStr);
                            logger.info(statModelToDataType.toString());
                        }
                    }
                }
            }
        }
        String schemes = statModelToScheme.get(datasourceName);
        String types = statModelToDataType.get(datasourceName);
//        List<String> otherStatModelKeys = new ArrayList<>();
//        for(String key : statModelTypeToScheme.keySet()){
//            if(key.indexOf(datasourceName) == -1){
//                otherStatModelKeys.add(key);
//            }
//        }
//        for(String otherKey : otherStatModelKeys){
//            statModelTypeToScheme.remove(otherKey);
//        }

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("schemes",schemes);
        columnMap.put("types",types);
        columnMap.put("statModelTypeToScheme",statModelTypeToScheme);
        String columnStr = JSON.toJSONString(columnMap);
        ResponseResult responseResult = new ResponseResult(200,columnStr);
        return responseResult;
    }


//    @RequestMapping(value = "getColumnScheme",method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseResult getColumnScheme(String modelStr, HttpSession session){
//        //拿到字段的来源
//        //拿到字段的类型
//        //拿到可选择的字段
//        User user = (User) session.getAttribute(Constants.USER_KEY);
//        //statModel -> 数据源名
//        Map<String,String> statModelToDataSource = new HashMap<>();
//        //statModel -> 数据字段名称
//        Map<String,String> statModelToDataType = new HashMap<>();
//        //statModel_type -> 字段名
//        Map<String,String> statModelTypeToScheme = new HashMap<>();
//        Set typeSet = new HashSet<String>();
//        Map<String,String> statModelToScheme = new HashMap<>();
//        List<org.com.drag.model.Model> models = JSON.parseArray(modelStr, org.com.drag.model.Model.class);
//        for(org.com.drag.model.Model model : models){
//            //输入是csv的情况
//            if(model.getId() == 1 || model.getId() == 56){
//                String hdfsPath = "";
//                for(ModelAttribute modelAttribute : model.getData()){
//                    //拿到数据源的名字
//                    if(modelAttribute.getMattribute().equals("inputPath")){
//                        String[] split = modelAttribute.getMvalue().split("/");
//                        statModelToDataSource.put(model.getBlockId(),split[split.length-1]);
//                        hdfsPath = MailAuthorSetting.HADOOP_NAMENODE + modelAttribute.getMvalue();
//                    }
//                }
//                InputStream inputStream = null;
//                //拿到所有的字段名称
//                FileSystem fs = iHdfsApi.getFileSystem(user.getLoginname());
//                try {
//                    inputStream = fs.open(new Path(hdfsPath));
//                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//                    //读取第一行
//                    String data = br.readLine();
//                    String schemes = "";
//                    for(String scheme : data.split(",")){
//                        schemes += scheme.substring(1,scheme.length()-1) + ",";
//                    }
//                    statModelToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),schemes.substring(0,schemes.length()-1));
//                    statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_STRING",schemes.substring(0,schemes.length()-1));
//                    statModelToDataType.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),"STRING");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            if(model.getId() == 53){
//                String tableName = "";
//                String tableScheme = "";
//                for(ModelAttribute modelAttribute : model.getData()){
//                    //拿到数据源的名字
//                    if(modelAttribute.getMattribute().equals("tableName")){
//                        tableName = modelAttribute.getMvalue().split(",")[1];
//                        statModelToDataSource.put(model.getBlockId(),tableName);
//                    }
//                }
//                for(ModelAttribute modelAttribute : model.getData()){
//                    //拿到数据源的字段
//                    if(modelAttribute.getMattribute().equals("dbLinkName")){
//                        String dbLinkName = modelAttribute.getMvalue().split("\\|")[1];
//                        DatabaseConnect databaseConnect = new DatabaseConnect();
//                        databaseConnect.setDbConName(dbLinkName);
//                        databaseConnect.setDbConUserId(user.getId());
//                        DatabaseConnect conn = databaseConnectService.findByUserIdAndConnName(databaseConnect);
//                        DatabaseType databaseType = databaseTypeService.selectByPrimaryKey(conn.getDbConTypeId());
//                        ResourceInfo resourceInfo = new ResourceInfo();
//                        resourceInfo.setUserName(conn.getDbConUser());
//                        resourceInfo.setHostIp(conn.getDbConIp());
//                        resourceInfo.setHostPort(conn.getDbConPort().toString());
//                        resourceInfo.setType(databaseType.getDbTypeName());
//                        resourceInfo.setDatabaseName(conn.getDbConDbname());
//                        resourceInfo.setPassWord(conn.getDbConPassword());
//                        resourceInfo.setTableName(tableName);
//                        resourceInfo.setConnectNmae(conn.getDbConName());
////                        System.out.println(resourceInfo);
//                        String metaData = readResourceService.getMetaData(resourceInfo);
//                        logger.info(metaData);
//
//                        String[] schemeSplits = metaData.split("\\|");
//
//                        for(String str : schemeSplits){
//                            tableScheme += str.split(",")[0] + ",";
//                        }
//                        statModelToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),tableScheme.substring(0,tableScheme.length()-1));
//                        Set<String> dbSchemeName = new HashSet<>();
//                        String stringScheme = "";
//                        String intScheme = "";
//                        String booScheme = "";
//                        String floatScheme = "";
//                        String douScheme = "";
//                        String bigScheme = "";
//                        String dateScheme = "";
//                        String timecheme = "";
//                        String timestampScheme = "";
//                        String longScheme = "";
//                        for(String split : schemeSplits){
//                            switch (split.split(",")[1].toUpperCase()){
//                                case "INT":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "VARCHAR":
//                                    stringScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("STRING");
//                                    break;
//                                case "CHAR":
//                                    stringScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("STRING");
//                                    break;
//                                case "TEXT":
//                                    stringScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("STRING");
//                                    break;
//                                case "INTEGER":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "TINYINT":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "SMALLINT":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "MEDIUMINT":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "BIT":
//                                    booScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("BOOLEAN");
//                                    break;
//                                case "BIGINT":
//                                    intScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("INT");
//                                    break;
//                                case "FLOAT":
//                                    floatScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("FLOAT");
//                                    break;
//                                case "DOUBLE":
//                                    douScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("DOUBLE");
//                                    break;
//                                case "DECIMAL":
//                                    bigScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("BIGDECIMAL");
//                                    break;
//                                case "DATE":
//                                    dateScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("DATE");
//                                    break;
//                                case "TIME":
//                                    timecheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("TIME");
//                                    break;
//                                case "DATETIME":
//                                    timestampScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("TIMESTAMP");
//                                    break;
//                                case "YEAR":
//                                    dateScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("DATE");
//                                    break;
//                                case "NUMBER":
//                                    bigScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("BIGDECIMAL");
//                                    break;
//                                case "VARCHAR2":
//                                    stringScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("STRING");
//                                    break;
//                                case "LONG":
//                                    longScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("LONG");
//                                    break;
//                                default:
//                                    stringScheme += split.split(",")[0] + ",";
//                                    dbSchemeName.add("STRING");
//                                    break;
//                            }
//                        }
//                        //每一个数据源中存储的不同的数据类型对应的字段名。
//                        if(StringUtils.hasText(stringScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_STRING", stringScheme.substring(0,intScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(intScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_INT", intScheme.substring(0,intScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(douScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DOUBLE", douScheme.substring(0,douScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(booScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_BOOLEAN", booScheme.substring(0,booScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(floatScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_FLOAT", floatScheme.substring(0,floatScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(bigScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DECIMAL",bigScheme.substring(0,bigScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(dateScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_DATE",dateScheme.substring(0,dateScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(timecheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_TIME",timecheme.substring(0,timecheme.length()-1));
//                        }
//                        if(StringUtils.hasText(timestampScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_TIMESTAMP",timestampScheme.substring(0,timestampScheme.length()-1));
//                        }
//                        if(StringUtils.hasText(longScheme)){
//                            statModelTypeToScheme.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()) + "_LONG",longScheme.substring(0,longScheme.length()-1));
//                        }
//
//                        String dbSchemeNameStr = "";
//                        for(String type : dbSchemeName){
//                            dbSchemeNameStr += type + ",";
//                        }
//                        if(!"".equals(dbSchemeNameStr)){
//                            dbSchemeNameStr = dbSchemeNameStr.substring(0,dbSchemeNameStr.length()-1);
//                        }
//                        statModelToDataType.put(model.getBlockId() + "_" + statModelToDataSource.get(model.getBlockId()),dbSchemeNameStr);
//                        logger.info(statModelToDataType.toString());
//                    }
//                }
//            }
//        }
//        Map<String,Map<String,String>> columnMap = new HashMap<>();
//        columnMap.put("statModelToDataSource",statModelToDataSource);
//        columnMap.put("statModelToDataType",statModelToDataType);
//        columnMap.put("statModelTypeToScheme",statModelTypeToScheme);
//        columnMap.put("statModelToScheme",statModelToScheme);
//        String columnStr = JSON.toJSONString(columnMap);
//        ResponseResult responseResult = new ResponseResult(200,columnStr);
//        return responseResult;
//    }




    @SuppressWarnings("unchecked")
    @RequestMapping(value = "readCsv",method = RequestMethod.GET)
    public  String readCsv(String path,HttpSession session, Model model) {
    	
    	try{
    		User user = (User)session.getAttribute(Constants.USER_KEY);
          //  HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
            byte[] byteArry = iHdfsApi.readFileFromHdfs(path, 1024*1024, user.getLoginname());
            String replace = new String(byteArry,"UTF-8").trim();
            String[] splitTr = replace.split("\\n|\\r");

            List<List<String>> table = new ArrayList();
            for (String tr : splitTr) {
                String[] td = tr.split(",");
                List<String> trTmp = new ArrayList<>();
                for (String s : td) {
                    trTmp.add(s);
                }
                table.add(trTmp);
            }
            String[] fileName = path.split("/");
            String FileName = fileName[fileName.length-1];
            model.addAttribute("table",table);
            model.addAttribute("FileName", FileName);
    	}catch (Exception e) {
            e.printStackTrace();
    	}
        
        return "readDetail/detail";
    }




    public  void  getFile(FileDto fileDto,String userName){
        FilefloderInfo fileInfo = fileDto.getFilefloderInfo();
        List<FileDto> childFiles = fileDto.getChildFiles();
        String path = fileInfo.getPath();
        Integer level = fileDto.getLevel() + 1;

        if(fileInfo.getIsdir()){
            //HdfsFileUtil hdfsUtils = new HdfsFileUtil();
            List<FileInfo> fileInfos = iHdfsApi.list(path.toString(), userName);

            List<FilefloderInfo> filefloderInfos = transtFileinfoToFileFloderInfo(fileInfos);


            for (FilefloderInfo info : filefloderInfos) {
                if(info.getPath().indexOf("_SUCCESS")==-1) {
                    FileDto fileDto1 = new FileDto();
                    fileDto1.setLevel(level);
                    fileDto1.setFilefloderInfo(info);
                    childFiles.add(fileDto1);
                    getFile(fileDto1, userName);
                }
            }
        }

    }


    public static  List<FilefloderInfo> transtFileinfoToFileFloderInfo(List<FileInfo> fileInfos){
        List<FilefloderInfo> filefloderInfos = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            FilefloderInfo filefloderInfo = new FilefloderInfo();
            filefloderInfo.setIsdir(fileInfo.isDir());
            filefloderInfo.setName(fileInfo.getPath().getName());
            filefloderInfo.setPath(fileInfo.getPath().toString());
            filefloderInfos.add(filefloderInfo);
        }
        return filefloderInfos;
    }
    
    
    public static void main(String[] args) {
		String a = "datas/input//csv/MOCK_DATA.csv";
		
		String[] b = a.split("/");
		System.out.println(b[b.length-1]);
		for(int i=0;i<b.length;i++){
			System.out.println(b[i]);
		}
	}
}
