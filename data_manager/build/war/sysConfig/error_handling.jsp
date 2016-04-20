<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>差错处理方式模块查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("errorHandlingSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/getErrorHandlingLst.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			document.getElementById("errorHandlingSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addHandling() {	
			$("#insert").css({display:"block"});
		}
		
		function addHandlingSub(){
			var handling_name_insert = $("#handling_name_insert").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addErrorHandlingLst.do",
				data : "handling_name_insert="+ handling_name_insert,
				dataType : "text",
				success : function(msg) {
					if (Boolean(msg)) {
						alert("添加成功！");
						paging($("#pageNo").val());
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		function selectData(id,handling_name){
			$("#id_update").val(id);
			$("#handling_name_update").val(handling_name);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var id = $("#id_update").val();
			var handling_name = $("#handling_name_update").val();
			if(handling_name == null || handling_name == ""){
				alert("差错处理方式名称不能为空！");
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateErrorHandlingLst.do",
				data : "id_update="+ id +"&handling_name_update="+handling_name,
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
			if(confirm("确定要删除"+id+"差错处理方式信息数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteErrorHandlingLst.do",
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
	</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>差错处理方式配置查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/getErrorHandlingLst.do" target="right" name="errorHandlingSearch" id = "errorHandlingSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>处理方式编号</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="id" id="id" />
							</span>
						</li>
						<li>
							<b>处理方式中文名称</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="handling_name" id = "handling_name" />
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
							<td align="center" width="20%">ID</td>
							<td align="center" width="50%">差错处理方式名称</td>
							<td align="center" width="30%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="errorHandling">
						<tr>
							<td align="center">${errorHandling.id }</td>
							<td align="center">${errorHandling.handling_name }</td>
							<td align="center" width="60">
								<a href="javascript:deleteData(${errorHandling.id})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData(${errorHandling.id},'${errorHandling.handling_name }')">修改</a>
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
				<span class="fl">差错处理方式信息添加</span> 
				<a class="close" href="javascript:hide('insert')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="instInfo">					
					<tr>
						<td align="right" bgcolor="#eeeeee">差错处理方式名称：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="handling_name_insert" name="handling_name_insert" /> 
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
				<span class="fl">差错处理方式信息修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">编号：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" disabled="disabled" id="id_update" name="id_update"  value="" /> 
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理方式名称：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="handling_name_update" name="handling_name_update"  value="" /> 
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
