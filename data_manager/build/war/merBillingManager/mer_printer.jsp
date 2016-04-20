<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算单打印</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<style>
	body{
		background-color: white;
	}
	th{
		white-space: nowrap;
	}
	td{
		padding-top: 5px;
		padding-bottom: 5px;
		white-space: nowrap;
	}
</style>

<script type="text/javascript">
	function printPage(){
		hide("des");
		hide("operate");
		window.print();
		window.close();
	}
	
	function printSetup(){
		wb.execwb(8,1); 
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	function hideTop() {
		hide("des");
		hide("operate");
	}
</script>

</head>

<body>
	<div id="des">
		* 注意，如果您使用的IE浏览器且第一次在本机使用打印功能，请参照如下设置<br/>
	     &nbsp; 1.工具-Internet选项-安全-可信任站点-站点-添加。(将该站点添加到可信任列表)<br/>
	     &nbsp; 2.工具-Internet选项-安全-可信任站点，将该区域的安全级别设置为“低”，并保存。<br/>
	     &nbsp; 3.点击打印设置，将“启用缩小字体填充”勾上，“页眉”以及“页脚”下的选项建议全部选择空。
	</div>
	<div id="operate">
		<br/>
		<object id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name="wb"></object>
		<input type="button" class="icon_normal" value="打印" onclick="printPage();" />
		<input type="button" class="icon_normal" value="打印设置" onclick="printSetup();" />
		<hr/>
		<div align="right">
			<span onclick="hideTop();" style="cursor:pointer;">&uarr;隐藏以上区域</span>
		</div>
	</div>
	<div align="center">
		<c:if test="${settle_object == 1 || settle_object == 3 }">
			<b style="font-size:35px ">商户资金结算表-银行账户</b>
		</c:if>
		<c:if test="${settle_object == 2 || settle_object == 4 }">
			<b style="font-size:35px ">商户资金结算表-电银账户</b>
		</c:if>
	</div>
	<br/>
	<table>
		<tr>
			<td colspan="21">
				<table border="thin" cellspacing="0" >
					<thead>
						<tr>
							<th>序号</th>
							<th>商户号</th>
							<th>商户简称</th>
							<th>商户类别</th>
							<th>是否资金分账</th>
							<th>结算类型</th>
							<th>结算初始日期</th>
							<th>结算截止日期</th>
							<th>制表日期</th>
							<th>支付金额</th>
							<th>支付笔数</th>
							<th>退款金额</th>
							<th>退款笔数</th>
							<th>手工调增金额</th>
							<th>手工调增笔数</th>
							<th>手工调减金额</th>
							<th>手工调减笔数</th>
							<th>商户手续费</th>
							<th>退回商户续费</th>
							<th>结算银行支行名称</th>
							<th>结算账户名称</th>
							<th>结算账号</th>
							<th>应结算金额</th>
						</tr>
					</thead>
		    		<tbody id="TableOne" style="text-align: center;">
		    			<c:forEach items="${merchantFundSettleList }" var="merchantFundSettle">
							<tr>
								<td align="center">${merchantFundSettle.id}</td>
								<td align="center">${merchantFundSettle.mer_code}</td>
								<td align="center">${merchantFundSettle.mer_name}</td>
								<td align="center">
									<c:if test="${merchantFundSettle.mer_type == 0}">RYF商户</c:if>
									<c:if test="${merchantFundSettle.mer_type == 1}">VAS商户</c:if>
									<c:if test="${merchantFundSettle.mer_type == 2}">POS商户</c:if>
								</td>
								<td align="center">
									<c:if test="${merchantFundSettle.whtherFz == 0}">否</c:if>
									<c:if test="${merchantFundSettle.whtherFz == 1}">是</c:if>
								</td>
								<td align="center">
									<c:if test="${merchantFundSettle.settle_type == 1 }">全额结算</c:if>
									<c:if test="${merchantFundSettle.settle_type == 2 }">净额结算</c:if>
								</td>
								<td align="center">${merchantFundSettle.start_date}</td>
								<td align="center">${merchantFundSettle.end_date}</td>
								<td align="center">${merchantFundSettle.create_tab_date}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.trade_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.trade_count}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.refund_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.refund_count}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.rec_amount_add }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.rec_amount_add_count }</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.rec_amount_sub }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.rec_amount_sub_count }</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.mer_fee}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.refund_mer_fee}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.open_bank_name}</td>
								<td align="center">${merchantFundSettle.open_acount_name}</td>
								<td align="center">${merchantFundSettle.open_account_code}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.settle_amount}" pattern="0.00"></f:formatNumber>
								</td>
							</tr>
						</c:forEach>
		    		</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="21"><br/></td>
		</tr>
		<tr style="font-size: large;" >
			<td colspan="2" width="9.4%" align="left">制 表:</td>
			<td></td>
			<td colspan="2" width="9.4%" align="right">复核:</td>
			<td></td>
		    <td colspan="2" align="right" width="9.4%">结算主管:</td>
		    <td></td>
		    <td colspan="2" align="right" width="9.4%">划款制单:</td>
		    <td></td>
		    <td colspan="2" align="right" width="9.4%">划款复核:</td>
		    <td></td>
		    <td colspan="2" align="right" width="9.4%">划款时间:</td>
		    <td></td>
		    <td align="right"  colspan="3" width="14.1%">
		    	总计:&nbsp;&nbsp;
		    	<span>${totalMoney }</span>&nbsp;元
		    </td>
		</tr>
	</table>
</body>
</html>
