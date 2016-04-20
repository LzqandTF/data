<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网关对应商户交易表查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script> --%>
<script type="text/javascript">
	// 单选按钮事件
	$(function () {
		if ($("input[name='instInfo']").attr("checked")) {
			initBankInst();
		}
		$("input[name='instInfo']").click(function () {
			if ($(this).attr("checked")) {
				$("input[name=merInfo]").attr("checked",false); 
				$("#code").css({display:"none"});
				$("#id").css({display:"block"});
				$("#startDate").val("");
				$("#endDate").val("");
				$("#merCode").val("");
				$("#merName").val("");
				$("#mer_name").val("");
			}
		});
		$("input[name='merInfo']").click(function () {
			if ($(this).attr("checked")) {
				$("input[name=instInfo]").attr("checked",false); 
				$("#id").css({display:"none"});
				$("#code").css({display:"block"});
				$("#startTime").val("");
				$("#endTime").val("");
				$("#bank_id").val("");
				var selectObj = document.getElementById("inst_id");
				while(selectObj.firstChild) {
					selectObj.removeChild(selectObj.firstChild);
				}
				$(selectObj).append("<option value=''>全部</option>");
			 }
		});
	 });
	
	// 初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] +'">'+ msg[i]['bank_name'] + '</option>');
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
    			var selectObj = document.getElementById("inst_id");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				if (data.length == 0 || data.length > 1) {
					$(selectObj).append("<option value=''>全部</option>");
				}
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].instId +">"+data[i].name+"</option>");
				}
    		}
    	});
	}
	
	//分页查询
	function paging(pageNo) {
		if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				alert("请选择交易日期！");
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var form = document.getElementById("searchMerchantSettleStatistics");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				alert("请选择交易日期！");
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			
			var form = document.getElementById("searchMerchantSettleStatistics");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				alert("请选择交易日期！");
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var form = document.getElementById("searchMerchantSettleStatistics");
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				alert("请选择交易日期！");
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			
			var form = document.getElementById("searchMerchantSettleStatistics");
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
	}
	
	// 查询事件
	function checkQuery() {
		if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				alert("请选择交易日期！");
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var form = document.getElementById("searchMerchantSettleStatistics");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				alert("请选择交易日期！");
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			
			var form = document.getElementById("searchMerchantSettleStatistics");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
	}
	
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
	
	function initSelected() {
		var instId_hidden = $("#instId_hidden").val();
		var inst_id = document.getElementById("inst_id");
		for(var i = 0; i < inst_id.options.length; i++){
			if(inst_id.options[i].value == instId_hidden){
				inst_id.options[i].selected = 'selected';
			}
		}
		
		var mer_name_hidden = $("#mer_name_hidden").val();
		var mer_name = document.getElementById("mer_name");
		for(var i = 0; i < mer_name.options.length; i++){
			if(mer_name.options[i].value == mer_name_hidden){
				mer_name.options[i].selected = 'selected';
			}
		}
		
		if (mer_name_hidden != "") {
			queryMerNameListByMerName();
		}
		
		var merInfo_hidden = $("#merInfo_hidden").val();
		if (merInfo_hidden == 1) {
			$("input[name=merInfo]").attr("checked",true);
			
			var mer_name = document.getElementById("mer_name");
			for(var i = 0; i < mer_name.options.length; i++){
				if(mer_name.options[i].value == mer_name_hidden){
					mer_name.options[i].selected = 'selected';
				}
			}
			
			$("input[name=instInfo]").attr("checked",false); 
			$("#id").css({display:"none"});
			$("#code").css({display:"block"});
			$("#startTime").val("");
			$("#endTime").val("");
			$("#bank_id").val("");
			var selectObj = document.getElementById("inst_id");
			while(selectObj.firstChild) {
				selectObj.removeChild(selectObj.firstChild);
			}
			$(selectObj).append("<option value=''>全部</option>");
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//统计对账金额
	function queryMoney() {
		var c1 = $("#msm tr:first-child").find("td").length;
	  	if(c1 == 1){
	   		return;
	  	}
	  	if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				return ;
			}
			var bank_id = $("#bank_id").val();
			var inst_id = $("#inst_id").val();
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryTotalMoney.do',
	    		type : 'post',
	    		data : {"startTime": startTime, "endTime":endTime,"bank_id":bank_id,"inst_id":inst_id},
	    		async : false,
	    		success : function(merchantSettleStatistics) {
	    			if (merchantSettleStatistics != null) {
	    				$("#trade_amount").html(merchantSettleStatistics.trade_amount);
						$("#refund_amount").html(merchantSettleStatistics.refund_amount);
						$("#zf_fee").html(merchantSettleStatistics.zf_fee);
						$("#refund_zf_fee").html(merchantSettleStatistics.refund_zf_fee);
						$("#settle_amount").html(merchantSettleStatistics.settle_amount);
	    			}
	    		}
	    	});
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var merCode = $("#merCode").val();
			var mer_name = $("#mer_name").val();
			
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				return ;
			}
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryTotalMoney.do',
	    		type : 'post',
	    		data : {"startDate": startDate, "endDate":endDate,"merCode":merCode,"mer_name":mer_name},
	    		async : false,
	    		success : function(merchantSettleStatistics) {
	    			if (merchantSettleStatistics != null) {
	    				$("#trade_amount").html(merchantSettleStatistics.trade_amount);
						$("#refund_amount").html(merchantSettleStatistics.refund_amount);
						$("#zf_fee").html(merchantSettleStatistics.zf_fee);
						$("#refund_zf_fee").html(merchantSettleStatistics.refund_zf_fee);
						$("#settle_amount").html(merchantSettleStatistics.settle_amount);
	    			}
	    		}
	    	});
		}
	}
	
	function queryMerNameListByMerName() {
		var mer_name = $("#merName").val();
		if (mer_name == null || mer_name == "") {
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryMerNameListByMerName.do',
    		type : 'post',
    		data : {"mer_name": mer_name},
    		async : false,
    		success : function(list) {
    			if (list != null) {
        			var selectObj = document.getElementById("mer_name");
    				while(selectObj.firstChild) {
    			        selectObj.removeChild(selectObj.firstChild);
    				}
    				if (list.length >= 0) {
    					$(selectObj).append("<option value=''>--请选择--</option>");
    				}
    				for(var i = 0; i < list.length; i++){
    					$(selectObj).append("<option value="+list[i].mer_name +">" + list[i].mer_name + "</option>");
    				}
    			}
    		}
    	});
	}
	
	function downExcel() {
	  	if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var bank_id = $("#bank_id").val();
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				alert("请选择交易日期！");
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var inst_id = $("#inst_id").val();
			
			var url ="<%=request.getContextPath()%>/queryDataLstForExcel.do?startTime="+startTime+
				"&endTime="+endTime+"&bank_id="+bank_id+"&inst_id="+inst_id;
			window.location=url;
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var merCode = $("#merCode").val();
			var mer_name = $("#mer_name").val();
			
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				alert("请选择交易日期！");
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var url ="<%=request.getContextPath()%>/queryDataLstForExcel.do?startDate="+startDate+
				"&endDate="+endDate+"&merCode="+merCode+"&mer_name="+mer_name;
			window.location=url;
		}
	}
	
	
	function downDataLstExcel() {
	  	if ($("input[name='instInfo']").attr("checked")) {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var bank_id = $("#bank_id").val();
			if ((startTime == null || startTime == "") || (endTime == null || endTime == "")) {
				alert("请选择交易日期！");
				return;
			}
			var stDate  = new Date(startTime.replace(/-/g,"/")).getTime();
			var enDate = new Date(endTime.replace(/-/g,"/")).getTime();
			if(enDate - stDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var inst_id = $("#inst_id").val();
			
			var url ="<%=request.getContextPath()%>/getDataLstForExcel.do?startTime="+startTime+
				"&endTime="+endTime+"&bank_id="+bank_id+"&inst_id="+inst_id;
			window.location=url;
		} else {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var merCode = $("#merCode").val();
			var mer_name = $("#mer_name").val();
			
			if ((startDate == null || startDate == "") || (endDate == null || endDate == "")) {
				alert("请选择交易日期！");
				return;
			}
			var sDate  = new Date(startDate.replace(/-/g,"/")).getTime();
			var eDate = new Date(endDate.replace(/-/g,"/")).getTime();
			if(eDate - sDate  > 31*24*60*60*1000*1){
				alert("日期范围只能为1个月以内");
				return ;
			}
			var url ="<%=request.getContextPath()%>/getMssDataLstByMerForExcel.do?startDate="+startDate+
				"&endDate="+endDate+"&merCode="+merCode+"&mer_name="+mer_name;
			window.location=url;
		}
	}
</script>
</head>

<body onload="initSelected(); queryMoney();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>网关对应商户交易表查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerchantSettleStatisticsList.do" target="right" name="searchMerchantSettleStatistics" id="searchMerchantSettleStatistics" method="post">
				<div>
					<span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="instInfo" name="instInfo" checked="checked"/>按渠道查询
					</span>
					<span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="merInfo" name="merInfo"/>按商户查询
						<input type="hidden" id="merInfo_hidden" value="${merInfo }"/>
					</span>
				</div>
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="95%" border="0" cellspacing="0">
							<tr id="id">
								<td align="right" nowrap="nowrap">交易日期</td>
				            	<td nowrap="nowrap" width="25%">
									<span style="width: 30px;" class="input_bgl"> 
										<input style="width: 75px" id="startTime" name="startTime" value="${param.startTime }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});" />
										至 
										<input style="width: 75px" id="endTime" name="endTime" value="${param.endTime }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
										<font color="red">*</font>
									</span>
								</td>
								<td align="right" nowrap="nowrap">银行机构</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="bank_id" name="bank_id" style="width: 150px;" onchange="getInstInfoByBankId(this.value);">
											<option value="">全部</option>
										</select>
										<input type="hidden" id="bankId_hidden" value="${bankId }"/>
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">扣款渠道</td>
				                <td nowrap="nowrap">
				                    <span class="input_bgl">
				                    	<select id="inst_id" name="inst_id" style="width: 150px;">
											<option value="">全部</option>
										</select>
										<input type="hidden" id="instId_hidden" value="${instId }"/>
				                    </span>
				                </td>
							</tr>
							<tr id="code" style="display: none;">
								<td align="right" nowrap="nowrap">交易日期</td>
				            	<td nowrap="nowrap" width="23%">
									<span style="width: 30px;" class="input_bgl"> 
										<input style="width: 75px" id="startDate" name="startDate" value="${param.startDate }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'});" />
										至 
										<input style="width: 75px" id="endDate" name="endDate" value="${param.endDate }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" />
										<font color="red">*</font>
									</span>
								</td>
								<td align="right" nowrap="nowrap">商户号</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merCode" id="merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d]/g,'')" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">商户名称</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="merName" id="merName" value="${param.merName }" style="width: 100px;" onblur="queryMerNameListByMerName();"/>
				                     </span>
				                     <span class="input_bgl">
				                    	<select id="mer_name" name="mer_name" style="width: 120px;" onblur="">
											<option value="">--请选择--</option>
										</select>
										<input type="hidden" id="mer_name_hidden" value="${mer_name }"/>
				                    </span>
				                </td>
							</tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="下载xls报表" onclick="downExcel();" />
					                
					                <input type="button" class="icon_normal" value="汇总下载" onclick="downDataLstExcel();" />
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
			
			<div style="font-size: 11px;">
				<span>
					本页共
					<font color="red">
						<c:if test="${fn:length(pageDataLst.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(pageDataLst.result) > 0 }">${fn:length(pageDataLst.result) }</c:if>
					</font>
					条数据
				</span>
				<span style="float: right;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					共
					<font color="red">
						<c:if test="${pageDataLst.totalItems == null }">0</c:if>
						<c:if test="${pageDataLst.totalItems != null }">${pageDataLst.totalItems }</c:if>
					</font>
					条数据
					<font color="red">
						<c:if test="${pageDataLst.totalPages == null}">0</c:if>
						<c:if test="${pageDataLst.totalPages != null}">${pageDataLst.totalPages}</c:if>
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
				<br />
				<span>
					总计:&nbsp;&nbsp;
					支付金额:
					<font color="red">
						<span id="trade_amount">0.00</span>
					</font>
					元
					&nbsp;
					退款金额:
					<font color="red">
						<span id="refund_amount">0.00</span>
					</font>
					元 
					&nbsp;
					银行手续费:
					<font color="red">
						<span id="zf_fee">0.00</span>
					</font>
					元 
					&nbsp;
					银行退回手续费:
					<font color="red">
						<span id="refund_zf_fee">0.00</span>
					</font>
					元
				 	&nbsp;
				 	银行划款额:
					<font color="red">
						<span id="settle_amount">0.00</span>
					</font>
					元
				</span>
			</div>
			
			<div class="table-m" >
				<div>
					<table width="100%" border="0" cellspacing="0">
						<thead>
							<tr>
								<c:if test="${merInfo == 0 }">
									<td align="center">扣款渠道</td>
									<td align="center">商户号</td>
									<td align="center">商户简称</td>
								</c:if>
								<c:if test="${merInfo == 1 }">
									<td align="center">商户号</td>
									<td align="center">商户简称</td>
									<td align="center">扣款渠道</td>
								</c:if>
								
								<td align="center">支付金额</td>
								<td align="center">退款金额</td>
								<td align="center">银行手续费</td>
								<td align="center">银行退回手续费</td>
								<td align="center">银行划款额</td>
							</tr>
						</thead>
						<tbody id="msm">
							<c:if test="${fn:length(pageDataLst.result)<=0 }">
								<tr align="center">
									<td colspan="8">对不起,暂无数据！</td>
								</tr>
							</c:if>
							<c:forEach items="${pageDataLst.result }" var="merchantSettleStatistics">
								<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
									<c:if test="${merInfo == 0 }">
										<td align="center">${merchantSettleStatistics.name_ }</td>
										<td align="center">${merchantSettleStatistics.mer_code }</td>
										<td align="center">${merchantSettleStatistics.mer_abbreviation }</td>
									</c:if>
									<c:if test="${merInfo == 1 }">
										<td align="center">${merchantSettleStatistics.mer_code }</td>
										<td align="center">${merchantSettleStatistics.mer_abbreviation }</td>
										<td align="center">${merchantSettleStatistics.name_ }</td>
									</c:if>
									
									<td align="center">
										<f:formatNumber value="${merchantSettleStatistics.trade_amount }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">
										<f:formatNumber value="${merchantSettleStatistics.refund_amount }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">
										<f:formatNumber value="${merchantSettleStatistics.zf_fee }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">
										<f:formatNumber value="${merchantSettleStatistics.refund_zf_fee }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">
										<f:formatNumber value="${merchantSettleStatistics.trade_amount + merchantSettleStatistics.refund_amount - merchantSettleStatistics.zf_fee - merchantSettleStatistics.refund_zf_fee }" pattern="0.00"></f:formatNumber>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- 分页 -->
				<c:if test="${pageDataLst.totalPages != null}">
					<div class="next">
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageDataLst.pageNo-2 > 0}">
							<a href="javascript:paging(${pageDataLst.pageNo-2 })"><span>${pageDataLst.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo-1 > 0}">
							<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>${pageDataLst.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${pageDataLst.pageNo }</span></a>
						<c:if test="${pageDataLst.pageNo+1 <= pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>${pageDataLst.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo+2 <= pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+2 })"><span>${pageDataLst.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo+3 <= pageDataLst.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageDataLst.pageNo < pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(${pageDataLst.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${pageDataLst.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${pageDataLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
			
		</div>
	</div>
</body>
</html>
