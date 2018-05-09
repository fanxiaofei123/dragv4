$(function () {
    editConfigModel();
    reviseColumn();
    eNetPaDefine();
    typeTransform();
    filterFun();
    addNewFilter();
    choiceColumnMethod();
    addThresFun()
});
// ----------------------算子初始化状态渲染-------------------
//    成功、失败状态数组
var allType = [];
var success = [];
var fail = [];

function onloadType() {
    var actionInfo = actionsType;
    var actiondatas = actionInfo.split("/");
    var workFlow = JSON.parse($("#returnData").text());
    var obj = JSON.parse(workFlow.models);
    for (var i = 0; i < actiondatas.length; i++) {
        var datas = actiondatas[i].split(":");
        for (var j = 0; j < obj.length; j++) {
            if (datas[0] == obj[j].BlockId) {
//                ERROR  OK
                if (datas[1] == "ERROR") {
                    fail.push(datas[0]);
                    allType.push(datas[0]);
                } else if (datas[1] == "OK") {
                    success.push(datas[0]);
                    allType.push(datas[0]);
                }
            }
        }
    }
    console.log("success+" + success);
    console.log("fail+" + fail);
    $.each(success, function (i, value) {
        $("#" + value + " .boxBody").addClass("success-type");
    });
    $.each(fail, function (i, value) {
        $("#" + value + " .boxBody").addClass("fail-type");
    })
}

//------------------------------------------------------------

// ----------------------单列变换-----------------------------
function singleConversionMethod() {
    if (singleConversion == true) {
        switchToHideChange("block", "none");
        if (selectValueType == "pow" || selectValueType == "nthroot") {
            $("#formBody #config_resultColumn").css("display", "block");
        } else {
            $("#formBody #config_resultColumn").css("display", "none");
        }
    } else {
        switchToHideChange("none", "block");
        $("#formBody #config_resultColumn").css("display", "block");
    }
    $("#formBody .config_select_radio").on("click", "input[name='singleConversion']", function (e) {
        e.stopPropagation();
        var value = $(this).val();
        if (value == "true") {
            switchToHideChange("block", "none");
            singleConversion = true;
        } else {
            switchToHideChange("none", "block");
            singleConversion = false;
        }

    })
    $("#formBody .config_select_radio").on("click", "input.membership", function (e) {
        e.stopPropagation();
        var value = $(this).val();
        if (singleConversion == true) {
            if (selectValueType == "pow" || selectValueType == "nthroot") {
                if (value == "false") {
                    $("#formBody #config_resultColumn").css("display", "block");
                } else {
                    $("#formBody #config_resultColumn").css("display", "none");
                }
            } else {
                if (value == "false") {
                    $("#formBody #config_resultColumn").css("display", "none");
                } else {
                    $("#formBody #config_resultColumn").css("display", "none");
                }
            }
        } else {
            if (value == "false") {
                $("#formBody #config_resultColumn").css("display", "block");
            } else {
                $("#formBody #config_resultColumn").css("display", "none");
            }

        }


    })
}

function switchToHideChange(display0, display1) {
    $("#formBody #config_changeA").css("display", display0);
    $("#formBody #config_changeB").css("display", display1);
}

//单列变换的算子
function showSingleChangeInput(blockId) {
    var selectVal
    for (var i = 0; i < configObj.length; i++) {
        if (configObj[i].BlockId == blockId) {
            for (var k = 0; k < configObj[i].data.length; k++) {
                if (configObj[i].data[k].configName == "select") {
                    selectVal = configObj[i].data[k].configVal;
                    break;
                }
            }
        }
    }
    var changeAVal;
    for (var j = 0; j < configObj.length; j++) {
        if (configObj[j].BlockId == blockId) {
            for (var m = 0; m < configObj[j].data.length; m++) {
                if (configObj[j].data[m].configName == "changeA") {
                    changeAVal = configObj[j].data[m].configSelectVal;
                    break;
                }
            }
        }
    }
    if (selectVal == "true") {
        if (selectValueType == "pow" || selectValueType == "nthroot") {
            $("#formBody #config_dataItem").css("display", "block");
        } else {
            $("#formBody #config_dataItem").css("display", "none");
        }
    } else {
        $("#formBody #config_dataItem").css("display", "block");
    }
}

//------------------------------------------------------------

// ----------------------下拉选择项后的其他输入框或弹框的切换隐藏
function switchToHide() {
    //无监督离散算子
    if (selectValueType == "equalWidth") {
        showHideInputMethod('block', 'none', 'none')
    } else if (selectValueType == "equalFrequency") {
        showHideInputMethod('none', 'block', 'none')
    } else if (selectValueType == "userDefined") {
        showHideInputMethod('none', 'none', 'block')
    } else {
        showHideInputMethod('none', 'none', 'none')
    }
    //有监督离散
    if (selectValByUnDispersion == "entropy") {
        $("#formBody #config_significanceLevel").css("display", "none");
        $("#formBody #config_purity").css("display", "none");
    } else {
        $("#formBody #config_significanceLevel").css("display", "block");
        $("#formBody #config_purity").css("display", "block");
    }
    //选择单选框后 下拉框再选择，出现输入框
    if (singleConversion == true) {
        if (selectValueType == "pow" || selectValueType == "nthroot") {
            $("#formBody #config_dataItem").css("display", "block");
        } else {
            $("#formBody #config_dataItem").css("display", "none");
        }
    } else {
        $("#formBody #config_dataItem").css("display", "block");

    }
    if (singleConversion == true) {
        if (selectValueType == "pow" || selectValueType == "nthroot") {
            if (radioStateVal == false) {
                $("#formBody #config_resultColumn").css("display", "block");
            } else {
                $("#formBody #config_resultColumn").css("display", "none");
            }
        } else {
            if (radioStateVal == false) {
                $("#formBody #config_resultColumn").css("display", "none");
            } else {
                $("#formBody #config_resultColumn").css("display", "none");
            }
        }
    } else {
        if (radioStateVal == false) {
            $("#formBody #config_resultColumn").css("display", "block");
        } else {
            $("#formBody #config_resultColumn").css("display", "none");
        }

    }
    //广义线性回归的连接函数分布族下拉选择显示和隐藏
    if (selectDisFunctionFamilyVal == "gaussian") {
        showHideSelectOptionMethod('block', 'none', 'none', 'none');
    } else if (selectDisFunctionFamilyVal == "binomial") {
        showHideSelectOptionMethod('none', 'block', 'none', 'none');
    } else if (selectDisFunctionFamilyVal == "poisson") {
        showHideSelectOptionMethod('none', 'none', 'block', 'none');
    } else if (selectDisFunctionFamilyVal == "gamma") {
        showHideSelectOptionMethod('none', 'none', 'none', 'block');
    }
}

//------------------------------------------------------------

// ----------------------显示和隐藏不同的框-------------------
function showHideInputMethod(display0, display1, display2) {
    $("#formBody #config_equalWidth").css("display", display0);
    $("#formBody #config_equalFrequency").css("display", display1);
    $("#formBody #config_userDefined").css("display", display2);
}

function showHideSelectOptionMethod(display0, display1, display2, display3) {
    $("#formBody #config_gaussian").css("display", display0);
    $("#formBody #config_binomial").css("display", display1);
    $("#formBody #config_poisson").css("display", display2);
    $("#formBody #config_gamma").css("display", display3);
}

//------------------------------------------------------------

// ----------------------添加分组start------------------------
//configId1 blockId1是从添加分组获取的值 设全局变量；
var configId1;
var blockId1;

function addGroupMethod() {
    //添加分组打开弹框

    $("#formBody #addGroupBtn").on('click', function (e) {
        e.stopPropagation();
        $("#groupDiscreteInterval").val("");
        configId1 = $(this).attr("dataVal");
        blockId1 = $(this).parents("#formBody").attr("name");
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId1) {
                for (var k = 0; k < configObj[i].data.length; k++) {
                    if (configObj[i].data[k].configId == configId1) {
                        $("#groupDiscreteInterval").val(configObj[i].data[k].configVal);
                    }
                }
            }
        }
        $("#addGroupModel").modal('show');
    });
    $("#saveAddGroup").on("click", function (e) {
        e.stopPropagation();
        var textVal = $("#groupDiscreteInterval").val();
        var ret = /^(\d+,)*\d+$/;
        //判断输入的是否是数字和逗号
        if (ret.test(textVal)) {

            var textArray = textVal.split(',');
            var str = "";
            for (var n = 0; n < textArray.length - 1; n++) {
                var sort = n + 1
                str += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n] + '-' + textArray[n + 1] + '</p>'
            }
            $('#formBody #group_' + configId1 + ' .mCSB_container').html(str);
            console.log(configId1);
            console.log("000000000000000000000");
            console.log(configObj);
            //把数据保存到configObj
            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == blockId1) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configId == configId1) {
                            configObj[i].data[k].configVal = textVal;
                        }
                    }
                }
            }
            console.log(configObj);

            $(".errorTitle").text("");
            $("#addGroupModel").modal('hide');
        } else {
            $(".errorTitle").text("请合法输入逗号隔开的数字");
        }

    });
}

//------------------------------------------------------------

// ----------------------加滚动条-----------------------------
function addScrollBar() {
    $("body .choice-column").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "inside"
    });
    $("body .choice-column-rule").mCustomScrollbar({
        theme: "dark-thin",
        axis: "y",
        setLeft: 0,
        scrollbarPosition: "outside"
    });
}

function eNetPaDefine() {

    $("#formBody").on("change", ".selectOptionVal", function () {
        var selectedVal = $(this).val();
        //如果是自定义算子就显示输入框
        if (selectedVal == "elasticNet") {
            $(this).parent().parent().next().css("display", "block");

        } else {
            $(this).parent().parent().next().css("display", "none");
        }

    });


}

// ----------------------编辑自定义算子python-----------------
function editPyNameModel() {
    //代码编辑器editor1表 python editor2表 R
    ace.require("ace/ext/language_tools");
    var pythonLanguage = "ace/mode/python";
    var editor1 = ace.edit("editor1");
    editor1.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true
    });
    editor1.setTheme("ace/theme/eclipse");
    editor1.getSession().setMode(pythonLanguage);

    // enable autocomplete anytime
    editor1.commands.on("afterExec", function (e) {
        if (e.command.name == "insertstring" && /^[\w.]$/.test(e.args)) {
            editor1.execCommand("startAutocomplete")
        }
    });

    var blockId;
    $("#formBody").on("click", "a.editPyStyle", function () {
        blockId = $(this).attr("blockId");
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {

                editor1.setValue(configObj[i].data[0].configVal);
            }
        }

        $("#pyRModal").modal("show");
    });
    $("#surePyR").on('click', function () {
        //$(".pythonInput").attr("value",editor1.getValue());
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {
                configObj[i].data[0].configVal = editor1.getValue();
                $("#formBody").find('.pythonInput').text(editor1.getValue());
            }
        }

        $("#pyRModal").modal("hide");
    })
    $("#closePyR").on('click', function () {
        $("#pyRModal").modal("hide");
    })
}

//------------------------------------------------------------

// ----------------------编辑自定义算子r----------------------
function editRNameModel() {
    //代码编辑器editor1表 python editor2表 R
    ace.require("ace/ext/language_tools");
    var rLanguage = "ace/mode/r";
    var editor2 = ace.edit("editor2");
    editor2.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true
    });

    editor2.setTheme("ace/theme/eclipse");
    editor2.getSession().setMode(rLanguage);

    // enable autocomplete anytime
    editor2.commands.on("afterExec", function (e) {
        if (e.command.name == "insertstring" && /^[\w.]$/.test(e.args)) {
            editor2.execCommand("startAutocomplete")
        }
    });

    var blockId;
    $("#formBody").on("click", "a.editRStyle", function () {
        blockId = $(this).attr("blockId");
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {

                editor2.setValue(configObj[i].data[0].configVal);
            }
        }
        $("#rModal").modal("show");
    });
    $("#sureR").on('click', function () {
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {
                configObj[i].data[0].configVal = editor2.getValue();
                $("#formBody").find('.rInput').text(editor2.getValue());
            }
        }
        $("#rModal").modal("hide");
    })
    $("#closeR").on('click', function () {
        $("#rModal").modal("hide");
    })
}

//------------------------------------------------------------

// ----------------------修改hdfs地址配置参数-----------------
function editUrlConfigModel() {
    var urlInput;
    var urlInputId;
    var urlInputVal;
    var blockId;
    $("#formBody").on("click", "a.btnUrl", function () {
        urlInput = $(this).parent().siblings("input");
        urlInputId = urlInput.attr("id");
        blockId = $(this).parents("#formBody").attr("name");
        //
        $("#treeBtn").click(function () {
            blockId = $("#formBody").attr("name");
            var url = $('.jstree-clicked').closest('li').attr('file-url');
            // var url1 = url.split("/")[url.split("/").length-2,url.split("/").length-1];
            var url1 = "/" + url.split("/")[url.split("/").length - 2] + "/" + url.split("/")[url.split("/").length - 1];
            // alert("qqqqqqqqqqqq" + url)
            var urlInputUser = "/" + url.substring(url.indexOf("user"), url.length);

            // alert("sdjsjjda"+urlInputUser)
            console.log(urlInputUser + "----------------------------88888888")
            console.log(url1 + "----------------------------9999999999999")
            urlInput.val(url1);
            urlInputVal = urlInput.val();

            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == blockId) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configId == urlInputId) {
                            configObj[i].data[k].configVal = urlInputUser;
                        }
                    }
                }
            }
            $("#basic").modal("hide");
        });
    });
}

//------------------------------------------------------------

