//获取颜色值
$.fn.getBackgroundColor = function () {
    console.log($(this).css('background-color'));
    var rgb = $(this).css('background-color');
    console.log("rgb:" + rgb)
    if (rgb >= 0) {
        return rgb;    //如果是一个hex值则直接返回
    } else {
        rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

        function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
        }

        rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }
    return rgb;
};

//拖拽时模块样式
function draggingModel(event, ui) {
    if (event.type == "mousedown") {
        var previewUi = event.currentTarget;
        var par = $(previewUi).parents("ul");
        var bor = par.find("li");
        var fontIcon = bor.find("i");
        var fontIconClass = fontIcon[0].classList[1];
        var borClass = "border-common-color";
        var bgClass = "common-bg-color";
        var fontClass = "common-color";
        var html = $(previewUi).html();
        var blockStr = '<div class="portlet ' + borClass + ' ' + bgClass + ' box" style="height: 4.3%;">'
            + '<div class="portlet-title">'
            + '<div class="tools">'
            + '</div></div>'
            + '<div class="portlet-body boxBody">'
            + '<div class="myicon text-center ' + fontClass + '"><i class="fa ' + fontIconClass + ' fa-2x"></i></div>'
            + '<h5 class="text-center"><span class="modelName">' + html + '</span></h5>'
            + '</div></div>';
    }
    return blockStr;
}

//生成拖拽模块
var i = 0;
var configObj = [];
//设置showQueryFlag，记录sqlStatment输入框弹出的状态
var showQueryFlag = "hide";

