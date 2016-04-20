<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="check clearfix" style="margin: 25px 5px;">
          <ul class="check-m">
          	<li class="welcome">
                <img src="images/hi.png" />
                <p>尊敬的会员 <span class="red">${sessionScope.login.loginName }</span>，欢迎您回来 ！</p>
            </li>
          </ul>
          <span class="red-radius-lt"></span>
          <span class="red-radius-rt"></span>
          <span class="red-radius-lb"></span>
          <span class="red-radius-rb"></span>
    </div>
</body>
</html>