<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义对账文件配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery_sp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/js.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

		var handler_type = 0;
		var isFlag = 0;
		function paging(pageNo) {
			var form = document.getElementById("objectRelevanceColumnSearch");
			var pageSize = $("#pageSize").val();
			var object_id = $("#object_id").val();
			var file_type = $("#file_type").val();
			if(object_id == 0){
				alert("请选择系统接口");
				return;
			}
			if(file_type == 0){
				alert("请选择文件类型");
				return;
			}
			with (form) {
				action = "<%=request.getContextPath()%>/queryObjectRelevanceColumn.do?pageNum=" + pageNo + "&numPerPage=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("objectRelevanceColumnSearch");
			var object_id = $("#object_id").val();
			var file_type = $("#file_type").val();
			if(object_id == 0){
				alert("请选择系统接口");
				return;
			}
			if(file_type == 0){
				alert("请选择文件类型");
				return;
			}
			with (form) {
				action = "<%=request.getContextPath()%>/queryObjectRelevanceColumn.do?numPerPage=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			var form = document.getElementById("objectRelevanceColumnSearch");
			var pageSize = $("#pageSize").val();
			var object_id = $("#object_id").val();
			var file_type = $("#file_type").val();
			if(object_id == 0){
				alert("请选择系统接口");
				return;
			}
			if(file_type == 0){
				alert("请选择文件类型");
				return;
			}
			with (form) {
				action = "<%=request.getContextPath()%>/queryObjectRelevanceColumn.do?numPerPage=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		/* function setColumn() {	
			$("#insert").css({display:"block"});
		} */
		function setData(id,rule_id,attribute_column) {
			$("#id_hidden").val(id);
			var tag = 0;
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryRuleList.do',
	    		type : 'post',
	    		async : false,
	    		data : "attribute_column="+ attribute_column,
	    		success : function(msg) {
	    			if(msg != null && msg != ""){
	    				for (i in msg){
	    					$("#rule_id").append('<option value="' + msg[i]['rule_id'] + '">'+ msg[i]['rule_description'] + '</option>');
	    				}
	    			}else{
    					alert("该字段没有配置任何规则");
    					tag = 1;
    				}
	    		},
	    		error : function(msg) {
	    			alert("获取规则失败!");
	    		}
	    	});
			if(tag == 0){
				var type = document.getElementById("rule_id");
				
				for(var i = 0;i<type.options.length;i++){
					if(type.options[i].value == rule_id){
						type.options[i].selected = 'selected';
					}
				}
				$("#setRule").css({display:"block"});
			}
		}
		
		function setRuleData(){
			var id = $("#id_hidden").val();
			var rule_id = $("#rule_id").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateObjectRelevanceColumn.do",
				data : "id="+ id +"&rule_id="+rule_id,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("setRule");
					}
				}
			});
		}
		function selectData(show_attribute_name,id){
			$("#show_name").val(show_attribute_name);
			$("#show_id_hidden").val(id);
			$("#updateShowName").css({display:"block"});
		}
		function updateShowNameData(){
			var show_attribute_name = $("#show_name").val();
			var id = $("#show_id_hidden").val();
			if(show_attribute_name == null || show_attribute_name == ""){
				alert("显示名称不能为空！");
				return;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateObjectRelevanceColumn.do",
				data : "id=" + id + "&show_attribute_name=" + show_attribute_name,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
    jQuery(function ($) {
		//上移
		var right=$("#selectedColumn");
		$("#moveUp").click(function(){
			moveUp(right);
		});
		//下移
		$("#moveDown").click(function(){
			moveDown();
		});
		
		//上移
		function moveUp(right){
			var so = right.find("option[selected]");
			if(so.length!=0){
				if(so.get(0).index!=0){
					so.each(function(){
						$(this).insertBefore($(this).prev());
					});
					handler_type = 5;
					isFlag = 1;
				}
			}
		}
		
		 /**单元素向下移动*/  
		function  moveDown(){     
		    var selectRight=document.getElementsByName("selectedColumn")[0];  
		    var i = document.getElementsByName("selectedColumn")[0].selectedIndex;
		    if (i != selectRight.length-1){     
		        var j = i+1;     
		        if(i < selectRight.length){     
		            var Temp_Text = selectRight[j].text;     
		            var Temp_ID = selectRight[j].value;     
		      
		            selectRight[j].text = selectRight[i].text;     
		            selectRight[j].value = selectRight[i].value;     
		      
		            selectRight[i].text = Temp_Text;     
		            selectRight[i].value = Temp_ID;     
		      
		            selectRight.selectedIndex=j;    
		            handler_type = 5;
		            isFlag = 1;
		        }     
		    }     
		}
    }); 
    
    var dz_column_id = new Array();
    jQuery.noConflict();
	jQuery(function ($) {
		var right=$("#selectedColumn");
		$(document).ready(function () {
			//左移右边选中的
			$("#delete_bt").click(function(){
				dz_column_id.push($("#selectedColumn option:selected").val());
				removeFromRight(right);
				handler_type = 1;
			});
			//右移左边选中的
			$("#select_bt").click(function(){
				leftToRight();
				dz_column_id.push($("#allColumn option:selected").val());
				handler_type = 2;
			});
			//右移左边全部的
			$("#selectAll_bt").click(function(){
				moveAllOption();
				handler_type = 3;
				isFlag = 1;
			});
			//右移左边全部的
			$("#deleteAll_bt").click(function(){
				clearAllOption();
				handler_type = 4;
				isFlag = 1;
			});
			
		});
	});
	function changeSelect(dz_column_name){
		$("#allColumn").empty();
		var file_type = $("#file_type").val();
		$.ajax({
    		url : '<%=request.getContextPath()%>/loadDzFileColumnConf.do',
    		data : "dz_column_name="+ dz_column_name+"&file_type="+file_type,
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg){
    				$("#allColumn").append('<option value="' + msg[i]['dz_column_id'] + '">'+ msg[i]['attribute_name'] + '</option>');
    			}
    		}
    	});
	}
	function submitData(){
		if(handler_type == 0){
			alert("请修改操作后再确认...");
			return;
		}
		
		var object_id = $("#object_id").val();
		var file_type = $("#file_type").val();
		var selectedColumn = new Array();
		var j =0;
		$("#insert").find("#selectedColumn").find("option").each(function(){
			//alert($(this).val());
			selectedColumn[j] = $(this).val();
			j++;
		});
		
		//3/4/5/6 后台处理是一样的
		//if(isFlag != 0)
		handler_type = 3;
		
		/**
			object_id:对象ID
			selectedColumn:右边框的所有option的value
			file_type:文件类型
			handler_type:处理类型 1：删除、2：添加、3：添加所有、4：删除所有、5：上移、6：下移
			dz_column_id:需要删除/添加  存放对账字段ID
			*/
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addObjectRelevanceColumn.do",
			data : "object_id="+ object_id + "&selectedColumn=" + selectedColumn +"&file_type="+file_type+"&handler_type="+handler_type+"&dz_column_id="+dz_column_id,
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
	
	function setColumn(){
		changeSelect("");
		/* var object_id = $("#object_id_hidden_select").val();
		var type = document.getElementById("object_id");
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == object_id){
				type.options[i].selected = 'selected';
			}
		} */
		var fileType = $("#file_type").val();
		var object_id = $('#object_id option:selected').val();
		if(object_id == null || object_id == 0){
			alert("请选择系统名称");
			return;
		}
		setObjectColumn(fileType,object_id);
		$("#insert").css({display:"block"});
	}
	
	function setObjectColumn(fileType,objectId){
		$("#selectedColumn").empty();
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryObjectColumnList.do',
    		data : "object_id="+ objectId+"&file_type="+fileType,
    		type : 'post',
    		dataType: 'text',
    		success : function(msg) {
    			var data_json = eval("("+msg+")");
    			for(var i=0;i<data_json.length;i++){
    				$("#selectedColumn").append('<option value="' + data_json[i].dz_column_id + '-'+data_json[i].rule_id+'">'+ data_json[i].attribute_name + '</option>');
    			}
    		}
    	});
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
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
		handler_type = 0;
		isFlag = 0;
	}
	
	function loadCustomObjectInfo(file_category){
		var object_ids = document.getElementById("object_id");
		
		var object_ids_length = object_ids.options.length;
		for(var i = object_ids_length-1;i>0;i--){
			object_ids.options[i].remove();
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryCustomObjectByFileType.do',
    		data : "file_category="+ file_category,
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (j in msg)
    				$("#object_id").append('<option value="' + msg[j]['object_id'] + '">'+ msg[j]['object_name'] + '</option>');
    		}
    	});
		var file_types = document.getElementById("file_type");
		var file_types_length = file_types.options.length;
		for(var k = file_types_length -1 ;k>0;k--){
			file_types.options[k].remove();
		}
		if(file_category == 2){
			$("#file_type").append('<option value="5">结算文件</option>');
		}else if(file_category == 1){
			$("#file_type").append('<option value="1">对账文件总表 </option>');
			$("#file_type").append('<option value="2">差错文件总表 </option>');
		}else{
			$("#file_type").append('<option value="3">内部清算文件总表 </option>');
		}
	}
	
	function init(){
		var file_category = $("#file_category_hidden").val();
		if(file_category != ""){
			loadCustomObjectInfo(file_category);
		} 
		
		var file_categorys = document.getElementById("file_category");
		for(var i = 0;i<file_categorys.options.length;i++){
			if(file_categorys.options[i].value == file_category){
				file_categorys.options[i].selected = 'selected';
			}
		}
		
		var object_id = $("#object_id_hidden_select").val();
		var object_ids = document.getElementById("object_id");
		
		for(var i = 0;i<object_ids.options.length;i++){
			if(object_ids.options[i].value == object_id){
				object_ids.options[i].selected = 'selected';
			}
		}
		
		var file_type = $("#file_type_hidden").val();
		var file_types = document.getElementById("file_type");
		
		for(var i = 0;i<file_types.options.length;i++){
			if(file_types.options[i].value == file_type){
				file_types.options[i].selected = 'selected';
			}
		}
	}
