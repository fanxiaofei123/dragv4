/**
 * Created by cdyoue on 2017/8/1.
 */
/**
 * Created by cdyoue on 2017/7/31.
 */
var setting3 = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        showLine:false,
        selectedMulti: false,
//                dblClickExpand: false,
//                showLine: false
//        不显示子节点icon
        showIcon: showIconForTree,
        //添加自定义控件
        addDiyDom: addDiyDom
    },
    edit: {
        enable: true,
        showRemoveBtn: showRemoveBtn,
        showRenameBtn: showRenameBtn
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
        onRemove: onRemove,
//				单击展开节点
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick
//                右键功能
//                onRightClick: OnRightClick
    }
};
var setting2 = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        showLine:false,
        selectedMulti: false,
//                dblClickExpand: false,
//                showLine: false
        //        不显示子节点icon
        showIcon: showIconForTree,
    },
    edit: {
        enable: true,
        showRemoveBtn: showRemoveBtn,
        showRenameBtn: showRenameBtn
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
        onRemove: onRemove,
//				单击展开节点
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick,
//                右键功能
        onRightClick: OnRightClick
    }
};
var setting1 = {
    view: {
//                左侧连线展示
        showLine:false
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
        enable: true
    },
    callback: {
//				单击展开节点
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick,
//                右键功能
        onRightClick: OnRightClick
    }
};
var zNodes =[
    { id:1, pId:0, name:"父节点1 - 展开", open:true},
    { id:11, pId:1, name:"父节点11 - 折叠"},
    { id:111, pId:11, name:"叶子节点111",nocheck:true},
    { id:112, pId:11, name:"叶子节点112",nocheck:true},
    { id:113, pId:11, name:"叶子节点113",nocheck:true},
    { id:114, pId:11, name:"叶子节点114",nocheck:true},
    { id:12, pId:1, name:"父节点12 - 折叠"},
    { id:121, pId:12, name:"叶子节点121",nocheck:true},
    { id:122, pId:12, name:"叶子节点122",nocheck:true},
    { id:123, pId:12, name:"叶子节点123",nocheck:true},
    { id:124, pId:12, name:"叶子节点124",nocheck:true}
];
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    // return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    remove();
    return false;
//     $('#deleteMsg').html('确认删除文件夹？');
//     deleteData();
}
function onRemove(e, treeId, treeNode) {
    showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function(){zTree.editName(treeNode)}, 10);
        return false;
    }
    return true;
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
function add(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"新建文件夹" + (newCount++)});
    } else {
        treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"new node" + (newCount++)});
    }
    if (treeNode) {
        zTree.editName(treeNode[0]);
    } else {
        alert("叶子节点被锁定，无法增加子节点");
    }
};
function edit() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
        return;
    }
    zTree.editName(treeNode);
};
function remove(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
        return;
    }
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            // var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            $('#deleteMsg').html('确认删除文件夹？')
            deleteData();
        } else if(treeNode){
            // var msg = "是否删除子节点。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            $('#deleteMsg').html('确认删除文件？')
            deleteData()
        }
    }
};
function addHoverDom(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent =treeNode.isParent;
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    if(isParent){
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
    }
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        if (treeNode) {
            treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"新建文件夹" + (newCount++)});
        } else {
            treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"new node" + (newCount++)});
        }
        if (treeNode) {
            zTree.editName(treeNode[0]);
        } else {
            alert("叶子节点被锁定，无法增加子节点");
        }

    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
//        是否显示删除和重命名(不显示根节点的删除和重命名)
function showRemoveBtn(treeId, treeNode){
    return !(treeNode.level==0);
}
function showRenameBtn(treeId, treeNode){
    return !(treeNode.level==0);
}
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
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode, null, null, null, true);
}


//        右键功能
function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX-240, event.clientY);
    } else if(treeNode.level==0){
        showRMenu("root1", event.clientX-240, event.clientY);
    }else if (treeNode && !treeNode.noR && treeNode.isParent) {
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX-240, event.clientY);
    }
}

function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type=="root") {
        $("#rMenu").hide();
    }else if(type=="root1"){
        $("#rMenu").show();
        $("#remove").hide();
        $("#edit").hide();
        $("#creat").hide();
    } else {
        $("#rMenu").show();
//                $("#addParent").show();
        $("#remove").show();
        $("#edit").show();
        $("#creat").show();
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
//        重命名
function edit() {
    $("#rMenu").hide();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
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