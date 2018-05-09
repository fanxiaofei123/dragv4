package org.com.drag.web.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.oozie.client.OozieClient;
import org.com.drag.common.util.MailAuthorSetting;
import org.com.drag.common.util.Setting;
import org.com.drag.model.*;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * Created by cdyoue on 2016/12/3.
 */
public class WorkFlowXmlUtils {

    static Logger logger = Logger.getLogger(WorkFlowXmlUtils.class);
    public static synchronized Properties buildXml(ModelXmlInfo modelXmlInfo, String path, String appPath, String flowName, String username) {
        logger.debug("开始创建xml");


        List<Model> models = modelXmlInfo.getModels();
        Forx forx = modelXmlInfo.getForx();

        List<Join> joins = modelXmlInfo.getJoins();

        Properties  xmlProperties= new Properties();
        xmlProperties.setProperty(OozieClient.APP_PATH, appPath);
        xmlProperties.setProperty("oozie.action.sharelib.for.spark", "spark2");
        xmlProperties.setProperty("nameNode", MailAuthorSetting.HADOOP_NAMENODE);//注意：此处为nameNode的地址
        xmlProperties.setProperty("jobTracker", MailAuthorSetting.HADOOP_JOBTRACKER);//注意：此处为resourcemanager的地址。HW中为yarn.resourcemanager.address=8050的值
        xmlProperties.setProperty("mapreduce.job.user.name", username);
        xmlProperties.setProperty("mapreduce.job.user.name", "hy_v4");
        xmlProperties.setProperty("queueName", "default");
//        xmlProperties.setProperty("user.name", username);
//        xmlProperties.setProperty(OozieClient.LIBPATH, MailAuthorSetting.OOZIECLIENT_LIBPATH1);
       xmlProperties.setProperty(OozieClient.LIBPATH, MailAuthorSetting.OOZIECLIENT_LIBPATH_V4);
//        xmlProperties.setProperty(OozieClient.LIBPATH,MailAuthorSetting.OOZIECLIENT_LIBPATH_V4_KAIFA);
//        xmlProperties.setProperty("spark.yarn.jars", "*.jar");

        // 使用DocumentHelper类创建一个Document对象
        // 添加根节点bookstore
        Element bookStore = DocumentHelper.createElement("workflow-app");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date= df.format(new Date());
        bookStore.addAttribute("name", "workflow-"+"flowName" + "_" + date);
        Namespace xmlns = DocumentHelper.createNamespace("xmlns", "uri:oozie:workflow:0.2");
        bookStore.add(xmlns);
        //创建start节点
        Element start = bookStore.addElement("start");

        if(forx != null){
            logger.info("创建fork");
            start.addAttribute("to",forx.getBlockId());
            Element forkEle = bookStore.addElement("fork");
            forkEle.addAttribute("name", forx.getBlockId());
            List<String> targetId = forx.getTargetId();
            for (String s : targetId) {
                Element pathEle = forkEle.addElement("path");
                pathEle.addAttribute("start", s);
            }
        }else {
            start.addAttribute("to", models.get(0).getBlockId());
        }


        //创建join

        for (Join join : joins) {
            Element joinEle = bookStore.addElement("join");
            joinEle.addAttribute("name", join.getName());
            joinEle.addAttribute("to", join.getTargetTo());
        }

        //action节点
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            if(Setting.getSparkIds().contains(model.getId()+"")){
                /*Element action = */buildSparkAction(model, bookStore, xmlProperties, appPath, username);
            }else if(Setting.getDbSparkIds().contains(model.getId() +"")){
                /**
                 * 添加代码else if模块 By gongmingxing 2017.08.22
                 */
                /*Element action = */buildDbSparkAction(model, bookStore, xmlProperties, appPath, username);
            }else {
                /*Element action = */buildJavaAction(model, bookStore, xmlProperties, appPath, username);
            }

        }

        //创建kill
        Element kill = bookStore.addElement("kill");
        kill.addAttribute("name", "fail");
        Element message = kill.addElement("message");
        message.addText(" something went wrong : ${wf:errorCode('wordcount')} ");
        //创建end
        Element end = bookStore.addElement("end");
        end.addAttribute("name", "end");
        File file = new File(path);
        // 如果上级目录不存在，则创建上级目录

