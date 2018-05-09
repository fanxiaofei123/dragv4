var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        showRemoveBtn: true,
        showRenameBtn: true
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
// 弹框文件
var setting2 = {
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
var zNodes =[
    { id:1, pId:0, name:"父节点 1", open:true},
    { id:11, pId:1, name:"叶子节点 1-1"},
    { id:12, pId:1, name:"叶子节点 1-2"},
    { id:13, pId:1, name:"叶子节点 1-3"},
    { id:2, pId:0, name:"父节点 2", open:true},
    { id:21, pId:2, name:"叶子节点 2-1"},
    { id:22, pId:2, name:"叶子节点 2-2"},
    { id:23, pId:2, name:"叶子节点 2-3"},
    { id:3, pId:0, name:"父节点 3", open:true},
    { id:31, pId:3, name:"叶子节点 3-1"},
    { id:32, pId:3, name:"叶子节点 3-2"},
    { id:33, pId:3, name:"叶子节点 3-3"}
];
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}
function onRemove(e, treeId, treeNode) {
    showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo1");
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
    console.log(e.data.isParent);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    console.log(isParent);
    if (treeNode) {
        treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, isParent:isParent, name:"new node" + (newCount++)});
    } else {
        treeNode = zTree.addNodes(null, {id:(100 + newCount), pId:0, isParent:isParent, name:"new node" + (newCount++)});
    }
    if (treeNode) {
        zTree.editName(treeNode[0]);
    } else {
        alert("叶子节点被锁定，无法增加子节点");
    }
};
// 添加按钮增加文件夹
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", {isParent:true}, add);
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};
function edit() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
        return;
    }
    zTree.editName(treeNode);
};
function remove(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        alert("请先选择一个节点");
        return;
    }
    var callbackFlag = $("#callbackTrigger").attr("checked");
    zTree.removeNode(treeNode, callbackFlag);
};
function clearChildren(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0 || !nodes[0].isParent) {
        alert("请先选择一个父节点");
        return;
    }
    zTree.removeChildNodes(treeNode);
};

$(document).ready(function(){
    $.fn.zTree.init($("#treeDemo1"), setting, zNodes);
    $.fn.zTree.init($("#treeDemo2"), setting2, zNodes);
    $("#addParent").bind("click", {isParent:true}, add);
    $("#addParent2").bind("click", {isParent:false}, add);
    $("#edit").bind("click", edit);
    $("#remove").bind("click", remove);
    $("#clearChildren").bind("click", clearChildren);
});

// 添加数据
function addData() {
    $('#addDataModal').modal('show')
}
$(function () {
    // 添加数据
    $('#addData').on('click',function (event) {
        event.stopPropagation();
        addData()
    });
})