<%
    String path = request.getContextPath();
    int port = request.getServerPort();
    String basePath = "";
    if(port==80){
        basePath = request.getScheme()+"://"+request.getServerName()+path;
    }else{
        basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    }
    request.setAttribute("basePath", basePath);
%>
<script>var basePath = "${basePath}";</script>
<script>var PATH = "${pageContext.request.contextPath}";</script>
