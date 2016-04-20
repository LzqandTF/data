<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>规则配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

		function paging(pageNo) {
			var form = document.getElementById("ruleHandlerSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryRuleHandler.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			var template_id = $("#template_id_").val(); 
			var reg = /^\d{1,}$/;
			if(template_id != null && template_id != ""){
				var i = new Number(template_id);
				if(i == 0){
					alert("规则ID不能为0！");
					return;
				}
			}
			if(template_id != null && template_id.length != 0){
				if(!reg.test(template_id)){
					alert("规则ID必须为数字！");
					return;
				}
			}
			document.getElementById("ruleHandlerSearch").submit();
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
		
		function addRuleHandler() {	
			$("#insert").css({display:"block"});
		}
		
		function addRuleHandlerSub(){
			var attribute_column = $("#attribute_column").val();
			var dz_column_name = $("#dz_column_name").val();
			var template_id = $("#template_id").val();
			var handler_type = $("#handler_type").val();
			var rule_description = $("#rule_description").val();
			if(attribute_column == null || attribute_column == ""){
				alert("规则模板名称不能为空！");
				return;
			}
			if(dz_column_name == "" || dz_column_name == null){
				alert("规则模板描述不能为空！");
				return;
			}
			if(rule_description == null || rule_description == ""){
				alert("规则描述不能为空！");
				return;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addRuleHandler.do",
				data : "attribute_column=" + attribute_column + "&dz_column_name=" + dz_column_name+ "&template_id=" + template_id+ "&handler_type=" + handler_type+ "&rule_description=" + rule_description,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						hide("insert");
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		function selectData(rule_id,attribute_column,dz_column_name,template_id,handler_type,rule_description){
			$("#rule_id_u").val(rule_id);
			$("#attribute_column_u").val(attribute_column);
			$("#dz_column_name_u").val(dz_column_name);
			
			var dz_column_name_ = document.getElementById("_name_u");
			
			for(var i = 0;i<dz_column_name_.options.length;i++){
				if(dz_column_name_.options[i].value == attribute_column){
					dz_column_name_.options[i].selected = 'selected';
				}
			}
			
			var template_id_ = document.getElementById("template_id_u");
			
			for(var i = 0;i<template_id_.options.length;i++){
				if(template_id_.options[i].value == template_id){
					template_id_.options[i].selected = 'selected';
				}
			}
			
			var handler_type_ = document.getElementById("handler_type_u");
			
			for(var i = 0;i<handler_type_.options.length;i++){
				if(handler_type_.options[i].value == handler_type){
					handler_type_.options[i].selected = 'selected';
				}
			}
			
			$("#rule_description_u").val(rule_description);
			
			$("#update").css({display:"block"});
			
		}
		
		function updateData(){
			var rule_id = $("#rule_id_u").val();
			var template_id = $("#template_id_u").val();
			var attribute_column = $("#attribute_column_u").val();
			var dz_column_name = $("#dz_column_name_u").val();
			var handler_type = $("#handler_type_u").val();
			var rule_description = $("#rule_description_u").val();
			if(attribute_column == null || attribute_column == ""){
				alert("规则模板名称不能为空！");
				return;
			}
			if(dz_column_name == "" || dz_column_name == null){
				alert("规则模板描述不能为空！");
				return;
			}
			if(rule_description == null || rule_description == ""){
				alert("规则描述不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateRuleHandler.do",
				data : "rule_id="+rule_id+"&attribute_column=" + attribute_column + "&dz_column_name=" + dz_column_name+ "&template_id=" + template_id+ "&handler_type=" + handler_type+ "&rule_description=" + rule_description,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
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
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteRuleHandler.do",
					data : "rule_id="+ id,
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
		
		function changeSelect(dz_column_name){
			$.ajax({
	    		url : '<%=request.getContextPath()%>/loadDzFileColumnConf.do',
	    		data : "dz_column_name="+ dz_column_name,
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			if(msg.length != null && msg.length != ""){
	    				$("#_name").empty();
	    				$("#_name_u").empty();
	    				for (i in msg){
		    				$("#_name").append('<option value="' + msg[i]['attribute_column'] + '">'+ msg[i]['attribute_name'] + '</option>');
		    				$("#_name_u").append('<option value="' + msg[i]['attribute_column'] + '">'+ msg[i]['attribute_name'] + '</option>');
		    			}
	    			}
	    		}
	    	});
		}
		function showColumnMsg(){
			var columnMsg = $("#_name").find("option:selected").text();
			var column = $("#_name").val();
			$("#dz_column_name").val(columnMsg);
			$("#attribute_column").val(column);
		}
		function showColumnMsg_u(){
			var columnMsg = $("#_name_u").find("option:selected").text();
			var column = $("#_name_u").val();
			$("#dz_column_name_u").val(columnMsg);
			$("#attribute_column_u").val(column);
		}
		function assignmentData(rule_id,template_id,handler_type){
			$("#addValueOfPart").css({display:"none"});
			var tbody = document.getElementById("addTr");
			while(tbody.firstChild){
				tbody.removeChild(tbody.firstChild);
			}
			$("#rule_id_h").val(rule_id);
			$("#template_id_h").val(template_id);
			$("#handle_type_h").val(handler_type);
			$("#id_hidden").val("");
			if(template_id == 1 && handler_type==1){/* 精确替换 -- 针对部分*/
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/queryRuleHandlerValueList.do",
					data : "rule_id="+ rule_id+"&template_id="+template_id+"&handler_type="+handler_type,
					dataType : "text",
					success : function(msg) {
						if(msg=='' || msg == null){
							$("#addTr").append('<tr align="center"><td colspan="3">对不起，暂无数据</td></tr>');
						}else{
							var obj = eval("("+msg+")"); 
							for(var i=0;i<obj.length;i++){
								$("#addTr").append('<tr class="ssss"><td align="center" width="40%">' + obj[i]['old_value'+i] + '</td><td align="center" width="40%">'+ obj[i]['new_value'+i] + '</td><td align="center" width="60"><a style="color: red" href="javascript:selectValue('+obj[i]['rule_id']+','+encodeURI("'"+obj[i]['old_value'+i]+"'")+','+encodeURI("'"+obj[i]['new_value'+i]+"'")+','+obj[i]['id'+i]+')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: red" href="javascript:deleteValue('+obj[i]['id'+i]+')">删除</a></td></tr>');
							 }
						}
						$("#replaceForPart").css({display:"block"});
					}
				});
			}else if(template_id == 1 && handler_type == 0){/* 精确替换 -- 针对所有*/
				$.ajax({
		    		url : '<%=request.getContextPath()%>/queryReplaceValueByRuleId.do',
		    		data : "rule_id="+ rule_id,
		    		type : 'post',
		    		async : false,
		    		success : function(msg) {
		    			if(msg != null){
		    				$("#new_value_all").val(msg.new_value);
		    				$("#id_all").val(msg.id);
		    			}
		    		}
		    	});
				$("#addValueOfAll").css({display:"block"});
			}else if(template_id == 2){ /* 模糊替换  */
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/queryRuleHandlerValueList.do",
					data : "rule_id="+ rule_id+"&template_id="+template_id+"&handler_type="+handler_type,
					dataType : "text",
					success : function(msg) {
						if(msg=='' || msg == null){
							$("#addTr").append('<tr align="center"><td colspan="3">对不起，暂无数据</td></tr>');
						}else{
							var obj = eval("("+msg+")"); 
							for(var i=0;i<obj.length;i++){
								$("#addTr").append('<tr class="ssss"><td align="center" width="40%">' + obj[i]['old_value'+i] + '</td><td align="center" width="40%">'+ obj[i]['new_value'+i] + '</td><td align="center" width="60"><a style="color: red" href="javascript:selectValue('+obj[i]['rule_id']+','+encodeURI("'"+obj[i]['old_value'+i]+"'")+','+encodeURI("'"+obj[i]['new_value'+i]+"'")+','+obj[i]['id'+i]+')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: red" href="javascript:deleteValue('+obj[i]['id'+i]+')">删除</a></td></tr>');
							 }
						}
						$("#replaceForPart").css({display:"block"});
					}
				});
			}else if(template_id == 3){ /* 截取  */
				$.ajax({
		    		url : '<%=request.getContextPath()%>/queryReplaceValueByRuleId.do',
		    		data : "rule_id="+ rule_id,
		    		type : 'post',
		    		async : false,
		    		success : function(msg) {
		    			if(msg != null){
		    				$("#start_position").val(msg.old_value);
		    				$("#end_position").val(msg.new_value);
		    				$("#id_sub").val(msg.id);
		    			}
		    		}
		    	});
				$("#templateOfSubString").css({display:"block"});
			}
			
		}
		function insertValueData(){
			insert_flag = 1;
			$("#addValueOfPart").css({display:"block"});
		}
		function addValueOfPart(){
			var rule_id = $("#rule_id_h").val();
			var template_id = $("#template_id_h").val();
			var handle_type = $("#handle_type_h").val();
			var id = $("#id_hidden").val();
			
			
			var old_value = $("#old_value").val();
			var new_value = $("#new_value").val();
			
			if(id=="" || id==null){
				id=0;
			}
			
			if(old_value == "" || old_value == null){
				alert("旧值不能为空！");
				return;
			}
			if(old_value.indexOf(" ") >=0){
				alert("旧值中不能包含空格");
				return;
			}
			if(template_id == 2){
				if(old_value.indexOf("%") >=0){
					if(old_value.split("%").length - 1 > 2){
						alert("旧值中的%个数至多为2");
						return;
					}
					if(old_value.lastIndexOf("%") - old_value.indexOf("%") == 1){
						alert("旧值中的%不能相邻");
						return;
					}
					if(old_value.split("%").length - 1 == 2){
						if(!(old_value.indexOf("%")==0 && old_value.lastIndexOf("%")==old_value.length-1)){
							alert("%的位置必须在旧值的开始或者最后");
							return;
						}
					}else{
						if(!(old_value.indexOf("%")==0 || old_value.lastIndexOf("%")==old_value.length-1)){
							alert("%必须处于首尾处");
							return;
						}
					}
				}else{
					alert("旧值中必须包含%");
					return;
				}
			} 
			if(new_value == null || new_value == ""){
				alert("新值不能为空！");
				return;
			}
			if(new_value.indexOf(" ") >=0){
				alert("新值中不能包含空格");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateReplaceValue.do",
				data : "id="+id+"&rule_id="+rule_id+"&old_value=" + encodeURIComponent(old_value) + "&new_value=" + encodeURIComponent(new_value),
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						$("#old_value").val("");
						$("#new_value").val("");
						assignmentData(rule_id,template_id,handle_type);
					}else{
						alert("修改失败");
						$("#old_value").val("");
						$("#new_value").val("");
						hide("addValueOfPart");
					}
				}
			});
		}
		function selectValue(rule_id,old_value,new_value,id){
			$("#new_value_h").val(new_value);
			$("#old_value_h").val(old_value);
			$("#new_value").val(new_value);
			$("#old_value").val(old_value);
			$("#id_hidden").val(id);
			$("#addValueOfPart").css({display:"block"});
		}
		function deleteValue(id){
			var rule_id = $("#rule_id_h").val();
			var template_id = $("#template_id_h").val();
			var handle_type = $("#handle_type_h").val();
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteValueOfRuleHandler.do",
					data : "id="+ id,
					dataType : "text",
					success : function(msg) {
						if(msg == "1"){
							alert("删除成功");
							assignmentData(rule_id,template_id,handle_type);
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		function addValueOfAll(){
			var new_value = $("#new_value_all").val();
			var rule_id = $("#rule_id_h").val();
			var id = $("#id_all").val();
			if(id=="" || id==null){
				id=0;
			}
			if(new_value == "" || new_value == null){
				alert("新值不能为空！");
				return;
			}
			if(new_value.indexOf(" ") >=0){
				alert("新值中不能包含空格");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateReplaceValue.do",
				data : "id="+id+"&rule_id="+rule_id+"&new_value=" + encodeURIComponent(new_value),
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						hide("addValueOfAll");
					}else{
						alert("修改失败");
						hide("addValueOfAll");
					}
				}
			});
		}
		function addValueOfSubString(){
		
			var rule_id = $("#rule_id_h").val();
			var old_value = $("#start_position").val();
			var new_value = $("#end_position").val();
			var id = $("#id_sub").val();
			if(id=="" || id==null){
				id=0;
			}
			if(old_value == "" || old_value == null){
				alert("起始位置不能为空！");
				return;
			}
			if(new_value == "" || new_value == null){
				alert("终止位置不能为空！");
				return;
			}
			var reg1 = /^\d{1,}$/;
			if(old_value != null && old_value != ""){
				if(!reg1.test(old_value)){
					alert("起始位置必须为数字！");
					return;
				}
			}
			if(new_value != null && new_value.length != 0){
				if(!reg1.test(new_value)){
					alert("终止位置必须为数字！");
					return;
				}
			}
			var i = new Number(old_value);
			var j = new Number(new_value);
			if(i >= j){
				alert("起始位置不能大于终止位置！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateReplaceValue.do",
				data : "id="+id+"&rule_id="+rule_id+"&old_value=" + old_value + "&new_value=" + new_value,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						hide("templateOfSubString");
					}else{
						alert("修改失败");
						hide("templateOfSubString");
					}
				}
			});
		}
		function checkOldValue(old_value){
			var old_value_ = $("#old_value_h").val();
			var rule_id = $("#rule_id_h").val();
			if(old_value_ == "" || old_value_ == null){//新增
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/checkOldValueOfReplaceValue.do",
					data : "rule_id="+rule_id+"&old_value=" + encodeURIComponent(old_value),
					dataType : "text",
					success : function(msg) {
						if(msg == "1"){
							alert("旧值不能重复");
							$("#old_value").val("");
							$("#old_value").focus();
						}
					}
				});
			}else{
				if(old_value_ != old_value){
					$.ajax({
						type : "post",
						url : "<%=request.getContextPath()%>/checkOldValueOfReplaceValue.do",
						data : "rule_id="+rule_id+"&old_value=" + encodeURIComponent(old_value),
						dataType : "text",
						success : function(msg) {
							if(msg == "1"){
								alert("旧值不能重复");
								$("#old_value").val("");
								$("#old_value").focus();
							}
						}
					});
				}
			}
				
		}
		function init(){
			changeSelect("");
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryRuleTemplateList.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg){
	    				$("#template_id").append('<option value="' + msg[i]['template_id'] + '">'+ msg[i]['template_name'] + '</option>');
	    				$("#template_id_u").append('<option value="' + msg[i]['template_id'] + '">'+ msg[i]['template_name'] + '</option>');
	    			}
	    		},
	    		error : function(msg) {
	    			alert("获取规则模板失败!");
	    		}
	    	});
		}
	</script>
