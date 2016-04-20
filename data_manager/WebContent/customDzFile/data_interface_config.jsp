<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口数据配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
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
		function init(){
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryCustomObjectListOfInterfaceTypeNotAll.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg)
	    				$("#custom_object").append('<option value="' + msg[i]['object_id'] + ';' + msg[i]['generate_number'] + '">'+ msg[i]['object_name'] + '</option>');
	    		}
	    	});
			
			var object_id = $("#custom_object_id").val();
			var custom_objects = document.getElementById("custom_object");
			
			for(var i = 0;i<custom_objects.options.length;i++){
				if(custom_objects.options[i].value == object_id){
					custom_objects.options[i].selected = 'selected';
				}
			}
			
			var sucessNum =  $("#sucessNum").val();
			var repeatNum =  $("#repeatNum").val();
			var errorNum =  $("#errorNum").val();
			var wrongNum =  $("#wrongNum").val();
			if(wrongNum =="" || wrongNum ==null){
				//首次进入查询页面，不需要弹框
			}else{
				if(wrongNum ==0){
					alert("导入成功"+sucessNum+"条，"+"导入失败"+errorNum+"条，"+"重复数据"+repeatNum+"条。");
				}else if(wrongNum ==1){
					alert("文件导入失败");
				}else if(wrongNum ==2){
					alert("文件格式不正确，导入失败");
				}
			}
		}
		function paging(pageNo) {
			var form = document.getElementById("dataInterfaceConfigSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryPageDataInterfaceConfig.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			document.getElementById("dataInterfaceConfigSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			var inputs= o.getElementsByTagName('input');
			for(var i = 0;i<inputs.length;i++){
				if($(inputs[i]).attr("type") == "text"){
					$(inputs[i]).val("");
				}
			} 
			o.style.display = "none";
		}
		
		function addDataInterfaceConfig() {	
			var custom_object = $("#custom_object").val();
			var generate_number = custom_object.split(";")[1];
			$("#custom_object_id").val(custom_object.split(";")[0]);
			var object_name = $("#custom_object").find("option:selected").text();
			if(generate_number == 1){
				alert("该系统接口选择的为全部数据");
				return;
			}else if(generate_number == 2){
				 $("#merBody > tr").each(function () {
					 var num = $("#merBody tr").length;
					 if(num > 1){
						 this.remove(); 
					 }
				 });
				 $("#merBody").find("#mer_code").each(function(){
					 $(this).val("");
				 });
				 $("#merBody").find("#mer_name").each(function(){
					 $(this).val("");
				 });
				$("#insertByMerCode").css({display:"block"});
				$("#object_name_mer").val(object_name);
				$("#generate_number_mer").val("按商户号统计");
			}else if(generate_number == 3){
				 $("#tradeCodeBody > tr").each(function () {
					 var num = $("#tradeCodeBody tr").length;
					 if(num > 1){
						 this.remove(); 
					 }
				 });
				 $("#tradeCodeBody").find("#trade_code").each(function(){
						$(this).val("");
				 });
				 $("#tradeCodeBody").find("#trade_name").each(function(){
						$(this).val("");
				 });
				$("#insertByTradeCode").css({display:"block"});
				$("#object_name_trade").val(object_name);
				$("#generate_number_trade").val("按交易码统计");
			}
		}
		function importDataInterface(){
			var custom_object = $("#custom_object").val();
			var generate_number = custom_object.split(";")[1];
			$("#custom_object_id").val(custom_object.split(";")[0]);
			var object_name = $("#custom_object").find("option:selected").text();
			if(generate_number == 1){
				alert("该系统接口选择的为全部数据");
				return;
			}else if(generate_number == 2){
				$("#importByMerCode").css({display:"block"});
				$("#objectName_mer").val(object_name);
				$("#generateNumber_mer").val("按商户号统计");
			}else if(generate_number == 3){
				$("#importByTradeCode").css({display:"block"});
				$("#objectName_trade").val(object_name);
				$("#generateNumber_trade").val("按交易码统计");
			}
		}
		
		function deleteData(id){
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteDataInterfaceConfig.do",
					data : "id="+ id,
					dataType : "text",
					success : function(msg) {
						if(msg == "1"){
							alert("删除成功");
							checkQuery();
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		
		function addColumnForMerCode(){
			var num = $("#merBody tr").length;
			if(num == 10){
				return;
			}
			var tr = $("#merBody_hidden tr").eq(0).clone();   
		    tr.appendTo("#merBody");
		}
		function addColumnForTradeCode(){
			var num = $("#tradeCodeBody tr").length;
			if(num == 10){
				return;
			}
			var tr = $("#tradeCodeBody_hidden tr").eq(0).clone();   
		    tr.appendTo("#tradeCodeBody");
		}
		function delColumnForMerCode(input){
			var num = $("#merBody tr").length;
			if(num == 1){
				return;
			}
			$(input).parent().parent().remove();
		}
		
		function delColumnForTradeCode(input){
			var num = $("#tradeCodeBody tr").length;
			if(num == 1){
				return;
			}
			$(input).parent().parent().remove();
		}
		
		
		function addMerCode(){
			var merCode = new Array();
			var i =0 ;
			var m_flag = true;
			$("#merBody").find("#mer_code").each(function(){
				if($(this).val() == null || $(this).val() == ""){
					alert("商户号不能为空！");
					m_flag = false;
					return false;
				}else{
					merCode[i] = $(this).val();
					i++;
				}
			});
			var merName = new Array();
			var j = 0;
			var t_flag = true;
			$("#merBody").find("#mer_name").each(function(){
				if($(this).val() == null || $(this).val() == ""){
					alert("商户名称不能为空！");
					t_flag = false;
					return false;
				}else{
					merName[j] = $(this).val();
					j++;
				}
			});
			var object_id = $("#custom_object_id").val();
			var object_name = $("#object_name_mer").val();
			var status_mer = $("#status_mer").val();
			if(m_flag && t_flag){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/addDataInterfaceConfigByMerCode.do",
					data : "merCode="+ merCode + "&merName=" + merName +"&object_id="+object_id+"&object_name="+object_name+"&status_mer="+status_mer,
					dataType : "text",
					success : function(msg) {
						if ("0" != msg) {
							var sucessNum = msg.split(";")[0];
							var errorNum = msg.split(";")[1];
							alert("添加成功"+sucessNum+"条"+"，添加失败"+errorNum+"条");
							$("#insertByMerCode").css({display:"none"});
							checkQuery();
						}else {					
							alert("添加失败！");
							return;
						}
					}
				});
			}
		}
		
			
		function addTradeCode(){
			var tradeCode = new Array();
			var i =0 ;
			var c_flag = true;
			$("#tradeCodeBody").find("#trade_code").each(function(){
				if($(this).val() == null || $(this).val() == ""){
					alert("交易码不能为空！");
					c_flag = false;
					return false;
				}else{
					tradeCode[i] = $(this).val();
					i++;
				}
			});
			var tradeName = new Array();
			var j = 0;
			var n_flag = true;
			$("#tradeCodeBody").find("#trade_name").each(function(){
				if($(this).val() == null || $(this).val() == ""){
					alert("交易码名称不能为空！");
					n_flag = false;
					return false;
				}else{
					tradeName[j] = $(this).val();
					j++;
				}
			});
			var object_id = $("#custom_object_id").val();
			var object_name = $("#object_name_trade").val();
			var status_trade = $("#status_trade").val();
			if(c_flag && n_flag){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/addDataInterfaceConfigByTradeCode.do",
					data : "tradeCode="+ tradeCode + "&tradeName=" + tradeName +"&object_id="+object_id+"&object_name="+object_name,
					dataType : "text",
					success : function(msg) {
						if ("0" != msg) {
							var sucessNum = msg.split(";")[0];
							var errorNum = msg.split(";")[1];
							alert("添加成功"+sucessNum+"条"+"，添加失败"+errorNum+"条");
							$("#insertByTradeCode").css({display:"none"});
							checkQuery();
						}else {					
							alert("添加失败！");
							return;
						}
					}
				});
			}
		}
		
		function checkMerCode(merCode){
			var pattern = new RegExp("^[0-9]{3,15}$");
			if(!pattern.test(merCode)){
				alert("电银商户号必须为3到15位数字！");
				return false;
			}else{
				//检查当前页面上是否添加过相同的商户号
				var num_merCode = 0;
				$("#merBody").find("#mer_code").each(function(){
					if($(this).val() == merCode){
						num_merCode++;
					}
				});
				if(num_merCode > 1){
					alert("商户号已经添加，请重新输入！");
					return false;
				}
				
				//检查数据库中是否保存过相同的商户号
				if(uniqueCode(merCode)){
					return true;
				}else{
					return false;
				}
			}
		}
		function checkTradeCode(tradeCode){
			var reg = new RegExp("^[a-zA-Z0-9]{3}$");
			if(!reg.test(tradeCode)){
				alert("交易码由数字，字母组成，长度必须为3！");
				return false;
			}else{
				//检查当前页面上是否添加过相同的交易码
				var num_tradeCode = 0;
				$("#tradeCodeBody").find("#trade_code").each(function(){
					if($(this).val() == tradeCode){
						num_tradeCode++;
					}
				});
				if(num_tradeCode > 1){
					alert("交易码已经添加，请重新输入！");
					return false;
				}
				
				//检查数据库中是否保存过相同的交易码
				if(uniqueCode(tradeCode)){
					return true;
				}else{
					return false;
				}
			}
		}
		function uniqueCode(code){
			var object_id = $("#custom_object_id").val();
			var flag = false;
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/checkMerCodeOrTradeCodeRepeatOrNot.do",
				data : "object_id=" + object_id+"&code="+code,
				dataType : "text",
				async: false,
				success : function(msg) {
					if(msg == "0"){
						alert("号码已经存在不能再次添加");
					}else{
						flag = true;
					}
				}
			});
			return flag;
		}
		
		function importDataByMerCodeFile(){
			
			var file_mer = $("#file_mer").val();
			if(file_mer == "" || file_mer == null){
				alert("请选择要上传的文件");
				return ;
			}else{
				if(!((file_mer.indexOf(".xls") >= 0 && file_mer.lastIndexOf(".xls")==file_mer.length-4) || (file_mer.indexOf(".xlsx") >= 0 && file_mer.lastIndexOf(".xlsx")==file_mer.length-5))){
					alert("请选择xls或xlsx格式的文件进行上传操作");
					return;
				}
			}
			var object_id = $("#custom_object_id").val();
			var object_name = $("#objectName_mer").val();
			var status_mer = $("#statusMer").val();
			var form = document.getElementById("importByMerCodeFile");
			form.action = "<%=request.getContextPath()%>/importDataInterfaceConfigByMerFile.do?object_id="+object_id+"&object_name="+object_name+"&status_mer="+status_mer;
			form.submit();
			
		}
		function importDataByTradeCodeFile(){
			
			var file_trade = $("#file_trade").val();
			if(file_trade == "" || file_trade == null){
				alert("请选择要上传的文件");
				return ;
			}else{
				if(!(file_trade.indexOf(".xls") >= 0 && file_trade.lastIndexOf(".xls")==file_trade.length-4)){
					alert("请选择xls格式的文件进行上传操作");
					return;
				}
			}
			var object_id = $("#custom_object_id").val();
			var object_name = $("#objectName_trade").val();
			var statusTrade = $("#statusTrade").val();
			var form = document.getElementById("importByTradeCodeFile");
			form.action = "<%=request.getContextPath()%>/importDataInterfaceConfigByTradeFile.do?object_id="+object_id+"&object_name="+object_name+"&statusTrade="+statusTrade;
			form.submit();
			
		}
		function importByAgentCode(){
			var object_id = $("#custom_object_id").val();
			var agent_code = $("#agent_code").val();
			if(agent_code == null || agent_code == ''){
				alert("请输入代理商渠道号");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/queryMerInfoByAgentCode.do",
				data : "object_id=" + object_id+"&agent_code="+agent_code,
				dataType : "text",
				async: false,
				success : function(msg) {
					var a = msg.split(";");
					if(a[0] == "0"){
						alert("该代理商下不存在商户");
					}else if(a[0] == "-1"){
						alert("请求管理平台接口失败");
					}else if(a[0] == "E0001"){
						alert("没有此代理商数据");
					}else if(a[0] == "E0002"){
						alert("查询不到商户数据");
					}else if(a[0] == "E0003"){
						alert("管理平台返回系统错误提示");
					}else{
						$("#agentCode").val(agent_code);
						$("#agentName").val(a[1]);
						$("#merInfo").val(a[0]);
						$("#importByAgentCodeInfo").css({display:"block"});
					}
				}
			});
		}
		function importByAgentCodeCommit(){
			var object_id = $("#custom_object_id").val();
			var object_name = $("#objectName_mer").val();
			var status_mer = $("#statusMer").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/importMerInfoByAgentCode.do",
				data : "object_id=" + object_id+"&object_name="+object_name+"&status_mer="+status_mer,
				dataType : "text",
				async: false,
				success : function(msg) {
					var a = msg.split(";");
					alert("导入成功"+a[0]+"条,"+"重复数据"+a[1]+"条,"+"错误数据"+a[2]+"条.");
					cancelCommit();
				}
			});
		}
		function cancelCommit(){
			<% 
				HttpSession sessions=request.getSession();
				sessions.removeAttribute("mer_list");
			%>
			$("#importByAgentCodeInfo").css({display:"none"});
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
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>接口数据配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageDataInterfaceConfig.do" target="right" name="dataInterfaceConfigSearch" id = "dataInterfaceConfigSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>商户号：</b>
							<span class="input_bgl">
								<input maxlength="15" type="text" name="mercode_" onkeyup="value=value.replace(/[^\d]/g,'')"  id="mercode_" value="${param.mercode_ }" />
							</span>
						</li>
						<li>
							<b>交易码：</b>
							<span class="input_bgl">
								<input maxlength="3" type="text" name="tradecode_" onkeyup="value=value.replace(/[\s.,，。?!@//]/g,'')"  id = "tradecode_" value="${param.tradecode_ }" />
							</span>
						</li>
						<li>
							<b>系统名称：</b>
							<span class="in_t_bgl">
								<select id="custom_object" name="custom_object">
								</select>
							</span><font color="red">*</font>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addDataInterfaceConfig();"/>
							<input type="button" class="icon_normal" value="导入" onclick="importDataInterface();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0" style="table-layout:fixed;">
					<thead>
						<tr>
							<td align="center" width="10%">系统接口ID</td>
							<td align="center" width="20%">系统接口名称</td>
							<td align="center" width="20%">商户号/交易码</td>
							<td align="center" width="20%">商户名称/交易码名称</td>
							<td align="center" width="20%">操作时间</td>
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataInterfaceConfig.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataInterfaceConfig.result }" var="dataInterfaceConfig">
						<tr id="${dataInterfaceConfig.id }" class="ssss">
							<td align="center" style="word-wrap:break-word;">${dataInterfaceConfig.object_id }</td>
							<td align="center" style="word-wrap:break-word;">${dataInterfaceConfig.object_name }</td>
							<td align="center" style="word-wrap:break-word;">${dataInterfaceConfig.value }</td>
							<td align="center" style="word-wrap:break-word;">${dataInterfaceConfig.name }</td>
							<td align="center" style="word-wrap:break-word;">${dataInterfaceConfig.operation_time }</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:deleteData('${dataInterfaceConfig.id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDataInterfaceConfig.totalPages != null}">
				<div class="next">
					<c:if test="${pageDataInterfaceConfig.pageNo > 1}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo-2 })"><span>${pageDataInterfaceConfig.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo-1 })"><span>${pageDataInterfaceConfig.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDataInterfaceConfig.pageNo }</span></a>
					<c:if test="${pageDataInterfaceConfig.pageNo+1 <= pageDataInterfaceConfig.totalPages}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo+1 })"><span>${pageDataInterfaceConfig.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo+2 <= pageDataInterfaceConfig.totalPages}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo+2 })"><span>${pageDataInterfaceConfig.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo+3 <= pageDataInterfaceConfig.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataInterfaceConfig.pageNo < pageDataInterfaceConfig.totalPages}">
						<a href="javascript:paging(${pageDataInterfaceConfig.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDataInterfaceConfig.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageDataInterfaceConfig.pageNo }" />页
							<input type="hidden" value="${pageDataInterfaceConfig.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<!-- 根据商户号添加 -->
	<div id="insertByMerCode" class="pop" style="display: none;OVERFLOW: auto; ">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">添加数据</span> 
				<a class="close" href="javascript:hide('insertByMerCode');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">					
					<tr>
						<td align="right" bgcolor="#eeeeee" >系统接口名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="object_name_mer" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="generate_number_mer" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<!--  
					<tr>
						<td align="right" bgcolor="#eeeeee">数据状态：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="status_mer">
						       		<option value="0">增加</option>
						       		<option value="1">删除</option>
						       </select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					-->
					<tr>
						<table>
							<tbody id="merBody">
								<tr>
									<td align="right" bgcolor="#eeeeee" width="35%">电银商户号：</td>
									<td align="center">
										<input type="text" id="mer_code" maxlength="15" name="mer_code" value="" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="if(!checkMerCode(this.value))this.value='';"/>
									</td>
									<td align="right" bgcolor="#eeeeee" width="15%">商户名称：</td>
									<td align="center">
										<input type="text" id="mer_name" name="mer_name" value=""/>
									</td>
									<td align="center" width="25%">
										<input type="button" value=" + " id="addColumn" name="addColumn" onclick="addColumnForMerCode();"/>
										<input type="button" value=" - " id="delColumn" name="delColumn" onclick="delColumnForMerCode(this);"/>
									</td>
								</tr>
							</tbody>
						</table>
					</tr>
					<tr>
						<div align="center">
							<input type="button" class="icon_normal" value="确定" onclick="addMerCode()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:hide('insertByMerCode');" />
						</div>
						<span style="color:red;margin-left: 280px;">注：1)一次最多添加10个商户号;2)输入内容为15位数字，且唯一</span>
					</tr>
				</table>
			</div>
			<div style="display: none">
				<table>
					<tbody id="merBody_hidden">
						<tr>
							<td align="right" bgcolor="#eeeeee" width="35%">电银商户号：</td>
							<td align="center">
								<input type="text" id="mer_code" maxlength="15" name="mer_code" value="" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="if(!checkMerCode(this.value))this.value='';"/>
							</td>
							<td align="right" bgcolor="#eeeeee" width="15%">商户名称：</td>
							<td align="center">
								<input type="text" id="mer_name" name="mer_name" value=""/>
							</td>
							<td align="center" width="25%">
								<input type="button" value=" + " id="addColumn" name="addColumn" onclick="addColumnForMerCode();"/>
								<input type="button" value=" - " id="delColumn" name="delColumn" onclick="delColumnForMerCode(this);"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- 根据交易码提交 -->
	<div id="insertByTradeCode" class="pop" style="display: none;OVERFLOW: auto; ">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">添加数据</span> 
				<a class="close" href="javascript:hide('insertByTradeCode');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="dataInterfaceConfig">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="object_name_trade" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="generate_number_trade" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<!--
					<tr>
						<td align="right" bgcolor="#eeeeee">数据状态：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="status_trade">
						       		<option value="0">增加</option>
						       		<option value="1">删除</option>
						       </select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					-->
					<tr>
						<table>
							<tbody id="tradeCodeBody">
								<tr>
									<td align="right" bgcolor="#eeeeee" width="31%">交易码：</td>
									<td align="center">
										<input type="text" maxlength="3" id="trade_code" name="trade_code" value="" onblur="if(!checkTradeCode(this.value))this.value='';"/>
									</td>
									<td align="right" bgcolor="#eeeeee" width="15%">交易码名称：</td>
									<td align="center">
										<input type="text" id="trade_name" name="trade_name" value=""/>
									</td>
									<td align="center" >
										<input type="button" value=" + " id="addColumn" name="addColumn" onclick="addColumnForTradeCode();"/>
										<input type="button" value=" - " id="delColumn" name="delColumn" onclick="delColumnForTradeCode(this);"/>
									</td>
								</tr>
							</tbody>
						</table>
					</tr>
					<tr>
						<div align="center">
							<input type="button" class="icon_normal" value="确定" onclick="addTradeCode()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:hide('insertByTradeCode');" />
						</div>
						<span style="color:red;margin-left: 280px;">注：1)一次最多添加10个交易码;2)输入内容为3位数字或字母组合，且唯一</span>
					</tr>
				</table>
			</div>
			<div style="display: none">
				<table>
					<tbody id="tradeCodeBody_hidden">
						<tr>
							<td align="right" bgcolor="#eeeeee" width="31%">交易码：</td>
							<td align="center">
								<input type="text" id="trade_code" maxlength="3" name="trade_code" value="" onblur="if(!checkTradeCode(this.value))this.value='';"/>
							</td>
							<td align="right" bgcolor="#eeeeee" width="15%">交易码名称：</td>
							<td align="center">
								<input type="text" id="trade_name" name="trade_name" value=""/>
							</td>
							<td align="center" >
								<input type="button" value=" + " id="addColumn" name="addColumn" onclick="addColumnForTradeCode();"/>
								<input type="button" value=" - " id="delColumn" name="delColumn" onclick="delColumnForTradeCode(this);"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 根据商户号导入 -->
	<form action="<%=request.getContextPath()%>/importDataInterfaceConfigByMerFile.do" target="right" name="importByMerCodeFile" id = "importByMerCodeFile" method="post" enctype="multipart/form-data">
		<div id="importByMerCode" class="pop" style="display: none;">
			<div class="pop_body" >
				<h1 class="pop_tit">
					<span class="fl">添加数据</span> 
					<a class="close" href="javascript:hide('importByMerCode');">&nbsp;</a>
				</h1>
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0">					
						<tr>
							<td align="right" bgcolor="#eeeeee" width="40%">系统接口名称：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="objectName_mer" readonly="readonly"/>
							   </span>
							   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="generateNumber_mer" readonly="readonly"/>
							   </span>
							   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<!--  
						<tr>
						<td align="right" bgcolor="#eeeeee">数据状态：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="statusMer">
						       		<option value="0">增加</option>
						       		<option value="1">删除</option>
						       </select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						</tr>
						-->
						<tr>
							<td align="right" bgcolor="#eeeeee" >按代理商渠道号导入：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="agent_code" onkeyup="value=value.replace(/[^\d]/g,'')"/>
							   </span>
							   <span>
							   		<input type="button" id="importByAgent" onclick="importByAgentCode()" value="导入"/>
							   </span>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">按文件批量导入：</td>
							<td>
								<input type="file" value="" id="file_mer" name="file_mer" />
								<span>
							   		<input type="button" onclick="importDataByMerCodeFile()" value="导入" />
							    注：导入的文件只能为xls格式</span>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input type="button" class="icon_normal" value="返回" onclick="javascript:hide('importByMerCode');" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<!-- 根据交易码导入 -->
	<form action="<%=request.getContextPath()%>/importDataInterfaceConfigByTradeFile.do" target="right" name="importByTradeCodeFile" id = "importByTradeCodeFile" method="post" enctype="multipart/form-data">
		<div id="importByTradeCode" class="pop" style="display: none;">
			<div class="pop_body" >
				<h1 class="pop_tit">
					<span class="fl">添加数据</span> 
					<a class="close" href="javascript:hide('importByTradeCode');">&nbsp;</a>
				</h1>
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0">					
						<tr>
							<td align="right" bgcolor="#eeeeee" width="40%">系统接口名称：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="objectName_trade" readonly="readonly" />
							   </span>
							   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="generateNumber_trade" readonly="readonly"/>
							   </span>
							   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						<!--  
						<tr>
							<td align="right" bgcolor="#eeeeee">数据状态：</td>
							<td>
							   <span class="input_bgl"> 
							       <select id="statusTrade">
							       		<option value="0">增加</option>
							       		<option value="1">删除</option>
							       </select>
							   </span>
							   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</td>
						</tr>
						-->
						<tr>
							<td align="right" bgcolor="#eeeeee">按文件批量导入：</td>
							<td>
								<input type="file" value="" id="file_trade" name="file_trade" />
								<span>
							   		<input type="button" id="importByTradeCodeFile" onclick="importDataByTradeCodeFile();" value="导入" />
							    注：导入的文件只能为xls格式</span>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input type="button" class="icon_normal" value="返回" onclick="javascript:hide('importByTradeCode');" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	
	<div id="importByAgentCodeInfo" class="pop" style="display: none;">
			<div class="pop_body" >
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0">					
						<tr>
							<td align="right" bgcolor="#eeeeee" width="40%">此次导入：</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">代理商渠道号：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="agentCode" value="" readonly="readonly"/>
							   </span>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee" >代理商名称：</td>
							<td>
							   <span class="input_bgl"> 
							       <input type="text" id="agentName" value="" readonly="readonly"/>
							   </span>
							</td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户数据：</td>
							<td>
								<span>共</span>
								 <span class="input_bgl"> 
							       <input type="text" id="merInfo" value="" readonly="readonly"/>
							   </span>个商户
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" class="icon_normal" value="确认导入" onclick="importByAgentCodeCommit()" />
								<input type="button" class="icon_normal" value="取消" onclick="cancelCommit()" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	
	<div>
		<input type="hidden" value="${object_id }" id="custom_object_id" />
		<input type="hidden" value="${sucessNum }" id="sucessNum" />
		<input type="hidden" value="${repeatNum }" id="repeatNum" />
		<input type="hidden" value="${errorNum }" id="errorNum" />
		<input type="hidden" value="${wrongNum }" id="wrongNum" />
	</div>
</body>
</html>
