
function updateWorkFlow(workSpaceId){
	$("#flowExplainName").val($("#"+workSpaceId+'a').val())
	$("#workSpaceFlowName").val($("#"+workSpaceId).val())
	$("#workFlowIds").val(workSpaceId);
	$("#basicEdit").modal("show");
}


function deleteWorkFlow(spaceId){

    if(!confirm("你确认要删除工作流吗")){
        return;
    }

    var data = {};
    data.id=spaceId
	 $.ajax({
         url:basePath+"/drag/flow/delete.do",
         type: 'POST',
         data: JSON.stringify(data),
         dataType: "json",
         contentType: "application/json;charset=UTF-8",
         success: function (data) {
             console.log(data);
             switch (data.code){
                 case 417:toastr.error(data.msg) ;
                     break;
                 case 200:toastr.success(data.msg);
                     window.location.reload();
                     break;
             }


         },
         error:function(data) {
             if (data.responseText == "AjaxSessionTimeout") {
                 window.location.href = basePath;
                 return;
             }
         }
     });
}


function updateFlowMdoel(id){
	

    var data = {};
    data.id=id
    data.type = 2;
	 $.ajax({
         url:basePath+"/drag/flow/updateType.do",
         type: 'POST',
         data: JSON.stringify(data),
         dataType: "json",
         contentType: "application/json;charset=UTF-8",
         success: function (data) {
             console.log(data);
             switch (data.code){
                 case 417:toastr.error(data.msg) ;
                     break;
                 case 200:toastr.success(data.msg);
                     window.location.reload();
                     break;
             }


         },
         error:function(data) {
             if (data.responseText == "AjaxSessionTimeout") {
                 window.location.href = basePath;
                 return;
             }
         }
     });
	
}
/*function addButton() {
    var searchFilter = $("#sample_2_filter");
    var str = '<div class="actions" style="float: right; margin-top: -3px;">'
            + '<a class="btn btn-circle btn-icon-only btn-default" style="margin-left: 15px;" href="#basic" data-toggle="modal" title="创建新的工作空间">'
            + '<i class="icon-plus"></i>'
            + '</a>'
            + '<a class="btn btn-circle btn-icon-only btn-default" style="margin-left: 5px;" href="javascript:;" title="删除选中">'
            + '<i class="icon-trash"></i>'
            + '</a></div>';
    searchFilter.before(str);
}*/

$(function () {
   // addButton();
	getModelUpdate(1);
	
	 $("#workflowNameId").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	getDataflowName(1);
		    }
		   }); 
	 
    FormValidation.init();
    batchDelInit();
    $(".caption-subject").click(function () {
        $(this).find("a").addClass("active");
        $(this).siblings("span").find("a").removeClass("active");
        var captionId = $(this).attr("id");
        if (captionId == "workSpaceName") {
            $(".workSpaceActions").show();
        }
        else {
            $(".workSpaceActions").hide();
        }
    });
});




