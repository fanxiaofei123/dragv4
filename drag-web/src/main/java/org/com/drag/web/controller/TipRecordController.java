package org.com.drag.web.controller;

import com.github.pagehelper.PageHelper;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.page.PageInfo;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.Model;
import org.com.drag.model.TipRecord;
import org.com.drag.model.User;
import org.com.drag.service.TipRecordService;
import org.com.drag.service.oozie.bean.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.com.drag.web.common.ListSort.fileInfoListSort;

/**
 * Created by Guzhendong on 2017/8/7 0007.
 */
@Controller
@RequestMapping("/tipRecord")
public class TipRecordController {
    public static final int DEFAULT_ROWCOUNT=5;
    public static final int DEFAULT_PAGENUM=1;
    @Autowired
    private TipRecordService tipRecordService;

    /**
     *
     * @param page  当前页码
     * @param tipRec 用TipRecord去接受参数
     * @param session
     * @return
     */
    @RequestMapping("selectrecordlist")
    @ResponseBody
    public PageBean selectRecordList(Integer page,TipRecord tipRec, HttpSession session){
        if (page==null|| page==0){
            page=DEFAULT_PAGENUM;
        }
        Integer rowCount=DEFAULT_ROWCOUNT;
        User user = (User) session.getAttribute(Constants.USER_KEY);
        tipRec.setLoginName(user.getLoginname());
        tipRec.setInputName(tipRec.getInputName());
        if (tipRec.getStartTime()!=""&&tipRec.getStartTime()!=null&&tipRec.getEndTime()!=""&&tipRec.getEndTime()!=null){
            tipRec.setStartTime(tipRec.getStartTime()+" 0:00:00");
            tipRec.setEndTime(tipRec.getEndTime()+" 23:59:59");
        }
        PageHelper.startPage(page,rowCount);//分页核心
        List<TipRecord> tipRecords = tipRecordService.selectAll(tipRec);
        for (TipRecord tipRecord : tipRecords) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tipRecord.setRecordSendTime(format.format(tipRecord.getTipRecordSendTime()));
            if(tipRecord.getTipConfigSendType()==0){
                tipRecord.setConfigSendType("邮箱");
            }else{
                tipRecord.setConfigSendType("手机");
            }
            if(tipRecord.getTipConfigType()==0){
                tipRecord.setConfigType("出错");
            }else if(tipRecord.getTipConfigType()==1){
                tipRecord.setConfigType("完成");
            }
            if(tipRecord.getTipRecordStatus()==1){
                tipRecord.setRecordStatus("发送成功");
            }else{
                tipRecord.setRecordStatus("发送失败");
            }
        }
        com.github.pagehelper.PageInfo<TipRecord> pageInfo = new com.github.pagehelper.PageInfo<TipRecord>(tipRecords);
        PageBean pageBean = new PageBean();
        pageBean.setCurPage(page);
        pageBean.setTotal(tipRecords.size());
        pageBean.setRows(tipRecords);
        pageBean.setTotalPage(pageInfo.getPages());
        return pageBean;

    }


    @RequestMapping("getdefaultdate")
    @ResponseBody
    public ResponseResult getDefaultDate(HttpSession session){
        ResponseResult result;
        User user = (User) session.getAttribute(Constants.USER_KEY);
        TipRecord tipRecord = new TipRecord();
        tipRecord.setLoginName(user.getLoginname());
        List<TipRecord> tipRecords = tipRecordService.selectAll(tipRecord);
        if (tipRecords.size()>0 && tipRecords!=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String maxDate=format.format(tipRecords.get(0).getTipRecordSendTime());
            String minDate=format.format(tipRecords.get(tipRecords.size()-1).getTipRecordSendTime());
            String defaultDate=minDate+"/"+maxDate;
            result = new ResponseResult(HttpStatus.OK,defaultDate);
        }else{
            result = new ResponseResult(HttpStatus.NOT_FOUND,"暂无数据");
        }

        return result;
    }

}
