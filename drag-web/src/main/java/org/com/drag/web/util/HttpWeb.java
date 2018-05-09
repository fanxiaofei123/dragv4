package org.com.drag.web.util;
import java.io.IOException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.client.params.AllClientPNames;
import org.com.drag.common.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

/**
 * Created by huangyu on 2017/10/20.
 */
public class HttpWeb {
    private static final Logger logger = LoggerFactory.getLogger(HttpWeb.class);

    /**
     * 获取重定向后的url地址
     * @param url
     * @return
     */
    public static ResponseResult getRedirectUrl(String url){
        ResponseResult responseResult= new ResponseResult();
        String redirectUrl="";
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        //用header的请求方式，减少返回值和一些非必要获取的信息
        HttpMethod method = new HeadMethod(url);
        HttpParams params = httpClient.getParams();
        params.setParameter(AllClientPNames.HANDLE_REDIRECTS, false);//设置为不重定向
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_METHOD_NOT_ALLOWED) {
                logger.info("Method failed: " + method.getStatusLine());
                responseResult.setCode(HttpStatus.SC_NOT_FOUND);
                responseResult.setMsg(method.getURI().getURI()+"could not be found, please try the history server");
            }
            // 处理内容
            redirectUrl=method.getURI().getURI();
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            responseResult.setCode(HttpStatus.SC_EXPECTATION_FAILED);
            responseResult.setMsg("Please check your provided http address!");
            logger.error("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            responseResult.setCode(HttpStatus.SC_EXPECTATION_FAILED);
            responseResult.setMsg("Please check your provided network!");
            logger.error("Please check your provided network!");
            e.printStackTrace();
        } finally {
            // 释放连接
            method.releaseConnection();
            if (responseResult.getCode()==200){
                return new ResponseResult(HttpStatus.SC_OK,redirectUrl);
            }else{
                return responseResult;
            }
        }
    }


    /**
     * 获取html页面内容
     * @param url
     * @return
     */
    public static ResponseResult getHtmlByGet(String url){
        String html = "";
        ResponseResult responseResult = new ResponseResult();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建GET方法的实例
        GetMethod getMethod = new GetMethod(url);
        // 使用系统提供的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                logger.info("Method failed: " + getMethod.getStatusLine());
                responseResult.setCode(HttpStatus.SC_NOT_FOUND);
                responseResult.setMsg("该日志已过期，请重新运行！");
            }
            // 处理内容
            html = getMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            responseResult.setCode(HttpStatus.SC_EXPECTATION_FAILED);
            responseResult.setMsg("Please check your provided http address!");
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            responseResult.setCode(HttpStatus.SC_EXPECTATION_FAILED);
            responseResult.setMsg("Please check your provided network!");
            // 发生网络异常
            logger.error("Please check your provided network!");
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
            if (responseResult.getCode()==200){
                return new ResponseResult(HttpStatus.SC_OK,html);
            }else{
                return responseResult;
            }
        }
    }
}

