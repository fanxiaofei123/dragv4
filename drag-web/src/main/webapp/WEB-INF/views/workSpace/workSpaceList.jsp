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
    <title>可视化数据处理分析平台 | 工作空间</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/workSpace/workSpaceList.css">
</head>
<!-- END HEAD -->
<!-- BEGIN HEADER -->
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
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class=" icon-briefcase font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">工作空间列表</span>
                            </div>

                            <div class="actions">
                                <a class="btn btn-circle btn-icon-only btn-default" href="#basic" data-toggle="modal" title="创建新的工作空间">
                                    <i class="icon-plus"></i>
                                </a>
                                <a class="btn btn-circle btn-icon-only btn-default" id="batchDelwork" href="javascript:;" title="删除选中">
                                    <i class="icon-trash"></i>
                                </a>
                                <div style="float:right">
                                <input type="text" name="name" id="workNameId"/>
                                <a href="javascript:;" class="btn green" onclick="selectWorkName()">搜索 <i class="fa fa-search"></i></a>
                            </div>
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
                                    <th width="75%"> 工作空间名 </th>
                                    <th width="10%"> 创建时间 </th>
                                </tr>
                                </thead>
                                <tbody id="workSpaceTbody">
                                 <%-- <c:forEach items="${workSpaceList}" var="workSpace">
                                <tr class="odd gradeX">
                                    <td>
                                        <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                          <input type="checkbox" name = "checkbox" class="checkboxes" ids="${workSpace.id }" />
                                            <span></span>
                                        </label>
                                    </td>
                                    <td onclick="">
                                        <div class="fileType">
                                            <i class="icon-briefcase"></i>
                                        </div>
                                        <div class="fileContent">
                                            <div class="fileTitle">
                                                <a href="${basePath}/drag/flow/getflowlist.do?workspid=${workSpace.id }&workName=${workSpace.name }&page=1">${workSpace.name }</a>
                                            </div>
                                            <div class="fileSize">属于 ${workSpace.userEmail }</div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="actions">
                                            <a href="javascript:;" data-toggle="modal" onclick="updateWorkSpace(${workSpace.id })" title="设置工作空间">
                                                <i class="icon-settings"></i>
                                            </a>
                                            <a href="javascript:;" onclick="deleteWorkSpace(${workSpace.id })" title="删除工作空间">
                                                <i class="icon-trash"></i>
                                            </a>
                                        </div>
                                        <span class="label label-sm label-success"> ${workSpace.createTimes } </span>
                                    </td>
                                     <input type="hidden" id="${workSpace.id }"  value="${workSpace.name}"/>
                                     <input type="hidden" id="${workSpace.id }E"  value="${workSpace.createContent}"/>
                                </tr>
                                </c:forEach> --%>
                                </tbody>
                            </table>
                             <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="sample_2_paginate">
                               <%--  <div class="pagination-panel">
                                    <a href="${basePath}/drag/work/getworklist.do?page=${Previous }" class="btn btn-sm default prev">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                    <input type="text" class="pagination-panel-input form-control input-sm input-inline input-mini" maxlenght="5" value="${page }" style="text-align:center; margin: 0 5px;" disabled>
                                    <a href="${basePath}/drag/work/getworklist.do?page=${next }" class="btn btn-sm default next">
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                    共<span class="pagination-panel-total">${Total }</span>页
                                </div> --%>
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

<!-- END FOOTER -->
<!--MODAL NEW-->
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">创建新的工作空间</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_1"  method="post" class="createSpace form-horizontal">
                                    <div class="form-body">
                                        <!-- <div class="alert alert-danger display-hide">
                                            <button class="close" data-close="alert"></button> 表单有误，请更改后提交！ </div>
                                        <div class="alert alert-success display-hide">
                                            <button class="close" data-close="alert"></button> 表单成功提交！ </div> -->
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" name="name" data-required="1" class="form-control" maxlength="30" /> </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间描述
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="createContent" class="workFlowInfo" rows="5" data-required="1" maxlength="150"></textarea>
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
<!--MODAL EDIT-->
<div class="modal fade" id="basicEdits" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">修改工作空间</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_4"  class="updateSpace form-horizontal">
                                    <div class="form-body">
                                        <!-- <div class="alert alert-dangers display-hide">
                                            <button class="close" data-close="alert"></button> 表单有误，请更改后提交！ </div>
                                        <div class="alert alert-successs display-hide">
                                            <button class="close" data-close="alert"></button> 表单成功提交！ </div> -->
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" id="workSpaceName" name="name" data-required="1" class="form-control" maxlength="30"/> 
                                                <input type="hidden" name="id" id="workSpaceIds" />    
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间描述
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="createContent" class="workFlowInfo" id="createContenta" rows="5" data-required="1" maxlength="150"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn green" >提交</button>
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
<!--[if lt IE 9]>
<!-- END CONTAINER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>

<script>window.nav='2'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script type="application/javascript" src="${basePath}/content/js/workSpace/workSpaceList.js"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
</body>

</html>
