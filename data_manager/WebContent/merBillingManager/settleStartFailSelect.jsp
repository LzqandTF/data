<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算发起失败查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

	function init(){
		$.ajax({
			url : '<%=request.getContextPath()%>/getSettleFailReason.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#reason").append('<option value="' + msg[i]['reason_id'] + '">'+ msg[i]['reason'] + '</option>');
			},
			error : function(msg) {
				alert("获取结算发起失败原因信息!");
			}
		});
		var reason_id = $("#reason_id_hidden").val();
		var type = document.getElementById("reason");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == reason_id){
				type.options[i].selected = 'selected';
			}
		}
		var mer_type = $("#mer_type_hidden").val();
		var type = document.getElementById("mer_type");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == mer_type){
				type.options[i].selected = 'selected';
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
		var startTime = $("#startTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择结算发起起始日期！");
			return;
		}
		var endTime = $("#endTime").val();
		if(endTime == null || endTime == ''){
			alert("请选择结算发起截止日期！");
			return;
		}
		var form = document.getElementById("merchantSettleFailSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/querySettleFailInfo.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var startTime = $("#startTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择结算发起起始日期！");
			return;
		}
		var endTime = $("#endTime").val();
		if(endTime == null || endTime == ''){
			alert("请选择结算发起截止日期！");
			return;
		}
		var form = document.getElementById("merchantSettleFailSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/querySettleFailInfo.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var startTime = $("#startTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择结算发起起始日期！");
			return;
		}
		var endTime = $("#endTime").val();
		if(endTime == null || endTime == ''){
			alert("请选择结算发起截止日期！");
			return;
		}
		var form = document.getElementById("merchantSettleFailSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/querySettleFailInfo.do?pageSize=" + pageSize;
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
		var startTime = $("#startTime").val();
		if(startTime == null || startTime == ''){
			alert("请选择交期起始日期！");
			return;
		}
		var endTime = $("#endTime").val();
		if(endTime == null || endTime == ''){
			alert("请选择交期截止日期！");
			return;
		}
		//根据查询条件下载
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		var reason = $("#reason").val();
		var mer_type = $("#mer_type").val();
		var mer_code = $("#mer_code").val();
		var url ="<%=request.getContextPath()%>/settleFailDownExcel.do?startTime="+startTime+
				"&endTime="+endTime+"&start_time="+start_time+
				"&end_time="+end_time+"&reason="+reason+
				"&mer_type="+mer_type+"&mer_code="+mer_code;
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
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>结算发起失败查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/querySettleFailInfo.do" target="right" id="merchantSettleFailSearch" name="merchantSettleFailSearch" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table align="center" style="width: 100%;">
						<tr>
							<td align="right" nowrap="nowrap">结算发起日期</td>
			            	<td nowrap="nowrap">
								<span style="width: 70px;" class="input_bgl"> 
									<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime(endTime);" />
									- 
									<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
										<font color="red">*</font>
								</span>
							</td>
							<td align="right" nowrap="nowrap">失败原因</td>
							<td nowrap="nowrap">
								<span style="width: 200px;" class="input_bgl">
									<select id="reason" name="reason">
										<option value="">-------全部-------</option>
									</select>
									<input type="hidden" id="reason_id_hidden" value="${reason_id }"/>
								</span>
							</td>
							<td align="right" nowrap="nowrap">商户类型</td>
							<td nowrap="nowrap">
								<span  class="input_bgl">
									<select id="mer_type" name="mer_type">
										<option value="">--全部--</option>
										<option value="0">RYF商户</option>
										<option value="1">VAS商户</option>
										<option value="2">POS商户</option>
									</select>
									<input type="hidden" id="mer_type_hidden" value="${mer_type }"/>
								</span>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">结算截止日期</td>
			            	<td nowrap="nowrap">
								<span style="width: 30px;" class="input_bgl"> 
									<input style="width: 70px" id="start_time" name="start_time" value="${param.start_time }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_time\')||\'%y-%M-%d\'}'});clearEndTime(end_time);" />
									- 
									<input style="width: 70px" id="end_time" name="end_time" value="${param.end_time }"
										readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_time\')}',maxDate:'%y-%M-%d'})" />
								</span>
							</td>
							<td align="right" nowrap="nowrap">商户号</td>
							<td nowrap="nowrap">
								<span class="input_bgl">
									<input maxlength="15" type="text" name="mer_code" id="mer_code" value="${param.mer_code }" />
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
				<c:if test="${fn:length(pageMerchantSettleFailInfo.result) <= 0 }">0</c:if>
				<c:if test="${fn:length(pageMerchantSettleFailInfo.result) > 0 }">${fn:length(pageMerchantSettleFailInfo.result) }</c:if>
			</font>
			条数据
			</span>
			<span style="float: right">共<font color="red">
				<c:if test="${pageMerchantSettleFailInfo.totalItems == null }">0</c:if>
				<c:if test="${pageMerchantSettleFailInfo.totalItems != null }">${pageMerchantSettleFailInfo.totalItems }</c:if>
			</font>条数据
			<font color="red">
				<c:if test="${pageMerchantSettleFailInfo.totalPages == null}">0</c:if>
				<c:if test="${pageMerchantSettleFailInfo.totalPages != null}">${pageMerchantSettleFailInfo.totalPages}</c:if>
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
							<td align="center">商户类型</td>
							<td align="center">商户号</td>
							<td align="center">商户简称</td>
							<td align="center">结算截止日期</td>
							<td align="center">结算发起日期</td>
							<td align="center">失败原因</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageMerchantSettleFailInfo.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageMerchantSettleFailInfo.result }" var="data">
						<tr>
							<td align="center">
								<c:if test="${data.mer_type == 0 }">RYF商户</c:if>
								<c:if test="${data.mer_type == 1 }">VAS商户</c:if>
								<c:if test="${data.mer_type == 2 }">POS商户</c:if>
							</td>
							<td align="center">${data.mer_code }</td>
							<td align="center">${data.mer_name }</td>
							<td align="center">${data.last_settle_date }</td>
							<td align="center">${data.settle_start_date }</td>
							<td align="center">${data.reason }</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageMerchantSettleFailInfo.totalPages != null}">
				<div class="next">
					<c:if test="${pageMerchantSettleFailInfo.pageNo > 1}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo-2 > 0}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo-2 })"><span>${pageMerchantSettleFailInfo.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo-1 > 0}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo-1 })"><span>${pageMerchantSettleFailInfo.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageMerchantSettleFailInfo.pageNo }</span></a>
					<c:if test="${pageMerchantSettleFailInfo.pageNo+1 <= pageMerchantSettleFailInfo.totalPages}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo+1 })"><span>${pageMerchantSettleFailInfo.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo+2 <= pageMerchantSettleFailInfo.totalPages}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo+2 })"><span>${pageMerchantSettleFailInfo.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo+3 <= pageMerchantSettleFailInfo.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageMerchantSettleFailInfo.pageNo < pageMerchantSettleFailInfo.totalPages}">
						<a href="javascript:paging(${pageMerchantSettleFailInfo.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageMerchantSettleFailInfo.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageMerchantSettleFailInfo.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>
