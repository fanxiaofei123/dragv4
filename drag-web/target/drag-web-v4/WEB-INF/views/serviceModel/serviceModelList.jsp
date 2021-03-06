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
    <title>可视化数据处理分析平台 | 已部署的模型服务</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/trainModel/trainOverModel.css">
</head>
<!-- END HEAD -->


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
            <!-- BEGIN PAGE HEADER-->
            <p id="titleFlowName" class="font-color">已部署的模型服务</p>
            <div class="page-bar shadowRadius">

                <div style="float: left" ><a href="#basic" data-toggle="modal" id="deleteDiv" class="creat_model" onclick="deleteModelTitle()"><i class="icon iconfont icon-cuo"></i><span>删除模型服务</span></a></div>
                        <div style="float: right">
                            <div class="search-common">
                                <div class="search-icon"><i  class="fa fa-search" style="margin-right: -2px" onclick="searchModelTrained()"></i></div>
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
                                                    <input type="checkbox" name = 'checkbox' class="group-checkable" data-set="#sample_2 .checkboxes" />
                                                    <span></span>
                                                </label>
                                            </th>
                                            <th width="15%"> 模型服务名称 </th>
                                            <th width="15%"> 部署人 </th>
                                            <th width="15%"> 部署时间 </th>
                                            <th width="20%"> 最近修改时间 </th>
                                            <th width="15%"> 操作 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="modelTrainedBody" class="model_tr">
                                        <tbody >
                                        <%--<tr class='odd gradeX'>--%>
                                            <%--<td>--%>
                                                <%--<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>--%>
                                                    <%--<input type='checkbox' name = 'checkbox' class='checkboxes'  />--%>
                                                    <%--<span></span>--%>
                                                <%--</label>--%>
                                            <%--</td>--%>
                                            <%--<td>决策回归</td>--%>
                                            <%--<td>luojun</td>--%>
                                            <%--<td>--%>
                                                <%--<div class='actions'>--%>
                                                    <%--<a href='javascript:;' data-toggle='modal'  title="停用">--%>
                                                        <%--<i class="icon iconfont icon-chakan icon-tingzhi1" ></i>--%>
                                                    <%--</a>--%>
                                                    <%--<a href='javascript:;' data-toggle='modal' onclick="renameModel()" title="重命名">--%>
                                                        <%--<i class="icon iconfont icon-chakan icon-bianji1" ></i>--%>
                                                    <%--</a>--%>
                                                    <%--<a href='javascript:;' data-toggle='modal' onclick="deleteModel()" title="删除">--%>
                                                        <%--<i class="icon iconfont icon-chakan icon-cuo2" ></i>--%>
                                                    <%--</a>--%>
                                                <%--</div>--%>
                                            <%--</td>--%>
                                            <%--<td>企业数据分析</td>--%>
                                            <%--<td>启用</td>--%>

                                        <%--</tr>--%>
                                        </tbody>
                                    </table>
                                    <div style="text-align:right" class="dataTables_paginate paging_bootstrap_extended" id="sample_2_paginates2">
                                    </div>
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
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
<%--批量删除--%>
</div><div class="modal fade" id="deleteAllModel" tabindex="-1"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">批量删除模型</h4>
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
                                        <p  class="font-orange text-center" style="font-size: 16px;">确认删除吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange"  onclick="sureDeleteModel()">确定</button>
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
<%--单个模型删除--%>
</div>
<div class="modal fade" id="deleteSingleModel" tabindex="-1"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除模型</h4>
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
                                        <p  class="font-orange text-center" style="font-size: 16px;">确认删除吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange"  onclick="sureSingleDeleteModel()">确定</button>
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
                            <input type="text" hidden id="serviceModelFlowId">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <%--<table class="table table-bordered">--%>
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
                                <%--</table>--%>
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
                        <button type="button" class="btn btn-default bg-color" id="saveField">提交</button>
                        <button type="button" class="btn btn-default bg-color" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<!--MODAL EDIT-->
<%--提示信息--%>
<jsp:include page="../common/prompt.jsp"></jsp:include>
<script>window.nav='13'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/serviceModel/serviceModel.js" type="text/javascript"></script>
</body>
<script>
    $(document).ready(function(){
        getModelTrainedList(1);
    })
</script>
</html>
