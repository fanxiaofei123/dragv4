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
    <title>可视化数据处理分析平台 | 导航</title>
    <jsp:include page="/components/baseCSS.jsp"></jsp:include>
    <!-- BEGIN PAGE LEVEL PLUGINS -->
    <link href="${basePath}/assets/pages/css/profile-2.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${basePath}/content/css/navigation.css">
    <link rel="stylesheet" href="${basePath}/content/css/score.css">
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
            <div class="caption">
                <h4> <i class="icon-docs font-green"></i>
                    <span class="caption-subject font-green sbold uppercase">专利价值评分</span></h4>
            </div>
            <!-- END PAGE HEADER-->
            <div class="portlet box blue">
                <div class="profile">
                    <h3>一种西瓜种子的浸种催芽方法</h3>
                    <%--<div class="timeAndread">--%>
                    <%--<p>--%>
                    <%--<span class="glyphicon glyphicon-time"></span>--%>
                    <%--<span class="">2017-03-20</span>--%>
                    <%--</p>--%>
                    <%--<p>--%>
                    <%--<span class="glyphicon glyphicon-eye-open"></span>--%>
                    <%--<span class="">阅读（11）</span>--%>
                    <%--</p>--%>
                    <%--</div>--%>
                    <div id="ringChart" style="height:320px;"></div>
                    <div class="patentTable">
                        <table>
                            <tbody><tr>
                                <td>申请人</td>
                                <td class="ng-binding">张某</td>
                                <td>发明人</td>
                                <td class="ng-binding">李某</td>
                            </tr>
                            <tr>
                                <td>第一发明人</td>
                                <td class="ng-binding">刘某</td>
                                <td>申请日</td>
                                <td class="ng-binding">20160804</td>
                            </tr>
                            <tr>
                                <td>公开日</td>
                                <td class="ng-binding">20170104</td>
                                <td>IPC分类</td>
                                <td class="ng-binding">A01C    1/00    (2006.01)</td>
                            </tr>
                            <tr>
                                <td>专利号</td>
                                <td class="ng-binding">201610634812.0</td>
                                <td>专利分类型</td>
                                <td class="ng-binding">技术专利</td>
                            </tr>
                            <tr>
                                <td>专利价格</td>
                                <td class="ng-binding">123123</td>
                                <td>联系方式</td>
                                <td class="ng-binding">安徽省合肥市巢湖市巢湖路219号</td>
                            </tr>
                            </tbody></table>
                    </div>
                    <div class="patentAbstract" style="width: 760px;margin: 10px auto">
                        <div class="patentAbstractHead">
                            <p>专利详情</p>
                        </div>
                        <div class="abstract" style="line-height: 2">
                            <p>本发明所要解决的技术问题在于提供一种操作简单、用时短且发芽率高的西瓜种子的浸种催芽方法。</p>
                            <p>本发明所要解决的技术问题采用以下的技术方案来实现：</p>
                            <p>一种西瓜种子的浸种催芽方法，包括如下步骤：</p>
                            <p>(1)浸种：先将西瓜种子加入25-30℃水中浸泡1h，再于超声频率40kHz、水温50-55℃下超声处理1h，然后加入15-20℃水中浸泡，15min后继续于超声频率40kHz、水温50-55℃下超声处理1h，最后用25-30℃水水洗种子两次，除去漂浮杂质；</p>
                            <p>(2)催芽：将浸种好的西瓜种子用毛巾包裹好制成催芽种子包，再将种子包置于小孔筛网上，并用25-30℃热风从筛网下方垂直吹过种子包，2h后将种子包置于微波处理器中，种子包底部均匀铺一层5cm厚的垫料，随后将种子包于微波频率2450MHz、功率700W下微波处理10s，最后再将种子包置于小孔筛网上，继续用25-30℃热风从筛网下方垂直吹过种子包，待85％种子露白后即可播种。</p>
                            <p>所述垫料由如下重量份数的原料混合制成：松针土15-20份、秸秆干粉10-15份、麦麸10-15份、甘蔗渣10-15份、干豆渣7-11份、凹凸棒土5-9份、木屑粉4-7份、砻糠灰4-7份、陶瓷微粉3-6份、茶枯粉3-6份、炉甘石粉2-4份、石棉绒1-2份；所述松针土和甘蔗渣的含水量低于5％。</p>
                            <p>本发明的有益效果是：本发明在浸种时利用超声协助方法缩短浸种时间，同时通过超声处理能够将种子表面的粘质物除去，以减少额外的人工投入；并在催芽时结合热风和微波两种方式催芽，缩短催芽时间，同时利用筛网和垫料的设置，避免催芽时种子因局部温度骤增而失活，保证种子催芽时的受热均匀度，从而充分提高种子发芽率。</p>

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
<script>window.nav='11'</script>
<jsp:include page="/components/baseJS.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/echarts/echarts.js" type="text/javascript"></script>
<script src="${basePath}/assets/pages/scripts/charts-echarts.js" type="text/javascript"></script>
<script src="${basePath}/content/js/score/score.js" type="text/javascript"></script>
<script>
    $(function () {
        ringCharts("ringChart");
//        lineCharts("lineChart");
    })
</script>
</body>

</html>
