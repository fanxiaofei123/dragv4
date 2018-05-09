package org.com.drag.web.common;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;
import oracle.jdbc.driver.Const;
import org.apache.commons.collections.map.HashedMap;
import org.com.drag.common.util.*;
import org.com.drag.model.*;
import org.com.drag.service.DatabaseConnectService;
import org.com.drag.service.DatabaseTypeService;
import org.mortbay.log.Log;
import org.mortbay.log.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.collection.parallel.mutable.ParArray;

import static org.com.drag.common.util.Constants.TMP_OUTPUT_FUNCTION;
import static org.com.drag.common.util.MailAuthorSetting.HADOOP_HDFS_URL;

/**
 * Created by cdyoue on 2016/12/5.
 */
public class ModelUtils {
    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 添加代码[DatabaseConnectService databaseConnectService;DatabaseTypeService databaseTypeService;]
     */
    /**
     * 传入的参数添加了userId。 By gongmingxing 2017.08.22。
     */
    public ModelXmlInfo getSortingFormatModels(List<Model> models, List<Connect> rules, String flowId, String username, int userId, DatabaseConnectService databaseConnectService, DatabaseTypeService databaseTypeService) {

        ConnectsInfo connectsInfo = formatRules(models,rules);
        Map<String, List<Connect>> ruleChain = connectsInfo.getRuleChain();

//        Map<String, List<Connect>> formatedRuleChain = formatRuleChain(ruleChain);

//        List<WorkflowNode> firstNodes = getFirsts(formatedRuleChain, models);
//        for (WorkflowNode node : firstNodes) {
//            iter(node, null, formatedRuleChain.get(node.getId()));
//        }
//
//        WorkflowNode startNode = genJoinForkAndHeadTail(firstNodes);
//        String id = startNode.getId();
        ModelXmlInfo modelXmlInfo = new ModelXmlInfo();
        /**
         * 多输入多输出,对于一个joinPoints比如数据集合并算子，会有两个输入路径，同时设计了两个输出路径，让两条线并行的运行，
         * 也就有两个输出路径。
         */
        List<String> joinPoint = connectsInfo.getJoinPoint();
        int i = 1;
        Forx forx = new Forx();
        List<String> targetId = forx.getTargetId();
        Map<String, Object> formatInfo = null;
        for (String key : ruleChain.keySet()) {
            if (ruleChain.size() == 1) {
                List<Connect> rulesSoted = ruleChain.get(key);
                List<Model> sortedFormatedModels = sortingModel(models, rulesSoted);
                formatInfo = formatModelFirst(sortedFormatedModels, flowId, username, null, userId, databaseConnectService, databaseTypeService);
                sortedFormatedModels = (List) formatInfo.get("models");
                String resultAddress = (String) formatInfo.get(Constants.RESULTADDRESS);
                modelXmlInfo.setResultAddress(resultAddress);
                modelXmlInfo.getModels().addAll(sortedFormatedModels);
            } else {
                targetId.add(key);
                modelXmlInfo.setForx(forx);
                List<Connect> rulesSoted = ruleChain.get(key);
                List sortedFormatedModels = new ArrayList();
                List<Join> joins = new ArrayList<>();

                if (i == 1) {
                    List<Model> copeModels = new ArrayList();
                    try {
                        copeModels = DeepCopyUtils.deepClone(models);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sortedFormatedModels = sortingModel(copeModels, rulesSoted);
                    formatInfo = formatModelFirst(sortedFormatedModels, flowId, username, joinPoint, userId, databaseConnectService, databaseTypeService);
                    sortedFormatedModels = (List) formatInfo.get("models");
                    String resultAddress = (String) formatInfo.get(Constants.RESULTADDRESS);
                    modelXmlInfo.setResultAddress(resultAddress);
                } else {
                    List<Model> copeModels = new ArrayList();
                    try {
                        copeModels = DeepCopyUtils.deepClone(models);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sortedFormatedModels = sortingModel(copeModels, rulesSoted);

                    if (formatInfo != null) {
                        Join join = (Join) formatInfo.get("join");
                        joins.add(join);
                        sortedFormatedModels = formatModel(sortedFormatedModels, flowId, username, join, userId, databaseConnectService, databaseTypeService);
                    }
                }
                modelXmlInfo.getModels().addAll(sortedFormatedModels);
                modelXmlInfo.setJoins(joins);
                i++;
            }

        }
        return modelXmlInfo;
    }

    //把多余的线去除掉。
    private Map<String, List<Connect>> formatRuleChain(Map<String, List<Connect>> ruleChain) {
        Map<String, List<Connect>> formatedRuleChain = new HashMap<String, List<Connect>>();
        List<String> keys = new ArrayList<String>();
        for (String key : ruleChain.keySet()) {
            keys.add(key);
        }
        List<Connect> otherConnects = new ArrayList<Connect>();
        List<Connect> firstConnects = ruleChain.get(keys.get(0));
        for (Connect connect : firstConnects) {
            otherConnects.add(connect);
        }
        formatedRuleChain.put(keys.get(0), firstConnects);
        for (int i = 1; i < keys.size(); i++) {
            List<Connect> thisConnects = ruleChain.get(keys.get(i));
            List<Connect> formatedThisConnects = new ArrayList<>();
            formatedThisConnects.addAll(thisConnects);
            for (Connect thisConnect : thisConnects) {
                for (Connect otherConnect : otherConnects) {
                    if (thisConnect.getSourceId().equals(otherConnect.getSourceId()) && thisConnect.getTargetId().equals(otherConnect.getTargetId())) {
                        formatedThisConnects.remove(thisConnect);
                    }
                }
            }
            formatedRuleChain.put(keys.get(i), formatedThisConnects);
            otherConnects.addAll(formatedThisConnects);
        }
        return formatedRuleChain;
    }


    /**
     * 格式化 连线
     *
     * @param rules
     * @return
     */

    public ConnectsInfo formatRules(List<Model> models, List<Connect> rules) {
        Map<String, List<Connect>> formatRules = new LinkedHashMap<>();
        List<String> joinPoints = new ArrayList<>();
        List<String> targetIds = new ArrayList<>();

        //TODO 添加代码 [|| rule.getSourceModel().replaceAll("null","").trim().equals("读取数据库")] BY gongmingxing 2017.08.21
        for (Connect rule : rules) {
            int modelId = 0;

            for(Model model : models){
                if(model.getBlockId().equals(rule.getSourceId())){
                    modelId = model.getId();
                }
            }

            if (modelId == 55 ||rule.getSourceModel().replaceAll("null", "").trim().equals("读取CSV") || rule.getSourceModel().replaceAll("null", "").trim().equals("读取数据库") || rule.getSourceModel().replaceAll("null", "").trim().equals("读取CSV(V4)")) {
                //存放连接信息（新建一个连接list，存放格式化后的连接）
                List<Connect> connects = new ArrayList<>();
                //原本连接信息
                List<Connect> ruleConnects = null;
                try {
                    ruleConnects = DeepCopyUtils.deepClone(rules);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                connects.add(rule);
                /**
                 * 判断算子连线是否已经存在，type=1时，代表没有算子连线，type=2时，代表已经存在连线。
                 */
                Integer type = formatRules.size() == 0 ? 1 : 2;
                findRule(ruleConnects, connects, rule.getTargetId(), targetIds, joinPoints, type);
                /**
                 * 将一条连线的信息存储在formateRules中。
                 */
                formatRules.put(rule.getSourceId(), connects);
            }
        }
        return new ConnectsInfo(joinPoints, formatRules);
    }


    /**
     * @param rules      规则
     * @param connects   连接信息
     * @param targetId   搜索目标id
     * @param targetIds  目标id池
     * @param joinPoints 连接点
     */
    public void findRule(List<Connect> rules, List<Connect> connects, String targetId, List<String> targetIds, List<String> joinPoints, Integer type) {
        //从所有的connect对象中找到符合条件的targetID
        for (Connect rule : rules) {
            String sourceId = rule.getSourceId();
            if (targetId.equals(rule.getSourceId())) {
                //TODO 新添加了[|| rule.getTargetModel().replaceAll("null","").trim().equals("写入数据库")] BY gongmingxing 2017.08.21
                /**
                 * 如果找到了写入csv的目标模型，这条算子运行链就运行结束，停止遍历.
                 */
                if (rule.getTargetModel().replaceAll("null", "").trim().equals("写入CSV") || rule.getTargetModel().replaceAll("null", "").trim().equals("写入数据库")) {
                    connects.add(rule);
                    targetIds.add(targetId);
                    if (type == 2) {
                        joinPoints.add(rule.getSourceId());
                    }
                    return;
                }
                if (targetIds.contains(rule.getSourceId())) {
                    /**
                     * 第一条算子连线是最长的。
                     */
                    joinPoints.add(rule.getSourceId());
                    return;
                } else {
                    connects.add(rule);
                }

                findRule(rules, connects, rule.getTargetId(), targetIds, joinPoints, type);
            }
        }
        targetIds.add(targetId);
    }

    /**
     * 格式化 model
     *
     * @param models
     * @param flowId
     * @param username
     * @param join
     * @return
     */
    private List<Model> formatModel(List<Model> models, String flowId, String username, Join join, int userId, DatabaseConnectService databaseConnectService, DatabaseTypeService databaseTypeService) {
        List<Model> formatModel = new ArrayList<>();
        ConcurrentHashMap<String, Object> cuurentMap = new ConcurrentHashMap<String, Object>();

        /**
         * 添加代码，By gongmingxing 2017.08.21
         * 先put一个delimiter为","的默认值，保证算子计算连线中没有读取csv算子时也能有分隔符。
         */
        ModelAttribute delimiterModelAttribute = new ModelAttribute();
        delimiterModelAttribute.setMattribute("delimiter");
        delimiterModelAttribute.setMvalue(",");
        delimiterModelAttribute.setType(1);
        cuurentMap.put(Constants.TMP_ARG, delimiterModelAttribute);
        /**
         * 添加代码，By gongminxing 2017.08.21
         */

        String joinPoint = join.getJoinPoint();
        Integer rule = 3;
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            List<ModelAttribute> data = model.getData();
            if (joinPoint != null && model.getBlockId().equals(joinPoint)) {
                break;
            }
            //如果是输入
            if (model.getId() == 1) {
                List<ModelAttribute> modelAttributes = model.getData();
                //将demilter放入Map

                for (ModelAttribute modelAttribute : modelAttributes) {
                    String trim = modelAttribute.getMattribute().trim();
                    if (trim.equalsIgnoreCase("delimiter")) {
                        cuurentMap.put(Constants.TMP_ARG, modelAttribute);
                        break;
                    }
                }

            }

            //格式化modelId=53和modelID=54的代码。
            if (model.getId() == 53 || model.getId() == 54) {
                String dbConnName = "";
                List<ModelAttribute> modelAttributes = model.getData();
                //遍历ModelAttribute，把有连接名的modelAttribute取出来
                for (ModelAttribute modelAttribute : modelAttributes) {
                    String trim = modelAttribute.getMattribute().trim();
                    if (trim.equalsIgnoreCase("dbLinkName")) {
//                        String [] dbConnNames = modelAttribute.getMvalue().split("\\|");
                        dbConnName = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(dbConnName);
                    }

                    if (trim.equalsIgnoreCase("sqlStatment")) {
                        String sql = modelAttribute.getMvalue().split("\\|")[0];
                        modelAttribute.setMvalue(sql);
                    }

                    if (trim.equalsIgnoreCase("tableName")) {
                        String tableName = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(tableName);
                    }

                    if (trim.equalsIgnoreCase("writeMode")) {
                        String writeMode = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(writeMode);
                    }
                }
                //把数据库中的连接信息查询出来，根据userId和连接名来查询。
                DatabaseConnect databaseConnect = new DatabaseConnect();
                databaseConnect.setDbConUserId(userId);
                databaseConnect.setDbConName(dbConnName);
                DatabaseConnect connect = databaseConnectService.findByUserIdAndConnName(databaseConnect);
                //添加ip属性
                ModelAttribute modelAttributeIp = new ModelAttribute();
                modelAttributeIp.setMattribute("hostIp");
                modelAttributeIp.setMvalue(connect.getDbConIp());
                model.getData().add(modelAttributeIp);
                //添加port
                ModelAttribute modelAttributePort = new ModelAttribute();
                modelAttributePort.setMattribute("hostPort");
                modelAttributePort.setMvalue(connect.getDbConPort().toString());
                model.getData().add(modelAttributePort);
//                Log.info("============" + model.getData().size());
                //添加访问的数据库
                ModelAttribute modelAttributeDatabase = new ModelAttribute();
                modelAttributeDatabase.setMattribute("databaseName");
                modelAttributeDatabase.setMvalue(connect.getDbConDbname());
                model.getData().add(modelAttributeDatabase);
                //添加数据库类型
                ModelAttribute modelAttributeDbType = new ModelAttribute();
                modelAttributeDbType.setMattribute("databaseType");
                String databasetypeName = databaseTypeService.selectByPrimaryKey(connect.getDbConTypeId()).getDbTypeName();
                modelAttributeDbType.setMvalue(databasetypeName);
                model.getData().add(modelAttributeDbType);
                //添加用户名
                ModelAttribute modelAttributeUser = new ModelAttribute();
                modelAttributeUser.setMattribute("userName");
                modelAttributeUser.setMvalue(connect.getDbConUser());
                model.getData().add(modelAttributeUser);
                //添加密码
                ModelAttribute modelAttributePassword = new ModelAttribute();
                modelAttributePassword.setMattribute("password");
                modelAttributePassword.setMvalue(connect.getDbConPassword());
                model.getData().add(modelAttributePassword);
            }
            //添加临时输入目录
            /**
             * 添加代码[&& model.getId() != 53] By gongmingxing 2017.08.21
             */
            if (model.getId() != 1 && model.getId() != 53) {
                final String tmpInput = (String) cuurentMap.get(Constants.TMP_INPUT);
                ModelAttribute modelAttribute = new ModelAttribute() {{
                    setConfigName(Constants.TMP_INPUT_FUNCTION);
                    setMvalue(tmpInput);
                }};

                //添加中间输入目录
                if (model.getId() == 3 || model.getId() == 4) {
                    data.add(1, modelAttribute);
                    String outputFileName = null;
                    String outputFilePath = null;
                    Iterator<ModelAttribute> attrIterator = data.iterator();
                    while (attrIterator.hasNext()) {
                        ModelAttribute outputAttr = attrIterator.next();
                        if ("inputName".equalsIgnoreCase(outputAttr.getMattribute())) {
                            outputFileName = outputAttr.getMvalue() + "-" + System.currentTimeMillis();
                            attrIterator.remove();
                        } else if ("outputPath".equalsIgnoreCase(outputAttr.getMattribute())) {
                            outputFilePath = new StringBuffer(outputAttr.getMvalue()).append("/").append(outputFileName).toString();
                            outputAttr.setMvalue(outputFilePath);
                        }

                    }

                }
                /**
                 * 添加代码else if模块，By gongmingxing 2017.08.21
                 */
                else if (model.getId() == 54) {
                    data.add(1, modelAttribute);
                } else {
                    data.add(1, modelAttribute);
                }

            }


            if (joinPoint != null && model.getTargetBlockId().equals(joinPoint)) {
                final String outputPath = join.getOutputPath();
                ModelAttribute modelAttribute = new ModelAttribute() {{
                    setConfigName(TMP_OUTPUT_FUNCTION);
                    setMvalue(outputPath);
                }};
                model.getData().add(2, modelAttribute);
                model.setTargetBlockId(join.getName());
                /**
                 * 添加代码[]
                 */
            } else if (model.getId() != 3 || model.getId() != 4 && model.getId() != 54) {
                //添加中间的输出目录
                ModelAttribute modelAttributeOutput = new ModelAttribute();
                modelAttributeOutput.setTmpConfigType(1);
                modelAttributeOutput.setConfigName(new StringBuffer(TMP_OUTPUT_FUNCTION).toString());
                //临时定义
                modelAttributeOutput.setTmpConfigType(2);
                String tmpDir = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                        .append("/")
                        .append(flowId)
                        .append("/")
                        .append(model.getBlockId()).append("_")
                        .append(Constants.TMP_OUTPUT.toLowerCase())
                        .append("_")
                        .append(UUID.randomUUID().toString())
                        .toString();
                String fullPath = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                        .append(tmpDir)
                        .toString();
                modelAttributeOutput.setConfigVal(fullPath);


                cuurentMap.put(Constants.TMP_INPUT, fullPath);
                model.getData().add(2, modelAttributeOutput);

            }

            //delimiter
            ModelAttribute modelAttribute = (ModelAttribute) cuurentMap.get(Constants.TMP_ARG);
            if (model.getId() != 1) {
                int index = 3;
                model.getData().add(index, modelAttribute);
            }

            formatModel.add(model);

        }

        return formatModel;
    }


    /**
     * @param models
     * @param flowId
     * @param username
     * @param joinPoint
     * @return
     */
    //传入的参数添加userId。
    private Map<String, Object> formatModelFirst(List<Model> models, String flowId, String username, List<String> joinPoint, int userId, DatabaseConnectService databaseConnectService, DatabaseTypeService databaseTypeService) {
        List<Model> formatModel = new ArrayList<>();
        ConcurrentHashMap<String, Object> cuurentMap = new ConcurrentHashMap<String, Object>();
        /**
         * 添加代码，By gongmingxing 2017.08.21
         * 先put一个delimiter为","的默认值，保证算子计算连线中没有读取csv算子时也能有分隔符。
         */
        ModelAttribute delimiterModelAttribute = new ModelAttribute();
        delimiterModelAttribute.setMattribute("delimiter");
        delimiterModelAttribute.setMvalue(",");
        delimiterModelAttribute.setType(1);
        cuurentMap.put(Constants.TMP_ARG, delimiterModelAttribute);
        /**
         * 添加代码，By gongminxing 2017.08.21
         */
        Map<String, Object> resultMap = new HashedMap();
        List<String> applyIds = Setting.getApplyIds();
        List<String> modelIds = Setting.getModelIds();

        List<String> ordinaryApplyIds = Setting.getOrdinaryApplyIds();

        Join join = new Join();
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            List<ModelAttribute> data = model.getData();

            //TODO 需要修改的地方
            //如果是多输入 添加join
            if (joinPoint != null && model.getTargetBlockId().equals(joinPoint.get(0))) {
                join.setJoinPoint(joinPoint.get(0));
                join.setName("joing");
                join.setTargetTo(model.getTargetBlockId());
                model.setTargetBlockId("joing");

            }

            //TODO 需要修改的地方。
            //如果是输入 将必须属性放入 map
            if (model.getId() == 1) {
                List<ModelAttribute> modelAttributes = model.getData();
                //将demilter放入Map


                for (ModelAttribute modelAttribute : modelAttributes) {
                    String trim = modelAttribute.getMattribute().trim();
                    if (trim.equalsIgnoreCase("delimiter")) {
                        cuurentMap.put(Constants.TMP_ARG, modelAttribute);
                        break;
                    }
                }

            }

            /**
             * 添加代码块 By gongmingxing 2017.08.22
             */
            //start By gongmingxing 2017.08.22
            if (model.getId() == 53 || model.getId() == 54) {
                String dbConnName = "";
                List<ModelAttribute> modelAttributes = model.getData();
                //遍历ModelAttribute，把有连接名的modelAttribute取出来
                for (ModelAttribute modelAttribute : modelAttributes) {
                    String trim = modelAttribute.getMattribute().trim();
                    if (trim.equalsIgnoreCase("dbLinkName")) {
//                        String [] dbConnNames = modelAttribute.getMvalue().split("\\|");
                        dbConnName = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(dbConnName);
                    }

                    if (trim.equalsIgnoreCase("sqlStatment")) {
                        String sql = modelAttribute.getMvalue().split("\\|")[0];
                        modelAttribute.setMvalue(sql);
                    }

                    if (trim.equalsIgnoreCase("tableName")) {
                        String tableName = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(tableName);
                    }
                    if (trim.equalsIgnoreCase("writeMode")) {
                        String writeMode = modelAttribute.getMvalue().split("\\|")[1];
                        modelAttribute.setMvalue(writeMode);
                    }
                }
                //把数据库中的连接信息查询出来，根据userId和连接名来查询。
                DatabaseConnect databaseConnect = new DatabaseConnect();
                databaseConnect.setDbConUserId(userId);
                databaseConnect.setDbConName(dbConnName);
                DatabaseConnect connect = databaseConnectService.findByUserIdAndConnName(databaseConnect);
                //添加ip属性
                ModelAttribute modelAttributeIp = new ModelAttribute();
                modelAttributeIp.setMattribute("hostIp");
                modelAttributeIp.setMvalue(connect.getDbConIp());
                model.getData().add(modelAttributeIp);
                //添加port
                ModelAttribute modelAttributePort = new ModelAttribute();
                modelAttributePort.setMattribute("hostPort");
                modelAttributePort.setMvalue(connect.getDbConPort().toString());
                model.getData().add(modelAttributePort);
//                Log.info("============" + model.getData().size());
                //添加访问的数据库
                ModelAttribute modelAttributeDatabase = new ModelAttribute();
                modelAttributeDatabase.setMattribute("databaseName");
                modelAttributeDatabase.setMvalue(connect.getDbConDbname());
                model.getData().add(modelAttributeDatabase);
                //添加数据库类型
                ModelAttribute modelAttributeDbType = new ModelAttribute();
                modelAttributeDbType.setMattribute("databaseType");
                String databasetypeName = databaseTypeService.selectByPrimaryKey(connect.getDbConTypeId()).getDbTypeName();
                modelAttributeDbType.setMvalue(databasetypeName);
                model.getData().add(modelAttributeDbType);
                //添加用户名
                ModelAttribute modelAttributeUser = new ModelAttribute();
                modelAttributeUser.setMattribute("userName");
                modelAttributeUser.setMvalue(connect.getDbConUser());
                model.getData().add(modelAttributeUser);
                //添加密码
                ModelAttribute modelAttributePassword = new ModelAttribute();
                modelAttributePassword.setMattribute("password");
                modelAttributePassword.setMvalue(connect.getDbConPassword());
                model.getData().add(modelAttributePassword);
                //添加function属性
            }
            //end By gongmingxing 2017.08.22

            String modelIdStr = model.getId() + "";

            //为没有输入模块的 添加临时输入
            /**
             * 添加代码 [&& model.getId() != 53] By gongmingxing 2017.08.21
             */
            if (model.getId() != 1 && !applyIds.contains(modelIdStr) && !ordinaryApplyIds.contains(modelIdStr) && model.getId() != 53) {
                final String tmpInput = (String) cuurentMap.get(Constants.TMP_INPUT);

                ModelAttribute modelAttribute = new ModelAttribute();

                modelAttribute.setConfigName(Constants.TMP_INPUT_FUNCTION);


                modelAttribute.setMvalue(tmpInput);


                data.add(1, modelAttribute);

                //TODO 需要修改的地方
                //如果是输出 输出目录 拼接
                if (model.getId() == 3 || model.getId() == 4) {
                    String outputFileName = null;
                    String outputFilePath = null;
                    Iterator<ModelAttribute> attrIterator = data.iterator();
                    while (attrIterator.hasNext()) {
                        ModelAttribute outputAttr = attrIterator.next();
                        if ("inputName".equalsIgnoreCase(outputAttr.getMattribute())) {
                            outputFileName = outputAttr.getMvalue() + "-" + System.currentTimeMillis();
                            attrIterator.remove();
                        } else if ("outputPath".equalsIgnoreCase(outputAttr.getMattribute())) {
                            outputFilePath = new StringBuffer(outputAttr.getMvalue()).append("/").append(outputFileName).toString();
                            //数据结果保存地址
                            resultMap.put(Constants.RESULTADDRESS, outputFilePath);
                            outputAttr.setMvalue(outputFilePath);
                        }
                    }
                }
            }

            //Apply Model
            if (applyIds.contains(modelIdStr)) {
                //添加块输入目录
                ModelAttribute modelAttributeBlock = new ModelAttribute() {{
                    setConfigName(Constants.TMP_INPUT_BLOCK);
                }};
                //添加data输入目录
                ModelAttribute modelAttributeData = new ModelAttribute() {{
                    setConfigName(Constants.TMP_INPUT_DATA);
                }};
                Model modelPrex = models.get(i - 1);
                String tmpInput = (String) cuurentMap.get(Constants.TMP_INPUT);
                //临时定义
                String tmpDir = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                        .append("/")
                        .append(flowId)
                        .append("/")
                        .append(model.getBlockId()).append("_")
                        .append(Constants.TMP_INPUT_BLOCK.toLowerCase())
                        .append("_")
                        .append(UUID.randomUUID().toString())
                        .toString();
                String fullPathModel = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                        .append(tmpDir)
                        .toString();

                modelAttributeData.setConfigVal(tmpInput);
                modelAttributeBlock.setConfigVal(fullPathModel);
                model.getData().add(modelAttributeBlock);
                model.getData().add(modelAttributeData);
                ModelAttribute modelAttributeTmp = new ModelAttribute() {{
                    setConfigName(TMP_OUTPUT_FUNCTION);
                }};

                System.err.println(modelPrex.getId() + "");

                if (modelIds.contains(modelPrex.getId() + "")) {
                    modelAttributeTmp.setConfigVal(modelAttributeBlock.getMvalue());
                    join.setOutputPath(modelAttributeData.getMvalue());
                } else {
                    modelAttributeTmp.setConfigVal(modelAttributeData.getMvalue());
                    join.setOutputPath(modelAttributeBlock.getMvalue());
                }
                modelPrex.getData().add(modelAttributeTmp);
            }


//            ordinaryApplyIds
            if (ordinaryApplyIds.contains(modelIdStr)) {
                //添加输出目录1
                ModelAttribute modelAttributeInputFirst = new ModelAttribute() {{
                    setConfigName(Constants.TMP_INPUT_FUNCTION_FIRST);
                }};

                modelAttributeInputFirst.setConfigVal((String) cuurentMap.get(Constants.TMP_INPUT));
                //添加输出目录2
                ModelAttribute modelAttributeInputSecond = new ModelAttribute() {{
                    setConfigName(Constants.TMP_INPUT_FUNCTION_SECOND);

                }};

                //临时定义
                String tmpDirSecond = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                        .append("/")
                        .append(flowId)
                        .append("/")
                        .append(model.getBlockId()).append("_")
                        .append(Constants.TMP_INPUT_BLOCK.toLowerCase())
                        .append("_")
                        .append(UUID.randomUUID().toString())
                        .toString();
                String fullPathSecond = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                        .append(tmpDirSecond)
                        .toString();

                modelAttributeInputSecond.setConfigVal(fullPathSecond);
                join.setOutputPath(fullPathSecond);

                model.getData().add(modelAttributeInputFirst);
                model.getData().add(modelAttributeInputSecond);
            }


            //TODO 需要修改的地方
            //添加临时的输出
            /**
             * 添加代码[&& model.getId() != 54] By gongmingxing 2017.08.21
             */
            if (model.getId() != 3 && model.getId() != 4 && model.getId() != 54) {

                //添加中间的输出目录
                ModelAttribute modelAttribute = new ModelAttribute();
                modelAttribute.setTmpConfigType(1);
                modelAttribute.setConfigName(new StringBuffer(TMP_OUTPUT_FUNCTION).toString());
                //临时定义
                String tmpDir = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                        .append("/")
                        .append(flowId)
                        .append("/")
                        .append(model.getBlockId()).append("_")
                        .append(Constants.TMP_OUTPUT.toLowerCase())
                        .append("_")
                        .append(UUID.randomUUID().toString())
                        .toString();

                String fullPath = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                        .append(tmpDir)
                        .toString();

                modelAttribute.setConfigVal(fullPath);
                Model nextModel = models.get(i + 1);
                if (!(applyIds.contains(nextModel.getId() + ""))) {
                    model.getData().add(2, modelAttribute);
                }
                cuurentMap.put(Constants.TMP_INPUT, fullPath);
            }


            //delimiter
            ModelAttribute modelAttribute = (ModelAttribute) cuurentMap.get(Constants.TMP_ARG);
            /**
             * 添加代码[|| model.getId() == 53 || model.getId() == 54] By gongmingxing 2017.08.21
             */
            if (!(model.getId() == 1 || model.getId() == 3 || model.getId() == 4)) {
                model.getData().add(modelAttribute);
            }
            formatModel.add(model);
        }
        resultMap.put("models", formatModel);
        resultMap.put("join", join);
        return resultMap;
    }


    /**
     * 将model排序
     *
     * @param models
     * @param rules
     * @return
     */
    private List<Model> sortingModel(List<Model> models, List<Connect> rules) {
        List<Model> result = new ArrayList<>();

        List<String> targetRules = new ArrayList<>();
        List<String> sourceRules = new ArrayList<>();

        for (int i = 0; i < rules.size(); i++) {

            Connect connect = rules.get(i);


            if (i == 0) {
                sourceRules.add(connect.getSourceId());
            }
            sourceRules.add(connect.getTargetId());

            targetRules.add(connect.getTargetId());
        }

        targetRules.add("end");

        for (String sourceRule : sourceRules) {
            for (Model model : models) {
                if (sourceRule.equals(model.getBlockId())) {
                    result.add(model);
                    break;
                }
            }
        }

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setTargetBlockId(targetRules.get(i));
        }


        return result.size() == 0 ? Collections.EMPTY_LIST : result;
    }

