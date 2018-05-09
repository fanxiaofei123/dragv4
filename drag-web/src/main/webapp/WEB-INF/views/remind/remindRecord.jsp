<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/9
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
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
    <title>可视化数据处理分析平台 | 提醒记录</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/js/malihu-custom-scrollbar/jquery.mCustomScrollbar.min.css" />
    <link href="${basePath}/assets/global/plugins/laydate/theme/default/laydate.css" rel="stylesheet" type="text/css" />


    <!-- END PAGE LEVEL PLUGINS -->

    <link rel="stylesheet" href="${basePath}/content/css/dataSource/common.css">
    <link rel="stylesheet" href="${basePath}/content/css/remind/remindRecord.css">
    <link rel="stylesheet" href="${basePath}/content/css/ops/taskCount.css">


    <%--<script type="application/javascript">--%>
    <%--var nodeList=${nodeList}--%>
    <%--</script>--%>
</head>
<!-- END HEAD -->

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
        <%--<jsp:include page="flie_menu.jsp" flush="true"/>--%>
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <%--<h4 class="title">提醒记录</h4>--%>
            <div class="page-bar shadowRadius">
                <div style="float: left">
                    <span style="font-size: 18px; float: left" class="font-color">提醒记录</span><span class="line">|</span>
                    <div class="opsDiv" style="float: left;position: relative;">

                        <%--<input class="laydate-icon" id="start1" style="width:120px; margin-right:10px;border: none" />至--%>
                        <%--<input class="laydate-icon " id="end1" style="width:120px; margin-right:10px;border: none" />--%>
                        <%--<input type="button" value="确认" onclick="searchByDate()"/>--%>

                            <label for=""><i class="icon iconfont icon-renwujindu"></i></label>
                            <input id="chooseDate" class="chooseDate" placeholder="选择时间范围" type="text">
                            <%--<span style="position: absolute;right: 0;top: 2px;"><i class="icon iconfont icon-yousanjiao"></i></span>--%>
                    </div>
                </div>
                <div class="search-common">
                    <div class="search-icon"> <i class="fa fa-search" onclick="searchRecord()"></i></div>
                    <div class="search-input">
                        <input value="" placeholder="请输入任务名或提醒名..." id="inputSearchRecord"/>
                    </div>

                </div>
                <%--</ul>--%>
            </div>

            <%--筛选状态--%>
            <div class="typeBox">
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
                            <div class="option">发送成功</div>
                            <div class="option">发送失败</div>
                        </div>
                    </div>
                    <div class="add">添加<span class="addIcon"><i class="icon iconfont icon-jia font-color"></i></span></div>
                </div>

                <span class="line">|</span>

                <div class="typeAll" id="type2">
                    <div class="allTypeNum">
                        <div class="allType">
                            <span>全部提醒</span>
                            <span class="dn"><i class="icon iconfont icon-dui"></i></span>
                        </div>
                    </div>
                    <div class="choose dn">
                        <div class="selectType">
                            <span id=""></span>
                            <span class="icon iconfont icon-yousanjiao"></span>
                        </div>
                        <div class="selectChoose">
                            <div class="option">完成</div>
                            <div class="option">出错</div>
                        </div>
                    </div>
                    <div class="add">添加<span><i class="icon iconfont icon-jia font-color"></i></span></div>
                </div>
            </div>


            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <!-- BEGIN TAB PORTLET-->
                    <div class="portlet portlet-fit portlet-datatable">
                        <div class="portlet-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="portlet_tab1">
                                    <table class="table table-striped table-hover order-column dataTable no-footer" id="sample_2" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <%--<th class="table-checkbox sorting_disabled" rowspan="1" colspan="1" width="5%">--%>
                                                <%--<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">--%>
                                                    <%--<input class="group-checkable" data-set="#sample_2 .checkboxes" type="checkbox">--%>
                                                    <%--<span></span>--%>
                                                <%--</label>--%>
                                            <%--</th>--%>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="17%"> 任务名称 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="25%"> 提醒名称 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="16%"> 发送时间 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="14%"> 接收人 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="8%"> 提醒方式 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="10%"> 状态 </th>
                                            <th class="sorting_disabled" rowspan="1" colspan="1" width="10%"> 提醒类型 </th>
                                        </tr>
                                        </thead>
                                        <tbody id="flowWorkTbody">
                                        <tr class="odd gradeX">
                                            <td class="remind_name">金融数据分析</td>
                                            <td>提醒1</td>
                                            <td class="hasAction">
                                                <div class="actions">
                                                    <a href="javascript:;" class="seeData" title="查看">
                                                        <i class="icon-magnifier"></i>
                                                    </a>
                                                </div>
                                                <span>2017-07-23  15:30:56</span>
                                                <input id="286" value="flow2" type="hidden">
                                                <input id="286a" value="实打实的" type="hidden">
                                            </td>
                                            <td>770395528@qq.com</td>
                                            <td>邮箱</td>
                                            <td>发送成功</td>
                                            <td>出错</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>


                    </div>

                </div>
            </div>
            <!-- END TAB PORTLET-->

            <%--分页--%>
            <div class="col-md-12">
                <input type="hidden" id="totalpage" value=""/>
                <input type="hidden" id="curPage" value=""/>
                <div style="float: left;color: #7f8fa4;height: 38px;line-height: 35px">Page <span id="new_num">1</span> of <span class="pagination-panel-total">15</span></div>
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
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="/components/navi/footer.jsp" flush="true"/>
<!-- END FOOTER -->
<%--邮件提醒--%>
<div class="modal fade" id="EmailRemind" tabindex="-1" role="basic" aria-hidden="true">
    <div class="modal-dialog Radius">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">邮件提醒</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <!-- BEGIN VALIDATION STATES-->
                        <div class="portlet light portlet-fit portlet-form ">
                            <div class="portlet-body">
                                <!-- BEGIN FORM-->
                                <form action="javascript:;" id="" class="updateFlow form-horizontal">
                                    <div class="form-body">
                                        <h4 class="text-center" id="tipTitle">运维中心出错提醒</h4>
                                        <div class="mes-box">
                                            <div class="mes-case">
                                                <p class="title">报告来自于下列提醒</p>
                                                <div class="mes-des">
                                                    <div class="mesGroup">
                                                        <span><i class="icon-credit-card"></i>任务</span>
                                                        <span id="jobName">金融数据分析</span>
                                                    </div>
                                                    <div class="mesGroup">
                                                        <span><i class="icon-bell"></i>提醒</span>
                                                        <span id="tipName">金融分析提醒</span>
                                                    </div>
                                                    <div class="mesGroup">
                                                        <span><i class="icon-clock"></i>提醒时间</span>
                                                        <span id="tipTime">2017-07-10  20:30:54</span>
                                                    </div>
                                                    <div class="mesGroup">
                                                        <span><i class="icon-user"></i>接收人</span>
                                                        <span id="receiver">lgg@youedata.com</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="mes-case">
                                                <p class="title">报告内容</p>
                                                <div class="mes-des">
                                                    <div class="mesGroup">
                                                        <p class="font-color" id="tipContent">你的任务出错了！</p>
                                                    </div>

                                                </div>
                                            </div>

                                        </div>

                                        <%--<p id="deleteMsg" class="font-orange text-center" style="font-size: 16px;">确认删除<span id="remind-name"></span>提醒吗？</p>--%>


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
<script>window.nav='9'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<!-- END THEME LAYOUT SCRIPTS -->
<script src="${basePath}/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script src="${basePath}/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${basePath}/assets/global/plugins/laydate/laydate.js"></script>
<script src="${basePath}/content/js/remind/remindRecord.js" type="text/javascript"></script>
<script src="${basePath}/js/taskCount.js" type="text/javascript"></script>

</body>
<script>
    $(document).ready(function(){
        getRecordList(1,"");
    })
</script>
</html>
