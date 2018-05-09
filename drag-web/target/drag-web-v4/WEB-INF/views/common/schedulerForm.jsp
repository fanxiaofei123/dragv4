<%--
  Created by IntelliJ IDEA.
  User: sky
  Date: 2017/9/7
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${basePath}/content/css/ops/ops.css">
<link href="${basePath}/assets/global/plugins/laydate/theme/default/laydate.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/assets/global/plugins/laydate/laydate.js"></script>
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
