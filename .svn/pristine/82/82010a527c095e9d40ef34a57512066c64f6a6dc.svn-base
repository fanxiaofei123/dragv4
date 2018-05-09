package org.com.drag.web.controller.interfaceController;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.dao.UserMapper;
import org.com.drag.model.CalculationHistory;
import org.com.drag.model.Connect;
import org.com.drag.model.FacadeModel;
import org.com.drag.model.Model;
import org.com.drag.model.ModelAttribute;
import org.com.drag.model.ModelXmlInfo;
import org.com.drag.model.User;
import org.com.drag.model.WorkFlow;
import org.com.drag.service.CalculationHistoryService;
import org.com.drag.service.FacadeModelService;
import org.com.drag.service.JobServiceApi;
import org.com.drag.service.WorkFlowService;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.api.IOozieApi;
import org.com.drag.web.common.ModelUtils;
import org.com.drag.web.common.WorkFlowXmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

/**
 * Created by cdyoue on 2016/11/21.
 */
@Controller
@RequestMapping("/drag/interface")
public class CalculationControllerAPI {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private CalculationHistoryService calculationHistoryService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private JobServiceApi jobServiceApi;
    @Autowired
    private IHdfsApi iHdfsApi;
    @Autowired
    private IOozieApi  iOozieApi;
    @Autowired
    private UserMapper userDao;
    @Autowired
    private FacadeModelService facadeModelService;
    
