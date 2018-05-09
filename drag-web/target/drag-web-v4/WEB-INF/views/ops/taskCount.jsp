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
    <title>可视化数据处理分析平台 | 任务运维</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/ops/scheduler.css">
    <link rel="stylesheet" href="${basePath}/content/css/ops/ops.css">
    <link rel="stylesheet" href="${basePath}/content/css/ops/taskCount.css">
    <link href="${basePath}/assets/global/plugins/laydate/theme/default/laydate.css" rel="stylesheet" type="text/css" />

    <script src="${basePath}/assets/global/plugins/laydate/laydate.js"></script>






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
        <div class="index page-content">
            <%--<div class="row">--%>
            <input type="hidden" value="${taskStatus}" id="taskStatus"/>
                <div><span class="opsDescribe">监控概况</span>
                    <div class="menu_ops">
                        <div class="opsDiv" style="padding-top: 18px;">
                            <div class="typeAll" id="type">
                                <div class="allTypeNum">
                                    <div class="allType">
                                        <span>全部状态</span>
                                        <span class="dn"><i class="icon iconfont icon-dui"></i></span>
                                    </div>
                                </div>
                                <div class="choose dn">
                                    <div class="selectType">
                                        <span id="chooseOne"></span>
                                        <span class="icon iconfont icon-yousanjiao"></span>
                                    </div>
                                    <div class="selectChoose">
                                        <div class="option" value="1">成功</div>
                                        <div class="option" value="0">失败</div>
                                        <div class="option" value="2">运行中</div>
                                        <div class="option" value="3">待运行</div>
                                    </div>
                                </div>
                                <div class="add">添加<span class="addIcon"><i class="icon iconfont icon-jia font-color" style="font-size: 20px;"></i></span></div>
                            </div>

                            <span class="line">|</span>

                            <label style="color: #7f8fa4;padding-top: 4px; "><i class="icon iconfont icon-renwujindu" style="padding-right: 6px;padding-top: 2px;"></i><span>结束时间从</span></label>
                            <input id="chooseDate" placeholder="选择时间范围" value="" type="text" >



                            <div style="float: right;">
                                <div style="float: left">
                                    <input  placeholder="请搜索任务名称..." id="schedulerName" style="border: none ;width: 300px;text-align: center"/>
                                    <div style="width: 300px;height: 1px;background-color: #1bbc9b;"></div>
                                </div>
                                <div style="display: inline-block;float: right"> <i class="fa fa-search searchTask" style="color: #1bbc9b"></i></div>
                            </div>


                        </div>
                    </div>
                </div>
                <div style="height: 70px;width: 100%">
                    <div class="opsDiv">
                        <div class="allStateCount">
                            <span class="allStateSpan">当前任务总数：<span id="nowTaskCount">20</span></span>
                        </div>
                        <div  class="runStateCount">
                            <span>运行成功：<span id="state1"></span></span><span>运行失败：<span id="state0"></span></span><span>运行中：<span id="state2"></span></span><span>待运行：<span id="state3"></span></span>
                        </div>


                    </div>
                </div>

                <table class="table table-striped table-hover table3" style="table-layout: fixed">
                    <%--<caption class="text">工作流 <a href="" style="float: right;color: #1bbc9b">更多 <span style="letter-spacing: 0.2em;font-weight: bold">···</span></a></caption>--%>
                    <thead>
                        <tr style="height: 60px" class="tableTr">
                            <th width="28%" style="vertical-align: middle;">任务名称</th>
                            <th width="14%" style="vertical-align: middle;">创建时间</th>
                            <th width="10%" style="vertical-align: middle;"></th>
                            <th width="10%" style="vertical-align: middle;">运行状态</th>
                            <th width="10%" style="vertical-align: middle;">运行时长<span id="runTimeSort" style="cursor: pointer;color: #ffffff"><i class="listUpShow icon iconfont icon-iconzhenghe32" ></i></span></th>
                            <th width="14%" style="vertical-align: middle;">开始时间</th>
                            <th width="14%" style="vertical-align: middle;">结束时间</th>

                        </tr>
                    </thead>
                    <tbody id="taskBody">
                    <%--<tr>--%>
                        <%--<td>工业数据清洗</td>--%>
                        <%--<td>2017-07-06   01:17:12</td>--%>
                        <%--<td> <i class="fa fa-search lookLog"></i></td>--%>
                        <%--<td>失败</td>--%>
                        <%--<td>10秒</td>--%>
                        <%--<td>2017-07-06   01:17:12</td>--%>
                        <%--<td>2017-07-06   01:17:22</td>--%>
                    <%--</tr>--%>

                    </tbody>
                </table>
            <%--</div>--%>

                <div class="col-md-12">
                    <input type="hidden" id="totalpage" value=""/>
                    <input type="hidden" id="curPage" value=""/>
                    <div style="float: left;color: #7f8fa4;height: 38px;line-height: 35px">Page<span id="new_num">1</span>of <span class="pagination-panel-total">15</span></div>
                    <nav aria-label="Page navigation" style="float: right">
                        <ul class="pagination">
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&lt;</span>
                                </a>
                            </li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-hidden="true">&gt;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>


        </div>

        <!-- END CONTENT BODY -->
    </div>
    <%--查看日志弹框--%>
    <div class="modal fade" id="seeLogs" tabindex="-1" role="basic" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 654px;height: 600px;border-radius: 5px !important;margin-top: 100px">
                <div class="modal-header ">
                    <div><span data-dismiss="modal" aria-hidden="true" style="float: right;font-size: 25px;color: #fff;margin-top: -10px;cursor: pointer">&times;</span></div>
                    <h4 class="modal-title">日志详情</h4>
                </div>
                <div>

                    <div class="form-group">
                        <div class="textWordFlowDiv" >
                            <div  style="height: 500px;width: 520px;padding-left: 20px;" id="innerDiv">

                            </div>

                        </div>
                    </div>

                </div>
            </div>

            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
        <!-- /.modal-dialog -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='7'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/charts-echarts.js" type="text/javascript"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/content/js/ops/taskOpsLog.js"></script>

<script>

    $(function () {
        //滚动条
        $('#innerDiv').slimScroll({
            color: '#aeb2b7',
            railColor: '#eee',
            size: '5px',
            height: "500px" ,
            alwaysVisible: true,
            disableFadeOut: false,
            wheelStep: 18,
            borderRadius: "15px !important",
            distance: '4px', //组件与侧边之间的距离
            start: 'top', //默认滚动位置：top/bottom
        });

    })

</script>


</body>

</html>