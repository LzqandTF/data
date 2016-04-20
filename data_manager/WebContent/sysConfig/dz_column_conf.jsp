<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账字段基本信息配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("dzColumnConfSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryDzColumnConf.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			document.getElementById("dzColumnConfSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addDzColumnConf() {	
			$("#insert").css({display:"block"});
		}
		
		function addDzColumnConfSub(){
			var dyColumnName = $("#dyColumnName").val();
			var dyColumnProperty = $("#dyColumnProperty").val();
			var channelColumnName = $("#channelColumnName").val();
			var channelColumnProperty = $("#channelColumnProperty").val();
			if(dyColumnName=="" || dyColumnName==null){
				alert("电银对账字段属性名称不能为空");
				return;
			}
			if(dyColumnProperty=="" || dyColumnProperty==null){
				alert("电银对账字段属性不能为空");
				return;
			}
			if(channelColumnName=="" || channelColumnName==null){
				alert("渠道对账字段属性名称不能为空");
				return;
			}
			if(channelColumnProperty=="" || channelColumnProperty==null){
				alert("渠道对账字段属性不能为空");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addDzColumnConf.do",
				data : "dyColumnName="+ dyColumnName +"&dyColumnProperty=" + dyColumnProperty + "&channelColumnName=" + channelColumnName +"&channelColumnProperty="+channelColumnProperty,
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
		function selectData(id,dyColumnName,dyColumnProperty,channelColumnName,channelColumnProperty){
			$("#id_u").val(id);
			$("#dyColumnName_u").val(dyColumnName);
			$("#dyColumnProperty_u").val(dyColumnProperty);
			$("#channelColumnName_u").val(channelColumnName);
			$("#channelColumnProperty_u").val(channelColumnProperty);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var id_u = $("#id_u").val();
			var dyColumnName_u = $("#dyColumnName_u").val();
			var dyColumnProperty_u = $("#dyColumnProperty_u").val();
			var channelColumnName_u = $("#channelColumnName_u").val();
			var channelColumnProperty_u = $("#channelColumnProperty_u").val();
			if(dyColumnName_u == null || dyColumnName_u == ""){
				alert("电银对账字段属性名称不能为空！");
			}
			if(dyColumnProperty_u == null || dyColumnProperty_u == ""){
				alert("电银对账字段属性不能为空！");
			}
			if(channelColumnName_u == null || channelColumnName_u == ""){
				alert("渠道对账字段属性名称不能为空！");
			}
			if(channelColumnProperty_u == null || channelColumnProperty_u == ""){
				alert("渠道对账字段属性不能为空！");
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateDzColumnConf.do",
				data : "id="+ id_u +"&dyColumnName="+dyColumnName_u +"&dyColumnProperty="+dyColumnProperty_u+"&channelColumnName="+channelColumnName_u+"&channelColumnProperty="+channelColumnProperty_u,
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
			if(confirm("确定要删除"+id+"对账字段信息数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteDzColumnConf.do",
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
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>对账字段基本信息配置</span>
			</div>
			<form action="<%=request.getContextPath()%>/queryDzColumnConf.do" target="right" name="dzColumnConfSearch" id = "dzColumnConfSearch" method="post">
			</form>		
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center" width="5%">对账字段ID</td>
							<td align="center" width="20%">电银对账字段属性名称</td>
							<td align="center" width="20%">电银对账字段属性</td>
							<td align="center" width="20%">渠道对账字段属性名称</td>
							<td align="center" width="20%">渠道对账字段属性</td>				
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDzColumnConf.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDzColumnConf.result }" var="dzColumnConf">
						<tr id="${dzColumnConf.id }" class="ssss">
							<td align="center">${dzColumnConf.id }</td>
							<td align="center">${dzColumnConf.dyColumnName }</td>
							<td align="center">${dzColumnConf.dyColumnProperty }</td>
							<td align="center">${dzColumnConf.channelColumnName }</td>
							<td align="center">${dzColumnConf.channelColumnProperty }</td>
							<td align="center" width="60">
								<a href="javascript:deleteData(${dzColumnConf.id })">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${dzColumnConf.id}','${dzColumnConf.dyColumnName}','${dzColumnConf.dyColumnProperty }','${dzColumnConf.channelColumnName }','${dzColumnConf.channelColumnProperty }')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDzColumnConf.totalPages != null}">
				<div class="next">
					<c:if test="${pageDzColumnConf.pageNo > 1}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo-2 })"><span>${pageDzColumnConf.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo-1 })"><span>${pageDzColumnConf.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDzColumnConf.pageNo }</span></a>
					<c:if test="${pageDzColumnConf.pageNo+1 <= pageDzColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo+1 })"><span>${pageDzColumnConf.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo+2 <= pageDzColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo+2 })"><span>${pageDzColumnConf.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo+3 <= pageDzColumnConf.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzColumnConf.pageNo < pageDzColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzColumnConf.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDzColumnConf.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageDzColumnConf.pageNo }" />页
							<input type="hidden" value="${pageDzColumnConf.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
			<div align="center">
				<input type="button" class="icon_normal" value="添加" onclick="addDzColumnConf();"/>
			</div>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账字段信息添加</span> 
				<a class="close" href="javascript:hide('insert');location.reload(true);">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="instInfo">					
					<tr>
						<td align="right" bgcolor="#eeeeee">电银对账字段属性名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dyColumnName" name="dyColumnName"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">电银对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dyColumnProperty" name="dyColumnProperty"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道对账字段属性名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="channelColumnName" name="channelColumnName"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="channelColumnProperty" name="channelColumnProperty"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addDzColumnConfSub()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--对账字段信息修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账字段信息修改</span> 
				<a class="close" href="javascript:hide('update');location.reload(true);">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="id_u" name="id_u" value="" />
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">电银对账字段属性名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dyColumnName_u" name="dyColumnName_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">电银对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dyColumnProperty_u" name="dyColumnProperty_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道对账字段属性名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="channelColumnName_u" name="channelColumnName_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="channelColumnProperty_u" name="channelColumnProperty_u"/>
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