//设置一个flag来保存sqlStatment输入框弹出的状态，false代表未显示输入框，true代表显示输入框。
function createNode(event, ui, block) {
    console.log(configObj);
    for (var f = 0; f < configObj.length; f++) {
        for (var k = 0; k < configObj[f].data.length; k++) {
            if (configObj[f].data[k].configType == 8) {
                if (configObj[f].data[k].configSelectNameOrRuleVal == undefined) {
                    configObj[f].data[k]["configSelectNameOrRuleVal"] = "true";
                    configObj[f].data[k]["configSelectRuleJson"] = "";

                }

            }

        }

    }

    var lastId = $("div.box:last").attr("id");
    if (lastId) {
        var lastIdNum = parseInt(lastId.substring(10));
        i = lastIdNum;
    }

    if (ui) {
        var scale = $("#canvasBlock").css("tra" +
            "nsform");
        var scaleStr = scale.slice(7, -1);
        var scaleNum = scaleStr.split(",")[0];
        var par = $(ui.draggable[0]).parents("ul");
        var bor = par.find("li");
        var fontIcon = bor.find("i");
        var fontIconClass = fontIcon[0].classList[1];
        // var borClass = bor[0].classList[1];
        // var bgClass = borClass.replace(/border/, "bg");
        // var fontClass = borClass.replace(/border/, "font");
        var borClass = "border-common-color";
        var bgClass = "common-bg-color";
        var fontClass = "common-color";
        console.log("bgClass111111121:" + $("." + bgClass).css('background-color'));
        var modelColor = $("." + bgClass).getBackgroundColor();
        console.log("modelColor111111121:" + modelColor);
        var left = parseInt((ui.offset.left - $(this).offset().left - $("#canvasBlock")[0].offsetLeft) / scaleNum);
        var top = parseInt((ui.offset.top - $(this).offset().top - $("#canvasBlock")[0].offsetTop) / scaleNum);
        var name = par[0].id;
        var html = $(ui.draggable[0]).html();
        var egHtml = $(ui.draggable[0]).siblings(".nameEnglish").val();
        var tem = egHtml.replace(/\s/g, "");
        var modelId = $("#" + tem).val();
        var trainedId = $(ui.draggable[0]).siblings("#modelTrainedId").val();
    }
    else {
        var left = block.BlockX;
        var top = block.BlockY;
        var name = block.Name;
        var html = block.BlockContent;
        var borClass = block.BlockBorClass;
        var bgClass = block.BlockBgClass;
        console.log("bgClass:" + bgClass);
        var modelColor = $("." + bgClass).getBackgroundColor();
        console.log("modelColor2222222:" + modelColor);
        var fontClass = block.BlockFontClass;
        var fontIconClass = block.BlockFontIconClass;
        var id = block.BlockId;
        var blockModelId = block.BlockModelId;
        var modelList = $(".operatorBlock").find("h3.ui-draggable");
        var modelListId = $(modelList).next();
        // for (var j = 0; j < modelList.length; j++) {
        //     if ($(modelList[j]).text().replace(/\s/g, "") == html) {
        //         var egHtml = $(modelList[j]).siblings(".nameEnglish").val();
        //     }
        // }
        for (var j = 0; j < modelListId.length; j++) {
            if ($(modelListId[j]).val().replace(/\s/g, "") == blockModelId) {
                var modelId = $(modelListId[j]).val().replace(/\s/g, "");
                var egHtml = $(modelListId[j]).siblings(".nameEnglish").val();
            }
        }
        var tem = egHtml.replace(/\s/g, "");
        // var modelId = $("#" + tem).val();
    }

    if (id) {

    } else {
        i++;
        id = "stateModel" + i;
    }
    console.log(id);
    var tooltipStr = "<div class='tooltipInfo'>" + html + "</div>";
    // var str = '<div class="portlet ' + borClass + ' ' + bgClass + ' box" data-nodeName="'+ name +'" id="' + id + '" data-toggle="tooltip" title="' + tooltipStr + '">'
    var str = '<div class="portlet ' + borClass + ' ' + bgClass + ' box" data-nodeName="' + name + '" id="' + id + '">'
        + '<div class="portlet-title">'
        + '<div class="tools">'
        + '<input type="hidden" name="modelId" value="' + modelId + '" />'
        + '<a class="icon-close" href="javascript:;" data-original-title="" title=""> </a>'
        + '</div></div>'
        + '<div class="portlet-body boxBody">'
        + '<div class="myicon text-center ' + fontClass + '"><i class="fa ' + fontIconClass + ' fa-2x"></i></div>'
        + '<h5 class="text-center"><span class="modelName" title="' + html + '">' + html + '</span></h5>'
        + '</div></div>';


    //基本连接线样式
    var connectorPaintStyle = {
        strokeWidth: 1,
        stroke: "#1bbc9b"
    };
    // 鼠标悬浮在连接线上的样式
    var connectorHoverStyle = {
        strokeWidth: 2,
        stroke: "#1bbc9b"
    };
    // 鼠标悬浮在连接点上的样式
    var endpointHoverStyle = {
        fill: modelColor,
        stroke: modelColor,
        radius: 7
    };
    var rectHoverStyle = {
        fill: modelColor,
        stroke: modelColor,
        width: 15,
        height: 15
    };
    var diaHoverStyle = {
        fill: modelColor,
        stroke: modelColor,
        width: 13,
        height: 13
    };
    var endpointStyle = {
        fill: "#fff",
        stroke: modelColor,
        radius: 6
    };
    //hive样式
    var linkCircleIn = {
        endpoint: "Dot",  //端点的形状
        endpointStyle: endpointStyle,
        // endpointHoverStyle: endpointHoverStyle,
        hoverClass: "inputData",
        dropOptions: {activeClass: "activeInputData"},
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            radius: 5,
            strokeWidth: 1
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: false,    //是否可以拖动（作为连线起点）
        scope: "dot",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: 1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    var linkCircleOut = {
        endpoint: "Dot",  //端点的形状
        endpointStyle: endpointStyle,
        // endpointHoverStyle: endpointHoverStyle,
        hoverClass: "outputData",
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            radius: 5,
            strokeWidth: 1
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: true,    //是否可以拖动（作为连线起点）
        scope: "dot",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    //model样式
    var linkSquareIn = {
        endpoint: ["Rectangle", {width: 12, height: 12}],  //端点的形状
        endpointStyle: endpointStyle,
        // endpointHoverStyle: rectHoverStyle,
        hoverClass: "inputModel",
        dropOptions: {activeClass: "activeInputModel"},
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            strokeWidth: 3
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: false,    //是否可以拖动（作为连线起点）
        scope: "Rectangle",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: 1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    var linkSquareOut = {
        endpoint: ["Rectangle", {width: 12, height: 12}],  //端点的形状
        endpointStyle: endpointStyle,
        // endpointHoverStyle: rectHoverStyle,
        hoverClass: "outputModel",
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            strokeWidth: 3
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: true,    //是否可以拖动（作为连线起点）
        scope: "Rectangle",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    //view样式
    var linkDiamondIn = {
        endpoint: ["Rectangle", {cssClass: "rotateModel", width: 13, height: 13}],  //端点的形状
        endpointStyle: endpointStyle,
        endpointHoverStyle: diaHoverStyle,
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            strokeWidth: 3
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: false,    //是否可以拖动（作为连线起点）
        scope: "Rectangle",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: 1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    var linkDiamondOut = {
        endpoint: ["Rectangle", {cssClass: "rotateModel", width: 13, height: 13}],  //端点的形状
        endpointStyle: endpointStyle,
        endpointHoverStyle: diaHoverStyle,
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: modelColor,
            fill: "transparent",
            strokeWidth: 3
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: true,    //是否可以拖动（作为连线起点）
        scope: "Rectangle",
        connector: ["Bezier", {curviness:45}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };

    //循环模块接口实例
    function createInterface(el, d) {
        var rightInterface = $("#" + el).siblings(".rightNumber").val().split(",");
        var leftInterface = $("#" + el).siblings(".leftNumber").val().split(",");
        for (var i = 0; i < leftInterface.length; i++) {
            if (leftInterface == "") {

            }
            else {
                jsPlumb.addEndpoint(d, {anchors: [i / 3 + 0.5, 0, 0, -1]}, eval(leftInterface[i]));
            }
        }
        for (var k = 0; k < rightInterface.length; k++) {
            if (rightInterface == "") {

            }
            else {
                jsPlumb.addEndpoint(d, {anchors: [k / 3 + 0.5, 1, 0, 1]}, eval(rightInterface[k]));
            }
        }
    }

    $("#canvasBlock").append(str);
    // if (ui) {
    //
    // }
    // else {
    //     for (var h = 0; h < configObj.length; h++) {
    //         if (configObj[h].BlockId == id) {
    //             for (var l = 0; l < configObj[h].data.length; l++) {
    //                 if (configObj[h].data[l].configName == "CustomModelDescription") {
    //                     $("#" + id).append('<p class="blockInfo">' + configObj[h].data[l].configVal + '</p>');
    //                 }
    //             }
    //         }
    //     }
    // }
    $("#" + id).css("left", left).css("top", top);
    createInterface(tem, id);
    jsPlumb.draggable(id, {containment: false});
    jsPlumb.bind("connection", function (connInfo, originalEvent) {
        if (connInfo.connection.sourceId == connInfo.connection.targetId) {
            jsPlumb.detach(connInfo);
        }
    });
    //switch (name) {
    //    case "import":
    //        $("#canvasBlock").append(str);
    //        $("#" + id).css("left", left).css("top", top);
    //        createInterface(tem, id);
    //        jsPlumb.draggable(id, { containment: false });
    //        break;
    //    case "export":
    //        $("#canvasBlock").append(str);
    //        $("#" + id).css("left", left).css("top", top);
    //        createInterface(tem, id);
    //        jsPlumb.draggable(id,{ containment: false });
    //        break;
    //    case "transfer":
    //        $("#canvasBlock").append(str);
    //        $("#" + id).css("left", left).css("top", top);
    //        createInterface(tem, id);
    //        jsPlumb.draggable(id,{ containment: false });
    //        break;
    //    case "attribution":
    //    case "classification":
    //    case "regression":
    //    case "clustering":
    //    case "associate":
    //    case "text":
    //        $("#canvasBlock").append(str);
    //        $("#" + id).css("left", left).css("top", top);
    //        jsPlumb.addEndpoint(id, { anchors: "TopRight" }, linkCircleOut);
    //        jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, linkSquareIn);
    //        console.log(jsPlumb);
    //        jsPlumb.draggable(id,{ containment: false });
    //        break;
    //    case "predict":
    //        $("#canvasBlock").append(str);
    //        $("#" + id).css("left", left).css("top", top);
    //        jsPlumb.addEndpoint(id, { anchors: [ 0, 0.8, 0, 1 ] }, linkCircleIn);
    //        jsPlumb.addEndpoint(id, { anchors: [ 0, 0.5, 0, 1 ] }, linkSquareIn);
    //        jsPlumb.addEndpoint(id, { anchors: [ 1, 0.33333333, 0, 1 ] }, linkCircleOut);
    //        jsPlumb.draggable(id,{ containment: false });
    //        break;
    //}
    if (ui) {
        configModel(trainedId, id, modelId, html, tem, bgClass, name);
        var modelInterface = $("#" + id).nextUntil(".box");
        $("#" + id).css("box-shadow", "2px 2px 19px #aaa");//选中效果
        $("#" + id).siblings(".box").css("box-shadow", "2px 2px 19px rgba(9,84,109,0.1)");//选中效果
        $("#" + id).css("z-index", "9");//选中层级提高
        modelInterface.css("z-index", "10");//选中层级提高
        $("#" + id).siblings(".box").css("z-index", "0");
        $(".jtk-endpoint").not(modelInterface).css("z-index", "1");
    }
}


function calculationPause() {

    $.ajax({
        url: basePath + "/drag/calculation/pause.do",
        type: 'POST',

        data: {'jobId': $("#jobId").val(), 'workFlowId': $("#flowId").val()},
        success: function (data) {
            var result = JSON.parse(data);
            switch (result.code) {
                case 417:
                    toastr.error("暂停错误");
                    break;
                case 200:
                    $("#iconInfo").hide();
                    break;
            }


        },
        error: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
        complete: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
    });
}

//重载模块
var field = [];

function reload(event) {
    $("#canvasBlock").html('');
    //var str = serliza;
    //var connectData = str.split("&")[0];
    //var blockData = str.split("&")[1];
    console.log($("#returnData").text());
    // alert($("#returnData").text())
    if ($("#returnData").text() == "") {
        $("#workSpaceExImg").css("display", "block");
        return;
    }
    var workFlow = JSON.parse($("#returnData").text());
    var connect = JSON.parse(workFlow.connects);
    var block = JSON.parse(workFlow.blocks);
    var config = JSON.parse(workFlow.models);
    //工作流流程示例的图片显示和隐藏
    if (config.length > 0) {
        $("#workSpaceExImg").css("display", "none");
    } else {
        $("#workSpaceExImg").css("display", "block");
    }
    if (workFlow.fields != undefined) {
        var fields = JSON.parse(workFlow.fields);
        if (fields.length > 0) {
            for (var i = 0; i < fields.length; i++) {
                field.push(fields[i]);
            }
        }
    }
    if (workFlow.notes != undefined) {
        var note = JSON.parse(workFlow.notes);
        if (note.length > 0) {
            for (var j = 0; j < note.length; j++) {
                createNoteWrite(event, note[j], note[j].NoteX, note[j].NoteY, note[j].NoteId, note[j].NoteWidth, note[j].NoteHeight);
            }
        }
    }
    for (var i = 0; i < config.length; i++) {
        configObj.push(config[i]);
    }

    var conn = "";

    console.log(connect);
    console.log(block);
    console.log(config);
    console.log(note);

    // 鼠标悬浮在连接线上的样式
    var connectorHoverStyle = {
        lineWidth: 4,
        stroke: "#1bbc9b",
        outlineWidth: 2,
        outlineColor: "white"
    };
    //基本连接线样式
    var connectorPaintStyle = {
        endpointStyle: {fill: "transparent"},
        paintStyle: {stroke: "#1bbc9b"},
        overlays: [["Arrow", {width: 10, length: 10, location: 1}]],
        isSource: true,    //是否可以拖动（作为连线起点）
        isTarget: true,
        connectorHoverStyle: connectorHoverStyle,
        connector: ["Bezier", {curviness:45}]
    };

    if (block.length > 0) {
        for (i = 0; i < block.length; i++) {
            createNode(event, "", block[i]);
        }

        for (i = 0; i < connect.length; i++) {
            conn = "jsPlumb.connect({ source: \"" + connect[i].PageSourceId + "\", target: \"" + connect[i].PageTargetId +
                "\", anchors:[[" + connect[i].SourceAnchor + "],[" + connect[i].TargetAnchor + "]]}, connectorPaintStyle);";
            var myScript = "jsPlumb.ready(function () {" + conn + "});";
            console.log(myScript);
            eval(myScript);
        }

    }
    onloadType();
}

//删除模块
function removeModel() {
    $("#canvasBlock").on("click", "a.icon-close", function () {
        $(this).parents("div.box").tooltip("destroy");
        var blockId = $(this).parents("div.box").attr("id");
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {
                configObj.splice(i, 1);
            }
        }
        var conn = $(this).parents(".portlet");
        jsPlumb.remove(conn);
        $(".page-quick-sidebar-wrapper").css({"z-index": -1});
        // $('body').removeClass('page-quick-sidebar-open');
        return false;
    });
}

//生成配置信息的树形结构
var levelArr = [];

function initFloderConstruct() {
    $("#configForm").on("click", "a.btnTreeModal", function () {
        $("#jiazai").show();
        var aTreeVal = $(this).attr("value");

        var modelID = $(this).parents("#formBody").attr("data-modelID");

        $.jstree.destroy("#tree_1");
        $("#tree_1").html("<ul id='file-choice'></ul>");
        initAtree(aTreeVal, modelID)
    });
}

function initAtree(aTreeVal, modelID) {
    // if(modelID == 139){
    //
    // }else{}
    $.get(basePath + "/drag/hdfs/fileConstruct.do?paths=" + aTreeVal, function (fileConstruct) {
        var datas = JSON.parse(fileConstruct);
        console.log(datas);
        jQuery.each(datas, function (i, file) {
            var $userRoot = $("#tree_1");
            createFileLabelToHtml(file, $userRoot, $("<ul></ul>"));
        });
        var ulNode = "";
        var datasChildren;
        var isDir;
        var dirName;
        for (var i = 0; i < datas.length; i++) {
            isDir = datas[i].filefloderInfo.isdir;
            dirName = datas[i].filefloderInfo.name;
            ulNode += "<li file-url='" + datas[i].filefloderInfo.path + "' ";
            if (isDir == true) {
                ulNode += 'data-jstree='
                    + '{ "opened" : false }'
                    + '>'
                    + dirName
                    + '<ul>';
                datasChildren = datas[i].childFiles;
                findAllChildFile(datasChildren);
                ulNode += '</ul></li>';
            }
            else if (modelID != 139) {
                ulNode += 'data-jstree='
                    + '{ "type" : "file" }'
                    + '>'
                    + dirName
                    + '</li>';
            }

            function findAllChildFile(datasChildren) {
                for (var k = 0; k < datasChildren.length; k++) {
                    isDir = datasChildren[k].filefloderInfo.isdir;
                    dirName = datasChildren[k].filefloderInfo.name;
                    if (isDir == true) {
                        ulNode += "<li file-url='" + datasChildren[k].filefloderInfo.path + "' data-jstree="
                            + "{ 'opened' : false }"
                            + ">"
                            + dirName
                            + "<ul>";
                        var datasChildrenA = datasChildren[k].childFiles;
                        findAllChildFile(datasChildrenA);
                        ulNode += '</ul></li>';
                    }
                    else if (modelID != 139) {
                        ulNode += "<li file-url='" + datasChildren[k].filefloderInfo.path + "' data-jstree="
                            + "{ 'type' : 'file' }"
                            + ">"
                            + dirName
                            + "</li>";
                    }
                }
            }
        }
        console.log(ulNode);
        $("#file-choice").html(ulNode);
        $("#jiazai").hide();
        handleSample1();
        //treeBtnClickInit();
    });
}

function createFileLabelToHtml(file, $parentLabel, $ul) {
    var childFiles = file.childFiles;
    for (var i = 0; i < childFiles.length; i++) {
        levelArr.push(childFiles[i].level);
    }
//       $parentLabel.append($ul.append(new FileLabel(file.filefloderInfo).create()));

    jQuery.each(childFiles, function (i, file) {
        createFileLabelToHtml(file);

    });
    levelArr.sort();
}

//单选按钮选择的值
var singleConversion = true;
//下拉框选择的值
var selectValueType = "";
var radioStateVal = true;
var selectValByUnDispersion = "";
//广义线性回归的连接函数分布族下拉选择的值
var selectDisFunctionFamilyVal = ""

//拖拽生成配置模块
function configModel(trainedModelId, id, modelId, html, tem, bgClass, name) {
    $("#workSpaceExImg").css("display", "none");
    var data = {};
    data.modelId = modelId;
    data.trainedModelId = trainedModelId;
    console.log(modelId);
    console.log(data);
    $.ajax({
        url: basePath + "/drag/modelattri/get.do",
        type: 'POST',
        data: data,
        // data:"modelId=" + modelId,
        async: false,
        // cache: false,
        // contentType: false,
        // processData: false,
        success: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
            var returndata = JSON.parse(data);
            console.log("===============" + returndata.msg);
            console.log(returndata);
            var databaseJson = returndata.msg;
            var databaseInfo = $.parseJSON(databaseJson);
            var connNames = databaseInfo.connNames;
            // var tableNames = databaseInfo.tables;
            /*switch (returndata.code){
             case 417:toastr.error(returndata.msg) ;
             close();
             break;
             case 200:toastr.success(returndata.msg);
             close();

             //window.location.reload();
             break;
             }*/
            if (returndata.code == 200) {
                var configData = [];
                var configInfo = $("#" + tem).siblings(".modelInfo").val();
                var modelID = $("#" + tem).val();
                $('body').addClass('page-quick-sidebar-open');
                // $("#sp").text(html + "参数配置");
                $("#sp").text(html);
                $("#modelInfo").html(configInfo);
                $("#formBody").html("");
                $("#formBody").attr("name", id);
                $("#formBody").attr("data-modelID", modelID);

                var nodename = name;
                if (nodename == "transfer") {
                    var url = "";
                    $("#notesDes").html("");
                } else {
                    var url = basePath + "/drag/dataModel/notesDetails.do?" + "modelID=" + modelID;
                    $("#notesDes").html("<p data-url='" + url + "' data-modelId='" + modelID + "'><u>查看算子详细说明</u></p>")
                }

                var options = "";
                // var optionsTables = "";
                // var splits = null;
                // var splitTables = null;
                if (connNames != null && connNames != "" && connNames != "null") {
                    splits = connNames.split(",");
                    for (var i = 0; i < splits.length; i++) {
                        var tableName = splits[i];
                        if (tableName.length > 11) {
                            tableName = tableName.substring(0, 11)
                        }
                        options += '<option value="' + splits[i] + '" title="' + splits[i] + '">' + tableName + '</option>';
                    }
                }

                // if(tableNames != null && tableNames != "" && tableNames != "null"){
                //     splitTables = tableNames.split(",");
                //     for(var i = 0; i < splitTables.length; i++){
                //         optionsTables += '<option value="'+splitTables[i]+'">'+ splitTables[i] +'</option>';
                //     }
                // }

                // console.log("+++++++++" + options);

                for (var i = 0; i < returndata.obj.length; i++) {
                    var configModelId = returndata.obj[i].modelId;
                    var configType = returndata.obj[i].type;
                    var configVal = returndata.obj[i].mvalue;
                    var configId = returndata.obj[i].id;
                    var configName = returndata.obj[i].mattribute;
                    var configCommon = returndata.obj[i].common;

                    // if(configModelId == modelId && configModelId == 55){
                    //     if(configName == "inputPath"){
                    //         configData.push({
                    //             configVal: configVal,
                    //             configName: configName
                    //         })
                    //     }
                    // }

                    if (configModelId == modelId && configType == 0) {
                        var str = '<div class="form-group formBox formBoxHidden"><label class="control-label col-md-12 config-label">' +
                            configName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<input type="text" data-required="1" class="form-control" id="config' + configId + '" name="config' + configId + '" value="' + configVal + '" disabled="disabled"/></div></div>';
                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 1) {
                        var showConfigName;
                        if (configName == "sampleSize") {
                            showConfigName = configName + "_" + "Num";
                        } else if (configName == "splitValue") {
                            showConfigName = configName + "_" + "Percent";
                        } else {
                            showConfigName = configName;
                        }
                        var ChineseConfigName = getChineseConfigName(showConfigName);
                        var configText;
                        //替换显示的null;
                        // alert(configVal);
                        if (configVal == null) {
                            var configText = "";
                        } else {
                            configText = configVal;
                        }
                        if (configName == "splitValue") {
                            configText = configVal.split(",")[0];
                        }

                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<input type="text" data-required="1" class="form-control" id="config' + configId + '" value="' + configText + '" /></div></div>';
                        $("#formBody").append(str);

                        if (configName == "featureSubsetStrategyN") {
                            $('#config_featureSubsetStrategyN').css('display', 'none');
                        }

                        if (configModelId == 77 || configModelId == 79) {
                            $("#config" + configId).addClass("unWrite");
                        }
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 2) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var selectConfigVal = configVal.split(",");
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<select class="form-control filterChoose" id="config' + configId + '" name="select">';
                        var ChineseSelectConfigVal
                        for (var k = 0; k < selectConfigVal.length; k++) {
                            if (configName == "sampleType" || configName == "splitType") {
                                ChineseSelectConfigVal = getSelectConfigValForChi(configName + "|" + selectConfigVal[k]);
                                str += '<option value="' + selectConfigVal[k] + '">' + ChineseSelectConfigVal + '</option>';
                                // }else if(configName == "splitType"){
                                //     ChineseSelectConfigVal = getSelectConfigValForChi(configName + "|" + selectConfigVal[k]);
                                //     str += '<option value="' + selectConfigVal[k] + '">' + ChineseSelectConfigVal + '</option>';
                            } else if (configName == "mergeType") {
                                ChineseSelectConfigVal = getSelectConfigValForChi(configName + "|" + selectConfigVal[k]);
                                str += '<option value="' + selectConfigVal[k] + '">' + ChineseSelectConfigVal + '</option>';
                            } else if (configName == "discretionType") {
                                var ChineseSelectVal = getSelectValForChinese(selectConfigVal[k]);
                                str += '<option value="' + selectConfigVal[k] + '">' + ChineseSelectVal + '</option>';
                            } else {
                                str += '<option value="' + selectConfigVal[k] + '">' + selectConfigVal[k] + '</option>';
                            }
                        }
                        str += '</select></div></div>';
                        $("#formBody").append(str);
                        var selectValSplitDefault = configVal.split(",")[0];
                        selectValueType = selectValSplitDefault;
                        selectValByUnDispersion = selectValSplitDefault;
                        if (configName == "disFunctionFamily") {
                            selectDisFunctionFamilyVal = selectValSplitDefault;
                        }
                        var selectedVal;
                        selectedVal = $("#config" + configId).children("option:selected").val();
                        $("#config" + configId).change(function () {
                            selectedVal = $(this).children("option:selected").val();
                            configData.push({
                                configId: "config" + configId,
                                configVal: configVal,
                                configSelectVal: selectedVal,
                                configType: configType,
                                configName: configName,
                                configCommon: configCommon
                            });
                        });
                        console.log(selectedVal);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configSelectVal: selectedVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 3) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="input-group">' +
                            '<input type="text" class="form-control" id="config' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
                            '<span class="input-group-btn">' +
                            '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="1"><i class="icon-doc"></i></a></span>' +
                            '</div></div></div>';
                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 5) {
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            configName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="input-group">' +
                            '<input type="text" class="form-control" id="config' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
                            '<span class="input-group-btn">' +
                            '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="2"><i class="icon-doc"></i></a></span>' +
                            '</div></div></div>';
                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }

                    if (configModelId == modelId && configType == 7) {
                        showQueryFlag = "hide";
                        if (configName == 'sqlStatment') {
                            configVal = configVal + "|" + showQueryFlag;
                            var text = configVal.split("|")[0];
                            // alert(text)
                            if (text == "null") {
                                text = "";
                            }
                            var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                                "SQL语句" +
                                '<span class="required"></span>' + returnParameterMsg(configCommon) + '</label>' +
                                '<div class="col-md-12">' +
                                '<div class="input-group">' +
                                '<button class="form-control btn btn-sql" type="button" onclick="showQueryBox()">建立SQL查询</button></div>' +
                                '<div class="queryBox" style="display: none"><div class="input-group col-md-12">' +
                                '<textarea name="textarea" class="form-control col-md-12 textarea-sql" placeholder="请书写sql语句" data-required="1" class="form-control" id="config' + configId + '" value="' + configVal + '">' + text + '</textarea></div>' +
                                //     '<div class="input-group">' +
                                //     '<button class="btn">确认</button>&nbsp;&nbsp;<button class="btn">取消</button></div></div>'
                                // '</div>' +
                                '</div></div>' +
                                '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="sql-Check"></label></div>';
                            $("#formBody").append(str);
                            if (configVal.indexOf("\\|")) {
                                if ("show" == configVal.split("|")[1]) {
                                    showQueryBox();
                                }
                            }
                        }
                        // alert("push前的：" + configId)
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }

                    if (configModelId == modelId && configType == 6) {
                        // console.log("++++++++" + connNames);
                        if (configName == 'dbLinkName') {
                            var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                                "连接名" +
                                '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                                '<div class="input-group col-md-12">' +
                                '<div class="col-md-9" style="padding: 0 5px 0 15px;">' +
                                '<select  name="select" onchange="dbLinkNameChange($(this))" class="form-control form-dbLinkName" id="config' + configId + '" data-required="1" >' +
                                // '<option value="'+configVal+'">'+configVal+'</option>'+
                                options +
                                '</select></div>' +
                                '<div class="col-md-2" style="padding: 0"><span class="data-link-btn input-group-btn">' +
                                '<a data-toggle="modal" class="btn btn-default btnUrl btnTreeModal createLink" onclick="createNewLink()"  name="btnUrl" type="button" value="2"><span class="icon iconfont icon-jia4"></span></a></span>' +
                                // '<li data-toggle="modal" class="createLink">新建连接</li>' +
                                '</div></div></div>';
                            $("#formBody").append(str);
                            // configVal = connNames;
                            /**
                             *用户拖动后不选择连接名的情况
                             * @type {string}
                             */
                            $("#config" + configId).val("");
                            configVal = connNames + "|" + $("#config" + configId).val();
                        }
                        if (configName == 'tableName') {
                            var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                                "表名" +
                                '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                                '<div class="input-group col-md-12">' +
                                '<div class="col-md-12">' +
                                '<select  name="select" class="form-control" id="config' + configId + '" data-required="1" >' +
                                // optionsTables +
                                '</select>' +
                                '</div></div>' +
                                '</div>';
                            $("#formBody").append(str);
                            configVal = "" + "|" + $("#config" + configId).val();
                        }

                        if (configName == 'writeMode') {
                            // alert(configVal);
                            var op1 = configVal.split(',')[0];
                            var op2 = configVal.split(',')[1];
                            var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                                "写入模式" +
                                '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                                '<div class="input-group col-md-12">' +
                                '<div class="col-md-12">' +
                                '<select  name="select" class="form-control col-md-12" id="config' + configId + '" data-required="1" >' +
                                '<option value="' + op1 + '">' + "追加" + '</option>' +
                                '<option value="' + op2 + '">' + "覆盖" + '</option>' +
                                '</select>' +
                                '</div></div>' +
                                '</div>' +
                                '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="writeMode-instruction"></label>' +
                                '</div>';
                            $("#formBody").append(str);
                            //用户不点击overWrite模式的时候。
                            configVal = configVal + "|" + $("#config" + configId).val();
                        }

                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 4) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required" aria-required="true"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="mt-radio-list" id="config' + configId + '" data-error-container="#form_2_membership_error">';
                        if (configName == "select") {
                            if (configVal == "true") {
                                str += '<label class="mt-radio">' +
                                    '<input type="radio" name="singleConversion" value="true" checked="true"> 单目运算' +
                                    '<span></span></label>' +
                                    '<label class="mt-radio">' +
                                    '<input type="radio" name="singleConversion" value="false"> 双目运算' +
                                    '<span></span></label>';
                                singleConversion = true;
                            }
                            else {
                                str += '<label class="mt-radio">' +
                                    '<input type="radio" name="singleConversion" value="true"> 单目运算' +
                                    '<span></span></label>' +
                                    '<label class="mt-radio">' +
                                    '<input type="radio" name="singleConversion" value="false" checked="true"> 双目运算' +
                                    '<span></span></label>';
                                singleConversion = false;
                            }
                        } else {
                            if (configVal == "true") {
                                str += '<label class="mt-radio">' +
                                    '<input type="radio" name="membership" value="true" checked="true"> true' +
                                    '<span></span></label>' +
                                    '<label class="mt-radio">' +
                                    '<input type="radio" name="membership" value="false"> false' +
                                    '<span></span></label>';
                                radioStateVal = true;
                            }
                            else {
                                str += '<label class="mt-radio">' +
                                    '<input type="radio" name="membership" value="true"> true' +
                                    '<span></span></label>' +
                                    '<label class="mt-radio">' +
                                    '<input type="radio" name="membership" value="false" checked="true"> false' +
                                    '<span></span></label>';
                                radioStateVal = false;
                            }
                        }

                        str += '</div><div id="form_2_membership_error"> </div>' +
                            '</div></div>';


                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    if (configModelId == modelId && configType == 9) {
                        var str = "";
                        if (configModelId == 83) {
                            configVal = $("#pythonText").text();
                            str = '<div class="form-group formBox">' +
                                '<div class="col-md-12">' +
                                '<a  class="editPyStyle" type="button"  >编辑python脚本</a>' +
                                '<textarea style="display: none" data-required="1" class="form-control" class="pythonInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
                                // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
                                '</div>';
                        } else {
                            configVal = $("#editorR").text();
                            str = '<div class="form-group formBox">' +
                                '<div class="col-md-12">' +
                                '<a  class="editRStyle" type="button"  >编辑R脚本</a>' +
                                '<textarea style="display: none" data-required="1" class="form-control" class="rInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
                                // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
                                '</div>';
                        }
                        $("#formBody").append(str);
                        ///$("#pyRTitle").html(boxName)
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName
                        });
                    }

                    //选择列
                    if (configModelId == modelId && configType == 8) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
                            '<div class="choice-column tag-already-' + configId + '"></div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="choiceColumnBtn btn btn-default bg-color"  onclick="choiceColumn($(this))" dataVal="' + configId + '">选择列</button>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        addScrollBar();
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon,
                            configDataSource: "",
                            configSelectedDataSource: "",
                            configDataType: "",
                            configSelectedDataType: "",
                            configPreparaedScheme: "",
                            configSerachName: "",
                            configStatModelToTypeOption: "",
                            configSelectNameOrRuleVal: "true",
                            configSelectRuleJson: ""
                        });
                    }
                    //选择标签列
                    if (configModelId == modelId && configType == 11) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var clickButtonName;
                        if (modelID == 116) {
                            clickButtonName = "选择拆分列";
                        } else {
                            clickButtonName = "选择标签列";
                        }
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
                            '<div class="choice-column tag-already-single' + configId + '"></div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="btn btn-default bg-color"  onclick="choiceSingleColumn($(this))">' + clickButtonName + '</button>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon,
                            configDataSource: "",
                            configSelectedDataSource: "",
                            configPreparaedScheme: ""
                        });
                    }
                    // 提取列新增
                    if (configModelId == modelId && configType == 10) {

                        var ChineseConfigName = getChineseConfigName(configName);
                        var noneValues = configVal.split("|")[0].split(",");//默认的checkbox
                        var checkedValue = configVal.split("|")[1];//选择的checkBox
                        var checkStr = "";
                        for (var j = 0; j < noneValues.length; j++) {

                            if (noneValues[j] == "\\s") {
                                checkStr += '<label class="checkbox_margin"><input name="checkbox" data-val="空字符" type="checkbox" value=" "  />空字符 </label>';
                            } else {
                                checkStr += '<label class="checkbox_margin"><input name="checkbox" data-val="' + noneValues[j] + '" type="checkbox" value=" "  />' + noneValues[j] + ' </label>';
                            }


                        }
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="mt-checkbox-list" id="' + configId + '" configVal="' + configVal + '" data-error-container="#form_2_membership_error">' + checkStr +
                            '</div></div>' +
                            '</div>';

                        $("#formBody").append(str);
                        if (checkedValue == "unchecked") {

                        } else {
                            checkedValue = checkedValue.split(",");
                            for (var j = 0; j < noneValues.length; j++) {
                                for (var m = 0; m < checkedValue.length; m++) {
                                    if (checkedValue[m] == noneValues[j]) {
                                        if (checkedValue[m] == "\\s") {
                                            checkedValue[m] = "空字符"
                                        }
                                        $("#formBody").find("input[data-val=" + checkedValue[m] + "]").prop("checked", true);
                                    }

                                }

                            }
                        }


                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    //下拉弹框，如果选择自定义选项，会出现输入框
                    if (configModelId == modelId && configType == 12) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var selectConfigVal = configVal.split(",");
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<select class="selectOptionVal form-control" id="config' + configId + '" name="select1">';
                        var selectedVal;
                        var labelName = "";
                        for (var k = 0; k < selectConfigVal.length; k++) {
                            selectedVal = selectConfigVal[0];
                            if (configName == "elasticNetParam" || configName == "regularizers") {
                                if (selectConfigVal[k] == "defineVal") {
                                    labelName = "Elastic Net（介于0到1之间）";
                                    str += '<option value="elasticNet" data-val="' + configId + '" >Elastic Net</option>';
                                } else {
                                    str += '<option value="' + selectConfigVal[k] + '" >' + selectConfigVal[k] + '</option>';
                                }
                            } else if (configName == "fillValue" || configName == "fillNone") {
                                if (selectConfigVal[k] == "defineVal") {
                                    labelName = "值";
                                    str += '<option value="elasticNet" data-val="' + configId + '" >自定义</option>';
                                } else {
                                    var ChineseSelectVal = getSelectValForChinese(selectConfigVal[k]);
                                    str += '<option value="' + selectConfigVal[k] + '" >' + ChineseSelectVal + '</option>';
                                }
                            } else {
                                str += '<option value="' + selectConfigVal[k] + '">' + selectConfigVal[k] + '</option>';
                            }

                        }
                        str += '</select></div></div><div class="displayDiv form-group formBox"><label class=" control-label col-md-12 config-label" >' + labelName +
                            '<span class="required"> * </span></label>' +
                            '<div class=" col-md-12">' +
                            '<input type="text" data-required="1" class="form-control" name="elasticNet"  dataValue="' + configId + '" /></div></div>';
                        $("#formBody").append(str);
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configSelectVal: selectedVal,
                            configSelectLabelVal: "",
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }

                    // 类型转换
                    if (configModelId == modelId && configType == 13) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var listStr = "";
                        if (configVal != null) {
                            var listAllName = JSON.parse(configVal);
                            var newType;
                            for (var q = 0; q < listAllName.listName.split(",").length; q++) {
                                if (listAllName.newType.split(",")[q] == "1") {
                                    newType = "string";
                                } else if (listAllName.newType.split(",")[q] == "0") {
                                    newType = "int"
                                }
                                listStr += '<p><span>' + listAllName.listName.split(",")[q] + '</span><br/><span>' + newType + '</span></p>'
                            }
                        }
                        var str = '<div class="operator-group">' +
                            '<div class="form-group formBox">' +
                            '<label class="control-label col-md-12 config-label">' + ChineseConfigName + '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="typeChange choice-column" id="config' + configId + '">' + listStr + '</div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="btn btn-default bg-color" id="choiceTransform">选择列</button>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        addScrollBar();
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                    //添加分组
                    if (configModelId == modelId && configType == 14) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +

                            '<div class="choice-column " id="group_' + configId + '"></div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="btn btn-default bg-color" id="addGroupBtn" dataVal="' + configId + '" dataBlockId="' + configModelId + '">添加分组</button>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        addScrollBar();
                        var fieldOptionStr = ""
                        if (configVal != null && configVal != undefined) {
                            var textArray = configVal.split(',');
                            for (var n = 0; n < textArray.length - 1; n++) {
                                var sort = n + 1
                                fieldOptionStr += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n] + '-' + textArray[n + 1] + '</p>'
                            }
                            $('#formBody #' + configId + ' .mCSB_container').html(fieldOptionStr);
                            $("#groupDiscreteInterval").val(configVal);
                        }
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }

                    // 列名修改
                    if (configModelId == modelId && configType == 15) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var listStr = "";
                        if (configVal != null) {
                            var listAllName = JSON.parse(configVal);
                            var newType;
                            for (var q = 0; q < listAllName.oldName.split(",").length; q++) {
                                listStr += '<p><span>' + listAllName.oldName.split(",")[q] + '</span><br/><span>' + listAllName.newName.split(",")[q] + '</span></p>'
                            }
                        }
                        var str = '<div class="operator-group">' +
                            '<div class="form-group formBox">' +
                            '<label class="control-label col-md-12 config-label">' + ChineseConfigName + '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="typeChange choice-column" id="config' + configId + '">' + listStr + '</div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="btn btn-default bg-color" id="choiceRevise">选择列</button>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        addScrollBar();
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }

                    // 列名修改end

                    // 二类分组单个阈值
                    if (configModelId == modelId && configType == 16) {
                        var ChineseConfigName = getChineseConfigName(configName);
                        var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                            ChineseConfigName +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +

                            '<div class="choice-column " id="group_' + configId + '"></div>' +
                            '</div>' +
                            '<div class="col-md-12">' +
                            '<button type="button" class="btn btn-default bg-color" id="addThresGroup" dataVal="' + configId + '" dataBlockId="' + configModelId + '">添加分组</button>' +
                            '</div>' +
                            '</div>';
                        $("#formBody").append(str);
                        addScrollBar();
                        var fieldOptionStr = ""
                        if (configVal != null && configVal != undefined) {
                            var textArray = configVal.split(',');
                            for (var n = 0; n < textArray.length; n++) {
                                var sort = n + 1
                                fieldOptionStr += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n]  + '</p>'
                            }
                            $('#formBody #' + configId + ' .mCSB_container').html(fieldOptionStr);
                            $("#groupDiscreteInterval").val(configVal);
                        }
                        configData.push({
                            configId: "config" + configId,
                            configVal: configVal,
                            configType: configType,
                            configName: configName,
                            configCommon: configCommon
                        });
                    }
                }
                configObj.push({
                    BlockName: html,
                    BlockBg: bgClass,
                    BlockId: id,
                    data: configData,
                    id: modelId,
                    trainedModelId: trainedModelId
                });
                if (modelId == "125") {
                    str = '<div class="operator-group" id="addNewFilterBox">' +
                        '<div class="form-group formBox">' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color" id="addNewFilter">添加新过滤</button>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                }
                console.log(configObj)
            }
            switchToHide();
            singleConversionMethod();
            editConfigModel();
            editUrlConfigModel();
            filterInit();
            var h = configObj.length;
            // for(var h=0;h<configObj.length;h++){
            if (configObj[h - 1].id == 125) {
                // configObj[h].data
                var m = [];
                m.push(configObj[h - 1].data);
                configObj[h - 1].data = m;
            }
            // }
            console.log("gggggggggggggggg" + configObj);
        },
        error: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
        complete: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
    });
}

