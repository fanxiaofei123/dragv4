package org.com.drag.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.model.FacadeModelRecord;
import org.com.drag.service.FacadeModelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: FacadeModelRecordControllerApi

 * @Description: 我的订阅接口

 * @author: jiaonanyue

 * @date: 2016年12月2日 上午10:17:21
 */
@Controller
@RequestMapping("/drag/modelRecord")
public class FacadeModelRecordController {
	
	@Autowired
	private FacadeModelRecordService facadeModelRecordService;
	
	    /**
	     * 保存我的訂閱信息
	     * @param id
	     * @return
	     */
	    @RequestMapping(value = "insert", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseResult insertFacadeModelRecord( FacadeModelRecord facadeModelRecord){
	    	
	    	if(facadeModelRecord != null){
	    		int a = facadeModelRecordService.insertSelective(facadeModelRecord);
	    		if(a == 1){
		        	   return new ResponseResult(HttpStatus.OK, "保存成功");
		           }
		           return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "保存失敗");
	    	}else{
	    		return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
	    	}
	       
	    }
	    
	    /**
	     * 删除订阅信息
	     * @param facadeModelRecord
	     * @return
	     */
	    @RequestMapping(value = "del", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseResult delectFacadeModelRecord( FacadeModelRecord facadeModelRecord){
	    	
	    	if(facadeModelRecord != null && facadeModelRecord.getId() != null){
	    		int a = facadeModelRecordService.deleteByPrimaryKey(facadeModelRecord.getId());
	    		if(a == 1){
		        	   return new ResponseResult(HttpStatus.OK, "删除成功");
		           }
		           return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除失敗");
	    	}else{
	    		return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
	    	}
	       
	    }
	    
	    
	    /**
	     * 修改状态
	     * @param facadeModelRecord
	     * @return
	     */
	    @RequestMapping(value = "upType", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseResult FacadeModelRecordType( @RequestBody FacadeModelRecord facadeModelRecord){
	    	
	    	if(facadeModelRecord != null && facadeModelRecord.getId() != null && facadeModelRecord.getType() != null){
	    		int a = facadeModelRecordService.updateByPrimaryKeySelective(facadeModelRecord);
	    		if(a == 1){
		        	   return new ResponseResult(HttpStatus.OK, "修改成功");
		           }
		           return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改失败");
	    	}else{
	    		return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "参数不对");
	    	}
	       
	    }
	    
	    
	    /**
	     * 分页查询我的订阅信息
	     * @param page
	     * @param facadeModelRecord
	     * @return
	     */
	    @RequestMapping(value = "select", method = RequestMethod.POST)
	    @ResponseBody
	    public PageBean selectFacadeModelRecord(Integer page , FacadeModelRecord facadeModelRecord){
	    	
	        PageBean pageBean = new PageBean();
	    	if(page == null || page == 0){
				page = 1;
			}
			PageHelper.startPage(page,10); // 核心分页代码  测试
			List<FacadeModelRecord> facadeModelRecordList = facadeModelRecordService.selectByFacadeModelRecord(facadeModelRecord);
			//设置返回的总记录数  
	        PageInfo<FacadeModelRecord> pageInfos=new PageInfo<FacadeModelRecord>(facadeModelRecordList);
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
	        if(facadeModelRecordList != null && facadeModelRecordList.size()>0){
				for(FacadeModelRecord ws :facadeModelRecordList){
					 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					 ws.setCreateTimes(dateformat.format(ws.getCreateTime()));
					 if(ws.getType() == 1){
						ws.setTypeName("待审核");
					 }
					 if(ws.getType() == 2){
						ws.setTypeName("审核通过");
					 }
					 if(ws.getType() == 3){
						ws.setTypeName("审核不通过");
					 }
					 
				}
			}
	        pageBean.setRows(facadeModelRecordList);
	       
	        return pageBean;
	    }


}
