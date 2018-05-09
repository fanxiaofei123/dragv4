/**
 * Created by cdyoue on 2017/8/1.
 */


/**
 * ztree目录树设置。
 */
var databaseSetting = {

    async: {

        enable: true,

        url: basePath + "/drag/ReadResource/loadtables.do",

        autoParam: [ "id=pid" ],

        otherParam: { "oper":"city" },

        dataFilter: dataFilter

    },

    view: {
//                左侧连线展示
        showLine:false,
        // dblClickExpand: false
//                showIcon:false
    },
    data: {
        simpleData: {
            enable: true,
            nocheckTrue:true
        }
    },
//            复选框
    check: {
        enable: false,
        nocheckInherit: false
    },
    callback: {
//				单击展开节点
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick,
        beforeClick: beforeClick,
        // beforeAsync: zTreeBeforeAsync,
//                右键功能
        onRightClick: OnRightClick,
        onDblClick:onDblClick,
        beforeRename: beforeRenameNode,
    }
};

/**
 * 拦截后端传回来的数据进行预处理。
 * @param treeId
 * @param parentNode
 * @param responseData
 * @returns {*}
 */
function dataFilter(treeId, parentNode, responseData) {
    if(responseData.length == 1 && responseData.errorInfo != null && responseData[0].errorInfo.indexOf("错误连接") != -1){
        $("#promptMsg").html(responseData[0].errorInfo);
        mesShow();
        responseData = [];
    }
    return responseData;
}

/**
 * 初始化的ztree节点数据。
 */
var zNodes = treelist;

// var log, className = "dark";
// function beforeDrag(treeId, treeNodes) {
//     return false;
// }
// function getTime() {
//     var now= new Date(),
//         h=now.getHours(),
//         m=now.getMinutes(),
//         s=now.getSeconds(),
//         ms=now.getMilliseconds();
//     return (h+":"+m+":"+s+ " " +ms);
// }
//
// var newCount = 1;
// function add(e) {
//     hideRMenu();
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
//         isParent = e.data.isParent,
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     if (treeNode) {
//         treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"新建文件夹" + (newCount++)});
//     } else {
//         treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"new node" + (newCount++)});
//     }
//     if (treeNode) {
//         zTree.editName(treeNode[0]);
//     } else {
//         alert("叶子节点被锁定，无法增加子节点");
//     }
// };

function remove(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择数据库");
        mesShow();
        return;
    }
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            // var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            $('#deleteMsg').html('确认删除文件夹？');
            deletelink();
        } else if(treeNode){
            // var msg = "是否删除子节点。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            $('#deleteMsg').html('确认删除文件？');
            deletelink()
        }
    }
};
//		点击展开节点
var curExpandNode = null;
function beforeExpand(treeId, treeNode) {
    var pNode = curExpandNode ? curExpandNode.getParentNode():null;
    var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
        if (treeNode !== treeNodeP.children[i]) {
            zTree.expandNode(treeNodeP.children[i], false);
        }
    }
    while (pNode) {
        if (pNode === treeNode) {
            break;
        }
        pNode = pNode.getParentNode();
    }
    if (!pNode) {
        singlePath(treeNode);
    }

}
function singlePath(newNode) {
    if (newNode === curExpandNode) return;

    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        rootNodes, tmpRoot, tmpTId, i, j, n;

    if (!curExpandNode) {
        tmpRoot = newNode;
        while (tmpRoot) {
            tmpTId = tmpRoot.tId;
            tmpRoot = tmpRoot.getParentNode();
        }
        rootNodes = zTree.getNodes();
        for (i=0, j=rootNodes.length; i<j; i++) {
            n = rootNodes[i];
            if (n.tId != tmpTId) {
                zTree.expandNode(n, false);
            }
        }
    } else if (curExpandNode && curExpandNode.open) {
        if (newNode.parentTId === curExpandNode.parentTId) {
            zTree.expandNode(curExpandNode, false);
        } else {
            var newParents = [];
            while (newNode) {
                newNode = newNode.getParentNode();
                if (newNode === curExpandNode) {
                    newParents = null;
                    break;
                } else if (newNode) {
                    newParents.push(newNode);
                }
            }
            if (newParents!=null) {
                var oldNode = curExpandNode;
                var oldParents = [];
                while (oldNode) {
                    oldNode = oldNode.getParentNode();
                    if (oldNode) {
                        oldParents.push(oldNode);
                    }
                }
                if (newParents.length>0) {
                    zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
                } else {
                    zTree.expandNode(oldParents[oldParents.length-1], false);
                }
            }
        }
    }
    curExpandNode = newNode;
}

