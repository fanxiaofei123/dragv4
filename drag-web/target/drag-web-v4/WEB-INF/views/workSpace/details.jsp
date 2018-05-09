<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>可视化数据处理分析平台 | 工作空间</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/dataSource/linkModel.jsp"></jsp:include>
    <link href="${basePath}/assets/global/plugins/jstree/dist/themes/default/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/assets/global/plugins/fullcalendar/lib/cupertino/jquery-ui.min.css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="${basePath}/content/css/workSpace/workSpace.css">
    <link rel="stylesheet" href="${basePath}/assets/global/plugins/select2/css/select2.min.css">
    <style>
        .text-suanzi{
            display: inline-block;
            position: relative;
            left: 10px;
        }
        .k_suanzi-width{
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
        }
    </style>
</head>
<body onunload="win.close();">
<%--<script>--%>
    <%--var calculationHistoryList=${calculationHistoryList};--%>
<%--</script>--%>

<!-- END HEAD -->
<jsp:include page="/components/navi/head.jsp" flush="true"/>
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
            <input type="hidden" id="jobId">
            <input type="hidden" id="usrId">
            <!-- BEGIN PAGE HEADER-->
            <%--         <h3 class="page-title"> 工作流：
                         <a href="#basicEdit" data-toggle="modal" id="workFlowName">${name }</a>
                         <input type="hidden"   id="flowName" value="${name}">
                         <input type="hidden"   id="" value="${workFlow.flowExplain}">
                         <input type="hidden"   id="workSpaceName" value="${workSpaceName}">
                         <input type="hidden"   id="jobId" value="">
                         <textarea id="returnData" hidden>${workFlowContxt}</textarea>
                     </h3>--%>
            <%--<div class="page-bar">--%>
            <%--<ul class="page-breadcrumb">--%>
            <%--<li>--%>
            <%--<i class="icon-home"></i>--%>
            <%--<a class="font-blue-steel" href="${basePath}/drag/dispatcher/workSpaceList.do">工作空间</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<i class="fa fa-angle-right"></i>--%>
            <%--<a class="font-blue-steel" href="${basePath}/drag/flow/getflow.do?workspid=${workFlow.workspid }&workName=${workSpaceName }">${workSpaceName }</a>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<i class="fa fa-angle-right"></i>--%>
            <%--<a class="font-blue-steel" href="#basicEdit" data-toggle="modal" id="workFlowName">${name }</a>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
            <!-- END PAGE HEADER-->
            <!-- BEGIN WORK-->
            <div class="row canvasMain">
                <!-- BEGIN LEFT-->
                <div class="operatorBlock">
                    <div class="portlet light ">
                        <div class="portlet-title">
                            <div class="caption" style="float: right;padding-right: 15px;">
                                <i class="icon-layers icon-green"></i>
                                <span class="caption-subject icon-green bold uppercase">算子</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="actions">
                                <div class="formInput form-group has-feedback">
                                    <%--<input class="form-control input-sm input-block input-inline" id="formInput" placeholder="搜索工作流" style="width: 100%" type="search">--%>
                                    <input type="search" class="form-control input-sm input-small input-inline" id="formInput" placeholder="搜索算子">
                                    <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
                                </div>
                                <%--<div class="formInput">--%>
                                    <%--<input type="search" class="form-control input-sm input-small input-inline" id="formInput" placeholder="搜索算子">--%>
                                <%--</div>--%>
                            </div>
                            <div class="mt-element-list">
                                <div class="mt-list-container list-simple ext-1 group" id="modelList">
                                    <!--import-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#import-simple" aria-expanded="false">
                                        <div class="list-toggle bg-green-meadow uppercase">
                                            <i class="fa fa-sign-in"></i><span class="text-suanzi">导 入</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelImport.size()}</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="import-simple" aria-expanded="false" style="height: 0;">
                                        <ul class="dragList" id="import">
                                            <c:forEach items="${dataMdoelMap.dataModelImport}" var="dataModelIt">
                                                <li class="mt-list-item border-green-meadow">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-sign-in"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content border-common-color">
                                                        <h3>${dataModelIt.name}</h3>
                                                        <input type="hidden" id="${dataModelIt.nameRmNull }" value="${dataModelIt.id }" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelIt.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelIt.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelIt.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelIt.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>

                                        </ul>
                                    </div>
                                    <!--export-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#export-simple" aria-expanded="false">
                                        <div class="list-toggle bg-blue-madison uppercase">
                                            <i class="fa fa-sign-out"></i><span class="text-suanzi">导 出</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelExport.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="export-simple" aria-expanded="false" style="height: 0;">
                                        <ul class="dragList" id="export">
                                            <c:forEach items="${dataMdoelMap.dataModelExport}" var="dataModelEt">
                                                <li class="mt-list-item border-blue-madison">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-sign-out"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelEt.name }</h3>
                                                        <input type="hidden" id="${dataModelEt.nameRmNull }" value="${dataModelEt.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelEt.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelEt.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelEt.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelEt.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--transfer-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#transfer-simple" aria-expanded="false">
                                        <div class="list-toggle bg-blue-steel uppercase">
                                            <i class="fa fa-server"></i><span class="text-suanzi">转 换</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelTransfer.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="transfer-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="transfer">
                                            <c:forEach items="${dataMdoelMap.dataModelTransfer}" var="dataModelTr">
                                                <li class="mt-list-item border-blue-steel">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-server"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelTr.name }</h3>
                                                        <input type="hidden" id="${dataModelTr.nameRmNull }" value="${dataModelTr.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelTr.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelTr.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelTr.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelTr.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--attribution-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#attribution-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-haze uppercase">
                                            <i class="fa fa-cubes"></i><span class="text-suanzi">特征提取</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelAttribution.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="attribution-simple" aria-expanded="false" style="height: 0px;">
                                        <input type="hidden" id="flowId" value="${flowId}" />
                                        <ul class="dragList" id="attribution">
                                            <c:forEach items="${dataMdoelMap.dataModelAttribution}" var="dataModelAn">
                                                <li class="mt-list-item border-yellow-haze">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-cubes"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelAn.name }</h3>
                                                        <input type="hidden" id="${dataModelAn.nameRmNull }" value="${dataModelAn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelAn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelAn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelAn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelAn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--classification-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#classification-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-mint uppercase">
                                            <i class="fa fa-list-ul"></i><span class="text-suanzi">分 类</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelClassification.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="classification-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="classification">
                                            <c:forEach items="${dataMdoelMap.dataModelClassification}" var="dataModelCn">
                                                <li class="mt-list-item border-yellow-mint">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-list-ul"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelCn.name }</h3>
                                                        <input type="hidden" id="${dataModelCn.nameRmNull }" value="${dataModelCn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelCn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelCn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelCn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelCn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--regression-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#regression-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-haze uppercase">
                                            <i class="fa fa-undo"></i><span class="text-suanzi">回 归</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelRegression.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="regression-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="regression">
                                            <c:forEach items="${dataMdoelMap.dataModelRegression}" var="dataModelRn">
                                                <li class="mt-list-item border-yellow-haze">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-undo"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelRn.name }</h3>
                                                        <input type="hidden" id="${dataModelRn.nameRmNull }" value="${dataModelRn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelRn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelRn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelRn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelRn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--abnormal-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#abnormal-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-haze uppercase">
                                            <i class="fa fa-medkit"></i><span class="text-suanzi">异常检测</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelAbnormal.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="abnormal-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="abnormal">
                                            <c:forEach items="${dataMdoelMap.dataModelAbnormal}" var="dataModelRn">
                                                <li class="mt-list-item border-yellow-haze">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-medkit"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelRn.name }</h3>
                                                        <input type="hidden" id="${dataModelRn.nameRmNull }" value="${dataModelRn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelRn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelRn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelRn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelRn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--clustering-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#clustering-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-mint uppercase">
                                            <i class="fa fa-database"></i><span class="text-suanzi">聚 类</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelClustering.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="clustering-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="clustering">
                                            <c:forEach items="${dataMdoelMap.dataModelClustering}" var="dataModelCt">
                                                <li class="mt-list-item border-yellow-mint">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-database"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelCt.name}</h3>
                                                        <input type="hidden" id="${dataModelCt.nameRmNull }" value="${dataModelCt.id        }" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelCt.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelCt.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelCt.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelCt.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--associate-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#associate-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow-haze uppercase">
                                            <i class="fa fa-chain"></i><span class="text-suanzi">关 联</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelAssociate.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="associate-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="associate">
                                            <c:forEach items="${dataMdoelMap.dataModelAssociate}" var="dataModelAn">
                                                <li class="mt-list-item border-yellow-haze">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-chain"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelAn.name }</h3>
                                                        <input type="hidden" id="${dataModelAn.nameRmNull }" value="${dataModelAn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelAn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelAn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelAn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelAn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>


                                    <!--text-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#text-simple" aria-expanded="false">
                                        <div class="list-toggle bg-purple-plum uppercase">
                                            <i class="fa fa-file-text-o"></i><span class="text-suanzi">文 本</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelText.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="text-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="text">
                                            <c:forEach items="${dataMdoelMap.dataModelText}" var="dataModelTn">
                                                <li class="mt-list-item border-purple-plum">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-file-text-o"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelTn.name}</h3>
                                                        <input type="hidden" id="${dataModelTn.nameRmNull }" value="${dataModelTn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelTn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelTn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelTn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelTn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--predict-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#predict-simple" aria-expanded="false">
                                        <div class="list-toggle bg-green-soft uppercase">
                                            <i class="fa fa-line-chart"></i><span class="text-suanzi">预 测</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelPredict.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="predict-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="predict">
                                            <c:forEach items="${dataMdoelMap.dataModelPredict}" var="dataModelDp">
                                                <li class="mt-list-item border-green-soft">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-line-chart"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelDp.name}</h3>
                                                        <input type="hidden" id="${dataModelDp.nameRmNull }" value="${dataModelDp.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelDp.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelDp.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelDp.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelDp.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                    <!--performance-->
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#performance-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow uppercase">
                                            <i class="fa fa-tasks"></i><span class="text-suanzi">评 估</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelPerformance.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="performance-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="performance">
                                            <c:forEach items="${dataMdoelMap.dataModelPerformance}" var="dataModelPn">
                                                <li class="mt-list-item border-yellow">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-tasks"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelPn.name }</h3>
                                                        <input type="hidden" id="${dataModelPn.nameRmNull }" value="${dataModelPn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelPn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelPn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelPn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelPn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#trained-simple" aria-expanded="false" id="modeltrained">
                                        <div class="list-toggle bg-yellow uppercase">
                                            <i class="fa fa-tasks"></i><span class="text-suanzi">已训练的模型</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold" id="modelsize">${dataMdoelMap.dataModelTrained.size() }</span>
                                        </div>
                                    </a>

                                    <div class="panel-collapse collapse" id="trained-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="trainedul">
                                            <c:forEach items="${dataMdoelMap.dataModelTrained}" var="dataModelPn">
                                                <li class="mt-list-item border-yellow">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-tasks"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelPn.name }</h3>
                                                        <input type="hidden" id="${dataModelPn.nameRmNull }" value="${dataModelPn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelPn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelPn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelPn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelPn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <%--自定义算子--%>
                                    <a class="list-toggle-container collapsed" data-toggle="collapse" href="#custom-simple" aria-expanded="false">
                                        <div class="list-toggle bg-yellow uppercase">
                                            <i class="fa fa-tasks"></i><span class="text-suanzi">自定义算子</span>
                                            <span class="badge badge-default pull-right common-bg-color font-white bold">${dataMdoelMap.dataModelCustom.size() }</span>
                                        </div>
                                    </a>
                                    <div class="panel-collapse collapse" id="custom-simple" aria-expanded="false" style="height: 0px;">
                                        <ul class="dragList" id="custom">
                                            <c:forEach items="${dataMdoelMap.dataModelCustom}" var="dataModelPn">
                                                <li class="mt-list-item border-yellow">
                                                    <div class="list-icon-container-box">
                                                        <div class="list-icon-container">
                                                            <i class="fa fa-tasks"></i>
                                                        </div>
                                                    </div>
                                                    <div class="list-item-content">
                                                        <h3>${dataModelPn.name }</h3>
                                                        <input type="hidden" id="${dataModelPn.nameRmNull }" value="${dataModelPn.id}" />
                                                        <input type="hidden" class="leftNumber" value="${dataModelPn.leftNumber }" />
                                                        <input type="hidden" class="rightNumber" value="${dataModelPn.rightNumber }" />
                                                        <input type="hidden" class="modelInfo" value="${dataModelPn.flowDetails }" />
                                                        <input type="hidden" class="nameEnglish" value="${dataModelPn.nameEnglish }" />
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END LEFT-->
                <div class="operatorTitle">

                    <div class="canvasBlock">
                        <div class="portlet light ">
                            <div class="oper-title shadowRadius">
                                <div class="oper-action">
                                    <div class="detailsName">
                                        <p class="text-left">${name } <i class="icon iconfont icon-xia" onclick="showDropDown()"></i></p>
                                        <%--<p>${name } <i class="glyphicon glyphicon-menu-up"></i></p>--%>
                                        <ul class="sessionStorage" id="sessionMessage" style="display: none">
                                            <%--<li class="sessionCheck"><a href=""><i class="glyphicon glyphicon-ok"></i><span>jk</span></a><i class="glyphicon glyphicon-remove"></i></li>--%>
                                            <%--<li><a href=""><i class="glyphicon"></i><span>123</span></a><i class="glyphicon glyphicon-remove"></i></li>--%>
                                            <%--<li><a href=""><i class="glyphicon"></i><span>test</span></a><i class="glyphicon glyphicon-remove"></i></li>--%>
                                        </ul>
                                    </div>

                                    <div class="oper-title-actions">
                                        <a  title="部署为web服务" class="bushuweiwebfuwu" hidden data-toggle="modal" onclick="ServiceModelStatus()" style="font-weight: 800;"><i class='icon iconfont  icon-bushu'></i></a>
                                        <%--<a  title="重新训练" class="chongxinxunlian" onclick="run(999)">重新训练</a>--%>
                                        <a  title="重新训练" class="chongxinxunlian" hidden onclick="chongxinxunlian()" ><i class='icon iconfont  icon-zhongxinbushu'></i></a>
                                        <a href="javascript:;" id="scheduler" title="任务调度" class="setScheduler" valueSchJobStatus="0"></a>
                                        <a class="" id="iconConfig" href="javascript:;" title="配置信息">
                                            <i class="icon iconfont icon-1"></i>
                                        </a>
                                        <a id="run" class="" href="javascript:;" onclick="run()" title="运行">
                                            <i class="icon iconfont icon-yunhang"></i>
                                        </a>
                                        <a class="" href="javascript:;" title="保存" onclick="save()">
                                            <i class="icon iconfont icon-baocun"></i>
                                        </a>
                                        <c:choose>
                                            <c:when test="${workFlow.type == 1}">
                                                <a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">
                                                    <i class="icon iconfont icon-lishiyunxingjilu"></i>
                                                </a>
                                            </c:when>
                                            <c:when test="${workFlow.type == 2}">
                                                <a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">
                                                    <i class="icon iconfont icon-lishiyunxingjilu"></i>
                                                </a>
                                            </c:when>
                                            <c:when test="${workFlow.type == 3}">
                                                <a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">
                                                    <i class="icon iconfont icon-lishiyunxingjilu"></i>
                                                </a>
                                            </c:when>
                                            <c:when test="${workFlow.type == 4}">
                                                <a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">
                                                    <i class="icon iconfont icon-lishiyunxingjilu"></i>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="" href="javascript:;" onclick="updateFlow()" title="完成训练模型">
                                                    <i class="icon iconfont icon-wancheng"></i>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                            </div>
                            <div class="portlet-title">
                                <div class="caption">
                                    <input type="hidden"   id="flowName" value="${name}">
                                    <input type="hidden"   id="" value="${workFlow.flowExplain}">
                                    <input type="hidden"   id="workSpaceName" value="${workSpaceName}">
                                    <input type="hidden"   id="jobId" value="">
                                    <textarea id="returnData" hidden>${workFlowContxt}</textarea>
                                </div>
                                <div class="actions">
                                    <%--<a class="btn btn-circle btn-icon-only btn-default" id="" href="javascript:;" title="配置信息">--%>
                                    <%--<i class="icon-info"></i>--%>
                                    <%--</a>--%>
                                    <%--<a class="" id="iconInfo" href="javascript:;" title="运行历史">--%>
                                        <%--<i class="icon-flag"></i>--%>
                                    <%--</a>--%>
                                    <a class="" id="zoomPlus" href="javascript:;" title="放大">
                                        <i class="icon-magnifier-add"></i>
                                    </a>
                                    <a class="" id="zoomMinus" href="javascript:;" title="缩小">
                                        <i class="icon-magnifier-remove"></i>
                                    </a>
                                    <a class="" id="zoomNormal" href="javascript:;" title="还原">
                                        <i class="icon-reload"></i>
                                    </a>
                                    <a class="" id="zoomGrid" href="javascript:;" title="网格显示">
                                        <i class="icon iconfont icon-grid"></i>
                                    </a>
                                    <a class="" id="zoomFull" href="javascript:;" title="全屏">
                                        <i class="icon iconfont icon-quanping"></i>
                                    </a>
                                    <%--<a id="run" class="btn btn-circle btn-icon-only btn-default" href="javascript:;" onclick="run()" title="运行">--%>
                                    <%--<i class="icon-control-play"></i>--%>
                                    <%--</a>--%>
                                    <!-- <a class="btn btn-circle btn-icon-only btn-default" href="javascript:;" onclick="calculationPause()" title="暂停">
                                        <i class="icon-control-pause"></i>
                                    </a> -->
                                    <%--<a class="btn btn-circle btn-icon-only btn-default" href="javascript:;" title="保存" onclick="save()">--%>
                                    <%--<i class="icon-check"></i>--%>
                                    <%--</a>--%>

                                    <%--<c:choose>--%>
                                    <%--<c:when test="${workFlow.type == 1}">--%>
                                    <%--<a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">--%>
                                    <%--继续完成训练模型--%>
                                    <%--</a>--%>
                                    <%--</c:when>--%>
                                    <%--<c:when test="${workFlow.type == 2}">--%>
                                    <%--<a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">--%>
                                    <%--继续完成训练模型--%>
                                    <%--</a>--%>
                                    <%--</c:when>--%>
                                    <%--<c:when test="${workFlow.type == 3}">--%>
                                    <%--<a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">--%>
                                    <%--继续完成训练模型--%>
                                    <%--</a>--%>
                                    <%--</c:when>--%>
                                    <%--<c:when test="${workFlow.type == 4}">--%>
                                    <%--<a class="" href="javascript:;" onclick="ContinueFlow()" title="继续完成训练模型">--%>
                                    <%--继续完成训练模型--%>
                                    <%--</a>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                    <%--<a class="" href="javascript:;" onclick="updateFlow()" title="完成训练模型">--%>
                                    <%--完成训练模型--%>
                                    <%--</a>--%>
                                    <%--</c:otherwise>--%>
                                    <%--</c:choose>--%>
                                </div>
                            </div>
                            <div class="portlet-body" id="canvasBody">
                                <div id="canvasBlock"></div> <img src="${basePath}/images/workSpaceEx.png" id="workSpaceExImg">

                            </div>
                            <div class="run-history">
                                <span class="history-title">
                                    <i class="icon iconfont icon-kongzhitai"></i><span>控制台</span>
                                </span>
                                <a class="" id="iconInfo" href="javascript:;" title="运行历史">
                                    <i class="icon iconfont icon-xian font-green-sharp"></i>
                                </a>
                            </div>
                        </div>
                        <!-- BEGIN CONSOLE -->
                        <div class="page-console">
                            <div class="row">
                                <div class="col-md-12">

                                    <div class="portlet light " id="blockui_sample_1_portlet_body">
                                        <div class="portlet-title">
                                            <div class="caption">
                                                <i class="icon-flag font-green-sharp"></i>
                                                <span class="caption-subject font-green-sharp sbold">控制台</span>
                                            </div>
                                            <%-- <div class="caption">
                                                <a  id="" href="${basePath}/drag/dispatcher/listAllResult.do">
                                                <span class="caption-subject font-green-sharp sbold">查看运行结果</span>
                                                </a>
                                            </div> --%>
                                            <div class="actions">
                                                <a class="btn" id="closeConsole" href="javascript:;" title="关闭控制台">
                                                    <i class="icon-close"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <div class="progress progress-striped active">
                                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                                    <span id="progress-bar" class="sr-only">0% </span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-7">
                                                    <div class="well" id="wellConsole">

                                                    </div>
                                                </div>
                                                <div class="col-md-5">
                                                    <div class="table-scrollable">
                                                        <table class="table table-striped table-hover">
                                                            <thead>
                                                            <tr>
                                                                <th> 执行者 </th>
                                                                <th> 工作流名称 </th>
                                                                <th> 结果 </th>
                                                                <!-- <th> 原因 </th> -->
                                                                <th> 时间 </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="calculationHistory-content">
                                                            <c:forEach items="${calculationHistoryList}" var="history">
                                                                <tr>
                                                                    <td>${history.email}</td>
                                                                    <td>${history.way}</td>
                                                                    <td>
                                                                    <c:if test="${history.status==-1}">
                                                                            <span style="color: #f82b2b" >失败<span>
                                                                    </c:if>
                                                                    <c:if test="${history.status==1}">
                                                                            <span id="running" style="color: #666666" >运行中...<span>
                                                                    </c:if>
                                                                    <c:if test="${history.status==2}">
                                                                            <span style="color: #1fad97"> 成功</span>
                                                                    </c:if>
                                                                    </td>
                                                                    <td>${history.formatTime}</td>
                                                                </tr>

                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END CONSOLE -->
                    </div>
                    <div class="operatorMessage">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN VALIDATION STATES-->
                                <div class="portlet light portlet-fit portlet-form ">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-settings icon-green"></i>
                                            <span class="caption-subject common-color sbold uppercase">工作流属性</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <!-- BEGIN FORM-->
                                        <form action="#" class="form-horizontal">
                                            <%--<div class="form-body">--%>

                                                <div class="operator-group">
                                                    <label><i class="icon-film"></i>名称：</label>
                                                    <p>${name}</p>
                                                </div>
                                                <div class="operator-group">
                                                    <label><i class="icon-speech"></i>描述：</label>
                                                    <p>${flowExplain}</p>
                                                </div>
                                                <div class="operator-group">
                                                    <label><i class="icon-user"></i>创建人：</label>
                                                    <p>${sessionScope.user.loginname}</p>
                                                </div>
                                                <div class="operator-group">
                                                    <label><i class="icon-calendar"></i>创建日期：</label>
                                                    <p>${createTime}</p>
                                                </div>
                                            <%--</div>--%>
                                        </form>
                                        <!-- END FORM-->
                                    </div>
                                </div>
                                <!-- END VALIDATION STATES-->
                            </div>
                            <%--<div class="col-md-12">--%>
                                <%--<div class="choice-column tag-already">--%>
                                    <%--<p>页面选择1</p>--%>
                                    <%--<p>页面选择2</p>--%>
                                    <%--<p>页面选择3</p>--%>
                                    <%--<p>页面选择4</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-12">--%>
                                <%--<button type="button" class="btn btn-default bg-color" id="choice-tag">选择标签列</button>--%>
                            <%--</div>--%>
                        </div>
                        <%--<div class="row">--%>
                            <%--<div class="col-md-12">--%>
                                <%--<h4><i class="icon-settings"></i>模型属性</h4>--%>
                                <%--<div class="operator-group">--%>
                                    <%--<label><i class="icon-calendar"></i>创建日期：</label>--%>
                                    <%--<p>2017-06-28 16:14:30</p>--%>
                                <%--</div>--%>
                                <%--<div class="operator-group">--%>
                                    <%--<label><i class="icon-film"></i>名称：</label>--%>
                                    <%--<p>${name}</p>--%>
                                <%--</div>--%>
                                <%--<div class="operator-group">--%>
                                    <%--<label><i class="icon-speech"></i>描述：</label>--%>
                                    <%--<p>心脏病案例</p>--%>
                                <%--</div>--%>
                                <%--<div class="operator-group">--%>
                                    <%--<label><i class="icon-user"></i>创建人：</label>--%>
                                    <%--<p>${sessionScope.user.name}</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>


                    </div>
                </div>
                <%--右键功能--%>
                <ul id="right_menu">
                    <li id="copy"><i class="icon iconfont icon-fuzhi"></i>复制</li>
                    <li id="paste"><i class="icon iconfont icon-paste"></i>粘贴</li>
                    <li id="deleteLi"><i class="icon iconfont icon-cuo2"></i>删除</li>
                    <li id="lookDataResult"><i class="icon iconfont icon-chakanjieguo"></i>查看运行结果</li>
                    <li id="lookLogResult"><i class="icon iconfont icon-icon"></i>查看运行日志</li>
                    <li id="save-model"><i class="icon iconfont icon-baocun1"></i>保存为训练好的模型</li>
                    <li id="addNote"><i class="icon iconfont icon-zhushi"></i>添加注释</li>
                    <li id="field-des"><i class="icon iconfont icon-miaoshu1"></i>添加字段描述</li>
                    <li id="visualization"><i class="icon iconfont icon-miaoshu1"></i>查看数据可视化结果</li>
                </ul>
                <%--右键功能end--%>
                <!-- BEGIN RIGHT-->

                <!-- END RIGHT-->
            </div>
            <!-- END WORK-->

        </div>
        <!-- END CONTENT BODY -->
    </div>
    <div class="modal fade" id="webServiceShow" tabindex="-1" role="basic" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">web服务</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN VALIDATION STATES-->
                            <div class="portlet light portlet-fit portlet-form ">
                                <div class="portlet-body">
                                    <!-- BEGIN FORM-->
                                    <form action="javascript:;" id="webService"  method="post" class="createSpace form-horizontal">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="control-label col-md-2">服务名称：
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-10">
                                                    <input type="text" id="serviceModelName" name="serviceModelName" data-required="1" class="form-control" maxlength="30" /> </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-2">服务描述：
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-10">
                                                    <textarea id="serviceModelDescription" name="serviceModelDescription" class="workFlowInfo" rows="5" data-required="1" maxlength="150"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <button type="button" onclick="insertServiceModel()" class="btn green">提交</button>
                                                    <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <!-- END FORM-->
                                </div>
                            </div>
                            <!-- END VALIDATION STATES-->
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<!-- BEGIN CONFIG SIDEBAR -->
<%--<a href="javascript:;" class="page-quick-sidebar-toggler">--%>
    <%--<i class="icon-login"></i>--%>
