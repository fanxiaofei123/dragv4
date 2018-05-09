<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/11/6
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 接口调用示例</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/serviceModel/serviceModel.css">

</head>
<!-- END HEAD -->
<%--<script type="application/javascript">--%>
   <%--var templateDescription = ${templateDescription}--%>
<%--</script>--%>
<jsp:include page="/components/navi/head.jsp" flush="true"/>
<!-- END HEADER -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"> </div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <jsp:include page="/components/navi/menu.jsp" flush="true"/>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <%--文档示例--%>
                <span style="font-size: 16px;margin-left: 22px;" class="font-color">已部署的模型服务</span>
                <div class="page-bar shadowRadius" style="margin-top: 18px;">
                    <div style="float: left;color: #7f8fa4" onclick="javascript:history.back(-1);">
                        <i class="icon iconfont icon-fanhui1" style="color: #a6a2a1;font-size: 26px" title="返回" ></i><span class="line">|</span>模型接口调试<i class="icon iconfont icon-you1"></i>调用示例
                    </div>

                </div>
                <div id="progress" class="progress" style="display: none">
                    <div class="progress-bar progress-bar-success progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                        <div id="progress-value" class="progress-value">85%</div>
                    </div>
                </div>
                <div  class="test_body">
                    <%--居中--%>
                    <div class="test_body_content">
                        <div class="test_body_content_left">
                            <div class="test_body_content_left_head">
                                <span class="body_head_ask">  接口调用示例：</span>
                                <span class="body_head_sample " id="copyBtn" ><i class="icon iconfont icon-fuzhi"> </i>复制</span>
                            </div>

                            <textarea class=" ask_body_code_sample"  id="body_code" disabled>
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Query {

       public static final String APPKEY = "${serviceModel.token}_${serviceModel.userId}";// 你的key
       public static final String URL = "${basePath}${serviceModelAdds}";
       public static String labelColumnA = "";

       public static final Map map=new LinkedHashMap();
            static {
               <c:forEach items="${labelColumnList}" var="list" >
                     map.put("${list}", "1");
                     </c:forEach>
                     map.forEach((k,v)->
                     labelColumnA += v+","
                  };
       }

       public static String getLabelColumnA() {
            return labelColumnA;
       }

       public static final String outputPath = " ${serviceModel.outputPath}";

       public static void Get() throws Exception {
            String result = null;
            String url = URL;
            String LabelColumnA  = getLabelColumnA();
            LabelColumnA= LabelColumnA.substring(0,LabelColumnA.length()-1);
            String LabelColumnB = LabelColumnA.toString();
            String params = "key=" + APPKEY + "&labelColumn=" + LabelColumnB+"&outputPath="+outputPath;
            try {
               result = HttpClientUtil.sendHttpPost(url,params);
               System.out.println(result);
            } catch (Exception e) {
               e.printStackTrace();
            }
       }

            // utf-8字符编码
        public static final String CHARSET_UTF_8 = "utf-8";

        // HTTP内容类型。
        public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

        // HTTP内容类型。相当于form表单的形式，提交数据
        public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

        // HTTP内容类型。相当于form表单的形式，提交数据
        public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";


        // 连接管理器
        private static PoolingHttpClientConnectionManager pool;

        // 请求配置
        private static RequestConfig requestConfig;

        /**
         * 发送 post请求
         *
         * @param httpUrl
         *            地址
         * @param params
         *            参数(格式:key1=value1&key2=value2)
         *
         */
        public static String sendHttpPost(String httpUrl, String params) {
            HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
            try {
                // 设置参数
                if (params != null && params.trim().length() > 0) {
                    StringEntity stringEntity = new StringEntity(params, "UTF-8");
                    stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                    httpPost.setEntity(stringEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sendHttpPost(httpPost);
        }

        /**
         * 发送Post请求
         *
         * @param httpPost
         * @return
         */
        private static String sendHttpPost(HttpPost httpPost) {

            CloseableHttpClient httpClient = null;
            CloseableHttpResponse response = null;
            // 响应内容
            String responseContent = null;
            try {
                // 创建默认的httpClient实例.
                httpClient = getHttpClient();
                // 配置请求信息
                httpPost.setConfig(requestConfig);
                // 执行请求
                response = httpClient.execute(httpPost);
                // 得到响应实例
                HttpEntity entity = response.getEntity();

                // 可以获得响应头
                // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
                // for (Header header : headers) {
                // System.out.println(header.getName());
                // }

                // 得到响应类型
                // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

                // 判断响应状态
                if (response.getStatusLine().getStatusCode() >= 300) {
                    throw new Exception(
                            "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
                }

                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                    EntityUtils.consume(entity);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 释放资源
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return responseContent;
        }

        public static CloseableHttpClient getHttpClient() {

            CloseableHttpClient httpClient = HttpClients.custom()
                    // 设置连接池管理
                    .setConnectionManager(pool)
                    // 设置请求配置
                    .setDefaultRequestConfig(requestConfig)
                    // 设置重试次数
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                    .build();

            return httpClient;
        }

       public static void main(String[] args) {
            try {
                Query.Get();
            } catch (Exception e) {
                e.printStackTrace();
            }
       }
}
                           </textarea>

                        </div>
                        <div class="test_body_content_right">
                            <div class="test_body_content_left_head">
                                <span class="body_head_ask"> 结果返回样例：</span>
                            </div>
                            <div class="scrollbarContentDiv result_context">
                                预测结果：{"predictedValues":["0"]}

                            </div>
                        </div>
                    </div>

                </div>

        </div>

        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!--MODAL EDIT-->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='13'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<%--<script src="${basePath}/content/js/template/templateExplain.js" type="text/javascript"></script>--%>
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/serviceModel/serviceModelApi.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
<br />
<br />
</body>
</html>