<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易金额配置查询</title>
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
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>交易金额配置查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageTradeAmountConf.do" target="right" name="TradeAmountConfSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>交易消息类型</b>
							<span class="input_bgl">
								<select name="name" id="name" >
									<option value="">---请选择---</option>
									<c:forEach items="${list }" var="trade_amount_conf">
										<option value="${trade_amount_conf.id }">${trade_amount_conf.name }</option>
									</c:forEach>
								</select>
							</span>
							<input type="hidden" id="name_h" name="name_h" value="${paramsMap.id }" />
						</li>
						<li>
							<b>交易金额状态</b>
							<span class="input_bgl">
								<select id="tradeMoneyStatus" name="tradeMoneyStatus">
									<option value="">---请选择---</option>
									<option value="0">&nbsp;正&nbsp;</option>
									<option value="1">&nbsp;负&nbsp;</option>
								</select> 
							</span>
							<input type="hidden" id="tradeMoneyStatus_h" name="tradeMoneyStatus_h" value="${paramsMap.tradeMoneyStatus }" />
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
							<input type="button" class="icon_normal" value="添加" onclick="addTradeAmountConf();"/>
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
							<td align="center">交易金额配置ID</td>
							<td align="center">交易处理码</td>
							<td align="center">交易消息类型</td>
							<td align="center">交易名称</td>				
							<td align="center">交易金额状态</td>
							<td align="center" colspan="2">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(tradeAmountConfPage.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${tradeAmountConfPage.result }" var="data">
						<tr>
							<td align="center">${data.id }</td>
							<td align="center">${data.process }</td>
							<td align="center">${data.trademsgType }</td>
							<td align="center">${data.name }</td>
							<td align="center">
								<c:if test="${data.tradeMoneyStatus==0 }">正</c:if>
								<c:if test="${data.tradeMoneyStatus==1 }">负</c:if>
							</td>
							<td align="center">
								<a href="javascript:deleteData(${data.id })">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${data.id }','${data.name}','${data.tradeMoneyStatus}','${data.process }','${data.trademsgType }')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${tradeAmountConfPage.totalPages != null}">
				<div class="next">
					<c:if test="${tradeAmountConfPage.pageNo > 1}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo-2 > 0}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo-2 })"><span>${tradeAmountConfPage.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo-1 > 0}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo-1 })"><span>${tradeAmountConfPage.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${tradeAmountConfPage.pageNo }</span></a>
					<c:if test="${tradeAmountConfPage.pageNo+1 <= tradeAmountConfPage.totalPages}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo+1 })"><span>${tradeAmountConfPage.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo+2 <= tradeAmountConfPage.totalPages}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo+2 })"><span>${tradeAmountConfPage.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo+3 <= tradeAmountConfPage.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${tradeAmountConfPage.pageNo < tradeAmountConfPage.totalPages}">
						<a href="javascript:paging(${tradeAmountConfPage.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${tradeAmountConfPage.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${tradeAmountConfPage.pageNo }" />页
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
				<span class="fl">交易金额配置信息修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="id_u" name="id_u" value="" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">交易名称：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" id="name_u" name="name_u" value=""/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">交易处理码：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" maxlength="6" id="process_u"  name="process_u" value=""
								 onkeyup="value=value.replace(/[^\d]/g,'')"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">交易消费类型：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" id="trademsgType_u" maxlength="2" name="trademsgType_u" value="" 
								 onkeyup="value=value.replace(/[^\d]/g,'')"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易金额状态：</td>
						<td>
							<span class="input_bgl"> 
								<select id="tradeMoneyStatus_u" name="tradeMoneyStatus_u">
									<option value="1">&nbsp;负&nbsp;</option>
									<option value="0">&nbsp;正&nbsp;</option>
								</select> 
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
				<span class="fl">交易金额配置信息添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="trade_amount_conf_info">	
					<!-- <tr>
						<td width="120" align="right" bgcolor="#eeeeee">交易金额配置ID：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="id" name="id" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>	 -->			
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">交易名称：</td>
						<td>
						   <span class="input_bgl"> 
								<input type="text" id="name_a" name="name_a" value=""/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
								<td width="120" align="right" bgcolor="#eeeeee">交易处理码：</td>
								<td>
									<span class="input_bgl"> 
										 <input type="text" maxlength="6" id="process_a" name="process_a" value=""
										 	onkeyup="value=value.replace(/[^\d]/g,'')"/>
									</span>
									<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
								</td>
							</tr>
							<tr>
								<td width="120" align="right" bgcolor="#eeeeee">交易消费类型：</td>
								<td>
									<span class="input_bgl"> 
										 <input type="text" maxlength="3" id="trademsgType_a" name="trademsgType_a" value="" 
										 onkeyup="value=value.replace(/[^\d]/g,'')"/>
									</span>
									<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
								</td>
							</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易金额状态：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="tradeMoneyStatus_a" name="tradeMoneyStatus_a">
									<option value="1">&nbsp;负&nbsp;</option>
									<option value="0">&nbsp;正&nbsp;</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addTradeAmountConfSub()" />
							<input type="button" class="icon_normal" value="取消" onclick="checkQuery()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementsByName("TradeAmountConfSearch")[0];
			with (form) {
				action = "<%=request.getContextPath()%>/queryPageTradeAmountConf.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){		
			document.TradeAmountConfSearch.submit();
		}
		function addTradeAmountConf() {	
			$("#insert").css({display:"block"});
		}
		
		function addTradeAmountConfSub(){
			var name = $("#name_a").val();
			if(name == null || name == ""){
				alert("交易名称 不能为空！");
				return;
			}
			var tradeMoneyStatus = $("#tradeMoneyStatus_a").val();
			
			var process = $("#process_a").val();
			if(process == null || process == ""){
				alert("交易处理码 不能为空！");
				return;
			}
			var trademsgType=$("#trademsgType_a").val();
			if(trademsgType == null || trademsgType == ""){
				alert("交易消息类型不能为空！");
				return;
			}
			/* if(name == "消费"){
				process = "910000";
				trademsgType = 2;
			}
			if(name == "消费撤销"){
				process = "200000";
				trademsgType = 18;
			}
			if(name == "消费退货"){
				process = "270000";
				trademsgType = 20;
			}
			if(name == "消费冲正"){
				process = "000000";
				trademsgType = 26;
			}
			if(name == "消费撤销冲正"){
				process = "200000";
				trademsgType = 28;
			}
			if(name == "信用卡还款"){
				process = "190000";
				trademsgType = 12;
			}
			if(name == "转账汇款"){
				process = "480000";
				trademsgType = 2;
			}
			if(name == "预授权完成"){
				process = "510000";
				trademsgType = 56;
			}
			if(name == "预授权完成冲正"){
				process = "510000";
				trademsgType = 80;
			}
			if(name == "预授权完成撤销"){
				process = "570000";
				trademsgType = 58;
			}
			if(name == "预授权完成撤销冲正"){
				process = "570000";
				trademsgType = 82;
			} */
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addTradeAmountConf.do",
				data : "&name=" + name + "&tradeMoneyStatus=" + tradeMoneyStatus+ "&process=" + process+ "&trademsgType=" + trademsgType,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						/* paging($("#pageNo").val()); */
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		function selectData(id,name,tradeMoneyStatus,process,trademsgType){
			$("#id_u").val(id);
			$("#name_u").val(name);
			$("#tradeMoneyStatus_u").val(tradeMoneyStatus);
			$("#process_u").val(process);
			$("#trademsgType_u").val(trademsgType);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var id_u = $("#id_u").val();
			var tradeMoneyStatus_u = $("#tradeMoneyStatus_u").val();
			var process_u = $("#process_u").val();
			var trademsgType_u = $("#trademsgType_u").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateTradeAmountConf.do",
				data : "id="+id_u+"&tradeMoneyStatus="+tradeMoneyStatus_u+"&process="+process_u+"&trademsgType="+trademsgType_u,
				dataType : "text",
				success : function(msg) {
					if(Boolean(msg)){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function deleteData(id){
			if(confirm("确定要删除"+id+"机构信息数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteTradeAmountConf.do",
					data : "id="+ id,
					dataType : "text",
					success : function(msg) {
						if(Boolean(msg)){
							alert("删除成功");
							checkQuery();
						}else{
							alert("删除失败");
						}
					}
				});
			}
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		function init(){
			var name = $("#name_h").val();
			var type = document.getElementById("name");
			
			var tradeMoneyStatus_t = $("#tradeMoneyStatus_h").val();
			var typt = document.getElementById("tradeMoneyStatus");
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == name){
					type.options[i].selected = 'selected';
				}
			}
			for(var j = 0;j<typt.options.length;j++){
				if(typt.options[j].value == tradeMoneyStatus_t){
					typt.options[j].selected = 'selected';
				}
			}
		}
	</script>
</body>
</html>
