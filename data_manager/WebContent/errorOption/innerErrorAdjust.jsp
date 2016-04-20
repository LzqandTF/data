<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内部差错调整</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox/wbox-min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<style type="text/css">
	.errorDetail{
		width: 900px;
		height: 350px; 
		overflow:auto;
		overflow-x:hidden;
		overflow-y:auto;
	}
	.errorDetail td{
		border-right:1px solid #dcdfe1;
		border-bottom:1px solid #dcdfe1; 
		padding:5px 10px;
	}
	.yhErrorDetail{
		width: 900px;
		height: 350px;
	}
	.yhErrorDetail td{
		border-right:1px solid #dcdfe1;
		border-bottom:1px solid #dcdfe1; 
		padding:5px 10px;
	}
	td table{
		border-collapse: collapse;
	}
</style>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("needAdjustDataSearch");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/getNeedAdjustData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("needAdjustDataSearch");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/getNeedAdjustData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var form = document.getElementById("needAdjustDataSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getNeedAdjustData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//重置：清空表单元素
	function clearForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#deduct_sys_reference").val("");
		$("#additional_data").val("");
		$("#whtherInnerJs").val("");
		$("#js_date").val("");
		$("#check_account_date").val("");
		$("#handling_id").val("");
		$("#whetherErroeHandle").val("");
		$("#handling_status").val("");
		$("#error_resource").val("");
		$("#req_sys_stance").val("");
		$("#bank_id").val("");
		var selectObj = document.getElementById("channel");
		while(selectObj.firstChild) {
			selectObj.removeChild(selectObj.firstChild);
		}
		$(selectObj).append("<option value=''>全部</option>");
	}
	
	// 初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['dz_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
    		}
    	});
		var whther_Inner_Js = $("#whther_Inner_Js_hidden").val();
		var whtherInnerJs = document.getElementById("whtherInnerJs");
		for(var i = 0;i<whtherInnerJs.options.length;i++){
			if(whtherInnerJs.options[i].value == whther_Inner_Js){
				whtherInnerJs.options[i].selected = 'selected';
			}
		}
		
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
				if (data.length >= 0) {
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
	
	//差错处理方式
	function initErrorHandlerMethod() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/findInnerErrorHandlingLst.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#handling_id").append('<option value="' + msg[i]['handling_id'] + '">'+ msg[i]['handling_name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取差错处理方式列表失败!");
    		}
    	});
		var handling_name = $("#handling_name_hidden").val();
		var type = document.getElementById("handling_id");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == handling_name){
				type.options[i].selected = 'selected';
			}
		}
		var error_type = $("#error_type_hidden").val();
		var errorType = document.getElementById("whetherErroeHandle");
		for(var i = 0;i<errorType.options.length;i++){
			if(errorType.options[i].value == error_type){
				errorType.options[i].selected = 'selected';
			}
		}
		var handling_status = $("#handling_status_hidden").val();
		var handlingStatus = document.getElementById("handling_status");
		for(var i = 0;i<handlingStatus.options.length;i++){
			if(handlingStatus.options[i].value == handling_status){
				handlingStatus.options[i].selected = 'selected';
			}
		}
		var error_resource = $("#error_resource_hidden").val();
		var errorResource = document.getElementById("error_resource");
		for (var i = 0; i < errorResource.options.length; i++) {
			if (errorResource.options[i].value == error_resource) {
				errorResource.options[i].selected = 'selected';
			}
		}
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//excel表格下载
	function downExcel(){
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构!");
			return;
		}
		var channel = $("#channel").val();
		//得到页面上的值
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var additional_data = $("#additional_data").val();
		var whtherInnerJs = $("#whtherInnerJs").val();
		var js_date = $("#js_date").val();
		var handling_id = $("#handling_id").val();
		var whetherErroeHandle = $("#whetherErroeHandle").val();
		var handling_status = $("#handling_status").val();
		var req_sys_stance = $("#req_sys_stance").val();
		var error_resource = $("#error_resource").val();
		var url ="<%=request.getContextPath()%>/innerErrorAdjustDownExcel.do?startTime="+startTime+"&endTime="+endTime+"&deduct_sys_reference="+deduct_sys_reference+
				"&additional_data="+additional_data+"&whtherInnerJs="+whtherInnerJs+"&channel="+channel+
				"&js_date="+js_date+"&whetherErroeHandle="+whetherErroeHandle+"&error_resource="+error_resource+
				"&handling_id="+handling_id+"&handling_status="+handling_status+"&req_sys_stance="+req_sys_stance+"&bank_id="+bank_id;
		window.location=url;
	}
	
	//差错处理方式
	function queryErrorHandlerMethod(handling_id) {
		$.ajax({
    		url : '<%=request.getContextPath()%>/findInnerErrorHandlingLst.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			var selectObj = document.getElementById("handingName");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				if (handling_id == 0) {
					$(selectObj).append("<option value=''>--请选择处理方式--</option>");
				}
    			for (i in msg)
    				if (msg[i]['handling_id'] == handling_id) {
    					$("#handingName").append('<option value="' + msg[i]['handling_id'] + '" selected="selected">'+ msg[i]['handling_name'] + '</option>');
    				} else {
    					$("#handingName").append('<option value="' + msg[i]['handling_id'] + '">'+ msg[i]['handling_name'] + '</option>');
    				}
    		},
    		error : function(msg) {
    			alert("获取差错处理方式列表失败!");
    		}
    	});
	}
	
	// 根据银行机构获取渠道信息
	function getInstNameByBankId(bank_id, deduct_sys_id) {
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryOutDzInstInfoByBankId.do',
    		type : 'post',
    		data : 'bank_id='+bank_id,
    		async : false,
    		success : function(msg) {
    			var selectObj = document.getElementById("instName");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				if (deduct_sys_id == 0) {
					$(selectObj).append("<option value=''>--请选择扣款渠道--</option>");
				}
				for (i in msg){
					if (msg[i]['instId'] == deduct_sys_id) {
    					$("#instName").append('<option value="' + msg[i]['instId'] + '" selected="selected">'+ msg[i]['name'] + '</option>');
    				} else {
    					$("#instName").append('<option value="' + msg[i]['instId'] + '">'+ msg[i]['name'] + '</option>');
    				}
					
				}
    		}
    	});
	}
	
	// 给弹出的差错调整弹出框赋值
 	function selectData(tradeId, handling_id, handler_remark,js_date, inst_type, bank_id,deduct_sys_id, operator) {
		$("#trade_id").val(tradeId);
		$("#operator").val(operator);
 		queryErrorHandlerMethod(handling_id);
 		$("#handler_remark").val(handler_remark);
 		$("#js_date_").val(js_date);
 		
		$("#update").css({display:"block"});
		if (inst_type == 0) {
			$("#instId").css({display:"none"});
		} else {
			getInstNameByBankId(bank_id, deduct_sys_id);
		}
	}
	
	
	//差错调整
	function updateErrorAdjust() {
		var trade_id = $("#trade_id").val();
		var operator = $("#operator").val();
		$("#operator").val(operator);
		var handling_id = $("#handingName").val();
		var handler_remark = $("#handler_remark").val();
		var js_date = $("#js_date_").val();
		var deduct_sys_id = $("#instName").val();
		if (handling_id == "") {
			alert("请选择处理方式！");
			return;
		}
		if (js_date == "" || js_date == null) {
			alert("请选择结算日期！");
			return;
		}
		if (handler_remark == "") {
			alert("请添加备注！");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/updateErrorDataLst.do',
    		type : 'post',
    		data : {"trade_id": trade_id, "operator":operator,"handling_id":handling_id,"handler_remark" : handler_remark,"js_date" : js_date, "deduct_sys_id":deduct_sys_id},
    		async : false,
    		success : function(msg) {
    			if(msg){
					alert("调整成功");
					checkQuery();
				}else{
					alert("调整失败");
				}
    		}
    	});
	}
	
	//清空电银的详情数据
	function clearDyForm() {
		$("#dy_req_sys_stance").text("");
		$("#dy_trade_time").text("");
		$("#dy_out_account").text("");
		$("#dy_trade_amount").text("");
		$("#dy_trade_fee").text("");
		$("#dy_trade_result").text("");
		$("#dy_req_process").text("");
		$("#dy_req_response").text("");
		$("#dy_req_mer_code").text("");
		$("#dy_req_mer_term_id").text("");
		$("#dy_deduct_mer_term_id").text("");
		$("#dy_mer_name").text("");
		$("#dy_deduct_sys_id").text("");
		$("#dy_deduct_mer_code").text("");
		$("#dy_deduct_result").text("");
		$("#dy_deduct_sys_response").text("");
		$("#dy_trademsg_type").text("");
		$("#dy_deduct_roll_bk_response").text("");
		$("#dy_deduct_roll_bk").text("");
		$("#dy_deduct_roll_bk_result").text("");
		$("#dy_handler_remark").text("");
		$("#dy_turnDown_remark").text("");
		$("#dy_deduct_stlm_date").text("");
		$("#dy_additional_data").text("");
		$("#dy_che_trademsg_type").text("");
		$("#dy_che_status").text("");
		$("#dy_tui_trademsg_type").text("");
		$("#dy_deduct_sys_stance").text("");
		$("#dy_deduct_roll_bk_stance").text("");
	}
	
	//清空银行明细数据
	function clearBankForm() {
		$("#yh_req_sys_stance").html("");
		$("#yh_trade_time").html("");
		$("#yh_out_account").html("");
		$("#yh_trade_amount").html("");
		$("#yh_trade_fee").html("");
		$("#yh_trade_result").html("");
		$("#yh_process").html("");
		$("#yh_deduct_sys_response").html("");
		$("#yh_deduct_mer_code").html("");
		$("#yh_deduct_mer_term_id").html("");
		$("#yh_deduct_sys_reference").html("");
		$("#yh_deduct_stlm_date").html("");
	}
	
	//处理电银数据的显示
	function queryDyData(errorDataLst) {
		var deductRollBk = errorDataLst['deduct_roll_bk'];
		var dy_req_sys_stance = null;
		if (0 == deductRollBk) {
			dy_req_sys_stance = errorDataLst['deduct_sys_stance'];
		} else if (1 == deductRollBk) {
			dy_req_sys_stance = errorDataLst['deduct_roll_bk_stance'];
		}
		$("#dy_req_sys_stance").html(dy_req_sys_stance);
		
		tradeTime = errorDataLst['trade_time'];
		if (tradeTime != null && tradeTime != "" && tradeTime.indexOf(".")) {
			$("#dy_trade_time").html(tradeTime.split('.')[0]);
		} else {
			$("#dy_trade_time").html(tradeTime);
		}
		
		$("#dy_out_account").html(errorDataLst['out_account']);
		$("#dy_trade_amount").html(errorDataLst['trade_amount']);
		$("#dy_trade_fee").html(errorDataLst['trade_fee']);
		var result = errorDataLst['trade_result'];
		var req_process = errorDataLst['req_process'];
		if (0 == result) {
			result = "成功";
		} else if(1 == result) {
			result = "超时";
		} else if (2 == result) {
			result = "失败";
		} else if (3 == result && req_process == '480000') {
			result = "受理成功";
		} else if (3 == result && req_process != '480000') {
			result = "冲正成功";
		} else {
			result = "未知";
		}
		$("#dy_trade_result").html(result);
		$("#dy_req_process").html(req_process);
		$("#dy_req_response").html(errorDataLst['req_response']);
		$("#dy_mer_name").html(errorDataLst['mer_name']);
		$("#dy_req_mer_code").html(errorDataLst['req_mer_code']);
		$("#dy_req_mer_term_id").html(errorDataLst['req_mer_term_id']);
		$("#dy_deduct_sys_id").html(errorDataLst['deduct_sys_id']);
		$("#dy_deduct_mer_term_id").html(errorDataLst['deduct_mer_term_id']);
		$("#dy_deduct_mer_code").html(errorDataLst['deduct_mer_code']);
		var deductResult = errorDataLst['deduct_sys_response'];
		if ('00' == deductResult) {
			deductResult = "成功";
		} else if (null == deductResult) {
			deductResult = "无";
		}else if ('N1' == deductResult) {
			deductResult = "超时";
		} else if (null != deductResult && '00' != deductResult) {
			deductResult = "失败";
		} 
		$("#dy_deduct_result").html(deductResult);
		$("#dy_deduct_sys_response").html(errorDataLst['deduct_sys_response']);
		if (0 == deductRollBk) {
			deductRollBk = "否";
		} else if (1 == deductRollBk) {
			deductRollBk = "是";
		} else {
			deductRollBk = errorDataLst['deduct_roll_bk'];
		}
		$("#dy_deduct_roll_bk").html(deductRollBk);
		var rollBkResult = errorDataLst['deduct_roll_bk_response'];
		if ('00' == rollBkResult) {
			rollBkResult = "成功";
		} else if (null == rollBkResult) {
			rollBkResult = "无";
		} else {
			rollBkResult = "失败";
		}
		$("#dy_deduct_sys_stance").html(errorDataLst['deduct_sys_stance']);
		$("#dy_deduct_roll_bk_stance").html(errorDataLst['deduct_roll_bk_stance']);
		$("#dy_deduct_roll_bk_result").html(rollBkResult);
		$("#dy_handler_remark").html(errorDataLst['handler_remark']);
		$("#dy_turnDown_remark").html(errorDataLst['turnDown_remark']);
		
		deductStmlDate = errorDataLst['deduct_stlm_date'];
		if (deductStmlDate != null && deductStmlDate != "" && deductStmlDate.indexOf(".")) {
			$("#dy_deduct_stlm_date").html(deductStmlDate.split('.')[0]);
		} else {
			$("#dy_deduct_stlm_date").html(deductStmlDate);
		}
		
		var additional_data = errorDataLst['additional_data'];
		if (additional_data != null) {
			$("#dy_additional_data").html(additional_data.split('|')[0]);
		}
		$("#dy_deduct_roll_bk_response").html(errorDataLst['deduct_roll_bk_response']);
		//交易消息类型
		var trademsg_type = errorDataLst['trademsg_type'];
		$("#dy_trademsg_type").html(trademsg_type);
		$("#dy_additional_response_data").html(errorDataLst['additional_response_data']);
	}
	
	//处理银行数据的显示
	function queryYhData(errorDataLst) {
		$("#yh_req_sys_stance").html(errorDataLst['req_sys_stance']);
		
		tradeTime = errorDataLst['trade_time'];
		if (tradeTime != null && tradeTime != "" && tradeTime.indexOf(".")) {
			$("#yh_trade_time").html(tradeTime.split('.')[0]);
		} else {
			$("#yh_trade_time").html(tradeTime);
		}
		
		$("#yh_out_account").html(errorDataLst['out_account']);
		$("#yh_trade_amount").html(errorDataLst['trade_amount']);
		$("#yh_trade_fee").html(errorDataLst['trade_fee']);
		$("#yh_trade_result").html("成功");
		$("#yh_process").html(errorDataLst['req_process']);
		$("#yh_deduct_sys_response").html("00");
		$("#yh_deduct_mer_code").html(errorDataLst['deduct_mer_code']);
		$("#yh_deduct_mer_term_id").html(errorDataLst['deduct_mer_term_id']);
		$("#yh_deduct_sys_reference").html(errorDataLst['deduct_sys_reference']);
		
		deductStmlDate = errorDataLst['deduct_stlm_date'];
		if (deductStmlDate != null && deductStmlDate != "" && deductStmlDate.indexOf(".")) {
			$("#yh_deduct_stlm_date").html(deductStmlDate.split('.')[0]);
		} else {
			$("#yh_deduct_stlm_date").html(deductStmlDate);
		}
	}
	
	//处理原始的相关交易
	function queryDyOriginalData(map) {
		//处理原始相关交易
		var oriTbody = document.getElementById("oriTbody");
		while(oriTbody.firstChild){
			oriTbody.removeChild(oriTbody.firstChild);
		}
		var trademsg_type1 = -1;
		var instType = map.instType;
		//线下原始相关交易
		if (instType == 0) {
			var originalDataList = map.originalDataList;
			if (originalDataList != null) {
				for(var i = 0;i < originalDataList.length; i++){
					trademsg_type1 = i;
					var tr = oriTbody.insertRow(i);
					var td = tr.insertCell(0);
					var td1 = tr.insertCell(1);
					var td2 = tr.insertCell(2);
					td2.innerHTML = originalDataList[i]["tradeType"];
					var trademsg_type = originalDataList[i]["trademsg_type"];
					var deduct_sys_response = originalDataList[i]['deduct_sys_response'];
					if(trademsg_type == 18){
						$("#dy_che_trademsg_type").html("有");
						if (deduct_sys_response == '00') {
							$("#dy_che_status").html("成功");
						} else if (deduct_sys_response == 'N1') {
							$("#dy_che_status").html("超时");
						} else {
							$("#dy_che_status").html("失败");
						}
					}else if(trademsg_type == 20){
						$("#dy_tui_trademsg_type").html("有");
					}
					var td3 = tr.insertCell(3);
					td3.innerHTML = originalDataList[i]["tradeName"];
					var td4 = tr.insertCell(4);
					var td5 = tr.insertCell(5);
			 		var deduct_roll_bk = originalDataList[i]['deduct_roll_bk'];
					if (deduct_roll_bk == 0) {
						td.innerHTML = originalDataList[i]["req_sys_stance"];
						td1.innerHTML = originalDataList[i]["deduct_sys_time"];
						if (deduct_sys_response == '00') {
							td4.innerHTML = "成功";
						} else if (deduct_sys_response == 'N1') {
							td4.innerHTML = "超时";
						} else {
							td4.innerHTML = "失败";
						}
						td5.innerHTML = originalDataList[i]["deduct_sys_response"];
					} else {
						td.innerHTML = originalDataList[i]["deduct_roll_bk_stance"];
						td1.innerHTML = originalDataList[i]["deduct_rollbk_sys_time"];
						var deduct_roll_bk_response = originalDataList[i]['deduct_roll_bk_response'];
						if (deduct_roll_bk_response == '00') {
							td4.innerHTML = "成功";
						} else if (deduct_roll_bk_response == 'N1') {
							td4.innerHTML = "超时";
						} else {
							td4.innerHTML = "失败";
						}
						td5.innerHTML = originalDataList[i]["deduct_roll_bk_response"];
					}
				}
			}
		} else {
			//线上原始相关交易
			if (trademsg_type = 2) {
				//收款相关交易
				var rytList = map.rytList;
				if (rytList != null) {
					for(var i = 0;i < rytList.length; i++){
						trademsg_type1 = i;
						var tr = oriTbody.insertRow(i);
						var td = tr.insertCell(0);
						td.innerHTML = rytList[i]["tseq"];
						var td1 = tr.insertCell(1);
						td1.innerHTML = rytList[i]["sys_date"];
						var td2 = tr.insertCell(2);
						td2.innerHTML = "收款交易";
						var td3 = tr.insertCell(3);
						td3.innerHTML = rytList[i]['tradeType'];
						var td4 = tr.insertCell(4);
						var tstat = rytList[i]['tstat'];
						if (tstat == 0) {
							td4.innerHTML = "初始状态";
						} else if (tstat == 1) {
							td4.innerHTML = "待支付";
						} else if (tstat == 2) {
							td4.innerHTML = "成功";
						} else if (tstat == 3) {
							td4.innerHTML = "失败";
						} else if (tstat == 4) {
							td4.innerHTML = "请求银行失败";
						} else if (tstat == 5) {
							td4.innerHTML = "撤销";
						} else {
							td4.innerHTML = "无";
						}
						var td5 = tr.insertCell(5);
						td5.innerHTML = "00";
					}
				}
			}
			//退款相关交易
			if (trademsg_type = 20) {
				var rytLogList = map.rytRefundLogsList;
				if (rytLogList != null) {
					for(var i = 0;i < rytLogList.length; i++){
						trademsg_type1 = i;
						var tr = oriTbody.insertRow(i);
						var td = tr.insertCell(0);
						td.innerHTML = rytLogList[i]["tseq"];
						var td1 = tr.insertCell(1);
						td1.innerHTML = rytLogList[i]["ref_date"];
						var td2 = tr.insertCell(2);
						td2.innerHTML = "退款交易";
						var td3 = tr.insertCell(3);
						td3.innerHTML = "退款交易";
						var td4 = tr.insertCell(4);
						var tstat = rytLogList[i]['online_refund_state'];
						if (tstat == 1) {
							td4.innerHTML = "处理中";
						} else if (tstat == 2) {
							td4.innerHTML = "成功";
						} else if (tstat == 3) {
							td4.innerHTML = "失败";
						} else if (tstat == 4) {
							td4.innerHTML = "请求银行失败";
						} else {
							td4.innerHTML = "无";
						}
						var td5 = tr.insertCell(5);
						td5.innerHTML = "00";
					}
				}
			}
		}
		var trcount = oriTbody.getElementsByTagName("tr");
		if(trcount.length == 0){
			var tr = oriTbody.insertRow(0);
			var td = tr.insertCell(0);
			td.setAttribute("colspan","6");
			td.innerHTML = "无相关交易";
		}
	}
	
	//处理对账文件的相关交易
	function queryDzData(errorDataLst) {
		var req_sys_stance = errorDataLst['req_sys_stance'];
		var inst_type = errorDataLst['inst_type'];
		var tradeTime = errorDataLst['trade_time'];
		var deduct_sys_id = errorDataLst['deduct_sys_id'];
		var additional_response_data = errorDataLst['additional_response_data'];
		var additional_data = errorDataLst['additional_data'];
		var deduct_sys_reference = errorDataLst['deduct_sys_reference'];
		var custom_define_info = errorDataLst['custom_define_info'];
		if (inst_type == 1 && deduct_sys_id == 0) {
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryDzDataByReqSysStance.do',
			data : "req_sys_stance="+ req_sys_stance+"&deduct_sys_id=" +deduct_sys_id+"&inst_type="+inst_type+"&tradeTime="+tradeTime+"&additional_response_data="+additional_response_data+
				"&deduct_sys_reference="+deduct_sys_reference+"&additional_data="+additional_data+"&custom_define_info="+custom_define_info,
			async:false,
			dataType:'text',
			success : function(yhtrade) {
				var flag = yhtrade.indexOf("error") > -1;
				if(yhtrade != "[]" && flag == false){
					var trade = eval("("+yhtrade+")");
					$("#yh_req_sys_stance").html(trade[0].reqSysStance);
					$("#yh_trade_time").html(trade[0].reqTime);
					$("#yh_out_account").html(trade[0].outAccount);
					$("#yh_trade_amount").html(trade[0].tradeAmount);
					$("#yh_trade_fee").html(trade[0].tradeFee);
					$("#yh_trade_result").html("成功");
					$("#yh_process").html(trade[0].process);
					$("#yh_deduct_sys_response").html("00");
					$("#yh_deduct_mer_code").html(trade[0].merCode);
					$("#yh_deduct_mer_term_id").html(trade[0].termId);
					$("#yh_deduct_sys_reference").html(trade[0].deductSysReference);
					$("#yh_deduct_stlm_date").html(trade[0].deduct_stlm_date);
				} else {
					clearBankForm();
				}
			},error : function(msg) {
    			alert("获取银行详情错误");
    		}
		});
	}
	
	function selectErrorDetail(tradeId) {
		$.ajax({
			type:"post",
			url:'<%=request.getContextPath()%>/queryErrorDataLstDetailByTradeId.do',
			data:"tradeId="+tradeId,
			async:false,
			success:function(map) {
			    if (map != null) {
			    	if (map.errorDataLst != null) {
			    		//获取差错来源
			    		var error_resource = map.errorDataLst['error_resource'];
			    		//如果是原始差错，则电银详情和银行详情都要显示
			    		if (error_resource == 0) {
			    			queryDyData(map.errorDataLst);
			    			//原始相关交易
			    			queryDyOriginalData(map);
			    			//对账文件相关交易
			    			queryDzData(map.errorDataLst);
			    		} else {
							clearDyForm();
							var oriTbody = document.getElementById("oriTbody");
							while(oriTbody.firstChild){
								oriTbody.removeChild(oriTbody.firstChild);
							}
							var trcount = oriTbody.getElementsByTagName("tr");
							if(trcount.length == 0){
								var tr = oriTbody.insertRow(0);
								var td = tr.insertCell(0);
								td.setAttribute("colspan","6");
								td.innerHTML = "无相关交易";
							}
							queryYhData(map.errorDataLst);
			    		}
			    		
			    		//显示扣款渠道(银行)列表相关交易
						var dzTbody = document.getElementById("dzTbody");
						while(dzTbody.firstChild){
							dzTbody.removeChild(dzTbody.firstChild);
						}
						var duizhangDataList = map.duizhangDataList;
						var instType = map.instType;
						if (duizhangDataList != null) {
							for(var i = 0;i < duizhangDataList.length; i++){
								var tr = dzTbody.insertRow(i);
								var td = tr.insertCell(0);
								if (instType == 0) {
									td.innerHTML = duizhangDataList[i]["reqSysStance"];
								} else {
									td.innerHTML = duizhangDataList[i]["reqSysStance"]+"|"+duizhangDataList[i]["orderId"];
								}
								var td1 = tr.insertCell(1);
								td1.innerHTML = duizhangDataList[i]["reqTime"];
								var td2 = tr.insertCell(2);
								var td3 = tr.insertCell(3);
								if (instType == 0) {
									td2.innerHTML = '消费';
									td3.innerHTML = "消费";
								} else {
									if (map.errorDataLst['trademsg_type'] == 2) {
										td2.innerHTML = '收款交易';
										td3.innerHTML = "收款交易";
									}
									if (map.errorDataLst['trademsg_type'] == 20) {
										td2.innerHTML = '退款交易';
										td3.innerHTML = "退款交易";
									}
								}
								var td4 = tr.insertCell(4);
								td4.innerHTML = "成功";
								var td5 = tr.insertCell(5);
								td5.innerHTML = "00";
							}
						} else {
							var trcount = dzTbody.getElementsByTagName("tr");
							if(trcount.length == 0){
								var tr = dzTbody.insertRow(0);
								var td = tr.insertCell(0);
								td.setAttribute("colspan","6");
								td.innerHTML = "无相关交易";
							}
						}
			    		$("#pop1").css({display:"block"});
			    	}
			    } else {
			    	alert("获取差错详细信息失败！");
					hide("pop1");
			    }
			}
		});
	}
	
	function clearEndTime(){
		$("#endTime").val("");
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
	//统计差错总金额
	function queryErrorTotalMoney() {
		var count = $("#tbody tr:first-child").find("td").length;
	  	if(count == 1){
	   		return;
	  	}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var js_date = $("#js_date").val();
		var whtherInnerJs = $("#whtherInnerJs").val();
		var channel = $("#channel").val();
		var additional_data = $("#additional_data").val();
		var whetherErroeHandle = $("#whetherErroeHandle").val();
		var handling_status = $("#handling_status").val();
		var req_sys_stance = $("#req_sys_stance").val();
		var error_resource = $("#error_resource").val();
		var handling_id = $("#handling_id").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryErrorTotalMoney.do',
    		type : 'post',
    		data : {"startTime": startTime, "endTime":endTime,"deduct_sys_reference":deduct_sys_reference,"js_date":js_date,
    				"whetherErroeHandle":whetherErroeHandle,"channel":channel,"handling_status":handling_status,"req_sys_stance":req_sys_stance,
    				"additional_data":additional_data,"whtherInnerJs":whtherInnerJs,"error_resource":error_resource,"handling_id":handling_id, "bank_id":bank_id},
    		async : false,
    		dataType : "text",
    		success : function(data) {
    			$("#errorTotalMoney").html(data);
    		},
    		error : function(data) {
    			alert("差错总金额统计异常!");
    		}
    	});
	}
