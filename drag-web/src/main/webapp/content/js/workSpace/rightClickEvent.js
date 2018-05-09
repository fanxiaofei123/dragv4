$(function () {
    rightClickEvent();
    copyAndPaste();
    addNotes();
    notesStyle();
    notesRightClick();
    rightDetele();
    saveModel();
    lookDataVisualization();
    lookLog();
    lookDataRes();
});
// 右键相关事件隐藏与显示
function rightClickEvent() {
    // 屏蔽浏览器右键功能
    document.oncontextmenu = function () {
        return false;
    };
    $(document).on('click', function (e) {
        e.stopPropagation();
        $("#right_menu").css({display: 'none'})
    });
    // 算子绑定右键功能
    $("#canvasBlock").on('contextmenu', '.box', function (e) {
        console.log($(this).parent());
        var modelId = $(this).find('input').val();
        e.stopPropagation();
        $('#paste').hide();
        $('#field-des').hide();
        $('#save-model').hide();
        $('#visualization').hide();
        $('#copy').show();
        //添加字段描述的判断
        if (modelId == 1 || modelId == 53 || modelId == 56) {
            $('#field-des').show();
        }
        if (modelId == 84){
            $('#visualization').show();
        }
        $('#deleteLi').show();
        var key = e.which;
        if (key == 3) {
            var x = e.clientX;
            var y = e.clientY;
            $("#right_menu").css({left: x, top: y, display: 'block'});
            var modelId = $(this).find('input').val();
            var blockId = $(this).attr("id");
            $('#right_menu').attr('blockId', blockId);
            $('#right_menu').attr('data-val', modelId);
            $('#right_menu').attr('CopyModelId', blockId);
            $('#right_menu').attr('blockHtml', $(this).next().prop("outerHTML"));
            var existSuccess = $(this).find('div').hasClass("success-type");//存在跑通过成功没
            var existFail = $(this).find('div').hasClass("fail-type");//存在通跑过失败没

            //查看日志的判断，如果算子正在运行中，就不能查看日志
            /* if (runningState == true) {
                 $('#lookLogResult').hide();
                 $('#save-model').hide();
                 $('#lookDataResult').hide();
             } else {*/
            //查看数据的判断
            if (existSuccess == true) {
                $('#lookDataResult').show();
                //下面判断是不是要保存的模型
                eachModels(modelId)
            } else {
                $('#lookDataResult').hide();
                $('#save-model').hide();
            }
            if (existSuccess == true || existFail == true) {
                $('#lookLogResult').show();
            } else {
                $('#lookLogResult').hide();
            }
        }
        return false;
    });
    //右键空白功能
    $('.operatorTitle').on('contextmenu', '.canvasBlock', function (e) {
        e.stopPropagation();
        var key = e.which;
        if (key == 3) {
            var x = e.clientX;
            var y = e.clientY;
            $("#right_menu").css({left: x, top: y, display: 'block'});
            $('#copy').hide();
            $('#paste').show();
            $('#lookDataResult').hide();
            $('#lookLogResult').hide();
            $('#save-model').hide();
            $('#field-des').hide();
            $('#deleteLi').hide();
            $("#visualization").hide();
        }
        return false;
    });
    //空白取消右键功能
    $('.canvasBlock').on('mousedown', function (e) {
        e.stopPropagation();
        var key = e.which;
        if (key == 1) {
            $("#right_menu").css('display', 'none');
        }
    });

}

