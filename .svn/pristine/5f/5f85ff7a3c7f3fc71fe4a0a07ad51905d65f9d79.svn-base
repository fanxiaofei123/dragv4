package org.com.drag.web.controller.interfaceController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.model.*;
import org.com.drag.service.CalculationFrontHistoryService;
import org.com.drag.service.JobService;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.WorkSpaceService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.web.common.ModelUtils;
import org.com.drag.web.common.WorkFlowXmlUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cdyoue on 2016/11/21.
 */
@Controller
@RequestMapping("/drag/calculationFront")
public class CalculationFrontController {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private CalculationFrontHistoryService calculationFrontHistoryService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private JobService jobService;
    @Autowired
    private IHdfsApi iHdfsApi;
    @Autowired
    private IOozieApi  iOozieApi;
    
    /**
     * 工作流的运行
     * @param session
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "run", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseResult runCalculation(HttpServletRequest request,HttpSession session, String modelStr, String connectStr,String blocksStr, final Integer flowId,String flowName,String workSpaceName) {
        User user = (User)session.getAttribute(Constants.USER_KEY);


        List<Connect> connects = JSON.parseArray(connectStr, Connect.class);
        List<Model> models =JSON.parseArray(modelStr,Model.class);
        String userName = user.getLoginname();
		/**
		 * 添加代码[int userId = user.getId();]By gongmingxing 2017.08.22
		 */
		int userId = user.getId();

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
        workFlow.setContext(contextJson.toString());
        workFlowService.updateByPrimaryKeySelective(workFlow);
        
        WorkFlow workFlows = workFlowService.selectByPrimaryKey(flowId);
        WorkSpace workSpace = workSpaceService.selectByPrimaryKey(workFlows.getWorkspid());
        
//生成xml和properties
        ModelUtils modelUtils = new ModelUtils();
        ModelXmlInfo modelXmlInfo = modelUtils.getSortingFormatModels(models, connects,new StringBuffer(workSpaceName).append("/").append(flowName).toString(),userName,userId,null,null);
        String pathPrefix = request.getServletContext().getRealPath("/")+"upload\\";

        String tmpoFolderName = new StringBuffer(Constants.WORK_FLOW).append(UUID.randomUUID().toString()).toString();

