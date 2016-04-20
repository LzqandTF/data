<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账结果查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

<style type="text/css">
	#tbody tr td{
		text-align: center;
	}
</style>
<script type="text/javascript">
	//初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['dz_data_tableName'] + ',' + msg[i]['original_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
			}
		});
		
		var bankId = $("#bankId_hidden").val();
		var bank_id = document.getElementById("bank_id");
		for (var i = 0; i < bank_id.length; i++) {
			if (bank_id.options[i].value == bankId) {
				bank_id.options[i].selected = 'selected';
			}
		}
		
		if (bankId != "") {
			getInstInfoByBankId(bankId);
		}
	}
	
	// 根据银行机构获取渠道信息
	function getInstInfoByBankId(bankInst) {
		if (bankInst == null || bankInst == "") {
			alert("请选择银行机构！");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/getInstInfoByBankId.do',
    		type : 'post',
    		data : 'bankInst='+bankInst,
    		async : false,
    		dataType : 'text',
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("channel");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				if (data.length == 0 || data.length > 1) {
					$(selectObj).append("<option value=''>全部</option>");
				}
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].instId + ',' + data[i].inst_type +">"+data[i].name+"</option>");
				}
    		}
    	});
	}
	
	
	function initChannel() {
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("channel");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == inst_id){
				type.options[i].selected = 'selected';
			}
		}
	}
	
	
	function clearForm(){
		$("#trade_date").val("");
		$("#deduct_stlm_date").val("");
		$("#bank_id").val("");
		var selectObj = document.getElementById("channel");
		while(selectObj.firstChild) {
			selectObj.removeChild(selectObj.firstChild);
		}
		$(selectObj).append("<option value=''>全部</option>");
	}
	
	function checkQuery(){
		var trade_date = $("#trade_date").val();
		var deductSysTime = $("#deduct_stlm_date").val();
		var bank_id = $("#bank_id").val();
		var channel = $("#channel").val();
		
	 	if ((trade_date == null || trade_date == "") && (deductSysTime == null || deductSysTime == "")) {
			alert("请选择交易日期或清算日期！");
			return;
		}
		if ((deductSysTime != null && deductSysTime != "") && (trade_date != null && trade_date != "")) {
			alert("只能选择一个日期！");
			return;
		}
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryDuizhangResult.do',
    		type : 'post',
    		data : {"trade_date": trade_date, "deduct_stlm_date":deductSysTime,"channel":channel, "bank_id":bank_id},
    		async : false,
    		dataType:'text',
    		success : function(json) {
    			var data = eval("("+json+")");
    			var tbody = document.getElementById("tbody");
				while(tbody.firstChild){
					tbody.removeChild(tbody.firstChild);
				}
				for (var i = 0; i < data.length; i++) {
    				var tr = tbody.insertRow(0);
    				//清算时间
    				var td0 = tr.insertCell(0);
    				td0.innerHTML = data[i]['original_tradeTime'];
    				//扣款渠道
    				var td1 = tr.insertCell(1);
    				td1.innerHTML = data[i].infoName;
    				//电银内部交易笔数
    				var td2 = tr.insertCell(2);
    				td2.innerHTML = data[i].original_trade_count;
    				//渠道对账笔数
    				var td3 = tr.insertCell(3);
    				td3.innerHTML = data[i].duizhang_trade_count;
    				//对账成功笔数
    				var td4 = tr.insertCell(4);
    				td4.innerHTML = data[i].original_dz_success;
    				//差错笔数
    				var td5 = tr.insertCell(5);
    				td5.innerHTML = data[i].original_dz_error;
    				//未对帐笔数
    				var td6 = tr.insertCell(6);
    				td6.innerHTML = data[i].original_dz_no;
    				//无需对账笔数
    				var td7 = tr.insertCell(7);
    				td7.innerHTML = data[i].original_dz_noNeed;
				}
    		},
    		error : function(data) {
    			alert("对账结果统计异常!");
    		}
    	});
		<%-- $.ajax({
    		url : '<%=request.getContextPath()%>/queryDuizhangMoney.do',
    		type : 'post',
    		data : {"trade_date": trade_date, "deduct_stlm_date":deductSysTime,"channel":channel, "bank_id":bank_id},
    		async : false,
    		success : function(data) {
    			if (data != null) {
    				var obj = eval("("+data+")"); 
					for(var i=0;i<obj.length;i++){
						$("#dzSuccessMoney").html(obj[i]["dzSuccessMoney"]);
						$("#dzFailMoney").html(obj[i]["dzFailMoney"]);
					 }
    			}
    		},
    		error : function(data) {
    			alert("对账金额统计异常!");
    		}
    	}); --%>
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}	 
</script>
</head>

<body onload="initBankInst(); initChannel();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>对账结果查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="" target="right" name="duizhangResultSearch" id="duizhangResultSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<table align="center" width="90%" border="0" cellspacing="0">	
				            <tr>
				            	<td align="right" nowrap="nowrap">交易日期</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
								  	<input name="trade_date" id="trade_date" value="${param.trade_date }" 
								  		maxlength="10" readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								  </span><font color="red">&nbsp;*</font>
				                </td>
				            	<td align="right" nowrap="nowrap">清算日期</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
								  	<input name="deduct_stlm_date" id="deduct_stlm_date" value="${param.deduct_stlm_date }" 
								  		maxlength="10" readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								  </span><font color="red">&nbsp;*</font>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">银行机构</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="bank_id" name="bank_id" style="width: 150px;" onchange="getInstInfoByBankId(this.value);">
											<option value="">--请选择银行机构--</option>
										</select>
										<input type="hidden" id="bankId_hidden" value="${bankId }"/>
				                     </span>
				                     <font color="red">*</font>
				                </td>
				                <td align="right" nowrap="nowrap">扣款渠道</td>
				               	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="channel" name="channel" style="width: 150px;">
											<option value="">全部</option>
										</select>
										<input type="hidden" id="table_name_hidden" value="${table_name }"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="重置" onclick="clearForm();" />
					            </td>
				            </tr>
				        </table>
					</div>		
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			
			<!-- <div style="font-size: 12px;">
				<span>
					对账成功金额：
					<font color="red">
						<span id="dzSuccessMoney">0.00</span>
					</font>
					元
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>
					对账失败金额：
					<font color="red">
						<span id="dzFailMoney">0.00</span>
					</font>
					元
				</span>
			</div> -->
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">清算日期</td>
							<td align="center">扣款渠道</td>
							<td align="center">电银内部交易笔数</td>
							<td align="center">渠道对账笔数</td>
							<td align="center">对账成功笔数</td>
							<td align="center">差错笔数</td>
							<td align="center">未对账笔数</td>
							<td align="center">无需对账笔数</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<tr>
							<td colspan="8">对不起，暂无数据！</td>
						</tr>
					</tbody>
				</table>
		</div>
	</div>
	</div>
</body>
</html>