var copyHtml = '';
var blocksCopy = [];
var notesCopy = [];
var k = 0;
//------------------------右键复制、粘贴功能------------------------
function copyAndPaste() {
    $('#copy').on('click', function () {
        copyHtml = $(this).parents('ul').attr('CopyModelId');
    });
    $('#paste').on('click', function (event) {
        blocksCopy.splice(0, blocksCopy.length);//清空数组
        $(".box").each(function (idx, elem) {
            var $elem = $(elem);
            var name = $elem[0].dataset.nodename;
            var fontClass = $elem.find(".myicon")[0].classList[2];
            var fontIconClass = $elem.find(".fa")[0].classList[1];
            blocksCopy.push({
                BlockId: $elem.attr('id'),
                BlockContent: $elem.find(".boxBody").text().replace(/\s/g, ""),
                BlockX: parseInt($elem.css("left"), 10),
                BlockY: parseInt($elem.css("top"), 10),
                BlockBorClass: $elem[0].classList[1],
                BlockBgClass: $elem[0].classList[2],
                BlockFontClass: fontClass,
                BlockFontIconClass: fontIconClass,
                Name: name
            });
        });
        notesCopy.splice(0, notesCopy.length);//清空数组
        $(".noteDiv").each(function (idx, elem) {
            var $elem = $(elem);
            console.log($elem);
            notesCopy.push({
                NoteId: $elem.attr('id'),
                NoteContent: $elem.find(".noteTextarea").val(),
                NoteX: parseInt($elem.css("left"), 10),
                NoteY: parseInt($elem.css("top"), 10),
                NoteBgColor: $elem.find(".noteTextarea").css("background-color"),
                NoteTextAlign: $elem.find(".noteTextarea").css("text-align")
            });
        });
        var left = event.clientX - $('#canvasBody').offset().left;
        var top = event.clientY - $('#canvasBody').offset().top;
        var model = copyHtml.substring(0, 5);
        //粘贴是算子还是注释
        if (model == "state") {
            if (blocksCopy.length > 0) {
                for (i = 0; i < blocksCopy.length; i++) {
                    if (blocksCopy[i].BlockId == copyHtml) {
                        createNode1(event, blocksCopy[i], left, top);
                    }
                }

            }
        } else {
            if (notesCopy.length > 0) {
                for (i = 0; i < notesCopy.length; i++) {
                    if (notesCopy[i].NoteId == copyHtml) {
                        var noteId = $("div.noteDiv:last").attr("id");
                        var noteId1;
                        if (noteId) {
                            var lastIdNum = parseInt(noteId.substring(6));
                            k = lastIdNum;
                            k++;
                            noteId1 = "noteId" + k;
                            createNoteWrite(event, notesCopy[i], left, top, noteId1);
                        }

                    }
                }

            }
        }


        console.log(copyHtml);

    })
}
// ------------------------右键粘贴function------------------------
    function createNode1(event, block, left, top) {
    console.log(configObj);

    var lastId = $("div.box:last").attr("id");
    if (lastId) {
        var lastIdNum = parseInt(lastId.substring(10));
        i = lastIdNum;
    }
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
    var modelList = $(".operatorBlock").find("h3.ui-draggable");
    for (var j = 0; j < modelList.length; j++) {
        if ($(modelList[j]).text() == html) {
            var egHtml = $(modelList[j]).siblings(".nameEnglish").val();
        }
    }
    var tem = egHtml.replace(/\s/g, "");
    var modelId = $("#" + tem).val();





    i++;
    id = "stateModel" + i;

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
        endpointHoverStyle: endpointHoverStyle,
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: 1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    var linkCircleOut = {
        endpoint: "Dot",  //端点的形状
        endpointStyle: endpointStyle,
        endpointHoverStyle: endpointHoverStyle,
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    //model样式
    var linkSquareIn = {
        endpoint: ["Rectangle", {width: 10, height: 10}],  //端点的形状
        endpointStyle: endpointStyle,
        endpointHoverStyle: rectHoverStyle,
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: 1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    var linkSquareOut = {
        endpoint: ["Rectangle", {width: 10, height: 10}],  //端点的形状
        endpointStyle: endpointStyle,
        endpointHoverStyle: rectHoverStyle,
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
    // view样式
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
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
        connector: ["Bezier", {stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true}],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: false,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", {width: 10, length: 10, location: 1}]]
    };
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

    $("#" + id).css("left", left).css("top", top);
    createInterface(tem, id);
    jsPlumb.draggable(id, {containment: false});

    if (block) {
        configModel("", id, modelId, html, tem, bgClass);
        var modelInterface = $("#" + id).nextUntil(".box");
        $("#" + id).css("box-shadow", "2px 2px 19px #aaa");//选中效果
        $("#" + id).siblings(".box").css("box-shadow", "2px 2px 19px rgba(9,84,109,0.1)");//选中效果
        $("#" + id).css("z-index", "9");//选中层级提高
        modelInterface.css("z-index", "10");//选中层级提高
        $("#" + id).siblings(".box").css("z-index", "0");
        $(".jtk-endpoint").not(modelInterface).css("z-index", "1");
    }
}
// ------------------------右键添加注释功能------------------------
    function addNotes() {
        var m = 0;
        $('#addNote').on('click', function (event) {
            var x = event.clientX - $('#canvasBody').offset().left;
            var y = event.clientY - $('#canvasBody').offset().top;
            var lastId = $("div.noteDiv:last").attr("id");
            if (lastId) {
                var lastIdNum = parseInt(lastId.substring(6)) + 1;
                m = lastIdNum;
                var id = "noteId" + m
            } else {
                m++;
                var id = "noteId" + m;
            }
            var str = '';
            str = '<div class="noteDiv"  id="' + id + '">' +
                '<div class="noteContent">' +
                '<div class="noteMenu">' +
                '<div class="noteMenuAlign">' +
                '<i class="align1 iconfont icon-zuoduiqi1"></i>' +
                '<i class="align2 iconfont icon-juzhong1"></i>' +
                '<i class="align3 iconfont icon-youduiqi"></i>' +
                '</div>' +
                '<div class="menuColorSelect">' +
                '<div class="menuColorDiv">' +
                '<div class="menuColorfather"></div>' +
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
                '<textarea class="noteTextarea"></textarea>' +
                '<div class=\"noteInput\" ></div>' +
                '</div>' +
                '</div>';
    //     str = '<div class="noteDiv" style="width:180px;height:140px;" id="' + id + '"><textarea style="width:100%;height:100%;"></textarea></div>';
    //
    //     $("#canvasBlock").append(str);
    //     $("#" + id).css("left", x).css("top", y);
    // //    jsPlumb.draggable(id, {containment: false});
    // //     jsPlumb.draggable(id);
    //         $("#canvasBody").off("mousedown");
    //     $(".noteDiv").resizable({
    //         resize : function(event, ui) {
    //             event.stopPropagation();
    //             $("#"+id +"textarea").off("focus");
    //             // jsPlumb.repaint(ui.helper);
    //         }
    //     });

            $("#canvasBlock").append(str);
            $("#" + id).css("left", x).css("top", y);
            jsPlumb.draggable(id, {containment: false});
            // jsPlumb.destroyDraggable(id);
            $(".noteDiv").resizable({
                start:function (event, ui) {
                    jsPlumb.destroyDraggable(ui.element[0].id);
                    $("#canvasBlock").off("mousedown");
                },
                resize : function(event, ui) {
                    event.stopPropagation();
                    $("#"+id +"textarea").off("focus");
                    // jsPlumb.repaint(ui.helper);
                },
                stop:function (event, ui) {
                    jsPlumb.draggable(ui.element[0].id, {containment: false});
                },
                // aspectRatio: true,
                minWidth: 120,
                minHeight: 120
                // maxWidth: 600,
                // maxHeight:600
            });
        });
    }
    // 注释切换事件
    function notesStyle() {
    // ============添加注释切换事件
    $('#canvasBody').on('click', '.menuColorList div', function () {
        var bg = $(this).attr('bgcolor');
        $(this).parent().prev().find(".menuColorfather").css("background", bg);//变背景色
        $(this).parent().parent().parent().next().css("background", bg);
    });
    $('#canvasBody').on('click', '.menuColorList div', function () {
        var bg = $(this).attr('bgcolor');
        $(this).parent().prev().find(".menuColorfather").css("background", bg);//变背景色
        $(this).parent().parent().parent().next().css("background", bg);
    });
    //对齐颜色
    $('#canvasBody').on('click', '.noteMenuAlign .align1', function () {
        $(this).css("color", "#1bbc9b");
        $(this).next().css("color", "#000000");
        $(this).next().next().css("color", "#000000");
        $(this).parent().parent().next().css("text-align", "left");
    });
    $('#canvasBody').on('click', '.noteMenuAlign .align2', function () {
        $(this).prev().css("color", "#000000");
        $(this).css("color", "#1bbc9b");
        $(this).next().css("color", "#000000");
        $(this).parent().parent().next().css("text-align", "center");
    });
    $('#canvasBody').on('click', '.noteMenuAlign .align3', function () {
        $(this).prev().prev().css("color", "#000000");
        $(this).prev().css("color", "#000000");
        $(this).css("color", "#1bbc9b");
        $(this).parent().parent().next().css("text-align", "right");
    });
    //删除标签
    $('#canvasBody').on('click', '.menuDelete', function () {
        $(this).parent().parent().parent().remove();
    });
    $("#canvasBody").on("blur", '.noteTextarea', function () {
        $(this).next().show();
        $(this).css({"border":"none"});
        $(this).css("overflow", "hidden");
        $(this).next().css("top", "0px");
        $(this).prev().hide();
    });

    $("#canvasBody").on("dblclick", '.noteInput', function () {
        $(this).hide();
        $(this).prev().focus();
        $(this).prev().css({"border":"1px solid #f89415"});
        $(this).prev().css("overflow", "visible");
        $(this).prev().prev().show();
    });
    $("#canvasBody").on("blur", '.noteInput', function () {
        $(this).prev().prev().show();
        $(this).prev().css("overflow", "visible");
    });

    $("#canvasBody").on('click', '.menuColorDiv', function () {
        console.log($(this));
        if ($(this).parent().find(".menuColorList").hasClass("show")) {
            $(this).find(".selectColor").empty();
            $(this).find(".selectColor").append("<i class=' iconfont icon-shang2'></i>");
            $(this).parent().find(".menuColorList").hide().removeClass("show");

        } else {
            $(this).find(".selectColor").empty();
            $(this).find(".selectColor").append("<i class=' iconfont icon-xia1'></i>")
            $(this).parent().find(".menuColorList").show().addClass("show");
        }
    });
    // ================添加注释切换事件end
}
// ------------------------添加注释end------------------------
// ------------------------注释右键功能------------------------
    function notesRightClick() {
        $("#canvasBlock").on('contextmenu', '.noteDiv', function (e) {
            e.stopPropagation();
            $('#lookDataResult').hide();
            $('#lookLogResult').hide();
            $('#save-model').hide();
            $('#field-des').hide();
            $("#visualization").hide();
            $('#addNote').show();
            $('#copy').show();
            $('#paste').show();
            $('#deleteLi').show();
            var key = e.which;
            if (key == 3) {
                var x = e.clientX;
                var y = e.clientY;
                $("#right_menu").css({left: x, top: y, display: 'block'});
                var noteId = $(this).attr('id')
                $('#right_menu').attr('CopyModelId', noteId);

            }
            return false;
        });
    }
// ------------------------注释右键功能end------------------------
// ------------------------右键删除功能------------------------
    function rightDetele() {
    $('#deleteLi').on('click', function (e) {
        copyHtml = $(this).parents('ul').attr('CopyModelId');
        var model = copyHtml.substring(0, 5);
        //删除是算子还是注释
        if (model == "state") {
            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == copyHtml) {
                    configObj.splice(i, 1);
                }
            }
            var conn = $("#" + copyHtml);
            jsPlumb.remove(conn);
            $(".page-quick-sidebar-wrapper").css({"z-index": -1});
        } else {
            $("#" + copyHtml).remove()
        }
        $("#right_menu").hide();
        return false;
    });
}
// ------------------------右键删除功能end------------------------

