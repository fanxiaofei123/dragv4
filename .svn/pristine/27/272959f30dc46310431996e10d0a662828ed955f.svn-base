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
<script type="text/javascript" src="${basePath}/content/js/dataSource/fileTree.js"></script>
<script type="text/javascript" src="${basePath}/content/js/dataSource/uploadData.js"></script>
<%--<script src="${basePath}/content/js/dataSource/fileManage.js"></script>--%>
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
    .menu_name>p{
        font-size: 12px;
    }
    .menu_name .formInput{
        padding: 0 15px;
    }
    .menu_name .formInput span{
        right: 25px;
    }
    .menu_name .creat_new{
        float: right;
        margin-right: 15px;
        height: 60px;
        line-height: 60px;
        overflow: hidden;
    }
    .menu_name .creat_new i{
        margin: 0 10px;
        font-size: 18px;
    }
    .menu_name .creat_new span:last-child{
        font-size: 12px;
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
    .has-feedback{
        clear: both;
        overflow: hidden;
    }
    div#rMenu ul li span{
        display: inline-block;
        height: 50px;
        width: 100%;
        border-top: 1px solid #e3e4e4;
    }
    #creat form{
        display: inline-block;
        margin-left: -3px;
    }
    /*目录树分割线*/
    .menu_name>p .line{
        color: #fff;
        padding: 0 10px;
        font-size: 12px;
    }
    /*!*目录树滚动条样式*!*/
    /*.menu_left_width .mCSB_container_wrapper{*/
        /*margin-right: 0px;*/
        /*margin-bottom: 20px*/
    /*}*/
    /*测试滚动条问题*/
    #treeScroll{
        height: 90.4%;

    }
</style>
<div class="menu_left_width">
    <div class="menu_name has-feedback">
        <p class="add-iconbg" style="float: right"><span id="add">上传文件</span><span class="line">|</span><span id="addParent1">文件夹 +</span></p>
        <%--<div class="formInput form-group has-feedback">--%>
            <%--<input type="search" class="form-control input-sm input-block input-inline" id="formInput" placeholder="输入文件名"--%>
                   <%--style="width: 100%;height: 40px">--%>
            <%--<span class="icon iconfont icon-search" aria-hidden="true" onclick="searchFile()"></span>--%>
        <%--</div>--%>

    </div>
    <div class="content_wrap" id="treeScroll">
        <div class="zTreeDemoBackground left">
            <ul id="treeDemo" class="ztree"></ul>
        </div>

    </div>
    <div id="rMenu" class="rMenuShadow">
        <ul>
            <li id="creat" onclick="hideRMenu()"><i class="icon iconfont icon-output-copy"></i>
                <form action="javascript:;" id="simple_form2"  enctype="multipart/form-data"  class="updateFlow form-horizontal">
                    上传
                    <div class="inputButton">
                    <input type="hidden" id="curDir2" class="curDir" name="currentDir" value="">
                    <input type="file" id="file2" name="file" accept=".txt,.csv" data-required="1" class="fileinput" onchange="rightSubmitData()"/>
                    </div>
                </form>
            </li>
            <li id="addParent" onclick="return false;"><i class="icon iconfont icon-xinjianwenjianjia"></i>新建文件夹</li>
            <li id="edit" onclick="return false;"><span><i class="icon iconfont icon-bianji6"></i>重命名</span></li>
            <li id="remove" onclick="return false;"><span><i class="icon iconfont icon-cuo2"></i>删除文件</span></li>
            <li id="remove2" onclick="return false;"><span><i class="icon iconfont icon-cuo2"></i>删除文件夹</span></li>
        </ul>
    </div>

</div>

<script>

    var zTree, rMenu;
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        zTree = $.fn.zTree.getZTreeObj("treeDemo");
        rMenu = $("#rMenu");
        $("#addParent").unbind("click").bind("click", {isParent:true}, add);
        $("#addParent1").unbind("click").bind("click", {isParent:true}, add);
        $("#edit").bind("click", edit);
        $("#remove").bind("click", remove);
        $("#remove2").bind("click", remove);
        $("#add").bind("click", creat);
        $("#realdelete").bind("click",realDelete);
        initUpload();
        getFileData();

//            上传
        function addDataModal() {
            $('#addDataModal').modal('show');
        }
        function creat(){
         /*   $.post(basePath+"/drag/filemanage/showboxnode.do",function (data) {

                 zNodes2=data;
                console.log(data);

            });*/
            $.fn.zTree.init($("#treeDemo2"), setting1, zNodes);
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
            var nodes = treeObj.getNodeByParam("id", 1, null);
            treeObj.reAsyncChildNodes(nodes, "refresh");
            hideRMenu();
            addDataModal();
        }
        function hideRightmenu() {
            hideRMenu();
        }
    });
    function searchFile() {
        alert(111)
    }
</script>