function onExpand(event, treeId, treeNode) {
    curExpandNode = treeNode;
}

function onClick(e,treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode, null, null, null, true);
}

function beforeClick(treeId, treeNode, clickFlag) {
    if(treeNode.closed){
        zTree.selectNode(treeNode);
        return false;
    }else{
        return true;
    }
}

//  右键功能
function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX-240, event.clientY);
    }
    if(treeNode.level==0) {
        zTree.selectNode(treeNode);
        showRMenu("root1", event.clientX - 240, event.clientY);
    }
    // else{
    //     // if (treeNode && !treeNode.noR && treeNode.isParent)
    //     zTree.selectNode(treeNode);
    //     showRMenu("node", event.clientX-240, event.clientY);
    // }
    if(treeNode['closed']==true){
        $('#stopLink').addClass('t_link-disable');
        $('#startLink').removeClass('t_link-disable')

    }else {
        $('#startLink').addClass('t_link-disable');
        $('#stopLink').removeClass('t_link-disable')
    }
    // if(!treeNode['closed']){
    //
    // }
}

/**
 * 子节点双击事件。
 */

//设置一个参数，保存正在预览的连接名。
var showingTableParent = "";

function onDblClick(event,treeId,treeNode) {
    if(!treeNode.isParent){
        // alert(treeNode?treeNode.id + "," + treeNode.name:"isRoot");
        var pNode = treeNode.getParentNode();
        var arr = [pNode.id.toString(),treeNode.name];
        showingTableParent = pNode.name;
        $.ajax({
            url: basePath + "/drag/ReadResource/showtable.do",
            type: 'POST',
            dataType: "json",
            data: {"datas":arr},
            beforeSend:function () {
                $('#dataBox').html('<div class="col-md-12" id="data-message"><div id="tableMes"></div></div>');
                // $("body #data-message").mCustomScrollbar({
                //     axis:"yx",
                //     setLeft: 0,
                //     scrollbarPosition: "outside",
                //     advanced:{
                //         updateOnContentResize:true
                //     }
                // });
            },
            success: function (data) {
                $("#tableMes").empty();
                var table = "<table class=\"table table-bordered text-center\" id=\"table_content\">";
                var thead = "<thead><tr>";
                var tbody = "<tbody>";
                $.each(data,function (index,line) {
                    var elems = line.split("|");
                    if(index == 0){
                        thead += "<th width=\"\" class='table-head-tr' >" + "行号" + "<br/><span>number</span> </th>";
                        for(var i=0; i<elems.length; i++){
                            var metas = elems[i].split(",");
                            thead += "<th width=\"\" class='table-head-tr'>" + metas[0] + "<br/><span>" + metas[1] + "</span> </th>";
                        }
                        thead += "</tr></thead>";
                    }else{
                        tbody += "<tr>" + "<td>" + index + "</td>";
                        for(var i=0; i<elems.length; i++){
                            tbody += "<td>" + elems[i] + "</td>";
                        }
                        tbody += "</tr>"
                    }
                });
                tbody += "</tbody>";
                table += thead;
                table += tbody;
                table += "</table>";
                $("#tableMes").html(table);
                $(".dataBg").addClass("dn")
            },
            complete:function () {
                $("body #data-message").mCustomScrollbar({
                    theme: "dark-thin",
                    axis:"yx",
                    setLeft: 0,
                    scrollbarPosition: "outside",
                    advanced:{
                        updateOnContentResize:true
                    }
                });
            },
            error:function(data){
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            }
        })
    }else{
        console.log("=======" + treeNode.name + treeNode.closed);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodeNameList=JSON.parse(sessionStorage.getItem('nodeNameList'));
        var zLinkNodeNamesStatusList=JSON.parse(sessionStorage.getItem('zLinkNodeNamesStatusList'));
        if(treeNode.closed){
            $.ajax({
                url: basePath + "/drag/ReadResource/startlink.do",
                type: 'POST',
                dataType: "text",
                data: "connName=" + treeNode.name,
                success: function (data) {
                    console.log("xxxxxxxxx" + data);
                    if("" == data){
                        // alert("启用连接失败！")
                        $("#promptMsg").html("启用连接失败");
                        mesShow();
                    }else{
                        //前台页面样式修改
                        zLinkNodeNamesStatusList.forEach(function (item, index, array) {
                            if(item['name']==treeNode.name)    {
                                item['closed']=false;
                            }
                        });
                        nodeNameList.forEach(function (item, index, array) {
                            if(item['ele_text']==treeNode.name){
                                var m=item['treeDemo_id_a'];
                                $("#"+m).removeClass('close_icon');
                            }
                        });
                        sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
                        sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));
                        treeNode.closed = false;
                        zTree.reAsyncChildNodes(treeNode,"refresh");
                    }
                },
                error: function (data) {
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    $("#promptMsg").html("启用连接失败");
                    mesShow();
                },
                complete:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                },
            })
        }
    }
}

