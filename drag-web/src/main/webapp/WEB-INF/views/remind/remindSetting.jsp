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
    <title>可视化数据处理分析平台 | 提醒设置</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/remind/remindSetting.css">

    <%--<script type="application/javascript">--%>
        <%--var nodeList=${nodeList}--%>
    <%--</script>--%>
    <style>
        .remindError{
            margin: 0;
        }
    </style>
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
        <%--<jsp:include page="flie_menu.jsp" flush="true"/>--%>
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <%--<h4 class="title">提醒设置</h4>--%>
            <div class="page-bar shadowRadius">
                <div style="float: left">
                    <span style="font-size: 18px;" class="font-color">提醒设置</span><span class="line">|</span>
                    <div class="creat_model" id="addRemind-icon"><i class="remindIcon remindAdd"></i><span>添加提醒</span></div>
                    <div class="creat_model" id="deleteRemind-icon"><i class="remindIcon deleteTipConfig"></i><span>删除提醒</span></div>
                    <%--<span id="addRemind-icon" class="remindIcon remindAdd"></span><span class="remindIcon deleteTipConfig" id="deleteRemind-icon"></span>--%>
                </div>
                <div class="search-common">
                    <div class="search-icon"> <i class="fa fa-search" onclick="showTipConfig()"></i></div>
                    <div class="search-input">
                        <input value="" id="tipName" placeholder="搜索..." />
                    </div>

                </div>
                <%--</ul>--%>
            </div>
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-success progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
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
                                    <table class="table table-striped table-hover table-checkable order-column dataTable no-footer" id="sample_2" role="grid" style="table-layout: fixed">
                                        <thead>
                                        <tr role="row">
                                            <th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" width="5%">
                                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                    <input class="group-checkable" data-set="#sample_2 .checkboxes" type="checkbox">
                                                    <span></span>
                                                </label>
                                            </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="20%"> 任务名称 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="27%"> 提醒名称 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="16%"> 创建时间 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="8%"> 提醒方式 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="14%"> 接收人 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="10%"> 提醒类型 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="flowWorkTbody">
                                        <%--<c:forEach items="${TipConfigList}" var="TipConfigList">--%>


                                        <%--<tr class="odd gradeX">--%>
                                            <%--<td>--%>
                                                <%--<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">--%>
                                                    <%--<input name="checkbox" class="checkboxes"  type="checkbox"><span></span>--%>
                                                <%--</label>--%>
                                            <%--</td>--%>
                                            <%--<td class="remind_name">${TipConfigList.schJobName}</td>--%>
                                            <%--<td>${TipConfigList.tipConfigName}</td>--%>
                                            <%--<td class="hasAction">--%>
                                                <%--<div class="actions">--%>
                                                    <%--<a href="javascript:;" data-toggle="modal" onclick="" title="关闭提醒">--%>
                                                        <%--<i class="icon-eyeglasses"></i>--%>
                                                    <%--</a>--%>
                                                    <%--<a href="javascript:;" class="editor" title="编辑">--%>
                                                        <%--<i class="icon-note"></i>--%>
                                                    <%--</a>--%>
                                                    <%--<a href="javascript:;" class="deleteRemindIcon" title="删除文件">--%>
                                                        <%--<i class="icon-close"></i>--%>
                                                    <%--</a>--%>
                                                <%--</div>--%>
                                                <%--<span>${TipConfigList.tipConfigCreateTimes}</span>--%>
                                            <%--</td>--%>
                                            <%--<td>${TipConfigList.sendType}</td>--%>
                                            <%--<td>${TipConfigList.tipConfigReceiver}</td>--%>
                                            <%--<td>${TipConfigList.configType}</td>--%>
                                        <%--</tr>--%>

                                        <%--</c:forEach>--%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>


                    </div>

                </div>
            </div>
            <!-- END TAB PORTLET-->

            <%--分页--%>
            <div class="col-md-12">
                <input type="hidden"  id="total" value="${pageBean.totalPage}">
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

        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<%--删除文件--%>
