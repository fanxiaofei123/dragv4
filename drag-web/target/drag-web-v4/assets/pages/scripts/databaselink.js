/**
 * Created by gongmingxing on 2017/7/26/ 17:49.
 */

/**
 * 点击右上角关闭按钮之后,重置form表单。
 */
function closeform() {
    // $("#connect_save").val("addNodes");
    // $("#parame_form")[0].reset();
    // $("#la_errorinfo").html("");
    $("#connect_save").val("addNodes");
    $("#dataLink").hide();
    // $("#la_errorinfo").html("");
    $("#parame_form")[0].reset();
    $("#ta_exception").empty();
    $(".linkNameError").remove();
    $(".linkDataBaseError").remove();
    $(".hostIpError").remove();
    $(".portError").remove();
    $(".userNameError").remove();
    $(".passwordError").remove();
    $("#connect_test").attr("disabled",false);
    $('#ta_prepare').hide();
    allowShowTestResult = false;
}
//检验连接名字
function checkLinkName() {
    var databaseName = $.trim($("#resource_linkname").val());
    var value = $.trim($("#connect_save").val());
    if(databaseName.length>20){
        $(".linkNameError").remove();
        $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;连接名不能超过20个字！</p>')
        return false
    }else {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var selectedNode = zTree.getSelectedNodes()[0];
        var currentNodes = zTree.getNodes();
        var databaseName = $.trim($("#resource_linkname").val());
        if(databaseName){
            if(zNodes && Array.isArray(zNodes)){
                var sameFlag=false;
                currentNodes.forEach(function (item, index, array) {
                    if(databaseName == item['name']){
                        if(value == "editNodes" && databaseName != selectedNode.name){
                            sameFlag = true;
                        }else if(value == "addNodes"){
                            sameFlag=true;
                        }
                    }
                });
                if(sameFlag){
                    $(".linkNameError").remove();
                    $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;连接名重复了！</p>')
                    return false;
                }else {
                    $(".linkNameError").remove();
                    return true;
                }
            }
        }else {
            $(".linkNameError").remove();
            $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;不能为空哦！</p>');
            return false;
        }
    }
}



