package org.com.drag.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.hadoop.fs.*;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Charsets;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.StringUtils;
import org.com.drag.model.*;
import org.com.drag.service.*;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.service.oozie.bean.FileInfo;
import org.com.drag.web.common.CustomMultipartResolver;
import org.com.drag.web.util.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.com.drag.common.util.MailAuthorSetting.HADOOP_HDFS_URL;
import static org.com.drag.common.util.MailAuthorSetting.HDFS_PATH_FEIX;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.
 * 
 * @ClassName: WorkFlowController
 * 
 * @Description: 工作电子流Controller
 * 
 * @author: jiaonanyue
 * 
 * @date: 2016年11月3日 上午9:58:23
 */
@Controller
@RequestMapping("/drag/flow")
public class WorkFlowController {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	//excel导出时候的标识
	private static final String EXCEL_PASSWORD = "cdyouedata";
	public static final int DEFAULT_PAGENUM=1;
	@Autowired
	private WorkFlowService workFolwService;
	@Autowired
	private WorkSpaceService workSpaceService;
	@Autowired
	private IHdfsApi iHdfsApi;
	@Autowired
	private FacadeModelService facadeModelService;
    @Autowired
	private FacadeUploadInfoService facadeUploadInfoService;
	@Autowired
	private SchedulerJobService schedulerJobService;
	@Autowired
	private UploadHistoryService uploadHistoryService;
	@Autowired
	private CalculationHistoryService calculationHistoryService;
	@Autowired
	private IOozieApi iOozieApi;
	@Autowired
	private DataModelService dataModelService;
	@Autowired
	private ModelTrainedService modelTrainedService;
	@Autowired
	private ReadResourceService<ResourceInfo> readResourceService;
	@Autowired
	private DatabaseConnectService databaseConnectService;
	@Autowired
	private DatabaseTypeService databaseTypeService;



	/**每页显示数据条数*/
	private static final Integer PAGE_NUMBER = 5;
	/**
	 * 已训练的模型
	 */
	private static final String MODEL_NAME="已训练的模型";
	/*
	 *两输出的算子，hdfs保存数据剩下的一部分路径
	 */
	private static final String RESULT_DATA_PATH="/transformed/data";
	/*
	 *两输出的算子，hdfs保存模型属性剩下的一部分路径
	 */
	private static final String RESULT_MODEL_PATH="/transformed/modelattr";
	/*
	 *查看运行结果hdfs路径下数据的过滤
	 */
	private static final String RESULT_DATA_FILTER="part";
	/*
     *查看运行结果hdfs路径下model的过滤
     */
	private static final String RESULT_MODEL_FILTER="attr";



	@ResponseBody
	@RequestMapping("selectByCreateTime")
	public WorkFlow selectByCreateTime(HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		WorkFlow workFlow;
		workFlow =	workFolwService.selectByCreateTime(user.getLoginname());
		return workFlow;
	}




    @RequestMapping("selectAllWorkFlow")
	@ResponseBody
	public PageBean selectAllWorkFlow(Integer page,Integer rowCount,HttpSession session){
		if (page==null|| page==0){
			page=1;
		}
		if (rowCount==null){
			rowCount=PAGE_NUMBER;
		}
		User user = (User) session.getAttribute(Constants.USER_KEY);
		WorkFlow workFlow = new WorkFlow();
		workFlow.setUserName(user.getLoginname());
		List<WorkFlow> workFlowList = workFolwService.selectAllWorkFlow(workFlow);
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		List filelist=new ArrayList<>();
		int startItem=(page-1)*rowCount;
		int endItem=page*rowCount-1;
		int totalPage=0;
		if (rowCount>workFlowList.size()){
			totalPage=1;
		}else{
			totalPage=workFlowList.size()%rowCount==0?workFlowList.size()/rowCount:workFlowList.size()/rowCount+1;
		}

		if (endItem>workFlowList.size()-1){
			endItem=workFlowList.size()-1;
		}
		PageBean pageBean = new PageBean();

		pageBean.setTotal(workFlowList.size());
		pageBean.setTotalPage(totalPage);
		pageBean.setCurPage(page);

		for (int i=startItem;i<=endItem;i++){
			filelist.add(workFlowList.get(i));
		}
		pageBean.setRows(filelist);



		return pageBean;
	}

    @RequestMapping("selectFlowByName")
	@ResponseBody
	public PageBean selectFlowByName(Integer page,Integer rowCount,String name,HttpSession session){
		if (page==null|| page==0){
			page=1;
		}
		if (rowCount==null){
			rowCount=PAGE_NUMBER;
		}
		User user = (User) session.getAttribute(Constants.USER_KEY);
		WorkFlow workFlow = new WorkFlow();
		workFlow.setUserName(user.getLoginname());
		workFlow.setName(name);
		List<WorkFlow> workFlowList = workFolwService.selectWorkFlowName(workFlow);
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		List filelist=new ArrayList<>();
		int startItem=(page-1)*rowCount;
		int endItem=page*rowCount-1;
		int totalPage=0;
		if (rowCount>workFlowList.size()){
			totalPage=1;
		}else{
			totalPage=workFlowList.size()%rowCount==0?workFlowList.size()/rowCount:workFlowList.size()/rowCount+1;
		}

		if (endItem>workFlowList.size()-1){
			endItem=workFlowList.size()-1;
		}
		PageBean pageBean = new PageBean();

		pageBean.setTotal(workFlowList.size());
		pageBean.setTotalPage(totalPage);
		pageBean.setCurPage(page);

		for (int i=startItem;i<=endItem;i++){
			filelist.add(workFlowList.get(i));
		}
		pageBean.setRows(filelist);



		return pageBean;
	}
	/**
	 * 保存于创建/创建工作电子流
	 * 
	 * @param modelStr
	 * @param connectStr
	 * @param blocksStr
	 * @param flowId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "createflow", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult CreateWorkFlow(String fieldStr,String modelStr, String connectStr, String blocksStr, String notesStr, Integer flowId) {
		// [{"configId":"5","configValue":"Category
		// 1"},{"configId":"6","configValue":"32444"}]
		
		    List<Connect> connects = JSON.parseArray(connectStr, Connect.class);
	        List<Model> models =JSON.parseArray(modelStr,Model.class);
	        //简要检查数据格式
	        if(connects.size()==0 || models.size()==0){
	            return new ResponseResult(HttpStatus.EXPECTATION_FAILED);
	        }
	       //保存工作流
	        WorkFlow workFlow = new WorkFlow();
	        workFlow.setId(flowId);
	        workFlow.setUpdatTime(new Date());
	        JSONObject contextJson = new JSONObject();
	        contextJson.put("models", modelStr);
	        contextJson.put("connects", connectStr);
	        contextJson.put("blocks", blocksStr);
		    contextJson.put("notes", notesStr);
		    contextJson.put("fields",fieldStr);
	        workFlow.setContext(contextJson.toString());
	        workFolwService.updateByPrimaryKeySelective(workFlow);
	        
	  /*      
		WorkFlow workFlow = new WorkFlow();

		JSONObject contextJson = new JSONObject();
		contextJson.put("models", modelStr);
		contextJson.put("connects", connectStr);
		contextJson.put("blocks", blocksStr);
		workFlow.setContext(contextJson.toString());

		workFlow.setId(flowId);
		workFlow.setContext(contextJson.toString());
		workFlow.setUpdatTime(new Date());
		workFolwService.updateByPrimaryKeySelective(workFlow);*/

