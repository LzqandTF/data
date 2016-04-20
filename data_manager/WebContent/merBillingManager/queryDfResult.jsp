<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>划款结果查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("merAdditionalData");
		var pageSize = $("#pageSize").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期！");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择结束日期！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryDfResultDataLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("merAdditionalData");
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期！");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择结束日期！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryDfResultDataLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var form = document.getElementById("merAdditionalData");
		var pageSize = $("#pageSize").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期！");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择结束日期！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryDfResultDataLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	function getSelected() {
		var df_result = $("#df_result_hidden").val();
		var dfResult = document.getElementById("dfResult");
		for (var i = 0; i < dfResult.options.length; i++) {
			if (dfResult.options[i].value == df_result) {
				dfResult.options[i].selected = 'selected';
			}
		}
		
		var date_type = $("#dateType_hidden").val();
		var dateType = document.getElementById("dateType");
		for (var i = 0; i < dateType.options.length; i++) {
			if (dateType.options[i].value == date_type) {
				dateType.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//设置每页显示条数
	function EnterPress(eve){ //传入 event
		var e = eve || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(10);
			}
		}
	} 
	//分页
	function queryByPage(eve) {
		var e = eve || window.event;
		if (e.keyCode == 13) {
			var pageNum = $("#pageNum").val();
			if (pageNum >= 1) {
				paging(pageNum);
			} else {
				paging(1);
			}
		}
	}
	function clearEndTime(){
		$("#endTime").val("");
	}
	
	//下载结算单  格式Excel
	function downExcelDfResult(){
		var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var merCode = $("#merCode").val();
		var merBatchNo = $("#merBatchNo").val();
		var dfResult = $("#dfResult").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期！");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择结束日期！");
			return;
		}
		var url ="<%=request.getContextPath()%>/downExcelDfResult.do?dateType="+dateType+
		"&startTime="+startTime+"&endTime="+endTime+"&merCode="+merCode+"&dfResult="+dfResult+"&merBatchNo="+merBatchNo;
		window.location=url;
 	}
</script>
</head>

<body onload="getSelected();">
	<jsp:include page="dfResult_title.jsp" ></jsp:include>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>银行账户划款结果查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryDfResultDataLst.do" target="right" name="merAdditionalData" id="merAdditionalData" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table width="90%" border="0" cellspacing="0">
				            <tr>
				            	<td align="right" nowrap="nowrap">
				            		 <span class="input_bgl">
				                     	<select name="dateType" id="dateType">
				                     		<option value="1">结算截止日期</option>
				                     		<option value="2">结算确认日期</option>
				                     	</select>
				                     	<input type="hidden" id="dateType_hidden" value="${dateType }" />
				                     </span>
				            	</td>
				            	<td nowrap="nowrap">
									<span style="width: 160px;" class="input_bgl">
										<input style="width: 80px" id="startTime" name="startTime" value="${param.startTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										- 
										<input style="width: 80px" id="endTime" name="endTime" value="${param.endTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>	
									<font color='red' size="4" style="margin-left: 2px;float:right;">*</font>		
								</td>
								<td align="right" nowrap="nowrap">商户号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merCode" id="merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">商户批次号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merBatchNo" id="merBatchNo" value="${param.merBatchNo }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">划款结果</td>
				               	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="dfResult" id="dfResult" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="3">划款中</option>
				                     		<option value="1">划款成功</option>
				                     		<option value="2">划款失败</option>
				                     	</select>
				                     	<input type="hidden" id="df_result_hidden" value="${df_result }" />
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="下载" onclick="downExcelDfResult();"/>
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
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(merchantFundSettle.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(merchantFundSettle.result) > 0 }">${fn:length(merchantFundSettle.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${merchantFundSettle.totalItems == null }">0</c:if>
					<c:if test="${merchantFundSettle.totalItems != null }">${merchantFundSettle.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${merchantFundSettle.totalPages == null}">0</c:if>
					<c:if test="${merchantFundSettle.totalPages != null}">${merchantFundSettle.totalPages}</c:if>
				</font>页
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>
					每页显示
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					<input type="hidden" id="pageSize_hidden" value="${pageSize }"/>
					条
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</div>
			<div class="table-m" >
				<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;" id="merInfo">
					<table width="1600px;" border="0" cellspacing="0">
						<thead>
							<tr>
								<td align="center">序号</td>
								<td align="center">商户批次号</td>
								<td align="center">系统批次号</td>
								<td align="center">商户号</td>
								<td align="center">商户简称</td>
								<td align="center">结算截止日期</td>
								<td align="center">结算确认日期</td>
								<td align="center">结算银行支行名称</td>
								<td align="center">结算账户名称</td>
								<td align="center">结算账号</td>
								<td align="center">应结算金额</td>
								<td align="center">划款结果</td>
								<td align="center">失败原因</td>
							</tr>
						</thead>
						<c:if test="${fn:length(merchantFundSettle.result)<=0 }">
							<tr align="center">
								<td colspan="13">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${merchantFundSettle.result }" var="merchantFundSettle">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${merchantFundSettle.id}</td>
								<td align="center">${merchantFundSettle.mer_batch_no}</td>
								<td align="center">${merchantFundSettle.sys_batch_no}</td>
								<td align="center">${merchantFundSettle.mer_code}</td>
								<td align="center">${merchantFundSettle.mer_name}</td>
								<td align="center">${merchantFundSettle.end_date}</td>
								<td align="center">${merchantFundSettle.settle_confirm_date}</td>
								<td align="center">${merchantFundSettle.open_bank_name}</td>
								<td align="center">${merchantFundSettle.open_acount_name}</td>
								<td align="center">${merchantFundSettle.open_account_code}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.settle_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<c:if test="${merchantFundSettle.syn_result == 1}">划款成功</c:if>
									<c:if test="${merchantFundSettle.syn_result == 3}">划款中</c:if>
									<c:if test="${merchantFundSettle.syn_result != 1 && merchantFundSettle.syn_result != 3}">划款失败</c:if>
								</td>
								<td align="center">${merchantFundSettle.error_msg}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<!-- 分页 -->
				<c:if test="${merchantFundSettle.totalPages != null}">
					<div class="next">
						<c:if test="${merchantFundSettle.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo > 1}">
							<a href="javascript:paging(${merchantFundSettle.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo-2 > 0}">
							<a href="javascript:paging(${merchantFundSettle.pageNo-2 })"><span>${merchantFundSettle.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo-1 > 0}">
							<a href="javascript:paging(${merchantFundSettle.pageNo-1 })"><span>${merchantFundSettle.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${merchantFundSettle.pageNo }</span></a>
						<c:if test="${merchantFundSettle.pageNo+1 <= merchantFundSettle.totalPages}">
							<a href="javascript:paging(${merchantFundSettle.pageNo+1 })"><span>${merchantFundSettle.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo+2 <= merchantFundSettle.totalPages}">
							<a href="javascript:paging(${merchantFundSettle.pageNo+2 })"><span>${merchantFundSettle.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo+3 <= merchantFundSettle.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo < merchantFundSettle.totalPages}">
							<a href="javascript:paging(${merchantFundSettle.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${merchantFundSettle.pageNo > 1}">
							<a href="javascript:paging(${merchantFundSettle.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${merchantFundSettle.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${merchantFundSettle.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>