package org.com.drag.dao;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.com.drag.common.util.DatabasePoolConnection;
import org.com.drag.model.ResourceInfo;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by gongmingxing on 2017/7/25/ 10:37.
 */


public interface ReadResourceDao<T> {

    String testConnection(T t);
    //Get the connect of the database;
    DruidPooledConnection getConnection(T t) throws Exception;

    //Get the ResultSet of the table
    List<String> getContentData(T t);

    //Get the scheme of the table
    String getMetaData(T t);

    //Get all table's name in the database;
    List<String> getTableNames(T t) throws Exception;
}
