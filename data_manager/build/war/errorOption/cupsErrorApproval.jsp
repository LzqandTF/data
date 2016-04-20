<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银联差错审批</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("cupsErrorApprovalSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorApprovalData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("cupsErrorApprovalSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorApprovalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var form = document.getElementById("cupsErrorApprovalSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorApprovalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//清空表单输入框的元素
	function clearForm(){
		$("#deductStlmDate").val("");
		$("#reqSysStance").val("");
		$("#tradeMsgType").val("");
		$("#channel").val("");
		$("#out_account").val("");
		$("#handling_id").val("");
		$("#trade_type").val("");
	}
	//初始化银行选择下拉框
	 function initChannelSelect() {
    	$.ajax({
    		url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#channel").append('<option value="' + msg[i]['instId'] + '">'+ msg[i]['name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取银行列表失败!");
    		}
    	});
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
    		url : '<%=request.getContextPath()%>/getErrorHandlingList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#handling_id").append('<option value="' + msg[i]['id'] + '">'+ msg[i]['handling_name'] + '</option>');
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
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	//根据ID获取详细信息
	function queryCupsErrorApprovalDetail(id) {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryCupsErrorApprovalDetail.do',
			data : "id="+ id,
			async:false,
			success : function(trade) {
				if(trade != null){
					var trade_time = trade.trade_time;
					if (trade_time != null && trade_time != '') {
						$("#trade_time1").html(trade_time.substring(0,19));
					}
					$("#tradeAmount1").html(trade.tradeAmount);
					$("#out_account1").html(trade.out_account);
					var result = trade.trade_result;
					var process = trade.process;
					if (0 == result) {
						result = "成功";
					} else if(1 == result) {
						result = "超时";
					} else if (2 == result) {
						result = "失败";
					} else if (3 == result && process == '480000') {
						result = "受理成功";
					} else if (3 == result && process != '480000') {
						result = "冲正成功";
					} else {
						result = trade.trade_result;
					}
					$("#trade_result1").html(result);
					$("#deduct_sys_reference1").html(trade.deduct_sys_reference);
					$("#reqSysStance1").html(trade.reqSysStance);
					var deductStlmDate = trade.deductStlmDate;
					if (deductStlmDate != null && deductStlmDate != '') {
						$("#deduct_stlm_date1").html(deductStlmDate.substring(0,10));
					}
					$("#mer_name1").html(trade.mer_name);
					$("#acqInstIdCode1").html(trade.acqInstIdCode);
					$("#tradeType1").html(trade.tradeType);
					var tradeStatus = trade.trade_status;
					if (0 == tradeStatus) {
						tradeStatus = "未处理";
					} else if(1 == tradeStatus) {
						tradeStatus = "待审核";
					} else if (2 == tradeStatus) {
						tradeStatus = "已审核";
					} else if (3 == tradeStatus) {
						tradeStatus = "已驳回";
					} else {
						tradeStatus = trade.trade_status;
					}
					$("#trade_status1").html(tradeStatus);
					$("#turnDown_remark1").html(trade.turnDown_remark);
					$("#entering_time1").html(trade.entering_time);
					$("#commit_time1").html(trade.commit_time);
					$("#handling_id1").html(trade.handling_name);
					$("#reason_code1").html(trade.reason_des);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
	//通过
	function updatePass(id,audit_operator) {
		if(!confirm("是否确认审批通过？")){
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/updatePass.do',
			data : {"id": id,"audit_operator":audit_operator},
			async : false,
			success : function(msg) {
				if (msg > 0) {
					alert("审批通过");
					paging(1);
				} else {
					alert("操作失败");
				}
			}
		});
	}
	//驳回
	function updateReject(trade_id,audit_operator) {
		$("#trade_id").val(trade_id);
		$("#audit_operator").val(audit_operator);
		$("#bohui").css({display:"block"});
	}
	//驳回
	function approvalRejectSubmit() {
		var reason = $("#reject").val();
		var trade_id = $("#trade_id").val();
		var audit_operator = $("#audit_operator").val();
		if (reason == "" || null == reason) {
			alert("请输入驳回原因");
			return;
		}
		var url= "<%=request.getContextPath()%>/updateReject.do";
		$.ajax({
			type : "post",
			url : url,
			data : {"trade_id": trade_id,"reason":reason,"audit_operator":audit_operator},
			async:false,
			success : function(msg) {
				if(msg){
					alert("驳回成功");
					paging(1);
				}else{
					alert("驳回失败");
				}
			}
		});
	}
	 //获取交易类型
	 function getTradeTypeLst() {
    	$.ajax({
    		url : '<%=request.getContextPath()%>/getTradeAmountConfList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#trade_type").append('<option value="' + msg[i]['process'] + msg[i]['trademsgType']+'">'+ msg[i]['name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取交易类型列表失败!");
    		}
    	});
    	var process = $("#process_hidden").val();
		var type = document.getElementById("trade_type");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == process){
				type.options[i].selected = 'selected';
			}
		}
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
<body onload="initChannelSelect();initErrorHandlerMethod();getTradeTypeLst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">差错处理</a>&gt;<span>银联差错审批</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/getCupsErrorApprovalData.do" target="right" name="cupsErrorApprovalSearch" id="cupsErrorApprovalSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="100%" border="0" cellspacing="0">	
					            <tr>
					            	<td align="right" nowrap="nowrap">清算日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
									  <input name="deductStlmDate" id="deductStlmDate" value="${param.deductStlmDate }"
									  	readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易类型</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="trade_type" name="trade_type" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="process_hidden" value="${trade_types }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">扣款渠道</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="channel" name="channel" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">主账号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
											<input type="text" name="out_account" id="out_account" value="${param.out_account }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
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
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery()" /> 
						                <input type="button" class="icon_normal" value="重置" onclick="clearForm()" />
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
						<c:if test="${fn:length(pageDataLst.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(pageDataLst.result) > 0 }">${fn:length(pageDataLst.result) }</c:if>
					</font>
					条数据
				</span>
				<span style="float: right;">共<font color="red">
					<c:if test="${pageDataLst.totalItems == null }">0</c:if>
					<c:if test="${pageDataLst.totalItems != null }">${pageDataLst.totalItems }</c:if>
					</font>条数据
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
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">流水号</td>
							<td align="center">清算日期</td>
							<td align="center">主账号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">交易结果</td>
							<td align="center">交易类型</td>
							<td align="center">处理方式</td>
							<td align="center">来源</td>
							<td align="center">操作员</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="13">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="cupsErrorApproval">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${cupsErrorApproval.reqSysStance }</td>
							<td align="center">${fn:substring(cupsErrorApproval.deductStlmDate,0,10)}</td>
							<td align="center">${cupsErrorApproval.out_account}</td>
							<td align="center">${fn:substring(cupsErrorApproval.trade_time,0,19)}</td>
							<td align="center"><f:formatNumber value="${cupsErrorApproval.tradeAmount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">
								<c:if test="${cupsErrorApproval.trade_result == 0 }">成功</c:if>
								<c:if test="${cupsErrorApproval.trade_result == 1 }">超时</c:if>
								<c:if test="${cupsErrorApproval.trade_result == 2 }">失败</c:if>
								<c:if test="${cupsErrorApproval.trade_result == 3 && cupsErrorApproval.process == '480000' }">受理成功</c:if>
								<c:if test="${cupsErrorApproval.trade_result == 3 && cupsErrorApproval.process != '480000' }">冲正成功</c:if>
							</td>
							<td align="center">${cupsErrorApproval.tradeType }</td>
							<td align="center">${cupsErrorApproval.handling_name }</td>
							<td align="center">${cupsErrorApproval.trade_source }</td>
							<td align="center">${cupsErrorApproval.operator }</td>
							<td align="center">
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryCupsErrorApprovalDetail('${cupsErrorApproval.id}')">详情</a>
								<c:if test="${cupsErrorApproval.trade_status == 1}">
									<a class="fl lj mr10" href="javascript:void(0);" onclick="updatePass('${cupsErrorApproval.id}', '${sessionScope.login.loginName}')">通过</a>
								</c:if>
								<c:if test="${cupsErrorApproval.trade_status == 1}">
									<a class="fl lj mr10" href="javascript:void(0);" onclick="updateReject('${cupsErrorApproval.id}', '${sessionScope.login.loginName}')">驳回</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
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
						<a href="javascript:paging(${pageDataLst.pageNo-2 })"><span>${pageDataLst.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>${pageDataLst.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDataLst.pageNo }</span></a>
					<c:if test="${pageDataLst.pageNo+1 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>${pageDataLst.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo+2 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+2 })"><span>${pageDataLst.pageNo+2 }</span></a>
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
							value="${pageDataLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
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
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="trade_time1"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">主账号：</td>
						<td id="out_account1"></td>
						<td align="right" bgcolor="#eeeeee">交易结果：</td>
						<td id="trade_result1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银联参考号：</td>
						<td id="deduct_sys_reference1"></td>
						<td align="right" bgcolor="#eeeeee">系统跟踪号：</td>
						<td id="reqSysStance1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">清算日期：</td>
						<td id="deduct_stlm_date1"></td>
						<td align="right" bgcolor="#eeeeee">商户简称：</td>
						<td id="mer_name1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">受理机构标识码：</td>
						<td id="acqInstIdCode1"></td>
						<td align="right" bgcolor="#eeeeee">交易类型：</td>
						<td id="tradeType1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易状态：</td>
						<td id="trade_status1"></td>
						<td align="right" bgcolor="#eeeeee">驳回原因：</td>
						<td id="turnDown_remark1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">录入时间：</td>
						<td id="entering_time1"></td>
						<td align="right" bgcolor="#eeeeee">提交时间：</td>
						<td id="commit_time1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理方式：</td>
						<td id="handling_id1"></td>
						<td align="right" bgcolor="#eeeeee">原因码：</td>
						<td id="reason_code1"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!--===========================驳回内容============================-->
	<div id="bohui" class="pop" style="display: none;">
		<div class="pop_body" style="width: 20%;">
			<h1 class="pop_tit">
				<span class="fl">驳回</span>
				 <a class="close" href="javascript:void(0);" onclick="hide('bohui')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">
					<tr>
						<td>
							<input type="hidden" id="trade_id" />
							<input type="hidden" id="audit_operator" />
							<textarea id="reject" style="resize:none;" cols="18" rows="10"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="approvalRejectSubmit();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
