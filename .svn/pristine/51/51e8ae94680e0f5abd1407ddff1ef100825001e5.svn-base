var i = 0;

function createNode(event, ui, block){

    console.log(event)
    if(ui){
        var left = parseInt(ui.offset.left - $(this).offset().left);
        var top = parseInt(ui.offset.top - $(this).offset().top);
        var name = ui.draggable[0].id;
        var html = $(ui.helper).html();
    }
    else{
        var left = block.BlockX;
        var top = block.BlockY;
        var name = block.Name;
        var html = block.BlockContent;
        var id = block.BlockId;
    }

    //基本连接线样式
    var connectorPaintStyle = {
        lineWidth: 4,
        stroke: "#61B7CF",
        joinstyle: "round",
        outlineColor: "white",
        outlineWidth: 2
    };
    // 鼠标悬浮在连接线上的样式
    var connectorHoverStyle = {
        lineWidth: 4,
        stroke: "#216477",
        outlineWidth: 2,
        outlineColor: "white"
    };
    // 鼠标悬浮在连接点上的样式
    var endpointHoverStyle = {
        fill: "#216477",
        stroke: "#216477"
    };
    var hollowCircle = {
        endpoint: ["Dot", { radius: 8 }],  //端点的形状
        endpointHoverStyle: endpointHoverStyle,
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: "#1e8151",
            fill: "transparent",
            radius: 8,
            lineWidth: 2
        },        //端点的颜色样式
        //anchor: "AutoDefault",
        isSource: true,    //是否可以拖动（作为连线起点）
        connector: ["Bezier", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
    };
    //实心圆样式
    var solidCircle = {
        endpoint: ["Dot", { radius: 8 }],  //端点的形状
        paintStyle: { fill: "rgb(122, 176, 44)" },	//端点的颜色样式
        endpointHoverStyle: endpointHoverStyle,
        connectorStyle: { stroke: "rgb(97, 183, 207)", lineWidth: 4 },	  //连接线的颜色，大小样式
        isSource: true,	//是否可以拖动（作为连线起点）
        connector: ["Bezier", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,		//是否可以放置（连线终点）
        //anchor: "AutoDefault",
        maxConnections: 3,	// 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
    };

    switch (name) {
        case "node1":
            if(id){

            }else{
                i++;
                var id = "state_start" + i;
            }

            $("#right").append('<div class="node" style="border-radius: 25em" data-nodeName="'+ name +'"  id="' + id + '" >' + html + '</div>');
            $("#" + id).css("left", left).css("top", top);
            jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, solidCircle);
            jsPlumb.draggable(id);
            $("#" + id).draggable({ containment: "parent" });
            doubleclick("#" + id);
            break;
        case "node2":
            if(id){

            }else{
                i++;
                id = "state_flow" + i;
            }

            $("#right").append("<div class='node' data-nodeName='"+ name +"' id='" + id + "'>" + html + "</div>");
            $("#" + id).css("left", left).css("top", top);
            jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, hollowCircle);
            jsPlumb.draggable(id);
            $("#" + id).draggable({ containment: "parent" });
            doubleclick("#" + id);
            break;
        case "node3":
            if(id){

            }else{
                i++;
                id = "state_decide" + i;
            }

            $("#right").append("<div class='node' data-nodeName='"+ name +"' id='" + id + "'>" + html + "</div>");
            $("#" + id).css("left", left).css("top", top);
            jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, hollowCircle);
            jsPlumb.draggable(id);
            $("#" + id).draggable({ containment: "parent" });
            doubleclick("#" + id);
            break;
        case "node4":
            if(id){

            }else{
                i++;
                id = "state_end" + i;
            }

            $("#right").append('<div class="node" style="border-radius: 25em" data-nodeName="'+ name +'"  id="' + id + '" >' + html + '</div>');
            $("#" + id).css("left", left).css("top", top);
            jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, solidCircle);
            jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);
            jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, solidCircle);
            jsPlumb.draggable(id);
            $("#" + id).draggable({ containment: "parent" });
            doubleclick("#" + id);
            break;
    }
}

$(function(){
    $("#left").children().draggable({
        helper: "clone",
        scope: "ss"
    });

    $("#right").droppable({
        scope: "ss",
        drop: createNode
    });

    //????
    $("#right").on("mouseenter", ".node", function () {
        $(this).append('<img src="images/close2.png"  style="position: absolute;" />');
        if ($(this).text() == "开始" || $(this).text() == "结束") {
            $("img").css("left", 158).css("top", 0);
        } else {
            $("img").css("left", 158).css("top", -10);
        }
    });
    $("#right").on("mouseleave", ".node", function () {
        $("img").remove();
    });
    $("#right").on("click", "img", function () {
        if (confirm("确定要删除吗")) {
            jsPlumb.removeAllEndpoints($(this).parent().attr("id"));
            $(this).parent().remove();

        }
    });


});
function doubleclick(id) {
    $(id).dblclick(function () {
        var text = $(this).text();
        $(this).html("");
        $(this).append("<input type='text' value='" + text + "' />");
        $(this).mouseleave(function () {
            $(this).html($("input[type='text']").val());
        });
    });
}

