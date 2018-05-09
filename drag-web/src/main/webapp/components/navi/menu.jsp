<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/6/30
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .page-sidebar-wrapper{
        position: fixed;
        width: 258px;
        height: 100%;
        background:linear-gradient(left, #0a3c4c, #062f3d);
        background:-moz-linear-gradient(left, #0a3c4c, #062f3d);
        background:-webkit-linear-gradient(left, #0a3c4c, #062f3d);
    }
    @font-face {
        font-family: 'Glyphicons Halflings';
        src: url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.eot");
        src: url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.eot?#iefix") format("embedded-opentype"), url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.woff2") format("woff2"), url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.woff") format("woff"), url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.ttf") format("truetype"), url("../../assets/global/plugins/bootstrap/fonts/bootstrap/glyphicons-halflings-regular.svg#glyphicons_halflingsregular") format("svg"); }
    .tree_menu ul{
        padding: 0;
        margin: 0;
    }
    .tree_menu ul li{
        margin: 0;
        /*padding: 0 20px;*/
        list-style: none;
    }
    .tree_menu #tree_root,.tree_menu #tree_left{overflow:auto;}
    .tree_menu #tree_root li span,
    .tree_menu #tree_root li a,
    .tree_menu #tree_left li span,
    .tree_menu #tree_left li a
    {
        display:block;height:40px;color:#cedce0;cursor:pointer;
    }
    .tree_menu #tree_root li span,.tree_menu #tree_root li a,
    .tree_menu #tree_left li span,.tree_menu #tree_left li a
    {padding:9px 20px;}
    .tree_menu li .hover,.tree_menu li span:hover,.tree_menu li a:hover,.tree_active{
        background-color:#093d4e;
        color: #1bbc9b!important;
    }
    .tree_active a{
        color: #1bbc9b!important;
    }
    /*.tree_menu li:hover a{ color: #1bbc9b!important;}*/
    .tree_menu ul{overflow:hidden;}
    .tree_menu ul li b{
        font-weight:normal;
        position:relative;
        /*padding-left:38px;*/
    }
    .tree_menu ul li .On:after, .tree_menu ul li .Off:after{
        font-family: 'Glyphicons Halflings'!important;
        content: "\e258";
        float: right;
        margin-top: 4px;
        margin-right: 10px;
        font-weight: 200;
        font-size: 10px;
    }
    .tree_menu ul li .On:after{
        transform:rotate(90deg);
        -o-transform:rotate(90deg);
        -ms-transform:rotate(90deg);
        -moz-transform:rotate(90deg);
        -webkit-transform:rotate(90deg);
    }

    #tree_root i, #tree_left i,.user a i{
        display: inline-block;
        width: 40px;
        text-align: center;
    }
    .tree_menu ul li .tree2 b{
        padding-left: 40px;
    }

    .menu_logo,.user{
        height: 90px;
        padding: 25px 20px 0;
    }
    .menu_logo a{
        display: inline-block;
        width: 100%;
        height: 65px;
        border-bottom: 2px solid rgba(13, 88, 89, 0.4);
    }
    .menu_logo a img{
        display: block;
        margin: 0 auto;
    }
    .menu_box{
        margin-top: 20px;
    }
    .user{
        position: absolute;
        bottom: 0;
        width: 258px;
        height: 160px;
    }
    .user .user_name{
        width: 100%;
        border-top: 2px solid rgba(13, 88, 89, 0.4);
    }
    .user a{
        margin-top: 30px;
        padding-left: 8px;
        display: block;
        color: #cedce0;
    }
    /*拓展三级目录*/
    .tree_menu #tree_root span.tree3{padding-left: 60px;}
    .tree_menu #tree_root li a.tree3 b{padding-left: 68px;}
    /*临时*/
    .iconfont{
        font-size: 20px;
    }
</style>
<div class="page-sidebar-wrapper page-sidebar">
    <div class="menu_logo">
        <a href="${basePath}/drag/dispatcher/drag.do">
            <img src="${basePath}/content/images/global/logo4.png" alt="logo" class="logo-default" />
        </a>
    </div>
    <div class="menu_box">
        <div class="tree_menu">
        <ul id="tree_root">
            <li data-li="1">
                <a href="${basePath}/drag/dispatcher/drag.do" class="tree"><i class="icon iconfont icon-shouye"></i>首页</a>
            </li>
            <li data-li="2">
                <%--<a href="${basePath}/drag/dispatcher/ " class="tree"><i class="icon-social-dropbox"></i>工作流</a>--%>
                <a href="${basePath}/drag/dispatcher/workSpace.do" class="tree"><i class="icon iconfont icon-chuangjiangongzuokongjian1"></i>工作流</a>
            </li>
            <li data-li="3">
                <span class="tree"><b class="Off"><i class="icon iconfont icon-moxingguanli"></i>数据源</b></span>
                <ul style="display:none;">
                    <li data-li="4"><a href="${basePath}/drag/database/data.do" class="tree2"><b>数据库连接</b></a></li>
                    <li data-li="5"><a href="${basePath}/drag/dispatcher/filemanage.do" class="tree2"><b>文件管理</b></a></li>
                </ul>
            </li>
            <li data-li="6">
                <span  class="tree"><b class="Off"><i class="icon-question"></i>运维中心</b></span>
                <ul style="display:none;">
                    <li data-li="7"><a href="${basePath}/drag/dispatcher/monitor.do" class="tree2"><b>概览</b></a></li>
                    <li data-li="8"><a href="${basePath}/drag/dispatcher/taskDispatch.do" class="tree2"><b>任务调度</b></a></li>
                    <li data-li="9"><a href="${basePath}/drag/dispatcher/taskOps.do" class="tree2"><b>任务运维</b></a></li>
                    <li data-li="10">
                        <span  class="tree2"><b class="Off">监控提醒</b></span>
                        <ul style="display:none;">
                            <li data-li="11"><a href="${basePath}/drag/dispatcher/remindSetting.do" class="tree3"><b>提醒设置</b></a></li>
                            <li data-li="12"><a href="${basePath}/drag/dispatcher/remindRecord.do" class="tree3"><b>提醒记录</b></a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li data-li="13">
                <span class="tree"><b class="Off"><i class="icon iconfont icon-fenlei"></i>模板库</b></span>
                <ul style="display:none;" class="adminAut">
                    <li data-li="14"><a href="${basePath}/drag/dispatcher/templateLibrary.do" class="tree2"><b>模板库</b></a></li>
                    <li data-li="15"><a href="${basePath}/drag/dispatcher/templateManage.do" class="tree2"><b>模板库管理维护</b></a></li>
                </ul>
            </li>
            <li data-li="16">
                <a href="${basePath}/drag/dispatcher/trainOverModel.do" class="tree"><i class="icon iconfont icon-moxingpingtai"></i>已训练的模型</a>

            </li>
            <%--挖掘分析项展示图管理是管理员能管理的，其他用户是不能管理的--%>
            <c:choose>
                <c:when test="${sessionScope.user.loginname =='admin'}">
                    <%--用户--%>
                    <li data-li="17">
                        <a href="${basePath}/drag/dispatcher/dataExcavateAnalyze.do" class="tree"><i class="icon iconfont icon-shiyongfenxi"></i>挖掘分析项展示图管理</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li data-li="1">

                    </li>
                </c:otherwise>
            </c:choose>
            <li data-li="18">
                <a href="${basePath}/drag/dispatcher/serviceModelList.do" class="tree"><i class="icon iconfont icon-Webfuwuqi"></i>已部署的模型服务</a>
            </li>
            <%--<li>--%>
                <%--<span  class="tree"><b class="Off"><i class="icon-question"></i>监控提醒</b></span>--%>
                <%--<ul style="display:none;">--%>
                    <%--<li><a href="${basePath}/drag/dispatcher/remindSetting.do" class="tree2"><b>提醒设置</b></a></li>--%>
                    <%--<li><a href="${basePath}/drag/dispatcher/remindRecord.do" class="tree2"><b>提醒记录</b></a></li>--%>
                <%--</ul>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="" class="tree"><i class="icon-picture"></i>模型示例</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="" class="tree"><i class="icon-speech"></i>问题反馈</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="" class="tree"><i class="icon-question"></i>使用帮助</a>--%>
            <%--</li>--%>
        </ul>
    </div>
    </div>
    <%--用户名--%>
    <div class="user">
        <div class="user_name">
            <a href="javascript:;">
                <c:choose>
                    <c:when test="${empty sessionScope.user.loginname}">
                        <%--用户--%>
                    </c:when>
                    <c:otherwise>
                        <i class="icon iconfont icon-yonghu"></i>
                        ${sessionScope.user.loginname}
                    </c:otherwise>
                </c:choose>
            </a>
            <a href="${basePath}/drag/user/logout.do">
                <i class="icon iconfont icon-tuichu"></i>
                <span>退出</span>
            </a>
        </div>
    </div>
</div>
