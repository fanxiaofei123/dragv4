<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/4
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/sdw.tld" prefix="sdw"%>
<script src="${basePath}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="application/javascript" src="${basePath}/plugins/jquery.tmpl.js"></script>
<script type="application/javascript" src="${basePath}/assets/pages/common/common.js"></script>
<link rel="stylesheet" href="${basePath}/content/css/toastr.css">
<%--<style>--%>
    <%--.menu.popup_notifications{--%>
        <%--position: absolute;--%>
        <%--right: 180px;--%>
        <%--top: 50px;--%>
        <%--background: #fff;--%>
        <%--display: none;--%>
    <%--}--%>
    <%--.dropdown-menu{--%>
        <%--width: 120px!important;--%>
        <%--min-width: 0!important;--%>
    <%--}--%>
    <%--.notification_list ul{--%>
        <%--padding: 0;--%>
        <%--margin: 0;--%>
        <%--font-family: "microsoft yahei","Source Sans Pro",Helvetica,Arial,Verdana,sans-serif;--%>
        <%--font-weight:600;--%>
    <%--}--%>
    <%--.notification_list li{--%>
        <%--list-style: none;--%>
        <%--padding: 10px;--%>
        <%--border: 1px solid #d4d4d4;--%>
        <%--border-top: none;--%>
        <%--width: 320px;--%>
        <%--cursor: pointer;--%>
        <%--/*overflow: hidden;*/--%>
        <%--word-wrap: break-word;--%>
    <%--}--%>
    <%--.notification_list li .icon{--%>
        <%--float: left;--%>
        <%--width: 38px;--%>
        <%--height: 38px;--%>
        <%--margin-right: 15px;--%>
    <%--}--%>
     <%--.notification_list li .time{--%>
         <%--margin-bottom: 5px;--%>
     <%--}--%>
     <%--.notification_list li .contentHeader{--%>
         <%--line-height: 20px;--%>
         <%--margin-bottom: 5px;--%>
     <%--}--%>
     <%--.notification_list li .actions{--%>
         <%--display: none;--%>
     <%--}--%>
     <%--.notification_list li .content{--%>
         <%--width: 240px;--%>
         <%--display: inline-block;--%>
     <%--}--%>
    <%--/*.notification_list li:hover .actions{*/--%>
        <%--/*display: block;*/--%>
        <%--/*-webkit-transition: all linear 2s;*/--%>
    <%--/*}*/--%>
    <%--.notification_list li:hover{--%>
        <%--background: #f0f7fe;--%>
    <%--}--%>
    <%--.notification_list li:first-of-type{--%>
        <%--border-top: 1px solid #d4d4d4;--%>
    <%--}--%>
<%--</style>--%>
<%--<body class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">--%>
<%--<div class="page-header navbar navbar-fixed-top">--%>
    <%--<!-- BEGIN HEADER INNER -->--%>
    <%--<div class="page-header-inner ">--%>
        <%--<!-- BEGIN LOGO -->--%>
        <%--<div class="page-logo">--%>
            <%--<a href="${basePath}/drag/dispatcher/drag.do">--%>
                <%--<img src="${basePath}/content/images/global/logo4.png" alt="logo" class="logo-default" /> </a>--%>
            <%--<div class="menu-toggler sidebar-toggler"></div>--%>
        <%--</div>--%>
        <%--<!-- END LOGO -->--%>
        <%--<!-- BEGIN RESPONSIVE MENU TOGGLER -->--%>
        <%--<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>--%>
        <%--<!-- END RESPONSIVE MENU TOGGLER -->--%>

        <%--<!-- BEGIN PAGE TOP -->--%>
        <%--<div class="page-top">--%>
            <%--<!-- BEGIN HEADER SEARCH BOX -->--%>
            <%--<form class="search-form search-form-expanded" action="page_general_search_3.html" method="GET">--%>
                <%--<div class="input-group">--%>
                    <%--&lt;%&ndash;<input type="text" class="form-control" placeholder="Search..." name="query">&ndash;%&gt;--%>
                    <%--<span class="input-group-btn">--%>
                                <%--<!-- <a href="javascript:;" class="btn submit">--%>
                                    <%--<i class="icon-magnifier"></i>--%>
                                <%--</a> -->--%>
                            <%--</span>--%>
                <%--</div>--%>
            <%--</form>--%>
            <%--<!-- END HEADER SEARCH BOX -->--%>
            <%--<!-- BEGIN TOP NAVIGATION MENU -->--%>
            <%--<div class="top-menu">--%>
                <%--<i id="i-tips" class="glyphicon glyphicon-bell" style="top: 28px;position: relative;color: #5a5953;">--%>

                                <%--<span id="waring" style="color: #fff;--%>
                                                <%--position: absolute;--%>
                                                <%--display: inline-block;--%>
                                                <%--width: 14px;--%>
                                                <%--height: 14px;--%>
                                                <%--background: red;--%>
                                                <%--border-radius: 10px !important;--%>
                                                <%--top: -7px;--%>
                                                <%--font-size: 12px;--%>
                                                <%--line-height: 10px;--%>
                                                <%--text-align: center;--%>
                                                <%--padding-left: 2px;--%>
                                                <%--left: 7px;">--%>
                                    <%--${sessionScope.user.tips}--%>
                                <%--</span>--%>


                <%--</i>--%>

                <%--<div   class="menu popup_notifications">--%>
                    <%--<div class="notification_area">--%>
                        <%--<div class="notification_list" data-template="notification_list">--%>
                            <%--<ul id="tips-content">--%>

                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>



                <%--<ul class="nav navbar-nav pull-right">--%>
                    <%--<!-- BEGIN USER LOGIN DROPDOWN -->--%>
                    <%--<li class="dropdown dropdown-user">--%>
                        <%--<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">--%>
                            <%--&lt;%&ndash;<img alt="" class="img-circle" src="${basePath}/content/images/update/avatar3_small.jpg" />&ndash;%&gt;--%>

                            <%--<span class="username username-hide-on-mobile">--%>
                                <%--<c:choose>--%>
                                    <%--<c:when test="${empty sessionScope.user.name}">--%>
                                      <%--用户--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--${sessionScope.user.name}--%>
                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>

                            <%--</span>--%>
                            <%--<i class="fa fa-angle-down"></i>--%>



                        <%--</a>--%>
                        <%--<ul class="dropdown-menu dropdown-menu-default">--%>
                            <%--<li>--%>
                                 <%--<a href="${basePath}/drag/users/detailsUser.do?id=${sessionScope.user.id}">--%>
                                     <%--<i class="icon-user"></i> 我的信息 </a>--%>
                             <%--</li>--%>
                             <%--<li>--%>
                                <%--<a href="${basePath}/drag/user/logout.do">--%>
                                    <%--<i class="icon-logout"></i>退出平台</a>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<!-- END USER LOGIN DROPDOWN -->--%>
                <%--</ul>--%>
            <%--</div>--%>

            <%--<!-- END TOP NAVIGATION MENU -->--%>
        <%--</div>--%>
        <%--<!-- END PAGE TOP -->--%>
    <%--</div>--%>
    <%--<!-- END HEADER INNER -->--%>
