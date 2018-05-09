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
                    <span class="caption-subject font-green sbold uppercase">农产品价格预测</span></h4>
            </div>
            <!-- END PAGE HEADER-->
            <div class="portlet box blue">
                <div class="profile">
                    <h4>农产品价格预测用来预测贵阳市未来一周内，农产品的平均价格及变化趋势，供政府、企业和公众参考使用。</h4>
                    <div class="veg-btn">
                        <div class="allVeg"><span class="glyphicon glyphicon-th-large"></span><span id="aaa">蔬菜种类</span><span class="glyphicon glyphicon-triangle-top"></span></div>
                        <div class="allveget none">
                            <div class="allveg">
                                蔬菜种类
                            </div>
                            <ul class="veget">
                                <li>胡萝卜</li>
                                <li>花菜</li>
                                <li>黄瓜</li>
                                <li>冬瓜</li>
                                <li>土豆</li>
                                <li>胡萝卜</li>
                                <li>花菜</li>
                                <li>黄瓜</li>
                                <li>冬瓜</li>
                                <li>土豆</li>
                            </ul>
                        </div>
                    </div>
                    <div id="lineChart" style="height: 550px;"></div>

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
        lineCharts("lineChart");
        $('.allVeg').on('click',function (e) {
            e.stopPropagation();
            if ($('.allveget').hasClass('none')){
                $('.allveget').slideDown(200);
                $('.allveget').removeClass('none');
                $('.allVeg span:last-child').removeClass('glyphicon-triangle-top');
                $('.allVeg span:last-child').addClass('glyphicon-triangle-bottom');
            }else{
                close()
            }

        });
        function close() {
            $('.allveget').slideUp(200);
            $('.allveget').addClass('none');
            $('.allVeg span:last-child').removeClass('glyphicon-triangle-bottom');
            $('.allVeg span:last-child').addClass('glyphicon-triangle-top');
        }

        $('body').on('click',function (e) {
            e.stopPropagation();
            close()
        })
    })
</script>
<script>
function getNames(){
	$.ajax({
		async:true,
		type:'get',
		url:'${basePath}/vegetable/names.do',
		data:{},
		success:function(data,status,cb){
			if(status=='success'&&data){
				data = JSON.parse(data);
				var str = '';
				var i = 0;
				for(; i < data.length;i++){
					str += '<li>'+data[i]+'</li>';
				}
				$('.veget').html(str);
			}
		}
	});
}
function getData(name){
	$.ajax({
		async:true,
		type:'get',
		url:'${basePath}/vegetable/simulated.do',
		data:{name:name},
		success:function(data,status,cb){
			if(status=='success'&&data){
				data = JSON.parse(data);
				var prices = [], maxPrice = 0,minPrice = 13;
				if(name){
					var i = 0;
					for(; i < data.length;i++){
						var price = data[i].value.trim();
						price = price.substr(0,price.length-5);
						if(price>maxPrice){
							maxPrice = price;
						}
						if(price<minPrice){
							minPrice = price;
						}
						prices.push(price);
					}
					maxPrice = parseFloat(maxPrice)+0.5;
					minPrice = parseFloat(minPrice)-0.5;
				}else{
					var i = 0,tmpPrice = 0;
					for(; i < data.length;i++){
						var price = data[i].value.trim();
						price = price.substr(0,price.length-5);
						if(i%20!=19){
							tmpPrice += parseFloat(price);
						}else{
							prices.push((tmpPrice/20).toFixed(2));
							tmpPrice = 0;
						}
					}
					i = 0;
					for(; i < prices.length; i++){
						if(prices[i]>maxPrice){
							maxPrice = prices[i];
						}
						if(prices[i]<minPrice){
							minPrice = prices[i];
						}
					}
					maxPrice = parseFloat(maxPrice)+0.5;
					minPrice = parseFloat(minPrice)-0.5;
				}
				maxPrice = Math.ceil(maxPrice);
				minPrice = Math.floor(minPrice);
				lineCharts("lineChart",prices,maxPrice, minPrice);
				if(name){
					$('#aaa').text(name);
				}else{
					$('#aaa').text('蔬菜种类');
				}
			}
		}
	});
}
    $(function () {
    	getNames();
    	$('.veget').on('click', 'li', function(){
    		var name = $(this).text();
    		getData(name);
    	});
    	$('.allveget').on('click','.allveg',function(){
    		getData(null);
    	});
    	getData(null);
        //ringCharts("ringChart");
        //lineCharts("lineChart");
    })
</script>
</body>

</html>
