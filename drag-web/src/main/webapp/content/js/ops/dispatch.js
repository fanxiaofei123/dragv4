/**
 * Created by luojun on 2017/8/14.
 */
$(function () {
    getDefaultDate();
    //列表显示
    getDispatchJobList(1)
    //搜索任务名称
    searchJobName();
    //添加调度
    searchWorkFlowList();
    //查看调度
    lookScheduler();
    //删除调度
    deleteSchedulerJob();
    //添加提醒
    addRemindSetting();
    $("#hiddenTime").val(new Date().Format("yyyy-MM-dd hh:mm:ss"))

})
//判断调度的输入框字符不能为空和不合法
function taskNameNotNull() {
        var taskName=$("#taskName").val();
        if(taskName=="") {
            $("#taskName + .linkNameError").remove();
            $("#taskName").after("<p class='linkNameError font-orange linkError'>* 不能为空哦</p>")
        }else {
            $("#taskName + .linkNameError").remove();
        }
}
function schJobIntervalNotNull() {
      var schJobInterval=$("#schJobInterval").val();
      var reg_zs = /^[1-9]\d*(\.\d+)?$/i;
      if(!reg_zs.test(schJobInterval)){
          $(".schJobInterval1 + .linkNameError").remove();
          $(".schJobInterval1").after("<p class='linkNameError font-orange linkError'>* 填写的时间不合法哦</p>")
      }else {
          $(".schJobInterval1 + .linkNameError").remove();
      }

}
//做调度的开始时间验证必须为现在的时间及其以后
function startTimeNotNull() {
    var start=$("#start").val();
    if(start==""){
        $("#start + .linkNameError").remove();
        $("#start").after("<p class='linkNameError font-orange linkError'>* 不能为空哦</p>")
    }else {
        $("#start + .linkNameError").remove();
        var startTime=(new Date(start.replace(/\-/g, "\/")));
        var nowTime=new Date();//现在
        if(startTime<=nowTime){
            $("#start").after("<p class='linkNameError font-orange linkError'>* 开始时间必须在当前时间之后</p>")
        }else {
            $("#start + .linkNameError").remove();
        }


    }
}
function endTimeNotNull() {
    var end=$("#end").val()
    if(end==""){
        $("#end + .linkNameError").remove();
        $("#end").after("<p class='linkNameError font-orange linkError'>* 不能为空哦</p>")
    }else {
        $("#end + .linkNameError").remove();
        var start=$("#start").val();
        var startTime=(new Date(start.replace(/\-/g, "\/")));
        var endTime=(new Date(end.replace(/\-/g, "\/")));
        if(endTime>startTime){
            $("#end + .linkNameError").remove();
        }else {
            $("#end").after("<p class='linkNameError font-orange linkError'>* 停止时间必须在开始时间之后</p>")
        }

    }
}
//页面上按时间筛选展示的默认时间段
function getDefaultDate(){
    $.ajax({
        type : "post",
        url : basePath+"/drag/scheduler/getDefaultDate.do",
        async : false,
        success : function(data){
            var obj=JSON.parse(data);
            var str=obj.msg;
            dateTime=str.replace('/', ' / ');
            $('#chooseDate').val(dateTime);
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
/**
 * 搜索调度任务分页显示
 */
function searchJobName() {

    //设置调度的默认开始时间

     $("#start").val(new Date().Format("yyyy-MM-dd hh:mm:ss"))

    $(".searchName").on("click",function () {
        var strs=$('#chooseDate').val().split('至');
        var startTime=$.trim(strs[0]);
        var endTime=$.trim(strs[1]);
        var schJobName=$.trim($("#searchJob").val());

        getDispatchJobList(1,startTime,endTime,schJobName);

    });
    //回车键搜索
    $("#searchJob").keydown(function(event){
        event=document.all?window.event:event;
        var strs=$('#chooseDate').val().split('至');
        var startTime=$.trim(strs[0]);
        var endTime=$.trim(strs[1]);
        var schJobName=$.trim($("#searchJob").val());
        if((event.keyCode || event.which)==13){
            getDispatchJobList(1,startTime,endTime,schJobName);
        }
    });

}
//时间日期的确认时间的触发
laydate.render({
    elem: '#chooseDate',
    type: 'date',
    range: '至',
    // value: dateTime,
    done:function(value){
        var schJobName=$.trim($("#searchJob").val());
        var strs=value.split('至');
        setTimeout(function () {
            if(strs[0]!=""||strs[1]!=""){
                getDispatchJobList(1,strs[0],strs[1],schJobName);
            }
        },10)
    }
});
var firstDate=$("#start").val();
var hiddenTime=$("#hiddenTime").val();
laydate.render({
    elem: '#start',
    min:hiddenTime,
    type: 'datetime',
    done:function(value){
        firstDate=value;
        $("#start + .linkNameError").remove();
        var startTime=(new Date(firstDate.replace(/\-/g, "\/")));
        var nowTime=new Date();//现在
        if(startTime<=nowTime){
            $("#start").after("<p class='linkNameError font-orange linkError'>* 开始时间必须在当前时间之后 </p>")
        }else {
            $("#start + .linkNameError").remove();
        }
    }

});

laydate.render({
    elem: '#end',
    min: firstDate,
    type: 'datetime',
    done:function(value){
        $("#end + .linkNameError").remove();
        var start=$("#start").val();
        var startTime=(new Date(start.replace(/\-/g, "\/")));
        var endTime=(new Date(value.replace(/\-/g, "\/")));
        if(endTime>startTime){
            $("#end + .linkNameError").remove();
        }else {
            $("#end").after("<p class='linkNameError font-orange linkError'>* 停止时间必须在开始时间之后</p>")
        }
    }
});

/**
 * 获取调度列表
 * @param page
 * @param startTime
 * @param endTime
 * @param schJobName
 */
function getDispatchJobList(page,startTime,endTime,schJobName) {
    if(startTime==""){
        startTime=null;
    }
    $.ajax({
        type:'post',
        url:basePath +"/drag/scheduler/selectSchedulerJobList.do",
        dataType: "json",
        //"schJobName":schJobName,"startTime":startTime,"endTime":endTime
        data:{"page":page,"schJobName":schJobName,"startTime":startTime,"endTime":endTime},
        success:function (json) {
            $("#schedulerJobBody").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            $("#totalpage").val(totalPage);
            var rightPage = "";
            var list = json.rows;
            if(list==""){
                $("#totalpage").val(1);
                rightPage= " <tr class='odd gradeX'>" +
                    "<td class=\"table-checkbox\"></td>"+
                    "<td>- -</td>"+
                    "<td>- -</td>"+
                    "<td>- -</td>"+
                    "<td>- -</td>"+
                    "<td>- -</td>"+
                    "<td>- -</td>"+
                    "</tr>"
            }else {
                $.each(list, function (index, array) { //遍历json数据列
                    var schJobEnable='启用';
                    if(array['schJobEnable']==0){
                        schJobEnable="停止";
                    }else {
                        schJobEnable="启用";
                    }
                    var sTime=""
                    if(array['startTime']==null){
                        sTime="";
                    }else {
                        sTime=array['startTime'];
                    }
                    var eTime=""
                    if(array['endTime']==null){
                        eTime=""
                        rightPage +="<tr>"+
                            "<td class=\"table-checkbox\"> <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
                            "<input type='checkbox' class='group-checkable' name=\"subBox\" schJobId="+array['schJobId']+" data-set='#sample_2 .checkboxes' />"+
                            "<span style=\"width: 20px\"></span>"+
                            "</label>"+
                            "</td>"+
                            "<td  class='k_table_td'>"+subStringLength(array['schJobName'])+"</td>"+
                            "<td>"+schJobEnable+"</td>"+
                            "<td>"+
                            "<i class=\"icon iconfont icon-chakan searchDispatch\" title=\"查看调度任务\" value="+array['schJobId']+"></i>"+
                            "<i class=\"icon iconfont icon-bianji1  editDispatch\" title=\"编辑调度任务\" value="+array['schJobId']+"></i>"+
                            "<i class=\"icon iconfont icon-cuowuguanbi deleteDispatch\" title=\"删除调度任务\" value="+array['schJobId']+"></i></td>"+
                            "<td  class='k_table_td'>"+subStringLength(array['workFlowName'])+"</td>"+
                            "<td>"+array['jobModify']+"</td>"+
                            "<td>"+sTime+"</td>"+
                            "</tr>"
                    }else {
                        eTime= array['endTime'];
                        rightPage +="<tr>"+
                            "<td class=\"table-checkbox\"> <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"+
                            "<input type='checkbox' class='group-checkable' name=\"subBox\" schJobId="+array['schJobId']+" data-set='#sample_2 .checkboxes' />"+
                            "<span style=\"width: 20px\"></span>"+
                            "</label>"+
                            "</td>"+
                            "<td class='k_table_td'>"+subStringLength(array['schJobName'])+"</td>"+
                            "<td>"+schJobEnable+"</td>"+
                            "<td>"+
                            "<i class=\"icon iconfont icon-chakan searchDispatch\" value="+array['schJobId']+"></i>"+
                            "<i class=\"icon iconfont icon-bianji1  editDispatch\" value="+array['schJobId']+"></i>"+
                            "<i class=\"icon iconfont icon-cuowuguanbi deleteDispatch\" value="+array['schJobId']+"></i></td>"+
                            "<td class='k_table_td'>"+subStringLength(array['workFlowName'])+"</td>"+
                            "<td>"+array['jobModify']+"</td>"+
                            "<td>"+sTime+" 至 "+eTime+"</td>"+
                            "</tr>"
                    }

                });

            }
            $("#schedulerJobBody").html(rightPage);
            $("#searchJob").val(schJobName);

        },
        complete: function () { //生成分页条
            paginatorByName();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })
}
//判断如果字符串的长度大于20后面的就用省略号代替
function subStringLength(str) {
     var s=str;
    if(str.length>20){
     s=str.substring(0,20)+"...";
    }
 return s;
}
//分页显示
function paginatorByName() {
    var fileTotal=$("#totalpage").val();
    $('.pagination-panel-total').text(fileTotal);
    $('.pagination').jqPaginator({
        totalPages: parseInt(fileTotal),
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (page) {
            $('#new_num').html(page);
            if(page!=curPage){
                getDispatchJobList(page);
            }


        }
    });
}

function lookScheduler() {
    var addOrEdit;//如果是添加调度 的保存就为true，如果是编辑调度的保存就为false，来判断不同的保存的区别
    //添加任务

    $(".addTaskDis").on('click',function (event) {
        //showAddTaskText();
        addOrEdit=true;
        $('.modal-backdrop ').show();
        $('#addTaskFrom').show();
        event.stopPropagation();
        //显示工作流弹框
        $('#addTaskFrom').modal('show')
        getAjaxWorkFlowNameList("")


    });
    $(".addDispatchFm").on('click',function (event) {
        $('#addTaskFrom').hide();
        $('.modal-backdrop ').hide();
        event.stopPropagation();

        //添加调度弹框
        $('#addDispatchFrom').modal('show')
    });

    //查看工作流的调度情况
    $("#schedulerJobBody").on('click','.searchDispatch',function (event) {
        event.stopPropagation();
        var schJobId=$(this).attr("value");

        $('#addDispatchFrom').modal('show');
        $("#saveDiv1").hide();
        $("#saveDiv").css("margin-bottom","30px");
        getLookAjaxSchedulerJob(schJobId);
        $('.updateFlow').css("pointer-events","none");//DIV区域恢复可编辑
        $('.updateFlow input').addClass("noClickBack");
        $('.updateFlow textarea').addClass("noClickBack");
    });
    $("#closeScheduler").on('click',function () {
        emptyDataText();//清空数据
        $("#saveDiv1").show();
        $("#saveDiv").css("margin-bottom","0");
        $('.updateFlow').css("pointer-events","auto");//DIV区域恢复可编辑
        $('.updateFlow input').removeClass("noClickBack");
        $('.updateFlow textarea').removeClass("noClickBack");
    })
    var schJobId;
    //编辑工作流的调度情况
    $("#schedulerJobBody").on('click','.editDispatch',function (event) {
        event.stopPropagation();
        schJobId=$(this).attr("value");
        addOrEdit=false;
        $('#addDispatchFrom').modal('show');
        $("#saveDiv1").show();
        $("#saveDiv").css("margin-bottom","0");
        emptyDataText();//清空数据
        $('.updateFlow').css("pointer-events","auto");//DIV区域恢复可编辑
        $('.updateFlow input').removeClass("noClickBack");
        $('.updateFlow textarea').removeClass("noClickBack");
        getLookAjaxSchedulerJob(schJobId);
        $("#start").val(new Date().Format("yyyy-MM-dd hh:mm:ss"))

    });

    //    工作流的id值
    var schJobWfId;

    //一次执行和间隔时间的切换
    var schJobMode=0;
    //切换时间分钟0 小时 1天2
    var schJobIntervalUnit=0;

    //调度是否生效
    var schJobEnable=true;

    //是否启用时间来获取启用停止时间
    var upOrStop=false;

    $(".flowLi").on('click','li',function () {

        schJobWfId=$(this).val();

        $('.flowLi>li').css({color:'#95aab1',background:"#ffffff"});
        $(this).css({color:'#ffffff',background:"#1bbc9b"});
        if(schJobWfId!=undefined){
            $('#nextWork').removeAttr("disabled");
            $('#nextWork').css({color:'#ffffff',background:"#1bbc9b"});
        }else {
            $('#nextWork').attr("disabled","disabled")
        }
    })
    //点击下一步
    $('#nextWork').on('click',function () {
        $('.flowLi>li').css({color:'#95aab1',background:"#ffffff"});
        $('#nextWork').attr("disabled","disabled")
        $('#nextWork').css({color:'#ffffff',background:"#d5d9dc"});
        emptyDataText();//清空数据
        $("#saveDiv1").show();
        $("#saveDiv").css("margin-bottom","0");
        $('.updateFlow').css("pointer-events","auto");//DIV区域恢复可编辑
        $('.updateFlow input').removeClass("noClickBack");
        $('.updateFlow textarea').removeClass("noClickBack");

    })
    //一次执行还是周期执行
    $('.onceExecute').on("click",function () {
        $(this).attr("checked");
        var op=$(this).val();
        if(op=='options1'){
            schJobMode=0;

            $('.hideIntervalTime').hide();
        }else {
            schJobMode=1;

            $('.hideIntervalTime').show();
        }
    })

    $('.times').on('click',function() {
        schJobIntervalUnit=$(this).val();
        var timesName=$(this).text()
        $('#dLabel').html(timesName);
    })

    $('.schedulerResult').on("click",function () {
        $(this).attr("checked")
        var op=$(this).val();
        if(op=='option1'){
            schJobEnable=true;

        }else {
            schJobEnable=false;
        }

    })

    $('.upStop').on("click",function () {

        $(this).attr("checked")
        var op=$(this).val();
        if(op=='option1'){
            upOrStop=true;
            $(".stopTimeDiv").show();
        }else {
            upOrStop=false;
            $(".stopTimeDiv").hide();
        }
    })
    $(".ulLi").on("click",".times",function () {
        $("li > i").remove(".icon-dui")
        $(this).append("<i class='icon iconfont icon-dui'></i>")
    })
    //保存调度

    $("#submitScheduler").on('click',function () {
        var start=$("#start").val();
        var startTime=(new Date(start.replace(/\-/g, "\/")));
        var nowTime=new Date();//现在

        if(startTime<=nowTime){
            toastr.error("开始时间必须在当前时间之后")
        }else {
            $("#start + .linkNameError").remove();
            if(addOrEdit==true){
                saveSchedulerJobAjax(schJobMode,upOrStop,schJobWfId,schJobIntervalUnit,schJobEnable)
                addOrEdit=false;
            }else if(addOrEdit==false) {

                editSchedulerJobAjax(schJobId);
            }
        }



    })


}
//批量删除和删除单个调度任务
function deleteSchedulerJob() {
    //选择复选框变样式
    $("#schedulerJobBody").on("click","input[name='subBox']",function(){
        var check=$(this);
        var tr=$(this).parents("tr");
        if(check.prop("checked")){
            tr.addClass("active");
        }else {
            tr.removeClass("active");
        }

    });
    //批量删除

    $('.deleteTaskDis').on("click",function () {
        // hideDeleteTaskText();
        var array =new Array();
        $("input[name='subBox']:checked").each(function () {

            array.push($(this).attr("schJobId"))

        })
      if(array.length>0){

          $.ajax({
              type:'post',
              url:basePath +"/drag/scheduler/batchDeleteSchedulerJob.do",
              dataType: "json",
              data:{"array":array},
              success:function (data) {
                  if(data.code==200){
                      toastr.success("删除成功");
                  }else {
                      toastr.error("删除失败");
                  }
              },
              error:function(data) {
                  if (data.responseText == "AjaxSessionTimeout") {
                      window.location.href = basePath;
                      return;
                  }
              }
              
          })
      }else {
          toastr.error("请选择需要删除的任务");

      }

        //列表显示
        getDispatchJobList(1)
    })
    //全选，全部选
    $('#checkAll').on("click",function () {
        $('input[name="subBox"]').prop("checked",this.checked);
        var check=$('input[name="subBox"]');
        var tr=$('input[name="subBox"]').parents("tr");
        if(check.prop("checked")){
            tr.addClass("active");
        }else {
            tr.removeClass("active");
        }
    });

    $('#schedulerJobBody').on("click",'.deleteDispatch',function () {
        var schJobId=$(this).attr("value");
        $.ajax({
            type:'post',
            url:basePath +"/drag/scheduler/deleteSchedulerJob.do",
            dataType: "json",
            data:{"schJobId":schJobId},
            success:function (obj) {
                if(obj.code==200){
                    toastr.success("删除成功");
                    getDispatchJobList(1);
                }else {
                    toastr.error("删除失败");
                }
            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        })

        //列表显示
        getDispatchJobList(1)
    })
}
//显示添加调度任务的字
function showAddTaskText() {
    $(".addSpan").show()
    $(".deleteSpan").hide()
    $(".addAndDelete").css("width","154px")
}
//显示删除调度任务的字
function hideDeleteTaskText() {
    $(".addSpan").hide()
    $(".deleteSpan").show()
    $(".addAndDelete").css("width","154px")
}
/**
 * 编辑调度任务
 * @param schJobId
 */
function editSchedulerJobAjax(schJobId) {
    var schJobMode=$('.fontDisColor1 input:checked').data("check");
    var upOrStop=$('.fontDisColor2 input:checked').data("check");
    var schJobEnable=$('.fontDisColor3 input:checked').data("check");
    var schJobName=$.trim($("#taskName").val());
    var schJobStartTime=$("#start").val();
    var schJobDesc=$.trim($("#schJobDesc").val());
    var schJobInterval=null;
    var schJobIntervalUnit=null;
    if(schJobMode==1){
        schJobInterval=$.trim($("#schJobInterval").val());
        var time=$.trim($('#dLabel').html());
        if(time=="分钟"){
            schJobIntervalUnit=0;
        }else if(time=="小时"){
            schJobIntervalUnit=1;
        }else {
            schJobIntervalUnit=2;
        }

    }
    var schJobEndTime=null;
    if(upOrStop==true){
        schJobEndTime=$("#end").val();
    }
    if(schJobName!=null&&schJobName!=""){

        $.ajax({
            type:'post',
            url:basePath +"/drag/scheduler/editSchedulerJob.do",
            dataType: "json",
            data:{'schJobId':schJobId,
                'schJobName':schJobName,
                'schJobDesc':schJobDesc,
                'schJobMode':schJobMode,
                'schJobIntervalUnit':schJobIntervalUnit,
                'schJobEnable':schJobEnable,
                'endTime':schJobEndTime,
                'startTime':schJobStartTime,
                'schJobInterval':schJobInterval
            },
            success:function (obj) {
                if(obj.code==200){
                    toastr.success("编辑成功");
                    //刷新列表
                    getDispatchJobList(1);
                }else {
                    toastr.error("编辑失败");

                }

            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        })


    }else {
        toastr.error("请把信息填写完整");

    }
}
/**
 * 查看调度工作
 * @param schJobId
 */
function getLookAjaxSchedulerJob(schJobId) {
    $.ajax({
        type:'post',
        url:basePath +"/drag/scheduler/selectSchedulerJob.do",
        dataType: "json",
        data:{'schJobId':schJobId},
        success:function (data) {
            if(data.code==200){

                $("#taskName").val(data.obj.schJobName);
                $("#start").val(data.obj.startTime)
                if(data.obj.endTime==null){
                    $("#inlineRadio6").prop("checked",true);
                    $("#inlineRadio5").prop("checked",false);
                    $("#end").val("");
                    //隐藏停止时间
                    $(".stopTimeDiv").hide();
                }else {
                    $("#inlineRadio6").prop("checked",false);
                    $("#inlineRadio5").prop("checked",true);
                    $("#end").val(data.obj.endTime);
                    //显示停止时间
                    $(".stopTimeDiv").show();
                }

                $("#schJobDesc").val(data.obj.schJobDesc);

                if(data.obj.schJobMode==0){
                    $("#inlineRadio3").prop("checked",true);
                    $("#inlineRadio4").prop("checked",false);
                    $('.hideIntervalTime').hide();
                }else if(data.obj.schJobMode==1){
                    $("#inlineRadio4").prop("checked",true);
                    $("#inlineRadio3").prop("checked",false);
                    $('.hideIntervalTime').show();

                    $('#schJobInterval').val(data.obj.schJobInterval)
                    $("li > i").remove(".icon-dui")
                    $("li[value="+data.obj.schJobIntervalUnit+"]").append("<i class='icon iconfont icon-dui'></i>")
                    if(data.obj.schJobIntervalUnit==0){
                        $('#dLabel').html("分钟");
                    }else if(data.obj.schJobIntervalUnit==1){
                        $('#dLabel').html("小时");
                    }else {
                        $('#dLabel').html("天");
                    }

                }
                if(data.obj.schJobEnable==true){
                    $("#inlineRadi1").prop("checked",true)
                    $("#inlineRadi2").prop("checked",false)
                }else {
                    $("#inlineRadi2").prop("checked",true)
                    $("#inlineRadi1").prop("checked",false)
                }


            }

        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })

}
//搜索工作流
function searchWorkFlowList() {

    //搜索工作流
     $(".searchFlowName").on('click',function () {

         var workFlowName=$(".workFlowName").val();
         getAjaxWorkFlowNameList(workFlowName)

     })

}
/**
 * 获取工作流的名称
 * @param workFlowName
 */
function getAjaxWorkFlowNameList(workFlowName) {
    $.ajax({
        type:'post',
        url:basePath +"/drag/scheduler/workFlowNameList.do",
        dataType: "json",
        data:{'workFlowName':workFlowName},
        success:function (obj) {
            if(obj.code==200){
                $('.flowLi').empty();
                for(var i=0;i<obj.obj.length;i++){
                    $('.flowLi').append("<li value="+obj.obj[i].id+">"+obj.obj[i].name+"</li>")
                }
            }

        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })
}
/**
 * 保存调度工作的ajax请求，返回200保存ok
 * @param schJobMode
 * @param upOrStop
 * @param schJobWfId
 * @param schJobIntervalUnit
 * @param schJobEnable
 */
function saveSchedulerJobAjax(schJobMode,upOrStop,schJobWfId,schJobIntervalUnit,schJobEnable) {
    var schJobName=$.trim($("#taskName").val());
    var schJobStartTime=$("#start").val();
    var schJobDesc=$.trim($("#schJobDesc").val());

    var schJobInterval=null;
    if(schJobMode==1){
        schJobInterval=$("#schJobInterval").val();
    }
    var schJobEndTime=null;
    if(upOrStop==true){
        schJobEndTime=$("#end").val();
    }
    if(schJobName!=null&&schJobName!=""){

        $.ajax({
            type:'post',
            url:basePath +"/drag/scheduler/insertScheduler.do",
            dataType: "json",
            data:{'schJobWfId':schJobWfId,
                'schJobName':schJobName,
                'schJobDesc':schJobDesc,
                'schJobMode':schJobMode,
                'schJobIntervalUnit':schJobIntervalUnit,
                'schJobEnable':schJobEnable,
                'endTime':schJobEndTime,
                'startTime':schJobStartTime,
                'schJobInterval':schJobInterval
            },
            success:function (obj) {
                if(obj.code==200){
                    toastr.success("保存成功");
                    //刷新列表
                    getDispatchJobList(1);
                }else {
                    toastr.error("保存失败")

                }

            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        })


    }else {
        toastr.error("请把信息填写完整");

    }
}
//清空调度弹框的信息
function emptyDataText() {
    $("#taskName").val("");
    $("#start").val(new Date().Format("yyyy-MM-dd hh:mm:ss"))
    $("#end").val("");
    $("#schJobDesc").val("");
    $("#schJobInterval").val("0")
    $("#inlineRadio3").prop("checked",true);
    $('.hideIntervalTime').hide();
    $("#inlineRadio6").prop("checked",true);
    $("#inlineRadi1").prop("checked",true);

}
function addRemindSetting() {
    // 批量增加提醒按钮
    $("#addRemind-icon").on('click', function () {
        var html = "";
        //获取任务名称
        $.ajax({
            url: basePath + "/tipConfig/selectByJobName.do",
            data: {},
            type: 'POST',
            dataType: "json",
            success: function (data) {
                $.each(data.obj, function (index, array) {
                    html += "<li data-id=" + array.schJobId + ">" + array.schJobName + "</li>"
                });
                $("#shadowRadius").html(html);
            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        });
        addRemind()
    });


    // 任务名称选择
    $('.task_name').on('click', function () {
        $('.task .task-name-ul').addClass('disbl')
    });
    $('.task').on('click', 'ul li', function () {
        var type = true;
        var name = $(this).text();
        var dataId = $(this).data('id');

        var arr = [];
        $.each($('.task_name span'), function () {
            arr.push($(this).data("id"))
        });
        arr.join(',');

        for (var n = 0; n < arr.length; n++) {
            if (dataId == arr[n]) {
                type = false;
                break;
            } else {
                type = true;
            }
        }
        if (type == true) {
            $('.task .task_name').append('<span data-id="' + dataId + '">' + name + '<i class="glyphicon glyphicon-remove"></i></span>')
        }
        $('.task .task-name-ul').removeClass('disbl');
    });
    // 已选中任务名称删除
    function deleTaskName() {
        $('#taskName1').on('click', 'span i', function (e) {
            e.stopPropagation();
            $(this).parent().remove();
        })
    }

    deleTaskName();
}
// 添加提醒
function addRemind() {
    $("#addRemind").modal('show');
}

function addTipConfig() {
    var tip_config_name = $("#resource_databaseName").val();
    var arr = [];
    $.each($('.task_name span'), function () {
        arr.push($(this).data("id"))
    });
    arr.join(',');
    var jobIds = arr.toString();

    var tip_config_receiver = $("#resource_hostIp").val();
    var tip_config_type = $(".remindSelect input[type='radio']:checked").val();
    var tip_config_content = $("#centent").val();
    $.ajax({
        url: basePath + "/tipConfig/adds.do",
        data: {
            "tipConfigName": tip_config_name,
            "jobIds": jobIds,
            "tipConfigReceiver": tip_config_receiver,
            "tipConfigType": tip_config_type,
            "tipConfigContent": tip_config_content
        },
        type: 'POST',
        dataType: "json",
        success: function (data) {
            switch (data.code) {
                case 417:
                    $("#resource_databaseName").val('');
                    $("#resource_hostIp").val('');
                    $("#centent").val('');
                    $("#addRemind").modal('hide');
                    toastr.error(data.msg);
                    break;
                case 200:
                    toastr.success(data.msg);
                    $("#resource_databaseName").val('');
                    $("#resource_hostIp").val('');
                    $("#centent").val('');
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

    })
}

//设置时间格式
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}