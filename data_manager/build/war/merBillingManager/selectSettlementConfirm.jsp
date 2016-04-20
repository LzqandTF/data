<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算制表确认</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<style type="text/css">
	#loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        filter:alpha(opacity=80);
    }
    #loading{
        position:absolute;
        left:40%;
        top:50%;
        padding:2px;
        z-index:20001;
        height:auto;
 }
    #loading .loading-indicator{
        font:bold 20px tahoma,arial,helvetica;
        padding:10px;
        margin:0;
        height:auto;
    }
    #loading-msg {
        font: normal 18px arial,tahoma,sans-serif;
    }
</style>
<script type="text/javascript">
	//分页查询 
	function paging(pageNo) {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == null || startTime == "" || endTime == null || endTime == "") {
			alert("请选择结算截止日期！");
			return ;
		}
		var form = document.getElementById("queryMerFundSetInfoConfirm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundSetInfoConfirm.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == null || startTime == "" || endTime == null || endTime == "") {
			alert("请选择结算截止日期！");
			return ;
		}
		var form = document.getElementById("queryMerFundSetInfoConfirm");
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundSetInfoConfirm.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//设置每页显示条数
	function EnterPress(eve){ //传入 event
		var e = eve || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(30);
			};
		};
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
			};
		};
	}
	
	//查询数据
	function checkQuery(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == null || startTime == "" || endTime == null || endTime == "") {
			alert("请选择结算截止日期！");
			return ;
		}
		var form = document.getElementById("queryMerFundSetInfoConfirm");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerFundSetInfoConfirm.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		} 
	}
	
	function init(){
		var mer_type = $("#hidden_merType").val();
		var merType = document.getElementById("merType");
		for (var i = 0; i < merType.length; i++) {
			if (merType.options[i].value == mer_type) {
				merType.options[i].selected = 'selected';
			}
		}
		
		var hidden_settlementAccountType = $("#hidden_settlementAccountType").val();
		var settlementAccountType = document.getElementById("settlementAccountType");
		for (var i = 0; i < settlementAccountType.length; i++) {
			if (settlementAccountType.options[i].value == hidden_settlementAccountType) {
				settlementAccountType.options[i].selected = 'selected';
			}
		}
		
		
		var hidden_settleState = $("#hidden_settleState").val();
		var settleState = document.getElementById("settleState");
		for (var i = 0; i < settleState.length; i++) {
			if (settleState.options[i].value == hidden_settleState) {
				settleState.options[i].selected = 'selected';
			}
		}
		
		changeButtonStatus(hidden_settlementAccountType,hidden_settleState);
		
		//保存页面上每页显示的数据条数
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 30;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//根据商户修改结算确认状态
	function updatemerinfo(){
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要操作的数据！");
  	   		return ;
  	 	}
  	  	if(!confirm("是否确认该操作?")){ 
			return ; 
		}
		var id = "";
		var settle_way = $("#settlementAccountType").val();//制表账户类型
		var sel = document.getElementsByName("subcheck");//获取checkbox的值
		for( var i = 0; i < sel.length; i++)
		if(sel[i].checked == true)
			id += sel[i].value+",";
			if(id != ""){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/updateMerFundSetInfoToJsQr.do",
					data : {'id':id,'settle_way' : settle_way},
					async:false,
					beforeSend:function(){
						document.getElementById("loading-mask").style.display="";
				        document.getElementById("loading").style.display="";
					},
					complete:function(){
						document.getElementById("loading-mask").style.display="none";
				        document.getElementById("loading").style.display="none";
					},
					success : function(msg) {
						if(msg != null){
							alert("共结算确认"+msg.split(";")[0]+"条,成功"+msg.split(";")[1]+"条");
						}
						checkQuery();
					},
					error : function(msg) {
						alert("操作失败");
					}
				}); 
			}
	}
	
	//隐藏域
	function hide(obj) {
		var o = document.getElementById(obj);	
		o.style.display = "none";
		history.go(-1);
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
	
	//根据ID获取详情信息
	function selectSettlementById(merCode,merName,settleAmount,startDate,endDate,settle_type,settlementAccountType,merBatchNo,sysBatchNo,merType){
			$("#detailed").css({display:"block"});
			$("#merCodes").html(merCode);
			$("#merNames").html(merName);
			$("#settleAmounts").html(settleAmount);
			$("#startDates").html(startDate);
			$("#endDates").html(endDate);
			if(settle_type == '1'){
				$("#payments").html("全额");
			}else{
				$("#payments").html("净额");
			}
			if(settlementAccountType == '1'){
				$("#settlementAccountTypes").html("银行卡账号");
			}else{
				$("#settlementAccountTypes").html("电银账号");
			}
			$("#merBatchNos").html(merBatchNo);
			$("#sysBatchNos").html(sysBatchNo);
			$("#mer_code_hide").val(merCode);
			$("#start_date_hide").val(startDate);
			$("#end_dates_hide").val(endDate);
			$("#mer_name_hide").val(merName);
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/queryMerSettleDataDetailList.do",
				data : {'merCode':merCode,'startDate':startDate,'endDate':endDate},
				async:false,
				success : function(msg) {
					$("#selectSettlementDetailed").empty();
					$("#selectSettlementDetailed").append(msg);
				}
			}); 
		}
	//分页查询 明细
	function pagingInfo(pageNo) {
		var merCode=$("#mer_code_hide").val();
		var startDate=$("#start_date_hide").val();
		var endDate=$("#end_dates_hide").val();
		 $.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/queryMerSettleDataDetailList.do",
				data : {'pageNum' : pageNo,'merCode' : merCode,'startDate' : startDate,'endDate' : endDate},
				async:false,
				success : function(msg) {
					$("#selectSettlementDetailed").empty();
					$("#selectSettlementDetailed").append(msg);
				}
			}); 
	}
	//根据每页显示的数据条数分页查询 明细
	function queryByPageSizeInfo(pageSize) {
		var merCode=$("#mer_code_hide").val();
		var startDate=$("#start_date_hide").val();
		var endDate=$("#end_dates_hide").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryMerSettleDataDetailList.do",
			data : {'pageSize' : pageSize ,'merCode' : merCode,'startDate' : startDate,'endDate' : endDate},
			async:false,
			success : function(msg) {
				$("#selectSettlementDetailed").empty();
				$("#selectSettlementDetailed").append(msg);
			}
		}); 
	}
	
	function changeButtonStatus(settlementAccountType,settleState){
		if(settlementAccountType == 2 && settleState == 2){
			$("#uploadDataButton").attr("disabled",false);
		}else{
			$("#uploadDataButton").attr("disabled",true);
		}
	}
	
	function uploadDataToFtp(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime == null || startTime == "" || endTime == null || endTime == "") {
			alert("请选择结算截止日期！");
			return ;
		}
		var settlementAccountType = $("#settlementAccountType").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/uploadSettleDataToFtp.do",
			data : "startTime=" + startTime + "&endTime=" + endTime + "&settlementAccountType="+settlementAccountType,
			dataType : "text",
			beforeSend:function(){
				document.getElementById("loading-mask").style.display="";
		        document.getElementById("loading").style.display="";
			},
			complete:function(){
				document.getElementById("loading-mask").style.display="none";
		        document.getElementById("loading").style.display="none";
			},
			success : function(msg) {
				if(msg == 0){
					alert("数据同步成功");
				}else if(msg == -1){
					alert("数据同步失败");
				}else{
					alert("该日期范围内存在未结算确认的信息，请核实！");
				}
			}
		}); 
	}
	
	function clearEndTime() {
		$("#endTime").val("");
	}