        try {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            // 如果文件不存在，则创建文件
            if (!file.exists())
                file.createNewFile();
            // 创建字节输出流FileOutputStream对象
            FileOutputStream out = new FileOutputStream(file);
            // 创建一个OutputFormat对象(XML以比较友好的格式输出)
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置XML文档编码
            format.setEncoding("UTF-8");
            // 设置XML文档缩进
            format.setIndent(true);
            // 创建一个XMLWriter对象
            XMLWriter writer = new XMLWriter(out, format);
            // 将内容写入XML文档
            writer.write(bookStore);
            // 释放资源
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return xmlProperties;
    }

    /**
     * 替换xml里面多余的:xmlns
     * @param path
     * @param newPath
     */
    public static  void updateXml(String path,String newPath){
        try {
            List<String> lines = FileUtils.readLines(new File(path));
            for (int i=0;i<lines.size();i++) {
                String line = lines.get(i);
                if(line.contains(":xmlns")){
                    String newLine = line.replaceAll(":xmlns", "");
                    lines.remove(i);
                    lines.add(i,newLine);

                }
            }
            FileUtils.writeLines(new File(newPath),lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //创建java Action
    public static Element buildJavaAction(Model model,Element bookStore,Properties xmlProperties,String appPath,String username){
        logger.debug("创建 javaAction id："+model.getBlockId());

        String blockId = model.getBlockId();
        //创建action节点
        Element action = bookStore.addElement("action");
        //String actionAttr = oneBook.getXmlStart();
        action.addAttribute("name",model.getBlockId());
        //创建java节点
        Element javaEle = action.addElement("java");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");
        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element property = configurationEle.addElement("property");
        //在property下创建name节点
        Element name = property.addElement("name");
        name.addText("mapred.job.queue.name");
        Element value = property.addElement("value");
        value.addText("${queueName}");
//        javaEle.addElement("main-class").addText("youe.data.scala.drivers.EtlDriver");// TODO mr -> spark
        javaEle.addElement("main-class").addText("com.youedata.etl.driver.Driver");



        List<ModelAttribute> modelAttributes = model.getData();
        for (ModelAttribute modelAttribute : modelAttributes) {
            if(modelAttribute==null){
                continue;
            }
            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
            String key = preMark + modelAttribute.getMattribute();
            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
            javaEle.addElement("arg").addText("${" + key + "}");
        }
        action.addElement("ok").addAttribute("to", model.getTargetBlockId());

        action.addElement("error").addAttribute("to", "fail");
        return action;
    }

//创建spark的action

    public static Element buildSparkAction(Model model,Element bookStore,Properties xmlProperties,String appPath,String username){
       logger.debug("创建 sparkAction ");

//        xmlProperties.setProperty(OozieClient.LIBPATH, MailAuthorSetting.OOZIECLIENT_LIBPATH);
        String blockId = model.getBlockId();
        //创建action节点
        Element action = bookStore.addElement("action");
        //String actionAttr = oneBook.getXmlStart();
        action.addAttribute("name",model.getBlockId());
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark scala job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.MachineLearnDriver2");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
        List<ModelAttribute> modelAttributes = model.getData();

        for (ModelAttribute modelAttribute : modelAttributes) {
            if(modelAttribute==null){
                continue;
            }
            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
            String key = preMark + modelAttribute.getMattribute();
            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
            javaEle.addElement("arg").addText("${" + key + "}");
        }
        action.addElement("ok").addAttribute("to", model.getTargetBlockId());

        action.addElement("error").addAttribute("to", "fail");
        return action;
    }

    private static Element buildDbSparkAction(Model model, Element bookStore, Properties xmlProperties, String appPath, String username) {
        logger.debug("创建 sparkAction ");

//        xmlProperties.setProperty(OozieClient.LIBPATH, MailAuthorSetting.OOZIECLIENT_LIBPATH1);
        String blockId = model.getBlockId();
        //创建action节点
        Element action = bookStore.addElement("action");
        //String actionAttr = oneBook.getXmlStart();
        action.addAttribute("name",model.getBlockId());
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark database job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.DbDriver");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
        List<ModelAttribute> modelAttributes = model.getData();

        for (ModelAttribute modelAttribute : modelAttributes) {
            if(modelAttribute==null){
                continue;
            }
            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
            String key = preMark + modelAttribute.getMattribute();
            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
            javaEle.addElement("arg").addText("${" + key + "}");
        }
        action.addElement("ok").addAttribute("to", model.getTargetBlockId());

        action.addElement("error").addAttribute("to", "fail");
        return action;
    }

    public static synchronized Properties builNewXml(WorkflowNode startWorkflowNode, String path, String appPath, String flowName, String username){
        logger.debug("开始创建xml");

        Properties  xmlProperties= new Properties();
        xmlProperties.setProperty(OozieClient.APP_PATH, appPath);
        xmlProperties.setProperty("oozie.wf.validate.ForkJoin","false");
        xmlProperties.setProperty("oozie.action.sharelib.for.spark", "spark2");
        xmlProperties.setProperty("nameNode", MailAuthorSetting.HADOOP_NAMENODE);//注意：此处为nameNode的地址
        xmlProperties.setProperty("jobTracker", MailAuthorSetting.HADOOP_JOBTRACKER);//注意：此处为resourcemanager的地址。HW中为yarn.resourcemanager.address=8050的值
        xmlProperties.setProperty("mapreduce.job.user.name", username);
        xmlProperties.setProperty("queueName", "default");
        xmlProperties.setProperty("user.name", username);
        xmlProperties.setProperty(OozieClient.LIBPATH, MailAuthorSetting.OOZIECLIENT_LIBPATH_V4_DEVELOP);
//        xmlProperties.setProperty(OozieClient.LIBPATH,MailAuthorSetting.OOZIECLIENT_LIBPATH_V4);

        Element bookStore = DocumentHelper.createElement("workflow-app");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date= df.format(new Date());
        bookStore.addAttribute("name", "workflow-"+"flowName" + "_" + date);
//        Namespace xmlns = DocumentHelper.createNamespace("xmlns", "uri:oozie:workflow:0.2");
        Namespace xmlns = DocumentHelper.createNamespace("xmlns", "uri:oozie:workflow:0.5");
        bookStore.add(xmlns);

        Set<WorkflowNode> set = new HashSet<>();

        Set<WorkflowNode> joinNodes = getJoinNodes(startWorkflowNode, set);

        Element element = geneateFullWorkflowXml(joinNodes,startWorkflowNode, bookStore,path,appPath,flowName,username,xmlProperties);
        startWorkflowNode.getAttrs().remove("blockID");
        Element kill = bookStore.addElement("kill");
        kill.addAttribute("name", "fail");
        Element message = kill.addElement("message");
        message.addText(" something went wrong : ${wf:errorCode('wordcount')} ");
        Element end = bookStore.addElement("end");
        end.addAttribute("name","end");

        File file = new File(path);
        // 如果上级目录不存在，则创建上级目录

        try {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            // 如果文件不存在，则创建文件
            if (!file.exists())
                file.createNewFile();
            // 创建字节输出流FileOutputStream对象
            FileOutputStream out = new FileOutputStream(file);
            // 创建一个OutputFormat对象(XML以比较友好的格式输出)
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置XML文档编码
            format.setEncoding("UTF-8");
            // 设置XML文档缩进
            format.setIndent(true);
            // 创建一个XMLWriter对象
            XMLWriter writer = new XMLWriter(out, format);
            // 将内容写入XML文档
            writer.write(element);
            // 释放资源
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return xmlProperties;
    }

    private static Element geneateFullWorkflowXml(Set<WorkflowNode> joins,WorkflowNode startWorkflowNode,Element bookStore,String path, String appPath, String flowName, String username,Properties xmlProperties) {
        Element currentEle = bookStore;
        if(startWorkflowNode.getId().equals("start")){
            Element startElement = bookStore.addElement("start");
            startElement.addAttribute("to",startWorkflowNode.getNextNodes().get(0).getId());
            //TODO 未完待续
        }else if(startWorkflowNode.getId().indexOf("fork") != -1 && startWorkflowNode.getId().indexOf("forkSecond") == -1 && startWorkflowNode.getId().indexOf("forkThird") == -1 && startWorkflowNode.getId().indexOf("forkForth") == -1){
            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"fork");
            //fork有重复的
            if(!isEleCreated){
                Element forkElement = bookStore.addElement("fork");
                forkElement.addAttribute("name",startWorkflowNode.getId());
                for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                    Element pathElement = forkElement.addElement("path");
                    pathElement.addAttribute("start",workflowNode.getId());
                }
                WorkflowNode joinNode = null;
                for(WorkflowNode workflowNode : joins){
                    if(workflowNode.getId().equals("join" + startWorkflowNode.getId().substring(startWorkflowNode.getId().length()-1,startWorkflowNode.getId().length()))){
                        joinNode = workflowNode;
                    }
                }
                Element joinElement = bookStore.addElement("join");
                joinElement.addAttribute("name",joinNode.getId());
                joinElement.addAttribute("to",joinNode.getNextNodes().get(0).getId());
            }
        }else if(startWorkflowNode.getId().indexOf("forkSecond") != -1){
            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"fork");
            if(!isEleCreated){
                Element forkElement = bookStore.addElement("fork");
                forkElement.addAttribute("name",startWorkflowNode.getId());
                for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                    Element pathElement = forkElement.addElement("path");
                    pathElement.addAttribute("start",workflowNode.getId());
                }
                WorkflowNode joinNode = null;
                for(WorkflowNode workflowNode : joins){
                    if(workflowNode.getId().equals("joinSecond" + startWorkflowNode.getId().substring(startWorkflowNode.getId().length()-1,startWorkflowNode.getId().length()))){
                        joinNode = workflowNode;
                    }
                }
                Element joinElement = bookStore.addElement("join");
                joinElement.addAttribute("name",joinNode.getId());
                joinElement.addAttribute("to",joinNode.getNextNodes().get(0).getId());
            }
        }else if(startWorkflowNode.getId().indexOf("forkThird") != -1){
            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"fork");
            if(!isEleCreated){
                Element forkElement = bookStore.addElement("fork");
                forkElement.addAttribute("name",startWorkflowNode.getId());
                for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                    Element pathElement = forkElement.addElement("path");
                    pathElement.addAttribute("start",workflowNode.getId());
                }
                WorkflowNode joinNode = null;
                for(WorkflowNode workflowNode : joins){
                    if(workflowNode.getId().equals("joinThird" + startWorkflowNode.getId().substring(startWorkflowNode.getId().length()-1,startWorkflowNode.getId().length()))){
                        joinNode = workflowNode;
                    }
                }
                Element joinElement = bookStore.addElement("join");
                joinElement.addAttribute("name",joinNode.getId());
                joinElement.addAttribute("to",joinNode.getNextNodes().get(0).getId());
            }
        }else if(startWorkflowNode.getId().indexOf("forkForth") != -1){
            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"fork");
            if(!isEleCreated){
                Element forkElement = bookStore.addElement("fork");
                forkElement.addAttribute("name",startWorkflowNode.getId());
                for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                    Element pathElement = forkElement.addElement("path");
                    pathElement.addAttribute("start",workflowNode.getId());
                }
                WorkflowNode joinNode = null;
                for(WorkflowNode workflowNode : joins){
                    if(workflowNode.getId().equals("joinForth" + startWorkflowNode.getId().substring(startWorkflowNode.getId().length()-1,startWorkflowNode.getId().length()))){
                        joinNode = workflowNode;
                    }
                }
                Element joinElement = bookStore.addElement("join");
                joinElement.addAttribute("name",joinNode.getId());
                joinElement.addAttribute("to",joinNode.getNextNodes().get(0).getId());
            }
        }else if(startWorkflowNode.getId().indexOf("join") != -1){
//            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"join");
//            if(!isEleCreated){
//                Element joinElement = bookStore.addElement("join");
//                joinElement.addAttribute("name",startWorkflowNode.getId());
//                joinElement.addAttribute("to",startWorkflowNode.getNextNodes().get(0).getId());
//            }
        } else if (startWorkflowNode.getId().equals("end")) {
//            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"end");
//            if(!isEleCreated){
//                Element kill = bookStore.addElement("kill");
//                kill.addAttribute("name", "fail");
//                Element message = kill.addElement("message");
//                message.addText(" something went wrong : ${wf:errorCode('wordcount')} ");
//                Element end = bookStore.addElement("end");
//                end.addAttribute("name","end");
//            }
        } else {
            boolean isEleCreated = eleIsCreated(startWorkflowNode,currentEle,"action");
            if(!isEleCreated){
                Element action = bookStore.addElement("action");
                action.addAttribute("name", startWorkflowNode.getId());
                if(Setting.getSparkIds().contains(startWorkflowNode.getAttrs().get("blockID"))){
//                /*Element action = */buildSparkAction(model, bookStore, xmlProperties, appPath, username);
                    buildNewSparkAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }else if(Setting.getDbSparkIds().contains(startWorkflowNode.getAttrs().get("blockID"))){
                    /**
                     * 添加代码else if模块 By gongmingxing 2017.08.22
                     */
//                /*Element action = */buildDbSparkAction(model, bookStore, xmlProperties, appPath, username);
                    buildNewDbSparkAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }else if(Setting.getSparkV4Ids().contains(startWorkflowNode.getAttrs().get("blockID"))){
                    buildV4SparkAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }else if(Setting.getSparkV4MlibIds().contains(startWorkflowNode.getAttrs().get("blockID"))){
                    buildV4SparkMlibAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }else if(Setting.getPythonAndRIds().contains(startWorkflowNode.getAttrs().get("blockID")) || Setting.getPythonAndRSingleIds().contains(startWorkflowNode.getAttrs().get("blockID"))){
                    buildShellAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }else{
//                /*Element action = */buildJavaAction(model, bookStore, xmlProperties, appPath, username);
                    buildNewJavaAction(startWorkflowNode,action,xmlProperties,appPath,username);
                }
                Element ok = action.addElement("ok");
                ok.addAttribute("to",startWorkflowNode.getNextNodes().get(0).getId());
                Element error = action.addElement("error");
                error.addAttribute("to","fail");
            }
        }

        if(startWorkflowNode.getNextNodes().size() == 1){
            currentEle = geneateFullWorkflowXml(joins,startWorkflowNode.getNextNodes().get(0), currentEle,path,appPath,flowName,username,xmlProperties);
        }else if(startWorkflowNode.getNextNodes().size() > 1){
            for(WorkflowNode workflowNode : startWorkflowNode.getNextNodes()){
                currentEle = geneateFullWorkflowXml(joins,workflowNode,currentEle,path,appPath,flowName,username,xmlProperties);
            }
        }
        return currentEle;
    }

    private static Element buildShellAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        logger.debug("创建 Shell Action");
        Element ShellEle = action.addElement("shell");
        ShellEle.addNamespace("xmlns","uri:oozie:shell-action:0.2");
        //创建job-tracker节点
        ShellEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        ShellEle.addElement("name-node").addText("${nameNode}");
        //创建configuration节点
        Element configurationEle = ShellEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");

        Map<String, String> attrs = startWorkflowNode.getAttrs();

        // modified by zhuliang String preMark = firstAttr.getTmpConfigType() == 2 ? "" : blockId + "_";
        String preMark = startWorkflowNode.getId() + "_";
        String functionKey = preMark + "shellScriptName";
        ShellEle.addElement("exec").addText("${" + functionKey + "}");
        xmlProperties.setProperty(functionKey,startWorkflowNode.getAttrs().get("shellScriptName"));

        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                if(!attrKey.equals("shellScriptName")){
                    String key = preMark + attrKey;
                    xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                    ShellEle.addElement("argument").addText("${" + key + "}");
                }
            }
        }
        ShellEle.addElement("file").addText("${" + functionKey + "}" + "#" + "${" + functionKey + "}");
        ShellEle.addElement("capture-output");

        return action;
    }

    private static Element buildV4SparkMlibAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark scala job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.MachineLearnDriver2");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
