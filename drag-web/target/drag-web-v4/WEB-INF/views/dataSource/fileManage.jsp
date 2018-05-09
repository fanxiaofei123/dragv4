<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/9
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 文件管理</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/fileMange.css">
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/dropzone.css">

    <script type="application/javascript">
        var nodeList=${nodeList}

    </script>
</head>
<%--<style type="text/css">
    .demo{
        padding: 2em 0;
        background: linear-gradient(to right, #2c3b4e, #4a688a, #2c3b4e);
    }
    .progress{
        height: 25px;
        background: #262626;
        padding: 5px;
        overflow: visible;
        border-radius: 20px;
        border-top: 1px solid #000;
        border-bottom: 1px solid #7992a8;
        margin-top: 50px;
    }
    .progress .progress-bar{
        border-radius: 20px;
        position: relative;
        animation: animate-positive 2s;
    }
    .progress .progress-value{
        display: block;
        padding: 3px 7px;
        font-size: 13px;
        color: #fff;
        border-radius: 4px;
        background: #191919;
        border: 1px solid #000;
        position: absolute;
        top: -40px;
        right: -10px;
    }
    .progress .progress-value:after{
        content: "";
        border-top: 10px solid #191919;
        border-left: 10px solid transparent;
        border-right: 10px solid transparent;
        position: absolute;
        bottom: -6px;
        left: 26%;
    }
    .progress-bar.active{
        animation: reverse progress-bar-stripes 0.40s linear infinite, animate-positive 2s;
    }
    @-webkit-keyframes animate-positive{
        0% { width: 0; }
    }
    @keyframes animate-positive{
        0% { width: 0; }
    }
</style>--%>
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
        <jsp:include page="flieMenu.jsp" flush="true"/>
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <div class="page-bar shadowRadius">
                <div style="float: left">
                    <span style="font-size: 18px;" class="font-color">文件列表</span>
                    <span class="line">|</span>
                    <div class="chooseNum">
                        <span class="chooseNum-des" style="color:#7f8fa4;"><span id="choose-num">5</span> <i class="glyphicon glyphicon-triangle-bottom font-color"></i> row</span>
                        <%--<div>--%>
                        <ul class="shadowRadius">
                            <li><i class="glyphicon glyphicon-ok"></i>5</li>
                            <li><i class="glyphicon"></i>10</li>
                            <li><i class="glyphicon"></i>15</li>
                        </ul>
                        <%--</div>--%>
                    </div>
                </div>
                <div style="float: right">
                    <%--<span id="checkText">已选中<span id="checkNum">5</span>个文件</span>--%>
                    <a href="#basic" data-toggle="modal" class="creat_model haveCheckBox" id="deleteConfirm" onclick="deleteConfirm()">批量删除 -</a>
                        <span class="line">|</span>
                        <div class="search-common" style="width: 242px;">
                            <div class="search-icon"><i onclick="searchFile()" class="fa fa-search" style="margin-right: -2px"></i></div>
                            <div class="search-input">
                                <input id="formInput" type="search" placeholder="输入文件名"/>
                            </div>
                        </div>
                </div>
                <%--</ul>--%>
            </div>
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-info progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
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
                                    <table class="table table-striped table-hover table-checkable order-column dataTable no-footer" id="sample_2" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" width="5%">
                                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                    <input class="group-checkable" data-set="#sample_2 .checkboxes" type="checkbox">
                                                    <span></span>
                                                </label>
                                            </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="80%"> 文件名称 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="15%"> 修改时间 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="flowWorkTbody">
                                        <c:forEach items="${pageBean.rows}" var="curCsv">
                                            <tr class="odd gradeX">
                                                <td>
                                                    <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                        <input name="checkbox" class="checkboxes" ids="286" type="checkbox"><span></span>
                                                    </label>
                                                </td>
                                                <td>
                                                    <div class="fileType"><i class="fa fa-file-code-o"></i></div>
                                                    <div class="fileContent">
                                                        <div class="fileTitle">
                                                            <a>${curCsv.path.name}</a>
                                                        </div>
                                                            <%--<div class="fileSize">属于 1038612634</div>--%>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="actions">
                                                        <a href="javascript:;" data-toggle="modal" onclick="seeData()" title="查看表数据">
                                                            <i class="icon-eyeglasses"></i>
                                                        </a>
                                                        <a href="javascript:;" data-toggle="modal" onclick="updateWorkFlow(286)" title="下载">
                                                            <i class="icon-note"></i>
                                                        </a>
                                                        <a href="javascript:;" onclick="deleteWorkFlow(286)" title="删除文件">
                                                            <i class="icon-close"></i>
                                                        </a>
                                                    </div>
                                                    <span>${curCsv.modifi_time}</span>
                                                    <input id="286" value="flow2" type="hidden">
                                                    <input id="286a" value="实打实的" type="hidden">
                                                </td>
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
            <!-- END TAB PORTLET-->

            <%--分页--%>
            <div class="col-md-12">
                <input type="hidden" id="totalpage" value=""/>
                <input type="hidden" id="curPage" value=""/>
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
<!--MODAL EDIT-->
<%--本地上传--%>
<div class="modal fade" id="addDataModal" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加数据到：</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <form action="#" id="simple_form1" enctype="multipart/form-data"  class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="addData">
                                            <ul id="treeDemo2" class="ztree"></ul>

                                            <%--<div id="dropz" class="dropzone"></div>--%>

                                        </div>

                                    </div>
                                    <input type="hidden" id="curDir1" name="currentDir" value="">
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <span class="uploadfile">
                                                    本地上传
                                                    <div class="inputButton">
                                                         <input type="file" name="file" accept=".txt,.csv" data-required="1" class="fileinput" id="file1"/>
                                                    </div>

                                                    <%--<input type="file" class="fileinput">--%>
                                                </span>
                                            </div>
                                            <div class="col-md-9">
                                                <button type="submit" class="btn btn-default font-color border-color" id="submitform">提交</button>
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
<!-- END MODAL EDIT-->
<%--删除文件--%>
<div class="modal fade" id="deleteSingleData" tabindex="-1"  aria-hidden="true">
    <div class="modal-dialog modal-custom-delete">
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
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deletjeMsg" class="font-orange text-center" style="font-size: 16px;">确认删除文件吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" onclick="deleteSingleData()">确定</button>
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
<div class="modal fade" id="deleteData" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom-delete">
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
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deleteMsg" class="font-orange text-center" style="font-size: 16px;">确认删除文件吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" id="realdelete">确定</button>
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
<div class="modal fade" id="deleteData2" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom-delete">
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
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <p id="deleteMsg2" class="font-orange text-center" style="font-size: 16px;">确认删除文件吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn-de btn btn-default font-orange border-orange" id="realdelete2" onclick="delete2()">确定</button>
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
<!-- END MODAL EDIT-->
<%--显示表数据--%>
<div class="dataExcel modal fade" id="dataExcel" tabindex="-1" role="basic" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">

        <%--<div class="excel_title">--%>
        <%--<div><span data-dismiss="modal" aria-hidden="true" class="closeExcel">&times;</span></div>--%>
        <%----%>
        <%--</div>--%>
        <div class="modal-content">
            <div class="excel_title">
                <div data-dismiss="modal" aria-hidden="true" style="width: 18px;float: right;cursor: pointer">
                    <i class="icon iconfont icon-wrong1" style="font-size: 16px"></i>
                </div>
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <div>
                    <p><span id="bodyRowCount"></span>row</p>
                </div>
                <div>
                    <p><span id="columns"></span>columns</p>
                </div>
                <%--<span style="color: #6fffe1">row     columns</span>--%>
                <%--<div style="color: #ffffff;margin-top: 10px;" >100 5</div>--%>

            </div>
            <div class="excel_content">
                <table class="table table-striped table-hover table1">
                    <%-- <thead>
                         <tr style="height: 60px">
                             <th style="vertical-align: middle; width: 580px;">任务名称</th>
                             <th style="vertical-align: middle; width: 280px;">创建人</th>
                             <th style="vertical-align: middle; width: 280px;">创建时间</th>
                             <th style="vertical-align: middle; width: 280px;">开始时间</th>
                             <th style="vertical-align: middle; width: 280px;">结束时间</th>
                             <th style="vertical-align: middle; width: 280px;">时长</th>
                             <th style="vertical-align: middle; width: 280px;">状态</th>
                             <th style="vertical-align: middle; width: 280px;">创建时间</th>
                             <th style="vertical-align: middle; width: 280px;">开始时间</th>
                             <th style="vertical-align: middle; width: 280px;">结束时间</th>
                             <th style="vertical-align: middle; width: 280px;">时长</th>
                             <th style="vertical-align: middle; width: 280px;">状态</th>
                             <th style="vertical-align: middle; width: 280px;">创建时间</th>
                             <th style="vertical-align: middle; width: 280px;">开始时间</th>
                             <th style="vertical-align: middle; width: 280px;">结束时间</th>
                             <th style="vertical-align: middle; width: 280px;">时长</th>
                             <th style="vertical-align: middle; width: 280px;">状态</th>
                         </tr>
                     </thead>--%>
                    <tbody id="tbody">
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>工业数据清洗111</td>
                        <td>lasysjsisi</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:12</td>
                        <td>2017-07-06   01:17:22</td>
                        <td>3分10秒</td>
                        <td>成功</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--<!-- /.modal-dialog -->--%>
</div>
<!-- END MODAL EDIT-->
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
                                        <input type="text" class="form-control" id="fileName" placeholder="请输入文件夹名称,长度最多20个字符" maxlength="20" autocomplete="off">
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color" id="confirm">确定</button>
                                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" onclick="unBind()">取消</button>
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
<%--提示信息--%>
<jsp:include page="../common/prompt.jsp"></jsp:include>

<script>window.nav='4'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<%--<script src="${basePath}/content/js/workSpace/workSpaceDetails.js" type="text/javascript"></script>--%>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/dataSource/dropzone.js"></script>
<script src="${basePath}/content/js/dataSource/fileManage.js"></script>
<script type="application/javascript">
    var websocket = null;
    if (window['WebSocket'])
// ws://host:port/project/websocketpath
        websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket.do');
    else
        websocket = new new SockJS(PATH + '/websocket/socketjs');

    websocket.onopen = function(event) {
        console.log('open', event);
    };
    websocket.onmessage = function(event) {

        var result = JSON.parse(event.data);
        console.log('message::::', event.data);
        var tipTimes = result.tipTimes;
        if(tipTimes != undefined){
            $("#waring").html(tipTimes);
            toastr.warning("文件已经上传到hdfs");
            $.get(basePath+'/drag/upload/addTipTimes.do',{'tips':tipTimes});
            setTimeout("location.reload();",1000);
        }
    };
</script>
</body>
</html>
