package org.com.drag.common.util;

/**
 * Created by cdyoue on 2016/11/4.
 */
public class Constants {
    public static final String USER_KEY = "user";
    public static final String WORKSPACES_SUFFIX = "workspaces";
    public static final String DATAS_SUFFIX = "datas";
    public static final String WEBSOCKET_KEY = "ws_user";
    public static final String LOADING_PROCESS = "process";
    public static final String IS_PROCESS = "isProcess";
    public static final String HDFS_TIPS = "tips";
    public static final String CALCULATION_FAIL = "fail";
    public static final String SELECTIVE_KEY = "key";
    public static final String TMP_OUTPUT = "TMP_OUTPUT";
    public static final String TMP_OUTPUT_DATA = "tmpOutputData";
    public static final String TMP_OUTPUT_FUNCTION = "outputPath";
    public static final String TMP_INPUT_FUNCTION = "inputPath";
    public static final String TMP_INPUT_DATA = "inputPathData";
    public static final String TMP_INPUT_BLOCK = "inputPathModel";
    public static final String TMP_INPUT_FUNCTION_FIRST = "inputpath1";
    public static final String TMP_INPUT_FUNCTION_SECOND = "inputpath2";
    public static final String TMP_OUTPUT_FUNCTION_FIRST = "outputpath1";
    public static final String TMP_OUTPUT_FUNCTION_SECOND = "outputpath2";
    public static final String TMP_INPUT = "TMP_INPUT";
    public static final String TMP_ARG = "TMP_ARG";
    public static final String WORK_FLOW = "workflow";
    public static final String RESULTADDRESS = "resultAddress";

    public static final String TMP_OUTPUT_MODEL = "tmpOutputModel";
    public static final String TMP_OUTPUT_IMAGE = "tmpImgPath";

    public static final String OUTPUT_DATAS="output";
    public static final String INPUT_DATAS="input";

    public static final Short DB_CON_TYPE_MYSQL_ID = 1;
    public static final Short DB_CON_TYPE_ORACLE_ID = 2;
    public static final Short DB_CON_TYPE_HIVE_ID = 3;
    
    public static final byte SCHEDULER_JOB_MODE_ONCE=0;
    public static final byte SCHEDULER_JOB_MODE_MULTI=1;
    
    public static final byte SCHEDULER_JOB_INTERVAL_UNIT_MINUTE=0;
    public static final byte SCHEDULER_JOB_INTERVAL_UNIT_HOUR=1; 
    public static final byte SCHEDULER_JOB_INTERVAL_UNIT_DAY=2;
    
    public static final String SCHEDULER_JOB_QUARTZ_GROUP_PREFIX="scheduler_group_";
    public static final String SCHEDULER_JOB_QUARTZ_NAME_PREFIX="scheduler_job_";

    //调度任务运行的状态0表失败，1表成功，2表运行，3表示待运行
    public static final Byte SCHEDULER_JOB_FAIL=0;
    public static final Byte SCHEDULER_JOB_SUCCESSFUL=1;
    public static final Byte SCHEDULER_JOB_RUNNING=2;
    public static final Byte SCHEDULER_JOB_NOT=3;
    //调度任务是否被编辑，1表示编辑，0表示还没被编辑
    public static final Byte SCHEDULER_JOB_IS_EDIT=1;
    public static final Byte SCHEDULER_JOB_NOT_EDIT=0;


    //OOZIE配置文件生成版本
    public static final String OOZIE_GENXML_V3 = "V3";
    public static final String OOZIE_GENXML_V4 = "V4";
    public static final String OOZIE_GEN_VERSION = "oozieGenVersion";
    public static final String PYTHON_OR_R_SHELL_NAME = "pythonR.sh";

    public static StringBuffer getSpacePrefix() {
        return new StringBuffer(MailAuthorSetting.HADOOP_HDFS_USER_DIR);
    }
    public static StringBuffer getHdfsUrl(){
        return  new StringBuffer(MailAuthorSetting.HADOOP_HDFS_URL);
    }
}
