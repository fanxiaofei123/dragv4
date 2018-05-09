<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/6/30
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${basePath}/content/css/workSpace/workSpaceList.css">
<style>
    .tree_menu #tree_left li span, .tree_menu #tree_left li a{
        padding: 10px 8px;
    }
    .tree_menu #tree_left li span{
        border-bottom: 1px solid #eaeaea;
    }
    .tree_menu #tree_left li,.tree_menu #tree_left li span,.tree_menu #tree_left li a{
        color: #333333!important;
    }
    .tree_menu #tree_left li span strong{
        margin-left: 10px;
        padding: 0px 5px;
        color: #fff;
        background: #1bbc9b;
        font-size: 12px;
        font-weight: normal;
        border-radius: 20px!important;
    }
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
    .tree_menu #tree_left li .hover,.tree_menu #tree_left li span:hover,.tree_menu #tree_left li a:hover,#tree_left .tree_active
    {
        background-color:#1bbc9b;
        color: #333333!important;
    }
    #tree_left .tree_active a{
        color: #333333!important;
    }
    .tree_menu #tree_left li span:hover strong,.tree_menu #tree_left li a:hover strong,
    .tree_menu #tree_left li .tree_active strong {
        color: #1bbc9b;
        background: #fff;
    }
    .tree_menu #tree_left li .tree_active .On:after,
    .tree_menu #tree_left li .tree_active .Off:after
    {
        color: #fff!important;
    }
    .menu_name .formInput{
        padding: 0 15px;
    }
    .menu_name .formInput span{
        right: 15px;
    }
    .menu_name p{
        margin: 30px 15px 10px;
        font-size: 16px;
        color: #0f9b7e;
    }
    .menu_name p i{
        margin-right: 8px;
    }
    #right_menu{
        position: absolute;
        padding: 2px 0;
        z-index: 2;
        display: none;
        width: 220px;
        background: #ededed;
        border-radius: 5px!important;
    }
    #right_menu li{
        padding: 10px;
        cursor: default;
    }
    #right_menu li:hover,#right_menu li:hover i{
        background: #1abc9c;
        color: #fff;
    }
    #right_menu li i{
        margin-right: 10px;
        color: #a3a3a3;
    }
</style>
<div class="menu_left_width">
    <div class="menu_name">
        <p style="text-align: right"><i class="icon-social-dropbox"></i>工作流</p>
        <div class="formInput form-group has-feedback">
            <a class="btn btn-circle btn-icon-only btn-default" href="#basic" data-toggle="modal" title="创建新的工作空间">
                <i class="icon-plus"></i>
            </a>
            <input type="search" class="form-control input-sm input-block input-inline" onclick="selectWorkName()" id="formInput" placeholder="搜索工作流"
                   style="width: 100%">
            <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
        </div>
    </div>
    <div class="menu_box">
        <div class="tree_menu">
            <ul id="tree_left">

            </ul>
            <%--<a style="display: none" class="font-blue-steel" href="${basePath}/drag/flow/getflow.do?workspid=${workFlow.workspid }&workName=${workSpaceName }">${workSpaceName }</a>--%>
            <ul id="right_menu">
                <li><i class="icon-plus"></i>从模板新建实验</li>
                <li><i class="icon-plus"></i>新建空白实验</li>
                <li><i class="icon-folder-alt"></i>新建文件夹</li>
                <li><i class="icon-pencil"></i>重命名</li>
                <li><i class="icon-ban"></i>删除</li>
            </ul>
        </div>
    </div>


</div>

<script>
    $(function(){
        function setTreeStyle(obj){
            var objStyle = obj.children("b");
            var objList = obj.siblings("ul");
            if(objList.length == 1){
                var style = objStyle.attr("class");
                objStyle.attr("class","On2Off");
                setTimeout(
                    function(){
                        if(style == "Off"){
                            objList.parent().siblings("li").children("span").children(".On").each(function(){
                                setTreeStyle($(this).parent());
                            });
                            var H = objList.innerHeight();
                            objList.css({display:"block",height:"0"});
                            objList.animate({height:H},300,function(){$(this).css({height:"auto"});});
                            objStyle.attr("class","On");
                        }
                        else if(style == "On"){
                            objList.find("li").children("span").children(".On").each(function(){
                                setTreeStyle($(this).parent());
                            });
                            var H = objList.innerHeight();
                            objList.animate({height:0},300,function(){$(this).css({height:"auto",display:"none"})});
                            objStyle.attr("class","Off");
                        }
                    },
                    42
                );
            }
        }
        $("#tree_left").on('click','li span',function () {
            var id=$(this).parent().data("li");
            var str=$(this).data("span");
            var th=$(this).parent().find('ul');
            var tree=th.find('li').length;
            console.log("tree:"+tree);
//            if(tree<=1){
                getMenuDataFlow(1,id ,str,th);
//            }
            setTreeStyle($(this));
        });

//        右键
        document.oncontextmenu=function () {
            return false;
        };
        $("#tree_left").on('contextmenu','li .tree',function (e) {
            var key = e.which;
            if(key == 3) {
                var x = e.clientX - 258 +'px';
                var y = e.clientY;
                console.log(x);
                console.log(y);
                $("#right_menu").css({left:x,top:y,display:'block'});
            }
        });
        $(document).on('click',function (e) {
            e.stopPropagation();
            $("#right_menu").css({display:'none'})
        })
    });

    //工作空间搜索
    function selectWorkName(){
        getMenuDataWrokName(1);
    }
    selectWorkName();
    //左侧工作空间
    function getMenuDataWrokName(page){
        var workid = $("#formInput").val();
        var tmp = {};
        tmp.page = page;
        $.ajax({
            url:basePath+"/drag/work/getworklistname.do",
            type: 'POST',
            dataType: "json",
            data:{"page":page,"name":workid},
            beforeSend:function(){
                $("#tree_left").append("<span id='loading'>loading...</span>");//显示加载动画
            },
            success:function(json){
                $("#tree_left").empty();//清空数据区
                next = json.next;//下一页数
                previous = json.previous; //上一页数
                curPage = page; //当前页
                totalPage = json.total; //总页数
                var li = "";
                var list = json.rows;
                $.each(list,function(index,array){ //遍历json数据列
                    console.log("####"+array)
                    var str = array.name;
                    li += '<li data-li="'+array.id+'">'
                        +'<span data-span="' + ""+ str +"" + '" class="tree tree_active" ><b class="Off"><i class="icon-folder-alt"></i>'+array.name+'</b></span>'
                        +'<ul style="display:none;">'
                        +'<li ></li>'
                        +'</ul>'
                        +'</li>';


                });
                $("#tree_left").html(li);


                <%--$("#newFlow").attr("href",' ${basePath}/drag/flow/getflow.do?workspid='+workspid+'&workName='+workName+'#basic  ');--%>


            },
            complete:function(){ //生成分页条
//                getPageBarTimeName();
                //fun();
            },
            error:function(){
                alert("数据加载失败");
            }
        });
    }
    //获取分页条