<div class="page-quick-sidebar-wrapper" data-close-on-body-click="false">
    <div class="page-quick-sidebar">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet light portlet-fit portlet-form ">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-settings icon-green"></i>
                            <span id="sp" class="caption-subject common-color sbold uppercase"></span>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <!-- BEGIN FORM-->
                        <form action="#" id="configForm" class="form-horizontal">
                            <div class="form-body" id="formBody">
                            </div>
                        </form>
                        <!-- END FORM-->
                    </div>
                </div>
                <!-- END VALIDATION STATES-->
                <div class="explainDiv portlet light ">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-book-open icon-green"></i>
                            <span class="caption-subject common-color bold uppercase">说明</span>
                        </div>
                    </div>
                    <div class="explainContext portlet-body">
                        <div class="well well-large">
                            <p id="modelInfo"></p>
                        </div>
                        <div id="notesDes"><p>查看算子详细说明</p></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END CONFIG SIDEBAR -->

<%--保存模型--%>
<div class="modal fade " id="saveModelTrainFrame" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">保存模型</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form id="test" action="javascript:;"  class="updateFlow form-horizontal">
                                    <div class="form-body">
                                    <span style="color: #64848e;font-size: 14px">输入保存模型的名称</span>
                                    </div>
                                     <div class="form-body">
                                        <input type="text" class="form-control" id="modelName" placeholder="请输入模型的名称" maxlength="20" specialLimit="true">
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color" style="margin: 0 58px;" id="savetrainedmodel">确定</button>
                                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="margin: 0 58px;">取消</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--查看结果--%>
