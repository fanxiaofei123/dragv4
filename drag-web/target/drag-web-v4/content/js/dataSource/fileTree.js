/**
 * Created by cdyoue on 2017/8/1.
 */
//文件管理左边树形目录
var setting = {

    data: {
        simpleData: {
            enable: true
        }
    },
    async: {
        enable: true,
        url:basePath+"/drag/filemanage/shownode.do",
        autoParam:["id", "name","curDir","pId"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        showLine:false,
        selectedMulti: false,
//                dblClickExpand: false,
//                showLine: false
        //        不显示子节点icon
        showIcon: showIconForTree
    },
    edit: {


    },
    data: {
        keep: {
            parent:true,
            leaf:true
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        onRemove: onRemove,

//				单击展开节点
        beforeExpand: beforeExpand,
        beforeRename: beforeRename,
        beforeRemove: beforeRemove,
        onExpand: onExpand,

        onClick: onClick,
//                右键功能
        onRightClick: onRightClick

    }
};



//上传弹框树形目录
var setting1 = {
    async: {
        enable: true,
        url:basePath+"/drag/filemanage/boxNode.do",
        autoParam:["id", "name","curDir"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    data: {
        keep: {
            parent:true,
            leaf:true
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove

    }
};

var zNodes =nodeList;
// var zNodes2=boxNodeList;
console.log(zNodes);
var log, className = "dark";
//目录树异步加载后的过滤方法
function filter(treeId, parentNode, childNodes) {
    console.log(childNodes);
    if (!childNodes) return null;
    for (var i=0, l=childNodes.obj.length; i<l; i++) {
        childNodes.obj[i].name = childNodes.obj[i].name.replace(/\.n/g, '.');
    }
    return childNodes.obj;
}
function autoparam() {

}
function beforeDrag(treeId, treeNodes) {
    return false;
}
function onRemove(e, treeId, treeNode) {
    showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}

var newCount = 1;


function stopDefault( e ) {
    if ( e && e.preventDefault )
        e.preventDefault();
    else {
        window.event.returnValue = false;
    }

    return false;
}
//右键增加文件夹方法
function add(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    console.log(e.length);
    random=parseInt(Math.random()*10000);
    var id=10000+random;
    var pId=treeNode.id;//当前选中的树的id
    hideRMenu();//隐藏右键菜单
    // 调用新建文件夹弹框
    addFile();
    $("#confirm").on('click',function () {
            var name=removeAllSpace($("#fileName").val());
            if (name.length==0 || name.trim().length == 0){
                toastr.error("请输入文件夹名称");
            }else{
                if (pId!=1) { //若果pId==1也就是（treeNode.id），表示是从根目录增加文件夹，如果pId！=1，表示是从非根目录增加文件夹；
                    // $.post(basePath+"/drag/filemanage/addDir.do",{id:id,pId:pId,name:name,curDir:treeNode.curDir+"/"+name});
                    $.ajax({
                        url:basePath+"/drag/filemanage/addDir.do",
                        type: 'POST',
                        dataType: "json",
                        data:{"id":id,"pId":pId,"name":name,"curDir":treeNode.curDir+"/"+name},
                        success: function (data) {
                            console.log(data);
                            if (data.code==417) {
                                toastr.error(data.msg);
                                refreshNode();
                            }else {
                                refreshNode();
                                toastr.success(data.msg);
                                addFileHide();
                            }

                        },
                        error:function(data) {
                            if (data.responseText == "AjaxSessionTimeout") {
                                window.location.href = basePath;
                                return;
                            }
                        }
                    });
                }else{
                    // $.post(basePath+"/drag/filemanage/addDir.do",{id:id,pId:1,name:name,curDir:"datas"+"/"+name});
                    $.ajax({
                        url:basePath+"/drag/filemanage/addDir.do",
                        type: 'POST',
                        dataType: "json",
                        data:{"id":id,"pId":1,"name":name,"curDir":"datas"+"/"+name},
                        success: function (data) {
                            console.log(data);
                            if (data.code==417) {
                                toastr.error(data.msg);
                                refreshNode();
                                addFileHide();
                            }else {
                                refreshNode();
                                toastr.success(data.msg);

                            }


                        },
                        error:function(data) {
                            if (data.responseText == "AjaxSessionTimeout") {
                                window.location.href = basePath;
                                return;
                            }
                        }
                    });


                }
                if (treeNode) {
                    // zTree.editName(treeNode[0]);//让新增的文件夹处于编辑状态；
                    // setTimeout("refreshNode();",10);
                    //调用刷新节点的方法，刷新当前新增的节点；
                    addFileHide()
                } else {
                    // alert("叶子节点被锁定，无法增加子节点");
                    addFileHide()
                }


            }

        stopDefault(e);

    });
/*    $("#fileName").keydown(function(event){
        event=document.all?window.event:event;
        if((event.keyCode || event.which)==13){
                // var name=$("#fileName").val();
                var name=removeAllSpace($("#fileName").val());
                if (name.length==0 || name.trim().length == 0){
                        toastr.error("请输入文件夹名称");
                }else{
                    if (pId!=1) { //若果pId==1也就是（treeNode.id），表示是从根目录增加文件夹，如果pId！=1，表示是从非根目录增加文件夹；
                        // $.post(basePath+"/drag/filemanage/addDir.do",{id:id,pId:pId,name:name,curDir:treeNode.curDir+"/"+name});
                        $.ajax({
                            url:basePath+"/drag/filemanage/addDir.do",
                            type: 'POST',
                            dataType: "json",
                            cache:false,
                            data:{"id":id,"pId":pId,"name":name,"curDir":treeNode.curDir+"/"+name},
                            success: function (data) {
                                console.log(data);
                                if (data.code==417) {
                                    toastr.error(data.msg);
                                    refreshNode();
                                }else {
                                    refreshNode();
                                    toastr.success(data.msg);
                                    addFileHide();
                                }

                            },
                        });
                    }else{
                        // $.post(basePath+"/drag/filemanage/addDir.do",{id:id,pId:1,name:name,curDir:"datas"+"/"+name});
                        $.ajax({
                            url:basePath+"/drag/filemanage/addDir.do",
                            type: 'POST',
                            dataType: "json",
                            cache:false,
                            data:{"id":id,"pId":1,"name":name,"curDir":"datas"+"/"+name},
                            success: function (data) {
                                console.log(data);
                                if (data.code==417) {
                                    toastr.error(data.msg);
                                    refreshNode();
                                    addFileHide();
                                }else {
                                    refreshNode();
                                    toastr.success(data.msg);
                                    addFileHide();
                                }
                            },
                        });


                    }
                    if (treeNode) {
                        // zTree.editName(treeNode[0]);//让新增的文件夹处于编辑状态；
                        // setTimeout("refreshNode();",10);
                        //调用刷新节点的方法，刷新当前新增的节点；
                        addFileHide()
                    } else {
                        // alert("叶子节点被锁定，无法增加子节点");
                        addFileHide()
                    }
                }
            }


    });*/
};



/*function edit() {
 var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
 nodes = zTree.getSelectedNodes(),
 treeNode = nodes[0];
 if (nodes.length == 0) {
 alert("请先选择一个节点");
 return;
 }
 zTree.editName(treeNode);
 };*/
function remove(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择文件");
        mesShow();
        return;
    }
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            $('#deleteMsg').html('该文件夹不为空，确定要删除？');
            deleteData();
        } else if(treeNode.isParent){
            $('#deleteMsg').html('该文件夹可能不为空，确认要删除？');
            deleteData()
        } else if(treeNode){
            $('#deleteMsg').html('确认删除文件？');
            deleteData()
        }
    }
};
/*function addHoverDom(treeId, treeNode) {  //鼠标移到根目录上悬浮的增加文件夹按钮的方法
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent = treeNode.isParent;
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    if (treeNode.level == 0) {
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
    }
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        console.log(isParent);
        random = parseInt(Math.random() * 10000);
        var id = 10000 + random;
        var pId=treeNode.id;//当前选中的树的id
        /!*   var name="新建文件夹"+(random);
         $.post(basePath+"/drag/filemanage/addDir.do",{id:id,pId:1,name:name,curDir:"datas"+"/"+name});
         treeNode = zTree.addNodes(treeNode, {id:id, pId:1, isParent:isParent, name:name,curDir:"datas"+"/"+name});*!/
        // 调用新建文件夹弹框
        addFile();
        var temp=1;
        $("#confirm").on('click', function () {
            if (temp==1){
                // var name = $("#fileName").val();
                var name=removeAllSpace($("#fileName").val());
                if(name.length==0 || name.trim().length==0){
                    if(temp==1){
                        toastr.error("请输入文件夹名称");
                    }
                }else {
                    $.ajax({
                        url:basePath+"/drag/filemanage/addDir.do",
                        type: 'POST',
                        dataType: "json",
                        data:{"id":id,"pId":1,"name":name,"curDir":"datas"+"/"+name},
                        success: function (data) {
                            console.log(data);
                            if (data.code==417) {
                                toastr.error(data.msg);
                                refreshNode();

                            }else {
                                refreshNode();
                                toastr.success(data.msg);
                                addFileHide();

                            }


                        },
                    });

                    if (treeNode) {
                        // zTree.editName(treeNode[0]);//让新增的文件夹处于编辑状态；
                        // setTimeout("refreshNode();",10);
                        //调用刷新节点的方法，刷新当前新增的节点；
                        addFileHide()
                    } else {
                        // alert("叶子节点被锁定，无法增加子节点");
                        addFileHide()
                    }
                }

            }
           temp++;

        });
        $("#fileName").keydown(function(event){
            event=document.all?window.event:event;
            if((event.keyCode || event.which)==13){
                if(temp==1){
                    var name=$("#fileName").val();
                    if(name.length==0 || name.trim().length==0){
                        if(temp==1){
                            toastr.error("请输入文件夹名称");
                        }
                    }else{
                        $.ajax({
                            url:basePath+"/drag/filemanage/addDir.do",
                            type: 'POST',
                            dataType: "json",
                            data:{"id":id,"pId":1,"name":name,"curDir":"datas"+"/"+name},
                            success: function (data) {
                                console.log(data);
                                if (data.code==417) {
                                    toastr.error(data.msg);
                                    refreshNode();
                                    addFileHide();
                                }else {
                                    refreshNode();
                                    toastr.success(data.msg);


                                }


                            },
                        });



                        if (treeNode) {
                            // zTree.editName(treeNode[0]);//让新增的文件夹处于编辑状态；
                            // setTimeout("refreshNode();",10);
                            //调用刷新节点的方法，刷新当前新增的节点；
                            addFileHide()
                        } else {
                            // alert("叶子节点被锁定，无法增加子节点");
                            addFileHide()
                        }
                    }

                }
                temp++;
            }
        });


    });
}*/


function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
//        是否显示删除和重命名(不显示根节点的删除和重命名)
// function showRemoveBtn(treeId, treeNode){
//     return !(treeNode.level==0);
// }
// function showRenameBtn(treeId, treeNode){
//     return !(treeNode.level==0);
// }
//不显示子节点icon
function showIconForTree(treeId, treeNode){
    return treeNode.isParent;
}
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
    var curDir=treeNode.curDir;
    var page=1;
    var rowCount=$("#choose-num").text();
    getFileData(page,curDir,rowCount);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode, null, null, null, true);
}


