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
    <link rel="stylesheet" href="${basePath}/content/css/user/updateDetails.css">
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
           <!--  <h3 class="page-title"> 我的信息
                <small>My Profile</small>
            </h3> -->
             <div class="caption">
                                <h4><i class="icon-users font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">我的信息</span></h4>
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
                                                       <%-- <c:when test="${user.userSex == 1}">
                                                       <li>
                                                    <i class="fa fa-male"></i> 男</li>
                                                       </c:when> --%>
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
                                                    <%--   <c:when test="${user.developer == 2}">
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
                                            <a href="#basicEdit" class="btn green" data-toggle="modal"> 修改信息
                                                <i class="fa fa-edit"></i>
                                            </a>
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
                                                            <span class="sale-num">${WorkSpaceNum } </span>
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
<!--MODAL EDIT-->
<div class="modal fade" id="basicEdit" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">修改信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="${basePath}/drag/users/modifyu.do" method ='post' id="form_sample_4" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label class="control-label col-md-3">昵称
                                              <!--   <span class="required"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <input type="text" name="name" data-required="1" class="form-control" value="${user.name }" />
                                                <input type="hidden" name="id"  value="${user.id }" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">角色
                                                <!-- <span class="required" aria-required="true"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <select class="form-control" name="developer" >
                                                    <option value="1" <c:if test="${'1' eq user.developer}">selected</c:if> >开发者</option>
                                                    <option value="0" <c:if test="${'0' eq user.developer}">selected</c:if> >使用者</option>
                                                    <option value="2" <c:if test="${'2' eq user.developer}">selected</c:if> >管理者</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">性别
                                                <!-- <span class="required" aria-required="true"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <div class="mt-radio-list" data-error-container="#form_2_membership_error">
                                                    <label class="mt-radio">
                                                        <input type="radio" name="userSex" value="1" <c:if test="${user.userSex == 1}">checked="checked"</c:if>  /> 男
                                                        <span></span>
                                                    </label>
                                                    <label class="mt-radio">
                                                        <input type="radio" name="userSex" value="2" <c:if test="${user.userSex == 2}">checked="checked"</c:if>  /> 女
                                                        <span></span>
                                                    </label>
                                                </div>
                                                <div id="form_2_membership_error"> </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">学历
                                               <!--  <span class="required" aria-required="true"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <select class="form-control" name="education">
                                                    <option value="0" <c:if test="${'0' eq user.education}">selected</c:if> >博士</option>
                                                    <option value="1" <c:if test="${'1' eq user.education}">selected</c:if> >硕士</option>
                                                    <option value="2" <c:if test="${'2' eq user.education}">selected</c:if> >本科及本科以下</option>
                                                    <%-- <option value="3" <c:if test="${'3' eq user.education}">selected</c:if> >专科</option> --%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">学校
                                                <!-- <span class="required"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <input type="text" name="school" data-required="1" class="form-control" value="${user.school }"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">职业
                                                <!-- <span class="required"> * </span> -->
                                            </label>
                                            <div class="col-md-9">
                                                <input type="text" name="occupation" data-required="1" class="form-control" value="${user.occupation }" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn green">提交</button>
                                                <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
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
<script>window.nav='4'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
</body>

</html>