</script> 
</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>自定义对账文件配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryObjectRelevanceColumn.do" target="right" name="objectRelevanceColumnSearch" id = "objectRelevanceColumnSearch" method="post">
					<ul class="check-m">
						<li>
							<b>文件类别:</b>
							<span class="input_bgl">
								<select id="file_category" name="file_category" onblur="loadCustomObjectInfo(this.value);">
									<option value="1">对账文件</option>
									<option value="2">结算文件</option>
									<option value="3">内部清算文件</option>
								</select>
								<input type="hidden" id="file_category_hidden" value="${file_category }" /> 
							</span>
						</li>						
						<li>
							<b>系统名称:</b>
							<span class="input_bgl">
								<select id="object_id" name="object_id">
									<option value="0">全部</option>
								</select>
								<input type="hidden" class="icon_normal" id="object_id_hidden_select" name="object_id_hidden_select" value="${object_id }" /> 
							</span>
						</li>
						<li>
							<b>文件类型:</b>
							<span class="input_bgl">
								<select id="file_type" name="file_type">
									<option value="0">全部</option>
								</select> 
								<input type="hidden" id="file_type_hidden" value="${file_type }" /> 
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="配置字段" onclick="setColumn();"/>
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
					<c:if test="${fn:length(pageObjectRelevanceColumn.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageObjectRelevanceColumn.result) > 0 }">${fn:length(pageObjectRelevanceColumn.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageObjectRelevanceColumn.totalItems == null }">0</c:if>
					<c:if test="${pageObjectRelevanceColumn.totalItems != null }">${pageObjectRelevanceColumn.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageObjectRelevanceColumn.totalPages == null}">0</c:if>
					<c:if test="${pageObjectRelevanceColumn.totalPages != null}">${pageObjectRelevanceColumn.totalPages}</c:if>
				</font>页
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>
					每页显示
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
							<td align="center" width="5%">序号</td>
							<td align="center" width="15%">对账文件字段名称</td>
							<td align="center" width="15%">对账文件字段属性</td>
							<td align="center" width="20%">显示名称</td>
							<td align="center" width="10%">规则序号</td>
							<td align="center" width="15%">来源</td>
							<td align="center" width="20%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageObjectRelevanceColumn.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageObjectRelevanceColumn.result }" var="objectRelevance">
						<tr id="${objectRelevance.id }" class="ssss">
							<td align="center">${objectRelevance.id }</td>
							<td align="center">${objectRelevance.attribute_name }</td>
							<td align="center">${objectRelevance.attribute_column }</td>
							<td align="center">${objectRelevance.show_attribute_name }</td>
							<td align="center">
								<c:if test="${objectRelevance.rule_id==0 }">无</c:if>
								<c:if test="${objectRelevance.rule_id!=0 }">${objectRelevance.rule_id }</c:if>
								
							</td>
							<td align="center">
								<c:if test="${objectRelevance.attribute_type==0 }">原始交易数据</c:if>
								<c:if test="${objectRelevance.attribute_type==1 }">对账单数据</c:if>
							</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:setData('${objectRelevance.id}','${objectRelevance.rule_id}','${objectRelevance.attribute_column }')">设置规则</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:selectData('${objectRelevance.show_attribute_name }','${objectRelevance.id }')">更改显示名称</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageObjectRelevanceColumn.totalPages != null}">
				<div class="next">
					<c:if test="${pageObjectRelevanceColumn.pageNo > 1}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo-2 > 0}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo-2 })"><span>${pageObjectRelevanceColumn.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo-1 > 0}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo-1 })"><span>${pageObjectRelevanceColumn.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageObjectRelevanceColumn.pageNo }</span></a>
					<c:if test="${pageObjectRelevanceColumn.pageNo+1 <= pageObjectRelevanceColumn.totalPages}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo+1 })"><span>${pageObjectRelevanceColumn.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo+2 <= pageObjectRelevanceColumn.totalPages}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo+2 })"><span>${pageObjectRelevanceColumn.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo+3 <= pageObjectRelevanceColumn.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageObjectRelevanceColumn.pageNo < pageObjectRelevanceColumn.totalPages}">
						<a href="javascript:paging(${pageObjectRelevanceColumn.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<%-- <b><span>共${pageObjectRelevanceColumn.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageObjectRelevanceColumn.pageNo }" />页
							<input type="hidden" value="${pageObjectRelevanceColumn.pageNo}" id="pageNo" />
					</span></b> --%>
					<b>
						<span>共${pageObjectRelevanceColumn.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${pageObjectRelevanceColumn.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">配置字段</span> 
				<a class="close" href="javascript:hide('insert');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">  
					<tr>  
					   <td>
					   		<span>可选文件字段:</span><br/>
					   		<span>
					   			<input type="text" id="dz_column_" name="" value=""/>
					   			<input type="button" id="dz_column_select" name="dz_column_select" value="查询" onclick='changeSelect($("#dz_column_").val());'/>
					   		</span><br/>
						   	<select id="allColumn" name="allColumn" size="15" multiple style="width: 200px;	height:	300px">
						    </select>
					   </td>  
					   <td>
					    	<input type="button" name="select_bt" id="select_bt" style = "width :	80;" value="添加 " /><br/>
					    	<input type="button" name="delete_bt" id="delete_bt" style = "width :	80;" value="删除 " /><br/>
					    	<input type="button" name="selectAll_bt" id="selectAll_bt" style = "width :	80;" value="添加全部 " /><br/>
					    	<input type="button" name="deleteAll_bt" id="deleteAll_bt" style = "width :	80;" value="删除全部 " /><br/>
						</td>
						<td>
							<span>已选文件字段</span><br/>
							<select	id="selectedColumn" name="selectedColumn" size="3" style="width: 200px;	height:	300px" Multiple>
									<%-- <c:forEach items="${pageObjectRelevanceColumn.result }" var="object_relevance">
										<option value="${object_relevance.dz_column_id }">${object_relevance.attribute_name }</option>
									</c:forEach> --%>
							</select>
						</td>
						<td>
							<input type="button" name="moveUp" id="moveUp" value="上移" /><br/>
							<input type="button" name="moveDown" id="moveDown" value="下移" /><br/>
						</td>
					</tr>  
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="submitData()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('insert');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--设置规则-->
	<div id="setRule" class="pop" style="display: none">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">设置规则</span> 
				<a class="close" href="javascript:hide('setRule');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">可选规则：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="rule_id" name="rule_id">
						       		<option value="0">无</option>
								</select> 
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="hidden" id="id_hidden" value=""/>
							<input type="button" class="icon_normal" value="提交" onclick="setRuleData()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('setRule')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--修改显示名称<-->
	<div id="updateShowName" class="pop" style="display: none">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">修改显示名称</span> 
				<a class="close" href="javascript:hide('updateShowName');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">显示名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="show_name" onkeyup="value=value.replace(/[\s]/g,'')" name="show_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="hidden" id="show_id_hidden" value=""/>
							<input type="button" class="icon_normal" value="提交" onclick="updateShowNameData()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('updateShowName')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