//返回参数类型的参数信息
function returnParameterMsg(configCommon) {
    var msg = '<span class="questionIcon"> <i class="icon iconfont icon-wenti2"></i></span><div class="questionCommonDiv"><div class="questionCommon">' + configCommon + '</div><s><i></i></s></div>';
    return msg;
}

//点击模块切换配置信息
function clickConfigModel() {
    $("#canvasBody").click(function (event) {
        // $('body').removeClass('page-quick-sidebar-open');
        event.stopPropagation();
        $(".page-quick-sidebar-wrapper").css({"z-index": -1})
    });
    $("#canvasBlock").on("click", ".box", function () {
        $(".page-quick-sidebar-wrapper").css("z-index", "2");
        $(".page-console").css("z-index", "1");
        var boxName = $(this).find("span.modelName").text();
        var modelInterface = $(this).nextUntil(".box");
        var modelList = $(".operatorBlock").find("h3.ui-draggable");
        for (var j = 0; j < modelList.length; j++) {
            if ($(modelList[j]).text() == boxName) {
                var configInfo = $(modelList[j]).siblings(".modelInfo").val();
                var modelID = $(modelList[j]).next().val();
            }
        }
        $("#modelInfo").html(configInfo);
        $(this).css("box-shadow", "2px 2px 19px #aaa");//选中效果
        $(this).siblings(".box").css("box-shadow", "2px 2px 19px rgba(9,84,109,0.1)");//选中效果
        $(this).css("z-index", "9");//选中层级提高
        modelInterface.css("z-index", "10");//选中层级提高
        $(this).siblings(".box").css("z-index", "0");
        $(".jtk-endpoint").not(modelInterface).css("z-index", "1");
        console.log(configObj);
        $('body').addClass('page-quick-sidebar-open');
        // $("#sp").text(boxName + "参数配置");
        $("#sp").text(boxName);
        $("#formBody").html("");
        var blockId = $(this).attr("id");
        $("#formBody").attr("name", blockId);
        $("#formBody").attr("data-modelID", modelID);
        var sampleVal = "";
        var splitVal = "";
        var nodename = $(this).attr("data-nodename");
        if (nodename == "transfer") {
            var url = "";
            $("#notesDes").html("");
        } else {
            var url = basePath + "/drag/dataModel/notesDetails.do?" + "modelID=" + modelID;
            $("#notesDes").html("<p data-url='" + url + "' data-modelId='" + modelID + "'><u>查看算子详细说明</u></p>")
        }

        /**
         * 每次点击模型的时候，从数据库动态获取连接名展示在select框中，
         */
        // alert(1111)
        // for (var i = 0; i < configObj.length; i++) {
        //     if (blockId == configObj[i].BlockId) {
        //         var configModalId=configObj[i].id;
        //         // 循环渲染之前将json整合
        //         if(configModalId == "125"){
        //             configObj[i].data=conformity_arr(configObj[i].data);
        //         }
        //         for (var k = 0; k < configObj[i].data.length; k++) {
        //             var configType = configObj[i].data[k].configType;
        //             var configVal = configObj[i].data[k].configVal;
        //             var configId = configObj[i].data[k].configId;
        //             var configName = configObj[i].data[k].configName;
        //             var configCommon = configObj[i].data[k].configCommon;
        //             console.log(configObj[i].data.length);
        //             if (configType == 0) {
        //                 var str = '<div class="form-group formBox formBoxHidden"><label class="control-label col-md-12 config-label">' +
        //                     configName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<input type="text" data-required="1" class="form-control" id="' + configId + '" value="' + configVal + '" disabled="disabled"/></div></div>';
        //                 $("#formBody").append(str);
        //             }
        //             if (configType == 1) {
        //                 var showVal = "";
        //                 if (configVal == null) {
        //                     showVal = "";
        //                 } else {
        //                     showVal = configVal;
        //                 }
        //                 var showConfigName
        //                 if (configName == "sampleSize") {
        //                     showConfigName = sampleVal
        //                 }else if(configName == "splitValue"){
        //                     showConfigName = splitVal
        //                 } else {
        //                     showConfigName = configName;
        //                 }
        //
        //                 if(configName == "splitValue"){
        //                     var select = $("#formBody").children().find("select");
        //                     var checkedVal = select.val();
        //                     // var checkedVal = $(this).parents("#formBody").children().eq(1).find("select").val();
        //                     if(checkedVal == 0){
        //                         showVal = configVal.split(",")[0];
        //                     }else{
        //                         showVal = configVal.split(",")[1];
        //                     }
        //                 }
        //
        //                 ChineseConfigName = getChineseConfigName(showConfigName);
        //                 var str = '<div class="form-group formBox" id="config_'+configName+'"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<input type="text" data-required="1" class="form-control" id="' + configId + '" value="' + showVal + '" /></div></div>';
        //                 $("#formBody").append(str);
        //
        //                 if(configName == 'featureSubsetStrategyN'){
        //                     for(var p=0;p<configObj.length;p++){
        //                         for(var q=0;q<configObj[p].data.length;q++){
        //                             if(configObj[p].data[q].configName == "featureSubsetStrategy"){
        //                                 var configVal = configObj[p].data[q].configSelectVal;
        //                                 if(configVal == "n"){
        //                                     $("#config_featureSubsetStrategyN").css('display','block');
        //                                 }else{
        //                                     $("#config_featureSubsetStrategyN").css('display','none');
        //                                 }
        //                             }
        //                         }
        //                     }
        //                 }
        //
        //                 if(configModalId == 77 || configModalId == 79){
        //                     $("#"+configId).addClass("unWrite");
        //                 }
        //             }
        //             if (configType == 2) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var configSelectVal = configObj[i].data[k].configSelectVal
        //                 var sampleTypeForChin;
        //                 var splitTypeForChin;
        //                 if (configName == "sampleType") {
        //                     if (configSelectVal == "1") {
        //                         sampleVal = "sampleSize_Num"
        //                         sampleTypeForChin = getChineseConfigName("sampleSize_Num")
        //                         $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
        //                     } else if (configSelectVal == "0") {
        //                         sampleVal = "sampleSize_Percent"
        //                         sampleTypeForChin = getChineseConfigName("sampleSize_Percent")
        //                         $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
        //                     }
        //                 }
        //                 if(configName == "splitType"){
        //                     if(configSelectVal == "0"){
        //                         splitVal = "splitValue_Percent"
        //                         splitTypeForChin = getChineseConfigName("splitValue_Percent")
        //                         $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
        //                     }else if(configSelectVal == "1"){
        //                         splitVal = "splitValue_Num"
        //                         splitTypeForChin = getChineseConfigName("splitValue_Num")
        //                         $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
        //                     }
        //                 }
        //                 var selectConfigVal = configVal.split(",");
        //                 var str = '<div class="form-group formBox"  id="config_'+configName+'"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<select class="form-control filterChoose" id="' + configId + '" name="select">';
        //                 for (var j = 0; j < selectConfigVal.length; j++) {
        //                     if (configName == "sampleType" ||  configName == "splitType") {
        //                         var ChineseSelectConfigVal = getSelectConfigValForChi(configName + "|" + selectConfigVal[j]);
        //                         str += '<option value="' + selectConfigVal[j] + '">' + ChineseSelectConfigVal + '</option>';
        //                     }else if(configName == "featrueSelectionMethod" || configName == "mergeType" || configName == "discretionType"){
        //                         var ChineseSelectVal = getSelectValForChinese(selectConfigVal[j]);
        //                         str += '<option value="' + selectConfigVal[j] + '">' + ChineseSelectVal + '</option>';
        //                     } else {
        //                         str += '<option value="' + selectConfigVal[j] + '">' + selectConfigVal[j] + '</option>';
        //                     }
        //                 }
        //                 str += '</select></div></div>';
        //                 $("#formBody").append(str);
        //                 selectValueType=configSelectVal;
        //                 selectValByUnDispersion=configSelectVal;
        //                 if(configName == "disFunctionFamily"){
        //                     selectDisFunctionFamilyVal=configSelectVal;
        //                 }
        //                 $("#" + configId).val(configSelectVal);
        //             }
        //             if (configType == 3) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 console.log(configVal)
        //                 if (configVal != undefined && configVal != "") {
        //                     configVal = "/" + configVal.split("/")[configVal.split("/").length - 2] + "/" + configVal.split("/")[configVal.split("/").length - 1];
        //                 } else {
        //                     configVal = "";
        //                 }
        //                 var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<div class="input-group">' +
        //                     '<input type="text" class="form-control" id="' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
        //                     '<span class="input-group-btn">' +
        //                     '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="1"><i class="icon-doc"></i></a></span>' +
        //                     '</div></div></div>';
        //                 $("#formBody").append(str);
        //             }
        //             if (configType == 5) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 if (configVal != undefined && configVal != "") {
        //                     configVal = "/" + configVal.split("/")[configVal.split("/").length - 2] + "/" + configVal.split("/")[configVal.split("/").length - 1];
        //                 }
        //                 var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<div class="input-group">' +
        //                     '<input type="text" class="form-control" id="' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
        //                     '<span class="input-group-btn">' +
        //                     '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="2"><i class="icon-doc"></i></a></span>' +
        //                     '</div></div></div>';
        //                 $("#formBody").append(str);
        //             }
        //
        //             if (configType == 6) {
        //                 if (configName == 'dbLinkName') {
        //                     $.ajax({
        //                         url: basePath + "/drag/modelattri/LoadEnableConnNames.do",
        //                         type: 'GET',
        //                         async: false,
        //                         contentType: "text",
        //                         success: function (data) {
        //                             var selectedConnName = configVal.split("|")[1];
        //                             configVal = data + "|" + selectedConnName;
        //                         },
        //                         error:function(data){
        //                             if(data == "AjaxSessionTimeout"){
        //                                 window.location.href=basePath;
        //                                 return;
        //                             }
        //                         },
        //                         complete:function(data){
        //                             if(data == "AjaxSessionTimeout"){
        //                                 window.location.href=basePath;
        //                                 return;
        //                             }
        //                         },
        //                     })
        //                     var splits = configVal.split("|");
        //                     var connNames = splits[0];
        //                     var selectedValue = splits[1];
        //                     // alert(selectedValue);
        //                     var optionValues = connNames.split(",");
        //                     var options = "";
        //                     for (var m = 0; m < optionValues.length; m++) {
        //                         //当用户Id没有连接名的时候，后台返回的数据可能为"",需要排除数据为""的情况。
        //                         if ("" != optionValues[m]) {
        //                             var tableName = optionValues[m];
        //                             if (optionValues[m].length > 11) {
        //                                 tableName = tableName.substring(0, 11)
        //                             }
        //                             options += '<option value="' + optionValues[m] + '" title="' + optionValues[m] + '">' + tableName + '</option>';
        //                         }
        //                     }
        //                     var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                         "连接名" +
        //                         '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                         '<div class="input-group col-md-12">' +
        //                         '<div class="col-md-9" style="padding: 0 5px 0 15px;">' +
        //                         '<select  name="select" class="form-control form-dbLinkName" onchange="dbLinkNameChange($(this))" id="' + configId + '" data-required="1" >' +
        //                         // '<option value="'+configVal+'">'+configVal+'</option>'+
        //                         options +
        //                         '</select></div>' +
        //                         '<div class="col-md-2" style="padding: 0"><span class="data-link-btn input-group-btn">' +
        //                         // '<a href="#dataLink" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="2"><i>新建连接</i></a></span>'
        //                         '<a data-toggle="modal" class="btn btn-default btnUrl btnTreeModal createLink" onclick="createNewLink()" name="btnUrl" type="button" value="2"><span class="icon iconfont icon-jia4"></span></a></span>'
        //                     // '<li data-toggle="modal" class="createLink">新建连接</li>' +
        //                     '</div></div></div>';
        //                     // alert(configId);
        //                     $("#formBody").append(str);
        //                     $("#" + configId).val(selectedValue);
        //                 }
        //                 if (configName == 'tableName') {
        //                     var tableSelected = configVal.split("|")[1];
        //                     // alert()
        //                     var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                         "表名" +
        //                         '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                         '<div class="input-group col-md-12">' +
        //                         '<div class="col-md-12">' +
        //                         '<select name="select" class="form-control" id="' + configId + '" data-required="1" >' +
        //                         '</select>' +
        //                         '</div></div>' +
        //                         '</div>';
        //                     $("#formBody").append(str);
        //                     var tbSelected = $("#" + configId);
        //                     var dbLinkNameSelect = tbSelected.parent().parent().parent().prev().find(".form-control");
        //                     if (dbLinkNameSelect.length > 0) {
        //                         var linkName = dbLinkNameSelect.val();
        //                         // alert(linkName)
        //                         $.ajax({
        //                             url: basePath + "/drag/modelattri/loadTables.do",
        //                             type: "POST",
        //                             dataType: "json",
        //                             data: "connName=" + linkName,
        //                             success: function (data) {
        //                                 var tablesContainsTbselected = false;
        //                                 var options = "";
        //                                 $.each(data, function (index, value) {
        //                                     options += '<option value="' + value + '">' + value + '</option>';
        //                                     if (value == tableSelected) {
        //                                         tablesContainsTbselected = true;
        //                                     }
        //                                 })
        //                                 if (!tablesContainsTbselected) {
        //                                     tableSelected = data[0];
        //                                 }
        //                                 tbSelected.append(options);
        //                                 tbSelected.val(tableSelected);
        //                             },
        //                             error:function(data){
        //                                 if(data.responseText == "AjaxSessionTimeout"){
        //                                     window.location.href=basePath;
        //                                     return;
        //                                 }
        //                             }
        //                         })
        //                     }
        //                 }
        //
        //                 if (configName == 'writeMode') {
        //                     console.log(configVal);
        //                     var op1 = "";
        //                     var op2 = "";
        //                     var ModesSelected = "";
        //                     if (configVal.indexOf("|")) {
        //                         var writeModes = configVal.split("|")[0];
        //                         ModesSelected = configVal.split("|")[1];
        //                         op1 = writeModes.split(",")[0];
        //                         op2 = writeModes.split(",")[1];
        //                     } else {
        //                         op1 = configVal.split(',')[0];
        //                         op2 = configVal.split(',')[1];
        //                     }
        //                     var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                         "写入模式" +
        //                         '<span class="required"> * </span></label>' +
        //                         '<div class="input-group col-md-12">' +
        //                         '<div class="col-md-12">' +
        //                         '<select name="select" class="form-control col-md-12" id="' + configId + '" data-required="1" >' +
        //                         '<option value="' + op1 + '">' + "追加" + '</option>' +
        //                         '<option value="' + op2 + '">' + "覆盖" + '</option>' +
        //                         '</select>' +
        //                         '</div></div>' +
        //                         '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="writeMode-instruction"></label>' +
        //                         '</div>';
        //                     '</div>';
        //                     $("#formBody").append(str);
        //                     $("#" + configId).val(ModesSelected);
        //                     if (ModesSelected == "overwrite") {
        //                         $("#writeMode-instruction").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>警告，覆盖模式将清空写入表格所有数据！");
        //                         $("#writeMode-notice").html(" * ");
        //                     } else if (ModesSelected == "append") {
        //                         $("#writeMode-instruction").html("");
        //                     }
        //                 }
        //
        //             }
        //
        //             if (configType == 7) {
        //                 if (configName == 'sqlStatment') {
        //                     var text = configVal.split("|")[0];
        //                     if (text == "null") {
        //                         text = "";
        //                     }
        //                     var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                         "SQL语句" +
        //                         '<span class="required"></span>'+returnParameterMsg(configCommon)+'</label>' +
        //                         '<div class="col-md-12">' +
        //                         '<div class="input-group">' +
        //                         '<button class="form-control btn btn-sql" type="button" onclick="showQueryBox()">建立SQL查询</button></div>' +
        //                         '<div class="queryBox" style="display: none"><div class="input-group col-md-12">' +
        //                         '<textarea name="textarea" class="form-control col-md-12 textarea-sql" placeholder="请书写sql语句" data-required="1" class="form-control" id="' + configId + '">' + text + '</textarea></div>' +
        //                         '</div></div>' +
        //                         '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="sql-Check"></label></div>';
        //                     $("#formBody").append(str);
        //                     // alert(configVal)
        //                     if (configVal.indexOf("\\|")) {
        //                         if ("show" == configVal.split("|")[1]) {
        //                             showQueryBox();
        //                         }
        //                     }
        //
        //                     if (configVal != null && configVal != "") {
        //                         var formatSql = configVal.split("|")[0].replace(/\s+/g, ",");
        //                         var words = formatSql.split(",");
        //                         for (var a = 0; a < words.length; a++) {
        //                             if (words[a] == "from") {
        //                                 var selectedTable = getSelectedTableName(blockId);
        //                                 if (words[a + 1] != selectedTable) {
        //                                     $("#sql-Check").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>sql语句查询的表名必须与所选表名一致!");
        //                                 } else {
        //                                     $("#sql-Check").html("");
        //                                 }
        //                             }
        //                         }
        //                     }
        //
        //                 }
        //             }
        //             if (configType == 4) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var str = '<div class="form-group formBox" id="config_select_radio"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required" aria-required="true"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<div class="mt-radio-list" id="' + configId + '" data-error-container="#form_2_membership_error">';
        //                 if(configName == "select"){
        //                     if (configVal == "true") {
        //                         str += '<label class="mt-radio">' +
        //                             '<input type="radio" name="singleConversion" value="true" checked="true" > 单目运算' +
        //                             '<span></span></label>' +
        //                             '<label class="mt-radio">' +
        //                             '<input type="radio" name="singleConversion" value="false"> 双目运算' +
        //                             '<span></span></label>';
        //                         singleConversion=true;
        //                     }
        //                     else {
        //                         str += '<label class="mt-radio">' +
        //                             '<input type="radio" name="singleConversion" value="true"> 单目运算' +
        //                             '<span></span></label>' +
        //                             '<label class="mt-radio">' +
        //                             '<input type="radio" name="singleConversion" value="false" checked="true"> 双目运算' +
        //                             '<span></span></label>';
        //                         singleConversion=false;
        //                     }
        //                 }else {
        //                     if (configVal == "true") {
        //                         str += '<label class="mt-radio">' +
        //                             '<input type="radio" name="membership" value="true" checked="true"> true' +
        //                             '<span></span></label>' +
        //                             '<label class="mt-radio">' +
        //                             '<input type="radio" name="membership" value="false"> false' +
        //                             '<span></span></label>';
        //                         radioStateVal=true;
        //                     }
        //                     else {
        //                         str += '<label class="mt-radio">' +
        //                             '<input type="radio" name="membership" value="true"> true' +
        //                             '<span></span></label>' +
        //                             '<label class="mt-radio">' +
        //                             '<input type="radio" name="membership" value="false" checked="true"> false' +
        //                             '<span></span></label>';
        //                         radioStateVal=false;
        //                     }
        //                 }
        //                 str += '</div><div id="form_2_membership_error"> </div>' +
        //                     '</div></div>';
        //                 $("#formBody").append(str);
        //             }
        //             if (configType == 9) {
        //                 var str = "";
        //                 if (configModalId == 83) {
        //                     configVal = $("#pythonText").text();
        //                     str = '<div class="form-group formBox">' +
        //                         '<div class="col-md-12">' +
        //                         '<a  class="editPyStyle" type="button" dataVal="' + configId + '" blockId="' + blockId + '">编辑python脚本</a>' +
        //                         '<textarea style="display: none" data-required="1" class="form-control" class="pythonInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
        //                         // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
        //                         '</div>';
        //                 } else {
        //                     configVal = $("#editorR").text();
        //                     str = '<div class="form-group formBox">' +
        //                         '<div class="col-md-12">' +
        //                         '<a  class="editRStyle" type="button" dataVal="' + configId + '" blockId="' + blockId + '">编辑R脚本</a>' +
        //                         '<textarea style="display: none" data-required="1" class="form-control" class="rInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
        //                         // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
        //                         '</div>';
        //                 }
        //
        //                 $("#formBody").append(str);
        //                 //$("#pyRTitle").html(boxName)
        //             }
        //             // 四期提取列
        //             if (configType == 8) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 // var fieldOptions = configVal.split(",");
        //                 var str = '<div class="form-group formBox" id="config_'+configName+'"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
        //                     '<div class="choice-column tag-already-'+configId+'" id="config'+configId+'"></div>';
        //                 // $.each(fieldOptions,function (index,value) {
        //                 //     str += '<p>' + value + '</p>';
        //                 // });
        //                 str += '</div>' +
        //                     '<div class="col-md-12">' +
        //                     '<button type="button" class="btn btn-default bg-color"  onclick="choiceColumn($(this))" dataVal="'+configId+'">选择列</button>' +
        //                     '</div>' +
        //                     '</div>';
        //                 $("#formBody").append(str);
        //                 addScrollBar();
        //                 var fieldOptionStr = "";
        //                 if(configVal != null && configVal != undefined){
        //                     $.each(configVal.split(","),function (index,value) {
        //                         fieldOptionStr += '<p>' + value + '</p>'
        //                     });
        //                     $(".tag-already-"+configId+" .mCSB_container").append(fieldOptionStr)
        //                 }
        //             }
        //             //选择标签列
        //             if (configType == 11) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var clickButtonNmae;
        //                 if(configModalId == 116){
        //                     clickButtonNmae = "选择拆分列";
        //                 }else{
        //                     clickButtonNmae = "选择标签列";
        //                 }
        //                 var str = '<div class="form-group formBox" id="config_'+configName+'"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
        //                     '<div class="choice-column tag-already-single'+configId+'" id="config'+configId+'"></div>' +
        //                     '</div>' +
        //                     '<div class="col-md-12">' +
        //                     '<button type="button" class="btn btn-default bg-color"  onclick="choiceSingleColumn($(this))" dataVal="'+configId+'">'+ clickButtonNmae +'</button>' +
        //                     '</div>' +
        //                     '</div>';
        //                 $("#formBody").append(str);
        //                 var fieldOptionStr = "";
        //                 if(configVal != null && configVal != undefined){
        //                     // $.each(configVal.split(";"),function (index,value) {
        //                       fieldOptionStr = '<p>' + configVal + '</p>';
        //                     // })
        //                     $('#config'+configId).append(fieldOptionStr)
        //                 }
        //             }
        //             // 复选框
        //             if ( configType == 10) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var noneValues = configVal.split("|")[0].split(",");//默认的checkbox
        //                 var checkedValue = configVal.split("|")[1];//选择的checkBox
        //                 var checkStr="";
        //                 for(var j=0;j<noneValues.length;j++){
        //
        //                     if(noneValues[j]=="\\s"){
        //                         checkStr+='<label class="checkbox_margin"><input name="checkbox" data-val="空字符" type="checkbox" value=" "  />空字符 </label>';
        //                     }else {
        //                         checkStr+='<label class="checkbox_margin"><input name="checkbox" data-val="'+noneValues[j]+'" type="checkbox" value=" "  />'+noneValues[j]+' </label>';
        //                     }
        //
        //                 }
        //                 var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<div class="mt-checkbox-list" id="' + configId + '" configVal="'+configVal+'" data-error-container="#form_2_membership_error">'+checkStr+
        //                     '</div></div>' +
        //                     '</div>';
        //
        //                 $("#formBody").append(str);
        //                 if(checkedValue=="unchecked"){
        //
        //                 }else {
        //                     checkedValue=checkedValue.split(",");
        //                     for(var j=0;j<noneValues.length;j++){
        //                         for(var m=0;m<checkedValue.length;m++){
        //                             if(checkedValue[m]==noneValues[j]){
        //                                 if(checkedValue[m]=="\\s"){
        //                                     checkedValue[m]="空字符"
        //                                 }
        //                                 $("#formBody").find("input[data-val="+checkedValue[m]+"]").prop("checked",true);
        //                             }
        //                         }
        //
        //                     }
        //                 }
        //
        //             }
        //             //下拉弹框，如果选择自定义选项，会出现输入框
        //             if (configType == 12) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var configSelectVal = configObj[i].data[k].configSelectVal
        //                 var configSelectLabelVal=configObj[i].data[k].configSelectLabelVal;
        //                 var selectConfigVal = configVal.split(",");
        //                 var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<select class="selectOptionVal form-control" id="' + configId + '" name="select1">';
        //                 var labelName="";
        //                 for (var m = 0; m < selectConfigVal.length; m++) {
        //                     if(configName == "elasticNetParam" || configName == "regularizers"){
        //                         if (selectConfigVal[m] == "defineVal") {
        //                             labelName="Elastic Net（介于0到1之间）";
        //                             str += '<option value="elasticNet" data-val="' + configId  + '" >Elastic Net</option>';
        //                         }else{
        //                             str += '<option value="' + selectConfigVal[m]  + '" >' + selectConfigVal[m] + '</option>';
        //                         }
        //                     }else if(configName == "fillValue" || configName == "fillNone"){
        //                         if (selectConfigVal[m] == "defineVal") {
        //                             labelName="值";
        //                             str += '<option value="elasticNet" data-val="' + configId  + '" >自定义</option>';
        //                         }else{
        //                             var ChineseSelectVal = getSelectValForChinese(selectConfigVal[m]);
        //                             str += '<option value="' + selectConfigVal[m]  + '" >' + ChineseSelectVal + '</option>';
        //                         }
        //                     }else {
        //                         str += '<option value="' + selectConfigVal[m]  + '">' + selectConfigVal[m] + '</option>';
        //                     }
        //
        //                 }
        //                 str += '</select></div></div><div class="displayDiv form-group formBox"><label class=" control-label col-md-12 config-label" >'+labelName+
        //                     '<span class="required"> * </span></label>' +
        //                     '<div class=" col-md-12">' +
        //                     '<input type="text" data-required="1" class="form-control" id="elasticNetId"  name="elasticNet"  dataValue="' + configId  + '" /></div></div>';
        //                 $("#formBody").append(str);
        //                 if(configSelectLabelVal !="defineVal"){
        //                     $(".displayDiv").css("display","none");
        //                     $(".selectOptionVal").val(configSelectVal);
        //                 }else{
        //                     $(".displayDiv").css("display","block");
        //                     $("#elasticNetId").val(configSelectVal);
        //                     $("#"+configId).find("option[value='elasticNet']").attr("selected",true);
        //
        //                 }
        //
        //             }
        //             // 类型转换
        //             if(configType == 13){
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var listStr="";
        //                 if(configVal != null){
        //                     var listAllName = JSON.parse(configVal);
        //                     var newType;
        //                     for(var q=0;q<listAllName.listName.split(",").length;q++){
        //                         if(listAllName.newType.split(",")[q] == "1"){
        //                             newType="string";
        //                         }else if(listAllName.newType.split(",")[q] == "0"){
        //                             newType="int"
        //                         }
        //                         listStr+='<p><span>'+listAllName.listName.split(",")[q]+'</span><br/><span>'+ newType +'</span></p>'
        //                     }
        //                 }
        //                 var str='<div class="operator-group">'+
        //                     '<div class="form-group formBox">'+
        //                     '<label class="control-label col-md-12 config-label">'+ChineseConfigName+'<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>'+
        //                     '<div class="col-md-12">'+
        //                     '<div class="typeChange choice-column" id="config'+configId+'">'+listStr+'</div>'+
        //                     '</div>'+
        //                     '<div class="col-md-12">'+
        //                     '<button type="button" class="btn btn-default bg-color" id="choiceTransform">选择列</button>'+
        //                     '</div>'+
        //                     '</div>'+
        //                     '</div>';
        //                 $("#formBody").append(str);
        //                 addScrollBar();
        //
        //             }
        //             //添加分组
        //             if (configType == 14) {
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 // var fieldOptions = configVal.split(",");
        //                 var str = '<div class="form-group formBox" id="config_'+configName+'"><label class="control-label col-md-12 config-label">' +
        //                     ChineseConfigName +
        //                     '<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>' +
        //                     '<div class="col-md-12">' +
        //                     '<div class="choice-column " id="group_'+configId+'"></div>';
        //                 str += '</div>' +
        //                     '<div class="col-md-12">' +
        //                     '<button type="button" class="btn btn-default bg-color" id="addGroupBtn" dataVal="'+configId+'" dataBlockId="'+blockId+'" >添加分组</button>' +
        //                     '</div>' +
        //                     '</div>';
        //                 $("#formBody").append(str);
        //                 addScrollBar();
        //                 var fieldOptionStr = "";
        //                 if(configVal != null && configVal != undefined){
        //                     var textArray=configVal.split(',');
        //                     for(var n=0;n<textArray.length-1;n++){
        //                         var sort=n+1;
        //                         fieldOptionStr+='<p><label class="labelMargin">组'+sort+'：</label>'+textArray[n]+'-'+textArray[n+1]+'</p>'
        //                     }
        //                     $('#formBody #group_'+configId+' .mCSB_container').html(fieldOptionStr);
        //                     $("#groupDiscreteInterval").val(configVal);
        //                 }
        //             }
        //
        //             // 列名修改
        //             if(configType == 15){
        //                 var ChineseConfigName = getChineseConfigName(configName);
        //                 var listStr="";
        //                 if(configVal != null){
        //                     var listAllName = JSON.parse(configVal);
        //                     var newType;
        //                     for(var q=0;q<listAllName.oldName.split(",").length;q++){
        //                         listStr+='<p><span>'+listAllName.oldName.split(",")[q]+'</span><br/><span>'+ listAllName.newName.split(",")[q] +'</span></p>'
        //                     }
        //                 }
        //                 var str='<div class="operator-group">'+
        //                     '<div class="form-group formBox">'+
        //                     '<label class="control-label col-md-12 config-label">'+ChineseConfigName+'<span class="required"> * </span>'+returnParameterMsg(configCommon)+'</label>'+
        //                     '<div class="col-md-12">'+
        //                     '<div class="typeChange choice-column" id="config'+configId+'">'+listStr+'</div>'+
        //                     '</div>'+
        //                     '<div class="col-md-12">'+
        //                     '<button type="button" class="btn btn-default bg-color" id="choiceRevise">选择列</button>'+
        //                     '</div>'+
        //                     '</div>'+
        //                     '</div>';
        //                 $("#formBody").append(str);
        //                 addScrollBar();
        //             }
        //
        //             // 列名修改end
        //         };
        //         // for循环结束之后将json重新拆分
        //         if(configModalId == "125"){
        //             configObj[i].data=split_array(configObj[i].data,8)
        //         }
        //     }
        //
        // }
        // if(modelID=="125"){
        //     str='<div class="operator-group">'+
        //         '<div class="form-group formBox">'+
        //         '<div class="col-md-12">'+
        //         '<button type="button" class="btn btn-default bg-color" id="addNewFilter">添加新过滤</button>'+
        //         '</div>'+
        //         '</div>'+
        //         '</div>';
        //     $("#formBody").append(str);
        // }
        // 封装上面的for循环方法提供多处使用
        switchConfigure(blockId, modelID);
        // end

        switchToHide();
        showSingleChangeInput(blockId);
        singleConversionMethod();
        addGroupMethod();
        editPyNameModel();
        editRNameModel();
        editConfigModel();
        editUrlConfigModel();
        isDn(blockId);
        isThresDn(blockId);
        return false;
    });
}

