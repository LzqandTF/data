<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退款经办</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript">
	function init() {
		var tk_date = $("#tk_date_hidden").val();
		var tk_dates = document.getElementById("tk_date");
		for (var i = 0; i < tk_dates.length; i++) {
			if (tk_dates.options[i].value == tk_date) {
				tk_dates.options[i].selected = 'selected';
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
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("refundHandleForm");
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
			action = "<%=request.getContextPath()%>/queryPageRefundHandleData.do?pageNum=" + pageNo + "&pageSize=" + pageSize + "&flag="+flag+"&applyTkTotalAmount="+applyTkTotalAmount+"&refundMerFeeTotalAmount="+refundMerFeeTotalAmount;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("refundHandleForm");
		
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
			action = "<%=request.getContextPath()%>/queryPageRefundHandleData.do?pageSize=" + pageSize + "&flag="+flag+"&applyTkTotalAmount="+applyTkTotalAmount+"&refundMerFeeTotalAmount="+refundMerFeeTotalAmount;
			method = "post";
			form.submit();
		}
	}
	
	//查询
	function checkQuery(){
		var form = document.getElementById("refundHandleForm");
		var pageSize = $("#pageSize").val();
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime == '' || startTime == null || endTime == '' || endTime == null){
			alert("请选择日期!");
			return;
		}
		
		var flag = 1;
		
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageRefundHandleData.do?pageSize=" + pageSize + "&flag="+flag;
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
		var stat = $("#stat").val();
		var mid = $("#mid").val();
		var tseq = $("#tseq").val();
		var mer_status = $("#mer_status").val();
		var id = $("#tk_stance").val();
		
		var url ="<%=request.getContextPath()%>/refundHandleDataDownLoad.do?startTime="+startTime+
				"&endTime="+endTime+"&stat="+stat+
				"&mid="+mid+"&tseq="+tseq+"&tk_date="+tk_date+
				"&mer_status="+mer_status+"&id="+id;
		window.location=url;
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	//子复选框的事件  
	function setSelectAll(){  
	    var chsub = $("input[type='checkbox'][name='subcheck']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll").attr("checked", true);  
	    }else{
	    	$("#SelectAll").attr("checked", false);
	    } 
	}
	function selectAllDetail(){  
	    if ($("#SelectAllDetail").attr("checked")) {  
	        $(":checkbox").attr("checked", true);  
	    } else {  
	        $(":checkbox").attr("checked", false);  
	    }  
	}  
	
	//子复选框的事件  
	function setSelectAllDetail(){  
	    var chsub = $("input[type='checkbox'][name='subcheckDetail']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheckDetail']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAllDetail").attr("checked", true);  
	    }else{
	    	$("#SelectAllDetail").attr("checked", false);
	    } 
	}
	
	//全选、取消全选的事件  
	function selectAll(){  
	    if ($("#SelectAll").attr("checked")) {  
	        $(":checkbox").attr("checked", true);  
	    } else {  
	        $(":checkbox").attr("checked", false);  
	    }  
	}
	
	function tkCannel(){
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	
  	  $("#canncelTk").css({display:"block"});
	}
	
	function canncelTkCommit(){
		
		var etro_reason = $("#etro_reason").val();
		
		if(etro_reason == null || etro_reason == ''){
			alert("请填写撤销退款原因!");
			return;
		}
		
		if(!confirm("是否确认该操作？")){
			return;
		}
  	  	
  	  	var idList = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			idList[i] = $(this).val();
			i++;
		});
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/canncelTk.do",
			data : "idList=" + idList + "&etro_reason="+etro_reason,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg == 1) {
					alert("操作成功！");
					checkQuery();
				} else {
					alert("操作失败！");
				}
			}
		});
	}
	
	function tkCommit(){
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	
  	  if(!confirm("是否确认该操作？")){
			return;
		}
	  	
	  	var idList = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			idList[i] = $(this).val();
			i++;
		});
		
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/handleTk.do",
			data : "idList=" + idList,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg != null && msg != '') {
					alert("共经办"+msg.split(";")[0]+"条,经办完成"+msg.split(";")[1]+"条,重复经办"+msg.split(";")[2]+"条.\n无法经办"+msg.split(";")[3]+"条;\n["+msg.split(";")[6]+"]:余额不足;\n["+msg.split(";")[5]+"]:RYF接口调用失败;\n["+msg.split(";")[4]+"]:退款不在有效时间范围内;\n["+msg.split(";")[7]+"]:其他原因;");
					checkQuery();
				} else {
					alert("操作失败！");
				}
			}
		});
	}
	
