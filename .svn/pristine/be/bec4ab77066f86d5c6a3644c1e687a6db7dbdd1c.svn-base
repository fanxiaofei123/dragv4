package org.com.drag.web.controller;

import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.com.drag.model.UploadStatus;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cdyoue on 2016/11/14.
 */
@Controller
@RequestMapping("drag")
public class ProgressController {
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "progress",method = RequestMethod.GET)
    public void  getProgressing(HttpSession session, PrintWriter printWriter){
        UploadStatus status = (UploadStatus) session.getAttribute("status");


        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位

        numberFormat.setMaximumFractionDigits(2);
        String progress = numberFormat.format((float) status.getBytesRead() / (float) status.getContentLength() * 100);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("progress", progress+"%");
        printWriter.write(jsonObject.toString());
    }
}
