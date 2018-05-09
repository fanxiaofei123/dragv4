package org.com.drag.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.com.drag.common.util.Constants;
import org.com.drag.model.DatabaseConnect;
import org.com.drag.model.Node;
import org.com.drag.model.User;
import org.com.drag.service.DatabaseTypeService;
import org.com.drag.service.DatabaseConnectService;
import org.com.drag.service.ReadResourceService;
import org.com.drag.web.util.LoadDatabaseLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;

/**
 * Created by sky on 2017/7/17.
 */
@Controller
@RequestMapping("drag/database")
public class DatabaseConnectController {

    @Autowired
    DatabaseConnectService dataBaseConnectService;
    @Autowired
    DatabaseTypeService databaseTypeService;
    @Autowired
    ReadResourceService readResourceService;

    //数据库连接界面，点击数据库连接按钮，进入页面，加载ztree的nodes数据。
    @RequestMapping(value = "data")
    public String ConnectData(HttpSession session, ModelMap modelMap){
        System.out.println(readResourceService);
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<DatabaseConnect> databaseConnects = dataBaseConnectService.selectByUserID(user.getId());
        List<Node> nodes = new ArrayList<Node>();

        //获取父节点
        nodes = LoadDatabaseLinkUtil.getParentNodes(dataBaseConnectService,databaseTypeService,readResourceService,user.getId());
        JSONArray treelist = JSONArray.fromObject(nodes);
        modelMap.put("treelist",treelist);
        return "dataSource/databaseLink";
    }
   /* @RequestMapping(value = "fileMana")
    public String ConnectData1(){
        return "dataSource/fileManage";
    }*/
}
