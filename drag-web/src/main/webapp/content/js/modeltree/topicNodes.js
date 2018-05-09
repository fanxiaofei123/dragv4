
/**
 * Created by sky on 2017/6/13.
 */

var settingtopic = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,

    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        beforeDrop: beforeDrop,


    },
    drag:{
        prev:false,
        next:false,
        inner:false
    }
};

var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}

function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
    zTree.selectNode(treeNode);
    return checkRemove(treeId,treeNode);
}

function  checkRemove(treeId,treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
    zTree.selectNode(treeNode);
    var id=treeNode.id;
    var delOrNot=false;
    $.post(basePath+"/drag/treemode/checkRelease.do",{id:id},function(data){
        if (data=="allow"){
            delOrNot=confirm("确定要删除 '" + treeNode.name + "' 吗?");
            if(delOrNot==true){
                $.post(basePath+"/drag/treemode/deleteNode.do",{id:id});
                setTimeout("location.reload();",500);

            }
        }else {
            alert("已经发布的模型不能删除哦");
        }
    });
    return delOrNot;



};

function getTime() {
    var now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}

function beforeRename(treeId, treeNode, newName, isCancel) {
    if (newName.length == 0||newName.trim().length==0) {
        alert("节点名不能为空");
        return false;
    }else if (newName.length>20){
        alert("长度不能超过20个字符");
        return false;
    }else{
        var id=treeNode.id;
        $.post(basePath+"/drag/treemode/updateName.do",{id:id,newName:newName})
        return true;
    }

}

function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
        var id=treeNode.id;
        $.get(basePath+"/drag/treemode/addtopicnode.do",{pid:id},function (data){
            var newID=data;
            zTree.addNodes(treeNode, {id:newID, pId:treeNode.id, name:"new node" + (newCount++)});
        });
    });

};
function beforeDrag(treeId, treeNodes) {
    for (var i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            return false;
        }
    }
    return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
//    alert(targetNode.id);
    var moveid=treeNodes[0].id;
    $.post(basePath+"/drag/treemode/moveNode.do",{moveId:moveid,targetId:targetNode.id})
    return targetNode ? targetNode.drop !== false : true;
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();

};



function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
    zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}


$(document).ready(function(){
    $.fn.zTree.init($("#treeDemo2"), settingtopic, topicNodes);

});

