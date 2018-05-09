/**
 * Created by cdyoue on 2017/8/7.
 */
//    分页
function Paginator() {
    var total = $("#total").val();
    $('.pagination-panel-total').text(total);
    $('.pagination').jqPaginator({
        totalPages: parseInt(total),
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',

        onPageChange: function (num) {
            if(num!=curPage) {
                showTipConfig(num);
            }
        }
    });
}
window.onload = function showTipConfig1(){
    showTipConfig();
};
function showTipConfig(num) {
    showTipConfigMethod(num);
}
function showTipConfigMethod(num) {
    var tipName = $("#tipName").val();
    $.ajax({
        url:basePath+"/tipConfig/selectAllByAnyThing.do",
        type:"Post",
        dataType:"json",
        data:{"page":num,"tipName":tipName},
        success:function (json) {
            $("#flowWorkTbody").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            $("#total").val(totalPage);
            var rightPage = "";
            var list = json.rows;
            if (list==""){
                $("#total").val(1);
                rightPage="<tr class=\"odd gradeX\">" +
                    "<td></td>" +
                    "<td class=\"remind_name\">- -</td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "</tr>";
            }else{
                var  updateClose = "<a href=\"javascript:;\" data-toggle=\"modal\" onclick=\"updateEnable(this)\" title=\"关闭提醒\">" +
                    "<i class=\"icon iconfont icon-tingyong1\"></i>";
                var updateOpen = "<a href=\"javascript:;\" data-toggle=\"modal\" onclick=\"updateEnable(this)\" title=\"开启提醒\">" +
                    "<i class=\"icon iconfont icon-qiyong\"></i>";
                $.each(list, function (index, array) {
                    rightPage += "<tr class=\"odd gradeX\">" +
                        "<td>" +
                        "<label class=\"mt-checkbox mt-checkbox-single mt-checkbox-outline\">" +
                        "<input name=\"checkbox\" class=\"checkboxes\"  ids='" + array['tipConfigId'] + "' type=\"checkbox\"><span></span>" +
                        "</label>" +
                        "</td>" +
                        "<td class=\"remind_name\">" + array['schJobName'] + "</td>" +
                        "<td>" + array['tipConfigName'] + "</td>" +
                        "<td class=\"hasAction\">" +
                        "<div class=\"actions\">";
                    if(array.tipConfigEnable == 0){
                        rightPage +=  updateOpen;
                    }
                    if(array.tipConfigEnable == 1){
                        rightPage += updateClose;
                    }

                    rightPage +=
                        "</a>" +
                        "<a href=\"javascript:;\" class=\"editor\" title=\"编辑\">" +
                        "<i class=\"icon iconfont icon-bianji\"></i>" +
                        "</a>"
                        + "<input id='tipConfigId' value='" + array['tipConfigId'] + "' type='hidden'>"
                        + "<input id='tipConfigName' value='" + array['tipConfigName'] + "' type='hidden'>"
                        + "<input id='schJobId' value='" + array['schJobId'] + "' type='hidden'>"
                        + "<input id='schJobName' value='" + array['schJobName'] + "' type='hidden'>"
                        + "<input id='tipConfigReceiver' value='" + array['tipConfigReceiver'] + "' type='hidden'>"
                        + "<input id='tipConfigType' value='" + array['tipConfigType'] + "' type='hidden'>"
                        + "<input id='configType' value='" + array['configType'] + "' type='hidden'>"
                        + "<input id='tipConfigEnable' value='" + array['tipConfigEnable'] + "' type='hidden'>"
                        + "<input id='tipConfigContent' value='" + array['tipConfigContent'] + "' type='hidden'>" +
                        // +"<input id='tipContent2' value='"+list[index].tipContent+"' type='hidden'>"+
                        "<a href=\"javascript:;\" class=\"deleteRemindIcon\" title=\"删除文件\">" +
                        "<i class=\"icon iconfont icon-cuo1\"></i>" +
                        "</a>" +
                        "</div>" +
                        "<span>" + array['tipConfigCreateTimes'] + "</span>" +
                        "</td>" +
                        "<td>" + array['sendType'] + "</td>" +
                        "<td>" + array['tipConfigReceiver'] + "</td>" +
                        "<td>" + array['configType'] + "</td>" +
                        "</tr>";

                });
            }


            $("#flowWorkTbody").html(rightPage);
        },
        complete: function () { //生成分页条
            Paginator();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }

    })
}

