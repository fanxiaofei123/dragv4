package org.com.drag.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.com.drag.common.base.BaseController;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.model.WorkSpace;
import org.com.drag.service.UserService;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.WorkSpaceService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.
 * 
 * @ClassName: UserController
 * 
 * @Description: 用户操作Controller
 * 
 * @author: jiaonanyue
 * 
 * @date: 2016年12月16日 上午10:44:17
 */
@Controller
@RequestMapping("/drag/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired
	private WorkFlowService workFlowService;
    @Autowired
    private IHdfsApi iHdfsApi;
    
	/**
	 * 查询用户信息 分页 pageHelper
	 * 
	 * @param model
	 * @param dataModel
	 * @return
	 */
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public String getUserList(Model model, int page) {
		if (page == 0) {
			page = 1;
		}
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<User> userList = userService.selectAll();
		for (User column : userList) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			column.setCreatedates(dateformat.format(column.getCreatedate()));
		}
		// 设置返回的总记录数
		PageInfo<User> pageInfos = new PageInfo<User>(userList);
		if (page == 1) {
			model.addAttribute("Previous", page);
		} else {
			model.addAttribute("Previous", page - 1);
		}

		if (page < pageInfos.getPages()) {
			model.addAttribute("next", page + 1);
		} else {
			model.addAttribute("next", page);
		}
		model.addAttribute("page", page);
		model.addAttribute("Total", pageInfos.getPages());
		model.addAttribute("userList", userList);
		return "/user/list";

	}

	/**
	 * 查询用户信息 分页 pageHelper
	 * 
	 * @param model
	 * @param dataModel
	 * @return
	 */
	@RequestMapping(value = "selects", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserList(Model model, Integer page) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<User> userList = userService.selectAll();
		for (User column : userList) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			column.setCreatedates(dateformat.format(column.getCreatedate()));
			if(null != column.getDeveloper() && column.getDeveloper() == 0){
				column.setDeveloperName("使用者");
			}
			if(null != column.getDeveloper() && column.getDeveloper() == 1){
				column.setDeveloperName("开发者");
			}
			if(null !=  column.getDeveloper() && column.getDeveloper() == 2){
				column.setDeveloperName("管理者");
			}
			if(null != column.getEducation() && column.getEducation().equals("0")){
				column.setEducationName("博士");
			}
			if(null != column.getEducation() && column.getEducation().equals("1")){
				column.setEducationName("硕士");
			}
			if(null != column.getEducation() && column.getEducation().equals("2")){
				column.setEducationName("本科及本科以下");
			}
			if(null != column.getUserSex() && column.getUserSex().equals("1")){
				column.setUserSexName("男");
			}
			if(null != column.getUserSex() && column.getUserSex().equals("2")){
				column.setUserSexName("女");
			}
			
		}
		// 设置返回的总记录数
		PageInfo<User> pageInfos = new PageInfo<User>(userList);
		if (page == 1) {
			pageBean.setPrevious(page);
		} else {
			pageBean.setPrevious(page - 1);
		}

		if (page < pageInfos.getPages()) {
			pageBean.setNext(page + 1);
		} else {
			pageBean.setNext(page);
		}
		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(userList);

		return pageBean;

	}

	
	/**
	 * 通过用户名模糊查询
	 * 查询用户信息 分页 pageHelper
	 * 
	 * @param model
	 * @param dataModel
	 * @return
	 */
	@RequestMapping(value = "selectname", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserName(Model model, Integer page,User user) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<User> userList = userService.selectNameAll(user);
		for (User column : userList) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			column.setCreatedates(dateformat.format(column.getCreatedate()));
			if(null != column.getDeveloper() && column.getDeveloper() == 0){
				column.setDeveloperName("使用者");
			}
			if(null != column.getDeveloper() && column.getDeveloper() == 1){
				column.setDeveloperName("开发者");
			}
			if(null !=  column.getDeveloper() && column.getDeveloper() == 2){
				column.setDeveloperName("管理者");
			}
			if(null != column.getEducation() && column.getEducation().equals("0")){
				column.setEducationName("博士");
			}
			if(null != column.getEducation() && column.getEducation().equals("1")){
				column.setEducationName("硕士");
			}
			if(null != column.getEducation() && column.getEducation().equals("2")){
				column.setEducationName("本科及本科以下");
			}
			if(null != column.getUserSex() && column.getUserSex().equals("1")){
				column.setUserSexName("男");
			}
			if(null != column.getUserSex() && column.getUserSex().equals("2")){
				column.setUserSexName("女");
			}
			
		}
		// 设置返回的总记录数
		PageInfo<User> pageInfos = new PageInfo<User>(userList);
		if (page == 1) {
			pageBean.setPrevious(page);
		} else {
			pageBean.setPrevious(page - 1);
		}

		if (page < pageInfos.getPages()) {
			pageBean.setNext(page + 1);
		} else {
			pageBean.setNext(page);
		}
		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(userList);

		return pageBean;

	}

	
	/**
	 * 详情页
	 * 
	 * @param model
	 * @param dataModel
	 * @return
	 */
	@RequestMapping(value = "details", method = RequestMethod.GET)
	public String detailsUser(Model model, User user) {

		User userOld = userService.selectByPrimaryKey(user.getId());
		List<WorkSpace> workSpaceList = workSpaceService.UserCountWorkSpace(user.getId());
		if (workSpaceList != null && workSpaceList.size() > 0) {
			model.addAttribute("WorkSpaceNum", workSpaceList.size());

			int workFlowNul = 0;
			for (WorkSpace ws : workSpaceList) {

				List<WorkFlow> workFlowList = workFlowService.selectWorkFlowBySpace(ws.getId());
				if (workFlowList != null && workFlowList.size() > 0) {
					workFlowNul = workFlowNul + workFlowList.size();

				}
			}
			model.addAttribute("WorkFlowNum", workFlowNul);
		} else {
			model.addAttribute("WorkSpaceNum", 0);
			model.addAttribute("WorkFlowNum", 0);
		}
		model.addAttribute("user", userOld);

		return "/user/details";

	}

	/**
	 * 详情页
	 * 
	 * @param model
	 * @param dataModel
	 * @return
	 */
	@RequestMapping(value = "detailsUser", method = RequestMethod.GET)
	public String updateUser(Model model, User user) {

		User userOld = userService.selectByPrimaryKey(user.getId());
		List<WorkSpace> workSpaceList = workSpaceService.UserCountWorkSpace(user.getId());
		if (workSpaceList != null && workSpaceList.size() > 0) {
			model.addAttribute("WorkSpaceNum", workSpaceList.size());

			int workFlowNul = 0;
			for (WorkSpace ws : workSpaceList) {

				List<WorkFlow> workFlowList = workFlowService.selectWorkFlowBySpace(ws.getId());
				if (workFlowList != null && workFlowList.size() > 0) {
					workFlowNul = workFlowNul + workFlowList.size();

				}
			}
			model.addAttribute("WorkFlowNum", workFlowNul);
		} else {
			model.addAttribute("WorkSpaceNum", 0);
			model.addAttribute("WorkFlowNum", 0);
		}
		model.addAttribute("user", userOld);

		return "/user/Updatedetails";

	}

	/**
	 * 用户信息修改
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "modifyu", method = RequestMethod.POST)
	public String modifyDetailsUser(HttpServletRequest request, User user, HttpServletResponse response,
			HttpSession session) {
		userService.updateByPrimaryKeySelective(user);

		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/drag/users/detailsUser.do?id=" + user.getId();
		return "redirect:" + path;
	}

	/**
	 * 用户信息修改
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult modifyUser(HttpServletRequest request, User user, HttpServletResponse response,
			HttpSession session) {
		User users = (User) session.getAttribute(Constants.USER_KEY);
		// 修改hdfs目录
		//HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
		Boolean a = iHdfsApi.renameDirectoryOrFile(
				MailAuthorSetting.HADOOP_HDFS_URL + MailAuthorSetting.HADOOP_HDFS_USER_DIR + users.getLoginname(),
				user.getLoginname(), users.getLoginname());
		if (a) {
			user.setId(users.getId());
			User userNew = new User();
			userNew.setLoginname(user.getLoginname());
			User userOld = userService.selectByUser(userNew);
			if (userOld != null) {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "用户名相同");
			}
			userService.updateByPrimaryKeySelective(user);
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改用户信息成功");

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改用户信息失败");
		}

	}
}
