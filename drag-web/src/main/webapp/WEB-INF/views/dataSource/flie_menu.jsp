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
<style>
    .menu_left_width{
        position: fixed;
        z-index: 1;
        left: 258px;
        width: 285px;
        height: 100%;
        background: #fff;
    }
    .page-content-wrapper .page-content{
        margin-left: 546px;
    }
    .menu_name .formInput{
        padding: 0 15px;
    }
    .menu_name .formInput span{
        right: 15px;
    }
    .menu_name .creat_new{
        float: right;
        margin-right: 15px;
        height: 60px;
        line-height: 60px;
        overflow: hidden;
    }
    .menu_name .creat_new i{
        margin: 0 5px;
    }
    .menu_name .creat_new span:last-child{
        padding: 2px 8px;
        font-size: 12px;
        color: #fff;
        background: #1bbc9b;
        border-radius: 10px!important;
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
        height: 34px;
        line-height: 34px;
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
</style>
<div class="menu_left_width">
    <div class="menu_name">
        <p style="text-align: right"><span id="add">添加数据</span><span>|</span><span id="addParent1">文件夹 +</span></p>
        <div class="formInput form-group has-feedback">
            <input type="search" class="form-control input-sm input-block input-inline" id="formInput" placeholder="输入连接名"
                   style="width: 100%">
            <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
        </div>
    </div>
    <div class="content_wrap">
        <div class="zTreeDemoBackground left">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
        <div id="rMenu">
            <ul>
              <li id="creat" onclick="hideRMenu()">
                  <form action="javascript:;" id="simple_form2"  enctype="multipart/form-data"  class="updateFlow form-horizontal">
                    上传
                    <input type="hidden" id="curDir2" class="curDir" name="currentDir" value="">
                    <input type="file" id="file2" name="file" accept=".txt,.csv" data-required="1" class="fileinput" onchange="rightSubmitData()"/>
                  </form>
                </li>
                <li id="addParent" onclick="return false;">新建文件夹</li>
                <li id="edit" onclick="return false;">重命名</li>
                <li id="remove" onclick="return false;">删除文件</li>
            </ul>
        </div>
    </div>


</div>

<script>

    var zTree, rMenu;
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        zTree = $.fn.zTree.getZTreeObj("treeDemo");
        rMenu = $("#rMenu");
        $("#addParent").bind("click", {isParent:true}, add);
        $("#addParent1").bind("click", {isParent:true}, add);
        $("#edit").bind("click", edit);
        $("#remove").bind("click", remove);
        $("#add").bind("click", creat);
        $("#realdelete").bind("click",realDelete);
        initUpload();

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
            var nodes = treeObj.getNodes();
            treeObj.reAsyncChildNodes(nodes, "refresh");
            hideRMenu();
            addDataModal();
        }
        function hideRightMenu() {
            hideRMenu();
        }



    });
</script>