function showRMenu(type, x, y) {
    // $("#rMenu ul").show();
    $("#rMenu").show();
    if (type=="root") {
        $("#rMenu").hide();
    }else if(type=="root1"){
        $("#rMenu").show();
        $("#remove").hide();
        $("#edit").hide();
        $("#creat").hide();
    } else {
        $("#rMenu").show();
        $("#addParent").show();
        $("#remove").show();
        $("#edit").show();
        $("#creat").show();
        $("#remove").show();
    }
    var windowHeigh=$(window).height()-170;
    if(y>windowHeigh){
        y=y-170;
    }
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}

/**
 * 选中节点进入编辑状态
 */
function rename() {
    $("#rMenu").hide();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择数据库");
        mesShow();
        return;
    }
    zTree.editName(treeNode);
};

/**
 * 在编辑结束还未更新节点名称时，更新后台数据，并根据后台数据更新成功与否来确定是否更新节点。
 */
function beforeRenameNode(treeId, treeNode, newName, isCancel){
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if (newName.length == 0) {
        // alert("节点名称不能为空.");
        $("#promptMsg").html("节点名称不能为空！");
        mesShow();
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
    }else if (newName.length>20){
        // alert("长度不能超过20个字符");
        $("#promptMsg").html("重命名长度不能超过20个字符！");
        mesShow();
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
    } else{
        var arr = [treeNode.id.toString(),newName];
        var isRename = false;
        $.ajax({
            url: basePath + "/drag/ReadResource/rename.do",
            type: 'POST',
            data: {"datas":arr},
            async : true,
            dataType: "json",
            success: function (data) {
                if(data.errorInfo != "" && data.errorInfo != null){
                    alert(data.errorInfo);
                    setTimeout(function(){zTree.editName(treeNode)}, 10);
                    isRename = false;
                }else{
                    isRename = true;
                }
                // isRename = true;
                return isRename;
            },
            error: function (data) {
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                // alert("重命名失败，请重试！");
                $("#promptMsg").html("重命名失败，请重试！");
                mesShow();
                // setTimeout(function(){zTree.editName(treeNode)}, 10);
                // isRename = false;
                // return isRename;

            }
        });
        // return isRename;
    }
}



/**
 * 停止连接，点击停止连接后，停止连接按钮要变成灰色。
 */