    /**
     * 工作流的运行
     * 
     * workFlow   实体里面必传参数  Integer id,String name,String workspaceName,Integer Modelid //模型id
     * @param session url hdfs://172.16.0.31:8020/user/jny3/datas/d/input/MOCK_DATA.csv
     * modelName 前端传过来的名称
     * @return
     */
	@RequestMapping(value = "modelRun")
    @ResponseBody
    public  ResponseResult runCalculation(HttpSession session,HttpServletRequest request,int modelid,String url,String modelName) {
		User sessionuser = (User)session.getAttribute(Constants.USER_KEY);
		FacadeModel facadeModel = facadeModelService.selectByPrimaryKey(modelid);
		//更新模型调用次数
		facadeModel.setInterfacenum(facadeModel.getInterfacenum()+1);
		facadeModelService.updateByPrimaryKeySelective(facadeModel);
		String context = facadeModel.getModelContext();
		facadeModel.setModelName(modelName);
		JSONObject jsonobject = JSONObject.fromObject(context);
		String modelss =(String) jsonobject.get("models");
		String connect = (String) jsonobject.get("connects");
		List<Model> models1 =JSON.parseArray(modelss,Model.class);
		List<Connect> connects = JSON.parseArray(connect, Connect.class);
		List<ModelAttribute> data = new ArrayList<ModelAttribute>();
		for(int i = 0; i < models1.size(); i++){
			data =models1.get(i).getData();
			for(ModelAttribute modelAtt :data){
				if(modelAtt.getMattribute().equals("inputPath")){
					String usrs = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX).append(facadeModel.getUserName()).append("/datas/").append(url).toString();
					modelAtt.setMvalue(usrs);
				}
			}
			System.out.println("==========models1========="+models1.get(i));
		}

        
        //生成xml和properties
        ModelUtils modelUtils = new ModelUtils();
        ModelXmlInfo modelXmlInfo = modelUtils.getSortingFormatModels(models1, connects,new StringBuffer(facadeModel.getWorkspaceName()).append("/").append(facadeModel.getName()).toString(),facadeModel.getUserName(),sessionuser.getId(),null,null);
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
            xmlProperties = WorkFlowXmlUtils.buildXml(modelXmlInfo, path, new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX).append(facadeModel.getUserName()).append("/workspaces/").append(facadeModel.getWorkspaceName()).append("/").append(facadeModel.getName()).toString(),facadeModel.getName(), facadeModel.getUserName());
            WorkFlowXmlUtils.updateXml(path, path);
            //上传xml到hdfs
            uploadBooler = iHdfsApi.uploadWorkFlowFile(path, new StringBuffer(Constants.WORKSPACES_SUFFIX).append("/").append(facadeModel.getWorkspaceName()).append("/").append(facadeModel.getName()).append("/").append("workflow.xml").toString(), facadeModel.getUserName());
        } finally {
            if(file != null){
                removedir(file);
            }
        }

        if(!uploadBooler){
            return  new ResponseResult(HttpStatus.EXPECTATION_FAILED);
        }
        User u = new User();
        u.setLoginname(facadeModel.getUserName());
        User user = userDao.selectByUser(u);
        //运行任务
        Integer jobId = jobServiceApi.run(xmlProperties,user.getId(),facadeModel,modelXmlInfo.getResultAddress(),models1.size());
        
        ResponseResult responseResult = new ResponseResult(HttpStatus.OK);
       //查询当前工作空间的历史记录
        /*CalculationHistory ch = new CalculationHistory();
        ch.setUserid(user.getId());
        ch.setFlowId(facadeModel.getFlowId());

        List<CalculationHistory> results =  calculationHistoryService.selectBySelectiveKey(ch);
        */

        Map<String, Object> map = new HashMap<String, Object>();
        /*map.put("historys", results);
        map.put("jobId", jobId);*/
        map.put("id", jobId);
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
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setStatus(1);
        List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveKey(calculationHistory);
        return calculationHistories.size() == 0 ? false : true;
    }

    
    
    /**
     * 分页查询用户的运行记录
     * @return
     */
    @RequestMapping(value = "history",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistory(Integer page ,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveKey(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
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
			for(CalculationHistory ws :calculationHistories){
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
    
    
    /**
     * 查询所用用户的日志记录
     * @param page
     * @param session
     * @return
     */
    @RequestMapping(value = "historyAll",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryAll(Integer page ,HttpSession session){
    	User user = (User) session.getAttribute(Constants.USER_KEY);
        CalculationHistory calculationHistory = new CalculationHistory();
        calculationHistory.setUserid(user.getId());
        
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelective(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
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
			for(CalculationHistory ws :calculationHistories){
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
     * @param session
     * @return
     */
    @RequestMapping(value = "historyTime",method = RequestMethod.POST)
    @ResponseBody
    public PageBean selectHistoryTime(Integer page ,HttpSession session,CalculationHistory calculationHistory){
        
    	if(null != calculationHistory ){
			if(null != calculationHistory.getStartTime()){
				calculationHistory.setStartTime(calculationHistory.getStartTime().toString().trim());
			}
			if(null != calculationHistory.getEndTime()){
				calculationHistory.setEndTime(calculationHistory.getEndTime().toString().trim());
			}
			
		}
    	
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveTime(calculationHistory);
		//设置返回的总记录数  
        PageInfo<CalculationHistory> pageInfos=new PageInfo<CalculationHistory>(calculationHistories);
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
			for(CalculationHistory ws :calculationHistories){
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
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse resp,CalculationHistory cal) throws IOException
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
			List<CalculationHistory> listContent = calculationHistoryService.selectBySelectiveTime(cal);
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
	public void exportExcels(HttpServletRequest request, HttpServletResponse resp,CalculationHistory cal) throws IOException
	{
		try
		{
			if (null == request || null == resp)
			{
				return;
			}
			List<CalculationHistory> listContent = calculationHistoryService.selectBySelectiveKey(cal);
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
	public void exportExcel(HttpServletRequest request,HttpServletResponse resp,List<CalculationHistory> listContent) throws UnsupportedEncodingException
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

		List<CalculationHistory> vUserOrder = listContent;

		for (int i = 0; i < vUserOrder.size(); i++)
		{
			HSSFRow row1 = sheet.createRow((int) i + 1);
			CalculationHistory vuserOrder = vUserOrder.get(i);
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
