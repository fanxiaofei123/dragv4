<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>可视化数据处理分析平台 | 模型上传</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- END PAGE LEVEL PLUGINS -->
    <link rel="stylesheet" href="${basePath}/content/css/workSpace/workSpaceList.css">
    <link rel="stylesheet" href="${basePath}/content/css/release.css">
</head>
<!-- END HEAD -->
<!-- BEGIN HEADER -->
    <jsp:include page="/components/navi/head.jsp" flush="true"/>
<!-- END HEADER -->
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
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class=" icon-briefcase font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">模型上传列表</span>
                            </div>
                            
                            <div class="actions">
                               <!--  <a class="btn btn-circle btn-icon-only btn-default" href="#basic" data-toggle="modal" title="创建新的工作空间">
                                    <i class="icon-plus"></i>
                                </a>
                                <a class="btn btn-circle btn-icon-only btn-default" id="batchDelwork" href="javascript:;" title="删除选中">
                                    <i class="icon-trash"></i>
                                </a> -->
                                <div style="float:right">
                                <input type="text" name="name" id="workNameId"/>
                                <a href="javascript:;" class="btn green" onclick="selectWorkName()">搜索 <i class="fa fa-search"></i></a>
                            </div>
                            </div>
                            
                        </div>
                        <div class="portlet-body">
                            <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                <thead>
                                <tr>
                                  <!--   <th class="table-checkbox" width="5%">
                                        <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                            <input type="checkbox" class="group-checkable" data-set="#sample_2 .checkboxes" />
                                            <span></span>
                                        </label>
                                    </th> -->
                                    <th width="10%"> 工作流名称</th>
                                    <th width="10%"> 创建时间 </th>
                                    <th width="10%">状态</th>
                                    <th width="50%">说明</th>
                                    <th width="20%">操作</th>
                                </tr>
                                </thead>
                                <tbody id="modelTbody">
                                </tbody>
                            </table>
                             <div style="text-align:center" class="dataTables_paginate paging_bootstrap_extended" id="getPageBarModel">
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
<div class="bodyBox">

</div>
<!-- <div class="release_info">
    <div class="tell">&nbsp;&nbsp;填写上传信息</div>
    <div class="info">
        <input id="wfid" type="hidden">
        <p>电话号码：<input id="telNum" type="text" name="telNum" placeholder="请输入你的电话号码" size="35"/></p>
        <p>发布用途：<input id="purpose" type="text" name="purpose" placeholder="请说明你的发布用途" size="35"/></p>
        <p>姓名：<input id="name" type="text" name="name" placeholder="请输入你的姓名" size="35"></p>
        <p>应用说明：<textarea id="appExplain" rows="3"  name="appExplain" cols="20" placeholder="请简要填写应用说明"></textarea></p>
        <p>API说明：<textarea id="apiExplain" rows="3" name="apiExplain" cols="20" placeholder="请简要填写调用API说明"></textarea></p>
        <p>测试结果：<input id="testResult" type="text" name="testResult" placeholder="请填写模型制作者的测试结果" size="35"/></p>
    </div>
    <div class="yes">确认</div>
    <div class="no">取消</div>

</div> -->
<!-- END FOOTER -->
<!--MODAL NEW-->
<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">填写上传信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                
                              <form action="javascript:;" id="form_sample_update"  enctype="multipart/form-data" class="upload-form form-horizontal">
                                <div class="form-group">
                                    <label class="control-label col-md-2">csv模板
                                        <span class="required"> * </span>
                                    </label>
                                    <div class="col-md-10">
                                      <!--   <form action="#"  enctype="multipart/form-data"  class="upload-form form-horizontal"> -->
                                            <input type="hidden" value="datas/input" name="currentDir"/>
                                            <input type="file" id="csvupload" name="file"/>
                                            <!--  <button style="float: right" type="submit" class="btn green">提交</button> -->
                                       <!--  </form> -->
                                    </div>
                                </div>
                                
                                    <div class="form-group">
                                            <label class="control-label col-md-2">电话号码
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input id="telNum" type="text" name="telNum" placeholder="请输入你的电话号码" size="35" class="form-control" maxlength="11"/>
                                                <input id="wfid" type="hidden">
                                                <input id="modelUrl" type="hidden" name="modelUrl"> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">发布用途
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                            <input id="purpose" type="text" name="purpose" placeholder="请说明你的发布用途" size="35" class="form-control" maxlength="150"/>
                                              <!--   <input type="text" id="workSpaceName" name="name" data-required="1" class="form-control"/>  -->
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">真实姓名
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input id="name" type="text" name="name" placeholder="请输入你的姓名" size="35" class="form-control" maxlength="5"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">应用说明
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                               <textarea id="appExplain" rows="3"  name="appExplain" cols="64" placeholder="请简要填写应用说明" maxlength="150"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">模型类型
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10" onclick="typechange()">
                                                <select id="u18_input">
                                                    <option value="多分类模型">多分类模型</option>
                                                    <option value="回归模型">回归模型</option>
                                                    <option value="聚类模型">聚类模型</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">模型测试结果
                                                <!-- <span class="required"> * </span> -->
                                            </label>
                                            <div class="col-md-10">
                                                   <div id="modeltype">
                                                       <p><span>Accuracy：</span>
                                                       <input type="text" name="Accuracy" size="6" class="modeltype">&nbsp;&nbsp;
                                                       <span>Precision by label：</span>
                                                       <input type="text" name="Precision" size="6" class="modeltype"></p>
                                                       <p><span>Recall by label：</span>
                                                       <input type="text" name="Recall" size="6" class="modeltype">&nbsp;&nbsp;
                                                       <span>F-measure by label：</span>
                                                       <input type="text" name="F-measure" size="6" class="modeltype"></p>
                                                       <p><span>Weighted precisionz：</span>
                                                       <input type="text" name="Weightedp" size="6" class="modeltype">&nbsp;&nbsp;
                                                       <span>Weighted recall：</span>
                                                       <input type="text" name="Weightedr" size="6" class="modeltype"></p>
                                                       <p><span>Weighted F-measure：</span>
                                                       <input type="text" name="Weightedf" size="6" class="modeltype"></p>
                                                   </div>
                                            </div>
                                        </div>



                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class=" btn green">提交</button>
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
<!--MODAL EDIT-->
<div class="modal fade" id="basicEdits" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">修改工作空间</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="form_sample_4"  class="updateSpace form-horizontal">
                                    <div class="form-body">
                                        <!-- <div class="alert alert-dangers display-hide">
                                            <button class="close" data-close="alert"></button> 表单有误，请更改后提交！ </div>
                                        <div class="alert alert-successs display-hide">
                                            <button class="close" data-close="alert"></button> 表单成功提交！ </div> -->
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间名称
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <input type="text" id="workSpaceName" name="name" data-required="1" class="form-control"/> 
                                                <input type="hidden" name="id" id="workSpaceIds" />    
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2">空间描述
                                                <span class="required"> * </span>
                                            </label>
                                            <div class="col-md-10">
                                                <textarea name="createContent" class="workFlowInfo" id="createContenta" rows="5" data-required="1"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="submit" class="btn green" >提交</button>
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


<!--[if lt IE 9]>
<!-- END CONTAINER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>

<script>window.nav='8'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script type="application/javascript" src="${basePath}/content/js/modelSharing/modelUpdateList.js"></script>
<%-- <script type="application/javascript" src="${basePath}/content/js/modelSharing/modelRelease.js"></script> --%>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
</body>

</html>