//function fileInputInit() {
//    $("span[class='input-group-btn']").on('click',function () {
//        var inputLabel = $(this).closest('div').find('input');
//        var changedId = $(this).closest('input').attr('id');
//        var blockId = $(this).parents('#formBody').attr('name');
//    });
//}


//使用鼠标滚轮进行缩放
//$("#canvasBody").on("mousewheel DOMMouseScroll", function (e) {
//
//    var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) ||  // chrome & ie
//        (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1));              // firefox
//
//    if (delta > 0 && zoomScale < 1.6) {
//        // 向上滚
//        zoomScale += 0.05;
//        setZoom(zoomScale);
//    } else if (delta < 0 && zoomScale > 0.6) {
//        // 向下滚
//        zoomScale = zoomScale - 0.05;
//        setZoom(zoomScale);
//    }
//});
$(function () {
    $("#canvasBlock").on("mouseover", ".box", function () {
        $(this).tooltip({
            trigger: 'hover',
            placement: 'right',
            html: true
        });
    });
    //设置滚动条样式
    var height = $(".page-content").css("height");
    var boxHeight = parseInt(height) - 100;
    var canvasHeight = parseInt(height);
    var configHeight = parseInt($(".page-quick-sidebar-wrapper").css("height")) - 400;
    $("#canvasBody").css("height", canvasHeight - 50);
    $(".operatorMessage").css("height", canvasHeight);
    $('#modelList').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        height: boxHeight + 'px',
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });
    $('#wellConsole').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        height: 180,
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });
    $('.table-scrollable').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        height: 180,
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });
    $('.page-quick-sidebar-wrapper #formBody').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        distance: '-10px',
        height: configHeight + "px",
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });
    $('.page-quick-sidebar-wrapper .explainContext').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        distance: '-10px',
        height: "200px",
        alwaysVisible: false,
        disableFadeOut: true,
        wheelStep: 10
    });
    $('#canvasBody').slimScroll({
        color: '#1bbc9b',
        railColor: '#eee',
        size: '5px',
        height: canvasHeight - 50 + "px",
        alwaysVisible: true,
        disableFadeOut: false,
        wheelStep: 5,
        borderRadius: "7px!important"
    });
    $(".dragList").find("h3").draggable({
        drag: draggingModel,
        helper: draggingModel,
        scope: "ss"
    });
    $("#workspace").addClass('active open');
    $("#canvasBody").droppable({
        scope: "ss",
        drop: createNode
    });
    removeModel();
    $("#formInput").change(function () {
        filterModel();
    });
    //configModel();
    initFloderConstruct();
    clickConfigModel();
    setTimeout(reload, 100);
    connetSocket();
    console.log(configObj);
});

