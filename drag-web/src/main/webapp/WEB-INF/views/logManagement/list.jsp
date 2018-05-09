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
    <title>可视化数据处理分析平台 | 日志列表</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/logManagement/list.css">
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
                                <i class="icon-docs font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">日志列表</span>
                            </div>
                            <div class="actions">
                                <label>筛选日期：</label>
                                <div class="input-group input-large date-picker input-daterange" data-date="" data-date-format="yyyy/mm/dd">
                                    <input type="text" id="startTime" class="form-control" name="from">
                                    <span class="input-group-addon"> 至 </span>
                                    <input type="text" id="endTime" class="form-control" name="to">
                                </div>
                                <!-- /input-group -->
                                <a href="javascript:;" class="btn green" onclick="selectTime()" id="selecttime"> 查询
                                    <i class="fa fa-search"></i>
                                </a>
                                <a href="javascript:;" class="btn blue" onclick="exportExcel()"> 批量导出
                                    <i class="fa fa-cloud-download"></i>
                                </a>
                            </div>
                        </div>
                        <div class="portlet-body">
                                    <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                        <thead>
                                        <tr>
                                            <th class="table-checkbox" width="5%">
                                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                    <input type="checkbox" class="group-checkable" data-set="#sample_2 .checkboxes" />
                                                    <span></span>
                                                </label>
                                            </th>
                                            <th width="20%"> 执行者 </th>
                                            <th width="20%"> 工作流名称 </th>
                                            <th width="15%"> 结果 </th>
                                           <!--  <th width="40%"> 原因 </th> -->
                                            <th width="20%"> 时间 </th>
                                            <th width="20%"> 操作 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="tbodyList">
                                        </tbody>
                                    </table>
                                    <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="sample_4_paginate">
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
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>

<script src="${basePath}/assets/global/plugins/moment.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/components-date-time-pickers.js" type="text/javascript"></script>
<script src="${basePath}/content/js/logManagement/list.js" type="text/javascript"></script>
</body>

</html>
