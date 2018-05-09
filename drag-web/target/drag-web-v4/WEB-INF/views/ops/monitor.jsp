
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<!DOCTYPE html>
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 运维中心</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <%--<link rel="stylesheet" href="${basePath}/content/css/index.css">--%>
    <%--<link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">--%>
    <link rel="stylesheet" href="${basePath}/content/css/ops/ops.css">
    <link rel="stylesheet" href="${basePath}/content/css/ops/scheduler.css">
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link href="${basePath}/assets/global/plugins/laydate/theme/default/laydate.css" rel="stylesheet" type="text/css" />

    <script src="${basePath}/assets/global/plugins/laydate/laydate.js"></script>
    <!-- END PAGE LEVEL PLUGINS -->

</head>
<body>
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
        <div class="index page-content">
            <%--<div class="row">--%>
            <div ><span class="opsDescribe">监控概况</span>
                <div class="menu_ops" style="border-radius: 4px !important;box-shadow:0px 0 10px #d5dadc;">
                    <div class="opsDiv">
                        <div class="opsLefts">任务完成情况&nbsp;&nbsp;<span class="line">|</span></div>
                        <div class="opsLeft opsColor">今天<img class="paddingLeft" src="${basePath}/images/line1.png"></div>
                        <div class="opsLeft opsColor">昨天<img class="paddingLeft" src="${basePath}/images/line2%20.png"></div>
                        <div class="opsLeft opsColor">历史平均<img class="paddingLeft" src="${basePath}/images/Fill.png"></div>
                        <a href="${basePath}/drag/ops/taskMisCount.do"><div class="opsRight falseDiv"><span class="leftSpan"><i class="icon iconfont icon-wrong1" style="color: #ffffff"></i></span><span class="rightSpan state0"></span></div></a>
                        <a href="${basePath}/drag/ops/taskRightCount.do"><div class="opsRight trueDiv"><span class="leftSpan"><i class="icon iconfont icon-dui2" style="color: #ffffff"></i></span><span class="rightSpan state1"></span></div></a>
                        <div class="opsRight opsColor">24 小时</div>

                    </div>
                </div>
            </div>
                <%--任务完成情况图--%>
            <div id="complete" style="height: 370px;width: 100%;margin-top: 30px;"></div>
                <div class="timeSort">
                    <div class="index">
                        <%--<div class="row">--%>
                            <div   class="text" style="background-color: #ecf0f1;padding: 10px 0px;">任务时长排行 <div class="timeLink"  style="float: right;color: #1bbc9b;cursor: pointer"> <span style="letter-spacing: 0.2em;font-weight: bold">···</span></div></div>
                            <div class="tableRecord">
                                <table class="table table_fix_width" >
                                    <div id="noRunRecord" style="text-align: center;padding-top: 7%">
                                        <img src="${basePath}/images/emptyData0.png">
                                        <p style="color: #85989e;font-size: 21px;">无运行记录</p>
                                    </div>
                                    <div >
                                        <thead id="runRecord">
                                        <tr style="height: 60px" class="tableTr">
                                            <th width="30%" style="vertical-align: middle;">任务名</th>
                                            <th width="40%" style="vertical-align: middle;">创建者</th>
                                            <th width="30%" style="vertical-align: middle;">时长<span class="timeLong" ><i class="listUpShow icon iconfont icon-iconzhenghe32 " ></i></span></th>

                                        </tr>
                                        </thead>
                                        <tbody class="timeLongFrom">
                                        </tbody>
                                    </div>

                                </table>
                                <%--</div>--%>
                            </div>
                    </div>


                </div>
                <%--<div id="zhexian" style="width: 850px;height: 370px;margin-top: 30px;border-radius: 4px !important;box-shadow:0px 0 10px #d5dadc;"></div>--%>
                <div  style="width: 53%;height: 370px;margin-top: 30px;border-radius: 4px !important;box-shadow:0px 0 10px #d5dadc;" >
                    <div class="conditionFrom" >
                        <span class="taskCondition">任务运行情况</span>
                        <span class="line">|</span> <span style=" color: #7f8fa4;">今天</span>
                        <div class="taskConditionRight">24小时</div>
                    </div>

                    <div style="height: 320px;" id="zhexian"></div>
                </div>
                <div class="timeSort">
                    <div class="index">
                        <div  class="text" style="background-color: #ecf0f1;padding: 10px 0px; ">近一个月出错排行 <div class="moreError" style="float: right;color: #1bbc9b;cursor: pointer"> <span style="letter-spacing: 0.2em;font-weight: bold">···</span></div></div>
                        <%--<div class="row">--%>
                        <div class="tableRecord">
                            <table class="table table_fix_width"  >
                                <div id="yesMark" style="text-align: center;padding-top: 7%">
                                    <img src="${basePath}/images/emptyData1.png">
                                    <p style="color: #85989e;font-size: 21px;">无出错记录</p>
                                </div>

                                <div>
                                    <thead id="failLineDiv">
                                    <tr style="height: 60px" class="tableTr">
                                        <th width="30%" style="vertical-align: middle;">任务名</th>
                                        <th width="40%" style="vertical-align: middle;">创建者</th>
                                        <th width="30%" style="vertical-align:
                                        middle;">出错次数<span class="failNum"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>

                                    </tr>
                                    </thead>
                                    <tbody class="failTable">

                                    </tbody>
                                </div>
                            </table>
                        </div>


                    </div>


                </div>


                <div  style="width: 53%;height: 370px;margin-top: 30px;border-radius: 4px !important;box-shadow:0px 0 10px #d5dadc;" >
                    <div class="conditionFrom" >
                        <span class="taskCondition">调度任务数量趋势</span> <span class="line" style="float: left">|</span><div class="buttonStates">当前任务总数：<span class="todayNum"></span></div>
                        <div class="taskConditionRight">同比昨天：<span class="yoyToday"></span></div><div class="taskConditionRight">同比上周:<span class="yoyWeek"></span></div><div class="taskConditionRight">同比上月:<span class="yoyMonth"></span></div>
                    </div>

                    <div style="height: 320px;" id="tiaoxin"></div>
                </div>
        </div>

        <!-- END CONTENT BODY -->
    </div>

    <%--时长排行--%>

    <div class="modal fade" id="timeSortLink" tabindex="-1" role="basic" aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-header" style="    width: 1350px;    margin-left: -350px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">时长排行</h4>
            </div>
            <div class="selectDivFound">
                <div class="dateSelectDiv" >
                    <label ><i class="icon iconfont icon-renwujindu" style="color: #64848e;font-size: 20px"></i></label>
                    <input id="chooseDate1" placeholder="选择时间范围" value="" type="text" >
                </div>

                <div class="portlet light portlet-fit  ">
                    <div class="portlet-body index " >
                        <table class="tableTh table  table-hover table1" >
                            <thead>
                            <tr style="height: 60px" class="tableTr">
                                <th width="11%" style="vertical-align: middle;">任务名称</th>
                                <th width="11%" style="vertical-align: middle;">创建人</th>
                                <th width="19%" style="vertical-align: middle;">创建时间<span class="createTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                                <th width="19%" style="vertical-align: middle;">开始时间<span class="firstTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                                <th width="20%" style="vertical-align: middle;">结束时间<span class="lastTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                                <th width="10%" style="vertical-align: middle;">时长<span class="schJobHisRunTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                                <th width="10%" style="vertical-align: middle;">状态</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <div class="tableBorderSolid"  ></div>
                <div   id="innerDiv" style="height: 680px;width: 1300px;    margin: 0px 30px 15px 30px; " >
                    <!-- BEGIN VALIDATION STATES-->
                    <div class="portlet light portlet-fit  ">
                        <div class="portlet-body index " >


                            <table class="table  table-hover table1" style=" border: 1px solid #dce3e4;">
                                <tbody id="timeCountForm">
                                </tbody>
                            </table>

                            <!-- END FORM-->
                        </div>
                    </div>
                    <!-- END VALIDATION STATES-->
                </div>


            </div>
            </div>

            <!-- /.modal-content -->

        <!-- /.modal-dialog -->
    </div>
    <%--出错排行--%>
