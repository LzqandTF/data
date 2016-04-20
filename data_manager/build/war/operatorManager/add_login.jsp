<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统用户新增</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/js.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript">	
	function checkform() {
		var numasc = 0;
        var charasc = 0;
		var pwd = document.getElementById("pwd").value;
		var rePwd = document.getElementById("rePwd").value;
		var loginName = document.getElementById("loginName").value;
		var chineseName = document.getElementById("chineseName").value;
		
		if (chineseName == null || chineseName.length == 0) {
			alert("操作员名称不能为空!");
			return false;
		}
		
		for ( var j = 0; j < loginName.length; j++) {
			if(loginName[j] == " ") {
				alert("操作员号不能有空格！");
				return;
			} 
		}
		var reg = /^[A-Za-z0-9]{6,20}$/;
		if (loginName == null || loginName.length == 0) {
			alert("操作员号不能为空!");
			return false;
		}
		if (pwd == null || pwd.length == 0) {
			alert("初始密码不能为空!");
			return false;
		} else if (!reg.test(pwd)) {
			alert("密码为6-20位数字和字母!");
			return false;
		} else if (rePwd != pwd) {
			alert("两次密码输入不一致!");
			return false;
		}
		for ( var i = 0; i < pwd.length; i++) {
			var asciiNumber = pwd.substr(i, 1).charCodeAt();
			if (asciiNumber >= 48 && asciiNumber <= 57) {
				numasc += 1;
			}
			if ((asciiNumber >= 65 && asciiNumber <= 90)
					|| (asciiNumber >= 97 && asciiNumber <= 122)) {
				charasc += 1;
			}
		}
		if (0 == numasc) {
			alert("密码必须含有数字");
			return false;
		} else if (0 == charasc) {
			alert("密码必须含有字母");
			return false;
		}
		document.addLogin.submit();
	}

</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">用户管理</a>&gt;<span>系统用户新增</span>
			</div>
			<div class="table_2">
				<form action="<%=request.getContextPath()%>/addLogin.do" method="post" target="right" name="addLogin">
					<table width="100%" border="0" cellspacing="0">						
						<tr>
							<td width="120" align="right" bgcolor="#eeeeee">操作员号：</td>
							<td>
								<span class="input_bgl">
									<input type="text" maxlength="20" name="loginName" id="loginName" value=""  />
								</span>
								<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<tr>
							<td width="120" align="right" bgcolor="#eeeeee">操作员名称：</td>
							<td>
								<span class="input_bgl">
									<input type="text" maxlength="20" name="chineseName" id="chineseName" value="" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">初始密码：</td>
							<td>
								<span class="input_bgl">
									<input type="password" maxlength="20" name="pwd" id="pwd" onfocus="" value="" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">确定初始密码：</td>
							<td>
								<span class="input_bgl">
									<input type="password" maxlength="20" name="rePwd" id="rePwd" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>						
						<tr>
							<td colspan="2" align="center">
								<input type="button" class="icon_normal" value="提交" onclick="checkform()" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
