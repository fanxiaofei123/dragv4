<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2017/12/21
  Time: 8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>算子注释详细说明</title>
</head>
<body>

</body>
<jsp:include page="/components/url.jsp"></jsp:include>
<script src="${basePath}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${basePath}/content/js/workSpace/notesJson.js" type="text/javascript"></script>
<script>
    $(function () {
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        }
        var modelID = GetQueryString('modelID');
        console.log(notesJson)
        console.log(notesJson.length)
        for(var i=0;i<notesJson.length;i++){
            if(modelID == notesJson[i].blockId){
                $("body").html(notesJson[i].notesDes)
            }
        }
    })
</script>
</html>
