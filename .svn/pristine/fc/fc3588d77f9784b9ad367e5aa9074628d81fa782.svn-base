package org.com.drag.web.controller;

import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.TipConfig;
import org.com.drag.model.User;
import org.com.drag.service.TipConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Guzhendong on 2017/8/7 0007.
 */
@Controller
@RequestMapping("/tipConfig")
public class TipConfigController {
    /**
     * 每页显示数据条数
     */
    private static final Integer PAGE_NUMBER = 6;
    @Autowired
    public TipConfigService tipConfigService;

    @RequestMapping(value = "selectId", method = RequestMethod.POST)
    @ResponseBody
    public TipConfig selectTip(Integer id) {
        return tipConfigService.selectByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping("add")
    public Integer addTipConfig(TipConfig tipConfig) {
        return tipConfigService.insert(tipConfig);
    }

    @ResponseBody
    @RequestMapping("del")
    public Integer delTipConfig(Integer tipConfigId) {
        return tipConfigService.deleteByPrimaryKey(tipConfigId);
    }

    @ResponseBody
    @RequestMapping("adds")
    public ResponseResult addsTipConfig(HttpSession session, TipConfig tipConfig, Integer[] jobIds) {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if (user == null) {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请登录用户!");
        }
        TipConfig newTipConfig = new TipConfig();
        newTipConfig.setLoginname(user.getLoginname());
        List<TipConfig> list = tipConfigService.selectAll(newTipConfig);
        for (TipConfig config : list) {
            for (int i = 0; i < jobIds.length; i++) {

                if ((config.getSchJobId().equals(jobIds[i])) && (config.getTipConfigType().equals(tipConfig.getTipConfigType()))) {
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "添加" + config.getSchJobName() + "重复了!");
                }

            }
        }
        Integer result = 0;
        for (int i = 0; i < jobIds.length; i++) {
            tipConfig.setSchJobId(jobIds[i]);
            tipConfig.setTipConfigSendType((short) 0);
            tipConfig.setTipConfigCreateTime(new Date());
            tipConfig.setTipConfigEnable(false);
            result = tipConfigService.insert(tipConfig);
        }
        if (result != 0) {
            return new ResponseResult(HttpStatus.OK, "添加成功!");
        }
        return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "添加失败!");
    }

    /**
     * 多删除
     *
     * @param tipConfigIds
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteById")
    public ResponseResult deleteByIdTipConfig(String tipConfigIds) {
        String[] tipConfigId = tipConfigIds.split(",");
        Integer integer = tipConfigService.deleteById(tipConfigId);
        if (0 != integer) {
            return new ResponseResult(HttpStatus.OK, "删除成功");
        } else {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "删除失败");
        }
    }

    @ResponseBody
    @RequestMapping("selectAll")
    public List selectAllTipConfig(TipConfig tipConfig, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        tipConfig.setLoginname(user.getLoginname());
        return tipConfigService.selectAll(tipConfig);
    }

    @ResponseBody
    @RequestMapping("selectAllByAnyThing")
    public PageBean selectAllTipAnyThing(Integer page, Integer rowCount, TipConfig tipConfig, String tipName, HttpSession session) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (rowCount == null) {
            rowCount = PAGE_NUMBER;
        }

        User user = (User) session.getAttribute(Constants.USER_KEY);
        tipConfig.setLoginname(user.getLoginname());
        if (!"".equals(tipName)) {
            tipConfig.setTipConfigName(tipName);
        }
        List<TipConfig> list = tipConfigService.selectAll(tipConfig);
        for (TipConfig config : list) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            config.setTipConfigCreateTimes(dateformat.format(config.getTipConfigCreateTime()));
            if (config.getTipConfigSendType() == 0) {
                config.setSendType("邮件");
            } else if (config.getTipConfigSendType() == 1) {
                config.setSendType("手机");
            }
            if (config.getTipConfigType() == 0) {
                config.setConfigType("出错");
            } else if (config.getTipConfigType() == 1) {
                config.setConfigType("完成");
            } else {
                config.setConfigType("未按时完成");
            }
            if (config.getTipConfigEnable()) {
                config.setTipConfigEnables("启用");
            } else {
                config.setTipConfigEnables("未启用");
            }
        }


        List filelist = new ArrayList<>();
        int startItem = (page - 1) * rowCount;
        int endItem = page * rowCount - 1;
        int totalPage = 0;
        if (rowCount > list.size()) {
            totalPage = 1;
        } else {
            totalPage = list.size() % rowCount == 0 ? list.size() / rowCount : list.size() / rowCount + 1;
        }

        if (endItem > list.size() - 1) {
            endItem = list.size() - 1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(list.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);

        for (int i = startItem; i <= endItem; i++) {
            filelist.add(list.get(i));
        }
        pageBean.setRows(filelist);
        return pageBean;
    }

    @ResponseBody
    @RequestMapping("update")
    public ResponseResult updateTipConfig(TipConfig tipConfig, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if (user == null) {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请登录用户!");
        }
        TipConfig newTipConfig = new TipConfig();
        newTipConfig.setLoginname(user.getLoginname());
        List<TipConfig> list = tipConfigService.selectAll(newTipConfig);
        for (TipConfig config : list) {
            if (!tipConfig.getTipConfigId().equals(config.getTipConfigId())) {
                if ((config.getSchJobId().equals(tipConfig.getSchJobId())) && (config.getTipConfigType().equals(tipConfig.getTipConfigType()))) {
                    return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "修改" + config.getSchJobName() + "的提醒类型重复了!");
                }
            }

        }
        return new ResponseResult(HttpStatus.OK, "修改成功", tipConfigService.updateByPrimaryKeySelective(tipConfig));
    }

    /**
     * 修改为提醒是否启用 0.未启用 1.启用
     *
     * @param tipConfig
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("updateEnable")
    public ResponseResult updateTipConfigEnable(TipConfig tipConfig, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        if (user == null) {
            return new ResponseResult(HttpStatus.EXPECTATION_FAILED, "请登录用户!");
        }
        if (tipConfig.getTipConfigEnable() == false) {
            tipConfig.setTipConfigEnable(true);
        } else {
            tipConfig.setTipConfigEnable(false);
        }

        return new ResponseResult(HttpStatus.OK, "修改成功", tipConfigService.updateByPrimaryKeySelective(tipConfig));
    }

    @RequestMapping("selectByJobName")
    @ResponseBody
    public ResponseResult selectByJobName(HttpSession session) {

        User user = (User) session.getAttribute(Constants.USER_KEY);
        String userLoginName = user.getLoginname();
//        return  tipConfigService.selectByJobName(userLoginName);
        return new ResponseResult(HttpStatus.OK, "查询成功", tipConfigService.selectByJobName(userLoginName));
    }
}
