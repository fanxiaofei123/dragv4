package org.com.drag.web.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.com.drag.common.util.Charsets;

/**
 * Created by huangyu on 2018/1/18.
 */
public class XmlConvertUtil {
    /**
     * json转xml
     * @param json
     * @return
     */
    public static String json2Xml(String[] json) {
        try {
            XMLSerializer serializer = new XMLSerializer();
            JSON jsonObject = JSONSerializer.toJSON(json);
            String xmlStr=serializer.write(jsonObject, String.valueOf(Charsets.GBK));
            return xmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String json2Xml(String json) {
        try {
            XMLSerializer serializer = new XMLSerializer();
            JSON jsonObject = JSONSerializer.toJSON(json);
            String xmlStr=serializer.write(jsonObject, String.valueOf(Charsets.GBK));
            return xmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String xml2Json(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml).toString();
    }

}