<div class="modal fade" id="errorSortLink" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog ">
        <div class="modal-header" style="    width: 1350px;    margin-left: -350px;">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <h4 class="modal-title">出错排行</h4>
        </div>
        <div class="selectDivFound">
            <div class="dateSelectDiv" >
                <label ><i class="icon iconfont icon-renwujindu" style="color: #64848e;font-size: 20px"></i></label>
                <input id="chooseDate2" placeholder="选择时间范围" value="" type="text" >
            </div>

            <div class="portlet light portlet-fit  ">
                <div class="portlet-body index " >
                     <table class="tableTh table  table-hover table1">
                        <thead>
                        <tr style="height: 60px" class="tableTr">
                            <th width="11%" style="vertical-align: middle;">任务名称</th>
                            <th width="12%" style="vertical-align: middle;">创建人</th>
                            <th width="20%" style="vertical-align: middle;">创建时间<span class="createTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                            <th width="20%" style="vertical-align: middle;">第一次开始运行时间<span class="firstTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                            <th width="20%" style="vertical-align: middle;">最后一次运行结束时间<span class="lastTime" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                            <th width="9%" style="vertical-align: middle;">出错次数<span class="num" style="cursor: pointer"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                            <th width="8%" style="vertical-align: middle;">状态</th>
                        </tr>
                        </thead>
                       </table>

                </div>
            </div>
            <div class="tableBorderSolid"  ></div>
            <div   id="innerDiv1" style="height: 680px;width: 1270px;    margin: 0px 30px 15px 30px; " >
                <!-- BEGIN VALIDATION STATES-->
                <div class="portlet light portlet-fit  ">
                    <div class="portlet-body index " >


                        <table class="table  table-hover table1" style=" border: 1px solid #dce3e4;">
                            <tbody id="failCountFrom">

                            </tbody>
                        </table>

                        <!-- END FORM-->
                    </div>
                </div>
                <!-- END VALIDATION STATES-->
            </div>


        </div>

        <%--</div>--%>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>




    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='5'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/charts-echarts.js" type="text/javascript"></script>
