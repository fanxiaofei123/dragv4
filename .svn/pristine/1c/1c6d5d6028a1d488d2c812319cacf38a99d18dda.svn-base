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
    <title>可视化数据处理分析平台 | 导航</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/pages/css/profile-2.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/navigation.css">
    <link rel="stylesheet" href="${basePath}/content/css/tree/tree.css">
    <script src="${basePath}/assets/pages/scripts/ui-tree.js" type="text/javascript"></script>

    <!-- END PAGE LEVEL PLUGINS -->

    <style type="text/css">
        ul{
            list-style: none;
        }
        ul li{
            margin-top: 10px;
        }
        #insertNode{
            display: none;

        }
        #updateNode{
            display: none;

        }
    </style>
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
            <!--  <h3 class="page-title"> 使用帮助
                 <small>Help</small>
             </h3> -->
            <div class="caption">
                <h4> <i class="icon-docs font-green"></i>
                    <span class="caption-subject font-green sbold uppercase">树形目录</span></h4>
            </div>
            <!-- END PAGE HEADER-->
            <div class="portlet box blue">
                <div class="portlet-body">
                    <%--<input type="button" value="添加" onclick="showDiv()">
                    <input type="button" value="修改" onclick="showDiv2()"/>
                    <input type="button" value="删除" onclick="deleteById()"/>
                    <div class="tree well">
                        <ul>
                            <c:forEach items="${fNode}" var="node1">
                            <li><input type="checkbox" class="checkbox" id="${node1.id}"><span onclick="findChild(this)" tid="${node1.id}"><i class="icon-folder-open"></i> ${node1.name}</span></li></c:forEach>

                        </ul>
                    </div>
                    <div id="insertNode">
                        目录名:<input id="nodeName" type="text" name="nodeName"/>
                        优先级:<input id="priority" type="text" name="priority"/>
                        <input type="button" value="添加" onclick="addNode()"/>
                        <input type="button" value="取消" onclick="hideDiv()"/>

                    </div>

                    <div id="updateNode">
                        父目录:<input id="pName" type="text" name="pName"/>
                        目录名:<input id="cName" type="text" name="cName"/>
                        <input type="button" value="修改" onclick="updateNode()"/>
                        <input type="button" value="取消" onclick="hideDiv()"/>

                    </div>--%>

                </div>
            </div>
        </div>
        <!-- END CONTENT BODY -->
    </div>

    <!-- END CONTENT -->
</div>

<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->

<script>window.nav='10'
/*$(function () {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
});

function findChild(obj) {
   var tid=$(obj).attr("tid")
    $.get("/drag/treemode/selectSNode.do",{pid:tid},function(data){
        data = JSON.parse(data);
        if(data==null){
            alert($(obj).text());
            alert("aaa")
        }else{
            var nodes="";
            for(var i=0;i<data.length;i++){
                nodes+="<li><input type='checkbox' class='checkbox' id='"+data[i].id+"'/><span onclick='findChild(this)' tid='"+data[i].id+"'><i class='icon-minus-sign'></i>"+data[i].name+"</span><a>Goes somewhere</a></li>"
            }
            newnodes="<ul>"+nodes+"</ul>";
            $(obj).nextAll("ul").remove();
            $(obj).parent().append(newnodes);
        }


    });




}

function deleteById() {
    var ids='';
    $("input:checked").each(function(){
        ids+=$(this).attr("id")+",";

    });
    if(ids==""||ids==null){
        alert("至少选择一个目录")
    }else{
        $.get("/drag/treemode/deleteById.do",{id:ids},function (data) {
            if(data=="reflush"){
                location.href="/drag/dispatcher/modeltree.do";
            }
        });
    }


}

function showDiv() {
    $("#insertNode").css('display','block');

}
function showDiv2() {
    var id=$("input:checked").attr("id");
    if(id!=null&&id!=""){
        getNodeInfo();
        $("#updateNode").css('display','block');
    }else {
        alert("至少选择一个目录");
    }


}
function hideDiv() {
    $("#insertNode").css('display','none');
    $("#updateNode").css('display','none');
}

function addNode() {
    var pid=$("input:checked").attr("id");
    if(pid==""||pid==null){
        pid=0;
    }
    var nodeName=$("#nodeName").val();
    var priority=$("#priority").val();
    if(nodeName==""||priority==""){
        alert("请输入相应的信息")
    }else{
        $.get("/drag/treemode/addNode.do",{pid:pid,nodeName:nodeName,priority:priority},function (data) {
            if(data==1){
                location.href="/drag/dispatcher/modeltree.do";
            }else{
                alert("添加失败");
            }
        })
    }

}

function getNodeInfo(){
    var id=$("input:checked").attr("id");
    $.get("/drag/treemode/getNodeInfo.do",{id:id},function (data){
        data=JSON.parse(data);
        $("#pName").val(data[0]);
        $("#cName").val(data[1]);

    })
}

function updateNode() {
  var pName=$("#pName").val();
  var cName=$("#cName").val();
  var id=$("input:checked").attr("id");
  $.get("/drag/treemode/updateNode.do",{pName:pName,cName:cName,id:id},function (data) {
      var data=JSON.parse(data)
         if(data==0){
             alert("不存在该目录");
         }else{
             location.href="/drag/dispatcher/modeltree.do";
         }

  })

}*/
</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
</body>

</html>
