<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<table class="table-m" style="top: 20px; left: 20px; width: 400px; height: 270px;">
	<tr>
		<td align="right">系统跟踪号：</td>
		<td align="left">
			<input type="text" id="reqSysStance2" name="reqSysStance2" value="${ylCupsErrorEntry.reqSysStance }"  maxlength="6" readonly="readonly"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易时间：</td>
		<td align="left">
			<input type="text" id="tradeTime2" name="tradeTime2" value="${fn:substring(ylCupsErrorEntry.trade_time,0,19)}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">清算日期：</td>
		<td align="left">
			<input type="text" id="deductStlmDate2" name="deductStlmDate2" value="${fn:substring(ylCupsErrorEntry.deductStlmDate,0,19)}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易金额：</td>
		<td align="left">
			<%-- <input type="text" id="tradeAmount2" name="tradeAmount2" value="${ylCupsErrorEntry.tradeAmount }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/> --%>
			<input type="text" id="tradeAmount2" name="tradeAmount2" value="${ylCupsErrorEntry.tradeAmount }" onkeyup="amount(this)"/>
			<font color="red">&nbsp;*（按元计算）</font>
		</td>
	</tr>
	<tr>
		<td align="right">主账号：</td>
		<td align="left">
			<input type="text" id="out_account2" name="out_account2" value="${ylCupsErrorEntry.out_account }" maxlength="19" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">受理机构代码：</td>
		<td align="left">
			<input type="text" id="acqInstIdCode2" name="acqInstIdCode2" value="${ylCupsErrorEntry.acqInstIdCode }" maxlength="12" />
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">扣款渠道：</td>
		<td align="left">
			<select id="inst_id" name="inst_id" style="width: 155px;">
				<c:if test="${empty instList}">
					<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:if>
				<c:forEach items="${instList}" var="obj">
				   <option value='${obj.instId}'
				   		<c:if test="${obj.instId == instId}">selected="selected"</c:if>
				   >${obj.name }</option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易类型：</td>
		<td align="left">
			<select id="trade_type2" name="trade_type2" style="width: 155px;">
				<c:if test="${ylCupsErrorEntry.process == null || ylCupsErrorEntry.process == '' || ylCupsErrorEntry.tradeMsgType == 0}">
					<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				</c:if>
				<c:forEach items="${tradeType}" var="obj">
				  <option value='${obj.process}${obj.trademsgType}'
				  	<c:if test='${obj.process == ylCupsErrorEntry.process && obj.trademsgType == ylCupsErrorEntry.tradeMsgType }'>
				  		selected="selected"
				  	</c:if>
				  >
				  	${obj.name }
				  </option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">处理方式：</td>
		<td align="left">
			<select id="errorHandleMethodList" name="errorHandleMethodList" onchange="getReasonCode(this.value);" style="width: 155px;">
				<c:forEach items="${errorHandleList}" var="obj">
				  <option value="${obj.id}" 
				  	<c:if test="${obj.id == ylCupsErrorEntry.handling_id }">
				  		selected="selected"
				  	</c:if>
				  >
				  	${obj.handling_name }
				  </option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
	</td>
	<tr>
		<td align="right">原因码：</td>
		<td align="left">
			<select id="reasonCode" name="reasonCode" style="width: 155px;">
				<c:forEach items="${reasonCodeList}" var="obj">
				  <option value="${obj.reason_id}" <c:if test='${obj.reason_id == ylCupsErrorEntry.reason_code }'>selected="selected"</c:if>>
				  	${obj.reason_desc }
				  </option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" value="确定" onclick="addCupsErrorData('${ylCupsErrorEntry.id}','${sessionScope.login.loginName}');"/> 
			<input type="button" class="wBox_close button" value="取消" />
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
</table>