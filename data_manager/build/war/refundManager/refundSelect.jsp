<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退款查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//初始化银行机构选择下拉框
	function initData() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getOnLineInstInfoList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#gate").append('<option value="' + msg[i]['instId'] +'">'+ msg[i]['name'] + '</option>');
			}
		});
		
		var tk_date = $("#tk_date_hidden").val();
		var tk_dates = document.getElementById("tk_date");
		for (var i = 0; i < tk_dates.length; i++) {
			if (tk_dates.options[i].value == tk_date) {
				tk_dates.options[i].selected = 'selected';
			}
		}
		
		var inst_name = $("#inst_name").val();
		if (inst_name != "") {
			loadInstInfo(inst_name);
		}
		
		var gate = $("#gate_hidden").val();
		var gates = document.getElementById("gate");
		for (var i = 0; i < gates.length; i++) {
			if (gates.options[i].value == gate) {
				gates.options[i].selected = 'selected';
			}
		}
		
		var stat = $("#stat_hidden").val();
		var stats = document.getElementById("stat");
		for (var i = 0; i < stats.length; i++) {
			if (stats.options[i].value == stat) {
				stats.options[i].selected = 'selected';
			}
		}
		
		var mer_status = $("#mer_status_hidden").val();
		var mer_statuss = document.getElementById("mer_status");
		for (var i = 0; i < mer_statuss.length; i++) {
			if (mer_statuss.options[i].value == mer_status) {
				mer_statuss.options[i].selected = 'selected';
			}
		}
		
		var refund_type = $("#refund_type_hidden").val();
		var refund_types = document.getElementById("refund_type");
		for (var i = 0; i < refund_types.length; i++) {
			if (refund_types.options[i].value == refund_type) {
				refund_types.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	// 根据银行机构获取渠道信息
	function loadInstInfo(inst_name) {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getInstInfoByInstName.do',
    		type : 'post',
    		data : 'inst_name='+inst_name,
    		async : false,
    		dataType : 'text',
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("gate");
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
	
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("refundSelectForm");
		var pageSize = $("#pageSize").val();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择日期!");
			return;
		}
		
		var flag = 2;
		var applyTkTotalAmount = $("#applyTkTotalAmount").val();
		var refundMerFeeTotalAmount = $("#refundMerFeeTotalAmount").val();
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageRedundData.do?pageNum=" + pageNo + "&pageSize=" + pageSize + "&flag="+flag+"&applyTkTotalAmount="+applyTkTotalAmount+"&refundMerFeeTotalAmount="+refundMerFeeTotalAmount;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("refundSelectForm");
		
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择日期!");
			return;
		}
		
		var flag = 2;
		var applyTkTotalAmount = $("#applyTkTotalAmount").val();
		var refundMerFeeTotalAmount = $("#refundMerFeeTotalAmount").val();
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageRedundData.do?pageSize=" + pageSize + "&flag="+flag+"&applyTkTotalAmount="+applyTkTotalAmount+"&refundMerFeeTotalAmount="+refundMerFeeTotalAmount;
			method = "post";
			form.submit();
		}
	}
	
	//查询
	function checkQuery(){
		var form = document.getElementById("refundSelectForm");
		var pageSize = $("#pageSize").val();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择日期!");
			return;
		}
		
		var flag = 1;
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageRedundData.do?pageSize=" + pageSize + "&flag="+flag;
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
			alert("请选择日期!");
			return;
		}
		
		var tk_date = $("#tk_date").val();
		var gate = $("#gate").val();
		var stat = $("#stat").val();
		var mid = $("#mid").val();
		var tseq = $("#tseq").val();
		var mer_status = $("#mer_status").val();
		var refund_type = $("#refund_type").val();
		
		var url ="<%=request.getContextPath()%>/refundSelectDataDownLoad.do?startTime="+startTime+
				"&endTime="+endTime+"&gate="+gate+
				"&stat="+stat+"&mid="+mid+"&tk_date="+tk_date+
				"&tseq="+tseq+"&mer_status="+mer_status+
				"&refund_type="+refund_type;
		window.location=url;
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	//根据ID获取详细信息
	function queryDetail(refund_id){
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryRefundDataDetail.do',
			data : {"id": refund_id},
			async:false,
			success : function(refund) {
				if(refund != null){
					$("#tseq_").html(refund.tseq);
					$("#mid_").html(refund.mid);
					$("#org_oid_").html(refund.org_oid);
					$("#id_").html(refund.id);
					$("#oid_").html(refund.oid);
					$("#mdate_").html(refund.mdate);
					$("#ref_amt_").html(refund.ref_amt);
					
					var stat = refund.stat;
					if(stat == 5){
						$("#stat_").html("商户申请退款");
					}else if(stat == 1){
						$("#stat_").html("商户确认退款");
					}else if(stat == 7){
						$("#stat_").html("商户撤销退款");
					}else if(stat == 2){
						$("#stat_").html("退款成功");
					}else if(stat == 6){
						$("#stat_").html("退款失败");
					}else if(stat == 3){
						$("#stat_").html("操作成功");
					}else if(stat == 4){
						$("#stat_").html("操作失败");
					}else{
						$("#stat_").html("未知");
					}
					
					$("#mer_fee_").html(refund.mer_fee);
					$("#gate_").html(refund.name);
					$("#gid_").html(refund.bankName);
					$("#org_bk_seq_").html(refund.org_bk_seq);
					$("#org_amt_").html(refund.org_amt);
					$("#org_pay_amt_").html(refund.org_pay_amt);
					$("#pre_amt1_").html(refund.pre_amt1);
					$("#req_date_").html(refund.req_date);
					$("#pro_date_").html(refund.pro_date);
					$("#ref_date_").html(refund.ref_date);
					
					var refund_type = refund.refund_type;
					if(refund_type == 0){
						$("#refund_type_").html("人工经办");
					}else if(refund_type == 1){
						$("#refund_type_").html("联机退款");
					}else if(refund_type == 2){
						$("#refund_type_").html("联机退款-人工经办");
					}else{
						$("#refund_type_").html("未知");
					}
					
					$("#etro_reason_").html(refund.etro_reason);
					$("#reason_").html(refund.reason);
					$("#detail").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
</script>

</head>
<body onload="initData();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">退款管理</a>&gt;<span>退款查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>" target="right" name="refundSelectForm" id="refundSelectForm" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>		
							<table width="95%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">
					            		<span class="input_bgl">
					                     	<select id="tk_date" name="tk_date" style="width: 150px;">
												<option value="0">退款申请日期</option>
												<option value="1">退款确认日期</option>
												<option value="2">退款经办日期</option>
												<option value="3">退款审核日期</option>
											</select>
											<input type="hidden" id="tk_date_hidden" value="${tk_date }"/>
					                     </span>
					            	</td>
					                <td align="left" nowrap="nowrap">
					                  <span style="width:70px;" class="input_bgl">
									  	<input style="width: 70px" id="startTime" name="startTime"  value="${param.startTime }" 
									  		readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										至
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }" 
											readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{M:1})}'})" />
									  <font color="red">*</font>
									  </span>
					                </td>
					                <td align="right" nowrap="nowrap">原扣款渠道</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" id="inst_name" name="inst_name" value="${param.inst_name }" onblur="loadInstInfo(this.value)" />
					                     	<select id="gate" name="gate" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="gate_hidden" value="${gate }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">退款状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="stat" name="stat" style="width: 150px;">
												<option value="">全部</option>
												<option value="5">商户申请退款</option>
												<option value="1">商户确认退款</option>
												<option value="7">商户撤销退款</option>
												<option value="2">退款成功</option>
												<option value="6">退款失败</option>
												<option value="4">操作失败</option>
											</select>
											<input type="hidden" id="stat_hidden" value="${stat }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
					                <td align="right" nowrap="nowrap">商户号</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="mid" id="mid" value="${param.mid }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">原电银流水号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
					                     	<input type="text" name="tseq" id="tseq" value="${param.tseq }" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">商户状态</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="mer_status" id="mer_status">
												<option value="">全部</option>
												<option value="0">正常</option>
												<option value="1">关闭</option>
											</select>
											<input type="hidden" id="mer_status_hidden" value="${mer_status }"/>
										</span>
					                </td>
					            </tr>
					            <tr>
					               <td align="right" nowrap="nowrap">退款处理类型</td>
					                <td align="left" nowrap="nowrap">
						                <span style="width:30px;" class="input_bgl">
											<select name="refund_type" id="refund_type">
												<option value="">全部</option>
												<option value="0">人工经办</option>
												<option value="1">联机退款</option>
												<option value="2">联机人工经办</option>
											</select>
											<input type="hidden" id="refund_type_hidden" value="${refund_type }"/>
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
					申请退款总金额：
					<font color="red">
						<c:if test="${applyTkTotalAmount == null }">0.00</c:if>
						<c:if test="${applyTkTotalAmount != null }">${applyTkTotalAmount }</c:if>
					</font>
					元;&nbsp;&nbsp;
					退回商户手续费总金额：
					<font color="red">
						<c:if test="${refundMerFeeTotalAmount == null }">0.00</c:if>
						<c:if test="${refundMerFeeTotalAmount != null }">${refundMerFeeTotalAmount }</c:if>
					</font>
					元;
				</span>
				
				<input type="hidden" id="applyTkTotalAmount" value="${applyTkTotalAmount }" />
				<input type="hidden" id="refundMerFeeTotalAmount" value="${refundMerFeeTotalAmount }" />
			</div>
			
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">退款流水号</td>
							<td align="center">原电银流水号</td>
							<td align="center">商户号</td>
							<td align="center">商户简称</td>
							<td align="center">原商户订单号</td>
							<td align="center">原扣款渠道</td>
							<td align="center">原交易银行</td>
							<td align="center">申请退款金额</td>
							<td align="center">退回商户手续费</td>
							<td align="center">退款确认日期</td>
							<td align="center">退款状态</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<tbody id="underLine">
						<c:if test="${fn:length(getDataResult.result)<=0 }">
							<tr align="center">
								<td colspan="12">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${getDataResult.result }" var="refundLst">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${refundLst.id }</td>
								<td align="center">${refundLst.tseq}</td>
								<td align="center">${refundLst.mid}</td>
								<td align="center">${refundLst.mer_abbreviation}</td>
								<td align="center">${refundLst.org_oid }</td>
								<td align="center">${refundLst.name }</td>
								<td align="center">${refundLst.bankName}</td>
								<td align="center">${refundLst.ref_amt }</td>
								<td align="center">${refundLst.mer_fee }</td>
								<td align="center">${refundLst.req_date }</td>
								<td align="center">
									<c:if test="${refundLst.stat == 1 }">商户确认退款</c:if>
									<c:if test="${refundLst.stat == 2 }">退款成功</c:if>
									<c:if test="${refundLst.stat == 3 }">操作成功</c:if>
									<c:if test="${refundLst.stat == 4 }">操作失败</c:if>
									<c:if test="${refundLst.stat == 5 }">商户申请退款</c:if>
									<c:if test="${refundLst.stat == 6 }">退款失败</c:if>
									<c:if test="${refundLst.stat == 7 }">商户取消退款</c:if>
								</td>
								<td align="center">
									<a class="fl lj mr10" href="#" onclick="queryDetail('${refundLst.id}')">详情</a>
								</td>
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
		<div id="detail" class="pop" style="display: none">
				<div class="pop_body" style="margin-top: 10px;">
					<h1 class="pop_tit">
						<span class="fl">退款订单信息详情</span> <a class="close"
							href="javascript:void(0);" onclick="hide('detail')">&nbsp;</a>
					</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0" id="operator">
							<tr>
								<td align="right" bgcolor="#eeeeee">原电银流水：</td>
								<td id="tseq_"></td>
								<td align="right" bgcolor="#eeeeee">商户号：</td>
								<td id="mid_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">原商户订单号：</td>
								<td id="org_oid_"></td>
								<td align="right" bgcolor="#eeeeee">退款流水号：</td>
								<td id="id_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">退款单号</td>
								<td id="oid_"></td>
								<td align="right" bgcolor="#eeeeee">退款申请日期</td>
								<td id="mdate_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">本次退款金额：</td>
								<td id="ref_amt_"></td>
								<td align="right" bgcolor="#eeeeee">退款状态：</td>
								<td id="stat_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">退回商户手续费：</td>
								<td id="mer_fee_"></td>
								<td align="right" bgcolor="#eeeeee">原扣款渠道：</td>
								<td id="gate_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">原交易银行：</td>
								<td id="gid_"></td>
								<td align="right" bgcolor="#eeeeee">原银行流水号：</td>
								<td id="org_bk_seq_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">原订单金额：</td>
								<td id="org_amt_"></td>
								<td align="right" bgcolor="#eeeeee">原实际交易金额：</td>
								<td id="org_pay_amt_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">原订单优惠金额：</td>
								<td id="pre_amt1_"></td>
								<td align="right" bgcolor="#eeeeee"></td>
								<td id=""></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">退款确认日期：</td>
								<td id="req_date_"></td>
								<td align="right" bgcolor="#eeeeee">退款经办日期：</td>
								<td id="pro_date_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">退款审核日期：</td>
								<td id="ref_date_"></td>
								<td align="right" bgcolor="#eeeeee">退款处理类型：</td>
								<td id="refund_type_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">退款撤销原因：</td>
								<td colspan="3" id="etro_reason_"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">失败原因：</td>
								<td colspan="3" id="reason_"></td>
							</tr>
						</table>
					</div>
					<div align="center">
						<input  type="button" class="icon_normal" value="返回" onclick="hide('detail')" />
					</div>
				</div>
			</div>
	</div>
	</div>
</body>
</html>