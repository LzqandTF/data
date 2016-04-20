<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户资金流水查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

	function init(){
		var mer_state = $("#mer_state_hidden").val();
		var mer_states = document.getElementById("mer_state");
		
		for(var i = 0;i<mer_states.options.length;i++){
			if(mer_states.options[i].value == mer_state){
				mer_states.options[i].selected = 'selected';
			}
		}
		var mer_category = $("#mer_category_hidden").val();
		var mer_categorys = document.getElementById("mer_category");
		
		for(var i = 0;i<mer_categorys.options.length;i++){
			if(mer_categorys.options[i].value == mer_category){
				mer_categorys.options[i].selected = 'selected';
			}
		}
		
		var derc_status = $("#derc_status_hidden").val();
		var derc_statuss = document.getElementById("derc_status");
		
		for(var i = 0;i<derc_statuss.options.length;i++){
			if(derc_statuss.options[i].value == derc_status){
				derc_statuss.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	//分页查询
	function paging(pageNo) {
		var startDate = $("#startDate").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var end_date = $("#end_date").val();
		if((startDate == null || startDate == '') && (deduct_stlm_date == null || deduct_stlm_date == '') && (end_date == null || end_date == "")){
			alert("请选择交易日期或清算日期或流水日期！");
			return;
		}
		var form = document.getElementById("merFundStanceSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundStancePage.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("merFundStanceSearch");
		var startDate = $("#startDate").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var end_date = $("#end_date").val();
		if((startDate == null || startDate == '') && (deduct_stlm_date == null || deduct_stlm_date == '') && (end_date == null || end_date == "")){
			alert("请选择交易日期或清算日期或流水日期！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundStancePage.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var endDate = $("#endDate").val();
		var end_date = $("#end_date").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		if((endDate == null || endDate == '') && (deduct_stlm_date == null || deduct_stlm_date == '') && (end_date == null || end_date == '')){
			alert("请选择交易日期或清算日期或流水日期！");
			return;
		}
		var form = document.getElementById("merFundStanceSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundStancePage.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//表单隐藏域
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//下载excel表格
	function downExcel(){
		//根据查询条件下载
		var startDate = $("#startDate").val();
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var start_date = $("#start_date").val();
		var end_date = $("#end_date").val();
		var endDate = $("#endDate").val();
		if((endDate == null || endDate == '') && (deduct_stlm_date == null || deduct_stlm_date == '') && (end_date == null || end_date == '')){
			alert("请选择交易日期或清算日期或流水日期！");
			return;
		}
		var mer_category = $("#mer_category").val();
		var mer_state = $("#mer_state").val();
		var trade_stance = $("#trade_stance").val();
		var mer_code = $("#mer_code").val();
		var url ="<%=request.getContextPath()%>/merFundStanceDownLoadExcel.do?startDate="+startDate+
				"&endDate="+endDate+"&mer_category="+mer_category+
				"&mer_state="+mer_state+"&trade_stance="+trade_stance+
				"&mer_code="+mer_code+"&deduct_stlm_date="+deduct_stlm_date+
				"&start_date="+start_date+"&end_date="+end_date;
		window.location=url;
	}
	function clearEndTime(obj){
		$(obj).val("");
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
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>商户资金流水查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerFundStancePage.do" target="right" id="merFundStanceSearch" name="merFundStanceSearch" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table align="center" style="width: 100%;">
						<tr>
							<td align="right" nowrap="nowrap">商户号</td>
							<td nowrap="nowrap">
								<span class="input_bgl">
									<input maxlength="15" type="text" name="mer_code" onkeyup="value=value.replace(/[^\d]/g,'')" id="mer_code" value="${param.mer_code }" />
								</span>
							</td>
							<td align="right" nowrap="nowrap">交易流水号</td>
							<td nowrap="nowrap">
								<span class="input_bgl">
									<input type="text" name="trade_stance" id="trade_stance" value="${param.trade_stance }" />
								</span>
							</td>
							<td align="right" nowrap="nowrap">商户状态</td>
							<td nowrap="nowrap">
								<span  class="input_bgl">
									<select id="mer_state" name="mer_state">
										<option value="">--全部--</option>
										<option value="5">开通</option>
										<option value="6">关闭</option>
									</select>
									<input type="hidden" id="mer_state_hidden" value="${mer_state }"/>
								</span>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">交易日期</td>
			            	<td nowrap="nowrap">
								<span style="width: 70px;" class="input_bgl"> 
									<input style="width: 70px" id="startDate" name="startDate" value="${param.startDate }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'});clearEndTime(endDate);" />
									- 
									<input style="width: 70px" id="endDate" name="endDate" value="${param.endDate }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'#F{$dp.$D(\'startDate\',{M:1})}'})" />
										<font color="red">*</font>
								</span>
							</td>
							<td align="right" nowrap="nowrap">商户类型</td>
							<td nowrap="nowrap">
								<span  class="input_bgl">
									<select id="mer_category" name="mer_category">
										<option value="">--全部--</option>
										<!-- <option value="2">线下商户</option>
										<option value="1">线上商户</option> -->
										<option value="0">RYF商户</option>
			                     		<option value="1">VAS商户</option>
			                     		<option value="2">POS商户</option>
									</select>
									<input type="hidden" id="mer_category_hidden" value="${mer_category }"/>
								</span>
							</td>
							<td align="right" nowrap="nowrap">简短描述</td>
							<td nowrap="nowrap">
								<span  class="input_bgl">
									<select id="derc_status" name="derc_status">
										<option value="">--全部--</option>
										<option value="1">消费(支付)</option>
										<option value="2">退款(冲正)</option>
										<option value="3">差错调整(支付)</option>
										<option value="4">差错调整(冲正)</option>
										<option value="5">结算到电银账户</option>
										<option value="6">结算到银行账户</option>
										<option value="7">手工调账</option>
									</select>
									<input type="hidden" id="derc_status_hidden" value="${derc_status }"/>
								</span>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">清算日期</td>
			                <td align="left" nowrap="nowrap">
			                	<span style="width:30px;" class="input_bgl">
									<input name="deduct_stlm_date" id="deduct_stlm_date" value="${param.deduct_stlm_date }" 
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
										<font color="red">*</font>
							    </span>
			                </td>
			                <td align="right" nowrap="nowrap">流水日期</td>
			            	<td nowrap="nowrap">
								<span style="width: 70px;" class="input_bgl"> 
									<input style="width: 70px" id="start_date" name="start_date" value="${param.start_date }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_date\')||\'%y-%M-%d\'}'});clearEndTime(end_date);" />
									- 
									<input style="width: 70px" id="end_date" name="end_date" value="${param.end_date }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_date\')}',maxDate:'#F{$dp.$D(\'start_date\',{M:1})}'})" />
										<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center;">
								<br />
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="下载" onclick="downExcel()"/>
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
				<c:if test="${fn:length(pageMerFundStance.result) <= 0 }">0</c:if>
				<c:if test="${fn:length(pageMerFundStance.result) > 0 }">${fn:length(pageMerFundStance.result) }</c:if>
			</font>
			条数据
			</span>
			<span style="float: right">共<font color="red">
				<c:if test="${pageMerFundStance.totalItems == null }">0</c:if>
				<c:if test="${pageMerFundStance.totalItems != null }">${pageMerFundStance.totalItems }</c:if>
			</font>条数据
			<font color="red">
				<c:if test="${pageMerFundStance.totalPages == null}">0</c:if>
				<c:if test="${pageMerFundStance.totalPages != null}">${pageMerFundStance.totalPages}</c:if>
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
							<td align="center">流水时间</td>
							<td align="center">商户号</td>
							<td align="center">商户简称</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">商户手续费</td>
							<td align="center">变动金额</td>
							<td align="center">账户余额</td>
							<td align="center">交易流水号</td>
							<td align="center">简短说明</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageMerFundStance.result)<=0 }">
						<tr align="center">
							<td colspan="9">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageMerFundStance.result }" var="data">
						<tr>
							<td align="center">${data.stance_time }</td>
							<td align="center">${data.mer_code }</td>
							<td align="center">${data.mer_name }</td>
							<td align="center">${data.trade_time }</td>
							<td align="center">
								<f:formatNumber value="${data.trade_amount}" pattern="0.00" />
							</td>
							<td align="center">
								<f:formatNumber value="${data.mer_fee}" pattern="0.00" />
							</td>
							<td align="center">
								<f:formatNumber value="${data.change_amount}" pattern="0.00" />
							</td>
							<td align="center">
								<f:formatNumber value="${data.account_amount}" pattern="0.00" />
							</td>
							<td align="center">${data.trade_stance }</td>
							<td align="center">
								<c:if test="${data.derc_status==1 }">消费(支付)</c:if>
								<c:if test="${data.derc_status==2 }">退款(冲正)</c:if>
								<c:if test="${data.derc_status==3 }">差错调整(支付)</c:if>
								<c:if test="${data.derc_status==4 }">差错调整(冲正)</c:if>
								<c:if test="${data.derc_status==5 }">结算到电银账户</c:if>
								<c:if test="${data.derc_status==6 }">结算到银行账户</c:if>
								<c:if test="${data.derc_status==7 }">手工调账</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageMerFundStance.totalPages != null}">
				<div class="next">
					<c:if test="${pageMerFundStance.pageNo > 1}">
						<a href="javascript:paging(${pageMerFundStance.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo-2 > 0}">
						<a href="javascript:paging(${pageMerFundStance.pageNo-2 })"><span>${pageMerFundStance.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo-1 > 0}">
						<a href="javascript:paging(${pageMerFundStance.pageNo-1 })"><span>${pageMerFundStance.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageMerFundStance.pageNo }</span></a>
					<c:if test="${pageMerFundStance.pageNo+1 <= pageMerFundStance.totalPages}">
						<a href="javascript:paging(${pageMerFundStance.pageNo+1 })"><span>${pageMerFundStance.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo+2 <= pageMerFundStance.totalPages}">
						<a href="javascript:paging(${pageMerFundStance.pageNo+2 })"><span>${pageMerFundStance.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo+3 <= pageMerFundStance.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageMerFundStance.pageNo < pageMerFundStance.totalPages}">
						<a href="javascript:paging(${pageMerFundStance.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageMerFundStance.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageMerFundStance.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>
