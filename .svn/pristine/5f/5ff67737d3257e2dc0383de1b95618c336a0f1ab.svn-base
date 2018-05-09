package org.com.drag.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.com.drag.common.result.DatasResult;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.HttpXmlClient;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.TreeCache;
import org.com.drag.model.DelFile;
import org.com.drag.model.DelFileListDto;
import org.com.drag.model.HdfsUploadDto;
import org.com.drag.model.HdfsUrlModel;
import org.com.drag.model.UploadHistory;
import org.com.drag.model.User;
import org.com.drag.service.UploadHistoryService;
import org.com.drag.service.UserService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.bean.FileInfo;
import org.com.drag.web.common.CustomMultipartResolver;
import org.com.drag.websock.TextMessageHandler;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.socket.TextMessage;

import net.sf.json.JSONArray;


/**
 *
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: UpdateController

 * @Description: 上传文件

 * @author: jiaonanyue

 * @date: 2016年10月26日 下午3:35:29
 */
@Controller
@RequestMapping("/drag/upload")
public class UploadDataController {
	// 日志对象
		private static Log logger = LogFactory.getLog(UploadDataController.class);
	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private UserService userService;
	@Autowired
	private UploadHistoryService uploadHistoryService;
    @Autowired
    private IHdfsApi iHdfsApi;

	 public String addUser(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request){

	        for(int i = 0;i<files.length;i++){
	            System.out.println("fileName---------->" + files[i].getOriginalFilename());

	            if(!files[i].isEmpty()){
	                int pre = (int) System.currentTimeMillis();
	                try {
	                    //拿到输出流，同时重命名上传的文件
	                    FileOutputStream os = new FileOutputStream("H:/" + new Date().getTime() + files[i].getOriginalFilename());
	                    //拿到上传文件的输入流
	                    FileInputStream in = (FileInputStream) files[i].getInputStream();

	                    //以写字节的方式写文件
	                    int b = 0;
	                    while((b=in.read()) != -1){
	                        os.write(b);
	                    }
	                    os.flush();
	                    os.close();
	                    in.close();
	                    int finaltime = (int) System.currentTimeMillis();
	                    System.out.println(finaltime - pre);

	                } catch (Exception e) {
	                    e.printStackTrace();
	                    System.out.println("上传出错");
	                }
	        }
	        }
	        return "/success";
	    }