<div class="modal-nobg modal fade" id="lookDataResultFrame" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog modal-lg">
            <div class="model_back_of model_body modal-content"  >
                <div class="excel_title "  id="changeTitle" >
                    <div    data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                    <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                    <div class="excel-box" style="position: relative;    margin: 10px 19px;    width: 200px"><p>数据查看<span style="color: #64848e;font-size: 14px" id="rowCount">（显示前100条数据）</span></p></div>


                </div>
                <div class=" excel_content" id="excel_content1" style="background:#ecf0f1;padding: 0 20px 20px;">
                    <table  class=" table_look_data table table-bordered" style="background: #ffffff" >
                        <thead id="resulthead">
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                            <%--<th>222</th>--%>
                        </thead>
                        <tbody id="resultbody">
                        <tr>
                            <%--<td>1</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                            <%--<td>423</td>--%>
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    <%--<!-- /.modal-dialog -->--%>
</div>
<%--查看模型结果--%>
<div class="modal-nobg modal fade" id="lookModelResultFrame" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="model_back_of model_body modal-content"  >
            <div class="excel_title " >
                <div    data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                <div class="excel-box"  style="position: relative;    margin: 10px 19px;    width: 200px"><p>模型属性</p></div>


            </div>
            <div  id="excel_content2" style="background:#ecf0f1;padding: 0 20px 20px; border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
                <div class="excel_content model_content " style="background: #ffffff">
                    <div  id="resultmodel">

                    </div>
                </div>

            </div>
        </div>
        <%--dddddddd--%>

    </div>
    <%--<!-- /.modal-dialog -->--%>