//检验数据库名称
function checkDataBaseName() {
    var resource_databaseName = $.trim($("#resource_databaseName").val());
    if(!resource_databaseName){
        $('.linkDataBaseError').remove();
        $("#resource_databaseName").after('<p class="linkDataBaseError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false
    }else if(resource_databaseName.length>50) {
        $(".linkDataBaseError").remove();
        $("#resource_databaseName").after('<p class="linkDataBaseError font-orange linkError">·&nbsp;数据库名不能超过50个字！</p>')
        return false
    }else {
        $('.linkDataBaseError').remove();
        return true
    }
}
//检验主机
function checkHostIp() {
    var resource_hostIp = $.trim($("#resource_hostIp").val());
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    if(resource_hostIp){
        if(reg.test(resource_hostIp)){
            $(".hostIpError").remove();
            return true
        }else {
            $(".hostIpError").remove();
            $("#resource_hostIp").after('<p class="hostIpError font-orange linkError">·&nbsp;IP书写格式错误！</p>')
            return false;
        }
    }else {
        $(".hostIpError").remove();
        $("#resource_hostIp").after('<p class="hostIpError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false;
    }
}

//检验端口号
function checkPort() {
    var resource_port = $.trim($("#resource_port").val());
    if(resource_port){
        if (/^[0-9]{1,5}$/g.test(resource_port) && parseInt(resource_port)<65535 && parseInt(resource_port)>0){
            $(".portError").remove();
            return true
        }else {
            $(".portError").remove();
            $("#resource_port").after('<p class="portError font-orange linkError">·&nbsp;端口号格式错误！</p>');
            return false;
        }
    }else {
        $(".portError").remove();
        $("#resource_port").after('<p class="portError font-orange linkError">·&nbsp;不能为空哦！</p>');
        return false;
    }
}

//检验用户名
function checkUserName() {
    var resource_username = $.trim($("#resource_username").val());
    if(!resource_username){
        $('.userNameError').remove();
        $("#resource_username").after('<p class="userNameError font-orange linkError">·&nbsp;不能为空哦</p>')
        return false
    }else if(resource_username.length>50){
        $(".userNameError").remove();
        $("#resource_username").after('<p class="userNameError font-orange linkError">·&nbsp;用户名不能超过50个字！</p>')
        return false
    }else{
        $('.userNameError').remove();
        return true
    }
}

//校验密码
function checkPassword() {
    var resource_password = $.trim($("#resource_password").val());
    if(!resource_password){
        $('.passwordError').remove();
        $("#resource_password").after('<p class="passwordError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false
    }else if(resource_password.length>20){
        $(".passwordError").remove();
        $("#resource_password").after('<p class="passwordError font-orange linkError">·&nbsp;密码不能超过20个字！</p>')
        return false
    }else {
        $('.passwordError').remove();
        return true
    }
}

$('#dataLink').on('hidden.bs.modal', function (e) {
    // do something...
    $(".linkError").remove();
})

/**
 * 数据库连接测试
 * @constructor
 */
//用一个变量来保存点击关闭按钮后，再点击新建连接时不允许显示上一次的错误信息。
var allowShowTestResult = true;
function LinkDataBaseTest(){
    $("#connect_test").attr("disabled",true);
    $("#la_errorinfo").html("");
    var type = $.trim($("#resource_type").val());
    var databaseName = $.trim($("#resource_databaseName").val());
    var hostIp = $.trim($("#resource_hostIp").val());
    var port = $.trim($("#resource_port").val());
    var userName = $.trim($("#resource_username").val());
    var passWord = $.trim($("#resource_password").val());
    var connectNmae = $.trim($("#resource_linkname").val());
    var textarea = $("#ta_exception");
    // textarea.empty();
    console.log("===============")
    var config = {
        'type':type,
        'databaseName':databaseName,
        'hostIp':hostIp,
        'hostPort':port,
        'userName':userName,
        'passWord':passWord,
        'connectNmae':connectNmae
    };
    console.log(basePath+"/drag/ReadResource/linktest.do")
    console.log(config);
    $("#ta_exception").hide();
    $.ajax({
        url:basePath+"/drag/ReadResource/linktest.do",
        type: 'POST',
        data:config,
        async: true,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        //cache: false,
        //contentType: false,
        //processData: false,
        beforeSend:function () {
          $('#ta_prepare').show();
        },
        success:function(data){
            allowShowTestResult = true;
            console.log(data);
            $("#ta_prepare").hide();
            $("#ta_exception").show();
            if(data.indexOf("失败")!=-1){
                if(allowShowTestResult){
                    textarea.html("<span class='font-orange'>"+data+"</span>");
                }
                // textarea.val("未知错误！");
            }else {
                if(allowShowTestResult){
                    allowShowTestResult = true;
                    textarea.html("<span class='font-color'>"+data+"</span>");
                }
                // textarea.val(data);
                console.log("连接成功！" == data);
            }
            $("#connect_test").attr("disabled",false);
        },
        error:function(){
            allowShowTestResult = true;
            console.log("回传数据失败！");
            if(allowShowTestResult){
                textarea.html("<span class='font-orange'>未知错误!</span>");
            }
            // textarea.val("未知错误！");
            // $("#connect_save").attr("disabled", true);
            $("#connect_test").attr("disabled",false);
        }
    })
}

/**
 * 取消连接数据库
 */
function cancleLink(){
    //每次点击取消按钮时，将保存按钮的状态重置为新建连接;
    $("#connect_save").val("addNodes");
    $("#dataLink").hide();
    $("#la_errorinfo").html("");
    $("#parame_form")[0].reset();
    $("#ta_exception").empty();
    $(".linkNameError").remove();
    $(".linkDataBaseError").remove();
    $(".hostIpError").remove();
    $(".portError").remove();
    $(".userNameError").remove();
    $(".passwordError").remove();
    $("#connect_test").attr("disabled",false);
    $('#ta_prepare').hide();
    allowShowTestResult = false;
}
/**
 * 保存连接
 */
function saveLink() {
    var value = $.trim($("#connect_save").val());
    var type = $.trim($("#resource_type").val());
    var databaseName = $.trim($("#resource_databaseName").val());
    var hostIp = $.trim($("#resource_hostIp").val());
    var port = $.trim($("#resource_port").val());
    var userName = $.trim($("#resource_username").val());
    var passWord = $.trim($("#resource_password").val());
    var connectNmae = $.trim($("#resource_linkname").val());
    // var textarea = $("#ta_exception");

    var checkFunc = [checkLinkName, checkDataBaseName, checkHostIp, checkPort, checkUserName, checkPassword];
    var result = checkFunc.every(function (item, index, array) {
        return item();
    });
    if (result) {
        var zTree, rMenu;
        if ("addNodes" == value) {
            // console.log(basePath+"/drag/ReadResource/savelink.do")
            // console.log(config);
            var config = {
                'type': type,
                'databaseName': databaseName,
                'hostIp': hostIp,
                'hostPort': port,
                'userName': userName,
                'passWord': passWord,
                'connectNmae': connectNmae
            };
            $.ajax({
                url: basePath + "/drag/ReadResource/savelink.do",
                type: 'POST',
                dataType: "json",
                data: config,
                success: function (data) {
                    console.log("==============" + data[0].errorInfo);
                    if (data[0].errorInfo != "" && data[0].errorInfo != null && data[0].errorInfo != undefined) {
                        //data[0].errorInfo是后台验证信息。
                        // $("#la_errorinfo").html(data[0].errorInfo);
                    } else {
                        zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        zTree.addNodes(null, data);
                        $("#dataLink").modal('hide');
                        cancleLink();
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
                            nodeNameStatus['treeDemo_id_ul']='treeDemo_'+ele_id+'_ul';
                            nodeNameStatus['treeDemo_id_switch']='treeDemo_'+ele_id+'_switch';
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
                        sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));
                        sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
                    }
                },
                error: function () {
                    // $("#la_errorinfo").html("保存失败，请重试！");
                }
            })
        } else if ("editNodes" == value) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];
            var config = {
                'primaryKey': treeNode.id,
                'type': type,
                'databaseName': databaseName,
                'hostIp': hostIp,
                'hostPort': port,
                'userName': userName,
                'passWord': passWord,
                'connectNmae': connectNmae
            };
            $.ajax({
                url: basePath + "/drag/ReadResource/editLink.do",
                type: 'POST',
                dataType: "json",
                data: config,
                success: function (data) {
                    if (data.errorInfo != "" && data.errorInfo != null && data.id != treeNode.id) {
                        $("#la_errorinfo").html(data.errorInfo);
                    } else {
                        var treeNodeName=sessionStorage.getItem('treeNodeName')
                        var nodeNameList=JSON.parse(sessionStorage.getItem('nodeNameList'));
                        if(treeNodeName){
                            nodeNameList.forEach(function (item, index, array) {
                                if(item['ele_text']==treeNodeName){
                                    item['ele_text']=config.connectNmae
                                }
                            });
                            sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));
                            sessionStorage.setItem('treeNodeName',"")
                        }else {
                            console.log('需要记录新的节点信息!!')
                        }


                        zTree = $.fn.zTree.getZTreeObj("treeDemo");
                        nodes = zTree.getSelectedNodes(),
                            treeNode = nodes[0];
                        treeNode.name = config.connectNmae;
                        zTree.updateNode(treeNode);
                        zTree.reAsyncChildNodes(treeNode, "refresh");
                        $("#connect_save").val("addNodes");
                        $("#dataLink").modal('hide');
                        cancleLink();
                    }
                },
                error: function () {
                    $("#la_errorinfo").html("保存失败，请重试！");
                }
            });
        }
    }
}

