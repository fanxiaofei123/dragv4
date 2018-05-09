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
    <title>可视化数据处理分析平台 | 我的信息</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/pages/css/profile-2.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/user/details.css">
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
          <!--   <h3 class="page-title"> 用户信息
                <small>User Profile</small>
            </h3> -->
              <div class="caption">
                                <h4><i class="icon-users font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">用户信息</span></h4>
                            </div>
            <div class="page-bar">
                <ul class="page-breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="${basePath}/drag/users/select.do?page=1&rows=10">用户</a>
                    </li>
                    <li>
                        <i class="fa fa-angle-right"></i>
                        ${user.loginname }
                    </li>
                </ul>
            </div>
            <!-- END PAGE HEADER-->
            <div class="profile">
                <div class="tabbable-line tabbable-full-width">
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab_1_1">
                            <div class="row">
                                <div class="col-md-2">
                                    <ul class="list-unstyled profile-nav">
                                        <li>
                                            <img src="${basePath}/content/images/profile/people19.png" class="img-responsive pic-bordered" alt="" />
                                        </li>
                                    </ul>
                                </div>
                                <div class="col-md-10">
                                    <div class="row">
                                        <div class="col-md-8 profile-info">
                                            <h1 class="font-green sbold uppercase">${user.loginname }</h1>
                                            <p>个人描述</p>
                                            <p>${user.describe }</p>
                                            <ul class="list-inline">
                                                <li>
                                                    <i class="fa fa-user"></i>${user.name }</li>
                                                    <c:choose>
                                                       <c:when test="${user.developer == 1}">
                                                       <li>
                                                    <i class="fa fa-tag"></i> 开发者</li>
                                                       </c:when>
                                                      <c:when test="${user.developer == 2}">
                                                      <li>
                                                    <i class="fa fa-tag"></i>  管理者 </li>
                                                      </c:when>
                                                   <%--     <c:when test="${user.developer == 3}">
                                                      <li>
                                                    <i class="fa fa-tag"></i> 使用者 </li>
                                                      </c:when> --%>
                                                    <c:otherwise>
                                                    <li>
                                                    <i class="fa fa-tag"></i> 使用者</li>
                                                     </c:otherwise>
                                                  </c:choose>

                                                 <c:choose>
                                                      <c:when test="${user.userSex == 2}">
                                                       <li>
                                                    <i class="fa fa-male"></i> 女</li>
                                                       </c:when> 
                                                    <c:otherwise>
                                                    <li>
                                                    <i class="fa fa-male"></i>男</li>
                                                     </c:otherwise>
                                                  </c:choose>
                                                  
                                                    <c:choose>
                                                       <c:when test="${user.education == 0}">
                                                       <li>
                                                    <i class="fa fa-mortar-board"></i> 博士</li>
                                                       </c:when>
                                                       <c:when test="${user.education == 1}">
                                                       <li>
                                                    <i class="fa fa-mortar-board"></i> 硕士</li>
                                                       </c:when>
                                                      <%-- <c:when test="${user.developer == 2}">
                                                      <li>
                                                    <i class="fa fa-mortar-board"></i> 本科</li>
                                                      </c:when>
                                                       <c:when test="${user.developer == 3}">
                                                      <li>
                                                    <i class="fa fa-mortar-board"></i> 专科</li>
                                                      </c:when> --%>
                                                    <c:otherwise>
                                                    <li>
                                                    <i class="fa fa-mortar-board"></i>本科及本科以下</li>
                                                     </c:otherwise>
                                                  </c:choose>
                                                  
                                                <li>
                                                    <i class="fa fa-university"></i>${user.school } </li>
                                                <li>
                                                    <i class="fa fa-briefcase"></i>${user.occupation } </li>
                                            </ul>
                                           <%-- <a href="javascript:;" class="btn green"> 修改信息
                                                <i class="fa fa-edit"></i>
                                            </a>--%>
                                        </div>
                                        <!--end col-md-8-->
                                        <div class="col-md-4">
                                            <div class="portlet sale-summary">
                                                <div class="portlet-title">
                                                    <div class="caption font-red sbold"> 工作详情 </div>
                                                    <div class="tools">
                                                        <a class="reload" href="javascript:;"> </a>
                                                    </div>
                                                </div>
                                                <div class="portlet-body">
                                                    <ul class="list-unstyled">
                                                        <li>
                                                                    <span class="sale-info"> 工作空间
                                                                        <i class="fa fa-img-up"></i>
                                                                    </span>
                                                            <span class="sale-num">${WorkSpaceNum }</span>
                                                        </li>
                                                        <li>
                                                                    <span class="sale-info"> 工作流
                                                                        <i class="fa fa-img-down"></i>
                                                                    </span>
                                                            <span class="sale-num"> ${WorkFlowNum } </span>
                                                        </li>
                                                       <!--  <li>
                                                            <span class="sale-info"> 关联数据集 </span>
                                                            <span class="sale-num"> 2377 </span>
                                                        </li> -->
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <!--end col-md-4-->
                                    </div>
                                    <!--end row-->
                                </div>
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

</body>

</html>
