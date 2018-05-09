package org.com.drag.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyu on 2017/11/27.
 */
public class TypeConversionUtil {
    public static List<String> typeConversion(List<String> typeList){
        List<String> columnType = new ArrayList<>();
        if(typeList != null&& typeList.size()>0){
            for (String type : typeList) {
                switch (type){
                    case "INT":
                        columnType.add("Integer");
                        break;
                    case "VARCHAR":
                        columnType.add("String");
                        break;
                    case "CHAR":
                        columnType.add("String");
                        break;
                    case "TEXT":
                        columnType.add("String");
                        break;
                    case "INTEGER":
                        columnType.add("Integer");
                        break;
                    case "TINYINT":
                        columnType.add("Integer");
                        break;
                    case "SMALLINT":
                        columnType.add("Integer");
                        break;
                    case "MEDIUMINT":
                        columnType.add("Integer");
                        break;
                    case "BIT":
                        columnType.add("Boolean");
                        break;
                    case "BIGINT":
                        columnType.add("Integer");
                        break;
                    case "FLOAT":
                        columnType.add("Float");
                        break;
                    case "DOUBLE":
                        columnType.add("Double");
                        break;
                    case "DECIMAL":
                        columnType.add("BigDecimal");
                        break;
                    case "DATE":
                        columnType.add("Date");
                        break;
                    case "TIME":
                        columnType.add("Time");
                        break;
                    case "DATETIME":
                        columnType.add("Timestamp");
                        break;
                    case "YEAR":
                        columnType.add("Date");
                        break;
                    case "NUMBER":
                        columnType.add("BigDecimal");
                        break;
                    case "number":
                        columnType.add("BigDecimal");
                        break;
                    case "VARCHAR2":
                        columnType.add("String");
                        break;
                    case "LONG":
                        columnType.add("String");
                        break;
                    default:
                        columnType.add("String");
                        break;
                }
            }
        }
        return columnType;
    }
}
