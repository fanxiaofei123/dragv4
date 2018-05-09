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
    <title>可视化数据处理分析平台 | 预览数据</title>
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
                <ul class="page-breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="${basePath}/drag/dispatcher/uploadData.do">数据</a>
                    </li>
                    <li>
                        <i class="fa fa-angle-right"></i>
                        ${FileName }
                    </li>
                </ul>
            </div>
            <!-- END PAGE HEADER-->
            <div class="row">
                <div class="col-md-12 dataList">
                    <div class="portlet light portlet-fit portlet-datatable">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class=" icon-layers font-green"></i>
                                <span class="caption-subject font-green sbold uppercase">${FileName }</span>

                            </div>

                        </div>


                        <div class="portlet-body">
                            <table class="table table-striped table-hover table-checkable order-column" id="sample_2">
                                    <c:forEach var="item"   items="${table}" >
                                        <tr>
                                            <c:forEach var="td"   items="${item}" >
                                                <td>
                                                        ${td}
                                                </td>

                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
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
<script>window.nav='3'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<jsp:include page="/components/uploadData/uploadJS.jsp"></jsp:include>

<script type="application/javascript"  src="${basePath}/assets/global/scripts/PerfectLoad.js">  </script>
<script type="application/javascript">


    $(function () {
        initTable2();
    });


    var initTable2 = function () {

        var table = $('#sample_2');

        var dataTable = table.dataTable({
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "processing" : "正在加载中......",
                "emptyTable": "无可展示数据",
                "info": "共有_TOTAL_条数据",
                "infoEmpty": "未发现数据",
                "infoFiltered": "",
                "lengthMenu": "展示条数 _MENU_",
                "search": "搜索：",
                "zeroRecords": "无匹配数据",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                }
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
            // So when dropdowns used the scrollable div should be removed.
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
            "searching": false,

            "bDestory" : true,
            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.
            "pagingType": "bootstrap_extended",
            "lengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "columnDefs": [{  // set default column settings
                'orderable': false,
                'targets': [1],
            }, {
                "searchable": true,
                "targets": [0],
                'targets': [2]
            }],
            "order": [
                [1, "asc"]
            ] // set first column as a default sort by asc
        });

        table.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).prop("checked", true);
                } else {
                    $(this).prop("checked", false);
                }
            });
        });


    }

</script>
<!-- END THEME LAYOUT SCRIPTS -->

</body>

</html>
