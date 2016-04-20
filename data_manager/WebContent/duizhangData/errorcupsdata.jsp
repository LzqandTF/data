<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>差错单查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function paging(pageNo) {
		var form = document.getElementById("cupsForm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageCupsErrorData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function queryByPageSize(pageSize) {
		var form = document.getElementById("cupsForm");
		var inst_name = $("#inst_name").val();
		if(inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageCupsErrorData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function checkQuery(){
		var reqSysStance = $("#reqSysStance").val();
		var deductSysReference = $("#deductSysReference").val();
		var origDataStance = $("#origDataStance").val();
		var outAccount = $("#outAccount").val();
		var inst_name = $("#inst_name").val();
		if(inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		
		var reg = /^\d{1,}$/;		
		if(reqSysStance != null && reqSysStance.length != 0){
			if(!reg.test(reqSysStance)){
				alert("系统跟踪号必须输入数字！");
				return;
			}
		}
		if(deductSysReference != null && deductSysReference.length != 0){
			if(!reg.test(deductSysReference)){
				alert("上一笔交易的检索参考号必须输入数字！");
				return;
			}
		} 
		if(origDataStance != null && origDataStance.length != 0){
			if(!reg.test(origDataStance)){
				alert("上一笔交易的系统跟踪号必须输入数字！");
				return;
			}
		} 
		if(outAccount != null && outAccount.length != 0){
			if(!reg.test(outAccount)){
				alert("最原始交易的交易代码必须输入数字！");
				return;
			}
		}
		var form = document.getElementById("cupsForm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageCupsErrorData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function detailData(id){
		var inst_name = $("#inst_name").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryErrorCupsDetailData.do",
			data : {"id": id,"inst_name":inst_name},
			dataType : 'JSON',
			success : function(msg) {
				if(msg != null){
					$("#reqSysStance_d").html(msg.reqSysStance);
					$("#outAccount_d").html(msg.outAccount);
					$("#reqTime_d").html(msg.reqTime);
					$("#tradeAccount_d").html(msg.tradeAccount);
					$("#acceptorReceiveFee_d").html(msg.acceptorReceiveFee);
					$("#acceptorPayFee_d").html(msg.acceptorPayFee);
					$("#accountIdentification_d").html(msg.accountIdentification);
					$("#deductSysReference_d").html(msg.deductSysReference);
					$("#tradeFee_d").html(msg.tradeFee);
					$("#origDataStance_d").html(msg.origDataStance);
					$("#onTradeTime_d").html(msg.onTradeTime);
					$("#onDeduct_stlm_date_d").html(msg.onDeduct_stlm_date);
					$("#onTradeAccount_d").html(msg.onTradeAccount);
					$("#errorTradeReceiveFee_d").html(msg.errorTradeReceiveFee);
					$("#errorTradePayFee_d").html(msg.errorTradePayFee);
					$("#errorInfo_d").html(msg.errorInfo);
					$("#error_trade_flag_d").html(msg.error_trade_flag);
					$("#acceptorReceiveFee_d").html(msg.deductSysResponse);
					$("#accountIdentification_d").html(msg.origDataTime);
					$("#tran_code_caused_error_d").html(msg.terminalType);
					
					$("#detail").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("detail");
				}
			}
		});
	}
	
	//初始化银行选择下拉框
	 function initChannelSelect() {
			$.ajax({
				url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
		   		type : 'post',
		   		async : false,
		   		success : function(msg) {
		   			for (i in msg)
		   			$("#inst_name").append('<option value="' + msg[i]['error_dz_data_tableName'] + '">'+ msg[i]['name'] + '</option>');
		   		},
		   		error : function(msg) {
		   			alert("获取渠道列表失败!");
		   		}
		   	});
	    	var inst_id = $("#inst_id_hidden").val();
			var type = document.getElementById("inst_name");
			
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
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	function clearForm(){
		$("#reqSysStance").val("");
		$("#origDataStance").val("");
		$("#deductSysReference").val("");
		$("#outAccount").val("");
		$("#error_trade_flag").val("");
		$("#startTime").val("");
		$("#endTime").val("");
	}
	
	//分页
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

<body onload="initChannelSelect();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>差错单查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageCupsErrorData.do" target="right" id="cupsForm" name="cupsForm" method="post">
					<table align="center" style="width: 93%;">
						<tr>
							<td style="text-align: right;">交易时间</td>
							<td>
								<span class="input_bgl">
									<input maxlength="20" style="width: 70px;" name="startTime" id="startTime" value="${param.startTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" />
										&nbsp;&nbsp;至&nbsp;&nbsp;
									<input maxlength="20" style="width: 70px;" name="endTime" id="endTime" value="${param.endTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
								</span>
							</td>
							<td style="text-align: right;">差错交易标志</td>
							<td>
								<span class="input_bgl" style="width: 1%;">
									<input maxlength="8" type="text" name="error_trade_flag" id="error_trade_flag" value="${param.error_trade_flag }" />
								</span>
							</td>
							<td style="text-align: right;">上一笔交易的检索参考号</td>
							<td>
								<span class="input_bgl">
									<input maxlength="20" type="text" name="deductSysReference" id="deductSysReference" value="${param.deductSysReference }" />
								</span>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">系统跟踪号</td>
							<td>
								<span class="input_bgl" style="width: 60px;">
									<input maxlength="20" type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" />
								</span>
							</td>
							<td style="text-align: right;">最原始交易的交易代码</td>
							<td>
								<span class="input_bgl"  style="width: 60px;">
									<input maxlength="20" type="text" name="outAccount" id="outAccount" value="${param.outAccount }" />
								</span>
							</td>
							<td style="text-align: right;">上一笔交易的系统跟踪号</td>
							<td>
								<span class="input_bgl" style="width: 60px;">
									<input maxlength="20" type="text" name="origDataStance" id="origDataStance" value="${param.origDataStance }" />
								</span>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">扣款渠道</td>
							<td>
								<span class="input_bgl">
									<select id="inst_name" name="inst_name">
										<option value="">--请选择扣款渠道--</option>
									</select>
									<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center;">
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="重置" onclick="clearForm()"/>
							</td>
						</tr>
					</table>
					<!--  
					<ul class="check-m">
						<li>
							<b style="margin-left: 0px;">交易时间</b>
							<span class="input_bgl" style="width: 1%;" >
								<input maxlength="20" name="startTime" id="startTime" value="${param.startTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" />
									&nbsp;&nbsp;至&nbsp;&nbsp;
								<input maxlength="20" name="endTime" id="endTime" value="${param.endTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})"/>
							</span>
						</li>						
						<li>
							<b>差错交易标志</b>
							<span class="input_bgl" style="width: 1%;">
								<input maxlength="8" type="text" name="error_trade_flag" id="error_trade_flag" value="${param.error_trade_flag }" />
							</span>
						</li>
						<li>
							<b style="width: 160px;">上一笔交易的检索参考号</b>
							<span class="input_bgl">
								<input maxlength="20" type="text" name="deductSysReference" id="deductSysReference" value="${param.deductSysReference }" />
							</span>
						</li>
						<li>
							<b style="width: 70px;">系统跟踪号</b>
							<span class="input_bgl" style="width: 60px;">
								<input maxlength="20" type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" />
							</span>
						</li>
						<li>
							<b style="width: 210px;">最原始交易的交易代码</b>
							<span class="input_bgl"  style="width: 60px;">
								<input maxlength="20" type="text" name="outAccount" id="outAccount" value="${param.outAccount }" />
							</span>
						</li>
						<li>
							<b style="width: 160px;">上一笔交易的系统跟踪号</b>
							<span class="input_bgl" style="width: 60px;">
								<input maxlength="20" type="text" name="origDataStance" id="origDataStance" value="${param.origDataStance }" />
							</span>
						</li>
						<%-- <li>
							<b style="width: 150px;">扣款渠道</b>
							<span class="input_bgl">
								<select id="inst_name" name="inst_name">
									<option value="">--全部--</option>
									<option value="1">--银联CUPS--</option>
								</select>
								<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
							</span>
						</li> --%>
						
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
							<input type="button" class="icon_normal" value="重置" onclick="clearForm()"/>
						</li>
					</ul>
					-->
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(pageErrorCups.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageErrorCups.result) > 0 }">${fn:length(pageErrorCups.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageErrorCups.totalItems == null }">0</c:if>
					<c:if test="${pageErrorCups.totalItems != null }">${pageErrorCups.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageErrorCups.totalPages == null}">0</c:if>
					<c:if test="${pageErrorCups.totalPages != null}">${pageErrorCups.totalPages}</c:if>
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
							<td align="center">交易时间</td>
							<td align="center">差错交易标志</td>
							<td align="center">系统跟踪号</td>			
							<td align="center">交易金额</td>
							<td align="center">交易类型码</td>
							<td align="center">上一笔交易检索参考号</td>
							<td align="center">上一笔交易的系统跟踪号</td>
							<td align="center">上一笔交易的日期时间</td>
							<td align="center">上一笔交易清算日期</td>
							<td align="center">上一笔交易金额</td>
							<td align="center">应收费用</td>
							<td align="center">应付费用</td>
							<td align="center">差错原因</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageErrorCups.result)<=0 }">
						<tr align="center">
							<td colspan="14">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageErrorCups.result }" var="data">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${data.reqTime }</td>
							<td align="center">${data.error_trade_flag }</td>
							<td align="center">${data.reqSysStance }</td>
							<td align="center">${data.tradeAccount }</td>
							<td align="center">${data.process }</td>
							<td align="center">${data.deductSysReference }</td>
							<td align="center">${data.origDataStance }</td>
							<td align="center">${data.onTradeTime }</td>
							<td align="center">${data.onDeduct_stlm_date }</td>
							<td align="center">${data.onTradeAccount}</td>
							<td align="center">${data.errorTradePayFee}</td>
							<td align="center">${data.errorTradeReceiveFee}</td>
							<td align="center">${data.error_info}</td>
							<td align="center"><a class="fl lj mr10" href="javascript:detailData('${data.id }')">详细</a></td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageErrorCups.totalPages != null}">
				<div class="next">
					<c:if test="${pageErrorCups.pageNo > 1}">
						<a href="javascript:paging(${pageErrorCups.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageErrorCups.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageErrorCups.pageNo-2 > 0}">
						<a href="javascript:paging(${pageErrorCups.pageNo-2 })"><span>${pageErrorCups.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageCupsData.pageNo-1 > 0}">
						<a href="javascript:paging(${pageErrorCups.pageNo-1 })"><span>${pageErrorCups.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageErrorCups.pageNo }</span></a>
					<c:if test="${pageErrorCups.pageNo+1 <= pageErrorCups.totalPages}">
						<a href="javascript:paging(${pageErrorCups.pageNo+1 })"><span>${pageErrorCups.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageErrorCups.pageNo+2 <= pageErrorCups.totalPages}">
						<a href="javascript:paging(${pageErrorCups.pageNo+2 })"><span>${pageErrorCups.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageErrorCups.pageNo+3 <= pageErrorCups.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageErrorCups.pageNo < pageErrorCups.totalPages}">
						<a href="javascript:paging(${pageErrorCups.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageErrorCups.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum"
							value="${pageErrorCups.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<div id="detail" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">银联对账数据详细信息</span> 
				<a class="close" href="javascript:hide('detail')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="id1" name="id1" />
				<input type="hidden" id="name1" name="name1" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统跟踪号：</td>
						<td id="reqSysStance_d"></td>
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="reqTime_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">主账号：</td>
						<td id="outAccount_d"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAccount_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">持卡人交易手续费：</td>
						<td id="tradeFee_d"></td>
						<td align="right" bgcolor="#eeeeee">受理方应收手续费：</td>
						<td id="acceptorReceiveFee_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">受理方应付手续费：</td>
						<td id="acceptorPayFee_d"></td>
						<td align="right" bgcolor="#eeeeee">转入卡号：</td>
						<td id=""></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">转出卡号：</td>
						<td id="accountIdentification_d"></td>
						<td align="right" bgcolor="#eeeeee">上一笔交易检索参考号：</td>
						<td id="deductSysReference_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上一笔交易的系统跟踪号：</td>
						<td id="origDataStance_d"></td>
						<td align="right" bgcolor="#eeeeee">上一笔交易的日期时间：</td>
						<td id="onTradeTime_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上一笔交易清算日期：</td>
						<td id="onDeduct_stlm_date_d"></td>
						<td align="right" bgcolor="#eeeeee">上一笔交易金额：</td>
						<td id="onTradeAccount_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">应收费用：</td>
						<td id="errorTradeReceiveFee_d"></td>
						<td align="right" bgcolor="#eeeeee">应付费用：</td>
						<td id="errorTradePayFee_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">差错原因：</td>
						<td id="errorInfo_d"></td>
						<td align="right" bgcolor="#eeeeee">差错交易标志：</td>
						<td id="error_trade_flag_d"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">引发差错交易的最原始交易的交易代码：</td>
						<td id="tran_code_caused_error_d"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
