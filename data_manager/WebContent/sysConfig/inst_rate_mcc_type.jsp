<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<table width="100%" border="0" cellspacing="0">	
	<tbody id="tbu">			
		<tr>
			<td colspan="6">
				<select id="inst_rate_mcc" name="inst_rate_mcc">
					<option value="1">标准MCC费率(发卡行+转接清算方)</option>
					<option value="2">标准MCC费率(全部)</option>
				</select> 
			</td>
		</tr>
		<c:if test="${fn:length(list)<=0 }">
			<tr>
				<td>添加例外</td>
				<td align="center" colspan="2">MCC大类
					<select id="mcc_da_lei" name="mcc_da_lei" onchange="loadMccXiLeiData(this);">
						<c:forEach items="${mccBigTypeList }" var="mccBigType">
							<option value="${mccBigType.big_type_id }">${mccBigType.type_name }</option>
						</c:forEach>
					</select>
				</td>
				<td align="center" colspan="2">MCC细类
					<select id="mcc_xi_lei" name="mcc_xi_lei">
						<option value="">--请选择--</option>
					</select>
				</td>
				<td align="center">
					<input type="button" onclick="javascript:addOne(this);" value=" + " />
					<input type="button" onclick="javascript:deleteThisData(this,1);" value=" - " />
				</td>
			</tr>
		</c:if>
		<c:forEach items="${list }"  var="data">
		<tr>
			<td>添加例外</td>
			<td align="center" colspan="2">MCC大类
				<select id="mcc_da_lei" name="mcc_da_lei" onchange="loadMccXiLeiData(this);">
					<c:forEach items="${mccBigTypeList }" var="mccBigType">
						<option value="${mccBigType.big_type_id }" <c:if test="${mccBigType.big_type_id == data.mcc_b_type }">selected="selected"</c:if> >${mccBigType.type_name }</option>
					</c:forEach>
				</select>
			</td>
			<td align="center" colspan="2">MCC细类
				<select id="mcc_xi_lei" name="mcc_xi_lei" >
					<c:forEach items="${data.mcc_type_list }" var="mccSmallType">
						<option value="${mccSmallType.id }" <c:if test="${mccSmallType.id == data.mcc_s_type }">selected="selected"</c:if> >${mccSmallType.type_name }</option>
					</c:forEach>
				</select>
			</td>
			<td align="center">
				<input type="button" onclick="javascript:addOne(this);" value=" + " />
				<input type="button" onclick="javascript:deleteThisData(this,1);" value=" - " />
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>