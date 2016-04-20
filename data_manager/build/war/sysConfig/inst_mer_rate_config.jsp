<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<table width="100%" border="0" cellspacing="0">	
	<tbody id="tbu">
		<tr>
			<td colspan="5"><input type="button" value="添加新费率" onclick="addInstMerRateConfig(2);"/></td>
		</tr>			
		<tr>
			<td align="center" bgcolor="#eeeeee">扣款商户号</td>
			<td align="center" bgcolor="#eeeeee">卡种</td>
			<td align="center" bgcolor="#eeeeee">本行/跨行</td>
			<td align="center" bgcolor="#eeeeee">渠道费率公式</td>
			<td align="center" bgcolor="#eeeeee">操作</td>
		</tr>
		<c:forEach items="${list }"  var="data">
			<tr>
				<td align="center">${data.mer_code }
					<input type="hidden" id="deductMerCode_h" value="${data.mer_code }" />
				</td>
				<td align="center">
					<c:if test="${data.card_type == 'C' }">信用卡</c:if>	
					<c:if test="${data.card_type == 'D' }">借记卡</c:if>	
					<c:if test="${data.card_type == 'ALL' }">信用卡、借记卡</c:if>
					<input type="hidden" id="cardType_h" value="${data.card_type }" />	
				</td>
				<td align="center">
					<c:if test="${data.lineOrinter == 1 }">本行</c:if>	
					<c:if test="${data.lineOrinter == 2 }">跨行</c:if>
					<c:if test="${data.lineOrinter == 3 }">本行、跨行</c:if>
					<input type="hidden" id="bankType_h" value="${data.lineOrinter }" />
				</td>
				<td align="center">${data.fee_Poundage }
					<input type="hidden" id="channelFee_h" value="${data.fee_Poundage }" />
				</td>
				<td align="center"><input type="hidden" id="operType" value="0" /><input type="button" onclick="javascript:deleteThisData(this,3);" value="删除" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>