//    function getPageBarTimeName(){
//
//        $("#sample_2_paginate").html("<div class='pagination-panel'>"+
//            "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getMenuDataWrokName("+previous+")'  >"+
//            "<i class='fa fa-angle-left'></i></a>"+
//            "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//            " style='text-align:center; margin: 0 5px;' disabled>"+
//            "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getMenuDataWrokName("+next+")' >"+
//            " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
//    }

    var curPage = 1;

    var previous,next,totalPage; //总记录数，每页显示数，总页数
    //以下为工作空间中查询的数据
    function getMenuDataFlow(page,workspid,workName,thi){
        var tmp = {};
        tmp.page = page;
        $.ajax({
            url:basePath+"/drag/flow/getflowlists.do",
            type: 'POST',
            dataType: "json",

            data:{"page":page,"workspid":workspid,"workName":workName},
            success:function(json){
                $("#dataFlowId").empty();//清空数据区
                next = json.next;//下一页数
                previous = json.previous; //上一页数
                curPage = page; //当前页
                totalPage = json.total; //总页数
                var leftMenu = "";
                var rightPage = "";
                var titleFlowName ="";
                var list = json.rows;
                $.each(list,function(index,array){ //遍历json数据列
                    leftMenu += "<li><span class=\"tree2\"><a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array.name+"</a></span></li>";
                    rightPage +=" <tr class='odd gradeX'>"+
                        " <td>"+
                        " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='"+array['id']+"' />"+
                        "<span></span>"+
                        "</label>"+
                        "</td>"+
                        "<td>"+
                        "<div class='fileType'>"+
                        "<i class='fa fa-file-code-o'></i>"+
                        "</div>"+
                        "<div class='fileContent'>"+
                        " <div class='fileTitle'>"+
                        "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array['name']+"</a>"+
                        "</div>"+
                        "<div class='fileSize'>属于 "+array['userMial']+"</div>"+
                        "</div>"+
                        "</td>"+
                        "<td>"+
                        "<div class='actions'>"+
                        "<a href='javascript:;' data-toggle='modal' onclick='showWorkFlow("+array['id']+")' title='查看工作流'>"+
                        "<i class='icon-eyeglasses'></i>"+
                        " </a>"+
                        "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow("+array['id']+")' title='设置工作流'>"+
                        "<i class='icon-note'></i>"+
                        " </a>"+
                        " <a href='javascript:;' onclick='deleteWorkFlow("+array['id']+")' title='删除工作空间'>"+
                        "<i class='icon-close'></i>"+
                        "</a>"+
                        " </div>"+
                        "<span>"+array['createTimes']+"</span>"+
                        "<input type='hidden' id='"+array['id']+"'  value='"+array['name']+"'/>"+
                        "<input type='hidden' id='"+array['id']+"a'  value='"+array['flowExplain']+"'/>"+
                        " </td>"+
                        "</tr>";
                });
                $(thi).html(leftMenu);
                $("#flowWorkTbody2").html(rightPage);
                $("#titleFlowName").html(workName);
                $("#workName").val(workName);
                $("#workspid").val(workspid);

            },
            complete:function(){ //生成分页条
                getPageBarflow();

                //fun();
            },

            error:function(){
                alert("数据加载失败");
            }
        });

    }
//    function getPageBarflow(page,workspid,workName,thi){
//
//        $("#sample_2_paginates2").html("<div class='pagination-panel'>"+
//            "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getMenuDataFlow("+previous+","+workspid+","+workName+","+thi+")'  >"+
//            "<i class='fa fa-angle-left'></i></a>"+
//            "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//            " style='text-align:center; margin: 0 5px;' disabled>"+
//            "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getMenuDataFlow("+next+","+workspid+","+workName+","+thi+")' >"+
//            " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
//    }
    function getPageBarflow(){

        $("#sample_2_paginates2").html("<div class='pagination-panel'>"+
            "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getMenuDataFlow("+previous+")'  >"+
            "<i class='fa fa-angle-left'></i></a>"+
            "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
            " style='text-align:center; margin: 0 5px;' disabled>"+
            "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getMenuDataFlow("+next+")' >"+
            " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
    }

</script>
