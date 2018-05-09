$(function () {
    $.validator.addMethod("mychecktrim",function(value,element,params) {
        var reg = /\s/;
        if(reg.exec(value)==null){
            return true //没有空格
        }
        else{
            return false; //存在空格
        }
    },"名称中不能存在空格")
    $.validator.addMethod("mychecktrim2",function(value,element,params) {
        if(value[0]==" " || value[value.length-1]==" "){
            return false //没有空格
        }
        else{
            return true; //存在空格
        }
    },"名称前后不能存在空格")
    $.validator.addMethod("mychecktrim3",function(value,element,params) {
        if(value[0]==" " || value[value.length-1]==" "){
            return false //没有空格
        }
        else{
            return true; //存在空格
        }
    },"工作流说明前后不能存在空格")
});
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
                    maxlength: "名称长度最多10个字符",
                    minlength: "名称长度最少1个字符"
                },
                file: {
                    required: "请上传文件"
                },
                name: {
                    required: "请填写工作流名称",
                    maxlength: "名称长度最多20个字符",
                    minlength: "名称长度最少1个字符"
                },
                flowExplain:{
                	 required: "请填写工作流说明",
                    maxlength: "说明长度最多150个字符"
                },
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true,
                    maxlength: 20,
                    mychecktrim2:true,
                },
                flowExplain: {
                    minlength: 1,
                    required: true,
                    mychecktrim3:true,
                    maxlength: 149

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
                var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
                nodes = zTree.getSelectedNodes();
                treeNode = nodes[0];
                if(nodes.length==0){
                    $(".selectMag").html("请先选择工作流下目录");
                }else if(treeNode.id == 0){
                    $(".selectMag").html("请先选择工作流下目录");
                }else if(treeNode.id != 0 && treeNode.id != ""){
                    $(".selectMag").html("");

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
                            switch (returndata.code){
                                case 417:toastr.error(returndata.msg) ;
                                    return false;
                                case 200:toastr.success(returndata.msg);
                                    selectFlowByName();
                                // window.location.reload();
                                // break;


                            }
                        },
                        complete: function (data) {
                                if(data == "AjaxSessionTimeout"){
                                    window.location.href=basePath;
                                    return;
                                }
                            // selectFlowByName();
                        },
                        error:function(data) {
                            if (data.responseText == "AjaxSessionTimeout") {
                                window.location.href = basePath;
                                return;
                            }
                        }

                    })
                }

            }
        });
    };
    //用于创建好工作流之后跳转到工作流
    function selectFlowByName() {
        var workSpaceName;
        $.ajax({
            url:basePath+"/drag/flow/selectByCreateTime.do",
            type: 'POST',
            data: {},
            async: false,
            success: function (data1) {
                var returndata = JSON.parse(data1);
                name = returndata.name;
                flowId = returndata.id;
                workSpaceName = returndata.workspaceName;
                window.location.href =""+basePath+"/drag/dataModel/select.do?" +
                    "name="+name+"&flowId="+flowId+"&workSpaceName="+workSpaceName+"";
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
                    maxlength: "名称长度最多10个字符",
                    minlength: "名称长度最少1个字符"
                },
                name: {
                    required: "请填写工作流名称",
                    maxlength: "名称长度最多20个字符",
                    minlength: "名称长度最少1个字符"
                },
                flowExplain: {
                    required: "请填写工作流说明",
                    maxlength: "说明长度最多150个字符",
                }
            },
            rules: {
                name: {
                    minlength: 1,
                    required: true,
                    maxlength: 20,
                    mychecktrim2:true
                },
                flowExplain: {
                    minlength: 1,
                    required: true,
                    mychecktrim3:true,
                    maxlength:149
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
                var formData = {"name":$("#workSpaceFlowName").val(),
                                "id":$("#workFlowIds").val(),
                                "workspid":$("#worksFlowPid").val(),
                                "flowExplain":$("#flowExplainName").val()
                };
                console.log(formData+"*************************")
                $.ajax({
                    url:basePath+"/drag/flow/update.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    success: function (data) {
                        var returndata = JSON.parse(data)
                        console.log(data);
                        switch (returndata.code){
                            case 417:toastr.error(returndata.msg) ;
                                break;
                            case 200:toastr.success(returndata.msg);
                                refreshNode();
                                // refreshParentNode();
                                $('#basicEdit').modal('hide');
                                if($("#titleFlowName").html() == '工作流'){
                                    getFileData(1,null,0,"工作流");
                                }else{
                                    getMenuDataFlow($("#page").val(), $("#PageWorkspid").val(), $("#titleFlowName").html())
                                }
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
//修改工作流
function updateWorkFlow(workSpaceId){
	$("#flowExplainName").val($("#"+workSpaceId+'a').val())
	$("#workSpaceFlowName").val($("#"+workSpaceId).val())
	$("#workFlowIds").val(workSpaceId);
	$("#basicEdit").modal("show");
}
//查看工作流
function showWorkFlow(workSpaceId){
	$("#showflowExplainName").val($("#"+workSpaceId+'a').val())
	$("#showworkSpaceFlowName").val($("#"+workSpaceId).val())
	$("#showworkFlowIds").val(workSpaceId);
	$("#showbasicEdit").modal("show");
}

function deleSingleWorkSpace() {
    $("#mymodal").modal("toggle");
    $.ajax({
        url:basePath+"/drag/flow/delete.do",
        type: 'POST',
        data: JSON.stringify(tempSingleWork),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            console.log(data);
            switch (data.code){
                case 417:toastr.error(data.msg) ;
                    break;
                case 200:toastr.success(data.msg);
                    refreshNode()
                    // window.location.reload();
                    $("#deleteWorkSpace").modal("hide");
                    var dataStr=sessionStorage.getItem('data')
                    if(dataStr){
                        var data=JSON.parse(dataStr);
                        data.forEach(function (item, index,array) {
                            if(parseInt(item['flowId'])==tempSingleWork['id']){
                                data.splice(index,1)
                            }
                        });
                        if(data.length==0){
                            sessionStorage.setItem('data',null);
                        }else {
                            sessionStorage.setItem('data',JSON.stringify(data))
                        }

                    }

                    getFileData(1,null,0,"工作流");
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


var tempSingleWork={};

function deleteWorkFlow(spaceId){
//查询是否存在调度。
    var delFlow=new Array();
    delFlow.push(spaceId);
    delTmpWork.ids = delFlow;
    $.ajax({
        url:basePath+"/drag/flow/findHaveSchJob.do",
        type: 'POST',
        dataType: "json",
        contentType : "application/json;charset=UTF-8",
        data: JSON.stringify(delTmpWork),
        success: function (data) {
            console.log(data);
            switch (data.code){
                case 417:$("#deletTmpMsg").html(data.msg);
                    break;
                case 200:$("#deletTmpMsg").html(data.msg);
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
    $('#deleteWorkSpace').modal("show");

    tempSingleWork = {};
    tempSingleWork.id=spaceId

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
	// getDataFlow(1);

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
// function getData(page){
//
// 	var tmp = {};
// 	tmp.page = page;
//     $.ajax({
//         url:basePath+"/drag/calculation/history.do",
//         type: 'POST',
//         dataType: "json",
//         data:{"page":page},
//         beforeSend:function(){
//             $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画
//         },
//         success:function(json){
//             $("#tbodyList").empty();//清空数据区
//             next = json.next;//下一页数
//             previous = json.previous; //上一页数
//             curPage = page; //当前页
//             totalPage = json.total; //总页数
//             var li = "";
//             var list = json.rows;
//             $.each(list,function(index,array){ //遍历json数据列
//
//                 li +="<tr class='odd gradeX'><td><span >"+array['userNmae']+"</span></td>" +
//                 		"<td><span >"+array['way']+"</span></td>" +
//                 				"<td><span >"+array['statusName']+"</span></td>" +
//                 						/*"<td><span >"+array['resason']+"</span></td>" +*/
//                 								"<td><span >"+array['createTimes']+"</span></td></tr>";
//             });
//             $("#tbodyList").append(li);
//         },
//         complete:function(){ //生成分页条
//             getPageBar();
//            // fun();
//         },
//         error:function(){
//             alert("数据加载失败");
//         }
//     });
// }
// //获取分页条
// function getPageBar(){
//
// 	 $("#sample_2_paginatese").html("<div class='pagination-panel'>"+
//              "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getData("+previous+")'  >"+
//               "<i class='fa fa-angle-left'></i></a>"+
//                "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//                		" style='text-align:center; margin: 0 5px;' disabled>"+
//                                  "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getData("+next+")' >"+
//                                       " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
// }


function selectHistory(){

	$("#portlet_tab3").show();
	 getData(1);
	 $("#portlet_tab1").hide();
	$("#sample_2_paginate").hide();
}


function selectwork(){
	// getDataFlow(1);
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

function deleWorkSpaces() {
    $("#deleteWorkSpaces").modal("toggle");
    console.log(JSON.stringify(delTmpWork));
    $.ajax({
        url:basePath+"/drag/flow/batchdel.do",
        type: 'POST',
        dataType: "json",
        contentType : "application/json;charset=UTF-8",
        data: JSON.stringify(delTmpWork),
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

var delTmpWork={};

function  batchDelInit() {
    var data = {};
    $("#batchDelFlow").on('click', function () {
        var workSpace = new Array();
        var choices = $("input[name='checkbox']:checked");
        if(choices.length == 0){
            toastr.warning("请选择要删除数据");
            return;
        }
        delTmpWork = {};
        for(var i =0 ;i<choices.length;i++){
            var $that = $(choices[i]);
            var ids =  $that.attr('ids');

            workSpace.push(ids)
        }
        delTmpWork.ids = workSpace;
        console.log(JSON.stringify(delTmpWork));
        //查询是否存在调度。
        $.ajax({
            url:basePath+"/drag/flow/findHaveSchJob.do",
            type: 'POST',
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            data: JSON.stringify(delTmpWork),
            success: function (data) {
                console.log(data);
                switch (data.code){
                    case 417:$("#deleteTmpMsg").html(data.msg);
                        break;
                    case 200:$("#deleteTmpMsg").html(data.msg);
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


        $("#deleteWorkSpaces").modal();


    });

}




// var curPage = 1; //当前页码 http://localhost:8085/drag-web/drag/calculation/exportExcelAll.do?startTime=&endTime=&idList=1271,1266
//
// var previous,next,totalPage; //总记录数，每页显示数，总页数
// //获取数据
// function getDataFlow(page){
// 	var workId = $("#workFlowSPaneId").val();
// 	var workName = $("#workFlowNames").val();
// 	var tmp = {};
// 	tmp.page = page;
//     $.ajax({
//         url:basePath+"/drag/flow/getflowlists.do",
//         type: 'POST',
//         dataType: "json",
//         data:{"page":page,"workspid":workId,"workName":workName},
//         beforeSend:function(){
//             $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画
//         },
//         success:function(json){
//             $("#flowWorkTbody").empty();//清空数据区
//             next = json.next;//下一页数
//             previous = json.previous; //上一页数
//             curPage = page; //当前页
//             totalPage = json.total; //总页数
//             var li = "";
//             var list = json.rows;
//             $.each(list,function(index,array){ //遍历json数据列
//
//             	li +=" <tr class='odd gradeX'>"+
//                                                " <td>"+
//                                                    " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
//                                                         "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='"+array['id']+"' />"+
//                                                         "<span></span>"+
//                                                     "</label>"+
//                                                 "</td>"+
//                                                 "<td>"+
//                                                     "<div class='fileType'>"+
//                                                         "<i class='fa fa-file-code-o'></i>"+
//                                                     "</div>"+
//                                                     "<div class='fileContent'>"+
//                                                        " <div class='fileTitle'>"+
//                                                             "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array['name']+"</a>"+
//                                                         "</div>"+
//                                                         "<div class='fileSize'>属于 "+array['userMial']+"</div>"+
//                                                     "</div>"+
//                                                 "</td>"+
//                                                 "<td>"+
//                                                     "<div class='actions'>"+
//                                                         "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow("+array['id']+")' title='设置工作空间'>"+
//                                                             "<i class='icon-eyeglasses'></i>"+
//                                                         " </a>"+
//                                                         "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow("+array['id']+")' title='设置工作空间'>"+
//                                                             "<i class='icon-note'></i>"+
//                                                        " </a>"+
//                                                        " <a href='javascript:;' onclick='deleteWorkFlow("+array['id']+")' title='删除工作空间'>"+
//                                                             "<i class='icon-close'></i>"+
//                                                         "</a>"+
//                                                    " </div>"+
//                                                     "<span>"+array['createTimes']+"</span>"+
//                                                     "<input type='hidden' id='"+array['id']+"'  value='"+array['name']+"'/>"+
//                                                     "<input type='hidden' id='"+array['id']+"a'  value='"+array['flowExplain']+"'/>"+
//                                                " </td>"+
//                                             "</tr>";
//
//
//             });
//             $("#flowWorkTbody").append(li);
//         },
//         complete:function(){ //生成分页条
//             getPageBarflow();
//             //fun();
//         },
//         error:function(){
//             alert("数据加载失败");
//         }
//     });
// }

// function getPageBarflow(){
//
//     $("#sample_2_paginates").html("<div style='float: left;color: #7f8fa4'>Page "+curPage+" of <span class='pagination-panel-total'>"+totalPage+"</span></div>"+
//         "<div class='pagination-panel'>"+
//               "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getDataFlow("+previous+")'  >"+
//                "<i class='fa fa-angle-left'></i></a>"+
//                 "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//                 		" style='text-align:center; margin: 0 5px;color: #fff;border: none;background: #1bbc9b' disabled>"+
//                                   "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getDataFlow("+next+")' >"+
//                                        " <i class='fa fa-angle-right'></i></a></div>");
// }




// function selectWorkFlowName(){
// 	getDataflowName(1);
// }
// //获取数据
// function getDataflowName(page){
// 	var startTime = $("#workflowNameId").val();
// 	var workId = $("#workFlowSPaneId").val();
// 	var workName = $("#workFlowNames").val();
// 	var tmp = {};
// 	tmp.page = page;
//     $.ajax({
//         url:basePath+"/drag/flow/getflowlistname.do",
//         type: 'POST',
//         dataType: "json",
//         data:{"page":page,"name":startTime,"workspid":workId,"workName":workName},
//         beforeSend:function(){
//             $("#flowWorkTbody").append("<span id='loading'>loading...</span>");//显示加载动画
//         },
//         success:function(json){
//             $("#flowWorkTbody").empty();//清空数据区
//             next = json.next;//下一页数
//             previous = json.previous; //上一页数
//             curPage = page; //当前页
//             totalPage = json.total; //总页数
//             var li = "";
//             var list = json.rows;
//             $.each(list,function(index,array){ //遍历json数据列
//
//             	li +=" <tr class='odd gradeX'>"+
//                                                " <td>"+
//                                                    " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
//                                                         "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='"+array['id']+"' />"+
//                                                         "<span></span>"+
//                                                     "</label>"+
//                                                 "</td>"+
//                                                 "<td>"+
//                                                     "<div class='fileType'>"+
//                                                         "<i class='fa fa-file-code-o'></i>"+
//                                                     "</div>"+
//                                                     "<div class='fileContent'>"+
//                                                        " <div class='fileTitle'>"+
//                                                             "<a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array['name']+"</a>"+
//                                                         "</div>"+
//                                                         "<div class='fileSize'>属于 "+array['userMial']+"</div>"+
//                                                     "</div>"+
//                                                 "</td>"+
//                                                 "<td>"+
//                                                     "<div class='actions'>"+
//                                                         "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow("+array['id']+")' title='设置工作空间'>"+
//                                                             "<i class='icon-settings'></i>"+
//                                                        " </a>"+
//                                                        " <a href='javascript:;' onclick='deleteWorkFlow("+array['id']+")' title='删除工作空间'>"+
//                                                             "<i class='icon-trash'></i>"+
//                                                         "</a>"+
//                                                    " </div>"+
//                                                     "<span class='label label-sm label-success'>"+array['createTimes']+"</span>"+
//                                                     "<input type='hidden' id='"+array['id']+"'  value='"+array['name']+"'/>"+
//                                                     "<input type='hidden' id='"+array['id']+"a'  value='"+array['flowExplain']+"'/>"+
//                                                " </td>"+
//                                             "</tr>";
//
//
//             });
//             $("#flowWorkTbody").append(li);
//         },
//         complete:function(){ //生成分页条
//         	getPageBarFlowName();
//             //fun();
//         },
//         error:function(){
//             alert("数据加载失败");
//         }
//     });
// }


//获取分页条
// function getPageBarFlowName(){
//
//     $("#sample_2_paginates").html("<div class='pagination-panel'>"+
//               "<a href='javascript:void(0)' class='btn btn-sm default prev' onclick='getDataflowName("+previous+")'  >"+
//                "<i class='fa fa-angle-left'></i></a>"+
//                 "<input type='text' class='pagination-panel-input form-control input-sm input-inline input-mini' maxlenght='5' value='"+curPage+"'" +
//                 		" style='text-align:center; margin: 0 5px;' disabled>"+
//                                   "<a href='javascript:void(0)' class='btn btn-sm default next' onclick='getDataflowName("+next+")' >"+
//                                        " <i class='fa fa-angle-right'></i></a>共<span class='pagination-panel-total'>"+totalPage+"</span>页 </div>");
// }

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
    var reg=/\.[x|X][m|M][l|L]$/;
    if(!reg.test(excelFile)){toastr.warning('文件格式不正确，请选择正确的xml文件(后缀名.xml)！');return false;}
    var nameStr=excelFile.split("\\");
    var fileNames=nameStr[nameStr.length-1].split("&&");
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
    var  nodes = zTree.getSelectedNodes();
    if(nodes.length==0){toastr.warning('请先选择一个目录后导入！');return false;}
    var children = nodes[0].children;
    for(var i=0;i<children.length;i++){
        for (var j=0;j<fileNames.length;j++){
            var dir = children[i].curDir.split("/");
            var flowName=dir[dir.length-1];
            var fileName=fileNames[j].split(".xml")[0];
            if (flowName==fileName){
                toastr.warning('该工作流已存在，请重新选择导入文件！');
                return false;
            }
        }
    }
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
                    toastr.success(returndata.msg)
                    $("#ImportFlows").modal("hide")
                    window.location.reload();
            }


        },
        complete :function (data) {
                if(data == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            processingFlag = false;
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    });


   // $("#fileUpload").submit();
 }



