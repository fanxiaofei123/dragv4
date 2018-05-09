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
                createContent: {
                    required: "请填写工作空间描述"
                },
                name: {
                    required: "请填写工作空间名字"
                }
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true
                },
                createContent: {
                	minlength: 1,
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
                var formData = new FormData($(".createSpace ")[0]);
                $.ajax({
                    url:basePath+"/drag/work/create.do",
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
                    required: "请填工作空间名称"
                },
                createContent: {
                    required: "请填工作空间描述"
                }
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true
                },
                createContent: {
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
                var formData = new FormData($(".updateSpace ")[0]);
                $.ajax({
                    url:basePath+"/drag/work/update.do",
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


function updateWorkSpace(workSpaceId){
	$("#workSpaceName").val($("#"+workSpaceId).val());
	$("#createContenta").val($("#"+workSpaceId+"E").val());
	$("#workSpaceIds").val(workSpaceId);
	$("#basicEdits").modal("show");
	
	
	
}

// function deleteWorkSpace(spaceId){
//
//     if(!confirm("你确认要删除工作空间及目录下的工作流吗")){
//         return;
//     }
//
//     var data = {};
//     data.id=spaceId;
// 	 $.ajax({
//          url:basePath+"/drag/work/delete.do",
//          type: 'POST',
//          data: JSON.stringify(data),
//          dataType: "json",
//          contentType: "application/json;charset=UTF-8",
//          success: function (data) {
//              console.log(data);
//              switch (data.code){
//                  case 417:toastr.error(data.msg) ;
//                      break;
//                  case 200:toastr.success(data.msg);
//                      window.location.reload();
//                      break;
//              }
//
//
//          }
//      });
// }

$(function () {
	getData(1);
	  $("#workNameId").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	getDataWrokName(1);
		    }
		   }); 
	    FormValidation.init();
	    batchDelInit();	    
});





function  batchDelInit() {
    var data = {};
    $("#batchDelwork").on('click', function () {
        var workSpace = new Array();
        var choices = $("input[name='checkbox']:checked");
        if(choices.length == 0){
            toastr.warning("请选择要删除数据");
            return;
        }
        var tmp = {};
        for(var i =0 ;i<choices.length;i++){
            var $that = $(choices[i]);
            //var name = $that.closest('tr').find('a').html();
            var ids =  $that.attr('ids');
           // tmp.name = name;
          
            workSpace.push(ids)
        }
         tmp.ids = workSpace;
        console.log(JSON.stringify(tmp));
        if(!confirm("确认删除")){
            return;
        }

        $.ajax({
            url:basePath+"/drag/work/batchdel.do",
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




/*$(function(){
	getData(1);
	
   $("#workNameId").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	getDataWrokName(1);
		    }
		   }); 
});*/
var curPage = 1; //当前页码 
var previous,next,totalPage; //总记录数，每页显示数，总页数 
//获取数据 
function getData(page){ 
	
	var tmp = {};
	tmp.page = page;
    $.ajax({ 
        url:basePath+"/drag/work/getworklists.do",
        type: 'POST',
        dataType: "json",
        data:{"pageNum":page},
        beforeSend:function(){ 
            $("#workSpaceTbody").append("<span id='loading'>loading...</span>");//显示加载动画 
        }, 
        success:function(json){ 
            $("#workSpaceTbody").empty();//清空数据区 
            next = json.next;//下一页数
            previous = json.previous; //上一页数
            curPage = page; //当前页 
            totalPage = json.total; //总页数 
            var li = ""; 
            var list = json.rows; 
            $.each(list,function(index,array){ //遍历json数据列 
            	li +=" <tr class='odd gradeX'>"+
                                    "<td>"+
                                        "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
                                          "<input type='checkbox' name = 'checkbox' class='checkboxes' ids="+array['id']+" />"+
                                            "<span></span>"+
                                        "</label>"+
                                    "</td>"+
                                    "<td onclick=''>"+
                                        "<div class='fileType'>"+
                                            "<i class='icon-briefcase'></i>"+
                                        "</div>"+
                                        "<div class='fileContent'>"+
                                            "<div class='fileTitle'>"+
                                               "<a href='"+basePath+"/drag/flow/getflow.do?workspid="+array['id']+"&workName="+array['name']+"'>"+array['name']+"</a>"+
                                           "</div>"+
                                            "<div class='fileSize'>由 "+array['userMial']+"  创建</div>"+
                                        "</div>"+
                                    "</td>"+
                                    "<td>"+
                                        "<div class='actions'>"+
                                            "<a href='javascript:;' data-toggle='modal' onclick='updateWorkSpace("+array['id']+")' title='设置工作空间'>"+
                                                "<i class='icon-settings'></i>"+
                                            "</a>"+
                                            "<a href='javascript:;' onclick='deleteWorkSpace("+array['id']+")' title='删除工作空间'>"+
                                                "<i class='icon-trash'></i>"+
                                            "</a>"+
                                       "</div>"+
                                       "<span class='label label-sm label-success'> "+array['createTimes']+" </span>"+
                                    "</td>"+
                                     "<input type='hidden' id="+array['id']+"  value='"+array['name']+"'/>"+
                                     "<input type='hidden' id="+array['id']+"E  value='"+array['createContent']+"'/>"+
                                "</tr>";
                
            }); 
            $("#workSpaceTbody").append(li); 
        }, 
        complete:function(){ //生成分页条 
            getPageBar();
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

function getPageBar(){ 
    
    $("#sample_2_paginate").html("<div class='pagination-panel'>"+
              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getData("+previous+")'  >"+
               "<i class='fa fa-angle-left'></i></a>"+
                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
                		" style='text-align:center; margin: 0 5px;' disabled>"+
                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getData("+next+")' >"+
                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
}




// function selectWorkName(){
// 	getDataWrokName(1);
// }
// //获取数据
// function getDataWrokName(page){
// 	var startTime = $("#workNameId").val();
// 	var tmp = {};
// 	tmp.page = page;
//     $.ajax({
//         url:basePath+"/drag/work/getworklistname.do",
//         type: 'POST',
//         dataType: "json",
//         data:{"page":page,"name":startTime},
//         beforeSend:function(){
//             $("#workSpaceTbody").append("<span id='loading'>loading...</span>");//显示加载动画
//         },
//         success:function(json){
//             $("#workSpaceTbody").empty();//清空数据区
//             next = json.next;//下一页数
//             previous = json.previous; //上一页数
//             curPage = page; //当前页
//             totalPage = json.total; //总页数
//             var li = "";
//             var list = json.rows;
//             $.each(list,function(index,array){ //遍历json数据列
//             	li +=" <tr class='odd gradeX'>"+
//                 "<td>"+
//                     "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
//                       "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='"+array['id']+"' />"+
//                         "<span></span>"+
//                     "</label>"+
//                 "</td>"+
//                 "<td onclick=''>"+
//                     "<div class='fileType'>"+
//                         "<i class='icon-briefcase'></i>"+
//                     "</div>"+
//                     "<div class='fileContent'>"+
//                         "<div class='fileTitle'>"+
//                            "<a href='"+basePath+"/drag/flow/getflow.do?workspid="+array['id']+"&workName="+array['name']+"'>"+array['name']+"</a>"+
//                        "</div>"+
//                         "<div class='fileSize'>属于 "+array['userEmail']+"</div>"+
//                     "</div>"+
//                 "</td>"+
//                 "<td>"+
//                     "<div class='actions'>"+
//                         "<a href='javascript:;' data-toggle='modal' onclick='updateWorkSpace("+array['id']+")' title='设置工作空间'>"+
//                             "<i class='icon-settings'></i>"+
//                         "</a>"+
//                         "<a href='javascript:;' onclick='deleteWorkSpace("+array['id']+")' title='删除工作空间'>"+
//                             "<i class='icon-trash'></i>"+
//                         "</a>"+
//                    "</div>"+
//                    "<span class='label label-sm label-success'> "+array['createTimes']+" </span>"+
//                 "</td>"+
//                  "<input type='hidden' id="+array['id']+"  value='"+array['name']+"'/>"+
//                  "<input type='hidden' id="+array['id']+"E  value='"+array['createContent']+"'/>"+
//             "</tr>";
//
//             });
//             $("#workSpaceTbody").append(li);
//         },
//         complete:function(){ //生成分页条
//             getPageBarTimeName();
//             //fun();
//         },
//         error:function(){
//             alert("数据加载失败");
//         }
//     });
// }
//
//
// //获取分页条
// function getPageBarTimeName(){
//
//     $("#sample_2_paginate").html("<div class='pagination-panel'>"+
//               "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getDataWrokName("+previous+")'  >"+
//                "<i class='fa fa-angle-left'></i></a>"+
//                 "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//                 		" style='text-align:center; margin: 0 5px;' disabled>"+
//                                   "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getDataWrokName("+next+")' >"+
//                                        " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
// }


/*
 * 导出功能
 */
/*function exportExcel(){ 
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	window.location.href=basePath +"/drag/calculation/exportExcelAll.do?startTime="+startTime+"&endTime="+endTime;
} */

