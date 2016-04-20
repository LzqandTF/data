<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算单明细</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
</head>
<body>
	<div class="content" style="font-size: 11px; width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
		<form action="<%=request.getContextPath()%>/queryMerSettleDataDetailList.do" target="right" name="merSettleData" id="merSettleData" method="post">
		</form>
		<span style="float: left;">
			共
			<font color="red">
				<c:if test="${empty getDataResult.totalItems}">0</c:if>
				<c:if test="${not empty getDataResult.totalItems != null }">${getDataResult.totalItems }</c:if>
			</font>
			条数据
		</span>
		<table width="1200px;" border="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center" bgcolor="#eeeeee">商户号</td>
				    <td align="center" bgcolor="#eeeeee">商户简称</td>
					<td align="center" bgcolor="#eeeeee">扣款渠道</td>
					<td align="center" bgcolor="#eeeeee">交易日期</td>
					<td align="center" bgcolor="#eeeeee">清算日期</td>
					<td align="center" bgcolor="#eeeeee">流水号</td>
					<td align="center" bgcolor="#eeeeee">订单号</td>
					<td align="center" bgcolor="#eeeeee">交易金额</td>
					<td align="center" bgcolor="#eeeeee">手续费</td>
					<td align="center" bgcolor="#eeeeee">交易类型</td>
					<td align="center" bgcolor="#eeeeee">入账ID</td>
				</tr>
			</thead>
			<c:if test="${fn:length(getDataResult.result)<=0 }">
				<tr align="center">
					<td colspan="11">对不起,暂无数据！</td>
				</tr>
			</c:if>
			<c:forEach items="${getDataResult.result}" var="channelDzCollect">
				<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
					<td align="center">${channelDzCollect.req_mer_code}</td>
					<td align="center">${channelDzCollect.dy_mer_name}</td>
					<td align="center">${channelDzCollect.inst_name}</td>
					<td align="center">${channelDzCollect.deduct_sys_time}</td>
					<td align="center">${channelDzCollect.deduct_stlm_date}</td>
					<td align="center">${channelDzCollect.deduct_sys_stance}</td>
					<td align="center">${channelDzCollect.oid }</td>
					<td align="center">
						<f:formatNumber value="${channelDzCollect.trade_amount}" pattern="0.00"></f:formatNumber>
					</td>
					<td align="center">
						<f:formatNumber value="${channelDzCollect.mer_fee}" pattern="0.00"></f:formatNumber>
					</td>
					<td align="center">${channelDzCollect.trade_type}</td>
					<td align="center">${channelDzCollect.in_user_id }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<c:if test="${getDataResult.totalPages != null}">
		<div class="next">
			<c:if test="${getDataResult.pageNo > 1}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo-1 })"><span>上一页</span></a>
			</c:if>
			<c:if test="${getDataResult.pageNo-3 > 0}">
				<b><span>...</span></b>
			</c:if>
			<c:if test="${getDataResult.pageNo-2 > 0}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo-2 })"><span>${getDataResult.pageNo-2 }</span></a>
			</c:if>
			<c:if test="${getDataResult.pageNo-1 > 0}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo-1 })"><span>${getDataResult.pageNo-1 }</span></a>
			</c:if>
			<a href="#" class="hover"><span>${getDataResult.pageNo }</span></a>
			<c:if test="${getDataResult.pageNo+1 <= getDataResult.totalPages}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo+1 })"><span>${getDataResult.pageNo+1 }</span></a>
			</c:if>
			<c:if test="${getDataResult.pageNo+2 <= getDataResult.totalPages}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo+2 })"><span>${getDataResult.pageNo+2 }</span></a>
			</c:if>
			<c:if test="${getDataResult.pageNo+3 <= getDataResult.totalPages}">
				<b><span>...</span></b>
			</c:if>
			<c:if test="${getDataResult.pageNo < getDataResult.totalPages}">
				<a href="javascript:pagingInfo(${getDataResult.pageNo+1 })"><span>下一页</span></a>
			</c:if>
			<b>
			<span>
				共${getDataResult.totalPages }页 跳到第
				<input style="width: 24px; margin: 0 5px; text-align: center;" onblur="pagingInfo(this.value)" value="${getDataResult.pageNo }" />
				页
				<input type="hidden" value="${getDataResult.pageNo}" id="pageNo"/>
			</span>
			</b>
		</div>
	</c:if>
</body>
</html>
