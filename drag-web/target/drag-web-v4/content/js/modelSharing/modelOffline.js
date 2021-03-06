var FormValidation = function () {

    var handleValidation1 = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form1 = $('#form_sample_1');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            messages: {
                select_multi: {
                    maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                    minlength: jQuery.validator.format("At least {0} items must be selected")
                },
                file: {
                    required: "请上传文件"
                },
                name: {
                    required: "请填写工作流名字"
                },
                flowExplain:{
                	 required: "请填写工作流说明"
                },
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true
                },
                flowExplain: {
                    minlength: 1,
                    required: true
                },
                file: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                url: {
                    required: true,
                    url: true
                },
                number: {
                    required: true,
                    number: true
                },
                digits: {
                    required: true,
                    digits: true
                },
                creditcard: {
                    required: true,
                    creditcard: true
                },
                occupation: {
                    minlength: 5,
                },
                select: {
                    required: true
                },
                select_multi: {
                    required: true,
                    minlength: 1,
                    maxlength: 3
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

            submitHandler: function (form) {
                var formData = new FormData($(".createFlow ")[0]);
                $.ajax({
                    url:basePath+"/drag/flow/create.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        console.log(data);
                        switch (returndata.code){
                            case 417:toastr.error(returndata.msg) ;
                                break;
                            case 200:toastr.success(returndata.msg);
                                window.location.reload();
                                break;
                        }


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
            }
        });


    }
    var handleValidation2 = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form4 = $('#form_sample_4');
        var error1 = $('.alert-danger', form4);
        var success1 = $('.alert-success', form4);

        form4.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            messages: {
                select_multi: {
                    maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                    minlength: jQuery.validator.format("At least {0} items must be selected")
                },
                name: {
                    required: "请填工作流名称"
                },
                text: {
                    required: "请填写名称"
                }
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true
                },
                text: {
                    minlength: 1,
                    required: true
                },
                file: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                url: {
                    required: true,
                    url: true
                },
                number: {
                    required: true,
                    number: true
                },
                digits: {
                    required: true,
                    digits: true
                },
                creditcard: {
                    required: true,
                    creditcard: true
                },
                occupation: {
                    minlength: 5,
                },
                select: {
                    required: true
                },
                select_multi: {
                    required: true,
                    minlength: 1,
                    maxlength: 3
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

           /* submitHandler: function (form) {
                success1.show();
                error1.hide();
            }*/
            
            submitHandler: function (form) {
                var formData = new FormData($(".updateFlow ")[0]);
                $.ajax({
                    url:basePath+"/drag/flow/update.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        console.log(data);
                        switch (returndata.code){
                            case 417:toastr.error(returndata.msg) ;
                                break;
                            case 200:toastr.success(returndata.msg);
                                window.location.reload();
                                break;
                        }


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
            }
        });


    }

  


    return {
        //main function to initiate the module
        init: function () {

            handleValidation1();
            handleValidation2();

        }

    };

}();

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
	getModelOffline(1);
	
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
function getModelOffline(page){ 
	//var workId = $("#workFlowSPaneId").val();
	//var workName = $("#workFlowNames").val();
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/flow/selectOffline.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page},
        beforeSend:function(){ 
            $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#ModelOffline").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	
            	li +=" <tr class='odd gradeX'>"+
                                               " <td>"+
                                               "<div class='actions'>"+ array['username']+ " </div>"+
                                                "</td>"+
                                                "<td>"+
                                                "<div class='actions'>"+ array['spacename']+ " </div>"+
                                                "</td>"+
                                                "<td>"+
                                                "<div class='actions'>"+ array['name']+ " </div>"+
                                                " </td>"+
                                                 "<td>"+
                                                 "<div class='actions'>"+array['typeName']+" </div>"+
                                                  "</td>"+
                                                "<td>"+
                                                "<div class='actions'>"+ array['flowExplain']+ " </div>"+
                                              " </td>"+
                                              "<td>"+
                                              "<div class='actions'>"+ array['createTimes']+ " </div>"+
                                            " </td>"+
                                            "<td>"+
                                            "<div class='actions'>" ;
                                            if(array['type'] == 4){
                                            	li += "<a class='' href='javascript:;' onclick='modelOfflines("+array['id']+","+array['flowId']+")' >下线 </a>";
                                        	}
                                           li += "</div>"+
                                          " </td>"+
                                               "</tr>";
                
            }); 
            $("#ModelOffline").append(li); 
        }, 
        complete:function(){ //生成分页条 
        	getPageBarModelOffline();
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

function getPageBarModelOffline(){ 
    
    $("#getPageBarModelOffline").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getModelOffline("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getModelOffline("+next+")' >"+
                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}



function selectWorkName(){
    getModelOfflineName(1);
}

$("#workNameId").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        getModelOfflineName(1);
    }
});

