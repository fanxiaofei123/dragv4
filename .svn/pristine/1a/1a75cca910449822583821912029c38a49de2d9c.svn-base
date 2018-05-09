<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/sdw.tld" prefix="sdw"%>
<%@ taglib uri="/WEB-INF/tld/sdwIf.tld" prefix="sdwIf"%>




<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 数据结果</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath}/content/css/updateData/updateData.css">
</head>


<style type="text/css">
    .demo{
        padding: 2em 0;
        background: linear-gradient(to right, #2c3b4e, #4a688a, #2c3b4e);
    }
    .progress{
        height: 25px;
        background: #262626;
        padding: 5px;
        overflow: visible;
        border-radius: 20px;
        border-top: 1px solid #000;
        border-bottom: 1px solid #7992a8;
        margin-top: 50px;
    }
    .progress .progress-bar{
        border-radius: 20px;
        position: relative;
        animation: animate-positive 2s;
    }
    .progress .progress-value{
        display: block;
        padding: 3px 7px;
        font-size: 13px;
        color: #fff;
        border-radius: 4px;
        background: #191919;
        border: 1px solid #000;
        position: absolute;
        top: -40px;
        right: -10px;
    }
    .progress .progress-value:after{
        content: "";
        border-top: 10px solid #191919;
        border-left: 10px solid transparent;
        border-right: 10px solid transparent;
        position: absolute;
        bottom: -6px;
        left: 26%;
    }
    .progress-bar.active{
        animation: reverse progress-bar-stripes 0.40s linear infinite, animate-positive 2s;
    }
    @-webkit-keyframes animate-positive{
        0% { width: 0; }
    }
    @keyframes animate-positive{
        0% { width: 0; }
    }
</style>
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
            <div class="page-bar">
                <ul class="page-breadcrumb" id="folder-navi">
                    <li onclick="showIndex(this)">
                        <i class="icon-home"></i>
                        <a class="font-blue-steel" href="javascript:;">结果数据</a>
                    </li>
                </ul>
            </div>

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
                                <i class=" icon-layers font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">数据列表</span>

                            </div>
                            
                             <div class="actions">
                                <a class="btn btn-circle btn-icon-only btn-default" href="#basic" data-toggle="modal" title="添加数据">
                                    <i class="icon-cloud-upload"></i>
                                </a>
                                <a class="btn btn-circle btn-icon-only btn-default" href="#basicFile" data-toggle="modal" title="创建文件夹">
                                    <i class="icon-folder-alt"></i>
                                </a>
                                <a class="btn btn-circle btn-icon-only btn-default" id="batchDel" href="javascript:;" title="删除选中">
                                    <i class="icon-trash"></i>
                                </a>

                            </div>
                        </div>


                        <div class="portlet-body">
                            <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                <thead>
                                <tr>
                                    <th class="table-checkbox" width="5%">
                                        <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                            <input type="checkbox" class="group-checkable" data-set="#sample_2 .checkboxes" />
                                            <span></span>
                                        </label>
                                    </th>
                                    <th width="75%"> 文件名 </th>
                                    <th width="10%"> 操作</th>
                                    <th width="10%"> 时间 </th>
                                </tr>
                                </thead>


                                <tbody id="div_demo">

                                </tbody>
                            </table>

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
<!--MODAL UPDATE-->
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">上传文件</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="#" id="form_sample_1"  enctype="multipart/form-data"  class="upload-form form-horizontal">
                                    <div class="form-body">
                                        <input type="hidden"  name="currentDir" value="${dir}">
                                        <div class="form-group">
                                            <label class="control-label col-md-3">选择文件
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-9">
                                                <input type="file" name="file" data-required="1" class="form-control" /> </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn green">提交</button>
                                                <button id="upload-close" type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
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
<!--MODAL FILENAME-->
<div class="modal fade" id="basicFile" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">创建文件夹</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="#" id="form_sample_4" class="creatDir-form  form-horizontal">
                                    <div class="form-body">
                                        <input type="hidden"  name="currentDir" value="${dir}">
                                        <div class="alert alert-success display-hide">
                                            <button class="close" data-close="alert"></button> 表单成功提交！ </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-9">
                                                <input type="text" name="name" data-required="1" class="form-control" /> </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit"   class="btn green">提交</button>
                                                <button type="button" class="btn grey-salsa btn-outline" data-dismiss="modal">关闭</button>
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
<script>window.nav='3'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>





<script id="demo" type="text/x-jquery-tmpl">

             <tr class="odd gradeX">
                                        <td>
                                            <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                <input type="checkbox" isdir="<sdw:tmpl name ="isdir"/>" name="checkbox" class="checkboxes" value="1" />
                                                <span></span>
                                            </label>
                                        </td>
                                        <td>
                                        <div class="user-defind">
                                            <div class="fileType">
                                                <i class="
                                                  {<sdwIf:if name ="if isdir"/>}
                                                    icon-folder
                                                  {<sdwIf:if name ="else"/>}
                                                    icon-doc
                                                  {<sdwIf:if name ="/if"/>}
                                                "></i>
                                            </div>
                                            <div class="fileContent">
                                                <div class="fileTitle">
                                                    <a href="javascript:;"><sdw:tmpl name ="fileName"/></a>
                                                </div>
                                                    <div name="fileSize" class="fileSize"><sdw:tmpl name ="size"/></div>
                                            </div>

                                            </div>
                                        </td>
                                        <td>
                                            <div class="user-download">

											{<sdwIf:if name ="if isdir"/>}

                                                  {<sdwIf:if name ="else"/>}
                                                    <a href="javascript:;" >下载</a>
                                                  {<sdwIf:if name ="/if"/>}

                                            <input type="hidden" id="hdfsUrl" value="<sdw:tmpl name ="hdfsUrl"/>" />
                                            </div>
                                        </td>
                                        <td>
                                            <span class="label label-sm label-success"><sdw:tmpl name="modificationTime"/> </span>
                                        </td>
                                    </tr>

</script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script type="application/javascript" src="${basePath}/content/js/uploadData/resultData.js"></script>
<!-- END THEME LAYOUT SCRIPTS -->
<script type="application/javascript">
    var websocket = null;
    if (window['WebSocket'])
    // ws://host:port/project/websocketpath
        websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket.do');
    else
        websocket = new new SockJS(PATH + '/websocket/socketjs');

    websocket.onopen = function(event) {
        console.log('open', event);
    };
    websocket.onmessage = function(event) {

        var result = JSON.parse(event.data);
        console.log('message::::', event.data);
        var tipTimes = result.tipTimes;
        if(tipTimes != undefined){
            $("#waring").html(tipTimes)
            toastr.warning("文件已经上传到hdfs");
            $.get(basePath+'/drag/upload/addTipTimes.do',{'tips':tipTimes});
            reload();
        }
    };
</script>

</body>

</html>
