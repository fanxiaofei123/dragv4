/**
 * Created by sky on 2017/9/7.
 */
$(function () {

    //判断工作流设置任务调度过没有
    ajaxGetSchJobExist();
    //添加或者编辑任务调度弹框
    setScheduler();
    lookScheduler();
})
//判断添加或者编辑调度
var schJobId;
function setScheduler() {
    $(".setScheduler").on('click',function () {
        $("#start").val(new Date().Format("yyyy-MM-dd hh:mm:ss"));
        $("#hiddenTime").val(new Date().Format("yyyy-MM-dd hh:mm:ss"))
        //  status可以新建调度 0，否则只有编辑 1
        var status=$(".setScheduler").attr("valueSchJobStatus");
        if(status==1){
            getLookAjaxSchedulerJob(schJobId);
        }

        $('#addDispatchFrom').modal('show')
    })


}
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
//先判断工作调度存不存在工作流之上建好的调度设置
function ajaxGetSchJobExist() {
    $.ajax({
        url:basePath+"/drag/scheduler/getSchJobExist.do",
        type: 'POST',
        data:{'schJobWfId':$("#flowId").val()},
        success: function (data) {

            schJobId=data;
           // schJobId可以新建调度 0，否则只有编辑 1
            if(data==""){
                $('.setScheduler').append("<i class='icon iconfont icon-huigui'></i>");
                $(".setScheduler").attr("title","添加任务调度");
                $(".setScheduler").attr("valueSchJobStatus","0");

            }else {
                $('.setScheduler').append("<i class='icon iconfont icon-shijian'></i>");
                $(".setScheduler").attr("title","编辑任务调度");
                $(".setScheduler").attr("valueSchJobStatus","1");
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
            $("#start").after("<p class='linkNameError font-orange linkError'>* 开始时间必须在当前时间之后</p>")
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
                    $("#inlineRadio5").prop("checked",true);
                    $("#inlineRadio6").prop("checked",false);
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
//编辑调度
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
        var time=$.trim($('#dLabel').text())
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

                }else {
                    toastr.error("保存失败");

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

function lookScheduler() {

      //    工作流的id值
    var schJobWfId=$("#flowId").val();

    //一次执行和间隔时间的切换
    var schJobMode=0;
    //切换时间分钟0 小时 1天2
    var schJobIntervalUnit=0;

    //调度是否生效
    var schJobEnable=true;
    //是否启用时间来获取启用停止时间
    var upOrStop=false;


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
            if(schJobId==null||schJobId==""){
                saveSchedulerJobAjax(schJobMode,upOrStop,schJobWfId,schJobIntervalUnit,schJobEnable)
            }else {
                editSchedulerJobAjax(schJobId);
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

