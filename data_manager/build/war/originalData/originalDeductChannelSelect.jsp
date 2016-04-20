<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>线下交易数据查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryOriginalData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("originalData");
		var bank_id = $("#bank_id").val();
		if (bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryOriginalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询数据
	function checkQuery(){
		var bank_id = $("#bank_id").val();
		if (bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryOriginalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	// 初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getUnderLineBankInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['original_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
    		}
    	});
		
		var whtherInner_Js = $("#whtherInner_Js_hidden").val();
		var whtherInnerJs = document.getElementById("whtherInnerJs");
		for (var i = 0; i < whtherInnerJs.length; i++) {
			if (whtherInnerJs.options[i].value == whtherInner_Js) {
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
		
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("channel");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == inst_id){
				type.options[i].selected = 'selected';
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
				if (data.length == 0) {
					$(selectObj).append("<option value=''>全部</option>");
				}
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].instId + ',' + data[i].inst_type +">"+data[i].name+"</option>");
				}
    		}
    	});
	}
	
	//根据ID获取详细信息
	function queryDetail(trade_id){
		var bank_id = $("#bank_id").val();
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryDetailByTradeId.do',
			data : {"trade_id": trade_id,"bank_id": bank_id},
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
					$("#req_mer_code11").html(trade.req_mer_code);
					$("#req_mer_term_id1").html(trade.req_mer_term_id);
					$("#req_process1").html(trade.req_process);
					$("#trademsg_type1").html(trade.trademsg_type);
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
					var deduct_rollbk_sys_time = trade.deduct_rollbk_sys_time;
					$("#deduct_roll_bk_stance1").html(trade.deduct_roll_bk_stance);
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
					$("#deduct_sys_id1").html(trade.deduct_sys_id);
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
		$("#req_sys_stance").val("");
		$("#deduct_roll_bk_stance").val("");
		$("#req_mer_code").val("");
		$("#out_account").val("");
		$("#req_mer_term_id").val("");
		$("#deduct_mer_code").val("");
		$("#additional_data").val("");
		$("#whtherInnerJs").val("");
		$("#deduct_stlm_date").val("");
		$("#bank_id").val("");
		var selectObj = document.getElementById("channel");
		while(selectObj.firstChild) {
			selectObj.removeChild(selectObj.firstChild);
		}
		$(selectObj).append("<option value=''>全部</option>");
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
<body onload="initBankInst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">交易数据查询</a>&gt;<span>线下交易数据查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryOriginalData.do" target="right" name="originalData" id="originalData" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="90%" border="0" cellspacing="0">	
				            <tr>
				            	<td align="right" nowrap="nowrap">交易日期</td>
				            	<td nowrap="nowrap">
									<span style="width: 30px;" class="input_bgl"> 
										<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										- 
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>
								</td>
				            	<td align="right" nowrap="nowrap">交易流水号</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" id="req_sys_stance" name="req_sys_stance"  value="${param.req_sys_stance }" maxlength="6" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">冲正流水号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" id="deduct_roll_bk_stance" name="deduct_roll_bk_stance"  value="${param.deduct_roll_bk_stance }" maxlength="6" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">电银商户号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="req_mer_code" id="req_mer_code" value="${param.req_mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">银联商户号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="deduct_mer_code" id="deduct_mer_code" value="${param.deduct_mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">电银终端号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="req_mer_term_id" id="req_mer_term_id" value="${param.req_mer_term_id }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
				                     </span>
				                </td> 
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">商户订单号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="additional_data" id="additional_data"
												value="${param.additional_data }" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">清算日期</td>
				                <td align="left" nowrap="nowrap">
				                	<span style="width:30px;" class="input_bgl">
										<input name="deduct_stlm_date" id="deduct_stlm_date" value="${param.deduct_stlm_date }" 
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
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
				                <td align="right" nowrap="nowrap">转出卡号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="out_account" id="out_account" value="${param.out_account }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="重置" onclick="clearForm();"/>
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
				<c:if test="${fn:length(getDataResult.result) <= 0 }">0</c:if>
				<c:if test="${fn:length(getDataResult.result) > 0 }">${fn:length(getDataResult.result) }</c:if>
			</font>
			条数据
			</span>
			<span style="float: right">共<font color="red">
				<c:if test="${getDataResult.totalItems == null }">0</c:if>
				<c:if test="${getDataResult.totalItems != null }">${getDataResult.totalItems }</c:if>
			</font>条数据
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
		</div>
		<div class="table-m">
			<table width="100%" border="0" cellspacing="0">
				<thead>
					<tr>
						<td align="center">电银商户号</td>
						<td align="center">扣款时间</td>
						<td align="center">交易金额</td>
						<td align="center">转出卡号</td>
						<td align="center">交易结果</td>
						<td align="center">交易类型</td>
						<td align="center">交易类别</td>
						<td align="center">扣款渠道</td>
						<td align="center">收单机构</td>
						<td align="center">内部清算</td>
						<td align="center">应答码</td>
						<td>操作</td>
					</tr>
				</thead>
				<c:if test="${fn:length(getDataResult.result)<=0 }">
					<tr align="center">
						<td colspan="12">对不起,暂无数据！</td>
					</tr>
				</c:if>
				<c:forEach items="${getDataResult.result }" var="tradeLst">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
						<td align="center">${tradeLst.req_mer_code}</td>
						<td align="center">
							<c:if test="${tradeLst.deduct_roll_bk == 0 }">
								${fn:substring(tradeLst.deduct_sys_time,0,19)}
							</c:if>
							<c:if test="${tradeLst.deduct_roll_bk == 1 }">
								${fn:substring(tradeLst.deduct_rollbk_sys_time,0,19)}
							</c:if>
						</td>
						<td align="center"><f:formatNumber value="${tradeLst.trade_amount }" pattern="0.00"></f:formatNumber></td>
						<td align="center">
							${tradeLst.out_account}
						</td>
						<td align="center">
							<c:if test="${tradeLst.deduct_sys_id != 12}">
								<c:if test="${tradeLst.deduct_roll_bk == 0 }">
									<c:if test="${tradeLst.deduct_sys_response == '00' }">成功</c:if>
									<c:if test="${tradeLst.deduct_sys_response == 'N1' }">超时</c:if>
									<c:if test="${tradeLst.deduct_sys_response != 'N1' && tradeLst.deduct_sys_response != '00'}">失败</c:if>
								</c:if>
								<c:if test="${tradeLst.deduct_roll_bk == 1 }">
									<c:if test="${tradeLst.deduct_roll_bk_response == '00' }">成功</c:if>
									<c:if test="${tradeLst.deduct_roll_bk_response == 'N1' }">超时</c:if>
									<c:if test="${tradeLst.deduct_roll_bk_response != 'N1' && tradeLst.deduct_roll_bk_response != '00'}">失败</c:if>
								</c:if>
							</c:if>
							<c:if test="${tradeLst.deduct_sys_id == 12}">
								<c:if test="${tradeLst.gain_sys_response == '00' }">成功</c:if>
								<c:if test="${tradeLst.gain_sys_response == 'N1' }">超时</c:if>
								<c:if test="${tradeLst.gain_sys_response != 'N1' && tradeLst.gain_sys_response != '00'}">失败</c:if>
							</c:if>
						</td>
						<td align="center">${tradeLst.tradeType }</td>
						<td align="center">${tradeLst.tradeName }</td>
						<td align="center">${tradeLst.name_ }</td>
						<td align="center">${tradeLst.receivi_name }</td>
						<td align="center">
							<c:if test="${tradeLst.whtherInnerJs == 0 }">否</c:if>
							<c:if test="${tradeLst.whtherInnerJs == 1 }">是</c:if>
						</td>
						<td align="center">
							<c:if test="${tradeLst.deduct_sys_id != 12}">
								<c:if test="${tradeLst.deduct_roll_bk == 0}">${tradeLst.deduct_sys_response}</c:if>
								<c:if test="${tradeLst.deduct_roll_bk == 1}">${tradeLst.deduct_roll_bk_response}</c:if>
							</c:if>
							<c:if test="${tradeLst.deduct_sys_id == 12}">
								${tradeLst.gain_sys_response}
							</c:if>
						</td>
						<td>
							<a class="fl lj mr10" href="#" onclick="queryDetail('${tradeLst.trade_id}');">详情</a>
						</td>
					</tr>
				</c:forEach>
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
							value="${getDataResult.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
			
	<!--===========================弹出内容============================-->
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
						<td align="right" bgcolor="#eeeeee">电银商户号：</td>
						<td id="req_mer_code11"></td>
						<td align="right" bgcolor="#eeeeee">电银终端号：</td>
						<td id="req_mer_term_id1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易处理码</td>
						<td id="req_process1"></td>
						<td align="right" bgcolor="#eeeeee">交易消息类型</td>
						<td id="trademsg_type1"></td>
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
						<td align="right" bgcolor="#eeeeee">扣款机构号：</td>
						<td id="deduct_sys_id1"></td>
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
	</div>
	</div>
</body>
</html>
