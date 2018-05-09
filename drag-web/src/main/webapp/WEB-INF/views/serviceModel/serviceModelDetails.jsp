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
    <title>可视化数据处理分析平台 | 模型服务详情</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/serviceModel/serviceModelDetails.css">

</head>
<!-- END HEAD -->
<%--<script type="application/javascript">--%>
   <%--var templateDescription = ${templateDescription}--%>
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
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <%--文档示例--%>
                <span style="font-size: 16px;margin-left: 22px;" class="font-color">已部署的模型服务</span>
                <div class="page-bar shadowRadius" style="margin-top: 18px;">
                    <div style="float: left;color: #7f8fa4" onclick="javascript:history.back(-1);">
                        <i class="icon iconfont icon-fanhui1" style="color: #a6a2a1;font-size: 26px" title="返回" ></i><span class="line">|</span><span id="serviceModelName"></span>
                    </div>

                </div>
                <div id="progress" class="progress" style="display: none">
                    <div class="progress-bar progress-bar-success progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                        <div id="progress-value" class="progress-value">85%</div>
                    </div>
                </div>
                <div class="modal_body">
                    <div class="modal_body_content">
                        <div class="margin_top_bottom">
                            <span class="title_name">模型服务描述:</span><span>(由部署时填写的描述信息生成)</span>
                            <span class="lookWorkFlow"><a href="${basePath}/drag/dataModel/select.do?name=${serviceModel.workFlowName}&flowId=${serviceModel.serviceModelFlowId}&workSpaceName=${serviceModel.workSpaceName}"
                               data-toggle="modal" class="creat_model">查看所属工作流</a></span>
                        </div>
                        <div class="margin_top_bottom text_description">${serviceModel.serviceModelDescription}</div>
                        <div class="margin_top_bottom">
                            <span class="title_name">key:</span><span class="text_url">${serviceModel.token}_${serviceModel.userId}</span>
                        </div>
                        <div class="margin_top_bottom">
                            <span class="title_name">请求地址:</span><span class="text_url">${basePath}${serviceModelAdds}</span>
                        </div>
                        <div class="margin_top_bottom">
                            <span class="title_name display_block" >请求参数说明:</span>
                            <div>
                                <div class="example_table"  >
                                    <div>
                                        <table class="table" data-toggle="table">
                                            <thead>
                                            <tr>
                                                <th>参数名</th>
                                                <th>类型</th>
                                                <th>描述</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${modelContext}" var="list" >
                                                <c:forEach items='${list["labelColumn"]}' var="list2" >
                                                    <c:choose>
                                                        <c:when test="${list2 == null}">
                                                            <tr>
                                                                <td>--</td>
                                                                <td>--</td>
                                                                <td>--</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td>${list2["0"]}</td>
                                                                <td>${list2["1"]}</td>
                                                                <td>${list2["2"]}</td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            var modelContext = ${modelContext};
                        </script>
                        <div class="margin_top_bottom">
                            <span class="title_name display_block"  >返回参数说明:</span>
                            <div>
                                <div class="example_table"  >
                                    <div>
                                        <table class="table" data-toggle="table">
                                            <thead>
                                            <tr>
                                                <th>参数名</th>
                                                <th>类型</th>
                                                <th>描述</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${modelContext}" var="list" >
                                                <c:forEach items='${list["selectTheColumn"]}' var="list2" >
                                                    <c:choose>
                                                        <c:when test="${list2 == null}">
                                                            <tr>
                                                                <td>--</td>
                                                                <td>--</td>
                                                                <td>--</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td>${list2["0"]}</td>
                                                                <td>${list2["1"]}</td>
                                                                <td>${list2["2"]}</td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="margin_top_bottom">
                            <%--<input type="button" onclick="serviceModelByApi()" value="nihao">--%>
                            <span class="title_name1 " >请求body:</span>
                        </div>
                        <div class="margin_top_bottom">
                            <%--请求body显示区--%>
                            <div class="scrollbarContentDiv askBody">
                                {<br>
                                "input":<br>
                                [<br>
                                {<br>
                                <c:forEach items='${labelColumnList}' var="list2" >
                                    "${list2}": "1",<br>
                                </c:forEach>
                                <%--'Sepal.Length': "1",<br>--%>
                                <%--'Sepal.Width': "1",<br>--%>
                                <%--'Petal.Length': "1",<br>--%>
                                <%--'Petal.Width': "1",<br>--%>
                                <%--'Species': "",<br>--%>
                                }<br>
                                ]<br>
                                <%--},<br>--%>
                                <%--"GlobalParameters":  {<br>--%>
                                <%--<c:forEach items='${biaoqianlie}' var="list2" >--%>
                                    <%--'${list2}': "1",<br>--%>
                                <%--</c:forEach>--%>
                                <%--}<br>--%>
                                }<br>

                            </div>
                        </div>
                    </div>
                </div>
        </div>

        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!--MODAL EDIT-->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='13'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<%--<script src="${basePath}/content/js/template/templateExplain.js" type="text/javascript"></script>--%>
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/content/js/serviceModel/serviceModelDetails.js" type="text/javascript"></script>
</body>
</html>