package org.com.drag.web.controller.interfaceController;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.User;
import org.com.drag.service.UserService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: LoginInterfaceController

 * @Description: 对外提供的接口

 * @author: jiaonanyue

 * @date: 2016年12月30日 下午1:58:44
 */
@Controller
@RequestMapping("/drag/interface")
public class LoginInterfaceController {
	
	@Autowired
	private UserService userService;
    @Autowired
    private IHdfsApi iHdfsApi;
	
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(User user, HttpSession session,HttpServletRequest request,
			  HttpServletResponse response){
		user.setPassword(user.getPassword());
		User users = userService.selectByUser(user);
		session.setAttribute(Constants.USER_KEY, users);
		
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+
				request.getContextPath()+"/drag/dispatcher/drag.do";
		return "redirect:"+path;
		
	}
	
	/**
	 * 用户密码修改
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult modify(User user){
		
		if(user != null){
			if( user.getPassword().equals("") || user.getId() == null || user.getLoginname().equals("")){
				
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "密码为空");
			}
			
			User users = userService.selectByPrimaryKey(user.getId());
			int a = 0;
			if(users == null){
				user.setCreatedate(new Date());
				user.setStatus(Byte.parseByte("1"));
				user.setUsertype(Byte.parseByte("1"));
				user.setTips(0);
				user.setToken("");
			  //创建对应hdfs目录
				String HdfsUrl = user.getLoginname();
				//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				Boolean userSpaceResult =  iHdfsApi.createUserDir(HdfsUrl);
				if(!userSpaceResult){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户目录创建失败");
				}

				Boolean  workspaceResult =  iHdfsApi.createUserWorkspace(Constants.WORKSPACES_SUFFIX, HdfsUrl,false);
				if( !workspaceResult){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "工作空间目录创建失败");
				}

				Boolean  dataspaceResult =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX, HdfsUrl,false);

				if( !dataspaceResult){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间目录创建失败");
				}
				Boolean  inputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.INPUT_DATAS, HdfsUrl,false);

				if( !inputDatas){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Input目录创建失败");
				}

				Boolean  outputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.OUTPUT_DATAS, HdfsUrl,false);

				if( !outputDatas){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Output目录创建失败");
				}
				user.setHdfsUrl(MailAuthorSetting.HADOOP_HDFS_URL+Constants.getSpacePrefix().append(HdfsUrl).append("/").append(Constants.WORKSPACES_SUFFIX).toString());
				 a = userService.registerUser(user);

			}else{
				user.setToken("");
			    a = userService.updateByPrimaryKeySelective(user);

			}
			
			return a == 0 ? new ResponseResult(HttpStatus.EXPECTATION_FAILED, "密码修改失败") : new ResponseResult(HttpStatus.OK, "密码修改成功");
		}
		return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
	}
	
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult register( User user){
		user.setCreatedate(new Date());
		user.setStatus(Byte.parseByte("1"));
		user.setUsertype(Byte.parseByte("1"));
		user.setTips(0);
		user.setEducation("2");
		user.setDeveloper(0);
		user.setUserSex("1");
		
		//默认给用户授权
		user.setOrganizationId(11);
		user.setRoleIds("10");
				
		//创建对应hdfs目录
		String HdfsUrl = user.getLoginname();
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		Boolean userSpaceResult =  iHdfsApi.createUserDir(HdfsUrl);
		if(!userSpaceResult){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户目录创建失败");
		}

		Boolean  workspaceResult =  iHdfsApi.createUserWorkspace(Constants.WORKSPACES_SUFFIX, HdfsUrl,false);
		if( !workspaceResult){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "工作空间目录创建失败");
		}

		Boolean  dataspaceResult =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX, HdfsUrl,false);

		if( !dataspaceResult){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间目录创建失败");
		}
		Boolean  inputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.INPUT_DATAS, HdfsUrl,false);

		if( !inputDatas){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Input目录创建失败");
		}

		Boolean  outputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.OUTPUT_DATAS, HdfsUrl,false);

		if( !outputDatas){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Output目录创建失败");
		}
		user.setHdfsUrl(MailAuthorSetting.HADOOP_HDFS_URL+Constants.getSpacePrefix().append(HdfsUrl).append("/").append(Constants.WORKSPACES_SUFFIX).toString());
		int status = userService.registerUser(user);

		return status == 1 ? new ResponseResult(HttpStatus.OK, "注册成功") : new ResponseResult(HttpStatus.PRECONDITION_FAILED, "注册失败");

	}

	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteUser( User user){
		
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		if(user != null && user.getIds() != null){
			String[] idList = user.getIds().split(",");
			for(String ids : idList){
				User users = userService.selectByPrimaryKey(Integer.parseInt(ids));
				//删除hdfs上的目录
				Boolean a = iHdfsApi.deleteFileOrDir(MailAuthorSetting.HADOOP_HDFS_URL+MailAuthorSetting.HADOOP_HDFS_USER_DIR+users.getLoginname(),
						  "hdfs", true);
				if(a){
					//删除用户数据
					int b = userService.deleteByPrimaryKey(Integer.parseInt(ids));
					if(b != 1){
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"删除失败");
					}
				}
			}
			return new ResponseResult(HttpStatus.OK,"删除成功");
		}else{
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}
			

	}


}