//        List<ModelAttribute> modelAttributes = model.getData();
//
//        for (ModelAttribute modelAttribute : modelAttributes) {
//            if(modelAttribute==null){
//                continue;
//            }
//            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
//            String key = preMark + modelAttribute.getMattribute();
//            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
//            javaEle.addElement("arg").addText("${" + key + "}");
//        }
//        action.addElement("ok").addAttribute("to", model.getTargetBlockId());
//
//        action.addElement("error").addAttribute("to", "fail");
        Map<String, String> attrs = startWorkflowNode.getAttrs();
        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                String preMark = startWorkflowNode.getId() + "_";
                String key = preMark + attrKey;
                xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                javaEle.addElement("arg").addText("${" + key + "}");
            }
        }
        return action;
    }

    private static Element buildV4SparkAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark scala job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.EtlDriver2");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
//        List<ModelAttribute> modelAttributes = model.getData();
//
//        for (ModelAttribute modelAttribute : modelAttributes) {
//            if(modelAttribute==null){
//                continue;
//            }
//            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
//            String key = preMark + modelAttribute.getMattribute();
//            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
//            javaEle.addElement("arg").addText("${" + key + "}");
//        }
//        action.addElement("ok").addAttribute("to", model.getTargetBlockId());
//
//        action.addElement("error").addAttribute("to", "fail");
        Map<String, String> attrs = startWorkflowNode.getAttrs();
        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                String preMark = startWorkflowNode.getId() + "_";
                String key = preMark + attrKey;
                xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                javaEle.addElement("arg").addText("${" + key + "}");
            }
        }
        return action;
    }

    private static Set<WorkflowNode> getJoinNodes(WorkflowNode startNode,Set<WorkflowNode> joinNodeSet){
        Set<WorkflowNode> set = joinNodeSet;
        if(startNode.getId().indexOf("join") != -1){
            joinNodeSet.add(startNode);
        }

        if(startNode.getNextNodes().size() > 0){
            for(WorkflowNode workflowNode : startNode.getNextNodes()){
                set = getJoinNodes(workflowNode,set);
            }
        }

        return set;
    }

    private static Element buildNewJavaAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        //创建java节点
        Element javaEle = action.addElement("java");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");
        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element property = configurationEle.addElement("property");
        //在property下创建name节点
        Element name = property.addElement("name");
        name.addText("mapred.job.queue.name");
        Element value = property.addElement("value");
        value.addText("${queueName}");
