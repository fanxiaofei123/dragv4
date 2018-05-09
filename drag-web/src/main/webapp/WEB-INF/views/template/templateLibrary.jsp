
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<!DOCTYPE html>
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 模板示例</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/template/templateLibrary.css">
    <!-- END PAGE LEVEL PLUGINS -->

</head>
<script type="application/javascript">
    var workFlowNodeList=${workFlowNodeList}
</script>
<body>
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
        <div class="index page-content">
            <p class="font-color">模型示例</p>
            <div class="menu_title">
                <div id="choose-type">
                    <span style="float: left;padding-right: 20px" id="sumNum">49Project</span>
                    <input type="text" id="adminId" hidden>
                    <input type="text" id="Vuserid" hidden>
                    <input type="text" id="templateCategoryName" hidden>
                    <input type="text" id="templateCategoryId" hidden>
                    <input type="text" id="templateCategoryPid" hidden>
                    <div class="filter">一级：<span><span class="filter-option">请选择一级分类</span><i class="icon iconfont icon-yousanjiao"></i></span>
                        <ul class="dn" data-level="1">
                            <li class="filter-active" data-id="-1" data-adminId="1" data-Aid="1" data-templatecategorypid="-1"><i class="glyphicon glyphicon-ok"></i><span>系统自带模板</span></li>
                            <li data-id="-1" data-userId="1" data-Aid="-1" data-templatecategorypid="-1"><i class="glyphicon glyphicon-ok"></i><span>个人模板</span></li>
                        </ul>
                    </div>

                    <%--<div class="filter">二级：<span><span class="filter-option">请选择一级分类</span><i class="icon iconfont icon-yousanjiao"></i></span>--%>
                        <%--<ul class="dn">--%>
                            <%--<li class="filter-active"><i class="glyphicon glyphicon-ok"></i><span>金融</span></li>--%>
                            <%--<li><i class="glyphicon glyphicon-ok"></i><span>体育</span></li>--%>
                            <%--<li><i class="glyphicon glyphicon-ok"></i><span>教育</span></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <%--<div class="filter">3级：<span><span class="filter-option">请选择一级分类</span><i class="icon iconfont icon-yousanjiao"></i></span>--%>
                        <%--<ul class="dn">--%>
                            <%--<li class="filter-active"><i class="glyphicon glyphicon-ok"></i><span>金融</span></li>--%>
                            <%--<li><i class="glyphicon glyphicon-ok"></i><span>体育</span></li>--%>
                            <%--<li><i class="glyphicon glyphicon-ok"></i><span>教育</span></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                </div>
                <div style="float: right;">
                    <div style="float: left">
                        <input  placeholder="请搜索任务名称..." id="searchName" style="border: none ;width: 300px;text-align: center"/>
                        <div style="width: 300px;height: 1px;background-color: #1bbc9b;"></div>
                    </div>
                    <div onclick="selectTempalateByName2()"  style="display: inline-block;float: right"> <i class="fa fa-search searchName" style="color: #1bbc9b;cursor: pointer"></i></div>
                </div>
            </div>

            <%--模板库--%>
            <div class="templateBase" id="templateBase">
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="creat">--%>
                    <%--<img src="${basePath}/content/images/template/template.png" alt="">--%>
                    <%--<div class="des">--%>
                        <%--<div class="des_width">--%>
                            <%--<p>在线预测成绩预测</p>--%>
                            <%--<p>主要是展示平台在线预测能力，通过中学生的在校园行为预测期末成绩以及对于成绩的关键影响因子。</p>--%>
                            <%--<p>深度学习图片识别深度学习图片识别深度学习图片识别</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="see">--%>
                        <%--<a href="">从模板创建</a>--%>
                        <%--<a href="">查看文档</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <%--page--%>
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

        <!-- END CONTENT BODY -->
    </div>


</div>
<!-- END CONTAINER -->
<!--MODAL EDIT-->
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
                                                <button type="button" class="btn btn-default font-color border-color" onclick="createWorkFlow()">提交</button>
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
<!-- BEGIN FOOTER -->
<%--<jsp:include page="/components/navi/footer.jsp" flush="true"/>--%>
<!-- END FOOTER -->
<script>window.nav='10'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.core.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.excheck.js" type="text/javascript"></script>
<script src="${basePath}/js/jquery.ztree.exedit.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/template/templateLibrary.js" type="text/javascript"></script>


</body>
<script>
    var zTree;
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
</script>

</html>