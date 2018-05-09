$(function () {
    draggableCanvas();
    zoomMinus();
    zoomPlus();
    zoomNormal();
    zoomGrid();
    zoomFull();
    viewAllConfigs();
    openConsole();
    closeConsole();
    notesDetail();
    setTags();
    lookDataSize();
    setTimeout(function(){
        selectFlowIdByRunStatus();
    }, 500);
});
//------------------------拖动画布事件------------------------
function draggableCanvas() {
    var mouseX;
    var mouseY;
    var muoseD;
    var canvasBody = $("#canvasBody");
    var canvasBlock = $("#canvasBlock");
    var blockLeft;
    var blockTop;
    canvasBody.on("mousedown",function (e) {
        muoseD = true;
        mouseX = e.pageX;
        mouseY = e.pageY;
        blockLeft = canvasBlock.css("left");
        blockTop = canvasBlock.css("top");
        console.log(blockLeft, blockTop);
        $(document).mousemove(function (e) {
            if (muoseD) {
                canvasBlock.css("left", parseInt(blockLeft) + (e.pageX - mouseX) + "px");
                canvasBlock.css("top", parseInt(blockTop) + (e.pageY - mouseY) + "px");
            }
        });
    });
    $(document).mouseup(function (e) {
        muoseD = false;
    });
}
//------------------------------------------------------------

//-----------------------放大、缩小模块----------------------
window.setZoom = function (zoom, instance, transformOrigin, el) {
    transformOrigin = transformOrigin || [0.5, 0.5];
    instance = instance || jsPlumb;
    el = el || instance.getContainer();
    var p = ["webkit", "moz", "ms", "o"],
        s = "scale(" + zoom + ")",
        oString = (transformOrigin[0] * 100) + "% " + (transformOrigin[1] * 100) + "%";

    for (var i = 0; i < p.length; i++) {
        el.style[p[i] + "Transform"] = s;
        el.style[p[i] + "TransformOrigin"] = oString;
    }

    el.style["transform"] = s;
    el.style["transformOrigin"] = oString;

    instance.setZoom(zoom);
    instance.repaintEverything();

};
var zoomScale = 1;
//使用按钮进行缩放
// 放大
function zoomPlus() {
    $("#zoomPlus").click(function () {
        if (zoomScale < 1.4) {
            zoomScale += 0.1;
            setZoom(zoomScale);
        }
    });
    $(document).keyup(function (e) {
        var key = e.which;
        if (key == 187) {
            if (zoomScale < 1.4) {
                zoomScale += 0.1;
                setZoom(zoomScale);
            }
        }
    });
}
// 缩小
function zoomMinus() {
    $("#zoomMinus").click(function () {
        if (zoomScale > 0.7) {
            zoomScale = zoomScale - 0.1;
            setZoom(zoomScale);
        }
    });
    $(document).keyup(function (e) {
        var key = e.which;
        if (key == 189) {
            if (zoomScale > 0.7) {
                zoomScale = zoomScale - 0.1;
                setZoom(zoomScale);
            }
        }
    });
}
// 还原
function zoomNormal() {
    $("#zoomNormal").click(function () {
        zoomScale = 1;
        setZoom(zoomScale);
    });
    $(document).keyup(function (e) {
        var key = e.which;
        if (key == 48) {
            zoomScale = 1;
            setZoom(zoomScale);
        }
    });
}
//------------------------------------------------------------

//------------------------查看工作流全局配置信息--------------
function viewAllConfigs() {
    $("#iconConfig").click(function () {
        var allConfigsStr = '<div class="mt-element-list">';
        for (var i = 0; i < configObj.length; i++) {
            allConfigsStr += '<div class="mt-list-head list-simple font-white ' + configObj[i].BlockBg + '">'
                + '<div class="list-head-title-container"><h4 class="list-title">' + configObj[i].BlockName + '</h4></div></div>'
                + '<div class="mt-list-container list-simple"><ul>';
            for (var k = 0; k < configObj[i].data.length; k++) {
                //判断特殊的配置信息
                var configVal = configObj[i].data[k].configVal;
                if (configObj[i].data[k].configName == "dbLinkName" || configObj[i].data[k].configName == "tableName" || configObj[i].data[k].configName == "writeMode") {
                    configVal = configObj[i].data[k].configVal.split("|")[1];
                } else if (configObj[i].data[k].configName == "sqlStatment") {
                    configVal = configObj[i].data[k].configVal.split("|")[0];
                } else {
                    configVal = configObj[i].data[k].configVal;
                }

                allConfigsStr += '<li class="mt-list-item"><div class="list-item-content">'
                    + '<h5><a href="javascript:;">'
                    + configObj[i].data[k].configName
                    + '：</a>'
                    // + configObj[i].data[k].configVal
                    + '<span>'+configVal+'</span>'
                    + '</h5></div></li>';
            }
            allConfigsStr += '</ul></div>';
        }
        allConfigsStr += '</div>';
        $("#basicConfigInfo").find(".form-body").html(allConfigsStr);
        $("#basicConfigInfo").modal();
    })
}
// -----------------------------------------------------------

