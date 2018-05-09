package org.com.drag.web.util;

import org.com.drag.common.util.DatabasePoolConnection;
import org.com.drag.model.*;
import org.com.drag.service.DatabaseConnectService;
import org.com.drag.service.DatabaseTypeService;
import org.com.drag.service.ReadResourceService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadDatabaseLinkUtil {
    private final static  String  REXP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    private static Pattern pat = Pattern.compile(REXP);
    /*
       TODO 目录树初始化或刷新时创建父亲节点。
     */
    public static List<Node> getParentNodes(DatabaseConnectService databaseConnectService,
                                            DatabaseTypeService databaseTypeService, ReadResourceService readResourceService, int userId){

 //       System.out.println("=============" + dataBaseConnectService);
        List<Node> nodes = new ArrayList<Node>();
        List<DatabaseConnect> databaseConnects = databaseConnectService.selectByUserID(userId);
        for (DatabaseConnect connect : databaseConnects) {
            DatabaseConnect databaseConnect1 = new DatabaseConnect();
            databaseConnect1.setDbConUserId(connect.getDbConUserId());
            databaseConnect1.setDbConName(connect.getDbConName());

            //从数据库中查询该数据库连接并获取主键作为前台znode父节点的唯一标识。
            DatabaseConnect returnConn = databaseConnectService.findByUserIdAndConnName(databaseConnect1);

            //创建znode的父节点
            Node parentNode = new Node();
            parentNode.setId((long)returnConn.getDbCconId());
            parentNode.setClosed(!returnConn.getDbConEnable());
            parentNode.setpId(-1l);
            parentNode.setName(connect.getDbConName());
            parentNode.setIsParent(true);
            parentNode.setNoCheck(true);
            nodes.add(parentNode);
        }
        System.out.println("----------------" + nodes);
        return nodes;
    }

    /*
    *  TODO 目录树右键停止连接，将连接状态写入数据库。
    */
    public static int stopLink(int userid,String connName,DatabaseConnectService databaseConnectService){
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbConUserId(userid);
        databaseConnect.setDbConName(connName);
        databaseConnect.setDbConEnable(false);
 //       System.out.println(databaseConnect);
        return databaseConnectService.updateConnEnadbleByUserIdAndConnName(databaseConnect);
    }

    /*
    * TODO 目录树右键启用连接，将连接状态写入数据库。
    * */
    public static int startLink(int userid,String connName,DatabaseConnectService dataBaseConnectService){
        DatabaseConnect databaseConnect = new DatabaseConnect();
        databaseConnect.setDbConUserId(userid);
        databaseConnect.setDbConName(connName);
        databaseConnect.setDbConEnable(true);
//        System.out.println("XXXXXXXXXXXXXX" + databaseConnect);
        return dataBaseConnectService.updateConnEnadbleByUserIdAndConnName(databaseConnect);
    }

    /*
    * TODO ztree异步加载数据表。
    * */
    public static List<Node> loadTbales(DatabaseConnect connect,DatabaseTypeService databaseTypeService,
                                        ReadResourceService<ResourceInfo> readResourceService) throws Exception{
        List<Node> nodes = new ArrayList<Node>();
        if(connect.getDbConEnable()){
            ResourceInfo resourceInfo1 = new ResourceInfo();
            resourceInfo1.setConnectNmae(connect.getDbConName());
            resourceInfo1.setDatabaseName(connect.getDbConDbname());
            resourceInfo1.setHostIp(connect.getDbConIp());
            resourceInfo1.setHostPort(connect.getDbConPort().toString());
            resourceInfo1.setPassWord(connect.getDbConPassword());
            //根据连接的typeid查询出type类型
            DatabaseType databaseType = databaseTypeService.selectByPrimaryKey(connect.getDbConTypeId());
            resourceInfo1.setType(databaseType.getDbTypeName());
            resourceInfo1.setUserName(connect.getDbConUser());
            List<String> tableNames = readResourceService.getTableNames(resourceInfo1);

            //遍历所有的表名，构建znode的子节点
            for (String tabelName : tableNames) {
                Node childnode = new Node();
                childnode.setId(0l);
                childnode.setpId((long)connect.getDbCconId());
                childnode.setName(tabelName);
                childnode.setIsParent(false);
                childnode.setNoCheck(true);
                nodes.add(childnode);
            }
        }
        return nodes;
    }

    /**
     * TODO 新建连接时获取后台数据。
     */

    public static List<Node> getNewParentNode(DatabaseConnect connect,DatabaseConnectService databaseConnectService){
        List<Node> nodes = new ArrayList<>();
        DatabaseConnect returnConn = databaseConnectService.findByUserIdAndConnName(connect);
        Node newNode = new Node();
        newNode.setId((long)returnConn.getDbCconId());
        newNode.setIsParent(true);
        newNode.setClosed(!returnConn.getDbConEnable());
        newNode.setName(connect.getDbConName());
        newNode.setpId(-1l);
        nodes.add(newNode);
        return nodes;
    }

    /*
    * TODO 获取数据表的字段信息和表内容，返回前台展示。
    * */
    public static List<String> getTableDatas(int primaryKey, String tableName, DatabaseConnectService databaseConnectService,
                      DatabaseTypeService databaseTypeService, ReadResourceService<ResourceInfo> readResourceService){
        DatabaseConnect connect = databaseConnectService.selectByPrimaryKey(primaryKey);
        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setUserName(connect.getDbConUser());
        resourceInfo.setPassWord(connect.getDbConPassword());
        DatabaseType databaseType = databaseTypeService.selectByPrimaryKey(connect.getDbConTypeId());
        resourceInfo.setType(databaseType.getDbTypeName());
        resourceInfo.setHostIp(connect.getDbConIp());
        resourceInfo.setHostPort(connect.getDbConPort().toString());
        resourceInfo.setDatabaseName(connect.getDbConDbname());
        resourceInfo.setTableName(tableName);

        List<String> metaDataAndContents = new ArrayList<>();
        String metaData = readResourceService.getMetaData(resourceInfo);
        List<String> contentData = readResourceService.getContentData(resourceInfo);
        metaDataAndContents.add(metaData);
        metaDataAndContents.addAll(contentData);
        return metaDataAndContents;
    }

    public static ResourceInfo scanLink(int primaryKey,DatabaseConnectService databaseConnectService,DatabaseTypeService databaseTypeService){
        ResourceInfo resourceInfo = new ResourceInfo();
        DatabaseConnect databaseConnect = databaseConnectService.selectByPrimaryKey(primaryKey);
        String dbTypeName = databaseTypeService.selectByPrimaryKey(databaseConnect.getDbConTypeId()).getDbTypeName();
        resourceInfo.setDatabaseName(databaseConnect.getDbConDbname());
        resourceInfo.setHostPort(databaseConnect.getDbConPort().toString());
        resourceInfo.setUserName(databaseConnect.getDbConUser());
        resourceInfo.setType(dbTypeName);
        resourceInfo.setHostIp(databaseConnect.getDbConIp());
        resourceInfo.setConnectNmae(databaseConnect.getDbConName());
        return resourceInfo;
    }

    public static boolean isIp(String inputIp){
        if(inputIp.length() < 7 || inputIp.length() > 15 || "".equals(inputIp)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        Matcher mat = pat.matcher(inputIp);
        boolean ipAddress = mat.find();
        return ipAddress;
}

    public static boolean isPort(String inputPort){
        int port;
        try{
            port = Integer.parseInt(inputPort);
            if(port >=1 && port <=65535){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置连接池不被初始化，不执行此段代码，当配置错误的参数的时候，
     * 第二次以及以后连接数据库时的,会从druid连接池找不到连接，会一直等待，报超时的错误。
     * @param resourceInfo
     */
    public static void setDruidDatasourceInited(ResourceInfo resourceInfo){
        //初始化连接池，使每次连接连接池都初始化。
        if("mysql".equals(resourceInfo.getType())){
            String url = "jdbc:mysql:" + "//" + resourceInfo.getHostIp() + ":"
                    + resourceInfo.getHostPort() + "/" + resourceInfo.getDatabaseName();
            DatabasePoolConnection.MyDruidDataSource myDruidDataSource =  DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
                    .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
            myDruidDataSource.setInited(false);
        }
        if("oracle".equals(resourceInfo.getType())){
            String url = "jdbc:oracle:thin:@" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
                    + ":" + resourceInfo.getDatabaseName();
            DatabasePoolConnection.MyDruidDataSource myDruidDataSource = DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
                    .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
            myDruidDataSource.setInited(false);
        }
        if("hive".equals(resourceInfo.getType())){
            String url = "jdbc:hive2://" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
                    + "/" + resourceInfo.getDatabaseName();
            DatabasePoolConnection.MyDruidDataSource myDruidDataSource = DatabasePoolConnection.getInstance(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord())
                    .getMyDruidDataSource(resourceInfo.getType(), url, resourceInfo.getUserName(), resourceInfo.getPassWord());
            myDruidDataSource.setInited(false);
        }
    }

}
