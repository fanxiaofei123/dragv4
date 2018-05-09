
function jiekoutiaoshi(){
    var sss = $(".labelColumn").val();
    var labelColumn =[];
    $.each($(".labelColumn"),function (index,value) {
        labelColumn.push($(this).val());
    });
    console.log(labelColumn)
    $.ajax({
        url:basePath+"/service/serviceModelApiUse.do",
        type: 'GET',
        dataType: "json",
        data:{"labelColumn":labelColumn.toString(),"outputPath":$("#outputPath").val()},
        success:function (json) {
            console.log(json.obj)
            // json = JSON.parse(json);
            if(json.code == 200){
                $("#resultData").html(json.obj)
            }else{
                $("#resultData").html(json.obj)
            }
        }
    })
}


$(window).on("load", function () {
    $(".scrollbarContentDiv").mCustomScrollbar({
        theme: "minimal-dark",
        axis: "yx",
        setLeft: 0,
        scrollbarPosition: "inside",
        alwaysShowScrollbar: 3
    });

    var name=GetQueryString("serviceModelName");
    $("#serviceModelName").text(name)

});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}
window.onload=function(event){
    var button=document.getElementById("copyBtn");
    button.onclick=function(event){
        document.getElementById("body_code").select();
        document.execCommand("copy",false,null);
    };
};