</div>
<%--查看2个数据结果--%>
<div class="modal-nobg modal fade" id="lookTwoDataResultFrame" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg" >
        <div class="model_back_of model_body modal-content">

            <div class="excel_title " >
                <div    data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                <div class="excel-box">
                    <p style="position: relative; margin: 10px 19px;    width: 400px"><span id="lookOneData">数据查看1</span><span style="color: #64848e;font-size: 14px;margin-left: 20px" >（显示前100条数据）</span>
                        <i  class="selectDataModel icon iconfont icon-yousanjiao" style="position: absolute; top: 4px;cursor: pointer;left: 60px;"></i>
                    </p>
                    <div class="filter">
                        <ul class=" dn" >
                            <li class="filter-active"><i class="glyphicon glyphicon-ok"></i><span>数据查看1</span></li>
                            <li><i class="glyphicon glyphicon-ok"></i><span>数据查看2</span></li>
                        </ul>
                    </div>

                </div>
            </div>
            <div class="excel_content" id="excel_content_dataList" style="background:#ecf0f1;padding: 0 20px 20px;">
                <table  class=" table_look_data table table-bordered" style="background: #ffffff" >
                    <thead id="resultheadOne">
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                        <%--<th>222</th>--%>
                    </thead>
                    <tbody id="resultbodyOne">
                    <tr>
                        <%--<td>1</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                        <%--<td>423</td>--%>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--<!-- /.modal-dialog -->--%>
