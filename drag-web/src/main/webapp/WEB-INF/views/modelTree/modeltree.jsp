<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<!DOCTYPE html>
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 导航</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/pages/css/profile-2.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/navigation.css">
    <%--<link rel="stylesheet" href="${basePath}/content/css/tree/demo.css" type="text/css">--%>
    <link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${basePath}/js/jquery-1.9.0.js"></script>
    <!-- END PAGE LEVEL PLUGINS -->
    <script type="application/javascript">

        var commonNodes=${commonNodes};
        var topicNodes=${topicNodes};


    </script>

</head>
<!-- END HEAD -->
<jsp:include page="/components/navi/head.jsp" flush="true"/>
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
            <!-- BEGIN PAGE HEADER-->
            <!--  <h3 class="page-title"> 使用帮助
                 <small>Help</small>
             </h3> -->
            <div class="caption">
                <h4> <i class="icon-docs font-green"></i>
                    <span class="caption-subject font-green sbold uppercase">树形目录</span></h4>
            </div>
            <!-- END PAGE HEADER-->

            <div class="portlet box blue">

                <div class="portlet-body">
                    <script type="text/javascript" src="${basePath}/js/jquery.ztree.core.js"></script>
                    <script type="text/javascript" src="${basePath}/js/jquery.ztree.excheck.js"></script>
                    <script type="text/javascript" src="${basePath}/js/jquery.ztree.exedit.js"></script>
                    <div class="zTreeDemoBackground left" id="common">
                        <h5> <i class="icon-docs font-green"></i>
                            <span class="caption-subject font-green sbold uppercase">通用模型:</span></h5>
                        <ul id="treeDemo1" class="ztree"></ul>
                    </div>
                    <div class="zTreeDemoBackground left" id="topic">
                        <h5> <i class="icon-docs font-green"></i>
                            <span class="caption-subject font-green sbold uppercase">主题模型:</span></h5>
                        <ul id="treeDemo2" class="ztree"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- END CONTENT BODY -->
    </div>

    <!-- END CONTENT -->
</div>


<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->

<script>window.nav='10'

</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<script type="application/javascript" src="${basePath}/content/js/modeltree/commonNodes.js"></script>
<script type="application/javascript" src="${basePath}/content/js/modeltree/topicNodes.js"></script>
</body>

</html>