</script>
</head>

<body onload="init();">
	<div id='loading-mask' style="display: none"></div>
	<div id="loading" style="display: none">
	    <div class="loading-indicator">
	       <img src="<%=request.getContextPath()%>/images/wait-1.gif" width="60" height="60" style="margin-right:8px;float:left;vertical-align:top;"/>
	       <br/><span id="loading-msg"></span>
	    </div>
	</div>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>结算制表确认</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerFundSetInfoConfirm.do" target="right" name="queryMerFundSetInfoConfirm" id="queryMerFundSetInfoConfirm" method="post">
				  <div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="90%" border="0" cellspacing="0">	
							<tr>
								<td align="right" nowrap="nowrap">结算截止日期</td>
				            	<td nowrap="nowrap">
									<span style="width: 70px;" class="input_bgl"> 
										<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime(endTime);" />
										至 
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>
									<font color='red' size="4" style="margin-left: 2px;float:right;">*</font>		
								</td>
								<td align="right" nowrap="nowrap">结算账户类型</td>
								<td nowrap="nowrap">
								 <span class="input_bgl">
										<select name="settlementAccountType" id="settlementAccountType" onchange="changeButtonStatus(this.value,$('#settleState').val());" style="width: 150px;">
											  <option value="1">银行账户</option>
											  <option value="2">电银账户</option>
										</select>
										<input type="hidden" id="hidden_settlementAccountType" value="${settlementAccountType}"/>
									</span> 
								</td>
								
								<td align="right" nowrap="nowrap">商户类型</td>
								<td nowrap="nowrap">
									<span class="input_bgl">
									 <select name="merType" id="merType" style="width: 150px;">
												  <option value="">全部</option>
												  <option value="0">RYF商户</option>
												  <option value="1">VAS商户</option>
												  <option value="2">POS商户</option>
											</select>
											<input type="hidden" id="hidden_merType" value="${merType}"/>
									</span>
								</td>	
							</tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">商户批次号</td>
				            	<td nowrap="nowrap">
					            	<span class="input_bgl">
										<input  type="text" name="merBatchNo" id = "merBatchNo" value="${param.merBatchNo }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
									</span>
								</td>
								<td align="right" nowrap="nowrap">系统批次号</td>
								<td nowrap="nowrap">
					            	<span class="input_bgl">
										<input  type="text" name="sysBatchNo" id = "sysBatchNo" value="${param.sysBatchNo }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									</span>
								</td>
								<td align="right" nowrap="nowrap">商户号</td>
								<td nowrap="nowrap">
					            	<span class="input_bgl">
										<input  type="text" name="merCode" id = "merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
									</span>
								</td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">结算状态</td>
								<td nowrap="nowrap">
								 <span class="input_bgl">
										<select name="settleState" id="settleState"  onchange="changeButtonStatus($('#settlementAccountType').val(),this.value);"  style="width: 150px;">
											  <option value="1">已制表</option>
											  <option value="2">已确认</option>
										</select>
										<input type="hidden" id="hidden_settleState" value="${settleState}"/>
									</span> 
								</td>
				            </tr>
				            <tr> <td colspan="8" align="center" style="height: 30px" class="cb mt0"><input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/></td></tr>
				        </table>
			        </center>
				 </div>
			   </form>
			</div>
			
			<!-- 分页显示数据条数 -->
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(merfunsetconfirm.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(merfunsetconfirm.result) > 0 }">${fn:length(merfunsetconfirm.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${merfunsetconfirm.totalItems == null }">0</c:if>
					<c:if test="${merfunsetconfirm.totalItems != null }">${merfunsetconfirm.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${merfunsetconfirm.totalPages == null}">0</c:if>
					<c:if test="${merfunsetconfirm.totalPages != null}">${merfunsetconfirm.totalPages}</c:if>
				</font>页
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>
					每页显示
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="30" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					<input type="hidden" id="pageSize_hidden" value="${pageSize }"/>
					条
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</div>
			
			<div class="table-m">
			   <div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
				<table width="1700px;" border="0" cellspacing="0">
					<thead>
						<tr>
							<td width="5%" align="center">
								<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
							</td>
						    <td align="center">商户批次号</td>
							<td align="center">系统批次号</td>
						    <td align="center">商户号</td>
						    <td align="center">商户简称</td>
						    <td align="center">是否资金分账</td>
						    <td align="center">结算起始日期</td>
							<td align="center">结算截止日期</td>
							<td align="center">支付金额</td>
							<td align="center">支付笔数</td>
							<td align="center">退款金额</td>
							<td align="center">退款笔数</td>
							<td align="center">交易净额</td>
							<td align="center">银行手续费</td>
							<td align="center">商户手续费</td>
							<td align="center">商户退回手续费</td>
							<td align="center">手工调增</td>
							<td align="center">手工调减</td>
							<td align="center">应结算金额</td>
							<td align="center">结算发起日期</td>
							<td align="center">结算状态</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(merfunsetconfirm.result)<=0 }">
						<tr align="center">
							<td colspan="18">对不起,暂无数据！</td>
						</tr>
					</c:if> 
					<c:forEach items="${merfunsetconfirm.result}" var="merfunsetconfirm"> 
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">
								<c:if test="${merfunsetconfirm.settle_state == 1}">
									<input type="checkbox" name="subcheck" id="subcheck" onclick="setSelectAll();" value="${merfunsetconfirm.id}"/>
								</c:if>
								<c:if test="${merfunsetconfirm.settle_state == 2}"></c:if>
							</td>
							<td align="center">${merfunsetconfirm.mer_batch_no}</td>
							<td align="center">${merfunsetconfirm.sys_batch_no}</td>
							<td align="center">${merfunsetconfirm.mer_code}</td>
							<td align="center">${merfunsetconfirm.mer_name}</td>
							<td align="center">
								<c:if test="${merfunsetconfirm.whtherFz == 0}">否</c:if>
								<c:if test="${merfunsetconfirm.whtherFz == 1}">是</c:if>
							</td>
							<td align="center">${merfunsetconfirm.start_date}</td>
							<td align="center">${merfunsetconfirm.end_date}</td>
							<td align="center">${merfunsetconfirm.trade_amount}</td>
							<td align="center">${merfunsetconfirm.trade_count}</td>
							<td align="center">${merfunsetconfirm.refund_amount}</td>
							<td align="center">${merfunsetconfirm.refund_count}</td>
							<td align="center">
								<f:formatNumber value="${merfunsetconfirm.trade_amount + merfunsetconfirm.refund_amount}" pattern="0.00"></f:formatNumber>
							</td>
							<td align="center">${merfunsetconfirm.system_fee}</td>
							<td align="center">${merfunsetconfirm.mer_fee}</td>
							<td align="center">${merfunsetconfirm.refund_mer_fee}</td>
							<td align="center">${merfunsetconfirm.rec_amount_add}</td>
							<td align="center">${merfunsetconfirm.rec_amount_sub}</td>
							<td align="center">${merfunsetconfirm.settle_amount}</td>
							<td align="center">${merfunsetconfirm.settle_date}</td>
							<td align="center">
								<c:if test="${merfunsetconfirm.settle_state == 1}">已制表</c:if>
								<c:if test="${merfunsetconfirm.settle_state == 2}">已确认</c:if>
							</td>
							<td align="center">
								<a class="fl lj mr10" href="#" onclick="selectSettlementById('${merfunsetconfirm.mer_code}','${merfunsetconfirm.mer_name}','${merfunsetconfirm.settle_amount}',
									'${merfunsetconfirm.start_date}','${merfunsetconfirm.end_date}','${merfunsetconfirm.settle_type}','${merfunsetconfirm.settle_way}',
									'${merfunsetconfirm.mer_batch_no}','${merfunsetconfirm.sys_batch_no}','${merinfolist.mer_type}');">详情</a>
							</td>
						</tr>
					</c:forEach> 
				</table>
				</div>
				<!-- 分页 -->
				<c:if test="${merfunsetconfirm.totalPages != null}">
					<div class="next">
						<c:if test="${merfunsetconfirm.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo > 1}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo-2 > 0}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo-2 })"><span>${merfunsetconfirm.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo-1 > 0}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo-1 })"><span>${merfunsetconfirm.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${merfunsetconfirm.pageNo }</span></a>
						<c:if test="${merfunsetconfirm.pageNo+1 <= merfunsetconfirm.totalPages}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo+1 })"><span>${merfunsetconfirm.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo+2 <= merfunsetconfirm.totalPages}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo+2 })"><span>${merfunsetconfirm.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo+3 <= merfunsetconfirm.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo < merfunsetconfirm.totalPages}">
							<a href="javascript:paging(${merfunsetconfirm.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${merfunsetconfirm.pageNo > 1}">
							<a href="javascript:paging(${merfunsetconfirm.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${merfunsetconfirm.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${merfunsetconfirm.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
			
			<!-- 结算制表确认操作及同步数据至FTP操作 -->
			<div>
	  		 	<ul>
		   			<li class="cb mt0">
						<input type="button" class="icon_normal" value="结算确认" onclick="updatemerinfo()"/>
						<!-- <input type="button" class="icon_normal" value="同步数据至FTP" onclick="uploadDataToFtp()" id="uploadDataButton"/> -->
					</li>
				</ul> 
	   		</div>
			
			<!-- 详情弹出层 -->
			<div id="detailed" class="pop" style="display: none">
				<div class="pop_body" style="margin-top: 50px; font-size: 11px;">
					<h1 class="pop_tit">
			    		<span class="fl">详情</span>
			    		<a class="close" href="javascript:void(0);" onclick="hide('detailed');">&nbsp;</a>
			   		</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0" id="operator">
							<tr align="center">
							   	<td bgcolor="#eeeeee">商户号：</td>
							    <td id="merCodes"/>
							    <td bgcolor="#eeeeee">商户简称：</td>
							    <td id="merNames"/>
							    <td bgcolor="#eeeeee">应结算金额：</td>
							    <td id="settleAmounts"/>
							</tr>
					        <tr align="center">
							    <td bgcolor="#eeeeee">结算初始日期：</td>
							    <td id="startDates"/>
							    <td bgcolor="#eeeeee">结算截止日期：</td>
							    <td id="endDates"/>
							    <td bgcolor="#eeeeee">结算方式：</td>
							    <td id="payments"/>
							</tr>
							<tr align="center">
							    <td bgcolor="#eeeeee">结算账户类型：</td>
							    <td id="settlementAccountTypes"/>
							    <td bgcolor="#eeeeee">商户批次号：</td>
							    <td id="merBatchNos"/>
							    <td bgcolor="#eeeeee">系统批次号：</td>
							    <td id="sysBatchNos"/>
							</tr>
						</table>
						<div>
							<input type="hidden" id="mer_code_hide" />
							<input type="hidden" id="start_date_hide"/>
							<input type="hidden" id="end_dates_hide"/>
							<input type="hidden" id="mer_name_hide"/>
						</div>
						<div id="selectSettlementDetailed"></div>
					</div>
				</div>
			</div>
   		</div>
  	</div>
</body>
</html>
