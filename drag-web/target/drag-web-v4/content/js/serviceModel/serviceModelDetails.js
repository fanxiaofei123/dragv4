
function serviceModelByApi(){
    $.ajax({
        url:basePath+"/service/serviceModelByApi.do",
        type: 'GET',
        dataType: "json",
        data:{"labelColumn":"{'1','2','3','4'}","serviceModelId":"63","outputPath":"hdfs://172.16.0.132:8020/user/GZD/workspaces/399/stateModel2_tmp_output_0ae26ca7-45ae-4178-a980-32584336dabf","key":"02b5f68e-8859-4b56-9d3e-26d879bed723_13"},
        success:function (json) {
            console.log(json)
            json = JSON.parse(json.obj);
            if(json.code == 200){
                $("#resultData").val(json.msg.data)
            }
        }
    })
}