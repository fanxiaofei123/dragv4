package org.com.drag.dao.impl;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.com.drag.common.util.DatabasePoolConnection;
import org.com.drag.dao.ReadResourceDao;
import org.com.drag.model.ResourceInfo;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gongmingxing on 2017/7/25/ 11:49.
 */

@Service
public class ReadResourceDaoImpl implements ReadResourceDao<ResourceInfo> {

    /**
     * 创建数据库连接对象
     */
//    private DruidPooledConnection conn = null;
//    private Connection conn = null;

    /**
     * 创建PreparedStatement对象
     */
//    private PreparedStatement pstm = null;
//
//    /**
//     * 创建结果集对象
//     */
//    private ResultSet rs = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String testConnection(ResourceInfo resourceInfo){
        DruidPooledConnection conn = null;
        try {
            conn = getConnection(resourceInfo);
            return "连接成功！";
        } catch (Exception e) {
            e.printStackTrace();
            setDruidDataSourceInited(resourceInfo);
            return "连接失败！" + "\n"  + "原因:" + "\n" + e.toString();
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 引入数据库连接池，获得数据库连接
     */
    @Override
    public DruidPooledConnection getConnection(ResourceInfo resourceInfo) throws Exception {
//        DruidPooledConnection connection = null;
          DruidPooledConnection connection;
            if("mysql".equals(resourceInfo.getType())){
                String url = "jdbc:mysql:" + "//" + resourceInfo.getHostIp() + ":"
                        + resourceInfo.getHostPort() + "/" + resourceInfo.getDatabaseName();
                connection = DatabasePoolConnection.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
//                connection = JdbcHelper.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
//                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
                return connection;
            }
            if("oracle".equals(resourceInfo.getType())){
                String url = "jdbc:oracle:thin:@" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
                        + ":" + resourceInfo.getDatabaseName();
                connection = DatabasePoolConnection.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
//                connection = JdbcHelper.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
//                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
                return connection;
            }
            if("hive".equals(resourceInfo.getType())){
                String url = "jdbc:hive2://" + resourceInfo.getHostIp() + ":" + resourceInfo.getHostPort()
                        + "/" + resourceInfo.getDatabaseName();
                connection = DatabasePoolConnection.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
//                connection = JdbcHelper.getInstance(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord())
//                        .getConnection(resourceInfo.getType(),url,resourceInfo.getUserName(),resourceInfo.getPassWord());
                return connection;
            }
        return null;
    }

    /**
     * 获得表的前50条数据。
     */
    @Override
    public List<String> getContentData(ResourceInfo resourceInfo) {
        //获取字段字符串，格式化datetime数据。
        String sql = null;
        DruidPooledConnection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //判断是否包含datetime字段
            boolean isContainsDateTime = false;
            //用一个List来存储字段为DATETIME或datetime的类型。
            List<Integer> datetimeTypeList = new ArrayList<Integer>();
            conn  = getConnection(resourceInfo);
            if("mysql".equals(resourceInfo.getType()) || "hive".equals(resourceInfo.getType())){
                String metaData = getMetaData(resourceInfo);
                String[] columnNmaeAndType = metaData.split("\\|");
                for (int i=0; i<columnNmaeAndType.length;i++){
                    String type = columnNmaeAndType[i].split(",")[1];
                    if("DATETIME".equals(type) || "datetime".equals(type)){
                        isContainsDateTime = true;
                        datetimeTypeList.add(i);
                    }
                }
                sql = "SELECT * FROM " + resourceInfo.getTableName() + " LIMIT 50";
            }else if("oracle".equals(resourceInfo.getType())){
                sql = "SELECT * FROM " + resourceInfo.getTableName() + " WHERE ROWNUM <= 50";
            }
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            int count = rs.getMetaData().getColumnCount();
            List<String> columnlist = new ArrayList<String>();
            //如果存在datetime字段，对该字段的内容进行格式化。
            if(isContainsDateTime){
                while (rs.next()){
                    String columnStr = "";
                    for (int i=1; i<=count;i++){
                        String formatedContent = rs.getString(i);
                        for(int k=0;k<datetimeTypeList.size();k++){
                            if(i == datetimeTypeList.get(k)+1){
                                Date date = format.parse(rs.getString(i));
                                formatedContent = format.format(date);
                            }
                        }
                        if(i == 1){
                            columnStr = columnStr + formatedContent;
                        }else{
                            columnStr = columnStr + "|" + formatedContent;
                        }
                    }
                    columnlist.add(columnStr);
                }
            }else{
                while (rs.next()){
                    String columnStr = "";
                    for (int i=1; i<=count;i++){
                        if(i == 1){
                            columnStr = columnStr + rs.getString(i);
                        }else{
                            columnStr = columnStr + "|" + rs.getString(i);
                        }
                    }
                    columnlist.add(columnStr);
                }
            }
            return columnlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(pstm != null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取表的字段信息。
     */
    @Override
    public String getMetaData(ResourceInfo resourceInfo) {
        DruidPooledConnection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if("mysql".equals(resourceInfo.getType()) || "oracle".equals(resourceInfo.getType())){
            try {
                conn = getConnection(resourceInfo);
                String sql = "select * from " + resourceInfo.getTableName() + " where 1=2";
                pstm = conn.prepareStatement(sql);
                ResultSetMetaData metaData = pstm.executeQuery().getMetaData();
                String metaInfos = "";
                for(int i=1; i<=metaData.getColumnCount();i++){
//                if(i == 1){
//                    metainfos = metainfos + metaData.getColumnName(i) + "(" + metaData.getColumnType(i) + ")";
//                }else{
//                    metainfos = metainfos + "|" + metainfos + metaData.getColumnName(i)
//                            + "(" + metaData.getColumnType(i) + ")";
//                }
                  if(i == 1){
                        metaInfos = metaData.getColumnName(i) + "," + metaData.getColumnTypeName(i);
                  }else{
                        metaInfos = metaInfos + "|" + metaData.getColumnName(i) + "," + metaData.getColumnTypeName(i) ;
                  }
                }
                return metaInfos;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }finally {
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(pstm != null){
                    try {
                        pstm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null ){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if("hive".equals(resourceInfo.getType())){
            try {
                conn = getConnection(resourceInfo);
                String sql = "describe " + resourceInfo.getTableName();
                pstm = conn.prepareStatement(sql);
                rs = pstm.executeQuery();
                String metainfos = "";
                while(rs.next()){
                    metainfos += rs.getString(1) + "," + rs.getString(2) + "|";
                }
                //去掉metainfos的最后一个字符
                metainfos = metainfos.substring(0,metainfos.length()-1);
                return metainfos;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(pstm != null){
                    try {
                        pstm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null ){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取数据库下所有表的名字
     */
    @Override
    public List<String> getTableNames(ResourceInfo resourceInfo) throws Exception{
        DruidPooledConnection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if("mysql".equals(resourceInfo.getType()) || ("hive").equals(resourceInfo.getType())){
            try {
                conn = getConnection(resourceInfo);
//                Connection conn1 = getConnection(resourceInfo);
                //ResultSet rs = conn.getMetaData().getTables(null, "%", "%", new String[]{"TABLE"});
                rs = conn.getMetaData().getTables(null,null,"%",null);
                List<String> tableNames = new ArrayList<String>();
                while (rs.next()){
                    tableNames.add(rs.getString("TABLE_NAME"));
                }
                return tableNames;
            } finally {
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(pstm != null){
                    try {
                        pstm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null ){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if("oracle".equals(resourceInfo.getType())){
            try {
                conn = getConnection(resourceInfo);
                String sql = "SELECT table_name FROM USER_TABLES";
                pstm = conn.prepareStatement(sql);
                rs = pstm.executeQuery();
                List<String> tablenames = new ArrayList<String>();
                while (rs.next()){
                    String tablename = rs.getString(1);
                    tablenames.add(tablename);
                }
                return tablenames;
            } finally {
                if(rs != null){
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(pstm != null){
                    try {
                        pstm.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null ){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 设置连接池不被初始化，不执行此段代码，当配置错误的参数的时候，
     * 第二次以及以后连接数据库时的,会从druid连接池找不到连接，会一直等待，报超时的错误。
     * @param resourceInfo
     */
    public void setDruidDataSourceInited(ResourceInfo resourceInfo){
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
