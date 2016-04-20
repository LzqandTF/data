<%@page import="com.chinaebi.entity.Login"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账单查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function paging(pageNo) {
		var form = document.getElementById("duizData");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryChannelDuiZhangData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function queryByPageSize(pageSize) {
		var form = document.getElementById("duizData");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryChannelDuiZhangData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function checkQuery(){
		var merCode = $("#merCode").val();
		var termId = $("#termId").val();
		var outAccount = $("#outAccount").val();
		var merType = $("#merType").val();
		var bank_id = $("#bank_id").val();
		
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		
		var reg = /^\d{1,}$/;		
		if(merCode != null && merCode.length != 0){
			if(!reg.test(merCode)){
				alert("商户号必须输入数字！");
				return;
			}
		} 
		if(termId != null && termId.length != 0){
			if(!reg.test(termId)){
				alert("扣款终端号必须输入数字！");
				return;
			}
		} 
		if(outAccount != null && outAccount.length != 0){
			if(!reg.test(outAccount)){
				alert("扣款账号必须输入数字！");
				return;
			}
		}
		if(merType != null && merType.length != 0){
			if(!reg.test(merType)){
				alert("商户类型必须输入数字！");
				return;
			}
			if(merType.length != 4){
				alert("商户类型长度必须为4！");
				return;
			}
		} 
		var form = document.getElementById("duizData");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryChannelDuiZhangData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	

	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	function clearForm(){
		$("#reqSysStance").val("");
		$("#merCode").val("");
		$("#termId").val("");
		$("#outAccount").val("");
		$("#merType").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#deductEndTime").val("");
		$("#deductStartTime").val("");
		$("#deductSysReference").val("");
		$("#bank_id").val("");
	}
	
	function detailData(id){
		var bank_id = $("#bank_id").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryChannelDuiZhangDetailData.do",
			data : "id="+ id+"&bank_id="+bank_id,
			dataType : 'JSON',
			success : function(msg) {
				if(msg != null){
					$("#reqSysStance_").html(msg.reqSysStance);
					$("#outAccount_").html(msg.outAccount);
					$("#reqTime_").html(msg.reqTime);
					var instName = msg.inst_name;
					if (instName == "大连交行" || instName == "深圳中行") {
						$("#tradeAmount_").html(msg.tradeAmount*100);
					} else {
						$("#tradeAmount_").html(msg.tradeAmount);
					}
					$("#termId_").html(msg.termId);
					$("#merCode_").html(msg.merCode);
					$("#authorizationCode_").html(msg.authorizationCode);
					$("#deductSysReference_").html(msg.deductSysReference);
					$("#tradeFee_").html(msg.tradeFee);
					$("#acqInstIdCode_").html(msg.acqInstIdCode);
					$("#fwdInstIdCode_").html(msg.fwdInstIdCode);
					$("#msgType_").html(msg.msgType);
					$("#process_").html(msg.process);
					$("#merType_").html(msg.merType);
					$("#reqType_").html(msg.reqType);
					$("#rcvgInstIdCode_").html(msg.rcvgInstIdCode);
					$("#origDataStance_").html(msg.origDataStance);
					$("#reqInputType_").html(msg.reqInputType);
					$("#deductSysResponse_").html(msg.deductSysResponse);
					$("#origDataTime_").html(msg.origDataTime);
					$("#terminalType_").html(msg.terminalType);
					if(msg.whetherErroeHandle == "0"){
						$("#whetherErroeHandle_").html("不需要处理");
					}else if(msg.whetherErroeHandle == "1"){
						$("#whetherErroeHandle_").html("长款处理");
					}else if(msg.whetherErroeHandle == "2"){
						$("#whetherErroeHandle_").html("短款处理");
					}
					
					$("#detail").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("detail");
				}
			}
		});
	}
	
	// 初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['dz_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
    		}
    	});
		
		var bankId = $("#bankId_hidden").val();
		var bank_id = document.getElementById("bank_id");
		for (var i = 0; i < bank_id.length; i++) {
			if (bank_id.options[i].value == bankId) {
				bank_id.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	function queryByPage(e) {
		var e = e || window.event;
		if (e.keyCode == 13) {
			var pageNum = $("#pageNum").val();
			
			if (pageNum >= 1) {
				paging(pageNum);
			} else {
				paging(1);
			}
		}
	}
	
	//设置每页显示条数
	function EnterPress(e){ //传入 event
		var e = e || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(10);
			}
		}
	} 
</script>
</head>

