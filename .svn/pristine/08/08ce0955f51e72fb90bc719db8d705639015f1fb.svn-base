<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/6/30
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${basePath}/content/css/tree/zTreeStyle.css" type="text/css">
<%--<script type="text/javascript" src="../../../js/jquery-1.4.4.min.js"></script>--%>
<script type="text/javascript" src="${basePath}/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.ztree.exhide.js"></script>
<script type="text/javascript" src="${basePath}/content/js/dataSource/databaseTree.js"></script>
<style>
    .menu_left_width{
        position: fixed;
        z-index: 1;
        left: 258px;
        width: 285px;
        height: 100%;
        background: #fff;
        border-right: 1px solid #eaeaea;
        box-shadow: 0 0 10px rgba(9, 84, 109, 0.05);

    }
    .page-content-wrapper .page-content{
        margin-left: 546px;
    }
    .menu_name .formInput{
        padding: 0 15px;
    }
    .menu_name .creat_new{
        position: relative;
        float: right;
        margin-right: 15px;
        width: 114px;
        height: 60px;
        overflow: hidden;
    }
    .menu_name .creat_new i{
        margin: 0 5px;
    }
    .menu_name .creat_new .createLink{
        position: absolute;
        top: 17px;
        display: inline-block;
        padding: 2px 8px;
        height: 25px;
        line-height: 25px;
        font-size: 12px;
        color: #fff;
        background: #1bbc9b;
        border-radius: 25px;
        width: 114px;
    }
    .menu_name .creat_new .createLink>span{
        position: absolute;
        top: 0;
        left: 28px;
    }
    .menu_name .creat_new .createLink>span span{
        position: absolute;
        top: -1px;
        right: -16px;

    }
    /*右键*/
    div#rMenu {
        position:absolute;
        z-index: 3;
        visibility:hidden;
        top:0;
        width: 220px;
        background-color: #ededed;
        text-align: left;
        border-radius: 5px!important;
    }
    div#rMenu ul{
        padding: 0;
        margin: 10px 0;
    }
    div#rMenu ul li{
        /*margin: 1px 0;*/
        height: 40px;
        line-height: 40px;
        padding: 0 10px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #ededed;
    }
    div#rMenu ul li:hover{
        background-color: #1abc9c;
        color: #fff;
    }
    .has-feedback{
        clear: both;
    }
    .has-feedback span{
        position: absolute;
        right: 22px;
        top: 12px;
    }
    li [class^="icon-"], li [class*=" icon-"]{
        top: 3px;
    }
    /*测试*/
    #treeScroll {
        height: 84%;
    }
</style>
<div class="menu_left_width">
    <div class="menu_name">
        <%--<p style="text-align: right"><i class="icon-social-dropbox"></i>工作流</p>--%>
        <div class="creat_new">
           <span class="createLink"><span>新建连接<span>+</span></span></span>
        </div>
            <div class="formInput form-group has-feedback">
            <input type="search" class="form-control input-sm input-block input-inline" id="serachLink" placeholder="输入连接名"
                   style="width: 100%">
            <span class="icon iconfont icon-chakan1" aria-hidden="true"></span>
        </div>
    </div>
    <div class="content_wrap" id="treeScroll">
        <div class="zTreeDemoBackground left">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
    </div>
    <div id="rMenu" class="rMenuShadow">
        <ul>
            <li class="openMessage" id="scanLink"><i class="icon iconfont icon-chakan"></i>查看连接信息</li>
            <li class="createLink"><i class="icon iconfont icon-xinjian3"></i>新建连接</li>
            <li id="stopLink" ><i class="icon iconfont icon-tingyong2"></i>停用连接</li>
            <li id="startLink"><i class="icon iconfont icon-qiyong"></i>启用连接</li>
            <li id="editLink" onclick="return false;"><i class="icon iconfont icon-bianji"></i>编辑连接</li>
            <li id="rename" onclick="return false;"><i class="icon iconfont icon-bianji"></i>重命名</li>
            <li id="delete" class="delete"><i class="icon iconfont icon-cuo1"></i>删除连接</li>
        </ul>
    </div>
</div>
<%--<script src="${basePath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>--%>
<script>
    var rMenu;
    $(document).ready(function(){
       $.fn.zTree.init($("#treeDemo"), databaseSetting, zNodes);
       console.log(zNodes);
       zLinkNodeNames=zNodes;
       zTree = $.fn.zTree.getZTreeObj("treeDemo");
        rMenu = $("#rMenu");
        $("#editLink").bind("click",editLink);
        $("#scanLink").bind("click",scanLink);
        $("#rename").bind("click", rename);
        $("#delete").bind("click", remove);
        $("#stopLink").bind("click",stopLink);
        $("#startLink").bind("click",startLink);
        $("#serachLink").bind("input propertychange",onTextChangeListener);
    });
//    设置滚动条
//    var height = $(".menu_left_width").css("height");
//    var boxHeight = parseInt(height)-120;
//    $('.zTreeDemoBackground').slimScroll({
//        color: '#1bbc9b',
//        railColor: '#eee',
//        size: '5px',
//        height: boxHeight+"px" ,
//        alwaysVisible: false,
//        disableFadeOut: true,
//        wheelStep: 10
//    });

</script>