// ------------------------右键查看运行结果------------------------
    function lookDataRes() {
        //查看结果的多种情况
        $('#lookDataResult').on('click', function () {
            $(".enlarge_model_btn").empty();
            $(".enlarge_model_btn").append("<i class='icon iconfont icon-quanping' value='0' ></i>");
            var modelId = $(this).parent().attr("data-val");
            var blockId = $(this).parent().attr("copymodelid");
            /*    //输出数据
             */
            dataIds = [1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 40, 41, 42, 43, 44, 45, 48, 49, 50, 52, 53, 56, 58, 76, 77, 82, 85,87,88,89,93,94,95,96,97,98,99,112,123,129];
            //python和R算子输出
            pythonModelIds = [90, 91, 92, 100,101,104, 127, 128];
            //输出数据和模型
            dataAndModelIds = [59, 60, 69,130,131,135,136,137,138];
            //输出模型
            modelIds = [70,102,106,107,108,109,111,126,132];
            for (var i = 0; i < dataIds.length; i++) {
                if (modelId == dataIds[i]) {
                    getActionResult4Data(blockId);
                    break;
                } else if (modelId == pythonModelIds[i]) {
                    getActionResult4Python(blockId);
                    break;
                } else if (modelId == dataAndModelIds[i]) {
                    getActionResult4DataAndModel(blockId);
                    break;
                } else if (modelId == modelIds[i]) {
                    getActionResult4Model(blockId);
                    break;
                }
            }
            if (modelId == 55) {  //已训练的模型
                var blockName = null;
                for (var i = 0; i < configObj.length; i++) {
                    if (blockId == configObj[i].BlockId) {
                        blockName = configObj[i].BlockName
                    }
                }
                getModelTrainedResult(blockId, blockName);
            } else if (modelId == 57) {  //写入csv(V4)因为输出路径文件名可以自定义，重新分类
                var configVal;
                var outputPath;
                for (var i = 0; i < configObj.length; i++) {
                    if (modelId == configObj[i].id) {
                        var data = configObj[i].data
                        for (var j = 0; j < data.length; j++) {
                            if (data[j].configId == "config244") {
                                configVal = data[j].configVal
                            }
                            if (data[j].configId == "config242") {
                                outputPath = data[j].configVal
                            }
                        }
                    }
                }
                getActionResult4WriteCSV(blockId, configVal, outputPath);
            } else if (modelId == 83 || modelId == 84) {
                //输出数据、模型、图片
                getActionResult4PythonR(blockId);
            } else if (modelId == 86) {
                //输出两个数据
                getActionResult4DoubleData(blockId);
            }
            // else {
            //     //输出模型
            //     getActionResult4Model(blockId);
            //     // $("#lookDataResultFrame").modal("toggle");//查看结果
            // }


        });
        $('.selectDataModel').on('click', function () {
            if ($(this).parent().next().children('ul').hasClass('dn')) {
                $(this).parent().next().children('ul').removeClass('dn');
            } else {
                $(this).parent().next().children('ul').addClass('dn');
            }

        });
        $('#lookDataAndModelResultFrame ').on('click', '.filter ul li', function () {
            $(this).addClass('filter-active').siblings('li').removeClass('filter-active');
            var txt = $(this).text();
            $('#titleName').html(txt);
            if (txt == "查看数据") {
                $("#lookDataDiv").show();
                $("#lookModelDiv").hide();
            } else {
                $("#lookDataDiv").hide();
                $("#lookModelDiv").show();
            }
            $(this).parents('ul').addClass('dn')
        });
        //查看两个数据结果的切换
        $('#lookTwoDataResultFrame ').on('click', '.filter ul li', function () {
            $(this).addClass('filter-active').siblings('li').removeClass('filter-active');
            var txt = $(this).text();
            $('#lookOneData').html(txt);
            var data="";
            if (txt == "数据查看1") {
                data=towDataList[0];
            } else {
                data=towDataList[1];
                // $("#lookDataDiv").hide();
                // $("#lookModelDiv").show();
            }
            var th = "";
            var tr = "";
            var dataLength = 100;
            var columns = 1;
            if (data.length < 100) {
                dataLength = data.length;
            }
            for (var j = 0; j < data[0].length; j++) {
                th += "<th>" + data[0][j] + "</th>";
            }
            for (var i = 1; i < dataLength; i++) {
                if (data[i].length > columns) {
                    columns = data[i].length;
                }
                tr += "<tr>";
                for (var j = 0; j < data[i].length; j++) {
                    tr += "<td>" + data[i][j] + "</td>";
                }
                tr += "</tr>";
            }
            $("#resultheadOne").empty();
            $("#resultheadOne").html(th);
            $("#resultbodyOne").empty();
            $("#resultbodyOne").html(tr);
            $(this).parents('ul').addClass('dn')
        });
    }
    // 运行结果方法
        /**
         * 右键查看算子运行结果，针对输出数据的算子
         * @param blockId
         */
        function getActionResult4Data(blockId) {
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getactionresult4data.do",
                type: 'GET',
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId},
                success: function (data) {
                    console.log(data);
                    // alert(data[0][0])
                    var th = "";
                    var tr = "";
                    var dataLength = 100;
                    var columns = 1;
                    if (data.length < 100) {
                        dataLength = data.length;
                    }
                    for (var j = 0; j < data[0].length; j++) {
                        th += "<th>" + data[0][j] + "</th>";
                    }
                    for (var i = 1; i < dataLength; i++) {
                        if (data[i].length > columns) {
                            columns = data[i].length;
                        }
                        tr += "<tr>";
                        for (var j = 0; j < data[i].length; j++) {
                            tr += "<td>" + data[i][j] + "</td>";
                        }
                        tr += "</tr>";
                    }
                    $("#resulthead").empty();
                    $("#resulthead").html(th);
                    $("#resultbody").empty();
                    $("#resultbody").html(tr);
                    $("#lookDataResultFrame").modal("toggle");//查看模型结果
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }

            });

        }
        //查看多个数据显示
        var towDataList=[];
        function getActionResult4DoubleData(blockId){
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getactionresult4doubledata.do",
                type: 'GET',
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId},
                success: function (msg) {
                    towDataList.splice(0, towDataList.length);//清空数组
                    $("#logsList").empty();
                    var databaseJson = msg;
                    for (var p in databaseJson) {
                        towDataList.push(databaseJson[p]);
                    }
                    // alert(data[0][0])
                    var data="";
                    if(towDataList[0]!="undefine" || towDataList[0] !=""){
                        data=towDataList[0];
                    }else{
                        data=towDataList[1];
                    }
                    console.log(towDataList);
                    var th = "";
                    var tr = "";
                    var dataLength = 100;
                    var columns = 1;
                    if (data.length < 100) {
                        dataLength = data.length;
                    }
                    for (var j = 0; j < data[0].length; j++) {
                        th += "<th>" + data[0][j] + "</th>";
                    }
                    for (var i = 1; i < dataLength; i++) {
                        if (data[i].length > columns) {
                            columns = data[i].length;
                        }
                        tr += "<tr>";
                        for (var j = 0; j < data[i].length; j++) {
                            tr += "<td>" + data[i][j] + "</td>";
                        }
                        tr += "</tr>";
                    }
                    $("#resultheadOne").empty();
                    $("#resultheadOne").html(th);
                    $("#resultbodyOne").empty();
                    $("#resultbodyOne").html(tr);
                    $("#lookTwoDataResultFrame").modal("toggle");//查看结果
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }

            });

        }

        /**
         * 右键查看算子运行结果，输出模型
         * @param blockId
         */
        function getActionResult4Model(blockId) {
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getactionresult4model.do",
                type: "GET",
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId},
                success: function (data) {
                    var modelAttr = data.resultmodel;
                    var html = "";
                    for (var i = 0; i < modelAttr.length; i++) {
                        html += modelAttr[i] + "<br>";
                    }
                    $("#resultmodel").empty();
                    $("#resultmodel").html(html);
                    $("#lookModelResultFrame").modal("toggle");//查看模型结果
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }
            })
        }
        /**
         * 右键查看已训练的模型的运行结果
         * @param blockId
         */
        function getModelTrainedResult(blockId, blockName) {
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getModelTrainedResult.do",
                type: 'GET',
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId, "modelTrainedName": blockName},
                success: function (data) {
                    var table = data.resulttable;
                    var model = data.resultmodel;
                    if (table.length != 0 && typeof(model) != "undefined") {
                        var th = "";
                        var tr = "";
                        var dataLength = 100;
                        var columns = 1;
                        if (table.length < 100) {
                            dataLength = table.length;
                        }
                        for (var j = 0; j < table[0].length; j++) {
                            th += "<th>" + table[0][j] + "</th>";
                        }
                        for (var i = 1; i < dataLength; i++) {
                            if (table[i].length > columns) {
                                columns = table[i].length;
                            }
                            tr += "<tr>";
                            for (var j = 0; j < table[i].length; j++) {
                                tr += "<td>" + table[i][j] + "</td>";
                            }
                            tr += "</tr>";
                        }

                        var html = "";
                        for (var i = 0; i < model.length; i++) {
                            html += model[i] + "<br>";
                        }
                        $("#resulthead1").empty();
                        $("#resultbody1").empty();
                        $("#resulthead1").html(th);
                        $("#resultbody1").html(tr);
                        $("#resultdata1").empty();
                        $("#resultdata1").html(html)
                        $("#lookDataAndModelResultFrame").modal("toggle");//查看
                    } else {
                        var html = "";
                        for (var i = 0; i < model.length; i++) {
                            html += model[i] + "<br>";
                        }
                        $("#resultmodel").empty();
                        $("#resultmodel").html(html);
                        $("#lookModelResultFrame").modal("toggle");//查看模型结果
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
        /**
         * 右键查看算子运行结果（针对两种输出)
         * @param blockId
         */
        function getActionResult4DataAndModel(blockId) {
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getactionresult4modelanddata.do",
                type: 'GET',
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId},
                success: function (data) {
                    // $("#lookModelDiv").hide();
                    var table = data.resulttable;
                    var model = data.resultmodel;
                    var th = "";
                    var tr = "";
                    var dataLength = 100;
                    var columns = 1;
                    if (table.length < 100) {
                        dataLength = table.length;
                    }
                    for (var j = 0; j < table[0].length; j++) {
                        th += "<th>" + table[0][j] + "</th>";
                    }
                    for (var i = 1; i < dataLength; i++) {
                        if (table[i].length > columns) {
                            columns = table[i].length;
                        }
                        tr += "<tr>";
                        for (var j = 0; j < table[i].length; j++) {
                            tr += "<td>" + table[i][j] + "</td>";
                        }
                        tr += "</tr>";
                    }

                    var html = "";
                    for (var i = 0; i < model.length; i++) {
                        html += model[i] + "<br>";
                    }
                    $("#resulthead1").empty();
                    $("#resultbody1").empty();
                    $("#resulthead1").html(th);
                    $("#resultbody1").html(tr);
                    $("#resultdata1").empty();
                    $("#resultdata1").html(html)
                    $("#lookDataAndModelResultFrame").modal("toggle");//查看
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }
            })
        }

        function getActionResult4WriteCSV(blockId, configVal, outputPath) {
    var flowId = $("#flowId").val();
    $.ajax({
        url: basePath + "/drag/flow/getActionResult4WriteCSV.do",
        type: 'GET',
        dataType: "json",
        data: {"flowId": flowId, "blockId": blockId, "fileName": configVal, "outputStr": outputPath},
        success: function (data) {
            console.log(data);
            // alert(data[0][0])
            var th = "";
            var tr = "";
            var dataLength = 100;
            var columns = 1;
            if (data.length < 100) {
                dataLength = data.length;
            }
            for (var j = 0; j < data[0].length; j++) {
                th += "<th>" + data[0][j] + "</th>";
            }
            for (var i = 1; i < dataLength; i++) {
                if (data[i].length > columns) {
                    columns = data[i].length;
                }
                tr += "<tr>";
                for (var j = 0; j < data[i].length; j++) {
                    tr += "<td>" + data[i][j] + "</td>";
                }
                tr += "</tr>";
            }
            $("#resulthead").empty();
            $("#resulthead").html(th);
            $("#resultbody").empty();
            $("#resultbody").html(tr);
            $("#lookDataResultFrame").modal("toggle");//查看模型结果
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }

    });
}
        function getActionResult4Python(blockId) {
    var flowId = $("#flowId").val();
    $.ajax({
        url: basePath + "/drag/flow/getactionresult4python.do",
        type: 'GET',
        dataType: "json",
        data: {"flowId": flowId, "blockId": blockId},
        success: function (data) {
            console.log(data);
            // alert(data[0][0])
            var th = "";
            var tr = "";
            var dataLength = 100;
            var columns = 1;
            if (data.length < 100) {
                dataLength = data.length;
            }
            for (var j = 0; j < data[0].length; j++) {
                th += "<th>" + data[0][j] + "</th>";
            }
            for (var i = 1; i < dataLength; i++) {
                if (data[i].length > columns) {
                    columns = data[i].length;
                }
                tr += "<tr>";
                for (var j = 0; j < data[i].length; j++) {
                    tr += "<td>" + data[i][j] + "</td>";
                }
                tr += "</tr>";
            }
            $("#resulthead").empty();
            $("#resulthead").html(th);
            $("#resultbody").empty();
            $("#resultbody").html(tr);
            $("#lookDataResultFrame").modal("toggle");//查看模型结果
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }

    });

}
        function getActionResult4PythonR(blockId) {
    var flowId = $("#flowId").val();
    $.ajax({
        url: basePath + "/drag/flow/RAndPythonResult.do",
        type: "GET",
        dataType: "json",
        data: {"flowId": flowId, "blockId": blockId},
        success: function (data) {
            console.log(data)
            var table = data.resulttable;
            var model = data.resultmodel;//resultmodel  resulttable
            var th = "";
            var tr = "";
            var dataLength = 100;
            var columns = 1;
            //查看数据和模型
            if(table.length>0 && model.length>0){
                if (table.length < 100) {
                    dataLength = table.length;
                }
                for (var j = 0; j < table[0].length; j++) {
                    th += "<th>" + table[0][j] + "</th>";
                }
                for (var i = 1; i < dataLength; i++) {
                    if (table[i].length > columns) {
                        columns = table[i].length;
                    }
                    tr += "<tr>";
                    for (var j = 0; j < table[i].length; j++) {
                        tr += "<td>" + table[i][j] + "</td>";
                    }
                    tr += "</tr>";
                }
                $("#resulthead1").empty();
                $("#resultbody1").empty();
                $("#resulthead1").html(th);
                $("#resultbody1").html(tr);
                var html = "";
                for (var i = 0; i < model.length; i++) {
                    html += model[i] + "<br>";
                }
                $("#resultdata1").empty();
                $("#resultdata1").html(html)
                $("#lookDataAndModelResultFrame").modal("toggle");//查看
            }else if(table.length>0 && (typeof(model) == "undefined" || model.length==0)){
                if (table.length < 100) {
                    dataLength = table.length;
                }
                for (var j = 0; j < table[0].length; j++) {
                    th += "<th>" + table[0][j] + "</th>";
                }
                for (var i = 1; i < dataLength; i++) {
                    if (table[i].length > columns) {
                        columns = table[i].length;
                    }
                    tr += "<tr>";
                    for (var j = 0; j < table[i].length; j++) {
                        tr += "<td>" + table[i][j] + "</td>";
                    }
                    tr += "</tr>";
                }
                $("#resulthead").empty();
                $("#resultbody").empty();
                $("#resulthead").html(th);
                $("#resultbody").html(tr);
                $("#lookDataResultFrame").modal("toggle");//查看
            }else if(table.length==0 && model.length>0){
                var html1 = "";
                for (var i = 0; i < model.length; i++) {
                    html1 += model[i] + "<br>";
                }
                $("#resultmodel").empty();
                $("#resultmodel").html(html1)
                $("#lookModelResultFrame").modal("toggle");//查看
            }else {
                toastr.warning("该数据已过期，请重新运行！")
            }
        },
        error: function (data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })


    // var imagePath="/python_shell/output/img/127_stateModel11/img/pic_1.png"+",/python_shell/output/img/127_stateModel11/img/pic_2.png";
    // window.location.href=basePath+'/drag/flow/downloadresultimage.do?imagePath='+imagePath+'';
}
// ------------------------右键查看运行结果end------------------------

// ------------------------右键查看运行日志------------------------
    function lookLog() {
        var logList = [];
        /**查看日志切换**/
        $('#selectLogs').on('click', function () {
            if ($(this).parent().next().children('ul').hasClass('dn')) {
                $(this).parent().next().children('ul').removeClass('dn');
            } else {
                $(this).parent().next().children('ul').addClass('dn');
            }
        });
        $('#lookLogResultFrame ').on('click', '.filter ul li', function () {
            $(this).addClass('filter-active').siblings('li').removeClass('filter-active');
            var num = $(this).attr('number');
            var txt = $(this).text();
            $('#logName').html(txt);
            console.log(logList);
            $("#actionlogs").empty();
            for (var i = 0; i < logList.length; i++) {
                if (i == num) {
                    if (i == logList.length - 1) {
                        var html = ""
                        for (var j = 0; j < logList[i].length; j++) {
                            html += logList[i][j] + "<br>"
                        }
                        $("#actionlogs").html(html);
                    } else {
                        $("#actionlogs").html(logList[i]);
                    }
                }
            }
            $(this).parents('ul').addClass('dn')
        });
        $('#lookLogResult').on('click', function () {
            $(".enlarge_model_btn").empty();
            $(".enlarge_model_btn").append("<i class='icon iconfont icon-quanping' value='0' ></i>");
            var modelId = $(this).parent().attr("data-val");
            var blockId = $(this).parent().attr("copymodelid");
            var flowId = $("#flowId").val();
            $.ajax({
                url: basePath + "/drag/flow/getactionlogs.do",
                type: 'GET',
                dataType: "json",
                data: {"flowId": flowId, "blockId": blockId},
                success: function (data) {
                    logList.splice(0, logList.length);//清空数组
                    $("#logsList").empty();
                    var databaseJson = data.obj;
                    for (var p in databaseJson) {
                        logList.push(databaseJson[p]);
                    }
                    for (var i = 0; i < logList.length; i++) {
                        var num = i + 1
                        var logslist = "<li number=" + i + "><i class=\"glyphicon glyphicon-ok\"></i><span>查看日志" + num + "</span></li>"
                        $("#logsList").append(logslist);
                    }
                    var h = logList.length * 40 + "px";
                    $("#logsList").css("height", h);
                    $("#logName").html("查看日志1");
                    $("#actionlogs").html(logList[0]);
                    $("#lookLogResultFrame").modal("toggle");//查看日志
                },
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }
            });
        });
    }
