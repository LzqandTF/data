<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<table width="100%" border="1" cellspacing="1">	
	<tr>
		<td align="center" bgcolor="#eeeeee">公式模板</td>
		<td align="center" bgcolor="#eeeeee">公式详解</td>
	</tr>
	<c:if test="${fn:length(dataList)<=0 }">
		<tr align="center">
			<td colspan="5">对不起,暂无数据！</td>
		</tr>
	</c:if>
	<c:forEach items="${dataList }" var="data">
		<tr>
			<td align="center">${data.fee_poundage }</td>
			<td align="center">${data.poundage_detail }</td>
		</tr>
	</c:forEach>
</table>