// ----------------------修改其他配置信息
function editConfigModel() {
    $("#formBody").find("input, select, textarea").on("change keyup", function () {
        var changedId;
        var changedVal;
        var changedType = $(this).attr("type");
        var changedName = $(this).attr("name");
        var blockId = $(this).parents("#formBody").attr("name");
        changedVal = $(this).val();
        var elasticNetParamId = $(this).attr("dataValue");
        if (changedName == "select") {
            //给数据离散的算子参数下拉框选择赋值来显示或隐藏对应的框
            selectValueType = changedVal;
            selectValByUnDispersion = changedVal;
            selectDisFunctionFamilyVal = changedVal;
            switchToHide();
        }
        if (changedName == "singleConversion") {
            switchToHide();
            if (changedVal == "true") {
                switchToHideChange("block", "none");
                singleConversion = true;
            } else {
                switchToHideChange("none", "block");
                singleConversion = false;
            }
        }
        if (changedName == "elasticNet") {
            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == blockId) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configId == elasticNetParamId) {
                            configObj[i].data[k].configSelectVal = changedVal;
                            break;
                        }
                    }
                }
            }
            //$(this).parent().parent().prev().children().eq(1).eq(0).val(changedName);
        }

        var optionValues = $(this).children("option").map(function () {
            return $(this).val();
        }).get().join(",");
        // var selectChangedVal = $(this).find("option:selected").text();;
        // alert("sssssss" + selectChangedVal)
        if (changedType == "radio") {
            changedId = $(this).parents("div.mt-radio-list").attr("id");
        } else if (changedType == "checkbox") {
            changedId = $(this).parents("div.mt-checkbox-list").attr("id");
        }
        else {
            changedId = $(this).attr("id");
        }


        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {
                // FOR循环之前整合条件过滤json
                if (configObj[i].id == "125") {
                    configObj[i].data = conformity_arr(configObj[i].data);
                }
                // end
                for (var k = 0; k < configObj[i].data.length; k++) {
                    if (configObj[i].data[k].configId == changedId) {
                        if (changedName == "select") {
                            if (configObj[i].data[k].configType == 2) {
                                configObj[i].data[k].configSelectVal = changedVal;
                                var sampleTypeForChin;
                                var splitTypeForChin;
                                if (changedVal == "0" && configObj[i].data[k].configName == "sampleType") {
                                    sampleTypeForChin = getChineseConfigName("sampleSize_Percent")
                                    $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
                                } else if (changedVal == "1" && configObj[i].data[k].configName == "sampleType") {
                                    sampleTypeForChin = getChineseConfigName("sampleSize_Num")
                                    $(this).parents("#formBody").children().eq(1).find("label").html(sampleTypeForChin);
                                }
                                // var configNameForChin = getChineseConfigName(changedVal)

                                if (changedVal == "0" && configObj[i].data[k].configName == "splitType") {
                                    splitTypeForChin = getChineseConfigName("splitValue_Percent")
                                    $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
                                } else if (changedVal == "1" && configObj[i].data[k].configName == "splitType") {
                                    splitTypeForChin = getChineseConfigName("splitValue_Num")
                                    $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
                                } else if (changedVal == "1" && configObj[i].data[k].configName == "splitType") {
                                    splitTypeForChin = getChineseConfigName("splitValue_Num")
                                    $(this).parents("#formBody").children().eq(1).find("label").html(splitTypeForChin);
                                    var thisCount;
                                    for (var p = 0; p < configObj.length; p++) {
                                        for (var q = 0; q < configObj[p].data.length; q++) {
                                            if (configObj[p].data[q].configId == $(this).attr("id")) {
                                                thisCount = p;
                                                break;
                                            }
                                        }
                                    }
                                    for (var q = 0; q < configObj[thisCount].data.length; q++) {
                                        if (configObj[thisCount].data[q].configName == "splitValue") {
                                            if (changedVal == "0") {
                                                $(this).parents("#formBody").children().find("input").val(configObj[thisCount].data[q].configVal.split(",")[0]);
                                            } else {
                                                $(this).parents("#formBody").children().find("input").val(configObj[thisCount].data[q].configVal.split(",")[1]);
                                            }
                                        }
                                    }
                                }

                                if (changedVal == "n" && configObj[i].data[k].configName == "featureSubsetStrategy") {
                                    $(this).parents("#formBody").find('div[id^="config_featureSubsetStrategyN"]').css('display', 'block');
                                } else if (changedVal != "n" && configObj[i].data[k].configName == "featureSubsetStrategy") {
                                    $(this).parents("#formBody").find('div[id^="config_featureSubsetStrategyN"]').css('display', 'none');
                                }
                            } else {
                                configObj[i].data[k].configVal = optionValues + "|" + changedVal;
                                //当切换表名时，更新sqlStatment表名验证。
                                if (configObj[i].data[k].configName == "tableName") {
                                    var sqlStatment = getSqlStatment(blockId);
                                    if (sqlStatment != null && sqlStatment != "") {
                                        var formatSql = sqlStatment.replace(/\s+/g, ",");
                                        var words = formatSql.split(",");
                                        for (var a = 0; a < words.length; a++) {
                                            if (words[a] == "from") {
                                                if (words[a + 1] != changedVal) {
                                                    $("#sql-Check").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>sql语句查询的表名必须与所选表名一致!");
                                                } else {
                                                    $("#sql-Check").html("");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //写入数据库算子界面，选择writeMode时，给出提醒消息。
                            if (changedVal == "overwrite") {
                                $("#writeMode-instruction").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>警告，覆盖模式将清空写入表格所有数据！");
                                $("#writeMode-notice").html(" * ");
                            } else if (changedVal == "append") {
                                $("#writeMode-instruction").html("");
                            }
                        } else if (changedName == "textarea") {
                            if (changedVal == "") {
                                changedVal = "null";
                            }
                            configObj[i].data[k].configVal = changedVal + "|" + showQueryFlag;
                            if (changedVal != null && changedVal != "null" && changedVal != "" && configObj[i].data[k].configName == "sqlStatment") {
                                var formatSql = changedVal.replace(/\s+/g, ",");
                                var words = formatSql.split(",");
                                for (var a = 0; a < words.length; a++) {
                                    if (words[a] == "from") {
                                        var selectedTable = getSelectedTableName(blockId);
                                        if (words[a + 1] != selectedTable) {
                                            $("#sql-Check").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>sql语句查询的表名必须与所选表名一致!");
                                        } else {
                                            $("#sql-Check").html("");
                                        }
                                    }
                                }
                            } else {
                                $("#sql-Check").html("");
                            }
                        } else if (changedName == "select1") {
                            if (changedVal == "elasticNet") {
                                configObj[i].data[k].configSelectLabelVal = "defineVal";
                                changedVal = "";
                            }
                            configObj[i].data[k].configSelectVal = changedVal;


                        } else if (changedName == "checkbox") {
                            // 复选框change
                            var checkList = $(this).parents("div.mt-checkbox-list").attr("configVal").split('|')[0];//获取复选框的总的分类默认值
                            // var check=$(this).parents("div.mt-checkbox-list").find("input[name='checkbox']").attr("data-val");
                            var checkTrueList = [];
                            $(this).parents("div.mt-checkbox-list").find("input[name='checkbox']").each(function () {
                                if ($(this).prop("checked") == true) {
                                    if ($(this).attr("data-val") == "空字符") {
                                        checkTrueList.push("\\s");
                                    } else {
                                        checkTrueList.push($(this).attr("data-val"));
                                    }

                                }

                            });
                            configObj[i].data[k].configVal = checkList + "|" + checkTrueList.toString();
                        }
                        else {
                            if (changedVal == "") {
                                changedVal = "null";
                            }
                            configObj[i].data[k].configVal = changedVal;
                        }
                    }
                }
                // for循环结束之后将json重新拆分
                if (configObj[i].id == "125") {
                    configObj[i].data = split_array(configObj[i].data, 9)
                }
            }
        }
    });
}

//------------------------------------------------------------

// ----------------------显示和关闭sql查询box-----------------
function showQueryBox() {
    if ($('.queryBox').css("display") == 'none') {
        $('.queryBox').show();
        // alert($('.queryBox').attr("id"));
        showQueryFlag = "show"
    }
    // }else {
    //     $('.queryBox').css("display","none")
    //     showQueryFlag = "hide";
    // }

    var blockId = $('.queryBox').parents("#formBody").attr("name");
    var configId = $('.queryBox').find("textarea").attr("id");
    for (var i = 0; i < configObj.length; i++) {
        if (configObj[i].BlockId == blockId) {
            for (var k = 0; k < configObj[i].data.length; k++) {
                if (configObj[i].data[k].configId == configId) {
                    var configVal = configObj[i].data[k].configVal;
                    configObj[i].data[k].configVal = configVal.split("|")[0] + "|" + showQueryFlag;
                    // alert(configObj[i].data[k].configVal);
                }
            }
        }
    }
}

//------------------------------------------------------------

// ----------------------提取列(选择标签列)方法---------------------------
//获取inputName文件名数组
function getInputNameArray() {
    var currentDataArr = [];
    for (var i = 0; i < configObj.length; i++) {
        for (var j = 0; j < configObj[i].data.length; j++) {
            if (configObj[i].id == 1 || configObj[i].id == 56) {
                if (configObj[i].data[j].configName == "inputPath") {
                    var configVal = configObj[i].data[j].configVal;
                    var csvName = configVal.split("/")[configVal.split("/").length - 1];
                    currentDataArr.push(configObj[i].BlockId + "_" + csvName)
                }
            }

            if (configObj[i].id == 53) {
                if (configObj[i].data[j].configName == "tableName") {
                    var tableName = configObj[i].data[j].configVal;
                    currentDataArr.push(configObj[i].BlockId + "_" + tableName.split("|")[1])
                }
            }
        }
    }
    return currentDataArr;
}

//获取inputname文件名拼接好的字符串
function getInputNameStr() {
    var savedDataSource = ""
    for (var i = 0; i < configObj.length; i++) {
        for (var j = 0; j < configObj[i].data.length; j++) {
            if (configObj[i].id == 1 || configObj[i].id == 56) {
                if (configObj[i].data[j].configName == "inputPath") {
                    var configVal = configObj[i].data[j].configVal
                    var csvName = configVal.split("/")[configVal.split("/").length - 1]
                    savedDataSource += configObj[i].BlockId + "_" + csvName + ","
                }
            }

            if (configObj[i].id == 53) {
                if (configObj[i].data[j].configName == "tableName") {
                    var tableName = configObj[i].data[j].configVal
                    savedDataSource += configObj[i].BlockId + "_" + tableName.split("|")[1] + ","
                }
            }
        }
    }
    return savedDataSource;
}

//选择列
var currentBlockId;
var choiceColumnConfigId = "";
var modalCount = 0;
var selectListNameOrRuleVal = "true";
var saveType = true;

function choiceColumnMethod() {
    var currentDataArr;
    var blockId;
    var isSameWorkStream1 = true;
    var extarColumnObjOption = "";
    var isSameWorkStream = true;
    var extarColumnObj;
    var arr = [];

    //获取默认的通过规则来选择
    function selectLableByRule() {
        for (var i = 0; i < configObj.length; i++) {
            if (blockId == configObj[i].BlockId) {
                for (var j = 0; j < configObj[i].data.length; j++) {
                    // for(var j=0; j<1;j++){
                    if (configObj[i].data[j].configId === choiceColumnConfigId) {
                        var reloadConfigDataSource = configObj[i].data[j].configDataSource;
                        //判断当前已保存的数据源算子是否与当前工作流里面已存在的算子相同
                        if (reloadConfigDataSource.split(",").length != currentDataArr.length) {
                            isSameWorkStream1 = false
                        } else {
                            $.each(currentDataArr, function (index, value) {
                                if (reloadConfigDataSource.indexOf(value) == -1) {
                                    isSameWorkStream1 = false
                                }
                            })
                        }

                        if (reloadConfigDataSource == undefined || reloadConfigDataSource == null || reloadConfigDataSource == "" || !isSameWorkStream1) {
                            var dataSourceArr = [];
                            for (var k = 0; k < configObj.length; k++) {
                                if (configObj[k].id == 1 || configObj[k].id == 56) {
                                    for (var m = 0; m < configObj[k].data.length; m++) {
                                        if (configObj[k].data[m].configName == "inputPath") {
                                            var configVal = configObj[k].data[m].configVal;
                                            if (configVal != "") {
                                                var csvName = configVal.split("/")[configVal.split("/").length - 1];
                                                dataSourceArr.push(configObj[k].BlockId + "_" + csvName);
                                            }
                                        }
                                    }
                                }
                                if (configObj[k].id == 53) {
                                    for (var m = 0; m < configObj[k].data.length; m++) {
                                        if (configObj[k].data[m].configName == "tableName") {
                                            var tableName = configObj[k].data[m].configVal.split("|")[1];
                                            if (tableName != "null") {
                                                dataSourceArr.push(configObj[k].BlockId + "_" + tableName);
                                            }
                                        }
                                    }
                                }
                            }
                            $("#data-source-rule").empty();
                            $(".choice-column-rule .mCSB_container").empty();
                            var datasourceStr = '<option value="selectOption">' + "请选择" + '</option>';
                            $.each(dataSourceArr, function (index, value) {
                                var showValue = value.substring(value.split("_")[0].length + 1, value.length);
                                datasourceStr = datasourceStr + '<option value="' + value + '">' + showValue + '</option>>'
                            });
                            var addStrOne = '<div class="form-group">' +
                                '<div class="col-sm-2">' +
                                '<select class="data-section-rule form-control">' +
                                '<option>正选</option>' +
                                '<option>反选</option>' +
                                '</select>' +
                                '</div>' +
                                '<div class="col-sm-3">' +
                                '<select class="data-type-rule form-control">' +
                                '<option>按索引</option>' +
                                '<option>按类型</option>' +
                                '<option>按列名</option>' +
                                '</select>' +
                                '</div>' +
                                '<div class="col-sm-5 ui-widget">' +
                                '<input type="text" class="defaultIndexValue inputTypeContent form-control boxshadow fontDisColor"  >' +
                                '</div>' +
                                '<div class="col-sm-1">' +
                                '<i class=" icon iconfont icon-jia"></i>' +
                                '</div>' +
                                '<div class="col-sm-1">' +
                                '</div>' +
                                '</div>';
                            $(".choice-column-rule .mCSB_container").append(addStrOne);
                            $("#data-source-rule").append(datasourceStr);
                            isSameWorkStream1 = true;

                        } else {
                            $("#data-source-rule").empty();
                            var configDataSource = configObj[i].data[j].configDataSource;
                            var selectedDataSource = configObj[i].data[j].configSelectedDataSource;
                            var configStatModelToTypeOption = configObj[i].data[j].configStatModelToTypeOption;
                            var configSelectRuleJsonStr = configObj[i].data[j].configSelectRuleJson;
                            var dataSourceOptionStr = '<option value="selectOption">' + "请选择" + '</option>';
                            $.each(configDataSource.split(","), function (index, value) {
                                if (value.trim() != "selectOption") {
                                    dataSourceOptionStr += '<option value="' + value.trim() + '">' + value.substring(value.split("_")[0].length + 1, value.length) + '</option>'
                                }
                            })
                            $("#data-source-rule").append(dataSourceOptionStr);
                            $("#data-source-rule").val(selectedDataSource);
                            extarColumnObjOption = $.parseJSON(configStatModelToTypeOption);

                            var optionStr = "";
                            $(".choice-column-rule .mCSB_container").empty();
                            var configSelectRuleJson = $.parseJSON(configSelectRuleJsonStr)
                            $.each(configSelectRuleJson, function (index, value) {
                                var schemesValArr = [];
                                var typesArr = extarColumnObjOption.types.split(",");
                                for (var ke = 0; ke < typesArr.length; ke++) {
                                    schemesValArr = extarColumnObjOption.statModelTypeToScheme[value.dataSourceName + "_" + typesArr[ke]].split(",")
                                    schemesValArr.concat(schemesValArr);
                                }

                                var schValArrList = []
                                for (var p = 0; p < schemesValArr.length; p++) {
                                    schValArrList.push({
                                        id: p,
                                        text: schemesValArr[p]
                                    })
                                }
                                var sectionStr = "";
                                if (value.section == "正选") {
                                    sectionStr = '<option>正选</option>' +
                                        '<option>反选</option>';
                                } else {
                                    sectionStr = '<option>反选</option>' +
                                        '<option>正选</option>';

                                }
                                var typeString = "";
                                var contentString = "";
                                if (value.type == "按索引") {
                                    typeString = '<option>按索引</option><option>按类型</option><option>按列名</option>';
                                    contentString = '<input type="text" class="inputTypeContent form-control boxshadow fontDisColor" dataSourceVal="' + value.dataSourceName + '" value="' + value.content + '">';
                                } else if (value.type == "按类型") {
                                    typeString = '<option>按类型</option><option>按索引</option><option>按列名</option>';
                                    var typeArrStr = "";
                                    for (var t = 0; t < typesArr.length; t++) {
                                        if (value.content != typesArr[t]) {
                                            typeArrStr += '<option>' + typesArr[t] + '</option>'
                                        }

                                    }
                                    typeArrStr = '<option>' + value.content + '</option>' + typeArrStr;
                                    contentString = '<select class="inputTypeContent form-control" dataSourceVal="' + value.dataSourceName + '">' + typeArrStr + '</select>';
                                } else {
                                    typeString = '<option>按列名</option><option>按索引</option><option>按类型</option>';
                                    contentString = '<select class="multipleChoice inputTypeContent form-control" multiple="multiple" dataSourceVal="' + value.dataSourceName + '"> </select>'
                                }
                                var jian = "";
                                if (index != 0) {
                                    jian = '<i class=" icon iconfont icon-jian2"></i>'
                                }
                                optionStr = '<div class="form-group">' +
                                    '<div class="col-sm-2">' + '<select class="data-section-rule form-control">' + sectionStr + '</select>' +
                                    '</div>' +
                                    '<div class="col-sm-3">' +
                                    '<select class="data-type-rule form-control">' + typeString +
                                    '</select>' +
                                    '</div>' +
                                    '<div class="col-sm-5 ui-widget">' + contentString +
                                    '</div>' +
                                    '<div class="col-sm-1">' +
                                    '<i class=" icon iconfont icon-jia"></i>' +
                                    '</div>' +
                                    '<div class="col-sm-1">' + jian +
                                    '</div>' +
                                    '</div>';
                                $(".choice-column-rule .mCSB_container").append(optionStr);
                                if (value.type == "按列名") {
                                    var contArray = value.content;
                                    $('.choice-column-rule .multipleChoice').select2({
                                        data: schValArrList
                                    });
                                    $(".choice-column-rule .multipleChoice").val(contArray).trigger('change')
                                }

                            });
                            // $(".choice-column-rule .mCSB_container").append(optionStr);


                        }
                    }
                }
                //
            }
        }
    }

    $("body").on("click", ".choiceColumnBtn", function () {
        //默认是通过列名选择界面
        $("#tag-modal").modal("show");
        $("#data-source").empty();
        $("#data-type").empty();
        $("#preparaed-column .mCSB_container").html("");
        $("#selected-column .mCSB_container").html("");
        $("#serach-scheme").val("");
        var buttonEle = $(this);
        //切换弹框 获取数据源
        currentDataArr = getInputNameArray();
        choiceColumnConfigId = buttonEle.attr("dataVal");
        blockId = buttonEle.parents("#formBody").attr("name");
        currentBlockId = blockId;
        //区分默认
        //得到configSelectNameOrRuleVal判断取默认值来选择显示通过列名选择还是通过规则选择界面
        for (var i = 0; i < configObj.length; i++) {
            if (blockId == configObj[i].BlockId) {
                for (var j = 0; j < configObj[i].data.length; j++) {
                    if (configObj[i].data[j].configId == choiceColumnConfigId) {
                        selectListNameOrRuleVal = configObj[i].data[j].configSelectNameOrRuleVal;
                        if (selectListNameOrRuleVal == "true") {
                            $(".pass_list_select #selectListShow").addClass('pass_list_select_hover');
                            $(".pass_list_select #selectRuleShow").removeClass('pass_list_select_hover');
                            reDn(["#selectListShowDiv"]);
                            addDn(["#selectRuleShowDiv"]);
                        } else {
                            $(".pass_list_select #selectRuleShow").addClass('pass_list_select_hover');
                            $(".pass_list_select #selectListShow").removeClass('pass_list_select_hover');
                            reDn(["#selectRuleShowDiv"]);
                            addDn(["#selectListShowDiv"]);
                        }
                    }
                }
            }
        }

        //通过列名选择
        for (var i = 0; i < configObj.length; i++) {
            if (blockId == configObj[i].BlockId) {
                for (var j = 0; j < configObj[i].data.length; j++) {
                    // for(var j=0; j<1;j++){
                    if (configObj[i].data[j].configId == choiceColumnConfigId) {
                        var reloadConfigDataSource = configObj[i].data[j].configDataSource;
                        var slectedConfigOption = configObj[i].data[j].configVal;
                        //判断当前已保存的数据源算子是否与当前工作流里面已存在的算子相同
                        if (reloadConfigDataSource.split(",").length != currentDataArr.length) {
                            isSameWorkStream = false
                        } else {
                            $.each(currentDataArr, function (index, value) {
                                if (reloadConfigDataSource.indexOf(value) == -1) {
                                    isSameWorkStream = false
                                }
                            })
                        }

                        if (reloadConfigDataSource == undefined || reloadConfigDataSource == null || reloadConfigDataSource == "" || !isSameWorkStream) {
                            var dataSourceArr = [];
                            // $("#tag-modal").modal("show");
                            for (var k = 0; k < configObj.length; k++) {
                                if (configObj[k].id == 1 || configObj[k].id == 56) {
                                    for (var m = 0; m < configObj[k].data.length; m++) {
                                        if (configObj[k].data[m].configName == "inputPath") {
                                            var configVal = configObj[k].data[m].configVal;
                                            if (configVal != "") {
                                                var csvName = configVal.split("/")[configVal.split("/").length - 1];
                                                dataSourceArr.push(configObj[k].BlockId + "_" + csvName);
                                            }
                                        }
                                    }
                                }
                                if (configObj[k].id == 53) {
                                    for (var m = 0; m < configObj[k].data.length; m++) {
                                        if (configObj[k].data[m].configName == "tableName") {
                                            var tableName = configObj[k].data[m].configVal.split("|")[1];
                                            if (tableName != "null") {
                                                dataSourceArr.push(configObj[k].BlockId + "_" + tableName);
                                            }
                                        }
                                    }
                                }
                            }
                            $("#data-source").empty();
                            $("#data-type").empty();
                            var datasourceStr = '<option value="selectOption">' + "请选择" + '</option>';
                            $.each(dataSourceArr, function (index, value) {
                                var showValue = value.substring(value.split("_")[0].length + 1, value.length);
                                datasourceStr = datasourceStr + '<option value="' + value + '">' + showValue + '</option>>'
                            })
                            $("#data-source").append(datasourceStr);
                            if (slectedConfigOption != undefined) {
                                var options = slectedConfigOption.split(";");
                                var selectedConfigOptionStr = "";
                                for (var m = 0; m < options.length; m++) {
                                    selectedConfigOptionStr += '<p>' + options[m] + '<span><i class="icon iconfont icon-jia1"></i></span></p>'
                                }
                                $("#selected-column .mCSB_container").append(selectedConfigOptionStr);
                            }
                        } else {
                            // $("#tag-modal").modal("show");
                            var configDataSource = configObj[i].data[j].configDataSource;
                            var selectedDataSource = configObj[i].data[j].configSelectedDataSource;
                            var configDataType = configObj[i].data[j].configDataType;
                            var configSelectedDataType = configObj[i].data[j].configSelectedDataType;
                            var configPreparaedScheme = configObj[i].data[j].configPreparaedScheme;
                            var configSerachName = configObj[i].data[j].configSerachName;
                            var configSelectedScheme = configObj[i].data[j].configVal;
                            var configStatModelToTypeOption = configObj[i].data[j].configStatModelToTypeOption;
                            var dataSourceOptionStr = '<option value="selectOption">' + "请选择" + '</option>';
                            $.each(configDataSource.split(","), function (index, value) {
                                if (value.trim() != "selectOption") {
                                    dataSourceOptionStr += '<option value="' + value.trim() + '">' + value.substring(value.split("_")[0].length + 1, value.length) + '</option>'
                                }
                            })
                            $("#data-source").append(dataSourceOptionStr);
                            $("#data-source").val(selectedDataSource);

                            var dataTypeOptionStr = '<option value="selectType">' + "请选择" + '</option>';
                            $.each(configDataType.split(","), function (index, value) {
                                if (value.trim() != "selectType") {
                                    dataTypeOptionStr += '<option value="' + value.trim() + '">' + value + '</option>'
                                }
                            })

                            $("#data-type").append(dataTypeOptionStr);
                            $("#data-type").val(configSelectedDataType);

                            if (configPreparaedScheme != "") {
                                var preparaedSchemeStr = '<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>'
                                $.each(configPreparaedScheme.split(","), function (index, value) {
                                    preparaedSchemeStr += '<p>' + value + '<span><i class="icon iconfont icon-jia1"></i></span></p>'
                                });
                                $("#preparaed-column .mCSB_container").append(preparaedSchemeStr);
                            } else {
                                $("#preparaed-column .mCSB_container").append('<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>');
                            }

                            $("#serach-scheme").val(configSerachName);

                            if (configSelectedScheme != "") {
                                var selectedSchemeStr = "";
                                $.each(configSelectedScheme.split(","), function (index, value) {
                                    selectedSchemeStr += '<p>' + value + '<span><i class="icon iconfont icon-jian"></i></span></p>'
                                })
                                $("#selected-column .mCSB_container").append(selectedSchemeStr);
                            }
                            extarColumnObj = $.parseJSON(configStatModelToTypeOption)
                        }
                    }
                }
            }
        }
        //全部右边显示
        if ($(".tag-already-" + choiceColumnConfigId + " p").text() != "") {
            $(".choice-column-right .mCSB_container").html('<p class="all">全部<span><i class="icon iconfont icon-jian"></i></span></p>');
            $(".tag-already-" + choiceColumnConfigId + " p").each(function () {
                $(".choice-column-right .mCSB_container").append('<p>' + $(this).text() + '<span><i class="icon iconfont icon-jian"></i></span></p>');
            })
        } else {
            $(".choice-column-right .mCSB_container").html('<p class="all">全部<span><i class="icon iconfont icon-jian"></i></span></p>');
        }
        //通过规则选择
        selectLableByRule();


    });

    /**
     * 选择列名选择列名start
     */
    $('#selectListShow').on("click", function () {
        $(".pass_list_select #selectListShow").addClass('pass_list_select_hover');
        $(".pass_list_select #selectRuleShow").removeClass('pass_list_select_hover');
        reDn(["#selectListShowDiv"]);
        addDn(["#selectRuleShowDiv"]);
        selectListNameOrRuleVal = "true";
        saveType = true;
    });
    //加载数据源
    $("#data-source").change(function () {
        $("#serach-scheme").val("");
        var statDatasourceName = $(this).val();
        if (statDatasourceName != "selectOption") {
            $("#remindGroup").text("请等待，正在加载...");
            $.ajax({
                data: {'datasourceName': statDatasourceName, 'modelStr': JSON.stringify(configObj)},
                url: basePath + "/drag/hdfs/getColumnScheme.do",
                type: 'POST',
                dataType: "json",
                success: function (data) {
                    switch (data.code) {
                        case 200:
                            $("#remindGroup").empty();
                            extarColumnObj = $.parseJSON(data.msg);
                            $("#data-type").empty();
                            var dataTypeStr = '<option value="selectType">' + "请选择" + '</option>';
                            var schemeTypesArr = extarColumnObj.types.split(",");
                            $.each(schemeTypesArr, function (index, value) {
                                dataTypeStr += '<option value="' + value + '">' + value + '</option>>'
                            })
                            $("#data-type").append(dataTypeStr);

                            $("#preparaed-column .mCSB_container").empty();
                            var schemeStr = '<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>';
                            var schemesArr = extarColumnObj.schemes.split(",");
                            $.each(schemesArr, function (index, value) {
                                schemeStr += '<p>' + value + '<span><i class="icon iconfont icon-jia1"></i></span></p>';
                            });
                            $("#preparaed-column .mCSB_container").append(schemeStr);

                            arr = [];
                            $("#preparaed-column .mCSB_container").children("p").not(".all").each(function () {
                                arr.push($(this).text())
                            });
                            break;
                    }
                },
                error: function () {

                }
            })
        } else {
            $("#data-type").empty();
            $("#preparaed-column .mCSB_container").html('<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>');
        }
    })
    //加载数据类型
    $("#data-type").change(function () {
        var currentCheckCondition = $("#data-source").val() + "_" + $(this).val();
        var checkedSchemeStr = extarColumnObj.statModelTypeToScheme[currentCheckCondition].toString();
        $("#preparaed-column .mCSB_container").empty()
        var schemeStr = '<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>';
        var schemesArr = checkedSchemeStr.split(",");
        $.each(schemesArr, function (index, value) {
            schemeStr += '<p>' + value + '<span><i class="icon iconfont icon-jia1"></i></span></p>'
        });
        $("#preparaed-column .mCSB_container").append(schemeStr);
        arr = [];
        $("#preparaed-column .mCSB_container").children("p").not(".all").each(function () {
            arr.push($(this).text());
        })
    })
    //搜索功能
    $("#serach-scheme").on("input propertychange", function () {
        // $("#preparaed-column").empty()
        var serachWord = $(this).val();
        var schemeStr = '<p class="all">全部<span><i class="icon iconfont icon-jia1"></i></span></p>';
        $("#preparaed-column .mCSB_container").empty();
        $.each(arr, function (index, value) {
            if (value.indexOf(serachWord) != -1) {
                schemeStr += '<p>' + value + '<span><i class="icon iconfont icon-jia1"></i></span></p>';
            }
        });
        $("#preparaed-column .mCSB_container").append(schemeStr);
    })
    // 可选择的方法
    $("#tag-modal").on("click", ".choice-column p span i.icon-jia1", function () {
        if ($(this).parents("p").hasClass("all")) {
            var tagArray = [];
            $(".choice-column-left p").not(".all").each(function () {
                tagArray.push($(this).text());
                // $(this).parents("p").remove();
                // $(".choice-column-right .mCSB_container").append($(this));
                var choiceRightOptions = "";
                $(".choice-column-right p").not(".all").each(function () {
                    choiceRightOptions += $(this).text() + ",";
                })
                if (choiceRightOptions.indexOf($(this).text()) == -1) {
                    $(".choice-column-right .mCSB_container").append('<p>' + $(this).text() + '<span><i class="icon iconfont icon-jian"></i></span></p>');
                }
            });
        } else {
            // $(this).parents("p").remove();
            //$(".choice-column-right .mCSB_container").append('<p>' + $(this).parents("p").text() + '<span><i class="icon iconfont icon-jian"></i></span></p>');
            var choiceRightOptions = "";
            $(".choice-column-right p").not(".all").each(function () {
                choiceRightOptions += $(this).text() + ",";
            })
            if (choiceRightOptions.indexOf($(this).parents("p").text()) == -1) {
                $("#selected-column .mCSB_container").append('<p>' + $(this).parents("p").text() + '<span><i class="icon iconfont icon-jian"></i></span></p>');
            }
        }
    });
    // 已选择删除方法
    $("#tag-modal").on("click", ".choice-column p span i.icon-jian", function () {
        if ($(this).parents("p").hasClass("all")) {
            var tagArray = [];
            $(".choice-column-right p").not(".all").each(function () {
                tagArray.push($(this).text());
                $(this).remove();
                // $(".choice-column-left .mCSB_container").append('<p>' + $(this).text() + '<span><i class="icon iconfont icon-jia1"></i></span></p>');
            });
        } else {
            $(this).parents("p").remove();
            // $(".choice-column-left .mCSB_container").append('<p>' + $(this).parents("p").text() + '<span><i class="icon iconfont icon-jia1"></i></span></p>');
        }
    });
    /**
     * 选择列名end
     */
    /**
     * 通过规则选择列名start
     */
    $('#selectRuleShow').on("click", function (e) {
        e.stopPropagation();
        // if ( e && e.preventDefault ){
        //     e.preventDefault(); //阻止默认浏览器动作(W3C)
        // }else {
        //     window.event.returnValue = false; //IE中阻止函数器默认动作的方式
        // }
        saveType = false;
        $(".pass_list_select #selectRuleShow").addClass('pass_list_select_hover');
        $(".pass_list_select #selectListShow").removeClass('pass_list_select_hover');
        reDn(["#selectRuleShowDiv"]);
        addDn(["#selectListShowDiv"]);
        selectListNameOrRuleVal = "false";
        selectLableByRule();


    });
    //点击规则界面选择添加选项
    var addStr = '';
    //添加规则
    $(".choice-column-rule").on('click', '.icon-jia', function (e) {
        e.stopPropagation();
        var sourceName = $("#data-source-rule").val();
        if (sourceName != "selectOption") {
            addStr = '<div class="form-group">' +
                '<div class="col-sm-2">' +
                '<select class="data-section-rule form-control">' +
                '<option>正选</option>' +
                '<option>反选</option>' +
                '</select>' +
                '</div>' +
                '<div class="col-sm-3">' +
                '<select class="data-type-rule form-control">' +
                '<option>按索引</option>' +
                '<option>按类型</option>' +
                '<option>按列名</option>' +
                '</select>' +
                '</div>' +
                '<div class="col-sm-5 ui-widget">' +
                '<input type="text" class="defaultIndexValue inputTypeContent form-control boxshadow fontDisColor" dataSourceVal="' + sourceName + '"  >' +
                '</div>' +
                '<div class="col-sm-1">' +
                '<i class=" icon iconfont icon-jia"></i>' +
                '</div>' +
                '<div class="col-sm-1">' +
                '<i class=" icon iconfont icon-jian2"></i>' +
                '</div>' +
                '</div>';

            $(this).parent().parent().parents("#mCSB_15_container").append(addStr);
        } else {
            $("#remindGroup2").text("请选择文件");
        }

    });
    //删除规则
    $(".choice-column-rule").on('click', '.icon-jian2', function (e) {
        e.stopPropagation();
        $(this).parent().parent().remove();
    });
    //选择规则类型
    var schemesArray = [];
    $("#tag-modal").on('change', '.choice-column-rule  .data-type-rule', function (e) {
        e.stopPropagation();
        var typeVal = $(this).val();
        var statsDataSourceName = $('#data-source-rule').val();

        if (typeVal == "按索引") {

            $(this).parent().next().children().remove();
            var typeStr1 = '<input type="text" class="inputTypeContent form-control boxshadow fontDisColor" dataSourceVal="' + statsDataSourceName + '">';
            // 填写选择按索引的值

            $(this).parent().next().append(typeStr1);
        } else if (typeVal == "按类型") {
            $(this).parent().next().children().remove();
            //填写选择类型的值
            if (extarColumnObjOption != "") {
                var schemeTypesArr = extarColumnObjOption.types.split(",");
                var dataTypeStr;
                $.each(schemeTypesArr, function (index, value) {
                    dataTypeStr += '<option value="' + value + '">' + value + '</option>>'
                })
                var typeStr2 = '<select class="inputTypeContent form-control" dataSourceVal="' + statsDataSourceName + '">' + dataTypeStr + '</select>';
                $(this).parent().next().append(typeStr2);
            } else {
                $(this).parent().next().append('<select class="inputTypeContent form-control" dataSourceVal="' + statsDataSourceName + '"></select>');
            }

        } else {

            $(this).parent().next().children().remove();
            if (extarColumnObjOption != "") {
                // 填写选择按列名的值
                schemesArray = extarColumnObjOption.schemes.split(",");

            }
            var typeStr3 = '<select class="multipleChoice inputTypeContent form-control" multiple="multiple" dataSourceVal="' + statsDataSourceName + '"> </select>';
            $(this).parent().next().append(typeStr3);

            multipleChoiceMethod(schemesArray);
        }

    });

    //select2多选方法
    function multipleChoiceMethod(schemesArray) {
        var schValArrList = []
        for (var p = 0; p < schemesArray.length; p++) {
            schValArrList.push({
                id: p,
                text: schemesArray[p]
            })
        }
        $('.choice-column-rule .multipleChoice').select2({
            data: schValArrList
        });
    }

    //得到数据源
    $("#data-source-rule").change(function (e) {
        e.stopPropagation();
        var statDatasourceName = $(this).val();
        if (statDatasourceName != "selectOption") {
            $("#remindGroup2").text("请等待，正在加载...");
            getExtarColumnObjOptionByDataName(statDatasourceName);
            $(".choice-column-rule .defaultIndexValue").attr("dataSourceVal", statDatasourceName);
        } else {

            $("#single-column .mCSB_container").html("");
        }
    });

    //得到数据源的对象方法
    function getExtarColumnObjOptionByDataName(statDatasourceName) {
        $.ajax({
            data: {'datasourceName': statDatasourceName, 'modelStr': JSON.stringify(configObj)},
            url: basePath + "/drag/hdfs/getColumnScheme.do",
            type: 'POST',
            dataType: "json",
            success: function (data) {
                switch (data.code) {
                    case 200:
                        $("#remindGroup2").empty();
                        extarColumnObjOption = $.parseJSON(data.msg);
                        break;
                }
            },
            error: function () {

            }
        })

    }

    /**
     * 通过规则选择列名end
     */

    //保存选择的内容
    $("#tagSave").on("click", function (e) {
        e.stopPropagation();
        if (saveType == true) {
            //将结果保存到configObj中。
            $("body .choice-column").mCustomScrollbar({
                theme: "dark-thin",
                axis: "y",
                setLeft: 0,
                scrollbarPosition: "inside"
            });
            $(".tag-already-" + choiceColumnConfigId + " .mCSB_container").empty();
            $(".choice-column-right p").not(".all").each(function () {
                // tagArray.push($(this).text());
                $(".tag-already-" + choiceColumnConfigId + " .mCSB_container").append('<p>' + $(this).text() + '</p>');
            });
            $("#tag-modal").modal("hide");
            var selectedValue = ""
            $(".tag-already-" + choiceColumnConfigId + " .mCSB_container").children("p").each(function () {
                selectedValue += $(this).text() + ","
            })

            //获取inputname文件名拼接好的字符串
            var savedDataSource = getInputNameStr();

            for (var m = 0; m < configObj.length; m++) {
                if (currentBlockId == configObj[m].BlockId) {
                    for (var k = 0; k < configObj[m].data.length; k++) {
                        if (configObj[m].data[k].configId == choiceColumnConfigId) {
                            configObj[m].data[k].configVal = selectedValue.substring(0, selectedValue.length - 1)
                            configObj[m].data[k].configDataSource = savedDataSource.substring(0, savedDataSource.length - 1)
                            configObj[m].data[k].configSelectedDataSource = $("#data-source").val()
                            configObj[m].data[k].configDataType = $("#data-type option").map(function () {
                                return $(this).val();
                            }).get().join(", ")
                            configObj[m].data[k].configSelectedDataType = $("#data-type").val()
                            configObj[m].data[k].configStatModelToTypeOption = JSON.stringify(extarColumnObj)
                            var preparaedScheme = ""
                            $("#preparaed-column .mCSB_container").children("p").not(".all").each(function () {
                                preparaedScheme += $(this).text() + ","
                            });
                            configObj[m].data[k].configPreparaedScheme = preparaedScheme.substring(0, preparaedScheme.length - 1)
                            configObj[m].data[k].configSerachName = $("#serach-scheme").val();
                            configObj[m].data[k].configSelectNameOrRuleVal = "true";
                        }
                    }
                }
            }
            console.log("aaa")
        } else {
            //通过规则选择保存
            var configSelectRuleJson = [];
            var configValue = [];
            var configVal = [];
            var dataName = $("#data-source-rule").val();
            if (extarColumnObjOption != "" && extarColumnObjOption != undefined) {
                if (dataName != "请选择") {
                    //判断数据源不能为空

                    var typesArr = extarColumnObjOption.types.split(",");
                    //数据源
                    var dataSourceName = $("#data-source-rule").val();

                    $(".choice-column-rule .data-section-rule").each(function () {
                        console.log(schemesArr)
                        var section = $(this).val();
                        var type = $(this).parent().next().children(1).val();
                        var content = $(this).parent().next().next().children(1).val();
                        var dataSourceValue = $(this).parent().next().next().children(1).attr("dataSourceVal");
                        var schemesArr = [];
                        if (dataSourceValue == dataSourceName) {
                            schemesArr = extarColumnObjOption.schemes.split(",");
                        } else {
                            for (var ke = 0; ke < typesArr.length; ke++) {
                                schemesArr = extarColumnObjOption.statModelTypeToScheme[dataSourceValue + "_" + typesArr[ke]].split(",")
                                schemesArr.concat(schemesArr);
                            }

                        }

                        if (content != undefined && content != "" && content != null) {
                            configSelectRuleJson.push({
                                section: section,
                                type: type,
                                content: content,
                                dataSourceName: dataSourceValue
                            });

                            if (section == "正选") {
                                //
                                if (type == "按索引") {
                                    var contentArr = content.split(",");
                                    for (var a = 0; a < contentArr.length; a++) {
                                        if (contentArr[a].indexOf("-") > 0) {
                                            var arr = contentArr[a].split("-");
                                            for (var b = arr[0]; b <= arr[1]; b++) {
                                                configValue.push(schemesArr[b])
                                            }
                                        } else {
                                            configValue.push(schemesArr[contentArr[a]])
                                        }

                                    }

                                } else if (type == "按类型") {
                                    if (dataSourceName != "" && dataSourceName != "selectOption") {
                                        var dataSourceType = dataSourceName + "_" + content;
                                        var typearr1 = extarColumnObjOption.statModelTypeToScheme[dataSourceType].split(",");
                                        for (var ki = 0; ki < typearr1.length; ki++) {
                                            configValue.push(typearr1[ki]);
                                        }
                                    }
                                } else {
                                    //按列名
                                    if (content != "") {
                                        var contentArr1 = content;
                                        for (var km = 0; km < contentArr1.length; km++) {
                                            configValue.push(schemesArr[contentArr1[km]]);
                                        }
                                    }

                                }
                            } else {
                                //反选
                                var arr1 = extarColumnObjOption.schemes.split(",");//数组全部的值
                                var spliceArr = [];//输入的部分值
                                if (type == "按索引") {
                                    var contentArr = content.split(",");
                                    for (var a = 0; a < contentArr.length; a++) {
                                        if (contentArr[a].indexOf("-") > 0) {
                                            var arr = contentArr[a].split("-");
                                            for (var b = arr[0]; b <= arr[1]; b++) {
                                                spliceArr.push(schemesArr[b])
                                            }
                                        } else {
                                            spliceArr.push(schemesArr[contentArr[a]])
                                        }

                                    }

                                } else if (type == "按类型") {
                                    if (dataSourceName != "" && dataSourceName != "selectOption") {
                                        var dataSourceType = dataSourceName + "_" + content;
                                        var typearr1 = extarColumnObjOption.statModelTypeToScheme[dataSourceType].split(",");
                                        for (var ki = 0; ki < typearr1.length; ki++) {
                                            spliceArr.push(typearr1[ki]);
                                        }
                                    }
                                } else {
                                    //按列名
                                    if (content != "") {
                                        var contentArr1 = content;
                                        for (var km = 0; km < contentArr1.length; km++) {
                                            spliceArr.push(schemesArr[contentArr1[km]]);
                                        }
                                    }

                                }
                                //去重
                                for (var ko = 0; ko < arr1.length; ko++) {
                                    for (var kp = 0; kp < spliceArr.length; kp++) {
                                        if (arr1[ko] === spliceArr[kp]) {
                                            arr1.splice(ko, 1);
                                        }
                                    }
                                }
                                for (var ka = 0; ka < arr1.length; ka++) {
                                    configValue.push(arr1[ka]);
                                }

                            }
                        }

                    });
                    //去重复的数据
                    $(".tag-already-" + choiceColumnConfigId + " .mCSB_container").empty();
                    for (var h in configValue) {
                        if (configVal.indexOf(configValue[h]) == -1) {
                            $(".tag-already-" + choiceColumnConfigId + " .mCSB_container").append('<p>' + configValue[h] + '</p>');
                            configVal.push(configValue[h]);
                        }
                    }
                    $("#tag-modal").modal("hide");
                    //保存数据
                    var savedDataSource = getInputNameStr();
                    for (var f = 0; f < configObj.length; f++) {
                        if (currentBlockId == configObj[f].BlockId) {
                            for (var k = 0; k < configObj[f].data.length; k++) {
                                if (configObj[f].data[k].configId == choiceColumnConfigId) {
                                    configObj[f].data[k].configVal = configVal.toString();
                                    configObj[f].data[k].configSelectNameOrRuleVal = "false";
                                    configObj[f].data[k].configDataSource = savedDataSource.substring(0, savedDataSource.length - 1);
                                    configObj[f].data[k].configSelectedDataSource = $("#data-source-rule").val();
                                    configObj[f].data[k].configSelectRuleJson = JSON.stringify(configSelectRuleJson);
                                    configObj[f].data[k].configStatModelToTypeOption = JSON.stringify(extarColumnObjOption);
                                }
                            }
                        }
                    }
                }

            } else {
                $("#tag-modal").modal("hide");
            }


        }

    });


}

var modalNum = 0

function choiceSingleColumn(buttonEle) {
    var configId = buttonEle.attr("dataVal");
    $("#data-source1").empty();
    $("#single-column .mCSB_container").html("");
    $("#search-name").val("");
    var blockId = buttonEle.parents("#formBody").attr("name");
    currentBlockId = blockId;
    // $("#formBody").on("click","#choice-tag",function () {
    //拿到所有的数据源名称
    var currentDataArr = getInputNameArray();

    console.log(currentDataArr);
    //第一步，加载字段来
    //第一步，加载字段来源
    var isSameWorkStream = true;
    var extarColumnObj;
    for (var i = 0; i < configObj.length; i++) {
        if (blockId == configObj[i].BlockId) {
            if (configObj[i].id == "125") {
                // 循环渲染之前将json整合
                configObj[i].data = conformity_arr(configObj[i].data);
            }
            for (var j = 0; j < configObj[i].data.length; j++) {
                // for(var j=0; j<1;j++){
                if (configObj[i].data[j].configId === configId) {
                    var reloadConfigDataSource = configObj[i].data[j].configDataSource;
                    //判断当前已保存的数据源算子是否与当前工作流里面已存在的算子相同
                    if (reloadConfigDataSource.split(",").length != currentDataArr.length) {
                        isSameWorkStream = false
                    } else {
                        $.each(currentDataArr, function (index, value) {
                            if (reloadConfigDataSource.indexOf(value) == -1) {
                                isSameWorkStream = false
                            }
                        })
                    }

                    if (reloadConfigDataSource == undefined || reloadConfigDataSource == null || reloadConfigDataSource == "" || !isSameWorkStream) {
                        var dataSourceArr = [];
                        $("#tag-single-modal").modal("show")
                        for (var k = 0; k < configObj.length; k++) {
                            if (configObj[k].id == 1 || configObj[k].id == 56) {
                                for (var m = 0; m < configObj[k].data.length; m++) {
                                    if (configObj[k].data[m].configName == "inputPath") {
                                        var configVal = configObj[k].data[m].configVal;
                                        if (configVal != "") {
                                            var csvName = configVal.split("/")[configVal.split("/").length - 1];
                                            dataSourceArr.push(configObj[k].BlockId + "_" + csvName);
                                        }
                                    }
                                }
                            }
                            if (configObj[k].id == 53) {
                                for (var m = 0; m < configObj[k].data.length; m++) {
                                    if (configObj[k].data[m].configName == "tableName") {
                                        var tableName = configObj[k].data[m].configVal.split("|")[1];
                                        if (tableName != "null") {
                                            dataSourceArr.push(configObj[k].BlockId + "_" + tableName);
                                        }
                                    }
                                }
                            }
                        }
                        $("#data-source1").empty();
                        var datasourceStr = '<option value="selectOption">' + "请选择" + '</option>';
                        $.each(dataSourceArr, function (index, value) {
                            var showValue = value.substring(value.split("_")[0].length + 1, value.length);
                            datasourceStr = datasourceStr + '<option value="' + value + '">' + showValue + '</option>>'
                        })
                        $("#data-source1").append(datasourceStr);

                    } else {
                        $("#tag-single-modal").modal("show");
                        var configDataSource = configObj[i].data[j].configDataSource;
                        var selectedDataSource = configObj[i].data[j].configSelectedDataSource;
                        var configPreparaedScheme = configObj[i].data[j].configPreparaedScheme;
                        var dataSourceOptionStr = '<option value="selectOption">' + "请选择" + '</option>';
                        $.each(configDataSource.split(","), function (index, value) {
                            if (value.trim() != "selectOption") {
                                dataSourceOptionStr += '<option value="' + value.trim() + '">' + value.substring(value.split("_")[0].length + 1, value.length) + '</option>'
                            }
                        })
                        $("#data-source1").append(dataSourceOptionStr);
                        $("#data-source1").val(selectedDataSource);
                        var preparaedSchemeStr = "";
                        $.each(configPreparaedScheme.split(","), function (index, value) {
                            preparaedSchemeStr += '<p>' + value + '</p>'
                        });
                        $("#single-column .mCSB_container").append(preparaedSchemeStr);

                    }
                }
            }
            if (configObj[i].id == "125") {
                // for循环结束之后将json重新拆分
                configObj[i].data = split_array(configObj[i].data, 9)
            }
        }
    }
    var arr = [];
    if (modalNum == 0) {
        $("#data-source1").change(function () {
            $("#search-name").val("");
            var statDatasourceName = $(this).val();
            if (statDatasourceName != "selectOption") {
                $("#remindGroup1").text("请等待，正在加载...");
                $.ajax({
                    data: {'datasourceName': statDatasourceName, 'modelStr': JSON.stringify(configObj)},
                    url: basePath + "/drag/hdfs/getColumnScheme.do",
                    type: 'POST',
                    dataType: "json",
                    success: function (data) {
                        switch (data.code) {
                            case 200:
                                $("#remindGroup1").empty();
                                extarColumnObj = $.parseJSON(data.msg);
                                var dataTypeStr = '<option value="selectType">' + "请选择" + '</option>';
                                var schemeTypesArr = extarColumnObj.types.split(",");


                                $("#single-column .mCSB_container").empty();
                                var schemeStr = "";
                                var schemesArr = extarColumnObj.schemes.split(",");
                                $.each(schemesArr, function (index, value) {
                                    schemeStr += '<p>' + value + '</p>';
                                });
                                $("#single-column .mCSB_container").append(schemeStr);

                                arr = [];
                                $("#single-column .mCSB_container").children("p").each(function () {
                                    arr.push($(this).text())
                                });
                                break;
                        }
                    },
                    error: function () {

                    }
                })
            } else {

                $("#single-column .mCSB_container").html("");
            }
        })
    }

    //搜索功能
    if (modalNum == 0) {
        $("#search-name").on("input propertychange", function () {
            console.log(arr)
            // $("#preparaed-column").empty()
            var serachWord = $(this).val();
            var schemeStr = "";
            $("#single-column .mCSB_container").empty();
            $.each(arr, function (index, value) {
                if (value.indexOf(serachWord) != -1) {
                    schemeStr += '<p>' + value + '</p>';
                }
            })
            $("#single-column .mCSB_container").append(schemeStr);
        })
    }
    // 保存到模板属性方法
    if (modalNum == 0) {
        var singleColVal = "";
        $("#single-column").on("click", "p", function () {
            singleColVal = $(this).text();
            $('#single-column p').css({color: '#000000', background: "#ffffff"});
            $(this).css({color: '#ffffff', background: "#1bbc9b"});
        });
        $("#saveSingleCol").on("click", function () {
            //将结果保存到configObj中。
            if (singleColVal == "") {

                $("#single-column + .linkNameError").remove();
                $("#single-column").parent().after("<p class='linkNameError font-orange linkError' style='padding: 0 18px'>请选择一列</p>");
            } else {
                $("#single-column + .linkNameError").remove();
                $("body .choice-column").mCustomScrollbar({
                    theme: "dark-thin",
                    axis: "y",
                    setLeft: 0,
                    scrollbarPosition: "inside"
                });
                $(".tag-already-single" + configId + " .mCSB_container").empty();
                $(".tag-already-single" + configId + " .mCSB_container").append('<p>' + singleColVal + '</p>');

                $("#tag-single-modal").modal("hide");


                //获取inputname文件名拼接好的字符串
                var savedDataSource = getInputNameStr();
                for (var m = 0; m < configObj.length; m++) {
                    if (currentBlockId == configObj[m].BlockId) {
                        var configModalId = configObj[m].id;
                        // 循环渲染之前将json整合
                        if (configModalId == "125") {
                            configObj[m].data = conformity_arr(configObj[m].data);
                        }
                        for (var k = 0; k < configObj[m].data.length; k++) {
                            if (configObj[m].data[k].configId == configId) {
                                configObj[m].data[k].configVal = singleColVal;
                                configObj[m].data[k].configDataSource = savedDataSource.substring(0, savedDataSource.length - 1)
                                configObj[m].data[k].configSelectedDataSource = $("#data-source1").val();
                                configObj[m].data[k].configPreparaedScheme = arr.toString();
                            }
                        }
                        // for循环结束之后将json重新拆分
                        if (configModalId == "125") {
                            configObj[m].data = split_array(configObj[m].data, 9)
                        }
                    }
                }
                $("#saveSingleCol").off("click");
            }

        });
    }
}

//------------------------------------------------------------

// ----------------------列名修改方法-------------------------
function reviseColumn() {
    var datasourceName, tag, allModify = [];
    $("body").on("click", "#choiceRevise", function () {
        $("#revise").modal("show");
        var blockId = $(this).parents("#formBody").attr("name");
        currentBlockId = blockId;

        //拿到所有的数据源名称
        var currentDataArr = getInputNameArray();
        // 加载字段来源
        var dataFromStr = '<option value="selectOption">' + "请选择" + '</option>';
        $.each(currentDataArr, function (i, val) {
            var showValue = val.substring(val.split("_")[0].length + 1, val.length);
            dataFromStr += '<option value="' + val + '">' + showValue + '</option>'
        });
        $("#reviseData").html(dataFromStr);
        // 将configVal里已存在的数据渲染到弹框中
        for (var m = 0; m < configObj.length; m++) {
            if (currentBlockId == configObj[m].BlockId) {
                for (var k = 0; k < configObj[m].data.length; k++) {
                    if (configObj[m].data[k].configType == 15) {
                        var listAllName = configObj[m].data[k].configVal;
                        listAllName = JSON.parse(listAllName);
                    }
                }
            }
        }
        ;
        // // 重载弹框数据
        var listStr = "", str = "";
        if (listAllName != null) {
            for (var i = 0; i < listAllName.oldName.split(",").length; i++) {
                listStr += '<div class="row field-line" data-from="' + listAllName.nameFrom.split(",")[i] + '">' +
                    '<div class="col-sm-5">' +
                    '<input type="text" class="' + listAllName.nameFrom.split("_")[0] + "tag" + ' form-control" value="' + listAllName.oldName.split(",")[i] + '">' +
                    '</div>' +
                    '<div class="col-sm-6">' +
                    '<input type="text" class="form-control" value="' + listAllName.newName.split(",")[i] + '">' +
                    '</div>' +
                    '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
                    '</div>';
            }

            // // 匹配字段来源是否和已选择的字段无匹配项
            var unSamArr = listAllName.nameFrom.split(",");
            // 对unSamArr进行去重处理
            Array.prototype.unique3 = function () {
                var res = [];
                var json = {};
                for (var i = 0; i < this.length; i++) {
                    if (!json[this[i]]) {
                        res.push(this[i]);
                        json[this[i]] = 1;
                    }
                }
                return res;
            };
            unSamArr = unSamArr.unique3();
            // 匹配字段来源是否和已选择的字段无匹配项
            for (var i = 0; i < currentDataArr.length; i++) {
                for (var k = 0; k < unSamArr.length; k++) {
                    if (unSamArr[k] == currentDataArr[i]) {
                        unSamArr.splice(k, 1);
                    }
                }
            }
            ;


            // 匹配页面上取到的namefrom是否不存在
            var existFrom = $("#revise .choice-column .mCSB_container .field-line");
            for (var k = 0; k < existFrom.length; k++) {
                for (var i = 0; i < unSamArr.length; i++) {
                    if ($(existFrom[k]).attr("data-from") == unSamArr[i]) {
                        $(existFrom[k]).addClass("listErrorDis");
                        $("#reviseErrorMessage").html('<p><span>警告：</span><span><i class="icon iconfont icon-jian2"></i></span>表示该字段已失效，请 点击按钮 或者 <span id="deleteAllErroe" class="deleteErroe">批量删除</span></p>');
                        $("#revise").addClass("listErrorEvents");
                    }
                }
            }
            // 匹配字段来源是否和已选择的字段无匹配项end
        }
        $("#revise .choice-column .mCSB_container").html(listStr);
        isSame();
    });
    // 获取列名

    // 自动补全方法
    function autoChange(name, allModify) {
        $("." + name).autocomplete({
            minLength: 0,
            delay: 0,
            minChars: 0,                //激活自动完成的输入字符数
            matchContains: true,        //只要包含输入字符就会显示提示
            autoFill: false,            //自动填充输入框
            mustMatch: true,
            source: allModify,
            focus: function () {
                // 防止在获得焦点时插入值
                return false;
            }
        })
    }

    $("#reviseData").on("change", function () {
        datasourceName = $(this).val();
        if (datasourceName != "selectOption") {
            $.ajax({
                url: basePath + "/drag/hdfs/getColumnScheme.do",
                type: 'POST',
                dataType: "json",
                data: {'datasourceName': datasourceName, 'modelStr': JSON.stringify(configObj)},
                success: function (data) {
                    switch (data.code) {
                        case 200:
                            // 定义变量存储获取的列名
                            allModify = ($.parseJSON(data.msg).schemes).split(',');
                            tag = datasourceName.split("_")[0] + "tag";
                            str = '<div class="row field-line" data-from="' + datasourceName + '">' +
                                '<div class="col-sm-5">' +
                                '<input type="text" class="' + tag + ' form-control" value="' + allModify[0] + '">' +
                                '</div>' +
                                '<div class="col-sm-6">' +
                                '<input type="text" class="form-control" value="">' +
                                '</div>' +
                                '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
                                '</div>';
                            $("#revise .choice-column .mCSB_container").append(str);
                            $(".field-line:last .col-sm-5 input").val("").focus().val(allModify[0]);
                            autoChange(tag, allModify);
                            isSame();
                            break;
                    }
                }
            })
        }
    });


    $("#revise").on("click", ".choice-column .icon.icon-jian2", function () {
        $(this).parents(".field-line").remove();
        var m = $("#revise .field-line.listErrorDis");
        if ($("#revise .field-line").hasClass("listError") || $("#revise .field-line").hasClass("listErrorDis")) {

        } else {
            $("#revise").removeClass("listErrorEvents");
            $("#reviseErrorMessage").empty();
        }
        ;
        if ($(this).parents(".field-line").hasClass("listError")) {
            $("#revise .field-line").removeClass("listError");
            $("#revise").removeClass("listErrorEvents");
            $("#reviseErrorMessage").empty();
        }
        if (m.length == 0) {
            $("#revise .field-line").removeClass("listErrorDis");
            $("#revise").removeClass("listErrorEvents");
            $("#reviseErrorMessage").empty();
        }
    });
    // 新增
    $("#addLine").on("click", function () {
        str = '<div class="row field-line" data-from="' + datasourceName + '">' +
            '<div class="col-sm-5">' +
            '<input type="text" class="' + tag + ' form-control" value="' + allModify[0] + '">' +
            '</div>' +
            '<div class="col-sm-6">' +
            '<input type="text" class="form-control" value="">' +
            '</div>' +
            '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
            '</div>';
        $("#revise .choice-column .mCSB_container").append(str);
        $(".field-line:last .col-sm-5 input").val("").focus().val(allModify[0]);
        autoChange(tag, allModify);
        isSame();
    });

    // input失去焦点判断是否存在相同字段
    function isSame() {
        $("#revise .field-line .col-sm-5 input").on("blur", function () {
            $(this).addClass("changeThis");
            var changeListName = $(this).val(),
                changeListClass = $(this).parents(".field-line"),
                isSame;
            $("#revise .field-line .col-sm-5 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    $(this).parents(".field-line").addClass("listError");
                    changeListClass.addClass("listError");
                }
            });
            $("#revise .field-line .col-sm-5 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    isSame = true;
                    return false;
                } else {
                    isSame = false;
                }
            });
            if (isSame) {
                $("#reviseErrorMessage").html('<p><span>警告：</span>选择字段不能相同，请更改列名或删除！</p>');
                $("#revise").addClass("listErrorEvents");
            } else {
                $("#reviseErrorMessage").empty();
                $("#revise .field-line").removeClass("listError");
                $("#revise").removeClass("listErrorEvents");
            }
            $(this).removeClass("changeThis");

        });

        // 判断修改后的列名是否相同
        $("#revise .field-line .col-sm-6 input").on("blur", function () {
            $(this).addClass("changeThis");
            var changeListName = $(this).val(),
                changeListClass = $(this).parents(".field-line"),
                isSame;
            $("#revise .field-line .col-sm-6 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    $(this).parents(".field-line").addClass("listError");
                    changeListClass.addClass("listError");
                }
            });
            $("#revise .field-line .col-sm-6 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    isSame = true;
                    return false;
                } else {
                    isSame = false;
                }
            });
            if (isSame) {
                $("#reviseErrorMessage").html('<p><span>警告：</span>修改字段不能相同，请更改列名或删除！</p>');
                $("#revise").addClass("listErrorEvents");
            } else {
                $("#reviseErrorMessage").empty();
                $("#revise .field-line").removeClass("listError");
                $("#revise").removeClass("listErrorEvents");
            }
            $(this).removeClass("changeThis");

        });
    };

    // 保存
    $("#saveLine").on("click", function () {
        if ($("#revise .field-line").hasClass("listError")) {
            return false;
        }
        // oldName
        var oldName = [];
        $("#revise .field-line .col-sm-5 input").each(function () {
            oldName.push($(this).val());
        });
        // newName
        var newName = [];
        $("#revise .field-line .col-sm-6 input").each(function () {
            newName.push($(this).val());
        });
        // from
        var nameFrom = [];
        $("#revise .field-line").each(function () {
            nameFrom.push($(this).data("from"));
        });
        var changeVal = {
            oldName: oldName.toString(),
            newName: newName.toString(),
            nameFrom: nameFrom.toString()
        };
        // 更改的数据保存在模板属性
        var listStr = "";
        if (changeVal != null) {
            // var listAllName = JSON.parse(configVal);
            var chooseType;
            for (var i = 0; i < oldName.length; i++) {
                listStr += '<p><span>' + oldName[i] + '</span><br/><span>' + newName[i] + '</span></p>'
            }

        }
        // end
        for (var m = 0; m < configObj.length; m++) {
            if (currentBlockId == configObj[m].BlockId) {
                for (var k = 0; k < configObj[m].data.length; k++) {
                    if (configObj[m].data[k].configType == 15) {
                        configObj[m].data[k].configVal = JSON.stringify(changeVal);
                        $("#config" + configObj[m].data[k].configId + " .mCSB_container").html(listStr);
                        // addScrollBar();
                    }
                }
            }
        }
        ;


        $("#revise").modal("hide");
    })
}

