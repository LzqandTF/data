<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手工调账审核</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("search");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryNeedApprovalDataLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("search");
		with (form) {
			action = "<%=request.getContextPath()%>/queryNeedApprovalDataLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var form = document.getElementById("search");
		with (form) {
			action = "<%=request.getContextPath()%>/queryNeedApprovalDataLst.do?";
			method = "post";
			form.submit();
		}
	}
	
	function getSelected() {
		var addorsub_ = $("#addorsub_hidden").val();
		var addorsub = document.getElementById("addorsub");
		for (var i = 0; i < addorsub.options.length; i++) {
			if (addorsub.options[i].value == addorsub_) {
				addorsub.options[i].selected = 'selected';
			}
		}
		
		var data_status = $("#data_status_hidden").val();
		var dataStatus = document.getElementById("data_status");
		for (var i = 0; i < dataStatus.options.length; i++) {
			if (dataStatus.options[i].value == data_status) {
				dataStatus.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	//子复选框的事件 
	function setSelectAll(){
	    var chsub = $("input[type='checkbox'][name='subcheck']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll").attr("checked", true);  
	    } else {
	    	$("#SelectAll").attr("checked", false);
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
	
	//手工调账审核  审核成功
	function updateDataById(auditor_user_name) {
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要审核成功的数据！");
  	   		return ;
  	 	}
		if(!confirm("是否确认审核成功已选择的数据？")){
			return;
		}
		var manualRecInfo = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			manualRecInfo[i] = $(this).val();
			i++;
		});
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateDataById.do",
			data : "manualRecInfo=" + manualRecInfo+"&auditor_user_name="+auditor_user_name,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功！");
					checkQuery();
				} else {
					alert("操作失败！");
				}
			}
		});
	}
	
	
	//手工调账审核  审核失败
	function updateDataToFail(auditor_user_name) {
		var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
  	  	if(checkedSub == 0){
  	   		alert("请选择需要审核失败的数据！");
  	   		return ;
  	 	}
		if(!confirm("是否确认审核失败已选择的数据？")){
			return;
		}
		var manualRecInfo = new Array();
		var i = 0 ;
		$("input[type='checkbox'][name='subcheck']:checked").each(function(){
			manualRecInfo[i] = $(this).val();
			i++;
		});
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/approvalDataToFail.do",
			data : "manualRecInfo=" + manualRecInfo+"&auditor_user_name="+auditor_user_name,
			dataType : "text",
			async:false,
			success : function(msg) {
				if (msg > 0) {
					alert("操作成功！");
					checkQuery();
				} else {
					alert("操作失败！");
				}
			}
		});
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
	function clearEndTime(){
		$("#endTime").val("");
	}
</script>
</head>

