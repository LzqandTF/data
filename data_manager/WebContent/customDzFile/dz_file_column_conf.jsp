<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账字段配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("dzFileColumnConfSearch");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryDzFileColumnConf.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("dzFileColumnConfSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryDzFileColumnConf.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			var dz_column_id = $("#dz_column_id_").val(); 
			var reg = /^\d{1,}$/;
			if(dz_column_id != null && dz_column_id != ""){
				var i = new Number(dz_column_id);
				if(i == 0){
					alert("对账字段ID不能为0！");
					return;
				}
			}
			if(dz_column_id != null && dz_column_id.length != 0){
				if(!reg.test(dz_column_id)){
					alert("对账字段ID必须为数字！");
					return;
				}
			}
			var form = document.getElementById("dzFileColumnConfSearch");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryDzFileColumnConf.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addDzFileColumnConf() {	
			$("#insert").css({display:"block"});
		}
		
		
		function unionCheckDzColumnName(obj){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/unionCheckDzColumnName.do",
				data : "attribute_name=" + obj.value,
				dataType : "text",
				success : function(msg) {
					if(msg == "true"){
						alert("对账字段名称已经存在不能再次添加");
						$(obj).val("");
						$(obj).select();
					}
				}
			});
		}
		
		function unionCheckDzColumnattr(obj){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/unionCheckDzColumnattr.do",
				data : "attribute_column=" + obj.value,
				dataType : "text",
				success : function(msg) {
					if(msg == "true"){
						alert("对账字段属性已经存在不能再次添加");
						$(obj).val("");
						$(obj).select();
					}
				}
			});
		}
		
		
		function addDzFileColumnConfSub(){
			var attribute_name = $("#attribute_name").val();
			var attribute_column = $("#attribute_column").val();
			var attribute_type = $("#attribute_type").val();
			var column_length = $("#column_length").val();
			var attribute_column_online = $("#attribute_column_online").val();
			var attribute_column_online_refund = $("#attribute_column_online_refund").val();
			if(attribute_name == null || attribute_name == ""){
				alert("对账字段名称不能为空！");
				return;
			}
			if(attribute_column == "" || attribute_column == null){
				alert("对账字段属性不能为空！");
				return;
			}
			if(attribute_type == "" || attribute_type == null){
				alert("对账字段来源不能为空！");
				return;
			}
			if(column_length == "" || column_length == null){
				alert("字段长度不能为空！");
				return;
			}
			var column_length_ = new Number(column_length);
			if(column_length_ == 0){
				alert("字段长度不能为0");
				return;
			}

			var attribute_name_length = getLength(attribute_name);
			if(attribute_name_length >= column_length_){
				alert("字段长度必须大于对账字段属性名称的长度");
				return;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addDzFileColumnConf.do",
				data : "attribute_name=" + attribute_name + "&attribute_column=" + attribute_column +"&attribute_type="+attribute_type+"&column_length="+column_length+"&attribute_column_online="+attribute_column_online+"&attribute_column_online_refund="+attribute_column_online_refund,
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
		function selectData(dz_column_id,attribute_name,attribute_column,attribute_type,column_length,attribute_column_online,attribute_column_online_refund){
			$("#dz_column_id_u").val(dz_column_id);
			$("#attribute_name_u").val(attribute_name);
			$("#attribute_column_u").val(attribute_column);
			$("#column_length_u").val(column_length);
			$("#attribute_column_online_u").val(attribute_column_online);
			$("#attribute_column_online_refund_u").val(attribute_column_online_refund);
			var type = document.getElementById("attribute_type_u");
			
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == attribute_type){
					type.options[i].selected = 'selected';
				}
			}
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var dz_column_id_u = $("#dz_column_id_u").val();
			var attribute_name_u = $("#attribute_name_u").val();
			var attribute_column_u = $("#attribute_column_u").val();
			var attribute_type_u = $("#attribute_type_u").val();
			var column_length_u = $("#column_length_u").val();
			var attribute_column_online_u = $("#attribute_column_online_u").val();
			var attribute_column_online_refund_u = $("#attribute_column_online_refund_u").val();
			if(attribute_name_u == null || attribute_name_u == ""){
				alert("对账字段名称不能为空！");
				return;
			}
			if(attribute_column_u == "" || attribute_column_u == null){
				alert("对账字段属性不能为空！");
				return;
			}
			if(attribute_type_u == "" || attribute_type_u == null){
				alert("对账字段来源不能为空！");
				return;
			}
			if(column_length_u == "" || column_length_u == null){
				alert("对账字段长度不能为空！");
				return;
			}
			var column_length_ = new Number(column_length_u);
			if(column_length_ == 0){
				alert("字段长度不能为0");
				return;
			}
			var attribute_name_length = getLength(attribute_name_u);
			if(attribute_name_length >= column_length_){
				alert("字段长度必须大于对账字段属性名称的长度");
				return;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateDzFileColumnConf.do",
				data : "dz_column_id="+ dz_column_id_u +"&attribute_name="+attribute_name_u+"&attribute_column="+attribute_column_u+"&attribute_type="+attribute_type_u+"&column_length="+column_length_u+"&attribute_column_online="+attribute_column_online_u+"&attribute_column_online_refund="+attribute_column_online_refund_u,
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
					url : "<%=request.getContextPath()%>/deleteDzFileColumnConf.do",
					data : "dz_column_id="+ id,
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
		
		function getLength(str){  
		    var realLength = 0;  
		    for (var i = 0; i < str.length; i++)   
		    {  
		        charCode = str.charCodeAt(i);  
		        if (charCode >= 0 && charCode <= 128)   
		        realLength += 1;  
		        else   
		        realLength += 2;  
		    }  
		    return realLength;  
		}  
		function init(){
			var attribute_type = $("#attribute_type_h").val();
			var attribute_type_s = document.getElementById("attribute_type_");
			
			for(var i = 0;i<attribute_type_s.options.length;i++){
				if(attribute_type_s.options[i].value == attribute_type){
					attribute_type_s.options[i].selected = 'selected';
				}
			}
		}
	</script>
</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>对账字段配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryDzFileColumnConf.do" target="right" name="dzFileColumnConfSearch" id = "dzFileColumnConfSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>对账字段ID：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="dz_column_id_" id="dz_column_id_" value="${param.dz_column_id_ }"/>
							</span>
						</li>
						<li>
							<b>对账字段名称：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="attribute_name_" id = "attribute_name_" value="${param.attribute_name_ }" />
							</span>
						</li>
						<li>
							<b>字段类型：</b>
							<span class="input_bgl">
								<input type="hidden" id="attribute_type_h" name="attribute_type_h" value="${attribute_type_ }" />
								<select name="attribute_type_" id = "attribute_type_" >
									<option value="">全部</option>
									<option value="1">对账总表字段</option>
									<option value="2">差错总表字段</option>
									<option value="3">内部清算总表字段</option>
									<option value="4">公用字段</option>
									<option value="5">结算单字段</option>
								</select>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addDzFileColumnConf();"/>
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
					<c:if test="${fn:length(pageDzFileColumnConf.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageDzFileColumnConf.result) > 0 }">${fn:length(pageDzFileColumnConf.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageDzFileColumnConf.totalItems == null }">0</c:if>
					<c:if test="${pageDzFileColumnConf.totalItems != null }">${pageDzFileColumnConf.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageDzFileColumnConf.totalPages == null}">0</c:if>
					<c:if test="${pageDzFileColumnConf.totalPages != null}">${pageDzFileColumnConf.totalPages}</c:if>
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
							<td align="center" width="5%">对账字段ID</td>
							<td align="center" width="15%">对账字段名称</td>
							<td align="center" width="15%">对账字段属性</td>
							<td align="center" width="15%">对账字段来源</td>
							<td align="center" width="10%">对账长度</td>
							<td align="center" width="10%">对应的线上收款交易对账字段属性</td>
							<td align="center" width="10%">对应的线上退款交易对账字段属性</td>
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDzFileColumnConf.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDzFileColumnConf.result }" var="dzFileColumnConf">
						<tr id="${dzFileColumnConf.dz_column_id }" class="ssss">
							<td align="center">${dzFileColumnConf.dz_column_id }</td>
							<td align="center">${dzFileColumnConf.attribute_name }</td>
							<td align="center">${dzFileColumnConf.attribute_column }</td>
							<td align="center">
								<c:if test="${dzFileColumnConf.attribute_type == 1 }">对账总表字段</c:if>
								<c:if test="${dzFileColumnConf.attribute_type == 2 }">差错总表字段</c:if>
								<c:if test="${dzFileColumnConf.attribute_type == 3 }">内部清算文件字段</c:if>
								<c:if test="${dzFileColumnConf.attribute_type == 4 }">公用字段</c:if>
								<c:if test="${dzFileColumnConf.attribute_type == 5 }">结算单字段</c:if>
							</td>
							<td align="center">${dzFileColumnConf.column_length }</td>
							<td align="center">${dzFileColumnConf.attribute_column_online }</td>
							<td align="center">${dzFileColumnConf.attribute_column_online_refund }</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:selectData('${dzFileColumnConf.dz_column_id}','${dzFileColumnConf.attribute_name}','${dzFileColumnConf.attribute_column}','${dzFileColumnConf.attribute_type}','${dzFileColumnConf.column_length }','${dzFileColumnConf.attribute_column_online }','${dzFileColumnConf.attribute_column_online_refund }')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:deleteData('${dzFileColumnConf.dz_column_id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDzFileColumnConf.totalPages != null}">
				<div class="next">
					<c:if test="${pageDzFileColumnConf.pageNo > 1}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo-2 })"><span>${pageDzFileColumnConf.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo-1 })"><span>${pageDzFileColumnConf.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDzFileColumnConf.pageNo }</span></a>
					<c:if test="${pageDzFileColumnConf.pageNo+1 <= pageDzFileColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo+1 })"><span>${pageDzFileColumnConf.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo+2 <= pageDzFileColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo+2 })"><span>${pageDzFileColumnConf.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo+3 <= pageDzFileColumnConf.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzFileColumnConf.pageNo < pageDzFileColumnConf.totalPages}">
						<a href="javascript:paging(${pageDzFileColumnConf.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDzFileColumnConf.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${pageDzFileColumnConf.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">对账字段信息添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="DzFileColumnConf">					
					<tr>
						<td align="right" bgcolor="#eeeeee">对账字段名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_name" name="attribute_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="unionCheckDzColumnName(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column" name="attribute_column" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="unionCheckDzColumnattr(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账字段来源：</td>
						<td>
						   <span class="input_bgl">
						   		<select id="attribute_type" name="attribute_type">
									<option value="1">对账总表字段</option>
									<option value="2">差错总表字段</option>
									<option value="3">内部清算文件字段</option>
									<option value="4">公用字段</option>
									<option value="5">结算单字段</option>
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账字段长度：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="column_length" maxlength="2" onkeyup="value=value.replace(/[^\d]/g,'')" name="column_length"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对应的线下收款交易对账字段属性值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_online"  name="attribute_column_online"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对应的线下退款交易对账字段属性值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_online_refund"  name="attribute_column_online_refund"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addDzFileColumnConfSub()" />
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
				<span class="fl">对账字段信息修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">对账字段名称：</td>
						<td>
						   <span class="input_bgl">
						   	   <input style="display: none" id="dz_column_id_u" name="dz_column_id_u" value=""/> 
						       <input type="text" id="attribute_name_u" name="attribute_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账字段属性：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_u" name="attribute_column_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账字段来源：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="attribute_type_u" name="attribute_type_u">
									<option value="1">对账总表字段</option>
									<option value="2">差错总表字段</option>
									<option value="3">内部差错文件字段</option>
									<option value="4">公用字段</option>
									<option value="5">结算单字段</option>
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账字段长度：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="column_length_u" name="column_length_u" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对应的线下收款交易对账字段属性值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_online_u" name="attribute_column_online_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对应的线下退款交易对账字段属性值：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="attribute_column_online_refund_u" name="attribute_column_online_refund_u"/>
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