        String folderPath = new StringBuffer(pathPrefix).append(tmpoFolderName).toString();
        File file = null;
        Properties xmlProperties;
        boolean uploadBooler;
        try {
            file = new File(folderPath);
            boolean mkdir = file.mkdir();
            if(!mkdir){
                return new ResponseResult(HttpStatus.FORBIDDEN);
            }
            String  path = new StringBuffer(folderPath).append("/").append(Constants.WORK_FLOW).append(".xml").toString();
            xmlProperties = WorkFlowXmlUtils.buildXml(modelXmlInfo, path, new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX).append(userName).append("/workspaces/").append(workSpace.getName()).append("/").append(flowName).toString(),flowName, userName);
            WorkFlowXmlUtils.updateXml(path, path);
            //上传xml到hdfs
           // HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
            uploadBooler = iHdfsApi.uploadWorkFlowFile(path, new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(workSpace.getName()).append("/").append(flowName).append("/").append("workflow.xml").toString(), userName);
        } finally {
            if(file != null){
                removedir(file);
            }
        }


        if(!uploadBooler){
            return  new ResponseResult(HttpStatus.EXPECTATION_FAILED);
        }

        //运行任务
        String jobId = jobService.run(xmlProperties,user.getId(),flowId,workFlows.getName(),modelXmlInfo.getResultAddress(),models.size(),session,null,null,null);

       //查询当前工作空间的历史记录
		CalculationFrontHistory ch = new CalculationFrontHistory();
        ch.setUserid(user.getId());
        ch.setFlowId(flowId);

        List<CalculationFrontHistory> results =  calculationFrontHistoryService.selectBySelectiveKey(ch);
        ResponseResult responseResult = new ResponseResult(HttpStatus.OK);

        Map map = new HashMap();
        map.put("historys", results);
        map.put("jobId", jobId);
       responseResult.setObj(map);
       return responseResult;
    }



    public  void removedir(File file)
    {
        File[] files=file.listFiles();
        for(File f:files)
        {
            if(f.isDirectory())//递归调用
            {
                removedir(f);
            }
            else {
                f.delete();
            }
        }
        //一层目录下的内容都删除以后，删除掉这个文件夹
        file.delete();
    }

    @RequestMapping(value = "pause",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseResult calculationPause(String jobId,Integer workFlowId){
        // OozieClientServiceImpl oc = new OozieClientServiceImpl(MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        boolean b = iOozieApi.oozieWorkflowSuspend(jobId,MailAuthorSetting.HADOOP_OOZIECLIENT_PATH);
        if(b){
            WorkFlow workFlow = new WorkFlow();
            workFlow.setId(workFlowId);
            workFlow.setJobstatus(3);

            workFlowService.updateByPrimaryKeySelective(workFlow);
        }

        return b ? new ResponseResult(HttpStatus.OK):new ResponseResult(HttpStatus.FORBIDDEN);
    }


    @RequestMapping(value = "ifRun",method = RequestMethod.GET)
    @ResponseBody
    public Boolean ifRun(){
		CalculationFrontHistory CalculationFrontHistory = new CalculationFrontHistory();
		CalculationFrontHistory.setStatus(1);
        List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryService.selectBySelectiveKey(CalculationFrontHistory);
        return calculationHistories.size() == 0 ? false : true;
    }

    
    
    /**
     * 分页查询用户的运行记录
     * @return
     */
    @RequestMapping(value = "history")
    @ResponseBody
    public PageBean selectHistory(Integer page ,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
		CalculationFrontHistory calculationFrontHistory = new CalculationFrontHistory();
       // calculationFrontHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryService.selectBySelectiveKey(calculationFrontHistory);
		//设置返回的总记录数  
        PageInfo<CalculationFrontHistory> pageInfos=new PageInfo<CalculationFrontHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationFrontHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
				 ws.setUserNmae(user.getLoginname());
				 
			}
		}
        pageBean.setRows(calculationHistories);
        
        return pageBean;
    }
    
    @RequestMapping(value = "foreList")
    @ResponseBody
    public PageBean<CalculationHistoryDetail> getForeList(HttpServletRequest request,HttpServletResponse response,Integer page){
    	if(page==null||page==0){
    		page = 1;
    	}
    	PageBean<CalculationHistoryDetail> pageBean = new PageBean<>();
    	String status = request.getParameter("status"), name = request.getParameter("name"),
    			type = request.getParameter("type"),frontusername = request.getParameter("frontusername"),
    					treeId = request.getParameter("treeId");
    	if(frontusername==null){
    		return pageBean;
    	}
    	if(StringUtils.isBlank(name)){
    		name = null;
    	}
    	if(!StringUtils.isNumeric(treeId)){
    		treeId = null;
    	}
    	if(StringUtils.isBlank(status)){
    		status = null;
    	}
    	if(StringUtils.isBlank(type)){
    		type = null;
    	}
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("status",status);params.put("name", name);params.put("type",type);params.put("frontusername",frontusername);params.put("treeId",treeId);
    	List<CalculationHistoryDetail> rows = calculationFrontHistoryService.selectByOptions(page,params);
    	PageInfo<CalculationHistoryDetail> pageInfos = new PageInfo<>(rows);
    	pageBean.setCurPage(pageInfos.getPageNum());
    	pageBean.setRows(rows);
    	pageBean.setTotal((int)pageInfos.getTotal());
    	pageBean.setTotalPage(pageInfos.getPages());
    	return pageBean;
    }
    
    /**
     * 查询所用用户的日志记录
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyAll")
    @ResponseBody
    public PageBean selectHistoryAll(Integer page ,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
		CalculationFrontHistory calculationFrontHistory = new CalculationFrontHistory();
//        calculationFrontHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryService.selectBySelective(calculationFrontHistory);
		//设置返回的总记录数  
        PageInfo<CalculationFrontHistory> pageInfos=new PageInfo<CalculationFrontHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationFrontHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    /**
     * 通过时间段查询用户日志记录
     * @param page
     * @return
     */
    @RequestMapping(value = "historyTime")
    @ResponseBody
    public PageBean selectHistoryTime(Integer page ,String frontusername,String name,Integer status,CalculationFrontHistory calculationFrontHistory){
        
    	if(null != calculationFrontHistory ){
			if(null != calculationFrontHistory.getStartTime()){
				calculationFrontHistory.setStartTime(calculationFrontHistory.getStartTime().toString().trim());
			}
			if(null != calculationFrontHistory.getEndTime()){
				calculationFrontHistory.setEndTime(calculationFrontHistory.getEndTime().toString().trim());
			}
			
		}
		if( null != frontusername){
			calculationFrontHistory.setFrontusername(frontusername);
		}
    	if (null != name ){
			calculationFrontHistory.setName(name);
		}
		if(null != status){
			calculationFrontHistory.setStatus(status);
		}
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationFrontHistory> calculationHistories = calculationFrontHistoryService.selectBySelectiveTime(calculationFrontHistory);
		//设置返回的总记录数  
        PageInfo<CalculationFrontHistory> pageInfos=new PageInfo<CalculationFrontHistory>(calculationHistories);
        if(page == 1){
        	 pageBean.setPrevious(page);
        }else{
        	 pageBean.setPrevious(page-1);
        }
       
        if(page < pageInfos.getPages()){
        	 pageBean.setNext(page+1);
        }else{
        	 pageBean.setNext(page);
        }
        pageBean.setTotal((int)pageInfos.getTotal());
        pageBean.setTotalPage(pageInfos.getPages());
        pageBean.setCurPage(page);
        if(calculationHistories != null && calculationHistories.size()>0){
			for(CalculationFrontHistory ws :calculationHistories){
				if(ws.getStatus() == 1){
					ws.setStatusName("运行中");
				}else if(ws.getStatus() == 2){
					ws.setStatusName("运行成功");
				}else{
					ws.setStatusName("运行失败");
				}
				 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
				 
			}
		}
        pageBean.setRows(calculationHistories);
       
        return pageBean;
    }
    
    
    /**
     * 批量导出
     * @param request
     * @param resp
     * @param cal
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value="exportExcelAll")
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse resp,CalculationFrontHistory cal) throws IOException
	{
		try
		{
			if (null == request || null == resp)
			{
				return;
			}
			if(null != cal ){
				if(null != cal.getStartTime()){
					if(cal.getStartTime().equals("")){
						cal.setStartTime(null);
					}else{
						cal.setStartTime(cal.getStartTime().toString().trim());	
					}
					
				}
				if(null != cal.getEndTime()){
					if(cal.getEndTime().equals("")){
						cal.setEndTime(null);
					}else{
						cal.setEndTime(cal.getEndTime().toString().trim());
					}
					
				}
				
			}
			List<CalculationFrontHistory> listContent = calculationFrontHistoryService.selectBySelectiveTime(cal);
			//生成Excel文件
			exportExcel(request, resp, listContent);
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
    
    
    /**
     * 导出指定的
     * @param request
     * @param resp
     * @param cal
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value="exportExcel")
	public void exportExcels(HttpServletRequest request, HttpServletResponse resp,CalculationFrontHistory cal) throws IOException
	{
		try
		{
			if (null == request || null == resp)
			{
				return;
			}
			List<CalculationFrontHistory> listContent = calculationFrontHistoryService.selectBySelectiveKey(cal);
			//生成Excel文件
			exportExcel(request, resp, listContent);
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
    
    
    
    /**
	 * excel导出交易记录
	 * @param request
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	public void exportExcel(HttpServletRequest request,HttpServletResponse resp,List<CalculationFrontHistory> listContent) throws UnsupportedEncodingException
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		request.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/x-download");

		String fileName = "日志记录.xls";
		fileName = URLEncoder.encode(fileName, "UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		HSSFSheet sheet = wb.createSheet("日志记录");
		sheet.setDefaultRowHeight((short) (2 * 256));
		sheet.setColumnWidth(0, 50 * 160);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		HSSFRow row = sheet.createRow((int) 0);
		sheet.createRow((int) 1);
		sheet.createRow((int) 2);
		sheet.createRow((int) 3);
		sheet.createRow((int) 4);
		sheet.createRow((int) 5);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("执行者 ");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("工作流名称 ");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("结果状态");
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("时间");
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("原因");
		cell = row.createCell(5);

		List<CalculationFrontHistory> vUserOrder = listContent;

		for (int i = 0; i < vUserOrder.size(); i++)
		{
			HSSFRow row1 = sheet.createRow((int) i + 1);
			CalculationFrontHistory vuserOrder = vUserOrder.get(i);
			/*row1.createCell(0).setCellValue(i + 1);*/
			
			if(vuserOrder.getStatus() == 1){
				vuserOrder.setStatusName("运行中");
			}else if(vuserOrder.getStatus() == 2){
				vuserOrder.setStatusName("运行成功");
			}else{
				vuserOrder.setStatusName("运行失败");
			}
			 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 vuserOrder.setCreateTimes(dateformat.format(vuserOrder.getCreateTime()));
			
			row1.createCell(0).setCellValue(vuserOrder.getEmail());//执行者
			row1.createCell(1).setCellValue(vuserOrder.getWay());//工作流名称
			row1.createCell(2).setCellValue(vuserOrder.getStatusName());//结果
			row1.createCell(3).setCellValue(vuserOrder.getCreateTimes());//时间
			row1.createCell(4).setCellValue(vuserOrder.getResason());//原因
		}
		try
		{
			OutputStream out = resp.getOutputStream();
			wb.write(out);
			out.close();
		}
		catch (Exception e1)
		{
			LOGGER.info("=====导出excel异常====");
		}
	}
	
}
