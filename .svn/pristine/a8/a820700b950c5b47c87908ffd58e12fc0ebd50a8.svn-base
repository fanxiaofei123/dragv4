/**
 * Created by guzhendong on 2017/11/28.
 */
function paginator(curPage,totalPage) {
    //    分页
    var total=parseInt(totalPage);
    $('.pagination-panel-total').text(total);
    $('.pagination').jqPaginator({
        totalPages: total,
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            $('#new_num').html(num);
            if(num!=curPage){
                getModelTrainedList(num);
            }

        }
    });

}

$(function () {
    //选择删除的模型批量删除，可选择的状态
    $(".table").on("click","input[name='checkbox']",function(){
        var check=$(this);
        var deleteModel=$("#deleteDiv");
        if(check.prop("checked")){
            deleteModel.addClass("haveCheckIcon");
        }else {
            if($("input[name='checkbox']:checked").length==0){
                deleteModel.removeClass("haveCheckIcon");
            }else if($(".table-checkbox input[name='checkbox']:checked").length==0 ){
                deleteModel.removeClass("haveCheckIcon");
            }else if($(".table-checkbox input[name='checkbox']:checked").length==1 && $(".model_tr input[name='checkbox']:checked").length==0){
                deleteModel.removeClass("haveCheckIcon");
            }
        }

    });

})
//显示弹框批量删除
function deleteModelTitle() {
    if($("#modelTrainedBody input[name='checkbox']:checked").length>0){
        $("#deleteAllModel").modal('show');
    }
}
function sureDeleteModel() {
    $("#deleteAllModel").modal('hide');
    var ids="";
    $("input:checked:not(.group-checkable)").each(function () {
        var id=$(this).attr('id');
        ids+=id+","
    });
    $.post(basePath+"/service/delServiceModel.do",{serviceModelIds:ids},function (data) {
        console.log(data);
        if (data.code==417) {
            toastr.error("删除失败");
        }else {
            toastr.success("删除成功");
        }
        var pageNum=$("#new_num").html();
        getModelTrainedList(pageNum);
    })

}
var modelTrainedId
//单个删除的弹框
function deleteModel(obj){
    modelTrainedId = $(obj).siblings("input").val();
    $("#deleteSingleModel").modal('show')
}
function sureSingleDeleteModel() {
    $("#deleteSingleModel").modal('hide')
    $.post(basePath+"/service/delServiceModel.do",{serviceModelIds:modelTrainedId},function (data) {
        console.log(data);
        if (data.code==417) {
            toastr.error("删除失败");
        }else {
            toastr.success("删除成功");
        }
        var pageNum=$("#new_num").html();
        getModelTrainedList(pageNum);
    })
}

//重命名的弹框
function renameModel(obj) {
    var newName=$("#fileName").val("");
    modelTrainedId = $(obj).siblings("input").val();
    $("#renameModel").modal('show');
}
$("#fileName").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        sureRename();
    }
});
//确认重命名
//搜索
function searchModelTrained() {
    var inputName=$("#searchName").val();
    if(inputName==""||inputName==null){
        toastr.warning("搜索内容不能为空")
    }else {
        getModelTrainedList(1);
    }
}
$("#searchName").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        getModelTrainedList(1);
    }
});
//加载列表
function getModelTrainedList(page){
    var inputName=$("#searchName").val();
    $.ajax({
        url:basePath+"/service/selectAllByAnyThing.do",
        type: 'GET',
        dataType: "json",
        data:{"page":page,"serviceModelName":inputName},
        success:function (json) {
            $("#modelTrainedBody").empty();
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            if(totalPage==0){
                totalPage=1;
            }
            var recordlist = "";
            var list = json.rows;
            $.each(list,function(index,array) { //遍历json数据列
                console.log(list[index]);

                recordlist+=" <tr class='odd gradeX'>"
                    +"<td>"
                    +"<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                    +"<input type='checkbox' name = 'checkbox' class='checkboxes' id='"+list[index].serviceModelId+"'/>"
                    +"<span></span>"
                    +"</label>"
                    +"</td>"
                    +"<td>"+list[index].serviceModelName+"</td>"
                    +"<td>"+list[index].userName+"</td>"
                    +"<td>"+list[index].serviceCreateTimes+"</td>"
                    +"<td>"+list[index].serviceUpdateTimes+"</td>"
                    +"<td>"
                    +"<div class='actions'>"
                    +"<input id='modelTrainedId' value='"+list[index].serviceModelId+"' type='hidden'>"
                    +"<a id='editField' onclick='editField("+list[index].serviceModelId+")' title='编辑字段信息' >"
                    +"<i class='icon iconfont  icon-bianji1'></i>"
                    +"</a>"
                    +"<a href='"+basePath+"/service/selectByPrimaryKey.do?serviceModelId="+list[index].serviceModelId+"&serviceModelName="+list[index].serviceModelName+"' data-toggle='modal' id='switchStatus' title='查看详情' >"
                    +"<i class='icon iconfont  icon-chakan'></i>"
                    +"</a>"
                    +"<a href='"+basePath+"/service/serviceModelApi.do?serviceModelId="+list[index].serviceModelId+"'  onclick='' title='模型调试'>"
                    +"<i class='icon iconfont  icon-qichexiuli'></i>"
                    +"</a>"
                    +"<a href='javascript:;' data-toggle='modal' onclick='deleteModel(this)' title='删除'>"
                    +"<i class='icon iconfont  icon-cuo2' ></i>"
                    +"</a>"
                    +"</div>"
                    +"</td>"
                +"</tr>"
            })
            $("#modelTrainedBody").append(recordlist);
        },
        complete:function() {
            paginator(curPage,totalPage);//分页
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }

    });
}



