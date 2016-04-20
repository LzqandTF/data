<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行账户查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

<script type="text/javascript">
	function paging(pageNo) {
		var form = document.getElementById("bankAccount");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankAccount.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("bankAccount");
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == "" || endTime == "") {
			alert("请选择交易日期！");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 31*24*60*60*1000){
			alert("日期范围只能为1个月以内");
			return ;
		 }
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankAccount.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//银行账户查询
	function queryBankAccount(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == "" || endTime == "") {
			alert("请选择交易日期！");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 31*24*60*60*1000){
			alert("日期范围只能为1个月以内");
			return ;
		 }
		var form = document.getElementById("bankAccount");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankAccount.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
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
	function clearEndTime(){
		$("#endTime").val("");
	}
	function getSelected() {
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
	function downExcelBankAccountLst(){
		//页面跳转至打印下载页面
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == "" || endTime == "") {
			alert("请选择交易日期！");
			return;
		}
		var startDate  = new Date(startTime.replace(/-/g,"/")).getTime();
		var endDate = new Date(endTime.replace(/-/g,"/")).getTime();
		if(endDate - startDate  > 31*24*60*60*1000){
			alert("日期范围只能为1个月以内");
			return ;
		 }
		var url ="<%=request.getContextPath()%>/getBankAccountLst.do?startTime="+startTime+
				"&endTime="+endTime;
		window.location=url;
 	}
</script>

</head>

<body onload="getSelected();">
	<jsp:include page="account_title.jsp" ></jsp:include>
	<div class="content">
		<div class="right" style="margin: -25px 5px;">
			<div id="tab_content">
				<div class="content" style="margin-top: 10px;">
					<div class="right" style="margin: -5px 5px;">
						<div class="position">
							当前位置：<a href="javascript:void(0)">账户头寸调拨</a>&gt;<span>银行账户查询</span>
						</div>
						<div class="check clearfix" style="margin-top: -15px;">
							<h1 class="tit">查询区</h1>
							<form action="<%=request.getContextPath()%>/queryBankAccount.do" target="right" name="bankAccount" id="bankAccount" method="post">
							<div class="table_2" style="background:  #dcdfe1; border: none;">
								<center>
									<table width="90%" border="0" cellspacing="0">
							            <tr>
							            	<td align="right" nowrap="nowrap">交易日期</td>
							            	<td nowrap="nowrap">
												<span style="width: 30px;" class="input_bgl">
													<input style="width: 80px" id="startTime" name="startTime" value="${param.startTime }"
														readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
													- 
													<input style="width: 80px" id="endTime" name="endTime" value="${param.endTime }"
														readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
													<font color="red">*</font>
												</span>	
											</td>
							            	<td colspan="8" align="center" style="height: 30px"> 
								                <input type="button" class="icon_normal" value="查询" onclick="queryBankAccount();" />
								                <input type="button" class="icon_normal" value="下载" onclick="downExcelBankAccountLst();"/>
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
								<c:if test="${fn:length(bankAccount.result) <= 0 }">0</c:if>
								<c:if test="${fn:length(bankAccount.result) > 0 }">${fn:length(bankAccount.result) }</c:if>
							</font>
							条数据
							</span>
							<span style="float: right">共<font color="red">
								<c:if test="${bankAccount.totalItems == null }">0</c:if>
								<c:if test="${bankAccount.totalItems != null }">${bankAccount.totalItems }</c:if>
							</font>条数据
							<font color="red">
								<c:if test="${bankAccount.totalPages == null}">0</c:if>
								<c:if test="${bankAccount.totalPages != null}">${bankAccount.totalPages}</c:if>
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
							<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
								<table width="100%" border="0" cellspacing="0">
									<thead>
										<tr>
											<td align="center" width="40%">结算银行名称</td>
											<td align="center" width="30%">总交易金额</td>
											<td></td>
										</tr>
									</thead>
									<c:if test="${fn:length(bankAccount.result)<=0 }">
										<tr align="center">
											<td colspan="2">对不起,暂无数据！</td>
										</tr>
									</c:if>
									<c:forEach items="${bankAccount.result }" var="bankAccount">
										<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
											<td align="center">
												<c:if test="${bankAccount._name == null}"></c:if>
												<c:if test="${bankAccount._name != null}">
													${bankAccount._name}
												</c:if>
											</td>
											<td align="center">
												<f:formatNumber value="${bankAccount.total_money}" pattern="0.00"></f:formatNumber>
											</td>
											<td></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<!-- 分页 -->
							<c:if test="${bankAccount.totalPages != null}">
								<div class="next">
									<c:if test="${bankAccount.pageNo > 1}">
										<a href="javascript:paging(1)"><span>首页</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo > 1}">
										<a href="javascript:paging(${bankAccount.pageNo-1 })"><span>上一页</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo-3 > 0}">
										<b><span>...</span></b>
									</c:if>
									<c:if test="${bankAccount.pageNo-2 > 0}">
										<a href="javascript:paging(${bankAccount.pageNo-2 })"><span>${bankAccount.pageNo-2
												}</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo-1 > 0}">
										<a href="javascript:paging(${bankAccount.pageNo-1 })"><span>${bankAccount.pageNo-1
												}</span></a>
									</c:if>
									<a href="#" class="hover"><span>${bankAccount.pageNo }</span></a>
									<c:if test="${bankAccount.pageNo+1 <= bankAccount.totalPages}">
										<a href="javascript:paging(${bankAccount.pageNo+1 })"><span>${bankAccount.pageNo+1
												}</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo+2 <= bankAccount.totalPages}">
										<a href="javascript:paging(${bankAccount.pageNo+2 })"><span>${bankAccount.pageNo+2
												}</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo+3 <= bankAccount.totalPages}">
										<b><span>...</span></b>
									</c:if>
									<c:if test="${bankAccount.pageNo < bankAccount.totalPages}">
										<a href="javascript:paging(${bankAccount.pageNo+1 })"><span>下一页</span></a>
									</c:if>
									<c:if test="${bankAccount.pageNo > 1}">
										<a href="javascript:paging(${bankAccount.totalPages })"><span>尾页</span></a>
									</c:if>
									<b>
										<span>共${bankAccount.totalPages }页 跳到第
										<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
											value="${bankAccount.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
										</span>
									</b>
								</div>
							</c:if>
						</div>
					</div>
				</div>
	    	</div>
		</div>
	</div>
</body>
</html>
