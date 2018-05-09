<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/demo.css"/>
</head>
<body>
<div id="left">
    <div class="node radius" id="node1">开始</div>
    <div class="node" id="node2">流程</div>
    <div class="node" id="node3">判断</div>
    <div class="node radius" id="node4">结束</div>
</div>

<div id="right">
    <p>拖拉到此区域</p>
</div>
<div id="save">
    <input type="button" value="保存" onclick="save()" />
    <input type="button" value="加载" onclick="reload()" />
</div>

<script src="js/jquery-1.9.0.js"></script>
<script src="js/jquery-ui-1.9.2-min.js"></script>
<script src="js/jsPlumb-2.2.0.js"></script>
<script src="js/demo.js"></script>
</body>
</html>