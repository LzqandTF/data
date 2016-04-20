<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账明细下载</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript">
	//初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['original_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
			}
		});
		
		var bk_chk = $("#bk_chk_hidden").val();
		var bkChk = document.getElementById("bk_chk");
		for (var i = 0; i < bkChk.length; i++) {
			if (bkChk.options[i].value == bk_chk) {
				bkChk.options[i].selected = 'selected';
			}
		}
		
		var whether_riqie = $("#whetherRiqie_hidden").val();
		var whetherRiqie = document.getElementById("whetherRiqie");
		for (var i = 0; i < whetherRiqie.length; i++) {
			if (whetherRiqie.options[i].value == whether_riqie) {
				whetherRiqie.options[i].selected = 'selected';
			}
		}
		
		var trade_result = $("#trade_result_hidden").val();
		var deductResult = document.getElementById("trade_result");
		for (var i = 0; i < deductResult.length; i++) {
			if (deductResult.options[i].value == trade_result) {
				deductResult.options[i].selected = 'selected';
			}
		}
		
		var bankId = $("#bankId_hidden").val();
		var bank_id = document.getElementById("bank_id");
		for (var i = 0; i < bank_id.length; i++) {
			if (bank_id.options[i].value == bankId) {
				bank_id.options[i].selected = 'selected';
			}
		}
		
		if (bankId != "") {
			getInstInfoByBankId(bankId);
		}
		
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("channel");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == inst_id){
				type.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	// 根据银行机构获取渠道信息
	function getInstInfoByBankId(bankInst) {
		if (bankInst == null || bankInst == "") {
			var selectObj = document.getElementById("channel");
			while(selectObj.firstChild) {
		        selectObj.removeChild(selectObj.firstChild);
			}
			$(selectObj).append("<option value=''>全部</option>");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/getInstInfoByBankId.do',
    		type : 'post',
    		data : 'bankInst='+bankInst,
    		async : false,
    		dataType : 'text',
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("channel");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				if (data.length == 0 || data.length > 1) {
					$(selectObj).append("<option value=''>全部</option>");
				}
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].instId +">"+data[i].name+"</option>");
				}
    		}
    	});
	}
	
	function initChannel() {
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("channel");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == inst_id){
				type.options[i].selected = 'selected';
			}
		}
	}
	
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		
		var mflag = 2;
		var dzSuccessMoney = $("#dzSuccessMoney").text();
		var merFee = $("#merFee").text();
		var dzFailMoney = $("#dzFailMoney").text();
		var noDzMoney = $("#noDzMoney").text();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择交易日期!");
			return;
		}
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageChannelDzData.do?pageNum=" + pageNo + "&pageSize=" + pageSize+"&mflag="+mflag+"&dzSuccessMoney="+dzSuccessMoney+"&merFee="+merFee+
					"&dzFailMoney="+dzFailMoney+"&noDzMoney="+noDzMoney;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("originalData");
		
		var mflag = 2;
		var dzSuccessMoney = $("#dzSuccessMoney").text();
		var merFee = $("#merFee").text();
		var dzFailMoney = $("#dzFailMoney").text();
		var noDzMoney = $("#noDzMoney").text();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择交易日期!");
			return;
		}
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageChannelDzData.do?pageSize=" + pageSize + "&mflag="+mflag+"&dzSuccessMoney="+dzSuccessMoney+"&merFee="+merFee+
					 "&dzFailMoney="+dzFailMoney+"&noDzMoney="+noDzMoney;
			method = "post";
			form.submit();
		}
	}
	
	//查询
	function checkQuery(){
		var mflag = 1;
		var form = document.getElementById("originalData");
		var pageSize = $("#pageSize").val();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择交易日期!");
			return;
		}
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageChannelDzData.do?pageSize=" + pageSize+"&mfFlag="+mflag;
			method = "post";
			form.submit();
		}
	}
	function clearEndTime() {
		$("#endTime").val("");
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
	
	//下载excel表格
	function downExcel(){
		//根据查询条件下载
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择交易日期!");
			return;
		}
		
		var deduct_stlm_date = $("#deduct_stlm_date").val();
		var deduct_sys_stance = $("#deduct_sys_stance").val();
		var additional_data = $("#additional_data").val();
		var req_mer_code = $("#req_mer_code").val();
		var bk_chk = $("#bk_chk").val();
		var whetherRiqie = $("#whetherRiqie").val();
		var trade_result = $("#trade_result").val();
		var bank_id = $("#bank_id").val();
		var channel = $("#channel").val();
		
		var url ="<%=request.getContextPath()%>/duiZhangDetailDataDownLoad.do?startTime="+startTime+
				"&endTime="+endTime+"&deduct_stlm_date="+deduct_stlm_date+
				"&deduct_sys_stance="+deduct_sys_stance+"&channel="+channel+
				"&additional_data="+additional_data+"&req_mer_code="+req_mer_code+
				"&bk_chk="+bk_chk+"&whetherRiqie="+whetherRiqie+"&trade_result="+trade_result+"&bank_id="+bank_id;
		window.location=url;
	}
	
