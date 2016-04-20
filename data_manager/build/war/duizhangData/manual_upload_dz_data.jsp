<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手动导入对账单</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
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

function paging(pageNo) {
	var form = document.getElementById("manualSummaryForm");
	var pageSize = $("#pageSize").val();
	var bank_id = $("#bankId").val();
	if (bank_id == null || bank_id == "") {
		alert("请选择银行机构！");
		return;
	}
	var summaryDate = $("#summaryDate").val();
	if(summaryDate == "" || summaryDate==null){
		alert("请选择交易日期");
		return ;
	}
	with (form) {
		action = "<%=request.getContextPath()%>/queryBankErrorData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
		method = "post";
		form.submit();
	}
}

function queryByPageSize(pageSize) {
	var form = document.getElementById("manualSummaryForm");
	var bank_id = $("#bankId").val();
	if (bank_id == null || bank_id == "") {
		alert("请选择银行机构！");
		return;
	}
	var summaryDate = $("#summaryDate").val();
	if(summaryDate == "" || summaryDate==null){
		alert("请选择交易日期");
		return ;
	}
	with (form) {
		action = "<%=request.getContextPath()%>/queryBankErrorData.do?pageSize=" + pageSize;
		method = "post";
		form.submit();
	}
}

		function manualUploadDz(){
			var bank_id = $("#bankId").val();
			if(bank_id == "" || bank_id == null){
				alert("请选择银行网关");
				return ;
			}
			var summaryDate = $("#summaryDate").val();
			if(summaryDate == "" || summaryDate==null){
				alert("请选择交易日期");
				return ;
			}
			
			
			document.getElementById("loading-mask").style.display="";
	        document.getElementById("loading").style.display="";
			
			var form = document.getElementById("manualSummaryForm");
			form.action = "<%=request.getContextPath()%>/manualUploadDzData.do";
			document.getElementById("loading-mask").style.display="";
	        document.getElementById("loading").style.display="";
			form.submit();
		}
		function init(){
			document.getElementById("loading-mask").style.display="none";
	        document.getElementById("loading").style.display="none";
			// 初始化银行机构选择下拉框
		 	$.ajax({
		      url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
		      type : 'post',
		      async : false,
		      success : function(msg) {
		       for (i in msg)
		        $("#bankId").append('<option value="' + msg[i]['bank_id'] +'">'+ msg[i]['bank_name'] + '</option>');
		      }
		    });
			var bank_id = $("#bank_id_hidden").val();
			var bank_ids = document.getElementById("bankId");
			
			for(var i = 0;i<bank_ids.options.length;i++){
				if(bank_ids.options[i].value == bank_id){
					bank_ids.options[i].selected = 'selected';
				}
			}
			if(bank_id == "" || bank_id == null){
				$("#inst_name").empty();
				$("#inst_name").append('<option value="">--请选择--</option>');
			}else{
				changeInst(bank_id);
			}
			
			
			var inst_id = $("#inst_id_hidden").val();
			var inst_id_type = document.getElementById("inst_name");
			
			for(var i = 0;i<inst_id_type.options.length;i++){
				if(inst_id_type.options[i].value == inst_id){
					inst_id_type.options[i].selected = 'selected';
				}
			}
			var summaryDate = $("#summaryDate_hidden").val();
			if(summaryDate != null){
				$("#summaryDate").html(summaryDate);
			}
			
			var resultFlag = $("#resultFlag").val();
			if(resultFlag != "" && resultFlag != null){
				if(resultFlag == -1){
					alert("参数原因,上传并对账操作失败");
				}else if(resultFlag == 0){
					alert("上传成功，解析失败");
				}else if(resultFlag == 1){
					alert("上传文件失败,系统错误");
				}else if(resultFlag == 2){
					alert("上传文件失败,文件格式不匹配");
				}else if(resultFlag == 3){
					alert("解析文件失败");
				}else if(resultFlag == 4){
					alert("对账操作失败");
				}else if(resultFlag == 5){
					alert("对账操作成功");
					$("#showBankErrorData").css({display:"block"});
				}else if(resultFlag == 6){
					alert("对账单内容为空,请重新上传");
				}else if(resultFlag == -2){
					$("#showBankErrorData").css({display:"block"});
				}
			}
		}
		
		function changeInst(bank_id){
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryOutDzInstInfoByBankId.do',
	    		type : 'post',
	    		data : "bank_id="+ bank_id,
	    		async : false,
	    		success : function(msg) {
	    			if(msg.length != null && msg.length != ""){
	    				$("#inst_name").empty();
	    				$("#inst_name").append('<option value="">--请选择--</option>');
	    				for (i in msg){
	 	    				$("#inst_name").append('<option value="' + msg[i]['instId'] + "," + msg[i]['inst_type'] + '">'+ msg[i]['name'] + '</option>');
		    			}
	    			}
	    		}
	    	});
		}
		
		function downLoadBankErrorDataOfTxt(){
			var bank_id = $("#bankId").val();
			if(bank_id == "" || bank_id == null){
				alert("请选择银行网关");
				return ;
			}
			
			var inst_name = $("#inst_name").val();
			
			var summaryDate = $("#summaryDate").val();
			
			if(summaryDate == "" || summaryDate==null){
				alert("请选择交易日期");
				return ;
			}
			
			var url ="<%=request.getContextPath()%>/downLoadBankErrorDataOfTxt.do?bank_id="+bank_id+"&summaryDate="+summaryDate+"&inst_name="+inst_name;
			window.location=url;
		}
		
		function downLoadBankErrorDataOfExcel(){
			var bank_id = $("#bankId").val();
			if(bank_id == "" || bank_id == null){
				alert("请选择银行网关");
				return ;
			}
			
			var inst_name = $("#inst_name").val();
			
			var summaryDate = $("#summaryDate").val();
			
			if(summaryDate == "" || summaryDate==null){
				alert("请选择交易日期");
				return ;
			}
			
			var url ="<%=request.getContextPath()%>/downLoadBankErrorDataOfExcel.do?bank_id="+bank_id+"&summaryDate="+summaryDate+"&inst_name="+inst_name;
			window.location=url;
		}
		
	</script>