</div>
<%--查看数据模型结果--%>
<div class="modal-nobg modal fade" id="lookDataAndModelResultFrame" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg">

        <div class=" model_back_of model_body modal-content"  >
            <div class="excel_title " >
                <div    data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                <div class="excel-box">
                    <p style="position: relative; margin: 10px 19px;    width: 200px"><span id="titleName">查看数据</span>
                        <i class=" selectDataModel icon iconfont icon-yousanjiao" style="position: absolute; top: 4px;cursor: pointer;left: 60px;"></i>
                    </p>
                    <div class="filter">
                        <ul class=" dn" id="divDn">
                            <li class="filter-active"><i class="glyphicon glyphicon-ok"></i><span>查看数据</span></li>
                            <li><i class="glyphicon glyphicon-ok"></i><span>模型属性</span></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="changeFrame">
                <%--查看数据--%>
                <div class=" excel_content" id="lookDataDiv" style="background:#ecf0f1;padding: 0 20px 20px; border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
                    <div class="tab-pane active">
                        <table  class=" table_look_data table table-bordered" style="background: #ffffff" >
                            <thead id="resulthead1">
                                <%--<th>222</th>--%>
                                <%--<th>222</th>--%>
                            </thead>
                            <tbody id="resultbody1">
                            <tr>
                                <%--<td>1</td>--%>
                                <%--<td>423</td>--%>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%--查看模型--%>
                <div class="model_body  " id="lookModelDiv" style="background:#ecf0f1; border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
                    <div class="excel_content model_content " style="background: #ffffff">
                        <div  id="resultdata1">
                            jjjjjj
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
    <%--<!-- /.modal-dialog -->--%>
</div>
<%--查看日志结果--%>
<div class="modal-nobg modal fade" id="lookLogResultFrame" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="model_back_of model_body modal-content" id="excel_content_logs" >
            <div class="excel_title " >
                <div    data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                <div class="excel-box">
                    <p style="position: relative;    margin: 10px 19px;    width: 200px"><span id="logName">查看日志1</span>
                        <i id="selectLogs" class="icon iconfont icon-yousanjiao" style="position: absolute; top: 4px;cursor: pointer;left: 60px;"></i>
                    </p>
                    <div class="filter" id="filterDiv">
                        <ul class=" dn" id="logsList" >

                        </ul>
                    </div>

                </div>


            </div>
            <div class="excel_content model_content " style="background:#ffffff;;">
            <div  id="actionlogs"></div>
            </div>
        </div>

    </div>
</div>
<%--python编辑器--%>
<div class="modal fade bs-modal-lg" id="pyRModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title" id="pyRTitle">python(v4)</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;"   id="form_sample_1" class="createFlow form-horizontal">
                                <div style="width: 100%; height: 500px;border: 1px solid #1bbc9b;border-radius: 5px ;font-size: 14px;" wrap="virtual" id="editor1">
                                </div>
                                    <div id="pythonText" hidden>def Python_Manipulation(data_port1, model_port2, img_path):
    #compile your own Python script here with dataSource from data_port1 and/or model from model_port2
    #data_port1 or model_port2 may be None as your link state

    #transfer your output data to data_port3 and/or your output model to model_port4
    result = {"data_port3": None, "model_port4": None}
    return result
</div>
                                <!-- END FORM-->
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-11" style="    position: relative;    left: -150px;">
                                                <button type="button" class="btn btn-default font-color border-color " id="surePyR" >确认</button>
                                                <button type="button" class="btn grey-salsa btn-outline" id="closePyR" >关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--R算子编辑器--%>
<div class="modal fade bs-modal-lg" id="rModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title" >R(v4)</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;"   id="form_sample_2" class="createFlow form-horizontal">
                                    <div style="width: 100%; height: 500px;border: 1px solid #1bbc9b;border-radius: 5px ;font-size: 14px;" wrap="virtual" id="editor2">
                                    </div>
                                    <div  id="editorR" hidden># get the input file path according to the input port index.
# get the input file from the inputPort,default format is dataFrame
dataSet <- util.MapInputPort()
# get the image_path that you can save your graphic with jpeg\Cairo device etc.
image_path <- util.GetImagePath()

# load the graphic device to store the image, you can see the result from the menu.
#CairoPNG(file.path(image_path,'graphic.png'),width = 480,height = 480)
#dev.off()

# you can save your result dataSet like this below.
util.mapOutputPort(dataSet,1)
util.mapOutputPort(dataSet,2)
</div>
                                    <!-- END FORM-->
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-11" style="    position: relative;    left: -150px;">
                                                <button type="button" class="btn btn-default font-color border-color " id="sureR" >确认</button>
                                                <button type="button" class="btn grey-salsa btn-outline" id="closeR" >关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--添加分组--%>
<div class="modal fade" id="addGroupModel" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加分组</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label>输入离散区间：</label>
                                            <textarea  rows="6" style="width: 100%;    border-radius: 5px;" id="groupDiscreteInterval"></textarea>
                                            <label class="error errorTitle"></label>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row" style="padding: 0 72px;">
                                            <%--<div class="col-md-12">--%>
                                                <button type="submit" class="btn btn-default font-color border-color" id="saveAddGroup">保存</button>
                                                <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<%--</a>--%>

<!--MODAL EDIT-->
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择文件</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_4" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <span id="jiazai">加载中。。。。。</span>
                                            <input type="hidden" name="dir" value="">
                                            <div id="tree_1" class="tree-demo">
                                                <ul id="file-choice">
                                                    <%--<li data-jstree="{ 'type' : 'file' }">201611301108_outComplexModel_kmm<ul><li>空</li></ul></li>
                                                    <li data-jstree="{ 'type' : 'file' }">201611301108_outComplexModel_kmm<ul><li>空</li></ul></li>
                                                    <li data-jstree="{ 'type' : 'file' }">201611301108_outComplexModel_kmm<ul><li>空</li></ul></li>
                                                    </li>
                                                    <li data-jstree='{ "type" : "file" }'>
                                                    </li>--%>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn btn-default" id="treeBtn">选择</button>
                                                <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--MODAL EDIT-->
<div class="modal fade" id="basicEdit" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">查看工作流</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_5" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="control-label col-md-2">名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" name="name" data-required="1" class="form-control" value="${name}"/>
                                                <input type="hidden" name="workspid" data-required="1" class="form-control" value="${workFlowList.workSpaceId}"/>
                                                <input type="hidden" name="workName" data-required="1" class="form-control" value="${workFlowList.workSpaceName}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">说明
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" class="workFlowInfo" rows="5" data-required="1">${workFlow.flowExplain}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn green" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--MODALTAGS EDIT-->
<div class="modal fade" id="basicTags" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">设置标签</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_6" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="control-label col-md-2">说明
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" class="workFlowInfo" rows="5" data-required="0">此处是算子的标签说明</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn green">提交</button>
                                                <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--MODALCONFIGINFO EDIT-->
