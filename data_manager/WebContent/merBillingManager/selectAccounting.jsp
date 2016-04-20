<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户账务查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

	//分页查询 
	function paging(pageNo) {
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易起始日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易结束日期");
			return ;
		}
		var form = document.getElementById("queryMerAccounting");
		
		var pageSize = $("#pageSize").val();
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAccounting.do?pageNo=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易起始日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易结束日期");
			return ;
		}
	
		var form = document.getElementById("queryMerAccounting");
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAccounting.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}

	//查询数据
	function checkQuery(){
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易起始日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易结束日期");
			return ;
		}
		
		var form = document.getElementById("queryMerAccounting");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerAccounting.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//下载excel数据
	function checkExcel(){
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易起始日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易结束日期");
			return ;
		}
		var merCode = $("#merCode").val();
		var mer_category = $("#mer_category").val();
		var url ="<%=request.getContextPath()%>/merchantAccountingExcel.do?endTime="+endTime +"&startTime="+startTime+"&merCode="+merCode+"&mer_category="+mer_category;
		window.location=url;
	}
	
	function init(){
		var mer_category = $("#hidden_mer_category").val();
		var mer_categorys = document.getElementById("mer_category");
		for (var i = 0; i < mer_categorys.length; i++) {
			if (mer_categorys.options[i].value == mer_category) {
				mer_categorys.options[i].selected = 'selected';
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
			};
		};
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
			};
		};
	}
	
	function clearEndTime(obj){
		$(obj).val("");
	}
	
</script>
</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>商户账务查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerAccounting.do" target="right" name="queryMerAccounting" id="queryMerAccounting" method="post">
				  <div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="95%" border="0" cellspacing="0">	
							<tr>
								<td align="right" nowrap="nowrap">商户号:</td>
								<td nowrap="nowrap">
					            	<span class="input_bgl">
										<input  type="text" name="merCode" id = "merCode" value="${param.merCode}" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									</span>
								</td>
								<td align="right" nowrap="nowrap">商户类型:</td>
								<td nowrap="nowrap">
								 <span class="input_bgl">
									<select name="mer_category" id="mer_category" style="width: 140px;">
										  <option value="">全部</option>
										  <option value="0">RYF商户</option>
										  <option value="1">VAS商户</option>
										  <option value="2">POS商户</option>
									</select>
								  </span>
								<input type="hidden" id="hidden_mer_category" value="${mer_category}"/>
								</td>
								<td align="right" nowrap="nowrap">交易日期:</td>
				            	<td nowrap="nowrap">
									<span style="width: 160px;" class="input_bgl"> 
										<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime}"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime(endTime);" />
										至 
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime}"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{M:1})}'})" />
										<font color="red">*</font>
									</span>
								</td>
							</tr>
				        </table>
				        <ul>
				        	<li class="cb mt0">
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="下载xls报表" onclick="checkExcel()"/>
							</li>
				        </ul>
			        </center>
				 </div>
			  </form>
			</div>
			
			<!-- 分页显示数据条数 -->
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
							    <td align="center">商户号</td>
							    <td align="center">商户简称</td>
							    <td align="center">商户类型</td>
								<td align="center">上期账户余额</td>
								<td align="center">起始交易日期</td>
								<td align="center">支付金额</td>
								<td align="center">退款金额</td>
								<td align="center">商户手续费</td>
								<td align="center">商户退回手续费</td>
								<td align="center">结算金额</td>
								<td align="center">手工调增金额</td>
								<td align="center">手工调减金额</td>
								<td align="center">结束交易日期</td>
								<td align="center">本期账户余额</td>
						</tr>
					</thead>
				 	<c:if test="${fn:length(pageMerFundStance.result)<=0 }">
						<tr align="center">
							<td colspan="12">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageMerFundStance.result }" var="merinfo"> 
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${merinfo.mer_code}</td>
							<td align="center">${merinfo.mer_name}</td>
							<td align="center">
									<c:if test="${merinfo.mer_category == 0}">RYF商户</c:if>
									<c:if test="${merinfo.mer_category == 1}">VAS商户</c:if>
									<c:if test="${merinfo.mer_category == 2}">POS商户</c:if>
							</td>
							<td align="center">${merinfo.onAccountAmt}</td>
							<td align="center">${merinfo.startDate}</td>
							<td align="center">${merinfo.theAmount}</td>
							<td align="center">${merinfo.refundAmount}</td>
							<td align="center">${merinfo.merFees}</td>
							<td align="center">${merinfo.merBackFees}</td>
							<td align="center">${merinfo.settlementAmt}</td>
							<td align="center">${merinfo.addAmount}</td>
							<td align="center">${merinfo.subAmount}</td>
							<td align="center">${merinfo.endDate }</td>
							<td align="center">${merinfo.thisAccountAmt}</td>
					</tr>
					</c:forEach>
				</table>
				</div>
				<!-- 分页 -->
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
