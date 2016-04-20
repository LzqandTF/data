<%@page import="com.chinaebi.entity.Login"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品牌服务费查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function paging(pageNo) {
		var form = document.getElementById("ppfwForm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryPpfwData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function queryByPageSize(pageSize) {
		var form = document.getElementById("ppfwForm");
		var inst_id = $("#inst_id").val();
		
		if (inst_id == null || inst_id == "") {
			alert("请选择扣款渠道！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryPpfwData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function checkQuery(){
		var reqSysStance = $("#reqSysStance").val();
		var inst_id = $("#inst_id").val();
		
		if(inst_id == null  || inst_id == ''){
			alert("请选择扣款渠道！");
			return;
		}
		
		var reg = /^\d{1,}$/;		
		if(reqSysStance != null && reqSysStance.length != 0){
			if(!reg.test(reqSysStance)){
				alert("流水号必须输入数字！");
				return;
			}
		}
		var form = document.getElementById("ppfwForm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryPpfwData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	

	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	function clearForm(){
		$("#reqSysStance").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#deductStlmDate").val("");
		var inst_selected = document.getElementById("inst_name");
		for(var i = 0;i<inst_selected.options.length;i++){
			if(inst_selected.options[i].value == ""){
				inst_selected.options[i].selected = 'selected';
			}
		}
	}
	
	function init(){
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryParsingPpfwInstList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			if(msg.length != null && msg.length != ""){
    				$("#inst_id").empty();
    				for (i in msg){
 	    				$("#inst_id").append('<option value="' + msg[i]['instId'] + '">'+ msg[i]['name'] + '</option>');
	    			}
    			}
    		}
    	});
		var inst_id = $("#inst_id_hidden").val();
		var type = document.getElementById("inst_id");
		
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
	function clearEndTime(){
		$("#endTime").val("");
	}
	
	
	function queryByPage(e) {
		var e = e || window.event;
		if (e.keyCode == 13) {
			var pageNum = $("#pageNum").val();
			
			if (pageNum >= 1) {
				paging(pageNum);
			} else {
				paging(1);
			}
		}
	}
	
	
	//设置每页显示条数
	function EnterPress(e){ //传入 event
		var e = e || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(10);
			}
		}
	} 
</script>
</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>品牌服务费查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="1" target="right" id="ppfwForm" name="ppfwForm" method="post">
					<table align="center" style="width: 90%;">
						<tr>
							<td>交易时间</td>
							<td>
								<span class="input_bgl">
								<input maxlength="20" style="width: 70px;" readonly="readonly" name="startTime" id="startTime" value="${param.startTime }" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
									&nbsp;&nbsp;至&nbsp;&nbsp;
								<input maxlength="20" style="width: 70px;" readonly="readonly" name="endTime" id="endTime" value="${param.endTime }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})"/>
								</span>
							</td>
							<td>清算时间</td>
							<td>
								<span class="input_bgl">
								<input maxlength="20" readonly="readonly" name="deductStlmDate" id="deductStlmDate" value="${param.deductStlmDate }" 
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{\'%y-%M-%d\'}'});" />
								</span>
							</td>
							<td>交易流水号</td>
							<td>
								<span class="input_bgl">
									<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\w]/ig,'')"/>
								</span>
							</td>
						</tr>
						<tr>
							<td>扣款渠道</td>
							<td>
								<span class="in_t_bgl">
									<select id="inst_id" name="inst_id">
										<option value="">--请选择扣款渠道--</option>
									</select>
									<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
								</span><font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center;">
								<br />
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="重置" onclick="clearForm()"/>
							</td>
						</tr>
					</table>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${empty size}">0</c:if>
					<c:if test="${size > 0 }">${size}</c:if>
				</font>
				条数据
				</span>
				&nbsp;&nbsp;&nbsp;
				<span>
				品牌服务费共
				<font color="red">
					<c:if test="${totalFee == null }">0</c:if>
     				<c:if test="${totalFee != null }">${totalFee }</c:if>
				</font>
				元
				</span>
				<span style="float: right;">共<font color="red">
					<c:if test="${totalItems == null }">0</c:if>
					<c:if test="${totalItems != null }">${totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${totalPage == null}">0</c:if>
					<c:if test="${totalPage != null}">${totalPage}</c:if>
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
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">系统跟踪号</td>
							<td align="center">交易传输时间</td>
							<td align="center">主账号</td>
							<td align="center">扣款商户号</td>
							<td align="center">交易金额</td>	
							<td align="center">受理方手续费</td>		
							<td align="center">品牌手续费</td>
							<td align="center">净金额</td>
							<td align="center">来源</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageData.result)<=0 }">
						<tr align="center">
							<td colspan="12">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageData.result }" var="data">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${data.reqSysStance }</td>
							<td align="center">${data.reqTime }</td>
							<td align="center">${data.cardNo }</td>
							<td align="center">${data.card_identification_code }</td>
							<td align="center">
								<f:formatNumber value="${data.trade_amount }" pattern="0.00"></f:formatNumber>
							</td>
							<td align="center">${data.card_issuer }</td>
							<td align="center">${data.brand_service_fee }</td>
							<td align="center">${data.net_amount }</td>
							<td align="center">${data.dz_file_name }</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageData.totalPages != null}">
				<div class="next">
					<c:if test="${pageData.pageNo > 1}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo-2 > 0}">
						<a href="javascript:paging(${pageData.pageNo-2 })"><span>${pageData.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-1 > 0}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>${pageData.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageData.pageNo }</span></a>
					<c:if test="${pageData.pageNo+1 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>${pageData.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+2 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+2 })"><span>${pageData.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+3 <= pageData.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo < pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageData.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum"
							value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<div id="detail" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账数据详细信息</span> 
				<a class="close" href="javascript:hide('detail')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">					
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">代理机构标识码：</td>
						<td id="acqInstIdCode_"></td>
						<td width="140" align="right" bgcolor="#eeeeee">发送机构标识码：</td>
						<td id="fwdInstIdCode_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">系统跟踪号：</td>
						<td id="reqSysStance_"></td>
						<td align="right" bgcolor="#eeeeee">交易传输时间：</td>
						<td id="reqTime_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">主账号：</td>
						<td id="outAccount_"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">持卡人交易手续费：</td>
						<td id="tradeFee_"></td>
						<td align="right" bgcolor="#eeeeee">报文类型：</td>
						<td id="msgType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易类型码：</td>
						<td id="process_"></td>
						<td align="right" bgcolor="#eeeeee">商户类型：</td>
						<td id="merType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">受卡机终端标识码：</td>
						<td id="termId_"></td>
						<td align="right" bgcolor="#eeeeee">受卡方标识码：</td>
						<td id="merCode_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">检索参考号：</td>
						<td id="deductSysReference_"></td>
						<td align="right" bgcolor="#eeeeee">服务点条件码：</td>
						<td id="reqType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接收机构标识码：</td>
						<td id="rcvgInstIdCode_"></td>
						<td align="right" bgcolor="#eeeeee">原始交易的系统跟踪号：</td>
						<td id="origDataStance_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易返回码：</td>
						<td id="deductSysResponse_"></td>
						<td align="right" bgcolor="#eeeeee">服务点输入方式：</td>
						<td id="reqInputType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">原始交易日期时间：</td>
						<td id="origDataTime_"></td>
						<td align="right" bgcolor="#eeeeee">终端类型：</td>
						<td id="terminalType_"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">授权应答码：</td>
						<td id="authorizationCode_"></td>
						<td align="right" bgcolor="#eeeeee">是否需要手工差错处理：</td>
						<td id="whetherErroeHandle_"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
