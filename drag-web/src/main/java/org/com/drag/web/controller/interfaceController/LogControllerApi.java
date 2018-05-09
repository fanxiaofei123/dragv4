package org.com.drag.web.controller.interfaceController;

import java.text.SimpleDateFormat;
import java.util.List;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.model.CalculationHistory;
import org.com.drag.service.CalculationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Copyright © 2016 All rights reserved.

 * @ClassName: LogControllerApi

 * @Description:日志记录

 * @author: jiaonanyue

 * @date: 2016年11月20日 下午6:17:24
 */
@Controller
@RequestMapping("/drag/interface")
public class LogControllerApi {
	
	@Autowired
	private CalculationHistoryService calculationHistoryService;
	
	
	/**
     * 查询所用用户的日志记录
     * @param page
     * @param calculationFrontHistory 实体 传frontusername;//前段用户名称
     * @return
     */
    @RequestMapping(value = "log")
    @ResponseBody
    public PageBean selectHistoryLog(Integer page ,CalculationHistory calculationFrontHistory){
    	
        PageBean pageBean = new PageBean();
    	if(page == null || page == 0){
			page = 1;
		}
		PageHelper.startPage(page,10); // 核心分页代码  测试
	    List<CalculationHistory> calculationHistories = calculationHistoryService.selectBySelectiveKey(calculationFrontHistory);
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
     * 通过id删除数据
     * @param id
     * @return
     */
    @RequestMapping(value = "del")
    @ResponseBody
    public ResponseResult delHistoryAll(Integer id){
       if(id != null ){
    	   int a = calculationHistoryService.deleteByPrimaryKey(id);
           if(a == 1){
        	   return new ResponseResult(HttpStatus.NOT_FOUND, "删除成功");
           }
           return new ResponseResult(HttpStatus.NOT_FOUND, "删除错误");
       }else{
    	   return new ResponseResult(HttpStatus.NOT_FOUND, "参数不对");
       }
	   
       
    }

}