//        右键功能
function onRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX-240, event.clientY);
    } else if(treeNode.level==0){//我的文件（第一级）
        zTree.selectNode(treeNode);
        showRMenu("root1", event.clientX-240, event.clientY);
    }else if (treeNode && !treeNode.noR && !treeNode.isParent) {//文件
        zTree.selectNode(treeNode);
        showRMenu("node1", event.clientX-240, event.clientY);
    }else if (treeNode && !treeNode.noR && treeNode.isParent) {//文件夹
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX-240, event.clientY);
    }
}
function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type=="root") {
        $("#rMenu").hide();
    }else if(type=="root1"){
        // $("#remove").removeClass("noEvent");
        $("#rMenu").show();
        $("#addParent").removeClass("noEvent");
        $("#remove2").addClass("noEvent");
        $("#edit").addClass("noEvent");
        $("#creat").addClass("noEvent");
        $("#remove").hide();
        $("#remove2").show();
    } else if(type=="node1"){
        $("#rMenu").show();
        $("#addParent").addClass("noEvent");
        $("#remove").removeClass("noEvent");
        $("#edit").removeClass("noEvent");
        $("#creat").addClass("noEvent");
        $("#remove").show();
        $("#remove2").hide();
    } else if(type=='node'){
        $("#rMenu").show();
        $("#addParent").removeClass("noEvent");
        $("#remove2").removeClass("noEvent");
        $("#edit").removeClass("noEvent");
        $("#creat").removeClass("noEvent");
        $("#remove2").show();
        $("#remove").hide();
    };
    var windowHeigh=$(window).height()-170;
    if(y>windowHeigh){
        y=y-170;
    }
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    $("#confirm").off('click');
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}

