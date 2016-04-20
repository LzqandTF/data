<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>差错对账结果查询</title>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function initChannelSelect() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#inst_name").append('<option value="' + msg[i]['instId'] + ',' + msg[i]['error_original_data_tableName']+','+msg[i]['error_dz_data_tableName'] + ',' + msg[i]['inst_type'] +'">'+ msg[i]['name'] + '</option>');
			},
			error : function(msg) {
				alert("获取渠道列表失败!");
			}
		});
		var table_name = $("#table_name_hidden").val();
		var inst_name = document.getElementById("inst_name");
		for(var i = 0;i<inst_name.options.length;i++){
			if(inst_name.options[i].value == table_name){
				inst_name.options[i].selected = 'selected';
			}
		}
	}
	
	function clearForm(){
		$("#trade_date").val("");
		$("#deduct_stlm_date").val("");
		$("#inst_name").val("");
	}
	
	function checkQuery(){
		var trade_date = $("#trade_date").val();
		var deductSysTime = $("#deduct_stlm_date").val();
		var instName = $("#inst_name").val();
		if ((trade_date == null || trade_date == "") && (deductSysTime == null || deductSysTime == "")) {
			alert("请选择交易日期或清算日期！");
			return;
		}
		if ((deductSysTime != null && deductSysTime != "") && (trade_date != null && trade_date != "")) {
			alert("只能选择一个日期！");
			return;
		}
		if (instName == null || instName == "") {
			alert("请选择扣款渠道！");
			return;
		}
		$.ajax({
			url : '<%=request.getContextPath()%>/queryErrorDuizhangResult.do',
			post : 'post',
			data : {"trade_date": trade_date, "deduct_stlm_date":deductSysTime,"inst_name":instName},
			async : false,
    		dataType:'text',
    		success : function(data) {
    			if (data != null) {
    				var obj = eval("("+data+")"); 
    				var tbody = document.getElementById("tbody");
    				while(tbody.firstChild){
    					tbody.removeChild(tbody.firstChild);
    				}
    				var tr = tbody.insertRow(0);
    				//清算时间
    				var td0 = tr.insertCell(0);
    				td0.setAttribute("align", "center");
    				td0.innerHTML = obj[0].original_tradeTime;
    				//扣款渠道
    				var td1 = tr.insertCell(1);
    				td1.setAttribute("align", "center");
    				td1.innerHTML = obj[0].infoName;
    				//电银内部交易笔数
    				var td2 = tr.insertCell(2);
    				td2.setAttribute("align", "center");
    				td2.innerHTML = obj[0].original_trade_count;
    				//渠道对账笔数
    				var td3 = tr.insertCell(3);
    				td3.setAttribute("align", "center");
    				td3.innerHTML = obj[0].error_channel_count;
    				//对账成功笔数
    				var td4 = tr.insertCell(4);
    				td4.setAttribute("align", "center");
    				td4.innerHTML = obj[0].qingkuan_count;
    				//差错笔数
    				var td5 = tr.insertCell(5);
    				td5.setAttribute("align", "center");
    				td5.innerHTML = obj[0].daiji_count;
    				//未对帐笔数
    				var td6 = tr.insertCell(6);
    				td6.setAttribute("align", "center");
    				td6.innerHTML = obj[0].tuihuo_count;
    				//无需对账笔数
    				var td7 = tr.insertCell(7);
    				td7.setAttribute("align", "center");
    				td7.innerHTML = obj[0].again_qingkuan_count;
    				var td8 = tr.insertCell(8);
    				td8.setAttribute("align", "center");
    				td8.innerHTML = obj[0].twice_duidan_count;
    				var td9 = tr.insertCell(9);
    				td9.setAttribute("align", "center");
    				td9.innerHTML = obj[0].exception_changkuan_count;
    			}
    		},
    		error : function(data) {
    			alert("差错对账结果统计异常!");
    		}
		});
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	
</script>
</head>

<body onload="initChannelSelect();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>差错对账结果查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="" target="right" name="errorResultSearch" id="errorResultSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<table align="center" width="80%" border="0" cellspacing="0">
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
				               <td align="right" nowrap="nowrap">扣款渠道</td>
				               <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="inst_name" name="inst_name" style="width: 150px;">
											<option value="">--请选择扣款渠道--</option>
										</select>
										<input type="hidden" id="table_name_hidden" value="${table_name }"/>
				                     </span><font color="red">&nbsp;*</font>
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
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">清算日期</td>
							<td align="center">扣款渠道</td>
							<td align="center">电银差错总笔数</td>
							<td align="center">渠道差错总笔数</td>
							<td align="center">请款笔数</td>
							<td align="center">贷记调整笔数</td>
							<td align="center">退单/退货笔数</td>
							<td align="center">再次请款笔数</td>
							<td align="center">二次退单笔数</td>
							<td align="center">例外长款笔数</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<tr align="center">
							<td colspan="10">对不起，暂无数据！</td>
						</tr>
					</tbody>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span>
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
		</div>
	</div>
</body>
</html>
