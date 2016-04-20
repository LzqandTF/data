<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户结算单查询</title>
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
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAdditionalData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
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
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAdditionalData.do?pageSize=" + pageSize;
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
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAdditionalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
		history.go(-1);
	}
	function getSelected() {
		var settle_object = $("#settle_object_hidden").val();
		var settleObject = document.getElementById("settleObject");
		for (var i = 0; i < settleObject.options.length; i++) {
			if (settleObject.options[i].value == settle_object) {
				settleObject.options[i].selected = 'selected';
			}
		}
		var mer_type = $("#mer_type_hidden").val();
		var merType = document.getElementById("merType");
		for (var i = 0; i < merType.options.length; i++) {
			if (merType.options[i].value == mer_type) {
				merType.options[i].selected = 'selected';
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
			page_size = 30;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//结算单打印
	function printPage(){
		var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var settleObject = $("#settleObject").val();
		var merType = $("#merType").val();
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		var openAcountName = $("#openAcountName").val();
		var merBatchNo = $("#merBatchNo").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		var url ="<%=request.getContextPath()%>/getMerPinterPage.do?dateType="+dateType+
				"&startTime="+startTime+"&endTime="+endTime+"&settleObject="+settleObject+
				"&merType="+merType+"&merCode="+merCode+"&merName="+merName+
				"&openAcountName="+openAcountName+"&merBatchNo="+merBatchNo;
		window.open(url,'XX',' left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',scrollbars,resizable=yes,toolbar=no');
 	}
	
	//设置每页显示条数
	function EnterPress(eve){ //传入 event
		var e = eve || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(30);
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
	function downMerDataExcel(){
		var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var settleObject = $("#settleObject").val();
		var merType = $("#merType").val();
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		var openAcountName = $("#openAcountName").val();
		var merBatchNo = $("#merBatchNo").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		/* if(settleObject == 1 || settleObject == 3){ */
			var url ="<%=request.getContextPath()%>/downloadMerSettleDataListOfBankAccount.do?dateType="+dateType+
			"&startTime="+startTime+"&endTime="+endTime+"&settleObject="+settleObject+
			"&merType="+merType+"&merCode="+merCode+"&merName="+merName+
			"&openAcountName="+openAcountName+"&merBatchNo="+merBatchNo;
			window.location=url;
		<%-- }else if(settleObject == 2 || settleObject == 4){
			var url ="<%=request.getContextPath()%>/downloadMerSettleDataListOfDyAccount.do?dateType="+dateType+
			"&startTime="+startTime+"&endTime="+endTime+"&settleObject="+settleObject+
			"&merType="+merType+"&merCode="+merCode+"&merName="+merName+
			"&openAcountName="+openAcountName+"&merBatchNo="+merBatchNo;
			window.location=url;
		} --%>
 	}
	
	//商户结算单下载  格式.txt
	function downMerDataTxt(){
		var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var settleObject = $("#settleObject").val();
		var merType = $("#merType").val();
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		var openAcountName = $("#openAcountName").val();
		var merBatchNo = $("#merBatchNo").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 30*24*60*60*1000*3){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		/* if(settleObject == 1 || settleObject == 3){ */
			var url ="<%=request.getContextPath()%>/downMerDataListOfBankAccountTxt.do?dateType="+dateType+
			"&startTime="+startTime+"&endTime="+endTime+"&settleObject="+settleObject+
			"&merType="+merType+"&merCode="+merCode+"&merName="+merName+
			"&openAcountName="+openAcountName+"&merBatchNo="+merBatchNo;
			window.location=url;
		<%-- }else if(settleObject == 2 || settleObject == 4){
			var url ="<%=request.getContextPath()%>/downMerDataListOfDyAcountTxt.do?dateType="+dateType+
			"&startTime="+startTime+"&endTime="+endTime+"&settleObject="+settleObject+
			"&merType="+merType+"&merCode="+merCode+"&merName="+merName+
			"&openAcountName="+openAcountName+"&merBatchNo="+merBatchNo;
			window.location=url;
		} --%>
 	}
	
	//子复选框的事件  
	function setSelectAll(){  
	    var chsub = $("input[type='checkbox'][name='subcheck']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll").attr("checked", true);  
	    }else{
	    	$("#SelectAll").attr("checked", false);
	    }
	}
	
	//全选、取消全选的事件  
	function selectAll(){  
	    if ($("#SelectAll").attr("checked")) {  
	        $(":checkbox").attr("checked", true);  
	    } else {  
	        $(":checkbox").attr("checked", false);  
	    }  
	} 
	
	function queryDetail(merCode,merType,startDate,endDate,merName){
		$("#pop1").css({display:"block"});
		$("#mer_code_hide").val(merCode);
		$("#mer_type_hide").val(merType);
		$("#start_date_hide").val(startDate);
		$("#end_dates_hide").val(endDate);
		$("#mer_name_hide").val(merName);
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryMerSettleDataDetailList.do",
			data : {'merCode':merCode,'merType' : merType,'startDate':startDate,'endDate':endDate, 'merName':merName},
			async:false,
			success : function(msg) {
				$("#selectSettlementDetailed").empty();
				$("#selectSettlementDetailed").append(msg);
			}
		}); 
	}
	
	//商户结算单明细下载
	function downExcel() {
	   	var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择您要下载的结算单明细！");
  	   		return ;
  	 	}
  	  	var merInfo = new Array();
  	  	var m = 0 ;
  	  	$("input[type='checkbox'][name='subcheck']:checked").each(function(){
  	  		merInfo[m] = $(this).val();
   	   		m++;
  	  	});
		var url ="<%=request.getContextPath()%>/downMerSettleDetailToExcel.do?merInfo="+merInfo;
		window.location=url;
	}
	
	//分页查询 明细
	function pagingInfo(pageNo) {
		var merCode=$("#mer_code_hide").val();
		var merType=$("#mer_type_hide").val();
		var startDate=$("#start_date_hide").val();
		var endDate=$("#end_dates_hide").val();
		var merName=$("#mer_name_hide").val();
		 $.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryMerSettleDataDetailList.do",
			data : {'pageNum' : pageNo,'merCode' : merCode,'merType' : merType,'startDate' : startDate,'endDate' : endDate,'merName':merName},
			async:false,
			success : function(msg) {
				$("#selectSettlementDetailed").empty();
				$("#selectSettlementDetailed").append(msg);
			}
		}); 
	}
	
	function queryByPageNum(eve) {
		var e = eve || window.event;
		if (e.keyCode == 13) {
			var pageNum = $("#pageNum").val();
			if (pageNum >= 1) {
				pagingInfo(pageNum);
			} else {
				pagingInfo(1);
			}
		}
	}
