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
    <!-- END PAGE LEVEL PLUGINS -->
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
                                <span class="caption-subject font-green sbold uppercase">使用帮助</span></h4>
                            </div>
            <!-- END PAGE HEADER-->
            <div class="portlet box blue">
                <div class="portlet-body">
                    <h3>帮助</h3>
                    <div class="navSection">
                        <h4>1.数据上传</h4>
                        <p>用户登陆后，根据图示步骤依次执行1，2，3打开数据上传功能。</p>
                        <img src="${basePath}/content/images/navigation/nav1.png" alt="">
                        <p>依次执行如下图示步骤：</p>
                        <p>步骤4：浏览本地文件；</p>
                        <p>步骤5：选着要上传文件的路径；</p>
                        <p>步骤6：执行数据提交，完成数据上传；</p>
                        <img src="${basePath}/content/images/navigation/nav2.jpg" alt="">
                    </div>
                    <div class="navSection">
                        <h4>2.工作空间创以及工作流创建</h4>
                        <p>执行图示步骤1进入工作空间页面，执行步骤2进行工作空间创建。</p>
                        <img src="${basePath}/content/images/navigation/nav3.jpg" alt="">
                        <p>输入工作空间名与工作空间描述，提交后完成工作空间创建。</p>
                        <img src="${basePath}/content/images/navigation/nav4.jpg" alt="">
                        <p>点击创建好的工作空间，进入工作流创建页面，执行步骤3进行工作流创建。</p>
                        <img src="${basePath}/content/images/navigation/nav5.jpg" alt="">
                        <p>在输入框中输入工作流名称以及工作流描述信息，点击提交后完成工作流创建。</p>
                        <img src="${basePath}/content/images/navigation/nav6.jpg" alt="">
                    </div>
                    <div class="navSection">
                        <h4>3.工作流模型构建</h4>
                        <p>选中已创建的工作流，进入工作流模型构建页面。标示框1处为算子，通过拖动算子进行工作流创建。</p>
                        <img src="${basePath}/content/images/navigation/nav7.jpg" alt="">
                        <p>下图示为模型创建示例，拖动2、3、4中算子构建工作流模型，点击5进行执行。</p>
                        <img src="${basePath}/content/images/navigation/nav8.jpg" alt="">
                    </div>
                    <div class="navSection">
                        <h4>4.日志查看与日志导出</h4>
                        <p>根据图示步骤1、2进入日志详情查看页面，页面展示日志详情和日志统计图表。</p>
                        <img src="${basePath}/content/images/navigation/nav9.jpg" alt="">
                        <img src="${basePath}/content/images/navigation/nav10.jpg" alt="">
                        <p>在日志页面执行图示步骤1和2分别完成单个日志导出以及按时间段的日志批量导出。</p>
                        <img src="${basePath}/content/images/navigation/nav11.jpg" alt="">
                        <img src="${basePath}/content/images/navigation/nav12.jpg" alt="">
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
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='6'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>

</body>

</html>