</head>

<body onload="init();">
<div id='loading-mask' style="display: none;"></div>
<div id="loading" style="display: none;">
    <div class="loading-indicator">
       <img src="<%=request.getContextPath()%>/images/wait-1.gif" width="60" height="60" style="margin-right:8px;float:left;vertical-align:top;"/>
       <br/><span id="loading-msg"></span>
    </div>
</div>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>手动导入对账单</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">手动对账</h1>
				<form action="<%=request.getContextPath()%>/manualUploadDzData.do" target="right" name="manualSummaryForm" id = "manualSummaryForm" method="post" enctype="multipart/form-data">
					<table align="center" style="width: 90%;">
						<tr>						
							<td>银行网关</td>
							<td>
								<span class="input_bgl">
									<select id="bankId" name="bankId" onchange="changeInst(this.value);">
										<option value="">--请选择--</option>
									</select>
									<input type="hidden" id="bank_id_hidden" value="${bank_id }"/>
								</span>
								<font color="red">*</font>
							</td>
							<td>扣款渠道</td>
							<td>
								<span class="input_bgl">
									<select id="inst_name" name="inst_name">
										<option value="">--请选择--</option>
									</select>
								</span>
								<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
							</td>
							<td>交易日期</td>
							<td>
								<span class="input_bgl">
									<input maxlength="20" name="summaryDate" id="summaryDate" value="${param.summaryDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
									<input type="hidden" id="summaryDate_hidden" value="${summaryDate }" />
								</span>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>上传对账文件</td>
							<td>
								<span class="input_bgl">
									<input type="file" value="" id="file" name="file" />
								</span>
							</td>
							<td colspan="4">
								<input type="button" class="icon_normal" value="上传并对账" onclick="manualUploadDz();"/>
								<input type="hidden" value="${result }" id="resultFlag" />
							</td>
						</tr>
					</table>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
		</div>
	</div>
<div style="display:none" id="showBankErrorData">
	<div>
		<span>
		本次渠道对账单有效交易总计
		<font color="red">
			${totalBankData}
		</font>
		笔，对账成功：
		</span>
		<span>
		<font color="red">
			${sucessBankData}
		</font>
		笔，对账差错：
		</span>
		<span>
		<font color="red">
			${errorBankData}
		</font>
		笔
		</span>
	</div>
	<div style="font-size: 12px;">
		<span>
		本页共
		<font color="red">
			<c:if test="${fn:length(pageData.result) <= 0 }">0</c:if>
			<c:if test="${fn:length(pageData.result) > 0 }">${fn:length(pageData.result) }</c:if>
		</font>
		条数据
		</span>
		<span style="float: right;">共<font color="red">
			<c:if test="${pageData.totalItems == null }">0</c:if>
			<c:if test="${pageData.totalItems != null }">${pageData.totalItems }</c:if>
		</font>条数据
		<font color="red">
			<c:if test="${pageData.totalPage == null}">0</c:if>
			<c:if test="${pageData.totalPage != null}">${pageData.totalPage}</c:if>
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
						<td align="center">银行记录的订单号/流水号</td>
						<td align="center">银行交易金额</td>
						<td align="center">来源</td>
					</tr>
				</thead>
				<c:if test="${fn:length(pageData.result)<=0 }">
					<tr align="center">
						<td colspan="3">对不起,暂无数据！</td>
					</tr>
				</c:if>
				<c:forEach items="${pageData.result }" var="data">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
						<td align="center">${data.additional_data} / ${data.req_sys_stance }</td>
						<td align="center">
							<f:formatNumber value="${data.trade_amount }" pattern="0.00"></f:formatNumber>
						</td>
						<td align="center">${data.nii }</td>
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
		
		<c:if test="${fn:length(pageData.result)>0 }">
			<div align="center">
				<input type="button" class="icon_normal" value="下载txt文件" onclick="downLoadBankErrorDataOfTxt();"/>
				<input type="button" class="icon_normal" value="下载excel文件" onclick="downLoadBankErrorDataOfExcel();"/>
			</div>
		</c:if>
</div>
</body>
</html>