//------------------------------------------------------------

// ----------------------类型转换-----------------------------
function typeTransform() {
    var datasourceName, tag, allModify = [];
    $("body").on("click", "#choiceTransform", function () {
        $("#transform").modal("show");
        var blockId = $(this).parents("#formBody").attr("name");
        currentBlockId = blockId;

        //拿到所有的数据源名称
        var currentDataArr = getInputNameArray();
        // 加载字段来源
        var dataFromStr = '<option value="selectOption">' + "请选择" + '</option>';
        $.each(currentDataArr, function (i, val) {
            var showValue = val.substring(val.split("_")[0].length + 1, val.length);
            dataFromStr += '<option value="' + val + '">' + showValue + '</option>'
        });
        $("#modifyData").html(dataFromStr);
        // 将configVal里已存在的数据渲染到弹框中
        for (var m = 0; m < configObj.length; m++) {
            if (currentBlockId == configObj[m].BlockId) {
                for (var k = 0; k < configObj[m].data.length; k++) {
                    if (configObj[m].data[k].configType == 13) {
                        var listAllName = configObj[m].data[k].configVal;
                        listAllName = JSON.parse(listAllName);
                    }
                }
            }
        }
        ;
        // 重载弹框数据
        var listStr = "", str = "";
        if (listAllName != null) {
            for (var i = 0; i < listAllName.listName.split(",").length; i++) {
                var typeCheck = listAllName.newType.split(",")[i];
                if (typeCheck == "1") {
                    str = '<option value="1" selected="selected">string</option><option value="0">int</option>';
                } else {
                    str = '<option value="1">string</option><option selected="selected" value="0">int</option>';
                }
                ;
                listStr += '<div class="row field-line" data-from="' + listAllName.nameFrom.split(",")[i] + '">' +
                    '<div class="col-sm-7">' +
                    '<input type="text" class="' + listAllName.nameFrom.split("_")[0] + "tag" + ' form-control" value="' + listAllName.listName.split(",")[i] + '">' +
                    '</div>' +
                    '<div class="col-sm-4">' +
                    '<select class="form-control">' + str + '</select>' +
                    '</div>' +
                    '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
                    '</div>';
            }
            $("#transform .choice-column .mCSB_container").html(listStr);

            // // 匹配字段来源是否和已选择的字段无匹配项
            var unSamArr = listAllName.nameFrom.split(",");
            // 对unSamArr进行去重处理
            Array.prototype.unique3 = function () {
                var res = [];
                var json = {};
                for (var i = 0; i < this.length; i++) {
                    if (!json[this[i]]) {
                        res.push(this[i]);
                        json[this[i]] = 1;
                    }
                }
                return res;
            };
            unSamArr = unSamArr.unique3();
            // 匹配字段来源是否和已选择的字段无匹配项
            for (var i = 0; i < currentDataArr.length; i++) {
                for (var k = 0; k < unSamArr.length; k++) {
                    if (unSamArr[k] == currentDataArr[i]) {
                        unSamArr.splice(k, 1);
                    }
                }
            }
            ;

            // 匹配页面上取到的namefrom是否不存在
            var existFrom = $("#transform .choice-column .mCSB_container .field-line");
            for (var k = 0; k < existFrom.length; k++) {
                for (var i = 0; i < unSamArr.length; i++) {
                    if ($(existFrom[k]).attr("data-from") == unSamArr[i]) {
                        $(existFrom[k]).addClass("listErrorDis");
                        $("#errorMessage").html('<p><span>警告：</span><span><i class="icon iconfont icon-jian2"></i></span>表示该字段已失效，请 点击按钮 或者 <span id="deleteAllErroe" class="deleteErroe">批量删除</span></p>');
                        $("#transform").addClass("listErrorEvents");
                    }
                }
            }
            // 匹配字段来源是否和已选择的字段无匹配项end
        }

        isSame();


    });
    // 获取列名

    // 自动补全方法
    function autoChange(name, allModify) {
        $("." + name).autocomplete({
            minLength: 0,
            source: allModify,
            focus: function () {
                // 防止在获得焦点时插入值
                return false;
            }
        })
    }

    $("#modifyData").on("change", function () {
        datasourceName = $(this).val();
        if (datasourceName != "selectOption") {
            $.ajax({
                url: basePath + "/drag/hdfs/getColumnScheme.do",
                type: 'POST',
                dataType: "json",
                data: {'datasourceName': datasourceName, 'modelStr': JSON.stringify(configObj)},
                success: function (data) {
                    switch (data.code) {
                        case 200:
                            // 定义变量存储获取的列名
                            allModify = ($.parseJSON(data.msg).schemes).split(',');
                            tag = datasourceName.split("_")[0] + "tag";
                            str = '<div class="row field-line" data-from="' + datasourceName + '">' +
                                '<div class="col-sm-7">' +
                                '<input type="text" class="' + tag + ' form-control" value="' + allModify[0] + '">' +
                                '</div>' +
                                '<div class="col-sm-4">' +
                                '<select class="form-control">' +
                                '<option value="1">string</option><option value="0">int</option>' +
                                '</select>' +
                                '</div>' +
                                '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
                                '</div>';
                            $("#transform .choice-column .mCSB_container").append(str);
                            $(".field-line:last .col-sm-7 input").val("").focus().val(allModify[0]);
                            autoChange(tag, allModify);
                            isSame();
                            break;
                    }
                }
            })
        }
    });

    // 删除
    $("#transform").on("click", ".choice-column .icon.icon-jian2", function () {
        $(this).parents(".field-line").remove();
        var m = $("#transform .field-line.listErrorDis");
        if ($("#transform .field-line").hasClass("listError") || $("#transform .field-line").hasClass("listErrorDis")) {

        } else {
            $("#transform").removeClass("listErrorEvents");
            $("#errorMessage").empty();
        }
        ;
        if ($(this).parents(".field-line").hasClass("listError")) {
            $("#transform .field-line").removeClass("listError");
            $("#transform").removeClass("listErrorEvents");
            $("#errorMessage").empty();
        }
        if (m.length == 0) {
            $("#transform .field-line").removeClass("listErrorDis");
            $("#transform").removeClass("listErrorEvents");
            $("#errorMessage").empty();
        }

    });
    // 新增
    $("#addTFline").on("click", function () {
        str = '<div class="row field-line" data-from="' + datasourceName + '">' +
            '<div class="col-sm-7">' +
            '<input type="text" class="' + tag + ' form-control" value="' + allModify[0] + '">' +
            '</div>' +
            '<div class="col-sm-4">' +
            '<select class="form-control">' +
            '<option value="1">string</option><option value="0">int</option>' +
            '</select>' +
            '</div>' +
            '<div class="icon-position col-sm-1"><i class="icon iconfont icon-jian2"></i></div>' +
            '</div>';
        $("#transform .choice-column .mCSB_container").append(str);
        $(".field-line:last .col-sm-7 input").val("").focus().val(allModify[0]);
        autoChange(tag, allModify);
        isSame();
    });

    // input失去焦点判断是否存在相同字段
    function isSame() {
        $("#transform .field-line .col-sm-7 input").on("blur", function () {
            $(this).addClass("changeThis");
            var changeListName = $(this).val(),
                changeListClass = $(this).parents(".field-line"),
                isSame;
            $("#transform .field-line .col-sm-7 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    $(this).parents(".field-line").addClass("listError");
                    changeListClass.addClass("listError");
                }
            });
            $("#transform .field-line .col-sm-7 input").not(".changeThis").each(function () {
                if ($(this).val() == changeListName) {
                    isSame = true;
                    return false;
                } else {
                    isSame = false;
                }
            });
            if (isSame) {
                $("#errorMessage").html('<p><span>警告：</span>选择字段不能相同，请更改列名或删除！</p>');
                $("#transform").addClass("listErrorEvents");
            } else {
                $("#errorMessage").empty();
                $("#transform .field-line").removeClass("listError");
                $("#transform").removeClass("listErrorEvents");
            }
            $(this).removeClass("changeThis");

        });
    };

    // 保存
    $("#saveTFline").on("click", function () {
        if ($("#transform .field-line").hasClass("listError")) {
            return false;
        }
        // listName
        var listName = [];
        $("#transform .field-line .col-sm-7 input").each(function () {
            listName.push($(this).val());
        });
        // newType
        var newType = [];
        $("#transform .field-line .col-sm-4 option:selected").each(function () {
            newType.push($(this).val());
        });
        // from
        var nameFrom = [];
        $("#transform .field-line").each(function () {
            nameFrom.push($(this).data("from"));
        });
        var changeVal = {
            listName: listName.toString(),
            newType: newType.toString(),
            nameFrom: nameFrom.toString()
        };
        // 更改的数据保存在模板属性
        var listStr = "";
        if (changeVal != null) {
            // var listAllName = JSON.parse(configVal);
            var chooseType;
            for (var i = 0; i < listName.length; i++) {
                if (newType[i] == "1") {
                    chooseType = "string";
                } else if (newType[i] == "0") {
                    chooseType = "int"
                }
                listStr += '<p><span>' + listName[i] + '</span><br/><span>' + chooseType + '</span></p>'
            }

        }
        // end
        for (var m = 0; m < configObj.length; m++) {
            if (currentBlockId == configObj[m].BlockId) {
                for (var k = 0; k < configObj[m].data.length; k++) {
                    if (configObj[m].data[k].configType == 13) {
                        configObj[m].data[k].configVal = JSON.stringify(changeVal);
                        $("#config" + configObj[m].data[k].configId + " .mCSB_container").html(listStr);
                        // addScrollBar();
                    }
                }
            }
        }
        ;


        $("#transform").modal("hide");

    })

}

