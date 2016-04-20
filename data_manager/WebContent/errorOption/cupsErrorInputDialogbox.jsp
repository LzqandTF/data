<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<table class="table-m" style="top: 20px; left: 20px; width: 480px; height: 270px;">
	<tr>
		<td align="right">系统跟踪号：</td>
		<td align="left">
			<input type="text" id="reqSysStance2" name="reqSysStance2" maxlength="6"  onkeyup="value=value.replace(/[^\d]/g,'')" />
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易时间：</td>
		<td align="left">
			<input type="text" id="tradeTime2" name="tradeTime2" 
				readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})" />
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">清算日期：</td>
		<td align="left">
			<input type="text" id="deductStlmDate2" name="deductStlmDate2" 
				readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s'})" />
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易金额：</td>
		<td align="left">
			<!-- <input type="text" id="tradeAmount2" name="tradeAmount2" onkeyup="value=value.replace(/[^\d\.]/g,'')"/> -->
			<input type="text" id="tradeAmount2" name="tradeAmount2" value="" onkeyup="amount(this)"/>
			<font color="red">&nbsp;*（按元计算）</font>
		</td>
	</tr>
	<tr>
		<td align="right">主账号：</td>
		<td align="left">
			<input type="text" id="out_account2" name="out_account2" maxlength="19" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">受理机构代码：</td>
		<td align="left">
			<input type="text" id="acqInstIdCode2" name="acqInstIdCode2" maxlength="11"/>
			<font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">扣款渠道：</td>
		<td align="left">
			<select id="inst_id" name="inst_id">
				<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				<c:forEach items="${instList}" var="obj">
				  <option value='${obj.instId}'>${obj.name }</option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">交易类型：</td>
		<td align="left">
			<select id="trade_type2" name="trade_type2">
				<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				<c:forEach items="${tradeType}" var="obj">
				  <option value='${obj.process}${obj.trademsgType}'>${obj.name }</option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right">处理方式：</td>
		<td align="left">
			<select id="errorHandleMethodList" name="errorHandleMethodList" onchange="getReasonCode(this.value);">
				<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
				<c:forEach items="${errorHandleList}" var="obj">
				  <option value="${obj.id}">${obj.handling_name }</option>
				</c:forEach>
			</select><font color="red">&nbsp;&nbsp;*</font>
	</td>
	<tr>
		<td align="right">原因码：</td>
		<td align="left">
			<select id="reasonCode" name="reasonCode">
				<option value="">--请选择--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
			</select><font color="red">&nbsp;&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" value="确定" onclick="addCupsErrorData('${sessionScope.login.loginName}');"/> 
			<input type="button" class="wBox_close button" value="取消" />
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>