//模糊查询
function getModelOfflineName(page){
    //var workId = $("#workFlowSPaneId").val();
    //var workName = $("#workFlowNames").val();
    var inputname=$("#workNameId").val();
    var tmp = {};
    tmp.page = page;
    $.ajax({
        url:basePath+"/drag/flow/selectOfflineName.do",
        type: 'POST',
        dataType: "json",
        data:{"inputname":inputname,"page":page},
        beforeSend:function(){
            $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画
        },
        success:function(json){
            $("#ModelOffline").empty();//清空数据区
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页
            totalPage = json.total; //总页数
            var li = "";
            var list = json.rows;
            $.each(list,function(index,array){ //遍历json数据列

                li +=" <tr class='odd gradeX'>"+
                    " <td>"+
                    "<div class='actions'>"+ array['username']+ " </div>"+
                    "</td>"+
                    "<td>"+
                    "<div class='actions'>"+ array['spacename']+ " </div>"+
                    "</td>"+
                    "<td>"+
                    "<div class='actions'>"+ array['name']+ " </div>"+
                    " </td>"+
                    "<td>"+
                    "<div class='actions'>"+array['typeName']+" </div>"+
                    "</td>"+
                    "<td>"+
                    "<div class='actions'>"+ array['flowExplain']+ " </div>"+
                    " </td>"+
                    "<td>"+
                    "<div class='actions'>"+ array['createTimes']+ " </div>"+
                    " </td>"+
                    "<td>"+
                    "<div class='actions'>" ;
                if(array['type'] == 4){
                    li += "<a class='' href='javascript:;' onclick='modelOfflines("+array['id']+","+array['flowId']+")' >下线 </a>";
                }
                li += "</div>"+
                    " </td>"+
                    "</tr>";

            });
            $("#ModelOffline").append(li);
        },
        complete:function(){ //生成分页条
            getPageBarModelOffline();
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


function getPageBarModelOffline(){

    $("#getPageBarModelOffline").html("<div class='pagination-panel'>"+
        "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getModelOfflineName("+previous+")'  >"+
        "<i class='fa fa-angle-left'></i></a>"+
        "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
        " style='text-align:center; margin: 0 5px;' disabled>"+
        "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getModelOfflineName("+next+")' >"+
        " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}



function modelOfflines(id,flowId){
	

    var data = {};
    data.id=id
    data.flowId = flowId;
	 $.ajax({
         url:basePath+"/drag/flow/modelTypeOffline.do",
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


function modelReleases(id){
	

    var data = {};
    data.id=id
	 $.ajax({
         url:basePath+"/drag/flow/modelReleases.do",
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



function selectWorkFlowName(){
	getDataflowName(1);
}
//获取数据 
function getDataflowName(page){ 
	var startTime = $("#workflowNameId").val();
	var workId = $("#workFlowSPaneId").val();
	var workName = $("#workFlowNames").val();
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/flow/getflowlistname.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page,"name":startTime,"workspid":workId,"workName":workName},
        beforeSend:function(){ 
            $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#flowWorkTbody").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 

            	li +=" <tr class='odd gradeX'>"+
                                               " <td>"+
                                                   " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
                                                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='"+array['id']+"' />"+
                                                        "<span></span>"+
                                                    "</label>"+
                                                "</td>"+
                                                "<td>"+
                                                    "<div class='fileType'>"+
                                                        "<i class='fa fa-file-code-o'></i>"+
                                                    "</div>"+
                                                    "<div class='fileContent'>"+
                                                       " <div class='fileTitle'>"+
                                                            "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array['name']+"</a>"+
                                                        "</div>"+
                                                        "<div class='fileSize'>由 "+array['userMial']+"  创建</div>"+
                                                    "</div>"+
                                                "</td>"+
                                                "<td>"+
                                                    "<div class='actions'>"+
                                                        "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow("+array['id']+")' title='设置工作空间'>"+
                                                            "<i class='icon-settings'></i>"+
                                                       " </a>"+
                                                       " <a href='javascript:;' onclick='deleteWorkFlow("+array['id']+")' title='删除工作空间'>"+
                                                            "<i class='icon-trash'></i>"+
                                                        "</a>"+
                                                   " </div>"+
                                                    "<span class='label label-sm label-success'>"+array['createTimes']+"</span>"+
                                                    "<input type='hidden' id='"+array['id']+"'  value='"+array['name']+"'/>"+
                                                    "<input type='hidden' id='"+array['id']+"a'  value='"+array['flowExplain']+"'/>"+
                                               " </td>"+
                                            "</tr>";
            	
                
            }); 
            $("#flowWorkTbody").append(li); 
        }, 
        complete:function(){ //生成分页条 
        	getPageBarFlowName();
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
function getPageBarFlowName(){ 
	 
    $("#sample_2_paginates").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getDataflowName("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getDataflowName("+next+")' >"+
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