//------------------------------------------------------------

// ----------------------数据库连接相关方法方法---------------
//设置读取数据库算子连接名拖拽下拉框的切换监听
function dbLinkNameChange(selectEle) {
    var connName = selectEle.val();
    var tableSelect = selectEle.parent().parent().parent().next().find(".form-control");
    var blockId = selectEle.parents("#formBody").attr("name");
    // alert("blockId:" + blockId);
    var tableConfigId = tableSelect.attr("id");
    tableSelect.empty();
    //每次求换前将tableName的值置为空。
    for (var i = 0; i < configObj.length; i++) {
        if (configObj[i].BlockId == blockId) {
            for (var k = 0; k < configObj[i].data.length; k++) {
                if (configObj[i].data[k].configId == tableConfigId) {
                    configObj[i].data[k].configVal = "null" + "|" + "null";
                }
            }
        }
    }
    $.ajax({
        url: basePath + "/drag/modelattri/loadTables.do",
        type: "POST",
        dataType: "json",
        data: "connName=" + connName,

        success: function (data) {
            // 针对用户只切换连接名的select框而不切换表名的select框时，将不会触发表名的onchange方法，
            // 这是将表名select框的第一个option值作为默认选择的option保存在configVal中。
            var options = "";
            var tables = "";
            $.each(data, function (index, value) {
                options += '<option value="' + value + '">' + value + '</option>';
                tables += value + ",";
            })
            var formatTables = tables.substring(0, tables.length - 1);
            tableSelect.append(options);

            // 切换连接名时，更新sqlStatment验证。
            var sqlStatment = getSqlStatment(blockId);
            if (sqlStatment != null && sqlStatment != "") {
                var formatSql = sqlStatment.replace(/\s+/g, ",");
                var words = formatSql.split(",");
                for (var a = 0; a < words.length; a++) {
                    if (words[a] == "from") {
                        if (words[a + 1] != data[0]) {
                            $("#sql-Check").html("<span class=\"required\" id=\"sqlStatment-notice\"> * </span>sql语句查询的表名必须与所选表名一致!");
                        } else {
                            $("#sql-Check").html("");
                        }
                    }
                }
            }

            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == blockId) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configId == tableConfigId) {
                            // configObj[i].data[k].configVal = configObj[i].data[k].configVal.split("|")[0] + "|" + data[0];
                            configObj[i].data[k].configVal = formatTables + "|" + data[0];
                        }
                    }
                }
            }
        },
        error: function (data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        }
    })
}