	    /**
	     * 上传的大文件
	     * @param request
	     * @param currentDir
	     * @param response
	     * @param session
	     * @return
	     * @throws IllegalStateException
	     * @throws IOException
	     */
	    @SuppressWarnings("unused")
		@RequestMapping(value = "data",method = RequestMethod.POST)
		@ResponseBody
	    public ResponseResult upload2(HttpServletRequest request,String currentDir, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
	        //创建一个通用的多部分解析器
				CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
			multipartResolver.setServletContext(request.getSession().getServletContext());
			//判断 request 是否有文件上传,即多部分请求

			//final HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
			String path = null;
			if(multipartResolver.isMultipart(request)){
	            //转换成多部分request
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				String url = request.getServletContext().getRealPath("/")+"upload\\";


	            //取得request中的所有文件名
	            Iterator<String> iter = multiRequest.getFileNames();

	            while(iter.hasNext()){
	                //记录上传过程起始时的时间，用来计算上传时间
	                int pre = (int) System.currentTimeMillis();
	                //取得上传文件
	                MultipartFile file = multiRequest.getFile(iter.next());

					int pre1 = (int) System.currentTimeMillis();

					int pre2 =1;
					if(file != null){
	                    //取得当前上传文件的文件名称
	                    final String myFileName = file.getOriginalFilename();
	                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
	                    if(myFileName.trim() !=""){
	                        //重命名上传后的文件名
	                        String fileName = file.getOriginalFilename();
	                        //定义上传路径
	                        path = url + fileName;
	                        final File localFile = new File(path);
	                        file.transferTo(localFile);

							final User user = (User) session.getAttribute(Constants.USER_KEY);
							String usr = user.getLoginname();
							String uploadPath = currentDir+"/" + fileName;
							final HdfsUploadDto hdfsUploadDto = new HdfsUploadDto(path,usr,uploadPath,user.getId(),user.getTips());

							pre2 = (int) System.currentTimeMillis();

							taskExecutor.execute(new Runnable(){
								@SuppressWarnings("unchecked")
								public void run(){
									try {
										Boolean uploadResult  = iHdfsApi.uploadFile(hdfsUploadDto.getPath(),hdfsUploadDto.getUploadPath(),hdfsUploadDto.getUsr());
										if(uploadResult){
											TextMessageHandler textMessageHandler = new TextMessageHandler();
											Integer tipTimes = user.getTips() == 0 ? 1 : user.getTips();
											JSONObject jsonObject = new JSONObject();
											jsonObject.put("tipTimes", tipTimes);
											TextMessage message = new TextMessage(jsonObject.toJSONString());
											textMessageHandler.sendMessageToUser(hdfsUploadDto.getUserKey(), message);
											UploadHistory uploadHistory = new UploadHistory(myFileName,new Date(),user.getId(),"/"+hdfsUploadDto.getUploadPath().substring(hdfsUploadDto.getUploadPath().indexOf(hdfsUploadDto.getUsr()),hdfsUploadDto.getUploadPath().length()));
											TreeCache.removeCache(user.getId()+"");
											uploadHistoryService.insert(uploadHistory);
										}
									} catch (Exception e) {
										logger.info(e);
									}finally {
										localFile.delete();
									}
								}
							});

	                    }
	                }
	            }

	        }

	        return new ResponseResult(HttpStatus.OK,"文件已经,缓存到服务器.正在上传到hdfs.....");
	    }

