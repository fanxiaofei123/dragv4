package org.com.drag.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.UploadHistory;
import org.com.drag.model.User;
import org.com.drag.service.UploadHistoryService;
import org.com.drag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cdyoue on 2016/11/17.
 */
@Controller
@RequestMapping("drag/history")
public class UploadHistroyController {
	
	
	
    @Autowired
    private UploadHistoryService uploadHistoryService;
    @Autowired
    private UserService userService;
    
    
    
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public  List<UploadHistory> listHistory(HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        Integer id = user.getId();

        Integer limit = user.getTips();
        limit = (limit == 0 ? 0 : limit);
        List<UploadHistory> uploadHistorys  =  uploadHistoryService.selectByUserKey(id,limit);
        user.setTips(0);
        userService.updateByPrimaryKeySelective(user);
        return uploadHistorys;
    }
    
    @RequestMapping(value = "del",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult delUploadHistory(Integer id){
        int result = uploadHistoryService.deleteByPrimaryKey(id);
        return result == 1 ? new ResponseResult(HttpStatus.OK, "删除成功") : new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除错误");
    }

    @RequestMapping(value = "listAll",method = RequestMethod.GET)
    @ResponseBody
    public  List<UploadHistory> listAllHistory(HttpSession session){
        List<UploadHistory> uploadHistorys = uploadHistoryService.selectAll();
        return uploadHistorys;
    }
}