/**
 * 数据库新建连接的业务逻辑
 */
function createNewLink() {
    $("#dataLink").modal("show");
}

/**
 * 数据库连接测试
 * @constructor
 */
    //用一个全局变量来保存ta_exception标签的状态。
var allowShowTestResult = true;

function LinkDataBaseTest() {
    allowShowTestResult = true;
    $("#connect_test").attr("disabled", true);
    $("#la_errorinfo").html("");
    var type = $.trim($("#resource_type").val());
    var databaseName = $.trim($("#resource_databaseName").val());
    var hostIp = $.trim($("#resource_hostIp").val());
    var port = $.trim($("#resource_port").val());
    var userName = $.trim($("#resource_username").val());
    var passWord = $.trim($("#resource_password").val());
    var connectNmae = $.trim($("#resource_linkname").val());
    var textarea = $("#ta_exception");
    textarea.html("");
    var config = {
        'type': type,
        'databaseName': databaseName,
        'hostIp': hostIp,
        'hostPort': port,
        'userName': userName,
        'passWord': passWord,
        'connectNmae': connectNmae
    };

    console.log(basePath + "/drag/ReadResource/linktest.do");
    console.log(config);
    $.ajax({
        url: basePath + "/drag/ReadResource/linktest.do",
        type: 'POST',
        data: config,
        async: true,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        // contentType:"text",
        beforeSend: function () {
            $('#ta_prepare').show();
        },
        success: function (data) {
            $('#ta_prepare').hide();
            console.log(data);
            if (data.indexOf("失败") != -1) {
                if (allowShowTestResult) {
                    textarea.html("<span class='font-orange'>" + data + "</span>");
                    // textarea.val("未知错误！");
                }
            } else {
                if (allowShowTestResult) {
                    textarea.html("<span class='font-color'>" + data + "</span>");
                    // textarea.val(data);
                }
                // console.log("连接成功！" == data);
            }
            $("#connect_test").attr("disabled", false);
        },
        error: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
            console.log("回传数据失败！")
            if (true) {
                textarea.html("<span class='font-orange'>未知错误!</span>");
            }
            // textarea.val("未知错误！");
            // $("#connect_save").attr("disabled", true);
            $("#connect_test").attr("disabled", false);
        },
        complete: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
    })
}

