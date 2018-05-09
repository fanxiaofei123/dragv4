package org.com.drag.common.util;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PythonRUtils {
    public static String getPythonRScriptName(Integer id){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = PythonRUtils.class.getResourceAsStream("/pythonRConfig.properties");
            prop.load(input);
            Map<String, String> proMap = new HashMap<String, String>((Map) prop);
            String scriptName = proMap.get(id+"");
            return scriptName;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
