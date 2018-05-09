<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/11/6
  Time: 11:24
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
    <title>可视化数据处理分析平台 | 模板说明文档</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/template/templateManage.css">

</head>
<!-- END HEAD -->
<%--<script type="application/javascript">--%>
   <%--var templateDescription = ${templateDescription}--%>
<%--</script>--%>
<script type="application/javascript">
   var workFlowNodeList = ${workFlowNodeList}
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
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <%--文档示例--%>
            <span style="font-size: 16px;margin-left: 22px;" class="font-color">模板说明文档</span>
            <div class="page-bar shadowRadius" style="margin-top: 18px;">
                <div style="float: left;margin-right: 25px;" onclick="javascript:history.back(-1);">
                    <i class="icon iconfont icon-fanhui1" style="color: #a6a2a1;font-size: 26px" title="返回" ></i>
                </div>
                <div>
                    <a href="#basic" data-toggle="modal" class="creat_model"><span>创建工作流<span>+</span></span></a>
                </div>
            </div>
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-success progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
                </div>
            </div>
            <div  class="content_example">
                <%--居中--%>
                <input type="hidden" name="templateDescription" value='${templateDescription}'>
                <div  id="templateDescription" >



                <%--<div>--%>
                        <%--<span class="example_title">模板介绍：</span>--%>
                        <%--<div class="example_des">介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。介绍各个模板的案例并一一说明。</div>--%>
                    <%--</div>--%>
                    <%--<div class="example_table">--%>
                        <%--<span class="example_title">数据字段说明：</span>--%>
                        <%--<div>--%>
                            <%--<table class="table">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th with="30%">字段名称</th>--%>
                                    <%--<th with="30%">字段说明</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody>--%>
                                <%--<tr>--%>
                                    <%--<td>Length</td>--%>
                                    <%--<td>每个样本长度，在工作流中作为标签列</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td>Weight</td>--%>
                                    <%--<td>样本的重量</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td>Age</td>--%>
                                    <%--<td>数据样本的年龄</td>--%>
                                <%--</tr>--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div>--%>
                        <%--<span class="example_title">模板流程图：</span>--%>
                        <%--<div class="example_des_bottom">--%>
                            <%--<img src="${basePath}/content/images/template/example.png">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div>--%>
                        <%--<span class="example_title">其他补充说明：</span>--%>
                        <%--<div class="example_des">这个模板的案例多说明。</div>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>

        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!--MODAL EDIT-->
<%--创建新的工作流--%>
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>--%>
                <h4 class="modal-title">创建新的工作流(模板说明文档)</h4>
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
                                                <p class="title_text selectMagName" style="color: red"></p>
                                                <input type="hidden" id="templateId" data-required="1" class="form-control"/>
                                                <input type="hidden" id="flowId" data-required="1" class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">说明</label>
                                            <div class="col-md-10">
                                                <textarea name="flowExplain" id="flowExplain" class="workFlowInfo" rows="5" data-required="1" class="form-control" maxlength="150"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">位置
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <div class="treeDeomBox">
                                                    <ul id="treeDemo" class="ztree"></ul>
                                                </div>
                                                <p class="title_text selectMag" style="color: red"></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-1"></div>
                                            <div class="col-md-11">
                                                <button type="button" onclick="createWorkFlow()" class="btn btn-default font-color border-color">提交</button>
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
<!-- END MODAL EDIT-->
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='10'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/content/js/template/templateExplain.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.core.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.excheck.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.exedit.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
</body>
<script>
    var zTree;
    zNodes = ${workFlowNodeList};
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
</script>
</html>