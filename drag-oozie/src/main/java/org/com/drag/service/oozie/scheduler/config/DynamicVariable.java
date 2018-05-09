/*package org.com.drag.service.oozie.scheduler.config;


import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

*//**
 * Created by cdyoue on 2016/9/24.
 * MAIL_HOST=host
 *//*
public class DynamicVariable {
	
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DynamicVariable.class);
    
    private static String proPath;
    
    
    //加载常量
    public static String HADOOP_HDFS_URL;
    public static String HADOOP_HDFS_SUPER_USER;
    public static String HADOOP_HDFS_USER_DIR;
    public static String OOZIECLIENT_APP_PATH;
    public static String OOZIECLIENT_LIBPATH;
    public static String HADOOP_NAMENODE;
    public static String HADOOP_JOBTRACKER;
    public static String HADOOP_INPUTPATH;
    public static String HADOOP_OUTPUTPATH;
    public static String SPARK_EVENTLOG_DIR;
    public static String WINDOWS_LINUX;
    public static String HADOOP_OOZIECLIENT_PATH;
    public static String HADOOP_HIVE_URL;
    public static String HDFS_PATH_FEIX;

    public static void init() {
        Properties pro = new Properties();
        InputStream is = null;
        try {
        	is = DynamicVariable.class.getResourceAsStream(proPath);
            pro.load(is);

            HADOOP_HDFS_URL = pro.getProperty("HADOOP_HDFS_URL");
            HADOOP_HDFS_SUPER_USER = pro.getProperty("HADOOP_HDFS_SUPER_USER");
            HADOOP_HDFS_USER_DIR = pro.getProperty("HADOOP_HDFS_USER_DIR");
            
            OOZIECLIENT_APP_PATH = pro.getProperty("OOZIECLIENT_APP_PATH");
            OOZIECLIENT_LIBPATH = pro.getProperty("OOZIECLIENT_LIBPATH");
            HADOOP_NAMENODE = pro.getProperty("HADOOP_NAMENODE");
            HADOOP_JOBTRACKER = pro.getProperty("HADOOP_JOBTRACKER");
            HADOOP_INPUTPATH = pro.getProperty("HADOOP_INPUTPATH");
            HADOOP_OUTPUTPATH = pro.getProperty("HADOOP_OUTPUTPATH");
            SPARK_EVENTLOG_DIR = pro.getProperty("SPARK_EVENTLOG_DIR");
            WINDOWS_LINUX = pro.getProperty("WINDOWS_LINUX");
            HADOOP_OOZIECLIENT_PATH = pro.getProperty("HADOOP_OOZIECLIENT_PATH");
            HADOOP_HIVE_URL = pro.getProperty("HADOOP_HIVE_URL");
            HDFS_PATH_FEIX = pro.getProperty("HDFS_PATH_FEIX");
            
        } catch (IOException e) {
            LOGGER.error("加载配置文件错误", e);
        }finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("关闭流错误", e);
				}
			}
		}
    }


    public static String getProPath() {
        return proPath;
    }

    public static void setProPath(String proPath) {
        DynamicVariable.proPath = proPath;
    }
    
    
    
}
*/