// ------------------------右键查看运行日志end------------------------

// ------------------------右键保存为训练好的模型------------------------
    function saveModel() {
        $('#save-model').on('click', function () {
            $("#modelName").val("");
            $("#saveModelTrainFrame").modal('toggle');
            var blockId = $(this).parents('ul').attr('blockid');
            var blockName = null;
            for (var i = 0; i < configObj.length; i++) {
                if (blockId == configObj[i].BlockId) {
                    blockName = configObj[i].BlockName
                }
            }
            $("#saveModelTrainFrame").attr('blockName', blockName);
            $("#saveModelTrainFrame").attr('blockid', blockId)
        });
        // 右键保存算子为已训练的模型(model确认处)
        $("#savetrainedmodel").on("click", function () {
            var blockId = $("#saveModelTrainFrame").attr('blockid');
            var blockName = $("#saveModelTrainFrame").attr('blockName');
            var modelName = removeAllSpace($("#modelName").val());
            var flowId = $("#flowId").val();
            if (modelName.length == 0 || modelName.trim().length == 0) {
                toastr.warning("文件名不能为空");
            } else if(specialtest.form()){
                $.ajax({
                    url: basePath + "/drag/flow/settrainedmodel.do",
                    type: "POST",
                    dataType: "json",
                    data: {"flowId": flowId, "blockId": blockId, "blockName": blockName, "modelName": modelName},
                    success: function (data) {
                        if (data.code == 200) {
                            toastr.success(data.msg);
                            $("#modelsize").html(parseInt($("#modelsize").html()) + 1)
                        } else {
                            toastr.error(data.msg);
                        }
                        $("#saveModelTrainFrame").modal('toggle');
                    },
                    error:function(data){
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    }
                })
            }
        });

        /**
         * 保存之后异步加载已训练的模型算子列表
         */
        $("#modeltrained").on("click", function (e) {
            $.ajax({
                url: basePath + "/drag/modeltrained/gettrainedmodel.do",
                type: 'GET',
                dataType: "json",
                success: function (json) {
                    $("#trainedul").empty();//清空数据区
                    var modellist = "";
                    var list = json;
                    $.each(list, function (index, array) { //遍历json数据列
                        console.log(list[index]);
                        modellist += "<li class='mt-list-item border-yellow'>"
                            + "<div class='list-icon-container'>"
                            + "<i class='fa fa-tasks'></i>"
                            + "</div>"
                            + "<div class='list-item-content'>"
                            + "<h3 class='ui-draggable k_suanzi-width'>" + list[index].name + "</h3>"
                            + "<input type='hidden' id='" + list[index].nameRmNull + "' value='" + list[index].id + "' />"
                            + "<input type='hidden' class='leftNumber' value='" + list[index].leftNumber + "' />"
                            + "<input type='hidden' class='rightNumber' value='" + list[index].rightNumber + "' />"
                            + "<input type='hidden' class='modelInfo' value='" + list[index].flowDetails + "' />"
                            + "<input type='hidden' class='nameEnglish' value='" + list[index].nameEnglish + "' />"
                            + "<input type='hidden' id='modelTrainedId' value='" + list[index].modelTrainedId + "' />"
                            + "</div>"
                            + "</li>";
                    });

                    $("#trainedul").append(modellist);
                },
                complete: function () {
                    $(".dragList").find("h3").draggable({
                        drag: draggingModel,
                        helper: draggingModel,
                        scope: "ss"
                    });
                },
                error: function (data) {
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                    alert("数据加载失败");
                }
            });

        });
    }
        //判断能保存模型的算子有哪些
    function eachModels(modelId) {
        var models = [55,59,60,65,69,70,83,84,102,105,106,107,108,109,110,111,124,126,130,131,132];
        for (var i = 0; i < models.length; i++) {
            if (models[i] == modelId) {
                $('#save-model').show();
            }
        }
    }