//------------------------重新训练----------------------------
function chongxinxunlian(){

    var b=0;
    for(i=0;i<configObj.length;i++){
        console.log(configObj[i])
        if(configObj[i].id == 76){
            b++;
        }
    }
    if(b==0){
        toastr.error("没有模型预测算子不能训练");
        $('#webServiceShow').modal('hide')
        return;
    }
    //重新训练
    run(999)


}
//------------------------------------------------------------

//-----------------------继续完成训练-------------------------
function updateFlow() {

    $.ajax({
        url: basePath + "/drag/flow/updateFlow.do",
        type: 'POST',
        data: {'id': $("#flowId").val()},
        success: function (data) {
            var data = JSON.parse(data);
            switch (data.code) {
                case 417:
                    toastr.error(data.msg);
                    break;
                case 200:
                    toastr.success(data.msg);
                    break;
            }
        },
        error:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
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
function ContinueFlow() {

    $.ajax({
        url: basePath + "/drag/flow/continueFlow.do",
        type: 'POST',
        data: {'id': $("#flowId").val()},
        success: function (data) {
            var data = JSON.parse(data);
            switch (data.code) {
                case 417:
                    toastr.error(data.msg);
                    break;
                case 200:
                    toastr.success(data.msg);
                    break;
            }
        },
        error:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        },
        complete:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
    });



}
//------------------------------------------------------------

// ----------------------新增网格是否显示---------------------
function zoomGrid() {
    var k = true;
    $('#zoomGrid').click(function () {
        if (k) {
            $('.portlet.light #canvasBody').css({"background": "#ecf0f1"});
            k = false;
        } else {
            $('.portlet.light #canvasBody').css({"background": "#ecf0f1 url('../../content/images/global/bg_grid.png') repeat"});
            k = true;
        }

    })
}
//------------------------------------------------------------

// ----------------------全屏---------------------------------
function zoomFull() {
    var k = true;
    $('#zoomFull').click(function () {
        if (k) {
            $('.canvasMain .operatorBlock').addClass('dn');
            $('.canvasMain .operatorMessage').addClass('dn');
            $('.operatorTitle').css({'width': '100%'});
            $('.canvasMain .canvasBlock').css({'width': '100%'})
            k = false;
        } else {
            $('.canvasMain .operatorBlock').removeClass('dn');
            $('.canvasMain .operatorMessage').removeClass('dn');
            $('.operatorTitle').css({'width': '84%'});
            $('.canvasMain .canvasBlock').css({'width': '80%'});
            k = true;
        }
    })
}
//------------------------------------------------------------


// ----------------------控制台-------------------------------
//开启、关闭控制台-
function openConsole() {
    $("#iconInfo").click(function () {
        $(".page-console").addClass("page-console-open");
    });
}
function closeConsole() {
    $("#closeConsole").click(function () {
        $(".page-console").removeClass("page-console-open");
    });
}
//------------------------------------------------------------

// ----------------------新增本地存储最近打开的工作流---------
$(document).ready(function () {
    // 通过获取本页面url
    var url = window.location.href;
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    }
    var name = GetQueryString('name'),
        flowId = GetQueryString('flowId');
    var message = {flowId: flowId, name: name, url: url};
    var m = JSON.parse(sessionStorage.getItem("data"));
    var str = "";
    if (m != null || m != undefined) {
        var n;
        for (var i = 0; i < m.length; i++) {
            console.log("sdhjk" + flowId);
            console.log("xoxodoososo" + m[i].flowId);
            if (m[i].flowId != flowId) {
                n = true;
            } else {
                n = false;
                break;
            }
        }
        console.log(n);
        if (n == true) {
            if (m.length == 20) {
                m.shift()
            }
            m.push(message);
            sessionStorage.setItem('data', JSON.stringify(m));
        }
        m.forEach(function (element, index, array) {
            if (element['flowId'] == flowId) {
                array.splice(index, 1);
            }
        });
        for (var i = 0; i < m.length; i++) {
            str += '<li><a href="' + m[i].url + '"><i class="glyphicon"></i><span>' + m[i].name + '</span></a><i class="glyphicon glyphicon-remove" onclick="deleteTempWorkStream(' + m[i].flowId + ',this)"></i></li>';

        }
        $('#sessionMessage').html('<li class="sessionCheck"><a href="' + url + '"><i class="glyphicon glyphicon-ok"></i><span>' + name + '</span></a></li>' + str);
        // 判断页面上面缓存是否有相同的flowid，有相同的去掉下面的li（明天来做）
    } else {
        var data = [];
        data.push(message);
        sessionStorage.setItem('data', JSON.stringify(data));
        $('#sessionMessage').html('<li class="sessionCheck"><a href="' + url + '"><i class="glyphicon glyphicon-ok"></i><span>' + name + '</span></a></li>');
    }
});
//删除临时工作流
function deleteTempWorkStream(flowId, obj) {
    var data = JSON.parse(sessionStorage.getItem('data'));
    data.forEach(function (element, index, array) {
        if (element['flowId'] == parseInt(flowId)) {
            array.splice(index, 1);
        }
    });
    $(obj).parent().remove()
    sessionStorage.setItem('data', JSON.stringify(data));
}
//显示和关闭临时工作流
function showDropDown() {
    if ($('#sessionMessage').css("display") == 'none') {
        $(".detailsName p i").removeClass("icon-xia").addClass("icon-shang");
        $('#sessionMessage').show();
        showQueryFlag = 1;
    } else {
        $(".detailsName p i").removeClass("icon-shang").addClass("icon-xia");
        $('#sessionMessage').css("display", "none")
    }
}
//------------------------------------------------------------

// ----------------------查看算子注释详情--------------------
function notesDetail() {
    $("#notesDes").on("click",function () {
        var url=$(this).find("p").attr("data-url");
        win = window.open(url,'notes','toolbar=no,location=no,resizable=no, height=600, width=800,scrollbars=yes ,left=380,top=100');
    })
}
//------------------------------------------------------------

// ----------------------设置算子标签-------------------------
function setTags() {
    $("#canvasBlock").on("click", "div.box .icon-tag", function () {
        $("#basicTags").modal();
        return false;
    })
}
//------------------------------------------------------------

// ----------------------数据查看弹框放大缩小物理按钮---------
function lookDataSize() {
    //数据查看的放大和还原
    $("#lookDataResultFrame .enlarge_model_btn").on('click', 'i', function (e) {
        e.stopPropagation();
        var val = $(this).attr('value')
        if (val == 0) {
            fullScreenAdd("lookDataResultFrame","enlarge_model_btn",1);
            $("#excel_content1").css({width: 'inherit'});
        } else {
            fullScreenRemove("lookDataResultFrame","enlarge_model_btn",0);
            $("#excel_content1").css({width:'899px'});
        }
    })
    //模型查看的放大和还原
    $("#lookModelResultFrame .enlarge_model_btn").on('click', 'i', function (e) {
        e.stopPropagation();
        var val = $(this).attr('value')
        if (val == 0) {
            fullScreenAdd("lookModelResultFrame","enlarge_model_btn",1);
            $("#excel_content2").css({width: 'inherit'});
        } else {
            fullScreenRemove("lookModelResultFrame","enlarge_model_btn",0);
            $("#excel_content2").css({width: '899px'});
        }
    });
    //查看数据和模型属性的放大和还原
    $("#lookDataAndModelResultFrame .enlarge_model_btn").on('click', 'i', function (e) {
        e.stopPropagation();
        var val = $(this).attr('value')
        if (val == 0) {
            fullScreenAdd("lookDataAndModelResultFrame","enlarge_model_btn",1);
            $("#lookDataDiv").css({width: 'inherit'});
            $("#lookModelDiv").css({width: 'inherit'});
        } else {
            fullScreenRemove("lookDataAndModelResultFrame","enlarge_model_btn",0);
            $("#lookDataDiv").css({width: '900px'});
            $("#lookModelDiv").css({width: '900px'});
        }
    });
    //模型查看的放大和还原
    $("#lookLogResultFrame .enlarge_model_btn").on('click', 'i', function (e) {
        e.stopPropagation();
        var val = $(this).attr('value')
        if (val == 0) {
            fullScreenAdd("lookLogResultFrame","enlarge_model_btn",1);
            $("#excel_content_logs").css({width: 'inherit'});
        } else {
            fullScreenRemove("lookLogResultFrame","enlarge_model_btn",0);
            $("#excel_content_logs").css({width: '900px'});
        }
    })
    //数据查看多个的放大和还原
    $("#lookTwoDataResultFrame .enlarge_model_btn").on('click', 'i', function (e) {
        e.stopPropagation();
        var val = $(this).attr('value')
        if (val == 0) {
            fullScreenAdd("lookTwoDataResultFrame","enlarge_model_btn",1);
            $("#excel_content_dataList").css({width: 'inherit'});
        } else {
            fullScreenRemove("lookTwoDataResultFrame","enlarge_model_btn",0);
            $("#excel_content_dataList").css({width: '899px'});
        }
    })
    function fullScreenAdd(frame,btn,num) {
        $("#"+frame+"  ."+btn).empty();
        $("#"+frame+"  ."+btn).append("<i class='icon iconfont icon-zuixiaohua' value='"+num+"' ></i>");
        $("#"+frame).children().first().addClass('fangdaDiv');
    }
    function fullScreenRemove(frame,btn,num) {
        $("#"+frame+"  ."+btn).empty();
        $("#"+frame+"  ."+btn).append("<i class='icon iconfont icon-zuixiaohua' value='"+num+"' ></i>");
        $("#"+frame).children().first().removeClass('fangdaDiv');
    }
//放大和还原end
}
//------------------------------------------------------------

// ----------------------搜索模块-----------------------------
function filterModel() {
    $("#formInput").keyup(function () {
        if ($(this).val() == "" || $(this).val() == null) {
            $(".mt-list-item").show();
            $("div.panel-collapse").removeClass("in");
            $("div.panel-collapse").css("height", "0");
        }
        else {
            var searchedList = $(".mt-list-item").filter(":contains('" + ($(this).val()) + "')");
            console.log($(this).val());
            $(".mt-list-item").hide();
            searchedList.show();
            searchedList.parents("div.panel-collapse").addClass("in");
            searchedList.parents("div.panel-collapse").css("height", "");
        }
    }).keyup();
}
//------------------------------------------------------------

// ----------------------查询工作流运行状态-------------------
function selectFlowIdByRunStatus(){
    var flowId=$("#flowId").val();
    //查询是否已部署服务，如果部署了就显示重新部署。
    $.ajax({
        url:basePath+"/service/selectRebootModel.do",
        type:"GET",
        dataType: "json",
        data:{"serviceModelFlowId":flowId},
        success: function (data) {
            if(data.code == 417){
                $(".bushuweiwebfuwu").prop("hidden",false);
                $(".chongxinxunlian").prop("hidden",true);
                var b=0;
                for(i=0;i<configObj.length;i++){
                    console.log(configObj[i])
                    if(configObj[i].id == 76){
                        b++;
                    }
                }
                if(b==0){
                    // toastr.error("没有模型预测算子不能部署417");
                    $(".chongxinxunlian").prop("hidden",true);
                    $(".bushuweiwebfuwu").prop("hidden",true);
                }
            }else{
                $(".bushuweiwebfuwu").prop("hidden",true);
                $(".chongxinxunlian").prop("hidden",false);
                var b=0;
                for(i=0;i<configObj.length;i++){
                    console.log(configObj[i])
                    if(configObj[i].id == 76){
                        b++;
                    }
                }
                if(b==0){
                    // toastr.error("没有模型预测算子不能部署200");
                    $(".chongxinxunlian").prop("hidden",true);
                    $(".bushuweiwebfuwu").prop("hidden",true);
                }
            }
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
    })
}
//------------------------------------------------------------

// ----------------------部署为模型服务-----------------------
function ServiceModelStatus(){
    var b=0;
    for(i=0;i<configObj.length;i++){
        console.log(configObj[i])
        if(configObj[i].id == 76){
            b++;
        }
    }
    if(b==0){
        toastr.error("没有模型预测算子不能部署");
        $('#webServiceShow').modal('hide')
        return;
    }
    //查询页面算子是否都训练成功
    function getElementsByClassName(n) {
        var classElements = [],allElements = document.getElementsByTagName('div');//所有div
        for (var i=0; i< allElements.length; i++ )
        {
            if (allElements[i].className == n ) {
                classElements[classElements.length] = allElements[i];
            }
        }
        return classElements;
    }
    var successType  =  getElementsByClassName("portlet-body boxBody success-type");
    if(successType.length!=configObj.length){
        toastr.error("模型算子未全部运行成功！");
        return;
    }else{
        $('#webServiceShow').modal('show')
    }


}
function insertServiceModel(){
    var flowId=$("#flowId").val();
    var serviceModelName=$("#serviceModelName").val();
    var serviceModelDescription=$("#serviceModelDescription").val();

    $.ajax({
        url:basePath+"/service/insertServiceModel.do",
        type:"GET",
        dataType: "json",
        data:{
            "serviceModelFlowId":flowId,
            "serviceModelName":serviceModelName,
            "serviceModelDescription":serviceModelDescription
        },
        success: function (data) {
            if(data.code == 417){
                $("#serviceModelName").val("");
                $("#serviceModelDescription").val("");
                toastr.error(data.msg);
                $('#webServiceShow').modal('hide')
            }else{
                $(".bushuweiwebfuwu").prop("hidden",true);
                $(".chongxinxunlian").prop("hidden",false);
                $("#serviceModelName").val("");
                $("#serviceModelDescription").val("");
                toastr.success(data.msg);
                $('#webServiceShow').modal('hide')
                // location.reload()
            }
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
    })
}
//------------------------------------------------------------

// ----------------------保存工作流---------------------------
function save() {

    var connects = [];
    $.each(jsPlumb.getAllConnections(), function (idx, connection) {
        var cont = connection.getLabel();
        console.log(jsPlumb.getAllConnections());
        console.log(connection);
        // 获取连接形状
        var connectType = "";
        var sourceId = connection.sourceId;
        var html = connection.source.children[1].textContent;
        var blockModelId=connection.source.children[0].children[0].firstChild.defaultValue;
        var modelList = $(".operatorBlock").find("h3.ui-draggable");
        // for (var j = 0; j < modelList.length; j++) {
        //     if ($(modelList[j]).text().replace(/\s/g, "") == html) {
        //         var connectType = $(modelList[j]).siblings(".rightNumber").val();
        //     }
        // }
        var modelListId=$(modelList).next();
        for (var j = 0; j < modelListId.length; j++) {
            if ($(modelListId[j]).val().replace(/\s/g, "") == blockModelId) {
                connectType = $(modelListId[j]).siblings(".rightNumber").val();
            }
        }
        connectType = connectType.substr(4, 6);

        // end
        connects.push({
            ConnectionId: connection.id,
            PageSourceId: connection.sourceId,
            PageTargetId: connection.targetId,
            SourceText: connection.source.children[1].textContent,
            TargetText: connection.target.children[1].textContent,
            SourceAnchor: [connection.endpoints[0].anchor.x, connection.endpoints[0].anchor.y, connection.endpoints[0].anchor.orientation[0], connection.endpoints[0].anchor.orientation[1]],
            TargetAnchor: [connection.endpoints[1].anchor.x, connection.endpoints[1].anchor.y, connection.endpoints[1].anchor.orientation[0], connection.endpoints[1].anchor.orientation[1]],
            ConnectText: $(cont).html(),
            ConnectType: connectType
        });
        console.log(connects);
    });


    var blocks = [];
    $(".box").each(function (idx, elem) {
        var $elem = $(elem);
        console.log($elem);
        var name = $elem[0].dataset.nodename;
        var fontClass = $elem.find(".myicon")[0].classList[2];
        var fontIconClass = $elem.find(".fa")[0].classList[1];
        var BlockModelId=$elem.find("input[name=modelId]").val();
        blocks.push({
            BlockId: $elem.attr('id'),
            BlockContent: $elem.find(".boxBody").text().replace(/\s/g, ""),
            BlockX: parseInt($elem.css("left"), 10),
            BlockY: parseInt($elem.css("top"), 10),
            BlockBorClass: $elem[0].classList[1],
            BlockBgClass: $elem[0].classList[2],
            BlockFontClass: fontClass,
            BlockFontIconClass: fontIconClass,
            Name: name,
            BlockModelId:BlockModelId
        });
    });

    console.log(blocks);

    //注释
    var notes = [];
    $(".noteDiv").each(function (idx, elem) {
        var $elem = $(elem);
        console.log($elem);
        notes.push({
            NoteId: $elem.attr('id'),
            NoteContent: $elem.find(".noteTextarea").val(),
            NoteWidth: parseInt($elem.css("width"), 10),
            NoteHeight: parseInt($elem.css("height"), 10),
            NoteX: parseInt($elem.css("left"), 10),
            NoteY: parseInt($elem.css("top"), 10),
            NoteBgColor: $elem.find(".noteTextarea").css("background-color"),
            NoteTextAlign: $elem.find(".noteTextarea").css("text-align")
        });
    });
    console.log(notes);

    $.ajax({
        url: basePath + "/drag/flow/createflow.do",
        type: 'POST',

        data: {
            'fieldStr': JSON.stringify(field),
            'modelStr': JSON.stringify(configObj),
            'connectStr': JSON.stringify(connects),
            "blocksStr": JSON.stringify(blocks),
            "notesStr": JSON.stringify(notes),
            'flowId': $("#flowId").val(),
            'flowName': $("#flowName").val(),
            "workSpaceName": $("#workSpaceName").val()
        },
        success: function (data) {
            var data = JSON.parse(data);
            switch (data.code) {
                case 417:
                    toastr.error("model配置错误，请检查");
                    break;
                case 200:
                    $("#run").removeClass("stopRun");
                    toastr.success("保存成功");
                    break;
            }
        },
        error:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
        ,
        complete:function(data){
            if(data == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        },
    });
}
//------------------------------------------------------------

// ----------------------序列化运行并保存模块数据-------------
// var serliza = "";
var schJobRunning = false;
function run(data) {
    if(data == 999){
        connetSocket2();
    }
    $.ajax({
        url: basePath + "/drag/scheduler/checkSchJobRunning.do",
        type: 'POST',
        dataType: "json",
        data: {'flowId': $("#flowId").val()},
        success: function (data) {
            schJobRunning = data;
            if (schJobRunning == false) {
                //第一步，先判断工作流配置里面是否包含有数据库连接的算子
                $("#run").addClass("stopRun");
                $("#scheduler").addClass("stopRun");
                var isContainsDbModel = false;
                for (var i = 0; i < configObj.length; i++) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configName == "dbLinkName") {
                            isContainsDbModel = true;
                        }
                    }
                }

                //第二步，判断工作流中的数据库配置的连接名和表名，判断是否已经删除了。
                //1.拿到所有的连接名
                var connNames = "";
                $.ajax({
                    url: basePath + "/drag/modelattri/LoadConnNames.do",
                    type: 'GET',
                    async: false,
                    contentType: "text",
                    success: function (data) {
                        connNames = data;
                    },
                    error: function () {
                    },
                    complete:function(data){
                        if(data == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    },
                })

                var isCorrectConfig = true;
                if (isContainsDbModel) {
                    for (var i = 0; i < configObj.length; i++) {
                        for (var k = 0; k < configObj[i].data.length; k++) {
                            if (configObj[i].data[k].configName.trim() == "dbLinkName") {
                                var configVal = configObj[i].data[k].configVal;
                                var selectedConfigVal = configVal.split("|")[1];
                                if (selectedConfigVal == "" || selectedConfigVal == null || selectedConfigVal == undefined) {
                                    isCorrectConfig = false;
                                } else {
                                    if (connNames.indexOf(selectedConfigVal) == -1) {
                                        isCorrectConfig = false;
                                    }
                                }
                            }
                        }
                    }
                }

                //如果工作流中包含数据库连接算子，并且数据库配置中的连接名已经被删除了。
                if (isContainsDbModel && !isCorrectConfig) {
                    toastr.error("model配置错误，请检查");
                } else {
                    //运行工作流
                    // 工作流运行清除状态
                    $.each(allType, function (i, value) {
                        $("#" + value + " .boxBody").removeClass("fail-type success-type");
                    });
                    // end
                    var connects = [];
                    $.each(jsPlumb.getAllConnections(), function (idx, connection) {
                        var cont = connection.getLabel();
                        var connectType = "";
                        var modelList = $(".operatorBlock").find("h3.ui-draggable");
                        var modelListId=$(modelList).next();
                        var blockModelId=connection.source.children[0].children[0].firstChild.defaultValue;
                        // for (var j = 0; j < modelList.length; j++) {
                        //     var html = connection.source.children[1].textContent;
                        //     if ($(modelList[j]).text().replace(/\s/g, "") == html) {
                        //         connectType = $(modelList[j]).siblings(".rightNumber").val();
                        //     }
                        // }
                        for (var j = 0; j < modelListId.length; j++) {
                            if ($(modelListId[j]).val().replace(/\s/g, "") == blockModelId) {
                                connectType = $(modelListId[j]).siblings(".rightNumber").val();
                            }
                        }
                        connectType = connectType.substr(4, 6);

                        connects.push({
                            ConnectionId: connection.id,
                            PageSourceId: connection.sourceId,
                            PageTargetId: connection.targetId,
                            SourceText: connection.source.children[1].textContent,
                            TargetText: connection.target.children[1].textContent,
                            SourceAnchor: [connection.endpoints[0].anchor.x, connection.endpoints[0].anchor.y, connection.endpoints[0].anchor.orientation[0], connection.endpoints[0].anchor.orientation[1]],
                            TargetAnchor: [connection.endpoints[1].anchor.x, connection.endpoints[1].anchor.y, connection.endpoints[1].anchor.orientation[0], connection.endpoints[1].anchor.orientation[1]],
                            ConnectText: $(cont).html(),
                            ConnectType: connectType
                        });
                    });

                    var checkResult = InputDataCheck(connects)
                    if(checkResult){
                        var blocks = [];
                        $(".box").each(function (idx, elem) {
                            var $elem = $(elem);
                            var name = $elem[0].dataset.nodename;
                            var fontClass = $elem.find(".myicon")[0].classList[2];
                            var fontIconClass = $elem.find(".fa")[0].classList[1];
                            var BlockModelId=$elem.find("input[name=modelId]").val();
                            blocks.push({
                                BlockId: $elem.attr('id'),
                                BlockContent: $elem.find(".boxBody").text().replace(/\s/g, ""),
                                BlockX: parseInt($elem.css("left"), 10),
                                BlockY: parseInt($elem.css("top"), 10),
                                BlockBorClass: $elem[0].classList[1],
                                BlockBgClass: $elem[0].classList[2],
                                BlockFontClass: fontClass,
                                BlockFontIconClass: fontIconClass,
                                Name: name,
                                BlockModelId: BlockModelId
                            });
                            console.log(blocks);
                        });
                        //注释
                        var notes = [];
                        $(".noteDiv").each(function (idx, elem) {
                            var $elem = $(elem);
                            console.log($elem);
                            notes.push({
                                NoteId: $elem.attr('id'),
                                NoteContent: $elem.find(".noteTextarea").val(),
                                NoteX: parseInt($elem.css("left"), 10),
                                NoteY: parseInt($elem.css("top"), 10),
                                NoteBgColor: $elem.find(".noteTextarea").css("background-color"),
                                NoteTextAlign: $elem.find(".noteTextarea").css("text-align")
                            });
                        });
                        console.log(notes);

                        //var configs = new Array();
                        //for (var i = 0; i < sessionStorage.length; i++) {
                        //    var key = sessionStorage.key(i);
                        //    var keyValue = sessionStorage[key];
                        //    tmp = {};
                        //    tmp.configId = key.substring(6);
                        //    tmp.configValue = keyValue;
                        //    configs.push(tmp);
                        //}

                        //serliza = JSON.stringify(connects) + "&" + JSON.stringify(blocks);
                        //var parm = {};
                        //parm.serliza = serliza;
                        //parm.configs = configs;
                        //parm.flowId = $('#flowId').val();

                        var runings = $.trim($("#running").html());
                        if (runings == "运行中。。") {
                            alert("工作电子流正在运行中......");
                        } else {
                            $.ajax({
                                url: basePath + "/drag/calculation/run.do",
                                type: 'POST',

                                data: {
                                    'modelStr': JSON.stringify(configObj),
                                    'connectStr': JSON.stringify(connects),
                                    "blocksStr": JSON.stringify(blocks),
                                    "notesStr": JSON.stringify(notes),
                                    'flowId': $("#flowId").val(),
                                    'flowName': $("#flowName").val(),
                                    "workSpaceName": $("#workSpaceName").val()
                                },
                                success: function (data) {

                                    var result = JSON.parse(data);
                                    console.log(result);
                                    //var historys = result.obj.historys
                                    switch (result.code) {
                                        case 417:
                                            $("#run").removeClass("stopRun");
                                            toastr.error("model配置错误，请检查");
                                            break;
                                        case 200:
                                            var jobId = result.obj.jobId;
                                            $("#usrId").val(jobId);
                                            $("#jobId").val(jobId);
                                            $("#iconInfo").click();
                                            var historys = result.obj.historys;
                                            showCalculationHistory(historys);
                                            break;
                                        case 400:
                                            toastr.error(result.msg);
                                            break;
                                    }


                                },
                                error: function () {
                                    // alert("连接错误，请重试！")
                                },
                                complete:function(data){
                                    if(data == "AjaxSessionTimeout"){
                                        window.location.href=basePath;
                                        return;
                                    }
                                },
                            });
                        }
                    }
                }
            } else {
                toastr.warning("当前的工作流正在调度任务中生效，不能运行此工作流！！！");
            }
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
    })
}
//给重新训练使用的socket
function connetSocket2() {
    var websocket = null;
    if (window['WebSocket'])
        websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket.do');
    else
        websocket = new new SockJS(PATH + '/websocket/socketjs');

    websocket.onopen = function (event) {
        console.log('open', event);
    };

    var count = 0 ;
    websocket.onmessage = function (event) {
        count++;
        if(count==1){
            var flowId=$("#flowId").val();
            $.ajax({
                url:basePath+"/service/updateByFlowId.do",
                type:"GET",
                dataType: "json",
                data:{
                    "serviceModelFlowId":flowId,
                    "serviceModelStatus":3
                },
                success: function (data) {
                    if(data.code == 417){
                        $("#serviceModelName").val("");
                        $("#serviceModelDescription").val("");
                        // toastr.error(data.msg);
                        $('#webServiceShow').modal('hide')
                    }else{
                        $("#serviceModelName").val("");
                        $("#serviceModelDescription").val("");
                        // toastr.success(data.msg);
                        $('#webServiceShow').modal('hide')
                    }
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }
            })
        }
        var result = JSON.parse(event.data);
        console.log(result.jobId + "^^^^^^^^" + $("#jobId").val())
        if (result.jobId == $("#jobId").val()) {
            $("div[ role='progressbar']").width(result.progressStatus);
            $("#progress-bar").html(result.progressStatus);
            $("#wellConsole").html(result.consoleMsg);
            var data = result.consoleMsg;
            console.log(data);
            if (data.indexOf("fail") > 0) {
                runningState = false;
                $("#running").html("<span style='color: #f82b2b' >失败<span>")
                $("#run").removeClass("stopRun");
                $("#scheduler").removeClass("stopRun");
                var flowId=$("#flowId").val();
                $.ajax({
                    url:basePath+"/service/updateByFlowId.do",
                    type:"GET",
                    dataType: "json",
                    data:{
                        "serviceModelFlowId":flowId,
                        "serviceModelStatus":2
                    },
                    success: function (data) {
                        if(data.code == 417){
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        }else{
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        }
                    },
                    error:function(data){
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    }
                })
            }

            var datas = data.split("<p");
            for (var i = 0; i < datas.length; i++) {
                if (datas.length > 2) {
                    for (var j = 0; j < configObj.length; j++) {
                        if (i + 2 < datas.length) {
                            if (datas[i + 2].indexOf("" + configObj[j].BlockId + "") > 0 && datas[i + 2].indexOf("运行成功") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("fail-type run-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("success-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("success-type");
                                }
                                console.log("-----------------------------" + configObj[j].BlockName + ":运行成功 ---------------------------");
                                // break;
                            } else if (datas[i + 2].indexOf("" + configObj[j].BlockId + "") > 0 && datas[i + 2].indexOf("运行出错") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("success-type run-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("fail-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("fail-type");
                                }
                                console.log("------------------------------" + configObj[j].BlockName + ":运行出错 ----------------------------");
                                // break;
                            } else if (datas[i + 2].indexOf("" + configObj[j].BlockId + "") > 0 && datas[i + 2].indexOf("开始启动") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("success-type fail-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("run-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("run-type");
                                }
                                runningState = true;
                                console.log("------------------------------" + configObj[j].BlockName + ":运行中... ----------------------------");
                                // break;
                            }
                        }
                    }
                }
            }
            if (result.progressStatus == "100%") {
                runningState = false;
                $("#running").html("成功")
                $("#run").removeClass("stopRun");
                $("#scheduler").removeClass("stopRun");
                if($(".chongxinxunlian").is(":hidden")){
                    $(".bushuweiwebfuwu").prop("hidden",false);
                }else{
                    // alert("显示web服务按钮")
                    $(".bushuweiwebfuwu").prop("hidden",true);
                }
                var flowId=$("#flowId").val();
                $.ajax({
                    url:basePath+"/service/updateByFlowId.do",
                    type:"GET",
                    dataType: "json",
                    data:{
                        "serviceModelFlowId":flowId,
                        "serviceModelStatus":1
                    },
                    success: function (data) {
                        if(data.code == 417){
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        }else{
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                            // toastr.success("工作流完成训练。");
                        }
                    },
                    error:function(data){
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    }
                })
            }
        }
    };
}
//------------------------------------------------------------

// ----------------------目前无效的运行状态-------------------
function ifRun() {
    $.get(basePath + "/drag/calculation/showHistory.do", function (result) {
        var result = JSON.parse(result);
        if (result.length > 0) {
            connetSocket();
            $("#iconInfo").click();
            showCalculationHistory(result);
        }
    });
}
function bindRun() {
    $(".icon-control-play").bind('click', function () {
        connetSocket();
        var result = $.get(basePath + "/drag/calculation/run.do");
        $(this).removeAttr('click');
        result.success(function (data) {
            var data = JSON.parse(data);
            if (data.code == '417') {
                alert("运行失败");
                return;
            } else if (data.code == '409') {
                alert(data.msg);
                return;
            }

            $("#iconInfo").click();
            showCalculationHistory(data.obj);
        });
    });
}
//------------------------------------------------------------
