<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>报警配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function checkQuery() {
		document.getElementById("emailPoliceSearch").submit();
	}
	function queryDetail(policeId) {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryAllByPoliceId.do',
			data : {"policeId":policeId},
			async : false,
			success : function(list) {
				if (list != null) {
					$("#police_name").html(list[0]["police_name"]);
					var emailBody = document.getElementById("emailBody");
					while(emailBody.firstChild){
						emailBody.removeChild(emailBody.firstChild);
					}
					var dataType = new Array();
					var d = 0;
					for (var i = 0; i < list.length; i++) {
						dataType[d] = list[i]["data_type"];
						d++;
						if (list[i]["email"] != null && list[i]["email_remark"] != null){
							var tr = emailBody.insertRow(i);
							var td = tr.insertCell(0);
							td.innerHTML = list[i]["email"];
							var td1 = tr.insertCell(1);
							td1.innerHTML = list[i]["email_remark"];
						}
					}
					var em = 0;
					var ph = 0;
					for (var i = 0; i < dataType.length; i++) {
						if (dataType[i] == 1) {
							em = 1;
						} else {
							ph = 1;
						}
					}
					if (em == 1 && ph == 0) {
						$("#data_type").html("邮件");
						$("#d1").css({display:"none"});
						$("#d2").css({display:"none"});
						$("#d3").css({display:"none"});
					} else if (em == 0 && ph == 1) {
						$("#data_type").html("短信");
						$("#d1").css({display:"block"});
						$("#d2").css({display:"block"});
						$("#d3").css({display:"block"});
					} else {
						$("#data_type").html("邮件、短信");
						$("#d1").css({display:"block"});
						$("#d2").css({display:"block"});
						$("#d3").css({display:"block"});
						var phoneBody = document.getElementById("phoneBody");
						while(phoneBody.firstChild){
							phoneBody.removeChild(phoneBody.firstChild);
						}
						var r = 0;
						for (var i = 0; i < list.length; i++) {
							if (list[i]["phone"] != null && list[i]["phone_remark"] != null){
								var tr = phoneBody.insertRow(r);
								var td = tr.insertCell(0);
								td.innerHTML = list[i]["phone"];
								var td1 = tr.insertCell(1);
								td1.innerHTML = list[i]["phone_remark"];
								$("#phone_content").html(list[i]["phone_content"]);
								r++;
							}
						}
					}
					$("#email_theme").html(list[0]["email_theme"]);
					$("#email_content").html(list[0]["email_content"]);
					$("#select").css({display:"block"});
				} else {
					alert("查询报警详细信息失败！");
					hide("select");
				}
			}
		});
	}
	
	//获取报警类型列表
	function initPoliceTypeSelect() {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/getPoliceTypeList.do',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#policeType").append("<option value="+msg[i]['police_id']+">"+msg[i]['police_name']+"</option>");
			},
			error : function(msg) {
	    		alert("获取报警类型失败!");
	    	}
		});
		
		var police_type = $("#police_type_hidden").val();
		var policeType = document.getElementById("policeType");
		for (var i = 0; i < policeType.options.length; i++) {
			if (policeType.options[i].value == police_type) {
				policeType.options[i].selected = 'selected';
			}
		}
	}
	function addEmailPolice() {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/getPoliceTypeList.do',
			async : false,
			success : function(msg) {
				var selectObj = document.getElementById("i_police_id");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				for (i in msg)
					$("#i_police_id").append("<option value="+msg[i]['police_id']+">"+msg[i]['police_name']+"</option>");
			},
			error : function(msg) {
	    		alert("获取报警类型失败!");
	    	}
		});
		$("#insert").css({display:"block"});
		var chk2 = document.getElementById("phone_method");
		chk2.onclick = function(){
			if (this.checked) {
				$("#s1").css({display:"block"});
				$("#s2").css({display:"block"});
				var phone = new Array();
				var p = 0;
				$("#insert").find("#i_phone").each(function(){
					phone[p] = $(this).val("");
					p++;
				});
				var phone_remark = new Array();
				var r = 0;
				$("#insert").find("#i_phone_remark").each(function(){
					phone_remark[r] = $(this).val("");
					r++;
				});
				$("#i_phone_content").val("");
				$("#s3").css({display:"block"});
				$("#s4").css({display:"block"});
			} else {
				$("#s1").css({display:"none"});
				$("#s2").css({display:"none"});
				$("#s3").css({display:"none"});
				$("#s4").css({display:"none"});
			}
		};
	}
	function addEmailPoliceSub() {
		var police_id = $("#i_police_id").val();
		if (police_id == "") {
			alert("请选择报警类型！");
			return;
		}
		var chk1 = document.getElementById("email_method");
		var chk2 = document.getElementById("phone_method");
		var data_type = new Array();
		if (chk1.checked && !chk2.checked) {
			data_type[0] = 1;
		} else if (!chk1.checked && chk2.checked) {
			data_type[0] = 2;
		} else if (chk1.checked && chk2.checked) {
			data_type[0] = 1;
			data_type[1] = 2;
		} else {
			alert("请选择报警方式！");
			return;
		}
		var email = new Array();
		var j = 0;
		$("#insert").find("#i_email").each(function(){
			email[j] = $(this).val();
			j++;
		});
		for (var i = 0; i < email.length; i++) {
			if (email[i] == "" || email[i] == null) {
				alert("请填写收件人地址！");
				return;
			}
			var pattern = /^([a-zA-Z0-9.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			if (!pattern.test(email[i])) {
				alert("邮箱地址格式不正确！");
				return;
			}
		}
		var email_remark = new Array();
		var k = 0;
		$("#insert").find("#i_email_remark").each(function(){
			email_remark[k] = $(this).val();
			k++;
		});
		for (var i = 0; i < email_remark.length; i++) {
			if (email_remark[i] == "" || email_remark[i] == null) {
				alert("请填写收件人备注！");
				return;
			}
		}
		var email_theme = $("#i_email_theme").val();
		if (email_theme == "" || email_theme == null) {
			alert("请填写邮件主题！");
			return;
		}
		var email_content = $("#i_email_content").val();
		if (email_content == null || email_content == "") {
			alert("请填写邮件模板！");
			return;
		}
		var phone = new Array();
		var p =0;
		if (chk2.checked) {
			$("#insert").find("#i_phone").each(function(){
				phone[p] = $(this).val();
				p++;
			});
			for (var i = 0; i < phone.length; i++) {
				if (phone[i] == "" || phone[i] == null) {
					alert("请填写短信接收人电话号码！");
					return;
				}
				if (phone[i].length != 11) {
					alert("手机号码必须是11位！");
					return;
				}
			}
		}
		var phone_remark = new Array();
		var r =0;
		$("#insert").find("#i_phone_remark").each(function(){
			phone_remark[r] = $(this).val();
			r++;
		});
		if (chk2.checked) {
			for (var i = 0; i < phone_remark.length; i++) {
				if (phone_remark[i] == "" || phone_remark == null) {
					alert("请填写短信接收人备注！");
					return;
				}
			}
		}
		
		var phone_content = $("#i_phone_content").val();
		if (chk2.checked) {
			if (phone_content == null || phone_content == "") {
				alert("请填写短信模板！");
				return;
			}
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addEmailPolice.do",
			data : "police_id="+police_id+"&data_type="+data_type+"&email="
					+email+"&email_remark="+email_remark+"&phone="+phone+"&email_content="+email_content+"&phone_content="
					+phone_content+"&phone_remark="+phone_remark+"&email_theme="+email_theme,
			dataType : "text",
			success : function(msg) {
				if (msg == '1') {
					alert("添加成功！");
					hide("insert");
					checkQuery();
				} else {
					alert("添加失败,该报邮件地址或手机号码已存在！");
					return;
				}
			}
		});
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	var emailId = new Array();
	function selectData(policeId) {
		//获取报警类型列表
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/getPoliceTypeList.do',
			async : false,
			success : function(msg) {
				var selectObj = document.getElementById("u_police_id");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				for(var i=0;i<msg.length;i++){
					if(policeId == msg[i].police_id){
						$("#u_police_id").append("<option selected='selected' value="+msg[i]['police_id']+">"+msg[i]['police_name']+"</option>");
					}else{
						$("#u_police_id").append("<option value="+msg[i]['police_id']+">"+msg[i]['police_name']+"</option>");
					}
				}
			},
			error : function(msg) {
	    		alert("获取报警类型失败!");
	    	}
		});
		//为控件赋值
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryAllByPoliceId.do',
			data : {"policeId":policeId},
			async : false,
			success : function(list) {
				if (list != null) {
					var tBody = document.getElementById("t3");
					while(tBody.firstChild){
						tBody.removeChild(tBody.firstChild);
					}
					for (var i = 0; i < list.length; i++) {
						var data_type = list[i]["data_type"];
						if (data_type == 1) {
							document.getElementById("u_email_method").checked = true;
						} else if (data_type == 2) {
							document.getElementById("u_phone_method").checked = true;
						}
						if (data_type == 2) {
							$("#s5").css({display:"block"});
							$("#s6").css({display:"block"});
							$("#s7").css({display:"block"});
							$("#s8").css({display:"block"});
						} else {
							$("#s5").css({display:"none"});
							$("#s6").css({display:"none"});
							$("#s7").css({display:"none"});
							$("#s8").css({display:"none"});
						}
						emailId[i] = list[i]["email_id"];
						if (list[i]["email"] != null && list[i]["email_remark"] != null) {
							var tr = tBody.insertRow(i);
							var td = tr.insertCell(0);
							td.innerHTML = "<span class='input_bgl'>"+"<input id='u_email' name='u_email' style='width: 190px;' value="+list[i]["email"]+">"+"</input>"+"</span>";
							var td1 = tr.insertCell(1);
							td1.innerHTML = "<span class='input_bgl'>"+"<input id='u_email_remark' name='u_email_remark' style='width: 70px;' value="+list[i]["email_remark"]+">"+"</input>"+"</span>";
							var td2 = tr.insertCell(2);
							td2.innerHTML = "<input type='button' value='添加' onclick='addEmail();'/>"+"<input type='button' value='删除' onclick='delEmail(this);'/>";
						}
					}
					$("#u_email_theme").val(list[0]["email_theme"]);
					$("#u_email_content").val(list[0]["email_content"]);
					var pBody = document.getElementById("t4");
					while(pBody.firstChild){
						pBody.removeChild(pBody.firstChild);
					}
					var flag = 0;
					var p = 0;
					for (var i = 0; i < list.length; i++) {
						if (list[i]["phone"] != null && list[i]["phone_remark"] != null) {
							var tr = pBody.insertRow(p);
							var td = tr.insertCell(0);
							td.innerHTML = "<span class='input_bgl'>"+"<input id='u_phone' name='u_phone' style='width: 190px;' maxlength='11' onkeyup='value=value.replace(/[^\d]/g,'')' value="+list[i]["phone"]+">"+"</input>"+"</span>";
							var td1 = tr.insertCell(1);
							td1.innerHTML = "<span class='input_bgl'>"+"<input id='u_phone_remark' name='u_phone_remark' style='width: 70px;' value="+list[i]["phone_remark"]+">"+"</input>"+"</span>";
							var td2 = tr.insertCell(2);
							td2.innerHTML = "<input type='button' value='添加' onclick='addPhone();'/>"+"<input type='button' value='删除' onclick='delPhone(this);'/>";
							p++;
						} else {
							if (flag == 0) {
								var tr = pBody.insertRow(0);
								var td = tr.insertCell(0);
								td.innerHTML = "<span class='input_bgl'>"+"<input id='u_phone' name='u_phone' style='width: 190px;' maxlength='11' onkeyup='value=value.replace(/[^\d]/g,'')' value="+""+">"+"</input>"+"</span>";
								var td1 = tr.insertCell(1);
								td1.innerHTML = "<span class='input_bgl'>"+"<input id='u_phone_remark' name='u_phone_remark' style='width: 70px;' value="+""+">"+"</input>"+"</span>";
								var td2 = tr.insertCell(2);
								td2.innerHTML = "<input type='button' value='添加' onclick='addPhone();'/>"+"<input type='button' value='删除' onclick='delPhone(this);'/>";
								flag = 1;
							}
						}
						$("#u_phone_content").val(list[i]["phone_content"]);
					}
					var num = $("#t4 tr").length;
					if (num > 1) {
						pBody.removeChild(pBody.lastChild);
					}
				} else {
					alert("查询报警详细信息失败！");
					hide("update");
				}
			}
		});
		//展开隐藏域
		$("#update").css({display: "block"});
		var chk = document.getElementById("u_phone_method");
		chk.onclick = function(){
			if (this.checked) {
				$("#s5").css({display:"block"});
				$("#s6").css({display:"block"});
				$("#s7").css({display:"block"});
				$("#s8").css({display:"block"});
			} else {
				$("#s5").css({display:"none"});
				$("#s6").css({display:"none"});
				$("#s7").css({display:"none"});
				$("#s8").css({display:"none"});
			}
		};
	}
	
	function updateByPoliceId() {
		var email_id = new Array();
		email_id = emailId;
		var police_id = $("#u_police_id").val();
		if (police_id == "" || police_id == null) {
			alert("请选择报警类型！");
			return;
		}
		var data_type = new Array();
		var email_method = document.getElementById("u_email_method");
		var phone_method = document.getElementById("u_phone_method");
		if (email_method.checked && !phone_method.checked) {
			data_type[0] = 1;
		} else if (!email_method.checked && phone_method.checked) {
			data_type[0] = 2;
		} else if (email_method.checked && phone_method.checked) {
			data_type[0] = 1;
			data_type[1] = 2;
		} else {
			alert("请选择报警方式！");
			return;
		}
		var email = new Array();
		var e = 0;
		$("#update").find("#u_email").each(function(){
			email[e] = $(this).val();
			e++;
		});
		for (var i = 0; i < email.length; i++) {
			if (email[i] == "" || email[i] == null) {
				alert("请填写收件人地址！");
				return;
			}
			var pattern = /^([a-zA-Z0-9.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			if (!pattern.test(email[i])) {
				alert("邮箱格式不正确！");
				return;
			}
		}
		var email_remark = new Array();
		var o = 0;
		$("#update").find("#u_email_remark").each(function(){
			email_remark[o] = $(this).val();
			o++;
		});
		for (var i = 0; i < email_remark.length; i++) {
			if (email_remark[i] == "" || email_remark[i] == null) {
				alert("请填写收件人备注！");
				return;
			}
		}
		var email_theme = $("#u_email_theme").val();
		if (email_theme == "" || email_theme == null) {
			alert("请填写邮件主题！");
			return;
		}
		var email_content = $("#u_email_content").val();
		if (email_content == "" || email_content == null) {
			alert("请填写邮件模板！");
			return;
		}
		
		var phone = new Array();
		var j = 0;
		if (phone_method.checked) {
			$("#update").find("#u_phone").each(function(){
				phone[j] = $(this).val();
				j++;
			});
			for (var i = 0; i < phone.length; i++) {
				if (phone[i] == "" || phone[i] == null) {
					alert("请填写短信接收人电话号码！");
					return;
				}
				if (phone[i].length != 11) {
					alert("手机号码必须为11位！");
					return;
				}
			}
		}
		
		var phone_remark = new Array();
		var k = 0;
		if (phone_method.checked) {
			$("#update").find("#u_phone_remark").each(function(){
				phone_remark[k] = $(this).val();
				k++;
			});
			for (var i = 0; i < phone_remark.length; i++) {
				if (phone_remark[i] == "" || phone_remark[i] == null) {
					alert("请填写短信接收人备注！");
					return;
				}
			}
		}
		var phone_content = $("#u_phone_content").val();
		if (phone_method.checked) {
			if (phone_content == "" || phone_content == null) {
				alert("请填写短信模板！");
				return;
			}
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updatePoliceId.do",
			data : "email_id="+email_id+"&police_id="+police_id+"&data_type="+data_type+"&email="
					+email+"&email_remark="+email_remark+"&email_content="+email_content+"&phone="
					+phone+"&phone_remark="+phone_remark+"&phone_content="+phone_content+"&email_theme="+email_theme,
			dataType : "text",
			success : function(msg) {
				if (msg == '1') {
					alert("修改成功！");
					checkQuery();
				} else {
					alert("修改失败,该报警类型已存在！");
					hide("update");
				}
			}
		});
	}
	function deleteByPoliceId(policeId) {
		if (confirm("确定要删除该条数据吗？")) {
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/deleteByPoliceId.do",
				data : "policeId=" + policeId,
				dataType : "text",
				success : function(msg) {
					if (msg == '1') {
						alert("删除成功！");
						checkQuery();
					} else {
						alert("删除失败！");
					}
				}
			});
		}
	}
	
	function addEmailPerson() {
		var num = $("#t1 tr").length;
		if (num == 11) {
			alert("邮件接收人最多只能添加10个！");
			return;
		}
		var tr = $("#t5 tr").eq(0).clone();
	    tr.appendTo("#t1");
	}
	function addPhonePerson() {
		var num = $("#t2 tr").length;
		if (num == 11) {
			alert("短信接收人最多只能添加10个！");
			return;
		}
		var tr = $("#t6 tr").eq(0).clone();   
	    tr.appendTo("#t2");
	}
	function delEmailPerson(input) {
		var num = $("#t1 tr").length;
		if(num == 2){
			return;
		}
		$(input).parent().parent().remove();
	}
	function delPhonePerson(input) {
		var num = $("#t2 tr").length;
		if(num == 2){
			return;
		}
		$(input).parent().parent().remove();
	}
	function addEmail() {
		var num = $("#t3 tr").length;
		if (num == 10) {
			alert("邮件接收人最多只能添加10个！");
			return;
		}
		var tr = $("#t7 tr").eq(0).clone();   
	    tr.appendTo("#t3");
	}
	function addPhone() {
		var num = $("#t4 tr").length;
		if (num == 10) {
			alert("短信接收人最多只能添加10个！");
			return;
		}
		var tr = $("#t8 tr").eq(0).clone();   
	    tr.appendTo("#t4");
	}
	function delEmail(input) {
		var num = $("#t3 tr").length;
		if(num == 1){
			return;
		}
		$(input).parent().parent().remove();
	}
	function delPhone(input) {
		var num = $("#t4 tr").length;
		if(num == 1){
			return;
		}
		$(input).parent().parent().remove();
	}
