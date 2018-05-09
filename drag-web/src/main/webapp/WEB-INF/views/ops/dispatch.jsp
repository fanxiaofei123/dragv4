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
    <title>可视化数据处理分析平台 | 任务调度</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/laydate/need/laydate.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/ops/scheduler.css">
    <link rel="stylesheet" href="${basePath}/content/css/ops/ops.css">

    <link rel="stylesheet" href="${basePath}/content/css/remind/remindSetting.css">
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
                <div><span class="opsDescribe">调度任务</span>
                    <div class="menu_ops">
                        <div class="opsDiv" style="padding-top: 18px;">
                            <label style="color: #7f8fa4;"><i class="icon iconfont icon-renwujindu" style="padding-right: 6px"></i>调度生效时间从</label>
                            <input id="chooseDate" placeholder="选择时间范围" value="" type="text" >

                            <div class="addAndDelete " >

                                <a id="addRemind-icon"><img src="${basePath}/images/remind2.png" class="spanPadding "/><span  class="spanText">添加提醒</span></a>



                            </div>
                            <div class="addAndDelete " >

                                    <a class="addTaskDis"><img src="${basePath}/images/addTask.png" class="spanPadding "/><span class="spanText">添加任务</span></a>



                            </div>
                            <div class="addAndDelete " >
                                <div >
                                    <a class="deleteTaskDis"><img src="${basePath}/images/deleteTask.png" class="spanPadding "/><span class="spanText">删除任务</span></a>

                                </div>


                            </div>

                            <div style="float: right;">
                                <div style="float: left">
                                    <input  placeholder="请搜索任务名称..." id="searchJob" style="border: none ;width: 300px;text-align: center"/>
                                    <div style="width: 300px;height: 1px;background-color: #1bbc9b;"></div>
                                </div>
                                <div style="display: inline-block;float: right"> <i class="fa fa-search searchName" style="color: #1bbc9b;cursor: pointer"></i></div>
                            </div>
                        </div>

                    </div>
                </div>
                <div style="height: 70px;width: 100%">
                   <div style="padding: 22px 0;">

                   </div>
                </div>

                <table class="table table-striped table-hover table2">
                    <%--<caption class="text">工作流 <a href="" style="float: right;color: #1bbc9b">更多 <span style="letter-spacing: 0.2em;font-weight: bold">···</span></a></caption>--%>
                    <thead>
                        <tr style="height: 60px" class="tableTr">
                            <th class="table-checkbox " width="7%">
                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                <input type="checkbox" id="checkAll" class="group-checkable" data-set="#sample_2 .checkboxes" />
                                <span style="background: #ffffff;margin: -20px 0px;"></span>
                            </label>
                            </th>
                            <th width="24%" style="vertical-align: middle;">任务名称</th>
                            <th width="8%" style="vertical-align: middle;">调度状态</th>
                            <th width="12%" style="vertical-align: middle;"></th>
                            <th width="14%" style="vertical-align: middle;">工作流名称</th>
                            <th width="12%" style="vertical-align: middle;">最近修改时间</th>
                            <th width="22%" style="vertical-align: middle;">调度生效时间</th>


                        </tr>
                    </thead>
                    <tbody id="schedulerJobBody">
                    <%--"<td class=\"table-checkbox\"> <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+--%>
                        <%--"<input type='checkbox' class='group-checkable'' data-set='#sample_2 .checkboxes' />"+--%>
                        <%--"<span style=\"width: 20px\"></span>"+--%>
                        <%--"</label>"+--%>
                        <%--"</td>"+--%>
                    <%--"<td>"+array['schJobName']+"</td>"+--%>
                    <%--"<td>"+schJobEnable+"</td>"+--%>
                    <%--"<td>"+--%>
                        <%--"<i class=\"fa fa-search searchDispatch\" value="+array['schJobId']+"></i>"+--%>
                        <%--"<i class=\"icon-note editDispatch\" value="+array['schJobId']+"></i>"+--%>
                        <%--"<i class=\"icon-close deleteDispatch\" value="+array['schJobId']+"></i></td>"+--%>
                    <%--"<td>"+array['workFlowName']+"</td>"+--%>
                    <%--"<td>"+array['jobModify']+"</td>"+--%>
                    <%--"<td>"+array['startTime']+"/"+array['endTime']+"</td>"+--%>
                    <%--"</tr>"--%>
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

    <div class="modal fade" id="addTaskFrom" tabindex="-1" role="basic" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 654px;height: 600px;border-radius: 5px !important;margin-top: 100px">
                <div class="modal-header ">
                    <div><span data-dismiss="modal" aria-hidden="true" style="float: right;font-size: 25px;color: #fff;margin-top: -10px;cursor: pointer">&times;</span></div>
                    <h4 class="modal-title">工作流选择</h4>
                </div>
                <div>
                    <div class="form-group">
                        <div class="formInput form-group has-feedback" style="margin: 34px 66px 4px;">
                            <div  class="boxshadow">
                                <input type="search" class="form-control input-sm input-block input-inline workFlowName"  placeholder="输入工作流名称搜索"    style="width: 100%;border-radius: 4px !important;border:1px solid #1bbc9b"/>
                                <i class="fa fa-search searchFlowName" style="color: #1bbc9b;float: right;position: relative;top: -22px;right: 10px;"></i>
                            </div>

                        </div>
                    </div>
                    <div class="form-group">
                        <div class="textWordFlow" >
                            <div  style="height: 330px;width: 520px;padding-left: 20px;" id="innerDiv">
                                <ul class="list-unstyled ulLi flowLi">

                                </ul>

                            </div>

                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-12" style="text-align: center;margin-top: 25px;">

                                <button type="button" class="btn btn-default font-color border-color addDispatchFm" data-dismiss="modal" style="border-radius: 5px !important;padding: 5px 24px;background-color: #d5d9dc;color: #ffffff;border: none" id="nextWork" disabled="disabled">下一步</button>
                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="margin-left: 60px;border-radius: 5px !important;padding: 5px 30px;">取消</button>
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

    <div class="modal fade" id="addDispatchFrom" tabindex="-1" role="basic" aria-hidden="true">
        <input type="hidden"  id="hiddenTime"/>
        <div class="modal-dialog">
            <div class="modal-content" style="width: 654px;border-radius: 5px !important;margin-top: 100px">
                <div class="modal-header ">
                    <div><span data-dismiss="modal" aria-hidden="true" id="closeScheduler" style="float: right;font-size: 25px;color: #fff;margin-top: -10px;cursor: pointer">&times;</span></div>
                    <h4 class="modal-title">调度设置</h4>
                </div>
                <!-- BEGIN FORM-->
                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                    <div class="form-body">
                        <div class="form-group" style="padding-top: 20px">
                            <label for="" class="col-sm-3 control-label">任务名称：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control boxshadow fontDisColor" placeholder="请输入任务名称" onblur="taskNameNotNull()" id="taskName" style="border: 1px solid #1bbc9b">
                            </div><label style="color: #ff0000; padding-top: 6px;" class="tellReminder">*</label>
                        </div>
                        <div class="form-group">
                            <label for="" class="col-sm-3 control-label">调度模式：</label>
                            <div class="col-sm-7 fontDisColor fontDisColor1">
                                <label class="radio-inline " style="padding-left: 40px">
                                    <input data-check="0" type="radio" name="inlineRadioOption" id="inlineRadio3" class="onceExecute" value="options1" checked> 一次执行
                                </label>
                                <label class="radio-inline " style="padding-left: 70px">
                                    <input data-check="1" type="radio" name="inlineRadioOption" id="inlineRadio4" class="onceExecute" value="options2" > 周期性执行
                                </label>
                            </div>
                        </div>
                        <div class="form-group hideIntervalTime">
                            <label for="" class="col-sm-3 control-label" >间隔时间：</label>
                            <div class="col-sm-7 fontDisColor">
                                <input type="text" class=" fontDisColor" placeholder="" id="schJobInterval"  value="0"  onblur="schJobIntervalNotNull()" style="border: 1px solid #1bbc9b;width: 40px;height: 30px;border-radius: 4px !important;padding-left: 5px;">
                                <button id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="buttonTimeSelect buttonColor">
                                     分钟
                                </button>

                                    <ul class="dropdown-menu ulLi" aria-labelledby="dLabel">
                                        <li class="times" value="0"><i class='icon iconfont icon-dui'></i>分钟</li>
                                        <li class="times" value="1">小时</li>
                                        <li class="times" value="2">天</li>

                                    </ul>
                                <label class="fontDisColor schJobInterval1">(执行下一次调度)</label>

                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="" class="col-sm-3 control-label">开始时间：</label>
                            <div class="col-sm-7 fontDisColor">
                                <%--<input class="laydate-icon" id="start" type="text" style="width:200px; margin-right:10px;height: 30px;border: 1px solid #1bbc9b;border-radius: 5px !important;"/>--%>
                                    <input id="start" placeholder="" value="" type="text" class="inputPaddingLeft" onblur="startTimeNotNull()" />

                            </div>
                        </div>
                        <div class="form-group">
                            <label for="" class="col-sm-3 control-label">启用停止时间：</label>
                            <div class="col-sm-7 fontDisColor fontDisColor2">
                                <label class="radio-inline" style="padding-left: 40px">
                                    <input  type="radio" data-check="true" name="inlineRadioOption5" class="upStop" id="inlineRadio5" value="option1" > 是
                                </label>
                                <label class="radio-inline" style="padding-left: 70px">
                                    <input type="radio" data-check="false" name="inlineRadioOption5" class="upStop" id="inlineRadio6" value="option2" checked> 否
                                </label>
                            </div>
                        </div>
                        <div class="form-group stopTimeDiv" >
                            <label for="" class="col-sm-3 control-label">停止时间：</label>
                            <div class="col-sm-7 fontDisColor">
                                <input id="end" placeholder="请输入停止时间" value="" type="text"  class="inputPaddingLeft" onblur="endTimeNotNull()"/>

                                <%--<span><i class="icon iconfont icon-yousanjiao" style="color: #1bbc9b"></i></span>--%>

                            </div>
                        </div>

                        <div class="form-group">
                            <label for="" class="col-sm-3 control-label">任务描述：</label>
                            <div class="col-sm-7 fontDisColor">
                                <textarea class="form-control " id="schJobDesc" rows="5" style="border: 1px solid #1bbc9b;border-radius: 4px !important;"></textarea>
                            </div>
                        </div>

                        <div class="form-group " id="saveDiv">
                            <label for="" class="col-sm-3 control-label">调度生效：</label>
                            <div class="col-sm-7 fontDisColor fontDisColor3">
                                <label class="radio-inline" style="padding-left: 40px" >
                                    <input type="radio" data-check="1" name="inlineRadioOptions" class="schedulerResult" id="inlineRadi1" value="option1" checked> 是
                                </label>
                                <label class="radio-inline" style="padding-left: 70px">
                                    <input type="radio" data-check="0" name="inlineRadioOptions" class="schedulerResult" id="inlineRadi2" value="option2"> 否
                                </label>
                            </div>
                        </div>



                    <div class="form-actions " id="saveDiv1" style="margin-bottom: 30px;margin-top: 25px;">
                        <div class="row">
                            <div class="col-md-12" style="text-align: center;">

                                <button type="button" class="btn btn-default font-color border-color"   data-dismiss="modal" style="border-radius: 5px !important;padding: 5px 30px;"  id="submitScheduler"> 保存</button>
                                <button type="button" class="btn btn-default font-color border-color" data-dismiss="modal" style="margin-left: 60px;border-radius: 5px !important;padding: 5px 30px;">取消</button>
                            </div>

                        </div>
                    </div>

                </form>



            </div>

            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
        <!-- /.modal-dialog -->
    </div>

    <!-- END CONTENT -->