function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0||newName.trim().length==0) {
        // alert("节点名称不能为空.");
        $("#promptMsg").html("重命名不能为空");
        mesShow();
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
    }else if (newName.length>20){
        // alert("长度不能超过20个字符");
        $("#promptMsg").html("字符长度不能超过20个");
        mesShow();
        return false;
    }else{
        var id=treeNode.id;
        var curDir=treeNode.curDir;
        var newName=newName;
        if(treeNode.name==newName){
            // toastr.warning("名称未做修改")
        }else{
            $.ajax({
                url:basePath+"/drag/filemanage/rename.do",
                type: 'POST',
                dataType: "json",
                data:{"id":id,"curDir":curDir,"newName":newName} ,
                success: function (data) {
                    console.log(data);
                    switch (data.code) {
                        case 417:
                            toastr.error(data.msg);
                            refreshParentNode();
                            break;
                        case 200:
                            toastr.success(data.msg);
                            refreshParentNode();
                            // reLoadFileList();
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


        }
        return true;
    }
    return true;
}

function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    return true;
}
//        重命名 通过edit方法来自动调用beforeReName方法；
function edit() {
    $("#rMenu").hide();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择文件");
        mesShow();
        return;
    }
    zTree.editName(treeNode);


};

//添加自定义控件
function addDiyDom(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent =treeNode.isParent;
    var aObj = $("#" + treeNode.tId + "_a");
    if ($("#diyBtn_"+treeNode.id).length>0) return;
    if(isParent&&!treeNode.level==0){
        var editStr = "<span class='button diy' id='diyBtn_" + treeNode.tId
            + "' title='diy node' onfocus='this.blur();'></span>";
        aObj.append(editStr);
    }
    var btn = $("#diyBtn_"+treeNode.tId);
    console.log(btn);
    if (btn) btn.bind("click", function(){
        alert(123)

    });
};

// 删除目录树文件
function deleteData() {
    $('#deleteData').modal('show');
}
function deleteHide() {
    $('#deleteData').modal('hide');
}

// 批量删除目录树文件
function batchDeleteData() {
    $('#deleteData2').modal('show');
}
function batchDeleteHide() {
    $('#deleteData2').modal('hide');
}
//执行删除操作
function realDelete() {
    $('#deleteData').modal('hide');
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    var curDir=treeNode.curDir;
    $.post(basePath+"/drag/filemanage/delDir.do",{curDir:curDir});
    // var callbackFlag = $("#callbackTrigger").attr("checked");
    //  zTree.removeNode(treeNode, callbackFlag);
    setTimeout("toastr.success('删除成功');",500);
    setTimeout("refreshParentNode();",500);
    $("#flowWorkTbody").empty();//执行右键删除的同时清空数据区
    // setTimeout("refreshParentNode();",500);



}

/**
 * 刷新当前节点
 */
function refreshNode() {
    /*根据 treeId 获取 zTree 对象*/
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        type = "refresh",
        silent = false,
        /*获取 zTree 当前被选中的节点数据集合*/
        nodes = zTree.getSelectedNodes();
        if(nodes.length!=0){
            /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
            zTree.reAsyncChildNodes(nodes[0], type, silent);
        }

}

/**
 * 刷新当前选择节点的父节点
 */
function refreshParentNode() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        type = "refresh",
        silent = false,
        nodes = zTree.getSelectedNodes();
    /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/

    if(nodes.length==0){
        // var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        // var nodes = treeObj.getNodeByParam("id", 1, null);
        // zTree.reAsyncChildNodes(nodes, "refresh");
    }else {
        /*选中指定节点*/
        var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
        zTree.selectNode(parentNode);
        zTree.reAsyncChildNodes(parentNode, type, silent);
    }

}