<body onload="getSelected();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>手工调账审核</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryNeedApprovalDataLst.do" target="right" name="search" id="search" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
						<table width="90%" border="0" cellspacing="0">
							<tr>
								<td align="right" nowrap="nowrap">商户号</td>
				                <td nowrap="nowrap">
				                    <span class="input_bgl">
				                    	<input type="text" id="mer_code" name="mer_code" value="${param.mer_code }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
				                    </span>
				                </td>
				                <td align="right" nowrap="nowrap">调账类型</td>
				                <td nowrap="nowrap">
				                    <span class="input_bgl">
				                     	<select name="addorsub" id="addorsub" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="1">手工增加</option>
				                     		<option value="2">手工减少</option>
				                     	</select>
				                     	<input type="hidden" id="addorsub_hidden" value="${addorsub }" />
				                     </span>
				                </td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">调账请求日期</td>
								<td nowrap="nowrap">
									<span style="width: 160px;" class="input_bgl">
										<input style="width: 80px" id="startTime" name="startTime" value="${param.startTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										- 
										<input style="width: 80px" id="endTime" name="endTime" value="${param.endTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>	
								</td>
								<td align="right" nowrap="nowrap">调账状态</td>
				                <td nowrap="nowrap">
				                    <span class="input_bgl">
				                     	<select name="data_status" id="data_status" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="1">调账提交</option>
				                     		<option value="2">审核成功</option>
				                     		<option value="3">审核失败</option>
				                     	</select>
				                     	<input type="hidden" id="data_status_hidden" value="${data_status }" />
				                     </span>
				                </td>
							</tr>
							<tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
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
					<c:if test="${fn:length(pageDataLst.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageDataLst.result) > 0 }">${fn:length(pageDataLst.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageDataLst.totalItems == null }">0</c:if>
					<c:if test="${pageDataLst.totalItems != null }">${pageDataLst.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageDataLst.totalPages == null}">0</c:if>
					<c:if test="${pageDataLst.totalPages != null}">${pageDataLst.totalPages}</c:if>
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
			
			<div class="table-m" >
				<div>
					<table width="100%" border="0" cellspacing="0">
						<thead>
							<tr>
								<td width="5%" align="center">
									<input type="checkbox" id="SelectAll" onclick="selectAll();"/>全选
								</td>
								<td align="center">商户号</td>
								<td align="center">商户简称</td>
								<td align="center">调账请求操作员ID</td>
								<td align="center">调账请求时间</td>
								<td align="center">调账金额（元）</td>
								<td align="center">调账类型</td>
								<td align="center">调账状态</td>
								<td align="center">调账审核操作员ID</td>
								<td align="center">调账审核时间</td>
								<td align="center">调账原因</td>
							</tr>
						</thead>
						<c:if test="${fn:length(pageDataLst.result)<=0 }">
							<tr align="center">
								<td colspan="10">对不起,暂无数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${pageDataLst.result }" var="manualRec">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<c:if test="${manualRec.data_status == 1}">
									<td align="center">
										<input type="checkbox" name="subcheck" id="subcheck" onclick="setSelectAll();" value="${manualRec.id},${manualRec.mer_code},${manualRec.rec_amount},${manualRec.addorsub}"/>
									</td>
								</c:if>
								<c:if test="${manualRec.data_status == 2 || manualRec.data_status == 3}">
									<td align="center"></td>
								</c:if>
								<td align="center">${manualRec.mer_code}</td>
								<td align="center">${manualRec.mer_abbreviation}</td>
								<td align="center">${manualRec.send_user_name}</td>
								<td align="center">
									${fn:substring(manualRec.handler_time,0,19)}
								</td>
								<td align="center">
									<f:formatNumber value="${manualRec.rec_amount}" pattern="0.00"></f:formatNumber>
								</td>
								<td align="center">
									<c:if test="${manualRec.addorsub == 1}">手工增加</c:if>
									<c:if test="${manualRec.addorsub == 2}">手工减少</c:if>
								</td>
								<td align="center">
									<c:if test="${manualRec.data_status == 1}">调账提交</c:if>
									<c:if test="${manualRec.data_status == 2}">审核成功</c:if>
									<c:if test="${manualRec.data_status == 3}">审核失败</c:if>
								</td>
								<td align="center">${manualRec.auditor_user_name}</td>
								<td align="center">
									${fn:substring(manualRec.audit_time,0,19)}
								</td>
								<td align="center">${manualRec.request_desc}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- 分页 -->
				<c:if test="${pageDataLst.totalPages != null}">
					<div class="next">
						<input style="float: left" type="button" class="icon_normal" value="审核成功" id="" onclick="updateDataById('${sessionScope.login.loginName}')"/>
						<input style="float: left" type="button" class="icon_normal" value="审核失败" id="" onclick="updateDataToFail('${sessionScope.login.loginName}')"/>
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageDataLst.pageNo-2 > 0}">
							<a href="javascript:paging(${pageDataLst.pageNo-2 })"><span>${pageDataLst.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo-1 > 0}">
							<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>${pageDataLst.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${pageDataLst.pageNo }</span></a>
						<c:if test="${pageDataLst.pageNo+1 <= pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>${pageDataLst.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo+2 <= pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+2 })"><span>${pageDataLst.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo+3 <= pageDataLst.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${pageDataLst.pageNo < pageDataLst.totalPages}">
							<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${pageDataLst.pageNo > 1}">
							<a href="javascript:paging(${pageDataLst.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${pageDataLst.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${pageDataLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
