<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
	<div>
		<span>商户简称：
			<input type="text" name="mer_abbreviation_" id="mer_abbreviation_" value="${param.mer_abbreviation_ }"/>
		</span>
		<span>商户号：
			<input type="text" name="mer_code_" id="mer_code_" value="${param.mer_code_ }"/>
		</span>
		<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
	</div>
	<div style="font-size: 12px;">
		<span>
		本页共
		<font color="red">
			<c:if test="${fn:length(pageSettleMerInfo.result) <= 0 }">0</c:if>
			<c:if test="${fn:length(pageSettleMerInfo.result) > 0 }">${fn:length(pageSettleMerInfo.result) }</c:if>
		</font>
		条数据
		</span>
		<span style="float: right">共<font color="red">
			<c:if test="${pageSettleMerInfo.totalItems == null }">0</c:if>
			<c:if test="${pageSettleMerInfo.totalItems != null }">${pageSettleMerInfo.totalItems }</c:if>
		</font>条数据
		<font color="red">
			<c:if test="${pageSettleMerInfo.totalPages == null}">0</c:if>
			<c:if test="${pageSettleMerInfo.totalPages != null}">${pageSettleMerInfo.totalPages}</c:if>
		</font>页
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span>
			每页显示
			<c:if test="${not empty pageSize }">
				<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="${pageSize }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
			</c:if>
			<c:if test="${empty pageSize }">
				<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
			</c:if>
			条
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</span>
	</div>
	<div class="table-m">
		<table width="100%" border="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center" width="10%">
						<input type="checkbox" id="SelectAllDetail" onclick="selectAllDetail();"/>全选
					</td>
					<td align="center" width="40%">商户简称</td>
					<td align="center" width="40%">上次结算截止日期</td>
					<td align="center" width="10%">操作</td>
				</tr>
			</thead>
			<c:if test="${fn:length(pageSettleMerInfo.result)<=0 }">
				<tr align="center">
					<td colspan="11">对不起,暂无数据！</td>
				</tr>
			</c:if>
			<c:forEach items="${pageSettleMerInfo.result }" var="settleMerInfo">
				<tr id="${settleMerInfo.mer_code }" class="ssss">
					<td width="10%" align="center">
			           <input type="checkbox" name="subcheckDetail" onclick="setSelectAllDetail();" value="${settleMerInfo.mer_code };${settleMerInfo.lastSettleDate };${settleMerInfo.mer_category };${settleMerInfo.bil_cycle };${settleMerInfo.mer_abbreviation };${settleMerInfo.bil_way };${settleMerInfo.bil_smallamt };${settleMerInfo.endDate};${settleMerInfo.bil_status};${settleMerInfo.bil_bank};${settleMerInfo.bank_branch};${settleMerInfo.bil_accountname};${settleMerInfo.bil_bankaccount};${settleMerInfo.bil_object};${settleMerInfo.bil_manual};${settleMerInfo.whtherFz};${settleMerInfo.bil_account}"/>
			        </td>
					<td align="center">${settleMerInfo.mer_abbreviation }</td>
					<td align="center">${settleMerInfo.lastSettleDate }</td>
					<td align="center" width="60">
						<a style="color: red" href="javascript:settle('${settleMerInfo.mer_code }','${settleMerInfo.lastSettleDate }','${settleMerInfo.mer_category }','${settleMerInfo.bil_cycle }','${settleMerInfo.mer_abbreviation }','${settleMerInfo.bil_way }','${settleMerInfo.bil_smallamt }','${settleMerInfo.endDate }','${settleMerInfo.bil_status }','${settleMerInfo.bil_bank }','${settleMerInfo.bank_branch }','${settleMerInfo.bil_accountname }','${settleMerInfo.bil_bankaccount }','${settleMerInfo.bil_object }','${settleMerInfo.bil_manual }','${settleMerInfo.whtherFz}','${settleMerInfo.bil_account}')">結算</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<span class="contect-lt"></span> 
		<span class="contect-rt"></span> 
		<span class="contect-lb"></span> 
		<span class="contect-rb"></span>
	</div>
	<div><input type="button" class="icon_normal" value="结算" onclick="batchSettle();"/></div>
	<c:if test="${pageSettleMerInfo.totalPages != null}">
		<div class="next">
			<c:if test="${pageSettleMerInfo.pageNo > 1}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo-1 })"><span>上一页</span></a>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo-3 > 0}">
				<b><span>...</span></b>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo-2 > 0}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo-2 })"><span>${pageSettleMerInfo.pageNo-2 }</span></a>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo-1 > 0}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo-1 })"><span>${pageSettleMerInfo.pageNo-1 }</span></a>
			</c:if>
			<a href="#" class="hover"><span>${pageSettleMerInfo.pageNo }</span></a>
			<c:if test="${pageSettleMerInfo.pageNo+1 <= pageSettleMerInfo.totalPages}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo+1 })"><span>${pageSettleMerInfo.pageNo+1 }</span></a>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo+2 <= pageSettleMerInfo.totalPages}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo+2 })"><span>${pageSettleMerInfo.pageNo+2 }</span></a>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo+3 <= pageSettleMerInfo.totalPages}">
				<b><span>...</span></b>
			</c:if>
			<c:if test="${pageSettleMerInfo.pageNo < pageSettleMerInfo.totalPages}">
				<a href="javascript:pagingForDetail(${pageSettleMerInfo.pageNo+1 })"><span>下一页</span></a>
			</c:if>
			<b><span>共${pageSettleMerInfo.totalPages }页 跳到第
				<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
					value="${pageSettleMerInfo.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPageForDetail(event)" />页
			</span></b>
		</div>
	</c:if>