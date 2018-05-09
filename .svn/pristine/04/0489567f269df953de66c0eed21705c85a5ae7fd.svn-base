

$(function(){
	getData(1);
});
var curPage = 1; //当前页码 
var previous,next,totalPage; //总记录数，每页显示数，总页数 
//获取数据 
function getData(page){ 
	
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/calculation/historyHAll.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page},
        beforeSend:function(){ 
            $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#tbodyList").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
                li +="<tr class='odd gradeX'>" +
                        "<td><label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids="+array['id']+" /><span></span></label></td>" +
                        "<td><span >"+array['email']+"</span></td>" +
                		"<td><span >"+array['way']+"</span></td>" +
                				"<td><span >"+array['statusName']+"</span></td>" +
                						/*"<td><span >"+array['resason']+"</span></td>" +*/
                								"<td><span >"+array['createTimes']+"</span></td>"+
                								"<td><span ><a href='" + basePath + "/drag/dispatcher/logdetails.do?id="+array['id']+"'>查看详情</a>" +
                    "<a class='exportExcel'  href='" + basePath + "/drag/calculation/exportExcel.do?id="+array['id']+"'>导出</a></span></td></tr>";
            }); 
            $("#tbodyList").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBar();
            //fun();
        }, 
        error:function(data){
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            alert("数据加载失败"); 
        } 
    }); 
} 

function getPageBar(){ 
    $("#sample_4_paginate").html("<div class='pagination-panel'>" +
    "<a href='javascript:void(0)' onclick='getData("+previous+")' " +
    " class='btn btn-sm default prev'><i class='fa fa-angle-left'></i></a>" +
    "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"' " +
    "style='text-align:center; margin: 0 5px;' disabled><a href='javascript:void(0)' onclick='getData("+next+")' " +
    " class='btn btn-sm default next'><i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页</div>"); 
}

function selectTime(){
	getDataTime(1);
}

$("#selecttime").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        getDataTime(1);
    }
});
//获取数据 
function getDataTime(page){ 
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/calculation/historyHTime.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page,"startTime":startTime,"endTime":endTime},
        beforeSend:function(){ 
            $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#tbodyList").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
                li +="<tr class='odd gradeX'>" +
                "<td><label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                "<input type='checkbox' name = 'checkbox' class='checkboxes' ids="+array['id']+" /><span></span></label></td>" +
                        "<td><span >"+array['email']+"</span></td>" +
                		"<td><span >"+array['way']+"</span></td>" +
                				"<td><span >"+array['statusName']+"</span></td>" +
                						/*"<td><span >"+array['resason']+"</span></td>" +*/
                								"<td><span >"+array['createTimes']+"</span></td>"+
                								"<td><span ><a href='" + basePath + "/drag/dispatcher/logdetails.do?id="+array['id']+"'>查看详情</a>" +
                										"<a class='exportExcel'  href='" + basePath + "/drag/calculation/exportExcel.do?id="+array['id']+"'>导出</a></span></td></tr>";
            }); 
            $("#tbodyList").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBarTime();
            //fun();
        }, 
        error:function(data){
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            alert("数据加载失败"); 
        } 
    }); 
} 


//获取分页条 
function getPageBarTime(){ 
    $("#sample_4_paginate").html("<div class='pagination-panel'>" +
    "<a href='javascript:void(0)' onclick='getDataTime("+previous+")' " +
    " class='btn btn-sm default prev'><i class='fa fa-angle-left'></i></a>" +
    "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"' " +
    "style='text-align:center; margin: 0 5px;' disabled><a href='javascript:void(0)' onclick='getDataTime("+next+")' " +
    " class='btn btn-sm default next'><i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页</div>"); 
}
var initTable2 = function () {

    var table = $('#sample_2');

    var dataTable = table.dataTable({
        "ordering": false,
        // Internationalisation. For more info refer to http://datatables.net/manual/i18n
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
        "bDestory" : true,
        "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
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
            "searchable": false,
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

/*
 * 导出功能
 */
function exportExcel(){ 
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	 var workSpace = new Array();
     var choices = $("input[name='checkbox']:checked");
     var tmp = {};
     for(var i =0 ;i<choices.length;i++){
         var $that = $(choices[i]);
         //var name = $that.closest('tr').find('a').html();
         var ids =  $that.attr('ids');
        // tmp.name = name;
       
         workSpace.push(ids)
     }
     if(workSpace == ''){
    	 window.location.href=basePath +"/drag/calculation/exportExcelAll.do?startTime="+startTime+"&endTime="+endTime;
     }else{
    	 window.location.href=basePath +"/drag/calculation/exportExcelAll.do?startTime="+startTime+"&endTime="+endTime+"&idList="+workSpace;
     }
	
} 

