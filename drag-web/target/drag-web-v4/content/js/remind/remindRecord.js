/**
 * Created by cdyoue on 2017/8/8.
 */
// 分页
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
                getRecordList(num);
            }

        }
    });

}


function EmailRemind(obj) {
    var tipTitle=$(obj).next().val();
    var jobName=$(obj).siblings("#jobName2").val();
    var tipName=$(obj).siblings("#tipName2").val();
    var tipTime=$(obj).siblings("#tipTime2").val();
    var receiver=$(obj).siblings("#receiver2").val();
    var tipContent=$(obj).siblings("#tipContent2").val();
    $("#tipTitle").text("运维中心"+tipTitle+"提醒")
    $("#jobName").text(jobName)
    $("#tipName").text(tipName)
    $("#tipTime").text(tipTime)
    $("#receiver").text(receiver)
    $("#tipContent").text(tipContent)
    $("#EmailRemind").modal('show')
}
$(function () {
    $('#flowWorkTbody').on('click','.seeData',function () {
        var obj = $(this)
        EmailRemind(obj);//提醒详情
    })
    //回车键搜索
    $("#inputSearchRecord").keydown(function(event){
        event=document.all?window.event:event;
        if((event.keyCode || event.which)==13){
            getRecordList(1);
        }
    });
});

//通用的ajax查询
function getRecordList(page) {
    var tipRecordStatus=null;
    var tipConfigType=null;
    $(".selected").each(function(){//获取添加的类型
        var tipType=$(this).text();
        if("发送成功"==tipType){
            tipRecordStatus=1;
        } else if("发送失败"==tipType){
            tipRecordStatus=0;
        } else if("完成"==tipType){
            tipConfigType=1;
        } else{
            tipConfigType=0;
        }

    });
    var inputName=$("#inputSearchRecord").val();
    var strs=$('#chooseDate').val().split('/');
    var start=$.trim(strs[0]);
    console.log(start);
    var end=$.trim(strs[1]);
    console.log(end);
    $.ajax({
        url:basePath+"/tipRecord/selectrecordlist.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page,"inputName":inputName,"startTime":start,"endTime":end,"tipRecordStatus":tipRecordStatus,"tipConfigType":tipConfigType},
        success: function (json) {
            $("#flowWorkTbody").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            if(totalPage==0){
                totalPage=1;
            }
         /*   $("#totalpage").val(totalPage);
            $("#curPage").val(curPage);*/
            var recordlist = "";
            var list = json.rows;
            if(list==""){
                recordlist='<tr class="odd gradeX"><td class="remind_name">- -</td><td>- -</td><td class="hasAction"><span>- -</span></td><td>- -</td><td>- -</td><td>- -</td><td>- -</td></tr>'
            }else{
                $.each(list,function(index,array) { //遍历json数据列
                    console.log(list[index]);
                    recordlist+="<tr class='odd gradeX'>"
                        +"<td class='remind_name'>"+list[index].schJobName+"</td>"
                        +"<td>"+list[index].tipConfigName+"</td>"
                        +"<td class='hasAction'>"
                        +"<div class='actions'>"
                        +"<a href='javascript:;' class='seeData' title='查看'>"
                        +"<i class='icon iconfont icon-chakan'></i>"
                        +"</a>"
                        +"<input id='tipTitle2' value='"+list[index].configType+"' type='hidden'>"
                        +"<input id='jobName2' value='"+list[index].schJobName+"' type='hidden'>"
                        +"<input id='tipName2' value='"+list[index].tipConfigName+"' type='hidden'>"
                        +"<input id='tipTime2' value='"+list[index].recordSendTime+"' type='hidden'>"
                        +"<input id='receiver2' value='"+list[index].tipConfigReceiver+"' type='hidden'>"
                        +"<input id='tipContent2' value='"+list[index].tipContent+"' type='hidden'>"
                        +"</div>"
                        +"<span>"+list[index].recordSendTime+"</span>"
                        +"</td>"
                        +"<td>"+list[index].tipConfigReceiver+"</td>"
                        +"<td>"+list[index].configSendType+"</td>"
                        +"<td>"+list[index].recordStatus+"</td>"
                        +"<td>"+list[index].configType+"</td>"

                        +"</tr>"
                });
            }

            $("#flowWorkTbody").append(recordlist);
        },
        complete:function () {
            paginator(curPage,totalPage);
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }

    });
}
$("#inputSearchRecord").keydown(function (event) {
    event=document.all?window.event:event;
    if ((event.code||event.which)==13){
        getRecordList(1);
    }
    
});
//输入框搜索
function searchRecord() {
    getRecordList(1);

}

//时间控件搜索
// function searchByDate() {
//     setTimeout(function () {
//         var start1=$("#start1").val();
//         var end1=$("#end1").val();
//         if(start1!=""||end1!=""){
//             getRecordList(1);
//         }
//     },10)
//
// }
// 筛选日期
var dateTime;
//页面上按时间筛选展示的默认时间段
function getDefaultDate(){
    $.ajax({
        type : "post",
        url : basePath+"/tipRecord/getdefaultdate.do",
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
//根据类型搜索
function selectByType() {
    getRecordList(1);
}

getDefaultDate();
laydate.render({
    elem: '#chooseDate',
    type: 'date',
    range: '/',
    value: dateTime,
    done:function(value){
        var strs=value.split('/');
        setTimeout(function () {
            if(strs[0]!=""||strs[1]!=""){
                getRecordList(1);
            }
        },10)
    }
});
