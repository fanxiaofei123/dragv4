package org.com.drag.service.oozie.scheduler.hive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhh on 2016/10/26.
 */
public class HiveHandle {

    private static HiveConnection hiveClient = new HiveConnection();

    /**
     * 创建数据库
     */
    public boolean createDatabase(){
        Connection hiveConn = hiveClient.getHiveConn();
        Statement stat = null;
        boolean res = false;
        return false;
    }

    /**
     *
     * 创建内部表
     * @param tableName 表明
     * @param fieldsDelimiter 表字段分隔符
     * @return 是否创建成功
     */
    public boolean createInnerTable(String tableName,String fieldsDelimiter){
        Connection hiveConn = hiveClient.getHiveConn();
        Statement stat = null;
        boolean res = false;
        try {
            String sql = "create external table " + tableName + "(id string, name string) row format delimited fields terminated by \"" + fieldsDelimiter+"\"";//
            stat = hiveConn.createStatement();
            stat.execute(sql);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.close(hiveConn,stat);
        }
        return res;
    }

    /**
     * 创建外部表
     * @param tableName 表名称
     * @param fieldsDelimiter 表字段分隔符
     * @return 是否创建成功
     */
    public boolean createExternalTable(String tableName,String fieldsDelimiter){
        Connection hiveConn = hiveClient.getHiveConn();
        Statement stat = null;
        boolean res = false;
        try {
            String sql = "create external table " + tableName + "(id string, name string) row format delimited fields terminated by \"" + fieldsDelimiter+"\"";//
            stat = hiveConn.createStatement();
            stat.execute(sql);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.close(hiveConn,stat);
        }
        return res;
    }

    /**
     * 加载数据到表中
     * @param tableName 表名称
     * @param filePath 待加载数据所在目录
     * @return 是否加载成功
     */
    public boolean loadDataIntoTable(String tableName,String filePath){
        Connection hiveConn = hiveClient.getHiveConn();
        Statement stat = null;
        boolean res = false;
        try {
            String sql = "load data inpath \"" + filePath + "\" into table " + tableName;
            stat = hiveConn.createStatement();
            stat.execute(sql);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           this.close(hiveConn,stat);
        }
        return res;
    }

    /**
     * 查询表中数据
     * @param tableName 表名称
     */
    public ResultSet queryTable(String tableName) {
        Connection hiveConn = hiveClient.getHiveConn();
        Statement stat = null;
        ResultSet resultSet = null;
        try {
            stat = hiveConn.createStatement();
            resultSet = stat.executeQuery("select * from " +tableName);
            while (resultSet.next()){
                System.out.print(resultSet.getString(1));
                System.out.println("\t"+resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.close(hiveConn,stat);
        }
        return resultSet;
    }


    private void close(Connection conn, Statement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