function stopLink() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择数据库");
        mesShow();
        return;
    }
    var connName = treeNode.name;
    console.log(connName);
    //前台页面样式修改
    var nodeNameList=JSON.parse(sessionStorage.getItem('nodeNameList'));
    var zLinkNodeNamesStatusList=JSON.parse(sessionStorage.getItem('zLinkNodeNamesStatusList'));
    if(!treeNode.closed){
        $.ajax({
            url: basePath + "/drag/ReadResource/stoplink.do",
            type: 'POST',
            dataType: "text",
            data: "connName=" + connName,
            success: function (data) {
                console.log("wwwwwwwwwww" + data);
                $("#rMenu").hide();
                if("" == data){
                    // alert("停止连接失败！")
                    $("#promptMsg").html("停止连接失败");
                    mesShow();
                }else{
                    //前台页面样式修改
                    zLinkNodeNamesStatusList.forEach(function (item, index, array) {
                        if(item['name']==connName)    {
                            item['closed']=true;
                        }
                    });
                    nodeNameList.forEach(function (item, index, array) {
                        if(item['ele_text']==connName){
                            var m=item['treeDemo_id_a'];
                            $("#"+m).addClass('close_icon');
                            var treeDemo_id_switch = item['treeDemo_id_switch'];
                            var treeDemo_id_ul = item['treeDemo_id_ul'];
                            $("#"+treeDemo_id_switch).removeClass("noline_open")
                            $("#"+treeDemo_id_switch).addClass("noline_close")
                            $("#"+treeDemo_id_ul).hide()
                        }
                    });
                    sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
                    sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));

                    treeNode.closed = true;
                    zTree.reAsyncChildNodes(treeNode,"refresh");
                    if(showingTableParent == connName){
                        $("#tableMes").empty();
                        $(".dataBg").removeClass("dn")
                    }
                    // treeNode.name = "111";
                    // zTree.updateNode(treeNode);
                }
            },
            error: function (data) {
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                $("#promptMsg").html("停止连接失败");
                mesShow();
            },
            complete:function(data){
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            },
        })
    }else{
        $("#rMenu").hide();
    }
}


/**
 * 编辑连接
 */

function editLink() {
    $("#rMenu").hide();
    $("#dataLink").modal('show');
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    var treeNodeName=treeNode['name'];
    sessionStorage.setItem('treeNodeName',treeNodeName)
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择数据库");
        mesShow();
        return;
    }else{
        $.ajax({
            url: basePath + "/drag/ReadResource/scanLink.do",
            type: 'POST',
            dataType: "json",
            data: "id=" + treeNode.id ,
            success: function (data) {
                // console.log("==========" + $("#connect_save").val());
                $("#resource_type").val(data.type);
                $("#resource_databaseName").val(data.databaseName);
                $("#resource_hostIp").val(data.hostIp);
                $("#resource_port").val(data.hostPort);
                $("#resource_username").val(data.userName);
                $("#resource_password").val(data.passWord);
                $("#resource_linkname").val(data.connectName);
                $("#connect_save").val("editNodes");
            },
            error: function (data) {
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                $("#rMenu").hide();
                // alert("查看连接失败！")
                $("#promptMsg").html("查看失败");
                mesShow()
            }
        })
    }
};

/**
 *  启用连接，点击启用连接后，启用连接要变成灰色。
 */

function startLink() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择数据库");
        mesShow();
        return;
    }
    connName = treeNode.name;
    //前台页面样式修改
    var nodeNameList=JSON.parse(sessionStorage.getItem('nodeNameList'));
    var zLinkNodeNamesStatusList=JSON.parse(sessionStorage.getItem('zLinkNodeNamesStatusList'));

    if(treeNode.closed){
        $.ajax({
            url: basePath + "/drag/ReadResource/startlink.do",
            type: 'POST',
            dataType: "text",
            data: "connName=" + connName,
            success: function (data) {
                $("#rMenu").hide();
                if("" == data){
                    // alert("启用连接失败！")
                    $("#promptMsg").html("启用连接失败");
                    mesShow();
                }else{
                    //前台页面样式修改
                    zLinkNodeNamesStatusList.forEach(function (item, index, array) {
                            if(item['name']==connName)    {
                                item['closed']=false;
                            }
                    });
                    nodeNameList.forEach(function (item, index, array) {
                        if(item['ele_text']==connName){
                            var m=item['treeDemo_id_a'];
                            $("#"+m).removeClass('close_icon');
                        }
                    });
                    sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
                    sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));

                    treeNode.closed = false;
                    zTree.reAsyncChildNodes(treeNode,"refresh");
                }
            },
            error: function (data) {
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                $("#rMenu").hide();
                // alert("启用连接失败！")
                $("#promptMsg").html("启用连接失败");
                mesShow();
            },
            complete:function(data){
                if(data.reponseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            },
        })
    }else{
        $("#rMenu").hide();
    }
}

