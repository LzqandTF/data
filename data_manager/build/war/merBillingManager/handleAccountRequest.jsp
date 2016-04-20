<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手工调账申请</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function checkQuery() {
		var merCode = $("#merCode").val();
		if (merCode == "") {
			alert("请输入商户号！");
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/getMerInfoByMerCode.do",
			data : {"merCode": merCode},
			async:false,
			success : function(merchants) {
				if (merchants != null) {
					if (merchants.mer_code != null) {
						$("#mer_abbreviation").html(merchants.mer_abbreviation);
						if (merchants.mer_balance == null) {
							$("#mer_balance").html("0.00");
						} else {
							$("#mer_balance").html(merchants.mer_balance);
						}
						var merStatus = merchants.mer_state;
						if (merStatus == 5) {
							merStatus = "正常";
						} else {
							merStatus = "关闭";
						}
						$("#mer_status").html(merStatus);
						$("#addorsub").val("1");
						$("#rec_amount").val("");
						$("#request_desc").val("");
						$("#submit").css({display:"block"});
					} else {
						alert("该商户号不存在！");
						$("#submit").css({display:"none"});
					}
				} else {
					alert("该商户号不存在！");
				}
			}
		});
	}
	
	//实时动态强制更改用户录入
	function amount(th) {
		var regStrs = [
			['^0(\\d+)$','$1'],//禁止录入整数部分两位以上，但首位为0
			['[^\\d\\.]+$', ''],//禁止录入任何非数字和小数点
			['\\.(\\d?)\\.+','.$1'],//禁止录入两个以上的小数点
			['^(\\d+\\.\\d{2}).+','$1']//禁止录入小数点两位以后
		];
		for (i = 0; i < regStrs.length; i++) {
			var reg = new RegExp(regStrs[i][0]);
			th.value = th.value.replace(reg, regStrs[i][1]);
		}
	}
	
	
	function addManualRec(requestUser) {
		var mer_code = $("#merCode").val();
		var mer_status = $("#mer_status").html();
		var mer_abbreviation = $("#mer_abbreviation").html();
		var mer_balance = $("#mer_balance").html();
		var addorsub = $("#addorsub").val();
		var rec_amount = $("#rec_amount").val();
		var request_desc = $("#request_desc").val();
		if (mer_status == "关闭") {
			alert("该商户已关闭，不能进行手工调账申请！");
			return;
		}
		if (rec_amount == "" || rec_amount == null) {
			alert("请输入调整金额！");
			return;
		}
		if (request_desc == "" || request_desc == null) {
			alert("请填写调账原因！");
			return;
		}
		if (addorsub == 1 && rec_amount == "0.00") {
			alert("调增金额必须大于0元！");
			return;
		}
		if (addorsub == 2 && parseFloat(rec_amount) > parseFloat(mer_balance)) {
			alert("调减金额大于商户余额！");
			return;
		}
		if (addorsub == 2 && rec_amount == "0.00") {
			alert("调减金额必须大于0元！");
			return;
		}
		if(!confirm("是否确认提交调账请求？")){
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addManualRec.do",
			data : {"mer_code": mer_code, "mer_abbreviation":mer_abbreviation, "mer_balance":mer_balance, "addorsub":addorsub, "rec_amount":rec_amount, 
					"requestUser":requestUser, "request_desc":request_desc},
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("手工调账请求成功！");
					$("#submit").css({display:"none"});
				} else {
					alert("手工调账请求失败！");
				}
			}
		});
	}
</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>手工调账请求</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="" target="right" name="" id="" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="90%" border="0" cellspacing="0">
							<tr>
								<td align="right" nowrap="nowrap">商户号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" id="merCode" name="merCode" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
				                     </span>
				                     <font color="red">*</font>
				                </td>
				                <td>
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					            </td>
							</tr>
						</table>
					</center>
				</div>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m" id="submit" style="display: none;">
				<div style="width:100%;">
					<h1 class="pop_tit" style="background-color: #990000">
						<span>手工调账</span>
					</h1>
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td align="right">商户：</td>
							<td id="mer_abbreviation"></td>
							<td style="float: left;margin-left: -650px;">商户状态：</td>
							<td id="mer_status" style="float: left;margin-left: -580px;"></td>
						</tr>
						<tr>
							<td align="right">现有余额（元）：</td>
							<td id="mer_balance"></td>
						</tr>
						<tr>
							<td align="right">操作：</td>
							<td>
		                     	<select id="addorsub" name="addorsub">
									<option value="1">增加</option>
									<option value="2">减少</option>
								</select>
			                </td>
						</tr>
						<tr>
							<td align="right">调整金额（元）：</td>
							<td>
								<input type="text" id="rec_amount" name="rec_amount" onkeyup="amount(this)"/>
								<font color="red">*</font>（必须包含两位小数）
							</td>
						</tr>
						<tr>
							<td align="right">调账原因：</td>
							<td>
								<textarea rows="3" cols="25" style="resize:none;" id="request_desc" name="request_desc"></textarea>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							 <td colspan="2" align="center">
				                <input type="button" class="icon_normal" value="提交请求" onclick="addManualRec('${sessionScope.login.loginName}');" />
				            </td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