// ------------------------右键保存为训练好的模型end------------------------

// ------------------------右键添加字段描述------------------------
    function fieldDes() {
        $("#changeType").modal('toggle')
    }
    $('#field-des').on('click', function () {
        var modelId = $(this).parents('ul').attr('data-val');
        var blockId = $(this).parents('ul').attr('blockid');
        if (field.length > 0) {
            if (modelId == 1) {
                var m = true;
                for (var i = 0; i < field.length; i++) {
                    // console.log(field);
                    var index = i;
                    if (field[index].type == 'csv' && field[index].blockId == blockId) {
                        console.log(i);
                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                        str += '<tbody>';
                        for (var j = 0; j < field[i].data.length; j++) {
                            str += '<tr>' +
                                '<td><span class="syl">' + field[i].data[j][0] + '</span></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][1] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                '</td>' +
                                '<td><span class="syl">' + field[i].data[j][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][3] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                '</td>' +
                                '</tr>'
                        }
                        str += '</tbody>';
                        $('#changeType table').html(str);
                        $('#changeType').attr('type', 'csv');
                        $('#changeType').attr('blockId', blockId);
                        fieldDes();

                        m = true;
                        return m;
                    } else {
                        m = false;
                    }
                }
                if (m == false) {
                    for (var i = 0; i < configObj.length; i++) {
                        if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                            var dataParF = [];
                            for (var j = 0; j < configObj[i].data.length; j++) {
                                var configType = configObj[i].data[j].configType;
                                var configName = configObj[i].data[j].configName;
                                var url = configObj[i].data[j].configVal;
                                if (configType == 3 && url != "") {
                                    // 截取路径
                                    var url1 = "/" + url.split("/")[url.split("/").length - 2] + "/" + url.split("/")[url.split("/").length - 1];
                                    console.log(url1);
                                    dataParF.push(url1);
                                }
                            }
                            if (dataParF.length < 1) {
                                alert('请先选择路径');
                            } else {
                                $.ajax({
                                    url: basePath + "/drag/flow/adddescription4csv.do",
                                    type: 'get',
                                    data: {inputPath: dataParF[0], tableHeader: false},
                                    dataType: "json",
                                    success: function (data) {
                                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                        str += '<tbody>';
                                        for (var i = 0; i < data.length; i++) {
                                            str += '<tr>' +
                                                '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][1] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                                '</td>' +
                                                '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][3] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                                '</td>' +
                                                '</tr>'
                                        }
                                        str += '</tbody>';
                                        $('#changeType table').html(str);
                                        $('#changeType').attr('type', 'csv');
                                    },
                                    error:function(data){
                                        if(data.responseText == "AjaxSessionTimeout"){
                                            window.location.href=basePath;
                                            return;
                                        }
                                    }
                                });
                                fieldDes();
                            }
                            $('#changeType').attr('blockId', blockId);
                        }
                    }
                }
            } else if (modelId == 53) {
                var m = true;
                for (var i = 0; i < field.length; i++) {
                    if (field[i].type == 'database' && field[i].blockId == blockId) {
                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                        str += '<tbody>';
                        for (var j = 0; j < field[i].data.length; j++) {
                            str += '<tr>' +
                                '<td><span class="syl">' + field[i].data[j][0] + '</span></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][1] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                '</td>' +
                                '<td><span class="syl">' + field[i].data[j][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][3] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                '</td>' +
                                '</tr>'
                        }
                        str += '</tbody>';
                        $('#changeType table').html(str);
                        $('#changeType').attr('type', 'database');
                        fieldDes();
                        $('#changeType').attr('blockId', blockId);
                        m = true;
                        return m;
                    } else {
                        m = false;
                    }
                }
                if (m == false) {
                    for (var i = 0; i < configObj.length; i++) {
                        if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                            var dataParT = [];
                            for (var j = 0; j < configObj[i].data.length; j++) {
                                var configType = configObj[i].data[j].configType;
                                var configName = configObj[i].data[j].configName;
                                var url = configObj[i].data[j].configVal;
                                if (configType == 6) {
                                    var splits = url.split("|");
                                    var splits = splits[1];
                                    if (splits == "null") {
                                        break;
                                    } else {
                                        dataParT.push(splits);
                                    }
                                }
                            }
                            if (dataParT.length < 2) {
                                alert('请先选择路径');
                            } else {
                                $.ajax({
                                    url: basePath + "/drag/flow/adddescription4database.do",
                                    type: 'get',
                                    data: {connectName: dataParT[0], tableName: dataParT[1]},
                                    dataType: "json",
                                    success: function (data) {
                                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                        str += '<tbody>';
                                        for (var i = 0; i < data.length; i++) {
                                            str += '<tr>' +
                                                '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][1] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                                '</td>' +
                                                '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][3] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                                '</td>' +
                                                '</tr>'
                                        }
                                        str += '</tbody>';
                                        $('#changeType table').html(str);
                                        $('#changeType').attr('type', 'database');
                                    },
                                    error:function(data){
                                        if(data.responseText == "AjaxSessionTimeout"){
                                            window.location.href=basePath;
                                            return;
                                        }
                                    }
                                });
                                fieldDes();
                            }
                            $('#changeType').attr('blockId', blockId);
                        }
                    }
                }
            } else if (modelId == 56) {
                var m = true;
                for (var i = 0; i < field.length; i++) {
                    // console.log(field);
                    var index = i;
                    if (field[index].type == 'csv' && field[index].blockId == blockId) {
                        console.log(i);
                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                        str += '<tbody>';
                        for (var j = 0; j < field[i].data.length; j++) {
                            str += '<tr>' +
                                '<td><span class="syl">' + field[i].data[j][0] + '</span></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][1] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                '</td>' +
                                '<td><span class="syl">' + field[i].data[j][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                '<td>' +
                                '<span class="choose-type">' +
                                '<span class="syl">' + field[i].data[j][3] + '</span>' +
                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                '</span>' +
                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                '</td>' +
                                '</tr>'
                        }
                        str += '</tbody>';
                        $('#changeType table').html(str);
                        $('#changeType').attr('type', 'csv');
                        $('#changeType').attr('blockId', blockId);
                        fieldDes();

                        m = true;
                        return m;
                    } else {
                        m = false;
                    }
                }
                if (m == false) {
                    for (var i = 0; i < configObj.length; i++) {
                        if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                            var dataParF = [];
                            var tableHeader;
                            for (var j = 0; j < configObj[i].data.length; j++) {
                                var configType = configObj[i].data[j].configType;
                                var configName = configObj[i].data[j].configName;
                                var url = configObj[i].data[j].configVal;
                                if (configType == 3 && url != "") {
                                    // 截取路径
                                    var url1 = "/" + url.split("/")[url.split("/").length - 2] + "/" + url.split("/")[url.split("/").length - 1];
                                    console.log(url1);
                                    dataParF.push(url1);
                                }
                                if (configObj[i].data[j].configId == "config237") {
                                    tableHeader = configObj[i].data[j].configVal
                                }
                            }
                            if (dataParF.length < 1) {
                                alert('请先选择路径');
                            } else {
                                $.ajax({
                                    url: basePath + "/drag/flow/adddescription4csv.do",
                                    type: 'get',
                                    data: {inputPath: dataParF[0], tableHeader: tableHeader},
                                    dataType: "json",
                                    success: function (data) {
                                        var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                        str += '<tbody>';
                                        for (var i = 0; i < data.length; i++) {
                                            str += '<tr>' +
                                                '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][1] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                                '</td>' +
                                                '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                                '<td>' +
                                                '<span class="choose-type">' +
                                                '<span class="syl">' + data[i][3] + '</span>' +
                                                '<i class="icon iconfont icon-yousanjiao"></i>' +
                                                '</span>' +
                                                '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                                '</td>' +
                                                '</tr>'
                                        }
                                        str += '</tbody>';
                                        $('#changeType table').html(str);
                                        $('#changeType').attr('type', 'csv');
                                    },
                                    error:function(data){
                                        if(data.responseText == "AjaxSessionTimeout"){
                                            window.location.href=basePath;
                                            return;
                                        }
                                    }
                                });
                                fieldDes();
                            }
                            $('#changeType').attr('blockId', blockId);
                        }
                    }
                }
            }
        } else {
            if (modelId == 1) {
                for (var i = 0; i < configObj.length; i++) {
                    if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                        var dataParF = [];
                        for (var j = 0; j < configObj[i].data.length; j++) {
                            var configType = configObj[i].data[j].configType;
                            var configName = configObj[i].data[j].configName;
                            var url = configObj[i].data[j].configVal;
                            if (configType == 3) {
                                if (url == "") {
                                    break;
                                } else {
                                    // 截取路径
                                    var url1 = "/" + url.split("/")[url.split("/").length - 2] + "/" + url.split("/")[url.split("/").length - 1];
                                    console.log(url1);
                                    dataParF.push(url1);
                                }
                            }
                        }
                        if (dataParF.length < 1) {
                            alert('请先选择路径');
                        } else {
                            $.ajax({
                                url: basePath + "/drag/flow/adddescription4csv.do",
                                type: 'get',
                                data: {inputPath: dataParF[0], tableHeader: false},
                                dataType: "json",
                                success: function (data) {
                                    var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                    str += '<tbody>';
                                    for (var i = 0; i < data.length; i++) {
                                        str += '<tr>' +
                                            '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][1] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                            '</td>' +
                                            '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][3] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                            '</td>' +
                                            '</tr>'
                                    }
                                    str += '</tbody>';
                                    $('#changeType table').html(str);
                                    $('#changeType').attr('type', 'csv');
                                },
                                error:function(data){
                                    if(data.responseText == "AjaxSessionTimeout"){
                                        window.location.href=basePath;
                                        return;
                                    }
                                }
                            });
                            fieldDes();
                        }
                        $('#changeType').attr('blockId', blockId);
                    }
                }
            } else if (modelId == 53) {
                for (var i = 0; i < configObj.length; i++) {
                    if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                        var dataParT = [];
                        for (var j = 0; j < configObj[i].data.length; j++) {
                            var configType = configObj[i].data[j].configType;
                            var configName = configObj[i].data[j].configName;
                            var url = configObj[i].data[j].configVal;
                            if (configType == 6) {
                                var splits = url.split("|");
                                var splits = splits[1];
                                if (splits == "null") {
                                    break;
                                } else {
                                    dataParT.push(splits);
                                }

                            }
                        }
                        if (dataParT.length < 2 || dataParT[0] == "null" || dataParT[1] == "null") {
                            alert('请先选择路径');
                        } else {
                            $.ajax({
                                url: basePath + "/drag/flow/adddescription4database.do",
                                type: 'get',
                                data: {connectName: dataParT[0], tableName: dataParT[1]},
                                dataType: "json",
                                success: function (data) {
                                    var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                    str += '<tbody>';
                                    for (var i = 0; i < data.length; i++) {
                                        str += '<tr>' +
                                            '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][1] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                            '</td>' +
                                            '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][3] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                            '</td>' +
                                            '</tr>'
                                    }
                                    str += '</tbody>';
                                    $('#changeType table').html(str);
                                    $('#changeType').attr('type', 'database');
                                },
                                error:function(data){
                                    if(data.responseText == "AjaxSessionTimeout"){
                                        window.location.href=basePath;
                                        return;
                                    }
                                }
                            });
                            fieldDes();
                        }
                        $('#changeType').attr('blockId', blockId);
                    }
                }
            } else if (modelId == 56) {
                for (var i = 0; i < configObj.length; i++) {
                    if (modelId == configObj[i].id && blockId == configObj[i].BlockId) {
                        var dataParF = [];
                        for (var j = 0; j < configObj[i].data.length; j++) {
                            var configType = configObj[i].data[j].configType;
                            var configName = configObj[i].data[j].configName;
                            var url = configObj[i].data[j].configVal;
                            var tableHeader;
                            if (configType == 3) {
                                if (url == "") {
                                    break;
                                } else {
                                    // 截取路径
                                    var url1 = "/" + url.split("/")[url.split("/").length - 2] + "/" + url.split("/")[url.split("/").length - 1];
                                    console.log(url1);
                                    dataParF.push(url1);
                                }

                            }
                            if (configObj[i].data[j].configId == "config237") {
                                tableHeader = configObj[i].data[j].configVal
                            }
                        }
                        if (dataParF.length < 1) {
                            alert('请先选择路径');
                        } else {
                            $.ajax({
                                url: basePath + "/drag/flow/adddescription4csv.do",
                                type: 'get',
                                data: {inputPath: dataParF[0], tableHeader: tableHeader},
                                dataType: "json",
                                success: function (data) {
                                    var str = "<thead><tr><th>字段名</th><th>类型</th><th>描述</th><th>标签列</th></tr></thead>";
                                    str += '<tbody>';
                                    for (var i = 0; i < data.length; i++) {
                                        str += '<tr>' +
                                            '<td><span class="syl">' + data[i][0] + '</span></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][1] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>Integer</li><li>String</li><li>Boolean</li><li>Float</li><li>Double</li><li>BigDecimal</li><li>Date</li><li>Time</li><li>Timestamp</li></ul>' +
                                            '</td>' +
                                            '<td><span class="syl">' + data[i][2] + '</span><i id="changeDes" class="icon iconfont icon-miaoshu1"></i></td>' +
                                            '<td>' +
                                            '<span class="choose-type">' +
                                            '<span class="syl">' + data[i][3] + '</span>' +
                                            '<i class="icon iconfont icon-yousanjiao"></i>' +
                                            '</span>' +
                                            '<ul class="dn"><li>是</li><li>否</li></ul>' +
                                            '</td>' +
                                            '</tr>'
                                    }
                                    str += '</tbody>';
                                    $('#changeType table').html(str);
                                    $('#changeType').attr('type', 'csv');
                                },
                                error:function(data){
                                    if(data.responseText == "AjaxSessionTimeout"){
                                        window.location.href=basePath;
                                        return;
                                    }
                                }
                            });
                            fieldDes();
                        }
                        $('#changeType').attr('blockId', blockId);
                    }
                }
            }

        }

    });
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
        // 点击确定保存field
    $('#saveField').on('click', function (blockId, type) {
        blockId = $('#changeType').attr('blockId');
        type = $('#changeType').attr('type');
        saveField(blockId, type);
        $('#changeType').modal('hide')
    });
    function saveField(blockId, type) {
        // 增加保存修改的字段描述
        var data = [];
        $('#changeType table tbody tr').each(function () {
            var json = {};
            $(this).find('td span.syl').each(function (i) {
                json[i] = $(this).text();
            });
            data.push(json)
        });
        for (var i = 0; i < field.length; i++) {
            var index = i;
            if (field[index].blockId == blockId && field[index].type == type) {
                field.splice(field[index], 1);
            }
        }
        field.push({
            blockId: blockId,
            type: type,
            data: data
        });

        console.log(field);
        // 增加保存修改的字段描述end
    }
