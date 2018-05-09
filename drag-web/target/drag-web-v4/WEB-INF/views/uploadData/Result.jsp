<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/8
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/sdw.tld" prefix="sdw"%>
<%@ taglib uri="/WEB-INF/tld/sdwIf.tld" prefix="sdwIf"%>




<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 上传历史</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath}/content/css/updateData/updateData.css">
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
            <h3 class="page-title">运行结果
               <!--  <small>Update Data</small> -->
            </h3>
            <div class="page-bar">
                <ul class="page-breadcrumb" id="folder-navi">
                    <li onclick="showIndex(this)">
                        <i class="icon-home"></i>
                        <a href="javascript:;">运行结果</a>
                    </li>
                </ul>
            </div>
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-info progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
                </div>
            </div>
            <div class="loading"  index="3" id="loading" style="position: relative;">
                <div style="display: none" class="demo"></div>
            </div>
            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class=" icon-layers font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">运行结果列表</span>

                            </div>


                        </div>


                        <div class="portlet-body" id="history-contents">
                            <ul id="history-list" style="list-style: none">
                                <li>
                                    <div class="icon" style=" display: inline-block;">
                                        <img src="${basePath}/images/task-import.png" alt="">
                                    </div>
                                    <div class="history-content" style="padding-left: 132px;display: inline-block;vertical-align: middle;">
                                        <div class="contentHeader">成功将<span style="color: #0d638f;">温热无热无若.iso</span>导入HDFS</div>
                                        <div class="time">时间:2017-06-01 12:32</div>
                                        <div class="actions delHistory" onclick='delHistory(<sdw:tmpl name ="id"/>)' >
                                            <a class="delete_link alert delete_notification "  href="#"><span class="fa fa-trash"></span><span> 删除</span></a>
                                        </div>
                                    </div>
                                </li>
                            </ul>

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
<div class="page-footer">
    <div class="page-footer-inner"> 2015 &copy; Metronic by keenthemes.
        <a href="http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes" title="Purchase Metronic just for 27$ and get lifetime updates for free" target="_blank">Purchase Metronic!</a>
    </div>
    <div class="scroll-to-top">
        <i class="icon-arrow-up"></i>
    </div>
</div>

<script>window.nav='3'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>

<script id="history-content" type="text/x-jquery-tmpl">
                    <ul id="history-list" style="list-style: none">
                                <li>
                                    <div class="icon" style=" display: inline-block;">
                                        <img src="${basePath}/images/task-import.png" alt="">
                                    </div>
                                    <div class="history-content" style="padding-left: 132px;display: inline-block;vertical-align: middle;">
                                        <div class="contentHeader">成功将<span style="color: #0d638f;"><sdw:tmpl name ="fileName"/></span>导入HDFS</div>
                                        <div class="time">时间:<sdw:tmpl name ="uploadTime"/></div>
                                        <div class="actions delHistory" onclick='del(<sdw:tmpl name ="id"/>)' >
                                            <a class="delete_link alert delete_notification "  href="#"><span class="fa fa-trash"></span><span> 删除</span></a>
                                        </div>
                                    </div>
                                </li>
                    </ul>

</script>
<script type="application/javascript">
    $(function () {
        init()
    });
    function init() {
        $("#history-contents").html("")
        $.get(basePath+"/drag/history/listAll.do",function (data) {
            var data = JSON.parse(data);
            $("#history-content").tmpl(data).appendTo('#history-contents');
        });
    }
    function del(id) {
        $.get(basePath + "/drag/history/del.do", {'id': id},function (data) {
            var data =  JSON.parse(data);
            console.log(data.code);
            switch (data.code){
                case 200:
                    toastr.success(data.msg);
                    break;
                case 417:
                    toastr.error(data.msg);
                    break;
            }
        });
        init();
    }
</script>
</body>
</html>
