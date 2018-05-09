package org.com.drag.web.util;

import org.com.drag.common.util.Constants;
import org.com.drag.model.User;
import org.com.drag.service.oozie.api.IHdfsApi;
import javax.servlet.http.HttpSession;
import org.com.drag.common.util.Charsets;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyu on 2017/10/17.
 */
public class ReadFileUtil {
    public static List<List<String>> readCsv(String curDir, IHdfsApi iHdfsApi, HttpSession session){
        List<List<String>> table = new ArrayList();
        try{
            User user = (User)session.getAttribute(Constants.USER_KEY);
            //  HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
/*            byte[] byteArray = iHdfsApi.readFileFromHdfs(curDir, user.getLoginname());
            Charset code= Charsets.GBK;//编码方式
            if(byteArray[0]==-17 && byteArray[1]==-69 && byteArray[2]==-65){
                code=Charsets.UTF_8;
            }
            if(byteArray[0]==-26 && byteArray[1]==-120 && byteArray[2]==-112){
                code=Charsets.UTF_8;
            }
            if(byteArray[0]==48 && byteArray[1]==44 && byteArray[2]==-28){
                code=Charsets.UTF_8;
            }
            String replace = new String(byteArray,code).trim();*/
            String replace= iHdfsApi.readLineFileFromHdfs(curDir,user.getLoginname());
            String[] splitTr = replace.split("\\n|\\r");
            for (String tr : splitTr) {
                if (tr==""||tr.equals("")){
                    continue;
                }else {
                    String[] td = tr.split(",");
                    List<String> trTmp = new ArrayList<>();
                    for (String s : td) {
                        trTmp.add(s);
                    }
                    table.add(trTmp);
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public static List<List<String>> readCsvCount(String curDir, IHdfsApi iHdfsApi, HttpSession session){
        List<List<String>> table = new ArrayList();
        try{
            User user = (User)session.getAttribute(Constants.USER_KEY);
            //  HdfsFileUtil hdfsFileUtil = new HdfsFileUtil();
            byte[] byteArray = iHdfsApi.readFileFromHdfs(curDir, user.getLoginname());
            Charset code= Charsets.UTF_8;//编码方式
            for (int i=0;i<byteArray.length;i++){
                if (byteArray[i]==-63){
                    code= Charsets.GBK;
                }
            }
            String replace = new String(byteArray,code).trim();
            String[] splitTr = replace.split("\\n|\\r");
            for (String tr : splitTr) {
                if (tr==""||tr.equals("")){
                    continue;
                }else {
                    String[] td = tr.split(",");
                    List<String> trTmp = new ArrayList<>();
                    for (String s : td) {
                        trTmp.add(s);
                    }
                    table.add(trTmp);
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
}