    //生成树
    private static void iter(WorkflowNode node, WorkflowNode preNode, List<Connect> connects,List<Model> models) {
        WorkflowNode previous = findConnectPrevious(node, preNode, connects);
        if (previous != null) {
            node.getPreNodes().add(previous);
        }
        WorkflowNode next = findConnectNext(node, connects, models);
        if (next != null) {
            node.getNextNodes().add(next);
            iter(next, node, connects,models);
        }
    }

    //拿到每条路的头节点
    private static List<WorkflowNode> getFirsts(Map<String, List<Connect>> ruleChain, List<Model> models) {
        List<WorkflowNode> nodes = new ArrayList<WorkflowNode>();
        for (String key : ruleChain.keySet()) {
            WorkflowNode node = new WorkflowNode();
            List<Connect> connects = ruleChain.get(key);
            Connect firstConnect = connects.get(0);
            String nodeId = firstConnect.getSourceId();
            node.setId(nodeId);

            for (Model model : models) {
                if (model.getBlockId().equals(nodeId)) {
                    List<ModelAttribute> datas = model.getData();
                    for (ModelAttribute modelAttribute : datas) {
//                        if(modelAttribute.getMattribute().equals("function")){
//                            node.setId(nodeId + "_" + modelAttribute.getMvalue());
//                        }
                        node.getAttrs().put(modelAttribute.getMattribute(), modelAttribute.getMvalue());
                        node.getAttrs().put("blockID",model.getId().toString());
                    }
                }
            }

            nodes.add(node);
        }
        return nodes;
    }

    //拿到后继节点
    private static WorkflowNode findConnectNext(WorkflowNode node, List<Connect> connects,List<Model> models) {
        boolean isLastNode = false;
        String id = node.getId();
        WorkflowNode afterWorkflowNode = new WorkflowNode();
        for (Connect connect : connects) {
            if (connect.getSourceId().equals(id)) {
                isLastNode = true;
                String targetId = connect.getTargetId();
                afterWorkflowNode.setId(targetId);
                for(Model model : models){
                    if(model.getBlockId().equals(targetId)){
                        for(ModelAttribute modelAttribute : model.getData()){
//                            if(modelAttribute.getMattribute().equals("function")){
//                                afterWorkflowNode.setId(targetId + "_" + modelAttribute.getMvalue());
//                            }
                            afterWorkflowNode.getAttrs().put(modelAttribute.getMattribute(), modelAttribute.getMvalue());
                        }
                        afterWorkflowNode.getAttrs().put("blockID",model.getId().toString());
                    }
                }
                break;
            }
        }
        if (isLastNode) {
            return afterWorkflowNode;
        } else {
            return null;
        }
    }

