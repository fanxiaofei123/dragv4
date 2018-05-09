<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/9
  Time: 15:50
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
    <title>可视化数据处理分析平台 | 工作空间</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/workSpace/workSpaceList.css">
</head>
<!-- END HEAD -->
<script type="application/javascript">
    var nodeList=${nodeList}

</script>

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
        <jsp:include page="workSpaceTree.jsp" flush="true"/>
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <!-- BEGIN PAGE HEADER-->

            <div class="page-bar shadowRadius">
                        <input id="PageWorkspid" class="font-color" type="hidden"></input>
                        <input id="page" class="font-color" type="hidden" ></input>
                        <%--<input id="PageWorkspid" class="font-color" ></input>--%>
                        <%--<input id="page" class="font-color"></input>--%>
                        <div style="float: left">

                            <div style="float: left"> <span id="titleFlowName" class="font-color"></span><span class="line">|</span></div>

                            <div style="float: left"><div class="chooseNum">
                                <span class="chooseNum-des" style="color:#7f8fa4;font-size: 14px"><span id="choose-num" style="font-size: 14px">5</span> <i class="glyphicon glyphicon-triangle-bottom font-color" style="font-size: 14px"></i> row</span>

                                <ul class="shadowRadius">
                                    <li><i class="glyphicon glyphicon-ok"></i>5</li>
                                    <li><i class="glyphicon"></i>10</li>
                                    <li><i class="glyphicon"></i>15</li>
                                </ul>

                            </div></div>

                            <div style="float: left;padding-top: 5px">
                                <a  onclick="addWorkDetails()"  class="creat_model_work"><span class="relative_span"><i class="icon_up_down icon iconfont icon-jia4" ></i><span class="font_size_span">新建工作流</span></span></a>
                                <a id="batchDelFlow" class="creat_model_work_color" ><span class="relative_span"><i class="icon_up_down icon iconfont icon-wrong1" ></i><span class="font_size_span">删除工作流</span></span></a>
                                <a  onclick="exportFlows()"  class="creat_model_work_color"><span class="relative_span"><i class="icon_up_down icon iconfont icon-xiajian" ></i><span class="font_size_span">导出</span></span></a>
                                <a  href="#ImportFlows" data-toggle="modal" class="creat_model_work_color" ><span class="relative_span"><i class="icon_up_down icon iconfont icon-shang1" ></i><span class="font_size_span">导入</span></span></a>

                            </div>

                        </div>
                        <div style="float: right">
                            <div class="search-common">
                                <div class="search-icon"><i onclick="selectFlowByName()" class="fa fa-search" style="margin-right: -2px"></i></div>
                                <div class="search-input">
                                    <input id="searchName"  type="text" placeholder="搜索..."/>
                                </div>
                            </div>

                        </div>
            </div>
            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <!-- BEGIN TAB PORTLET-->
                    <div class="portlet portlet-fit portlet-datatable">
                        <div class="portlet-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="portlet_tab1">
                                    <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                        <thead>
                                        <tr>
                                            <th class="table-checkbox" width="5%">
                                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                    <input type="checkbox" class="group-checkable" data-set="#sample_2 .checkboxes" />
                                                    <span></span>
                                                </label>
                                            </th>
                                            <th width="80%"> 工作流名 </th>
                                            <th width="15%"> 修改时间 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="flowWorkTbody">
                                        </tbody>
                                    </table>
                                    <div style="text-align:right" class="dataTables_paginate paging_bootstrap_extended" id="sample_2_paginates2">
                                    </div>
                                </div>
                                        </tbody>
                                    </table>
                                    <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="sample_2_paginatese">
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <!-- END TAB PORTLET-->
                </div>
                <%--分页--%>
                <div class="page col-md-12">
                    <input type="hidden" id="totalpage" value=""/>
                <div style="float: left;color: #7f8fa4;height: 38px;line-height: 35px">Page <span id="new_num">1</span> of <span class="pagination-panel-total">15</span></div>
                <nav aria-label="Page navigation" style="float: right">
                    <ul class="pagination">
                    </ul>
                </nav>
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
<!--MODAL EDIT-->
<%--删除文件--%>
<div class="modal fade" id="deleteData" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom-delete">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;"  class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deleteMsg" class="font-gray text-center" style="font-size: 16px;">确认删除吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button  onclick="deleteWorkSpace()" class="btn-de btn btn-default font-orange border-orange">确定</button>
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" data-dismiss="modal">取消</button>
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
<!-- END MODAL EDIT-->
<!--MODAL NEW-->
<%--创建新的工作流--%>
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">创建新的工作流</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;"  id="form_sample_1" class="createFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="control-label col-md-2">名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" id="nameWork" name="name" data-required="1" class="form-control"  placeholder="名称长度最多20个字符"/>
                                                <input type="hidden" id="workspid" name="workspid" data-required="1" class="form-control"/>
                                                <input type="hidden" id="workName" name="workName" data-required="1" class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">说明
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" id="flowExplainAA" class="workFlowInfo" rows="5" data-required="1" class="form-control" maxlength="150"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">位置
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <div class="treeDeomBox">
                                                    <ul id="treeDemo2" class="ztree"></ul>
                                                </div>
                                                <p class="title_text selectMag" style="color: red"></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-1"></div>
                                            <div class="col-md-11">
                                                <button type="submit" class="btn btn-default font-color border-color">提交</button>
                                                <button type="button" class="btn grey-salsa btn-outline" onclick="cleanData()" data-dismiss="modal">关闭</button>
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
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">修改工作流</h4>
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
                                            <label class="control-label col-md-2">工作流名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" id="workSpaceFlowName" name="name" data-required="1" class="form-control" maxlength="30"/>
                                                 <input type="hidden" name="workspid" id="worksFlowPid" data-required="1" class="form-control" />
                                                <input type="hidden" name="id" id="workFlowIds" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">工作流说明
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" id="flowExplainName" class="workFlowInfo" rows="5" data-required="1" class="form-control" maxlength="150"></textarea>
                                                <input type="hidden" name="id" id="workFlowInfo" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-1"></div>
                                            <div class="col-md-11">
                                                <button type="submit" class="btn btn-default font-color border-color">提交</button>
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
<div class="modal fade" id="showbasicEdit" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">查看工作流</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="showform_sample_4" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="control-label col-md-2">名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" id="showworkSpaceFlowName" name="name" data-required="1" class="form-control" maxlength="30"/>
                                                 <input type="hidden" name="workspid" data-required="1" class="form-control" value="${WorkFlow.workspid}"/>
                                                <input type="hidden" name="id" id="showworkFlowIds" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">说明
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" id="showflowExplainName" class="workFlowInfo" rows="5" data-required="1" class="form-control" maxlength="150"></textarea>
                                                <input type="hidden" name="id" id="showworkFlowInfo" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-1"></div>
                                            <div class="col-md-11">
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
<div class="modal fade" id="basicInfo" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">工作空间描述</h4>
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
                                            <label class="control-label col-md-2">描述
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="createContent" class="workFlowInfo" rows="5" data-required="1" disabled >${WorkFlow.createContent }</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
<%--工作流导入--%>
<div class="modal fade" id="ImportFlows" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">工作流导入</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                               <form id="fileUpload" action="#" enctype="multipart/form-data" class="upload-forms">
                                    <div class="form-body">
                                        <input id="excelFile" name="file" type="file" accept="text/xml, application/xml"/>
                                         <input type="hidden" name="workspid" data-required="1" id="inputWorksPid" class="form-control" value="${WorkFlow.workspid}"/>
                                        <!-- <input type="button" value="提交" onclick="submitExcel()"/> -->
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color" onclick="submitExcel()" >提交</button>
                                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal">关闭</button>
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
<%--右键新增功能--%>
<%--新建文件夹--%>
<div class="modal fade addNewFile" id="addFile" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <input type="text" class="form-control" id="fileName" placeholder="请输入文件夹名称,最多20个字符长度" maxlength="20">
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color" onclick="confirm()" id="confirm">确定</button>
                                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal">取消</button>
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
<!-- END MODAL EDIT-->
<!--批量删除工作流-->
<div class="modal fade" id="deleteWorkSpaces" tabindex="-1"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除工作流</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deleteTmpMsg" class="font-orange text-center" style="font-size: 16px;">确认删除工作流吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" id="realdelete" onclick="deleWorkSpaces()">确定</button>
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" data-dismiss="modal">取消</button>
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
<!--删除工作流-->
<div class="modal fade" id="deleteWorkSpace" tabindex="-1"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除工作流</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deletTmpMsg" class="font-orange text-center" style="font-size: 16px;">确认删除工作流吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange"  onclick="deleSingleWorkSpace()">确定</button>
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" data-dismiss="modal">取消</button>
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

<%--提示信息--%>
<jsp:include page="../common/prompt.jsp"></jsp:include>

<script>window.nav='2'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/workSpaceDetails.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}/content/js/workSpace/workSpaceTree.js"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
</body>
</html>
