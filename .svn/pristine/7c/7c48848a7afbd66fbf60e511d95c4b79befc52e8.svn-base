package org.com.drag.web.controller.interfaceController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.com.drag.common.page.PageBean;
import org.com.drag.model.FacadeModel;
import org.com.drag.model.ModelDetail;
import org.com.drag.model.ModelFrontUser;
import org.com.drag.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

/**
 * 
 * Copyright © 2016 All rights reserved.
 * 
 * @ClassName: FacadeModelControllerApi
 * 
 * @Description: 模型接口
 * 
 * @author: jiaonanyue
 * 
 * @date: 2016年11月14日 上午10:12:11
 */
@Controller
@RequestMapping("/model")
public class FacadeModelControllerApi {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ModelService modelService;

	/**
	 * 通过treeid查询 treeid in查询 Integer[] idListTree;
	 * 
	 * @param facadeModel
	 * 这里的参数type后来被弃用，注意
	 * @return
	 */
	@RequestMapping(value = "/models")
	@ResponseBody
	public PageBean models(HttpServletRequest request, HttpServletResponse response) {
		String pageStr = request.getParameter("page"), treeIdStr = request.getParameter("treeId");
		String frontName = request.getParameter("frontName"),type = request.getParameter("type");
		int page;
		long treeId;
		page = StringUtils.isNumeric(pageStr) ? Integer.valueOf(pageStr) : 1;
		treeId = StringUtils.isNumeric(treeIdStr) ? Integer.valueOf(treeIdStr) : 0;
		frontName = StringUtils.isBlank(frontName) ? null : frontName;
		List<? extends FacadeModel> FacadeModelList = modelService.getByTreeId(treeId, page, frontName, type);
		// 设置返回的总记录数
		PageBean pageBean = new PageBean();
		PageInfo<? extends FacadeModel> pageInfos = new PageInfo<>(FacadeModelList);
		if(page == 1){
			pageBean.setPrevious(page);
		}else{
			pageBean.setPrevious(page - 1);
		}

		if(page < pageInfos.getPages()){
			pageBean.setNext(page + 1);
		}else{
			pageBean.setNext(page);
		}
		pageBean.setTotal((int)pageInfos.getTotal());
		pageBean.setTotalPage(pageInfos.getPages());
		pageBean.setCurPage(pageInfos.getPageNum());

		pageBean.setRows(FacadeModelList);
		return pageBean;
	}
	
	/**
	 * 获取我的模型，如果frontName为null，则不会获取相关用户信息
	 * 这里的参数type后来被弃用，注意
	 */
	@RequestMapping(value = "/mymodels")
	@ResponseBody
	public PageBean getMyModels(HttpServletRequest request, HttpServletResponse response) {
		String pageStr = request.getParameter("page"), treeIdStr = request.getParameter("treeId"),
				frontNameStr = request.getParameter("frontName"),type = request.getParameter("type");;
		int page;
		long treeId;
		String frontName;
		page = StringUtils.isNumeric(pageStr) ? Integer.valueOf(pageStr) : 1;
		treeId = StringUtils.isNumeric(treeIdStr) ? Integer.valueOf(treeIdStr) : 0;
		frontName = StringUtils.isBlank(frontNameStr) ? "" : frontNameStr;
		List<ModelFrontUser> FacadeModelList = modelService.getMyModels(treeId, page, frontName, type);
		// 设置返回的总记录数
		PageBean pageBean = new PageBean();
		PageInfo<ModelFrontUser> pageInfos = new PageInfo<ModelFrontUser>(FacadeModelList);
		if(page == 1){
			pageBean.setPrevious(page);
		}else{
			pageBean.setPrevious(page - 1);
		}
		
		if(page < pageInfos.getPages()){
			pageBean.setNext(page + 1);
		}else{
			pageBean.setNext(page);
		}
		pageBean.setTotal((int)pageInfos.getTotal());
		pageBean.setTotalPage(pageInfos.getPages());
		pageBean.setCurPage(pageInfos.getPageNum());
		
		pageBean.setRows(FacadeModelList);
		return pageBean;
	}
	
	/**
	 * 获取我的申请，如果frontName为null，则不会获取相关用户信息
	 */
	@RequestMapping(value = "/myapplies")
	@ResponseBody
	public PageBean getMyApplies(HttpServletRequest request, HttpServletResponse response) {
		String pageStr = request.getParameter("page"), treeIdStr = request.getParameter("treeId"),
				frontNameStr = request.getParameter("frontName"), type = request.getParameter("type");
		int page;
		long treeId;
		String frontName;
		page = StringUtils.isNumeric(pageStr) ? Integer.valueOf(pageStr) : 1;
		treeId = StringUtils.isNumeric(treeIdStr) ? Integer.valueOf(treeIdStr) : 0;
		frontName = StringUtils.isBlank(frontNameStr) ? "" : frontNameStr;
		List<ModelFrontUser> FacadeModelList = modelService.getMyApply(treeId, page, frontName, type);
		// 设置返回的总记录数
		PageBean pageBean = new PageBean();
		PageInfo<ModelFrontUser> pageInfos = new PageInfo<ModelFrontUser>(FacadeModelList);
		if(page == 1){
			pageBean.setPrevious(page);
		}else{
			pageBean.setPrevious(page - 1);
		}
		
		if(page < pageInfos.getPages()){
			pageBean.setNext(page + 1);
		}else{
			pageBean.setNext(page);
		}
		pageBean.setTotal((int)pageInfos.getTotal());
		pageBean.setTotalPage(pageInfos.getPages());
		pageBean.setCurPage(pageInfos.getPageNum());
		
		pageBean.setRows(FacadeModelList);
		return pageBean;
	}
	
	@RequestMapping("/model")
	@ResponseBody
	public ModelDetail getModel(HttpServletRequest request, HttpServletResponse response){
		String modelId = request.getParameter("modelId");
		int id = StringUtils.isNumeric(modelId)?Integer.valueOf(modelId).intValue():1;
		return modelService.getModel(id);
	}
	
	@RequestMapping("/applies")
	@ResponseBody
	public PageBean getApplies(HttpServletRequest request, HttpServletResponse response){
		String pageStr = request.getParameter("page"), frontNameStr = request.getParameter("frontName"),
				type = request.getParameter("type"), treeIdStr = request.getParameter("treeId");
		int page;
		String frontName;
		long treeId;
		page = StringUtils.isNumeric(pageStr) ? Integer.valueOf(pageStr) : 1;
		frontName = StringUtils.isBlank(frontNameStr) ? "" : frontNameStr;
		treeId = StringUtils.isNumeric(treeIdStr) ? Integer.valueOf(treeIdStr) : 0;
		List<ModelFrontUser> FacadeModelList = modelService.getMyApply(treeId, page, frontName, type);
		// 设置返回的总记录数
		PageBean pageBean = new PageBean();
		PageInfo<ModelFrontUser> pageInfos = new PageInfo<ModelFrontUser>(FacadeModelList);
		if(page == 1){
			pageBean.setPrevious(page);
		}else{
			pageBean.setPrevious(page - 1);
		}
		
		if(page < pageInfos.getPages()){
			pageBean.setNext(page + 1);
		}else{
			pageBean.setNext(page);
		}
		pageBean.setTotal((int)pageInfos.getTotal());
		pageBean.setTotalPage(pageInfos.getPages());
		pageBean.setCurPage(pageInfos.getPageNum());
		
		pageBean.setRows(FacadeModelList);
		return pageBean;
	}

}
