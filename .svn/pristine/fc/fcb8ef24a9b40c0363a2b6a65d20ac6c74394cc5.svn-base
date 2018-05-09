package org.com.drag.service;

import org.apache.poi.ss.formula.functions.T;
import org.com.drag.model.ResourceInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;


/**
 * Created by Administrator on 2017/7/25/025.
 */
public interface ReadResourceService<T> {

    /**
     * 测试连接
     */
    String testConnection(T t);

    /**
     * 获取表内容
     * @param t
     * @return
     */
    List<String> getContentResultSet(T t);

    /**
     * 获取数据库表名
     * @param t
     * @return
     */
    List<String> getTableNames(T t) throws Exception;

    /**
     * 获取字段结果
     * @param t
     * @return
     */
    String getMetaResultSet(T t);

    /**
     * 获取表内容
     * @param t
     * @return
     */
    List<String> getContentData(T t);

    /**
     * 获取字段数据
     * @param t
     * @return
     */
    String getMetaData(T t);
}
