package org.com.drag.service.oozie.scheduler.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 使用DOM4j 生成xml文件
 * Created by zhh on 2016/10/18.
 */
public class Dom4jXmlHelper implements XmlHelper {

    public Object createXml(String path){
        try {

            //Document document = DocumentHelper.createDocument();

            //创建workflow-app
            Element workflowApp = DocumentHelper.createElement("workflow-app"); //
            workflowApp.addAttribute("name", "my-workflow-test");

            Namespace xmlns = DocumentHelper.createNamespace("xmlns", "uri:oozie:workflow:0.2");
            workflowApp.add(xmlns);
            //创建start
            Element startEle = workflowApp.addElement("start");
            startEle.addAttribute("to", "node1");
            //startEle.remove(startEle.getNamespace());

            //创建action
            Element action1 = workflowApp.addElement("action");
            action1.addAttribute("name", "node1");
            Element javaEle = action1.addElement("java");
            javaEle.addElement("job-tracker").addText("${jobTracker}");
            javaEle.addElement("name-node").addText("${name-node}");
            Element configurationEle = javaEle.addElement("configuration");
            Element property = configurationEle.addElement("property");
            Element name = property.addElement("name");
            name.addText("mapred.job.queue.name");
            Element value = property.addElement("value");
            value.addText("${queueName}");
            javaEle.addElement("main-class").addText("com.youedata.etl.driver.Driver");
            javaEle.addElement("arg").addText("${function}");
            javaEle.addElement("arg").addText("${inputPath}");
            javaEle.addElement("arg").addText("${outputPath}");
            javaEle.addElement("arg").addText("${dilimiter}");
            action1.addElement("ok").addAttribute("to", "end");
            action1.addElement("error").addAttribute("to", "fail");

            //创建kill
            Element kill = workflowApp.addElement("kill");
            kill.addAttribute("name", "fail");
            Element message = kill.addElement("message");
            message.addText(" something went wrong : ${wf:errorCode('wordcount')} ");

            //创建end
            Element end = workflowApp.addElement("end");
            end.addAttribute("name", "end");

            OutputFormat format = new OutputFormat("\t", true);
//        format.setEncoding("GBK");//设置编码格式
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path), format);

            /**去掉xml头部*/
//            document.getRootElement().asXML();
            xmlWriter.write(workflowApp);
            xmlWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Object parseXml() {
        return null;
    }

    public void updateXml(String path,String newPath){
        try {
            List<String> lines = FileUtils.readLines(new File(path));
            for (int i=0;i<lines.size();i++) {
                String line = lines.get(i);
                if(line.contains(":xmlns")){
                    String newLine = line.replaceFirst(":xmlns", "");
                    lines.remove(i);
                    lines.add(i,newLine);
                    break;
                }
            }
            FileUtils.writeLines(new File(newPath),lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
