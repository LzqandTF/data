<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手续费异常交易</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<span style="float: left;">
				共
				<font color="red">
					<c:if test="${tradingList.totalItems == null }">0</c:if>
					<c:if test="${tradingList.totalItems != null }">${tradingList.totalItems }</c:if>
				</font>
				条数据
				总计：交易金额总计<fmt:formatNumber value="${sumTradingAmt}" type="number"/>元，
						银行实收手续费总计<fmt:formatNumber value="${sumBankEchoFee}" type="number"/>元，
						银行应收手续费总计<fmt:formatNumber value="${sumBankRealFee}" type="number"/>元
			</span>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							    <td align="center">交易流水号</td>
							    <td align="center">订单号</td>
								<td align="center">商户号</td>
								<td align="center">商户简称</td>
								<td align="center">扣款渠道</td>
								<td align="center">交易类型</td>
								<td align="center">交易时间</td>
								<td align="center">交易金额</td>
								<td align="center">银行实收手续费</td>
								<td align="center">银行应收手续费</td>
						</tr>
					</thead>
					<c:if test="${fn:length(tradingList.result)<=0 }">
						<tr align="center">
							<td colspan="10">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${tradingList.result}" var="tradingList">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${tradingList.tseq}</td>
							<td align="center">${tradingList.oid}</td>
							<td align="center">${tradingList.mid}</td>
							<td align="center">${tradingList.mabbreviation}</td>
							<td align="center">${tradingList.name}</td>
							<td align="center">${tradingList.type}</td>
							<td align="center">${tradingList.mdate}</td>
							<td align="center">${tradingList.amount}</td>
							<td align="center">${tradingList.zf_file_fee}</td>
							<td align="center">${tradingList.zf_fee}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<c:if test="${tradingList.totalPages != null}">
				<div class="next">
					<c:if test="${tradingList.pageNo > 1}">
						<a href="javascript:pagingInfo(${tradingList.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${tradingList.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${tradingList.pageNo-2 > 0}">
						<a href="javascript:pagingInfo(${tradingList.pageNo-2 })"><span>${tradingList.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${tradingList.pageNo-1 > 0}">
						<a href="javascript:pagingInfo(${tradingList.pageNo-1 })"><span>${tradingList.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${tradingList.pageNo }</span></a>
					<c:if test="${tradingList.pageNo+1 <= tradingList.totalPages}">
						<a href="javascript:pagingInfo(${tradingList.pageNo+1 })"><span>${tradingList.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${tradingList.pageNo+2 <= tradingList.totalPages}">
						<a href="javascript:pagingInfo(${tradingList.pageNo+2 })"><span>${tradingList.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${tradingList.pageNo+3 <= tradingList.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${tradingList.pageNo < tradingList.totalPages}">
						<a href="javascript:pagingInfo(${tradingList.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${tradingList.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="pagingInfo(this.value)"
							value="${tradingList.pageNo }" />页
							<input type="hidden" value="${tradingList.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
       </div>
  </div>
</body>
</html>