	    /**
	     * 上传小文件
	     * @param request
	     * @param currentDir
	     * @param response
	     * @param session
	     * @return
	     * @throws IllegalStateException
	     * @throws IOException
	     */
	@RequestMapping(value = "updae", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult Updatedata(HttpServletRequest request, String currentDir, HttpServletResponse response,
			HttpSession session) throws IllegalStateException, IOException {
		// 创建一个通用的多部分解析器
		CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
		multipartResolver.setServletContext(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求

		// final HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		String path = null;
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			String url = request.getServletContext().getRealPath("/") + "upload\\";

			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());

				if (file != null) {
					// 取得当前上传文件的文件名称
					final String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						ResponseResult responseResult = new ResponseResult();
						// 重命名上传后的文件名
						String fileName = file.getOriginalFilename();
						// 定义上传路径
						path = url + fileName;
						final File localFile = new File(path);
						file.transferTo(localFile);

						final User user = (User) session.getAttribute(Constants.USER_KEY);
						String usr = user.getLoginname();
						Date data = new Date();
						
						String uploadPath = currentDir + "/" +data.getTime()+"_"+ fileName;
						final HdfsUploadDto hdfsUploadDto = new HdfsUploadDto(path, usr, uploadPath, user.getId(),
								user.getTips());

						Boolean uploadResult = iHdfsApi.uploadFile(hdfsUploadDto.getPath(),
								hdfsUploadDto.getUploadPath(), hdfsUploadDto.getUsr());
						if (uploadResult) {
							UploadHistory uploadHistory = new UploadHistory(myFileName, new Date(), user.getId(),
									"/"+hdfsUploadDto.getUploadPath().substring(hdfsUploadDto.getUploadPath().indexOf(hdfsUploadDto.getUsr()),hdfsUploadDto.getUploadPath().length()));
							uploadHistoryService.insert(uploadHistory);
							responseResult.setCode(200);
							responseResult.setMsg("上传文件成功");
							responseResult.setObj(uploadPath);
							return responseResult;
						}else{
							return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "上传文件失败");
						}
					}
				}
			}

		}
		return new ResponseResult(HttpStatus.OK, "上传文件成功");
	}
	@RequestMapping(value = "del",method = RequestMethod.POST)
	@ResponseBody
	public  ResponseResult   batchDel(@RequestBody DelFileListDto delFileListDto, HttpSession session) {
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		User user = (User)session.getAttribute(Constants.USER_KEY);
		List<DelFile> delFiles = delFileListDto.getDelFiles();
		for (DelFile delFile : delFiles) {
			StringBuffer path = new StringBuffer();
			path.append(delFileListDto.getCurrentDir()).append("/").append(delFile.getName());

			iHdfsApi.deleteFileOrDir(path.toString(),user.getLoginname(),delFile.getIsdir());
		}


		return new ResponseResult(HttpStatus.OK,"删除成功");
	}

	@RequestMapping(value = "creDir",method = RequestMethod.POST)
	@ResponseBody
	public  ResponseResult  creDir(String currentDir,String name,HttpSession session) {
		User user =  (User)session.getAttribute(Constants.USER_KEY);
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		StringBuffer path = new StringBuffer(currentDir).append("/").append(name);
		Boolean result = iHdfsApi.createUserWorkspace(path.toString(),user.getLoginname(),false);
		return result ? new ResponseResult(HttpStatus.OK,"创建目录成功"):new ResponseResult(HttpStatus.EXPECTATION_FAILED,"创建目录失败");
	}

	
	/**
	 * 数据展示
	 * @param session
	 * @param currentDir
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "datas",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> datas(HttpSession session, String currentDir){

         currentDir = StringUtils.isBlank(currentDir) ? Constants.DATAS_SUFFIX : currentDir;

         //HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
         User user = (User) session.getAttribute(Constants.USER_KEY);
		String LoginName = user.getLoginname();
		List<FileInfo> fileStatusList =  iHdfsApi.list(currentDir, LoginName);


		List<DatasResult> datasResults = new ArrayList<>();

		for (FileInfo fileInfo : fileStatusList) {
			String fileName = fileInfo.getPath().getName();
			Boolean isdir = fileInfo.isDir();

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(fileInfo.getModificationTime());
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String date = dateformat.format(c.getTime());

			StringBuffer path = new StringBuffer();
			path.append("/user/").append(LoginName).append("/").append(currentDir).append("/").append(fileName);
			//MailAuthorSetting.HADOOP_HDFS_URL+"/user/"+LoginName+"/"+currentDir+"/"+fileName
			DatasResult datasResult = new DatasResult(fileName,isdir,date,path.toString());

			if(isdir){
				Long size = Long.valueOf(iHdfsApi.list(currentDir+"/"+fileName,LoginName).size());
				datasResult.setSize(size+"个文件");
			}else{
				datasResult.setSize(convertFileSize(fileInfo.getLength()));

			}
			datasResults.add(datasResult);
		}

		Map<String, Object> result = new HashedMap();
		result.put("dir",currentDir);
		result.put("datasResults",datasResults);
		return result;
	}

	
	/**
	 * 共享数据展现  特有
	 * @param session
	 * @param currentDir
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "share",method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> shateDatas( HttpSession session,String currentDir){
		
		if(currentDir == null){
			currentDir = MailAuthorSetting.HDFS_USER_DATA;
		}
		
         currentDir = StringUtils.isBlank(currentDir) ? Constants.DATAS_SUFFIX : currentDir;
        // HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
         User user = (User) session.getAttribute(Constants.USER_KEY);	
         String xml = HttpXmlClient.get(MailAuthorSetting.HTTPCLINET_PATH+"?userid="+user.getId()); 
         JSONArray jsonArray = JSONArray.fromObject(xml); 
	 
	      List<HdfsUrlModel> list =   (List<HdfsUrlModel>) JSONArray.toCollection(jsonArray,HdfsUrlModel.class);
	      List<String> listHdfs = new ArrayList<String>();
	      for (HdfsUrlModel receiptList:list) {
	    	  String[] hdfsPath = receiptList.getHdfsPath().split("/");
	    	  listHdfs.add(hdfsPath[5]);
	       /*System.out.println(receiptList.getHdfsPath());
	       List<FileInfo> fileHdfsList = hdfsFileUtil.list(receiptList.getHdfsPath(), MailAuthorSetting.HDFS_USER);
	       hdfs://172.16.0.23:8020/user/govdata/贵阳执行类裁判文书
	       */
	       }
	     
      /*  List<String> list = new ArrayList<String>();
        list.add("地级行政界线");
        */
        List<DatasResult> datasResults = new ArrayList<>();
        if(currentDir.equals(MailAuthorSetting.HDFS_USER_DATA)){
        	
        	List<FileInfo> fileStatusList =  iHdfsApi.list(currentDir, MailAuthorSetting.HDFS_USER);

    		for (FileInfo fileInfo : fileStatusList) {
    			String fileName = fileInfo.getPath().getName();
    			Boolean isdir = fileInfo.isDir();

    			if(listHdfs.contains(fileName)){
    				Calendar c = Calendar.getInstance();
    				c.setTimeInMillis(fileInfo.getModificationTime());
    				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    				String date = dateformat.format(c.getTime());

    				StringBuffer path = new StringBuffer();
    				path.append(currentDir).append(fileName);
    				//MailAuthorSetting.HADOOP_HDFS_URL+"/user/"+LoginName+"/"+currentDir+"/"+fileName
    				DatasResult datasResult = new DatasResult(fileName,isdir,date,path.toString());

    				if(isdir){
    					Long size = Long.valueOf(iHdfsApi.list(currentDir+"/"+fileName,MailAuthorSetting.HDFS_USER).size());
    					datasResult.setSize(size+"个文件");
    				}else{
    					datasResult.setSize(convertFileSize(fileInfo.getLength()));

    				}
    				datasResults.add(datasResult);
    			}
    			
    		}

        	
        }else{
        	
		List<FileInfo> fileStatusList =  iHdfsApi.list(currentDir, MailAuthorSetting.HDFS_USER);

		for (FileInfo fileInfo : fileStatusList) {
			String fileName = fileInfo.getPath().getName();
			Boolean isdir = fileInfo.isDir();
			
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(fileInfo.getModificationTime());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = dateformat.format(c.getTime());

				StringBuffer path = new StringBuffer();
				path.append(currentDir).append(fileName);
				//MailAuthorSetting.HADOOP_HDFS_URL+"/user/"+LoginName+"/"+currentDir+"/"+fileName
				DatasResult datasResult = new DatasResult(fileName,isdir,date,path.toString());

				if(isdir){
					Long size = Long.valueOf(iHdfsApi.list(currentDir+"/"+fileName,MailAuthorSetting.HDFS_USER).size());
					datasResult.setSize(size+"个文件");
				}else{
					datasResult.setSize(convertFileSize(fileInfo.getLength()));

				}
				datasResults.add(datasResult);
			}
			
        }

		Map<String, Object> result = new HashedMap();
		result.put("dir",currentDir);
		result.put("datasResults",datasResults);
		return result;
	}


	@RequestMapping(value = "addTipTimes",method = RequestMethod.GET)
	@ResponseBody
	public  void  addTipTimes(Integer tips,HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		user.setTips(tips);
		session.setAttribute(Constants.USER_KEY,user);
		userService.updateByPrimaryKeySelective(user);
	}



	private   String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		//%.2f 即是保留两位小数的浮点数，后面跟上对应单位就可以了，不得不说java很方便
		if (size >= gb) {
			return String.format("%.2f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			//如果大于100MB就不用保留小数位啦
			return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			//如果大于100kB就不用保留小数位了
			return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
		} else
			return String.format("%d B", size);
	}

	
}
