/**
 * Created by cdyoue on 2017/11/6.
 */
var setting = {
    async: {
        enable: true,
        url:basePath+"/drag/work/selectZtreeWindow.do",
        autoParam:["id", "name","pId","isParent"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        showIcon: showIconForTree,
        selectedMulti: false,
        showLine: false
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
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick

    }
};
// var zNodes = workFlowNodeList;
function showIconForTree(treeId, treeNode) {
    return treeNode.isParent;
};
function filter(treeId, parentNode, childNodes) {
    console.log(childNodes);
    if (!childNodes) return null;
    var aa  = JSON.stringify(childNodes.obj);
    // childNodes.obj= childNodes.obj.replace(/\.n/g, '.');
    // var bb = aa.replace(/\"/g, "");
    var cc = JSON.parse(aa)
    // for (var i=0; i<childNodes.obj.length;i++) {
    // }

    return cc;

    // return JSON.stringify(childNodes.obj);
}

function beforeDrag(treeId, treeNodes) {
    return false;
}
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
// 渲染模板说明文档
function rendering() {
    $("#templateDescription").html($("input[name='templateDescription']").val());
}
// 说明文档end
// 弹框目录树滚动条
$(window).on("load", function () {
    $("body .treeDeomBox").mCustomScrollbar({
        theme: "dark-thin",
        axis:"y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
});
$(function () {
    rendering();
});

//创建新的工作流
function createWorkFlow() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    nodes = zTree.getSelectedNodes();
    treeNode = nodes[0];
    var workFlowName = $("#nameWork").val();
    var flowExplain = $("#flowExplain").val();
    var templateId = GetQueryString("id");
    var flowId = GetQueryString("flowId");
    if(workFlowName.length == 0 || workFlowName.length >= 20){
        $(".selectMagName").html("请填写20以内字符");
        return;
    }else{
        $(".selectMagName").html("");
    }
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $(".selectMag").html("请先选择目录");
        return;
    }
    if(!treeNode.isParent==true){
        $(".selectMag").html("请先选择目录")
    }else{
        $.ajax({
            url: basePath + "/template/copyWorkFlow.do",
            type: 'POST',
            // dataType: "json",
            data: {"workSpaceId": treeNode.id,"workFlowName": workFlowName,"flowExplain":flowExplain,"workFlowId":flowId},
            success: function (json) {
                var returnData = JSON.parse(json);
                switch (returnData.code) {
                    case 417:
                        toastr.error(returnData.msg);
                        $("#basic").modal("hide");
                        break;
                    case 200:
                        toastr.success(returnData.msg);
                        window.location.href=basePath+"/drag/dataModel/select.do?name="+workFlowName+"&flowId="+returnData.obj+"&workSpaceName="+treeNode.name;
                        break;
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
        });
    }

}
//获取浏览器参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}