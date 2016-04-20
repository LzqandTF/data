<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账明细查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['original_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
			}
		});
		
		var whtherInner_Js = $("#whtherInner_Js_hidden").val();
		var whtherInnerJs = document.getElementById("whtherInnerJs");
		for (var i = 0; i < whtherInnerJs.length; i++) {
			if (whtherInnerJs.options[i].value == whtherInner_Js) {
				whtherInnerJs.options[i].selected = 'selected';
			}
		}
		
		var bk_chk = $("#bk_chk_hidden").val();
		var bkChk = document.getElementById("bk_chk");
		for (var i = 0; i < bkChk.length; i++) {
			if (bkChk.options[i].value == bk_chk) {
				bkChk.options[i].selected = 'selected';
			}
		}
		
		var whether_riqie = $("#whetherRiqie_hidden").val();
		var whetherRiqie = document.getElementById("whetherRiqie");
		for (var i = 0; i < whetherRiqie.length; i++) {
			if (whetherRiqie.options[i].value == whether_riqie) {
				whetherRiqie.options[i].selected = 'selected';
			}
		}
		
		var deduct_result = $("#deduct_result_hidden").val();
		var deductResult = document.getElementById("deduct_result");
		for (var i = 0; i < deductResult.length; i++) {
			if (deductResult.options[i].value == deduct_result) {
				deductResult.options[i].selected = 'selected';
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
		
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("channel");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == inst_id){
				type.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
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
	
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		
		var mflag = 2;
		var dzSuccessMoney = $("#dzSuccessMoney").text();
		var merFee = $("#merFee").text();
		var dzFailMoney = $("#dzFailMoney").text();
		var noDzMoney = $("#noDzMoney").text();
		var noDzMoney1 = $("#noDzMoney1").text();
		var dzSuccessMoney1 = $("#dzSuccessMoney1").text();
		var dzFailMoney1 = $("#dzFailMoney1").text();
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangOriginalData.do?pageNum=" + pageNo + "&pageSize=" + pageSize+"&mflag="+mflag+"&dzSuccessMoney="+dzSuccessMoney+"&merFee="+merFee+
					"&dzFailMoney="+dzFailMoney+"&noDzMoney="+noDzMoney+"&noDzMoney1="+noDzMoney1+"&dzSuccessMoney1="+dzSuccessMoney1+"&dzFailMoney1="+dzFailMoney1;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("originalData");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		
		var mflag = 2;
		var dzSuccessMoney = $("#dzSuccessMoney").text();
		var merFee = $("#merFee").text();
		var dzFailMoney = $("#dzFailMoney").text();
		var noDzMoney = $("#noDzMoney").text();
		var noDzMoney1 = $("#noDzMoney1").text();
		var dzSuccessMoney1 = $("#dzSuccessMoney1").text();
		var dzFailMoney1 = $("#dzFailMoney1").text();
		
		
		/* var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var deduct_sys_stance = $("#deduct_sys_stance").val();
		var deduct_mer_code = $("#deduct_mer_code").val();
		var out_account = $("#out_account").val();
		var req_mer_term_id = $("#req_mer_term_id").val();
		var additional_data = $("#additional_data").val();
		var whtherInnerJs = $("#whtherInnerJs").val();
		var req_mer_code = $("#req_mer_code").val();
		var bk_chk = $("#bk_chk").val();
		var whetherRiqie = $("#whetherRiqie").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var deduct_result = $("#deduct_result").val();
		var channel = $("#channel").val(); */
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangOriginalData.do?pageSize=" + pageSize + "&mflag="+mflag+"&dzSuccessMoney="+dzSuccessMoney+"&merFee="+merFee+
					 "&dzFailMoney="+dzFailMoney+"&noDzMoney="+noDzMoney+"&noDzMoney1="+noDzMoney1+"&dzSuccessMoney1="+dzSuccessMoney1+"&dzFailMoney1="+dzFailMoney1;
					 /*"&startTime="+startTime+
					"&endTime="+endTime+"&deduct_stlm_date="+deduct_stlm_date+
					"&deduct_sys_stance="+deduct_sys_stance+"&deduct_mer_code="+deduct_mer_code+
					"&channel="+channel+"&out_account="+out_account+
					"&req_mer_term_id="+req_mer_term_id+"&additional_data="+additional_data+
					"&whtherInnerJs="+whtherInnerJs+"&req_mer_code="+req_mer_code+
					"&bk_chk="+bk_chk+"&whetherRiqie="+whetherRiqie+
					"&deduct_sys_reference="+deduct_sys_reference+"&deduct_result="+deduct_result+"&bank_id="+bank_id; */
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
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		
		var mflag = 1;
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangOriginalData.do?pageSize=" + pageSize+"&mflag="+mflag;
			method = "post";
			form.submit();
		}
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//根据ID获取详细信息
	function queryDetail(trade_id){
		var bank_id = $("#bank_id").val();
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryOriginalDataDetail.do',
			data : {"trade_id": trade_id, "bank_id" : bank_id},
			async:false,
			success : function(trade) {
				if(trade != null){
					$("#req_sys_stance1").html(trade.req_sys_stance);
					var tradeTime = trade.trade_time;
					if (tradeTime != null && tradeTime != "") {
						$("#trade_time1").html(tradeTime.substring(0,19));
					}
					$("#out_account1").html(trade.out_account);
					$("#trade_amount1").html(trade.trade_amount);
					$("#req_process1").html(trade.req_process);
					$("#trademsg_type1").html(trade.trademsg_type);
					$("#req_mer_code11").html(trade.req_mer_code);
					$("#req_mer_term_id1").html(trade.req_mer_term_id);
					$("#trade_fee1").html(trade.trade_fee);
					$("#trade_type1").html(trade.tradeType);
					$("#zf_fee1").html(trade.zf_fee);
					$("#mer_fee1").html(trade.mer_fee);
					$("#req_response1").html(trade.req_response);
					var deductRollBk = trade.deduct_roll_bk;
					if (0 == deductRollBk) {
						deductRollBk = "否";
					} else if (1 == deductRollBk) {
						deductRollBk = "是";
					} else {
						deductRollBk = trade.deduct_roll_bk;
					}
					$("#deduct_roll_bk1").html(deductRollBk);
					$("#deduct_roll_bk_stance1").html(trade.deduct_roll_bk_stance);
					var deduct_rollbk_sys_time = trade.deduct_rollbk_sys_time;
					if (deduct_rollbk_sys_time != null && deduct_rollbk_sys_time != "") {
						$("#deduct_rollbk_sys_time1").html(deduct_rollbk_sys_time.substring(0,19));
					}
					$("#deduct_roll_bk_response1").html(trade.deduct_roll_bk_response);
					var rollBkResult = trade.deduct_roll_bk_response;
					if ('00' == rollBkResult) {
						rollBkResult = "成功";
					} else if (null == rollBkResult) {
						rollBkResult = "无";
					} else {
						rollBkResult = "失败";
					}
					$("#deduct_roll_bk_result1").html(rollBkResult);
					
					$("#deduct_sys_stance1").html(trade.deduct_sys_stance);
					var deduct_sys_time = trade.deduct_sys_time;
					if (deduct_sys_time != null && deduct_sys_time != "") {
						$("#deduct_sys_time1").html(deduct_sys_time.substring(0,19));
					}
					var deductResult = trade.deduct_sys_response;
					$("#deduct_sys_response1").html(deductResult);
					if ('00' == deductResult) {
						deductResult = "成功";
					} else if (null == deductResult) {
						deductResult = "无";
					}else if ('N1' == deductResult) {
						deductResult = "超时";
					} else if (null != deductResult && '00' != deductResult) {
						deductResult = "失败";
					} 
					$("#deduct_result1").html(deductResult);
					$("#deduct_stlm_date1").html(trade.deduct_stlm_date);
					$("#zfFileFee").html(trade.zf_file_fee);
					$("#mer_name1").html(trade.mer_name);
					var additional_data = trade.additional_data;
					if (additional_data != null) {
						$("#additional_data1").html(additional_data.split('|')[0]);
					}
					$("#deduct_sys_reference1").html(trade.deduct_sys_reference);
					$("#authorization_code1").html(trade.authorization_code);
					$("#deduct_mer_code1").html(trade.deduct_mer_code);
					$("#deduct_mer_term_id1").html(trade.deduct_mer_term_id);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
	//清空表单查询条件
	function clearForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#deduct_sys_stance").val("");
		$("#out_account").val("");
		$("#req_mer_code").val("");
		$("#req_mer_term_id").val("");
		$("#deduct_mer_code").val("");
		$("#additional_data").val("");
		$("#whtherInnerJs").val("");
		$("#bk_chk").val("");
		$("#deduct_stlm_date").val("");
		$("#deduct_sys_reference").val("");
		$("#deduct_result").val("");
		$("#whetherRiqie").val("");
		$("#bank_id").val("");
		var selectObj = document.getElementById("channel");
		while(selectObj.firstChild) {
			selectObj.removeChild(selectObj.firstChild);
		}
		$(selectObj).append("<option value=''>全部</option>");
	}
	function clearEndTime() {
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
	//统计对账金额
	function queryMoney() {
		var c1 = $("#underLine tr:first-child").find("td").length;
		var c2 = $("onLine tr:first-child").find("td").length;
	  	if(c1 == 1 && c2 == 1){
	   		return;
	  	}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var deduct_sys_stance = $("#deduct_sys_stance").val();
		var deduct_mer_code = $("#deduct_mer_code").val();
		var out_account = $("#out_account").val();
		var req_mer_term_id = $("#req_mer_term_id").val();
		var additional_data = $("#additional_data").val();
		var whtherInnerJs = $("#whtherInnerJs").val();
		var req_mer_code = $("#req_mer_code").val();
		var bk_chk = $("#bk_chk").val();
		var whetherRiqie = $("#whetherRiqie").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var deduct_result = $("#deduct_result").val();
		
		var bank_id = $("#bank_id").val();
		if (bank_id == "") {
			return;
		}
		if (bk_chk == 3) {
			return;
		}
		var channel = $("#channel").val();
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryDYMoney.do',
    		type : 'post',
    		data : {"startTime": startTime, "endTime":endTime,"deduct_stlm_date":deduct_stlm_date,"deduct_sys_stance":deduct_sys_stance,
    				"deduct_mer_code":deduct_mer_code,"channel":channel,"out_account":out_account,"req_mer_term_id":req_mer_term_id,
    				"additional_data":additional_data,"whtherInnerJs":whtherInnerJs,"req_mer_code":req_mer_code,"bk_chk":bk_chk,
    				"whetherRiqie":whetherRiqie, "deduct_sys_reference":deduct_sys_reference, "deduct_result":deduct_result, "bank_id":bank_id},
    		async : false,
    		success : function(data) {
    			if (data != null) {
    				var obj = eval("("+data+")");
    				var bkChk = $("#bk_chk").val();
   					for(var i=0;i<obj.length;i++){
   						if (bkChk == '') {
   							$("#noDzMoney").html(obj[i]["noDzMoney"]);
							$("#dzSuccessMoney").html(obj[i]["dzSuccessMoney"]);
							$("#merFee").html(obj[i]["merFee"]);
							$("#dzFailMoney").html(obj[i]["dzFailMoney"]);
							$("#noNeedMoney").html(obj[i]["noNeedMoney"]);
   						} else if (bkChk == 0) {
   	    					$("#noDzMoney1").html(obj[i]["allMoney"]);
   	    				} else if (bkChk == 1) {
   	    					$("#dzSuccessMoney1").html(obj[i]["allMoney"]);
   	    					$("#merFee").html(obj[i]["merFee"]);
   	    				} else if (bkChk == 2) {
   	    					$("#dzFailMoney1").html(obj[i]["allMoney"]);
   	    				} 
					 }
    			}
    		},
    		error : function(data) {
    			alert("对账金额统计异常!");
    		}
    	});
	}
	
	//下载excel表格
	function downExcel(){
		//根据查询条件下载
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var deduct_sys_stance = $("#deduct_sys_stance").val();
		var deduct_mer_code = $("#deduct_mer_code").val();
		var out_account = $("#out_account").val();
		var req_mer_term_id = $("#req_mer_term_id").val();
		var additional_data = $("#additional_data").val();
		var whtherInnerJs = $("#whtherInnerJs").val();
		var req_mer_code = $("#req_mer_code").val();
		var bk_chk = $("#bk_chk").val();
		var whetherRiqie = $("#whetherRiqie").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var deduct_result = $("#deduct_result").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var channel = $("#channel").val();
		
		var url ="<%=request.getContextPath()%>/dzDetailDownExcel.do?startTime="+startTime+
				"&endTime="+endTime+"&deduct_stlm_date="+deduct_stlm_date+
				"&deduct_sys_stance="+deduct_sys_stance+"&deduct_mer_code="+deduct_mer_code+
				"&channel="+channel+"&out_account="+out_account+
				"&req_mer_term_id="+req_mer_term_id+"&additional_data="+additional_data+
				"&whtherInnerJs="+whtherInnerJs+"&req_mer_code="+req_mer_code+
				"&bk_chk="+bk_chk+"&whetherRiqie="+whetherRiqie+
				"&deduct_sys_reference="+deduct_sys_reference+"&deduct_result="+deduct_result+"&bank_id="+bank_id;
		window.location=url;
	}
	
	function querySkDetail(tesq) {
		var bank_id = $("#bank_id").val();
		var gate = $("#channel").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryRytSkDataDetail.do",
			data : {"gate":gate,"tesq":tesq, "bank_id":bank_id},
			async : false,
			success : function(ryt) {
				if (ryt != null) {
					$("#sys_date_sys_time").html(ryt.sys_date);
					$("#s_mid").html(ryt.mid);
					$("#s_oid").html(ryt.oid);
					$("#s_amount").html(ryt.amount);
					$("#s_type").html(ryt.tradeType);
					$("#s_gate").html(ryt.gate);
					$("#s_gid").html(ryt.gid);
					$("#s_card_no").html(ryt.card_no);
					$("#s_p15").html(ryt.deduct_stlm_date);
					$("#s_tseq").html(ryt.tseq);
					$("#s_bk_seq1").html(ryt.bk_seq1);
					var stat = ryt.tstat;
					if (0 == stat) {
						stat = "初始状态";
					} else if (1 == stat) {
						stat = "待支付";
					} else if (2 == stat) {
						stat = "成功";
					} else if (3 == stat) {
						stat = "失败";
					} else if (4 == stat) {
						stat = "请求银行失败";
					} else if (5 == stat) {
						stat = "撤销";
					} else {
						stat = "其他";
					}
					$("#s_tstat").html(stat);
					$("#s_bk_resp").html(ryt.bk_resp);
					$("#s_fee_amt").html(ryt.fee_amt);
					$("#s_bank_fee").html(ryt.bank_fee);
					$("#s_pay_amt").html(ryt.pay_amt);
					$("#s_pre_amt1").html(ryt.pre_amt1);
					var whetherRiqie = ryt.whetherRiqie;
					if(whetherRiqie == 0){
						$("#s_whetherRiqie").html("否");
					}else{
						$("#s_whetherRiqie").html("是");
					}
					var whetherJs = ryt.whetherJs;
					if(whetherJs == 0){
						$("#s_whetherJs").html("否");
					}else{
						$("#s_whetherJs").html("是");
					}
					var bk_chk = ryt.bk_chk;
					if(bk_chk == 0){
						$("#s_bk_chk").html("未对账");
					}else if(bk_chk == 1){
						$("#s_bk_chk").html("对账成功");
					}else if(bk_chk == 2){
						$("#s_bk_chk").html("对账失败");
					}else if(bk_chk == 3){
						$("#s_bk_chk").html("无需对账");
					}
					
					$("#out_user_id").html(ryt.out_user_id);
					$("#in_user_id").html(ryt.in_user_id);
					$("#pop1").css({display:"block"});
				} else {
					alert("获取详细信息失败！");
					hide("pop1");
				}
			}
		});
	}
	
	  function initView() {
		  var mflag = $("#mflag_hidden").val();
		  if (mflag == 1) {
			  if (document.readyState == "complete") {
			  	  queryMoney();
			  }
		  }
	 }
	 
	  function initView1() {
		  var mflag = $("#mflag_hidden").val();
		  if (mflag == 2) {
			  if (document.readyState == "complete") {
				  var dzSuccessMoney = $("#dzSuccessMoney_hidden").val();
				  var merFee = $("#merFee_hidden").val();
				  var dzFailMoney = $("#dzFailMoney_hidden").val();
				  var noDzMoney = $("#noDzMoney_hidden").val();
				  var noDzMoney1 = $("#noDzMoney1_hidden").val();
				  var dzSuccessMoney1 = $("#dzSuccessMoney1_hidden").val();
				  var dzFailMoney1 = $("#dzFailMoney1_hidden").val();
				  
				  var bk_chk = $("#bk_chk_hidden").val();
				  if (bk_chk == null || bk_chk == "") {
					  $("#dzSuccessMoney").html(dzSuccessMoney);
					  $("#merFee").html(merFee);
					  $("#dzFailMoney").html(dzFailMoney);
					  $("#noDzMoney").html(noDzMoney);
				  } else if (bk_chk == 0) {
  					  $("#noDzMoney1").html(noDzMoney1);
  				  } else if (bk_chk == 1) {
  					  $("#dzSuccessMoney1").html(dzSuccessMoney1);
  					  $("#merFee").html(merFee);
  				  } else if (bk_chk == 2) {
  					  $("#dzFailMoney1").html(dzFailMoney1);
  				  }
			  }
		  }
	 }
	
	// 线上成功交易出现未对账情况，手动将该笔交易移至差错
	function moveDataToErrorDataLst(tseq, sys_date, card_no, amount, mer_fee, gid, mid, zf_fee, zf_file_fee, zf_fee_bj, whetherErroeHandle, oid, 
			type, gate, out_user_id, in_user_id, p14, whetherValid) {
		var bank_id = $("#bank_id").val();
		if(bank_id == null || bank_id == "") {
			alert("请选择银行机构");
			return;
		}
		if(!confirm("是否对该笔交易进行差错处理？此操作执行后将不可回退。")){
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/addDataToErrorDataLst.do',
			data : {"tseq": tseq, "sys_date":sys_date, "card_no":card_no, "amount":amount, "mer_fee":mer_fee, "mid":mid, "gid":gid, "zf_fee":zf_fee, "zf_file_fee":zf_file_fee, "zf_fee_bj":zf_fee_bj, "whetherErroeHandle":whetherErroeHandle, "oid":oid, "bank_id":bank_id, 
					"type":type, "gate":gate, "out_user_id":out_user_id, "in_user_id":in_user_id, "p14":p14, "whetherValid":whetherValid},
			async : false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功");
					paging(1);
				} else {
					alert("操作失败");
				}
			}
		});
	}
	
	function addUnderLineDataToErrorDataLst(tradeId) {
		var bank_id = $("#bank_id").val();
		if(bank_id == null || bank_id == "") {
			alert("请选择银行机构");
			return;
		}
		if(!confirm("是否对该笔交易进行差错处理？此操作执行后将不可回退。")){
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/addUnderLineDataToErrorDataLst.do',
			data : {"bank_id":bank_id, "tradeId": tradeId},
			async : false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功");
					paging(1);
				} else {
					alert("操作失败");
				}
			}
		});
	}