function closeform() {
    $("#parame_form")[0].reset();
    $("#ta_exception").empty();
    $(".linkNameError").remove();
    $(".linkDataBaseError").remove();
    $(".hostIpError").remove();
    $(".portError").remove();
    $(".userNameError").remove();
    $(".passwordError").remove();
    $("#connect_test").attr("disabled", false);
    $('#ta_prepare').hide();
    allowShowTestResult = false;
}

function cancleLink() {
    $("#parame_form")[0].reset();
    $("#ta_exception").empty();
    $(".linkNameError").remove();
    $(".linkDataBaseError").remove();
    $(".hostIpError").remove();
    $(".portError").remove();
    $(".userNameError").remove();
    $(".passwordError").remove();
    $("#connect_test").attr("disabled", false);
    $('#ta_prepare').hide();
    allowShowTestResult = false;
}

function saveLink() {
    var newConnName = $("#resource_linkname").val();
    var value = $("#connect_save").val();
    var type = $("#resource_type").val();
    var databaseName = $("#resource_databaseName").val();
    var hostIp = $("#resource_hostIp").val();
    var port = $("#resource_port").val();
    var userName = $("#resource_username").val();
    var passWord = $("#resource_password").val();

    var checkFunc = [checkLinkName, checkDataBaseName, checkHostIp, checkPort, checkUserName, checkPassword];
    var result = checkFunc.every(function (item, index, array) {
        return item();
    });

    var config = {
        'type': type,
        'databaseName': databaseName,
        'hostIp': hostIp,
        'hostPort': port,
        'userName': userName,
        'passWord': passWord,
        'connectNmae': newConnName
    };
    //新建连接时要添加的select的option。
    var newConnOption = '<option value="' + newConnName + '">' + newConnName + '</option>';
    var connNames = "";
    // var configId;
    // var tableSelect;
    // var newTableOption;
    $.ajax({
        url: basePath + "/drag/modelattri/savelink.do",
        type: 'POST',
        dataType: "json",
        data: config,
        success: function (data) {
            //数据库名下拉框
            if (data.code == 200) {
                if (data.msg == "") {
                    for (var i = 0; i < configObj.length; i++) {
                        for (var k = 0; k < configObj[i].data.length; k++) {
                            if (configObj[i].data[k].configName.trim() == "dbLinkName") {
                                var oldConfigVal = configObj[i].data[k].configVal;
                                connNames = oldConfigVal.split("|")[0];
                                var selectedLink = oldConfigVal.split("|")[1];
                                //对于新建连接前用户目录下已经存在连接名的情况，将新的连接名添加到老的连接名里面，设置select的选择值为新建连接前的选择值。
                                configObj[i].data[k].configVal = connNames + "," + newConnName + "|" + selectedLink;
                                configId = configObj[i].data[k].configId;
                            }
                        }
                    }
                    $("#" + configId).append(newConnOption);

                    for (var i = 0; i < configObj.length; i++) {
                        for (var k = 0; k < configObj[i].data.length; k++) {
                            if (configObj[i].data[k].configName.trim() == "dbLinkName") {
                                var oldConfigVal = configObj[i].data[k].configVal;
                                connNames = oldConfigVal.split("|")[0];
                                var selectedLink = oldConfigVal.split("|")[1];
                                var configId = configObj[i].data[k].configId;
                                $("#" + configId).val(selectedLink);
                            }
                        }
                    }
                    cancleLink();
                    $("#dataLink").modal('hide');
                } else {
                    $("#la_errorinfo").html(data.msg);
                }
            }
        },
        error: function (data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
            $("#la_errorinfo").html("保存失败，请重试！");
        }
    })
}

