<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mcc大类数据查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("mccBigTypeSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryMccBigTypePage.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("mccBigTypeSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryMccBigTypePage.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
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
		
		//分页
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
		
		function checkQuery(){	
			document.getElementById("mccBigTypeSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addHandling() {	
			$("#insert").css({display:"block"});
		}
		
		function addHandlingSub(){
			var type_name = $("#type_name_insert").val();
			if(type_name == null || type_name == ""){
				alert("Mcc类型名称不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addMccBigType.do",
				data : "type_name="+ type_name,
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
		function selectData(id,type_name){
			$("#id_update").val(id);
			$("#type_name_update").val(type_name);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var id = $("#id_update").val();
			var type_name = $("#type_name_update").val();
			if(type_name == null || type_name == ""){
				alert("Mcc类型名称不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateMccBigType.do",
				data : "big_type_id="+ id +"&type_name="+type_name,
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
		function deleteData(big_type_id,name){
			if(confirm("确定要删除"+name+"类型的Mcc数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteMccBigType.do",
					data : "big_type_id="+ big_type_id,
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
	</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>Mcc大类数据查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMccBigTypePage.do" target="right" name="mccBigTypeSearch" id = "mccBigTypeSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>Mcc类型名称</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="type_name" id = "type_name" value="${param.type_name }" />
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
					<%-- <input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					<input type="hidden" id="pageSize_hidden" value="${pageSize }"/> --%>
					
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
							<td align="center" width="20%">ID</td>
							<td align="center" width="50%">Mcc类型名称</td>
							<td align="center" width="30%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="mccType">
						<tr>
							<td align="center">${mccType.big_type_id }</td>
							<td align="center">${mccType.type_name }</td>
							<td align="center" width="60">
								<a href="javascript:deleteData(${mccType.big_type_id},'${mccType.type_name }')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData(${mccType.big_type_id},'${mccType.type_name }')">修改</a>
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
				<span class="fl">Mcc类型添加</span> 
				<a class="close" href="javascript:hide('insert')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="instInfo">					
					<tr>
						<td align="right" bgcolor="#eeeeee">Mcc类型名称：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="type_name_insert" name="type_name_insert" /> 
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
				<span class="fl">Mcc类型修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">编号：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" disabled="disabled" id="id_update" name="id_update"  value="" /> 
							</span>
						</td>
					</tr>
					<tr>
						<td width="140" align="right" bgcolor="#eeeeee">Mcc类型名称：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="type_name_update" name="type_name_update"  value="" /> 
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