<div class="basicConfigInfo modal fade bs-modal-lg" id="basicConfigInfo" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">${name }配置总览</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <%--<div class="col-md-12">--%>
                                                <%--<button type="button" class="btn green" data-dismiss="modal">关闭</button>--%>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--动态生成字段描述输入界面--%>
<div class="R-modal modal fade bs-modal-lg" id="changeType" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">字段描述</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <table class="table table-bordered">
                                <%--<tr>--%>
                                    <%--<th>字段名</th>--%>
                                    <%--<th>类型</th>--%>
                                    <%--<th>描述</th>--%>
                                    <%--<th>标签列</th>--%>
                                <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td><span class="syl">C_1</span></td>--%>
                                        <%--<td>--%>
                                            <%--<span class="choose-type">--%>
                                                <%--<span class="syl">String</span>--%>
                                                <%--<i class="icon iconfont icon-yousanjiao"></i>--%>
                                            <%--</span>--%>
                                            <%--<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<span class="syl"></span>--%>
                                            <%--<i id="changeDes" class="icon iconfont icon-miaoshu1"></i>--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<span class="choose-type">--%>
                                                <%--<span class="syl">否</span>--%>
                                                <%--<i class="icon iconfont icon-yousanjiao"></i>--%>
                                            <%--</span>--%>
                                            <%--<ul class="dn"><li>是</li><li>否</li></ul>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                </table>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="button-position row">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default bg-color" id="saveField">确定</button>
                        <button type="button" class="btn btn-default bg-color" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END MODAL EDIT-->
<%--提取列--%>
<div class="modal fade" id="tag-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 750px;border-radius: 5px !important;margin-top: 100px">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择列</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="pass_list_select col-md-3">
                        <p id="selectListShow" class="pass_list_select_hover">通过列名选择</p>
                        <p id="selectRuleShow">通过规则选择</p>
                    </div>
                    <div  class="lineBar" ></div>
                    <div class="col-md-9" id="selectListShowDiv">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-sm-7">
                                                <div class="col-sm-5"><span style="line-height: 40px;">字段来源：</span></div>
                                                <div class="col-sm-7">
                                                    <select class="form-control" id="data-source">
                                                        <%--<option>读取CSV</option>--%>
                                                        <%--<option>2</option>--%>
                                                        <%--<option>3</option>--%>
                                                        <%--<option>4</option>--%>
                                                    </select>

                                                </div>
                                                <div class="col-sm-9">
                                                    <span style="color: #ff0000;position: relative;top: -31px;left: 292px;" id="remindGroup"></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-4">
                                                <select class="form-control" id="data-type">
                                                    <%--<option>读取CSV</option>--%>
                                                    <%--<option>2</option>--%>
                                                    <%--<option>3</option>--%>
                                                    <%--<option>4</option>--%>
                                                </select>
                                            </div>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control boxshadow fontDisColor" placeholder="请输入字段名称" id="serach-scheme">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-6">
                                                <label for="">可选择的字段</label>
                                                <div class="choice-column choice-column-left" id="preparaed-column">
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <label for="">已选择的字段</label>
                                                <div class="choice-column choice-column-right" id="selected-column">

                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                    <div class="col-md-9 dn"  id="selectRuleShowDiv">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-sm-7">
                                                <div class="col-sm-5"><span style="line-height: 40px;">字段来源：</span></div>
                                                <div class="col-sm-7">
                                                    <select class="form-control" id="data-source-rule">
                                                        <%--<option>读取CSV</option>--%>
                                                        <%--<option>2</option>--%>
                                                        <%--<option>3</option>--%>
                                                        <%--<option>4</option>--%>
                                                    </select>
                                                </div>
                                                <div class="col-sm-9">
                                                    <span style="color: #ff0000;position: relative;top: -31px;left: 292px;" id="remindGroup2"></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="choice-column-rule" >
                                            <div class="form-group">
                                                <div class="col-sm-2">
                                                   <select class="data-section-rule form-control" >
                                                      <option>正选</option>
                                                      <option>反选</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-3">
                                                    <select class="data-type-rule form-control">
                                                        <option>按索引</option>
                                                        <option>按类型</option>
                                                        <option>按列名</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-5">
                                                    <input type="text" class="inputTypeContent form-control boxshadow fontDisColor"   >
                                                </div>
                                                <div class="col-sm-1">
                                                   <i class=" icon iconfont icon-jia"></i>
                                                </div>
                                                <div class="col-sm-1">
                                                    <%--<i class=" icon iconfont icon-jian2"></i>--%>
                                                </div>
                                            </div>

                                        </div>


                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
            <div class="form-actions " id="saveDiv1" style="margin-bottom: 30px;margin-top: 25px;">
                <div class="row">
                    <div class="col-md-12" style="text-align: center;">
                        <button type="button" class="btn btn-default font-color border-color"   data-dismiss="modal" style="border-radius: 5px !important;padding: 5px 30px;"  id="tagSave"> 保存</button>
                        <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="margin-left: 60px;border-radius: 5px !important;padding: 5px 30px;">取消</button>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--列名修改--%>
<div class="column-modify modal fade" id="revise" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 654px;border-radius: 5px !important;margin-top: 100px">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择列</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-sm-7">
                                                <div class="col-sm-4">
                                                    <span style="line-height: 40px;">字段来源：</span>
                                                </div>
                                                <div class="col-sm-8">
                                                    <select class="form-control" id="reviseData">
                                                        <%--<option>读取CSV</option>--%>
                                                        <%--<option>2</option>--%>
                                                        <%--<option>3</option>--%>
                                                        <%--<option>4</option>--%>
                                                    </select>
                                                    <span style="color: #ff0000;position: relative;top: -31px;left: 352px;"></span>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="choice-column">
                                                    <div class="row field-line">
                                                        <div class="col-sm-5">
                                                            <input type="text" class="form-control" value="age">
                                                        </div>
                                                        <div class="col-sm-6">
                                                            <input type="text" class="form-control" value="age">
                                                        </div>
                                                        <div class="icon-position col-sm-1">
                                                            <i class="icon iconfont icon-jian2"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="errorMesCss" id="reviseErrorMessage">

                                        </div>

                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
            <div class="form-actions " style="margin-bottom: 30px;margin-top: 25px;">
                <div class="row">
                    <div class="col-md-12" style="text-align: center;">
                        <button type="button" class="btn btn-default font-color border-color" id="addLine"  style="border-radius: 5px !important;padding: 5px 30px;">新增</button>
                        <button type="button" class="btn btn-default font-color border-color" id="saveLine" style="border-radius: 5px !important;padding: 5px 30px;">保存</button>
                        <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="border-radius: 5px !important;padding: 5px 30px;">取消</button>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--类型转换--%>
<div class="column-modify modal fade" id="transform" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 654px;border-radius: 5px !important;margin-top: 100px">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择列</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-sm-7">
                                                <div class="col-sm-4">
                                                    <span style="line-height: 40px;">字段来源：</span>
                                                </div>
                                                <div class="col-sm-8">
                                                    <select class="form-control" id="modifyData">
                                                        <option>请选择</option>
                                                    </select>
                                                    <span style="color: #ff0000;position: relative;top: -31px;left: 352px;"></span>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="choice-column">
                                                    <%--<div class="row field-line">--%>
                                                        <%--<div class="col-sm-4">--%>
                                                             <%--<input type="text" class="form-control" value="age">--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-sm-7">--%>
                                                            <%--<select class="form-control">--%>
                                                                <%--<option>字符</option>--%>
                                                                <%--<option>字符</option>--%>
                                                                <%--<option>字符</option>--%>
                                                            <%--</select>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="icon-position col-sm-1">--%>
                                                            <%--<i class="icon iconfont icon-jian2"></i>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="errorMesCss" id="errorMessage">

                                        </div>

                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
            <div class="form-actions " style="margin-bottom: 30px;margin-top: 25px;">
                <div class="row">
                    <div class="col-md-12" style="text-align: center;">
                        <button type="button" class="btn btn-default font-color border-color" id="addTFline"  style="border-radius: 5px !important;padding: 5px 30px;">新增</button>
                        <button type="button" class="btn btn-default font-color border-color" id="saveTFline" style="border-radius: 5px !important;padding: 5px 30px;">保存</button>
                        <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="border-radius: 5px !important;padding: 5px 30px;">取消</button>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<%--提取标签列--%>
