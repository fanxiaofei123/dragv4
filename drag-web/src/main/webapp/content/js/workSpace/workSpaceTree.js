/**
 * Created by cdyoue on 2017/8/1.
 */
//    点击工作实验室分页
function Paginator() {
    var fileTotal = $("#totalpage").val();
    $('.pagination-panel-total').text(fileTotal);
    $('.pagination').jqPaginator({
        totalPages: parseInt(fileTotal),
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            $('#new_num').html(num);
            // alert('当前第' + num + '页');
            // getData(num);

            if (num != curPage) {
                // var rowNum = $("#choose-num").text();
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
                // getFileData(num,rowNum);
                getFileData(num);

            }
        }
    });
}

function PaginatorChild() {
    var fileTotal = $("#totalpage").val();
    $('.pagination-panel-total').text(fileTotal);
    $('.pagination').jqPaginator({
        totalPages: parseInt(fileTotal),
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            $('#new_num').html(num);
            // alert('当前第' + num + '页');
            // getData(num);

            if (num != curPage) {
                // var rowNum = $("#choose-num").text();
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
                console.log("####" + treeNode)
                console.log(num)
                // getFileData(num,rowNum);
                getMenuDataFlow(num, treeNode.id, treeNode.name);

            }
        }
    });
}
function PaginatorByName() {
    var fileTotal = $("#totalpage").val();
    $('.pagination-panel-total').text(fileTotal);
    $('.pagination').jqPaginator({
        totalPages: parseInt(fileTotal),
        visiblePages: 4,
        currentPage: parseInt(curPage),

        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">尾页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
            $('#new_num').html(num);
            // alert('当前第' + num + '页');
            // getData(num);

            if (num != curPage) {
                // var rowNum = $("#choose-num").text();
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
                console.log("####" + treeNode)
                console.log(num)
                // getFileData(num,rowNum);
                selectFlowByName(num);

            }
        }
    });
}