</script>

</head>
<!-- <body onload="initBankInst(); queryMoney(); initChannel();"> -->
<body onload="initBankInst(); initChannel();initView();initView1();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>对账明细查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>" target="right" name="originalData" id="originalData" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>		
							<table width="95%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">交易日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:30px;" class="input_bgl">
									  	<input style="width: 70px" id="startTime" name="startTime"  value="${param.startTime }" 
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										-
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }" 
											readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">清算日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:30px;" class="input_bgl">
									  	<input name="deduct_stlm_date" id="deduct_stlm_date" value="${param.deduct_stlm_date }" 
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="deduct_sys_stance" id="deduct_sys_stance" value="${param.deduct_sys_stance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">参考号</td>
					                 <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="deduct_sys_reference" id="deduct_sys_reference" value="${param.deduct_sys_reference }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td> 
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">银联商户号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="deduct_mer_code" id="deduct_mer_code" value="${param.deduct_mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td>
					                
					                 <td align="right" nowrap="nowrap">转出卡号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="out_account" id="out_account" value="${param.out_account }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">商户号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="req_mer_code" id="req_mer_code" value="${param.req_mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易状态</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:30px;" class="input_bgl">
									  	<select name="deduct_result" id="deduct_result" style="width: 150px;">
											<option value="">全部</option>
											<option value="0">初始状态</option>
											<option value="1">待支付</option>
											<option value="2">成功</option>
											<option value="3">失败</option>
											<option value="4">请求银行失败</option>
											<option value="5">撤销</option>
											<option value="6">超时</option>
										</select>
										<input type="hidden" id="deduct_result_hidden" value="${deduct_result }"/>
									  </span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">终端编号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="req_mer_term_id" id="req_mer_term_id" value="${param.req_mer_term_id }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td> 
					            	<td align="right" nowrap="nowrap">订单号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="additional_data" id="additional_data" value="${param.additional_data }" />
					                     </span>
					                </td>
					            	<td align="right" nowrap="nowrap">清算状态</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="whtherInnerJs" id="whtherInnerJs" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">未内部清算</option>
												<option value="1">已内部清算</option>
											</select>
											<input type="hidden" id="whtherInner_Js_hidden" value="${whtherInnerJs }"/>
										 </span>
					                </td>
					                <td align="right" nowrap="nowrap">是否日切</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="whetherRiqie" id="whetherRiqie">
												<option value="">全部</option>
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
											<input type="hidden" id="whetherRiqie_hidden" value="${whetherRiqie }"/>
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
					                <td align="right" nowrap="nowrap">对账结果</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="bk_chk" id="bk_chk">
												<option value="">全部</option>
												<option value="0">未对账</option>
												<option value="1">对账成功</option>
												<option value="2">对账失败</option>
												<option value="3">无需对账</option>
											</select>
											<input type="hidden" id="bk_chk_hidden" value="${bk_chk }"/>
										</span>
					                </td>
					            </tr>
					            <tr>
						            <td colspan="8" align="center" style="height: 30px"> 
					            		<input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					            		
					            		<input type="hidden" id="mflag_hidden" value="${mflag }"/>
					            		
					            		<input type="button" class="icon_normal" value="重置" onclick="clearForm();" />
					            		<input type="button" class="icon_normal" value="下载" onclick="downExcel();" />
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
		
		<input type="hidden" id="dzSuccessMoney_hidden" value="${dzSuccessMoney }"/>
		<input type="hidden" id="merFee_hidden" value="${merFee }"/>
		<input type="hidden" id="dzFailMoney_hidden" value="${dzFailMoney }"/>
		<input type="hidden" id="noDzMoney_hidden" value="${noDzMoney }"/>
		<input type="hidden" id="noDzMoney1_hidden" value="${noDzMoney1 }"/>
		<input type="hidden" id="dzSuccessMoney1_hidden" value="${dzSuccessMoney1 }"/>
		<input type="hidden" id="dzFailMoney1_hidden" value="${dzFailMoney1 }"/>
		
		<c:if test="${bank_type == '' || bank_type == null || bank_type == 0 }">
			<div style="font-size: 12px;">
				<span>
					本页共
					<font color="red">
						<c:if test="${fn:length(getDataResult.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(getDataResult.result) > 0 }">${fn:length(getDataResult.result) }</c:if>
					</font>
					条数据
				</span>
				<span style="float: right;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					共
					<font color="red">
						<c:if test="${getDataResult.totalItems == null }">0</c:if>
						<c:if test="${getDataResult.totalItems != null }">${getDataResult.totalItems }</c:if>
					</font>
					条数据
					<font color="red">
						<c:if test="${getDataResult.totalPages == null}">0</c:if>
						<c:if test="${getDataResult.totalPages != null}">${getDataResult.totalPages}</c:if>
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
					<c:if test="${bk_chk == '' || bk_chk == null }">
						对账成功金额：
						<font color="red">
							<span id="dzSuccessMoney">0.00</span>
						</font>
						元
						&nbsp;&nbsp;
						商户手续费：
						<font color="red">
							<span id="merFee">0.00</span>
						</font>
						&nbsp;&nbsp;
						对账失败金额：
						<font color="red">
							<span id="dzFailMoney">0.00</span>
						</font>
						元 
						&nbsp;&nbsp;
						未对账金额：
						<font color="red">
							<span id="noDzMoney">0.00</span>
						</font>
						元
					 	&nbsp;&nbsp;
					</c:if>
					<c:if test="${bk_chk == '0' }">
						未对账金额：
						<font color="red">
							<span id="noDzMoney1">0.00</span>
						</font>
						元
					</c:if>
					<c:if test="${bk_chk == '1' }">
						对账成功金额：
						<font color="red">
							<span id="dzSuccessMoney1">0.00</span>
						</font>
						元
						&nbsp;&nbsp;
						商户手续费：
						<font color="red">
							<span id="merFee">0.00</span>
						</font>
					</c:if>
					<c:if test="${bk_chk == '2' }">
						对账失败金额：
						<font color="red">
							<span id="dzFailMoney1">0.00</span>
						</font>
						元
					</c:if>
				</span>
			</div>
			
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">交易流水号</td>
							<td align="center">商户订单号</td>
							<td align="center">电银商户号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">商户手续费</td>
							<td align="center">交易状态</td>
							<td align="center">交易类别</td>
							<td align="center">扣款渠道</td>
							<td align="center">是否清算</td>
							<td align="center">对账结果</td>
							<td align="center">是否日切</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<tbody id="underLine">
						<c:if test="${fn:length(getDataResult.result)<=0 }">
							<tr align="center">
								<td colspan="14">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${getDataResult.result }" var="tradeLst">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td>
									<c:if test="${tradeLst.deduct_roll_bk == 0 }">
										${tradeLst.deduct_sys_stance }
									</c:if>
									<c:if test="${tradeLst.deduct_roll_bk == 1 }">
										${tradeLst.deduct_roll_bk_stance }
									</c:if>
								</td>
								<td align="center">${tradeLst.additional_data}</td>
								<td align="center">${tradeLst.req_mer_code}</td>
								<td align="center">
									<c:if test="${tradeLst.deduct_roll_bk == 0 }">
										${fn:substring(tradeLst.deduct_sys_time,0,19)}
									</c:if>
									<c:if test="${tradeLst.deduct_roll_bk == 1 }">
										${fn:substring(tradeLst.deduct_rollbk_sys_time,0,19)}
									</c:if>
								</td>
								<td align="center">
									<f:formatNumber value="${tradeLst.trade_amount }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${tradeLst.mer_fee }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<c:if test="${tradeLst.deduct_roll_bk == 0 }">
										<c:if test="${tradeLst.deduct_result == 0 }">成功</c:if>
										<c:if test="${tradeLst.deduct_result == 1 }">超时</c:if>
										<c:if test="${tradeLst.deduct_result != 0 && tradeLst.deduct_result != 1}">失败</c:if>
									</c:if>
									<c:if test="${tradeLst.deduct_roll_bk == 1 }">
										<c:if test="${tradeLst.deduct_roll_bk_result == 0 }">成功</c:if>
										<c:if test="${tradeLst.deduct_roll_bk_result == 1 }">超时</c:if>
										<c:if test="${tradeLst.deduct_roll_bk_result != 0 && tradeLst.deduct_roll_bk_result != 1}">失败</c:if>
									</c:if>
								</td>
								<td align="center">${tradeLst.tradeName }</td>
								<td align="center">${tradeLst.name_ }</td>
								<td align="center">
									<c:if test="${tradeLst.whetherQs == 0 }">否</c:if>
									<c:if test="${tradeLst.whetherQs == 1 }">是</c:if>
								</td>
								<td align="center">
									<c:if test="${tradeLst.bk_chk == 0 }">未对账</c:if>
									<c:if test="${tradeLst.bk_chk == 1 }">对账成功</c:if>
									<c:if test="${tradeLst.bk_chk == 2 }">对账失败</c:if>
									<c:if test="${tradeLst.bk_chk == 3 }">无需对账</c:if>
								</td>
								<td align="center">
									<c:if test="${tradeLst.whetherRiqie == 0 }">否</c:if>
									<c:if test="${tradeLst.whetherRiqie == 1 }">是</c:if>
								</td>
								<td align="center">
									<a class="fl lj mr10" href="#" onclick="queryDetail('${tradeLst.trade_id}')">详情</a>
									<c:if test="${(tradeLst.deduct_result == 0 ||  tradeLst.deduct_roll_bk_result == 0) && tradeLst.bk_chk == 0 }">
										<a class="fl lj mr10" href="#" onclick="addUnderLineDataToErrorDataLst('${tradeLst.trade_id}')">差错处理</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
			</table>
		
			<!-- 分页 -->
			<c:if test="${getDataResult.totalPages != null}">
				<div class="next">
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(${getDataResult.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${getDataResult.pageNo-2 > 0}">
						<a href="javascript:paging(${getDataResult.pageNo-2 })"><span>${getDataResult.pageNo-2
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo-1 > 0}">
						<a href="javascript:paging(${getDataResult.pageNo-1 })"><span>${getDataResult.pageNo-1
								}</span></a>
					</c:if>
					<a href="#" class="hover"><span>${getDataResult.pageNo }</span></a>
					<c:if test="${getDataResult.pageNo+1 <= getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+1 })"><span>${getDataResult.pageNo+1
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo+2 <= getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+2 })"><span>${getDataResult.pageNo+2
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo+3 <= getDataResult.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${getDataResult.pageNo < getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(${getDataResult.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${getDataResult.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${getDataResult.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
						</span>
					</b>
				</div>
			</c:if>
		</div>
		
			<div id="pop1" class="pop" style="display: none">
				<div class="pop_body" style="margin-top: 10px;">
					<h1 class="pop_tit">
						<span class="fl">详情</span> <a class="close"
							href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
					</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0" id="operator">
							<tr>
								<td align="right" bgcolor="#eeeeee">交易流水：</td>
								<td id="req_sys_stance1"></td>
								<td align="right" bgcolor="#eeeeee">交易时间：</td>
								<td id="trade_time1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">转出卡号：</td>
								<td id="out_account1"></td>
								<td align="right" bgcolor="#eeeeee">交易金额：</td>
								<td id="trade_amount1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">交易处理码</td>
								<td id="req_process1"></td>
								<td align="right" bgcolor="#eeeeee">交易消息类型</td>
								<td id="trademsg_type1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">电银商户号：</td>
								<td id="req_mer_code11"></td>
								<td align="right" bgcolor="#eeeeee">电银终端号：</td>
								<td id="req_mer_term_id1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">电银手续费：</td>
								<td id="trade_fee1"></td>
								<td align="right" bgcolor="#eeeeee">交易类型：</td>
								<td id="trade_type1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">支付手续费：</td>
								<td id="zf_fee1"></td>
								<td align="right" bgcolor="#eeeeee">商户手续费：</td>
								<td id="mer_fee1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">交易应答码：</td>
								<td id="req_response1"></td>
								<td align="right" bgcolor="#eeeeee">是否冲正：</td>
								<td id="deduct_roll_bk1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">冲正流水：</td>
								<td id="deduct_roll_bk_stance1"></td>
								<td align="right" bgcolor="#eeeeee">冲正扣款时间：</td>
								<td id="deduct_rollbk_sys_time1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">冲正应答码：</td>
								<td id="deduct_roll_bk_response1"></td>
								<td align="right" bgcolor="#eeeeee">冲正结果：</td>
								<td id="deduct_roll_bk_result1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">扣款流水：</td>
								<td id="deduct_sys_stance1"></td>
								<td align="right" bgcolor="#eeeeee">扣款时间：</td>
								<td id="deduct_sys_time1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">扣款应答码：</td>
								<td id="deduct_sys_response1"></td>
								<td align="right" bgcolor="#eeeeee">扣款结果：</td>
								<td id="deduct_result1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">清算日期：</td>
								<td id="deduct_stlm_date1"></td>
								<td align="right" bgcolor="#eeeeee">银行手续费：</td>
								<td id="zfFileFee"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">商户名称：</td>
								<td id="mer_name1"></td>
								<td align="right" bgcolor="#eeeeee">商户订单号：</td>
								<td id="additional_data1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">银联参考号</td>
								<td id="deduct_sys_reference1"></td>
								<td align="right" bgcolor="#eeeeee">授权码：</td>
								<td id="authorization_code1"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">银联商户号：</td>
								<td id="deduct_mer_code1"></td>
								<td align="right" bgcolor="#eeeeee">银联终端号：</td>
								<td id="deduct_mer_term_id1"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</c:if>
		
		<c:if test="${bank_type == 1 }">
			<div style="font-size: 12px;">
				<span>
					本页共
					<font color="red">
						<c:if test="${fn:length(rytUpmpData.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(rytUpmpData.result) > 0 }">${fn:length(rytUpmpData.result) }</c:if>
					</font>
					条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${rytUpmpData.totalItems == null }">0</c:if>
					<c:if test="${rytUpmpData.totalItems != null }">${rytUpmpData.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${rytUpmpData.totalPages == null}">0</c:if>
					<c:if test="${rytUpmpData.totalPages != null}">${rytUpmpData.totalPages}</c:if>
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
					<c:if test="${bk_chk == '' || bk_chk == null }">
						对账成功金额：
						<font color="red">
							<span id="dzSuccessMoney">0.00</span>
						</font>
						元
						&nbsp;&nbsp;
						商户手续费：
						<font color="red">
							<span id="merFee">0.00</span>
						</font>
						&nbsp;&nbsp;
						&nbsp;&nbsp;
						对账失败金额：
						<font color="red">
							<span id="dzFailMoney">0.00</span>
						</font>
						元 
						 &nbsp;&nbsp;
						未对账金额：
						<font color="red">
							<span id="noDzMoney">0.00</span>
						</font>
						元
					</c:if>
					<c:if test="${bk_chk == '0' }">
						未对账金额：
						<font color="red">
							<span id="noDzMoney1">0.00</span>
						</font>
						元
					</c:if>
					<c:if test="${bk_chk == '1' }">
						对账成功金额：
						<font color="red">
							<span id="dzSuccessMoney1">0.00</span>
						</font>
						元
						&nbsp;&nbsp;
						商户手续费：
						<font color="red">
							<span id="merFee">0.00</span>
						</font>
					</c:if>
					<c:if test="${bk_chk == '2' }">
						对账失败金额：
						<font color="red">
							<span id="dzFailMoney1">0.00</span>
						</font>
						元
					</c:if>
				</span>
			</div>
			
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">交易流水号</td>
							<td align="center">商户订单号</td>
							<td align="center">电银商户号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">商户手续费</td>
							<td align="center">交易状态</td>
							<td align="center">交易类别</td>
							<td align="center">扣款渠道</td>
							<td align="center">是否清算</td>
							<td align="center">对账结果</td>
							<td align="center">是否日切</td>
							<td align="center">备注</td>
							<td>操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(rytUpmpData.result)<=0 }">
						<tr align="center">
							<td colspan="13">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<tbody id="onLine">
						<c:forEach items="${rytUpmpData.result }" var="rytUpmp">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${rytUpmp.tseq}</td>
								<td align="center">${rytUpmp.oid }</td>
								<td align="center">${rytUpmp.mid}</td>
								<td align="center">${rytUpmp.sys_date }</td>
								<td align="center">
									<f:formatNumber value="${rytUpmp.amount }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${rytUpmp.mer_fee }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<c:if test="${rytUpmp.tstat == 0 }">初始状态</c:if>
									<c:if test="${rytUpmp.tstat == 1 }">待支付</c:if>
									<c:if test="${rytUpmp.tstat == 2 }">成功</c:if>
									<c:if test="${rytUpmp.tstat == 3 }">失败</c:if>
									<c:if test="${rytUpmp.tstat == 4 }">请求银行失败</c:if>
									<c:if test="${rytUpmp.tstat == 5 }">撤销</c:if>
								</td>
								<td align="center">${rytUpmp.tradeType }</td>
								<td align="center">${rytUpmp.name }</td>
								<td align="center">
									<c:if test="${rytUpmp.whetherQs == 0 }">否</c:if>
									<c:if test="${rytUpmp.whetherQs == 1 }">是</c:if>
								</td>
								<td align="center">
									<c:if test="${rytUpmp.bk_chk == 0 }">未对账</c:if>
									<c:if test="${rytUpmp.bk_chk == 1 }">对账成功</c:if>
									<c:if test="${rytUpmp.bk_chk == 2 }">对账失败</c:if>
									<c:if test="${rytUpmp.bk_chk == 3 }">无需对账</c:if>
								</td>
								<td align="center">
									<c:if test="${rytUpmp.whetherRiqie == 0 }">否</c:if>
									<c:if test="${rytUpmp.whetherRiqie == 1 }">是</c:if>
								</td>
								<td align="center">${rytUpmp.p14 }</td>
								<td>
									<a class="fl lj mr10" href="#" onclick="querySkDetail('${rytUpmp.tseq}')">详情</a>
									<c:if test="${rytUpmp.tstat == 2 && rytUpmp.bk_chk == 0 }">
										<a class="fl lj mr10" href="#" onclick="moveDataToErrorDataLst('${rytUpmp.tseq}', '${rytUpmp.sys_date }', '${rytUpmp.card_no }', '${rytUpmp.amount }', '${rytUpmp.mer_fee }', '${rytUpmp.gid }', '${rytUpmp.mid}', '${rytUpmp.zf_fee }', '${rytUpmp.zf_file_fee }', '${rytUpmp.zf_fee_bj }', '${rytUpmp.whetherErroeHandle }', '${rytUpmp.oid }', 
										'${rytUpmp.type}', '${rytUpmp.gate}', '${rytUpmp.out_user_id}', '${rytUpmp.in_user_id}', '${rytUpmp.p14}', '${rytUpmp.whetherValid }');">差错处理</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
				<c:if test="${rytUpmpData.totalPages != null}">
					<div class="next">
						<c:if test="${rytUpmpData.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo > 1}">
							<a href="javascript:paging(${rytUpmpData.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${rytUpmpData.pageNo-2 > 0}">
							<a href="javascript:paging(${rytUpmpData.pageNo-2 })"><span>${rytUpmpData.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo-1 > 0}">
							<a href="javascript:paging(${rytUpmpData.pageNo-1 })"><span>${rytUpmpData.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${rytUpmpData.pageNo }</span></a>
						<c:if test="${rytUpmpData.pageNo+1 <= rytUpmpData.totalPages}">
							<a href="javascript:paging(${rytUpmpData.pageNo+1 })"><span>${rytUpmpData.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo+2 <= rytUpmpData.totalPages}">
							<a href="javascript:paging(${rytUpmpData.pageNo+2 })"><span>${rytUpmpData.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo+3 <= rytUpmpData.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${rytUpmpData.pageNo < rytUpmpData.totalPages}">
							<a href="javascript:paging(${rytUpmpData.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${rytUpmpData.pageNo > 1}">
							<a href="javascript:paging(${rytUpmpData.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${rytUpmpData.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${rytUpmpData.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
				<span class="contect-lt"></span>
				<span class="contect-rt"></span>
				<span class="contect-lb"></span>
				<span class="contect-rb"></span>
			</div>
			
			<div id="pop1" class="pop" style="display: none">
				<div class="pop_body" style="margin-top: 10px;">
					<h1 class="pop_tit">
						<span class="fl">收款交易明细</span> <a class="close"
							href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
					</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0">
							<tr>
								<td align="right" bgcolor="#eeeeee">交易时间：</td>
								<td id="sys_date_sys_time"></td>
								<td align="right" bgcolor="#eeeeee">商户号：</td>
								<td id="s_mid"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">交易金额：</td>
								<td id="s_amount"></td>
								<td align="right" bgcolor="#eeeeee">交易类别：</td>
								<td id="s_type"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">支付网关号：</td>
								<td id="s_gate"></td>
								<td align="right" bgcolor="#eeeeee">支付网关：</td>
								<td id=""></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">支付渠道：</td>
								<td id="s_gid"></td>
								<td align="right" bgcolor="#eeeeee">支付银行卡号：</td>
								<td id="s_card_no"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">清算日期：</td>
								<td id="s_p15"></td>
								<td align="right" bgcolor="#eeeeee">电银流水号：</td>
								<td id="s_tseq"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">银行流水号：</td>
								<td id="s_bk_seq1"></td>
								<td align="right" bgcolor="#eeeeee">交易状态：</td>
								<td id="s_tstat"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">银行应答标识：</td>
								<td id="s_bk_resp"></td>
								<td align="right" bgcolor="#eeeeee">商户手续费：</td>
								<td id="s_fee_amt"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">支付手续费：</td>
								<td id="s_bank_fee"></td>
								<td align="right" bgcolor="#eeeeee">实际交易金额：</td>
								<td id="s_pay_amt"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">优惠金额：</td>
								<td id="s_pre_amt1"></td>
								<td align="right" bgcolor="#eeeeee">订单号：</td>
								<td id="s_oid"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">是否日切：</td>
								<td id="s_whetherRiqie"></td>
								<td align="right" bgcolor="#eeeeee">是否结算：</td>
								<td id="s_whetherJs"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">出账用户ID：</td>
								<td id="out_user_id"></td>
								<td align="right" bgcolor="#eeeeee">入账用户ID：</td>
								<td id="in_user_id"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">对账结果：</td>
								<td id="s_bk_chk"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>	
		</c:if>
		
	</div>
	</div>
</body>
</html>