//-----------------------------------------------------------------------

// ------------------------右键查看数据可视化结果------------------------
    function lookDataVisualization() {
        var blockId;
        $("#visualization").on("click",function (e) {
            e.stopPropagation();
            var modelId = $(this).parent().attr("data-val");
            if(modelId==83 || modelId==84){
                //输出数据、模型、图片
                for (var i=0;i<configObj.length; i++) {
                    if (modelId == configObj[i].id) {
                        blockId=configObj[i].BlockId;
                    }
                }
                getRAndPythonList(1,blockId);
            }

        });
        //全选
        $("#allChoose").on("click",function () {
            $(".visual-box input[name='allChoose']").prop("checked",this.checked);
        });
        //单选
        $(".visual-box").on("click","input[name='allChoose']",function () {
            var dataArray=$(".visual-box input[name='allChoose']:checked");
            if(dataArray.length<1){
                $("#allChoose").prop("checked",false);
            }
        })

        //选择图片上传
        $("#imgListDownlaod").on("click",function (e) {
            e.stopPropagation();
            var dataArray=$(".visual-box input[name='allChoose']:checked");
            if(dataArray.length>0){

                var urlArray=[];
                $.each(dataArray,function () {
                    urlArray.push($(this).val());
                })
                window.location.href=basePath+"/drag/flow/downloadresultimage.do?imagePath="+urlArray.toString();
            }else {
                toastr.warning("请选择");
            }

        })
        //删除图片
        $("#imgListDelete").on("click",function (e) {
            e.stopPropagation();
            var dataArray=$(".visual-box input[name='allChoose']:checked");
            if(dataArray.length>0){

                var urlArray=[];
                $.each(dataArray,function () {
                    urlArray.push($(this).val());
                })
                /// window.location.href=basePath+"/drag/flow/deleteresultimage.do?imagePath="+urlArray;
                $.ajax({
                    url:basePath+"/drag/flow/deleteresultimage.do",
                    type:"get",
                    dataType: "json",
                    data:{"imagePath":urlArray},
                    success:function (data) {
                        if(data==true){
                            getRAndPythonList(1,blockId);
                        }else {
                            toastr.error("删除失败！");
                        }
                    },
                    error:function (data) {
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    }

                })
            }else {
                toastr.warning("请选择");
            }
        })
        //$(".visual-box").ch
    }
    function getRAndPythonList(page,blockId){
        var flowId = $("#flowId").val();
        $.ajax({
            url: basePath + "/drag/flow/RAndPythonImgResult.do",
            type: "GET",
            dataType: "json",
            data: {"flowId": flowId, "blockId": blockId,"page":page},
            success: function (data) {
                $(".visual-box").empty();
                curPage = data.curPage; //当前页
                totalPage = data.totalPage; //总页数
                $("#totalpage").val(totalPage);
                console.log(data)
                var dataArray = data.rows;
                var fileNamelist=data.fileNamelist;
                var html = "";
                for (var i in dataArray) {
                    html+='<div class="visual-img">'+
                        '<div class="visual-img-title"><input type="checkbox" name="allChoose" value="'+fileNamelist[i]+'">'+fileNamelist[i].split("/")[fileNamelist[i].split("/").length-1]+'</div>'+
                        '<div>'+
                        '<img class="imgWidthHeight" src="' + dataArray[i] + '" alt="">'+
                        '</div>'+
                        '</div>'

                }

                $(".visual-box").html(html);
                $("#dataVisualization").modal("show");
            },
            complete: function () { //生成分页条
                paginatorByRAndPython(page,blockId);
            },
            error: function (data) {
                if (data.responseText == "AjaxSessionTimeout") {
                    window.location.href = basePath;
                    return;
                }
            }
        })
    }
        //查看数据分页
    function paginatorByRAndPython(page1,blockId) {
        var fileTotal=$("#totalpage").val();
        $('#RAndPythonPage').jqPaginator({
            totalPages: parseInt(fileTotal),
            visiblePages: 4,
            currentPage: parseInt(curPage),

            first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
            prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
            last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
            onPageChange: function (page) {
                if(page!=curPage){
                    getRAndPythonList(page,blockId);
                }


            }
        });
    }
//-----------------------------------------------------------------------

// ------------------------注释标签用户可自定义大小----------------------
function changeNoteSize() {
    $("#canvasBlock").on("mousedown",".noteDiv",function () {
        var noteId=$(this).parents(".noteDiv").attr("id");
        jsPlumb.destroyDraggable(noteId);
        $("#canvasBlock").off("mousedown");
    });
    $("#canvasBlock").on("mouseup",".ui-icon-gripsmall-diagonal-se",function () {
        var noteId=$(this).parents(".noteDiv").attr("id");
        jsPlumb.draggable(noteId, {containment: false});
        draggableCanvas()
    })
}
//-----------------------------------------------------------------------