/*var curPage = 1; //当前页码 
var previous,next,totalPage; //总记录数，每页显示数，总页数 
*///获取数据 
function getData(page){ 
	
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/calculation/history.do",
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
            	
                li +="<tr class='odd gradeX'><td><span >"+array['userNmae']+"</span></td>" +
                		"<td><span >"+array['way']+"</span></td>" +
                				"<td><span >"+array['statusName']+"</span></td>" +
                						/*"<td><span >"+array['resason']+"</span></td>" +*/
                								"<td><span >"+array['createTimes']+"</span></td></tr>";
            }); 
            $("#tbodyList").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBar();
           // fun(); 
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    }); 
} 
//获取分页条 
function getPageBar(){ 
	
	 $("#sample_2_paginatese").html("<div class='pagination-panel'>"+
             "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getData("+previous+")'  >"+
              "<i class='fa fa-angle-left'></i></a>"+
               "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
               		" style='text-align:center; margin: 0 5px;' disabled>"+
                                 "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getData("+next+")' >"+
                                      " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}


function selectHistory(){
	
	$("#portlet_tab3").show();
	 getData(1);
	 $("#portlet_tab1").hide();
	$("#sample_2_paginate").hide();
}


function selectwork(){
	getDataFlow(1);
	$("#portlet_tab3").hide();
	/*$("#sample_4_paginate").hide();*/
	$("#portlet_tab1").show();
	$("#sample_2_paginate").show();
}


function fun(){
    $("#pagecount span a").on('click',function(){ 
        var rel = $(this).attr("rel"); 
        alert(rel)
        if(rel){ 
            getData(rel); 
        } 
    });  
}

function  batchDelInit() {
    var data = {};
    $("#batchDelFlow").on('click', function () {
        var workSpace = new Array();
        var choices = $("input[name='checkbox']:checked");
        if(choices.length == 0){
            toastr.warning("请选择要删除数据");
            return;
        }
        var tmp = {};
        for(var i =0 ;i<choices.length;i++){
            var $that = $(choices[i]);
            var ids =  $that.attr('ids');
          
            workSpace.push(ids)
        }
         tmp.ids = workSpace;
        console.log(JSON.stringify(tmp));
        if(!confirm("确认删除")){
            return;
        }

        $.ajax({
            url:basePath+"/drag/flow/batchdel.do",
            type: 'POST',
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            data: JSON.stringify(tmp),
            success: function (data) {
                console.log(data);
                switch (data.code){
                case 417:toastr.error(data.msg) ;
                    break;
                case 200:toastr.success(data.msg);
                    window.location.reload();
                    break;
            }

            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        });

    });

}




var curPage = 1; //当前页码 http://localhost:8085/drag-web/drag/calculation/exportExcelAll.do?startTime=&endTime=&idList=1271,1266
                        
var previous,next,totalPage; //总记录数，每页显示数，总页数 
//获取数据 
function getModelUpdate(page){ 
	//var workId = $("#workFlowSPaneId").val();
	//var workName = $("#workFlowNames").val();
	var type = $("#Audit").val();
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/flow/getModelNameTypes.do",
        type: 'POST',
        dataType: "json",
        data:{"type":type,"page":page},
        beforeSend:function(){ 
            $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#modelTbody").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
            	li +=" <tr class='odd gradeX'>"+
                                               " <td>"+
                                               "<div class='fileType'>"+
                                               "<i class='fa fa-file-code-o'></i>"+
                                              "</div>"+
                                                    "<div class='fileContent'>"+
                                                    " <div class='fileTitle'>";
                                                    if(type == 1 || type == 2){
                                                   li += "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"'>"+array['name']+"</a>";
                                                    } else{
                                                    	li += "<span >"+array['name']+"</span>";
                                                    }
                                                   li+= "</div>"+
                                                  "</div>"+
                                                "</td>"+
                                                "<td>"+
                                                        "<div class='fileSize'>"+
                                                "<span >"+array['createTimes']+"</span>"+ 
                                                        "</div>"+
                                                "</td>"+
                                                "<td>"+
                                                "<div class='actions'>"+ array['typeName']+ " </div>"+
                                           " </td>"+
                                           "<td>"+
                                           "<div class='actions'>"+array['flowExplain']+" </div>"+
                                           "</td>"+
                                      
                "</tr>";
            	
                
            }); 
            $("#modelTbody").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBarModel();
            //fun();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    }); 
} 

function getPageBarModel(){ 
    
    $("#getPageBarModel").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getModelUpdate("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getModelUpdate("+next+")' >"+
                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}




function selectWorkName(){
    getModelUpdateName(1);
}

$("#workNameId").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        getModelUpdateName(1);
    }
});
//模糊查询
function getModelUpdateName(page){
	var inputname = $("#workNameId").val();
    var tmp = {};
    tmp.page = page;
    $.ajax({
        url:basePath+"/drag/flow/getmodelnameType.do",
        type: 'POST',
        dataType: "json",
        data:{"inputname":inputname,"page":page},
        beforeSend:function(){
            $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画
        },
        success:function(json){
            $("#modelTbody").empty();//清空数据区
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页
            totalPage = json.total; //总页数
            var li = "";
            var list = json.rows;
            $.each(list,function(index,array){ //遍历json数据列

                li +=" <tr class='odd gradeX'>"+
                    " <td>"+
                    "<div class='fileType'>"+
                    "<i class='fa fa-file-code-o'></i>"+
                    "</div>"+
                    "<div class='fileContent'>"+
                    " <div class='fileTitle'>"+
                    "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"'>"+array['name']+"</a>"+
                    "</div>"+
                    "</div>"+
                    "</td>"+
                    "<td>"+
                    "<div class='fileSize'>"+
                    "<span >"+array['createTimes']+"</span>"+
                    "</div>"+
                    "</td>"+
                    "<td>"+
                    "<div class='actions'>"+ array['typeName']+ " </div>"+
                    " </td>"+
                    "<td>"+
                    "<div class='actions'>"+array['flowExplain']+" </div>"+
                    "</td>"+

                    "</tr>";

            });
            $("#modelTbody").append(li);
        },
        complete:function(){ //生成分页条
            getPageBarModelName();
            //fun();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    });
}

//获取分页条 
function getPageBarModelName(){

    $("#getPageBarModel").html("<div class='pagination-panel'>"+
        "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getModelUpdateName("+previous+")'  >"+
        "<i class='fa fa-angle-left'></i></a>"+
        "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
        " style='text-align:center; margin: 0 5px;' disabled>"+
        "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getModelUpdateName("+next+")' >"+
        " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}

//导出工作流
function  exportFlows() {
    var data = {};
    var workSpace = new Array();
    var choices = $("input[name='checkbox']:checked");
    if(choices.length == 0){
        toastr.warning("请选择要导出的工作流数据");
        return;
    }
    var tmp = {};
    for(var i =0 ;i<choices.length;i++){
        var $that = $(choices[i]);
        var ids =  $that.attr('ids');
      
        workSpace.push(ids)
    }
    window.location.href=basePath +"/drag/flow/selectExportFlow.do?idList="+workSpace;
}

//导入工作流
function submitExcel(){
    var excelFile = $("#excelFile").val();
    if(excelFile=='') {alert("请选择需上传的文件!");return false;}
    if(excelFile.indexOf('.xls')==-1){alert("文件格式不正确，请选择正确的Excel文件(后缀名.xls)！");return false;}
    
    
    var formData = new FormData($(".upload-forms ")[0]);
    $.ajax({
        url:basePath+"/drag/flow/importFlows.do",
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        beforeSend :function () {

        },
        success: function (data) {
            var returndata = JSON.parse(data)
            console.log(data);
            switch (returndata.code){
                case 417:toastr.error(returndata.msg) ;
                    close();
                    break;
                case 200:
                 //   close();
                	 $("#ImportFlows").modal("hide")
                	getDataFlow(1);
                    break;
            }


        },
        complete :function () {
            processingFlag = false;
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
        complete:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        },
    });
    
    
   // $("#fileUpload").submit();
 }

//弹框让用户填写上传信息
function infoBox(wfid){
    var wfid=wfid;
    $("#wfid").val(wfid);
   /* $(".bodyBox").fadeIn(100);*/
    
    $("#basic").modal("show");
    
   /* setTimeout(function(){
        $('.release_info').slideDown(200);
    },200)*/

}


//确定按钮提交数据
$(".yesClass").on("click",function(){
    var data={};
    var wfid=$("#wfid").val();
    var telNum=$("#telNum").val();
    var purpose=$("#purpose").val();
    var name=$("#name").val();
    var appExplain=$("#appExplain").val();
    var apiExplain=$("#apiExplain").val();
    var testResult=$("#testResult").val();
    data.flowid=wfid;
    data.telnum=telNum;
    data.purpose=purpose;
    data.name=name;
    data.appexplain=appExplain;
    data.apiexplain=apiExplain;
    data.testresult=testResult;
    $.ajax({
        url:basePath+"/drag/flow/addUploadInfo.do",
        type: 'POST',
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            console.log(data);
            switch (data.code){
                case 417:toastr.error(data.msg) ;
                    break;
                case 200:toastr.success(data.msg);
                    updateFlowMdoel(wfid);//模型上传
                    window.location.reload();
                    break;
            }


        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    });

   /* $(".bodyBox").fadeOut(100)
    $(".release_info").hide(100)*/
})
$(".no").on("click",function(){
    $(".bodyBox").fadeOut(100)
    $(".release_info").hide(100)
})


