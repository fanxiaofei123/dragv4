package org.com.drag.common.util;

import java.util.Properties;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DataSourceUtil {

    public static final DataSource getDataSource(String type, String url, String username,String password) throws Exception {
        DataSource dataSource = null;

        Properties p =new Properties();
        p.put("initialSize", "1");
        p.put("minIdle", "1");
        p.put("maxActive", "20");
        p.put("maxWait", "6000");
        p.put("timeBetweenEvictionRunsMillis", "6000");
        p.put("minEvictableIdleTimeMillis", "3000");
        p.put("testWhileIdle", "true");
        p.put("testOnBorrow", "false");
        p.put("testOnReturn", "false");
        p.put("poolPreparedStatements", "true");
        p.put("maxPoolPreparedStatementPerConnectionSize", "20");
        p.put("filters", "stat");
        switch (type) {
            case "mysql":
                p.put("driverClassName","com.mysql.jdbc.Driver");
                p.put("url", url);
                p.put("username", username);
                p.put("password", password);

                dataSource = DruidDataSourceFactory.createDataSource(p);
                break;
            case "oracle":
                p.put("driverClassName","oracle.jdbc.driver.OracleDriver");
                p.put("url", url);
                p.put("username", username);
                p.put("password", password);

                dataSource = DruidDataSourceFactory.createDataSource(p);
                break;

            case "hive":
                p.put("driverClassName","org.apache.hive.jdbc.HiveDriver");
                p.put("url", url);
                p.put("username", username);
                p.put("password", password);

                dataSource = DruidDataSourceFactory.createDataSource(p);
                break;
        }
        return dataSource;
    }


}