function FileLabel(file) {
    this._file = file;
}

FileLabel.prototype = {};
FileLabel.prototype.create = function () {
//        var file = this._file;
//        var isDir = file.isdir;
//        var fileSize = file.size;
//
//        if(isDir && fileSize != 0){
//            var $li = $("<li>"+file.fileName+"</li>");
//
//            var $li_default = $("<li>空</li>");
//            $li.append($ul.append($li_default));
//        }
//        else if (isDir){
//            var $li = $("<li data-jstree={ 'type' : 'folder' }>"+file.fileName+"</li>");
//
//        }
//        else{
//            var $li = $("<li data-jstree={ 'type' : 'file' }>"+file.fileName+"</li>");
//        }
    var $li = $("<li>" + file.name + "</li>");
    $li.attr("data-jstree", "{ 'type' : 'file' }");
    return $li;
};

function showCalculationHistory(data) {
    $("#calculationHistory-content").html('');
    $("#calculationHistory").tmpl(data).appendTo('#calculationHistory-content');
}

var runningState = false;

function connetSocket() {
    var websocket = null;
    if (window['WebSocket'])
        websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket.do');
    else
        websocket = new new SockJS(PATH + '/websocket/socketjs');

    websocket.onopen = function (event) {
        console.log('open', event);
    };
    var count = 0;
    websocket.onmessage = function (event) {
        count++;
        if (count == 1) {
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/service/updateByFlowId.do",
                type: "GET",
                dataType: "json",
                data: {
                    "serviceModelFlowId": flowId,
                    "serviceModelStatus": 3
                },
                success: function (data) {
                    if (data.code == 417) {
                        $("#serviceModelName").val("");
                        $("#serviceModelDescription").val("");
                        // toastr.error(data.msg);
                        $('#webServiceShow').modal('hide')
                    } else {
                        $("#serviceModelName").val("");
                        $("#serviceModelDescription").val("");
                        // toastr.success(data.msg);
                        $('#webServiceShow').modal('hide')
                    }
                },
                error: function (data) {
                    if (data.responseText == "AjaxSessionTimeout") {
                        window.location.href = basePath;
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
                for (var i = 0; i < configObj.length; i++) {
                    if ($("#" + configObj[i].BlockId + " .boxBody").hasClass("run-type")) {
                        $("#" + configObj[i].BlockId + " .boxBody").removeClass("run-type");
                        $("#" + configObj[i].BlockId + " .boxBody").addClass("fail-type");
                    }
                }
                // $("#run").removeClass("stopRun");
                $("#run").removeClass("stopRun");
                $("#scheduler").removeClass("stopRun");
                var flowId = $("#flowId").val();
                $.ajax({
                    url: basePath + "/service/updateByFlowId.do",
                    type: "GET",
                    dataType: "json",
                    data: {
                        "serviceModelFlowId": flowId,
                        "serviceModelStatus": 2
                    },
                    success: function (data) {
                        if (data.code == 417) {
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        } else {
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        }
                    },
                    error: function (data) {
                        if (data.responseText == "AjaxSessionTimeout") {
                            window.location.href = basePath;
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
                            if (datas[i + 2].indexOf("" + configObj[j].BlockId + "_") > 0 && datas[i + 2].indexOf("运行成功") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("fail-type run-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("success-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("success-type");
                                }
                                console.log("-----------------------------" + configObj[j].BlockId + "=====" + configObj[j].BlockName + ":运行成功 ---------------------------");
                                // break;
                            } else if (datas[i + 2].indexOf("" + configObj[j].BlockId + "_") > 0 && datas[i + 2].indexOf("运行出错") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("success-type run-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("fail-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("fail-type");
                                }
                                console.log("------------------------------" + configObj[j].BlockId + "=====" + configObj[j].BlockName + ":运行出错 ----------------------------");
                                // break;
                            } else if (datas[i + 2].indexOf("" + configObj[j].BlockId + "_") > 0 && datas[i + 2].indexOf("开始启动") > 0) {
                                $("#" + configObj[j].BlockId + " .boxBody").removeClass("success-type fail-type");
                                var n = $("#" + configObj[j].BlockId + " .boxBody").hasClass("run-type");
                                if (!n) {
                                    $("#" + configObj[j].BlockId + " .boxBody").addClass("run-type");
                                }
                                runningState = true;
                                console.log("------------------------------" + configObj[j].BlockId + "=====" + configObj[j].BlockName + ":运行中... ----------------------------");
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
                if ($(".chongxinxunlian").is(":hidden")) {
                    $(".bushuweiwebfuwu").prop("hidden", false);
                } else {
                    // alert("显示web服务按钮")
                    $(".bushuweiwebfuwu").prop("hidden", true);
                }
                var flowId = $("#flowId").val();
                $.ajax({
                    url: basePath + "/service/updateByFlowId.do",
                    type: "GET",
                    dataType: "json",
                    data: {
                        "serviceModelFlowId": flowId,
                        "serviceModelStatus": 1
                    },
                    success: function (data) {
                        if (data.code == 417) {
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                        } else {
                            $("#serviceModelName").val("");
                            $("#serviceModelDescription").val("");
                            // toastr.success("工作流完成训练。");
                        }
                    },
                    error: function (data) {
                        if (data.responseText == "AjaxSessionTimeout") {
                            window.location.href = basePath;
                            return;
                        }
                    }
                })
            }
        }
    }
}

//初始化注释start
function createNoteWrite(event, note, left, top, id, width, height) {
    var noteContent = note.NoteContent;
    var bgColor = note.NoteBgColor;
    var align = note.NoteTextAlign;
    var alginHtmlStr = '';
    if (align == "center") {
        alginHtmlStr = '<i class="align1 iconfont icon-zuoduiqi1" style="color:#000000"></i>' +
            '<i class="align2 iconfont icon-juzhong1" style="color:#1bbc9b"></i>' +
            '<i class="align3 iconfont icon-youduiqi" style="color:#000000"></i>';
    } else if (align == 'right') {
        alginHtmlStr = '<i class="align1 iconfont icon-zuoduiqi1" style="color:#000000"></i>' +
            '<i class="align2 iconfont icon-juzhong1" style="color:#000000"></i>' +
            '<i class="align3 iconfont icon-youduiqi" style="color:#1bbc9b"></i>';
    } else {
        alginHtmlStr = '<i class="align1 iconfont icon-zuoduiqi1" style="color:#1bbc9b"></i>' +
            '<i class="align2 iconfont icon-juzhong1" style="color:#000000"></i>' +
            '<i class="align3 iconfont icon-youduiqi" style="color:#000000"></i>';
    }

    var str = '';
    str = '<div class="noteDiv" id="' + id + '">' +
        '<div class="noteContent">' +
        '<div class="noteMenu">' +
        '<div class="noteMenuAlign">' + alginHtmlStr +
        '</div>' +
        '<div class="menuColorSelect">' +
        '<div class="menuColorDiv">' +
        '<div class="menuColorfather" style="background-color: ' + bgColor + '"></div>' +
        '<div class="selectColor"><i class=" iconfont icon-shang2"></i></div>' +
        '</div>' +
        '<div class="menuColorList">' +
        '<div class="colorNum1" bgcolor="#f6dada"></div>' +
        '<div class="colorNum2" bgcolor="#f6dfc7"></div>' +
        '<div class="colorNum3" bgcolor="#fafacd"></div>' +
        '<div class="colorNum4" bgcolor="#dcf1c8"></div>' +
        '<div class="colorNum5"  bgcolor="#d5f5e5"></div>' +
        '<div class="colorNum6"  bgcolor="#c7eded"></div>' +
        '<div class="colorNum7"  bgcolor="#c5daee"></div>' +
        '<div class="colorNum8"  bgcolor="#e7daf3"></div>' +
        '</div>' +
        '</div>' +
        '<a class="menuDelete"><i class="iconfont icon-shanchu1"></i></a>' +
        '</div>' +
        '<textarea class="noteTextarea" style="background-color: ' + bgColor + ';text-align: ' + align + '" >' + noteContent + '</textarea>' +
        '<div class=\"noteInput\" ></div>' +
        '</div>' +
        '</div>';
    $("#canvasBlock").append(str);
    $("#" + id).css({"left": left, "top": top, "width": width, "height": height});
    jsPlumb.draggable(id, {containment: false});
    $(".noteDiv").resizable({
        start: function (event, ui) {
            jsPlumb.destroyDraggable(ui.element[0].id);
            $("#canvasBlock").off("mousedown");
        },
        resize: function (event, ui) {
            event.stopPropagation();
            $("#" + id + "textarea").off("focus");
            // jsPlumb.repaint(ui.helper);
        },
        stop: function (event, ui) {
            jsPlumb.draggable(ui.element[0].id, {containment: false});
        },
        // aspectRatio: true,
        minWidth: 120,
        minHeight: 120
        // maxWidth: 600,
        // maxHeight:600
    });
};
//初始化注释end

$(window).on("load", function () {
    $("body .excel_content").mCustomScrollbar({
        theme: "dark-thin",
        axis: "yx",
        setLeft: 0,
        scrollbarPosition: "outside",
        autoDraggerLength: false
    });
    $("body .excel_content_scrollbar").mCustomScrollbar({
        theme: "dark-thin",
        axis: "yx",
        setLeft: 0,
        scrollbarPosition: "outside",
        autoDraggerLength: false
    });
    // 提取列参数滚动条
    $("body .choice-column").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
    // 查看数据可视化滚动条
    $("#dataVisualization .model_content").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside",
        alwaysShowScrollbar: 3
    });
});

// 切换配置及条件过滤渲染新页面
// blockId---statemodel
function switchConfigure(blockId, modelID) {
    $("#formBody").html("");
    /**
     * 每次点击模型的时候，从数据库动态获取连接名展示在select框中，
     */
    // alert(1111)
    for (var i = 0; i < configObj.length; i++) {
        if (blockId == configObj[i].BlockId) {
            var configModalId = configObj[i].id;
            // 循环渲染之前将json整合
            if (configModalId == "125") {
                configObj[i].data = conformity_arr(configObj[i].data);
            }
            for (var k = 0; k < configObj[i].data.length; k++) {
                var configType = configObj[i].data[k].configType;
                var configVal = configObj[i].data[k].configVal;
                var configId = configObj[i].data[k].configId;
                var configName = configObj[i].data[k].configName;
                var configCommon = configObj[i].data[k].configCommon;
                console.log(configObj[i].data.length);
                if (configType == 0) {
                    var str = '<div class="form-group formBox formBoxHidden"><label class="control-label col-md-12 config-label">' +
                        configName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<input type="text" data-required="1" class="form-control" id="' + configId + '" value="' + configVal + '" disabled="disabled"/></div></div>';
                    $("#formBody").append(str);
                }
                if (configType == 1) {
                    var showVal = "";
                    if (configVal == null) {
                        showVal = "";
                    } else {
                        showVal = configVal;
                    }
                    var showConfigName
                    if (configName == "sampleSize") {
                        showConfigName = sampleVal
                    } else if (configName == "splitValue") {
                        showConfigName = splitVal
                    } else {
                        showConfigName = configName;
                    }

                    if (configName == "splitValue") {
                        var select = $("#formBody").children().find("select");
                        var checkedVal = select.val();
                        // var checkedVal = $(this).parents("#formBody").children().eq(1).find("select").val();
                        if (checkedVal == 0) {
                            showVal = configVal.split(",")[0];
                        } else {
                            showVal = configVal.split(",")[1];
                        }
                    }

                    ChineseConfigName = getChineseConfigName(showConfigName);
                    var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<input type="text" data-required="1" class="form-control" id="' + configId + '" value="' + showVal + '" /></div></div>';
                    $("#formBody").append(str);

                    if (configName == 'featureSubsetStrategyN') {
                        for (var p = 0; p < configObj.length; p++) {
                            for (var q = 0; q < configObj[p].data.length; q++) {
                                if (configObj[p].data[q].configName == "featureSubsetStrategy") {
                                    var configVal = configObj[p].data[q].configSelectVal;
                                    if (configVal == "n") {
                                        $("#config_featureSubsetStrategyN").css('display', 'block');
                                    } else {
                                        $("#config_featureSubsetStrategyN").css('display', 'none');
                                    }
                                }
                            }
                        }
                    }

                    if (configModalId == 77 || configModalId == 79) {
                        $("#" + configId).addClass("unWrite");
                    }
                }
                if (configType == 2) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var configSelectVal = configObj[i].data[k].configSelectVal
                    var sampleTypeForChin;
                    var splitTypeForChin;
                    if (configName == "sampleType") {
                        if (configSelectVal == "1") {
                            sampleVal = "sampleSize_Num"
                            sampleTypeForChin = getChineseConfigName("sampleSize_Num")
                            $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
                        } else if (configSelectVal == "0") {
                            sampleVal = "sampleSize_Percent"
                            sampleTypeForChin = getChineseConfigName("sampleSize_Percent")
                            $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
                        }
                    }
                    if (configName == "splitType") {
                        if (configSelectVal == "0") {
                            splitVal = "splitValue_Percent"
                            splitTypeForChin = getChineseConfigName("splitValue_Percent")
                            $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
                        } else if (configSelectVal == "1") {
                            splitVal = "splitValue_Num"
                            splitTypeForChin = getChineseConfigName("splitValue_Num")
                            $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
                        }
                    }
                    var selectConfigVal = configVal.split(",");
                    var str = '<div class="form-group formBox"  id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<select class="form-control filterChoose" id="' + configId + '" name="select">';
                    for (var j = 0; j < selectConfigVal.length; j++) {
                        if (configName == "sampleType" || configName == "splitType") {
                            var ChineseSelectConfigVal = getSelectConfigValForChi(configName + "|" + selectConfigVal[j]);
                            str += '<option value="' + selectConfigVal[j] + '">' + ChineseSelectConfigVal + '</option>';
                        } else if (configName == "featrueSelectionMethod" || configName == "mergeType" || configName == "discretionType") {
                            var ChineseSelectVal = getSelectValForChinese(selectConfigVal[j]);
                            str += '<option value="' + selectConfigVal[j] + '">' + ChineseSelectVal + '</option>';
                        } else {
                            str += '<option value="' + selectConfigVal[j] + '">' + selectConfigVal[j] + '</option>';
                        }
                    }
                    str += '</select></div></div>';
                    $("#formBody").append(str);
                    if (singleConversion == true) {
                        if (configName == "changeA") {
                            selectValueType = configSelectVal;
                        }
                    }
                    selectValByUnDispersion = configSelectVal;
                    if (configName == "disFunctionFamily") {
                        selectDisFunctionFamilyVal = configSelectVal;
                    }
                    $("#" + configId).val(configSelectVal);
                }
                if (configType == 3) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    console.log(configVal)
                    if (configVal != undefined && configVal != "") {
                        configVal = "/" + configVal.split("/")[configVal.split("/").length - 2] + "/" + configVal.split("/")[configVal.split("/").length - 1];
                    } else {
                        configVal = "";
                    }
                    var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="input-group">' +
                        '<input type="text" class="form-control" id="' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
                        '<span class="input-group-btn">' +
                        '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="1"><i class="icon-doc"></i></a></span>' +
                        '</div></div></div>';
                    $("#formBody").append(str);
                }
                if (configType == 5) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    if (configVal != undefined && configVal != "") {
                        configVal = "/" + configVal.split("/")[configVal.split("/").length - 2] + "/" + configVal.split("/")[configVal.split("/").length - 1];
                    }
                    var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="input-group">' +
                        '<input type="text" class="form-control" id="' + configId + '" data-required="1" value="' + configVal + '" title="' + configVal + '" disabled="disabled">' +
                        '<span class="input-group-btn">' +
                        '<a href="#basic" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="2"><i class="icon-doc"></i></a></span>' +
                        '</div></div></div>';
                    $("#formBody").append(str);
                }

                if (configType == 6) {
                    if (configName == 'dbLinkName') {
                        $.ajax({
                            url: basePath + "/drag/modelattri/LoadEnableConnNames.do",
                            type: 'GET',
                            async: false,
                            contentType: "text",
                            success: function (data) {
                                var selectedConnName = configVal.split("|")[1];
                                configVal = data + "|" + selectedConnName;
                            },
                            error: function (data) {
                                if (data == "AjaxSessionTimeout") {
                                    window.location.href = basePath;
                                    return;
                                }
                            },
                            complete: function (data) {
                                if (data == "AjaxSessionTimeout") {
                                    window.location.href = basePath;
                                    return;
                                }
                            },
                        })
                        var splits = configVal.split("|");
                        var connNames = splits[0];
                        var selectedValue = splits[1];
                        // alert(selectedValue);
                        var optionValues = connNames.split(",");
                        var options = "";
                        for (var m = 0; m < optionValues.length; m++) {
                            //当用户Id没有连接名的时候，后台返回的数据可能为"",需要排除数据为""的情况。
                            if ("" != optionValues[m]) {
                                var tableName = optionValues[m];
                                if (optionValues[m].length > 11) {
                                    tableName = tableName.substring(0, 11)
                                }
                                options += '<option value="' + optionValues[m] + '" title="' + optionValues[m] + '">' + tableName + '</option>';
                            }
                        }
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            "连接名" +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="input-group col-md-12">' +
                            '<div class="col-md-9" style="padding: 0 5px 0 15px;">' +
                            '<select  name="select" class="form-control form-dbLinkName" onchange="dbLinkNameChange($(this))" id="' + configId + '" data-required="1" >' +
                            // '<option value="'+configVal+'">'+configVal+'</option>'+
                            options +
                            '</select></div>' +
                            '<div class="col-md-2" style="padding: 0"><span class="data-link-btn input-group-btn">' +
                            // '<a href="#dataLink" data-toggle="modal" class="btn btn-default btnUrl btnTreeModal" name="btnUrl" type="button" value="2"><i>新建连接</i></a></span>'
                            '<a data-toggle="modal" class="btn btn-default btnUrl btnTreeModal createLink" onclick="createNewLink()" name="btnUrl" type="button" value="2"><span class="icon iconfont icon-jia4"></span></a></span>'
                        // '<li data-toggle="modal" class="createLink">新建连接</li>' +
                        '</div></div></div>';
                        // alert(configId);
                        $("#formBody").append(str);
                        $("#" + configId).val(selectedValue);
                    }
                    if (configName == 'tableName') {
                        var tableSelected = configVal.split("|")[1];
                        // alert()
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            "表名" +
                            '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="input-group col-md-12">' +
                            '<div class="col-md-12">' +
                            '<select name="select" class="form-control" id="' + configId + '" data-required="1" >' +
                            '</select>' +
                            '</div></div>' +
                            '</div>';
                        $("#formBody").append(str);
                        var tbSelected = $("#" + configId);
                        var dbLinkNameSelect = tbSelected.parent().parent().parent().prev().find(".form-control");
                        if (dbLinkNameSelect.length > 0) {
                            var linkName = dbLinkNameSelect.val();
                            // alert(linkName)
                            $.ajax({
                                url: basePath + "/drag/modelattri/loadTables.do",
                                type: "POST",
                                dataType: "json",
                                data: "connName=" + linkName,
                                success: function (data) {
                                    var tablesContainsTbselected = false;
                                    var options = "";
                                    $.each(data, function (index, value) {
                                        options += '<option value="' + value + '">' + value + '</option>';
                                        if (value == tableSelected) {
                                            tablesContainsTbselected = true;
                                        }
                                    })
                                    if (!tablesContainsTbselected) {
                                        tableSelected = data[0];
                                    }
                                    tbSelected.append(options);
                                    tbSelected.val(tableSelected);
                                },
                                error: function (data) {
                                    if (data.responseText == "AjaxSessionTimeout") {
                                        window.location.href = basePath;
                                        return;
                                    }
                                }
                            })
                        }
                    }

                    if (configName == 'writeMode') {
                        console.log(configVal);
                        var op1 = "";
                        var op2 = "";
                        var ModesSelected = "";
                        if (configVal.indexOf("|")) {
                            var writeModes = configVal.split("|")[0];
                            ModesSelected = configVal.split("|")[1];
                            op1 = writeModes.split(",")[0];
                            op2 = writeModes.split(",")[1];
                        } else {
                            op1 = configVal.split(',')[0];
                            op2 = configVal.split(',')[1];
                        }
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            "写入模式" +
                            '<span class="required"> * </span></label>' +
                            '<div class="input-group col-md-12">' +
                            '<div class="col-md-12">' +
                            '<select name="select" class="form-control col-md-12" id="' + configId + '" data-required="1" >' +
                            '<option value="' + op1 + '">' + "追加" + '</option>' +
                            '<option value="' + op2 + '">' + "覆盖" + '</option>' +
                            '</select>' +
                            '</div></div>' +
                            '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="writeMode-instruction"></label>' +
                            '</div>';
                        '</div>';
                        $("#formBody").append(str);
                        $("#" + configId).val(ModesSelected);
                        if (ModesSelected == "overwrite") {
                            $("#writeMode-instruction").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>警告，覆盖模式将清空写入表格所有数据！");
                            $("#writeMode-notice").html(" * ");
                        } else if (ModesSelected == "append") {
                            $("#writeMode-instruction").html("");
                        }
                    }

                }

                if (configType == 7) {
                    if (configName == 'sqlStatment') {
                        var text = configVal.split("|")[0];
                        if (text == "null") {
                            text = "";
                        }
                        var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                            "SQL语句" +
                            '<span class="required"></span>' + returnParameterMsg(configCommon) + '</label>' +
                            '<div class="col-md-12">' +
                            '<div class="input-group">' +
                            '<button class="form-control btn btn-sql" type="button" onclick="showQueryBox()">建立SQL查询</button></div>' +
                            '<div class="queryBox" style="display: none"><div class="input-group col-md-12">' +
                            '<textarea name="textarea" class="form-control col-md-12 textarea-sql" placeholder="请书写sql语句" data-required="1" class="form-control" id="' + configId + '">' + text + '</textarea></div>' +
                            '</div></div>' +
                            '<div class="form-group formBox"><label class="control-label col-md-12 config-label" id="sql-Check"></label></div>';
                        $("#formBody").append(str);
                        // alert(configVal)
                        if (configVal.indexOf("\\|")) {
                            if ("show" == configVal.split("|")[1]) {
                                showQueryBox();
                            }
                        }

                        if (configVal != null && configVal != "") {
                            var formatSql = configVal.split("|")[0].replace(/\s+/g, ",");
                            var words = formatSql.split(",");
                            for (var a = 0; a < words.length; a++) {
                                if (words[a] == "from") {
                                    var selectedTable = getSelectedTableName(blockId);
                                    if (words[a + 1] != selectedTable) {
                                        $("#sql-Check").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>sql语句查询的表名必须与所选表名一致!");
                                    } else {
                                        $("#sql-Check").html("");
                                    }
                                }
                            }
                        }

                    }
                }
                if (configType == 4) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var str = '<div class="form-group formBox config_select_radio" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required" aria-required="true"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="mt-radio-list" id="' + configId + '" data-error-container="#form_2_membership_error">';
                    if (configName == "select") {
                        if (configVal == "true") {
                            str += '<label class="mt-radio">' +
                                '<input type="radio" name="singleConversion" value="true" checked="true" > 单目运算' +
                                '<span></span></label>' +
                                '<label class="mt-radio">' +
                                '<input type="radio" name="singleConversion" value="false"> 双目运算' +
                                '<span></span></label>';
                            singleConversion = true;
                        }
                        else {
                            str += '<label class="mt-radio">' +
                                '<input type="radio" name="singleConversion" value="true"> 单目运算' +
                                '<span></span></label>' +
                                '<label class="mt-radio">' +
                                '<input type="radio" name="singleConversion" value="false" checked="true"> 双目运算' +
                                '<span></span></label>';
                            singleConversion = false;
                        }
                    } else {
                        if (configVal == "true") {
                            str += '<label class="mt-radio">' +
                                '<input class="membership" type="radio" name="' + configName + '" value="true" checked="true"> true' +
                                '<span></span></label>' +
                                '<label class="mt-radio">' +
                                '<input class="membership" type="radio" name="' + configName + '" value="false"> false' +
                                '<span></span></label>';
                            radioStateVal = true;
                        }
                        else {
                            str += '<label class="mt-radio">' +
                                '<input class="membership" type="radio" name="' + configName + '" value="true"> true' +
                                '<span></span></label>' +
                                '<label class="mt-radio">' +
                                '<input class="membership" type="radio" name="' + configName + '" value="false" checked="true"> false' +
                                '<span></span></label>';
                            radioStateVal = false;
                        }
                    }
                    str += '</div><div id="form_2_membership_error"> </div>' +
                        '</div></div>';
                    $("#formBody").append(str);
                }
                if (configType == 9) {
                    var str = "";
                    if (configModalId == 83) {
                        configVal = $("#pythonText").text();
                        str = '<div class="form-group formBox">' +
                            '<div class="col-md-12">' +
                            '<a  class="editPyStyle" type="button" dataVal="' + configId + '" blockId="' + blockId + '">编辑python脚本</a>' +
                            '<textarea style="display: none" data-required="1" class="form-control" class="pythonInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
                            // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
                            '</div>';
                    } else {
                        configVal = $("#editorR").text();
                        str = '<div class="form-group formBox">' +
                            '<div class="col-md-12">' +
                            '<a  class="editRStyle" type="button" dataVal="' + configId + '" blockId="' + blockId + '">编辑R脚本</a>' +
                            '<textarea style="display: none" data-required="1" class="form-control" class="rInput" id="config' + configId + '"  >' + configVal + '</textarea>' +
                            // '<textarea  data-required="1"  style="height:200px;" class="form-control" id="'+configId+'"   >'+showVal+'</textarea></div>
                            '</div>';
                    }

                    $("#formBody").append(str);
                    //$("#pyRTitle").html(boxName)
                }
                // 四期提取列
                if (configType == 8) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    // var fieldOptions = configVal.split(",");
                    var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
                        '<div class="choice-column tag-already-' + configId + '" id="config' + configId + '"></div>';
                    // $.each(fieldOptions,function (index,value) {
                    //     str += '<p>' + value + '</p>';
                    // });
                    str += '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="choiceColumnBtn btn btn-default bg-color"  onclick="choiceColumn($(this))" dataVal="' + configId + '">选择列</button>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    addScrollBar();
                    var fieldOptionStr = "";
                    if (configVal != null && configVal != undefined) {
                        $.each(configVal.split(","), function (index, value) {
                            fieldOptionStr += '<p>' + value + '</p>'
                        });
                        $(".tag-already-" + configId + " .mCSB_container").append(fieldOptionStr)
                    }
                }
                //选择标签列
                if (configType == 11) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var clickButtonNmae;
                    if (configModalId == 116) {
                        clickButtonNmae = "选择拆分列";
                    } else {
                        clickButtonNmae = "选择标签列";
                    }
                    var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        // '<input type="text" data-required="1" class="form-control" id="'+configId+'" value="'+configVal+'" disabled="disabled"/>' +
                        '<div class="choice-column tag-already-single' + configId + '" id="config' + configId + '"></div>' +
                        '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color"  onclick="choiceSingleColumn($(this))" dataVal="' + configId + '">' + clickButtonNmae + '</button>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    var fieldOptionStr = "";
                    if (configVal != null && configVal != undefined) {
                        // $.each(configVal.split(";"),function (index,value) {
                        fieldOptionStr = '<p>' + configVal + '</p>';
                        // })
                        $('#config' + configId).append(fieldOptionStr)
                    }
                }
                // 复选框
                if (configType == 10) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var noneValues = configVal.split("|")[0].split(",");//默认的checkbox
                    var checkedValue = configVal.split("|")[1];//选择的checkBox
                    var checkStr = "";
                    for (var j = 0; j < noneValues.length; j++) {

                        if (noneValues[j] == "\\s") {
                            checkStr += '<label class="checkbox_margin"><input name="checkbox" data-val="空字符" type="checkbox" value=" "  />空字符 </label>';
                        } else {
                            checkStr += '<label class="checkbox_margin"><input name="checkbox" data-val="' + noneValues[j] + '" type="checkbox" value=" "  />' + noneValues[j] + ' </label>';
                        }

                    }
                    var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="mt-checkbox-list" id="' + configId + '" configVal="' + configVal + '" data-error-container="#form_2_membership_error">' + checkStr +
                        '</div></div>' +
                        '</div>';

                    $("#formBody").append(str);
                    if (checkedValue == "unchecked") {

                    } else {
                        checkedValue = checkedValue.split(",");
                        for (var j = 0; j < noneValues.length; j++) {
                            for (var m = 0; m < checkedValue.length; m++) {
                                if (checkedValue[m] == noneValues[j]) {
                                    if (checkedValue[m] == "\\s") {
                                        checkedValue[m] = "空字符"
                                    }
                                    $("#formBody").find("input[data-val=" + checkedValue[m] + "]").prop("checked", true);
                                }
                            }

                        }
                    }

                }
                //下拉弹框，如果选择自定义选项，会出现输入框
                if (configType == 12) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var configSelectVal = configObj[i].data[k].configSelectVal
                    var configSelectLabelVal = configObj[i].data[k].configSelectLabelVal;
                    var selectConfigVal = configVal.split(",");
                    var str = '<div class="form-group formBox"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<select class="selectOptionVal form-control" id="' + configId + '" name="select1">';
                    var labelName = "";
                    for (var m = 0; m < selectConfigVal.length; m++) {
                        if (configName == "elasticNetParam" || configName == "regularizers") {
                            if (selectConfigVal[m] == "defineVal") {
                                labelName = "Elastic Net（介于0到1之间）";
                                str += '<option value="elasticNet" data-val="' + configId + '" >Elastic Net</option>';
                            } else {
                                str += '<option value="' + selectConfigVal[m] + '" >' + selectConfigVal[m] + '</option>';
                            }
                        } else if (configName == "fillValue" || configName == "fillNone" || configName == "smoothPadding") {
                            if (selectConfigVal[m] == "defineVal") {
                                labelName = "值";
                                str += '<option value="elasticNet" data-val="' + configId + '" >自定义</option>';
                            } else {
                                var ChineseSelectVal = getSelectValForChinese(selectConfigVal[m]);
                                str += '<option value="' + selectConfigVal[m] + '" >' + ChineseSelectVal + '</option>';
                            }
                        } else {
                            str += '<option value="' + selectConfigVal[m] + '">' + selectConfigVal[m] + '</option>';
                        }

                    }
                    str += '</select></div></div><div class="displayDiv form-group formBox"><label class=" control-label col-md-12 config-label" >' + labelName +
                        '<span class="required"> * </span></label>' +
                        '<div class=" col-md-12">' +
                        '<input type="text" data-required="1" class="form-control" id="elasticNetId"  name="elasticNet"  dataValue="' + configId + '" /></div></div>';
                    $("#formBody").append(str);
                    if (configSelectLabelVal != "defineVal") {
                        $(".displayDiv").css("display", "none");
                        $(".selectOptionVal").val(configSelectVal);
                    } else {
                        $(".displayDiv").css("display", "block");
                        $("#elasticNetId").val(configSelectVal);
                        $("#" + configId).find("option[value='elasticNet']").attr("selected", true);

                    }

                }
                // 类型转换
                if (configType == 13) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var listStr = "";
                    if (configVal != null) {
                        var listAllName = JSON.parse(configVal);
                        var newType;
                        for (var q = 0; q < listAllName.listName.split(",").length; q++) {
                            if (listAllName.newType.split(",")[q] == "1") {
                                newType = "string";
                            } else if (listAllName.newType.split(",")[q] == "0") {
                                newType = "int"
                            }
                            listStr += '<p><span>' + listAllName.listName.split(",")[q] + '</span><br/><span>' + newType + '</span></p>'
                        }
                    }
                    var str = '<div class="operator-group">' +
                        '<div class="form-group formBox">' +
                        '<label class="control-label col-md-12 config-label">' + ChineseConfigName + '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="typeChange choice-column" id="config' + configId + '">' + listStr + '</div>' +
                        '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color" id="choiceTransform">选择列</button>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    addScrollBar();

                }
                //添加分组
                if (configType == 14) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    // var fieldOptions = configVal.split(",");
                    var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="choice-column " id="group_' + configId + '"></div>';
                    str += '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color" id="addGroupBtn" dataVal="' + configId + '" dataBlockId="' + blockId + '" >添加分组</button>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    addScrollBar();
                    var fieldOptionStr = "";
                    if (configVal != null && configVal != undefined) {
                        var textArray = configVal.split(',');
                        for (var n = 0; n < textArray.length - 1; n++) {
                            var sort = n + 1;
                            fieldOptionStr += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n] + '-' + textArray[n + 1] + '</p>'
                        }
                        $('#formBody #group_' + configId + ' .mCSB_container').html(fieldOptionStr);
                        $("#groupDiscreteInterval").val(configVal);
                    }
                }

                // 列名修改
                if (configType == 15) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    var listStr = "";
                    if (configVal != null) {
                        var listAllName = JSON.parse(configVal);
                        var newType;
                        for (var q = 0; q < listAllName.oldName.split(",").length; q++) {
                            listStr += '<p><span>' + listAllName.oldName.split(",")[q] + '</span><br/><span>' + listAllName.newName.split(",")[q] + '</span></p>'
                        }
                    }
                    var str = '<div class="operator-group">' +
                        '<div class="form-group formBox">' +
                        '<label class="control-label col-md-12 config-label">' + ChineseConfigName + '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="typeChange choice-column" id="config' + configId + '">' + listStr + '</div>' +
                        '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color" id="choiceRevise">选择列</button>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    addScrollBar();
                }

                // 列名修改end
                // 二类分组单个阈值
                if (configType == 16) {
                    var ChineseConfigName = getChineseConfigName(configName);
                    // var fieldOptions = configVal.split(",");
                    var str = '<div class="form-group formBox" id="config_' + configName + '"><label class="control-label col-md-12 config-label">' +
                        ChineseConfigName +
                        '<span class="required"> * </span>' + returnParameterMsg(configCommon) + '</label>' +
                        '<div class="col-md-12">' +
                        '<div class="choice-column " id="group_' + configId + '"></div>';
                    str += '</div>' +
                        '<div class="col-md-12">' +
                        '<button type="button" class="btn btn-default bg-color" id="addThresGroup" dataVal="' + configId + '" dataBlockId="' + blockId + '" >添加分组</button>' +
                        '</div>' +
                        '</div>';
                    $("#formBody").append(str);
                    addScrollBar();
                    var fieldOptionStr = "";
                    if (configVal != null && configVal != undefined) {
                        var textArray = configVal.split(',');
                        for (var n = 0; n < textArray.length; n++) {
                            var sort = n + 1;
                            fieldOptionStr += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n]  + '</p>'
                        }
                        $('#formBody #group_' + configId + ' .mCSB_container').html(fieldOptionStr);
                        $("#groupDiscreteInterval").val(configVal);
                    }
                }
            }
            ;
            // for循环结束之后将json重新拆分
            if (configModalId == "125") {
                configObj[i].data = split_array(configObj[i].data, 9)
            }
        }

    }
    if (modelID == "125") {
        str = '<div class="operator-group">' +
            '<div class="form-group formBox">' +
            '<div class="col-md-12">' +
            '<button type="button" class="btn btn-default bg-color" id="addNewFilter">添加新过滤</button>' +
            '</div>' +
            '</div>' +
            '</div>';
        $("#formBody").append(str);
    }

}