<div class="modal fade" id="tag-single-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 654px;border-radius: 5px !important;margin-top: 100px">
            <div class="modal-header modal-header2">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择标签列</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <div class="col-sm-7">
                                                <span>字段来源：</span>
                                                <select class="form-control" id="data-source1">
                                                    <%--<option>读取CSV</option>--%>
                                                    <%--<option>2</option>--%>
                                                    <%--<option>3</option>--%>
                                                    <%--<option>4</option>--%>
                                                </select>
                                                <span style="color: #ff0000;position: relative;top: -31px;left: 352px;" id="remindGroup1"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="formInput form-group has-feedback" style="margin: 0 18px;">
                                                <div  style=" border-radius: 5px">
                                                    <input type="search" class="form-control input-sm input-block input-inline workFlowName"  placeholder="输入标签列名称搜索"  id="search-name"  style="width: 100%;border-radius: 4px !important;border:1px solid #1bbc9b"/>
                                                    <i class="fa fa-search searchFlowName" style="color: #1bbc9b;float: right;position: relative;top: -26px;right: 10px;"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div style="border: 1px solid #c2cad8; border-radius: 5px; margin: 0 18px;    overflow: hidden;" >
                                                <div class="choice-column choice-column-left"  style="height: 280px;background: #ffffff" id="single-column">

                                                </div>

                                            </div>
                                        </div>


                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
            <div class="form-actions "  style="margin-bottom: 30px;margin-top: 25px;">
                <div class="row">
                    <div class="col-md-12" style="text-align: center;">
                        <button type="button" class="btn btn-default font-color border-color"   style="border-radius: 5px !important;padding: 5px 30px;"  id="saveSingleCol"> 保存</button>
                        <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="margin-left: 60px;border-radius: 5px !important;padding: 5px 30px;">取消</button>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<%--查看数据可视化结果--%>
<div class="dataVisua modal-nobg modal fade" id="dataVisualization" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="model_body modal-content" style="background: #ecf0f1">
            <div class="excel_title " >
                <div data-dismiss="modal" aria-hidden="true" style="float: right;margin-right: 15px; margin-top: -1px;cursor: pointer" ><i class="icon iconfont icon-wrong1" style="font-size: 16px"></i></div>
                <span class="enlarge_model_btn" style="float: right;" ><i class="icon iconfont icon-quanping" ></i></span>
                <div class="excel-box">
                    <p style="position: relative;    margin: 10px 19px;    width: 200px"><span>数据可视化</span></p>
                    <div class="modal-bar">
                        <div><input type="checkbox" name="allChoose" id="allChoose"></div>
                        <div class="dataVisBtn" id="imgListDownlaod"><i class="icon iconfont icon-daochu"></i>导出</div>
                        <div class="dataVisBtn" id="imgListDelete"><i class="icon iconfont icon-wrong1"></i>删除</div>
                    </div>
                </div>
            </div>
            <div class="dataVisualizationHeight model_content">
                <div class="visual-box">
                    <div class="visual-img">
                        <div class="visual-img-title"><input type="checkbox" name="allChoose"></div>
                        <div>
                            <img src="" alt="">
                        </div>
                    </div>
                    <div class="visual-img">
                        <div class="visual-img-title"><input type="checkbox" name="allChoose"></div>
                        <div>
                            <img src="" alt="">
                        </div>
                    </div>
                    <div class="visual-img">
                        <div class="visual-img-title"><input type="checkbox" name="allChoose"></div>
                        <div>
                            <img src="" alt="">
                        </div>
                    </div>
                </div>

            </div>
            <div class="model_footer" style="height: 60px">
                <input type="hidden" id="totalpage" value=""/>
                <input type="hidden" id="curPage" value=""/>
                <nav aria-label="Page navigation" class="pageCenter">
                        <ul class="pagination" id="RAndPythonPage">
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&lt;</span>
                                </a>
                            </li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-hidden="true">&gt;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>


            </div>
        </div>


    </div>
</div>

<%--阈值分组弹框--%>
<%--添加分组--%>
<div class="modal fade" id="addThresModel" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加分组</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label>输入值：</label>
                                            <textarea  rows="6" style="width: 100%;    border-radius: 5px;" id="thresholdVal"></textarea>
                                            <label class="error errorTitle"></label>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row" style="padding: 0 72px;">
                                            <%--<div class="col-md-12">--%>
                                            <button type="submit" class="btn btn-default font-color border-color" id="saveAddThre">保存</button>
                                            <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END MODAL EDIT-->
<jsp:include page="../common/schedulerForm.jsp"></jsp:include>
<jsp:include page="../common/prompt.jsp"></jsp:include>
</body>
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script>window.nav='2';var win;</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END JSPLUMB SCRIPTS -->
<script src="${basePath}/assets/global/plugins/jstree/dist/jstree.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/ui-tree.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/Chinesze.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/details.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/modelEvent.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/clickEvent.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/rightClickEvent.js" type="text/javascript"></script>
<script src="${basePath}/content/js/toastr.js"></script>
<script src="${basePath}/content/js/ops/commonSchedulerForm.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/assets/global/plugins/ace-master/grammar/piethon.js"></script>
<script src="${basePath}/assets/global/plugins/ace-master/lib/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="${basePath}/assets/global/plugins/ace-master/lib/ace/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
<%--验证--%>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/localization/messages_zh.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/detailValidate.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<%--多选--%>
<script src="${basePath}/assets/global/plugins/select2/js/select2.min.js"></script>

<%--<script src="${basePath}/content/js/dataSource/detailsLink.js" type="text/javascript"></script>--%>
</body>


<script id="calculationHistory" type="text/x-jquery-tmpl">

    <tr>
                                                   
                                                    <td> <sdw:tmpl name ="email"/> </td>
                                                    <td> <sdw:tmpl name ="way"/> </td>
                                                    <td>
                                                    {<sdwIf:if name ="if status==-1"/>}
                                                        <span style="color: #f82b2b" >失败<span>
                                                     {<sdwIf:if name ="else status==1"/>}
                                                       <span style="color: #666666" id= 'running'> 运行中...</span>
                                                     {<sdwIf:if name ="else"/>}
                                                       <span style="color: #1fad97"> 成功</span>
                                                  {<sdwIf:if name ="/if"/>}


                                                      </td>
                                                   <td> <sdw:tmpl name ="formatTime"/> </td>
    </tr>


</script>






