package org.com.drag.web.controller.interfaceController;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.TreeCache;
import org.com.drag.model.FileDto;
import org.com.drag.model.FilefloderInfo;
import org.com.drag.model.User;
import org.com.drag.service.UserService;
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
@RequestMapping("drag/interface")
public class HdfsControllerApi {
	

	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Autowired
	 private IHdfsApi iHdfsApi;
	 @Autowired
	 private UserService userService;
	
	/**
	 * hdfs文件下载
	 */
	@RequestMapping(value = "down",method = RequestMethod.GET)
    @ResponseBody
	public void DownFileFromHDFS(String hdfsUrl,String userName, HttpServletRequest request,
			HttpServletResponse response)throws IOException {
		//User user = (User) session.getAttribute(Constants.USER_KEY);
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
        response.addHeader("Access-Control-Allow-Origin","*");
		FileSystem fileSystem = iHdfsApi.getFileSystem(userName);
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
	/**
	 * 
	 * @param 
	 * @param fName 前段用户名称
	 * @param user 后台用户对象   里面要传 loginname用户的名称
	 * @return
	 */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "fileApi",method = RequestMethod.GET)
    @ResponseBody
    public  List<FileDto> getUserFileConstruct(String fName,User user) {
    	
    	List<FileDto> rootfileDtos= null;
    	if(user != null && user.getLoginname() != null && fName != null){
    		 User users = userService.selectByUser(user);
             rootfileDtos =(List<FileDto>)TreeCache.getCache(users.getId() + "", null);
             String	selectPath = Constants.DATAS_SUFFIX+"/"+fName+"/"+Constants.INPUT_DATAS;
               
                if(rootfileDtos == null){
                	 rootfileDtos = new ArrayList<>();
                    List<FileInfo> fileStatusList = iHdfsApi.list(selectPath, users.getLoginname());
                    
                    List<FilefloderInfo> filefloderInfos = transtFileinfoToFileFloderInfo(fileStatusList);
                    EncapsulationFile(filefloderInfos,users.getLoginname(),rootfileDtos);
                   
                }

        	 return rootfileDtos;
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
                FileDto fileDto1 = new FileDto();
                fileDto1.setLevel(level);
                fileDto1.setFilefloderInfo(info);
                childFiles.add(fileDto1);
                getFile(fileDto1,userName);
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