// 删除提醒
function deleteRemind(id) {
    $("#deleteRemind").modal('show')
    if (id) {
        //单删除
        $("#realdelete").on('click', function () {
            $.ajax({
                url: basePath + "/tipConfig/deleteById.do",
                type: 'POST',
                dataType: "json",
                data: {"tipConfigIds": id},
                success: function (data) {
                    console.log(data);
                    switch (data.code) {
                        case 417:
                            toastr.error(data.msg);
                            break;
                        case 200:
                            toastr.success(data.msg);
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
        })
    } else {
        //多删除
        $("#realdelete").on('click', function () {
            var tipConfig = new Array();
            var choices = $("input[name='checkbox']:checked");
            var tmp = {};
            for (var i = 0; i < choices.length; i++) {
                var $that = $(choices[i]);
                var ids = $that.attr('ids');
                tipConfig.push(ids)
            }
            tmp.ids = tipConfig;
            var data = tmp.ids.toString();
            $.ajax({
                url: basePath + "/tipConfig/deleteById.do",
                type: 'POST',
                dataType: "json",
                data: {"tipConfigIds": data},
                success: function (data) {
                    console.log(data);
                    switch (data.code) {
                        case 417:
                            toastr.error(data.msg);
                            break;
                        case 200:
                            toastr.success(data.msg);
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
        })

    }

}

// 添加提醒
function addRemind() {
    $("#addRemind").modal('show');
}
// 修改提醒
function updateRemind() {
    $("#updateRemind").modal('show');
}
// 提醒选择提醒
function checkRemind() {
    $("#checkRemind").modal('show')
}
// 滚动条
$(window).on("load", function () {
    $("body .task .task-name-ul").mCustomScrollbar({
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
});

$(function () {
    // 批量增加提醒按钮
    //回车键搜索
    $("#tipName").keydown(function(event){
        event=document.all?window.event:event;
        if((event.keyCode || event.which)==13){
            showTipConfigMethod(1);
        }
    });
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
    // 批量删除提醒
    $("#deleteRemind-icon").on('click', function () {
        if ($('input[type="checkbox"]').is(':checked')) {
            deleteRemind()
        } else {
            checkRemind()
        }

    });
    // 删除提醒（单个）
    $('#flowWorkTbody').on('click', '.deleteRemindIcon', function () {
        var m = $(this).parents('.hasAction').siblings('.remind_name').text();
        $('#remind-name').html(m);
        var id = $(this).parents('tr').find('.checkboxes').attr('ids');

        deleteRemind(id);
    });
    // 修改提醒
    $('#flowWorkTbody').on('click', '.editor', function () {
        var obj = $(this);
        var tipConfigId = $(obj).siblings("#tipConfigId").val();
        var tipConfigName = $(obj).siblings("#tipConfigName").val();
        var schJobId = $(obj).siblings("#schJobId").val();
        var schJobName = $(obj).siblings("#schJobName").val();
        var tipConfigReceiver = $(obj).siblings("#tipConfigReceiver").val();
        var tipConfigType = $(obj).siblings("#tipConfigType").val();
        var configType = $(obj).siblings("#configType").val();
        var tipConfigContent = $(obj).siblings("#tipConfigContent").val();
        $("#tipConfigId").val(tipConfigId);
        $("#update_databaseName").val(tipConfigName);
        $("#update_databaseName").attr('required','required');
        $("#update_databaseName").attr('placeholder','名称长度不超过20个字符');
        $("#updateTaskName").text(schJobName);
        $("#update_Resource_hostIp").val(tipConfigReceiver);
        $("#update_Resource_hostIp").attr('required','required');
        $("#update_Centent").val(tipConfigContent);
        $("#update_Centent").attr('required','required');
        $("#update_schJobId").val(schJobId);
        if (tipConfigType == 0) {
            $("#update_InlineRadio1").prop("checked", "checked");
        } else if (tipConfigType == 1) {
            $("#update_InlineRadio2").prop("checked", "checked");
        }
        // $("#receiver").text(receiver)
        // $("#tipContent").text(tipContent)
        updateRemind();
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
        $('#taskName').on('click', 'span i', function (e) {
            e.stopPropagation();
            $(this).parent().remove();
        })
    }

    deleTaskName();
});


function checkRemindName() {
    var tip_config_name = $.trim($("#resource_databaseName").val());
    if(tip_config_name.length==0){
        $(".resource_databaseNameError").remove();
        $("#resource_databaseName").after('<p class="resource_databaseNameError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return true
    }else if(tip_config_name.length>20){
        $(".resource_databaseNameError").remove();
        $("#resource_databaseName").after('<p class="resource_databaseNameError font-orange remindError">·&nbsp;名称长度不能超过20个字符！</p>')
        return false
    }else {
        $(".resource_databaseNameError").remove();
        return true
    }
}
function checkRemindTaskName() {
    var arr = [];
    $.each($('.task_name span'), function () {
        arr.push($(this).data("id"))
    });
    if(arr.length==0){
        $(".taskNameError").remove()
        $("#taskName").after('<p class="taskNameError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return false
    }else {
        $(".taskNameError").remove()
        return true
    }
}

function checkRemindhostIp() {
    var resource_hostIp=$.trim($("#resource_hostIp").val())
    if(resource_hostIp.length==0){
        $(".resource_hostIpError").remove()
        $("#resource_hostIp").after('<p class="resource_hostIpError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return false
    }else {
        var ePattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if(ePattern.test(resource_hostIp)){
            $(".resource_hostIpError").remove()
            return true
        }else {
            $(".resource_hostIpError").remove()
            $("#resource_hostIp").after('<p class="resource_hostIpError font-orange remindError">·&nbsp;邮箱格式不对！</p>')
            return false
        }
    }


}

function checkRemindcentent() {
    var tip_config_content = $("#centent").val();
    if(tip_config_content.trim().length==0){
        $(".cententError").remove()
        $("#centent").after('<p class="cententError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return false
    }else {
        $(".cententError").remove()
        return true
    }
}

$('#addRemind').on('hidden.bs.modal', function (e) {
    // do something...
    $(".remindError").remove();
})

function addTipConfig() {
    var tip_config_name = $.trim($("#resource_databaseName").val());
    var arr = [];
    $.each($('.task_name span'), function () {
        arr.push($(this).data("id"))
    });
    arr.join(',');
    var jobIds = arr.toString();

    var tip_config_receiver = $("#resource_hostIp").val();
    var tip_config_type = $("input[type='radio']:checked").val();
    var tip_config_content = $("#centent").val();
    var checkFunc=[checkRemindName,checkRemindTaskName,checkRemindhostIp,checkRemindcentent]
    var result = checkFunc.every(function (item, index, array) {
        return item();
    })
    if(result){
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

}

function UpcheckRemindName() {
    var update_databaseName = $.trim($("#update_databaseName").val());
    if(update_databaseName.length==0){
        $(".resource_databaseNameError").remove();
        $("#update_databaseName").after('<p class="resource_databaseNameError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return true
    }else if(update_databaseName.length>20){
        $(".resource_databaseNameError").remove();
        $("#update_databaseName").after('<p class="resource_databaseNameError font-orange remindError">·&nbsp;名称长度不能超过20个字符！</p>')
        return false
    }else {
        $(".resource_databaseNameError").remove();
        return true
    }
}

function UpcheckRemindhostIp() {
    var update_Resource_hostIp=$.trim($("#update_Resource_hostIp").val())
    if(update_Resource_hostIp.length==0){
        $(".resource_hostIpError").remove()
        $("#update_Resource_hostIp").after('<p class="resource_hostIpError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return false
    }else {
        var ePattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if(ePattern.test(update_Resource_hostIp)){
            $(".resource_hostIpError").remove()
            return true
        }else {
            $(".resource_hostIpError").remove()
            $("#update_Resource_hostIp").after('<p class="resource_hostIpError font-orange remindError">·&nbsp;邮箱格式不对！</p>')
            return false
        }
    }


}

function UpcheckRemindcentent() {
    var update_Centent = $("#update_Centent").val();
    if(update_Centent.trim().length==0){
        $(".cententError").remove()
        $("#update_Centent").after('<p class="cententError font-orange remindError">·&nbsp;不能为空哦！</p>')
        return false
    }else {
        $(".cententError").remove()
        return true
    }
}

$('#updateRemind').on('hidden.bs.modal', function (e) {
    // do something...
    $(".remindError").remove();
})



function updateTipConfig() {
    var tip_config_name = $("#update_databaseName").val();
    var tipConfigId = $("#tipConfigId").val();
    var jobId = $("#update_schJobId").val();
    // var arr = [];
    // $.each($('.task_name span'),function(){
    //     arr.push($(this).data("id"))
    // });
    // arr.join(',');
    // var jobIds = arr.toString();
    // var jobId = $('.task_name1 span').data("id");

    var tip_config_receiver = $("#update_Resource_hostIp").val();
    var tip_config_type = $("input[name='updateInlineRadioOptions']:checked").val();
    var tip_config_content = $("#update_Centent").val();
    var checkFunc=[UpcheckRemindName,UpcheckRemindhostIp,UpcheckRemindcentent]
    var result = checkFunc.every(function (item, index, array) {
        return item();
    });
    if(result){
        $.ajax({
            url: basePath + "/tipConfig/update.do",
            data: {
                "tipConfigName": tip_config_name,
                "tipConfigId": tipConfigId,
                "schJobId": jobId,
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

}
function updateEnable(obj) {
    console.log($(obj))
    // var obj = $(this);
    var tipConfigId = $(obj).siblings("#tipConfigId").val();
    // var tipConfigName = $(obj).siblings("#tipConfigName").val();
    // var schJobId = $(obj).siblings("#schJobId").val();
    // var schJobName = $(obj).siblings("#schJobName").val();
    // var tipConfigReceiver = $(obj).siblings("#tipConfigReceiver").val();
    // var tipConfigType = $(obj).siblings("#tipConfigType").val();
    // var configType = $(obj).siblings("#configType").val();
    // var tipConfigContent = $(obj).siblings("#tipConfigContent").val();
    //
    // var tipConfigEnable = $("#tipConfigEnable").val();
    var tipConfigEnable = $(obj).siblings("#tipConfigEnable").val();
    var tipConfigId = $(obj).siblings("#tipConfigId").val();
    $.ajax({
        url: basePath + "/tipConfig/updateEnable.do",
        data: {
            "tipConfigEnable": tipConfigEnable,
            "tipConfigId": tipConfigId,
        },
        type: 'POST',
        dataType: "json",
        success: function (data) {
            switch (data.code) {
                case 417:
                    toastr.error(data.msg);
                    break;
                case 200:
                    toastr.success(data.msg);
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