function getLinkNames() {
    var linkNames = "";
    $.ajax({
        url: basePath + "/drag/modelattri/LoadConnNames.do",
        type: 'GET',
        async: false,
        contentType: "text",
        success: function (data) {
            linkNames = data;
        },
        error: function (data) {
            if (data.responseText == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
        complete: function (data) {
            if (data == "AjaxSessionTimeout") {
                window.location.href = basePath;
                return;
            }
        },
    })
    // alert(linkNames);
    return linkNames;
}

function getSelectedTableName(blockId) {
    var selectedTableName = "";
    for (var i = 0; i < configObj.length; i++) {
        if (configObj[i].BlockId == blockId) {
            for (var k = 0; k < configObj[i].data.length; k++) {
                if (configObj[i].data[k].configName.trim() == "tableName") {
                    selectedTableName = configObj[i].data[k].configVal.split("|")[1];
                    return selectedTableName;
                }
            }
        }
    }
}

function getSqlStatment(blockId) {
    var sqlStatment = "";
    for (var i = 0; i < configObj.length; i++) {
        if (configObj[i].BlockId == blockId) {
            for (var k = 0; k < configObj[i].data.length; k++) {
                if (configObj[i].data[k].configName.trim() == "sqlStatment") {
                    sqlStatment = configObj[i].data[k].configVal.split("|")[0];
                    return sqlStatment;
                }
            }
        }
    }
}

$('#dataLink').on('hidden.bs.modal', function (e) {
    // do something...
    $(".linkError").remove();
});
//------------------------------------------------------------

// ----------------------没有输入连线的算子不允许运行function-
function InputDataCheck(connects) {
    var targetIds = [];
    for (var i = 0; i < connects.length; i++) {
        targetIds.push(connects[i].PageTargetId);
    }
    for (var i = 0; i < configObj.length; i++) {
        if ($.inArray(configObj[i].BlockId, targetIds) == -1 && configObj[i].id != 53 && configObj[i].id != 56 && configObj[i].id != 55 && configObj[i].id != 139) {
            toastr.error('"' + configObj[i].BlockName + '"' + "没有输入数据，请重新配置！");
            return false;
        }
    }
    return true;
}

//------------------------------------------------------------

// ----------------------条件过滤（新增条件过滤）-------------
// 整合json方法
function conformity_arr(arr) {
    var m = [];
    for (var a = 0; a < arr.length; a++) {
        for (var b = 0; b < arr[a].length; b++) {
            m.push(arr[a][b])
        }
    }
    return m;
}

// 拆分json方法
function split_array(arr, len) {
    var a_len = arr.length;
    var result = [];
    for (var i = 0; i < a_len; i += len) {
        result.push(arr.slice(i, i + len));
    }
    return result;
}

function addDn(id) {
    for (var i = 0; i < id.length; i++) {
        $(id[i]).addClass("dn")
    }
}

function reDn(id) {
    for (var i = 0; i < id.length; i++) {
        $(id[i]).removeClass("dn")
    }

}

function filterInit() {
    $("#config_filterType").nextAll().not("#config_newIndex").addClass("dn");
    $("#addNewFilterBox").removeClass("dn");
    // 二类分组初始化
    $("#config_groupType").nextAll().addClass("dn");
}

function filterFun() {
    $("#formBody").on("change", "#config420", function () {
        var _thisVal = $(this).val();
        if (_thisVal == "<=>" || _thisVal == "∉") {
            addDn(["#config_filterType", "#config_threshold"]);
            reDn(["#config_UpperThreshold", "#config_LowerThreshold"]);
        } else {
            reDn(["#config_filterType"]);
            addDn(["#config_UpperThreshold", "#config_LowerThreshold", "#config_UpperThresholdVal", "#config_LowerThresholdVal"]);
        }
    });
    $("#formBody").on("change", ".filterChoose", function () {
        var _filterType = $(this).val();
        if (_filterType == "quantile" || _filterType == "customFilter") {
            $(this).parents(".formBox").next().removeClass("dn");
        } else if (_filterType == "median" || _filterType == "mean" || _filterType == "lossValue" || _filterType == "atuopath") {
            $(this).parents(".formBox").next().addClass("dn");
            // $(this).parents(".formBox").next().find("input").val("");
        }
    })
}

// for循环给后添加的过滤增加点击事件
function forClick(len) {
    var i = len;
    $("#formBody").on("change", "#config420_" + i, function () {
        var _thisVal = $(this).val();
        if (_thisVal == "<=>" || _thisVal == "∉") {
            addDn(["#config_filterType_" + i, "#config_threshold_" + i]);
            reDn(["#config_UpperThreshold_" + i, "#config_LowerThreshold_" + i]);
        } else {
            reDn(["#config_filterType_" + i]);
            addDn(["#config_UpperThreshold_" + i, "#config_LowerThreshold_" + i, "#config_UpperThresholdVal_" + i, "#config_LowerThresholdVal_" + i]);
            // $("#config_UpperThresholdVal_"+i).val("");
            // $("#config_LowerThresholdVal_"+i).val("");
        }
    })
}

// 点击切换判断是否显示
function isDn(blockId) {
    for (var i = 0; i < configObj.length; i++) {
        if (blockId == configObj[i].BlockId && configObj[i].id == "125") {
            // 循环渲染之前将json整合
            configObj[i].data = conformity_arr(configObj[i].data);
            $("#formBody .formBox").addClass("dn");
            for (var k = 0; k < (configObj[i].data.length + 1) / 9; k++) {
                if (k > 0) {
                    reDn(["#config_" + configObj[i].data[k * 9 - 1].configName])
                }
                reDn(["#config_" + configObj[i].data[k * 9].configName, "#config_" + configObj[i].data[1 + k * 9].configName, ".operator-group .formBox"]);
                if (configObj[i].data[1 + k * 9].configSelectVal == "<=>" || configObj[i].data[1 + k * 9].configSelectVal == "∉") {
                    // addDn(["#config_"+configObj[i].data[1+k*8+1].configName,"#config_"+configObj[i].data[1+k*8+2].configName]);
                    reDn(["#config_" + configObj[i].data[1 + k * 9 + 3].configName, "#config_" + configObj[i].data[1 + k * 9 + 5].configName]);
                } else {
                    reDn(["#config_" + configObj[i].data[1 + k * 9 + 1].configName])
                }
                if (configObj[i].data[1 + k * 9 + 1].configSelectVal == "quantile" || configObj[i].data[1 + k * 9 + 1].configSelectVal == "customFilter") {
                    reDn(["#config_" + configObj[i].data[1 + k * 9 + 2].configName])
                }
                if (configObj[i].data[1 + k * 9 + 3].configSelectVal == "quantile" || configObj[i].data[1 + k * 9 + 3].configSelectVal == "customFilter") {
                    reDn(["#config_" + configObj[i].data[1 + k * 9 + 4].configName])
                }
                if (configObj[i].data[1 + k * 9 + 5].configSelectVal == "quantile" || configObj[i].data[1 + k * 9 + 5].configSelectVal == "customFilter") {
                    reDn(["#config_" + configObj[i].data[1 + k * 9 + 6].configName])
                }
            }
            // for循环结束之后将json重新拆分
            configObj[i].data = split_array(configObj[i].data, 9)
        }

    }
}

// 点击添加新过滤操作
function addNewFilter() {
    $("#formBody").on("click", "#addNewFilter", function () {
        var blockname = $(this).parents("#formBody").attr("name");
        // 添加数据到obj中
        var id_length;
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].id == "125" && configObj[i].BlockId == blockname) {
                id_length = configObj[i].data.length - 1;
                configObj[i].data.push([
                    {
                        configCommon: null,
                        configId: "config427_" + id_length,
                        configName: "logicRule_" + id_length,
                        configType: 4,
                        configVal: "false"
                    },
                    {
                        configCommon: null,
                        configDataSource: "",
                        configId: "config419_" + id_length,
                        configName: "labelCol_" + id_length,
                        configPreparaedScheme: "",
                        configSelectedDataSource: "",
                        configType: 11,
                        configVal: null
                    },
                    {
                        configCommon: null,
                        configId: "config420_" + id_length,
                        configName: "filterFun_" + id_length,
                        configSelectVal: ">=",
                        configType: 2,
                        configVal: ">=,<=,=,<=>,∉"
                    },
                    {
                        configCommon: null,
                        configId: "config421_" + id_length,
                        configName: "filterType_" + id_length,
                        configSelectVal: "mean",
                        configType: 2,
                        configVal: "mean,lossValue,quantile,customFilter,valuepath"
                    },
                    {
                        configCommon: null,
                        configId: "config422_" + id_length,
                        configName: "threshold_" + id_length,
                        configType: 1,
                        configVal: null
                    },
                    {
                        configCommon: null,
                        configId: "config423_" + id_length,
                        configName: "UpperThreshold_" + id_length,
                        configSelectVal: "mean",
                        configType: 2,
                        configVal: "mean,lossValue,quantile,customFilter"
                    },
                    {
                        configCommon: null,
                        configId: "config424_" + id_length,
                        configName: "UpperThresholdVal_" + id_length,
                        configType: 1,
                        configVal: null
                    },
                    {
                        configCommon: null,
                        configId: "config425_" + id_length,
                        configName: "LowerThreshold_" + id_length,
                        configSelectVal: "mean",
                        configType: 2,
                        configVal: "mean,lossValue,quantile,customFilter"
                    },
                    {
                        configCommon: null,
                        configId: "config426_" + id_length,
                        configName: "LowerThresholdVal_" + id_length,
                        configType: 1,
                        configVal: null
                    }
                ])
            }
        }
        // end
        // 页面创建
        switchConfigure(blockname, 125);
        isDn(blockname);
        var _boxlen = $("#formBody").find(".formBox").length - 3;
        // 添加点击事件
        forClick(id_length);
    })
}

