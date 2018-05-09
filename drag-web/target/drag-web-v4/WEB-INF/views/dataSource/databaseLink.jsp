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
    <title>可视化数据处理分析平台 | 数据库连接</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/databaseLink.css">

    <script>
        var treelist=${treelist}
    </script>
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
        <jsp:include page="/components/navi/database_menu_left.jsp" flush="true"/>
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <div class="dataBg">
                <img src="${basePath}/content/images/datalink/dataBg.png" alt="没有数据">
            </div>

            <div class="row" id="dataBox">
                <div class="col-md-12" id="data-message">
                    <div id="tableMes">
                        <table class="table table-bordered text-center" id="table_content">
                            <%--<thead>--%>
                            <%--<tr>--%>
                            <%--<th width=""> ID<br/><span>int</span> </th>--%>
                            <%--<th width=""> NAME<br/><span>string</span> </th>--%>
                            <%--<th width=""> COMPANY<br/><span>string</span> </th>--%>
                            <%--<th width=""> PUNISHMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--<th width=""> ENFORCEMENT<br/><span>double</span> </th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>75012</td>--%>
                            <%--<td>Dorothy wilk</td>--%>
                            <%--<td>大邑县新城医院</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>1</td>--%>
                            <%--<td>008874263-B-026-000</td>--%>
                            <%--</tr>--%>
                            <%--</tbody>--%>
                        </table>
                    </div>

                </div>
                <%--<div class="col-md-12 page">--%>
                    <%--<div style="float: left;color: #7f8fa4;height: 38px;line-height: 35px">Page 1 of <span class="pagination-panel-total">15</span></div>--%>
                    <%--<nav aria-label="Page navigation" style="float: right">--%>
                        <%--<ul class="pagination">--%>
                            <%--<li>--%>
                                <%--<a href="#" aria-label="Previous">--%>
                                    <%--<span aria-hidden="true">&lt;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                            <%--<li class="active"><a href="#">1</a></li>--%>
                            <%--<li><a href="#">2</a></li>--%>
                            <%--<li><a href="#">3</a></li>--%>
                            <%--<li><a href="#">4</a></li>--%>
                            <%--<li><a href="#">5</a></li>--%>
                            <%--<li>--%>
                                <%--<a href="#" aria-label="Next">--%>
                                    <%--<span aria-hidden="true">&gt;</span>--%>
                                <%--</a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</nav>--%>
                <%--</div>--%>
            </div>
                    <!-- END TAB PORTLET-->
        </div>
    </div>
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<!--MODAL EDIT-->
<%--删除数据源连接--%>
<div class="modal fade" id="deleteData" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog modal-custom-delete">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">删除连接</h4>
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
                                        <p class="font-orange text-center" style="font-size: 16px;">删除连接后将不再显示此连接的数据源！确定要删除连接吗？</p>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn-de btn btn-default font-orange border-orange" onclick="confirmDelLink()">确定</button>
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
<!--MODAL EDIT-->
<%--&lt;%&ndash;数据源连接&ndash;%&gt;--%>
<jsp:include page="linkModel.jsp"></jsp:include>
<%--<div class="modal fade" id="dataLink" tabindex="-1" role="basic" aria-hidden="true" data-backdrop="static">--%>
    <%--<div class="modal-dialog">--%>
        <%--<div class="modal-content" id="create_new_link">--%>
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeform()"></button>--%>
                <%--<h4 class="modal-title">数据库连接</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--<div class="row">--%>
                    <%--<div class="col-md-12">--%>
                        <%--<!-- BEGIN VALIDATION STATES-->--%>
                        <%--<div class="portlet light portlet-fit portlet-form ">--%>
                            <%--<div class="portlet-body">--%>
                                <%--<!-- BEGIN FORM-->--%>
                                <%--<form action="javascript:;" id="parame_form" class="updateFlow form-horizontal">--%>
                                    <%--<div class="form-body">--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">类型：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<select class="form-control" id="resource_type">--%>
                                                    <%--<option>mysql</option>--%>
                                                    <%--<option>oracle</option>--%>
                                                    <%--<option>hive</option>--%>
                                                <%--</select>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">连接名：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="text" class="form-control" placeholder="" id="resource_linkname">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">数据库名：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="text" class="form-control" placeholder="" id="resource_databaseName">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">主机名或IP地址：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="text" class="form-control" placeholder="例：192.168.1.95" id="resource_hostIp">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">端口：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="text" class="form-control" placeholder="例：3306" id="resource_port">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">用户名：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="text" class="form-control" placeholder="Email" id="resource_username">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group">--%>
                                            <%--<label for="" class="col-sm-4 control-label">密码：</label>--%>
                                            <%--<div class="col-sm-7">--%>
                                                <%--<input type="password" class="form-control" placeholder="Email" id="resource_password">--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<textarea id="ta_exception" rows="7" disabled="disabled"></textarea>--%>
                                    <%--<label id="la_errorinfo"></label>--%>
                                    <%--<div class="form-actions">--%>
                                        <%--<div class="row">--%>
                                            <%--<div class="col-md-12">--%>
                                                <%--<button type="submit" class="btn btn-default font-color border-color" id="connect_test" onclick="LinkDataBaseTest()">测试</button>--%>
                                                <%--<button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" onclick="cancleLink()">取消</button>--%>
                                                <%--<button type="button" class="btn btn-default font-color border-color" id="connect_save" onclick="saveLink()" value="addNodes">保存</button>--%>
                                            <%--</div>--%>
                                            <%--<div class="col-md-12">--%>
                                                <%--<p>注：支持连接My SQL5.6.16-log及以下版本</p>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</form>--%>
                                <%--<!-- END FORM-->--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<!-- END VALIDATION STATES-->--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<!-- /.modal-content -->--%>
    <%--</div>--%>
    <%--<!-- /.modal-dialog -->--%>
