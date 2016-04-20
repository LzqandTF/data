<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统用户查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">用户管理</a>&gt;<span>系统用户查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryLogin.do" target="right" name="loginSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>操作员号</b>
							<span class="input_bgl">
								<input maxlength="20" type="text" name="loginName" value="${requestMap.loginName }" />
							</span>
						</li>
						<li>
							<b>操作员名称</b>
							<span class="input_bgl">
								<input maxlength="20" type="text" name="chineseName" value="${requestMap.chineseName }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td>序号</td>
							<td>操作员号</td>
							<td>操作员名称</td>
							<td>上次登录时间</td>				
							<td>状态</td>
							<td align="center" colspan="3">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageLogin.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageLogin.result }" var="login">
						<tr>
							<td>${login.id }</td>
							<td>${login.loginName }</td>
							<td>${login.chineseName }</td>
							<td><f:formatDate value="${login.loginDate }" /></td>
							<td id="status${login.id }">
								<c:if test="${login.status == 1 }">已开通</c:if> 
								<c:if test="${login.status == 2 }">已关闭</c:if>
							</td>
							<td align="center" width="90">
								<a class="fl lj mr10" href="javascript:resetChineseName('${login.id }','${login.chineseName }')">修改操作员名称</a>
							</td>
							<td align="center" width="70">
								<a class="fl lj mr10" href="javascript:resetPassword('${login.id }','${login.loginName }')">密码重置</a>
							</td>
							<td align="center" id="href${login.id }"> 
								<c:if test="${login.status == 1}">
									<a class="fl lj mr10" href="javascript:openOrClose('${login.id }',2)">关闭</a>
								</c:if> 
								<c:if test="${login.status == 2}">
									<a class="fl lj mr10" href="javascript:openOrClose('${login.id }',1)">开通</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageLogin.totalPages != null}">
				<div class="next">
					<c:if test="${pageLogin.pageNo > 1}">
						<a href="javascript:paging(${pageLogin.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageLogin.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageLogin.pageNo-2 > 0}">
						<a href="javascript:paging(${pageLogin.pageNo-2 })"><span>${pageLogin.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageLogin.pageNo-1 > 0}">
						<a href="javascript:paging(${pageLogin.pageNo-1 })"><span>${pageLogin.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageLogin.pageNo }</span></a>
					<c:if test="${pageLogin.pageNo+1 <= pageLogin.totalPages}">
						<a href="javascript:paging(${pageLogin.pageNo+1 })"><span>${pageLogin.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageLogin.pageNo+2 <= pageLogin.totalPages}">
						<a href="javascript:paging(${pageLogin.pageNo+2 })"><span>${pageLogin.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageLogin.pageNo+3 <= pageLogin.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageLogin.pageNo < pageLogin.totalPages}">
						<a href="javascript:paging(${pageLogin.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageLogin.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageLogin.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<div id="pop1" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">重置密码</span> 
				<a class="close" href="javascript:hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="id1" name="id1" />
				<input type="hidden" id="name1" name="name1" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">序号：</td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">操作员号：</td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">原密码：</td>
						<td>
							<span class="input_bgl"> 
								<input type="password" id="oldPwd" name="oldPwd" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">重置密码：</td>
						<td>
							<span class="input_bgl"> 
								<input type="password" id="newPwd" name="newPwd" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">确认重置密码：</td>
						<td>
							<span class="input_bgl"> 
								<input type="password" id="rePwd" name="rePwd" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updatePassword()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<div id="pop2" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">修改操作员名称</span> 
				<a class="close" href="javascript:hide('pop2')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="id_c_n" name="id_c_n" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">操作员名称：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="chineseName" name="chineseName" value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateChineseName()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementsByName("loginSearch")[0];
			with (form) {
				action = "<%=request.getContextPath()%>/queryLogin.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){		
			/* var id1 = $("#id").val(); 
			var reg = /^\d{1,}$/;		
			if(id1 != null && id1.length != 0){
				if(!reg.test(id1)){
					alert("操作员号必须为数字！");
					return;
				}
			} */
			document.loginSearch.submit();
		}
		
		function resetPassword(id1,loginName) {		
			$("#id1").val(id1);
			$("#name1").val(loginName);
			$("#oldPwd").val("");
			$("#newPwd").val("");
			$("#rePwd").val("");
			$("#login").find("tr:eq(0)").find("td:eq(1)").html(id1);		
			$("#login").find("tr:eq(1)").find("td:eq(1)").html(loginName);		
			document.getElementById("pop1").style.display = "block";
		}
		function resetChineseName(id,chineseName){
			$("#id_c_n").val(id);
			$("#chineseName").val(chineseName);
			document.getElementById("pop2").style.display = "block";
		}
		function updateChineseName(){
			var id = $("#id_c_n").val();
			var chineseName = $("#chineseName").val();
			
			
			if (chineseName == null || chineseName.length == 0) {
				alert("操作员名称不能为空!");
				return false;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/resetChineseName.do",
				data : "id="+ id +"&chineseName=" + chineseName,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("修改成功！");
						document.getElementById("pop2").style.display = "none";	
						checkQuery();
					}else {					
						alert("修改失败！");
						return;
					}
				}
			});
		}
		function updatePassword(pageNo) {
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var rePwd = $("#rePwd").val();
			var loginName = $("#name1").val();
			var id = $("#id1").val();
			var numasc = 0;
	        var charasc = 0;			
			for ( var i = 0; i < newPwd.length; i++) {
				var asciiNumber = newPwd.substr(i, 1).charCodeAt();
				if (asciiNumber >= 48 && asciiNumber <= 57) {
					numasc += 1;
				}
				if ((asciiNumber >= 65 && asciiNumber <= 90)
						|| (asciiNumber >= 97 && asciiNumber <= 122)) {
					charasc += 1;
				}
			}
			var reg = /^[A-Za-z0-9]{6,20}$/;
			if (oldPwd == null || oldPwd.length == 0) {
				alert("原密码不能为空!");
				return;
			} 
			if (newPwd == null || newPwd.length == 0) {
				alert("新密码不能为空!");
				return;
			} else if (!reg.test(newPwd)) {
				alert("新密码为6-20位数字和字母!");
				return;
			}
			if (0 == numasc) {
				alert("新密码必须含有数字");
				return;
			} else if (0 == charasc) {
				alert("新密码必须含有字母");
				return;
			}
			if(newPwd != rePwd){
				alert("两次密码输入不一致");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/resetLoginPassword.do",
				data : "id="+ id +"&loginName=" + loginName + "&oldPwd=" + oldPwd +"&newPwd="+ newPwd,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("重置成功！");
						document.getElementById("pop1").style.display = "none";						
					}else if(msg == "2"){
						alert("原密码输入有误！");
						return;
					}else {					
						alert("重置失败！");
						return;
					}
				}
			});
		}
		function openOrClose(ids,status){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/openOrClose.do",
				data : "id="+ ids +"&status=" + status,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						if(jQuery("#status"+ids).text().replace(/(^\s*)|(\s*$)/g,"")=="已开通"){					
							jQuery("#status"+ids).html("已关闭");
							jQuery("#href"+ids).html("<a class='fl lj mr10' href='javascript:openOrClose("+ids+",1)'>开通</a>");
						}else{					
							jQuery("#status"+ids).text("已开通" );
							jQuery("#href"+ids).html("<a class='fl lj mr10' href='javascript:openOrClose("+ids+",2)'>关闭</a>");					
						}	
					}else {					
						alert("状态修改失败");
					}
				}
			});
		}
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
	</script>
</body>
</html>