<div class="modal fade" id="deleteRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除文件</h4>
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
                                        <p id="deleteMsg" class="font-orange text-center" style="font-size: 16px;">确认删除<span id="remind-name"></span>提醒吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn font-orange border-orange btn-default" id="realdelete">确定</button>
                                                <button type="button" class="btn-de btn font-orange border-orange btn-default" data-dismiss="modal">取消</button>
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
<%--提醒选中提醒--%>
<div class="modal fade" id="checkRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">选择提醒</h4>
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
                                        <p class="font-color text-center" style="font-size: 16px;">请先选择任务？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <%--<button type="button" class="btn-de btn btn-default font-orange border-orange" id="">确定</button>--%>
                                                <button type="button" class="btn btn-default border-color font-color" data-dismiss="modal">确定</button>
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
<!-- END FOOTER -->
<%--添加提醒设置--%>
<div class="modal fade" id="addRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" id="create_new_link">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeform()"></button>
                <h4 class="modal-title">提醒设置</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="parame_form" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">提醒名称：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="名称长度不超过20个字符" id="resource_databaseName" required onblur="checkRemindName()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">任务名称：</label>
                                            <div class="task col-sm-7">
                                                <div class="task_name" id="taskName" onblur="checkRemindTaskName()">
                                                    <%--<span data-id="1">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                    <%--<span data-id="2">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                </div>
                                                <div class="task-name-ul">
                                                    <ul class="" id="shadowRadius">
                                                        <%--<li data-id="1">菁蓉数据分析1</li>--%>
                                                        <%--<li data-id="2">数据分析2</li>--%>
                                                        <%--<li data-id="3">菁蓉分析3</li>--%>
                                                        <%--<li data-id="4">菁据分析4</li>--%>
                                                        <%--<li data-id="5">菁蓉数析5</li>--%>
                                                        <%--<li data-id="6">菁蓉数析6</li>--%>
                                                        <%--<li data-id="7">菁蓉数析7</li>--%>
                                                    </ul>
                                                </div>

                                            </div>

                                        </div>

                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">接收人邮箱：</label>
                                            <div class="col-sm-7">
                                                <input type="email" class="form-control" placeholder="132352462@qq.com" id="resource_hostIp" required onblur="checkRemindhostIp()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">调试模式：</label>
                                            <div class="col-sm-7">
                                                <label class="radio-inline">
                                                    <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="0" checked="checked"> 出错
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="1"> 完成
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">内容：</label>
                                            <div class="col-sm-7">
                                                <textarea class="form-control" id="centent" rows="10" required onblur="checkRemindcentent()"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default border-color font-color" onclick="addTipConfig()" >确定</button>
                                                <button type="button" class="btn btn-default border-color font-color" data-dismiss="modal">取消</button>
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
<%--修改提醒设置--%>
<div class="modal fade" id="updateRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" id="update_new_link">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeform()"></button>
                <h4 class="modal-title">修改提醒设置</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="update_form" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">提醒名称：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="" id="update_databaseName" onblur="UpcheckRemindName()">
                                                <input class="form-control" type="hidden" id="tipConfigId">
                                                <input class="form-control" type="hidden" id="update_schJobId">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">任务名称：</label>
                                            <div class="task col-sm-7">
                                                <div class="task_name1" id="updateTaskName">
                                                    <%--<span data-id="1">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                    <%--<span data-id="2">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                </div>
                                                <div class="task-name-ul">
                                                    <ul class="" id="update_ShadowRadius">
                                                        <%--<li data-id="1">菁蓉数据分析1</li>--%>
                                                        <%--<li data-id="2">数据分析2</li>--%>
                                                        <%--<li data-id="3">菁蓉分析3</li>--%>
                                                        <%--<li data-id="4">菁据分析4</li>--%>
                                                        <%--<li data-id="5">菁蓉数析5</li>--%>
                                                        <%--<li data-id="6">菁蓉数析6</li>--%>
                                                        <%--<li data-id="7">菁蓉数析7</li>--%>
                                                    </ul>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">接收人邮箱：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="132352462@qq.com" id="update_Resource_hostIp" onblur="UpcheckRemindhostIp()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">调试模式：</label>
                                            <div class="col-sm-7">
                                                <label class="radio-inline">
                                                    <input type="radio" name="updateInlineRadioOptions" id="update_InlineRadio1" value="0"> 出错
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="updateInlineRadioOptions" id="update_InlineRadio2" value="1"> 完成
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">内容：</label>
                                            <div class="col-sm-7">
                                                <textarea class="form-control" id="update_Centent" rows="10" onblur="UpcheckRemindcentent()"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default border-color font-color" onclick="updateTipConfig()" >确定</button>
                                                <button type="button" class="btn btn-default border-color font-color" data-dismiss="modal">取消</button>
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
<script>window.nav='8'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/content/js/remind/remindSetting.js" type="text/javascript"></script>
<script>
    $(function () {
        $('#taskName').on('click',function () {
            var taskHeight=$('#taskName').css('height');
            $('.task .task-name-ul').css('top',taskHeight)
        })

    })
</script>
</body>
</html>
