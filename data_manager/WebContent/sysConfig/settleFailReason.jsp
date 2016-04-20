<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算发起原因配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>结算发起原因配置查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/querySettleFailReason.do" target="right" name="settleFailReasonSearch" method="post">
					<table align="center" style="width: 80%;">
						<tr>
							<td>原因ID</td>
							<td>
								<span class="input_bgl">
									<input maxlength="15" type="text" name="reasonId" id="reasonId" value="${param.reasonId }" />
								</span>
							</td>
							<td>失败原因</td>
							<td>
								<span class="input_bgl">
									<input maxlength="15" type="text" name="reasonName" id="reasonName" value="${param.reasonName }" />
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center;">
								<br />
								<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
								<input type="button" class="icon_normal" value="添加" onclick="addSettleFailReason()"/>
							</td>
						</tr>
					</table>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">发起失败原因ID</td>
							<td align="center">发起失败原因</td>
							<td align="center" colspan="2">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageSettleFailReason.result)<=0 }">
						<tr align="center">
							<td colspan="3">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageSettleFailReason.result }" var="data">
						<tr>
							<td align="center">${data.reason_id }</td>
							<td align="center">${data.reason }</td>
							<td align="center">
								<a href="javascript:deleteData(${data.reason_id })">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${data.reason_id }','${data.reason}')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageSettleFailReason.totalPages != null}">
				<div class="next">
					<c:if test="${pageSettleFailReason.pageNo > 1}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo-2 > 0}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo-2 })"><span>${pageSettleFailReason.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo-1 > 0}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo-1 })"><span>${pageSettleFailReason.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageSettleFailReason.pageNo }</span></a>
					<c:if test="${pageSettleFailReason.pageNo+1 <= pageSettleFailReason.totalPages}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo+1 })"><span>${pageSettleFailReason.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo+2 <= pageSettleFailReason.totalPages}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo+2 })"><span>${pageSettleFailReason.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo+3 <= pageSettleFailReason.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageSettleFailReason.pageNo < pageSettleFailReason.totalPages}">
						<a href="javascript:paging(${pageSettleFailReason.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageSettleFailReason.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageSettleFailReason.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>

	<!--===========================弹出内容============================-->
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">结算发起失败原因配置信息修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="reason_id_u" name="reason_id_u" value="" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">结算发起失败原因：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" id="reason_u" name="reason_u" value=""/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">结算发起失败原因配置信息添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="trade_amount_conf_info">	
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">结算发起失败原因：</td>
						<td>
						   <span class="input_bgl"> 
								<input type="text" id="reason_a" name="reason_a" value=""/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addData()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
	function init(){
		$.ajax({
    		url : '<%=request.getContextPath()%>/getSettleFailReason.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#inst_name").append('<option value="' + msg[i]['reason_id'] + '">'+ msg[i]['reason'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取银行列表失败!");
    		}
    	});
		var reason_id = $("#reason_id_hidden").val();
		var type = document.getElementById("reason");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == reason_id){
				type.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
		
		function paging(pageNo) {
			var form = document.getElementById("settleFailReasonSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/querySettleFailReason.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){		
			document.settleFailReasonSearch.submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function clearEndTime(obj){
			$(obj).val("");
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
		function selectData(reason_id,reason){
			$("#reason_id_u").val(reason_id);
			$("#reason_u").val(reason);
			$("#update").css({display:"block"});
		}
		function updateData(){
			var reason_id = $("#reason_id_u").val();
			var reason = $("#reason_u").val();
			if(reason == null || reason == ''){
				alert("请填写失败原因");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateSettleFailReason.do",
				data : "reason_id="+reason_id+"&reason="+reason,
				dataType : "text",
				success : function(msg) {
					if(msg >= 0){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function addSettleFailReason(){
			$("#insert").css({display:"block"});
		}
		function addData(){
			var reason = $("#reason_a").val();
			if(reason == null || reason == ''){
				alert("请填写失败原因");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addSettleFailReason.do",
				data : "reason="+reason,
				dataType : "text",
				success : function(msg) {
					if(msg == 1){
						alert("添加成功");
						checkQuery();
					}else{
						alert("添加失败");
						hide("insert");
					}
				}
			});
		}
		function deleteData(reason_id){
			if(confirm("确定要删除"+reason_id+"结算发起失败原因配置信息吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteSettleFailReason.do",
					data : "reason_id="+ reason_id,
					dataType : "text",
					success : function(msg) {
						if(msg == 1){
							alert("删除成功");
							checkQuery();
						}else{
							alert("删除失败");
						}
					}
				});
			}
		}
	</script>
</body>
</html>
