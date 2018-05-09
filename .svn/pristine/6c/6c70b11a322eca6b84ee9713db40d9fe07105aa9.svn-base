/**
 * Created by sky on 2017/8/23.
 */
$(function () {
//页面上按时间筛选展示的默认时间段
    getDefaultDate();
    //获取任务的成功和失败的情况个数
    taskStatusCount();
    //任务情况图
    taskCondition();
    //任务运行情况
    runCondition();
    //任务数量
    taskStateCount();
    //出错排行
    failList();
    //时长排行
    taskTimeList();

})
//页面上按时间筛选展示的默认时间段
function getDefaultDate(){
    $.ajax({
        type : "post",
        url : basePath+"/drag/scheduler/getSchedulerHisJobDefaultDate.do",
        async : false,
        success : function(data){
            var obj=JSON.parse(data);
            var str=obj.msg;

            dateTime=str.replace('/', ' / ');

            $('#chooseDate1').val(dateTime);
            $('#chooseDate2').val(dateTime);
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
//获取任务的成功和失败的情况个数
function taskStatusCount() {
    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/taskStatusCount.do',
        dataType:"json",
        success:function (result) {
            if(result!=null){
                $('.state0').html(result.obj.statusCount.state0);
                $('.state1').html(result.obj.statusCount.state1);

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
function taskCondition() {
    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/taskFinish.do',
        contentType: "application/json",
        success:function (result) {
            if(result!=null){
                var data = eval('(' + result + ')');
                var todayData=data.todayData;
                var yesterdayData=data.yesterdayData;
                var avgData=data.avgData;
//                     把参数传入任务情况图函数
                completeCharts("complete",todayData,yesterdayData,avgData);

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
    })

}
function runCondition() {
    $.ajax({
        type:'post',
        url:basePath +"/drag/ops/taskRun.do",
        contentType: "application/json",
        success:function (result) {
            if(result!=null){
                var data = eval('(' + result + ')');

                var runData=data.runData;

//                     把参数传入任务情况图函数
                zhexian("zhexian",runData);
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
    })
}
function taskStateCount() {
    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/taskCount.do',
        contentType: "application/json",
        success:function (result) {
            if(result!=null){
                var data = eval('(' + result + ')');
                var weekData=data.weekData;
                var resultData=data.resultData;
                //同比昨天
                var yoyToday=data.yoyToday;
                //同比上周
                var yoyWeek=data.yoyWeek;
                //同比上个月
                var yoyMonth=data.yoyMonth;
                //当前任务总数
                var todayNum=data.todayNum;
                if(yoyToday<0){
                    $('.yoyToday').after("<i class='yoyDownStyle icon iconfont icon-xiajian'></i>")
                }else if(yoyToday>0){
                    $('.yoyToday').after("<i class='yoyUpStyle icon iconfont icon-shang1'></i>")
                }
                if(yoyWeek<0){
                    $('.yoyWeek').after("<i class='yoyDownStyle icon iconfont icon-xiajian'></i>")
                }else if(yoyWeek>0){
                    $('.yoyWeek').after("<i class='yoyUpStyle icon iconfont icon-shang1'></i>")
                }
                if(yoyMonth<0){
                    $('.yoyMonth').after("<i class='yoyDownStyle icon iconfont icon-xiajian'></i>")
                }else if(yoyMonth>0){
                    $('.yoyMonth').after("<i class='yoyUpStyle icon iconfont icon-shang1'></i>")
                }

                $('.yoyToday').html(yoyToday)
                $('.yoyWeek').html(yoyWeek)
                $('.yoyMonth').html(yoyMonth)
                $('.todayNum').html(todayNum)


            }
            tiaoxin("tiaoxin",weekData,resultData);
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
    })

}
//任务时长
function taskTimeList() {
    var schJobHisRunTime=null;
    var order=0;//排序 asc
    var createTime=null;
    var firstTime=null; //第一次开始运行时间
    var lastTime=null; //最后一次运行结束时间
    var strs=$('#chooseDate1').val().split('/');
    var startTime=null;
    var endTime=null;
    if(strs[0]!=""||strs[1]!=""){
        startTime=$.trim(strs[0]);//开始时间
        endTime=$.trim(strs[1]); //结束时间
    }




    $('.schJobHisRunTime').on('click',function () {

        schJobHisRunTime=1;
        createTime=null;
        firstTime=null;
        lastTime=null;

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        timeAjaxLine(startTime,endTime,createTime,firstTime,lastTime,schJobHisRunTime,order)
    })
    $('.createTime').on('click',function () {
        schJobHisRunTime=null;
        firstTime=null;
        lastTime=null;
        createTime="createTime";

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        timeAjaxLine(startTime,endTime,createTime,firstTime,lastTime,schJobHisRunTime,order)
    })
    $('.firstTime').on('click',function () {
        schJobHisRunTime=null;
        createTime=null;
        lastTime=null;
        firstTime="firstTime";

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        timeAjaxLine(startTime,endTime,createTime,firstTime,lastTime,schJobHisRunTime,order)

    })
    $('.lastTime').on('click',function () {
        schJobHisRunTime=null;
        createTime=null;
        firstTime=null;
        lastTime="lastTime";
        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        timeAjaxLine(startTime,endTime,createTime,firstTime,lastTime,schJobHisRunTime,order)
    })
    //1-4个显示
    $('.timeLong').on('click',function () {
        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        $.ajax({
            type:'post',
            url:basePath+'/drag/ops/timeList.do',
            dataType: "json",
            //'startTime':startTime,'endTime':endTime,'createTime':"kkk",'firstTime':firstTime,'lastTime':lastTime,'num':num,'order':order
            data:{'schJobHisRunTime':1,'order':order},
            success:function (result) {
                if(result!=""){
                    $("#noRunRecord").hide();
                    $("#runRecord").show()
                    var data = eval( result );
                    $('.timeLongFrom').empty();
                    for (var j=0;j<4;j++){

                        $('.timeLongFrom').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+ formatSeconds(data[j].schJobHisRunTime)+"</td>")
                    }
                }else {
                    $("#runRecord").hide();
                    $("#noRunRecord").show()
                }

            },
            error:function(data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        })
    })
    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/timeList.do',
        dataType: "json",
        //'startTime':startTime,'endTime':endTime,'createTime':"kkk",'firstTime':firstTime,'lastTime':lastTime,'num':num,'order':order
        //data:{'schJobHisStartTime':startTime,'schJobHisEndTime':endTime,'createTime':createTime,'firstTime':firstTime,'lastTime':lastTime,'schJobHisRunTime':schJobHisRunTime,'order':order},
        success:function (result) {
            if(result!=""){
                $("#noRunRecord").hide();
                $("#runRecord").show()
                var data = eval( result );
                for (var j=0;j<4;j++){
                    if(data[j]!=null){
                        $('.timeLongFrom').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+ formatSeconds(data[j].schJobHisRunTime)+"</td>")
                    }
                }
                $('#timeCountForm').empty();
                for (var j=0;j<data.length;j++){
                    var status=data[j].schJobHisStatus;
                    var state="成功";
                    //0:失败          1:成功        2:运行中    3:待运行
                    if(status==0){
                        state="失败"
                    }else if(status==1){
                        state="成功"
                    }else if(status==2){
                        state="运行中";
                    }else if(status==3){
                        state="待运行"
                    }
                    var runTime=formatSeconds(data[j].schJobHisRunTime);

                    $('#timeCountForm').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+data[j].createTime+"</td><td>"+data[j].firstTime+"</td><td>"+data[j].lastTime+"</td><td>"+runTime+"</td><td>"+state+"</td></tr>")
                }

            }else {
                $("#runRecord").hide();
                $("#noRunRecord").show()
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
//出错排行
function failList() {
    var num=null;//出错个数
    var order=0;//排序 asc
    var createTime=null;
    var firstTime=null; //第一次开始运行时间
    var lastTime=null; //最后一次运行结束时间
    var startTime=null; //开始时间
    var endTime=null;  //结束时间
    var strs1=$('#chooseDate1').val().split('/');
    if(strs1[0]!=""||strs1[1]!=""){
        startTime=$.trim(strs1[0]);//开始时间
        endTime=$.trim(strs1[1]); //结束时间
    }

    //点击次数排序
    $('.num').on('click',function () {

        num=1;
        createTime=null;
        firstTime=null;
        lastTime=null;

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        failAjaxLine(startTime,endTime,createTime,firstTime,lastTime,num,order)
    })
    //点击创建时间排序
    $('.createTime').on('click',function () {
        num=null;
        firstTime=null;
        lastTime=null;
        createTime="createTime";

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        failAjaxLine(startTime,endTime,createTime,firstTime,lastTime,num,order)

    })
    //点击开始时间排序
    $('.firstTime').on('click',function () {
        num=null;
        createTime=null;
        lastTime=null;
        firstTime="firstTime";

        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        failAjaxLine(startTime,endTime,createTime,firstTime,lastTime,num,order)
    })
    //点击结束时间排序
    $('.lastTime').on('click',function () {
        num=null;
        createTime=null;
        firstTime=null;
        lastTime="lastTime";
        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        failAjaxLine(startTime,endTime,createTime,firstTime,lastTime,num,order)

    })
    //点击失败次数时间排序
    $('.failNum').on('click',function () {
        if(order==0){
            order=1;
        }else  if(order==1){
            order=0;
        }
        failNumAjaxList(order);
    })

    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/failList.do',
        dataType: "json",
        data:{'schJobHisStartTime':startTime,'schJobHisEndTime':endTime,'createTime':createTime,'firstTime':firstTime,'lastTime':lastTime,'num':num,'order':order},
        success:function (data) {
            //alert(result)
            if(data!=""){
                //显示
                $("#failLineDiv").show();
                $("#yesMark").hide()
                for(var i=0;i<4;i++){
                    if(data[i]!=null){

                        //近一个月的出错
                        sTime=data[i].firstTime;
                        var d1=(new Date(sTime.replace(/\-/g, "\/")));
                        var nowTime=new Date();//现在
                        var monthPre=new Date().Format("yyyy-MM-dd hh:mm:ss")
                        var monthTime=new Date(getPreMonth(monthPre).replace(/\-/g, "\/"));//上个月
                        if(d1>=monthTime && d1<= nowTime){
                            $('.failTable').append(" <tr><td class='k_table_td' title="+data[i].sjName+">"+data[i].sjName+"</td><td>"+data[i].loginName+"</td><td>"+data[i].num+"</td></tr>")

                        }
                    }
                }
                for (var j=0;j<data.length;j++){
                    $('#failCountFrom').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+data[j].createTime+"</td><td>"+data[j].firstTime+"</td><td>"+data[j].lastTime+"</td><td>"+data[j].num+"</td><td>失败</td></tr>")
                }

            }else {
                $("#failLineDiv").hide();
                $("#yesMark").show()

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
/*
 * 时长排行的时间
 * */
laydate.render({
    elem: '#chooseDate1',
    type: 'date',
    range: '/',
    done:function(value){
        var strs=value.split('/');
        setTimeout(function () {
            if(strs[0]!=""||strs[1]!=""){
                timeAjaxLine(strs[0],strs[1]);
            }
        },10)
    }
});
/**
 * 出错的时间排行
 */
laydate.render({
    elem: '#chooseDate2',
    type: 'date',
    range: '/',
    done:function(value){
        var strs=value.split('/');
        setTimeout(function () {
            if(strs[0]!=""||strs[1]!=""){
                failAjaxLine(strs[0],strs[1]);
            }
        },10)
    }
});
function failAjaxLine(startTime,endTime,createTime,firstTime,lastTime,num,order) {

    $.ajax({
        type:'post',
        url:basePath+"/drag/ops/failList.do",
        dataType: "json",
        //'startTime':startTime,'endTime':endTime,'createTime':"kkk",'firstTime':firstTime,'lastTime':lastTime,'num':num,'order':order
        data:{'schJobHisStartTime':startTime,'schJobHisEndTime':endTime,'createTime':createTime,'firstTime':firstTime,'lastTime':lastTime,'num':num,'order':order},
        success:function (result) {
            if(result!=null){
                var data = eval( result );
                $('#failCountFrom').empty()

                for (var j=0;j<data.length;j++){

                    $('#failCountFrom').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+data[j].createTime+"</td><td>"+data[j].firstTime+"</td><td>"+data[j].lastTime+"</td><td>"+data[j].num+"</td><td>失败</td></tr>")
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
function failNumAjaxList(order) {
    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/failList.do',
        dataType: "json",
        data:{'num':1,'order':order},
        success:function (result) {
            if(result!=null){
                var data = eval( result );
                $('.failTable').empty()
                var sTime="";
                for(var i=0;i<4;i++){
                    //近一个月的出错
                    sTime=data[i].firstTime;
                    var d1=(new Date(sTime.replace(/\-/g, "\/")));
                    var nowTime=new Date();//现在
                    var monthPre=new Date().Format("yyyy-MM-dd hh:mm:ss")
                    var monthTime=new Date(getPreMonth(monthPre).replace(/\-/g, "\/"));//上个月
                    if(d1>=monthTime && d1<= nowTime){
                        $('.failTable').append(" <tr><td class='k_table_td' title="+data[i].sjName+">"+data[i].sjName+"</td><td>"+data[i].loginName+"</td><td>"+data[i].num+"</td></tr>")

                    }
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
function timeAjaxLine(startTime,endTime,createTime,firstTime,lastTime,schJobHisRunTime,order) {

    $.ajax({
        type:'post',
        url:basePath+'/drag/ops/timeList.do',
        dataType: "json",
        //'startTime':startTime,'endTime':endTime,'createTime':"kkk",'firstTime':firstTime,'lastTime':lastTime,'schJobHisEndTime':schJobHisEndTime,'order':order
        data:{'schJobHisStartTime':startTime,'schJobHisEndTime':endTime,'createTime':createTime,'firstTime':firstTime,'lastTime':lastTime,'schJobHisRunTime':schJobHisRunTime,'order':order},
        success:function (result) {
            if(result!=null){
                var data = eval( result );
                $('#timeCountForm').empty()

                for (var j=0;j<data.length;j++){
                    var status=data[j].schJobHisStatus;
                    var state="成功";
                    //0:失败          1:成功        2:运行中    3:待运行
                    if(status==0){
                        state="失败"
                    }else if(status==1){
                        state="成功"
                    }else if(status==2){
                        state="运行中";
                    }else if(status==3){
                        state="待运行"
                    }
                    var runTime=formatSeconds(data[j].schJobHisRunTime);
                    $('#timeCountForm').append("<tr><td class='k_table_td' title="+data[j].sjName+">"+data[j].sjName+"</td><td>"+data[j].loginName+"</td><td>"+data[j].createTime+"</td><td>"+data[j].firstTime+"</td><td>"+data[j].lastTime+"</td><td>"+runTime+"</td><td>"+state+"</td></tr>")
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
function formatSeconds(value) {
    var theTime = parseInt(value);// 秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时

    if(theTime > 60) {
        theTime1 = parseInt(theTime/60);
        theTime = parseInt(theTime%60);

        if(theTime1 > 60) {
            theTime2 = parseInt(theTime1/60);
            theTime1 = parseInt(theTime1%60);
        }
    }
    var result = ""+parseInt(theTime)+"秒";
    if(theTime1 > 0) {
        result = ""+parseInt(theTime1)+"分"+result;
    }
    if(theTime2 > 0) {
        result = ""+parseInt(theTime2)+"小时"+result;
    }
    return result;
}
/**
 * 获取上一个月
 *
 * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
 */
function getPreMonth(date) {
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var day = arr[2]; //获取当前日期的日
    var days = new Date(year, month, 0);
    days = days.getDate(); //获取当前日期中月的天数
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 == 0) {
        year2 = parseInt(year2) - 1;
        month2 = 12;
    }
    var day2 = day;
    var days2 = new Date(year2, month2, 0);
    days2 = days2.getDate();
    if (day2 > days2) {
        day2 = days2;
    }
    if (month2 < 10) {
        month2 = '0' + month2;
    }
    var t2 = year2 + '-' + month2 + '-' + day2;
    return t2;
}
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