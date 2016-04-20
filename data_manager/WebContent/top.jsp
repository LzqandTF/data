<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>
<head>
<meta name="Keywords" content="Travel, Flight, Airline" />
<meta http-equiv="windows-target" content="_top" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>主页面上部</title>

<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/javascript.js" type="text/javascript"></script>

</head>

<body>
	<div class="head">
		<img src="images/logo.png" />
		<h1>POS清结算系统V2.6-管理后台</h1>
		<h2>
			欢迎您，${sessionScope.login.loginName}&nbsp;&nbsp;上次登录：<f:formatDate value="${login.loginDate }" pattern="yyyyMMdd  HH:mm:ss"/> <a href="exit.do" >退出</a>
		</h2>
	</div>
</body>
</html>