/**
 *  数据预览
 */
function scanLink() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        $("#rMenu").hide();
        $("#promptMsg").html("请先选择需查看的文件");
        mesShow();
        return;
    }
    $.ajax({
        url: basePath + "/drag/ReadResource/scanLink.do",
        type: 'POST',
        dataType: "json",
        data: "id=" + treeNode.id ,
        success: function (data) {
            console.log(data)
            $("#rMenu").hide();
            $("#linkType").attr('title',data.type);
            $("#linkType").html(data.type);
            $("#linkDbName").attr('title',data.databaseName);
            $("#linkDbName").html(data.databaseName);
            $("#linkIp").attr('title',data.hostIp);
            $("#linkIp").html(data.hostIp);
            $("#linkPort").attr('title',data.hostPort);
            $("#linkPort").html(data.hostPort);
            $("#linkUser").attr('title',data.userName);
            $("#linkUser").html(data.userName);
            // alert(data.connectName);
            $("#linkName").attr('title',data.connectName);
            $("#linkName").html(data.connectName);
            dataMessage();
        },
        error: function (data) {
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }

            $("#rMenu").hide();
            // alert("查看连接失败！")
            $("#promptMsg").html("查看连接失败");
            mesShow()
        }
    })
}

/**
 * 确认删除连接
 */
function confirmDelLink() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    var id = treeNode.id;
    $.ajax({
        url: basePath + "/drag/ReadResource/deleteLink.do",
        type: 'POST',
        dataType: "text",
        data: "id=" + treeNode.id ,
        success: function (data) {
            console.log("%%%%%%%%%%%%%%%%" + data);
            $('#deleteData').modal('hide');
            var result = parseInt(data);
            if(result > 0){
                zTree.removeChildNodes(treeNode);
                zTree.removeNode(treeNode);
                if(treeNode.name == showingTableParent){
                    $("#tableMes").empty();
                    $(".dataBg").removeClass("dn");
                }
                var nodeNameList=[];
                $(".node_name").each(function (index,element) {
                    var nodeNameStatus={};
                    var ele_text=$(element).text();
                    var element_id=$(element).attr('id');
                    var ele_id=element_id.match(/\d+/g)[0];
                    nodeNameStatus['treeDemo_id']='treeDemo_'+ele_id;
                    nodeNameStatus['treeDemo_id_ico']='treeDemo_'+ele_id+'_ico';
                    nodeNameStatus['treeDemo_id_span']='treeDemo_'+ele_id+'_span';
                    nodeNameStatus['treeDemo_id_a']='treeDemo_'+ele_id+'_a';
                    nodeNameStatus['ele_text']=ele_text;
                    nodeNameList.push(nodeNameStatus)
                });
                var zLinkNodeNamesStatusList=[];
                zLinkNodeNames.forEach(function (item, index, array) {
                    var zLinkNodeNamesStatus={};
                    zLinkNodeNamesStatus['closed']=item['closed'];
                    zLinkNodeNamesStatus['name']=item['name'];
                    zLinkNodeNamesStatusList.push(zLinkNodeNamesStatus)
                });
                console.log(nodeNameList);
                console.log(zLinkNodeNamesStatusList);
                sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
                sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList))

            }else{
                // alert("删除节点失败");
                $("#promptMsg").html("删除连接失败");
                mesShow()
            }
        },
        error: function (data) {
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            $('#deleteData').modal('hide');
            // alert("删除节点失败");
            $("#promptMsg").html("删除连接失败");
            mesShow()
        },
        complete:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        },
    })
}

//提示信息弹框
function mesShow() {
    $("#prompt").modal('show')
}
function mesHide() {
    $("#prompt").modal('hide')
}
// end