</script>

</head>
<body onload="initBankInst(); initChannel();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>对账明细下载</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>" target="right" name="originalData" id="originalData" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>		
							<table width="95%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">交易日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:70px;" class="input_bgl">
									  	<input style="width: 70px" id="startTime" name="startTime"  value="${param.startTime }" 
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										-
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }" 
											readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{M:1})}'})" />
									  <font color="red">*</font>
									  </span>
					                </td>
					                 <td align="right" nowrap="nowrap">银行机构</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="bank_id" name="bank_id" style="width: 150px;" onchange="getInstInfoByBankId(this.value);">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="bankId_hidden" value="${bankId }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">扣款渠道</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="channel" name="channel" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
					                <td align="right" nowrap="nowrap">电银商户号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="req_mer_code" id="req_mer_code" value="${param.req_mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易状态</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:30px;" class="input_bgl">
									  	<select name="trade_result" id="trade_result" style="width: 150px;">
											<option value="">全部</option>
											<option value="0">初始状态</option>
											<option value="1">待支付</option>
											<option value="2">成功</option>
											<option value="3">失败</option>
											<option value="4">请求银行失败</option>
											<option value="5">撤销</option>
											<option value="6">超时</option>
										</select>
										<input type="hidden" id="trade_result_hidden" value="${trade_result }"/>
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">对账结果</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="bk_chk" id="bk_chk">
												<option value="">全部</option>
												<option value="0">未对账</option>
												<option value="1">对账成功</option>
												<option value="2">对账失败</option>
												<option value="3">无需对账</option>
											</select>
											<input type="hidden" id="bk_chk_hidden" value="${bk_chk }"/>
										</span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">订单号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="additional_data" id="additional_data" value="${param.additional_data }" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="deduct_sys_stance" id="deduct_sys_stance" value="${param.deduct_sys_stance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">清算日期</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:30px;" class="input_bgl">
									  	<input name="deduct_stlm_date" id="deduct_stlm_date" value="${param.deduct_stlm_date }" 
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									  </span>
					                </td>
					            </tr>
					            <tr>
					               <td align="right" nowrap="nowrap">是否日切</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="whetherRiqie" id="whetherRiqie">
												<option value="">全部</option>
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
											<input type="hidden" id="whetherRiqie_hidden" value="${whetherRiqie }"/>
										</span>
					                </td>
					            </tr>
					            <tr>
						            <td colspan="8" align="center" style="height: 30px"> 
					            		<input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					            		<input type="button" class="icon_normal" value="下载" onclick="downExcel();" />
						            </td>
					            </tr>
					        </table>
				        </center>
					</div>
				</form>
			<span class="red-radius-rt"></span>
			<span class="red-radius-lb"></span>
			<span class="red-radius-rb"></span>
		</div>
		
		<input type="hidden" id="dzSuccessMoney_hidden" value="${dzSuccessMoney }"/>
		<input type="hidden" id="merFee_hidden" value="${merFee }"/>
		<input type="hidden" id="dzFailMoney_hidden" value="${dzFailMoney }"/>
		<input type="hidden" id="noDzMoney_hidden" value="${noDzMoney }"/>
		
			<div style="font-size: 12px;">
				<span>
					本页共
					<font color="red">
						<c:if test="${fn:length(getDataResult.result) <= 0 }">0</c:if>
						<c:if test="${fn:length(getDataResult.result) > 0 }">${fn:length(getDataResult.result) }</c:if>
					</font>
					条数据
				</span>
				<span style="float: right;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					共
					<font color="red">
						<c:if test="${getDataResult.totalItems == null }">0</c:if>
						<c:if test="${getDataResult.totalItems != null }">${getDataResult.totalItems }</c:if>
					</font>
					条数据
					<font color="red">
						<c:if test="${getDataResult.totalPages == null}">0</c:if>
						<c:if test="${getDataResult.totalPages != null}">${getDataResult.totalPages}</c:if>
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
				<br />
				<span>
					<c:if test="${dzSuccessMoney != '' && dzSuccessMoney != null }">
						对账成功金额：
						<font color="red">
							<span id="dzSuccessMoney">${dzSuccessMoney }</span>
						</font>
						元&nbsp;&nbsp;
					</c:if>
					<c:if test="${merFee != '' && merFee != null }">
						商户手续费：
						<font color="red">
							<span id="merFee">${merFee }</span>
						</font>
						元&nbsp;&nbsp;
					</c:if>
					<c:if test="${dzFailMoney != '' && dzFailMoney != null }">
						对账失败金额：
						<font color="red">
							<span id="dzFailMoney">${dzFailMoney }</span>
						</font>
						元 &nbsp;&nbsp;
					</c:if>
					<c:if test="${noDzMoney != '' && noDzMoney != null }">
						未对账金额：
						<font color="red">
							<span id="noDzMoney">${noDzMoney }</span>
						</font>
						元
					</c:if>
				</span>
			</div>
			
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">交易流水号</td>
							<td align="center">商户订单号</td>
							<td align="center">电银商户号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">商户手续费</td>
							<td align="center">交易状态</td>
							<td align="center">交易类别</td>
							<td align="center">扣款渠道</td>
							<td align="center">是否清算</td>
							<td align="center">对账结果</td>
							<td align="center">是否日切</td>
							<td align="center">备注</td>
						</tr>
					</thead>
					<tbody id="underLine">
						<c:if test="${fn:length(getDataResult.result)<=0 }">
							<tr align="center">
								<td colspan="14">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${getDataResult.result }" var="tradeLst">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${tradeLst.deduct_sys_stance }</td>
								<td align="center">${tradeLst.oid}</td>
								<td align="center">${tradeLst.req_mer_code}</td>
								<td align="center">${tradeLst.deduct_sys_time}</td>
								<td align="center">
									<f:formatNumber value="${tradeLst.trade_amount }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<f:formatNumber value="${tradeLst.mer_fee }" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<c:if test="${tradeLst.trade_result == 0 }">初始状态</c:if>
									<c:if test="${tradeLst.trade_result == 1 }">待支付</c:if>
									<c:if test="${tradeLst.trade_result == 2 }">成功</c:if>
									<c:if test="${tradeLst.trade_result == 3 }">失败</c:if>
									<c:if test="${tradeLst.trade_result == 4 }">请求银行失败</c:if>
									<c:if test="${tradeLst.trade_result == 5 }">撤销</c:if>
									<c:if test="${tradeLst.trade_result == 6 }">超时</c:if>
								</td>
								<td align="center">${tradeLst.trade_type }</td>
								<td align="center">${tradeLst.inst_name }</td>
								<td align="center">
									<c:if test="${tradeLst.whetherQs == 0 }">否</c:if>
									<c:if test="${tradeLst.whetherQs == 1 }">是</c:if>
								</td>
								<td align="center">
									<c:if test="${tradeLst.bk_chk == 0 }">未对账</c:if>
									<c:if test="${tradeLst.bk_chk == 1 }">对账成功</c:if>
									<c:if test="${tradeLst.bk_chk == 2 }">对账失败</c:if>
									<c:if test="${tradeLst.bk_chk == 3 }">无需对账</c:if>
								</td>
								<td align="center">
									<c:if test="${tradeLst.whetherRiqie == 0 }">否</c:if>
									<c:if test="${tradeLst.whetherRiqie == 1 }">是</c:if>
								</td>
								<td align="center">${tradeLst.remark}</td>
							</tr>
						</c:forEach>
					</tbody>
			</table>
		
			<!-- 分页 -->
			<c:if test="${getDataResult.totalPages != null}">
				<div class="next">
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(${getDataResult.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${getDataResult.pageNo-2 > 0}">
						<a href="javascript:paging(${getDataResult.pageNo-2 })"><span>${getDataResult.pageNo-2
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo-1 > 0}">
						<a href="javascript:paging(${getDataResult.pageNo-1 })"><span>${getDataResult.pageNo-1
								}</span></a>
					</c:if>
					<a href="#" class="hover"><span>${getDataResult.pageNo }</span></a>
					<c:if test="${getDataResult.pageNo+1 <= getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+1 })"><span>${getDataResult.pageNo+1
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo+2 <= getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+2 })"><span>${getDataResult.pageNo+2
								}</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo+3 <= getDataResult.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${getDataResult.pageNo < getDataResult.totalPages}">
						<a href="javascript:paging(${getDataResult.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<c:if test="${getDataResult.pageNo > 1}">
						<a href="javascript:paging(${getDataResult.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${getDataResult.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${getDataResult.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	</div>
</body>
</html>