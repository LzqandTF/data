<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银联差错查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox/wbox-min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("cupsErrorSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryCupsErrorLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("cupsErrorSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/queryCupsErrorLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var form = document.getElementById("cupsErrorSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryCupsErrorLst.do?pageSize=" + pageSize;
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
		$("#out_account").val("");
		$("#entering_time").val("");
		$("#reqSysStance").val("");
		$("#handling_id").val("");
		$("#trade_status").val("");
		$("#channel").val("");
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
	//excel表格下载
	function downExcel(){
		//得到页面上的值
		var deductStlmDate = $("#deductStlmDate").val();
		var out_account = $("#out_account").val();
		var tradeMsgType = $("#tradeMsgType").val();
		var entering_time = $("#entering_time").val();
		var reqSysStance = $("#reqSysStance").val();
		var handling_id = $("#handling_id").val();
		var trade_status = $("#trade_status").val();
		var channel = $("#channel").val();
		var url ="<%=request.getContextPath()%>/cupsErrorDownExcel.do?deductStlmDate="+deductStlmDate+"&out_account="+
				out_account+"&tradeMsgType="+tradeMsgType+"&reqSysStance="+reqSysStance+
				"&handling_id="+handling_id+"&trade_status="+trade_status+"&channel="+channel
				+"&entering_time="+entering_time;
		window.location=url;
	}
	//已处理差错查询 明细查询
	function queryDetailData(id) {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryCupsErrorDetailData.do',
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
					if (deductStlmDate != null || deductStlmDate != '') {
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
	//重提交
	function againCommit(id,operator) {
		if(!confirm("是否确认重提交？")){
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/againCommit.do',
			data : {"id": id,"operator":operator},
			async : false,
			success : function(msg) {
				if (msg > 0) {
					alert("重提交成功");
					paging(1);
				} else {
					alert("重提交失败");
				}
			}
		});
	}
	//重录入
	function againCupsErrorInput(id,instId) {
		$.ajax({
			url : '<%=request.getContextPath()%>/againCupsErrorInput.do',
			type : 'post',
			data : {"id": id,"instId":instId},
			async : false,
			success : function(data) {
			    $("#againCupsErrorInputDialogbox").wBox({title: "重录入",html:data,show:true});
			},
			error : function(msg) {
				alert("操作异常");
			}
		});
	}
	//获取原因码列表
	function getReasonCode(id) {
		if(id == ""){
			alert("请选择差错处理方式");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/getReasonCodeLstId.do',
    		type : 'post',
    		data : "id="+ id,
    		async : false,
    		dataType : "text",
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("reasonCode");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				$(selectObj).append("<option value=''>请选择...</option>");
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].reason_id+">"+data[i].reason_desc+"</option>");
				}
    		},
    		error : function(msg) {
    			alert("获取差错原因码列表失败!");
    		}
    	});
	}
	function addCupsErrorData(id,operator) {
		var reqSysStance2 = $("#reqSysStance2").val();
		if (reqSysStance2 == "" || reqSysStance2 == null) {
			alert("系统跟踪号不能为空！");
			return;
		}
		var tradeTime2 = $("#tradeTime2").val();
		if(tradeTime2 == "") {
			alert("交易时间不能为空！");
			return;
		}
		var deductStlmDate2 = $("#deductStlmDate2").val();
		if (deductStlmDate2 == "") {
			alert("清算日期不能为空！");
			return;
		}
		var tradeAmount2 = $("#tradeAmount2").val();
		if (tradeAmount2 == "") {
			alert("交易金额不能为空！");
			return;
		}
		var out_account2 = $("#out_account2").val();
		if (out_account2 == "") {
			alert("主账号不能为空！");
			return;
		}
		var acqInstIdCode2 = $("#acqInstIdCode2").val();
		if (acqInstIdCode2 == "") {
			alert("受理机构代码不能为空！");
			return;
		}
		var deduct_sys_id = $("#inst_id").val();
		if (deduct_sys_id == "" || deduct_sys_id == null) {
			alert("请选择扣款渠道！");
			return;
		}
		var trade_type2 = $("#trade_type2").val();
		if (trade_type2 == "") {
			alert("请选择交易类型！");
			return;
		}
		var handling_id = $("#errorHandleMethodList").val();
		if (handling_id == "") {
			alert("请选择处理方式！");
			return;
		}
		var reasonCode = $("#reasonCode").val();
		if (reasonCode == "") {
			alert("请选择原因码！");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/updateCupsErrorInputData.do',
    		type : 'post',
    		data : {"id":id, "operator": operator, "reqSysStance2": reqSysStance2,"deductStlmDate2":deductStlmDate2,"tradeTime2":tradeTime2,"tradeAmount2":tradeAmount2, "out_account2" : out_account2,"acqInstIdCode2":acqInstIdCode2, "deduct_sys_id": deduct_sys_id, "trade_type2": trade_type2, "handling_id": handling_id, "reasonCode":reasonCode},
    		async : false,
    		success : function(msg) {
    			if(msg){
					alert("重录入操作成功");
					$("#wBox_close").click();
					paging(1);
				}else{
					alert("重录入操作失败");
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
		var trade_sta = $("#trade_status_hidden").val();
		var tradeStatus = document.getElementById("trade_status");
		for (var i = 0; i < tradeStatus.options.length; i++) {
			if (tradeStatus.options[i].value == trade_sta) {
				tradeStatus.options[i].selected = 'selected';
			}
		}
	 }
	//实时动态强制更改用户录入
	function amount(th) {
		var regStrs = [
			//['^0(\\d+)$','$1'],//禁止录入整数部分两位以上，但首位为0
			['[^\\d\\.]+$', ''],//禁止录入任何非数字和小数点
			['\\.(\\d?)\\.+','.$1'],//禁止录入两个以上的小数点
			['^(\\d+\\.\\d{2}).+','$1']//禁止录入小数点两位以后
		];
		for (i = 0; i < regStrs.length; i++) {
			var reg = new RegExp(regStrs[i][0]);
			th.value = th.value.replace(reg, regStrs[i][1]);
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
				当前位置：<a href="javascript:void(0)">差错处理</a>&gt;<span>银联差错查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryCupsErrorLst.do" target="right" name="cupsErrorSearch" id="cupsErrorSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="90%" border="0" cellspacing="0">
				            <tr>
				            	<td align="right" nowrap="nowrap">清算日期</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
										<input name="deductStlmDate" id="deductStlmDate" value="${param.deductStlmDate }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								  </span>
				                </td>
				            	<td align="right" nowrap="nowrap">主账号</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
										<input type="text" name="out_account" id="out_account" value="${param.out_account }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
								  </span>
				                </td>
				               <td align="right" nowrap="nowrap">录入时间</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
									<input name="entering_time" id="entering_time" value="${param.entering_time }"
										maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								  </span>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">交易类型</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="trade_type" name="trade_type" style="width: 150px;">
											<option value="">全部</option>
										</select>
				                     </span>
				                     <input type="hidden" id="process_hidden" value="${trade_types }"/>
				                </td>
				                <td align="right" nowrap="nowrap">交易流水号</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
										<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
								  </span>
				                </td>
				               <td align="right" nowrap="nowrap">处理方式</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="handling_id" name="handling_id" style="width: 150px;">
													<option value="">全部</option>
											</select>
				                     </span>
				                     <input type="hidden" id="handling_name_hidden" value="${handling_name }"/>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">扣款渠道</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="channel" name="channel" style="width: 150px;">
											<option value="">全部</option>
										</select>
				                     </span>
				                     <input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
				                </td>
				            	<td align="right" nowrap="nowrap">状态</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select id="trade_status" name="trade_status" style="width: 150px;">
											<option value="">全部</option>
											<option value="0">未处理</option>
											<option value="1">待审核</option>
											<option value="2">已审核</option>
											<option value="3">已驳回</option>
										</select>
				                     </span>
				                     <input type="hidden" id="trade_status_hidden" value="${trade_status }"/>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" /> 
					                <input type="button" class="icon_normal" value="重置" onclick="clearForm()" />
					                <!-- <input type="button" class="icon_normal" value="下载xls报表" onclick="downExcel()" /> -->
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
							<td align="center">清算日期</td>
							<td align="center">主账号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">交易结果</td>
							<td align="center">系统跟踪号</td>
							<td align="center">交易类型</td>
							<td align="center">处理方式</td>
							<td align="center">来源</td>
							<td align="center">状态</td>
							<td align="center">驳回原因</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="13">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="cupsErrorData">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${fn:substring(cupsErrorData.deductStlmDate,0,10)}</td>
							<td align="center">${cupsErrorData.out_account}</td>
							<td align="center">${fn:substring(cupsErrorData.trade_time,0,19)}</td>
							<td align="center"><f:formatNumber value="${cupsErrorData.tradeAmount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">
								<c:if test="${cupsErrorData.trade_result == 0 }">成功</c:if>
								<c:if test="${cupsErrorData.trade_result == 1 }">超时</c:if>
								<c:if test="${cupsErrorData.trade_result == 2 }">失败</c:if>
								<c:if test="${cupsErrorData.trade_result == 3 && cupsErrorData.process == '480000' }">受理成功</c:if>
								<c:if test="${cupsErrorData.trade_result == 3 && cupsErrorData.process != '480000' }">冲正成功</c:if>
							</td>
							<td align="center">${cupsErrorData.reqSysStance }</td>
							<td align="center">${cupsErrorData.tradeType }</td>
							<td align="center">${cupsErrorData.handling_name }</td>
							<td align="center">${cupsErrorData.trade_source }</td>
							<td align="center">
								<c:if test="${cupsErrorData.trade_status == 0 }">未处理</c:if>
								<c:if test="${cupsErrorData.trade_status == 1 }">待审核</c:if>
								<c:if test="${cupsErrorData.trade_status == 2 }">已审核</c:if>
								<c:if test="${cupsErrorData.trade_status == 3 }">已驳回</c:if>
							</td>
							<td align="center">${cupsErrorData.turnDown_remark }</td>
							<td align="center">
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryDetailData('${cupsErrorData.id}')">详情</a>
								<c:if test="${cupsErrorData.trade_status == 3 || (cupsErrorData.trade_status == 0 && cupsErrorData.trade_source == '直接录入') }">
									<a class="fl lj mr10" href="javascript:void(0);" onclick="againCommit('${cupsErrorData.id}', '${sessionScope.login.loginName}')">重提交</a>
								</c:if>
								<c:if test="${cupsErrorData.trade_status != 2 && cupsErrorData.trade_source != '内部差错'}">
									<a class="fl lj mr10" href="javascript:void(0);" onclick="againCupsErrorInput('${cupsErrorData.id}',${cupsErrorData.deduct_sys_id});">重录入</a>
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
						<td align="right" bgcolor="#eeeeee">审核时间：</td>
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
</body>
</html>
