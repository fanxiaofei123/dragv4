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
    <title>可视化数据处理分析平台 | 日志详情</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/pages/css/profile-2.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/logManagement/details.css">
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
           <!--  <h3 class="page-title"> 日志详情
                <small>Log Details</small>
            </h3> -->
             <div class="caption">
                                <h4><i class="icon-docs font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">日志详情</span></h4>
                            </div>
            <div class="page-bar">
                <ul class="page-breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="${basePath}/drag/dispatcher/loglist.do">日志</a>
                    </li>
                    <li>
                        <i class="fa fa-angle-right"></i>
                        ${user.loginname }
                    </li>
                </ul>
            </div>
            <!-- END PAGE HEADER-->
            <div class="profile">
                <div class="tabbable-line tabbable-full-width">
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab_1_1">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-7 profile-info">
                                            <h1 class="font-green sbold uppercase">${user.loginname }</h1>
                                            <ul class="list-inline">
                                                <li>
                                                    <i class="fa fa-user"></i>执行者：
                                                    <span>${calculationHistory.email }</span>
                                                </li>
                                                <li>
                                                    <i class="fa fa-code-fork"></i>工作流：
                                                    <span>${calculationHistory.way }</span>
                                                </li>
                                                <li>
                                                    <i class="fa fa-file-text-o"></i>执行结果：
                                                    <span>${calculationHistory.statusName }</span>
                                                </li>
                                                <li>
                                                    <i class="fa fa-clock-o"></i>执行时间：
                                                    <span>${calculationHistory.createTimes }</span>
                                                </li>
                                                <li>
                                                    <i class="fa fa-question-circle"></i>日志详情：
                                                   
                                                    <%-- <span>${calculationHistory.resason }</span> --%>
                                                </li>
                                                 <textarea cols="120" rows="20" >${calculationHistory.resason }</textarea>
                                            </ul>
                                            <%-- <a href="javascript:;" class="btn green"> 修改信息
                                                 <i class="fa fa-edit"></i>
                                             </a>--%>
                                        </div>
                                        <!--end col-md-8-->
                                        <div class="col-md-5">
                                            <div class="portlet sale-summary">
                                                <div class="portlet-body">
                                                    <div id="echartsSectorA" style="height:320px;"></div>
                                                    <div id="echartsSectorB" style="height:320px;"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <!--end col-md-4-->
                                    </div>
                                    <!--end row-->
                                </div>
                            </div>
                        </div>
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
<script>window.nav='7'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/charts-echarts.js" type="text/javascript"></script>
<script>
    $(function () {
        circleCharts("echartsSectorA", "所有用户工作流成功率");
        circleCharts("echartsSectorB", "本用户工作流成功率");
    })
</script>
</body>

</html>
