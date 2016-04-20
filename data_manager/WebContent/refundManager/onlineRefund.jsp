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
		var form = document.getElementById("searchRefundLogData");
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
		if(endDate - startDate  > 30*24*60*60*1000*1){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryOnlineTkDataLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("searchRefundLogData");
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
		if(endDate - startDate  > 30*24*60*60*1000*1){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryOnlineTkDataLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var form = document.getElementById("searchRefundLogData");
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
		if(endDate - startDate  > 30*24*60*60*1000*1){
			alert("日期范围只能为3个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryOnlineTkDataLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//初始化银行机构选择下拉框
	function initData() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getOnLineInstInfoList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#gate").append('<option value="' + msg[i]['instId'] +'">'+ msg[i]['name'] + '</option>');
			}
		});
		
		var date_type = $("#dateType_hidden").val();
		var dateType = document.getElementById("dateType");
		for (var i = 0; i < dateType.options.length; i++) {
			if (dateType.options[i].value == date_type) {
				dateType.options[i].selected = 'selected';
			}
		}
		
		var online_refund_state = $("#online_refund_state_hidden").val();
		var onlineRefundState = document.getElementById("onlineRefundState");
		for (var i = 0; i < onlineRefundState.options.length; i++) {
			if (onlineRefundState.options[i].value == online_refund_state) {
				onlineRefundState.options[i].selected = 'selected';
			}
		}
		
		var mer_state = $("#mer_state_hidden").val();
		var merState = document.getElementById("merState");
		for (var i = 0; i < merState.options.length; i++) {
			if (merState.options[i].value == mer_state) {
				merState.options[i].selected = 'selected';
			}
		}
		
		var inst_name = $("#inst_name").val();
		if (inst_name != "") {
			loadInstInfo(inst_name);
		}
		
		var gate = $("#gate_hidden").val();
		var gates = document.getElementById("gate");
		for (var i = 0; i < gates.length; i++) {
			if (gates.options[i].value == gate) {
				gates.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	// 根据银行机构获取渠道信息
	function loadInstInfo(inst_name) {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getInstInfoByInstName.do',
    		type : 'post',
    		data : 'inst_name='+inst_name,
    		async : false,
    		dataType : 'text',
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("gate");
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
	
	// 下载Excel
	function downExcel(){
		var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var onlineRefundState = $("#onlineRefundState").val();
		var merState = $("#merState").val();
		var merCode = $("#merCode").val();
		var onlineRefundId = $("#onlineRefundId").val();
		var tseq = $("#tseq").val();
		var gate = $("#gate").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
		var url ="<%=request.getContextPath()%>/downExcelOnlineTkData.do?dateType="+dateType+
		"&startTime="+startTime+"&endTime="+endTime+"&onlineRefundState="+onlineRefundState+
		"&merState="+merState+"&merCode="+merCode+"&onlineRefundId="+onlineRefundId+"&tseq="+tseq+"&gate="+gate;
		window.location=url;
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
	
	function queryMoney() {
		var c1 = $("#tk tr:first-child").find("td").length;
	  	if(c1 == 1){
	   		return;
	  	}
	  	var dateType = $("#dateType").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var onlineRefundState = $("#onlineRefundState").val();
		var merState = $("#merState").val();
		var merCode = $("#merCode").val();
		var onlineRefundId = $("#onlineRefundId").val();
		var tseq = $("#tseq").val();
		var gate = $("#gate").val();
		if(startTime == null || startTime == ''){
			alert("请选择起始日期");
			return;
		}
		if(endTime == null || endTime == ''){
			alert("请选择截止日期");
			return;
		}
	  	$.ajax({
    		url : '<%=request.getContextPath()%>/queryOnlineTkTotalMoney.do',
    		type : 'post',
    		data : {"dateType": dateType, "startTime":startTime,"endTime":endTime,"onlineRefundState":onlineRefundState, "merState":merState, 
    			"merCode":merCode, "onlineRefundId":onlineRefundId, "tseq":tseq, "gate":gate},
    		async : false,
    		success : function(refundLog) {
    			if (refundLog != null) {
    				$("#ref_amt").html(refundLog.batch);
					$("#mer_fee").html(refundLog.bgRetUrl);
    			}
    		}
    	});
	}
	
	// 联机退款
	function onlineTk(){
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	
  	  	if(!confirm("是否确认该操作？")){
			return;
		}
	  	
	  	var idList = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			idList[i] = $(this).val();
			i++;
		});
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateRefundLogDataToOnlineTk.do",
			data : "idList=" + idList,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功!");
					checkQuery();
				} else if (msg == -1) {
					alert("本条数据是联机退款失败的数据不能再做联机退款操作！");
				} else {
					alert("操作失败！");
				}
			}
		});
	}
	
	// 人工经办
	function jinbanTk(){
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	
  	  	if(!confirm("是否确认该操作？")){
			return;
		}
	  	
	  	var idList = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			idList[i] = $(this).val();
			i++;
		});
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateRefundLogDataToRGJB.do",
			data : "idList=" + idList,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg != null) {
					alert("共经办"+msg.split(";")[0]+"条,经办完成"+msg.split(";")[1]+"条.\n无法经办"+msg.split(";")[2]+"条;\n["+msg.split(";")[3]+"]:余额不足;\n["+msg.split(";")[4]+"]:RYF接口调用失败;\n["+msg.split(";")[5]+"]:退款不在有效时间范围内;\n["+msg.split(";")[6]+"]:其他原因;");
					checkQuery();
				} else {
					alert("操作失败！");
				}
			}
		});
	}
	
	// 撤销退款
	function chexiaoTk() {
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	
  	  	if(!confirm("是否确认该操作？")){
			return;
		}
	  	
	  	var idList = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			idList[i] = $(this).val();
			i++;
		});
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateRefundLogDataToChexiaoById.do",
			data : "idList=" + idList,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功！");
					checkQuery();
				} else if (msg == -1) {
					alert("本条数据是联机退款失败的数据不能再做撤销退款操作！");
				} else {
					alert("操作失败！");
				}
			}
		});
	}
