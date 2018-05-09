package org.com.drag.web.controller.interfaceController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.com.drag.common.page.PageBean;
import org.com.drag.model.Backmsg;
import org.com.drag.service.BackmsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by cdyoue on 2016/11/21.
 */
@Controller
@RequestMapping("/drag/backMsg")
public class BackMsgController {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private BackmsgService backmsgService;

        /**
         * 通过时间段查询用户问题反馈
         * @param page
         * @return
         */
        @RequestMapping(value = "selectAll")
        @ResponseBody
        public PageBean selectBackMsg(Integer page ,String frontusername,String startTime,String endTime,Backmsg backmsg) throws ParseException {

            if(null != backmsg ){
                if(null != backmsg.getStartTime()){
                    backmsg.setStartTime(backmsg.getStartTime().toString().trim());
                }
                if(null != backmsg.getEndTime()){
                    backmsg.setEndTime(backmsg.getEndTime().toString().trim());
                }
            }
            if( null != frontusername){
                backmsg.setFrontname(frontusername);
            }

            PageBean pageBean = new PageBean();
            if(page == null || page == 0){
                page = 1;
            }
            PageHelper.startPage(page,10); // 核心分页代码  测试
            List<Backmsg> backmsgs = backmsgService.selectBySelectiveTime(backmsg);
            //设置返回的总记录数
            PageInfo<Backmsg> pageInfos=new PageInfo<Backmsg>(backmsgs);
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
            if(backmsgs != null && backmsgs.size()>0){
                for(Backmsg ws :backmsgs){
                     ws.setCreatetime(ws.getCreatetime());
                }
            }
            pageBean.setRows(backmsgs);

            return pageBean;
        }

        @ResponseBody
        @RequestMapping(value = "InsertMsg")
        public Integer InsertMsg(String email,String phone,String content,String frontname,Backmsg backmsg){
            backmsg.setCreatetime(new Date());
            backmsg.setFrontname(frontname);
            backmsg.setContent(content);
            backmsg.setEmail(email);
            backmsg.setPhone(phone);
            backmsgService.insert(backmsg);
            return 200;
        }


        /**
         * 删除
         * @param id
         * @return
         */
        @RequestMapping(value = "deleteById")
        @ResponseBody
        public Integer deleteALL(Integer id){
            try {
                backmsgService.deleteByPrimaryKey(id);
            }catch (Exception e){
                e.printStackTrace();
                return 400;
            }
            return 200;
        }

        /**
         * 查看详情
         * @param response
         * @param request
         * @param id
         * @return
         */
        @RequestMapping("FindById")
        public String findOneById(HttpServletResponse response, HttpServletRequest request, Integer id){
            response.setCharacterEncoding("text/html;charset=utf-8");
            Backmsg backmsg=null;
            try{
                    backmsg=backmsgService.selectByPrimaryKey(id);
            }catch(Exception e){
                e.printStackTrace();
            }

            request.setAttribute("backmsg", backmsg);
            return "/backDetail";
        }
}
