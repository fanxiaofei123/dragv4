package org.com.drag.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * Created by cdyoue on 2016/12/30.
 */
public class Setting {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Setting.class);
    private static  List<String> sparkIds = new ArrayList<>();
    private static  List<String> applyIds = new ArrayList<>();
    private static  List<String> applyV4Ids = new ArrayList<>();
    private static  List<String> modelIds = new ArrayList<>();
    private static  List<String> ordinaryApplyIds = new ArrayList<>();
    /**
     * 添加代码 [private static List<String> dbSparkIds = new ArrayList<>();] By gongmingxing 2017.08.22
     */
    private static List<String> dbSparkIds = new ArrayList<>();
    private static List<String> doubleOutputDataModelIds = new ArrayList<>();
    private static List<String> doubleOutputDataIds = new ArrayList<>();

    private static List<String> sparkV4Ids = new ArrayList<>();
    private static List<String> sparkV4MlibIds = new ArrayList<>();
    private static List<String> pythonRSingleIds = new ArrayList<>();
    private static List<String> pythonAndRIds = new ArrayList<>();
    private static List<String> ordinaryApplyV4Ids = new ArrayList<>();
    private static List<String> sparkTrainedModelIds = new ArrayList<>();
    private static List<String> sparkV4SingleOutputTransformIds = new ArrayList<>();
    private static List<String> containsUsedFieldsIds = new ArrayList<>();

    public  void  init(){
        Properties pro = new Properties();
        InputStream is = null;
        try {
            is = Setting.class.getResourceAsStream("/setting.properties");
            pro.load(is);
            String sparkds = pro.getProperty("sparkds");
            String applyds = pro.getProperty("sparkApplyIds");
            String applyV4ds = pro.getProperty("sparkApplyV4Ids");
            String modelds = pro.getProperty("modelIds");
            String ordinaryApplyId = pro.getProperty("ordinaryApplyIds");
            String doubleOutputDataModelds = pro.getProperty("doubleOutputDataModelIds");
            String doubleOutputDatads = pro.getProperty("doubleOutputDataIds");
            String sparkTrainedModelds = pro.getProperty("sparkTrainedModelIds");
            String sparkV4SingleOutputTransformds = pro.getProperty("sparkV4SingleOutputTransformIds");
            String containsUsedFieldsds = pro.getProperty("containsUsedFieldsIds");
            /**
             * 添加代码[String dbSparkIds = pro.getProperty("dbSparkIds");]
             */
            String dbSparkds = pro.getProperty("dbSparkIds");
            String sparkV4ds = pro.getProperty("sparkV4Ids");
            String sparkV4Mlibds = pro.getProperty("sparkV4MlibIds");
            String pythonRSingleds = pro.getProperty("pythonRSingleIds");
            String pythonAndRds = pro.getProperty("pythonAndRIds");
            String ordinaryApplyV4Id = pro.getProperty("ordinaryApplyV4Ids");

            String[] sparkdsSplit = sparkds.trim().split(",");
            String[] applydsSplit = applyds.trim().split(",");
            String[] applyV4dsSplit = applyV4ds.trim().split(",");
            String[] modelIdsSplit = modelds.trim().split(",");
            String[] ordinaryApplyIdsSplit = ordinaryApplyId.trim().split(",");
            String[] sparkV4MlibIdsSplit = sparkV4Mlibds.trim().split(",");
            String[] sparkV4SingleOutputTransformSplit = sparkV4SingleOutputTransformds.trim().split(",");
            String[] containsUsedFieldsIdsSplit = containsUsedFieldsds.trim().split(",");
            /**
             * 添加代码[String[] dbSparkIdsSplit = dbSparkIds.trim().split(",");] By gongmingxing 2017.08.22
             */
            String[] dbSparkIdsSplit = dbSparkds.trim().split(",");
            String[] doubleOutputDataModelSplit = doubleOutputDataModelds.trim().split(",");
            String[] doubleOutputDataSplit = doubleOutputDatads.trim().split(",");
            String[] sparkV4Split = sparkV4ds.trim().split(",");
            String[] pythonRSingleSplit = pythonRSingleds.trim().split(",");
            String[] pythonAndRSplit = pythonAndRds.trim().split(",");
            String[] ordinaryApplyV4Split = ordinaryApplyV4Id.split(",");
            String[] sparkTrainedModelIdsSplit = sparkTrainedModelds.split(",");

            sparkIds = Arrays.asList(sparkdsSplit);
            applyIds = Arrays.asList(applydsSplit);
            applyV4Ids = Arrays.asList(applyV4dsSplit);
            modelIds = Arrays.asList(modelIdsSplit);
            ordinaryApplyIds = Arrays.asList(ordinaryApplyIdsSplit);
            doubleOutputDataModelIds = Arrays.asList(doubleOutputDataModelSplit);
            doubleOutputDataIds = Arrays.asList(doubleOutputDataSplit);
            sparkV4Ids = Arrays.asList(sparkV4Split);
            containsUsedFieldsIds = Arrays.asList(containsUsedFieldsIdsSplit);
            /**
             * 添加代码 [dbSparkIds = Arrays.asList(dbSparkIdsSplit);] By gongmingxing 2017.08.22
             */
            dbSparkIds = Arrays.asList(dbSparkIdsSplit);
            sparkV4MlibIds = Arrays.asList(sparkV4MlibIdsSplit);
            pythonAndRIds = Arrays.asList(pythonAndRSplit);
            pythonRSingleIds = Arrays.asList(pythonRSingleSplit);
            ordinaryApplyV4Ids = Arrays.asList(ordinaryApplyV4Split);
            sparkTrainedModelIds = Arrays.asList(sparkTrainedModelIdsSplit);
            sparkV4SingleOutputTransformIds = Arrays.asList(sparkV4SingleOutputTransformSplit);
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

    public static List<String> getSparkV4SingleOutputTransformIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(sparkV4SingleOutputTransformIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getContainsUsedFieldsIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(containsUsedFieldsIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getModelIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(modelIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getSparkTrainedModelIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(sparkTrainedModelIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getApplyIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(applyIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
    public static List<String> getApplyV4Ids(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(applyV4Ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }


    public  static List<String> getSparkIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(sparkIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
    public  static List<String> getOrdinaryApplyIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(ordinaryApplyIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * 添加方法[getDbSparkIds()] By gongmingxing 2017.08.22
     * @return
     */
    public static List<String> getDbSparkIds(){
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(dbSparkIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getDoubleOutputDataModelIds() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(doubleOutputDataModelIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getDoubleOutputDataIds() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(doubleOutputDataIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getSparkV4Ids() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(sparkV4Ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getSparkV4MlibIds() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(sparkV4MlibIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getPythonAndRIds() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(pythonAndRIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getPythonAndRSingleIds() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(pythonRSingleIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> getOrdinaryApplyV4Ids() {
        List<String> ids = new ArrayList<>();
        try {
            ids = DeepCopyUtils.deepClone(ordinaryApplyV4Ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }



}