</script>
</head>

<body onload="getSelected();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>商户结算单查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerAdditionalData.do" target="right" name="merAdditionalData" id="merAdditionalData" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table width="90%" border="0" cellspacing="0">
				            <tr>
				            	<td align="right" nowrap="nowrap">
				            		 <span class="input_bgl">
				                     	<select name="dateType" id="dateType">
				                     		<option value="3">结算截止日期</option>
				                     		<option value="2">结算制表日期</option>
				                     		<option value="1">结算确认日期</option>
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
				                <td align="right" nowrap="nowrap">结算对象</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="settleObject" id="settleObject" style="width: 150px;">
				                     		<option value="1">手工结算-银行账户</option>
				                     		<option value="2">手工结算-电银账户</option>
				                     		<option value="3">自动结算-银行账户</option>
				                     		<option value="4">自动结算-电银账户</option>
				                     	</select>
				                     	<input type="hidden" id="settle_object_hidden" value="${settle_object }" />
				                     </span>
				                </td>
				               <td align="right" nowrap="nowrap">商户类型</td>
				               <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="merType" id="merType" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="0">RYF商户</option>
				                     		<option value="1">VAS商户</option>
				                     		<option value="2">POS商户</option>
				                     	</select>
				                     	<input type="hidden" id="mer_type_hidden" value="${mer_type }" />
				                     </span>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">商户号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merCode" id="merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">商户简称</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merName" id="merName" value="${param.merName }"/>
				                     </span>
				                </td>
				               <td align="right" nowrap="nowrap">结算账户名称</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="openAcountName" id="openAcountName" value="${param.openAcountName }"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
				                <td align="right" nowrap="nowrap">商户批次号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merBatchNo" id="merBatchNo" value="${param.merBatchNo }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="打印" onclick="printPage();"></input>
					                <input type="button" class="icon_normal" value="下载结算单xls" onclick="downMerDataExcel();"/>
					                <input type="button" class="icon_normal" value="下载结算单txt" onclick="downMerDataTxt();"/>
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
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="30" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					<input type="hidden" id="pageSize_hidden" value="${pageSize }"/>
					条
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<br />
				<span>
					结算总金额：
					<font color="red">
						<span>
							<c:if test="${fn:length(merchantFundSettle.result)<=0 }">
								0.00
							</c:if>
							<c:if test="${fn:length(merchantFundSettle.result)>0 }">
								<f:formatNumber value="${merchantFundSettle.result[0].totalMoney }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元
				</span>
			</div>
			<div class="table-m" >
				<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;" id="merInfo">
					<table width="2200px;" border="0" cellspacing="0">
						<thead>
							<tr>
								<td width="5%" align="center">
									<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
								</td>
								<td align="center">商户批次号</td>
								<td align="center">系统批次号</td>
								<td align="center">商户号</td>
								<td align="center">商户简称</td>
								<td align="center">是否资金分账</td>
								<td align="center">商户类别</td>
								<td align="center">结算起始日期</td>
								<td align="center">结算截止日期</td>
								<td align="center">制表日期</td>
								<td align="center">结算账户名称</td>
								<td align="center">结算账号</td>
								<td align="center">支付金额</td>
								<td align="center">支付笔数</td>
								<td align="center">退款金额</td>
								<td align="center">退款笔数</td>
								<td align="center">商户手续费</td>
								<td align="center">退回商户手续费</td>
								<td align="center">手工调增金额</td>
								<td align="center">手工调增笔数</td>
								<td align="center">手工调减金额</td>
								<td align="center">手工调减笔数</td>
								<td align="center">应结算金额</td>
								<td align="center">确认日期</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:if test="${fn:length(merchantFundSettle.result)<=0 }">
							<tr align="center">
								<td colspan="14">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${merchantFundSettle.result }" var="merchantFundSettle">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">
									<input type="checkbox" name="subcheck" id="subcheck" onclick="setSelectAll();" value="${merchantFundSettle.mer_code},${merchantFundSettle.start_date },${merchantFundSettle.end_date},${merchantFundSettle.mer_name},${merchantFundSettle.id}"/>
								</td>
								<td align="center">${merchantFundSettle.mer_batch_no}</td>
								<td align="center">${merchantFundSettle.sys_batch_no}</td>
								<td align="center">${merchantFundSettle.mer_code}</td>
								<td align="center">${merchantFundSettle.mer_name}</td>
								<td align="center">
									<c:if test="${merchantFundSettle.whtherFz == 0}">否</c:if>
									<c:if test="${merchantFundSettle.whtherFz == 1}">是</c:if>
								</td>
								<td align="center">
									<c:if test="${merchantFundSettle.mer_type  == 0 }">RYF商户</c:if>
									<c:if test="${merchantFundSettle.mer_type  == 1 }">VAS商户</c:if>
									<c:if test="${merchantFundSettle.mer_type  == 2 }">POS商户</c:if>
								</td>
								<td align="center">${merchantFundSettle.start_date}</td>
								<td align="center">${merchantFundSettle.end_date}</td>
								<td align="center">${merchantFundSettle.create_tab_date}</td>
								<td align="center">${merchantFundSettle.open_acount_name}</td>
								<td align="center">${merchantFundSettle.open_account_code}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.trade_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.trade_count}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.refund_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.refund_count}</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.mer_fee}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.refund_mer_fee}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.rec_amount_add }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.rec_amount_add_count }</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.rec_amount_sub }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.rec_amount_sub_count }</td>
								<td align="center">
									<f:formatNumber value="${merchantFundSettle.settle_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${merchantFundSettle.settle_confirm_date }</td>
								<td>
									<a class="fl lj mr10" href="#" onclick="queryDetail('${merchantFundSettle.mer_code}','${merchantFundSettle.mer_type }','${merchantFundSettle.start_date }','${merchantFundSettle.end_date }', '${merchantFundSettle.mer_name }');">详情</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<!-- 分页 -->
				<c:if test="${merchantFundSettle.totalPages != null}">
					<div class="next">
						<input style="float: left" type="button" class="icon_normal" value="下载结算单明细" id="down" onclick="downExcel();"/>
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
			
		 	<div id="pop1" class="pop" style="display: none;OVERFLOW: auto;">
				<div class="pop_body" style="width: 1000px;">
					<h1 class="pop_tit">
						<span class="fl">结算单明细</span> <a class="close"
							href="javascript:void(0);" onclick="hide('pop1');">&nbsp;</a>
					</h1>
					<div class="table_2">
						<div>
							<input type="hidden" id="mer_code_hide" />
							<input type="hidden" id="mer_type_hide" />
							<input type="hidden" id="start_date_hide"/>
							<input type="hidden" id="end_dates_hide"/>
							<input type="hidden" id="mer_name_hide"/>
						</div>
						<div id="selectSettlementDetailed"></div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>