</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>规则配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryRuleHandler.do" target="right" name="ruleHandlerSearch" id = "ruleHandlerSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>规则序号：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="rule_id_" onkeyup="value=value.replace(/[\s]/g,'')" id="rule_id_" value="${param.rule_id_ }"/>
							</span>
						</li>
						<%-- <li>
							<b>规则模板ID</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="template_id_" id="template_id_" value="${param.template_id_ }"/>
							</span>
						</li> --%>
						<li>
							<b>对账字段属性：</b>
							<span class="input_bgl">
								<input type="text" name="attribute_column_" onkeyup="value=value.replace(/[\s]/g,'')" id = "attribute_column_" value="${param.attribute_column_ }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addRuleHandler();"/>
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
							<td align="center" width="10%">规则序号</td>
							<td align="center" width="15%">对账文件字段属性</td>
							<td align="center" width="10%">对账文件字段名称</td>
							<td align="center" width="15%">应用模板名称</td>
							<td align="center" width="10%">规则类型</td>
							<td align="center" width="20%">描述</td>
							<td align="center" width="20%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageRuleHandler.result)<=0 }">
						<tr align="center">
							<td colspan="7">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageRuleHandler.result }" var="data">
						<tr class="ssss" id="${data.rule_id }">
							<td align="center">${data.rule_id }</td>
							<td align="center">${data.attribute_column }</td>
							<td align="center">${data.dz_column_name }</td>
							<td align="center">${data.template_name }</td>
							<td align="center">
								<c:if test="${data.handler_type == 1 }">针对部分</c:if>
								<c:if test="${data.handler_type == 0 }">针对所有</c:if>
							</td>
							<td align="center">${data.rule_description }</td>
							<td align="center" style="display: none">${data.old_value }</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:assignmentData('${data.rule_id}','${data.template_id}','${data.handler_type}')">赋值</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:selectData('${data.rule_id}','${data.attribute_column}','${data.dz_column_name}','${data.template_id}','${data.handler_type}','${data.rule_description}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:deleteData('${data.rule_id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageRuleHandler.totalPages != null}">
				<div class="next">
					<c:if test="${pageRuleHandler.pageNo > 1}">
						<a href="javascript:paging(${pageRuleHandler.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo-2 > 0}">
						<a href="javascript:paging(${pageRuleHandler.pageNo-2 })"><span>${pageRuleHandler.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo-1 > 0}">
						<a href="javascript:paging(${pageRuleHandler.pageNo-1 })"><span>${pageRuleHandler.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageRuleHandler.pageNo }</span></a>
					<c:if test="${pageRuleHandler.pageNo+1 <= pageRuleHandler.totalPages}">
						<a href="javascript:paging(${pageRuleHandler.pageNo+1 })"><span>${pageRuleHandler.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo+2 <= pageRuleHandler.totalPages}">
						<a href="javascript:paging(${pageRuleHandler.pageNo+2 })"><span>${pageRuleHandler.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo+3 <= pageRuleHandler.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageRuleHandler.pageNo < pageRuleHandler.totalPages}">
						<a href="javascript:paging(${pageRuleHandler.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageRuleHandler.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageRuleHandler.pageNo }" />页
							<input type="hidden" value="${pageRuleHandler.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">规则模板添加</span> 
				<a class="close" href="javascript:hide('insert');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="RuleHandler">					
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件字段名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_column_name" name="dz_column_name" onkeyup="value=value.replace(/[\s]/g,'')" onblur="changeSelect(this.value);" value=""/>
						   </span>
						   <span class="input_bgl"> 
						       <select id="_name" name="_name" onclick="showColumnMsg();">
									<option value="">--请选择--</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column" name="attribute_column" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">应用模板名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="template_id" name="template_id">
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">规则类型：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="handler_type" name="handler_type">
										<option value="0">针对所有</option>
										<option value="1">针对部分</option>
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">描述：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="rule_description" onkeyup="value=value.replace(/[\s]/g,'')" name="rule_description"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addRuleHandlerSub()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:hide('insert');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">规则模板修改</span> 
				<a class="close" href="javascript:hide('update');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件字段名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_column_name_u" name="dz_column_name_u" onblur="changeSelect(this.value);" value=""/>
						       <input type="hidden" id="rule_id_u" name="rule_id_u" value=""/>
						   </span>
						   <span class="input_bgl"> 
						       <select id="_name_u" name="_name_u" onclick="showColumnMsg_u();">
									<option value="">--请选择--</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_u" name="attribute_column_u" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">应用模板名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="template_id_u" name="template_id_u">
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">规则类型：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="handler_type_u" name="handler_type_u">
										<option value="0">针对所有</option>
										<option value="1">针对部分</option>
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">描述：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="rule_description_u" name="rule_description_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('update')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="replaceForPart" class="pop" style="display: none;OVERFLOW: auto; ">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">规则旧值与新值修改</span> 
				<a class="close" href="javascript:hide('replaceForPart');">&nbsp;</a>
			</h1>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center" width="40%">旧值</td>
							<td align="center" width="40%">新值</td>
							<td align="center" width="20%">操作</td>
						</tr>
					</thead>
					<tbody id="addTr">
					</tbody>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="添加" onclick="insertValueData()" />
						</td>
					</tr>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
		</div>
	</div>
	
	<div id="addValueOfPart" class="pop" style="display: none;OVERFLOW: auto; ">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">规则添加或修改</span> 
				<a class="close" href="javascript:hide('addValueOfPart');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="part">					
					<tr>
						<td align="right" bgcolor="#eeeeee">旧值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="old_value" name="old_value" value="" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="checkOldValue(this.value);"/>
						       <input type="hidden" id="old_value_h" name="old_value_h" value=""/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">新值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="new_value" name="new_value" value="" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')"/>
						       <input type="hidden" id="new_value_h" name="new_value_h" value=""/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						   <input type="hidden" id="id_hidden" value="" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addValueOfPart()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('addValueOfPart')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div>
		<input type="hidden" id="rule_id_h" value=""/>
		<input type="hidden" id="template_id_h" value=""/>
		<input type="hidden" id="handle_type_h" value=""/>
	</div>
	
	
	<div id="addValueOfAll" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">模板：精确替换，类型：所有</span> 
				<a class="close" href="javascript:hide('addValueOfAll');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="all">					
					<tr>
						<td align="right" bgcolor="#eeeeee">新值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="new_value_all" name="new_value_all"  value="" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')"/>
						       <input type="hidden" id="id_all" value="" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addValueOfAll()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('addValueOfAll')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div id="templateOfSubString" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">模板：截取显示</span> 
				<a class="close" href="javascript:hide('templateOfSubString');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="part">					
					<tr>
						<td align="right" bgcolor="#eeeeee">字段取值（位置）：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="start_position" name="start_position" maxlength="2" value="" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')"/>
						       		&nbsp;&nbsp;至&nbsp;&nbsp;
						       <input type="text" id="end_position" name="end_position" maxlength="2" value="" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')"/>
						       <input type="hidden" id="id_sub" value="" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						   <span style="color: red">字段取值（位置）从0开始</span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addValueOfSubString()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('templateOfSubString')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

</body>
</html>
