<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户余额查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("queryMerchantsBalanceList");
		var pageSize = $("#pageSize").val();
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		if(merCode==null || merCode == ""){
			if(merName==null || merName == ""){
				alert("查询条件商户号、商户简称，两者必须选其一");
				return ;
			}
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerchantsBalanceList.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("queryMerchantsBalanceList");
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		if(merCode==null || merCode == ""){
			if(merName==null || merName == ""){
				alert("查询条件商户号、商户简称，两者必须选其一");
				return ;
			}
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerchantsBalanceList.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var form = document.getElementById("queryMerchantsBalanceList");
		var pageSize = $("#pageSize").val();
		var merCode = $("#merCode").val();
		var merName = $("#merName").val();
		if(merCode==null || merCode == ""){
			if(merName==null || merName == ""){
				alert("查询条件商户号、商户简称，两者必须选其一");
				return ;
			}
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerchantsBalanceList.do?pageSize=" + pageSize;
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
</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>商户余额查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerchantsBalanceList.do" target="right" name="queryMerchantsBalanceList" id="queryMerchantsBalanceList" method="post">
					<ul class="check-m">						
						<li>
							<b>商户号:</b>
							<span class="input_bgl">
								<input type="text" name="merCode" id = "merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
							</span>
							<b>商户简称:</b>
							<span class="input_bgl">
								<input type="text" name="merName" id = "merName" value="${param.merName }"/>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
						</li>
					</ul>
				</form>
			</div>
			
			<!-- 分页显示数据条数 -->
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(merchantsList.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(merchantsList.result) > 0 }">${fn:length(merchantsList.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${merchantsList.totalItems == null }">0</c:if>
					<c:if test="${merchantsList.totalItems != null }">${merchantsList.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${merchantsList.totalPages == null}">0</c:if>
					<c:if test="${merchantsList.totalPages != null}">${merchantsList.totalPages}</c:if>
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
							<td align="center">余额(元)</td>
							<td align="center">商户状态</td>
						</tr>
					</thead>
					<c:if test="${fn:length(merchantsList.result)<=0 }">
						<tr align="center">
							<td colspan="4">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${merchantsList.result}" var="merchantsList">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${merchantsList.mer_code}</td>
							<td align="center">${merchantsList.merName}</td>
							<td align="center">${merchantsList.mer_balance}</td>
							<td align="center">
								<c:if test="${merchantsList.mer_state == 5}">正常</c:if>
								<c:if test="${merchantsList.mer_state == 6}">关闭</c:if>
							</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- 分页 -->
				<c:if test="${merchantsList.totalPages != null}">
					<div class="next">
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(${merchantsList.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantsList.pageNo-2 > 0}">
							<a href="javascript:paging(${merchantsList.pageNo-2 })"><span>${merchantsList.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo-1 > 0}">
							<a href="javascript:paging(${merchantsList.pageNo-1 })"><span>${merchantsList.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${merchantsList.pageNo }</span></a>
						<c:if test="${merchantsList.pageNo+1 <= merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+1 })"><span>${merchantsList.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo+2 <= merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+2 })"><span>${merchantsList.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo+3 <= merchantsList.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantsList.pageNo < merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(${merchantsList.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${merchantsList.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${merchantsList.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
   	   </div>
  </div>
</body>
</html>