    //拿到前驱节点
    private static WorkflowNode findConnectPrevious(WorkflowNode node, WorkflowNode preNode, List<Connect> connects) {
        //是否为第一个node
        boolean isFirstNode = false;
        WorkflowNode previousNode = new WorkflowNode();
        String id = node.getId();
        for (Connect connect : connects) {
            if (connect.getTargetId().equals(id)) {
                isFirstNode = true;
//                String previousId = connect.getSourceId();
//                previousNode.setId(previousId);
                previousNode = preNode;
                break;
            }
        }
        if (!isFirstNode) {
            return null;
        } else {
            return previousNode;
        }
    }

    //生成join,fork和head,tail节点
    private   WorkflowNode genJoinForkAndHeadTail(List<WorkflowNode> firstNodes) {

        //以第一条链表为基准
        WorkflowNode basicWorkflowNode = firstNodes.get(0);
        //将树里面的所有node添加进来。
        Set<WorkflowNode> nodeSet = new HashSet<WorkflowNode>();

        while (basicWorkflowNode.getNextNodes().size() != 0) {
            nodeSet.add(basicWorkflowNode);
            basicWorkflowNode = basicWorkflowNode.getNextNodes().get(0);
            if (basicWorkflowNode.getNextNodes().size() == 0) {
                nodeSet.add(basicWorkflowNode);
            }
        }

        //记录所有的多输入节点
        List<WorkflowNode> doubleInputNodes = new ArrayList<>();

        Map<Integer,Integer> connectElementMap = new HashMap<Integer,Integer>();
        for(int i=1; i<firstNodes.size(); i++){
            int thatNodeSize = 0;
            WorkflowNode countThatNode = firstNodes.get(i);
            while (countThatNode.getNextNodes().size() != 0){
                countThatNode = countThatNode.getNextNodes().get(0);
                thatNodeSize ++;
            }
            connectElementMap.put(i,thatNodeSize);
        }

        for (int i = 1; i < firstNodes.size(); i++) {
            //记录多输入的id
            String doubleInputNodeId = "";
            //拿来进行比较的链表
//            WorkflowNode thatNode = firstNodes.get(i);
            List<WorkflowNode> nodeTree = new ArrayList<WorkflowNode>(nodeSet);
            for (int j = 0; j < nodeTree.size(); j++) {
                //遍历的NodeTree中的Node
                WorkflowNode thatNode = firstNodes.get(i);
                WorkflowNode thisNode = nodeTree.get(j);
                List<WorkflowNode> preNodes = thisNode.getPreNodes();
                //遍历拿来比较的链表
                int thatNodeCount = connectElementMap.get(i);
                while (thatNodeCount != 0) {
                    //如果拿来比较的链表中的节点和NodeTree中的节点的id相等，那么更改NodeTree中的节点的前驱和后置节点。
                    if (thatNode.getId().equals(thisNode.getId())) {
                        doubleInputNodeId = thatNode.getId();
                        if(thatNode.getPreNodes().size() > 0){
                            for (WorkflowNode workflowNode : thatNode.getPreNodes()) {
                                if(!thisNode.getPreNodes().contains(workflowNode)){
                                    thisNode.getPreNodes().add(workflowNode);
                                }
                            }
                        }

                        if(thatNode.getNextNodes().size() > 0){
                            for (WorkflowNode workflowNode : thatNode.getNextNodes()) {
                                if(!thisNode.getNextNodes().contains(workflowNode)){
                                    thisNode.getNextNodes().add(workflowNode);
                                }
                            }
                        }
                        //将拿来比较的链表两个输入的节点的前驱和后置节点更改指向。
                        if(thatNode.getPreNodes().size() > 0){
                            thatNode.getPreNodes().get(0).getNextNodes().remove(thatNode);
                            thatNode.getPreNodes().get(0).getNextNodes().add(thisNode);
                        }

                        if(thatNode.getNextNodes().size() > 0){
                            thatNode.getNextNodes().get(0).getPreNodes().remove(thatNode);
                            thatNode.getNextNodes().get(0).getPreNodes().add(thisNode);
                        }
                        doubleInputNodes.add(thisNode);
                    }
                    thatNode = thatNode.getNextNodes().get(0);
                    thatNodeCount--;

                    if (thatNode.getNextNodes().size() == 0) {
                        if (thatNode.getId().equals(thisNode.getId())) {
                            doubleInputNodeId = thatNode.getId();
                            if(thatNode.getPreNodes().size() > 0){
                                for (WorkflowNode workflowNode : thatNode.getPreNodes()) {
                                    if(!thisNode.getPreNodes().contains(workflowNode)){
                                        thisNode.getPreNodes().add(workflowNode);
                                    }
                                }
                            }
                            if(thatNode.getNextNodes().size() > 0){
                                for (WorkflowNode workflowNode : thatNode.getNextNodes()) {
                                    if(!thisNode.getNextNodes().contains(workflowNode)){
                                        thisNode.getNextNodes().add(workflowNode);
                                    }
                                }
                            }
                            //将拿来比较的链表两个输入的节点的前驱和后置节点更改指向。
                            thatNode.getPreNodes().get(0).getNextNodes().clear();
                            thatNode.getPreNodes().get(0).getNextNodes().add(thisNode);

                            if(thatNode.getNextNodes().size() == 1){
                                thatNode.getNextNodes().get(0).getPreNodes().clear();
                                thatNode.getNextNodes().get(0).getPreNodes().add(thisNode);
                            }
                            doubleInputNodes.add(thisNode);
                        }
                    }
                }
            }

            //遍历拿来比较的链表，把多输入的那个节点剔除，加入NodeTree里面。
            WorkflowNode singleLinkNode = firstNodes.get(i);
            while (singleLinkNode.getNextNodes().size() != 0) {
                if (!singleLinkNode.getId().equals(doubleInputNodeId)) {
                    nodeSet.add(singleLinkNode);
                }
                singleLinkNode = singleLinkNode.getNextNodes().get(0);
                if (singleLinkNode.getNextNodes().size() == 0) {
                    if (!singleLinkNode.getId().equals(doubleInputNodeId)) {
                        nodeSet.add(singleLinkNode);
                    }
                }
            }
        }
        //到此为止这棵网状结构的树已经构成了

        //遍历多输入节点
        int joinFirst = 0;
        //用一个变量来保存join节点。
        List<WorkflowNode> allJoinNodes = new ArrayList<>();
        //生成join节点。
        for (WorkflowNode doubleInputNode : doubleInputNodes) {
            //新建一个join节点
            WorkflowNode joinNode = new WorkflowNode();
            joinNode.setId("join" + joinFirst);

            List<WorkflowNode> doubleInputNodeList = new ArrayList<>();
            doubleInputNodeList.add(doubleInputNode);
            //设置join节点的前驱节点
            joinNode.setPreNodes(doubleInputNode.getPreNodes());

            //设置join节点的后置节点
            joinNode.setNextNodes(doubleInputNodeList);

            //将多输入前驱节点的后置节点指向join节点
            List<WorkflowNode> joinNodes = new ArrayList<>();
            joinNodes.add(joinNode);
            for (WorkflowNode workflowNode : doubleInputNode.getPreNodes()) {
                workflowNode.setNextNodes(joinNodes);
            }

            //将多输入节点的前驱节点指向join节点
            doubleInputNode.setPreNodes(joinNodes);

            allJoinNodes.add(joinNode);
            joinFirst++;
        }


        //将firstNodes复制一份出来
        List<WorkflowNode> copyFirstNodes = new ArrayList<WorkflowNode>();
        for (WorkflowNode workflowNode : firstNodes) {
            copyFirstNodes.add(workflowNode);
        }

        //与join配对生成forkNode的算法。
        //定义一个list来记录已经生成过的fork节点。
        List<WorkflowNode> forkNodes = new ArrayList<WorkflowNode>();
        while (allJoinNodes.size() > 0) {
            List<WorkflowNode> forkedFitstNodes = new ArrayList<WorkflowNode>();
            for (WorkflowNode joinNode : allJoinNodes) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("firstNodeCount", 0);
                map.put("forkNodeCount", 0);
                Set<WorkflowNode> firstNodeSet = new HashSet<WorkflowNode>();
                map.put("firstNodeSet", firstNodeSet);
                Map<String, Object> firstAndForkNodeCount = getFirstNodeCount(map, joinNode);
//                int firstNodeCount = (Integer) firstAndForkNodeCount.get("firstNodeCount") - (Integer) firstAndForkNodeCount.get("forkNodeCount") / 2;
                int firstNodeCount = ((Set)firstAndForkNodeCount.get("firstNodeSet")).size();
                //如果这个join节点往前只找到两个没有前驱节点的first节点或者多输出节点，给这两个前驱节点加上fork节点。
                if (firstNodeCount == 2) {
                    //判断这个fork是发生在头节点还是中间节点上
                    boolean isHeadForkNode = true;
                    Set<WorkflowNode> returnFirstNodeSet = (Set<WorkflowNode>) firstAndForkNodeCount.get("firstNodeSet");
                    WorkflowNode forkNode = new WorkflowNode();
                    forkNode.setId("fork" + joinNode.getId().substring(joinNode.getId().length() - 1, joinNode.getId().length()));

                    List<WorkflowNode> forkNodeList = new ArrayList<WorkflowNode>();
                    forkNodeList.add(forkNode);

                    List<WorkflowNode> firsNodeList = new ArrayList<WorkflowNode>();
                    for (WorkflowNode workflowNode : returnFirstNodeSet) {
                        firsNodeList.add(workflowNode);
                    }

                    for (WorkflowNode firstNode : firsNodeList) {
                        if (firstNode.getPreNodes().size() > 0) {
                            isHeadForkNode = false;
                        }
                    }

                    if (isHeadForkNode) {
                        //设置forkNode的后置节点为firstNode。
                        forkNode.setNextNodes(firsNodeList);
                        //设置firstNode的前驱节点为forkNode。
                        for (WorkflowNode workflowNode : firsNodeList) {
                            workflowNode.setPreNodes(forkNodeList);
                        }
                        forkedFitstNodes.add(joinNode);
                        forkNodes.add(forkNode);
                    } else {
                        forkNode.setPreNodes(firsNodeList.get(0).getPreNodes());
                        forkNode.setNextNodes(firsNodeList);
                        firsNodeList.get(0).getPreNodes().get(0).setNextNodes(forkNodeList);
                        for (WorkflowNode workflowNode : firsNodeList) {
                            workflowNode.setPreNodes(forkNodeList);
                        }
                        forkNodes.add(forkNode);
                    }
                }
            }
            for (WorkflowNode workflowNode : forkedFitstNodes) {
                allJoinNodes.remove(workflowNode);
            }
        }

        //以任意一个firstNode节点为出发点，从后往前找到唯一的一个没有输入的节点，生成start节点。
        WorkflowNode anyFirstNode = firstNodes.get(0);
        while ((anyFirstNode.getPreNodes().size() != 0)) {
            anyFirstNode = anyFirstNode.getPreNodes().get(0);
        }

        List<WorkflowNode> firstForkNodes = new ArrayList<>();
        firstForkNodes.add(anyFirstNode);

        //生成start节点
        WorkflowNode startNode = new WorkflowNode();
        startNode.setId("start");
        startNode.setNextNodes(firstForkNodes);

        List<WorkflowNode> startNodes = new ArrayList<>();
        anyFirstNode.setPreNodes(startNodes);

        //从start节点开始找拥有两个输出的节点，并剔除已经生成过的fork节点。
        Set<WorkflowNode> initDoubleOputputNodes = new HashSet<>();
        Set<WorkflowNode> doubleOutputNodes = findDoubleOutputNodes(initDoubleOputputNodes, startNode);
        //生成新的fork节点，插入fork节点并改变指向。
        int forkSecondFlag = 0;
        List<WorkflowNode> allForkSecoundNodes = new ArrayList<WorkflowNode>();

        for (WorkflowNode doubleOutputNode : doubleOutputNodes) {
            WorkflowNode forkNode = new WorkflowNode();
            forkNode.setId("forkSecond" + forkSecondFlag);
            forkNode.setNextNodes(doubleOutputNode.getNextNodes());
            List<WorkflowNode> doubleOutputNodeList = new ArrayList<>();
            doubleOutputNodeList.add(doubleOutputNode);
            List<WorkflowNode> forkNodeList = new ArrayList<>();
            forkNode.setPreNodes(doubleOutputNodeList);
            doubleOutputNode.setNextNodes(forkNodeList);
            for (WorkflowNode nextNode : doubleOutputNode.getNextNodes()) {
                nextNode.setPreNodes(forkNodeList);
            }
            allForkSecoundNodes.add(forkNode);
            forkSecondFlag++;
        }

        //遍历所有的forkSecondNode,向后寻找没有输出的Node,如果数量为2，则添加与该fork对应的joinSecond节点。
        while (allForkSecoundNodes.size() != 0) {
            for (WorkflowNode forkSecondNode : allForkSecoundNodes) {
                Map<String, Object> map = new HashMap<String, Object>();
                Set<WorkflowNode> lastNodeSet = new HashSet<WorkflowNode>();
//            map.put("lastNodeCount",0);
//            map.put("joinSecoundNodeCount",0);
                map.put("LastNodeSet", lastNodeSet);
                Map<String, Object> lastNodeCount = getLastNodeCount(map, forkSecondNode);
                Set<WorkflowNode> returnedLastNodeSet = (Set<WorkflowNode>) lastNodeCount.get("LastNodeSet");
                List<WorkflowNode> returnedLastNodeList = new ArrayList<WorkflowNode>();
                for (WorkflowNode returnedLastNode : returnedLastNodeSet) {
                    returnedLastNodeList.add(returnedLastNode);
                }
                //如果恰好有两个节点没有
                if (returnedLastNodeList.size() == 2) {
                    WorkflowNode joinSecondNode = new WorkflowNode();
                    joinSecondNode.setId("joinSecond" + forkSecondNode.getId().substring(forkSecondNode.getId().length() - 1, forkSecondNode.getId().length()));
                    joinSecondNode.setPreNodes(returnedLastNodeList);
                    List<WorkflowNode> joinSecondNodeList = new ArrayList<WorkflowNode>();
                    joinSecondNodeList.add(joinSecondNode);
                    for (WorkflowNode workflowNode : returnedLastNodeList) {
                        workflowNode.setNextNodes(joinSecondNodeList);
                    }
                    allForkSecoundNodes.remove(forkSecondNode);
                }
            }
        }