// 空格过滤
    function removeAllSpace(str) {
        return str.replace(/\s+/g, "");
    }

// 动态描述字段
function fieldDes(data) {
    $("#serviceModelFlowId").val(data);
    $("#changeType").modal('toggle')
}

function editField(data) {
    $.ajax({
        url:basePath+"/service/editField.do",
        type: 'GET',
        dataType: "json",
        data:{"serviceModelId":data},
        success:function (json) {
            console.log(json.obj)
            var strList = new Array();
            var str = "请求参数：";
                str += "<table id='labelColumn' class=\"table table-bordered\"><thead><tr><th>字段名(选择列)</th><th>类型</th><th>描述</th></tr></thead>";
            str += '<tbody>';
            for (var j = 0; j < json.obj.labelColumnList.length; j++) {
                str += '<tr>' +
                    '<td><span class="syl">' + json.obj.labelColumnList[j] + '</span></td>' +
                    '<td>' +
                    '<span class="choose-type">' +
                    '<span class="syl">Numeric</span>' +
                    '</span>' +
                    '</td>' +
                    '<td><span class="syl">' + 1 + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                    '</tr>'
            }
            str += "</table>";
            if(json.obj.selectTheColumnList.length > 1){
                strList =   json.obj.selectTheColumnList[0].split(",");
                console.log("strList:"+strList)
                str += '<table id="selectTheColumn" class=\"table table-bordered\"><thead><tr><th>字段名(标签列)</th><th>类型</th><th>描述</th></tr></thead>';
                for(var k =0;k<strList.length;k++){
                    // console.log(strList[k])
                    str += '<tr><td><span class="syl">' + strList[k] + '</span></td>' +
                        '<td>' +
                        '<span class="choose-type">' +
                        '<span class="syl">Numeric</span>' +
                        '</span>' +
                        '</td>' +
                        '<td><span class="syl">' + "描述" + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td></tr>'
                }
            }
            str += '</tbody></table>';
            $('#changeType .portlet-body').html(str);
            fieldDes(data);
        }


    })

}
// 点击确定保存field
$('#saveField').on('click', function (blockId, type) {
    saveField(blockId, type);
    $('#changeType').modal('hide')
});

function saveField(blockId, type) {
    // 增加保存修改的字段描述
    var data = [],data2=[];
    $('#changeType table#labelColumn tbody tr').each(function () {
        var json = {};
        $(this).find('td span.syl').each(function (i) {
            json[i] = $(this).text();
        });
        data.push(json)
    });
    $('#changeType table#selectTheColumn tbody tr').each(function () {
        var json = {};
        $(this).find('td span.syl').each(function (i) {
            json[i] = $(this).text();
        });
        data2.push(json)
    });
    var allData={
        labelColumn:data,
        selectTheColumn:data2
    };
    var serviceModelFlowId = $("#serviceModelFlowId").val();
    $.ajax({
        url:basePath+"/service/updateModelContext.do",
        type: 'GET',
        // dataType: "json",
        data:{"serviceModelContext":JSON.stringify(allData),"serviceModelId":serviceModelFlowId},
        success:function (json) {
            json = JSON.parse(json);
            if (json.code==417) {
                toastr.error("修改失败");
            }else {
                toastr.success("修改成功");
            }

        }
    });
}


$('#changeType table').on('click', 'td .choose-type', function () {
    if ($(this).next('ul').hasClass('dn')) {
        $(this).next('ul').removeClass('dn')
    } else {
        $(this).next('ul').addClass('dn')
    }
});
$('#changeType table').on('click', 'td ul li', function () {
    $(this).parents('td').find('.choose-type span').html($(this).text());
    $(this).parents('ul').addClass('dn');
});
$('#changeType table').on('click', '#changeDes', function (e) {
    e.stopPropagation();
    if ($(this).hasClass('icon-miaoshu1')) {
        var txt = $(this).parent().find('.syl').text();
        $(this).parent().find('.syl').html('<input id="changDesTxt" type="text" value="' + txt + '">');
        $(this).removeClass('icon-miaoshu1').addClass('icon-dui2')
    } else {
        var txt = $(this).parent().find('#changDesTxt').val();
        $(this).parent().find('.syl').html(txt);
        $(this).removeClass('icon-dui2').addClass('icon-miaoshu1');
    }

});
// 字段描述添加滚动条
$(window).on("load", function () {
    $("#changeType .portlet-body").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside",
        alwaysShowScrollbar: 3
    });

});
// 滚动条end