<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户信息配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function checkQuery() {
		var merCode = $("#merCode").val();
		if (merCode == "" || merCode == null) {
			alert("商户号不能为空！");
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryAllMerInfoByMerCode.do',
			data : {"merCode": merCode},
			async:false,
			success : function(merInfo) {
				if(merInfo != null){
					if (merInfo.mer_code != null) {
						var mer_category = merInfo.mer_category;
						if (mer_category == 0) {
							mer_category = "RYF商户";
						} else if (mer_category == 1) {
							mer_category = "VAS商户";
						} else {
							mer_category = "POS商户";
						}
						$("#mer_category").html(mer_category);
						$("#mer_name").html(merInfo.mer_name);
						$("#mer_abbreviation").html(merInfo.mer_abbreviation);
						$("#mer_code").html(merInfo.mer_code);
						var mer_state = merInfo.mer_state;
						if (mer_state == 5) {
							mer_state = "正常";
						} else {
							mer_state = "关闭";
						}
						$("#mer_state").html(mer_state);
						$("#provinces").html(merInfo.provinces + merInfo.city);
						
						var mer_type = merInfo.mer_type;
						if (mer_type == 1) {
							mer_type = "企业";
						} else if (mer_type == 2) {
							mer_type = "个人";
						} else {
							mer_type = "集团";
						} 
						$("#mer_type").html(mer_type);
						$("#startDate").html(merInfo.startDate);
						$("#endDate").val(merInfo.endDate);
						$("#expand").html(merInfo.expand);
						
						var bil_type = merInfo.bil_type;
						if (bil_type == 1) {
							bil_type = "银行账户";
						} else {
							bil_type = "电银账户";
						}
						$("#bil_type").html(bil_type);
						$("#bil_bank").html(merInfo.bil_bank);
						$("#bank_branch").html(merInfo.bank_branch);
						$("#bil_accountname").html(merInfo.bil_accountname);
						$("#bil_bankaccount").html(merInfo.bil_bankaccount);
						$("#bil_account").html(merInfo.bil_account);
						
						// 是否自动结算
						var bil_manual_ = merInfo.bil_manual;
						var bil_manual = document.getElementById("bil_manual");
						for (var i = 0; i < bil_manual.length; i++) {
							if (bil_manual.options[i].value == bil_manual_) {
								bil_manual.options[i].selected = 'selected';
							}
						}
						
						var refund_fee = merInfo.refund_fee;
						if (refund_fee == 0) {
							refund_fee = "不退还";
						} else {
							refund_fee = "退还";
						}
						$("#refund_fee").html(refund_fee);
						
						var bil_status_ = merInfo.bil_status;
						var bil_status = document.getElementById("bil_status");
						for (var i = 0; i < bil_status.length; i++) {
							if (bil_status.options[i].value == bil_status_) {
								bil_status.options[i].selected = 'selected';
							}
						}
						
						var bil_way = merInfo.bil_way;
						if (bil_way == 1) {
							bil_way = "全额";
						} else {
							bil_way = "净额";
						}
						$("#bil_way").html(bil_way);
						var bil_cycle_ = merInfo.bil_cycle;
						var bil_cycle = document.getElementById("bil_cycle");
						for (var i = 0; i < bil_cycle.length; i++) {
							if (bil_cycle.options[i].value == bil_cycle_) {
								bil_cycle.options[i].selected = 'selected';
							}
						}
						
						$("#bil_smallamt").val(merInfo.bil_smallamt);
						var whtherFz_ = merInfo.whtherFz;
						var whtherFz = document.getElementById("whtherFz");
						for (var i = 0; i < whtherFz.length; i++) {
							if (whtherFz.options[i].value == whtherFz_) {
								whtherFz.options[i].selected = 'selected';
							}
						}
						$("#pop").css({display:"block"});
					} else {
						alert("该商户号不存在！");
						$("#pop").css({display:"none"});
					}
				}else{
					alert("该商户不存在！");
				}
			}
		});
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
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
	
	// 修改商户信息操作
	function updateMerInfoByMerCode() {
		var mer_code = $("#mer_code").text();
		var endDate = $("#endDate").val();
		var bil_manual = $("#bil_manual").val();
		var bil_status = $("#bil_status").val();
		var bil_cycle = $("#bil_cycle").val();
		var bil_smallamt = $("#bil_smallamt").val();
		var whtherFz = $("#whtherFz").val();
		if (bil_smallamt == "" || bil_smallamt == null) {
			alert("结算最少金额不能为空！");
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateMerInfoByMerCode.do",
			data : {'mer_code': mer_code, 'endDate': endDate, 'bil_manual':bil_manual,'bil_status' : bil_status,'bil_cycle':bil_cycle,'bil_smallamt':bil_smallamt, 'whtherFz':whtherFz},
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功！");
					$("#pop").css({display:"none"});
				} else {
					alert("操作失败！");
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
				当前位置：<a href="javascript:void(0)">商户管理</a>&gt;<span>商户信息配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryAllMerInfoByMerCode.do" target="right" name="searchMerInfo" id="searchMerInfo" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">商户号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="merCode" id="merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d]/g,'')" />
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
   		</div>
   		
   		<!-- 配置弹出框 -->
   		<div id="pop" class="pop" style="display: none; font-size: 11px;">
			<div class="pop_body" style="margin-top: 10px;">
				<h1 class="pop_tit">
					<span class="fl">商户信息配置</span> <a class="close"
						href="javascript:void(0);" onclick="hide('pop')">&nbsp;</a>
				</h1>
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td align="center" bgcolor="#eeeeee" colspan="6">商户基本信息</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户类别：</td>
							<td id="mer_category"></td>
							<td align="right" bgcolor="#eeeeee">商户名称：</td>
							<td id="mer_name"></td>
							<td align="right" bgcolor="#eeeeee">商户简称：</td>
							<td id="mer_abbreviation"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">电银商户号：</td>
							<td id="mer_code"></td>
							<td align="right" bgcolor="#eeeeee">商户状态：</td>
							<td id="mer_state"></td>
							<td align="right" bgcolor="#eeeeee">所在省市：</td>
							<td id="provinces"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户类型：</td>
							<td id="mer_type"></td>
							<td align="right" bgcolor="#eeeeee">合同起始日期：</td>
							<td id="startDate"></td>
							<td align="right" bgcolor="#eeeeee">合同结束日期：</td>
							<td>
				                  <span class="input_bgl">
								  	<input name="endDate" id="endDate" value="${param.endDate }" 
								  		maxlength="10" readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" />
								  </span>
								 <font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户拓展方：</td>
							<td id="expand"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#eeeeee" colspan="6">商户结算信息</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">结算账户类型：</td>
							<td id="bil_type"></td>
							<td align="right" bgcolor="#eeeeee">结算银行联行号：</td>
							<td id="bil_bank"></td>
							<td align="right" bgcolor="#eeeeee">结算银行支行名称：</td>
							<td id="bank_branch"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">结算账户名称：</td>
							<td id="bil_accountname"></td>
							<td align="right" bgcolor="#eeeeee">结算银行账号：</td>
							<td id="bil_bankaccount"></td>
							<td align="right" bgcolor="#eeeeee">结算电银账号：</td>
							<td id="bil_account"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">是否自动结算：</td>
							<td>
								<span class="input_bgl">
					                <select id="bil_manual" name="bil_manual">
										<option value="1">否</option>
										<option value="2">是</option>
									</select>
			                    </span>
							</td>
							<td align="right" bgcolor="#eeeeee">退款时是否退还手续费：</td>
							<td id="refund_fee"></td>
							<td align="right" bgcolor="#eeeeee">结算状态：</td>
							<td>
								<span class="input_bgl">
					                <select id="bil_status" name="bil_status">
										<option value="1">正常</option>
										<option value="2">冻结</option>
									</select>
			                    </span>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">结算方式：</td>
							<td id="bil_way"></td>
							<td align="right" bgcolor="#eeeeee">结算周期：</td>
							<td>
								<span class="input_bgl">
					                <select id="bil_cycle" name="bil_cycle">
										<option value="1">T+1</option>
										<option value="2">周结</option>
										<option value="3">月结</option>
									</select>
			                    </span>
							</td>
							<td align="right" bgcolor="#eeeeee">结算最少金额(元)：</td>
							<td>
								<input type="text" id="bil_smallamt" name="bil_smallamt" onkeyup="amount(this)"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">是否资金分账：</td>
							<td>
								<span class="input_bgl">
					                <select id="whtherFz" name="whtherFz">
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
			                    </span>
							</td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
						</tr>
						<tr>
							<td align="center" bgcolor="#eeeeee" colspan="6">手续费信息</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户签约手续费：</td>
							<td></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
							<td bgcolor="#eeeeee"></td>
						</tr>
						<tr>
							<td align="center" colspan="6">
						    	<input type="button" class="icon_normal" value="确认" onclick="updateMerInfoByMerCode()" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
   		
  	</div>
</body>
</html>