        //从任意一个first节点往后面找，找到唯一的那个只有一个输出的节点，在后面添加end节点。
        Set<WorkflowNode> finalLastNodeSet = new HashSet<WorkflowNode>();
        finalLastNodeSet = findFinalLastNode(finalLastNodeSet, startNode);
        List<WorkflowNode> finalLastNodeList = new ArrayList<>();
        for(WorkflowNode workflowNode : finalLastNodeSet)
            finalLastNodeList.add(workflowNode);
        if (finalLastNodeSet.size() == 1) {
            WorkflowNode endNode = new WorkflowNode();
            endNode.setId("end");
            endNode.setPreNodes(finalLastNodeList);
            List<WorkflowNode> endNodeList = new ArrayList<WorkflowNode>();
            endNodeList.add(endNode);
            finalLastNodeList.get(0).setNextNodes(endNodeList);
        }
        //到此为止这棵树已经完全生成了，包括fork,join,start以及end节点。
        return startNode;
    }

    public static Map<String, Object> getFirstNodeCount(Map<String, Object> map, WorkflowNode baseNode) {
        Map<String, Object> currentMap = map;
        //如果节点前面没有前驱节点，进行计数。
        if (baseNode.getPreNodes().size() == 0 || (baseNode.getPreNodes().size() == 1
                && baseNode.getPreNodes().get(0).getNextNodes().size() > 1
                && baseNode.getPreNodes().get(0).getId().indexOf("fork") == -1)) {
//        if(baseNode.getPreNodes().size() == 0){
            currentMap.put("firstNodeCount", (Integer) currentMap.get("firstNodeCount") + 1);
            Set<WorkflowNode> firstNodeSet = (Set<WorkflowNode>) currentMap.get("firstNodeSet");
            firstNodeSet.add(baseNode);
            currentMap.put("firstNodeSet", firstNodeSet);
        }

        if (baseNode.getId().indexOf("fork") != -1 && baseNode.getPreNodes().size() == 0) {
            currentMap.put("forkNodeCount", (Integer) currentMap.get("forkNodeCount") + 1);
        }

        //如果前驱节点只有一个节点，那么递归，继续往前找，直到找到头节点。
        if (baseNode.getPreNodes().size() == 1) {
            WorkflowNode node = baseNode.getPreNodes().get(0);
            currentMap = getFirstNodeCount(currentMap, node);
        }

        //如果前驱节点有多个节点，那么进行遍历，然后递归，往前找，直到找到头结点。
        if (baseNode.getPreNodes().size() > 1) {
            List<WorkflowNode> preNodes = baseNode.getPreNodes();
            for (WorkflowNode node : preNodes) {
                currentMap = getFirstNodeCount(currentMap, node);
            }
        }
        return currentMap;
    }

    private static Set<WorkflowNode> findDoubleOutputNodes(Set<WorkflowNode> doubleOutputNodes, WorkflowNode startNode) {
        Set<WorkflowNode> currntLastNodes = doubleOutputNodes;
        if (startNode.getNextNodes().size() > 1 && startNode.getId().indexOf("fork") == -1) {
            currntLastNodes.add(startNode);
        }

        if (startNode.getNextNodes().size() > 0) {
            for(WorkflowNode workflowNode : startNode.getNextNodes())
                currntLastNodes = findDoubleOutputNodes(currntLastNodes, workflowNode);
        }

        return currntLastNodes;
    }

    private static Map<String, Object> getLastNodeCount(Map<String, Object> map, WorkflowNode baseNode) {
        Map<String, Object> currentMap = map;
        if (baseNode.getNextNodes().size() == 0) {
//            currentMap.put("lastNodeCount",(Integer)currentMap.get("lastNodeCount") + 1);
            Set<WorkflowNode> lastNodeSet = (Set<WorkflowNode>) currentMap.get("LastNodeSet");
            lastNodeSet.add(baseNode);
            currentMap.put("LastNodeSet", lastNodeSet);
        }
        if (baseNode.getNextNodes().size() == 1) {
            currentMap = getLastNodeCount(currentMap, baseNode.getNextNodes().get(0));
        }

        if (baseNode.getNextNodes().size() > 1) {
            for (WorkflowNode workflowNode : baseNode.getNextNodes()) {
                currentMap = getLastNodeCount(currentMap, workflowNode);
            }
        }
        return currentMap;
    }

    private static Set<WorkflowNode> findFinalLastNode(Set<WorkflowNode> set, WorkflowNode startNode) {
        Set<WorkflowNode> currentSet = set;
        if (startNode.getNextNodes().size() == 0) {
            currentSet.add(startNode);
        } else if (startNode.getNextNodes().size() > 0) {
            for (WorkflowNode workflowNode : startNode.getNextNodes()) {
                currentSet = findFinalLastNode(currentSet, workflowNode);
            }
        }
        return currentSet;
    }

    public WorkflowNode getStartNode(List<Model> models, List<Connect> rules, String flowId, String username, int userId, DatabaseConnectService databaseConnectService, DatabaseTypeService databaseTypeService) {
        List<WorkflowNode> firstNodes = null;
        String xmlVersion = "V4_DEVELOP";
        if(xmlVersion.equals("V4_DEVELOP")){
            firstNodes = getNewFirstNode(models,rules);
            WorkflowNode newStartNode = genNewJoinForkAndHeadTail(firstNodes);
            return newStartNode;
        }else{
            ConnectsInfo connectsInfo = formatRules(models,rules);
            Map<String, List<Connect>> ruleChain = connectsInfo.getRuleChain();

            Map<String, List<Connect>> formatedRuleChain = formatRuleChain(ruleChain);

            firstNodes = getFirsts(formatedRuleChain, models);
            for (WorkflowNode node : firstNodes) {
            iter(node, null, formatedRuleChain.get(node.getId()),models);
            }
            WorkflowNode startNode = genJoinForkAndHeadTail(firstNodes);
            return startNode;
        }
    }

    private List<WorkflowNode> getNewFirstNode(List<Model> models, List<Connect> rules) {
        Map<String,WorkflowNode> nodeMap = new HashMap<>();
        //第一步拿到所有的stateModelId;
        Set<String> allNodeIds = new HashSet<>();
        List<WorkflowNode> firstNodes = new ArrayList<>();

        for(Connect connect : rules){
            allNodeIds.add(connect.getSourceId());
            allNodeIds.add(connect.getTargetId());
        }
        for(String nodeId : allNodeIds){
            WorkflowNode workflowNode = new WorkflowNode();
            workflowNode.setId(nodeId);
            if(nodeId.contains("source")){
                workflowNode.getSourceConnectTypes().add(nodeId);
            }
            if(nodeId.contains("target")){
                workflowNode.getTargetConnectTypes().add(nodeId);
            }
            for(Model model : models){
                if(model.getBlockId().equals(nodeId)){
                    for(ModelAttribute modelAttribute : model.getData()){
                        workflowNode.getAttrs().put(modelAttribute.getMattribute(),modelAttribute.getMvalue());
                    }
                    workflowNode.getAttrs().put("blockID",model.getId().toString());
                }
            }
            nodeMap.put(nodeId,workflowNode);
        }

        Set<String> ids = nodeMap.keySet();


        for(Connect connect : rules){
            if(ids.contains(connect.getSourceId())){
                WorkflowNode sourceNode = nodeMap.get(connect.getSourceId());
                sourceNode.getNextNodes().add(nodeMap.get(connect.getTargetId()));
                sourceNode.getTargetConnectTypes().add(connect.getTargetId() + "_" + connect.getConnectType());
                WorkflowNode targetNode = nodeMap.get(connect.getTargetId());
                targetNode.getPreNodes().add(sourceNode);
                targetNode.getSourceConnectTypes().add(connect.getSourceId() + "_" + connect.getConnectType());
                for(Model model : models){
                    if(model.getBlockId().equals(connect.getSourceId())){
                        if(Setting.getDoubleOutputDataIds().contains(model.getId() + "")){
                            targetNode.getSourceConnectPostion().add(connect.getConnectSourcePosition());
                        }
                    }
                    if(model.getBlockId().equals(connect.getTargetId())){
                        if(Setting.getOrdinaryApplyV4Ids().contains(model.getId() + "")){
                            sourceNode.getTargetConnectPosition().add(connect.getTargetId() + "_" + connect.getConnectTargetPosition());
                        }
                    }
                }
            }
        }

        for(String id : ids){
            WorkflowNode workflowNode = nodeMap.get(id);
            if(workflowNode.getPreNodes().size() == 0){
                firstNodes.add(workflowNode);
            }
        }
        return firstNodes;
    }


    private WorkflowNode genNewJoinForkAndHeadTail(List<WorkflowNode> firstNodes) {
        //找出多输入节点
        List<WorkflowNode> doubleInputNodes = getdoubleInputNodes(firstNodes);
        //找出多输出节点
        List<WorkflowNode> allDoubleOutputNodes = getDoubleOutputNodes(firstNodes);
        List<WorkflowNode> preparaedOutputNodes = new ArrayList<>();
        for(WorkflowNode workflowNode : allDoubleOutputNodes)
            preparaedOutputNodes.add(workflowNode);
        int forkCount = 0;
        //寻找非传统的多输出节点
        List<WorkflowNode> unTranditionalDoubleOutputNodes = findUnTranditionalDoubleOutputNode(doubleInputNodes, allDoubleOutputNodes, new ArrayList<WorkflowNode>());
        //寻找所有的一条线连join，一条线不连join的节点。
        List<WorkflowNode> joinAndUnjoinNodes = findJoinAndUnjoinNode(doubleInputNodes, unTranditionalDoubleOutputNodes, new ArrayList<WorkflowNode>());
        //排除伪装的非传统的多输出节点
        List<WorkflowNode> removedMaskUnTranditionalOutputNodes = removeMaskUnTranditionalOutputNodes(unTranditionalDoubleOutputNodes, joinAndUnjoinNodes);
        //拿到第一个unTranditionalDoubleOutputNode
        WorkflowNode firstUnTranditionalNode = getFirstUnTranditionalNode(removedMaskUnTranditionalOutputNodes);
        //寻找嵌套最深的多输出节点
        while(allDoubleOutputNodes.size() != 0){
            Iterator<WorkflowNode> iterator = allDoubleOutputNodes.iterator();
            while(iterator.hasNext()){
                WorkflowNode doubleOutputNode = iterator.next();
                List<Boolean> isDeepestNodes = new ArrayList<>();
                for(WorkflowNode node : doubleOutputNode.getNextNodes()){
                    boolean isDeepestNode = isDeepestNode(true, node, allDoubleOutputNodes);
                    isDeepestNodes.add(isDeepestNode);
                }
                if(!isDeepestNodes.contains(false)){
                    //寻找与之相连的节点有没有多输入的节点，如果没有按常规的节点闭合，如果有则按新方式闭合
                    Set<WorkflowNode> nextDoubleInputNodes = getNextDoubleInputNode(new HashSet<WorkflowNode>(), doubleOutputNode, doubleInputNodes);
                    if(nextDoubleInputNodes.size() == 0){
                        List<WorkflowNode> nextNodes = new ArrayList<>();
                        for(WorkflowNode workflowNode : doubleOutputNode.getNextNodes())
                            nextNodes.add(workflowNode);
                        WorkflowNode forkNode = new WorkflowNode();
                        forkNode.setId("forkThird" + forkCount);
                        forkNode.getPreNodes().add(doubleOutputNode);
                        forkNode.setNextNodes(nextNodes);
                        for(WorkflowNode nextNode : doubleOutputNode.getNextNodes()){
                            nextNode.getPreNodes().remove(doubleOutputNode);
                            nextNode.getPreNodes().add(forkNode);
                        }
                        doubleOutputNode.getNextNodes().clear();
                        doubleOutputNode.getNextNodes().add(forkNode);
                        //生成join节点

                        WorkflowNode forkSecondNode = doubleOutputNode;
                        Map<String, Object> map = new HashMap<String, Object>();
                        Set<WorkflowNode> lastNodeSet = new HashSet<WorkflowNode>();

                        map.put("LastNodeSet", lastNodeSet);
                        Map<String, Object> lastNodeCount = getLastNodeCount(map, forkSecondNode);
                        Set<WorkflowNode> returnedLastNodeSet = (Set<WorkflowNode>) lastNodeCount.get("LastNodeSet");
                        List<WorkflowNode> returnedLastNodeList = new ArrayList<WorkflowNode>();
                        for (WorkflowNode returnedLastNode : returnedLastNodeSet) {
                            returnedLastNodeList.add(returnedLastNode);
                        }

                        //如果恰好有两个节点没有
                        if (returnedLastNodeList.size() == 2) {
                            WorkflowNode joinSecondNode = new WorkflowNode();
                            joinSecondNode.setId("joinThird" + forkCount);
                            joinSecondNode.setPreNodes(returnedLastNodeList);
                            List<WorkflowNode> joinSecondNodeList = new ArrayList<WorkflowNode>();
                            joinSecondNodeList.add(joinSecondNode);
                            for (WorkflowNode workflowNode : returnedLastNodeList) {
                                workflowNode.setNextNodes(joinSecondNodeList);
                            }
                        }
                        forkCount ++;
                        iterator.remove();
                        break;
                    }if(nextDoubleInputNodes.size() == 1 && removedMaskUnTranditionalOutputNodes.contains(doubleOutputNode)){
                        if(!firstUnTranditionalNode.equals(doubleOutputNode)){
                            //拿到另外一个单输入的末尾节点
                            Set<WorkflowNode> set = new HashSet<>();
                            Set<WorkflowNode> anotherNodeSet = getAnotherNode(doubleInputNodes, doubleOutputNode,set);
                            WorkflowNode anotherNode = null;
                            if(anotherNodeSet.size() == 1)
                                anotherNode = anotherNodeSet.iterator().next();
                            //生成fork
                            WorkflowNode forkNode = new WorkflowNode();
                            forkNode.setId("forkThird" + forkCount);
                            forkNode.getPreNodes().add(doubleOutputNode);
                            List<WorkflowNode> nextNodeList = new ArrayList<>();
                            for(WorkflowNode node : doubleOutputNode.getNextNodes())
                                nextNodeList.add(node);
                            forkNode.setNextNodes(nextNodeList);
                            for(WorkflowNode nextNode : doubleOutputNode.getNextNodes()){
                                nextNode.getPreNodes().remove(doubleOutputNode);
                                nextNode.getPreNodes().add(forkNode);
                            }
                            List<WorkflowNode> doubleOutputNextNodes = new ArrayList<>();
                            for(WorkflowNode node : doubleOutputNode.getNextNodes())
                                doubleOutputNextNodes.add(node);

                            doubleOutputNode.getNextNodes().clear();
                            doubleOutputNode.getNextNodes().add(forkNode);

                            //生成join
                            WorkflowNode joinNode = new WorkflowNode();
                            joinNode.setId("joinThird" + forkCount);
                            List<WorkflowNode> joinPreNodes = new ArrayList<>();
                            joinPreNodes.add(anotherNode);
                            joinPreNodes.add(forkNode);
                            joinNode.setPreNodes(joinPreNodes);
                            //从anotherNode往前寻找doubleOutputNode的下一个节点
                            WorkflowNode anotherNextNode = getAnotherNextNode(forkNode, anotherNode, null);
                            forkNode.getNextNodes().clear();
                            forkNode.getNextNodes().add(anotherNextNode);
                            forkNode.getNextNodes().add(joinNode);

                            doubleOutputNextNodes.remove(anotherNode);
                            doubleOutputNextNodes.get(0).getPreNodes().remove(forkNode);

                            anotherNode.getNextNodes().add(joinNode);
                            forkCount ++;
                            iterator.remove();
                            break;
                        }else{
                            Set<WorkflowNode> set = new HashSet<>();
                            Set<WorkflowNode> anotherNodeSet = getAnotherNode(doubleInputNodes, doubleOutputNode,set);
                            WorkflowNode anotherNode = anotherNodeSet.iterator().next();
                            //生成fork
                            WorkflowNode forkNode = new WorkflowNode();
                            forkNode.setId("forkThird" + forkCount);
                            forkNode.getPreNodes().add(doubleOutputNode);
                            List<WorkflowNode> nextNodeList = new ArrayList<>();
                            for(WorkflowNode node : doubleOutputNode.getNextNodes())
                                nextNodeList.add(node);
                            forkNode.setNextNodes(nextNodeList);
                            for(WorkflowNode nextNode : doubleOutputNode.getNextNodes()){
                                nextNode.getPreNodes().remove(doubleOutputNode);
                                nextNode.getPreNodes().add(forkNode);
                            }
                            doubleOutputNode.getNextNodes().clear();
                            doubleOutputNode.getNextNodes().add(forkNode);

                            //生成join
                            WorkflowNode joinNode = new WorkflowNode();
                            joinNode.setId("joinThird" + forkCount);
                            List<WorkflowNode> joinPreNodes = new ArrayList<>();
                            joinPreNodes.add(anotherNode);
                            joinPreNodes.add(doubleOutputNode);
                            joinNode.setPreNodes(joinPreNodes);

                            List<WorkflowNode> nextNodes = new ArrayList<>();
                            for(WorkflowNode node : forkNode.getNextNodes())
                                nextNodes.add(node);

                            //从anotherNode往前寻找forkNode的下一个节点
                            WorkflowNode anotherNextNode = getAnotherNextNode(forkNode, anotherNode, null);
                            forkNode.getNextNodes().clear();
                            forkNode.getNextNodes().add(anotherNextNode);
                            forkNode.getNextNodes().add(joinNode);

                            anotherNode.getNextNodes().add(joinNode);

                            nextNodes.remove(anotherNextNode);
                            joinNode.getNextNodes().add(nextNodes.get(0));
                            nextNodes.get(0).getPreNodes().remove(forkNode);
                            nextNodes.get(0).getPreNodes().add(joinNode);
                            forkCount ++;
                            iterator.remove();
                            break;
                        }
                    }else{
                        LOGGER.info("can't support this condition!!!");
                        iterator.remove();
                    }
                }
            }
        }

                //将菱环形的节点生成forkThird和joinThird
        List<WorkflowNode> preparedDoubleOutputNodes = new ArrayList<>();
        for(WorkflowNode preparaNode : preparaedOutputNodes){
            if(!removedMaskUnTranditionalOutputNodes.contains(preparaNode)){
                preparedDoubleOutputNodes.add(preparaNode);
            }
        }

        int forkThirdCount = 0;
        for(WorkflowNode doubleOutputNode : preparedDoubleOutputNodes){
            //寻找是否有匹配的多输入节点
            WorkflowNode joinDoubleInputNode = findJoinDoubleInputNode(doubleOutputNode);
            if(joinDoubleInputNode != null){
                //添加fork节点。
                WorkflowNode forkNode = new WorkflowNode();
                forkNode.setId("forkForth" + forkThirdCount);
                forkNode.setNextNodes(doubleOutputNode.getNextNodes());
                List<WorkflowNode> doubleOutputNodeList = new ArrayList<>();
                doubleOutputNodeList.add(doubleOutputNode);
                forkNode.setPreNodes(doubleOutputNodeList);
                List<WorkflowNode> forkNodeList = new ArrayList<>();
                forkNodeList.add(forkNode);
                for(WorkflowNode nextNode : doubleOutputNode.getNextNodes()){
                    nextNode.getPreNodes().remove(doubleOutputNode);
                    nextNode.getPreNodes().add(forkNode);
                }
                doubleOutputNode.setNextNodes(forkNodeList);

                //添加join节点
                WorkflowNode joinNode = new WorkflowNode();
                joinNode.setId("joinForth" + forkThirdCount);
                joinNode.setPreNodes(joinDoubleInputNode.getPreNodes());
                List<WorkflowNode> joinDoubleInputNodeList = new ArrayList<>();
                joinDoubleInputNodeList.add(joinDoubleInputNode);
                joinNode.setNextNodes(joinDoubleInputNodeList);
                for(WorkflowNode preNode : joinDoubleInputNode.getPreNodes()){
                    preNode.getNextNodes().remove(joinDoubleInputNode);
                    preNode.getNextNodes().add(joinNode);
                }
                List<WorkflowNode> joinNodeList = new ArrayList<>();
                joinNodeList.add(joinNode);
                joinDoubleInputNode.setPreNodes(joinNodeList);
                forkThirdCount++;
            }
        }


        //遍历多输入节点
        int joinFirst = 0;
        //用一个变量来保存join节点。
        List<WorkflowNode> allJoinNodes = new ArrayList<>();

        //剔除已经用过的doubleInputNodes
        Iterator<WorkflowNode> iterator = doubleInputNodes.iterator();
        while(iterator.hasNext()){
            WorkflowNode node = iterator.next();
            if(node.getPreNodes().size() <= 1){
                iterator.remove();
            }
        }

        //剔除已经用过的doubleOutputNodes
        Iterator<WorkflowNode> iterator1 = preparaedOutputNodes.iterator();
        while(iterator1.hasNext()){
            WorkflowNode node = iterator1.next();
            if(node.getNextNodes().size() <= 1){
                iterator1.remove();
            }
        }

        //生成join节点。
        for (WorkflowNode doubleInputNode : doubleInputNodes) {
            //新建一个join节点
            WorkflowNode joinNode = new WorkflowNode();
            joinNode.setId("join" + joinFirst);

            List<WorkflowNode> doubleInputNodeList = new ArrayList<>();
            doubleInputNodeList.add(doubleInputNode);
            //设置join节点的前驱节点
            joinNode.setPreNodes(doubleInputNode.getPreNodes());

            //设置join节点的后置节点
            joinNode.setNextNodes(doubleInputNodeList);

            //将多输入前驱节点的后置节点指向join节点
            List<WorkflowNode> joinNodes = new ArrayList<>();
            joinNodes.add(joinNode);
            for (WorkflowNode workflowNode : doubleInputNode.getPreNodes()) {
                workflowNode.getNextNodes().remove(doubleInputNode);
                workflowNode.getNextNodes().add(joinNode);
            }

            //将多输入节点的前驱节点指向join节点
            doubleInputNode.setPreNodes(joinNodes);

            allJoinNodes.add(joinNode);
            joinFirst++;
        }


        //将firstNodes复制一份出来
        List<WorkflowNode> copyFirstNodes = new ArrayList<WorkflowNode>();
        for (WorkflowNode workflowNode : firstNodes) {
            copyFirstNodes.add(workflowNode);
        }

        //与join配对生成forkNode的算法。
        //定义一个list来记录已经生成过的fork节点。
        List<WorkflowNode> forkNodes = new ArrayList<WorkflowNode>();
        while (allJoinNodes.size() > 0) {
            List<WorkflowNode> forkedFitstNodes = new ArrayList<WorkflowNode>();
            for (WorkflowNode joinNode : allJoinNodes) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("firstNodeCount", 0);
                map.put("forkNodeCount", 0);
                Set<WorkflowNode> firstNodeSet = new HashSet<WorkflowNode>();
                map.put("firstNodeSet", firstNodeSet);
                Map<String, Object> firstAndForkNodeCount = getFirstNodeCount(map, joinNode);
//                int firstNodeCount = (Integer) firstAndForkNodeCount.get("firstNodeCount") - (Integer) firstAndForkNodeCount.get("forkNodeCount") / 2;
                int firstNodeCount = ((Set)firstAndForkNodeCount.get("firstNodeSet")).size();
                //如果这个join节点往前只找到两个没有前驱节点的first节点或者多输出节点，给这两个前驱节点加上fork节点。
                if (firstNodeCount == 2) {
                    //判断这个fork是发生在头节点还是中间节点上
                    boolean isHeadForkNode = true;
                    Set<WorkflowNode> returnFirstNodeSet = (Set<WorkflowNode>) firstAndForkNodeCount.get("firstNodeSet");
                    WorkflowNode forkNode = new WorkflowNode();
                    forkNode.setId("fork" + joinNode.getId().substring(joinNode.getId().length() - 1, joinNode.getId().length()));

                    List<WorkflowNode> forkNodeList = new ArrayList<WorkflowNode>();
                    forkNodeList.add(forkNode);

                    List<WorkflowNode> firsNodeList = new ArrayList<WorkflowNode>();
                    for (WorkflowNode workflowNode : returnFirstNodeSet) {
                        firsNodeList.add(workflowNode);
                    }

                    for (WorkflowNode firstNode : firsNodeList) {
                        if (firstNode.getPreNodes().size() > 0) {
                            isHeadForkNode = false;
                        }
                    }

                    if (isHeadForkNode) {
                        //设置forkNode的后置节点为firstNode。
                        forkNode.setNextNodes(firsNodeList);
                        //设置firstNode的前驱节点为forkNode。
                        for (WorkflowNode workflowNode : firsNodeList) {
                            workflowNode.setPreNodes(forkNodeList);
                        }
                        forkedFitstNodes.add(joinNode);
                        forkNodes.add(forkNode);
                    } else {
                        forkNode.setPreNodes(firsNodeList.get(0).getPreNodes());
                        forkNode.setNextNodes(firsNodeList);
                        firsNodeList.get(0).getPreNodes().get(0).setNextNodes(forkNodeList);
                        for (WorkflowNode workflowNode : firsNodeList) {
                            workflowNode.setPreNodes(forkNodeList);
                        }
                        forkNodes.add(forkNode);
                    }
                }
            }
            for (WorkflowNode workflowNode : forkedFitstNodes) {
                allJoinNodes.remove(workflowNode);
            }
        }

        //以任意一个firstNode节点为出发点，从后往前找到唯一的一个没有输入的节点，生成start节点。
        WorkflowNode anyFirstNode = firstNodes.get(0);
        while ((anyFirstNode.getPreNodes().size() != 0)) {
            anyFirstNode = anyFirstNode.getPreNodes().get(0);
        }

        List<WorkflowNode> firstForkNodes = new ArrayList<>();
        firstForkNodes.add(anyFirstNode);

        //生成start节点
        WorkflowNode startNode = new WorkflowNode();
        startNode.setId("start");
        startNode.setNextNodes(firstForkNodes);

        List<WorkflowNode> startNodes = new ArrayList<>();
        anyFirstNode.setPreNodes(startNodes);

        //从start节点开始找拥有两个输出的节点，并剔除已经生成过的fork节点。
        Set<WorkflowNode> initDoubleOputputNodes = new HashSet<>();
        Set<WorkflowNode> doubleOutputNodes = findDoubleOutputNodes(initDoubleOputputNodes, startNode);
        //生成新的fork节点，插入fork节点并改变指向。
        int forkSecondFlag = 0;
        List<WorkflowNode> allForkSecoundNodes = new ArrayList<>();

        for (WorkflowNode doubleOutputNode : doubleOutputNodes) {
            WorkflowNode forkNode = new WorkflowNode();
            forkNode.setId("forkSecond" + forkSecondFlag);
            //更改节点指向
            forkNode.setNextNodes(doubleOutputNode.getNextNodes());
            ArrayList<WorkflowNode> doubleOutputNodeList = new ArrayList<>();
            doubleOutputNodeList.add(doubleOutputNode);
            forkNode.setPreNodes(doubleOutputNodeList);
            List<WorkflowNode> forkNodeList = new ArrayList<>();
            forkNodeList.add(forkNode);
            for(WorkflowNode workflowNode : doubleOutputNode.getNextNodes()){
                workflowNode.setPreNodes(forkNodeList);
            }
            doubleOutputNode.setNextNodes(forkNodeList);
            allForkSecoundNodes.add(forkNode);
            forkSecondFlag++;
        }

        int allForkSecondNodeSize = allForkSecoundNodes.size();
        //遍历所有的forkSecondNode,向后寻找没有输出的Node,如果数量为2，则添加与该fork对应的joinSecond节点。
        while (allForkSecondNodeSize != 0) {
            Iterator<WorkflowNode> iterator2 = allForkSecoundNodes.iterator();
            while(iterator2.hasNext()){
                WorkflowNode forkSecondNode = iterator2.next();
                Map<String, Object> map = new HashMap<String, Object>();
                Set<WorkflowNode> lastNodeSet = new HashSet<WorkflowNode>();
//            map.put("lastNodeCount",0);
//            map.put("joinSecoundNodeCount",0);
                map.put("LastNodeSet", lastNodeSet);
                Map<String, Object> lastNodeCount = getLastNodeCount(map, forkSecondNode);
                Set<WorkflowNode> returnedLastNodeSet = (Set<WorkflowNode>) lastNodeCount.get("LastNodeSet");
                List<WorkflowNode> returnedLastNodeList = new ArrayList<WorkflowNode>();
                for (WorkflowNode returnedLastNode : returnedLastNodeSet) {
                    returnedLastNodeList.add(returnedLastNode);
                }
                //如果恰好有两个节点没有
                if (returnedLastNodeList.size() == 2) {
                    WorkflowNode joinSecondNode = new WorkflowNode();
                    joinSecondNode.setId("joinSecond" + forkSecondNode.getId().substring(forkSecondNode.getId().length() - 1, forkSecondNode.getId().length()));
                    joinSecondNode.setPreNodes(returnedLastNodeList);
                    List<WorkflowNode> joinSecondNodeList = new ArrayList<WorkflowNode>();
                    joinSecondNodeList.add(joinSecondNode);
                    for (WorkflowNode workflowNode : returnedLastNodeList) {
                        workflowNode.setNextNodes(joinSecondNodeList);
                    }
                    iterator2.remove();
                    allForkSecondNodeSize--;
                }
            }
        }

        //从任意一个first节点往后面找，找到唯一的那个只有一个输出的节点，在后面添加end节点。
        Set<WorkflowNode> finalLastNodeSet = new HashSet<WorkflowNode>();
        finalLastNodeSet = findFinalLastNode(finalLastNodeSet, startNode);
        List<WorkflowNode> finalLastNodeList = new ArrayList<>();
        for(WorkflowNode workflowNode : finalLastNodeSet)
            finalLastNodeList.add(workflowNode);
        if (finalLastNodeSet.size() == 1) {
            WorkflowNode endNode = new WorkflowNode();
            endNode.setId("end");
            endNode.setPreNodes(finalLastNodeList);
            List<WorkflowNode> endNodeList = new ArrayList<WorkflowNode>();
            endNodeList.add(endNode);
            finalLastNodeList.get(0).setNextNodes(endNodeList);
        }
        //到此为止这棵树已经完全生成了，包括fork,join,start以及end节点。
        return startNode;
    }

    private List<WorkflowNode> removeMaskUnTranditionalOutputNodes(List<WorkflowNode> unTranditionalDoubleOutputNodes, List<WorkflowNode> joinAndUnjoinNodes) {
        List<WorkflowNode> preparaUnTranditionalOutputNodes = new ArrayList<>();
        for(WorkflowNode workflowNode : unTranditionalDoubleOutputNodes)
            preparaUnTranditionalOutputNodes.add(workflowNode);
        for(WorkflowNode workflowNode : unTranditionalDoubleOutputNodes){
            int containsCount = 0;
            int returnCount = containsJoinAndUnjoinNode(containsCount, workflowNode, joinAndUnjoinNodes);
            if(returnCount == 0){
                preparaUnTranditionalOutputNodes.remove(workflowNode);
            }
        }
        return preparaUnTranditionalOutputNodes;
    }

    private int containsJoinAndUnjoinNode(int containsCount,WorkflowNode workflowNode, List<WorkflowNode> joinAndUnjoinNodes) {
        if(joinAndUnjoinNodes.contains(workflowNode)){
            containsCount++;
        }
        for(WorkflowNode node : workflowNode.getNextNodes()){
            containsCount = containsJoinAndUnjoinNode(containsCount,node,joinAndUnjoinNodes);
        }
        return containsCount;
    }

    private List<WorkflowNode> findJoinAndUnjoinNode(List<WorkflowNode> doubleInputNodes, List<WorkflowNode> unTranditionalDoubleOutputNodes, ArrayList<WorkflowNode> workflowNodes) {
        List<WorkflowNode> preparaJoinAndUnjoinNode = new ArrayList<>();
        for(WorkflowNode workflowNode : unTranditionalDoubleOutputNodes)
            preparaJoinAndUnjoinNode.add(workflowNode);
        Iterator<WorkflowNode> iterator = preparaJoinAndUnjoinNode.iterator();
        while(iterator.hasNext()){
            WorkflowNode node = iterator.next();
            Map<WorkflowNode,Integer> doubleInputNodesMap = new HashMap<>();
            for(WorkflowNode workflowNode : doubleInputNodes)
                doubleInputNodesMap.put(workflowNode,0);
            boolean isJoinAndUnjoinNode = isJoinAndUnjoinNode(node, doubleInputNodesMap, doubleInputNodes);
            if(!isJoinAndUnjoinNode){
                iterator.remove();
            }
        }
        return preparaJoinAndUnjoinNode;
    }

    private boolean isJoinAndUnjoinNode(WorkflowNode node, Map<WorkflowNode, Integer> doubleInputNodesMap, List<WorkflowNode> doubleInputNodes) {
        Map<WorkflowNode, Integer> workflowNodeIntegerMap = genJoinAndUnjoinMap(node, doubleInputNodesMap, doubleInputNodes);
        boolean isJoinAndUnjoinNode = true;
        for(WorkflowNode workflowNode : workflowNodeIntegerMap.keySet()){
            if(workflowNodeIntegerMap.get(workflowNode) > 1){
                isJoinAndUnjoinNode = false;
                break;
            }
        }
        return isJoinAndUnjoinNode;
    }

    private Map<WorkflowNode,Integer> genJoinAndUnjoinMap(WorkflowNode node, Map<WorkflowNode, Integer> doubleInputNodesMap, List<WorkflowNode> doubleInputNodes) {
        if(doubleInputNodes.contains(node)){
            doubleInputNodesMap.put(node, doubleInputNodesMap.get(node) + 1);
        }
        for(WorkflowNode workflowNode : node.getNextNodes()){
            doubleInputNodesMap =  genJoinAndUnjoinMap(workflowNode,doubleInputNodesMap,doubleInputNodes);
        }
        return doubleInputNodesMap;
    }

    private WorkflowNode getFirstUnTranditionalNode(List<WorkflowNode> unTranditionalDoubleOutputNodes) {
        WorkflowNode firstUnTranditionNode = null;
        for(WorkflowNode workflowNode : unTranditionalDoubleOutputNodes){
            boolean isFirstUnTranditionalNode = isFirstUnTranditionalNode(workflowNode.getPreNodes().get(0), unTranditionalDoubleOutputNodes, true);
            if(isFirstUnTranditionalNode){
                firstUnTranditionNode = workflowNode;
                break;
            }
        }
        return firstUnTranditionNode;
    }

    private boolean isFirstUnTranditionalNode(WorkflowNode workflowNode, List<WorkflowNode> unTranditionalDoubleOutputNodes,boolean isFirstUnTranditionalNode) {
        if(unTranditionalDoubleOutputNodes.contains(workflowNode)){
            isFirstUnTranditionalNode = false;
        }
        for(WorkflowNode node : workflowNode.getPreNodes()){
            isFirstUnTranditionalNode = isFirstUnTranditionalNode(node, unTranditionalDoubleOutputNodes, isFirstUnTranditionalNode);
        }
        return isFirstUnTranditionalNode;
    }

    private List<WorkflowNode> findUnTranditionalDoubleOutputNode(List<WorkflowNode> doubleInputNodes, List<WorkflowNode> allDoubleOutputNodes, ArrayList<WorkflowNode> workflowNodes) {
        Iterator<WorkflowNode> iterator = allDoubleOutputNodes.iterator();
        while (iterator.hasNext()){
            WorkflowNode outputNode = iterator.next();
            boolean isUnTranditionalOutputNode = isTranditionalOutputNode(doubleInputNodes, allDoubleOutputNodes, outputNode, false);
            if(isUnTranditionalOutputNode){
                workflowNodes.add(outputNode);
            }
        }
        return workflowNodes;
    }

    private boolean isTranditionalOutputNode(List<WorkflowNode> doubleInputNodes, List<WorkflowNode> allDoubleOutputNodes, WorkflowNode outputNode,boolean isTranditionalNode) {
        if(doubleInputNodes.contains(outputNode)){
            isTranditionalNode = true;
        }
        for(WorkflowNode nextNode : outputNode.getNextNodes()){
            if(!allDoubleOutputNodes.contains(nextNode)){
                isTranditionalNode = isTranditionalOutputNode(doubleInputNodes, allDoubleOutputNodes, nextNode, isTranditionalNode);
            }
        }
        return isTranditionalNode;
    }

    private WorkflowNode getAnotherNextNode(WorkflowNode doubleOutputNode, WorkflowNode anotherNode,WorkflowNode anotherNextNode) {
        if(anotherNode.getPreNodes().contains(doubleOutputNode)){
            anotherNextNode = anotherNode;
        }else{
            for(WorkflowNode node : anotherNode.getPreNodes()){
                anotherNextNode = getAnotherNextNode(doubleOutputNode, node, anotherNextNode);
            }
        }
        return anotherNextNode;
    }

    private Set<WorkflowNode> getAnotherNode(List<WorkflowNode> doubleInputNodes, WorkflowNode doubleOutputNode,Set<WorkflowNode> set) {
        List<WorkflowNode> nextNodes = doubleOutputNode.getNextNodes();
        if(nextNodes.size() == 0){
            set.add(doubleOutputNode);
        }else{
            for(WorkflowNode workflowNode : nextNodes){
                if(workflowNode.getPreNodes().size() == 1 && !doubleInputNodes.contains(workflowNode)){
                    set = getAnotherNode(doubleInputNodes,workflowNode,set);
                }else if(workflowNode.getPreNodes().size() >1 && workflowNode.getId().indexOf("join") != -1){
                    set = getAnotherNode(doubleInputNodes,workflowNode,set);
                }
            }
        }
        return set;
    }

    private Set<WorkflowNode> getNextDoubleInputNode(Set<WorkflowNode> returnNodes, WorkflowNode doubleOutputNode,List<WorkflowNode> doubleInputNodes) {
        List<WorkflowNode> nextNodes = doubleOutputNode.getNextNodes();
        for(WorkflowNode node : nextNodes){
            if(doubleInputNodes.contains(node)){
                returnNodes.add(node);
                nextNodes = new ArrayList<WorkflowNode>();
            }
        }
        for(WorkflowNode node : nextNodes){
            returnNodes = getNextDoubleInputNode(returnNodes, node, doubleInputNodes);
        }
        return returnNodes;
    }

    private boolean isDeepestNode(boolean isDeepest, WorkflowNode doubleOutputNode, List<WorkflowNode> doubleOutputNodes) {
        if(doubleOutputNodes.contains(doubleOutputNode)){
            isDeepest = false;
            return isDeepest;
        }
        for(WorkflowNode node : doubleOutputNode.getNextNodes())
            isDeepestNode(isDeepest,node,doubleOutputNodes);
        return isDeepest;
    }

    private WorkflowNode findJoinDoubleInputNode(WorkflowNode doubleOutputNode) {
        Map<Integer,Set<WorkflowNode>> joinDoubleInputNodeMap = new HashMap<>();
        for(int i=0;i<doubleOutputNode.getNextNodes().size();i++){
            Set<WorkflowNode> workflowNodes = genDoubleInputNodes(new HashSet<WorkflowNode>(), doubleOutputNode.getNextNodes().get(i));
            joinDoubleInputNodeMap.put(i,workflowNodes);
        }
        Map<WorkflowNode,Integer> nodeCountMap = new HashMap<>();
        for(Integer key : joinDoubleInputNodeMap.keySet()){
            for(WorkflowNode node : joinDoubleInputNodeMap.get(key)){
                nodeCountMap.put(node,0);
            }
        }
        for(Integer key : joinDoubleInputNodeMap.keySet()){
            for(WorkflowNode node : joinDoubleInputNodeMap.get(key)){
                nodeCountMap.put(node,nodeCountMap.get(node) + 1);
            }
        }
        List<WorkflowNode> preparedDoubleInputNodes = new ArrayList<>();
        for(WorkflowNode node : nodeCountMap.keySet()){
            if(nodeCountMap.get(node) == 2){
                preparedDoubleInputNodes.add(node);
            }
        }
        if(preparedDoubleInputNodes.size() == 1){
            return preparedDoubleInputNodes.iterator().next();
        }else{
            return genFinalJoinDoubleNode(preparedDoubleInputNodes);
        }
    }

    private WorkflowNode genFinalJoinDoubleNode(List<WorkflowNode> preparedDoubleInputNodes) {
        for(WorkflowNode node : preparedDoubleInputNodes){
            Map<WorkflowNode,Integer> otherJoinInputNodeMap = new HashMap<>();
            for(WorkflowNode workflowNode : preparedDoubleInputNodes){
                if(!node.equals(workflowNode)){
                    otherJoinInputNodeMap.put(workflowNode,0);
                }
            }
            boolean isRightJoinDoubleInputNode = isRightJoinDoubleInputNode(node,otherJoinInputNodeMap);
            if(isRightJoinDoubleInputNode){
                return node;
            }
        }
        return null;
    }

    private boolean isRightJoinDoubleInputNode(WorkflowNode node, Map<WorkflowNode,Integer> otherJoinInputNodeMap) {
        Map<WorkflowNode, Integer> joinDoubleInputNodeMap = getJoinDoubleInputNodeMap(node, otherJoinInputNodeMap);
        boolean isRightJoinDoubleInputNode = true;
        for(WorkflowNode otherNode : joinDoubleInputNodeMap.keySet()){
            if(joinDoubleInputNodeMap.get(otherNode) == 0){
                isRightJoinDoubleInputNode = false;
                break;
            }
        }
        return isRightJoinDoubleInputNode;
    }

    private Map<WorkflowNode,Integer> getJoinDoubleInputNodeMap(WorkflowNode node, Map<WorkflowNode, Integer> otherJoinInputNodeMap) {
        if(otherJoinInputNodeMap.keySet().contains(node)){
            otherJoinInputNodeMap.put(node,otherJoinInputNodeMap.get(node) + 1);
        }
        if(node.getNextNodes().size() > 0){
            getJoinDoubleInputNodeMap(node,otherJoinInputNodeMap);
        }
        return otherJoinInputNodeMap;
    }

    private List<WorkflowNode> getDoubleOutputNodes(List<WorkflowNode> firstNodes) {
        Set<WorkflowNode> doubleOutputNodes = new HashSet<>();
        for(WorkflowNode workflowNode : firstNodes){
            Set<WorkflowNode> returnDoubleOutputNodes = genDoubleOutputNodes(new HashSet<WorkflowNode>(), workflowNode);
            for(WorkflowNode returnNode : returnDoubleOutputNodes){
                doubleOutputNodes.add(returnNode);
            }
        }
        List<WorkflowNode> doubleOutputList = new ArrayList<>(doubleOutputNodes);
        return doubleOutputList;
    }

    private Set<WorkflowNode> genDoubleOutputNodes(HashSet<WorkflowNode> doubleOutputNodeSet, WorkflowNode workflowNode) {
        if(workflowNode.getNextNodes().size() > 1){
            doubleOutputNodeSet.add(workflowNode);
        }
        if(workflowNode.getNextNodes().size() > 0){
            for(WorkflowNode node : workflowNode.getNextNodes()){
                genDoubleOutputNodes(doubleOutputNodeSet, node);
            }
        }
        return doubleOutputNodeSet;
    }

    private List<WorkflowNode> getdoubleInputNodes(List<WorkflowNode> firstNodes) {
        Set<WorkflowNode> doubleInputNodes = new HashSet<>();
        for(WorkflowNode workflowNode : firstNodes){
            Set<WorkflowNode> returnDoubleInputNodes = genDoubleInputNodes(new HashSet<WorkflowNode>(),workflowNode);
            for(WorkflowNode node : returnDoubleInputNodes){
                doubleInputNodes.add(node);
            }
        }
        ArrayList<WorkflowNode> doubleInputNodesList = new ArrayList<>(doubleInputNodes);
        return doubleInputNodesList;
    }

    private Set<WorkflowNode> genDoubleInputNodes(Set<WorkflowNode> doubleWorkflowNodeSet,WorkflowNode workflowNode) {
        if(workflowNode.getPreNodes().size() > 1){
            doubleWorkflowNodeSet.add(workflowNode);
        }

        if(workflowNode.getNextNodes().size()>0){
            for(WorkflowNode node : workflowNode.getNextNodes()){
                genDoubleInputNodes(doubleWorkflowNodeSet,node);
            }
        }
        return doubleWorkflowNodeSet;
    }

    public static Map<String,Object> formatWorkflowNodeAttributeOutputPath(Map<String,Object> formatAttrMap,Map<String,WorkflowNode> nodeMap,WorkflowNode startWorkflowNode,String username,
                                                                          int userId, String flowId,DatabaseConnectService databaseConnectService,DatabaseTypeService databaseTypeService) {
        //避免多条路线时重复添加属性。
        Map<String,Object> currentFormatAttrMap = formatAttrMap;
        List<String> currentList = (List<String>) currentFormatAttrMap.get("formatedIds");
        Map<String,Integer> usedOutputPathCountMap = (Map<String,Integer>) currentFormatAttrMap.get("usedDoubleOutputPathCountMap");
        int blockId = -1;
        if(startWorkflowNode.getId().indexOf("fork") == -1 && startWorkflowNode.getId().indexOf("join") == -1
                && startWorkflowNode.getId().indexOf("start") == -1 && startWorkflowNode.getId().indexOf("end") == -1
                && startWorkflowNode.getId().indexOf("kill") == -1){
            blockId = Integer.parseInt(startWorkflowNode.getAttrs().get("blockID"));
        }
        //模型算子
        List<String> modelIds = Setting.getModelIds();
        //多输出的算子，两个输出都为模型
        List<String> doubleOutputDataIds = Setting.getDoubleOutputDataIds();
        //python和R算子
        List<String> pythonAndRIds = Setting.getPythonAndRIds();
        //四期的已保存的模型算子
        List<String> sparkTrainedModelIds = Setting.getSparkTrainedModelIds();
        //单个shell和python算子输出
        List<String> pyhtonRSingleIds = Setting.getPythonAndRSingleIds();

        if(blockId !=1 && blockId != 3 && blockId != 56 && blockId != 55 && blockId != -1){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "delimiter")){
                startWorkflowNode.getAttrs().put("delimiter",",");
                currentList.add(startWorkflowNode.getId() + "_" + "delimiter");
            }
        }

        //添加临时输出。
        if(blockId != 3 && blockId != 4 && blockId != 54 && blockId != 57 && blockId != -1){
            boolean hasShellAndRNode = false;
            List<String> targetConnectTypes = startWorkflowNode.getTargetConnectTypes();
            for(String targetConnectType : targetConnectTypes){
                WorkflowNode targetNode = nodeMap.get(targetConnectType.split("_")[0]);
                if(pythonAndRIds.contains(targetNode.getAttrs().get("blockID")) || pyhtonRSingleIds.contains(targetNode.getAttrs().get("blockID"))){
                    hasShellAndRNode = true;
                    break;
                }
            }

            //如果下一个算子是python和R的算子，分两种情况，一种情况是python和R算子前面只连一个接口
            if(hasShellAndRNode){
                //加到权限为777的公共路径里面。
                if(!currentList.contains(startWorkflowNode.getId() + "_" + "tmpOutput")){
                    if(doubleOutputDataIds.contains(blockId + "")){
                        //两个输出的情况，两个都是CSV的情况
                        String fullDataPath1 = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_DATA.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID())
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION_FIRST,fullDataPath1);

                        String fullDataPath2 = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_DATA.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID())
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION_SECOND,fullDataPath2);
                    }else if(modelIds.contains(blockId + "")){
                        //添加模型临时输出路径
                        String fullPathModel = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_MODEL.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullPathModel);
                    }else if(sparkTrainedModelIds.contains(blockId + "")){
                        //对已训练的模型算子，如果后面有R或者PYTHON算子，添加公共输出路径。
                        String fullPathModel = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_MODEL.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullPathModel);
                    }else if(pythonAndRIds.contains(blockId + "" )){
                        //如果这个算子正好是R或者python算子
                        String fullDataPath = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_DATA.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID())
                                .append(".csv").toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_DATA,fullDataPath);

                        //添加临时输出模型路径
                        String fullModelPath = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_MODEL.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_MODEL,fullModelPath);
                    }else if(pyhtonRSingleIds.contains(blockId + "")){
                        //如果是单个的sehll算子
                        String fullDataPath = new StringBuffer(MailAuthorSetting.PYTHON_R_OUTPUT_PATH)
                                .append("data").append("/").append(UUID.randomUUID().toString()).append(".csv").toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullDataPath);
                    }else{
                        //其他情况，只有一个输出的，并且只能输出数据的。
                        String fullDataPath = new StringBuffer(MailAuthorSetting.PYTHON_R_INPUT_PATH)
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();

                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullDataPath);
                    }
                    currentList.add(startWorkflowNode.getId() + "_" + "tmpOutput");
                }
            }else{
                //对于后面不是python或者R算子的情况。
                if(!currentList.contains(startWorkflowNode.getId() + "_" + "tmpOutput")){
                    if(doubleOutputDataIds.contains(blockId + "")){
                        String tmpDataDir1 = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                                .append("/")
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_DATA.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();

                        String fullDataPath1 = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                                .append(tmpDataDir1)
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION_FIRST,fullDataPath1);

                        String tmpDataDir2 = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                                .append("/")
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_DATA.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();

                        String fullDataPath2 = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                                .append(tmpDataDir2)
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION_SECOND,fullDataPath2);
                    }else if(modelIds.contains(blockId + "")){
                        //添加模型临时输出路径
                        String tmpDir = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                                .append("/")
                                .append(flowId)
                                .append("/")
                                .append(startWorkflowNode.getId()).append("_")
                                .append(Constants.TMP_OUTPUT_MODEL.toLowerCase())
                                .append("_")
                                .append(UUID.randomUUID().toString())
                                .toString();
                        String fullPathModel = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                                .append(tmpDir)
                                .toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullPathModel);
                    }else if(blockId == 55){
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,startWorkflowNode.getAttrs().get(Constants.TMP_INPUT_FUNCTION));
                        currentList.add(startWorkflowNode.getId() + "_" + "tmpOutput");
                    }else if(pythonAndRIds.contains(blockId + "")){
                        String fullDataPath = new StringBuffer(MailAuthorSetting.PYTHON_R_OUTPUT_PATH)
                                .append("data").append("/").append(UUID.randomUUID().toString()).append(".csv").toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_DATA,fullDataPath);

                        //添加临时输出模型路径
                        String fullModelPath = new StringBuffer(MailAuthorSetting.PYTHON_R_OUTPUT_PATH)
                                .append("model").append("/").append(UUID.randomUUID().toString()).append(".csv").toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_MODEL,fullModelPath);

                        //添加临时图片路径
                        String fullImgPath = new StringBuffer(MailAuthorSetting.PYTHON_R_OUTPUT_PATH)
                                .append("img").append("/").append(flowId).append("_").append(startWorkflowNode.getId()).toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_IMAGE,fullImgPath);
                    }else if(pyhtonRSingleIds.contains(blockId + "")){
                        //如果是单个的sehll算子
                        String fullDataPath = new StringBuffer(MailAuthorSetting.PYTHON_R_OUTPUT_PATH)
                                .append("data").append("/").append(UUID.randomUUID().toString()).append(".csv").toString();
                        startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullDataPath);
                    }else{
                        if(blockId == 139){
                            startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,HADOOP_HDFS_URL+startWorkflowNode.getAttrs().get(Constants.TMP_INPUT_FUNCTION));
                        }else{
                            String tmpDataDir = new StringBuffer(username).append("/").append(Constants.WORKSPACES_SUFFIX)
                                    .append("/")
                                    .append(flowId)
                                    .append("/")
                                    .append(startWorkflowNode.getId()).append("_")
                                    .append(Constants.TMP_OUTPUT.toLowerCase())
                                    .append("_")
                                    .append(UUID.randomUUID().toString())
                                    .append("_")
                                    .append(System.currentTimeMillis())
                                    .toString();

                            String fullDataPath = new StringBuffer(MailAuthorSetting.HDFS_PATH_FEIX)
                                    .append(tmpDataDir)
                                    .toString();
                            startWorkflowNode.getAttrs().put(Constants.TMP_OUTPUT_FUNCTION,fullDataPath);
                        }
                    }
                    currentList.add(startWorkflowNode.getId() + "_" + "tmpOutput");
                }
            }
        }


        if(blockId == 3 || blockId == 4 || blockId == 57){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "outputPath")){
                String fileName = startWorkflowNode.getAttrs().get("filename");
                fileName = fileName + "-" + System.currentTimeMillis() + "-" + UUID.randomUUID() ;
                startWorkflowNode.getAttrs().put("inputName",fileName);
                String outputPath = startWorkflowNode.getAttrs().get("outputPath");
                outputPath = new StringBuffer(MailAuthorSetting.HADOOP_NAMENODE)
                        .append(outputPath)
                        .append("/").append(fileName).toString();
                startWorkflowNode.getAttrs().put("outputPath",outputPath);
                currentList.add(startWorkflowNode.getId() + "_" + "outputPath");
            }
        }

        currentFormatAttrMap.put("usedDoubleOutputPathCountMap",usedOutputPathCountMap);
        currentFormatAttrMap.put("formatedIds",currentList);

        if(startWorkflowNode.getNextNodes().size() > 0){
            for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                currentFormatAttrMap = formatWorkflowNodeAttributeOutputPath(currentFormatAttrMap,nodeMap,workflowNode,username,userId,flowId,databaseConnectService,databaseTypeService);
            }
        }
        return currentFormatAttrMap;
    }

    public static Map<String,Object> formatWorkflowNodeAttributeInputPath(Map<String,Object> formatAttrMap,Map<String,WorkflowNode> nodeMap,WorkflowNode startWorkflowNode,String username,
                                                                           int userId, String flowId,DatabaseConnectService databaseConnectService,DatabaseTypeService databaseTypeService) {
        //避免多条路线时重复添加属性。
        Map<String,Object> currentFormatAttrMap = formatAttrMap;
        List<String> currentList = (List<String>) currentFormatAttrMap.get("formatedIds");
        Map<String,Integer> usedOutputPathCountMap = (Map<String,Integer>) currentFormatAttrMap.get("usedDoubleOutputPathCountMap");
        int blockId = -1;
        if(startWorkflowNode.getId().indexOf("fork") == -1 && startWorkflowNode.getId().indexOf("join") == -1
                && startWorkflowNode.getId().indexOf("start") == -1 && startWorkflowNode.getId().indexOf("end") == -1
                && startWorkflowNode.getId().indexOf("kill") == -1){
            blockId = Integer.parseInt(startWorkflowNode.getAttrs().get("blockID"));
        }
        //3期spark的算子
        List<String> applyIds = Setting.getApplyIds();
        //多输出的算子，一个输出为模型，一个输出为CSV
        List<String> doubleOutputDataModelIds = Setting.getDoubleOutputDataModelIds();
        //多输出的算子，两个输出都为模型
        List<String> doubleOutputDataIds = Setting.getDoubleOutputDataIds();
        //三期的数据集合并算子
        List<String> ordinaryApplyIds = Setting.getOrdinaryApplyIds();
        //python和R算子
        List<String> pythonAndRIds = Setting.getPythonAndRIds();
        //四期的数据集合并算子
        List<String> ordinaryApplyV4Ids = Setting.getOrdinaryApplyV4Ids();
        //四期的预测类算子
        List<String> applyV4Ids = Setting.getApplyV4Ids();
        //单个shell和python算子输出
        List<String> pyhtonRSingleIds = Setting.getPythonAndRSingleIds();
        //有transform的算子
        List<String> sparkV4SingleOutputTransformIds = Setting.getSparkV4SingleOutputTransformIds();

        if(blockId !=1 && blockId != 3 && blockId != 56 && blockId != 55 && blockId != 139 && blockId != -1){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "delimiter")){
                startWorkflowNode.getAttrs().put("delimiter",",");
                currentList.add(startWorkflowNode.getId() + "_" + "delimiter");
            }
        }

        if(blockId != 1 && blockId != 2 && blockId != 53 && blockId != 56 && blockId != 55 && blockId != 139 && blockId != -1){
            //对于startNode节点是输入两个CSV的情况
            if(ordinaryApplyIds.contains(blockId + "") || ordinaryApplyV4Ids.contains(blockId + "")){
                if(!currentList.contains(startWorkflowNode.getId() + "_" + "tmpInput")){
                    List<String> outputPaths = new ArrayList<>();
                    String []outputPathArr = new String[2];
                    for(String sourceConnectModelType : startWorkflowNode.getSourceConnectTypes()){
                        String sourceId = sourceConnectModelType.split("_")[0];
                        WorkflowNode sourceNode = nodeMap.get(sourceId);
                        if(doubleOutputDataIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            if(usedOutputPathCountMap.get(sourceNode.getId()) == 0){
                                List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                                for(String targetPosition : targetConnectPositions){
                                    String targetStatModel = targetPosition.split("_")[0];
                                    if(targetStatModel.equals(startWorkflowNode.getId())){
                                        if("Left".equals(targetPosition.split("_")[1])){
                                            outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST);
                                        }else{
                                            outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST);
                                        }
                                    }
                                }
                                usedOutputPathCountMap.put(sourceNode.getId(),usedOutputPathCountMap.get(sourceNode.getId())+1);
                            }else{
                                List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                                for(String targetPosition : targetConnectPositions){
                                    String targetStatModel = targetPosition.split("_")[0];
                                    if(targetStatModel.equals(startWorkflowNode.getId())){
                                        if("Left".equals(targetPosition.split("_")[1])){
                                            outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST);
                                        }else{
                                            outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST);
                                        }
                                    }
                                }
                            }
                        }else if(pythonAndRIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                            for(String targetPosition : targetConnectPositions){
                                String targetStatModel = targetPosition.split("_")[0];
                                if(targetStatModel.equals(startWorkflowNode.getId())){
                                    if("Left".equals(targetPosition.split("_")[1])){
                                        outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_DATA);
                                    }else{
                                        outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_DATA);
                                    }
                                }
                            }
                        }else if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                            for(String targetPosition : targetConnectPositions){
                                String targetStatModel = targetPosition.split("_")[0];
                                if(targetStatModel.equals(startWorkflowNode.getId())){
                                    if("Left".equals(targetPosition.split("_")[1])){
                                        outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }else{
                                        outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }
                                }
                            }
                        } else if(sparkV4SingleOutputTransformIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                            for(String targetPosition : targetConnectPositions){
                                String targetStatModel = targetPosition.split("_")[0];
                                if(targetStatModel.equals(startWorkflowNode.getId())){
                                    if("Left".equals(targetPosition.split("_")[1])){
                                        outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }else{
                                        outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }
                                }
                            }
                        }else{
                            List<String> targetConnectPositions = sourceNode.getTargetConnectPosition();
                            for(String targetPosition : targetConnectPositions){
                                String targetStatModel = targetPosition.split("_")[0];
                                if(targetStatModel.equals(startWorkflowNode.getId())){
                                    if("Left".equals(targetPosition.split("_")[1])){
                                        outputPathArr[0] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }else{
                                        outputPathArr[1] = sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION);
                                    }
                                }
                            }
                        }
                    }
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION_FIRST,outputPathArr[0]);
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION_SECOND,outputPathArr[1]);
                    currentList.add(startWorkflowNode.getId() + "_" + "tmpInput");
                }
            }else if(applyIds.contains(blockId + "") || applyV4Ids.contains(blockId + "")){
                if(!currentList.contains(startWorkflowNode.getId() + "_" + "tmpInput")){
                    for(String sourceConnectModelType : startWorkflowNode.getSourceConnectTypes()){
                        String sourceId = sourceConnectModelType.split("_")[0];
                        String sourceConnectType = sourceConnectModelType.split("_")[1];
                        WorkflowNode sourceNode = nodeMap.get(sourceId);
                        if(doubleOutputDataIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            if("Left".equals(startWorkflowNode.getSourceConnectPostion().get(0))){
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_DATA,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST));
                            }else{
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_DATA,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_SECOND));
                            }

                        }else if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "") && "Circle".equals(sourceConnectType)){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_DATA,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/transformed/data");
                        }else if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "") && "Square".equals(sourceConnectType)){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_BLOCK,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/model");
                        }else if(sparkV4SingleOutputTransformIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_DATA,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                        }else{
                            if("Circle".equals(sourceConnectType)){
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_DATA,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                            }else{
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_BLOCK,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/model");
                            }
                        }
                    }
                    currentList.add(startWorkflowNode.getId() + "_" + "tmpInput");
                }
            }else if(pythonAndRIds.contains(blockId + "") || pyhtonRSingleIds.contains(blockId + "")){
                String sourceId = startWorkflowNode.getSourceConnectTypes().get(0).split("_")[0];
                WorkflowNode sourceNode = nodeMap.get(sourceId);
                if(doubleOutputDataIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                    if("Left".equals(startWorkflowNode.getSourceConnectPostion().get(0))){
                        startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST) + "/part-m-00000");
                    }else{
                        startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_SECOND) + "/part-m-00000");
                    }
                }else if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/transformed/data/part-m-00000");
                }else if(pythonAndRIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_DATA));
                }else if(pyhtonRSingleIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                }else if(sparkV4SingleOutputTransformIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/transformed/part-m-00000");
                }else{
                    startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/part-m-00000");
                }
            }else{
                if(!currentList.contains(startWorkflowNode.getId() + "_" + "tmpInput")){
                    String sourceId = startWorkflowNode.getSourceConnectTypes().get(0).split("_")[0];
                    String sourceConnectType = startWorkflowNode.getSourceConnectTypes().get(0).split("_")[1];
                    WorkflowNode sourceNode = nodeMap.get(sourceId);
                    if("Circle".equals(sourceConnectType)){
                        if(doubleOutputDataIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            if("Left".equals(startWorkflowNode.getSourceConnectPostion().get(0))){
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_FIRST));
                            }else{
                                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION_SECOND));
                            }
                        }else if(pythonAndRIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_DATA));
                        }else if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/transformed/data");
                        }else if(sparkV4SingleOutputTransformIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                        }else{
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                        }
                    }else{
                        if(doubleOutputDataModelIds.contains(sourceNode.getAttrs().get("blockID") + "")){
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION) + "/model");
                        }else{
                            startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,sourceNode.getAttrs().get(Constants.TMP_OUTPUT_FUNCTION));
                        }
                    }
                    currentList.add(startWorkflowNode.getId() + "_" + "tmpInput");
                }
            }
        }

        if(blockId == 1 || blockId == 2 || blockId == 56|| blockId == 139){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "inputPath")){
                String inputPath = startWorkflowNode.getAttrs().get("inputPath");
                inputPath = new StringBuffer(MailAuthorSetting.HADOOP_NAMENODE).append(inputPath).toString();
                startWorkflowNode.getAttrs().put("inputPath",inputPath);
                currentList.add(startWorkflowNode.getId() + "_" + "inputPath");
            }
        }

        //删除pythonR算子的前台代码
        if(blockId == 83 || blockId == 84){
            startWorkflowNode.getAttrs().remove("textarea");
        }

        if(blockId == 94 || blockId == 95){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                String fieldOptions = startWorkflowNode.getAttrs().get("fieldOption");
                if(fieldOptions != null){
                    String overwrite = startWorkflowNode.getAttrs().get("overwrite");
                    JSONObject json = JSONObject.fromObject(fieldOptions);
                    if(blockId == 95){
                        if(StringUtils.hasText(json.getString("listName")) && StringUtils.hasText(json.getString("newType"))){
                            startWorkflowNode.getAttrs().put("fieldName",json.getString("listName"));
                            startWorkflowNode.getAttrs().put("convertType",json.getString("newType"));
                        }
                        startWorkflowNode.getAttrs().put("overwrite",overwrite);
                    }else if(blockId == 94){
                        startWorkflowNode.getAttrs().put("oldName",json.getString("oldName"));
                        startWorkflowNode.getAttrs().put("newName",json.getString("newName"));
                    }
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        if(blockId == 102){
            if("n".equals(startWorkflowNode.getAttrs().get("featureSubsetStrategy"))){
                startWorkflowNode.getAttrs().put("featureSubsetStrategy",startWorkflowNode.getAttrs().get("featureSubsetStrategyN"));
                startWorkflowNode.getAttrs().remove("featureSubsetStrategyN");
            }else{
                startWorkflowNode.getAttrs().remove("featureSubsetStrategyN");
            }
        }

        if(blockId == 76){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "header")){
                startWorkflowNode.getAttrs().put("header","true");
            }
            currentList.add(startWorkflowNode.getId() + "_" + "header");
        }

        if(blockId == 77 || blockId == 78 || blockId == 79){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                String originalLabel = startWorkflowNode.getAttrs().get("originalLabel");
                String predictedLabel = startWorkflowNode.getAttrs().get("predictLabel");
                startWorkflowNode.getAttrs().remove("originalLabel");
                startWorkflowNode.getAttrs().remove("predictLabel");
                startWorkflowNode.getAttrs().put("usedFields",predictedLabel + "," + originalLabel);
                if(blockId == 77){
                    startWorkflowNode.getAttrs().put("modelname","performancebinaryclassification");
                }else if(blockId == 79){
                    startWorkflowNode.getAttrs().put("modelname","performanceregression");
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        if(Setting.getContainsUsedFieldsIds().contains(blockId + "")){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "usedFields")){
                if(startWorkflowNode.getAttrs().get("usedFields") == null || !StringUtils.hasText(startWorkflowNode.getAttrs().get("usedFields"))){
                    startWorkflowNode.getAttrs().remove("usedFields");
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "usedFields");
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "labelCol")){
                if(startWorkflowNode.getAttrs().get("labelCol") == null || !StringUtils.hasText(startWorkflowNode.getAttrs().get("labelCol"))){
                    startWorkflowNode.getAttrs().remove("labelCol");
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "labelCol");
        }

        if(blockId == 86){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                if(Integer.parseInt(startWorkflowNode.getAttrs().get("splitType")) == 0 ){
                    startWorkflowNode.getAttrs().put("splitValue",startWorkflowNode.getAttrs().get("splitValue").split(",")[0]);
                }else{
                    startWorkflowNode.getAttrs().put("splitValue",startWorkflowNode.getAttrs().get("splitValue").split(",")[1]);
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        if(blockId == 96){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                if(startWorkflowNode.getAttrs().get("fields1") == null || !StringUtils.hasText(startWorkflowNode.getAttrs().get("fields1"))){
                    startWorkflowNode.getAttrs().remove("fields1");
                }
                if(startWorkflowNode.getAttrs().get("fields2") == null || !StringUtils.hasText(startWorkflowNode.getAttrs().get("fields2"))){
                    startWorkflowNode.getAttrs().remove("fields2");
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        if(blockId == 124){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                if("gaussian".equals(startWorkflowNode.getAttrs().get("disFunctionFamily"))){
                    startWorkflowNode.getAttrs().put("likeFunction",startWorkflowNode.getAttrs().get("gaussian"));
                }else if("binomial".equals(startWorkflowNode.getAttrs().get("disFunctionFamily"))){
                    startWorkflowNode.getAttrs().put("likeFunction",startWorkflowNode.getAttrs().get("binomial"));
                }else if("poisson".equals(startWorkflowNode.getAttrs().get("disFunctionFamily"))){
                    startWorkflowNode.getAttrs().put("likeFunction",startWorkflowNode.getAttrs().get("poisson"));
                }else{
                    startWorkflowNode.getAttrs().put("likeFunction",startWorkflowNode.getAttrs().get("gamma"));
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        if(blockId == 125){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                String []options = startWorkflowNode.getAttrs().get("option").split(";");
                System.out.println(options);
                String finalOption = "";
                for(String option : options){
                    String currentOpt = "";
                    Map<String,String> attrs = new HashMap<>();
                    for(String attr : option.split(",")){
                        attrs.put(attr.split("\\|")[0],attr.split("\\|")[1]);
                    }
                    //将各种类型的参数统一设置为UpperThreshold，LowerThreshold以及filterType的value
                    if("<=>".equals(attrs.get("filterFun")) || "∉".equals(attrs.get("filterFun"))){
                        if("quantile".equals(attrs.get("UpperThreshold")) || "customFilter".equals(attrs.get("UpperThreshold"))){
                            attrs.put("UpperThreshold",attrs.get("UpperThreshold") + "=" + attrs.get("UpperThresholdVal"));
                        }
                        if("quantile".equals(attrs.get("LowerThreshold")) || "customFilter".equals(attrs.get("LowerThreshold"))){
                            attrs.put("LowerThreshold",attrs.get("LowerThreshold") + "=" + attrs.get("LowerThresholdVal"));
                        }
                    }else if(">=".equals(attrs.get("filterFun")) || "<=".equals(attrs.get("filterFun")) || "=".equals(attrs.get("filterFun"))){
                        if("quantile".equals(attrs.get("filterType")) || "customFilter".equals(attrs.get("filterType"))){
                            attrs.put("filterType",attrs.get("filterType") + "=" + attrs.get("threshold"));
                        }else if("valuepath".equals(attrs.get("filterType"))){
                            attrs.put("filterType",attrs.get("filterType") + "=" + startWorkflowNode.getAttrs().get(Constants.TMP_INPUT_FUNCTION_SECOND));
                        }
                    }

                    if("<=>".equals(attrs.get("filterFun"))){
                        currentOpt += attrs.get("labelCol") + "," + ">=" + attrs.get("LowerThreshold") + ";"
                                + attrs.get("labelCol") + "," + "<=" + attrs.get("UpperThreshold") + ";";
                    }else if("∉".equals(attrs.get("filterFun"))){
                        currentOpt += attrs.get("labelCol") + "," + ">=" + "," + attrs.get("UpperThreshold") + ";"
                                + attrs.get("labelCol") + "," + "<=" + "," + attrs.get("LowerThreshold") + ";";
                    }else if(">=".equals(attrs.get("filterFun")) || "<=".equals(attrs.get("filterFun")) || "=".equals(attrs.get("filterFun"))){
                        currentOpt += attrs.get("labelCol") + "," + attrs.get("filterFun") + "," + attrs.get("filterType") + ";";
                    }
                    finalOption += currentOpt;
                }
                startWorkflowNode.getAttrs().put("option",finalOption.substring(0,finalOption.length() - 1));
                startWorkflowNode.getAttrs().put(Constants.TMP_INPUT_FUNCTION,startWorkflowNode.getAttrs().get(Constants.TMP_INPUT_FUNCTION_FIRST));
                startWorkflowNode.getAttrs().remove(Constants.TMP_INPUT_FUNCTION_FIRST);
                startWorkflowNode.getAttrs().remove(Constants.TMP_INPUT_FUNCTION_SECOND);
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        //将数据库的附加属性添加到节点的属性界面中
        if(blockId == 53 || blockId == 54 || blockId == 139){
            if(!currentList.contains(startWorkflowNode.getId() + "_" + "otherAttrs")){
                Set<String> keys = startWorkflowNode.getAttrs().keySet();
                if(keys.contains("sqlStatment")){
                    String sql = startWorkflowNode.getAttrs().get("sqlStatment").split("\\|")[0];
                    startWorkflowNode.getAttrs().put("sqlStatment",sql);
                }
                if(keys.contains("dbLinkName")){
                    String linkName = startWorkflowNode.getAttrs().get("dbLinkName").split("\\|")[1];
                    startWorkflowNode.getAttrs().put("dbLinkName",linkName);
                    DatabaseConnect databaseConnect = new DatabaseConnect();
                    databaseConnect.setDbConUserId(userId);
                    databaseConnect.setDbConName(linkName);
                    DatabaseConnect connect = databaseConnectService.findByUserIdAndConnName(databaseConnect);
                    String dbTypeName = databaseTypeService.selectByPrimaryKey(connect.getDbConTypeId()).getDbTypeName();
                    startWorkflowNode.getAttrs().put("hostIp",connect.getDbConIp());
                    startWorkflowNode.getAttrs().put("hostPort",connect.getDbConPort().toString());
                    startWorkflowNode.getAttrs().put("databaseName",connect.getDbConDbname());
                    startWorkflowNode.getAttrs().put("databaseType",dbTypeName);
                    startWorkflowNode.getAttrs().put("userName",connect.getDbConUser());
                    startWorkflowNode.getAttrs().put("password",connect.getDbConPassword());
                }

                if(keys.contains("tableName")){
                    String tableName = startWorkflowNode.getAttrs().get("tableName").split("\\|")[1];
                    startWorkflowNode.getAttrs().put("tableName",tableName);
                }

                if(keys.contains("writeMode")){
                    String writeMode = startWorkflowNode.getAttrs().get("writeMode").split("\\|")[1];
                    startWorkflowNode.getAttrs().put("writeMode",writeMode);
                }
            }
            currentList.add(startWorkflowNode.getId() + "_" + "otherAttrs");
        }

        currentFormatAttrMap.put("usedDoubleOutputPathCountMap",usedOutputPathCountMap);
        currentFormatAttrMap.put("formatedIds",currentList);

        if(startWorkflowNode.getNextNodes().size() > 0){
            for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                currentFormatAttrMap = formatWorkflowNodeAttributeInputPath(currentFormatAttrMap,nodeMap,workflowNode,username,userId,flowId,databaseConnectService,databaseTypeService);
            }
        }
        return currentFormatAttrMap;
    }

    public static List<WorkflowNode> addFuctionToStatModel(WorkflowNode startWorkflowNode,List<WorkflowNode> list) {
        List<WorkflowNode> functionAddedList = list;
        if(startWorkflowNode.getId().indexOf("start") == -1 && startWorkflowNode.getId().indexOf("fork") == -1
                && startWorkflowNode.getId().indexOf("join") == -1 && startWorkflowNode.getId().indexOf("end") == -1 ){
            boolean isFunctionAdded = false;
            if(functionAddedList.contains(startWorkflowNode)){
                isFunctionAdded = true;
            }
            if(!isFunctionAdded){
                startWorkflowNode.setId(startWorkflowNode.getId() + "_" + startWorkflowNode.getAttrs().get("function"));
                functionAddedList.add(startWorkflowNode);
            }
        }

        if(startWorkflowNode.getNextNodes().size() > 0){
            for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                functionAddedList = addFuctionToStatModel(workflowNode, functionAddedList);
            }
        }
        return functionAddedList;
    }

    public Map<String,WorkflowNode> getNodeMap(Map<String,WorkflowNode> map,WorkflowNode startWorkflowNode) {
        Map<String,WorkflowNode> currentMap = map;
        currentMap.put(startWorkflowNode.getId(),startWorkflowNode);
        for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()) {
            currentMap = getNodeMap(currentMap, workflowNode);
        }
        return currentMap;
    }

    public static Map<String,Integer> getUsedDoubleOutputPathCountMap(Map<String, WorkflowNode> nodeMap) {
        List<String> doubleOutputDataIds = Setting.getDoubleOutputDataIds();
        Map<String,Integer> usedOutputPathCountMap = new HashMap<>();
        for(String key : nodeMap.keySet()){
            WorkflowNode workflowNode = nodeMap.get(key);
            if(doubleOutputDataIds.contains(workflowNode.getAttrs().get("blockID") + "")){
                usedOutputPathCountMap.put(key,0);
            }
        }
        return usedOutputPathCountMap;
    }

    /**
     * 格式化model
     * @param model
     * @return
     */
    public static Model updateModel(Model model){
        //数据合并算子
        if(model.getId() == 85){
            if("0".equals(model.getData().get(1).getConfigSelectVal())){
                model.getData().get(0).setMvalue("etl_merge_col");
            }
        }
        //空值过滤算子
        if(model.getId() == 88 || model.getId() ==89){
            for (ModelAttribute modelAttribute : model.getData()) {
                if ("noneValues".equals(modelAttribute.getMattribute())){
                    String[] mValueStr = modelAttribute.getMvalue().split("\\|");
                    if (mValueStr.length==1){
//                                modelAttribute.setMattribute(null);
                        modelAttribute.setMvalue(null);
                    }else{
                        modelAttribute.setMvalue(mValueStr[1]);
                    }
                }
                if("usedFields".equals(modelAttribute.getMattribute()) && StringUtils.isEmpty(modelAttribute.getMvalue())){
                    modelAttribute.setMvalue(null);
                }
            }
        }
        //线性回归算子
        if (model.getId() == 70){
            for(ModelAttribute modelAttribute : model.getData()){
                if("elasticNetParam".equals(modelAttribute.getMattribute())){
                    if("Lasso".equals(modelAttribute.getConfigSelectVal())){
                        modelAttribute.setConfigSelectVal("1");
                    }else if("Ridge".equals(modelAttribute.getMvalue())){
                        modelAttribute.setConfigSelectVal("0");
                    }
                }
            }
        }
        //单列变换算子
        if (model.getId() == 97) {
            for (ModelAttribute modelAttribute : model.getData()) {
                //前台选择changeB的时候,实际调用多列组合变换这个算子
                if ("select".equals(modelAttribute.getMattribute()) && "false".equals(modelAttribute.getMvalue())) {
                    ModelAttribute modelAttribute2 = new ModelAttribute();
                    modelAttribute2.setMattribute("operands");
                    String operandsStr = null;
                    for (ModelAttribute modelAttribute1 : model.getData()) {
                        //修改调用的方法
                        if ("function".equals(modelAttribute1.getMattribute())) {
                            modelAttribute1.setMvalue("binary_operator");
                            continue;
                        }
                        //修改参数名为operator
                        if ("changeB".equals(modelAttribute1.getMattribute())) {
                            modelAttribute1.setMattribute("operator");
                            continue;
                        }
                        if ("usedFields".equals(modelAttribute1.getMattribute())) {
                            operandsStr = "field:" + modelAttribute1.getMvalue();
                            continue;
                        }
                        if ("dataItem".equals(modelAttribute1.getMattribute())) {
                            operandsStr = operandsStr + ",value:" + modelAttribute1.getMvalue();
                        }
                    }
                    //设置新的参数operandsStr
                    modelAttribute2.setMvalue(operandsStr);
                    model.getData().add(modelAttribute2);
                    break;
                } else if ("select".equals(modelAttribute.getMattribute()) && "true".equals(modelAttribute.getMvalue())) {  //前台选changeA的时候
                    for (ModelAttribute modelAttribute1 : model.getData()) {
                        //选择changeA里的Pow和Nthroot这两个操作符，调用多列组合变换算子
                        if ("pow".equals(modelAttribute1.getMvalue()) || "nthroot".equals(modelAttribute1.getMvalue())) {
                            ModelAttribute modelAttribute2 = new ModelAttribute();
                            modelAttribute2.setMattribute("operands");
                            String operandsStr = null;
                            for (ModelAttribute modelAttribute3 : model.getData()) {
                                if ("function".equals(modelAttribute3.getMattribute())) {
                                    modelAttribute3.setMvalue("binary_operator");
                                    continue;
                                }
                                if ("usedFields".equals(modelAttribute3.getMattribute())) {
                                    operandsStr = "field:" + modelAttribute3.getMvalue();
                                    continue;
                                }
                                if ("dataItem".equals(modelAttribute3.getMattribute())) {
                                    operandsStr = operandsStr + ",value:" + modelAttribute3.getMvalue();
                                }
                            }
                            modelAttribute2.setMvalue(operandsStr);
                            model.getData().add(modelAttribute2);
                            modelAttribute1.setMattribute("operator");
                            break;
                        }
                        if ("changeA".equals(modelAttribute1.getMattribute())) {
                            modelAttribute1.setMattribute("operator");
                            break;
                        }
                    }
                    break;
                }
            }
        }
        //多列组合变换算子
        if (model.getId()==98){
            ModelAttribute modelAttribute2 = new ModelAttribute();
            modelAttribute2.setMattribute("operands");
            String operandsStr = null;
            for (ModelAttribute modelAttribute:model.getData()){
                if ("field1".equals(modelAttribute.getMattribute())) {
                    operandsStr = "field:"+modelAttribute.getMvalue();
                    continue;
                }
                if("field2".equals(modelAttribute.getMattribute())){
                    operandsStr = operandsStr + "," + "field:"+modelAttribute.getMvalue();
                }
            }
            modelAttribute2.setMvalue(operandsStr);
            model.getData().add(modelAttribute2);
        }
        //多分类评估
        if (model.getId()==129){
            ModelAttribute modelAttribute = new ModelAttribute();
            modelAttribute.setMattribute("modelname");
            modelAttribute.setMvalue("performancemulticlassification");
            model.getData().add(modelAttribute);
        }
        //二分类逻辑回归
        if (model.getId() == 132){
            for(ModelAttribute modelAttribute : model.getData()){
                if("regularizers".equals(modelAttribute.getMattribute())){
                    if("Lasso".equals(modelAttribute.getConfigSelectVal())){
                        modelAttribute.setConfigSelectVal("1.0");
                    }else if("Ridge".equals(modelAttribute.getMvalue())){
                        modelAttribute.setConfigSelectVal("0");
                    }
                }
            }
        }

        return model;
    }


}















