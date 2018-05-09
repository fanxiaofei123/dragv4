package org.com.drag.common.base;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.StringEscapeEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: BaseController

 * @Description: 基础Controller

 * @author: jiaonanyue

 * @date: 2016年10月20日 上午10:36:41
 */
public abstract class BaseController {
	

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }


    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        ResponseResult result = new ResponseResult();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        ResponseResult result = new ResponseResult();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
}