<%--</div>--%>
<%--<!-- END MODAL EDIT-->--%>
<!--MODAL EDIT-->
<%--查看连接信息--%>
<div class="modal fade" id="dataMessage" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">连接信息</h4>
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
                                        <div class="message-group">
                                            <span>连接名：</span>
                                            <span id="linkName" class="t_inline-nowrap">银行客户数据</span>
                                        </div>
                                        <div class="message-group">
                                            <span>类型：</span>
                                            <span id="linkType" class="t_inline-nowrap">Oracle</span>
                                        </div>
                                        <div class="message-group">
                                            <span>数据库名：</span>
                                            <span id="linkDbName" class="t_inline-nowrap">table123</span>
                                        </div>
                                        <div class="message-group">
                                            <span>主机名或IP地址：</span>
                                            <span id="linkIp" class="t_inline-nowrap">10.0.3.213</span>
                                        </div>
                                        <div class="message-group">
                                            <span>端口：</span>
                                            <span id="linkPort" class="t_inline-nowrap">1521</span>
                                        </div>
                                        <div class="message-group">
                                            <span>用户名：</span>
                                            <span id="linkUser" class="t_inline-nowrap">hsdb</span>
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
<%--新建文件夹--%>
<div class="modal fade" id="addFile" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
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
                                        <input type="text" class="form-control" id="fileName" placeholder="请输入文件夹名称,长度最多20个字符">
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color" id="confirm">确定</button>
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
<%--提示信息--%>
<jsp:include page="../common/prompt.jsp"></jsp:include>
<%--end--%>
<script>window.nav='3'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/content/js/dataSource/databaseLink.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/databaselink.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
</body>

<script>
    $(window).on("load",function() {
        // zTree添加滚动条
        $("body #treeScroll").mCustomScrollbar({
            theme: "dark-thin",
            axis: "yx",
            setLeft: 0,
            scrollbarPosition: "inside",
            scrollInertia: 1,
            autoDraggerLength: false,
            alwaysShowScrollbar: 0,
            autoHideScrollbar:true,
            autoExpandScrollbar:false
        });
    });
    var total=100;
    $('.pagination-panel-total').text(total);
    $('.pagination').jqPaginator({
        totalPages: total,
        visiblePages: 4,
        currentPage: 1,
        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            $('#new_num').html(num);
        }
    });
//    滚动条
//    $(window).on("load",function(){
//        $("body #data-message").mCustomScrollbar({
//            axis:"yx",
//            setLeft: 0,
//            scrollbarPosition: "outside",
//            advanced:{
//                updateOnContentResize:true
//            }
//        });
//    })
</script>

<script>
    $(function () {
        //'treeDemo_1' treeDemo_1_span
        var zLinkNodeNamesStatusList=[];
        zLinkNodeNames.forEach(function (item, index, array) {
            var zLinkNodeNamesStatus={};
            zLinkNodeNamesStatus['closed']=item['closed'];
            zLinkNodeNamesStatus['name']=item['name'];
            zLinkNodeNamesStatusList.push(zLinkNodeNamesStatus)
        });
        var nodeNameList=[];
        $(".node_name").each(function (index,element) {
            var nodeNameStatus={};
            var ele_text=$(element).text();
            var element_id=$(element).attr('id');
            var ele_id=element_id.match(/\d+/g)[0];
            nodeNameStatus['treeDemo_id']='treeDemo_'+ele_id;
            nodeNameStatus['treeDemo_id_ico']='treeDemo_'+ele_id+'_ico';
            nodeNameStatus['treeDemo_id_span']='treeDemo_'+ele_id+'_span';
            nodeNameStatus['treeDemo_id_a']='treeDemo_'+ele_id+'_a';
            nodeNameStatus['treeDemo_id_ul']='treeDemo_'+ele_id+'_ul';
            nodeNameStatus['treeDemo_id_switch']='treeDemo_'+ele_id+'_switch';
            nodeNameStatus['ele_text']=ele_text;
            nodeNameList.push(nodeNameStatus)
        })
        console.log(nodeNameList)

        zLinkNodeNamesStatusList.forEach(function (item1, index1, arr1) {
            nodeNameList.forEach(function (item2, index2, arr2) {
                if(item1['name']==item2['ele_text']){
                    if(item1['closed']==true){
                        console.log(11);
                        var m=item2['treeDemo_id_a'];
                        $("#"+m).addClass('close_icon');
                        var treeDemo_id_switch = item2['treeDemo_id_switch'];
                        $("#"+treeDemo_id_switch).removeClass("noline_close")
                        $("#"+treeDemo_id_switch).addClass("noline_open")

                    }else {
                        console.log(222)
                    }
                }
            })
        });
        sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
        sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList))
    })


</script>

</html>
