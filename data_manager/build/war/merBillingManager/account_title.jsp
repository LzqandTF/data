<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户头寸调拨</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<style>
	div,ul,li{ margin:0px; padding:0px;}
	ul,li,a{font:bold 14px/24px "微软雅黑", Verdana, Arial, Helvetica, sans-serif; color:green; text-decoration:none;}
    ul,li,a:hover{color:red;}
    .menu ul li{ float:left; background:#990000; list-style:none;  line-height:40px; height:40px;margin-right:5px;  }
    .menu ul li a{ color:#FFFFFF;  display: inline-block;;  padding:0px 10px; line-height:40px;   }
    .menu ul li a:visited{ color:#FFFFFF}
    .menu ul li a:hover{ background:#CC0000;}
    .menu ul li a:active{ color:#FFFFFF}
</style>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: -25px 5px;">
			<div class="menu">
				<ul>
					<li><a href="<%=request.getContextPath()%>/queryBankAccount.do">银行账户</a></li>
					<li><a href="<%=request.getContextPath()%>/queryDyAccount.do">电银账户</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
