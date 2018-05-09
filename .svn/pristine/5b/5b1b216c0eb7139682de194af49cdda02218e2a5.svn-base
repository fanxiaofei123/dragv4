package org.com.drag.service.oozie.scheduler.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.com.drag.common.util.MailAuthorSetting;

/**
 * Created by zhh on 2016/10/26.
 */
public class HiveConnection {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = MailAuthorSetting.HADOOP_HIVE_URL;
    private static String dbName = "default";
    private static String user = "bigdata";


    public HiveConnection() {
    }

    public HiveConnection(String driverName,String url,String dbName,String user) {
        this.driverName = driverName;
        this.url = url;
        this.dbName = dbName;
        this.user = user;
    }

    public Connection getHiveConn(){
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url+dbName, user, "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
