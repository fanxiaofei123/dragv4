package org.com.drag.model;

import java.util.HashMap;

/**
 * Created by sky on 2017/12/18.
 */
public class UserRunningInfo {
    /**
     * num表示用户提交的任务正在跑的个数
     */
    private Integer num;
    /**
     * workRunMap 表示map中的key是工作流的id,value对应的值是iOozie的jobId，一对一关系
     */
    private HashMap<Integer,String> workRunMap;

    public HashMap<Integer, String> getWorkRunMap() {
        return workRunMap;
    }

    public void setWorkRunMap(HashMap<Integer, String> workRunMap) {
        this.workRunMap = workRunMap;
    }



    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