var setting = {
    async: {
        enable: true,
        url: basePath + "/drag/work/selectZtree.do",
        autoParam: ["id", "pId", "name", "isParent"],
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        showLine: false,
        selectedMulti: false,
//                dblClickExpand: false,
//                showLine: false
        //        不显示子节点icon
        showIcon: showIconForTree
    },
    edit: {},
    data: {
        keep: {
            parent: true,
            leaf: true
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRename: beforeRename,
        onRemove: onRemove,
//				单击展开节点
        beforeExpand: beforeExpand,
        onExpand: onExpand,
        onClick: onClick,
//                右键功能，
//         beforeRightClick: beforeExpand,
        onRightClick: OnRightClick
    },
};
var setting2 = {
    async: {
        enable: true,
        url: basePath + "/drag/work/selectZtreeWindow.do",
        autoParam: ["id", "pId", "name", "isParent"],
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        showLine: false,
        selectedMulti: false,
        dblClickExpand: false,
        //        不显示子节点icon
        showIcon: showIconForTree
    },
    edit: {},
    data: {
        keep: {
            parent: true,
            leaf: true
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        // beforeRename: beforeRename,
        // onRemove: onRemove,
//				单击展开节点
//         beforeExpand: beforeExpand,
        onExpand: onExpand2,
        onClick: onClick2
//                右键功能
//         onRightClick: OnRightClick
    }
};
function filter(treeId, parentNode, childNodes) {
    if (!childNodes.obj) return null;

    for (var i = 0, l = childNodes.obj.length; i < l; i++) {
        childNodes.obj[i].name = childNodes.obj[i].name.replace(/\.n/g, '.');
    }
    return childNodes.obj;
}
var zNodes = nodeList;
//     = [
//     {id: 0, pId: -1, name: "工作流", isParent: true, open: true},
//     // {id: 1, pId: 0, name: "工作流",open : true},
//     //  { id:11, pId:1, name:"父节点11 - 折叠"},
//     // { id:111, pId:11, name:"叶子节点111",nocheck:true},
//     // { id:112, pId:11, name:"叶子节点112",nocheck:true},
//     // { id:113, pId:11, name:"叶子节点113",nocheck:true},
//     // { id:114, pId:11, name:"叶子节点114",nocheck:true},
//     // { id:12, pId:1, name:"父节点12 - 折叠"},
//     // { id:121, pId:12, name:"叶子节点121",nocheck:true},
//     // { id:122, pId:12, name:"叶子节点122",nocheck:true},
//     // { id:123, pId:12, name:"叶子节点123",nocheck:true},
//     // { id:124, pId:12, name:"叶子节点124",nocheck:true}
// ];
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
    return false;
}
// 重命名回调
function beforeRename(treeId, treeNode, newName) {
    var newNameLength=$.trim(newName).length
    if (newNameLength==0) {
        toastr.info("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        setTimeout(function () {
            zTree.editName(treeNode)
        }, 10);
        return false;
    } else if (treeNode.isParent) {
        // if(treeNode[0].name == newName.trim()){
        //     return false;
        // }
        if(newName.length>20){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            newName=newName.substring(0,20);
            treeNode['name']=newName
            zTree.editName(treeNode)
        }
        $.ajax({
            url: basePath + "/drag/work/update.do",
            type: 'POST',
            data: {"id": treeNode.id, "name": newName, "pid": treeNode.pId},
            async: false,
            success: function (data) {
                var returndata = JSON.parse(data);
                switch (returndata.code) {
                    case 417:
                        toastr.error(returndata.msg);
                        refreshParentNode();
                        break;
                    case 200:
                        toastr.success(returndata.msg);
                        // refreshNode();
                        refreshParentNode();
                        refreshParentNode2();
                        return true;
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
    } else {
        //修改在右键重命名工作流
        if(newName.length>20){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            newName=newName.substring(0,20);
            treeNode['name']=newName
            zTree.editName(treeNode)
        }
        var formData = {
            "name": newName,
            "id": treeNode.id
        };
        $.ajax({
            url: basePath + "/drag/flow/update.do",
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            success: function (data) {
                var returndata = JSON.parse(data);
                console.log(data);
                switch (returndata.code) {
                    case 417:
                        toastr.error(returndata.msg);
                        refreshParentNode();
                        break;
                    case 200:
                        toastr.success(returndata.msg);
                        // refreshNode();
                        refreshParentNode();
                        $('#basicEdit').modal('hide');
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
    return true;
}
function onRemove(e, treeId, treeNode) {
    showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='" + className + "'>" + str + "</li>");
    if (log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now = new Date(),
        h = now.getHours(),
        m = now.getMinutes(),
        s = now.getSeconds(),
        ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}
//随机产生6位字符
var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
function generateMixed(n) {
    var res = "";
    for (var i = 0; i < n; i++) {
        var id = Math.ceil(Math.random() * 35);
        res += chars[id];
    }
    return res;
}

var newCount = 1;
function add(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        var pid = treeNode.pId;
    // 调用新建文件夹弹框
    addFile();

    var number = 0;
    var numberT = 0;
    $("#confirm").on('click', function  confirm() {
        var name = $("#fileName").val();
        if(name.length>20){
            toastr.error("请书写小于20字符串的文件夹名称！");
            $("#fileName").val("");
        }else {

        // if( number == 0){
            if (pid != -1) {
                //添加文件夹
                $.ajax({
                    url: basePath + "/drag/work/create.do",
                    type: 'POST',
                    data: {"pid": treeNode.id, "isParent": 1, "name": name, "PPid": treeNode.name},
                    async: false,
                    success: function (data) {
                        var returndata = JSON.parse(data);
                        if(returndata.code == '417'){
                            toastr.error(returndata.msg);
                            refreshNode()
                        }else if(returndata.code == "200") {
                            toastr.success(returndata.msg);
                                    refreshNode()
                            window.location.reload();
                        }
                        // switch (returndata.code) {
                        //     case 417:
                        //         toastr.error(returndata.msg);
                        //         break;
                        //     case 200:
                        //         toastr.success(returndata.msg);
                        //         refreshNode();
                        //         break;
                        // }


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
                addFileHide()
            } else {
                // treeNode = zTree.addNodes(null, {
                //     id: (100 + newCount),
                //     pId: 0,
                //     isParent: false,
                //     name: "new node" + (newCount++)
                // });
                toastr.error("请先选择工作流！");
                addFileHide()
            }
            // if (treeNode) {
            //     zTree.editName(treeNode[0]);
            //     addFileHide()
            // } else {
            //     alert("叶子节点被锁定，无法增加子节点");
            //     addFileHide()
            // }
            // number++
        // }
        //设置工作流文件夹为空
            $("#fileName").val("");
        }
    })


};
// function edit() {
//     var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     if (nodes.length == 0) {
//         alert("请先选择一个节点");
//         return;
//     }
//     zTree.editName(treeNode);
// };
function remove(e) {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择一个文件");
        mesShow();
        return;
    }
    if (nodes && nodes.length > 0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            // var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                // isParent = e.data.isParent,
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];

            //查询是否存在调度。
            $.ajax({
                url:basePath+"/drag/work/findByWorkSpaceId.do",
                type: 'POST',
                // dataType: "json",
                // contentType : "application/json;charset=UTF-8",
                data: {"id": treeNode.id},
                async: false,
                success: function (data) {
                    var returndata = JSON.parse(data)
                    switch (returndata.code){
                        case 417:$("#deleteMsg").html(returndata.msg);
                            break;
                        case 200:$("#deleteMsg").html(returndata.msg);
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
            // $('#deleteMsg').html('确认删除文件夹？');
            deleteData();
        } else if (treeNode) {
            // var msg = "是否删除子节点。\n\n请确认！";
            // if (confirm(msg)==true){
            //     zTree.removeNode(nodes[0]);
            // }
            console.log("treeNode:"+treeNode)
            var delFlow=new Array();
            delFlow.push(treeNode.id);
            delTmpWork.ids = delFlow;
            //查询是否存在调度。
            $.ajax({
                url:basePath+"/drag/flow/findHaveSchJob.do",
                type: 'POST',
                dataType: "json",
                contentType : "application/json;charset=UTF-8",
                data: JSON.stringify(delTmpWork),
                success: function (data) {
                    console.log(data);
                    switch (data.code){
                        case 417:$("#deleteMsg").html(data.msg);
                            break;
                        case 200:$("#deleteMsg").html(data.msg);
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
            // $('#deleteMsg').html('确认删除文件？');
            deleteData()
        }
    }
};
function addHoverDom(treeId, treeNode) {
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
        if (treeNode) {
            alert(treeNode.id)
            treeNode = zTree.addNod(treeNode, {
                id: (100 + newCount),
                pId: treeNode.id,
                isParent: isParent,
                name: "新建文件夹" + (newCount++)
            });
        } else {
            alert("123:" + treeNode.id)
            treeNode = zTree.addNodes(null, {
                id: (100 + newCount),
                pId: 0,
                isParent: isParent,
                name: "new node" + (newCount++)
            });
        }
        if (treeNode) {
            alert("123:")
            zTree.editName(treeNode[0]);
        } else {
            // alert("12344:")
            // alert("叶子节点被锁定，无法增加子节点");
            $("#promptMsg").html("请选择文件夹");
            mesShow();
        }
        // 调用新建文件夹弹框
        addFile();
        $("#confirm").on('click', function () {
            var name = $("#fileName").val();
            if (treeNode) {
                alert(treeNode.id)
                treeNode = zTree.addNodes(treeNode, {
                    id: (100 + newCount),
                    pId: treeNode.id,
                    isParent: isParent,
                    name: name
                });
                addFileHide()
            } else {
                alert("123:" + treeNode.id)
                treeNode = zTree.addNodes(null, {
                    id: (100 + newCount),
                    pId: 0,
                    isParent: isParent,
                    name: "new node" + (newCount++)
                });
                addFileHide()
            }
            if (treeNode) {
                alert("123:");
                zTree.editName(treeNode[0]);
                addFileHide()
            } else {
                // alert("12344:");
                // alert("叶子节点被锁定，无法增加子节点");
                $("#promptMsg").html("请选择文件夹");
                mesShow();
                addFileHide()
            }

        })

    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};
//        是否显示删除和重命名(不显示根节点的删除和重命名)
// function showRemoveBtn(treeId, treeNode){
//     return !(treeNode.level==0);
// }
// function showRenameBtn(treeId, treeNode){
//     return !(treeNode.level==0);
// }
//不显示子节点icon
function showIconForTree(treeId, treeNode) {
    return treeNode.isParent;
}

//		点击展开节点
var curExpandNode = null;
function beforeExpand(treeId, treeNode) {


    var pNode = curExpandNode ? curExpandNode.getParentNode() : null;
    var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
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
        for (i = 0, j = rootNodes.length; i < j; i++) {
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
            if (newParents != null) {
                var oldNode = curExpandNode;
                var oldParents = [];
                while (oldNode) {
                    oldNode = oldNode.getParentNode();
                    if (oldNode) {
                        oldParents.push(oldNode);
                    }
                }
                if (newParents.length > 0) {
                    zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
                } else {
                    zTree.expandNode(oldParents[oldParents.length - 1], false);
                }
            }
        }
    }
    curExpandNode = newNode;
}

function onExpand(event, treeId, treeNode) {
    curExpandNode = treeNode;
    if (treeNode.name == "工作流" && treeNode.pId == null) {
        var page = 1;
        // var rowNum=$("#choose-num").text();
        getFileData(page, null, treeNode.id, treeNode.name);
    }
    else if (treeNode.isParent == true) {
        var page = 1;
        getMenuDataFlow(page, treeNode.id, treeNode.name);
    }
}
function onExpand2(event, treeId, treeNode) {
    curExpandNode = treeNode;
    if (treeNode.name == "工作流" && treeNode.pId == null) {
        var page = 1;
        // var rowNum=$("#choose-num").text();
        getMenuDataFlowNotChange(page, null, treeNode.id, treeNode.name);
    }
    else if (treeNode.isParent == true) {
        var page = 1;
        getMenuDataFlowNotChange(page, treeNode.id, treeNode.name);
    }
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    //跳转到工作流
    if (treeNode.isParent == false) {
        var workSpaceName;
        $.ajax({
            url: basePath + "/drag/work/getwork.do",
            type: 'POST',
            data: {"id": treeNode.pId},
            async: false,
            success: function (data) {
                var returndata = JSON.parse(data);
                // console.log("workSpaceNameworkSpaceName:"+returndata);
                workSpaceName = returndata.name;
                window.location.href = "" + basePath + "/drag/dataModel/select.do?" +
                    "name=" + treeNode.name + "&flowId=" + treeNode.id + "&workSpaceName=" + workSpaceName + "";
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


    zTree.expandNode(treeNode, null, null, null, true);
}
function onClick2(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
    //跳转到工作流
    if (treeNode.isParent == false) {
        var workSpaceName;
        $.ajax({
            url: basePath + "/drag/work/getwork.do",
            type: 'POST',
            data: {"id": treeNode.pId},
            async: false,
            success: function (data) {
                var returndata = JSON.parse(data);
                // console.log("workSpaceNameworkSpaceName:"+returndata);
                workSpaceName = returndata.name;
                window.location.href = "" + basePath + "/drag/dataModel/select.do?" +
                    "name=" + treeNode.name + "&flowId=" + treeNode.id + "&workSpaceName=" + workSpaceName + "";
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


    zTree.expandNode(treeNode, null, null, null, true);
}

//        右键功能
function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX - 240, event.clientY);
    } else if (treeNode.level == 0) {
        zTree.selectNode(treeNode);
        showRMenu("root1", event.clientX - 240, event.clientY);
    } else if (treeNode && !treeNode.noR && !treeNode.isParent) {
        zTree.selectNode(treeNode);
        showRMenu("node1", event.clientX - 240, event.clientY);
    } else if (treeNode && !treeNode.noR && treeNode.isParent) {
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX - 240, event.clientY);
    }
}
function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type == "root") {
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
    }
    var windowHeigh=$(window).height()-170;
    if(y>windowHeigh){
        y=y-170;
    }
    rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        rMenu.css({"visibility": "hidden"});
    }
}
//        重命名
function edit() {
    $("#rMenu").hide();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if(treeNode['name'].length>20){
        treeNode['name']=treeNode['name'].substring(0,20);
    }
    console.log("ztree修改名：" + zTree.editName(treeNode));
    if (nodes.length == 0) {
        // alert("请先选择一个节点");
        $("#promptMsg").html("请先选择需要修改的文件");
        mesShow();
        return;
    }
    zTree.editName(treeNode);
};
//添加自定义控件
function addDiyDom(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        isParent = treeNode.isParent;
    var aObj = $("#" + treeNode.tId + "_a");
    if ($("#diyBtn_" + treeNode.id).length > 0) return;
    if (isParent && !treeNode.level == 0) {
        var editStr = "<span class='button diy' id='diyBtn_" + treeNode.tId
            + "' title='diy node' onfocus='this.blur();'></span>";
        aObj.append(editStr);
    }
    var btn = $("#diyBtn_" + treeNode.tId);
    console.log(btn);
    if (btn) btn.bind("click", function () {
        alert(123)

    });
};
// 删除目录树文件
function deleteData() {
    $('#deleteData').modal('show')
}
function deleteWorkSpace() {
    hideRMenu();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        // isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode.isParent == true) {
        $.ajax({
            url: basePath + "/drag/work/delete.do",
            type: 'POST',
            data: {"id": treeNode.id, "hdfsUrl": treeNode.curDir},
            // data: {"pid":treeNode.id,"isParent":treeNode.isParent,"name":treeNode.name},
            async: false,
            success: function (data) {
                var returndata = JSON.parse(data)
                switch (returndata.code) {
                    case 417:
                        toastr.error(returndata.msg);
                        break;
                    case 200:
                        toastr.success(returndata.msg);
                        refreshParentNode();
                        $('#deleteData').modal('hide');
                        $("#flowWorkTbody").empty();
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
    } else {
        //删除文件
        var data = {};
        data.id = treeNode.id;
        $.ajax({
            url: basePath + "/drag/flow/delete.do",
            type: 'POST',
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                console.log(data);
                switch (data.code) {
                    case 417:
                        toastr.error(data.msg);
                        break;
                    case 200:
                        toastr.success(data.msg);
                        refreshParentNode();
                        $('#deleteData').modal('hide');
                        $("#flowWorkTbody").empty();
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
    // alert("删除文件夹")

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
    /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
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
function refreshParentNode2() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo2"),
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


//以下为工作空间中查询的数据
function getMenuDataFlow(page, workspid, workName) {
    $("#PageWorkspid").val(workspid);
    $("#page").val(page);
    var rowCount = $("#choose-num").text();
    $.ajax({
        url: basePath + "/drag/flow/getflowlists.do",
        type: 'POST',
        dataType: "json",
        data: {"page": page, "workspid": workspid, "workName": workName,"rowCount":rowCount},
        success: function (json) {
            $("#flowWorkTbody").empty();//清空数据区
            curPage = json.curPage; //当前页
            // totalPage = json.totalPage; //总页数
            $("#totalpage").val(json.totalPage);
            var rightPage = "";
            var titleFlowName = "";
            var list = json.rows;
            if(list==""){
                $("#totalpage").val(1);
                rightPage= " <tr class='odd gradeX'>" +
                    " <td></td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "</tr>";
            }else{
                $.each(list, function (index, array) { //遍历json数据列
                    rightPage += " <tr class='odd gradeX'>" +
                        " <td>" +
                        " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='" + array['id'] + "' />" +
                        "<span></span>" +
                        "</label>" +
                        "</td>" +
                        "<td>" +
                        "<div class='fileType'>" +
                        "<i class='fa fa-file-code-o'></i>" +
                        "</div>" +
                        "<div class='fileContent'>" +
                        " <div class='fileTitle'>" +
                        "<a href='" + basePath + "/drag/dataModel/select.do?name=" + array['name'] + "&flowId=" + array['id'] + "&workSpaceName=" + workName + "'>" + array['name'] + "</a>" +
                        "</div>" +
                        "<div class='fileSize'>由 "+array['userName']+"  创建</div>" +
                        "</div>" +
                        "</td>" +
                        "<td>" +
                        "<div class='actions'>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='showWorkFlow(" + array['id'] + ")' title='查看工作流'>" +
                        "<i class='icon iconfont icon-chakan'></i>" +
                        " </a>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow(" + array['id'] + "," + array['workspid'] + " )' title='设置工作流'>" +
                        "<i class='icon iconfont icon-bianji2'></i>" +
                        " </a>" +
                        " <a href='javascript:;' onclick='deleteWorkFlow(" + array['id'] + ")' title='删除工作空间'>" +
                        "<i class='icon iconfont icon-cuowuguanbi'></i>" +
                        "</a>" +
                        " </div>" +
                        "<span>" + array['createTimes'] + "</span>" +
                        "<input type='hidden' id='" + array['id'] + "'  value='" + array['name'] + "'/>" +
                        "<input type='hidden' id='" + array['id'] + "a'  value='" + array['flowExplain'] + "'/>" +
                        " </td>" +
                        "</tr>";
                });
            }

            $("#flowWorkTbody").html(rightPage);
            $("#titleFlowName").html(workName);
            $("#workName").val(workName);
            $("#workspid").val(workspid);
            $("#inputWorksPid").val(workspid);

        },
        complete: function () { //生成分页条
            PaginatorChild();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    });

}

//新建工作流+不改变背景
function getMenuDataFlowNotChange(page, workspid, workName) {
    $("#PageWorkspid").val(workspid);
    $("#page").val(page);
    $("#titleFlowName").html(workName);
    $("#workName").val(workName);
    $("#workspid").val(workspid);
    $("#inputWorksPid").val(workspid)

}
//进入工作流展现
getFileData(1,null,0,"工作流");


function getFileData(page, rowNum, id, name) {
    var rowCount = $("#choose-num").text();
    $.ajax({
        url: basePath + "/drag/flow/selectAllWorkFlow.do",
        type: 'POST',
        dataType: "json",
        data: {"page": page,"rowCount":rowCount},
        // data:{"page":page,"rowNum":rowNum},
        // async: false,
        success: function (json) {
            $("#flowWorkTbody").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            $("#totalpage").val(totalPage);
            var rightPage = "";
            var titleFlowName = "";
            var list = json.rows;
            if (list==""){
                $("#totalpage").val(1);
                rightPage= " <tr class='odd gradeX'>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "</tr>";
            }else{
                $.each(list, function (index, array) { //遍历json数据列
                    // leftMenu += "<li><span class=\"tree2\"><a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array.name+"</a></span></li>";
                    rightPage += ' <tr class="odd gradeX">' +
                        " <td>" +
                        " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='" + array['id'] + "' />" +
                        "<span></span>" +
                        "</label>" +
                        "</td>" +
                        '<td onclick="intoWorkFlow(\''+array['name']+ '\',\'' + array['id'] + '\',\'' + array['workspaceName'] +'\')">' +
                        "<div class='fileType'>" +
                        "<i class='fa fa-file-code-o'></i>" +
                        "</div>" +
                        "<div class='fileContent'>" +
                        " <div class='fileTitle'>" +
                        "<a href='" + basePath + "/drag/dataModel/select.do?name=" + array['name'] + "&flowId=" + array['id'] + "&workSpaceName=" + array['workspaceName'] + "'>" + array['name'] + "</a>" +
                        "</div>" +
                        "<div class='fileSize'>由 "+array['userName']+"  创建</div>" +
                        "</div>" +
                        "</td>" +
                        "<td>" +
                        "<div class='actions'>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='showWorkFlow(" + array['id'] + ")' title='查看工作流'>" +
                        "<i class='icon iconfont icon-chakan'></i>" +
                        " </a>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow(" + array['id'] + "," + array['workspid'] + " )' title='设置工作流'>" +
                        "<i class='icon iconfont icon-bianji2'></i>" +
                        " </a>" +
                        " <a href='javascript:;' onclick='deleteWorkFlow(" + array['id'] + ")' title='删除工作空间'>" +
                        "<i class='icon iconfont icon-cuowuguanbi'></i>" +
                        "</a>" +
                        " </div>" +
                        "<span>" + array['createTimes'] + "</span>" +
                        "<input type='hidden' id='" + array['id'] + "'  value='" + array['name'] + "'/>" +
                        "<input type='hidden' id='" + array['id'] + "a'  value='" + array['flowExplain'] + "'/>" +
                        " </td>" +
                        "</tr>";
                });
            }

            $("#flowWorkTbody").html(rightPage);
            $("#titleFlowName").html(name);
            // $("a.creat_model").addClass("haveCheckBox");
            // $("a.haveCheckIcon").addClass("haveCheckIcon");
            // $("i.icon-arrow-down").addClass("haveCheckIcon");
            $("#workName").val(name);
            $("#workspid").val(id);
            $("#inputWorksPid").val(id);
            $("#page").val(""), $("#PageWorkspid").val("");
        },

        complete: function () { //生成分页条
            Paginator();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    });

}
function intoWorkFlow(name,flowId,workSpaceName) {
   window.location.href=basePath+"/drag/dataModel/select.do?name="+name+"&flowId="+flowId+"&workSpaceName="+workSpaceName;
}
// 新建工作流方法
function creat() {
    hideRMenu();
    addWorkDetails();
}

//        新建文件夹弹框显示
function addFile() {

    $("#addFile").modal('show')
}
//       新建文件夹弹框隐藏
function addFileHide() {
    $("#addFile").modal('hide')
}
//        新建工作流弹框显示
function addWorkDetails() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
    var nodes = treeObj.getNodeByParam("id", 0, null);
    treeObj.reAsyncChildNodes(nodes, "refresh");
    $("#basic").modal('show')


}

//用于首页进入工作流创建---start
addWorkOpen();
//获取浏览器参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function addWorkOpen() {
    var open = GetQueryString("open");
    if(open == 1){
        $("#basic").modal('show')
    }
}
//用于首页进入工作流创建---end

function cleanData() {
    $("#nameWork").val('');
    $("#flowExplainAA").val('');
}
//       新建工作流弹框隐藏
function addWorkDetailsHide() {
    $("#basic").modal('hide')
}
$('#basic').on('hidden.bs.modal', function () {
    // 执行一些动作...
    cleanData()
});
// end
//提示信息弹框
function mesShow() {
    $("#prompt").modal('show')
}
function mesHide() {
    $("#prompt").modal('hide')
}
// end

//回车键搜索
$("#searchName").keydown(function(event){
    event=document.all?window.event:event;
    if((event.keyCode || event.which)==13){
        selectFlowByNameMethod(1);
    }
});
//全局搜索工作流
function selectFlowByName(page) {
    selectFlowByNameMethod(page)
}
function selectFlowByNameMethod(page) {
    var workFlowName = $("#searchName").val();
    var rowCount = $("#choose-num").text();
    $.ajax({
        url: basePath + "/drag/flow/selectFlowByName.do",
        type: 'POST',
        dataType: "json",
        data: {"page": page,"rowCount":rowCount,"name": workFlowName},
        success: function (json) {
            $("#flowWorkTbody").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            $("#totalpage").val(totalPage);
            var rightPage = "";
            var titleFlowName = "";
            var list = json.rows;
            if (list==""){
                $("#totalpage").val(1);
                rightPage= " <tr class='odd gradeX'>" +
                    "<td></td>" +
                    "<td>- -</td>" +
                    "<td>- -</td>" +
                    "</tr>";
            }else{
                $.each(list, function (index, array) { //遍历json数据列
                    // leftMenu += "<li><span class=\"tree2\"><a href='"+basePath+"/drag/dataModel/select.do?name="+array['name']+"&flowId="+array['id']+"&workSpaceName="+workName+"'>"+array.name+"</a></span></li>";
                    rightPage += " <tr class='odd gradeX'>" +
                        " <td>" +
                        " <label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                        "<input type='checkbox' name = 'checkbox' class='checkboxes' ids='" + array['id'] + "' />" +
                        "<span></span>" +
                        "</label>" +
                        "</td>" +
                        "<td>" +
                        "<div class='fileType'>" +
                        "<i class='fa fa-file-code-o'></i>" +
                        "</div>" +
                        "<div class='fileContent'>" +
                        " <div class='fileTitle'>" +
                        "<a href='" + basePath + "/drag/dataModel/select.do?name=" + array['name'] + "&flowId=" + array['id'] + "&workSpaceName=" + array['workspaceName'] + "'>" + array['name'] + "</a>" +
                        "</div>" +
                        "<div class='fileSize'>由 "+array['userName']+"  创建</div>" +
                        "</div>" +
                        "</td>" +
                        "<td>" +
                        "<div class='actions'>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='showWorkFlow(" + array['id'] + ")' title='查看工作流'>" +
                        "<i class='icon iconfont icon-chakan'></i>" +
                        " </a>" +
                        "<a href='javascript:;' data-toggle='modal' onclick='updateWorkFlow(" + array['id'] + "," + array['workspid'] + " )' title='设置工作流'>" +
                        "<i class='icon iconfont icon-bianji2'></i>" +
                        " </a>" +
                        " <a href='javascript:;' onclick='deleteWorkFlow(" + array['id'] + ")' title='删除工作空间'>" +
                        "<i class='icon iconfont icon-cuowuguanbi'></i>" +
                        "</a>" +
                        " </div>" +
                        "<span>" + array['createTimes'] + "</span>" +
                        "<input type='hidden' id='" + array['id'] + "'  value='" + array['name'] + "'/>" +
                        "<input type='hidden' id='" + array['id'] + "a'  value='" + array['flowExplain'] + "'/>" +
                        " </td>" +
                        "</tr>";
                });
            }

            $("#flowWorkTbody").html(rightPage);
            // $("#titleFlowName").html(name);
            // $("#workName").val(name);
            // $("#workspid").val(id);
        },
        complete: function () { //生成分页条
            PaginatorByName();
        },
        error:function(data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })
}
// 弹框目录树滚动条
$(window).on("load", function () {
//    $.mCustomScrollbar.defaults.theme="dark";
    $("body .treeDeomBox").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
    // zTree添加滚动条
    $("body #workTreeScroll").mCustomScrollbar({
        theme: "dark-thin",
        axis:"yx",
        setLeft: 0,
        scrollbarPosition: "inside",
        scrollInertia: 1,
        autoDraggerLength: false,
        alwaysShowScrollbar: 0,
        autoHideScrollbar:true,
        autoExpandScrollbar:false
    });
});
// $('#treeDemo').on("click",'#treeDemo_1_ul',function (e) {
//     // e.stopPropagation()
//     $("a.creat_model").removeClass("haveCheckBox");
//     $("a.haveCheckIcon").removeClass("haveCheckIcon");
//     $("i.icon-arrow-down").removeClass("haveCheckIcon");
// });
// 下拉选择展示多少条数据
var clickType=true;
function showUl() {
    $('.chooseNum ul').show();
    clickType=false;
}
function hideUl() {
    $('.chooseNum ul').hide();
    clickType=true;
}
$('.chooseNum').mouseleave(function () {
    hideUl();
});
$('.chooseNum').on('click','.chooseNum-des',function () {
    if(clickType){
        showUl();
    }else {
        hideUl();
    }
});
$('.chooseNum').on('click','ul li',function () {
    var txt=$(this).text();
    $('#choose-num').html(txt);
    $(this).find('i').addClass('glyphicon-ok').parents('li').siblings().children('i').removeClass('glyphicon-ok');
    hideUl();
    var page=1;
    var workFlowName = $("#searchName").val();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes();
    if(nodes.length>0){
        treeNode = nodes[0];
        getMenuDataFlow(page, treeNode.id, treeNode.name);
    }else if(workFlowName==""||workFlowName==null){
        getFileData(page,null,0,"工作流");
    }else {
        selectFlowByNameMethod(page);
    }



});