<%--</div>--%>

<%--<!-- END HEADER -->--%>
<%--<!-- BEGIN HEADER & CONTENT DIVIDER -->--%>
<%--<div class="clearfix"> </div>--%>
<%--<!-- END HEADER & CONTENT DIVIDER -->--%>


<%--<script id="tips" type="text/x-jquery-tmpl">--%>

     <%--<li>--%>
                                        <%--<div class="icon">--%>
                                            <%--<img src="${basePath}/images/task-import.png" alt="">--%>
                                        <%--</div>--%>
                                        <%--<div class="content">--%>
                                            <%--<div class="contentHeader">成功将<span style="color: #0d638f;"><sdw:tmpl name ="fileName"/></span>导入HDFS</div>--%>
                                            <%--<div class="time">时间:<sdw:tmpl name ="uploadTime"/></div>--%>
                                            <%--<div class="actions delHistory" onclick='delHistory(<sdw:tmpl name ="id"/>)' >--%>
                                                <%--<a class="delete_link alert delete_notification "  href="#"><span class="fa fa-trash"></span><span  '> 删除</span></a>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
        <%--</li>--%>


<%--</script>--%>


<%--<script>--%>
    <%--var isOut = true;--%>
    <%--$(function () {--%>
        <%--$('.top-menu .glyphicon-bell').click(function () {--%>
                <%--$.get(basePath+'/drag/history/list.do',function (data) {--%>
                    <%--$('.menu.popup_notifications').show();--%>
                    <%--var data = JSON.parse(data);--%>
                    <%--$("#tips-content").html('');--%>
                    <%--if(data.length == 0){--%>
                        <%--$("#tips-content").html('<li><div class="icon"><span class="glyphicon glyphicon-minus" style="color: #8b131b;"></span></div><div class="content">暂无新上传纪录!</div></li>')--%>
                    <%--}--%>
                    <%--$("#tips").tmpl(data).appendTo('#tips-content');--%>
                    <%--$('.notification_list li').hover(function () {--%>
                        <%--$(this).find('.actions').slideToggle();--%>
                    <%--})--%>
                    <%--$("#tips-content").append('<li style="align-content: center" ><a href="${basePath}/drag/dispatcher/listAllHistory.do">查看全部纪录</a></li>')--%>

                <%--});--%>
                <%--$("#waring").html("0");--%>
        <%--});--%>

        <%--$('#i-tips').hover(function () {--%>
            <%--isOut = false;--%>
        <%--}, function () {--%>
            <%--isOut = true;--%>
        <%--});--%>


        <%--$('body').on('click',function () {--%>
            <%--if(isOut){--%>
                <%--$('.menu.popup_notifications').hide();--%>

            <%--}--%>
        <%--});--%>



    <%--})--%>

    <%--function delHistory(id) {--%>
       <%--$.get(basePath + "/drag/history/del.do", {'id': id},function (data) {--%>
          <%--var data =  JSON.parse(data);--%>
           <%--console.log(data.code);--%>
           <%--switch (data.code){--%>
               <%--case 200:--%>
                   <%--toastr.success(data.msg);--%>
                   <%--break;--%>
               <%--case 417:--%>
                   <%--toastr.error(data.msg);--%>
                   <%--break;--%>
           <%--}--%>
       <%--});--%>

    <%--}--%>


<%--</script>--%>