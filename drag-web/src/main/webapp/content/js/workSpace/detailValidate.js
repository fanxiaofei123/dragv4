/**
 * Created by cdyoue on 2017/11/23.
 */
// ----------------------空格过滤-----------------------------
function removeAllSpace(str) {
    return str.replace(/\s+/g, "");
}
//------------------------------------------------------------

// ----------------------检验连接名字-------------------------
function checkLinkName() {
    var databaseName = $.trim($("#resource_linkname").val());
    if (databaseName.length > 20) {
        $(".linkNameError").remove();
        $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;连接名不能超过20个字！</p>')
        return false
    } else {
        var connNames = getLinkNames();
        // var connNames=JSON.parse(linkNameInfo)['connNames'];
        var connNamesList = connNames.split(',');
        var databaseName = $.trim($("#resource_linkname").val());
        if (databaseName) {
            if (connNamesList && Array.isArray(connNamesList)) {
                var flag = false;
                connNamesList.forEach(function (item, index, array) {
                    if (databaseName == item) {
                        flag = true;
                    }
                });
                if (flag) {
                    $(".linkNameError").remove();
                    $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;连接名重复了！</p>')
                    return false;
                } else {
                    $(".linkNameError").remove();
                    return true;
                }
            }
        } else {
            $(".linkNameError").remove();
            $("#resource_linkname").after('<p class="linkNameError font-orange linkError">·&nbsp;不能为空哦！</p>');
            return false;
        }
    }





}
//------------------------------------------------------------

// ----------------------检验数据库名称-----------------------
function checkDataBaseName() {
    var resource_databaseName = $.trim($("#resource_databaseName").val());
    if (!resource_databaseName) {
        $('.linkDataBaseError').remove();
        $("#resource_databaseName").after('<p class="linkDataBaseError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false
    } else if (resource_databaseName.length > 50) {
        $(".linkDataBaseError").remove();
        $("#resource_databaseName").after('<p class="linkDataBaseError font-orange linkError">·&nbsp;数据库名不能超过20个字！</p>')
        return false
    } else {
        $('.linkDataBaseError').remove();
        return true
    }
}
//------------------------------------------------------------

// ----------------------检验主机-----------------------------
function checkHostIp() {
    var resource_hostIp = $.trim($("#resource_hostIp").val());
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    if (resource_hostIp) {
        if (reg.test(resource_hostIp)) {
            $(".hostIpError").remove();
            return true
        } else {
            $(".hostIpError").remove();
            $("#resource_hostIp").after('<p class="hostIpError font-orange linkError">·&nbsp;IP书写格式错误！</p>')
            return false;
        }
    } else {
        $(".hostIpError").remove();
        $("#resource_hostIp").after('<p class="hostIpError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false;
    }
}
//------------------------------------------------------------

// ----------------------检验端口号---------------------------
function checkPort() {
    var resource_port = $.trim($("#resource_port").val());
    if (resource_port) {
        if (/^[0-9]{1,5}$/g.test(resource_port) && parseInt(resource_port) < 65535 && parseInt(resource_port) > 0) {
            $(".portError").remove();
            return true
        } else {
            $(".portError").remove();
            $("#resource_port").after('<p class="portError font-orange linkError">·&nbsp;端口号格式错误！</p>');
            return false;
        }
    } else {
        $(".portError").remove();
        $("#resource_port").after('<p class="portError font-orange linkError">·&nbsp;不能为空哦！</p>');
        return false;
    }
}
//------------------------------------------------------------

// ----------------------检验用户名---------------------------
function checkUserName() {
    var resource_username = $.trim($("#resource_username").val());
    if (!resource_username) {
        $('.userNameError').remove();
        $("#resource_username").after('<p class="userNameError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false
    } else if (resource_username.length > 50) {
        $(".userNameError").remove();
        $("#resource_username").after('<p class="userNameError font-orange linkError">·&nbsp;用户名不能超过50个字！</p>')
        return false
    } else {
        $('.userNameError').remove();
        return true
    }
}
//------------------------------------------------------------

// ----------------------校验密码-----------------------------
function checkPassword() {
    var resource_password = $.trim($("#resource_password").val());
    if (!resource_password) {
        $('.passwordError').remove();
        $("#resource_password").after('<p class="passwordError font-orange linkError">·&nbsp;不能为空哦！</p>')
        return false
    } else if (resource_password.length > 20) {
        $(".passwordError").remove();
        $("#resource_password").after('<p class="passwordError font-orange linkError">·&nbsp;密码不能超过20个字！</p>')
        return false
    } else {
        $('.passwordError').remove();
        return true
    }
}
//------------------------------------------------------------


// var configForm;
// var dateFrom=["config118","config119","config20"];
// var str;
// var str1="";
// var str2="";
// function creatCode(dateFrom) {
//     for (var i=0;i<dateFrom.length;i++){
//         if(dateFrom[i]=="config118"){
//             str1+=  dateFrom[i]+': {'+
//                 'required: true,'+
//                 'minlength: 2'+
//                 '},';
//             str2+= dateFrom[i]+': {'+
//                 'required: "请输入用户名0000",'+
//                 'minlength: "用户名必需由两个字母组成000"'+
//                 '},';
//         }else if(dateFrom[i]=="config20"){
//             str1+=  dateFrom[i]+': {'+
//                 'required: true,'+
//                 'minlength: 2'+
//                 '},';
//             str2+= dateFrom[i]+': {'+
//                 'required: "请输入用户名111",'+
//                 'minlength: "用户名必需由两个字母组成11"'+
//                 '},';
//         }else if(dateFrom[i]=="config119"){
//             str1+=  dateFrom[i]+': {'+
//                 'required: true,'+
//                 'minlength: 2'+
//                 '},';
//             str2+= dateFrom[i]+': {'+
//                 'required: "请输入用户名22222",'+
//                 'minlength: "用户名必需由两个字母组成22222"'+
//                 '},';
//         }
//     }
//
//     str='configForm = $("#configForm").validate({' +
//         'rules:{'+str1+
//
//         '},'+
//         'messages:{'+str2+
//
//         '}'+
//         '});'+'configForm.form();';
//     eval(str);
// }
//  creatCode(dateFrom) ;

var detail=function () {
    var specialString=function () {
        // /^["'<>%;)(&+]/
        jQuery.validator.addMethod("specialLimit",function (value, element) {
            return this.optional(element) || !(/[<>]/.test(value));
        },"非法字符，请重新输入")
    };
    return {
        init:function () {
            specialString();
        }
    }
}();
var specialtest=$("#test").validate({});  //对保存模型表单初始化验证


jQuery(document).ready(function() {
    detail.init();
});




