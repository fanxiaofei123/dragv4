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
    <title>可视化数据处理分析平台 | 问题反馈</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/logManagement/list.css">
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
            <div id="progress" class="progress" style="display: none">
                <div class="progress-bar progress-bar-info progress-bar-striped active" id="progress-bar"  style="z-index:1000;width: 20%;">
                    <div id="progress-value" class="progress-value">85%</div>
                </div>
            </div>
            <div class="loading"  index="3" id="loading" style="position: relative;">
                <div style="display: none" class="demo"></div>
            </div>
            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-docs font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">问题反馈</span>
                            </div>
                            <div class="actions">
                                <label>筛选日期：</label>
                                <div class="input-group input-large date-picker input-daterange" data-date="" data-date-format="yyyy/mm/dd">
                                    <input type="text" id="startTime" class="form-control" name="from">
                                    <span class="input-group-addon"> 至 </span>
                                    <input type="text" id="endTime" class="form-control" name="to">
                                </div>
                                <!-- /input-group -->
                                <button class="btn green" onclick="selectTime()" id="selecttime"> 查询
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                <thead>
                                <tr>
                                    <th width="20%"> 反馈人 </th>
                                    <th width="25%"> 反馈内容 </th>
                                    <th width="15%"> E-mail </th>
                                    <th width="10%"> 电话 </th>
                                    <th width="15%"> 反馈时间 </th>
                                    <th width="15%"> 操作 </th>
                                </tr>
                                </thead>
                                <tbody id="tbodyList">
                                </tbody>
                            </table>
                            <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="sample_4_paginate">


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
<script>window.nav='12'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>

<script src="${basePath}/assets/global/plugins/moment.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/components-date-time-pickers.js" type="text/javascript"></script>
</body>
<script>
    function getData(page) {
        if(null == page || "" == page){
            return page = 1;
        }
        $.ajax({
            async:false,
            type:"POST",
            url:basePath+"/drag/backMsg/selectAll.do",
            data:{page:page,startTime:$("#startTime").val(),endTime:$("#endTime").val()},
            success:function(data){
                var b =  jQuery.parseJSON(data);
                $("#tbodyList").empty();//清空数据区
                next = b.next;//下一页数
                previous = b.previous; //上一页数
                curPage = page; //当前页
                totalPage = b.total; //总页数
                var str ="";
                for(var i=0; i<b.rows.length; i++)
                {
                    str+='<tr class="odd gradeX">'
                        +'<td><span>'+b.rows[i].frontname+'</span></td>'
                        +'<td><span>'+subString(b.rows[i].content)+'</span></td>'
                        +'<td><span>'+b.rows[i].email+'</span></td>'
                        +'<td><span>'+b.rows[i].phone+'</span></td>'
                        +'<td><span>'+dateTimeFormata(b.rows[i].createtime)+'</span></td>'
                        +'<td><span><a href="'+basePath+'/drag/backMsg/FindById.do?id='+b.rows[i].id+'">查看详情</a><a class="exportExcel" href="" onclick="del('+b.rows[i].id+')">删除</a></span></td></tr>'
                }
                $("#tbodyList").html(str);
            },
            complete:function(){ //生成分页条
                getPageBarTime();
                //fun();
            },
            error:function(){
                alert("数据加载失败");
            }

        });
        //获取分页条
        function getPageBarTime(){
            $("#sample_4_paginate").html("<div class='pagination-panel'>" +
                "<a href='javascript:void(0)' onclick='getData("+previous+")' " +
                " class='btn btn-sm default prev'><i class='fa fa-angle-left'></i></a>" +
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"' " +
                "style='text-align:center; margin: 0 5px;' disabled><a href='javascript:void(0)' onclick='getData("+next+")' " +
                " class='btn btn-sm default next'><i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页</div>");
        }
    }
    //按照时间查询
    function selectTime(){
        getData(1);
    }
    //首次进入页面显示
    $(function () {
       $.ajax({
            async:false,
            type:"POST",
            url:basePath+"/drag/backMsg/selectAll.do",
            data:{startTime:$("#startTime").val(),endTime:$("#endTime").val()},
            success:function(data){
                var b =  jQuery.parseJSON(data);
                $("#tbodyList").empty();//清空数据区
                next = b.next;//下一页数
                previous = b.previous; //上一页数
                curPage = 1; //当前页
                totalPage = b.total; //总页数
                var str ="";
                for(var i=0; i<b.rows.length; i++)
                {
                    str+='<tr class="odd gradeX">'
                        +'<td><span>'+b.rows[i].frontname+'</span></td>'
                        +'<td><span>'+subString(b.rows[i].content)+'</span></td>'
                        +'<td><span>'+b.rows[i].email+'</span></td>'
                        +'<td><span>'+b.rows[i].phone+'</span></td>'
                        +'<td><span>'+dateTimeFormata(b.rows[i].createtime)+'</span></td>'
                        +'<td><span><a href="'+basePath+'/drag/backMsg/FindById.do?id='+b.rows[i].id+'">查看详情</a><a class="exportExcel" href="" onclick="del('+b.rows[i].id+')">删除</a></span></td></tr>'
                }
                $("#tbodyList").html(str);
            },
            complete:function(){ //生成分页条
                getPageBarTime();
            },
            error:function(){
                alert("数据加载失败");
            }

        });
        //获取分页条
        function getPageBarTime(){
            $("#sample_4_paginate").html("<div class='pagination-panel'>" +
                "<a href='javascript:void(0)' onclick='getData("+previous+")' " +
                " class='btn btn-sm default prev'><i class='fa fa-angle-left'></i></a>" +
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"' " +
                "style='text-align:center; margin: 0 5px;' disabled><a href='javascript:void(0)' onclick='getData("+next+")' " +
                " class='btn btn-sm default next'><i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页</div>");
        }

    });
    //删除
    del = function (data) {
        $.ajax({
            async: false,
            type:"POST",
            url:basePath+"/drag/backMsg/deleteById.do",
            data:{id:data},
            success:function (data) {
                if(data == 200){
                    alert("删除成功！")
                }
            }
        })
    };

    function subString(str){
     return str.length >= 20 ? str.substring(0, 20)+"......" : str;
    }
    //日期转化
    function dateTimeFormata(date) {
        var now=new Date(date);
        return formatDate(now);

    }
    function formatDate(now) {
        var year=now.getFullYear();
        var month=now.getMonth()+1;
        var date=now.getDate();
        var hour=now.getHours();
        var minute=now.getMinutes();
        var second=now.getSeconds();
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
    }
</script>

</html>