<script src="${basePath}/js/ops.js" type="text/javascript"></script>
<script src="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<%--<script src="${basePath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>--%>
<script src="${basePath}/content/js/ops/monitor.js" type="text/javascript"></script>
<script>
    $("body #innerDiv").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
    $("body #innerDiv1").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
//    $('#innerDiv').slimScroll({
//        color: '#1abc9c',
//        railColor: '#00ffcb',
//        railVisible: true, //是否 显示轨道
//        railOpacity: 1, //轨道透明度
//        size: '5px',
//        opacity: 1, //滚动条透明度
//        height: "800px" ,
//        alwaysVisible: true,
//        disableFadeOut: false,
//        wheelStep: 18,
//        distance: '4px', //组件与侧边之间的距离
//        start: 'top', //默认滚动位置：top/bottom
//        railClass: 'slimScrollRail1', //轨道div类名
//        barClass: 'slimScrollBar1', //滚动条div类名
//    });
//    $('#innerDiv1').slimScroll({
//        color: '#1abc9c',
//        railColor: '#00ffcb',
//        railVisible: true, //是否 显示轨道
//        railOpacity: 1, //轨道透明度
//        size: '5px',
//        opacity: 1, //滚动条透明度
//        height: "800px" ,
//        alwaysVisible: true,
//        disableFadeOut: false,
//        wheelStep: 18,
//        distance: '4px', //组件与侧边之间的距离
//        start: 'top', //默认滚动位置：top/bottom
//        railClass: 'slimScrollRail1', //轨道div类名
//        barClass: 'slimScrollBar1', //滚动条div类名
//    });
</script>


</body>

</html>