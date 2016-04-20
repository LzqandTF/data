<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统用户密码修改</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

<script type="text/javascript">
	function checkform() {		
		var loginName = $("#name").val();
		var id = $("#id").val();
		
		var numasc = 0;
        var charasc = 0;
		var oldPwd = document.getElementById("oldPwd").value;
		var pwd = document.getElementById("pwd").value;
		var rePwd = document.getElementById("rePwd").value;
		var reg = /^[A-Za-z0-9]{6,20}$/;
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
		if (oldPwd == null || oldPwd.length == 0) {
			alert("请输入原密码!");
			return false;
		}
		if (pwd == null || pwd.length == 0) {
			alert("新密码不能为空!");
			return false;
		} else if (!reg.test(pwd)) {
			alert("密码为6-20位数字和字母!");
			return false;
		} else if (rePwd != pwd) {
			alert("两次密码输入不一致!");
			return false;
		}
		if (0 == numasc) {
			alert("密码必须含有数字");
			return false;
		} else if (0 == charasc) {
			alert("密码必须含有字母");
			return false;
		}		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/resetLoginPassword.do",
			data : "id="+ id +"&loginName=" + loginName + "&oldPwd=" + oldPwd +"&newPwd="+ pwd,
			dataType : "text",
			success : function(msg) {
				if (msg == "1") {
					alert("修改成功！");
					document.getElementById("oldPwd").value="";
					document.getElementById("pwd").value="";
					rePwd = document.getElementById("rePwd").value="";					
				}else if(msg == "2"){
					alert("原密码输入有误！");
					return;
				}else {					
					alert("修改失败！");
					return;
				}
			}
		});
	}
	/**初始化密码框,防止火狐自动注入**/
	function init(){
		document.getElementById("oldPwd").value="";
		document.getElementById("pwd").value="";
		rePwd = document.getElementById("rePwd").value="";
	}
</script>
</head>

<body onload="init()">
	<div class="right" style="margin: 25px 5px;">
		<div class="position">
			当前位置：<a href="javascript:void(0)">用户管理</a>&gt;<span>系统用户密码修改</span>
		</div>
		<div class="table_2">
			<div class="card">
				<form action="<%=request.getContextPath()%>/resetLoginPassword.do" method="post" name="updatePassword">
					<input type="hidden" name="id" id="id" value="${login.id }" />
					<input type="hidden" name="name" id="name" value="${login.loginName }" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">						
						<tr>
							<td width="120" align="right" bgcolor="#eeeeee">序号：</td>
							<td>${login.id }</td>
						</tr>
						<tr>
							<td width="120" align="right" bgcolor="#eeeeee">操作员号：</td>
							<td>${login.loginName }</td>
						</tr>
						<tr>
							<td width="120" align="right" bgcolor="#eeeeee">操作员名称：</td>
							<td>${login.chineseName }</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">原密码：</td>
							<td>
								<span class="input_bgl"> 
									<input class="w150" type="password" id="oldPwd" name="oldPassword" maxlength="20" align="top" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">新密码：</td>
							<td>
								<span class="input_bgl"> 
									<input class="w150" type="password" id="pwd" name="password" maxlength="20" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">确定新密码：</td>
							<td>
								<span class="input_bgl"> 
									<input class="w150" type="password" id="rePwd" />
								</span>
								<font color='red' size="4" style="margin-left: 2px;">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input class="icon_normal" type="button" value="提交" onclick="checkform()" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
