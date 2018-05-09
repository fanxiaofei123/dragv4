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
    <title>可视化数据处理分析平台 | 模型接口调试</title>
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
                    <i class="icon iconfont icon-fanhui1" style="color: #a6a2a1;font-size: 26px" title="返回" ></i><span class="line">|</span>模型接口调试
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
                            <span class="body_head_ask"> 请求body：</span>
                            <span class="body_head_sample"><i class="icon iconfont icon-wenti"> </i><a  href="${basePath}/service/serviceModelSample.do?serviceModelId=${serviceModel.serviceModelId}">查看调用示例</a></span>
                        </div>

                        <div class="ask_body_code">


                    {<br>
                        <%--"Inputs": {<br>--%>
                               "input":<br>
                                [<br>
                                    {<br>
                            <p><input type="text" /><input type="text"/></p>
                                    <c:forEach items="${labelColumnList}" var="list" >
                                        <p>"<input type="text" value="${list}" disabled/>" ："<input type="text" class="labelColumn" value="1"/>",</p>
                                    </c:forEach>
                                    }<br>
                                 ]<br>
                            <%--}<br>--%>
                    }<br>
                        </div>
                        <div class="ask_body_foot">
                            <input id="outputPath" value="${serviceModel.outputPath}" hidden>
                            <input type="button" id="jiekoutiaoshi" onclick="jiekoutiaoshi()" value="调试接口"/>
                        </div>
                    </div>
                    <div class="test_body_content_right">
                        <div class="test_body_content_left_head">
                            <span class="body_head_ask"> 结果返回：</span>
                        </div>
                        <div class="scrollbarContentDiv result_context" id="resultData">

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
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/serviceModel/serviceModelApi.js" type="text/javascript"></script>
</body>
</html>