</script>
</head>
<body onload="initPoliceTypeSelect();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>报警配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageEmailPolice.do" target="right" id="emailPoliceSearch" name="emailPoliceSearch" method="post">
					<ul class="check-m">
						<li>
							<b>报警类型：</b>
							<span class="input_bgl">
								<select id="policeType" name="policeType" style="width: 200px;">
									<option value="">全部</option>
								</select>
								<input type="hidden" id="police_type_hidden" value="${policeType }"/>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
							<input type="button" class="icon_normal" value="添加" onclick="addEmailPolice();" />
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
							<td align="center">ID</td>
							<td align="center">报警类型</td>
							<td align="center">报警方式</td>
							<td align="center" width="12%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageEmailPolice.result) <= 0 }">
						<tr align="center">
							<td colspan="5">对不起，暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageEmailPolice.result }" var="emailPolice">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
						<td align="center">${emailPolice.police_id }</td>
						<td align="center">${emailPolice.police_name }</td>
						<td align="center">
							<c:if test="${emailPolice.data_type == 1 }">邮件</c:if>
							<c:if test="${emailPolice.data_type == 2 }">短信</c:if>
							<c:if test="${emailPolice.data_type == 12 || emailPolice.data_type == 21}">邮件、短信</c:if>
						</td>
						<td align="center">
							<a class="fl lj mr10" href="#" onclick="queryDetail('${emailPolice.police_id}');">查看</a>
							<a class="fl lj mr10" href="#" onclick="selectData('${emailPolice.police_id}');">配置</a>
							<a class="fl lj mr10" href="#" onclick="deleteByPoliceId('${emailPolice.police_id}');">删除</a>
						</td>
					</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span>
				<span class="contect-rt"></span>
				<span class="contect-lb"></span>
				<span class="contect-rb"></span>
			</div>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="select" class="pop" style="display: none; height: 500px; overflow:auto;overflow-x:hidden;overflow-x:auto; ">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">查看报警信息</span>
				<a class="close" href="javascript:hide('select')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td align="center" bgcolor="#eeeeee">报警类型：</td>
						<td id="police_name" align="center"></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">报警方式：</td>
						<td id="data_type" align="center"></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件接收人：</td>
						<td align="center">
							<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
								<tr>
									<td align="center" style="width: 170px;">邮件地址</td>
									<td align="center" style="width: 80px;">备注</td>
								</tr>
								<tbody id="emailBody"></tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件主题：</td>
						<td align="center" id="email_theme"></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件模板：</td>
						<td align="center" id="email_content"></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">
							<span id="d1" style="display: none;">短信接收人：</span>
						</td>
						<td align="center">
							<span id="d2" style="display: none;">
								<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
									<tr>
										<td align="center" style="width: 170px;">电话号码</td>
										<td align="center" style="width: 80px;">备注</td>
									</tr>
									<tbody id="phoneBody"></tbody>
								</table>
							</span>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">
							<span id="d3" style="display: none;">短信模板：</span>
						</td>
						<td align="center" id="phone_content"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="insert" class="pop" style="display: none; height: 500px; overflow:auto;overflow-x:hidden;overflow-x:auto; ">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">添加报警信息</span>
				<a class="close" href="javascript:hide('insert')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table border="0" cellspacing="0">
					<tr>
						<td align="center" bgcolor="#eeeeee" width="250px;">报警类型：</td>
						<td>
							<span class="input_bgl"> 
								<select id="i_police_id" name="i_police_id" style="width: 200px;">
									<option value="">全部</option>
								</select>
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">报警方式：</td>
						<td>
							<span>
								<input type="checkbox" id="email_method" name="email_method" value="1" checked="checked" disabled="disabled"/>邮件
								<input type="checkbox" id="phone_method" name="phone_method" value="2" />短信
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件接收人：</td>
						<td>
							<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
								<tbody id="t1">
									<tr>
										<td align="center">邮件地址</td>
										<td align="center">备注</td>
										<td align="center"><font color='red' size="4">*</font></td>
									</tr>
									<tr>
										<td>
											<span class="input_bgl">
									 			<input type="text" id="i_email" name="i_email" value="" style="width: 190px;"/>
											</span>
										</td>
										<td>
											<span class="input_bgl"> 
									 			<input type="text" id="i_email_remark" name="i_email_remark" value="" style="width: 70px;"/>
											</span>
										</td>
										<td>
											<input type="button" value="添加" onclick="addEmailPerson();" />
											<input type="button" value="删除" onclick="delEmailPerson(this);" />
										</td>
									</tr>
								</tbody>
							</table>
							最多添加10个
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件主题：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" id="i_email_theme" name="i_email_theme" style="width: 300px;"/>
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件模板：</td>
						<td>
							<span>
								 <textarea rows="" cols="34" id="i_email_content" name="i_email_content" style="resize:none;"></textarea>
							</span>
							<font color='red' size="4">*</font>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							模板集：【渠道】用于获取扣款渠道名称
						</td>
					</tr>
					<tr>
						<td  align="center" bgcolor="#eeeeee">
							<span id="s1" style="display: none">短信接收人：</span>
						</td>
						<td>
							<span id="s2" style="display: none;">
							<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
								<tbody id="t2">
									<tr>
										<td align="center">手机号码</td>
										<td align="center">备注</td>
										<td align="center"><font color='red' size="4">*</font></td>
									</tr>
									<tr>
										<td>
											<span class="input_bgl">
									 			<input type="text" id="i_phone" name="i_phone" style="width: 190px;" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"/>
											</span>
										</td>
										<td>
											<span class="input_bgl"> 
									 			<input type="text" id="i_phone_remark" name="i_phone_remark" value="" style="width: 70px;"/>
											</span>
										</td>
										<td>
											<input type="button" value="添加" onclick="addPhonePerson();" />
											<input type="button" value="删除" onclick="delPhonePerson(this);" />
										</td>
									</tr>
								</tbody>
							</table>
							最多添加10个
							</span>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">
							<span id="s3" style="display: none">短信模板：</span>
						</td>
						<td>
							<span id="s4" style="display: none"> 
								 <textarea rows="" cols="35" id="i_phone_content" name="i_phone_content" style="resize:none;"></textarea>
								 <font color='red' size="4">*</font>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								模板集：【渠道】用于获取扣款渠道名称
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="addEmailPoliceSub();" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('insert');checkQuery();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div style="display: none">
		<table>
		<tbody id="t5">
			<tr>
				<td>
					<span class="input_bgl">
			 			<input type="text" id="i_email" name="i_email" value="" style="width: 190px;"/>
					</span>
				</td>
				<td>
					<span class="input_bgl"> 
			 			<input type="text" id="i_email_remark" name="i_email_remark" value="" style="width: 70px;"/>
					</span>
				</td>
				<td>
					<input type="button" value="添加" onclick="addEmailPerson();" />
					<input type="button" value="删除" onclick="delEmailPerson(this);" />
				</td>
			</tr>
		</tbody>
		</table>
	</div>
	
	<div style="display: none">
		<table>
		<tbody id="t6">
			<tr>
				<td>
					<span class="input_bgl">
			 			<input type="text" id="i_phone" name="i_phone" style="width: 190px;" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</span>
				</td>
				<td>
					<span class="input_bgl"> 
			 			<input type="text" id="i_phone_remark" name="i_phone_remark" value="" style="width: 70px;"/>
					</span>
				</td>
				<td>
					<input type="button" value="添加" onclick="addPhonePerson();" />
					<input type="button" value="删除" onclick="delPhonePerson(this);" />
				</td>
			</tr>
		</tbody>
		</table>
	</div>
	
	<div id="update" class="pop" style="display: none; height: 500px; overflow:auto;overflow-x:hidden;overflow-x:auto; ">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">配置报警信息</span>
				<a class="close" href="javascript:hide('update')" onclick="checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table border="0" cellspacing="0">
					<tr>
						<td align="center" bgcolor="#eeeeee" width="250px;">报警类型：</td>
						<td>
							<span class="input_bgl"> 
								<select id="u_police_id" name="u_police_id" style="width: 200px;">
									<option value="">全部</option>
								</select>
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">报警方式：</td>
						<td>
							<span>
								<input type="checkbox" id="u_email_method" name="u_email_method" value="1" disabled="disabled" />邮件
								<input type="checkbox" id="u_phone_method" name="u_phone_method" value="2" />短信
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件接收人：</td>
						<td>
							<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
								<tr>
									<td align="center">邮件地址</td>
									<td align="center">备注</td>
									<td align="center"><font color='red' size="4">*</font></td>
								</tr>
								<tbody id="t3"></tbody>
							</table>
							最多添加10个
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件主题：</td>
						<td>
							<span class="input_bgl">
								 <input type="text" id="u_email_theme" name="u_email_theme" value="" style="width: 300px;"/>
							</span>
							<font color='red' size="4">*</font>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">邮件模板：</td>
						<td>
							<span>
								 <textarea rows="" cols="35" id="u_email_content" name="u_email_content" style="resize:none;"></textarea>
							</span>
							<font color='red' size="4">*</font>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							模板集：【渠道】用于获取扣款渠道名称
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">
							<span id="s5" style="display: none">短信接收人：</span>
						</td>
						<td>
							<span id="s6" style="display: none">
								<input type="hidden" id="u_email_id" name="u_email_id" value="" />
								<table border="1" cellpadding="0" cellspacing="0" style="border-color: black;">
									<tr>
										<td align="center">手机号码</td>
										<td align="center">备注</td>
										<td align="center"><font color='red' size="4">*</font></td>
									</tr>
									<tbody id="t4"></tbody>
								</table>
							最多添加10个
							</span>
						</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#eeeeee">
							<span id="s7" style="display: none">短信模板：</span>
						</td>
						<td>
							<span id="s8" style="display: none"> 
								<textarea rows="" cols="35" id="u_phone_content" name="u_phone_content" style="resize:none;"></textarea>
								<font color='red' size="4">*</font>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								模板集：【渠道】用于获取扣款渠道名称
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="updateByPoliceId();" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('update');checkQuery();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div style="display: none">
		<table>
		<tbody id="t7">
			<tr>
				<td>
					<span class="input_bgl">
			 			<input type="text" id="u_email" value="" style="width: 190px;"/>
					</span>
				</td>
				<td>
					<span class="input_bgl"> 
			 			<input type="text" id="u_email_remark" value="" style="width: 70px;"/>
					</span>
				</td>
				<td>
					<input type="button" value="添加" onclick="addEmail();" />
					<input type="button" value="删除" onclick="delEmail(this);" />
				</td>
			</tr>
		</tbody>
		</table>
	</div>
	
	<div style="display: none">
		<table>
		<tbody id="t8">
			<tr>
				<td>
					<span class="input_bgl">
			 			<input type="text" id="u_phone" value="" style="width: 190px;" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</span>
				</td>
				<td>
					<span class="input_bgl"> 
			 			<input type="text" id="u_phone_remark" value="" style="width: 70px;"/>
					</span>
				</td>
				<td>
					<input type="button" value="添加" onclick="addPhone();" />
					<input type="button" value="删除" onclick="delPhone(this);" />
				</td>
			</tr>
		</tbody>
		</table>
	</div>
</body>
</html>