function getFileData(page,curDir,rowCount) {
    var tmp = {};
    tmp.page = page;
    tmp.curDir=curDir;
    tmp.rowCount=rowCount;
    $.ajax({
        url:basePath+"/drag/filemanage/showfilelist.do",
        type: 'POST',
        dataType: "json",
        data:{"page":page,"curDir":curDir,"rowCount":rowCount},
        /*beforeSend:function(){
            $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画
        },*/
        success:function(json){
            $("#flowWorkTbody").empty();//清空数据区
          /*  next = json.next;//下一页数
            previous = json.previous; //上一页数*/
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
           $("#totalpage").val(totalPage);

            var filelist = "";
            var list = json.rows;
            if (list==""){
                $("#totalpage").val(1);
                filelist +="   <tr class='odd gradeX'>"
                    +" <td></td>"
                    +" <td>- -</td>"
                    +" <td>- -</td>"
                    +" </tr>";
            }else {
                $.each(list,function(index,array){ //遍历json数据列
                    console.log(list[index]);
                    filelist +="   <tr class='odd gradeX'>"
                        +" <td>"
                        +" <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>"
                        +" <input name='checkbox' class='checkboxes' ids='286' type='checkbox' curDir='"+array['curDir']+"'><span></span>"
                        +" </label>"
                        +" </td>"
                        +" <td>"
                        +" <div class='fileType'><i class='fa fa-file-code-o'></i></div>"
                        +" <div class='fileContent'>"+" <div class='fileTitle'>"
                        +" <a>"+list[index].path.name+"</a>"
                        +" <input type='hidden' id='curPath' value='"+array['curDir']+"'/>"
                        +" </div>"
                        +" </div>"
                        +" </td>"
                        +" <td>"
                        +" <div class='actions'>"
                        +" <a data-toggle='modal' onclick='seeData(this)' title='查看表数据'>"
                        +" <i class='icon iconfont icon-chakan'></i>"
                        +" </a>"
                        +" <input type='hidden' id='curPath' value='"+array['curDir']+"'/>"
                        +" <a href='javascript:;' data-toggle='modal' onclick='downloadFile(this)' title='下载'>"
                        +" <i class='icon iconfont icon-input-copy'></i>"
                        +" </a>"
                        +" <a href='javascript:;' onclick='deleteFile(this)' title='删除文件'>"
                        +" <i class='icon iconfont icon-cuo2'></i>"
                        +" </a>"
                        +" </div>"
                        +" <span>"+array['modifiedTime']+"</span>"
                        +" </td>"
                        +" </tr>";
                });
            }

            $("#flowWorkTbody").append(filelist);
        },
        complete:function(){ //生成分页条
            Paginator();
            // fun();
        },
        error:function(data){
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            // alert("数据加载失败");
            $("#promptMsg").html("数据加载失败");
            mesShow();
        }
    });

}


//        新建文件夹弹框显示
function addFile() {
    $("#fileName").val("");
    $("#addFile").modal('show')
}
// 新建文件夹弹框隐藏
function addFileHide() {
    $("#confirm").off('click');
    $("#addFile").modal('hide')
}
//提示信息弹框
function mesShow() {
    $("#prompt").modal('show')
}
function mesHide() {
    $("#prompt").modal('hide')
}
// end


// 空格过滤
function removeAllSpace(str) {
    return str.replace(/\s+/g, "");
}
$(function () {

    // 删除文件
    $('.deletedata').on('click',function (event) {
        event.stopPropagation();
        // $(this).id
        deleteData()
    });


});