</script>
</head>

<body onload="initBankInst();initErrorHandlerMethod();queryErrorTotalMoney(); initChannel();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">差错处理</a>&gt;<span>内部差错调整</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/getNeedAdjustData.do" target="right" name="needAdjustDataSearch" id="needAdjustDataSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="95%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">交易日期</td>
					                <td align="left" nowrap="nowrap" style="width: 25%;">
					                  <span style="width:30px;" class="input_bgl">
										 <input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }" 
										  	readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										  -
										 <input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }" 
										 	readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">参考号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="deduct_sys_reference" id="deduct_sys_reference" value="${param.deduct_sys_reference }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									  </span>
					                </td>
					               	<td align="right" nowrap="nowrap">差错结算日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
										<input name="js_date" id="js_date" value="${param.js_date }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">清算状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="whtherInnerJs" name="whtherInnerJs" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">未内部清算</option>
												<option value="1">已内部清算</option>
											</select>
											<input type="hidden" id="whther_Inner_Js_hidden" value="${whtherInnerJs }"/>
					                     </span>
					                </td>
					            	<td align="right" nowrap="nowrap">处理方式</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="handling_id" name="handling_id" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="handling_name_hidden" value="${handling_name }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">订单号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="additional_data" id="additional_data" value="${param.additional_data }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">差错类型</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="whetherErroeHandle" name="whetherErroeHandle" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">正常</option>
												<option value="1">长款</option>
												<option value="2">短款</option>
												<option value="3">金额不符</option>
												<option value="4">短款(清算未对账)</option>
											</select>
											<input type="hidden" id="error_type_hidden" value="${error_type }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="handling_status" name="handling_status" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">未处理</option>
												<option value="1">待审核</option>
												<option value="2">已审核</option>
												<option value="3">已驳回</option>
											</select>
											<input type="hidden" id="handling_status_hidden" value="${handling_status }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="req_sys_stance" id="req_sys_stance" value="${param.req_sys_stance }"/>
					                     </span>
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
											<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">差错来源</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="error_resource" name="error_resource" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">原始交易差错</option>
												<option value="1">对账文件差错</option>
											</select>
											<input type="hidden" id="error_resource_hidden" value="${error_resource }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
						            <td colspan="8" align="center" style="height: 30px"> 
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" /> 
						                <input type="button" class="icon_normal" value="重置" onclick="clearForm()" />
						                <input type="button" class="icon_normal" value="下载xls报表" onclick="downExcel()" />
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
					<c:if test="${fn:length(needAdjustData.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(needAdjustData.result) > 0 }">${fn:length(needAdjustData.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right;">
					差错总金额：
					<font color="red">
						<span id="errorTotalMoney">0.00</span>
					</font>
					元
					&nbsp;&nbsp;&nbsp;&nbsp;
					共
					<font color="red">
						<c:if test="${needAdjustData.totalItems == null }">0</c:if>
						<c:if test="${needAdjustData.totalItems != null }">${needAdjustData.totalItems }</c:if>
					</font>
					条数据
					<font color="red">
						<c:if test="${needAdjustData.totalPages == null}">0</c:if>
						<c:if test="${needAdjustData.totalPages != null}">${needAdjustData.totalPages}</c:if>
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
			<div class="table-m">
				<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
				<table width="1650px;" border="0" cellspacing="0" >
					<thead>
						<tr>
							<td align="center" rowspan="2">流水号</td>
							<td align="center" colspan="4">电银内部交易情况</td>
							<td align="center" colspan="4">扣款渠道交易情况</td>
							<td align="center" rowspan="2">扣款渠道</td>
							<td align="center" rowspan="2">差错类型</td>
							<td align="center" rowspan="2">状态</td>
							<td align="center" rowspan="2">差错来源</td>
							<td align="center" rowspan="2">驳回原因</td>
							<td align="center" rowspan="2">处理方式</td>
							<td align="center" rowspan="2">处理时间</td>
							<td align="center" rowspan="2">差错清算日期</td>
							<td align="center" rowspan="2">重对账备注</td>
							<td align="center" rowspan="2">文件名称</td>
							<td rowspan="2">操作</td>
						</tr>
						<tr>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">交易类型</td>
							<td align="center">交易结果</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">交易类型</td>
							<td align="center">交易结果</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:if test="${fn:length(needAdjustData.result)<=0 }">
							<tr align="center">
								<td colspan="15">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${needAdjustData.result }" var="adjustData">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${adjustData.deduct_sys_stance}</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										<c:if test="${adjustData.deduct_roll_bk == 0 }">
											${fn:substring(adjustData.deduct_sys_time,0,19)}
										</c:if>
										<c:if test="${adjustData.deduct_roll_bk == 1 }">
											${fn:substring(adjustData.deduct_rollbk_sys_time,0,19)}
										</c:if>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										<f:formatNumber value="${adjustData.trade_amount }" pattern="0.00"></f:formatNumber>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										<c:if test="${adjustData.inst_type == 0 }">
											${adjustData.tradeType }
										</c:if>
										<c:if test="${adjustData.inst_type == 1 }">
											<c:if test="${adjustData.trademsg_type == 2 }">收款交易</c:if>
											<c:if test="${adjustData.trademsg_type == 20 }">退款交易</c:if>
										</c:if>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										<c:if test="${adjustData.deduct_roll_bk == 0 }">
											<c:if test="${adjustData.deduct_sys_response == '00' }">成功</c:if>
											<c:if test="${adjustData.deduct_sys_response == 'N1' }">超时</c:if>
											<c:if test="${adjustData.deduct_sys_response != 'N1' && adjustData.deduct_sys_response != '00'}">失败</c:if>
										</c:if>
										<c:if test="${adjustData.deduct_roll_bk == 1 }">
											<c:if test="${adjustData.deduct_roll_bk_response == '00' }">成功</c:if>
											<c:if test="${adjustData.deduct_roll_bk_response == 'N1' }">超时</c:if>
											<c:if test="${adjustData.deduct_roll_bk_response != 'N1' && adjustData.deduct_roll_bk_response != '00'}">失败</c:if>
										</c:if>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										${adjustData.dzTime }
									</c:if>
									<c:if test="${adjustData.error_resource == 1 }">
										${fn:substring(adjustData.trade_time,0,19)}
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										<f:formatNumber value="${adjustData.dzTradeAmount }" pattern="0.00"></f:formatNumber>
									</c:if>
									<c:if test="${adjustData.error_resource == 1 }">
										<f:formatNumber value="${adjustData.trade_amount }" pattern="0.00"></f:formatNumber>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">
										${adjustData.dzTradeType }
									</c:if>
									<c:if test="${adjustData.error_resource == 1 }">
										<c:if test="${adjustData.inst_type == 0 }">
											${adjustData.tradeType }
										</c:if>
										<c:if test="${adjustData.inst_type == 1 }">
											<c:if test="${adjustData.trademsg_type == 2 }">收款交易</c:if>
											<c:if test="${adjustData.trademsg_type == 20 }">退款交易</c:if>
										</c:if>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">${adjustData.dzTradeResult}</c:if>
									<c:if test="${adjustData.error_resource == 1 }">成功</c:if>
								</td>
								<td align="center">${adjustData.name_ }</td>
								<td align="center">
									<c:if test="${adjustData.whetherErroeHandle == 0 }">正常</c:if>
									<c:if test="${adjustData.whetherErroeHandle == 1 }">长款</c:if>
									<c:if test="${adjustData.whetherErroeHandle == 2 }">短款</c:if>
									<c:if test="${adjustData.whetherErroeHandle == 3 }">金额不符</c:if>
									<c:if test="${adjustData.whetherErroeHandle == 4 }">短款(清算未对账)</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.handling_status == 0 }">未处理</c:if>
									<c:if test="${adjustData.handling_status == 1 }">待审核</c:if>
									<c:if test="${adjustData.handling_status == 2 }">已审核</c:if>
									<c:if test="${adjustData.handling_status == 3 }">已驳回</c:if>
								</td>
								<td align="center">
									<c:if test="${adjustData.error_resource == 0 }">原始交易差错</c:if>
									<c:if test="${adjustData.error_resource == 1 }">对账文件差错</c:if>
								</td>
								<td align="center">${adjustData.turnDown_remark}</td>
								<td align="center">${adjustData.handling_name}</td>
								<td align="center">${adjustData.handling_time}</td>
								<td align="center">${adjustData.js_date}</td>
								<td align="center">${adjustData.cdz_remark}</td>
								<td align="center">${adjustData.nii}</td>
								<td>
									<a class="fl lj mr10" href="javascript:void(0);" onclick="selectErrorDetail('${adjustData.trade_id}')">详情</a>
									<c:if test="${adjustData.handling_status == 0 || adjustData.handling_status == 3}">
										<a class="fl lj mr10" href="javascript:void(0);" onclick="selectData('${adjustData.trade_id}', '${adjustData.handling_id }', '${adjustData.handler_remark }', '${adjustData.js_date }', '${adjustData.inst_type }', '${adjustData.bank_id }', '${adjustData.deduct_sys_id }', '${sessionScope.login.loginName}')">调整</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${needAdjustData.totalPages != null}">
				<div class="next">
					<c:if test="${needAdjustData.pageNo > 1}">
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo > 1}">
						<a href="javascript:paging(${needAdjustData.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${needAdjustData.pageNo-2 > 0}">
						<a href="javascript:paging(${needAdjustData.pageNo-2 })"><span>${needAdjustData.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo-1 > 0}">
						<a href="javascript:paging(${needAdjustData.pageNo-1 })"><span>${needAdjustData.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${needAdjustData.pageNo }</span></a>
					<c:if test="${needAdjustData.pageNo+1 <= needAdjustData.totalPages}">
						<a href="javascript:paging(${needAdjustData.pageNo+1 })"><span>${needAdjustData.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo+2 <= needAdjustData.totalPages}">
						<a href="javascript:paging(${needAdjustData.pageNo+2 })"><span>${needAdjustData.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo+3 <= needAdjustData.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${needAdjustData.pageNo < needAdjustData.totalPages}">
						<a href="javascript:paging(${needAdjustData.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<c:if test="${needAdjustData.pageNo > 1}">
						<a href="javascript:paging(${needAdjustData.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${needAdjustData.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${needAdjustData.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="pop1" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">电银交易详情</span>
				 <a class="close" href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="errorDetail">
				<table width="100%" cellspacing="0" id="operator">
					<tr>
						<td align="right" bgcolor="#eeeeee" width="150px;">交易流水：</td>
						<td id="dy_req_sys_stance" width="150px;"></td>
						<td align="right" bgcolor="#eeeeee" width="150px;">交易时间：</td>
						<td id="dy_trade_time" width="150px;"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">转出卡号：</td>
						<td id="dy_out_account"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="dy_trade_amount"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">电银手续费：</td>
						<td id="dy_trade_fee"></td>
						<td align="right" bgcolor="#eeeeee">交易结果：</td>
						<td id="dy_trade_result"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="dy_req_process"></td>
						<td align="right" bgcolor="#eeeeee">交易应答码：</td>
						<td id="dy_req_response"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">内部系统商户号：</td>
						<td id="dy_req_mer_code"></td>
						<td align="right" bgcolor="#eeeeee">内部系统终端号：</td>
						<td id="dy_req_mer_term_id"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款终端号：</td>
						<td id="dy_deduct_mer_term_id"></td>
						<td align="right" bgcolor="#eeeeee">商户名称：</td>
						<td id="dy_mer_name"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款机构号：</td>
						<td id="dy_deduct_sys_id"></td>
						<td align="right" bgcolor="#eeeeee">扣款商户号：</td>
						<td id="dy_deduct_mer_code"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款结果：</td>
						<td id="dy_deduct_result"></td>
						<td align="right" bgcolor="#eeeeee">扣款应答码：</td>
						<td id="dy_deduct_sys_response"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款流水：</td>
						<td id="dy_deduct_sys_stance"></td>
						<td align="right" bgcolor="#eeeeee">冲正流水：</td>
						<td id="dy_deduct_roll_bk_stance"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">消息类型：</td>
						<td id="dy_trademsg_type"></td>
						<td align="right" bgcolor="#eeeeee">冲正应答码：</td>
						<td id="dy_deduct_roll_bk_response"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否有冲正标志：</td>
						<td id="dy_deduct_roll_bk"></td>
						<td align="right" bgcolor="#eeeeee">冲正结果状态：</td>
						<td id="dy_deduct_roll_bk_result"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理备注：</td>
						<td id="dy_handler_remark"></td>
						<td align="right" bgcolor="#eeeeee">驳回原因：</td>
						<td id="dy_turnDown_remark"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">清算日期：</td>
						<td id="dy_deduct_stlm_date"></td>
						<td align="right" bgcolor="#eeeeee">订单号：</td>
						<td id="dy_additional_data"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否有撤销交易：</td>
						<td id="dy_che_trademsg_type"></td>
						<td align="right" bgcolor="#eeeeee">撤销结果状态：</td>
						<td id="dy_che_status"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否有退货交易：</td>
						<td id="dy_tui_trademsg_type"></td>
						<td align="right" bgcolor="#eeeeee">唯一检索号：</td>
						<td id="dy_additional_response_data"></td>
					</tr>
					<tr style="background-color:#eeeeee;">
						<td align="center" colspan="4">相关交易</td>
					</tr>
					<tr>
						<td colspan="4">
							<table width="100%" border="1">
								<tr>
									<th>交易流水号</th>
									<th>交易时间</th>
									<th>交易类型</th>
									<th>交易类别</th>
									<th>交易结果</th>
									<th>应答码</th>
								</tr>
								<tbody id="oriTbody" align="center"></tbody>
							</table>
						</td>
					</tr>
				</table>
				<h1 class="pop_tit">
				<span class="fl">银行交易详情</span>
				</h1>
				<div class="yhErrorDetail">
					<table width="100%" cellspacing="0" id="dzData">
						<tr>
							<td align="right" bgcolor="#eeeeee" width="150px;">交易流水：</td>
							<td id="yh_req_sys_stance" width="150px;"></td>
							<td align="right" bgcolor="#eeeeee" width="150px;">交易时间：</td>
							<td id="yh_trade_time" width="150px;"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">转出卡号：</td>
							<td id="yh_out_account"></td>
							<td align="right" bgcolor="#eeeeee">交易金额：</td>
							<td id="yh_trade_amount"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银行手续费：</td>
							<td id="yh_trade_fee"></td>
							<td align="right" bgcolor="#eeeeee">交易结果：</td>
							<td id="yh_trade_result"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">处理码：</td>
							<td id="yh_process"></td>
							<td align="right" bgcolor="#eeeeee">应答码：</td>
							<td id="yh_deduct_sys_response"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银联商户号：</td>
							<td id="yh_deduct_mer_code"></td>
							<td align="right" bgcolor="#eeeeee">银联终端号：</td>
							<td id="yh_deduct_mer_term_id"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银联参考号：</td>
							<td id="yh_deduct_sys_reference"></td>
							<td align="right" bgcolor="#eeeeee">清算日期：</td>
							<td id="yh_deduct_stlm_date"></td>
						</tr>
						<tr style="background-color:#eeeeee;">
							<td align="center" colspan="4">相关交易</td>
						</tr>
						<tr>
							<td colspan="4">
								<table width="100%" border="1" style="">
									<tr>
										<th>流水号|订单号</th>
										<th>交易时间</th>
										<th>交易类型</th>
										<th>交易类别</th>
										<th>交易结果</th>
										<th>应答码</th>
									</tr>
									<tbody id="dzTbody" align="center"></tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		</div>
		
		<!-- 调整操作弹出框 -->
		<div id="update" class="pop" style="display: none;OVERFLOW: auto;margin-top: 10px;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">差错调整</span> 
				<a class="close" href="javascript:void(0);" onclick="hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">				
					<tr>
						<td align="right" width="40%">处理方式：</td>
						<td>
							<span class="input_bgl">
					    		<select id="handingName" name="handingName"  style="width: 155px;"></select>
							</span><font color="red">*</font>
							<input type="hidden" id="trade_id" />
						</td>
					</tr>
					<tr>
					<td align="right">备注：</td>
					<td>
						<textarea rows="3" cols="25" style="resize:none;" id="handler_remark" name="handler_remark"></textarea>
						<font color="red">*</font>
					</td>
					</tr>
					<tr>
						<td align="right">结算日期：</td>
						<td>
						  <input type="text" id="js_date_" name="js_date_" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-#/{%d-1}'})" />
							<font color="red">*(选择前一天)</font>
						</td>
					</tr>
					<tr id="instId">
						<td align="right">扣款渠道：</td>
						<td>
							<span class="input_bgl">
					    		<select id="instName" name="instName" style="width: 155px;"></select>
							</span><font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="updateErrorAdjust();" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('update');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