//------------------------------------------------------------

// ----------------------阈值分组-----------------------------
function addThresFun() {
    var configId,blockId;
    $("#formBody").on('click', "#addThresGroup", function (e) {
        e.stopPropagation();
        $("#thresholdVal").val("");
        configId = $(this).attr("dataVal");
        blockId = $(this).parents("#formBody").attr("name");
        for (var i = 0; i < configObj.length; i++) {
            if (configObj[i].BlockId == blockId) {
                for (var k = 0; k < configObj[i].data.length; k++) {
                    if (configObj[i].data[k].configId == configId) {
                        $("#thresholdVal").val(configObj[i].data[k].configVal);
                    }
                }
            }
        }
        $("#addThresModel").modal('show');
    });
    $("#saveAddThre").on("click", function (e) {
        e.stopPropagation();
        var textVal = $("#thresholdVal").val();
        var ret = /^(\d+,)*\d+$/;
        //判断输入的是否是数字和逗号
        if (ret.test(textVal)) {

            var textArray = textVal.split(',');
            var str = "";
            for (var n = 0; n < textArray.length; n++) {
                var sort = n + 1;
                str += '<p><label class="labelMargin">组' + sort + '：</label>' + textArray[n] + '</p>'
            }
            $('#formBody #group_' + configId + ' .mCSB_container').html(str);
            //把数据保存到configObj
            for (var i = 0; i < configObj.length; i++) {
                if (configObj[i].BlockId == blockId) {
                    for (var k = 0; k < configObj[i].data.length; k++) {
                        if (configObj[i].data[k].configId == configId) {
                            configObj[i].data[k].configVal = textVal;
                        }
                    }
                }
            }
            console.log(configObj);

            $(".errorTitle").text("");
            $("#addThresModel").modal('hide');
        } else {
            $(".errorTitle").text("请合法输入逗号隔开的数字");
        }

    });
    $("#formBody").on("change", "#config480", function () {
        var _thisVal = $(this).val();
        if (_thisVal == "equal") {
            addDn(["#config_porportion", "#config_threshold", "#config_userdefine"]);
        } else if (_thisVal == "porportion") {
            addDn([ "#config_threshold", "#config_userdefine"]);
            reDn(["#config_porportion"]);
        } else if (_thisVal == "threshold") {
            addDn(["#config_porportion",  "#config_userdefine"]);
            reDn(["#config_threshold"]);
        } else if (_thisVal == "userdefine") {
            addDn(["#config_porportion", "#config_threshold"]);
            reDn(["#config_userdefine"]);
        }
    });
}
function isThresDn (blockId,modelId) {
    for (var i = 0; i < configObj.length; i++) {
        if (blockId == configObj[i].BlockId && configObj[i].id == "134") {
            $("#config_groupType").nextAll().addClass("dn");
            var _thisVal=configObj[i].data[ 1 ].configSelectVal;
            if (_thisVal == "equal") {
                addDn(["#config_porportion", "#config_threshold", "#config_userdefine"]);
            } else if (_thisVal == "porportion") {
                addDn([ "#config_threshold", "#config_userdefine"]);
                reDn(["#config_porportion"]);
            } else if (_thisVal == "threshold") {
                addDn(["#config_porportion",  "#config_userdefine"]);
                reDn(["#config_threshold"]);
            } else if (_thisVal == "userdefine") {
                addDn(["#config_porportion", "#config_threshold"]);
                reDn(["#config_userdefine"]);
            }
        }

    }
}
//------------------------------------------------------------


