package org.com.drag.web.controller;

import org.com.drag.common.util.Constants;
import org.com.drag.common.util.StringUtils;
import org.com.drag.model.*;
import org.com.drag.service.DatabaseConnectService;
import org.com.drag.service.DatabaseTypeService;
import org.com.drag.service.ReadResourceService;
import org.com.drag.web.util.LoadDatabaseLinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by gongmingxing on 2017/7/24/ 18:44.
 */


@Controller
@RequestMapping("/drag/ReadResource")
public class ReadResourcesController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    ReadResourceService<ResourceInfo> readResourceService;
    @Autowired
    DatabaseConnectService databaseConnectService;
    @Autowired
    DatabaseTypeService databaseTypeService;

    /**
     * 数据库连接测试，返回数据库的连接结果。
     */
    @RequestMapping(value = "linktest",method = RequestMethod.POST)
    public @ResponseBody String ReadResourceTest(ResourceInfo resourceInfo){
     //   String aaa = readResourceService.testConnection(resourceInfo);
        return readResourceService.testConnection(resourceInfo);
    }


    /**
     * 保存连接后加载所有的父节点
     */
    @RequestMapping(value = "savelink")
    public @ResponseBody List<Node> saveLink(ResourceInfo resourceInfo, HttpSession session) {
//        System.out.println("===========" + resourceInfo);
        List<Node> nodes = new ArrayList<>();

        String hostIp = resourceInfo.getHostIp();
        String hostPort = resourceInfo.getHostPort();
        String connName = resourceInfo.getConnectName();
        boolean isIp = LoadDatabaseLinkUtil.isIp(hostIp);
        boolean isPort = LoadDatabaseLinkUtil.isPort(hostPort);

        if(!StringUtils.hasText(connName)){
            Node node = new Node();
            node.setErrorInfo("连接名不能为空！");
            nodes.add(node);
            return nodes;
        }

        if(!isIp){
            Node node = new Node();
            node.setErrorInfo("请输入正确格式的ip地址！");
            nodes.add(node);
            return nodes;
        }
        if(!isPort){
            Node node = new Node();
            node.setErrorInfo("请输入正确格式的端口号！");
            nodes.add(node);
            return nodes;
        }

        User user = (User) session.getAttribute(Constants.USER_KEY);
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbConDbname(resourceInfo.getDatabaseName());
        databaseConnect.setDbConEnable(true);
        databaseConnect.setDbConIp(resourceInfo.getHostIp());
        databaseConnect.setDbConPort(Integer.parseInt(resourceInfo.getHostPort()));
        databaseConnect.setDbConName(resourceInfo.getConnectName());
        databaseConnect.setDbConPassword(resourceInfo.getPassWord());
        databaseConnect.setDbConUser(resourceInfo.getUserName());
        databaseConnect.setDbConUserId(user.getId());

        /**
         * 判断访问的源数据类型
         */
        if("mysql".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_MYSQL_ID);
        }
        if("oracle".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_ORACLE_ID);
        }
        if("hive".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_HIVE_ID);
        }

        /**
         * 判断要保存的连接名是否已经存在,如果存在，将错误信息信息返回到前台。
         */

        int countForConnName = databaseConnectService.selectByUserIdAndConnName(databaseConnect);

        if(countForConnName > 0){
            Node node = new Node();
            node.setErrorInfo("连接名已存在，请使用其他的连接名！");
            nodes.add(node);
            return nodes;
        }

        //将数据库连接信息写入数据库
        int insetResult = databaseConnectService.insert(databaseConnect);

        /**
         * 查询数据库中该用户下所有的数据库连接，得到ztree目录树的znodes。
         */

        if(insetResult != 0){
            nodes = LoadDatabaseLinkUtil.getNewParentNode(databaseConnect, databaseConnectService);
        }
        //System.out.println(nodes);
        return nodes;
    }

    /**
     * 停止连接
     */
    @RequestMapping(value = "stoplink")
    public @ResponseBody int stopLink(HttpServletRequest request, HttpSession session){
        String connName = request.getParameter("connName");
 //       System.out.println("1111111111111111111" + request.getParameter("connName"));
        User user = (User)session.getAttribute(Constants.USER_KEY);
        return LoadDatabaseLinkUtil.stopLink(user.getId(),connName,databaseConnectService);
    }

    /**
    * 启用连接
    * */
    @RequestMapping(value = "startlink")
    public @ResponseBody int startLink(HttpServletRequest request, HttpSession session){
        String connName = request.getParameter("connName");
        User user = (User)session.getAttribute(Constants.USER_KEY);
        return LoadDatabaseLinkUtil.startLink(user.getId(),connName,databaseConnectService);
    }

    /**
     * 删除数据库连接。
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteLink")
    public @ResponseBody int deleteLink(@RequestParam int id){
        int result = databaseConnectService.deleteByPrimaryKey(id);
        return result;
    }


    /**
    * 改变连接名
    * */
    @RequestMapping(value = "changeconname")
    public @ResponseBody int changeConnName(String connName, HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbConName(connName);
        databaseConnect.setDbConUserId(user.getId());
        return 0;
    }

    /**
     * ztree点击父节点异步加载表。
     */
    @RequestMapping(value = "loadtables")
    public @ResponseBody List<Node> loadTables(@RequestParam(defaultValue = "0") int pid){
       // System.out.println("++++++++++++" + pid);
        if(pid != 0){
            DatabaseConnect databaseConnect = databaseConnectService.selectByPrimaryKey(pid);
            ResourceInfo resourceInfo = new ResourceInfo();
            resourceInfo.setUserName(databaseConnect.getDbConUser());
            resourceInfo.setHostIp(databaseConnect.getDbConIp());
            resourceInfo.setHostPort(databaseConnect.getDbConPort().toString());
            String dbTypeName = databaseTypeService.selectByPrimaryKey(databaseConnect.getDbConTypeId()).getDbTypeName();
            resourceInfo.setType(dbTypeName);
            resourceInfo.setPassWord(databaseConnect.getDbConPassword());
            resourceInfo.setDatabaseName(databaseConnect.getDbConDbname());

            List<Node> nodes = new ArrayList<>();
            try {
                nodes = LoadDatabaseLinkUtil.loadTbales(databaseConnect, databaseTypeService, readResourceService);
                return nodes;
            } catch (Exception e) {
                e.printStackTrace();
                LoadDatabaseLinkUtil.setDruidDatasourceInited(resourceInfo);
//                if("mysql".equals(resourceInfo.getType())){
//                    String url = "jdbc:mysql:" + "//" + resourceInfo.getHostIp() + ":"
//                            + resourceInfo.getHostPort() + "/" + resourceInfo.getDatabaseName();
//                    DatabasePoolConnection.MyDruidDataSource myDruidDataSource =  DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
//                            .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
//                    myDruidDataSource.setInited(false);
//                }
//                if("oracle".equals(resourceInfo.getType())){
//                    String url = "jdbc:oracle:thin:@" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
//                            + ":" + resourceInfo.getDatabaseName();
//                    DatabasePoolConnection.MyDruidDataSource myDruidDataSource = DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
//                            .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
//                    myDruidDataSource.setInited(false);
//                }
//                if("hive".equals(resourceInfo.getType())){
//                    String url = "jdbc:hive2://" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
//                            + "/" + resourceInfo.getDatabaseName();
//                    DatabasePoolConnection.MyDruidDataSource myDruidDataSource = DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
//                            .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
//                    myDruidDataSource.setInited(false);
//                }

                Node node = new Node();
                node.setErrorInfo("错误连接：" + e.getMessage());
                nodes.add(node);
                return nodes;
            }
        }else{
            return null;
        }
    }

    /**
    * 双击表格，展示表中数据。
    * */
    @RequestMapping(value = "showtable")
    public @ResponseBody List<String> showTable(@RequestParam(value = "datas[]") String[] datas){
        int primaryKey = Integer.parseInt(datas[0]);
        String tablename = datas[1];
        List<String> tableDatas = LoadDatabaseLinkUtil.getTableDatas(primaryKey, tablename, databaseConnectService, databaseTypeService, readResourceService);
        return tableDatas;
    }

    @RequestMapping(value = "scanLink")
    public @ResponseBody ResourceInfo scanLink(@RequestParam int id){
        return LoadDatabaseLinkUtil.scanLink(id,databaseConnectService,databaseTypeService);
    }

    @RequestMapping(value = "editLink")
    public @ResponseBody Node editLink(ResourceInfo resourceInfo, HttpSession session){
        Node node = new Node();
        User user = (User)session.getAttribute(Constants.USER_KEY);

        String hostIp = resourceInfo.getHostIp();
        String hostPort = resourceInfo.getHostPort();
        String connName = resourceInfo.getConnectName();
        boolean isIp = LoadDatabaseLinkUtil.isIp(hostIp);
        boolean isPort = LoadDatabaseLinkUtil.isPort(hostPort);

        if(!StringUtils.hasText(connName)){
            node.setErrorInfo("连接名不能为空！");
            return node;
        }
        if(!isIp){
            node.setErrorInfo("请输入正确格式的ip地址！");
            return node;
        }
        if(!isPort){
            node.setErrorInfo("请输入正确格式的端口号！");
            return node;
        }

        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbCconId(resourceInfo.getPrimaryKey());
        databaseConnect.setDbConDbname(resourceInfo.getDatabaseName());
        databaseConnect.setDbConIp(resourceInfo.getHostIp());
        databaseConnect.setDbConPort(Integer.parseInt(resourceInfo.getHostPort()));
        databaseConnect.setDbConName(resourceInfo.getConnectName());
        databaseConnect.setDbConPassword(resourceInfo.getPassWord());
        databaseConnect.setDbConUser(resourceInfo.getUserName());
        databaseConnect.setDbConUserId(user.getId());

        /**
         * 判断访问的源数据类型
         */
        if("mysql".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_MYSQL_ID);
        }
        if("oracle".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_ORACLE_ID);
        }
        if("hive".equals(resourceInfo.getType())){
            databaseConnect.setDbConTypeId(Constants.DB_CON_TYPE_HIVE_ID);
        }


        /**
         * 判断要保存的连接名是否已经存在，如果存在，将错误信息信息返回到前台，同时要保证这个连接名不是自己修改前的连接名。
         */
        int countForConnName = databaseConnectService.selectByUserIdAndConnName(databaseConnect);
        DatabaseConnect oldConnect = databaseConnectService.selectByPrimaryKey(resourceInfo.getPrimaryKey());

        if(countForConnName > 0 && !(resourceInfo.getConnectName()).equals(oldConnect.getDbConName())){
            node.setId((long)resourceInfo.getPrimaryKey());
            node.setErrorInfo("连接名已存在，请使用其他的连接名！");
            return node;
        }

        /**
         * 更新编辑后的该条数据
         */
        int updateResult = databaseConnectService.updateByPrimaryKeySelective(databaseConnect);

        /**
         * 查询数据库中该用户下所有的数据库连接，得到ztree目录树的znodes。
         */
        if(updateResult == 0){
            node.setErrorInfo("编辑连接失败！");
            return node;
        }else{
            /**
             * ztree的返回值不能为null类型，所以封装一个只有id属性的Node返回回去。
             */
            node.setId((long)resourceInfo.getPrimaryKey());
            return node;
        }
    }

    /**
     * 数据库连接重命名。
     * @param datas
     * @param session
     * @return
     */
    @RequestMapping(value = "rename")
    public @ResponseBody Node rename(@RequestParam(value = "datas[]") String[] datas, HttpSession session){
        User user = (User)session.getAttribute(Constants.USER_KEY);
        int primaryKey = Integer.parseInt(datas[0]);
        String newName = datas[1].trim();
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbCconId(primaryKey);
        databaseConnect.setDbConName(newName);
        databaseConnect.setDbConUserId(user.getId());

        Node node = new Node();

        LOGGER.info("primaryKey===================" + primaryKey);
        String oldName = databaseConnectService.selectByPrimaryKey(primaryKey).getDbConName().trim();
        LOGGER.info("oldName===================" + oldName);
        /**
         * 查询新的命名是否在后台数据库已经存在。
         */
        int countForConnName = databaseConnectService.selectByUserIdAndConnName(databaseConnect);
        if(countForConnName > 0 && !oldName.equals(newName)){
            node.setId((long)primaryKey);
            node.setErrorInfo("连接名已存在，请重命名！");
            return node;
        }

        /**
         * 更新数据库
         */
        int resultCount = databaseConnectService.updateByPrimaryKeySelective(databaseConnect);
        if(resultCount == 0){
            node.setErrorInfo("重命名失败，请重试！");
        }

        /**
         * 构造一个只含有id的节点，表示重命名成功。
         */
        node.setId((long)primaryKey);
        return node;
    }

//    @RequestMapping(value = "preLoadTables")
//    public @ResponseBody String preLoadTables(@RequestParam int pid){
//        DatabaseConnect databaseConnect = databaseConnectService.selectByPrimaryKey(pid);
//        String dbTypeName = databaseTypeService.selectByPrimaryKey(databaseConnect.getDbConTypeId()).getDbTypeName();
//        ResourceInfo resourceInfo = new ResourceInfo();
//        resourceInfo.setUserName(databaseConnect.getDbConUser());
//        resourceInfo.setConnectNmae(databaseConnect.getDbConName());
//        resourceInfo.setHostIp(databaseConnect.getDbConIp());
//        resourceInfo.setHostPort(databaseConnect.getDbConPort().toString());
//        resourceInfo.setType(dbTypeName);
//        resourceInfo.setDatabaseName(databaseConnect.getDbConDbname());
//        resourceInfo.setPassWord(databaseConnect.getDbConPassword());
//        String result = readResourceService.testConnection(resourceInfo);
//        return result;
//    }
}


















