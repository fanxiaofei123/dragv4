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
    .menu_name{
        height: 50px;
        margin-bottom: 10px;
    }
    .menu_name .formInput{
        padding: 0 15px;
    }
    .menu_name .formInput span{
        right: 15px;
    }
    .menu_name .creat_new{
        float: right;
        margin-right: 26px;
        height: 50px;
        line-height: 60px;
        overflow: hidden;
    }
    .menu_name .creat_new i{
        margin: 0 10px;
        font-size: 18px;
    }
    .menu_name .creat_new span:last-child{
        font-size: 16px;
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
        /*padding: 2px;*/
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
        padding: 0 12px;
        cursor: pointer;
        list-style: none outside none;
        background-color: #ededed;
    }
    div#rMenu ul li i{
        margin-right: 10px;
        color: #a3a3a3;
    }
    div#rMenu ul li:hover{
        background-color: #1abc9c;
        color: #fff;
    }
    div#rMenu ul li:hover i{
        color: #fff;
    }
    .has-feedback{
        clear: both;
    }
    div#rMenu ul li span{
        display: inline-block;
        height: 50px;
        width: 100%;
        border-top: 1px solid #e3e4e4;
    }
    /*滚动条高度*/
    #workTreeScroll{
        height: 90.4%;
    }
</style>
<div class="menu_left_width">
    <div class="menu_name">
        <%--<p style="text-align: right"><i class="icon-social-dropbox"></i>工作流</p>--%>
        <div class="creat_new">
            <%--<i class="icon-ban"></i><i class="icon-power"></i><i class="icon-close"></i><span style="padding: 0 10px 0 5px;">|</span><span class="creatLink">新建连接 +</span>--%>

            <i class="icon iconfont icon-chuangjiangongzuokongjian1 icon-green"></i><span class="font-color">工作流</span>
        </div>

    </div>
    <div class="content_wrap" id="workTreeScroll">
        <div class="zTreeDemoBackground left">
            <ul id="treeDemo" class="ztree"></ul>
        </div>

    </div>
    <div id="rMenu" class="rMenuShadow">
        <ul>
            <li id="addParent" onclick="return false;"><i class="icon iconfont icon-xinjianwenjianjia"></i>新建文件夹</li>
            <li id="creat" onclick="return false;"><i class="icon iconfont icon-xinjian3"></i>新建工作流</li>
            <li id="edit" onclick="return false;"><span><i class="icon iconfont icon-bianji6"></i>重命名</span></li>
            <li id="remove" onclick="return false;"><span><i class="icon iconfont icon-cuo2"></i>删除工作流</span></li>
            <li id="remove2" onclick="return false;"><span><i class="icon iconfont icon-cuo2"></i>删除文件夹</span></li>
        </ul>
    </div>

</div>

<script>
    var zTree, rMenu;
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo2"), setting2, zNodes);
        zTree = $.fn.zTree.getZTreeObj("treeDemo");
        rMenu = $("#rMenu");
        $("#addParent").bind("click", {isParent:true}, add);
        $("#edit").bind("click", edit);
        $("#remove").bind("click", remove);
        $("#remove2").bind("click", remove);
        $("#creat").bind("click", creat);

    });
</script>