<script type="application/javascript">
    var actionsType = '${actionInfo}';
    <%--var actionInfo=${actionMap};--%>

    <%--$(function () {--%>
        <%--connetSocket();--%>
<%--//        $("#workFlowName").tooltip();--%>
    <%--});--%>










    <%--function FileLabel(file) {--%>
        <%--this._file = file;--%>
    <%--}--%>
    <%--FileLabel.prototype = {};--%>
    <%--FileLabel.prototype.create = function () {--%>
<%--//        var file = this._file;--%>
<%--//        var isDir = file.isdir;--%>
<%--//        var fileSize = file.size;--%>
<%--//--%>
<%--//        if(isDir && fileSize != 0){--%>
<%--//            var $li = $("<li>"+file.fileName+"</li>");--%>
<%--//--%>
<%--//            var $li_default = $("<li>空</li>");--%>
<%--//            $li.append($ul.append($li_default));--%>
<%--//        }--%>
<%--//        else if (isDir){--%>
<%--//            var $li = $("<li data-jstree={ 'type' : 'folder' }>"+file.fileName+"</li>");--%>
<%--//--%>
<%--//        }--%>
<%--//        else{--%>
<%--//            var $li = $("<li data-jstree={ 'type' : 'file' }>"+file.fileName+"</li>");--%>
<%--//        }--%>
        <%--var $li = $("<li>"+file.name+"</li>");--%>
        <%--$li.attr("data-jstree", "{ 'type' : 'file' }");--%>
        <%--return $li ;--%>
    <%--};--%>


    <%--function ifRun() {--%>
        <%--$.get(basePath + "/drag/calculation/showHistory.do", function (result) {--%>
            <%--var result = JSON.parse(result);--%>
            <%--if(result.length > 0){--%>
                <%--connetSocket();--%>
                <%--$("#iconInfo").click();--%>
                <%--showCalculationHistory(result);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--function bindRun() {--%>
        <%--$(".icon-control-play").bind('click', function () {--%>
            <%--connetSocket();--%>
            <%--var result =  $.get(basePath + "/drag/calculation/run.do");--%>
            <%--$(this).removeAttr('click');--%>
            <%--result.success(function (data) {--%>
                <%--var data = JSON.parse(data);--%>
                <%--if(data.code=='417'){--%>
                    <%--alert("运行失败");--%>
                    <%--return;--%>
                <%--}else  if(data.code == '409'){--%>
                    <%--alert(data.msg);--%>
                    <%--return;--%>
                <%--}--%>

                <%--$("#iconInfo").click();--%>
                <%--showCalculationHistory(data.obj);--%>
            <%--});--%>
        <%--});--%>
    <%--}--%>


    <%--function showCalculationHistory(data) {--%>
        <%--$("#calculationHistory-content").html('');--%>
        <%--$("#calculationHistory").tmpl(data).appendTo('#calculationHistory-content');--%>
    <%--}--%>


    <%--function connetSocket() {--%>
        <%--var websocket = null;--%>
        <%--if (window['WebSocket'])--%>
            <%--websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket.do');--%>
        <%--else--%>
            <%--websocket = new new SockJS(PATH + '/websocket/socketjs');--%>

        <%--websocket.onopen = function(event) {--%>
            <%--console.log('open', event);--%>
        <%--};--%>
        <%--websocket.onmessage = function(event) {--%>

            <%--var result = JSON.parse(event.data);--%>
            <%--console.log("resultresultresult:"+event.data);--%>
            <%--$("div[ role='progressbar']").width(result.progressStatus);--%>
            <%--$("#progress-bar").html(result.progressStatus);--%>
            <%--if(result.jobId == $("#jobId").val()){--%>
                <%--$("#wellConsole").html(result.consoleMsg);--%>
            <%--}--%>
            <%--var data = result.consoleMsg;--%>
            <%--if(data.indexOf("fail") > 0 ){--%>
                <%--$("#running").html("<span style='color: #f82b2b' >失败<span>")--%>
            <%--}--%>

            <%--var datas=data.split("<p");--%>
           <%--for(var i=0;i<datas.length;i++){--%>
               <%--if(datas.length>2) {--%>
                   <%--for (var j = 0; j < configObj.length; j++) {--%>
                           <%--if (datas[i + 2].indexOf(configObj[j].BlockId) > 0 && datas[i + 2].indexOf("运行成功") > 0) {--%>
                               <%--$("#"+configObj[j].BlockId+" .boxBody").removeClass("fail-type run-type");--%>
                               <%--var n=$("#"+configObj[j].BlockId+" .boxBody").hasClass("success-type");--%>
                               <%--if(!n){--%>
                                   <%--$("#"+configObj[j].BlockId+" .boxBody").addClass("success-type");--%>
                               <%--}--%>
                               <%--console.log("-----------------------------" + configObj[j].BlockName + ":运行成功 ---------------------------");--%>
                               <%--break;--%>
                           <%--} else if (datas[i + 2].indexOf(configObj[j].BlockId) > 0 && datas[i + 2].indexOf("运行出错") > 0) {--%>
                               <%--$("#"+configObj[j].BlockId+" .boxBody").removeClass("success-type run-type");--%>
                               <%--var n=$("#"+configObj[j].BlockId+" .boxBody").hasClass("fail-type");--%>
                               <%--if(!n){--%>
                                   <%--$("#"+configObj[j].BlockId+" .boxBody").addClass("fail-type");--%>
                               <%--}--%>
                               <%--console.log("------------------------------" + configObj[j].BlockName + ":运行出错 ----------------------------");--%>
                               <%--break;--%>
                           <%--} else  if (datas[i + 2].indexOf(configObj[j].BlockId) > 0 && datas[i + 2].indexOf("开始启动") > 0) {--%>
                               <%--$("#"+configObj[j].BlockId+" .boxBody").removeClass("success-type fail-type");--%>
                               <%--var n=$("#"+configObj[j].BlockId+" .boxBody").hasClass("run-type");--%>
                               <%--if(!n){--%>
                                   <%--$("#"+configObj[j].BlockId+" .boxBody").addClass("run-type");--%>
                               <%--}--%>

                               <%--console.log("------------------------------" + configObj[j].BlockName + ":运行中... ----------------------------");--%>
                               <%--break;--%>
                           <%--}--%>
                   <%--}--%>
               <%--}--%>
           <%--}--%>
            <%--// alert(result.consoleMsg);--%>
            <%--if(result.progressStatus == "100%" ){--%>
                <%--$("#running").html("成功")--%>
            <%--}--%>
        <%--};--%>
    <%--}--%>
    <%--//    成功、失败状态数组--%>
    <%--var allType=[];--%>
    <%--var success=[];--%>
    <%--var fail=[];--%>
    <%--function onloadType() {--%>
        <%--var actionInfo='${actionInfo}';--%>
        <%--var actiondatas=actionInfo.split("/");--%>
        <%--var workFlow = JSON.parse($("#returnData").text());--%>
        <%--var obj=JSON.parse(workFlow.models);--%>
        <%--for(var i=0;i<actiondatas.length;i++){--%>
            <%--var datas=actiondatas[i].split(":");--%>
            <%--for(var j=0;j<obj.length;j++){--%>
                <%--if(datas[0]==obj[j].BlockId){--%>
<%--//                ERROR  OK--%>
                    <%--if(datas[1]=="ERROR"){--%>
                        <%--fail.push(datas[0]);--%>
                        <%--allType.push(datas[0]);--%>
                    <%--}else if(datas[1]=="OK"){--%>
                        <%--success.push(datas[0]);--%>
                        <%--allType.push(datas[0]);--%>
                    <%--}--%>

                <%--}--%>
            <%--}--%>
        <%--}--%>
        <%--console.log("success+"+success);--%>
        <%--console.log("fail+"+fail);--%>
        <%--$.each(success,function (i,value) {--%>
            <%--$("#"+value+" .boxBody").addClass("success-type");--%>
        <%--});--%>
        <%--$.each(fail,function (i,value) {--%>
            <%--$("#"+value+" .boxBody").addClass("fail-type");--%>
        <%--})--%>
    <%--}--%>





</script>
<script>
    $(function () {
        $("h3.ui-draggable").addClass('k_suanzi-width')
    })
</script>
</html>