var serliza = "";
function save() {
    var connects = [];
    $.each(jsPlumb.getAllConnections(), function (idx, connection) {
        var cont = connection.getLabel();
        console.log(connection);
        connects.push({
            ConnectionId: connection.id,
            PageSourceId: connection.sourceId,
            PageTargetId: connection.targetId,
            SourceText: connection.source.innerText,
            TargetText: connection.target.innerText,
            SourceAnchor: connection.endpoints[0].anchor.type,
            TargetAnchor: connection.endpoints[1].anchor.type,
            ConnectText: $(cont).html()
        });
    });
    var blocks = [];
    $("#right .node").each(function (idx, elem) {
        var $elem = $(elem);
        var name = $elem[0].dataset.nodename;
        blocks.push({
            BlockId: $elem.attr('id'),
            BlockContent: $elem.html(),
            BlockX: parseInt($elem.css("left"), 10),
            BlockY: parseInt($elem.css("top"), 10),
            Name: name
        });
    });

    serliza = JSON.stringify(connects) + "&" + JSON.stringify(blocks);
    console.log(serliza);
    var url = "http://localhost:8080/ssm-drag-web/drag/work/create.do";
    $.ajax({
        type: "post",
        dataType: "json",
        //contentType: "application/json;charset=UTF-8",
        url: url,
        data: {"workContextJson":JSON.stringify(connects),"blocks":JSON.stringify(blocks)},
        success: function (filePath) {
           // window.open("show-flowChart.aspx?path=" + filePath);
        }
    });
}

function reload(){
    $("#right").html('');
    //基本连接线样式
    var connectorPaintStyle = {
        lineWidth: 4,
        stroke: "#61B7CF",
        joinstyle: "round",
        outlineColor: "white",
        outlineWidth: 2
    };
    // 鼠标悬浮在连接线上的样式
    var connectorHoverStyle = {
        lineWidth: 4,
        stroke: "#216477",
        outlineWidth: 2,
        outlineColor: "white"
    };
    // 鼠标悬浮在连接点上的样式
    var endpointHoverStyle = {
        fill: "#216477",
        stroke: "#216477"
    };
    var hollowCircle = {
        endpoint: ["Dot", { radius: 8 }],  //端点的形状
        endpointHoverStyle: endpointHoverStyle,
        connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: {
            stroke: "#61B7CF",
            fill: "transparent",
            radius: 8,
            lineWidth: 2
        },        //端点的颜色样式
        anchor: "AutoDefault",
        isSource: true,    //是否可以拖动（作为连线起点）
        connector: ["Bezier", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
    };
    //实心圆样式
    var solidCircle = {
        endpoint: ["Dot", { radius: 8 }],  //端点的形状
        paintStyle: { fill: "rgb(122, 176, 44)" },	//端点的颜色样式
        endpointHoverStyle: endpointHoverStyle,
        connectorStyle: { stroke: "rgb(97, 183, 207)", lineWidth: 4 },	  //连接线的颜色，大小样式
        isSource: true,	//是否可以拖动（作为连线起点）
        connector: ["Bezier", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,		//是否可以放置（连线终点）
        //anchor: "AutoDefault",
        maxConnections: 3,	// 设置连接点最多可以连接几条线
        connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
    };
    var str = serliza;
    console.log(serliza);
    var connectData = str.split("&")[0];
    var blockData = str.split("&")[1];
    var connect = JSON.parse(connectData);
    var block = JSON.parse(blockData);
    console.log(block.length);
    var conn = "";

    if(block.length > 0){
        for(i = 0; i < block.length; i++){
            console.log(block[i]);
            createNode(event, "", block[i]);
        }
        for(i = 0; i < connect.length; i++){
            //if(connect[i].ConnectText == null){
            //    conn += "jsPlumb.bind(\"connection\",function (connInfo, originalEvent) {	connInfo.connection.setLabel(\" \")});";
            //}
            //else{
                conn +=
                    "jsPlumb.bind(\"connection\",function (connInfo, originalEvent) {	connInfo.connection.setLabel(\"<span style='display:block;padding:10px;opacity: 0.5;height:auto;background-color:white;border:1px solid #346789;text-align:center;font-size:12px;color:black;border-radius:0.5em;'>" + connect[i].ConnectText + "</span>\")});";
                conn += "jsPlumb.connect({ source: \"" + connect[i].PageSourceId + "\", target: \"" + connect[i].PageTargetId +
                    "\" ,anchors:[\"" + connect[i].SourceAnchor + "\",\"" + connect[i].TargetAnchor + "\"]},hollowCircle);";
            //}
        }
        var myScript = "jsPlumb.ready(function () {" + conn + "});";
        eval(myScript);
    }
}