<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>扣款渠道Mcc计算</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("channelMccCalculateSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryChannelMccCalculatePage.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			document.getElementById("channelMccCalculateSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addHandling() {	
			$("#insert").css({display:"block"});
		}
		
		function addHandlingSub(){
			var inst_id = $("#inst_id_insert").val();
			var issuer = $("#issuer_insert").val();
			var billToParty = $("#billToParty_insert").val();
			var unionpay = $("#unionpay_insert").val();
			if(issuer == null || issuer == ""){
				alert("发卡方不能为空！");
				return;
			}
			if(billToParty == null || billToParty == ""){
				alert("收单行不能为空！");
				return;
			}
			if(unionpay == null || unionpay == ""){
				alert("银联不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addChannelMccCalculate.do",
				data : "inst_id="+inst_id+"&issuer="+issuer+"&billToParty="+billToParty+"&unionpay="+unionpay,
				dataType : "text",
				success : function(msg) {
					if (msg == '1') {
						alert("添加成功！");
						paging($("#pageNo").val());
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		function selectData(inst_id,issuer,billToParty,unionpay){
			$("#inst_id_update").val(inst_id);
			$("#issuer_update").val(issuer);
			$("#billToParty_update").val(billToParty);
			$("#unionpay_update").val(unionpay);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var inst_id = $("#inst_id_update").val();
			var issuer = $("#issuer_update").val();
			var billToParty = $("#billToParty_update").val();
			var unionpay = $("#unionpay_update").val();
			if(issuer == null || issuer == ""){
				alert("发卡方不能为空！");
				return;
			}
			if(billToParty == null || billToParty == ""){
				alert("收单行不能为空！");
				return;
			}
			if(unionpay == null || unionpay == ""){
				alert("银联不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateChannelMccCalculate.do",
				data : "inst_id="+inst_id+"&issuer="+issuer+"&billToParty="+billToParty+"&unionpay="+unionpay,
				dataType : "text",
				success : function(msg) {
					if(msg == '1'){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function deleteData(inst_id){
			if(confirm("确定要删除渠道ID"+inst_id+"的扣率配置数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteChannelMccCalculate.do",
					data : "inst_id="+ inst_id,
					dataType : "text",
					success : function(msg) {
						if(msg == '1'){
							alert("删除成功");
							checkQuery();
						}else{
							alert("删除失败");
						}
					}
				});
			}
		}
		function checkInstInfo(){
			var instId = $("#inst_id_insert").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/checkInstInfoOfMccCalculate.do",
				data : "instId="+ instId,
				dataType : "text",
				success : function(msg) {
					if(msg == '0'){
						alert("该渠道信息已经存在，请去修改或者增加其他渠道的MCC计算信息配置！");
						checkQuery();
					}
				}
			});
		}
	</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>Mcc类型配置查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryChannelMccCalculatePage.do" target="right" name="channelMccCalculateSearch" id = "channelMccCalculateSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>渠道ID</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="inst_id_" id = "inst_id_" value="${param.inst_id_ }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addHandling();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center" width="20%">渠道ID</td>
							<td align="center" width="20%">发卡方</td>
							<td align="center" width="20%">收单行</td>
							<td align="center" width="20%">银联</td>
							<td align="center" width="20%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="channelMccCalculate">
						<tr>
							<td align="center">${channelMccCalculate.inst_id }</td>
							<td align="center">${channelMccCalculate.issuer }</td>
							<td align="center">${channelMccCalculate.billToParty }</td>
							<td align="center">${channelMccCalculate.unionpay }</td>
							<td align="center" width="60">
								<a href="javascript:deleteData(${channelMccCalculate.inst_id})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData(${channelMccCalculate.inst_id},'${channelMccCalculate.issuer }','${channelMccCalculate.billToParty }','${channelMccCalculate.unionpay }')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDataLst.totalPages != null}">
				<div class="next">
					<c:if test="${pageDataLst.pageNo > 1}">
						<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataLst.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDataLst.pageNo-2 })"><span>${pageDataLst.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>${pageDataLst.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDataLst.pageNo }</span></a>
					<c:if test="${pageDataLst.pageNo+1 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>${pageDataLst.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo+2 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+2 })"><span>${pageDataLst.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo+3 <= pageDataLst.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataLst.pageNo < pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDataLst.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageDataLst.pageNo }" />页
							<input type="hidden" value="${pageDataLst.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">扣款渠道MCC计算信息配置添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">					
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道ID：</td>
						<td>
							<span class="input_bgl" style="float: inherit;"> 
								<select id="inst_id_insert" name="inst_id_insert" onchange="checkInstInfo();">
									<c:forEach items="${instInfoList }" var="instInfo">
										<option value="${instInfo.instId }">${instInfo.name }</option>
									</c:forEach>
								</select> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发卡方：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="issuer_insert" name="issuer_insert"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">收单行：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="billToParty_insert" name="billToParty_insert"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银联：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="unionpay_insert" name="unionpay_insert"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addHandlingSub()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--差错处理方式字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">扣款渠道MCC计算信息配置修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">渠道ID：</td>
						<td>
							<input type="text" id="inst_id_update" name="inst_id_update" readonly="readonly"  value="" /> 
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">发卡方：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="issuer_update" name="issuer_update"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">收单行：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="billToParty_update" name="billToParty_update"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">银联：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="unionpay_update" name="unionpay_update"  value="" /> 
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

</body>
</html>
