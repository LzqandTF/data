<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>线上交易数据查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("rytDataSerarch");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryRytDataList.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("rytDataSerarch");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryRytDataList.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery() {
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var form = document.getElementById("rytDataSerarch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryRytDataList.do?pageSize=" + pageSize;
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
    		url : '<%=request.getContextPath()%>/getOnLineBankInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['original_data_tableName'] + ',' + msg[i]['tk_tableName'] + '">'+ msg[i]['bank_name'] + '</option>');
    		}
    	});
		var trade_type = $("#trade_type_hidden").val();
		var tradeType = document.getElementById("tradeType");
		for(var i = 0;i<tradeType.options.length;i++){
			if(tradeType.options[i].value == trade_type){
				tradeType.options[i].selected = 'selected';
			}
		}
		var tstat_hidden = $("#tstat_hidden").val();
		var tstat = document.getElementById("tstat");
		for(var i = 0;i<tstat.options.length;i++){
			if(tstat.options[i].value == tstat_hidden){
				tstat.options[i].selected = 'selected';
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
    			var selectObj = document.getElementById("gate");
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
	
	function initGate() {
		var gate_hidden = $("#gate_hidden").val();
		var gate = document.getElementById("gate");
		for(var i = 0;i<gate.options.length;i++){
			if(gate.options[i].value == gate_hidden){
				gate.options[i].selected = 'selected';
			}
		}
	}
	
	//根据ID获取收款交易的详细信息
	function querySkDetail(tesq) {
		var bank_id = $("#bank_id").val();
		var gate = $("#gate").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryRytSkDataDetail.do",
			data : {"bank_id":bank_id, "gate": gate, "tesq": tesq},
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
					$("#s_p15").html(ryt.p15);
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
	//根据ID获取退款交易的详细信息
	function queryTkDetail(id) {
		var bank_id = $("#bank_id").val();
		var gate = $("#gate").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryRytTkDataDetail.do",
			data : {"gate":gate,"id":id, "bank_id": bank_id},
			async : false,
			success : function(ryt) {
				if (ryt != null) {
					$("#t_id").html(ryt.id);
					$("#t_mid").html(ryt.mid);
					$("#t_oid").html(ryt.oid);
					$("#t_ref_amt").html(ryt.ref_amt);
					$("#t_ref_date").html(ryt.ref_date);
					$("#t_p1").html(ryt.p1);
					var stat = ryt.stat;
					if (1 == stat) {
						stat = "商户确认退款";
					} else if (2 == stat) {
						stat = "等待审核";
					} else if (3 == stat) {
						stat = "退款成功";
					} else if (4 == stat || 6 == stat) {
						stat = "退款失败";
					} else if (5 == stat) {
						stat = "商户申请退款";
					} else {
						stat = "其他";
					}
					$("#t_tradeType").html("退款交易");
					$("#t_tseq").html(ryt.tseq);
					$("#t_org_bk_seq").html(ryt.org_bk_seq);
					$("#t_stat").html(stat);
					$("#t_org_mdate").html(ryt.org_mdate);
					$("#t_org_oid").html(ryt.org_oid);
					$("#t_org_amt").html(ryt.org_amt);
					$("#t_org_pay_amt").html(ryt.org_pay_amt);
					$("#t_mer_fee").html(ryt.mer_fee);
					$("#t_bk_fee").html(ryt.bk_fee);
					$("#t_name").html(ryt.gid);
					$("#t_gid").html(ryt.gid);
					$("#pop2").css({display:"block"});
				} else {
					alert("获取详细信息失败！");
					hide("pop2");
				}
			}
		});
	}
	//清空表单的查询条件
	function clearForm(){
		$("#tradeType").val("1");
		$("#tseq").val("");
		$("#oid").val("");
		$("#mid").val("");
		$("#startDate").val("");
		$("#endDate").val("");
		$("#tstat").val("");
		$("#bank_id").val("");
		var selectObj = document.getElementById("gate");
		while(selectObj.firstChild) {
			selectObj.removeChild(selectObj.firstChild);
		}
		$(selectObj).append("<option value=''>全部</option>");
	}
	function clearEndTime(){
		$("#endTime").val("");
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
</script>
</head>
<body onload="initBankInst();initGate();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">交易数据查询</a>&gt;<span>线上交易数据查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryRytDataList.do" target="right" id="rytDataSerarch" name="rytDataSerarch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">交易类型</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="tradeType" name="tradeType" style="width: 150px;">
												<option value="1">收款交易</option>
												<option value="2">退款交易</option>
											</select>
											<input type="hidden" id="trade_type_hidden" value="${tradeType }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">电银流水号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="tseq" id="tseq" value="${param.tseq }" onkeyup="value=value.replace(/[^\d]/g,'')" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">订单号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="oid" id="oid" value="${param.oid }"/>
					                     </span>
					                </td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap">商户号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="mid" id="mid" value="${param.mid }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易日期</td>
					            	<td nowrap="nowrap">
										<span style="width: 30px;" class="input_bgl"> 
											<input style="width: 70px" id="startDate" name="startDate" value="${param.startDate }"
												maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'});clearEndTime();" />
											- 
											<input style="width: 70px" id="endDate" name="endDate" value="${param.endDate }"
												maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" />
										</span>
									</td>
					                <td align="right" nowrap="nowrap">交易状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="tstat" name="tstat" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">初始状态</option>
												<option value="1">待支付</option>
												<option value="2">成功</option>
												<option value="3">失败</option>
												<option value="4">请求银行失败</option>
												<option value="5">撤销</option>
											</select>
											<input type="hidden" id="tstat_hidden" value="${tstat }"/>
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
					                <td align="right" nowrap="nowrap">支付渠道</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
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
					<c:if test="${tradeType == 1 }">
						<c:if test="${fn:length(rytUpmpData.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(rytUpmpData.result) > 0 }">${fn:length(rytUpmpData.result) }</c:if>
					</c:if>
					<c:if test="${tradeType == 2 }">
						<c:if test="${fn:length(rytRefundLog.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(rytRefundLog.result) > 0 }">${fn:length(rytRefundLog.result) }</c:if>
					</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${tradeType == 1 }">
						<c:if test="${rytUpmpData.totalItems == null }">0</c:if>
						<c:if test="${rytUpmpData.totalItems != null }">${rytUpmpData.totalItems }</c:if>
					</c:if>
					<c:if test="${tradeType == 2 }">
						<c:if test="${rytRefundLog.totalItems == null }">0</c:if>
						<c:if test="${rytRefundLog.totalItems != null }">${rytRefundLog.totalItems }</c:if>
					</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${tradeType == 1 }">
						<c:if test="${rytUpmpData.totalPages == null}">0</c:if>
						<c:if test="${rytUpmpData.totalPages != null}">${rytUpmpData.totalPages}</c:if>
					</c:if>
					<c:if test="${tradeType == 2 }">
						<c:if test="${rytRefundLog.totalPages == null}">0</c:if>
						<c:if test="${rytRefundLog.totalPages != null}">${rytRefundLog.totalPages}</c:if>
					</c:if>
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
				<c:if test="${tradeType == 1 || tradeType == null}">
					<table width="100%" border="0" cellspacing="0">
						<thead>
							<tr>
								<td align="center">电银流水号</td>
								<td align="center">商户号</td>
								<td align="center">商户订单号</td>
								<td align="center">银行流水</td>
								<td align="center">交易时间</td>
								<td align="center">交易金额(元)</td>
								<td align="center">交易状态</td>
								<td align="center">交易类别</td>
								<td align="center">交易银行</td>
								<td align="center">支付渠道</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:if test="${fn:length(rytUpmpData.result)<=0 }">
							<tr align="center">
								<td colspan="11">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${rytUpmpData.result }" var="rytUpmp">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${rytUpmp.tseq}</td>
								<td align="center">${rytUpmp.mid}</td>
								<td align="center">${rytUpmp.oid }</td>
								<td align="center">${rytUpmp.bk_seq1 }</td>
								<td align="center">${rytUpmp.sys_date }</td>
								<td align="center">
									<f:formatNumber value="${rytUpmp.amount }" pattern="0.00"></f:formatNumber>
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
								<td align="center">${rytUpmp.name }</td>
								<td>
									<a class="fl lj mr10" href="#" onclick="querySkDetail('${rytUpmp.tseq}')">详情</a>
								</td>
							</tr>
						</c:forEach>
					</table>
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
				</c:if>
				
				<c:if test="${tradeType == 2 }">
					<table width="100%" border="0" cellspacing="0">
						<thead>
							<tr>
								<td align="center">退款流水号</td>
								<td align="center">商户号</td>
								<td align="center">退款订单号</td>
								<td align="center">退款金额</td>
								<td align="center">退款交易日期</td>
								<td align="center">清算日期</td>
								<td align="center">退款状态</td>
								<td align="center">交易类别</td>
								<td align="center">原电银流水号</td>
								<td align="center">原商户订单号</td>
								<td align="center">交易银行</td>
								<td align="center">支付渠道</td>
								<td>操作</td>
							</tr>
						</thead>
						<c:if test="${fn:length(rytRefundLog.result)<=0 }">
								<tr align="center">
									<td colspan="13">对不起,暂无数据！</td>
								</tr>
						</c:if>
						<c:forEach items="${rytRefundLog.result }" var="rytLog">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${rytLog.id}</td>
								<td align="center">${rytLog.mid }</td>
								<td align="center">${rytLog.oid }</td>
								<td align="center">
									<f:formatNumber value="${rytLog.ref_amt }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">${rytLog.ref_date }</td>
								<td align="center">${rytLog.p1 }</td>
								<td align="center">
									<c:if test="${rytLog.stat == 1}">经办成功</c:if>
									<c:if test="${rytLog.stat == 2}">审核成功</c:if>
									<c:if test="${rytLog.stat != 1 && rytLog.stat != 2}">其他</c:if>
								</td>
								<td align="center">退款交易</td>
								<td align="center">${rytLog.tseq }</td>
								<td align="center">${rytLog.org_oid }</td>
								<td align="center">${rytLog.name }</td>
								<td align="center">${rytLog.name }</td>
								<td align="center">
									<a class="fl lj mr10" href="#" onclick="queryTkDetail('${rytLog.id}')">详情</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<c:if test="${rytRefundLog.totalPages != null}">
						<div class="next">
							<c:if test="${rytRefundLog.pageNo > 1}">
								<a href="javascript:paging(1)"><span>首页</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo > 1}">
								<a href="javascript:paging(${rytRefundLog.pageNo-1 })"><span>上一页</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo-3 > 0}">
								<b><span>...</span></b>
							</c:if>
							<c:if test="${rytRefundLog.pageNo-2 > 0}">
								<a href="javascript:paging(${rytRefundLog.pageNo-2 })"><span>${rytRefundLog.pageNo-2
										}</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo-1 > 0}">
								<a href="javascript:paging(${rytRefundLog.pageNo-1 })"><span>${rytRefundLog.pageNo-1
										}</span></a>
							</c:if>
							<a href="#" class="hover"><span>${rytRefundLog.pageNo }</span></a>
							<c:if test="${rytRefundLog.pageNo+1 <= rytRefundLog.totalPages}">
								<a href="javascript:paging(${rytRefundLog.pageNo+1 })"><span>${rytRefundLog.pageNo+1
										}</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo+2 <= rytRefundLog.totalPages}">
								<a href="javascript:paging(${rytRefundLog.pageNo+2 })"><span>${rytRefundLog.pageNo+2
										}</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo+3 <= rytRefundLog.totalPages}">
								<b><span>...</span></b>
							</c:if>
							<c:if test="${rytRefundLog.pageNo < rytRefundLog.totalPages}">
								<a href="javascript:paging(${rytRefundLog.pageNo+1 })"><span>下一页</span></a>
							</c:if>
							<c:if test="${rytRefundLog.pageNo > 1}">
								<a href="javascript:paging(${rytRefundLog.totalPages })"><span>尾页</span></a>
							</c:if>
							<b>
								<span>共${rytRefundLog.totalPages }页 跳到第
								<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
									value="${rytRefundLog.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
								</span>
							</b>
						</div>
					</c:if>
				</c:if>
			</div>
			
		<!--===========================弹出内容============================-->
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
							<td align="right" bgcolor="#eeeeee">银行手续费：</td>
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
							<td align="right" bgcolor="#eeeeee">出账用户ID：</td>
							<td id="out_user_id"></td>
							<td align="right" bgcolor="#eeeeee">入账用户ID：</td>
							<td id="in_user_id"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>	
		<div id="pop2" class="pop" style="display: none">
			<div class="pop_body" style="margin-top: 10px;">
				<h1 class="pop_tit">
					<span class="fl">退款交易明细</span> <a class="close"
						href="javascript:void(0);" onclick="hide('pop2')">&nbsp;</a>
				</h1>
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0">
						<tr>
							<td align="right" bgcolor="#eeeeee">退款流水号：</td>
							<td id="t_id"></td>
							<td align="right" bgcolor="#eeeeee">商户号：</td>
							<td id="t_mid"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">退款金额：</td>
							<td id="t_ref_amt"></td>
							<td align="right" bgcolor="#eeeeee">退款交易日期：</td>
							<td id="t_ref_date"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">清算日期：</td>
							<td id="t_p1"></td>
							<td align="right" bgcolor="#eeeeee">退款状态：</td>
							<td id="t_stat"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">交易类别：</td>
							<td id="t_tradeType"></td>
							<td align="right" bgcolor="#eeeeee">原电银流水号：</td>
							<td id="t_tseq"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">原银行流水号：</td>
							<td id="t_org_bk_seq"></td>
							<td align="right" bgcolor="#eeeeee">原交易时间：</td>
							<td id="t_org_mdate"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">原商户订单号：</td>
							<td id="t_org_oid"></td>
							<td align="right" bgcolor="#eeeeee">原交易金额：</td>
							<td id="t_org_amt"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">原实际交易金额：</td>
							<td id="t_org_pay_amt"></td>
							<td align="right" bgcolor="#eeeeee">退回给商户的手续费：</td>
							<td id="t_mer_fee"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银行退回的手续费：</td>
							<td id="t_bk_fee"></td>
							<td align="right" bgcolor="#eeeeee">支付银行：</td>
							<td id="t_name"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">支付渠道：</td>
							<td id="t_gid"></td>
							<td align="right" bgcolor="#eeeeee">退款订单号：</td>
							<td id="t_oid"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>