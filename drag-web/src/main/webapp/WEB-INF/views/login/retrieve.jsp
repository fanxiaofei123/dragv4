<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 8]><html lang="en" class="ie8 no-js"><![endif]-->
<!--[if IE 9]><html lang="en" class="ie9 no-js"><![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 登录</title>
    <jsp:include page="/content/css/login/baseCSS.jsp" flush="true"/>
</head>
<!-- END HEAD -->

<body class=" login">
<!-- BEGIN LOGO -->
<div class="logo">
    <a href="index.html">
        <img src="${basePath}/content/images/global/logo.png" alt="" /> </a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">




    <!-- BEGIN FORGOT PASSWORD FORM -->
     <form class="retrieve-form"  method="post">
         <h3 class="font-green">密码找回!</h3>
         <p> 请输入重置密码 </p>

         <div class="alert alert-danger display-hide">
             <button class="close" data-close="alert"></button>
             <span> 邮箱不正确 </span>
         </div>

         <div class="form-group">
             <input class="form-control placeholder-no-fix" type="hidden" value="${id}" name="id" />
         </div>
         <div class="form-group">
         <input class="form-control placeholder-no-fix" type="password" id="password" autocomplete="off" placeholder="新密码" name="password" />
         </div>
         <div class="form-group">
             <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="再次输入" name="rePassword" />
         </div>
         <div class="form-actions">
             <button type="submit" class="btn btn-success uppercase pull-right">提交</button>
         </div>
     </form>
    <!-- END FORGOT PASSWORD FORM -->

    <!-- BEGIN REGISTRATION FORM -->

    <!-- END REGISTRATION FORM -->
</div>
<div class="copyright"> 2016 &copy; Youedata.All rights reserved. </div>

<script src="${basePath}/assets/global/plugins/respond.min.js"></script>
<script src="${basePath}/assets/global/plugins/excanvas.min.js"></script>
<jsp:include page="/content/css/login/baseJS.jsp" flush="true"/>
<script src="${basePath}/assets/pages/scripts/retrieve.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<!-- END THEME LAYOUT SCRIPTS -->
</body>
</html>