		// workFolwService.insertWorkSpaceByLogic(workFlowDto);
		return new ResponseResult(HttpStatus.OK);
	}

	/**
	 * 不带业务逻辑保存
	 * 
	 * @param session
	 * @param request
	 * @param workFlow
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult CreateWorkFlow(HttpSession session, HttpServletRequest request, WorkFlow workFlow) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			// 判断工作空间下是否有同名的电子流
			WorkFlow wok = new WorkFlow();
			wok.setName(workFlow.getName());
			wok.setWorkspid(workFlow.getWorkspid());
			List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(wok);

			if (workFlowList != null && workFlowList.size() > 0) {

				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "存在相同的工作流");

			} else {
				// 创建对应hdfs目录
				// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				WorkSpace workSpace = workSpaceService.selectByPrimaryKey(workFlow.getWorkspid());
				Boolean b = iHdfsApi.createUserWorkspace(workSpace.getHdfsUrl(workSpace) + "/" + workFlow.getName(),
						user.getLoginname(), true);
				if (b) {
					workFlow.setHdfsUrl(workSpace.getHdfsUrl() + "/" + workFlow.getName());
					workFlow.setCreateTime(new Date());
					workFlow.setUserName(user.getLoginname());
					int a = workFolwService.insert(workFlow);
					if (a != -1) {
						return new ResponseResult(HttpStatus.OK, "创建工作流成功");
					} else {
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "创建工作流失败");
					}
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "创建工作流失败");
				}

			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

		/*
		 * String path =
		 * request.getScheme()+"://"+request.getServerName()+":"+request.
		 * getServerPort()+
		 * request.getContextPath()+"/drag/flow/getflowlist.do?workspid="+
		 * workFlow.getWorkspid()+"&workName="+workFlow.getWorkName(); return
		 * "redirect:"+path;
		 */

	}

	/**
	 * 执行电子流
	 * 
	 * @return
	 */
	@RequestMapping(value = "implement", method = RequestMethod.POST)
	public String implement() {

		return "";
	}

	/**
	 * 通过用户工作空间id查询电子流
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getflowlist", method = RequestMethod.GET)
	public String getWorkFlowList(Model model, WorkFlow workFlow, int page, HttpSession session) {
		WorkSpace workSpace = null;
		if (workFlow != null && workFlow.getWorkspid() != null) {
			workSpace = workSpaceService.selectByPrimaryKey(workFlow.getWorkspid());
		}
		if (page == 0) {
			page = 1;
		}
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(workFlow);
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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

		User user = (User) session.getAttribute(Constants.USER_KEY);
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}
		WorkSpaceFlow workSpaceFlow = new WorkSpaceFlow();
		workSpaceFlow.setWorkFLowList(workFlowList);
		workSpaceFlow.setWorkSpaceId(workFlow.getWorkspid().toString());
		workSpaceFlow.setWorkSpaceName(workFlow.getWorkName());
		model.addAttribute("workFlowList", workSpaceFlow);
		model.addAttribute("CreateContent", workSpace.getCreateContent());
		return "/workSpace/workSpaceDetails";
	}

	@RequestMapping(value = "getflow", method = RequestMethod.GET)
	public String getWorkFlow(Model model, WorkFlow workFlow, HttpSession session) {
		WorkSpace workSpace = null;
		if (workFlow != null && workFlow.getWorkspid() != null) {
			workSpace = workSpaceService.selectByPrimaryKey(workFlow.getWorkspid());
		}
		workFlow.setCreateContent(workSpace.getCreateContent());
		model.addAttribute("WorkFlow", workFlow);
		return "/workSpace/workSpaceDetails";
	}

	/**
	 * 导出工作流
	 * 
	 * @param request
	 * @param resp
	 * @param workFlow
	 * @throws IOException
	 */
	@RequestMapping(value = "selectExportFlow")
	@ResponseBody
	public void selectExportFlow(HttpServletRequest request, HttpServletResponse resp, WorkFlow workFlow)
			throws IOException {

		try {
			if (null == request || null == resp) {
				return;
			}
			List<WorkFlow> listContent = workFolwService.batchSelectWorkFlow(workFlow);
			// 生成Excel文件
//			exportExcel(request, resp, listContent);
			//生成xml文件
			exportXml(request,resp,listContent);
		} catch (Exception e1) {
			LOGGER.error("=====导出excel异常====",e1);
		}

	}

	/**
	 * 工作流导出成xml文件
	 * @param request
	 * @param resp
	 * @param listContent   工作流列表
	 * @throws UnsupportedEncodingException
	 */
	private void exportXml(HttpServletRequest request, HttpServletResponse resp, List<WorkFlow> listContent) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String fileName="";
		for (int i= 0;i<listContent.size();i++){
			fileName=fileName+listContent.get(i).getName();
			if (i!=listContent.size()-1){
				fileName=fileName+"&&";
			}
		}
		fileName=fileName+".xml";
		resp.setContentType("multipart/form-data");

		String userAgent = request.getHeader("User-Agent");

		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		resp.setHeader("Content-disposition", "attachment; filename="+fileName+"");
		String xmlContent;
		OutputStream out = null;
		try {
		if (listContent.size()==1){
			com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(listContent.get(0).getContext());
			jsonObject.put("flowName",listContent.get(0).getName());
			jsonObject.put("flowExplain",listContent.get(0).getFlowExplain());
			String curFlow = jsonObject.toString();
			xmlContent=XmlConvertUtil.json2Xml(curFlow);
		}else  {
			String[] jsonFlows=new String [listContent.size()];
			int i=0;
			for (WorkFlow workFlow : listContent) {
				com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(workFlow.getContext());
				jsonObject.put("flowName",workFlow.getName());
				jsonObject.put("flowExplain",workFlow.getFlowExplain());
				String curFlow = jsonObject.toString();
				jsonFlows[i]=curFlow;
				i++;
			}
			xmlContent= XmlConvertUtil.json2Xml(jsonFlows);
		}
			out = resp.getOutputStream();
			byte [] xmlbyte=xmlContent.getBytes();
			out.write(xmlbyte);
			out.flush();
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 通过用户工作空间id查询电子流
	 * 
	 * @param workFlow
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getflowlists", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getWorkFlowLists( WorkFlow workFlow, Integer page,Integer rowCount, HttpSession session) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (rowCount==null){
			rowCount=5;
		}

		List<WorkFlow> workFlowList = null;
		workFlowList = workFolwService.selectWorkFlow(workFlow);
		//将文件下所有的工作流都查询出来
		List<WorkSpace> menuList = workSpaceService.selectAll(new WorkSpace());
		List<Integer> ids =  getChildNodes(menuList,workFlow.getWorkspid(),new LinkedList());
		for(Integer id : ids ){
			WorkFlow flow = new WorkFlow();
			flow.setWorkspid(id);
			List<WorkFlow> workFlows = workFolwService.selectWorkFlow(flow);
			if(workFlows != null){
				for (int i = 0; i <workFlows.size() ; i++) {
					workFlowList.add(workFlows.get(i));
				}
			}
		}

		User user = (User) session.getAttribute(Constants.USER_KEY);
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}

		List filelist=new ArrayList<>();
		int startItem=(page-1)*rowCount;
		int endItem=page*rowCount-1;
		int totalPage=0;
		if (rowCount>workFlowList.size()){
			totalPage=1;
		}else{
			totalPage=workFlowList.size()%rowCount==0?workFlowList.size()/rowCount:workFlowList.size()/rowCount+1;
		}

		if (endItem>workFlowList.size()-1){
			endItem=workFlowList.size()-1;
		}
		PageBean pageBean = new PageBean();

		pageBean.setTotal(workFlowList.size());
		pageBean.setTotalPage(totalPage);
		pageBean.setCurPage(page);

		for (int i=startItem;i<=endItem;i++){
			filelist.add(workFlowList.get(i));
		}
		pageBean.setRows(filelist);


		return pageBean;

	}

	/**
	 * 通过用户工作空间id查询电子流
	 * 
	 * @param  workFlow
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "getflowlistname", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getWorkFlowListName(Model model, WorkFlow workFlow, Integer page, HttpSession session) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = workFolwService.selectWorkFlowName(workFlow);
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		User user = (User) session.getAttribute(Constants.USER_KEY);
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}
	
	
	

	/**
	 * 更新工作电子流
	 * 
	 * @param workFlow
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateWorkFlow(HttpSession session, WorkFlow workFlow) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			// 判断工作空间下是否有同名的电子流
			WorkFlow workFlowName = new WorkFlow();
			workFlowName.setName(workFlow.getName());
			List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(workFlowName);

			if (workFlowList != null && workFlowList.size() == 1) {
				if(workFlowList.get(0).getName().equals(workFlow.getName()) && workFlowList.get(0).getFlowExplain().equals(workFlow.getFlowExplain())){
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "未作修改！");
				}else if(!workFlowList.get(0).getFlowExplain().equals(workFlow.getFlowExplain())){
					int b = workFolwService.updateByPrimaryKeySelective(workFlow);
					if (b == 1) {
						return new ResponseResult(HttpStatus.OK, "修改工作流成功");
					} else {
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
					}
				}

				// 创建对应hdfs目录
				// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				WorkFlow workFlows = workFolwService.selectByPrimaryKey(workFlow.getId());
				String hdfs = workFlows.getHdfsUrl();
				int c = hdfs.length() - workFlows.getName().length();
				String hdfs1 = hdfs.substring(0, c);
				Boolean a = iHdfsApi.renameDirectoryOrFile(workFlows.getHdfsUrl(), workFlow.getName(),
						user.getLoginname());
				// 查询对应的工作空间信息
				if (a) {

					workFlow.setHdfsUrl(hdfs1 + workFlow.getName());
					workFlow.setUpdatTime(new Date());

					int b = workFolwService.updateByPrimaryKeySelective(workFlow);
					if (b == 1) {
						return new ResponseResult(HttpStatus.OK, "修改工作流成功");
					} else {
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
					}
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
				}
			}
			else if (workFlowList != null && workFlowList.size() > 1) {
				for (int i = 0; i <workFlowList.size() ; i++) {
					if(workFlowList.get(i).getName().equals(workFlow.getName()) &&
							workFlowList.get(i).getFlowExplain().equals(workFlow.getFlowExplain())
							&& workFlowList.get(i).getId().equals(workFlow.getId())){
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "未作修改！");
					}else if(workFlowList.get(i).getId().equals(workFlow.getId())
							&& workFlowList.get(i).getName().equals(workFlow.getName())
							&& !workFlowList.get(i).getFlowExplain().equals(workFlow.getFlowExplain())){
						int b = workFolwService.updateByPrimaryKeySelective(workFlow);
						if (b == 1) {
							return new ResponseResult(HttpStatus.OK, "修改工作流成功");
						} else {
							return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
						}
					}



				}

				// 创建对应hdfs目录
				// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				WorkFlow workFlows = workFolwService.selectByPrimaryKey(workFlow.getId());
				String hdfs = workFlows.getHdfsUrl(workFlows);
				int c = hdfs.length() - workFlows.getName().length();
				String hdfs1 = hdfs.substring(0, c);
				Boolean a = iHdfsApi.renameDirectoryOrFile(workFlows.getHdfsUrl(workFlows), workFlow.getName(),
						user.getLoginname());
				// 查询对应的工作空间信息
				if (a) {

					workFlow.setHdfsUrl(hdfs1 + workFlow.getName());
					workFlow.setUpdatTime(new Date());

					int b = workFolwService.updateByPrimaryKeySelective(workFlow);
					if (b == 1) {
						return new ResponseResult(HttpStatus.OK, "修改工作流成功");
					} else {
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
					}
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
				}
			}

			else {

				// 创建对应hdfs目录
				// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				WorkFlow workFlows = workFolwService.selectByPrimaryKey(workFlow.getId());
				String hdfs = workFlows.getHdfsUrl();
				int c = hdfs.length() - workFlows.getName().length();
				String hdfs1 = hdfs.substring(0, c);
				Boolean a = iHdfsApi.renameDirectoryOrFile(workFlows.getHdfsUrl(), workFlow.getName(),
						user.getLoginname());
				// 查询对应的工作空间信息
				if (a) {

					workFlow.setHdfsUrl(hdfs1 + workFlow.getName());
					workFlow.setUpdatTime(new Date());

					int b = workFolwService.updateByPrimaryKeySelective(workFlow);
					if (b == 1) {
						return new ResponseResult(HttpStatus.OK, "修改工作流成功");
					} else {
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
					}
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改工作流失败");
				}

			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}

	/**
	 * 查询训练好的模型
	 * @param model
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getmodel", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getmodelList(Model model, Integer page, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		//Integer[] idList ={1,2,3,4,5};
		Integer[] idList ={1};
		workFlow.setIdList(idList);
		workFlow.setUserName(user.getLoginname());
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(workFlow);
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	/**
	 * 查询模型状态
	 * @param model
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getmodelType", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getmodelType(Model model, Integer page, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		//Integer[] idList ={1,2,3,4,5};
		Integer[] idList ={1,2,6};
		workFlow.setIdList(idList);
		workFlow.setUserName(user.getLoginname());
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(workFlow);
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 6){
					ws.setTypeName("审核不通过");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	/**
	 * 模糊查询训练好的模型
	 * @param inputname
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getmodelname", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getmodelListName(String inputname,Integer page, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		Integer[] idList ={1};
		workFlow.setIdList(idList);
		workFlow.setUserName(user.getLoginname());
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = new ArrayList<WorkFlow>();
		if (inputname==""||inputname==null){
			workFlowList = workFolwService.selectWorkFlow(workFlow);
		}else{
			workFlow.setName(inputname);
			workFlowList = workFolwService.selectWorkFlowName(workFlow);
		}
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	/**
	 * 模糊查询模型状态
	 * @param inputname
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getmodelnameType", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getmodelnameType(String inputname,Integer page, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		Integer[] idList ={1,2,3,4,5};
		workFlow.setIdList(idList);
		workFlow.setUserName(user.getLoginname());
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = new ArrayList<WorkFlow>();
		if (inputname==""||inputname==null){
			workFlowList = workFolwService.selectWorkFlow(workFlow);
		}else{
			workFlow.setName(inputname);
			workFlowList = workFolwService.selectWorkFlowName(workFlow);
		}
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				ws.setUserMial(user.getemail());
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	
	/**
	 * 查询所有用户的状态数据
	 * @param model
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getModelAudit", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getModelAudit(Model model, Integer page, HttpSession session,Integer type) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		
		if(type == 1 ||type == 2 || type == 6){
			
			WorkFlow workFlow = new WorkFlow();
			Integer[] idList ={type};
			workFlow.setIdList(idList);
			PageHelper.startPage(page, 10); // 核心分页代码 测试
			List<WorkFlow> workFlowList = workFolwService.selectModelAudit(workFlow);
			// 设置返回的总记录数
			PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);

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
			if (workFlowList != null && workFlowList.size() > 0) {
				for (WorkFlow ws : workFlowList) {
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
					if(ws.getType() == 2){
						ws.setTypeName("待审核");
					}
					if(ws.getType() == 1){
						ws.setTypeName("待上传");
					}
					/*if(ws.getType() == 4){
						ws.setTypeName("已经审核");
					}
					if(ws.getType() == 5){
						ws.setTypeName("已经审核");
					}*/
					if(ws.getType() == 6){
						ws.setTypeName("审核不通过");
					}
				}
			}
			pageBean.setTotal(pageInfos.getPages());
			pageBean.setCurPage(page);
			pageBean.setRows(workFlowList);
		}
		
		//审核通过的
		if(type == 8 ){
			FacadeModel fm = new FacadeModel();
			
			Integer[] idList ={3,4,5};
			fm.setIdList(idList);
			PageHelper.startPage(page, 10); // 核心分页代码 测试
			List<FacadeModel> FacadeModelList = facadeModelService.selectFacadeModelByType(fm);
			// 设置返回的总记录数
			PageInfo<FacadeModel> pageInfos = new PageInfo<FacadeModel>(FacadeModelList);

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
			if (FacadeModelList != null && FacadeModelList.size() > 0) {
				for (FacadeModel ws : FacadeModelList) {
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					ws.setCreateTimes(dateformat.format(ws.getCreatime()));
					if(ws.getType() == 3){
						ws.setTypeName("审核通过");
					}
					if(ws.getType() == 4){
						ws.setTypeName("审核通过");
					}
					if(ws.getType() == 5){
						ws.setTypeName("审核通过");
					}
				}
			}
			pageBean.setTotal(pageInfos.getPages());
			pageBean.setCurPage(page);
			pageBean.setRows(FacadeModelList);
		}
		
		return pageBean;

	}

	/**
	 * 查看用户的状态
	 * @param model
	 * @param page
	 * @param session
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "getModelNameTypes", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getModelNameTypes(Model model, Integer page, HttpSession session,Integer type) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		
		if(type == 1 ||type == 2 || type == 6){
			
			WorkFlow workFlow = new WorkFlow();
			Integer[] idList ={type};
			workFlow.setIdList(idList);
			workFlow.setUserName(user.getLoginname());
			PageHelper.startPage(page, 10); // 核心分页代码 测试
			List<WorkFlow> workFlowList = workFolwService.selectModelAudit(workFlow);
			// 设置返回的总记录数
			PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);

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
			if (workFlowList != null && workFlowList.size() > 0) {
				for (WorkFlow ws : workFlowList) {
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
					if(ws.getType() == 2){
						ws.setTypeName("待审核");
					}
					if(ws.getType() == 1){
						ws.setTypeName("待上传");
					}
					/*if(ws.getType() == 4){
						ws.setTypeName("已经审核");
					}
					if(ws.getType() == 5){
						ws.setTypeName("已经审核");
					}*/
					if(ws.getType() == 6){
						ws.setTypeName("审核不通过");
					}
				}
			}
			pageBean.setTotal(pageInfos.getPages());
			pageBean.setCurPage(page);
			pageBean.setRows(workFlowList);
		}
		
		//审核通过的
		if(type == 3 || type == 4 ){
			FacadeModel fm = new FacadeModel();
			
			Integer[] idList ={type};
			fm.setIdList(idList);
			fm.setUserName(user.getLoginname());
			PageHelper.startPage(page, 10); // 核心分页代码 测试
			List<FacadeModel> FacadeModelList = facadeModelService.selectFacadeModel(fm);
			// 设置返回的总记录数
			PageInfo<FacadeModel> pageInfos = new PageInfo<FacadeModel>(FacadeModelList);

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
			if (FacadeModelList != null && FacadeModelList.size() > 0) {
				for (FacadeModel ws : FacadeModelList) {
					SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					ws.setCreateTimes(dateformat.format(ws.getCreatime()));
					if(ws.getType() == 3){
						ws.setTypeName("待发布");
					}
					if(ws.getType() == 4){
						ws.setTypeName("已经发布");
					}
					if(ws.getType() == 5){
						ws.setTypeName("审核通过");
					}
				}
			}
			pageBean.setTotal(pageInfos.getPages());
			pageBean.setCurPage(page);
			pageBean.setRows(FacadeModelList);
		}
		
		return pageBean;

	}

	/**
	 * 模糊查询审核的数据
	 * @param workName
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "getModelAuditName", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getModelAuditName(String workName, Integer page, HttpSession session) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		//workFlow.setType(1);
		Integer[] idList ={2};
		workFlow.setIdList(idList);
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList= new ArrayList<WorkFlow>();
		if (workName==""||workName==null){
			 workFlowList = workFolwService.selectModelAudit(workFlow);
		}else{
			workFlow.setName(workName);
			 workFlowList = workFolwService.selectModelAuditName(workFlow);
		}
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	/**
	 * 查询模型发布数据
	 * @param model
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "modelRelease", method = RequestMethod.POST)
	@ResponseBody
	public PageBean modelRelease(Model model, Integer page, HttpSession session,Integer type) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		
		
		FacadeModel fm = new FacadeModel();
		Integer[] idList ={type};
		fm.setIdList(idList);
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<FacadeModel> workFlowList =facadeModelService.selectFacadeModelByType(fm);
		//List<WorkFlow> workFlowList = workFolwService.selectModelAudit(workFlow);
		// 设置返回的总记录数
		PageInfo<FacadeModel> pageInfos = new PageInfo<FacadeModel>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (FacadeModel ws : workFlowList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreatime()));
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}


	/**
	 * 模糊查询模型发布数据
	 * @param workName
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "modelReleaseName", method = RequestMethod.POST)
	@ResponseBody
	public PageBean modelReleaseName(String workName, Integer page, HttpSession session) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		WorkFlow workFlow = new WorkFlow();
		//workFlow.setType(1);
		Integer[] idList ={3};
		workFlow.setIdList(idList);
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<WorkFlow> workFlowList = new ArrayList<WorkFlow>();
		if (workName==""||workName==null){
			workFlowList = workFolwService.selectModelAudit(workFlow);
		}else{
			workFlow.setName(workName);
			workFlowList = workFolwService.selectModelAuditName(workFlow);
		}
		// 设置返回的总记录数
		PageInfo<WorkFlow> pageInfos = new PageInfo<WorkFlow>(workFlowList);
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
		if (workFlowList != null && workFlowList.size() > 0) {
			for (WorkFlow ws : workFlowList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(workFlowList);

		return pageBean;

	}

	/**
	 * 模型下线查询
	 * @param model
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "selectOffline", method = RequestMethod.POST)
	@ResponseBody
	public PageBean selectOffline(Model model, Integer page, HttpSession session) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		FacadeModel facadeModel = new FacadeModel();
		//workFlow.setType(1);
		Integer[] idList ={4,5};
		facadeModel.setIdList(idList);
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<FacadeModel> facadeModelList = facadeModelService.selectFacadeModelByType(facadeModel);
		// 设置返回的总记录数
		PageInfo<FacadeModel> pageInfos = new PageInfo<FacadeModel>(facadeModelList);
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
		if (facadeModelList != null && facadeModelList.size() > 0) {
			for (FacadeModel ws : facadeModelList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreatime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(facadeModelList);

		return pageBean;

	}

	/**
	 * 模型下线模糊查询
	 * @param inputname
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "selectOfflineName", method = RequestMethod.POST)
	@ResponseBody
	public PageBean selectOfflineName(String inputname, Integer page, HttpSession session) {
		PageBean pageBean = new PageBean();
		if (page == null || page == 0) {
			page = 1;
		}
		FacadeModel facadeModel = new FacadeModel();
		//workFlow.setType(1);
		Integer[] idList ={4,5};
		facadeModel.setIdList(idList);
		PageHelper.startPage(page, 10); // 核心分页代码 测试
		List<FacadeModel> facadeModelList = new ArrayList<FacadeModel>();
		if (inputname==""||inputname==null){
			facadeModelList = facadeModelService.selectFacadeModelByType(facadeModel);
		}else{
			facadeModel.setName(inputname);
			facadeModelList = facadeModelService.selectFacadeModelByTypeName(facadeModel);
		}
		// 设置返回的总记录数
		PageInfo<FacadeModel> pageInfos = new PageInfo<FacadeModel>(facadeModelList);
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
		if (facadeModelList != null && facadeModelList.size() > 0) {
			for (FacadeModel ws : facadeModelList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				ws.setCreateTimes(dateformat.format(ws.getCreatime()));
				if(ws.getType() == 1){
					ws.setTypeName("待上传");
				}
				if(ws.getType() == 2){
					ws.setTypeName("待审核");
				}
				if(ws.getType() == 3){
					ws.setTypeName("待发布");
				}
				if(ws.getType() == 4){
					ws.setTypeName("已经发布");
				}
				if(ws.getType() == 5){
					ws.setTypeName("已经下线");
				}
			}
		}

		pageBean.setTotal(pageInfos.getPages());
		pageBean.setCurPage(page);
		pageBean.setRows(facadeModelList);

		return pageBean;

	}

	
	
	/**
	 * 提交训练完成的模型
	 * 
	 * @param session
	 * @param workFlow
	 * @return
	 */
	@RequestMapping(value = "updateFlow", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateWorkFlowModel(HttpSession session, WorkFlow workFlow) {
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			User user = (User) session.getAttribute(Constants.USER_KEY);
			workFlow.setType(1);
			workFlow.setUserName(user.getLoginname());
			int b = workFolwService.updateByPrimaryKeySelective(workFlow);
			if (b == 1) {
				return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}
	
	
	/**
	 * 修改接口
	 * 提交训练完成的模型
	 * 
	 * @param session
	 * @param workFlow
	 * @return
	 */
	/*@RequestMapping(value = "updateFlows", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateWorkFlowModels(HttpSession session, WorkFlow workFlow) {
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			User user = (User) session.getAttribute(Constants.USER_KEY);
			
			FacadeModel record = new FacadeModel();
			
			facadeModelService.insertSelective(record);
			workFlow.setUserName(user.getLoginname());
			int b = workFolwService.updateByPrimaryKeySelective(workFlow);
			if (b == 1) {
				return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}*/

	/**
	 * 修改工作流状态
	 * @param session
	 * @param workFlow
	 * @return
	 */
	@RequestMapping(value = "updateType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateFlowType(HttpSession session, @RequestBody WorkFlow workFlow) {
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			//审核通过就创建数据
			if(workFlow.getType() == 3){
				WorkFlow workFlows = new WorkFlow();
				workFlows.setId(workFlow.getId());						
				WorkFlow  wf = workFolwService.selectModelRelease(workFlows);
				FacadeModel fm = new FacadeModel();
				fm.setCreatime(new Date());
				fm.setFlowId(workFlow.getId());
				fm.setInterfacenum(0);
				fm.setModelContext(wf.getContext());
				fm.setName(wf.getName());
				fm.setType(workFlow.getType());
				fm.setUserName(wf.getUserName());
				fm.setFlowExplain(wf.getFlowExplain());
				fm.setWorkspaceName(wf.getWorkspaceName());
				int a = facadeModelService.insertSelective(fm);
				//更新状态
				workFolwService.updateByPrimaryKeySelective(workFlow);
				if (a == 1) {
					return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
				}
			}
			
			//
			if(workFlow.getType() == 4 || workFlow.getType() == 5){
				
				FacadeModel fm = new FacadeModel();
				fm.setId(workFlow.getId());
				fm.setType(workFlow.getType());
				int c = facadeModelService.updateByPrimaryKeySelective(fm);
				if (c == 1) {
					return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
				}
			}
			//取消发布
			if(workFlow.getType() == 7){
				FacadeModel fm = new FacadeModel();
				fm.setId(workFlow.getId());
				fm.setType(3);
				int c = facadeModelService.updateByPrimaryKeySelective(fm);
				if (c == 1) {
					return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
				}
			}
			//不通过
			if(workFlow.getType() == 6 || workFlow.getType() == 2){
				int b = workFolwService.updateByPrimaryKeySelective(workFlow);
				if (b == 1) {
					return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
				}
			}
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}
	
	
	
	@RequestMapping(value = "selectInfoByid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult selectInfoByid(HttpSession session, @RequestBody WorkFlow workFlow) {
		   ResponseResult responseResult = new ResponseResult();
		   FacadeUploadInfo fus = new FacadeUploadInfo();
		   fus.setFlowid(workFlow.getId());
		   List<FacadeUploadInfo> fu = facadeUploadInfoService.selectByFacadeUploadInfo(fus);
		   responseResult.setObj(fu.get(0));
			return responseResult;

	}
	
	
	/**
	 * 模型下线
	 * @param session
	 * @param facadeModel
	 * @return
	 */
	@RequestMapping(value = "modelTypeOffline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult modelTypeOffline(HttpSession session, @RequestBody FacadeModel facadeModel) {

		if (facadeModel != null && facadeModel.getId() != null) {
			facadeModel.setType(5);
			facadeModelService.updateByPrimaryKeySelective(facadeModel);
			WorkFlow workFlow = new WorkFlow();
			workFlow.setType(5);
			workFlow.setId(facadeModel.getFlowId());
			int b = workFolwService.updateByPrimaryKeySelective(workFlow);
			if (b == 1) {
				return new ResponseResult(HttpStatus.OK, "模型下线成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型下线失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}
	
	
	/**
	 * 模型发布
	 * @param session
	 * @param tid 目录树的id
	 * @param wfid 工作流的id
	 * @return
	 */
	@RequestMapping(value = "modelReleases", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult modelRelease(HttpSession session,Integer tid,Integer wfid) {

		if (tid!= null && wfid!= null) {
			
			FacadeModel fm = new FacadeModel();
			fm.setId(wfid);
			fm.setType(4);
			fm.setTreeid(tid);
			int a = facadeModelService.updateByPrimaryKeySelective(fm);
			if (a == 1) {
				return new ResponseResult(HttpStatus.OK, "模型发布成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型发布失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}

	
	@RequestMapping(value = "continueFlow", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult continueFlowModel(HttpSession session, WorkFlow workFlow) {
		// 前端只传了电子流的信息 保存
		if (workFlow != null) {
			User user = (User) session.getAttribute(Constants.USER_KEY);
			workFlow.setType(0);
			workFlow.setUserName(user.getLoginname());
			int b = workFolwService.updateByPrimaryKeySelective(workFlow);
			if (b == 1) {
				return new ResponseResult(HttpStatus.OK, "提交工作流模型成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "提交工作流模型失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}

	}

	
	/**
	 * 删除工作空间
	 * 
	 * @param workSpace
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteWorkFlow(@RequestBody WorkFlow workSpace, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		if (workSpace != null && workSpace.getId() != null) {

			WorkFlow workFlows = workFolwService.selectByPrimaryKey(workSpace.getId());
			// 删除工作空间hdfs目录
			// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
			// String hdfs = workFlows.getHdfsUrl().substring(24,
			// workFlows.getHdfsUrl().length());
			Boolean b = iHdfsApi.deleteFileOrDir(workFlows.getHdfsUrl(workFlows), user.getLoginname(), true);
			if (b) {

				workFolwService.deleteByPrimaryKey(workSpace.getId());

				return new ResponseResult(HttpStatus.OK, "删除工作流成功");
			} else {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除工作流失败");
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除工作流失败");
		}

	}

	/**
	 * 批量删除工作空间
	 * 
	 * @param workFlow
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "batchdel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult batchDelete(@RequestBody WorkFlow workFlow, HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);

		return workFolwService.BatchDeleteWorkSpace(workFlow, user);
	}

	@RequestMapping(value = "findHaveSchJob", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult findHaveSchJob(@RequestBody WorkFlow workFlow){
		if(workFlow != null && workFlow.getIds() != null && workFlow.getIds().size() > 0){
			List<Integer> idList = workFlow.getIds();
			Integer integer = null;
			String name = null;
			for(Integer ids : idList){
				 integer = schedulerJobService.findSchJobIdBySchJobWfId(ids);
				 if(null != integer){
					 name = schedulerJobService.findJobNameById(ids);
				 	break;
				 }
			}
			if(null != integer){
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED,name+":文件包含已设置调度任务的工作流，确定删除？");
			}else {
				return new ResponseResult(HttpStatus.OK,"该文件不存在已设置调度任务的可以删除！");
			}
		}

		return new ResponseResult(HttpStatus.OK,"不存在调度可以删除！");
	}


	/**
	 * 导入工作流
	 * 
	 * @param request
	 * @param workspid
	 * @param response
	 * @param session
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "importFlows", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult upload2(HttpServletRequest request, Integer workspid, HttpServletResponse response,
			HttpSession session) throws IllegalStateException, IOException {

		if (workspid != null) {
			User user = (User) session.getAttribute(Constants.USER_KEY);
			WorkSpace workSpace = workSpaceService.selectByPrimaryKey(workspid);
			// 创建一个通用的多部分解析器
			CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
			multipartResolver.setServletContext(request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			String path = null;
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();

				while (iter.hasNext()) {
					// 记录上传过程起始时的时间，用来计算上传时间
					int pre = (int) System.currentTimeMillis();
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					// 这个myfile是MultipartFile的
					int pre1 = (int) System.currentTimeMillis();

					int pre2 = 1;
					if (file != null) {
						// 取得当前上传文件的文件名称
						final String myFileName = file.getOriginalFilename();
						String [] names= myFileName.split("&&");
						try {
							// 读取本地xml文件
							String xmlStr = readXml2String(file.getInputStream());
							//将xml转成json
							String jsonContextStr=XmlConvertUtil.xml2Json(xmlStr);
							//单个文件
							if (names.length==1){
								//替换掉json后多出来的双引号
								jsonContextStr=jsonContextStr.replaceAll("\":\"\\\\\"","\":\"");
								jsonContextStr=jsonContextStr.replaceAll("\\\\\"\"","\"");
								com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(jsonContextStr);
								String flowName=jsonObj.get("flowName").toString();
								String flowExplain = jsonObj.get("flowExplain").toString();

								//创建hdfs路径
								Boolean b = iHdfsApi.createUserWorkspace(workSpace.getHdfsUrl(workSpace) + "/" + flowName, user.getLoginname(),
										true);
								if (b) {
									WorkFlow workFlow = new WorkFlow();
									workFlow.setContext(jsonContextStr);
									workFlow.setCreateTime(new Date());
									workFlow.setFlowExplain(flowExplain);
									workFlow.setName(flowName);
									workFlow.setWorkspid(workSpace.getId());
									workFlow.setHdfsUrl(workSpace.getHdfsUrl() + "/" + flowName);
									workFlow.setUserName(user.getLoginname());
									int a =workFolwService.insertSelective(workFlow);
									if(a==1){
										return new ResponseResult(HttpStatus.OK, "上传成功");
									}else{
										return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请上传本程序导出的xls文件");
									}
								}
							}else{
								//多个文件
								net.sf.json.JSONArray jsonObject = (JSONArray) JSONSerializer.toJSON(jsonContextStr);
								int count=0;
								for (int i=0;i<jsonObject.size();i++){
									String jsonStr=jsonObject.get(i).toString();
									//替换掉json后多出来的双引号
									jsonStr=jsonStr.replaceAll("\":\"\\\\\"","\":\"");
									jsonStr=jsonStr.replaceAll("\\\\\"\"","\"");
									com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(jsonStr);
									String flowName=jsonObj.get("flowName").toString();
									String flowExplain = jsonObj.get("flowExplain").toString();

									//创建hdfs路径
									Boolean b = iHdfsApi.createUserWorkspace(workSpace.getHdfsUrl(workSpace) + "/" + flowName, user.getLoginname(),
											true);
									if (b) {
										WorkFlow workFlow = new WorkFlow();
										workFlow.setContext(jsonStr);
										workFlow.setCreateTime(new Date());
										workFlow.setFlowExplain(flowExplain);
										workFlow.setName(flowName);
										workFlow.setWorkspid(workSpace.getId());
										workFlow.setHdfsUrl(workSpace.getHdfsUrl() + "/" + flowName);
										workFlow.setUserName(user.getLoginname());
										int a =workFolwService.insertSelective(workFlow);
										count= count+a;
									}
								}
								if(jsonObject.size() == count){
									return new ResponseResult(HttpStatus.OK, "上传成功");
								}else{
									return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请上传本程序导出的xls文件");
								}
							}
						}catch (Exception e){
							LOGGER.info("导出工作流异常");
							return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请上传本程序导出的xls文件");
						}
					}
				}

			}

			return new ResponseResult(HttpStatus.OK, "上传成功");
		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "上传失败");
		}

	}

	/**
	 * 读本地xml文件
	 * @param inputStream
	 * @return
	 */
	public String readXml2String(InputStream inputStream){
		String xmlStr=null;
		try {
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inputStream.close();
			xmlStr=outSteam.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlStr;
	}

/**
	 * excel导出交易记录
	 * 
	 * @param request
	 * @param resp
	 * @throws UnsupportedEncodingException
	 *//*

	public void exportExcel(HttpServletRequest request, HttpServletResponse resp, List<WorkFlow> listContent)
			throws UnsupportedEncodingException {
		HSSFWorkbook wb = new HSSFWorkbook();
		request.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
//		resp.setContentType("application/x-download");
		String fileName = listContent.get(0).getName()+".xls";

		resp.setContentType("multipart/form-data");

		String userAgent = request.getHeader("User-Agent");

		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		resp.setHeader("Content-disposition",
				String.format("attachment; filename=\"%s\"", fileName));
		resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");


//		fileName = URLEncoder.encode(fileName, "UTF-8");
//		resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		HSSFSheet sheet = wb.createSheet("工作流记录");
		sheet.setDefaultRowHeight((short) (2 * 256));
		sheet.setColumnWidth(0, 50 * 160);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		HSSFRow row = sheet.createRow((int) 0);
		sheet.createRow((int) 1);
		sheet.createRow((int) 2);
		*/
/* sheet.createRow((int) 3); *//*

		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell(1);
		cell.setCellValue("工作流名称 ");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("工作流程内容 ");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("工作流的说明");
		//用于简单的判断（标识字段：cdyouedata）
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(EXCEL_PASSWORD);

		List<WorkFlow> vUserOrder = listContent;

		for (int i = 0; i < vUserOrder.size(); i++) {
			HSSFRow row1 = sheet.createRow((int) i + 1);
			WorkFlow vuserOrder = vUserOrder.get(i);
			*/
/* row1.createCell(0).setCellValue(i + 1); *//*


			row1.createCell(0).setCellValue(EXCEL_PASSWORD);//标识
			row1.createCell(1).setCellValue(vuserOrder.getName());// 工作流名称
			row1.createCell(2).setCellValue(vuserOrder.getContext());// 工作流内容
			row1.createCell(3).setCellValue(vuserOrder.getFlowExplain());// 工作流说明
		}
		try {
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e1) {
			LOGGER.error("=====导出excel异常====",e1);
		}
	}

*/


	/**
	 * 添加模型上传信息到facade_upload_info
	 * @param session
	 * @param facadeUploadInfo
	 * @return
	 */
	@RequestMapping(value = "addUploadInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult addUploadInfo(HttpSession session, @RequestBody FacadeUploadInfo facadeUploadInfo) {
		if (facadeUploadInfo.getName() != "" && facadeUploadInfo.getFlowid() != null
				&& facadeUploadInfo.getModeltype() != "" && facadeUploadInfo.getAppexplain() != ""
				&& facadeUploadInfo.getPurpose() != "" && facadeUploadInfo.getTelnum() != ""
				&& facadeUploadInfo.getTestresult() != "") {
			User user = (User)session.getAttribute(Constants.USER_KEY);
			facadeUploadInfo.setModelUrl(HDFS_PATH_FEIX+user.getLoginname()+"/"+facadeUploadInfo.getModelUrl());
			FacadeUploadInfo fi = new FacadeUploadInfo();
			fi.setFlowid(facadeUploadInfo.getFlowid());
			List<FacadeUploadInfo> fiList = facadeUploadInfoService.selectByFacadeUploadInfo(fi);
			if (fiList != null && fiList.size() > 0) {
				facadeUploadInfo.setId(fiList.get(0).getId());
				int a = facadeUploadInfoService.updateByPrimaryKeySelective(facadeUploadInfo);
				if (a == 1) {
					return new ResponseResult(HttpStatus.OK, "模型信息添加成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型信息添加失败");
				}
			} else {
				int x = facadeUploadInfoService.insertSelective(facadeUploadInfo);
				if (x == 1) {
					return new ResponseResult(HttpStatus.OK, "模型信息添加成功");
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型信息添加失败");
				}
			}

		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "信息填写错误");
		}
	}


	/**
	 * 保存上传模型说明
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "addModelInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult Updatedata(HttpServletRequest request, FacadeUploadInfo facadeUploadInfo, HttpServletResponse response,
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
						// 重命名上传后的文件名
						String fileName = file.getOriginalFilename();
						// 定义上传路径
						path = url + fileName;
						final File localFile = new File(path);
						file.transferTo(localFile);

						final User user = (User) session.getAttribute(Constants.USER_KEY);
						String usr = user.getLoginname();
						Date data = new Date();
						
						String uploadPath = facadeUploadInfo.getCurrentDir() + "/" +data.getTime()+"_"+ fileName;
						final HdfsUploadDto hdfsUploadDto = new HdfsUploadDto(path, usr, uploadPath, user.getId(),
								user.getTips());

						Boolean uploadResult = iHdfsApi.uploadFile(hdfsUploadDto.getPath(),
								hdfsUploadDto.getUploadPath(), hdfsUploadDto.getUsr());
						if (uploadResult) {
							UploadHistory uploadHistory = new UploadHistory(myFileName, new Date(), user.getId(),
									"/"+hdfsUploadDto.getUploadPath().substring(hdfsUploadDto.getUploadPath().indexOf(hdfsUploadDto.getUsr()),hdfsUploadDto.getUploadPath().length()));
							uploadHistoryService.insert(uploadHistory);
							facadeUploadInfo.setModelUrl(uploadPath);
							if (facadeUploadInfo.getName() != "" && facadeUploadInfo.getFlowid() != null
									&& facadeUploadInfo.getModeltype() != "" && facadeUploadInfo.getAppexplain() != ""
									&& facadeUploadInfo.getPurpose() != "" && facadeUploadInfo.getTelnum() != ""
									&& facadeUploadInfo.getTestresult() != "") {
								FacadeUploadInfo fi = new FacadeUploadInfo();
								fi.setFlowid(facadeUploadInfo.getFlowid());
								List<FacadeUploadInfo> fiList = facadeUploadInfoService.selectByFacadeUploadInfo(fi);
								if (fiList != null && fiList.size() > 0) {
									facadeUploadInfo.setId(fiList.get(0).getId());
									int a = facadeUploadInfoService.updateByPrimaryKeySelective(facadeUploadInfo);
									if (a == 1) {
										return new ResponseResult(HttpStatus.OK, "模型信息添加成功");
									} else {
										return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型信息添加失败");
									}
								} else {
									int x = facadeUploadInfoService.insertSelective(facadeUploadInfo);
									if (x == 1) {
										return new ResponseResult(HttpStatus.OK, "模型信息添加成功");
									} else {
										return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "模型信息添加失败");
									}
								}

							} else {
								return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "信息填写错误");
							}
						}else{
							return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "上传文件失败");
						}
					}
				}
			}

		}
		return new ResponseResult(HttpStatus.OK, "保存成功");
	}


	/**
	 * 先查询出父节点相关集合，在遍历出子节点id集合
	 * @param list 父节点相关集合
	 * @param parentId
	 * @param returnList
	 * @return
	 */
	public static List<Integer> getChildNodes(List<WorkSpace> list, Integer parentId,List returnList) {
		if (list == null && parentId == null)
		{
			return null;
		}
		for (Iterator<WorkSpace> iterator = list.iterator(); iterator.hasNext();) {
			WorkSpace node = (WorkSpace) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (parentId.equals(node.getPid())) {
				returnList.add(node.getId());
				//递归遍历子后子
				getChildNodes(list, node.getId(), returnList);
			}
		}
		return returnList;
	}

	/**
	 * 右键查看算子运行日志
	 * @param flowId 工作流id
	 * @param blockId 单个算子的id,如statemodel3
	 * @return
	 */
	@RequestMapping("/getactionlogs")
	@ResponseBody
	public ResponseResult getActionLogs(Integer flowId,String blockId) throws IOException {
		WorkflowAction workflowAction = selectActionByFlowId(flowId,blockId);
		Map<String,Object> logsMap=new HashMap<>();
        String[] strs=null;
		if (workflowAction!=null){
			String consoleUrl=workflowAction.getConsoleUrl();
			if (!StringUtils.isEmpty(consoleUrl)){
				logsMap= getHtmlBody(consoleUrl);
				String logs1= (String) logsMap.get("logs1");
				strs=logs1.split("\\n");
				logsMap.put("logs1",strs);
            }
		}else{
			return new ResponseResult(HttpStatus.OK,"","暂时没有查询到运行日志，请调整配置后重新运行！");
		}
		return new ResponseResult(HttpStatus.OK,"",logsMap);
	}

	/**
	 * 通过工作流ID和算子ID获得当前算子的公用方法
	 * @param flowId
	 * @param blockId
	 * @return
	 */
	private WorkflowAction selectActionByFlowId(Integer flowId,String blockId){
		WorkflowAction workflowAction = null;
		List<CalculationHistory> calculationHistoryList = new ArrayList<>();
		calculationHistoryList = calculationHistoryService.selectByFlowId(String.valueOf(flowId));
		if (calculationHistoryList.size()!=0 && calculationHistoryList!=null){
			String jobId=calculationHistoryList.get(0).getJobId();// 根据flowId查出最近一次记录的jobId
			if (!StringUtils.isEmpty(jobId)) {
				WorkflowJob workflowJob = iOozieApi.getOozieWorkflowInfo(jobId, MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);//根据jobid获得这个工作流的运行记录
				List<WorkflowAction> actions = workflowJob.getActions();//拿到每一个算子
				for (int i = actions.size() - 2; i > 0; i--) {//去掉第一个和最后一个
					if (actions.get(i).getName().split("_")[0].equals(blockId)){
						workflowAction=actions.get(i);
						break;
					}
				}
			}
		}
		return workflowAction;
	}

	/**
	 * 通过httpclient获取页面上的内容
	 * @param consoleUrl 从算子里拿出的url，通过此方法进行字符串的拼装，拿到html页面上的logs的内容
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> getHtmlBody(String consoleUrl) {
		String logs=""; //日志1
		String logs2="";//日志2
		Map<String,Object> logsMap= new HashMap<>();
		String curStr=consoleUrl.substring(consoleUrl.indexOf("cdh"),consoleUrl.indexOf("cdh")+6);
		if (MailAuthorSetting.CDH_001.equals(curStr)){
			consoleUrl=consoleUrl.replace(curStr,MailAuthorSetting.CDH_001_IP);
		}else if (MailAuthorSetting.CDH_002.equals(curStr)){
			consoleUrl=consoleUrl.replace(curStr,MailAuthorSetting.CDH_002_IP);
		}else if (MailAuthorSetting.CDH_003.equals(curStr)){
			consoleUrl=consoleUrl.replace(curStr,MailAuthorSetting.CDH_003_IP);
		}else if (MailAuthorSetting.CDH_004.equals(curStr)){
			consoleUrl=consoleUrl.replace(curStr,MailAuthorSetting.CDH_004_IP);
		}else if (MailAuthorSetting.CDH_005.equals(curStr)){
			consoleUrl=consoleUrl.replace(curStr,MailAuthorSetting.CDH_005_IP);
		}
//		ResponseResult result = HttpWeb.getRedirectUrl(consoleUrl);//拿到重定向后的地址
		String[] strs=consoleUrl.split("/");
		String[] ipstr=strs[2].split(":");
		String redirectUrl=strs[0]+"//"+ipstr[0]+":"+MailAuthorSetting.CDH_PORT+MailAuthorSetting.CDH_JOB+strs[4]+"/";
		redirectUrl=redirectUrl.replace("application","job");
		ResponseResult result=new ResponseResult(HttpStatus.OK,redirectUrl);
		if(result.getCode()==200){
			String url=result.getMsg();         //初始日志页面
			String[] urlStr=url.split("/");
			String url2=urlStr[0]+"//"+urlStr[2]+"/"+urlStr[3]+"/attempts/"+urlStr[5]+"/m/SUCCESSFUL";  //另个一日志页面url
			ResponseResult result1 = HttpWeb.getHtmlByGet(url);
			ResponseResult result3 = HttpWeb.getHtmlByGet(url2);
			if (result1.getCode()==200){
				String html=result1.getMsg();
				String logslink = html.substring(html.indexOf("logslink") + 16, html.indexOf(">logs<") - 1);//截取页面上logs链接对应的源码的内容
				String[] uris = url.split("/");
				String logsUrl = uris[0] + "//" + uris[1] + uris[2] + logslink + "/syslog/?start=0";//拼装成最终的url
				System.out.println(logsUrl);
				ResponseResult result2 = HttpWeb.getHtmlByGet(logsUrl);
				if (result2.getCode()==200){
					String responseBody=result2.getMsg();
					logs = responseBody.substring(responseBody.indexOf("<pre>") + 5, responseBody.indexOf("</pre>"));//通过源码里的<pre>标签拿到logs日志
				}else {
					logs=result2.getMsg();
				}
			}else {
				logs=result1.getMsg();
			}
			if (result3.getCode()==200){
				String html=result3.getMsg();
				String logslink = html.substring(html.indexOf("logslink") + 16, html.indexOf(">logs<") - 1);//截取页面上logs链接对应的源码的内容

				String[] uris = url2.split("/");
				String logsUrl = uris[0] + "//" + uris[1] + uris[2] + logslink;//拼装成最终的url
				ResponseResult result2 = HttpWeb.getHtmlByGet(logsUrl);
				if (result2.getCode()==200){
					String logsHtml=result2.getMsg();
					logs2 = logsHtml.substring(logsHtml.indexOf("<td class=\"content\">")+20, logsHtml.indexOf("</tbody>")-15);//通过源码里的<td>标签拿到logs日志
                    String[] htmls =logs2.split("<a");
                    for (int i=1;i<htmls.length;i++) {
                        String logsLink=htmls[i].substring(htmls[i].indexOf("href")+6,htmls[i].indexOf(">here<")-1);
                        String linkUrl= uris[0] + "//" + uris[2]+logsLink;
                        ResponseResult results = HttpWeb.getHtmlByGet(linkUrl);
                        if (results.getCode()==200) {
                            String curHtml = results.getMsg();
                            String logsN = curHtml.substring(curHtml.indexOf("<td class=\"content\">") + 20, curHtml.indexOf("</tbody>") - 15);
                            logsMap.put("logs"+(i+2),logsN);
                        }
                    }
                    System.out.println(htmls.length);
				}else {
					logs2=result2.getMsg();
				}
			}else {
				logs2=result1.getMsg();
			}
        }else{
			logs=result.getMsg();
			logs2=result.getMsg();
		}
		logsMap.put("logs1",logs);
		logs2=logs2.replaceAll("href","class");
		logsMap.put("logs2",logs2);
		return logsMap;
	}

	/**
	 * 查询运行结果--数据
	 * @param dataPath hdfs路径
	 * @param iHdfsApi
	 * @param session
	 * @return
	 */
	private List<List<String>> getDataResult(String dataPath,IHdfsApi iHdfsApi,String filter,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		List<List<String>> table= new ArrayList<>();
		FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
		try{
			boolean dataExists = fileSystem.exists(new Path(dataPath));
			if (dataExists){
				List<FileInfo> dataFileList = iHdfsApi.listFileAndDir(dataPath,filter,user.getLoginname());
				if(dataFileList.size()>0){
					for (FileInfo fileInfo : dataFileList) {
						List<List<String>> fileList=ReadFileUtil.readCsv(String.valueOf(fileInfo.getPath()),iHdfsApi,session);
						table.addAll(fileList);
					}
				}
			}
		}catch (Exception e){
			LOGGER.info("======================================算子路径不存在====================================");
			e.printStackTrace();
		}finally {
			return table;
		}

	}

	/**
	 * 查询运行结果--模型
	 * @param modelPath  hdfs路径
	 * @param iHdfsApi
	 * @param session
	 * @return
	 */
	private List<String> getModelResult(String modelPath,IHdfsApi iHdfsApi,String filter,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		String resultJson="";
		List<String> modelStr= new ArrayList<>();
		FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
		try {
			boolean modelExists = fileSystem.exists(new Path(modelPath));
			if (modelExists){
				List<FileInfo> modelFileList = iHdfsApi.listFileAndDir(modelPath,filter,user.getLoginname());
				if (modelFileList.size()>0){ //大于零表示获取到的文件路径下存在包含attr的文件
					byte[] byteArray = iHdfsApi.readFileFromHdfs(String.valueOf(modelFileList.get(0).getPath()),user.getLoginname());//读hdfs文件
					Charset code= Charsets.UTF_8;//编码方式
					resultJson = new String(byteArray,code).trim();
					Map<String,String> stringMap =new HashMap<>();
					com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultJson);
					Set<String> keys = jsonObject.keySet();
					Iterator iterator = keys.iterator();
					while(iterator.hasNext()){
						String key = (String) iterator.next();
						String jsonStr=new StringBuffer(key).append("    :   ").append(jsonObject.getString(key)).toString();
						modelStr.add(jsonStr);
					}
				}
			}
		}catch (Exception e){
			LOGGER.info("======================================算子路径不存在====================================");
			modelStr.add("暂未查询到数据，请尝试重新运行！");
			e.printStackTrace();
		}finally {
			return  modelStr;
		}
	}
	/**
	 * 右键查看算子运行中间结果 针对两个输出（一个数据,一个模型)
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getactionresult4modelanddata")
	@ResponseBody
	public Map<String,Object> getActionResult4ModelAndData(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		Map<String,Object> resultMap= new HashMap<>();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				List<List<String>> table = new ArrayList();
				List<String> resultJson = new ArrayList<>();
				String substr = conf.substring(conf.indexOf("outputPath") + 11);
				String[] strs = substr.split("</arg>");
				String outputPath = strs[0];//通过截取字符串获得算子运行结果中间路径
				String dataPath=outputPath+RESULT_DATA_PATH;
				String modelPath=outputPath+RESULT_MODEL_PATH;
				String dataFilter=RESULT_DATA_FILTER;
				String modelFilter=RESULT_MODEL_FILTER;
				table = getDataResult(dataPath,iHdfsApi,dataFilter,session);
				resultJson = getModelResult(modelPath,iHdfsApi,modelFilter,session);
				if (StringUtils.isEmpty(resultJson)) {
					resultMap.put("resultmodel","暂未查询到数据，请尝试重新运行！");
				}else {
					resultMap.put("resultmodel",resultJson);
				}
				resultMap.put("resulttable",table);
			}
		}
		return resultMap;
	}

	/**
	 * 已训练的模型查看运行结果
	 * @param flowId
	 * @param blockId
	 * @param modelTrainedName
	 * @param session
	 * @return
	 */
	@RequestMapping("/getModelTrainedResult")
	@ResponseBody
	public Map<String,Object> getModelTrainedResult(Integer flowId,String blockId,String modelTrainedName,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
        ModelTrained modelTrained = new ModelTrained();
        modelTrained.setUserId(user.getId());
        modelTrained.setModelTrainedName(modelTrainedName);
        List<ModelTrained>  modelTraineds = modelTrainedService.selectAll(modelTrained);
        String dataModelName= modelTraineds.get(0).getDataModelName();
        Integer modelId = dataModelService.selectByName(dataModelName);
		Map<String,Object> resultMap= new HashMap<>();
		if (modelId==59||modelId==60||modelId==69||modelId==130||modelId==131){  //两种输出
			if (workflowAction!=null) {
				String conf = workflowAction.getConf();
				if (!StringUtils.isEmpty(conf)) {
					List<List<String>> table = new ArrayList();
					String substr = conf.substring(conf.indexOf("outputPath") + 11);
					String[] strs = substr.split("</arg>");
//					String outputPath = strs[0].substring(0,strs[0].indexOf("/model"));//通过截取字符串获得算子运行结果中间路径
					String outputPath = strs[0];
					String dataPath=outputPath+RESULT_DATA_PATH;
					String modelPath=outputPath+RESULT_MODEL_PATH;
					String dataFilter=RESULT_DATA_FILTER;
					String modelFilter=RESULT_MODEL_FILTER;
					table = getDataResult(dataPath,iHdfsApi,dataFilter,session);
					List<String> resultJson = getModelResult(modelPath,iHdfsApi,modelFilter,session);
					if (StringUtils.isEmpty(resultJson)) {
						resultMap.put("resultmodel","暂未查询到数据，请尝试重新运行！");
					}else {
						resultMap.put("resultmodel",resultJson);
					}
					resultMap.put("resulttable",table);
				}
			}
		}else { //只输出模型
			if (workflowAction!=null) {
				String conf = workflowAction.getConf();
				if (!StringUtils.isEmpty(conf)) {
					List<List<String>> table = new ArrayList();
					String substr = conf.substring(conf.indexOf("outputPath") + 11);
					String[] strs = substr.split("</arg>");
//					String outputPath = strs[0].substring(0,strs[0].indexOf("/model"));//通过截取字符串获得算子运行结果中间路径
					String outputPath=strs[0];
					String modelPath=outputPath+RESULT_MODEL_PATH;
					String modelFilter=RESULT_MODEL_FILTER;
					List<String> resultJson = getModelResult(modelPath,iHdfsApi,modelFilter,session);
					resultMap.put("resultmodel",resultJson);
					resultMap.put("resulttable",table);
				}
			}
		}
		return resultMap;
	}
	/**
	 * 右键查看算子运行中间结果 针对只输出一个数据
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getactionresult4data")
	@ResponseBody
	public List<List<String>> getActionResult4Data(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		List<List<String>> table = new ArrayList();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				String substr = conf.substring(conf.indexOf("outputPath") + 11);
				String[] strs = substr.split("</arg>");
				String outputPath = strs[0];//通过截取字符串获得算子运行结果中间路径
				String dataFilter=RESULT_DATA_FILTER;
				table = getDataResult(outputPath,iHdfsApi,dataFilter,session);
			}
		}
		return table;
	}


	/**
	 * 右键查看算子运行中间结果 针对只输出两个数据
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getactionresult4doubledata")
	@ResponseBody
	public Map<String,Object> getActionResult4DoubleData(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		List<List<String>> table1 = new ArrayList();
		List<List<String>> table2 = new ArrayList();
		Map<String,Object> resultMap= new HashMap<>();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				String substr = conf.substring(conf.indexOf("outputpath1") + 12);
				String[] strs = substr.split("</arg>");
				String outputPath1 = strs[0];//通过截取字符串获得算子运行结果中间路径
				String subStr2= conf.substring(conf.indexOf("outputpath2")+12);
				String[] strs2 = subStr2.split("</arg>");
				String outputPath2 = strs2[0];
				String dataFilter=RESULT_DATA_FILTER;
				table1 = getDataResult(outputPath1,iHdfsApi,dataFilter,session);
				table2 = getDataResult(outputPath2,iHdfsApi,dataFilter,session);
				if(table1.size()>0){
                    resultMap.put("table1",table1);
                }
				if (table2.size()>0){
                    resultMap.put("table2",table2);
                }
			}
		}
		return resultMap;
	}


	/**
	 * 右键查看算子运行中间结果 针对python和R算子
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getactionresult4python")
	@ResponseBody
	public List<List<String>> getActionResult4Python(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		List<List<String>> table = new ArrayList();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				String substr = conf.substring(conf.indexOf("outputPath") + 11);
				String[] strs = substr.split("</argument>");
				String outputPath = strs[0];//通过截取字符串获得算子运行结果中间路径
				table = ReadFileUtil.readCsv(outputPath,iHdfsApi,session);
			}
		}
		return table;
	}


	/**
	 * 右键查看写入CSV(V4)算子运行中间结果
	 * @param flowId
	 * @param blockId
	 * @param fileName 前台属性面板自定义的名称
	 * @param outputStr 前台属性面板选择的输出路径
	 * @param session
	 * @return
	 */
	@RequestMapping("/getActionResult4WriteCSV")
	@ResponseBody
	public List<List<String>> getActionResult4WriteCSV(Integer flowId,String blockId,String fileName,String outputStr,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		StringBuilder output = new StringBuilder(HADOOP_HDFS_URL);
		output=output.append(outputStr).append("/").append(fileName);
		List<List<String>> table = new ArrayList();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				String substr = conf.substring(conf.indexOf("outputPath") + 11);
				String[] strs = substr.split("</arg>");
				String outputPath = strs[0];//通过截取字符串获得算子运行结果中间路径
				String[] outStr=outputPath.split("/");
				//通过截取字符串拿到路径里随机生成的UUID
				String[] UUIDStrs=outStr[outStr.length-1].split("-");
				String UUIDStr="";
				for (int i=1;i<UUIDStrs.length;i++){
					UUIDStr=UUIDStr+"-"+UUIDStrs[i];
				}
				//拼装成输出路径，避免oozie读取配置文件的中文乱码
				output=output.append(UUIDStr);
				//根据前台属性面板的fileName来对输出路径下的文件进行过滤
				String dataFilter=fileName;
				table = getDataResult(String.valueOf(output),iHdfsApi,dataFilter,session);
			}
		}
		return table;
	}

	/**
	 * 右键查看算子运行中间结果  针对只输出一个模型
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
	@RequestMapping("/getactionresult4model")
	@ResponseBody
	public Map<String,Object> getActionResult4Model(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		Map<String,Object> resultMap= new HashMap<>();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				List<List<String>> table = new ArrayList();
				String substr = conf.substring(conf.indexOf("outputPath") + 11);
				String[] strs = substr.split("</arg>");
				String outputPath = strs[0];//通过截取字符串获得算子运行结果中间路径
				String modelPath=outputPath+RESULT_MODEL_PATH;
				String modelFilter=RESULT_MODEL_FILTER;
				List<String> resultJson = getModelResult(modelPath,iHdfsApi,modelFilter,session);
				resultMap.put("resultmodel",resultJson);
			}
		}
		return resultMap;
	}

	/**
	 * 保存为已训练的模型
	 * @param flowId  工作流ID
	 * @param modelName  已训练的模型的名称
	 * @param session
	 * @return
	 */
	@RequestMapping("/settrainedmodel")
	@ResponseBody
	public ResponseResult setTrainedModel(Integer flowId,String blockId,String blockName,String modelName,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		Map<String,Object> params= new HashMap<>();
		params.put("userId",user.getId());
		params.put("modelTrainedName",modelName);
		List<ModelTrained> modelTrainedList  = modelTrainedService.selectByIdAndName(params);
		if (modelTrainedList.size()==0){
			WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
			String outputPath="";
			if (workflowAction!=null) {
				String conf = workflowAction.getConf();
				if (!StringUtils.isEmpty(conf)) {
					String substr = conf.substring(conf.indexOf("outputPath") + 11);
					String[] strs = substr.split("</arg>");
					//通过截取字符串获得算子运行结果中间路径
					outputPath = strs[0];
				}
			}
			DataModel trainedModel=new DataModel();
			trainedModel.setName(MODEL_NAME);
			List<DataModel> dataModels=dataModelService.selectByDataModel(trainedModel);
			ModelTrained modelTrained = new ModelTrained();
			modelTrained.setModelId(dataModels.get(0).getId());
			modelTrained.setDataModelName(blockName);
			modelTrained.setModelTrainedName(modelName);
			modelTrained.setUserId(user.getId());
			modelTrained.setWorkflowId(flowId);
			modelTrained.setModelTrainedUpdateTime(new Date());
			modelTrained.setModelTrainedStatus(0);
			modelTrained.setModelTrainedPath(outputPath);
			int status =modelTrainedService.insert(modelTrained);
			return status==1? new ResponseResult(HttpStatus.OK,"保存成功"):new ResponseResult(HttpStatus.EXPECTATION_FAILED,"保存失败");
		}else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED,"该模型名称已存在");
		}

	}

	/**
	 * 读取csv算子添加注释信息
	 * @param inputPath  读取文件的路径
	 * @param tableHeader   是否有表头
	 * @param session
	 * @return
	 */
	@RequestMapping("adddescription4csv")
	@ResponseBody
	public List<List<String>> addDescription4Csv(String inputPath,Boolean tableHeader,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		String path=MailAuthorSetting.HDFS_PATH_FEIX+user.getLoginname()+"/datas"+inputPath;
		List<List<String>> csvTable= ReadFileUtil.readCsv(path,iHdfsApi,session);
		List<List<String>> descriptionTable = new ArrayList<>();
		if (tableHeader){
			for (int i = 0;i<csvTable.get(0).size();i++){
				List<String> descirptionRow=new ArrayList<>();
				descirptionRow.add(csvTable.get(0).get(i));
				descirptionRow.add("String");
				descirptionRow.add("");
				descirptionRow.add("否");
				descriptionTable.add(i,descirptionRow);
			}
		}else {
			for (int i = 0;i<csvTable.get(0).size();i++){
				List<String> descirptionRow=new ArrayList<>();
				descirptionRow.add("C_"+(i+1));
				descirptionRow.add("String");
				descirptionRow.add("");
				descirptionRow.add("否");
				descriptionTable.add(i,descirptionRow);
			}
		}
		return  descriptionTable;
	}

	/**
	 *读取数据库算子添加注释信息
	 * @param connectName
	 * @param tableName
	 * @return
	 */
	@RequestMapping("adddescription4database")
	@ResponseBody
	public List<List<String>> addDescription4DataBase(String connectName,String tableName,HttpSession session){
		User user = (User) session.getAttribute(Constants.USER_KEY);
		DatabaseConnect curDatabaseConnect = new DatabaseConnect();
		curDatabaseConnect.setDbConName(connectName);
		curDatabaseConnect.setDbConUserId(user.getId());
		DatabaseConnect databaseConnect = databaseConnectService.findByUserIdAndConnName(curDatabaseConnect);
		List<String> tableDatas = LoadDatabaseLinkUtil.getTableDatas(databaseConnect.getDbCconId(), tableName, databaseConnectService, databaseTypeService, readResourceService);
		String firstRow = tableDatas.get(0);//拿到数据库元数据
		String[] nameType=firstRow.split("\\|");
		List<String> columnName= new ArrayList<>();
		List<String> typeList= new ArrayList<>();
		for (int i=0;i<nameType.length;i++) {
			String[] str=nameType[i].split(",");
			columnName.add(str[0]);
			typeList.add(str[1]);
		}
		List<String> columnType = TypeConversionUtil.typeConversion(typeList);
		List<List<String>> descriptionTable= new ArrayList<>();
		for (int i=0;i<columnName.size();i++){
			List<String> descirptionRow=new ArrayList<>();
			descirptionRow.add(columnName.get(i));
			descirptionRow.add(columnType.get(i));
			descirptionRow.add("");
			descirptionRow.add("否");
			descriptionTable.add(descirptionRow);
		}
		return  descriptionTable;
	}

	/**
	 * 从首页新建工作流
	 * @param workSpaceId
	 * @param workFlowName
	 * @param flowExplain
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "creatworkflowbyindex")
	@ResponseBody
	public ResponseResult copyWorkFlow(Integer workSpaceId,String workFlowName,String flowExplain,HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_KEY);
		if (workSpaceId!=null && !StringUtils.isEmpty(workFlowName) && !StringUtils.isEmpty(flowExplain)){
			WorkFlow workFlow = new WorkFlow();
			workFlow.setName(workFlowName);
			workFlow.setWorkspid(workSpaceId);
			List<WorkFlow> workFlowList = workFolwService.selectWorkFlow(workFlow);
			if (workFlowList != null && workFlowList.size() > 0) {
				return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "存在相同的工作流");
			}else{
				// 创建对应hdfs目录
				// HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
				WorkSpace workSpace = workSpaceService.selectByPrimaryKey(workFlow.getWorkspid());
				Boolean b = iHdfsApi.createUserWorkspace(workSpace.getHdfsUrl(workSpace) + "/" + workFlow.getName(),
							user.getLoginname(), true);
				if (b) {
					workFlow.setHdfsUrl(workSpace.getHdfsUrl() + "/" + workFlow.getName());
					workFlow.setCreateTime(new Date());
					workFlow.setFlowExplain(flowExplain);
					workFlow.setUserName(user.getLoginname());
					Integer id = workFolwService.insert(workFlow);
					if(null != id){
						return new ResponseResult(HttpStatus.OK, "创建新的工作流成功",id);
					}else{
						return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "创建工作流失败");
					}
				} else {
					return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "创建工作流失败");
				}
			}
		} else {
			return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
		}
	}

    /**
     *Python和R自定义算子查看运行结果
     * @param flowId
     * @param blockId
     * @param session
     * @return
     */
	@RequestMapping(value = "RAndPythonResult")
	@ResponseBody
	public Map<String,Object> RAndPythonResult(Integer flowId,String blockId,HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		Map<String,Object> resultMap= new HashMap<>();
		List<List<String>> table = new ArrayList();
		List<String> modelStr= new ArrayList<>();
		if (workflowAction!=null){
			String conf = workflowAction.getConf();
			if(!StringUtils.isEmpty(conf)){
				String subStr2 = conf.substring(conf.indexOf("tmpOutputData") + 14);
				String[] strs2 = subStr2.split("</argument>");
				String tmpOutputData = strs2[0];
				String subStr3 = conf.substring(conf.indexOf("tmpOutputModel") + 15);
				String[] strs3 = subStr3.split("</argument>");
				String tmpOutputModel = strs3[0];
				FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
					boolean dataExists = false;
					try {
						dataExists = fileSystem.exists(new Path(tmpOutputData));
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							fileSystem.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (dataExists){
						table = ReadFileUtil.readCsv(tmpOutputData,iHdfsApi,session);
						resultMap.put("resulttable",table);
					}
					modelStr=getModelResult(tmpOutputModel,iHdfsApi,RESULT_MODEL_FILTER,session);
					resultMap.put("resultmodel",modelStr);
			}
		}
		return resultMap;

	}

	/**
	 * R和python自定义算子查看图片运行结果
	 * @param flowId
	 * @param blockId
	 * @param session
	 * @return
	 */
    @RequestMapping(value = "RAndPythonImgResult")
	@ResponseBody
	public PageBean pythonResult(Integer flowId,String blockId,Integer page,HttpSession session){
		if (page==null|| page==0){
			page=DEFAULT_PAGENUM;
		}
		User user = (User)session.getAttribute(Constants.USER_KEY);
		WorkflowAction workflowAction= selectActionByFlowId(flowId,blockId);
		Map<String,Object> resultMap= new HashMap<>();
		List<List<String>> table = new ArrayList();
		String imgStr = null;
		List<String> modelStr = null;
		PageBean pageBean = new PageBean();
		if (workflowAction!=null) {
			String conf = workflowAction.getConf();
			if (!StringUtils.isEmpty(conf)) {
				String subStr = conf.substring(conf.indexOf("tmpImgPath") + 11);
				String[] strs = subStr.split("</argument>");
				//通过截取字符串获得算子运行结果图片中间路径
				String tmpImgPath = strs[0];
/*				String subStr2 = conf.substring(conf.indexOf("tmpOutputData") + 14);
				String[] strs2 = subStr2.split("</argument>");
				String tmpOutputData = strs2[0];
				String subStr3 = conf.substring(conf.indexOf("tmpOutputModel") + 15);
				String[] strs3 = subStr3.split("</argument>");
				String tmpOutputModel = strs3[0];*/
				List<FileInfo> imgDir= iHdfsApi.listFileAndDir(tmpImgPath,"img",user.getLoginname());
                int i=1;
                for (FileInfo dir : imgDir) {
                    List<FileInfo> fileInfos = iHdfsApi.list(String.valueOf(dir.getPath()),user.getLoginname());
					int startItem=(page-1)*4;
					int endItem=page*4-1;
					int totalPage=0;
					if (4>fileInfos.size()){
						totalPage=1;
					}else{
						totalPage=fileInfos.size()%4==0?fileInfos.size()/4:fileInfos.size()/4+1;
					}

					if (endItem>fileInfos.size()-1){
						endItem=fileInfos.size()-1;
					}
					List<FileInfo> pageList= new ArrayList<>();
					for (int j =startItem;j<=endItem;j++){
						pageList.add(fileInfos.get(j));
					}
					pageBean.setTotal(fileInfos.size());
					pageBean.setTotalPage(totalPage);
					pageBean.setCurPage(page);
					List<String> imgList= new ArrayList<>();
					List<String> fileNamelist= new ArrayList<>();
                    for (FileInfo fileInfo : pageList) {
                    	fileNamelist.add(fileInfo.getPath().toUri().getPath());
                        byte[] byteArray = iHdfsApi.readFileFromHdfs(fileInfo.getCurDir(),user.getLoginname());
                        if (byteArray != null){
                            BASE64Encoder encoder = new BASE64Encoder();
                            imgStr = encoder.encode(byteArray);
                            String base64str= "data:image/jpeg;base64,"+imgStr;
							imgList.add(base64str);
                        }

                    }
					pageBean.setFileNamelist(fileNamelist);
                    pageBean.setRows(imgList);
                }
			}
		}
		return pageBean;
	}

    /**
     * Python和R算子图片下载和压缩
     * @param imagePath 图片hdfs路径
     * @param request
     * @param response
     * @param session
     */
	@RequestMapping(value="downloadresultimage")
    public void downLoadResultImage(String imagePath, HttpServletRequest request, HttpServletResponse response, HttpSession session){
	    String[] images = imagePath.split(",");
        User user = (User) session.getAttribute(Constants.USER_KEY);
        FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
        FSDataInputStream is = null;
        //多张图片
	    if (images.length>1){
            List<FSDataInputStream> dataInputStreamList = new ArrayList<>();
            String[] names = new String[images.length];
            for (int i=0;i<images.length;i++){
                try {
                    if(!fileSystem.exists(new Path(images[i]))){
                        throw new FileNotFoundException("File["+images[i]+"] does not exists.");
                    }
                    is = fileSystem.open(new Path(images[i]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dataInputStreamList.add(is);
                String[] str= images[i].split("/");
                names[i]=str[str.length-1];
            }
            byte[] buf = new byte[1024];
            int len;
            //临时压缩包
            File zipFile= new File("/c:Images.zip");
            try {
                ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
                for (int i= 0;i<dataInputStreamList.size();i++) {
                    FSDataInputStream in = dataInputStreamList.get(i);
                    zout.putNextEntry(new ZipEntry(names[i]));
                    while ((len = in.read(buf)) > 0) {
                        zout.write(buf, 0, len);
                    }
                    zout.closeEntry();
                    in.close();
                };
                zout.close();
                FileInputStream zipInput =new FileInputStream(zipFile);
                response.setContentType("multipart/form-data");
                response.setHeader("Content-disposition", "attachment; filename=images.zip");
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[1024*1024];
                int bytesRead;
                while (-1 != (bytesRead = zipInput.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bos.flush();
                bos.close();
                zipInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
				//删除压缩包
				zipFile.delete();
                try {
                    fileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
	        //只选中一张图片
            try {
                Path path = new Path(images[0]);
                String fileName= path.getName();
                if(!fileSystem.exists(path)){
                    throw new FileNotFoundException("File["+fileName+"] does not exists.");
                }
                is = fileSystem.open(path);
                response.setContentType("multipart/form-data");
                response.setHeader("Content-disposition", "attachment; filename="+fileName+"");
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[1024*1024];
                int bytesRead;
                while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bos.flush();
                bos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping(value = "deleteresultimage")
	public ResponseResult deleteResultImage(@RequestParam(value = "imagePath[]") String [] imagePath,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
		Boolean result = true;
		for (String s : imagePath) {
			String path=HADOOP_HDFS_URL+s;
			result = iHdfsApi.deleteFileOrDir(path,user.getLoginname(),false);
		}
		return result ? new ResponseResult(HttpStatus.OK,"删除成功"):new ResponseResult(HttpStatus.EXPECTATION_FAILED,"删除失败");
	}



}