<body onload="initBankInst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>对账单查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="1" target="right" id="duizData" name="duizData" method="post">
					<table align="center" style="width: 90%;">
						<tr>
							<td>交易时间</td>
							<td>
								<span class="input_bgl">
								<input maxlength="20" style="width: 70px;" readonly="readonly" name="startTime" id="startTime" value="${param.startTime }" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
									&nbsp;&nbsp;至&nbsp;&nbsp;
								<input maxlength="20" style="width: 70px;" readonly="readonly" name="endTime" id="endTime" value="${param.endTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})"/>
								</span>
							</td>
							<td>清算时间</td>
							<td>
								<span class="input_bgl">
								<input maxlength="20" readonly="readonly" name="deductStartTime" id="deductStartTime" value="${param.deductStartTime }" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}'});" />
								</span>
							</td>
							<td>交易流水号</td>
							<td>
								<span class="input_bgl">
									<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" />
								</span>
							</td>
						</tr>
						<tr>
							<td>扣款账号</td>
							<td>
								<span class="input_bgl">
									<input maxlength="19" type="text" name="outAccount" id="outAccount" value="${param.outAccount }" />
								</span>
							</td>
							<td>商户编号</td>
							<td>
								<span class="input_bgl">
									<input maxlength="15" type="text" name="merCode" id="merCode" value="${param.merCode }" />
								</span>
							</td>
							<td>终端编号</td>
							<td>
								<span class="input_bgl">
									<input maxlength="8" type="text" name="termId" id="termId" value="${param.termId }" />
								</span>
							</td>
						</tr>
						<tr>
							<td>银行机构</td>
							<td>
								<span class="in_t_bgl">
									<select id="bank_id" name="bank_id">
										<option value="">--请选择银行机构--</option>
									</select>
									<input type="hidden" id="bankId_hidden" value="${bankId }"/>
								</span><font color="red">*</font>
							</td>
							<td>商户类型</td>
							<td>
								<span class="input_bgl">
									<input maxlength="20" type="text" name="merType" id="merType" value="${param.merType }" />
								</span>
							</td>
							<td>参考号</td>
							<td>
								<span class="input_bgl">
									<input maxlength="20" type="text" name="deductSysReference" id="deductSysReference" value="${param.deductSysReference }" />
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center;">
								<br />
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="重置" onclick="clearForm()"/>
							</td>
						</tr>
					</table>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${empty size}">0</c:if>
					<c:if test="${size > 0 }">${size}</c:if>
				</font>
				条数据
				</span>
				<span style="float: right;">共<font color="red">
					<c:if test="${totalItems == null }">0</c:if>
					<c:if test="${totalItems != null }">${totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${totalPage == null}">0</c:if>
					<c:if test="${totalPage != null}">${totalPage}</c:if>
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
							<td align="center">交易流水号</td>
							<td align="center">系统参考号</td>
							<td align="center">终端号</td>
							<td align="center">商户号</td>
							<td align="center">交易时间</td>	
							<td align="center">扣款账号</td>		
							<td align="center">交易金额</td>
							<td align="center">交易处理码</td>
							<td align="center">来源</td>
							<td align="center">渠道名称</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageData.result)<=0 }">
						<tr align="center">
							<td colspan="12">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageData.result }" var="data">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${data.reqSysStance }</td>
							<td align="center">${data.deductSysReference }
							<%-- <f:formatNumber value="${data.deductSysReference }"></f:formatNumber> --%>
							</td>
							<td align="center">${data.termId }</td>
							<td align="center">${data.merCode }</td>
							<td align="center">${data.reqTime }</td>
							<td align="center">${data.outAccount }</td>
							<td align="center">
								<%-- <c:if test="${dz_data_tableName != 'duizhang_dljh_lst' and dz_data_tableName != 'duizhang_szzh_lst'  }"> --%>
									<f:formatNumber value="${data.tradeAmount }" pattern="0.00"></f:formatNumber>
								<%-- </c:if> --%>
								<%-- <c:if test="${dz_data_tableName == 'duizhang_dljh_lst' or dz_data_tableName == 'duizhang_szzh_lst' }">
									${data.tradeAmount*100 }
								</c:if> --%>
							</td>
							<td align="center">${data.process }</td>
							<td align="center">${data.dz_file_name }</td>
							<td align="center">${data.inst_name }</td>
							<td align="center"><a class="fl lj mr10" href="javascript:detailData('${data.id }')">详细</a></td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageData.totalPages != null}">
				<div class="next">
					<c:if test="${pageData.pageNo > 1}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo-2 > 0}">
						<a href="javascript:paging(${pageData.pageNo-2 })"><span>${pageData.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-1 > 0}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>${pageData.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageData.pageNo }</span></a>
					<c:if test="${pageData.pageNo+1 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>${pageData.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+2 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+2 })"><span>${pageData.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+3 <= pageData.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo < pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageData.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum"
							value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<div id="detail" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账数据详细信息</span> 
				<a class="close" href="javascript:hide('detail')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">					
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">代理机构标识码：</td>
						<td id="acqInstIdCode_"></td>
						<td width="140" align="right" bgcolor="#eeeeee">发送机构标识码：</td>
						<td id="fwdInstIdCode_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">系统跟踪号：</td>
						<td id="reqSysStance_"></td>
						<td align="right" bgcolor="#eeeeee">交易传输时间：</td>
						<td id="reqTime_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">主账号：</td>
						<td id="outAccount_"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">持卡人交易手续费：</td>
						<td id="tradeFee_"></td>
						<td align="right" bgcolor="#eeeeee">报文类型：</td>
						<td id="msgType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易类型码：</td>
						<td id="process_"></td>
						<td align="right" bgcolor="#eeeeee">商户类型：</td>
						<td id="merType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">受卡机终端标识码：</td>
						<td id="termId_"></td>
						<td align="right" bgcolor="#eeeeee">受卡方标识码：</td>
						<td id="merCode_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">检索参考号：</td>
						<td id="deductSysReference_"></td>
						<td align="right" bgcolor="#eeeeee">服务点条件码：</td>
						<td id="reqType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接收机构标识码：</td>
						<td id="rcvgInstIdCode_"></td>
						<td align="right" bgcolor="#eeeeee">原始交易的系统跟踪号：</td>
						<td id="origDataStance_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易返回码：</td>
						<td id="deductSysResponse_"></td>
						<td align="right" bgcolor="#eeeeee">服务点输入方式：</td>
						<td id="reqInputType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">原始交易日期时间：</td>
						<td id="origDataTime_"></td>
						<td align="right" bgcolor="#eeeeee">终端类型：</td>
						<td id="terminalType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">授权应答码：</td>
						<td id="authorizationCode_"></td>
						<td align="right" bgcolor="#eeeeee">是否需要手工差错处理：</td>
						<td id="whetherErroeHandle_"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
