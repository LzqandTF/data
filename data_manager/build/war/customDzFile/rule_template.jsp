<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>规则模板配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("ruleTemplateSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryRuleTemplate.do?pageNum=" + pageNo;
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
					alert("规则模板ID不能为0！");
					return;
				}
			}
			if(template_id != null && template_id.length != 0){
				if(!reg.test(template_id)){
					alert("规则模板ID必须为数字！");
					return;
				}
			}
			document.getElementById("ruleTemplateSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addRuleTemplate() {	
			$("#insert").css({display:"block"});
		}
		
		function addRuleTemplateSub(){
			var template_name = $("#template_name").val();
			var template_function = $("#template_function").val();
			var template_descripe = $("#template_descripe").val();
			if(template_name == null || template_name == ""){
				alert("规则模板名称不能为空！");
				return;
			}
			if(template_function == "" || template_function == null){
				alert("规则模板函数不能为空！");
				return;
			}
			if(template_descripe == "" || template_descripe == null){
				alert("规则模板描述不能为空！");
				return;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addRuleTemplate.do",
				data : "template_name=" + encodeURI(template_name) + "&template_function=" + encodeURI(template_function) + "&template_descripe=" + encodeURI(template_descripe),
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
		function selectData(template_id,template_name,template_function,template_descripe){
			$("#template_id_u").val(template_id);
			$("#template_name_u").val(template_name);
			$("#template_function_u").val(template_function);
			$("#template_descripe_u").val(template_descripe);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var template_id_u = $("#template_id_u").val();
			var template_name_u = $("#template_name_u").val();
			var template_function_u = $("#template_function_u").val();
			var template_descripe_u = $("#template_descripe_u").val();
			if(template_name_u == null || template_name_u == ""){
				alert("规则模板名称不能为空！");
			}
			if(template_function_u == "" || template_function_u == null){
				alert("规则模板函数不能为空！");
				return;
			}
			if(template_descripe_u == "" || template_descripe_u == null){
				alert("规则模板描述不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateRuleTemplate.do",
				data : "template_id="+ template_id_u +"&template_name="+encodeURI(template_name_u)+"&template_function="+encodeURI(template_function_u)+"&template_descripe="+encodeURI(template_descripe_u),
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
					url : "<%=request.getContextPath()%>/deleteRuleTemplate.do",
					data : "template_id="+ id,
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
	</script>
</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>规则模板配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryRuleTemplate.do" target="right" name="ruleTemplateSearch" id = "ruleTemplateSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>规则模板ID：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="template_id_" id="template_id_" value="${param.template_id_ }"/>
							</span>
						</li>
						<li>
							<b>规则模板名称：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="template_name_" id = "template_name_" value="${param.template_name_ }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addRuleTemplate();"/>
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
							<td align="center" width="10%">规则模板ID</td>
							<td align="center" width="20%">规则模板名称</td>
							<td align="center" width="30%">规则模板函数</td>
							<td align="center" width="30%">规则模板描述</td>
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageRuleTemplate.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageRuleTemplate.result }" var="ruleTemplate">
						<tr id="${ruleTemplate.template_id }" class="ssss">
							<td align="center">${ruleTemplate.template_id }</td>
							<td align="center">${ruleTemplate.template_name }</td>
							<td align="center">${ruleTemplate.template_function }</td>
							<td align="center">${ruleTemplate.template_descripe }</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:selectData('${ruleTemplate.template_id}','${ruleTemplate.template_name}','${ruleTemplate.template_function}','${ruleTemplate.template_descripe}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:deleteData('${ruleTemplate.template_id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageRuleTemplate.totalPages != null}">
				<div class="next">
					<c:if test="${pageRuleTemplate.pageNo > 1}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo-2 > 0}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo-2 })"><span>${pageRuleTemplate.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo-1 > 0}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo-1 })"><span>${pageRuleTemplate.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageRuleTemplate.pageNo }</span></a>
					<c:if test="${pageRuleTemplate.pageNo+1 <= pageRuleTemplate.totalPages}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo+1 })"><span>${pageRuleTemplate.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo+2 <= pageRuleTemplate.totalPages}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo+2 })"><span>${pageRuleTemplate.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo+3 <= pageRuleTemplate.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageRuleTemplate.pageNo < pageRuleTemplate.totalPages}">
						<a href="javascript:paging(${pageRuleTemplate.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageRuleTemplate.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageRuleTemplate.pageNo }" />页
							<input type="hidden" value="${pageRuleTemplate.pageNo}" id="pageNo" />
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
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="RuleTemplate">					
					<tr>
						<td align="right" bgcolor="#eeeeee">规则模板名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="template_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="template_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">规则模板函数：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="template_function" maxlength="40" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="template_function"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">规则模板描述：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="template_descripe" maxlength="50" onkeyup="value=value.replace(/[.,，。?!@]/g,'')" name="template_descripe"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addRuleTemplateSub()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:checkQuery();" />
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
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">规则模板名称：</td>
						<td>
						   <span class="input_bgl">
						   	   <input style="display: none" id="template_id_u" name="template_id_u" value=""/> 
						       <input type="text" id="template_name_u" name="template_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">规则模板函数：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="template_function_u" name="template_function_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">规则模板描述：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="template_descripe_u" name="template_descripe_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
							<input type="button" class="icon_normal" value="取消" onclick="checkQuery()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

</body>
</html>
