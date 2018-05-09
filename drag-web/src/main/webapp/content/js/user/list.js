$(function(){
	getData(1);
	
   $("#loginnameId").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	getDataTime(1);
		    }
		   }); 
});
var curPage = 1; //当前页码 
var previous,next,totalPage; //总记录数，每页显示数，总页数 
//获取数据 
function getData(page){ 
	
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/users/selects.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page},
        beforeSend:function(){ 
            $("#userList").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#userList").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
               /* li +="<tr class='odd gradeX'><td><span >"+array['email']+"</span></td>" +
                		"<td><span >"+array['way']+"</span></td>" +
                				"<td><span >"+array['statusName']+"</span></td>" +
                						"<td><span >"+array['resason']+"</span></td>" +
                								"<td><span >"+array['createTimes']+"</span></td>"+
                								"<td><span ><a href='" + basePath + "/drag/dispatcher/logdetails.do?id="+array['id']+"'>查看详情</a>" +
                    "<a class='exportExcel'  href='" + basePath + "/drag/calculation/exportExcel.do?id="+array['id']+"'>导出</a></span></td></tr>";
                */
                
                li +="<tr class='odd gradeX'>"+
                       "<td><div class='fileType'><i class='icon-user'></i></div><div class='fileContent'>"+
                            "<div class='fileTitle'><a href='"+basePath+"/drag/users/details.do?id="+array['id']+"'>"+array['loginname']+"</a></div>"+
                            " </div></td>"+
                       "<td><span class=''>"+array['name']+"</span></td>"+
                       "<td><span class=''>"+array['userSexName']+"</span></td>"+
                       "<td><span class=''>"+array['educationName']+"</span></td>"+
                       "<td><span class=''>"+array['email']+"</span></td>"+
                       "<td><span class=''>"+array['developerName']+"</span></td>"+
                             " <td><span class=''>"+array['createdates']+"</span></td>"+
                              " </tr>";
            }); 
            $("#userList").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBar();
            //fun();
        }, 
        error:function(){ 
            alert("数据加载失败"); 
        } 
    }); 
} 

function getPageBar(){ 
   /* $("#sample_4_paginate").html("<div class='pagination-panel'>" +
    "<a href='javascript:void(0)' onclick='getData("+previous+")' " +
    " class='btn btn-sm default prev'><i class='fa fa-angle-left'></i></a>" +
    "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"' " +
    "style='text-align:center; margin: 0 5px;'><a href='javascript:void(0)' onclick='getData("+next+")' " +
    " class='btn btn-sm default next'><i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页</div>"); 
    */
    
    $("#sample_2_paginate").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getData("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getData("+next+")' >"+
                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}




function selectName(){
	getDataTime(1);
}
//获取数据 
function getDataTime(page){ 
	var startTime = $("#loginnameId").val();
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/users/selectname.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page,"loginname":startTime},
        beforeSend:function(){ 
            $("#userList").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#userList").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
            	 li +="<tr class='odd gradeX'>"+
                 "<td><div class='fileType'><i class='icon-user'></i></div><div class='fileContent'>"+
                      "<div class='fileTitle'><a href='"+basePath+"/drag/users/details.do?id="+array['id']+"'>"+array['loginname']+"</a></div>"+
                      " </div></td>"+
                 "<td><span class=''>"+array['name']+"</span></td>"+
                 "<td><span class=''>"+array['userSexName']+"</span></td>"+
                 "<td><span class=''>"+array['educationName']+"</span></td>"+
                 "<td><span class=''>"+array['email']+"</span></td>"+
                 "<td><span class=''>"+array['developerName']+"</span></td>"+
                       " <td><span class=''>"+array['createdates']+"</span></td>"+
                        " </tr>";
            }); 
            $("#userList").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBarTime();
            //fun();
        }, 
        error:function(){ 
            alert("数据加载失败"); 
        } 
    }); 
} 


//获取分页条 
function getPageBarTime(){ 
	 
    $("#sample_2_paginate").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getDataTime("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getDataTime("+next+")' >"+
                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}


/*
 * 导出功能
 */
function exportExcel(){ 
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	window.location.href=basePath +"/drag/calculation/exportExcelAll.do?startTime="+startTime+"&endTime="+endTime;
} 

