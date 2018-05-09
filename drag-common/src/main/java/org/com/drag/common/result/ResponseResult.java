package org.com.drag.common.result;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: Result

 * @Description: 操作结果集

 * @author: jiaonanyue

 * @date: 2016年10月20日 上午10:30:01
 */
public class ResponseResult implements Serializable {
	/*
	 * 序列化
	 */
	private static final long serialVersionUID = 5576237395711742681L;

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private boolean success = false;

    private String msg = "";

//    private String msg1 = "";

    private Object obj = null;

    private int code;


    public ResponseResult() {
        this.code = 200;
    }

    public ResponseResult(int code,Object obj){
        this.code = code;
        this.obj = obj;
    }

    public ResponseResult(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.msg = httpStatus.getReasonPhrase();
    }

    public ResponseResult(HttpStatus httpStatus, String msg) {
        this.code = httpStatus.value();
        this.msg = msg;
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(HttpStatus httpStatus, String msg, Object data) {
        this.code = httpStatus.value();
        this.msg = msg;
        this.obj = data;
    }

    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.obj = data;
    }
//
//    public ResponseResult(int code,String msg, String msg1, Object data){
//        this.code = code;
//        this.msg = msg;
//        this.msg1 = msg1;
//        this.obj = data;
//    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
	public String toString() {
		return "Result [success=" + success + ", msg=" + msg + ", obj=" + obj + "]";
	}

//    public String getMsg1() {
//        return msg1;
//    }
//
//    public void setMsg1(String msg1) {
//        this.msg1 = msg1;
//    }
}