</div>

<%--添加提醒设置--%>
<div class="modal fade" id="addRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" id="create_new_link">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="closeform()"></button>
                <h4 class="modal-title">提醒设置</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="parame_form" class="updateFlow form-horizontal">
                                    <div class="form-body" style="padding: 0px">
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">提醒名称：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="" id="resource_databaseName">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">任务名称：</label>
                                            <div class="task col-sm-7">
                                                <div class="task_name" id="taskName1">
                                                    <%--<span data-id="1">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                    <%--<span data-id="2">菁蓉数据分析<i class="glyphicon glyphicon-remove"></i></span>--%>
                                                </div>
                                                <div class="task-name-ul">
                                                    <ul class="" id="shadowRadius">
                                                        <%--<li data-id="1">菁蓉数据分析1</li>--%>
                                                        <%--<li data-id="2">数据分析2</li>--%>
                                                        <%--<li data-id="3">菁蓉分析3</li>--%>
                                                        <%--<li data-id="4">菁据分析4</li>--%>
                                                        <%--<li data-id="5">菁蓉数析5</li>--%>
                                                        <%--<li data-id="6">菁蓉数析6</li>--%>
                                                        <%--<li data-id="7">菁蓉数析7</li>--%>
                                                    </ul>
                                                </div>

                                            </div>

                                        </div>

                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">接收人邮箱：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="132352462@qq.com" id="resource_hostIp">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">调试模式：</label>
                                            <div class="col-sm-7">
                                                <label class="radio-inline">
                                                    <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="0" checked="checked"> 出错
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="1"> 完成
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">内容：</label>
                                            <div class="col-sm-7">
                                                <textarea class="form-control" id="centent" rows="10"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="remindBtn btn btn-default border-color font-color" onclick="addTipConfig()" >确定</button>
                                                <button type="button" class="remindBtn1 btn btn-default border-color font-color" data-dismiss="modal">取消</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- END FORM-->
                            </div>
                        </div>
                        <!-- END VALIDATION STATES-->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<%--弹框页面--%>
<jsp:include page="../common/prompt.jsp"></jsp:include>
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<script>window.nav='6'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>

<script src="${basePath}/assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>
<script src="${basePath}/js/ops.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/content/js/ops/dispatch.js" type="text/javascript"></script>
<script type="text/javascript">


    $(function(){

        $('#innerDiv').slimScroll({
            color: '#aeb2b7',
            railColor: '#eee',
            size: '5px',
            height: "336px" ,
            alwaysVisible: true,
            disableFadeOut: false,
            wheelStep: 18,
            borderRadius: "15px !important",
            distance: '4px', //组件与侧边之间的距离
            start: 'top', //默认滚动位置：top/bottom
        });


    });
    //选择工作流

</script>
</body>

</html>
