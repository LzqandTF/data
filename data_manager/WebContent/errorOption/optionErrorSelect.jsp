<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已处理差错查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("optionErrorSearch");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/getOptionErrorData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("optionErrorSearch");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/getOptionErrorData.do?pageSize=" + pageSize;
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
		var form = document.getElementById("optionErrorSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getOptionErrorData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//表单隐藏域
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//清空表单输入框的元素
	function clearForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#deduct_sys_reference").val("");
		$("#js_date").val("");
		$("#additional_data").val("");
		$("#req_sys_stance").val("");
		$("#handling_id").val("");
		$("#check_date").val("");
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
	}
	//下载excel表格
	function downExcel(){
		//根据查询条件下载
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var js_date = $("#js_date").val();
		var channel = $("#channel").val();
		var additional_data = $("#additional_data").val();
		var req_sys_stance = $("#req_sys_stance").val();
		var handling_id = $("#handling_id").val();
		var check_date = $("#check_date").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var url ="<%=request.getContextPath()%>/optionErrorDownExcel.do?startTime="+startTime+
				"&endTime="+endTime+"&deduct_sys_reference="+deduct_sys_reference+
				"&js_date="+js_date+"&channel="+channel+"&bank_id="+bank_id+
				"&additional_data="+additional_data+"&req_sys_stance="+req_sys_stance+"&handling_id="+handling_id+
				"&check_date="+check_date;
		window.location=url;
	}
	//已处理差错查询 明细查询
	function queryOptionErrorDetail(trade_id) {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryOptionErrorDetail.do',
			data : "trade_id="+ trade_id,
			async:false,
			success : function(trade) {
				if(trade != null){
					$("#req_sys_stance1").html(trade.req_sys_stance);
					$("#trade_time1").html(trade.trade_time.substring(0,19));
					$("#out_account1").html(trade.out_account);
					$("#trade_amount1").html(trade.trade_amount);
					$("#trade_fee1").html(trade.trade_fee);
					var result = trade.trade_result;
					var req_process = trade.req_process;
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
						result = trade.trade_result;
					}
					$("#trade_result1").html(result);
					$("#req_process1").html(req_process);
					$("#req_response1").html(trade.req_response);
					$("#mer_name1").html(trade.mer_name);
					$("#req_mer_code11").html(trade.req_mer_code);
					$("#req_mer_term_id1").html(trade.req_mer_term_id);
					$("#deduct_sys_id1").html(trade.deduct_sys_id);
					$("#deduct_mer_term_id").html(trade.deduct_mer_term_id);
					$("#deduct_mer_code1").html(trade.deduct_mer_code);
					$("#deduct_mer_term_id1").html(trade.deduct_mer_term_id);
					var deductResult = trade.deduct_sys_response;
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
					$("#deduct_sys_response1").html(trade.deduct_sys_response);
					var deductRollBk = trade.deduct_roll_bk;
					if (0 == deductRollBk) {
						deductRollBk = "否";
					} else if (1 == deductRollBk) {
						deductRollBk = "是";
					} else {
						deductRollBk = trade.deduct_roll_bk;
					}
					$("#deduct_roll_bk1").html(deductRollBk);
					var rollBkResult = trade.deduct_roll_bk_response;
					if ('00' == rollBkResult) {
						rollBkResult = "成功";
					} else if (null == rollBkResult) {
						rollBkResult = "无";
					} else {
						rollBkResult = "失败";
					}
					$("#deduct_roll_bk_result1").html(rollBkResult);
					var deduct_stlm_date = trade.deduct_stlm_date;
					if (deduct_stlm_date != null || deduct_stlm_date != '') {
						$("#deduct_stlm_date1").html(deduct_stlm_date.substring(0,10));
					}
					var additional_data = trade.additional_data;
					if (additional_data != null) {
						$("#additional_data1").html(trade.additional_data.split('|')[0]);
					}
					$("#trademsg_type1").html(trade.trademsg_type);
					$("#deduct_roll_bk_response1").html(trade.deduct_roll_bk_response);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
	function updateHandlerRemark(trade_id, operator) {
		$("#trade_id").val(trade_id);
		$("#operator").val(operator);
		$("#mark").css({display:"block"});
	}
	function remarkSubmit() {
		var remark = $("#remark").val();
		var trade_id = $("#trade_id").val();
		var operator = $("#operator").val();
		if (remark == "" || null == remark) {
			alert("请填写备注信息！");
			return;
		}
		var url= "<%=request.getContextPath()%>/updateHandlerRemark.do";
		$.ajax({
			type : "post",
			url : url,
			data : {"trade_id": trade_id,"remark":remark,"operator":operator},
			async:false,
			success : function(msg) {
				if(msg){
					alert("修改备注成功");
					$("#mark").css({display:"none"});
					paging(1);
				}else{
					alert("修改备注失败");
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
</script>
</head>
<body onload="initBankInst();initErrorHandlerMethod(); initChannel();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">差错处理</a>&gt;<span>已处理差错查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/getOptionErrorData.do" target="right" name="optionErrorSearch" id="optionErrorSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="100%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">交易日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:60px;" class="input_bgl">
									  	<input style="width: 70px" name="startTime" id="startTime" value="${param.startTime }"
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										-
										<input style="width: 70px" name="endTime" id="endTime" value="${param.endTime }" 
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					            	<td align="right" nowrap="nowrap">参考号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="deduct_sys_reference" id="deduct_sys_reference" value="${param.deduct_sys_reference }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">差错清算日期</td>
					                <td align="left" nowrap="nowrap">
					                	<span class="input_bgl">
											<input name="js_date" id="js_date" value="${param.js_date }"
												readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									    </span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">审批日期</td>
					                <td align="left" nowrap="nowrap">
					                	<span class="input_bgl">
											<input name="check_date" id="check_date" value="${param.check_date }"
												readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									    </span>
					                </td>
					                <td align="right" nowrap="nowrap">订单号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="additional_data" id="additional_data" value="${param.additional_data }"/>
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="req_sys_stance" id="req_sys_stance" value="${param.req_sys_stance }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
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
					                <td align="right" nowrap="nowrap">处理方式</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="handling_id" name="handling_id" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="handling_name_hidden" value="${handling_name }"/>
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
					<c:if test="${fn:length(optionErrorData.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(optionErrorData.result) > 0 }">${fn:length(optionErrorData.result) }</c:if>
				</font>
				条数据
			</span>
				<span style="float: right;">共<font color="red">
					<c:if test="${optionErrorData.totalItems == null }">0</c:if>
					<c:if test="${optionErrorData.totalItems != null }">${optionErrorData.totalItems }</c:if>
					</font>条数据
					<font color="red">
						<c:if test="${optionErrorData.totalPages == null}">0</c:if>
						<c:if test="${optionErrorData.totalPages != null}">${optionErrorData.totalPages}</c:if>
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
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">内部商户号</td>
							<td align="center">流水号</td>
							<td align="center">主账号</td>
							<td align="center">交易金额</td>
							<td align="center">交易类型</td>
							<td align="center">扣款渠道</td>
							<td align="center">审批时间</td>
							<td align="center">差错清算日期</td>
							<td align="center">交易时间</td>
							<td align="center">处理方式</td>
							<td align="center">备注</td>
							<td align="center">重对账备注</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(optionErrorData.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${optionErrorData.result }" var="operatedData">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${operatedData.req_mer_code }</td>
							<td align="center">
								<c:if test="${operatedData.deduct_roll_bk == 0 }">
									${operatedData.deduct_sys_stance}
								</c:if>
								<c:if test="${operatedData.deduct_roll_bk == 1 }">
									${operatedData.deduct_roll_bk_stance}
								</c:if>
							</td>
							<td align="center">${operatedData.out_account}</td>
							<td align="center"><f:formatNumber value="${operatedData.trade_amount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">
								<c:if test="${operatedData.inst_type == 0 }">
									${operatedData.tradeType }
								</c:if>
								<c:if test="${operatedData.inst_type == 1 }">
									<c:if test="${operatedData.trademsg_type == 2 }">收款交易</c:if>
									<c:if test="${operatedData.trademsg_type == 20 }">退款交易</c:if>
								</c:if>
							</td>
							<td align="center">${operatedData.name_ }</td>
							<td align="center">${fn:substring(operatedData.check_time,0,19)}</td>
							<td align="center">${operatedData.js_date}</td>
							<td align="center">${fn:substring(operatedData.trade_time,0,19)}</td>
							<td align="center">${operatedData.handling_name}</td>
							<td align="center">${operatedData.handler_remark}</td>
							<td align="center">${operatedData.cdz_remark}</td>
							<td align="center">
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryOptionErrorDetail('${operatedData.trade_id}')">详情</a>
								<a class="fl lj mr10" href="javascript:void(0);" onclick="updateHandlerRemark('${operatedData.trade_id}','${sessionScope.login.loginName}')">备注</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${optionErrorData.totalPages != null}">
				<div class="next">
					<c:if test="${optionErrorData.pageNo > 1}">
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo > 1}">
						<a href="javascript:paging(${optionErrorData.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${optionErrorData.pageNo-2 > 0}">
						<a href="javascript:paging(${optionErrorData.pageNo-2 })"><span>${optionErrorData.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo-1 > 0}">
						<a href="javascript:paging(${optionErrorData.pageNo-1 })"><span>${optionErrorData.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${optionErrorData.pageNo }</span></a>
					<c:if test="${optionErrorData.pageNo+1 <= optionErrorData.totalPages}">
						<a href="javascript:paging(${optionErrorData.pageNo+1 })"><span>${optionErrorData.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo+2 <= optionErrorData.totalPages}">
						<a href="javascript:paging(${optionErrorData.pageNo+2 })"><span>${optionErrorData.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo+3 <= optionErrorData.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${optionErrorData.pageNo < optionErrorData.totalPages}">
						<a href="javascript:paging(${optionErrorData.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<c:if test="${optionErrorData.pageNo > 1}">
						<a href="javascript:paging(${optionErrorData.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${optionErrorData.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${optionErrorData.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
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
				<span class="fl">详情</span>
				 <a class="close" href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
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
						<td align="right" bgcolor="#eeeeee">电银手续费：</td>
						<td id="trade_fee1"></td>
						<td align="right" bgcolor="#eeeeee">交易结果：</td>
						<td id="trade_result1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="req_process1"></td>
						<td align="right" bgcolor="#eeeeee">交易应答码：</td>
						<td id="req_response1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">内部系统商户号：</td>
						<td id="req_mer_code11"></td>
						<td align="right" bgcolor="#eeeeee">内部系统终端号：</td>
						<td id="req_mer_term_id1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款终端号：</td>
						<td id="deduct_mer_term_id1"></td>
						<td align="right" bgcolor="#eeeeee">商户名称：</td>
						<td id="mer_name1"></td>
						
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款机构号：</td>
						<td id="deduct_sys_id1"></td>
						<td align="right" bgcolor="#eeeeee">扣款商户号：</td>
						<td id="deduct_mer_code1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款结果：</td>
						<td id="deduct_result1"></td>
						<td align="right" bgcolor="#eeeeee">扣款应答码：</td>
						<td id="deduct_sys_response1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">消息类型：</td>
						<td id="trademsg_type1"></td>
						<td align="right" bgcolor="#eeeeee">冲正应答码：</td>
						<td id="deduct_roll_bk_response1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否有冲正标志：</td>
						<td id="deduct_roll_bk1"></td>
						<td align="right" bgcolor="#eeeeee">冲正结果状态：</td>
						<td id="deduct_roll_bk_result1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">清算日期：</td>
						<td id="deduct_stlm_date1"></td>
						<td align="right" bgcolor="#eeeeee">订单号：</td>
						<td id="additional_data1"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
		<!--===========================备注内容============================-->
	<div id="mark" class="pop" style="display: none;">
		<div class="pop_body" style="width: 20%;">
			<h1 class="pop_tit">
				<span class="fl">备注</span>
				 <a class="close" href="javascript:void(0);" onclick="hide('mark')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table  border="0" cellspacing="0" id="operator" style="width: inherit;">
					<tr>
						<td>
							<input type="hidden" id="trade_id" />
							<input type="hidden" id="trade_id" />
							<textarea id="remark" style="resize:none;" cols="20" rows="2"></textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input type="button" class="icon_normal" value="提交" onclick="remarkSubmit();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
