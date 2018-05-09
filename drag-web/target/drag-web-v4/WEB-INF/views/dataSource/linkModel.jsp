<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/9/1
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--数据源连接--%>
<div class="modal fade" id="dataLink" tabindex="-1" role="basic" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content" id="create_new_link">
            <div class="modal-header">
                <button type="button" class="close modal-header-close" data-dismiss="modal" aria-hidden="true" onclick="closeform()"></button>
                <h4 class="modal-title">数据库连接</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="parame_form" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">连接名：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="长度最多20个字符串" id="resource_linkname"  onblur="checkLinkName()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">类型：</label>
                                            <div class="col-sm-7">
                                                <select class="form-control" id="resource_type">
                                                    <option>mysql</option>
                                                    <option>oracle</option>
                                                    <option>hive</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">数据库名：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="" id="resource_databaseName" onblur="checkDataBaseName()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">主机名或IP地址：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="例如：192.168.1.95" id="resource_hostIp" onblur="checkHostIp()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label" id="input_port">端口：</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" placeholder="例如：3306" id="resource_port" onblur="checkPort()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">用户名：</label>
                                            <div class="col-sm-7">

                                                <input type="text" class="form-control" id="resource_username"  autocomplete="off" onblur="checkUserName()">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="" class="col-sm-4 control-label">密码：</label>
                                            <div class="col-sm-7">
                                                <input type="password" class="form-control"  id="resource_password"  autocomplete="off" onblur="checkPassword()">
                                            </div>
                                        </div>
                                        <div id="ta_exception" class="col-sm-11 test-type"></div>
                                        <div id="ta_prepare" class="col-sm-11" style="display: none">
                                            <div class="loader loader-3">
                                                <div class="dot dot1"></div>
                                                <div class="dot dot2"></div>
                                                <div class="dot dot3"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <%--<textarea id="ta_exception" rows="7" disabled="disabled"></textarea>--%>
                                    <%--<label id="la_errorinfo"></label>--%>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <button type="button" class="btn btn-default font-color border-color btn-connect1" id="connect_test" onclick="LinkDataBaseTest()">测试</button>
                                                <button type="button" class="btn btn-default font-color border-color btn-connect1" id="connect_save" onclick="saveLink()" value="addNodes">保存</button>
                                                <button type="button" class="btn btn-default font-color border-color btn-connect1" data-dismiss="modal" onclick="cancleLink()">取消</button>
                                            </div>
                                            <div class="col-md-12">
                                                <%--<p>注：支持连接My SQL5.6.16-log及以下版本</p>--%>
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
<!-- END MODAL EDIT-->