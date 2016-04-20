<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
	<div class="table-m">
		<table width="100%" border="0" cellspacing="0">
			<thead>
				<tr>
					<td width="10%" align="center">
						<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
					</td>
	            	<td width="30%" align="center">商户类型</td>
	            	<td width="30%" align="center">数目</td>
	            	<td width="30%" align="center">操作</td>
				</tr>
			</thead>
			<c:if test="${fn:length(merchantList)<=0 }">
				<tr align="center">
					<td colspan="11">对不起,暂无数据！</td>
				</tr>
			</c:if>
			<c:forEach items="${merchantList }" var="merInfo">
				<tr id="${merInfo.mer_code }" class="ssss">
					<td width="10%" align="center">
	            		<input type="checkbox" name="subcheck" onclick="setSelectAll();" value="${merInfo.mer_category }"/>
	            	</td>
					<td align="center">
						<c:if test="${merInfo.mer_category == 0 }">RYF商户</c:if>
						<c:if test="${merInfo.mer_category == 1 }">VAS商户</c:if>
						<c:if test="${merInfo.mer_category == 2 }">POS商户</c:if>
					</td>
					<td align="center">${merInfo.merchantTotalCount }</td>
					<td align="center" width="60">
						<a href="javascript:settleMerInfoByMerType(${merInfo.mer_category });">結算</a>
						<a href="javascript:querySettleMerInfoDetail(${merInfo.mer_category });">详情</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<span class="contect-lt"></span> 
		<span class="contect-rt"></span> 
		<span class="contect-lb"></span> 
		<span class="contect-rb"></span>
		<input type="hidden" id="mer_type_hide" value=""/>
	</div>
	<div><input type="button" class="icon_normal" value="结算" onclick="batchSettleMerInfoByMerType();"/></div>