//        javaEle.addElement("main-class").addText("youe.data.scala.drivers.EtlDriver");// TODO mr -> spark
        javaEle.addElement("main-class").addText("com.youedata.etl.driver.Driver");

        Map<String, String> attrs = startWorkflowNode.getAttrs();


//        List<ModelAttribute> modelAttributes = model.getData();
//        for (ModelAttribute modelAttribute : modelAttributes) {
//            if(modelAttribute==null){
//                continue;
//            }
//            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
//            String key = preMark + modelAttribute.getMattribute();
//            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
//            javaEle.addElement("arg").addText("${" + key + "}");
//        }
//        action.addElement("ok").addAttribute("to", model.getTargetBlockId());
//
//        action.addElement("error").addAttribute("to", "fail");
        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                String preMark = startWorkflowNode.getId() + "_";
                String key = preMark + attrKey;
                xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                javaEle.addElement("arg").addText("${" + key + "}");
            }
        }
        return action;
    }

    private static Element buildNewDbSparkAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark database job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.DbDriver");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
//        List<ModelAttribute> modelAttributes = model.getData();
//
//        for (ModelAttribute modelAttribute : modelAttributes) {
//            if(modelAttribute==null){
//                continue;
//            }
//            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
//            String key = preMark + modelAttribute.getMattribute();
//            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
//            javaEle.addElement("arg").addText("${" + key + "}");
//        }
//        action.addElement("ok").addAttribute("to", model.getTargetBlockId());
//
//        action.addElement("error").addAttribute("to", "fail");
        Map<String, String> attrs = startWorkflowNode.getAttrs();
        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                String preMark = startWorkflowNode.getId() + "_";
                String key = preMark + attrKey;
                xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                javaEle.addElement("arg").addText("${" + key + "}");
            }
        }
        return action;
    }

    private static Element buildNewSparkAction(WorkflowNode startWorkflowNode, Element action, Properties xmlProperties, String appPath, String username) {
        //创建java节点
        Element javaEle = action.addElement("spark");
        javaEle.addNamespace("xmlns","uri:oozie:spark-action:0.1");
        //创建job-tracker节点
        javaEle.addElement("job-tracker").addText("${jobTracker}");
        //创建name-node节点
        javaEle.addElement("name-node").addText("${nameNode}");


        //创建configuration节点
        Element configurationEle = javaEle.addElement("configuration");
        //创建property节点
        Element fistProperty = configurationEle.addElement("property");
        fistProperty.addElement("name").addText("mapred.job.queue.name");
        fistProperty.addElement("value").addText("${queueName}");



        Element secProperty = configurationEle.addElement("property");

        secProperty.addElement("name").addText("oozie.service.SparkConfigurationService.spark.configurations");
        secProperty.addElement("value").addText(MailAuthorSetting.SPARK_EVENTLOG_DIR);

        javaEle.addElement("master").addText("yarn-cluster");
        javaEle.addElement("name").addText("spark scala job");
//        javaEle.addElement("class").addText("youe.data.scala.mllib.MachineLearnDriver");
        javaEle.addElement("class").addText("youe.data.scala.drivers.MachineLearnDriver");
//        javaEle.addElement("jar").addText("youe.scala-0.0.1-SNAPSHOT.jar");
        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT.jar");
//        javaEle.addElement("jar").addText("youe.scala.2.0-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        javaEle.addElement("spark-opts").addText("--conf spark.yarn.jars=/opt/cloudera/parcels/SPARK2-2.0.0.cloudera2-1.cdh5.7.0.p0.118100/lib/spark2/jars/*");//<spark-opts>--conf spark.yarn.jar=local:/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly.jar</spark-opts>

        xmlProperties.setProperty(OozieClient.USE_SYSTEM_LIBPATH,"true");
//        List<ModelAttribute> modelAttributes = model.getData();
//
//        for (ModelAttribute modelAttribute : modelAttributes) {
//            if(modelAttribute==null){
//                continue;
//            }
//            String preMark = modelAttribute.getTmpConfigType() == 2 ? "" : blockId + "_";
//            String key = preMark + modelAttribute.getMattribute();
//            xmlProperties.setProperty(key,new StringBuffer(modelAttribute.getMattribute()).append("=").append(modelAttribute.getMvalue()).toString() );
//            javaEle.addElement("arg").addText("${" + key + "}");
//        }
//        action.addElement("ok").addAttribute("to", model.getTargetBlockId());
//
//        action.addElement("error").addAttribute("to", "fail");
        Map<String, String> attrs = startWorkflowNode.getAttrs();
        for(String attrKey : attrs.keySet()){
            if(!attrKey.equals("blockID")){
                String preMark = startWorkflowNode.getId() + "_";
                String key = preMark + attrKey;
                xmlProperties.setProperty(key,new StringBuffer(attrKey).append("=").append(attrs.get(attrKey)).toString());
                javaEle.addElement("arg").addText("${" + key + "}");
            }
        }
        return action;
    }

    private static boolean eleIsCreated(WorkflowNode startWorkflowNode,Element currentEle,String EleQname){
        boolean eleIsCreated = false;
        List joins = currentEle.elements(EleQname);
        for (Iterator it = joins.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            // do something
            Attribute attribute = elm.attribute("name");
            String value = attribute.getValue();
            if(value.equals(startWorkflowNode.getId())){
                eleIsCreated = true;
            }
        }
        return eleIsCreated;
    }
}