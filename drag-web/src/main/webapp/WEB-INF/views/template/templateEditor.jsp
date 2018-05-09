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
    <title>可视化数据处理分析平台 | 模板说明编辑</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/template/templateManage.css">


</head>
<!-- END HEAD -->
<%--<script type="application/javascript">--%>
    <%--var templateId=${requestScope.TemplateId}--%>
    <%--var templateInstructionAAA=${requestScope.TemplateInstruction}--%>
    <%--var CCC=${requestScope.names}--%>
<%--</script>--%>

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
                    <span style="font-size: 18px;" class="font-color">编辑说明文档</span>
                    <input type="text" id="templateID" value="${requestScope.TemplateId}" hidden>
                    <div type="text" id="TemplateInstruction" hidden>${requestScope.TemplateInstruction}</div>
                </div>
                <div class="search-common">
                    <div class="search-icon" onclick="lookExample()"> <i class="icon iconfont icon-wenti1" title="查看文档编辑示例" ></i></div>
                    <div class="search-icon" onclick="getContent(1)"> <i class="icon iconfont icon-shangchuan" title="上传" ></i></div>
                    <div class="search-icon" onclick="getContent()"> <i class="icon iconfont icon-baocun1" title="保存"></i></div>
                    <div class="search-icon" onmousedown="return $EDITORUI['edui30']._onMouseDown(event, this);" onclick="return $EDITORUI['edui30']._onClick(event, this);"> <i class="icon iconfont icon-yulan1" title="预览" ></i></div>

                </div>
                <%--</ul>--%>
            </div>
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-success progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
                </div>
            </div>
                <div id="editorDiv">
                    <div id="editor" type="text/plain" style="width:100%;min-height:700px; margin-top: 30px;"></div>
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
<script>window.nav='11'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8" src="${basePath}/plugins/UEditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/plugins/UEditor/ueditor.all.min.js"> </script>
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${basePath}/plugins/UEditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="UTF-8" src="${basePath}/content/js/template/templateEditor.js"></script>

</body>
</html>
