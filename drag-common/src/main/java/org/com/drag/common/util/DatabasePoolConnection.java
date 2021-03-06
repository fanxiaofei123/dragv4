package org.com.drag.common.util;
import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.TransactionInfo;
import com.alibaba.druid.stat.JdbcDataSourceStat;
import org.apache.poi.poifs.nio.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabasePoolConnection {
    private static Map<String,DatabasePoolConnection> poolMap = null;
    private static Map<String,MyDruidDataSource> dataSourceMap = null;
    private static DatabasePoolConnection databasePoolConnection = null;
    private static MyDruidDataSource myDruidDataSource = null;

    static{
        poolMap = new HashMap<>();
        dataSourceMap = new HashMap<>();
    }

    private DatabasePoolConnection(String type,String url, String userName, String password){
        Map<String,String> p = new HashMap<>();
        p.put("maxActive", "20");
        p.put("initialSize", "1");
//        p.put("maxWait", "60000");
        p.put("maxWait", "6000");
//        p.put("useUnfairLock","true");
        p.put("maxIdle", "20");
        p.put("minIdle", "3");
        p.put("removeAbandoned", "true");
        p.put("removeAbandonedTimeout", "180");
        p.put("connectionProperties", "clientEncoding=UTF-8");
        if("mysql".equals(type)){
            p.put("driverClassName", "com.mysql.jdbc.Driver");
        }
        if("oracle".equals(type)){
            p.put("driverClassName", "oracle.jdbc.driver.OracleDriver");
        }
        if("hive".equals(type)){
            p.put("driverClassName", "org.apache.hive.jdbc.HiveDriver");
        }
        p.put("url", url);
        p.put("username", userName);
        p.put("password", password);
        try {
            myDruidDataSource = (MyDruidDataSource)MyDruidDataSourceFactory.createDataSource(p);
//            myDruidDataSource = (MyDruidDataSource) DruidDataSourceFactory.createDataSource(p);
            dataSourceMap.put(type + "_" + url + "_" + userName + "_" + password, myDruidDataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabasePoolConnection getInstance(String type, String url, String userName, String password){
        String key = null;
        key = type + "_" + url + "_" + userName + "_" + password;
            if(!poolMap.containsKey(key)){
                databasePoolConnection = new DatabasePoolConnection(type, url, userName, password);
                poolMap.put(key,databasePoolConnection);
            }
            return poolMap.get(key);
    }

    public synchronized DruidPooledConnection getConnection(String type, String url, String userName, String password) throws SQLException {
        String key = type + "_" + url + "_" + userName + "_" + password;
        if(dataSourceMap.containsKey(key)){

            DruidPooledConnection connection = dataSourceMap.get(key).getConnection();
            return connection;
        }else{
            return null;
        }
    }

    public synchronized MyDruidDataSource getMyDruidDataSource(String type,String url,String userName,String password){
        return dataSourceMap.get(type + "_" + url + "_" + userName + "_" + password);
    }

    public static class MyDruidDataSource extends DruidDataSource {

       public void setInited(boolean status){
           super.inited = status;
       }
    }
}

