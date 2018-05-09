package org.com.drag.service.oozie.scheduler.xml;

import java.io.FileOutputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Created by zhh on 2016/11/1.
 */
public class JdomXmlHelper implements XmlHelper {
    public Object createXml(String path) {
        try {
            // 创建根节点workflow
            Element workflow = new Element("workflow-app");
            workflow.setNamespace(Namespace.getNamespace("xmls","uri:oozie:workflow:0.2"));
            workflow.setAttribute("name", "my-workflow-test");
            // 将根节点添加到文档中
            Document Doc = new Document(workflow);

            //创建start
            Element start = new Element("start");
            start.setAttribute("to","node1");
            //workflow.addContent(start);

            // 此处 for 循环可替换成 遍历 数据库表的结果集操作;
            for (int i = 0; i < 5; i++) {
                // 创建新节点 company;
                Element elements = new Element("company");
                // 给 company 节点添加属性 id;
                elements.setAttribute("id", "" + i);
                // 给 company 节点添加子节点并赋值
                elements.addContent(new Element("company_name").setText("name" + i));
                elements.addContent(new Element("company_email").setText("name" + i + "@163.com"));
                // 给父节点list添加company子节点;
                workflow.addContent(elements);
            }

            //输出创建好的xml文档对象
            XMLOutputter xmlOutput = new XMLOutputter();
            Format format = Format.getPrettyFormat();
            format.setIndent("\t");
            xmlOutput.setFormat(format);
            // 指定输出文件
            xmlOutput.output(Doc, new FileOutputStream("workflow-jdom.xml"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object parseXml() {
        return null;
    }
}
