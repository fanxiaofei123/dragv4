package org.com.drag.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.com.drag.common.base.BaseController;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.DigestUtils;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.EmailDto;
import org.com.drag.model.User;
import org.com.drag.service.MailService;
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

 * @ClassName: LoginController

 * @Description: 用户操作Controller

 * @author: jiaonanyue

 * @date: 2016年10月26日 上午9:24:50
 */
@Controller
@RequestMapping("/drag/user")
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
    @Autowired
	private IHdfsApi iHdfsApi;
	
	/**
	 * 用户登录
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(User user, HttpSession session){
		/*user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User users = userService.selectByUser(user);
		if(users == null){
			return new ResponseResult(HttpStatus.NOT_FOUND, "账户或密码错误");
		}

		if(users.getStatus() == 0){
			return new ResponseResult(HttpStatus.LOCKED, "账户尚未激活,请前往邮箱激活");
		}
		session.setAttribute(Constants.USER_KEY, users);
		return new ResponseResult(HttpStatus.OK);*/
		
		 Subject users = SecurityUtils.getSubject();
	        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), DigestUtils.md5Hex(user.getPassword().trim()));
	        // 设置记住密码
	       //token.setRememberMe(1 == rememberMe);
	        try {
	            users.login(token);
	            return renderSuccess();
	        } catch (UnknownAccountException e) {
	        	return new ResponseResult(HttpStatus.NOT_FOUND, "账户或密码错误");
	        	// throw new RuntimeException("账号不存在！", e);
	        } catch (DisabledAccountException e) {
	        	return new ResponseResult(HttpStatus.LOCKED, "账户尚未激活,请前往邮箱激活");
	            //throw new RuntimeException("账号未启用！", e);
	        } catch (IncorrectCredentialsException e) {
	        	return new ResponseResult(HttpStatus.NOT_FOUND, "账户或密码错误");
	            //throw new RuntimeException("密码错误！", e);
	        } catch (Throwable e) {
	            throw new RuntimeException(e.getMessage(), e);
	        }
	        
	}

	/**
	 * 用户登录集成
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logins", method = RequestMethod.GET)
	public String login(User user, HttpSession session,HttpServletRequest request,
			  HttpServletResponse response){
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User users = userService.selectByUser(user);
		/*if(users == null){
			return new ResponseResult(HttpStatus.NOT_FOUND, "账户或则密码错误");
		}

		if(users.getStatus() == 0){
			return new ResponseResult(HttpStatus.LOCKED, "账户尚未激活,请前往邮箱激活");
		}*/
		session.setAttribute(Constants.USER_KEY, users);
		
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+
				request.getContextPath()+"/drag/dispatcher/workSpaceList.do";
		return "redirect:"+path;
		//return new ResponseResult(HttpStatus.OK);
		
	}


	/**
	 * 用户登出
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String  logout(HttpServletRequest request,
						  HttpServletResponse response, HttpSession session){
		session.removeAttribute(Constants.USER_KEY);
		String contextPath = request.getContextPath();
		String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
		return "redirect:" + path;
	}
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult register( User user) throws IOException {
		user.setCreatedate(new Date());
		user.setStatus(Byte.parseByte("0"));
		user.setUsertype(Byte.parseByte("1"));
		user.setTips(0);
		user.setEducation("2");
		user.setDeveloper(0);
		user.setUserSex("1");
		
		//默认给用户授权
		user.setOrganizationId(11);
		user.setRoleIds("10");
		User registerTest= userService.selectByLoginName(user.getLoginname());//验证用户是否已经存在
//        User registerTest =userService.selectByUser(user);
		if (registerTest!=null){
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户已经注册");
		}
		//创建对应hdfs目录
		String HdfsUrl = user.getLoginname();
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
		if (!fileSystem.exists(new Path(MailAuthorSetting.HADOOP_HDFS_USER_DIR+HdfsUrl))){//如果用户目录已经存在，绕过检测目录的逻辑
			Boolean userSpaceResult =  iHdfsApi.createUserDir(HdfsUrl);
			if(!userSpaceResult){
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户目录创建失败");
			}
		}
         if (!fileSystem.exists(new Path(Constants.WORKSPACES_SUFFIX))){
			 Boolean  workspaceResult =  iHdfsApi.createUserWorkspace(Constants.WORKSPACES_SUFFIX, HdfsUrl,false);
			 if( !workspaceResult){
				 return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "工作空间目录创建失败");
			 }

		 }
		 if (!fileSystem.exists(new Path(Constants.DATAS_SUFFIX))){
			 Boolean  dataspaceResult =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX, HdfsUrl,false);

			 if( !dataspaceResult){
				 return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间目录创建失败");
			 }
		 }


         if (!fileSystem.exists(new Path(Constants.DATAS_SUFFIX+"/"+Constants.INPUT_DATAS))){
			 Boolean  inputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.INPUT_DATAS, HdfsUrl,false);

			 if( !inputDatas){
				 return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Input目录创建失败");
			 }
		 }

		 if (!fileSystem.exists(new Path(Constants.DATAS_SUFFIX+"/"+Constants.OUTPUT_DATAS))){
			 Boolean  outputDatas =  iHdfsApi.createUserWorkspace(Constants.DATAS_SUFFIX+"/"+Constants.OUTPUT_DATAS, HdfsUrl,false);

			 if( !outputDatas){
				 return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "储存空间Output目录创建失败");
			 }
		 }

		user.setHdfsUrl(Constants.getSpacePrefix().append(HdfsUrl).append("/").append(Constants.WORKSPACES_SUFFIX).toString());

		String token = UUID.randomUUID().toString();
		user.setToken(token);
		int status = userService.register(user);



		EmailDto emailDto = new EmailDto(user.getemail(),token,MailAuthorSetting.getServerAddress().append("drag/acount/active.do").toString());

		mailService.sendMailByAsynchronousMode(emailDto);

		return status == 1 ? new ResponseResult(HttpStatus.OK, "注册成功，即將跳转到登录页，请前往邮箱激活") : new ResponseResult(HttpStatus.PRECONDITION_FAILED, "注册失败");

	}

	/**
	 * 用户验证
	 * @return
	 */
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	@ResponseBody
	public Boolean validateName(User  user,String oldPassword){

		if(StringUtils.isNotBlank(oldPassword)){
			user.setPassword(DigestUtils.md5Hex(oldPassword));
		}

		User users = userService.selectByUser(user);
		return users == null ? true : false;

	}



	@RequestMapping(value = "retrieve",method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult retrieve(User user){
		User users =  userService.selectByUser(user);

		String token = UUID.randomUUID().toString();

		if(users == null){
			return new ResponseResult(HttpStatus.NOT_FOUND, "邮箱不正确");
		}
		users.setToken(token);
		userService.updateByPrimaryKeySelective(users);

		EmailDto emailDto = new EmailDto(user.getemail(),token,MailAuthorSetting.getServerAddress().append("/drag/acount/toModify.do").toString());
		mailService.sendRetrieveMailByAsynchronousMode(emailDto);
		return  new ResponseResult(HttpStatus.OK,"密码修改邮件已发送,请查收");
	}

	/**
	 * 用户密码修改
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult modify(User user){
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setToken("");
	    int a = userService.updateByPrimaryKeySelective(user);

		return a == 0 ? new ResponseResult(HttpStatus.EXPECTATION_FAILED, "密码修改失败") : new ResponseResult(HttpStatus.OK, "密码修改成功");
	}
}
