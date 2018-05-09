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
        url:basePath+"/drag/dataMiningCategory/selectAllDataMiningCategory.do",
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
        url:basePath+"/drag/work/selectZtree.do",
        autoParam:["id", "name","pId","isParent"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        showIcon: showIconForTree,
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
        beforeRename: beforeRename,

    }
};
function showIconForTree(treeId, treeNode) {
    return treeNode.level != 2;
};
/*var zNodes =[
    { id:1, pId:0, name:"模板根目录", open:true},
    { id:11, pId:1, name:"金融 - 折叠"},
    { id:111, pId:11, name:"叶子节点111"},
    { id:112, pId:11, name:"叶子节点112"},
    { id:113, pId:11, name:"叶子节点113"},
    { id:114, pId:11, name:"叶子节点114"},
    { id:12, pId:1, name:"父节点12 - 折叠"},
    { id:121, pId:12, name:"叶子节点121"},
    { id:122, pId:12, name:"叶子节点122"},
    { id:123, pId:12, name:"叶子节点123"},
    { id:124, pId:12, name:"叶子节点124"},
    { id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
];*/

var zNodes = nodeList;
var zNodes1=workFlowNodeList;
/*var zNodes1 =[
    { id:1, pId:0, name:"父节点1 - 展开", open:true},
    { id:11, pId:1, name:"父节点11 - 折叠"},
    { id:111, pId:11, name:"叶子节点111",icon:null},
    { id:112, pId:11, name:"叶子节点112",icon:null},
    { id:113, pId:11, name:"叶子节点113",icon:null},
    { id:114, pId:11, name:"叶子节点114"},
    { id:12, pId:1, name:"父节点12 - 折叠"},

];*/
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
    $("#submitFolder").on('click', function  confirm() {
        var name = $("#fileName").val();
            description = $("#fileDescription").val();
            if (name.length == null || $.trim(name) == ''){
                toastr.error("文件夹名称不能为空！");
                $("#fileName").val("");
            }else if(name.length>20){
            toastr.error("请书写小于20字符串的文件夹名称！");
            $("#fileName").val("");
        }else {

            // if( number == 0){
            if (pid != -1) {
                //添加文件夹
                 $.ajax({
                     url: basePath + "/drag/dataMiningCategory/insertCategoryFolder.do",
                     type: 'GET',
                     //data: {"pid": treeNode.id, "isParent": 1, "name": name, "PPid": treeNode.name},
                     data: {"dataMiningCategoryPid": treeNode.id, "dataMiningCategoryIsParent": 1, "dataMiningCategoryName": name,"dataMiningCategoryDescription":description},
                     async: false,
                     success: function (data) {
                         //window.location.reload();
                         var returndata = JSON.parse(data);
                         if(returndata.code == '417'){
                             toastr.error(returndata.msg);
                          refreshNode();
                             window.location.reload();
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
                     error:function(data){
                         if(data.responseText == "AjaxSessionTimeout"){
                             window.location.href=basePath;
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
                treeNode = zTree.addNodes(null, {
                    id: (100 + newCount),
                    pId: 0,
                    isParent: false,
                    name: "new node" + (newCount++)
                });
               // toastr.error("请先选择工作流！");
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
            //console.log(nodes[0].id);
            deleteData();
        } else if(treeNode.isParent){
            $('#deleteMsg').html('该文件夹可能不为空，确认要删除？');
            //console.log(nodes[0].id);
            deleteData();
        } else if(treeNode){
            $('#deleteMsg').html('确认删除文件？');
            //console.log(nodes[0].id);
            deleteData();
        }
    }
};


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
    //删除创建模板的不可选择状态
    //$("a.creat_model").removeClass("haveCheckBox");
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
    if (treeNode.name == "数据挖掘分析" && treeNode.pId == null) {
        var page = 1;
        //getFileData(page,treeNode.id, treeNode.name);
        //删除创建模板的不可选择状态
        // $("a.creat_model").removeClass("haveCheckBox");
        // $("#basic").modal('hide');
        getDataMiningPageSearch(null);
    }else if (treeNode.isParent == true) {
        var page = 1;
        getFileData(page,treeNode.id, treeNode.name);
    }
}

function onClick(e,treeId, treeNode) {
    if(treeNode.name=="数据挖掘分析"){
        $("a.creat_model").addClass("haveCheckBox");
    }else {
        $("a.creat_model").removeClass("haveCheckBox");
    }
    var curDir=treeNode.curDir;
    var page=1;
    var rowCount=$("#choose-num").text();
   // getFileData(page,curDir,rowCount);
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
    if (treeNode.isParent == 0){
        if (newName.length == 0||newName.trim().length==0) {
            // alert("节点名称不能为空.");
            $("#promptMsg").html("重命名不能为空");
            mesShow();
            refreshParentNode();
            //return false;
        }else if (newName.length>20){
            // alert("长度不能超过20个字符");
            $("#promptMsg").html("字符长度不能超过20个");
            mesShow();
            refreshParentNode();
            //return false;
        }else if(treeNode.name==newName){
            toastr.warning("名称未做修改");
        }else {
            $.ajax({
                url:basePath+"/drag/dataMining/updateDataMiningName.do",
                type: 'GET',
                dataType: "json",
                data:{"id":treeNode.id,"name":newName} ,
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
                error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                }
            });
            return true;
        }
    }else {
        if (newName.length == 0||newName.trim().length==0) {
            // alert("节点名称不能为空.");
            $("#promptMsg").html("重命名不能为空");
            mesShow();
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            setTimeout(function(){zTree.editName(treeNode)}, 10);
            refreshParentNode();
            //return false;
        }else if (newName.length>20){
            // alert("长度不能超过20个字符");
            $("#promptMsg").html("字符长度不能超过20个");
            mesShow();
            refreshParentNode();
            //return false;
        }else{
            var id=treeNode.id;
            var curDir=treeNode.curDir;
            var newName=newName;
            if(treeNode.name==newName){
                toastr.warning("名称未做修改");
            }else{
                $.ajax({
                    url:basePath+"/drag/dataMiningCategory/updateCategoryName.do",
                    type: 'GET',
                    dataType: "json",
                    data:{"dataMiningCategoryId":id,"curDir":curDir,"dataMiningCategoryName":newName},
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
                    error:function(data){
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    }
                });
            }
            return true;
        }
        return true;
    }
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
        alert(123);
    });
};
//批量删除提示框
function deleteAllTemplate() {
    var length=$("input:checked:not(.group-checkable)").length;
    if (length==0){
        $('#deleteConfirm').addClass('haveCheckBox');
        toastr.warning("请选择要删除的挖掘分析项");
    }else {
        $('#deleteMsg2').html("确认删除已选中挖掘分析项吗？");
        batchDeleteData();
    }
}


// 删除单个指标项
function deleteData1(id) {
    $("#idHiddenValue").val(id);
    $('#deleteData1').modal('show');
}
//确认删除单个模板
function delete1() {
    //todo
    var id = $("#idHiddenValue").val();
    $.ajax({
        url:basePath+"/drag/dataMining/deleteOne.do",
        data: "dataMiningId="+id,
        type:"GET",
        dataType:"json",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //console.log(data);
            window.location.reload();
            //var returndata = JSON.parse(data);
            if(data.code == '424'){
                toastr.error(data.msg);
                refreshNode();
            } else if(data.code == "200") {
                toastr.success(data.msg);
                refreshNode();
                window.location.reload();
            }
        },
        error:function(data){
            if(data.responseText == "AjaxSessionTimeout"){
                window.location.href=basePath;
                return;
            }
        }
    });
    $('#deleteData1').modal('hide');
}

// 批量删除模板提示框
function batchDeleteData() {
    var ids = "";
    $.each($(".checkboxes:checked"),function(){
        ids += $(this).val()+"-";
    });
    //alert(ids);
    $("#batchDeleteDataMining").val(ids);
    $('#deleteData2').modal('show');
}
//确认批量删除
function batchDeleteHide() {
    //todo
    var ids = $("#batchDeleteDataMining").val();
    $.ajax({
        url:basePath+"/drag/dataMining/deleteMany.do",
        data: "dataMiningIds="+ids,
        type:"GET",
        dataType:"json",
        contentType: "application/json;charset=UTF-8",
        success:function (result) {
            console.log(result);
            window.location.reload();
        },
        error:function (data) {
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            alert("数据批量删除失败");
        }
    });
    $('#deleteData2').modal('hide');
}
//删除
function deleteData() {
    $('#deleteData').modal('show');
}

function hideDeleteData() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        id = treeNode.id;
        if (treeNode.isParent == 0){
            $.ajax({
                url:basePath+"/drag/dataMining/deleteOne.do",
                data: "dataMiningId="+id,
                type:"GET",
                dataType:"json",
                contentType: "application/json;charset=UTF-8",
                success:function (result) {
                    console.log(result);
                    window.location.reload();
                },
                error:function (data) {
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    alert("数据删除失败");
                }
            });
        }else {
            $.ajax({
                url:basePath+"/drag/dataMiningCategory/deleteCategory.do",
                data: "id="+id,
                type:"GET",
                dataType:"json",
                contentType: "application/json;charset=UTF-8",
                success:function (result) {
                    console.log(result);
                    window.location.reload();
                },
                error:function (data) {
                        if(data.responseText == "AjaxSessionTimeout"){
                            window.location.href=basePath;
                            return;
                        }
                    alert("数据删除失败");
                }
            });
        }

    $('#deleteData').modal('hide');
}
function creat() {
    hideRMenu();
    addDataExcavate();
}
//新建模板
function addDataExcavate() {
    $("#basic").modal('show');
}
//查看模板
function lookDataExcavate(id) {
    $.ajax({
        url:basePath+"/drag/dataMining/selectById.do",
        data: {
            dataMiningId:id
        },
        type:"GET",
        dataType:"json",
        success:function (result) {
            //show_dataMining(result);
            $("#lookH4Title").html("查看"+result.obj.dataMiningCategoryName);
            $("#lookDataMining").val(result.obj.dataMiningName);
            $("#analyzeItemExplain2").val(result.obj.dataMiningDescription);
            $("#lookWorkName").val(result.obj.flowName);
        },
        complete:function(){
            //getPageBar();
        },
        error:function (data) {
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            alert("数据加载失败");
        }
    });
    $("#lookDataExcavate").modal('show');
}

//编辑模板
function editDataExcavate(id) {
    //alert(id);
    $.ajax({
        url:basePath+"/drag/dataMining/selectById.do",
        data: {
            dataMiningId:id
        },
        type:"GET",
        dataType:"json",
        success:function (result) {
            //show_update_dataMining(result);
            $("#lookEditTitle").html("编辑"+result.obj.dataMiningCategoryName);
            $("#analyzeItemName2").val(result.obj.dataMiningName);
            $("#analyzeItemExplain3").val(result.obj.dataMiningDescription);
            $("#editDataMiningContent").val(result.obj.dataMiningId);
        },
        complete:function(){
            //getPageBar();
        },
        error:function (data) {
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            alert("数据加载失败");
        }
    });
    $("#editDataExcavate").modal('show');
}

//目录执行删除操作
function realDelete() {
    $('#deleteData').modal('hide');
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    var curDir=treeNode.curDir;
    //$.post(basePath+"/drag/filemanage/delDir.do",{curDir:curDir});
    // var callbackFlag = $("#callbackTrigger").attr("checked");
    //  zTree.removeNode(treeNode, callbackFlag);
    setTimeout("toastr.success('删除成功');",500);
    setTimeout("refreshParentNode();",500);
    //$("#flowWorkTbody").empty();//执行右键删除的同时清空数据区
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

//getFileData(1,0,"数据挖掘分析");
function getFileData(page,id,name) {
    var tmp = {};
    tmp.page = page;
    tmp.id=id;
    tmp.name=name;
    $.ajax({
        url:basePath+"/drag/dataMining/selectDeepDataMiningCategory.do",
        type: 'GET',
        dataType: "json",
        data:{"page":page,"dataMiningCategoryId":id,"dataMiningCategoryName":name},
        /*beforeSend:function(){
            $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画
        },*/
        success:function(json){
            $("#emptyTr").empty();//清空数据区
            curPage = json.curPage; //当前页
            totalPage = json.totalPage; //总页数
            $("#totalpage").val(totalPage);
            var li = "";
            var list = json.rows;
            if (list==""){
                $("#totalpage").val(1);
                li +="   <tr class='odd gradeX'>"
                    +" <td></td>"
                    +" <td>- -</td>"
                    +" <td>- -</td>"
                    +" </tr>";
            }else {
                $.each(list,function(index,array){ //遍历json数据列
                    console.log(list[index]);
                    li +="<tr class='odd gradeX'>" +
                        "<td>" +
                        "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                        "<input type='checkbox' value='"+array.dataMiningId+"' name = 'checkbox' class='checkboxes'  />" +
                        "<span></span>" +
                        "</label>" +
                        "</td>" +
                        "<td ><div class='fileType'>" +
                        "<i class='fa fa-file-code-o'></i></div>" +
                        "<div class='fileContent'>" +
                        "<div class='fileTitle'>"+array['dataMiningName']+"</div>" +
                        "<div class='fileSize'>"+array.loginname+"</div></div>" +
                        "</td>" +
                        "<td class=\"table_three_td_color\">"+array.flowName+"</td>" +
                        "<td><div class='actions'>" +
                        "<a href='javascript:void(0)' data-toggle='modal' onclick='lookDataExcavate("+array.dataMiningId+")' title='查看模板'>" +
                        "<i class='icon iconfont icon-chakan'></i></a>" +
                        "<a href='javascript:void(0)' data-toggle='modal' onclick='editDataExcavate("+array.dataMiningId+")' title='编辑模板'>" +
                        "<i class='icon iconfont icon-bianji2'></i></a>" +
                        "<a href='javascript:void(0)' onclick='deleteData1("+array.dataMiningId+")'  title='删除模板'>" +
                        "<i class='icon iconfont icon-cuowuguanbi'></i></a></div><span> </span>" +
                        "</td>" +
                        "<td>"+array.createTime+"</td>" +
                        "</tr>";
                });
            }

            $("#emptyTr").append(li);
        },
        complete:function(){ //生成分页条
            PaginatorFileData();
        },
        error:function(data){
        if(data.responseText == "AjaxSessionTimeout"){
            window.location.href=basePath;
            return;
        }
    }
        // error:function(){
        //     // alert("数据加载失败");
        //     $("#promptMsg").html("数据加载失败");
        //     mesShow();
        // }
    });
}
function PaginatorFileData() {
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
            if (num != curPage) {
                // var rowNum = $("#choose-num").text();
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
                getFileData(num,treeNode.id,treeNode.name);

            }
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

function getFileName(o){
    var pos=o.lastIndexOf("\\");
    return o.substring(pos+1);
}

// 空格过滤
function removeAllSpace(str) {
    return str.replace(/\s+/g, "");
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
//确认按钮，需要验证关联工作流
function sureAnalyzeItem() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo1"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
        zTree_ = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes_ = zTree_.getSelectedNodes(),
        treeNode_ = nodes_[0];
    var miningName = $("#analyzeItemName").val();
    var miningDescription = $("#analyzeItemExplain1").val();
       //console.log(nodes)
    if (miningName.length == 0||miningName.trim().length==0) {
        $("#analyzeItemName + .title_text").remove();
        $("#analyzeItemName ").after(" <p class='title_text'>* 分析项名称不能为空</p>");
        return;
    }else if (miningName.length>20){
        $("#analyzeItemName + .title_text").remove();
        $("#analyzeItemName ").after(" <p class='title_text'>* 字符长度不能超过20个</p>");
        return;
    }else if (nodes.length == 0) {
        $("#analyzeItemName + .title_text").remove();
        $(".selectMag").html("请先选择一条工作流");
        return;
    }
    if(treeNode.isParent==true){
        $(".selectMag").html("请先选择一条工作流");
    }else {
        $.ajax({
            url: basePath + "/drag/dataMiningCategory/insertMiningAndCategory.do",
            type: 'GET',
            data: {"workFlowId": treeNode.id, "dataMiningCategoryId": treeNode_.id, "dataMiningName": miningName,"dataMiningDescription":miningDescription},
            async: false,
            success: function (data) {
                window.location.reload();
                var returndata = JSON.parse(data);
                if(returndata.code == '417'){
                    toastr.error(returndata.msg);
                    refreshNode()
                }else if (returndata.code == '404'){
                    toastr.error(returndata.msg);
                    refreshNode()
                }
                else if(returndata.code == "200") {
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
            error:function(data){
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
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
       // }
        //关闭对话框
        $("#basic").modal('hide');
    }
}

$("#closeAnalyzeItem").click(function () {
    $("#analyzeItemName").val("");
    $("#analyzeItemExplain1").val("");
    $("#analyzeItemName + .title_text").remove("");
});

function cleanData() {
    $("#fileDescription").val("");
}

$("#closeAnalyzeItem1").click(function () {
    $("#analyzeItemName2 + .title_text").remove();
});

//确认弹框编辑的分析项
function sureEditAnalyzeItem() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo3"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    id = $("#editDataMiningContent").val();
    dataMiningName = $("#analyzeItemName2").val();
    dataMiningDescription = $("#analyzeItemExplain3").val();
    console.log(nodes)

    if (dataMiningName.length == 0||dataMiningName.trim().length==0) {
        $("#analyzeItemName2 + .title_text").remove();
        $("#analyzeItemName2 ").after(" <p class='title_text'>* 分析项名称不能为空</p>");
        return;
    }else if (dataMiningName.length>20){
        $("#analyzeItemName2 + .title_text").remove();
        $("#analyzeItemName2 ").after(" <p class='title_text'>* 字符长度不能超过20个</p>");
        return;
    }else if (nodes.length == 0) {
        $("#analyzeItemName2 + .title_text").remove();
        $(".selectMag1").html("请先选择一条工作流");
        return;
    }
    if(treeNode.isParent==true){
        $(".selectMag1").html("请先选择一条工作流");
    }else {
        $.ajax({
            url:basePath+"/drag/dataMining/updateOne.do",
            data: {
                "dataMiningId":id,
                "dataMiningName":dataMiningName,
                "dataMiningDescription":dataMiningDescription,
                "workFlowId":treeNode.id
            },
            type:"GET",
            dataType:"json",
            success:function (data) {
                window.location.reload();
                var returndata = JSON.parse(data);
                if(returndata.code == '417'){
                    toastr.error(returndata.msg);
                    refreshNode();
                }else if(returndata.code == "200") {
                    toastr.success(returndata.msg);
                    refreshNode();
                    //window.location.reload();
                }
            },
            complete:function(){
                //getPageBar();
            },
            error:function(data){
                    if(data.responseText == "AjaxSessionTimeout"){
                        window.location.href=basePath;
                        return;
                    }
                alert("数据更新失败");
            }
        });
        //关闭对话框
        $("#editDataExcavate").modal('hide');
    }
}
//创建验证
// function checkAnalyzeItemName() {
//     var analyzeItemName=$("#analyzeItemName").val()
//     if(analyzeItemName==""){
//         $("#analyzeItemName + .title_text").remove();
//         $("#analyzeItemName ").after(" <p class='title_text'>* 不能为空哦</p>");
//     }else {
//         $("#analyzeItemName + .title_text").remove();
//     }
// }

//编辑验证
// function checkAnalyzeItemNameEdit() {
//     var analyzeItemName=$("#analyzeItemName2").val()
//     if(analyzeItemName==""){
//         $("#analyzeItemName2 + .title_text").remove();
//         $("#analyzeItemName2 ").after(" <p class='title_text'>* 不能为空哦</p>");
//     }else {
//         $("#analyzeItemName2 + .title_text").remove();
//     }
//
// }

getDataMiningPageSearch(null);//全局搜索,初始化加载数据

$("#clickButton").click(function () {
    getDataMiningPageSearch(1);
});


function getDataMiningPageSearch(page) {
    var  searchName = $("#searchName").val();
        $.ajax({
            url: basePath + "/drag/dataMining/getManyOrOne.do",
            type: 'GET',
            dataType: "json",
            data: {
                "categoryName": searchName,
                "page": page
            },
            /*beforeSend:function(){
                $("#tbodyList").append("<span id='loading'>loading...</span>");//显示加载动画
            },*/
            success: function (json) {
                //alert(json);
                $("#emptyTr").empty();//清空数据区
                curPage = json.curPage; //当前页
                totalPage = json.totalPage; //总页数
                $("#totalpage").val(totalPage);

                var li = "";
                var list = json.rows;
                if (list == "") {
                    $("#totalpage").val(1);
                    li += "   <tr class='odd gradeX'>"
                        + " <td></td>"
                        + " <td>- -</td>"
                        + " <td>- -</td>"
                        + " </tr>";
                } else {
                    $.each(list, function (index, array) { //遍历json数据列
                        console.log(list[index]);
                        li += "<tr class='odd gradeX'>" +
                            "<td>" +
                            "<label class='mt-checkbox mt-checkbox-single mt-checkbox-outline'>" +
                            "<input type='checkbox' value='"+array.dataMiningId+"' name = 'checkbox' class='checkboxes'  />" +
                            "<span></span>" +
                            "</label>" +
                            "</td>" +
                            "<td ><div class='fileType'>" +
                            "<i class='fa fa-file-code-o'></i></div>" +
                            "<div class='fileContent'>" +
                            "<div class='fileTitle'>" + array['dataMiningName'] + "</div>" +
                            "<div class='fileSize'>" + array.loginname + "</div></div>" +
                            "</td>" +
                            "<td class=\"table_three_td_color\">" + array.flowName + "</td>" +
                            "<td><div class='actions'>" +
                            "<a href='javascript:void(0)' data-toggle='modal' onclick='lookDataExcavate(" + array.dataMiningId + ")' title='查看模板'>" +
                            "<i class='icon iconfont icon-chakan'></i></a>" +
                            "<a href='javascript:void(0)' data-toggle='modal' onclick='editDataExcavate(" + array.dataMiningId + ")' title='编辑模板'>" +
                            "<i class='icon iconfont icon-bianji2'></i></a>" +
                            "<a href='javascript:void(0)' onclick='deleteData1(" + array.dataMiningId + ")'  title='删除模板'>" +
                            "<i class='icon iconfont icon-cuowuguanbi'></i></a></div><span> </span>" +
                            "</td>" +
                            "<td>" + array.createTime + "</td>" +
                            "</tr>";
                    });
                }

                $("#emptyTr").append(li);
            },
            error:function(data){
                if(data.responseText == "AjaxSessionTimeout"){
                    window.location.href=basePath;
                    return;
                }
            },
            complete: function () { //生成分页条
                PaginatorSearch();
            },
            // error:function(){
            //     // alert("数据加载失败");
            //     $("#promptMsg").html("数据加载失败");
            //     mesShow();
            // }
        });

}
function PaginatorSearch() {
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
            if (num != curPage) {
                // var rowNum = $("#choose-num").text();
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
                // getFileData(num,rowNum);
                getDataMiningPageSearch(num);

            }
        }
    });
}