/**
 * 监听搜索框的input，实时监听input的变化，进行模糊搜索。
 */
function onTextChangeListener(){

    var nodeNameList=JSON.parse(sessionStorage.getItem('nodeNameList'));
    var zLinkNodeNamesStatusList=JSON.parse(sessionStorage.getItem('zLinkNodeNamesStatusList'));

    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    /**
     * 每次更新时，先显示全部节点
     */
    var nodes = zTree.getNodes();
    // 每次都要讲节点前面的+-号信息展示出来
    zTree.showNodes(nodes);
    /**
     * 隐藏不匹配的节点
     */
    var hideParentNodes=zTree.getNodesByFilter(filterFunc);
    for(var i=0; i< hideParentNodes.length; i++){
        zTree.hideNode(hideParentNodes[i]);
    }
    //搜索的时候让展开收起的+-号不变。
    zLinkNodeNamesStatusList.forEach(function (item1, index1, arr1) {
        nodeNameList.forEach(function (item2, index2, arr2) {
            if(item1['name']==item2['ele_text']){
                if(item1['closed']==true){
                    console.log(11);
                    var m=item2['treeDemo_id_a'];
                    $("#"+m).addClass('close_icon');
                    var treeDemo_id_switch = item2['treeDemo_id_switch'];
                    $("#"+treeDemo_id_switch).removeClass("noline_close")
                    $("#"+treeDemo_id_switch).addClass("noline_open")
                }else {
                    console.log(222)
                }
            }
        })
    });
    sessionStorage.setItem('zLinkNodeNamesStatusList',JSON.stringify(zLinkNodeNamesStatusList));
    sessionStorage.setItem('nodeNameList',JSON.stringify(nodeNameList));
}


/**
 * 过滤节点。
 * @param node
 * @returns {boolean}
 */
function filterFunc(node){
    var _keywords=$("#serachLink").val();
    //如果是父亲节点，而且不包含搜索词，则取出父节点，这是需要过滤的。
    if(node.isParent && node.name.indexOf(_keywords)== -1) return true;
    return false;
};