</script>
</head>

<body onload="initData(); queryMoney();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">退款管理</a>&gt;<span>联机退款</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryOnlineTkDataLst.do" target="right" name="searchRefundLogData" id="searchRefundLogData" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table width="90%" border="0" cellspacing="0">
				            <tr>
				            	<td align="right" nowrap="nowrap">
				            		 <span class="input_bgl">
				                     	<select name="dateType" id="dateType">
				                     		<option value="1">退款确认日期</option>
				                     		<option value="2">退款经办日期</option>
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
									<font color='red' size="4">*</font>		
								</td>
				                <td align="right" nowrap="nowrap">联机退款状态</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="onlineRefundState" id="onlineRefundState" style="width: 150px;">
				                     		<option value="0">待处理</option>
				                     		<option value="1">退款中</option>
				                     		<option value="2">联机退款成功</option>
				                     		<option value="3">联机退款失败</option>
				                     	</select>
				                     	<input type="hidden" id="online_refund_state_hidden" value="${onlineRefundState }" />
				                     </span>
				                </td>
				               <td align="right" nowrap="nowrap">商户状态</td>
				               <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="merState" id="merState" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="5">正常</option>
				                     		<option value="6">关闭</option>
				                     	</select>
				                     	<input type="hidden" id="mer_state_hidden" value="${merState }" />
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
				                <td align="right" nowrap="nowrap">原电银流水号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="tseq" id="tseq" value="${param.tseq }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				               <td align="right" nowrap="nowrap">退款流水号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="onlineRefundId" id="onlineRefundId" value="${param.onlineRefundId }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
				                <td align="right" nowrap="nowrap">原扣款渠道</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" id="inst_name" name="inst_name" value="${param.inst_name }" onblur="loadInstInfo(this.value)" />
				                     	<select id="gate" name="gate" style="width: 150px;">
											<option value="">全部</option>
										</select>
										<input type="hidden" id="gate_hidden" value="${gate }"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="下载" onclick="downExcel();"/>
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
					<c:if test="${fn:length(pageLst.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageLst.result) > 0 }">${fn:length(pageLst.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageLst.totalItems == null }">0</c:if>
					<c:if test="${pageLst.totalItems != null }">${pageLst.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageLst.totalPages == null}">0</c:if>
					<c:if test="${pageLst.totalPages != null}">${pageLst.totalPages}</c:if>
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
					申请退款总金额：
					<font color="red">
						<span id="ref_amt">0.00</span>
					</font>
					元
					&nbsp;&nbsp;
					退回商户手续费总金额：
					<font color="red">
						<span id="mer_fee">0.00</span>
					</font>
					元
				</span>
			</div>
			<div class="table-m" >
				<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;" id="merInfo">
					<table width="1800px;" border="0" cellspacing="0">
						<thead>
							<tr>
								<td width="5%" align="center">
									<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
								</td>
								<td align="center">退款流水号</td>
								<td align="center">原电银流水号</td>
								<td align="center">商户号</td>
								<td align="center">商户简称</td>
								<td align="center">原商户订单号</td>
								<td align="center">原扣款渠道</td>
								<td align="center">原交易银行</td>
								<td align="center">申请退款金额</td>
								<td align="center">退回商户手续费</td>
								<td align="center">原交易日期</td>
								<td align="center">退款确认日期</td>
								<td align="center">联机退款状态</td>
								<td align="center">退款失败原因</td>
								<td align="center">申请退款原因</td>
							</tr>
						</thead>
						<tbody id="tk">
							<c:if test="${fn:length(pageLst.result)<=0 }">
								<tr align="center">
									<td colspan="14">对不起,暂无数据！</td>
								</tr>
							</c:if>
							<c:forEach items="${pageLst.result }" var="refundLog">
								<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
									<td align="center">
										<c:if test="${refundLog.stat != 2 || refundLog.stat != 3 || refundLog.stat != 6 || refundLog.online_refund_state != 1 || refundLog.online_refund_state != 2}">
											<input type="checkbox" name="subcheck" id="subcheck" onclick="setSelectAll();" value="${refundLog.id}"/>
										</c:if>
									</td>
									<td align="center">${refundLog.id}</td>
									<td align="center">${refundLog.tseq}</td>
									<td align="center">${refundLog.mid}</td>
									<td align="center">${refundLog.mer_abbreviation }</td>
									<td align="center">${refundLog.org_oid}</td>
									<td align="center">${refundLog.name }</td>
									<td align="center">${refundLog.bankName }</td>
									<td align="center">
										<f:formatNumber value="${refundLog.ref_amt }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">
										<f:formatNumber value="${refundLog.mer_fee }" pattern="0.00"></f:formatNumber>
									</td>
									<td align="center">${refundLog.sys_date }</td>
									<td align="center">${refundLog.req_date }</td>
									<td align="center">
										<c:if test="${refundLog.online_refund_state == 0}">待处理</c:if>
										<c:if test="${refundLog.online_refund_state == 1}">退款中</c:if>
										<c:if test="${refundLog.online_refund_state == 2}">联机退款成功</c:if>
										<c:if test="${refundLog.online_refund_state == 3}">联机退款失败</c:if>
									</td>
									<td align="center">${refundLog.reason }</td>
									<td align="center">${refundLog.refund_reason }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<!-- 分页 -->
				<c:if test="${pageLst.totalPages != null}">
					<div class="next">
						<input style="float: left" type="button" class="icon_normal" value="联机退款" onclick="onlineTk();"/>
						<input style="float: left" type="button" class="icon_normal" value="撤销退款" onclick="chexiaoTk();"/>
						<input style="float: left" type="button" class="icon_normal" value="人工经办" onclick="jinbanTk();"/>
						<c:if test="${pageLst.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo > 1}">
							<a href="javascript:paging(${pageLst.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageLst.pageNo-2 > 0}">
							<a href="javascript:paging(${pageLst.pageNo-2 })"><span>${pageLst.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo-1 > 0}">
							<a href="javascript:paging(${pageLst.pageNo-1 })"><span>${pageLst.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${pageLst.pageNo }</span></a>
						<c:if test="${pageLst.pageNo+1 <= pageLst.totalPages}">
							<a href="javascript:paging(${pageLst.pageNo+1 })"><span>${pageLst.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo+2 <= pageLst.totalPages}">
							<a href="javascript:paging(${pageLst.pageNo+2 })"><span>${pageLst.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo+3 <= pageLst.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageLst.pageNo < pageLst.totalPages}">
							<a href="javascript:paging(${pageLst.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${pageLst.pageNo > 1}">
							<a href="javascript:paging(${pageLst.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${pageLst.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${pageLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
