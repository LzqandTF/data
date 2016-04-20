<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style>
*{
	margin:0;
	padding:0;
}
.clear:after {
    clear: both;
    content: ".";
    display: block;
    height: 0;
    visibility: hidden;
}
nav{
	display:inline-block;
	border:1px solid #505255;
	border-bottom: 1px solid #282C2F;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	margin:50px;
	-webkit-box-shadow:1px 1px 3px #292929;
    -moz-box-shadow:1px 1px 3px #292929;
}
li{
	list-style:none;
	float:left;
	border-right: 1px solid #2E3235;
	position: relative;
	background:#555D5F;
}
li:hover{
	background:#3E4245;
	-moz-transition: background 1s ease-out;
	-webkit-transition: background 1s ease-out;
}
li a{
	display:block;
	height:40px;
	line-height:40px;
	padding:0 30px;
	font-size:12px;
	color:#fff;
	text-shadow: 0px -1px 0px #000;
	text-decoration:none;
	white-space:nowrap;
	border-left: 1px solid #999E9F;
    border-top: 1px solid #999E9F;
	-moz-border-top-left-radius: 2px;
	-webkit-border-top-left-radius: 2px;
	
	z-index:100;
}
li > a{
	position:relative;
}
li.first a{
	-moz-border-radius-topleft: 4px;
	-moz-border-radius-bottomleft: 4px;
	-webkit-border-top-left-radius: 4px;
	-webkit-border-bottom-left-radius: 4px;
}
li.last{
	border-right: 0 none;
}
</style>
</head>
<body>
	<nav>
		<ul class="clear">
			<li class="first"><a href="<%=request.getContextPath()%>/originalTimingTask.do" onclick="changeColor(this.id);">交易数据获取时间</a></li>
			<li><a href="<%=request.getContextPath()%>/dzDataTime.do" onclick="changeColor(this.id);">对账文件获取时间</a></li>
			<li><a href="<%=request.getContextPath()%>/dzTimingTask.do" onclick="changeColor(this.id);">对账时间</a></li>
			<li class="last"><a  href="<%=request.getContextPath()%>/createDzFileTimingTask.do" onclick="changeColor(this.id);">对账文件生成时间</a></li>
		</ul>
	</nav>
</body>
</html>