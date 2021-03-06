package org.com.drag.common.util;


import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cdyoue on 2016/9/24.
 * MAIL_HOST=host
 */
public class MailAuthorSetting {
	
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MailAuthorSetting.class);
    
    
    
    public static String HOST;
    public static String AUTH;
    public static String ENABLE;
//    public static String CONTENT;
    public static String USERHOST;
    public static String USERNAME;
    public static String PASSWORD;

    public static String SERVER_ADDRESS;

    private static String proPath;
    
    
    //加载常量
    public static String HADOOP_HDFS_URL;
    public static String HADOOP_HDFS_SUPER_USER;
    public static String HADOOP_HDFS_USER_DIR;
    
    public static String OOZIECLIENT_APP_PATH;
    public static String OOZIECLIENT_LIBPATH;
    /**
     * 添加代码
     */
    public static String OOZIECLIENT_LIBPATH1;
    public static String OOZIECLIENT_LIBPATH_V4;
    public static String OOZIECLIENT_LIBPATH_V4_DEVELOP;

    public static String HADOOP_NAMENODE;
    public static String HADOOP_JOBTRACKER;
    public static String HADOOP_INPUTPATH;
    public static String HADOOP_OUTPUTPATH;
    public static String SPARK_EVENTLOG_DIR;
    public static String WINDOWS_LINUX;
    public static String HADOOP_OOZIECLIENT_PATH;
    public static String HADOOP_HIVE_URL;
    public static String HDFS_PATH_FEIX;

    public static String HDFS_PATH_AMBERI;
    public static String HDFS_USER;
    public static String HDFS_USER_DATA;
    public static String HTTPCLINET_PATH;

    public static String HDFS_PATH_ADRESS;

    public static String CDH_PORT;
    public static String CDH_001;
    public static String CDH_002;
    public static String CDH_003;
    public static String CDH_004;
    public static String CDH_005;
    public static String CDH_JOB;
    public static String CDH_001_IP;
    public static String CDH_002_IP;
    public static String CDH_003_IP;
    public static String CDH_004_IP;
    public static String CDH_005_IP;

    /**
     * 提交任务个数限制
     */
    public static String SUBMIT_TASK_TOTAL_LIMIT;
    public static String USER_TASK_NUM_LIMIT;
    /**
     * 多数据源的切换
     */
    public static String DATABASE_SWITCHING;

    /**
     * python和R的算子路径
     */
    public static String PYTHON_R_DIR_PATH;
    public static String PYTHON_R_INPUT_PATH;
    public static String PYTHON_R_OUTPUT_PATH;

    public static void init() {
        Properties pro = new Properties();
        InputStream is = null;
        try {
        	is = MailAuthorSetting.class.getResourceAsStream(proPath);
            pro.load(is);

            HOST = pro.getProperty("MAIL_HOST");
            AUTH= pro.getProperty("MAIL_AUTH");
            ENABLE = pro.getProperty("MAIL_ENABLE");
            USERHOST = pro.getProperty("MAIL_USERHOST");
            USERNAME = pro.getProperty("MAIL_USERNAME");
            PASSWORD = pro.getProperty("MAIL_PASSWORD");
            SERVER_ADDRESS = pro.getProperty("SERVER_ADDRESS");
            HDFS_PATH_ADRESS = pro.getProperty("HDFS_PATH_ADRESS");
            
            HADOOP_HDFS_URL = pro.getProperty("HADOOP_HDFS_URL");
            HADOOP_HDFS_SUPER_USER = pro.getProperty("HADOOP_HDFS_SUPER_USER");
            HADOOP_HDFS_USER_DIR = pro.getProperty("HADOOP_HDFS_USER_DIR");

            /**
             * 添加代码[OOZIECLIENT_LIBPATH1 =  pro.getProperty("OOZIECLIENT_LIBPATH1");] By gongmingxing 2017.08.22
             */
            OOZIECLIENT_LIBPATH1 =  pro.getProperty("OOZIECLIENT_LIBPATH1");
            OOZIECLIENT_LIBPATH_V4 = pro.getProperty("OOZIECLIENT_LIBPATH_V4");
            OOZIECLIENT_LIBPATH_V4_DEVELOP = pro.getProperty("OOZIECLIENT_LIBPATH_V4_DEVELOP");


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
            
            HDFS_PATH_AMBERI = pro.getProperty("HDFS_PATH_AMBERI");
            HDFS_USER = pro.getProperty("HDFS_USER");
            HDFS_USER_DATA = pro.getProperty("HDFS_USER_DATA");
            HTTPCLINET_PATH = pro.getProperty("HTTPCLINET_PATH");
            
            DATABASE_SWITCHING = pro.getProperty("DATABASE_SWITCHING");
            CDH_PORT=pro.getProperty("CDH_PORT");
            CDH_001=pro.getProperty("CDH_001");
            CDH_002=pro.getProperty("CDH_002");
            CDH_003=pro.getProperty("CDH_003");
            CDH_004=pro.getProperty("CDH_004");
            CDH_005=pro.getProperty("CDH_005");
            CDH_JOB=pro.getProperty("CDH_JOB");
            CDH_001_IP=pro.getProperty("CDH_001_IP");
            CDH_002_IP=pro.getProperty("CDH_002_IP");
            CDH_003_IP=pro.getProperty("CDH_003_IP");
            CDH_004_IP=pro.getProperty("CDH_004_IP");
            CDH_005_IP=pro.getProperty("CDH_005_IP");

            PYTHON_R_DIR_PATH = pro.getProperty("PYTHON_R_DIR_PATH");
            PYTHON_R_INPUT_PATH = pro.getProperty("PYTHON_R_INPUT_PATH");
            PYTHON_R_OUTPUT_PATH = pro.getProperty("PYTHON_R_OUTPUT_PATH");
            SUBMIT_TASK_TOTAL_LIMIT = pro.getProperty("SUBMIT_TASK_TOTAL_LIMIT");
            USER_TASK_NUM_LIMIT = pro.getProperty("USER_TASK_NUM_LIMIT");
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


    public static StringBuffer getServerAddress() {
        return new StringBuffer(SERVER_ADDRESS);
    }

    public static String getProPath() {
        return proPath;
    }

    public static void setProPath(String proPath) {
        MailAuthorSetting.proPath = proPath;
    }



}
