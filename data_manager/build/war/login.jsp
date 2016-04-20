<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>登录页</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	if (top.location != self.location) {
		top.location = self.location;
	}
	function checklogin() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var verifyCode = document.getElementById("verifyCode").value;
		if (username == '') {
			alert("请输入用户名!");
			document.getElementById("username").focus();
			return false;
		}
		if (password == '') {
			alert("请输入密码!");
			document.getElementById("password").focus();
			return false;
		}

		if (verifyCode == '') {
			alert("请输入验证码!");
			document.getElementById("verifyCode").focus();
			return false;
		}

	}
	//更换验证码
	function changeVerifyCode() {
		//用<img>实现，则修改<img src=url>的url
		//这里有一个小技巧，如果给url赋相同的值，浏览器不会重新发出请求，因此用js生成一个即时毫秒数做url中的参数
		t = new Date().getTime();
		document.getElementById("verifyCodeImg").src = "verifyCodeAction?t="
				+ t;
	}
	function checklogins() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var verifyCode = document.getElementById("verifyCode").value;
		if (username == '' || username == "请输入用户名") {
			alert("请输入用户名!");
			document.getElementById("username").focus();
			return false;
		}
		if (password == '') {
			alert("请输入密码!");
			document.getElementById("password").focus();
			return false;
		}

		if (verifyCode == '' || verifyCode == "请输入验证码") {
			alert("请输入验证码!");
			document.getElementById("verifyCode").focus();
			return false;
		}
	}
	function BindEnter(obj)
	{
		var userNameInput=document.getElementById("username");
		userNameInput.focus();
	    //使用document.getElementById获取到按钮对象
	    var button = document.getElementById('login');
	    if(obj.keyCode == 13)
	        {
	            button.click();
	            obj.returnValue = false;
	        }
	}
	function valueOfNull() {
		var name = document.getElementById("username");
		if(name.value == "请输入用户名") {
			name.value = "";
		}
	}
	function nullOfValue() {
		var name = document.getElementById("username");
		if(name.value == "") {
			name.value = "请输入用户名";
		}
	}
	function verValueOfNull() {
		var name = document.getElementById("verifyCode");
		if(name.value == "请输入验证码") {
			name.value = "";
		}
	}
	function verNullOfValue() {
		var name = document.getElementById("verifyCode");
		if(name.value == "") {
			name.value = "请输入验证码";
		}
	}
</script>
</head>
<body  onload="BindEnter(event)">
	<div class="head"></div>
	<div class="login">
		<form action="<%=request.getContextPath() %>/login.do" method="post" onsubmit="return checklogins()">
			<ul class="login-m">
				<li>
				<span class="fl">用户名：</span>
					<span class="input_bgl">
						<input class="w320" value="请输入用户名" name="id" id="username" size="20" maxlength="20" onfocus="valueOfNull()" onblur="nullOfValue()"/>
					</span>
				</li>
				<li>
					<span class="fl">密&nbsp;&nbsp;&nbsp;码：</span>
					<span class="input_bgl">
						<input class="w320" name="passWord" id="password" maxlength="20" type="password"/>
					</span>
				</li>
				<li>
				<span class="fl">验证码：&nbsp;</span>
					<span class="input_bgl fl">
						<input class="w218" value="请输入验证码" name="verifyCode" id="verifyCode"  size="4" maxlength="4" onfocus="verValueOfNull()" onblur="verNullOfValue()"/>
					</span> 
					<img class="fl ml15" id="verifyCodeImg" src="<%=request.getContextPath()%>/img/verifyCode" style="cursor: hand; margin-top: 5px;" align="top" onclick="this.src = this.src + '?1'" />
				</li>
				<li>
	    		<font color="red">
					<c:if test="${loginerror!=null }">
						<c:out value="${loginerror }"></c:out>
					</c:if>
				</font>
	    	</li>
				<li><span class="fl mt15"></span>
					<button class="icon_login"></button></li>
			</ul>
		</form>
		<div class="login-b"></div>
	</div>
</body>
</html>