</script>

</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">退款管理</a>&gt;<span>退款经办</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>" target="right" name="refundHandleForm" id="refundHandleForm" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>		
							<table width="95%" border="0" cellspacing="0">
					            <tr>
					            	<td align="right" nowrap="nowrap">
					            		<span class="input_bgl">
					                     	<select id="tk_date" name="tk_date" style="width: 150px;">
												<option value="1">退款确认日期</option>
												<option value="2">退款经办日期</option>
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
					                <td align="right" nowrap="nowrap">退款状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="stat" name="stat" style="width: 150px;">
												<option value="1">待处理</option>
												<option value="2">人工经办完成</option>
											</select>
											<input type="hidden" id="stat_hidden" value="${stat }"/>
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
					                <td align="right" nowrap="nowrap">退款流水号</td>
					                <td align="left" nowrap="nowrap">
					                  <span class="input_bgl">
					                     	<input type="text" name="tk_stance" id="tk_stance" value="${param.tk_stance }" />
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
			</div>
			
			<div class="table-m">
			<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
				<table width="1650px;" border="0" cellspacing="0">
					<thead>
						<tr>
							<td width="5%" align="center">
								<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
							</td>
							<td align="center">退款流水号</td>
							<td align="center">原电银流水号</td>
							<td align="center">商户号</td>
							<td align="center">商户简称</td>
							<td align="center">原商户订单号</td>
							<td align="center">原扣款渠道</td>
							<td align="center">原交易银行</td>
							<td align="center">申请退款金额</td>
							<td align="center">退回商户手续费</td>
							<td align="center">原交易日期</td>
							<td align="center">退款确认日期</td>
							<td align="center">退款状态</td>
							<td align="center">申请退款原因</td>
						</tr>
					</thead>
					<tbody id="underLine">
						<c:if test="${fn:length(getDataResult.result)<=0 }">
							<tr align="center">
								<td colspan="14">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${getDataResult.result }" var="refundLst">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">
									<c:if test="${refundLst.stat == 1}">
										<input type="checkbox" name="subcheck" id="subcheck" onclick="setSelectAll();" value="${refundLst.id}"/>
									</c:if>
								</td>
								<td align="center">${refundLst.id }</td>
								<td align="center">${refundLst.tseq}</td>
								<td align="center">${refundLst.mid}</td>
								<td align="center">${refundLst.mer_abbreviation}</td>
								<td align="center">${refundLst.org_oid }</td>
								<td align="center">${refundLst.name }</td>
								<td align="center">${refundLst.bankName }</td>
								<td align="center">${refundLst.ref_amt }</td>
								<td align="center">${refundLst.mer_fee }</td>
								<td align="center">${refundLst.sys_date }</td>
								<td align="center">${refundLst.req_date	}</td>
								<td align="center">
									<c:if test="${refundLst.stat==1	}">待处理</c:if>
									<c:if test="${refundLst.stat==2	}">人工经办完成</c:if>
								
								</td>
								<td align="center">${refundLst.refund_reason}</td>
							</tr>
						</c:forEach>
					</tbody>
			</table>
			</div>
			
			<div>
				<input type="button" class="icon_normal" value="退款完成" onclick="tkCommit();" />
				<input type="button" class="icon_normal" value="撤销退款" onclick="tkCannel();" />
			</div>
		
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
		
		
		
		<div id="canncelTk" class="pop" style="display: none">
				<div class="pop_body" style="margin-top: 10px;">
					<h1 class="pop_tit">
						<span class="fl">撤销退款</span> <a class="close"
							href="javascript:void(0);" onclick="hide('canncelTk')">&nbsp;</a>
					</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0" >
							<tr>
								<td align="right" bgcolor="#eeeeee">撤销原因：</td>
								<td>
									<textarea rows="3" cols="25" style="resize:none;" id="etro_reason" name="etro_reason"></textarea>
									<font color="red">*</font>
								</td>
							</tr>
						</table>
					</div>
					<div align="center">
						<input  type="button" class="icon_normal" value="提交" onclick="canncelTkCommit()" />
					</div>
				</div>
			</div>
	</div>
	</div>
</body>
</html>