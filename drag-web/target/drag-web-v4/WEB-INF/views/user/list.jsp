<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<!DOCTYPE html>
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 用户列表</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/user/list.css">
    <!-- END PAGE LEVEL PLUGINS -->
</head>
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
        <div class="page-content">
            <!-- BEGIN PAGE HEADER-->
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-info progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
                </div>
            </div>
            <div class="loading"  index="3" id="loading" style="position: relative;">
                <div style="display: none" class="demo"></div>
            </div>
            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-users font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">用户列表</span>
                            </div>
                            <div style="float:right">
                                <input type="text" name="loginname" id="loginnameId"/>
                                <a href="javascript:;" class="btn green" onclick="selectName()">搜索 <i class="fa fa-search"></i></a>
                            </div>
                        </div>

                        <div class="portlet-body">
                            <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                <thead>
                                <tr>
                                    <th width="15%"> 用户名 </th>
                                    <th width="15%"> 昵称 </th>
                                    <th width="10%"> 性别</th>
                                    <th width="10%"> 学历 </th>
                                    <th width="20%"> 邮箱 </th>
                                    <th width="10%"> 角色 </th>
                                    <th width="20%">创建时间 </th>
                                </tr>
                                </thead>

                                <tbody id="userList">
                                <%--  <c:forEach items="${userList}" var="user">
                                <tr class="odd gradeX">
                                    <td>
                                        <div class="fileType">
                                            <i class="icon-user"></i>
                                        </div>
                                        <div class="fileContent">
                                            <div class="fileTitle">
                                                <a href="${basePath}/drag/users/details.do?id=${user.id}">${user.loginname }</a>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <span class="">${user.name } </span>
                                    </td>
                                     <c:choose>
                                      <c:when test="${user.userSex == 2}">
                                                    <td>
                                        <span class=""> 女 </span>
                                                </td>
                                      </c:when>
                                      <c:otherwise>
                                                  <td>
                                        <span class="">男 </span>
                                    </td>
                                       </c:otherwise>
                                      </c:choose>
                                                  
                                        <c:choose>
                                             <c:when test="${user.education == 0}">
                                                      <td>
                                        <span class=""> 博士 </span>
                                    </td>
                                              </c:when>
                                         <c:when test="${user.education == 1}">
                                               <td>
                                        <span class=""> 硕士</span>
                                    </td>
                                         </c:when>
                                         
                                                    <c:otherwise>
                                                   <td>
                                        <span class=""> 本科及本科以下 </span>
                                    </td>
                                                     </c:otherwise>
                                                  </c:choose>
                                    <td>
                                        <span class=""> ${user.email } </span>
                                    </td>
                                    
                                       <c:choose>
                                                       <c:when test="${user.developer == 1}">
                                                       <td>
                                        <span class=""> 开发者 </span>
                                    </td>
                                                       </c:when>
                                                      <c:when test="${user.developer == 2}">
                                                      <td>
                                        <span class=""> 管理者 </span>
                                    </td>
                                                      </c:when>
                                                    <c:otherwise>
                                                    <td>
                                        <span class=""> 使用者 </span>
                                    </td>
                                                     </c:otherwise>
                                                  </c:choose>
                                    <td>
                                        <span class="">${user.createdates } </span>
                                    </td>
                                    
                                </tr>
                                </c:forEach> --%>
                                </tbody>
                            </table>
                            <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="sample_2_paginate">
                                <%-- <div class="pagination-panel">
                                    <a href="${basePath}/drag/users/select.do?page=${Previous }" class="btn btn-sm default prev">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                    <input type="text" class="pagination-panel-input form-control input-sm input-inline input-mini" maxlenght="5" value="${page }" style="text-align:center; margin: 0 5px;">
                                    <a href="${basePath}/drag/users/select.do?page=${next }" class="btn btn-sm default next">
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
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='4'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>


<script src="${basePath}/assets/global/plugins/moment.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/components-date-time-pickers.js" type="text/javascript"></script>
<script src="${basePath}/content/js/user/list.js" type="text/javascript"></script>

</body>

</html>
