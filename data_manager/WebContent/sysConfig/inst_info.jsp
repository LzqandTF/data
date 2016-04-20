<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构信息配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function queryAllBankInstLst() {
			$.ajax({
				url : '<%=request.getContextPath()%>/queryAllBankInst.do',
				type : 'post',
				async : false,
				success : function(msg) {
					for (i in msg)
						$("#bank_select").append('<option value="' + msg[i]['bank_id'] + '">'+ msg[i]['bank_name'] + '</option>');
				}
			});
			
			var active = $("#active_hidden").val();
			var type = document.getElementById("active_select");
			
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == active){
					type.options[i].selected = 'selected';
				}
			}
			
			var typeHidden = $("#type_hidden").val();
			var instType = document.getElementById("type_select");
			
			for(var i = 0;i<instType.options.length;i++){
				if(instType.options[i].value == typeHidden){
					instType.options[i].selected = 'selected';
				}
			}
			
			var bankHidden = $("#bank_hidden").val();
			var bankId = document.getElementById("bank_select");
			
			for(var i = 0;i<bankId.options.length;i++){
				if(bankId.options[i].value == bankHidden){
					bankId.options[i].selected = 'selected';
				}
			}
			
			var page_size = $("#pageSize_hidden").val();
			if (page_size == '') {
				page_size = 10;
			}
			document.getElementById("pageSize").value = page_size;
		}
		
		//分页查询
		function paging(pageNo) {
			var form = document.getElementById("instInfoSearch");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryInstInfo.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
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
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("instInfoSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryInstInfo.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			var id1 = $("#id").val(); 
			var reg = /^\d{1,}$/;
			if(id1 != null && id1 != ""){
				var i = new Number(id1);
				if(i == 0){
					alert("渠道ID不能为0！");
					return;
				}
			}
			if(id1 != null && id1.length != 0){
				if(!reg.test(id1)){
					alert("渠道ID必须为数字！");
					return;
				}
			}
			var form = document.getElementById("instInfoSearch");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryInstInfo.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addInstInfo() {
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryAllBankInst.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg)
	    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] +'">'+ msg[i]['bank_name'] + '</option>');
	    		}
	    	});
			$("#insert").css({display:"block"});
		}
		
		// 添加渠道信息
		function addInstInfoSub(){
			var instId = $("#instId").val();
			if(instId == "" || instId == null){
				alert("渠道ID 不能为空！");
				return;
			}
			var name = $("#name").val();
			if(name == "" || name == null){
				alert("渠道名称 不能为空！");
				return;
			}
			var remark = $("#remark").val();
			var receivi_name = $("#receivi_name").val();
			if(receivi_name == "" || receivi_name == null){
				alert("收单机构名称不能为空！");
				return;
			}
			
			var error_dz_data_tableName = $("#error_dz_data_tableName").val();
			var error_original_data_tableName = $("#error_original_data_tableName").val();
			var whetherInnerDz = $("input[name='whetherInnerDz'][checked]").val();
			var whetherOuterDz = $("input[name='whetherOuterDz'][checked]").val();
			var whether_outer_error_dz = $("input[name='whether_outer_error_dz'][checked]").val();
			var whether_inner_js = $("input[name='whether_inner_js'][checked]").val();
			var whether_apply_online_tk = $("input[name='whether_apply_online_tk'][checked]").val();
			var dz_data_column_a=$("#dz_data_column_a").val();
			var gate=$("#gate").val();
			var bankftp_download = $("input[name='bankftp_download'][checked]").val();
			var bankftp_path=$("#bankftp_path").val();
			var bankftp_ip=$("#bankftp_ip").val();
			var bankftp_port=$("#bankftp_port").val();
			var bankftp_username=$("#bankftp_username").val();
			var bankftp_password=$("#bankftp_password").val();
			var whether_t_1 = $("input[name='whether_t_1'][checked]").val();
			var whether_parse_brank = $("input[name='whether_parse_brank'][checked]").val();
			
			var bankInst = $("#bank_id").val();
			var bank_id;
			var inst_type;
			
			if (bankInst == null || bankInst == "") {
				alert("银行机构不能为空！");
				return;
			} else {
				var strs= new Array(); 
				strs = bankInst.split(",");
				bank_id = strs[0];
				inst_type = strs[1];
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addInstInfo.do",
				data : "instId="+ instId +"&name=" + name + "&remark=" + remark +"&whetherInnerDz="+whetherInnerDz+"&whether_inner_js="+whether_inner_js+"&whetherOuterDz="+whetherOuterDz+"&whether_apply_online_tk="+whether_apply_online_tk+
						"&whether_outer_error_dz="+whether_outer_error_dz+"&error_dz_data_tableName="+error_dz_data_tableName+
						"&error_original_data_tableName="+error_original_data_tableName+"&receivi_name="+receivi_name+
						"&inst_type="+inst_type+"&gate="+gate+"&bankftp_download="+bankftp_download+"&bankftp_path="+bankftp_path+"&bankftp_ip="+bankftp_ip+"&bankftp_port="+bankftp_port+"&bankftp_username="+bankftp_username+
						"&bankftp_password="+bankftp_password+"&whether_t_1="+whether_t_1+"&bank_id="+bank_id+"&whether_parse_brank="+whether_parse_brank+"&dz_data_column_a="+dz_data_column_a,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						hide("insert");
						queryPreviousStep();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		
		
		// 初始化银行机构选择下拉框
		function initBankInst(bankId) {
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryAllBankInst.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			var selectObj = document.getElementById("bank_id_u");
					while(selectObj.firstChild) {
				        selectObj.removeChild(selectObj.firstChild);
					}
					if (bankId == 0) {
						$(selectObj).append("<option value=''>--请选择银行机构--</option>");
					}
	    			for (i in msg)
	    				if (msg[i]['bank_id'] == bankId) {
	    					$("#bank_id_u").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] +'" selected="selected">'+ msg[i]['bank_name'] + '</option>');
	    				} else {
	    					$("#bank_id_u").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] +'">'+ msg[i]['bank_name'] + '</option>');
	    				}
	    		}
	    	});
		}
		
		var whetherInnerDz_u ;
		var whetherOuterDz_u ;
		var whether_outer_error_dz_u;
		var whether_inner_js_u;
		var whether_apply_online_tk_u;
		var bankftp_download_u;
		var whether_t_1_u;
		var whether_parse_brank_u;
		function selectData(instId,whetherInnerDz,whetherOuterDz,whether_apply_online_tk,whether_outer_error_dz,whether_inner_js,inst_type,dz_data_column,remark,gate,bankftp_download,bankftp_path,bankftp_ip,bankftp_port,bankftp_username,bankftp_password,whether_t_1,whether_parse_brank,bank_id){
			var objId = document.getElementById(instId+","+inst_type);
			var nodeList = objId.children || objId.childNodes;
			$("#instId_u").val(nodeList[0].innerHTML);
			$("#remark_u").val(remark);
			$("#gate_u").val(gate);
			$("#bankftp_path_u").val(bankftp_path);
			$("#bankftp_ip_u").val(bankftp_ip);
			$("#bankftp_port_u").val(bankftp_port);
			$("#bankftp_username_u").val(bankftp_username);
			$("#bankftp_password_u").val(bankftp_password);
			$("#inst_type_u").val(inst_type);
			$("#dz_data_column_u").val(dz_data_column);
			$("#error_dz_data_tableName_u").val(nodeList[6].innerHTML);
			$("#error_original_data_tableName_u").val(nodeList[7].innerHTML);
			$("#receivi_name_u").val(nodeList[9].innerHTML);
			$("#name_u").val(nodeList[1].innerHTML);
 			$("input[name=whetherOuterDz_u][value="+whetherOuterDz+"]").attr("checked",true);
			$("input[name=whetherInnerDz_u][value="+whetherInnerDz+"]").attr("checked",true);
			$("input[name=whether_outer_error_dz_u][value="+whether_outer_error_dz+"]").attr("checked",true);
			$("input[name=whether_inner_js_u][value="+whether_inner_js+"]").attr("checked",true);
			$("input[name=bankftp_download_u][value="+bankftp_download+"]").attr("checked",true);
			$("input[name=whether_t_1_u][value="+whether_t_1+"]").attr("checked",true);
			$("input[name=whether_apply_online_tk_u][value="+whether_apply_online_tk+"]").attr("checked",true);
			
			$("input[name=whether_parse_brank_u][value="+whether_parse_brank+"]").attr("checked",true);
			
			whetherInnerDz_u = $("input[name='whetherInnerDz_u'][checked]").val();
			whetherOuterDz_u = $("input[name='whetherOuterDz_u'][checked]").val();
			whether_outer_error_dz_u = $("input[name='whether_outer_error_dz_u'][checked]").val();
			whether_inner_js_u = $("input[name='whether_inner_js_u'][checked]").val();
			bankftp_download_u = $("input[name='bankftp_download_u'][checked]").val();
			whether_t_1_u = $("input[name='whether_t_1_u'][checked]").val();
			whether_parse_brank_u = $("input[name='whether_parse_brank_u'][checked]").val();
			whether_apply_online_tk_u = $("input[name='whether_apply_online_tk_u'][checked]").val();
			changeFTPInfo(bankftp_download_u);
			
			initBankInst(bank_id);
			$("#update").css({display:"block"});
		}
		
		$(function(){
			$("input[name='whetherInnerDz_u']").on("click", function() {
		        // 这里需要更新
		        whetherInnerDz_u = $(this).val();
		    });
			$("input[name='whetherOuterDz_u']").on("click", function() {
		        whetherOuterDz_u = $(this).val();
		    });
			$("input[name='whether_outer_error_dz_u']").on("click", function() {
				whether_outer_error_dz_u = $(this).val();
		    });
			$("input[name='whether_inner_js_u']").on("click", function() {
				whether_inner_js_u = $(this).val();
		    });
			$("input[name='whether_t_1_u']").on("click", function() {
				whether_t_1_u = $(this).val();
		    });
			$("input[name='whether_parse_brank_u']").on("click", function() {
				whether_parse_brank_u = $(this).val();
		    });
			$("input[name='bankftp_download_u']").on("click", function() {
				bankftp_download_u = $(this).val();
		    });
			$("input[name='whether_apply_online_tk_u']").on("click", function() {
				whether_apply_online_tk_u = $(this).val();
		    });
		});
		function updateData(){
			var instId_u = $("#instId_u").val();
			var name_u = $("#name_u").val();
			var remark_u = $("#remark_u").val();
			var dz_data_column_u = $("#dz_data_column_u").val();
			var gate_u = $("#gate_u").val();
			var bankftp_path_u = $("#bankftp_path_u").val();
			var bankftp_ip_u = $("#bankftp_ip_u").val();
			var bankftp_port_u = $("#bankftp_port_u").val();
			var bankftp_username_u = $("#bankftp_username_u").val();
			var bankftp_password_u = $("#bankftp_password_u").val();
			
			if(name_u == null || name_u == ""){
				alert("渠道名称不能为空！");
			}
			if(whetherInnerDz_u == '0'){
				innerDzLevel_u = "0";
			}
			var receivi_name_u = $("#receivi_name_u").val();
			if(receivi_name_u == "" || receivi_name_u == null){
				alert("收单机构名称不能为空！");
				return;
			}
			
			var error_dz_data_tableName_u = $("#error_dz_data_tableName_u").val();
			var error_original_data_tableName_u = $("#error_original_data_tableName_u").val();
			var bankInst = $("#bank_id_u").val();
			var bank_id;
			var inst_type_u;
			if (bankInst == null || bankInst == "") {
				alert("银行机构不能为空！");
				return;
			} else {
				var strs= new Array();
				strs = bankInst.split(",");
				bank_id = strs[0];
				inst_type_u = strs[1];
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateInstInfo.do",
				data : "instId="+ instId_u +"&whetherInnerDz="+whetherInnerDz_u+"&whether_inner_js="+whether_inner_js_u+"&whetherOuterDz="+whetherOuterDz_u+"&name="+name_u+"&remark="+remark_u+"&whether_apply_online_tk="+whether_apply_online_tk_u+"&whether_outer_error_dz="+whether_outer_error_dz_u+"&error_dz_data_tableName="+error_dz_data_tableName_u+"&error_original_data_tableName="+error_original_data_tableName_u+"&receivi_name="+receivi_name_u+"&inst_type="+inst_type_u+"&dz_data_column="+dz_data_column_u+"&gate="+gate_u+"&bankftp_download="+bankftp_download_u+"&bankftp_path="+bankftp_path_u+"&bankftp_ip="+bankftp_ip_u+"&bankftp_port="+bankftp_port_u+"&bankftp_username="+bankftp_username_u+"&bankftp_password="+bankftp_password_u+"&whether_t_1="+whether_t_1_u+"&bank_id="+bank_id+"&whether_parse_brank="+whether_parse_brank_u,
				dataType : "text",
				success : function(msg) {
					if(Boolean(msg)){
						alert("修改成功");
						queryPreviousStep();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function lockOrActivateData(id,active,inst_type){
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/lockOrActivateInstInfo.do",
					data : "inst_id="+ id+"&active="+active+"&inst_type="+inst_type,
					dataType : "text",
					success : function(msg) {
						if(Boolean(msg)){
							alert("操作成功");
							checkQuery();
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		function checkInstId(instId){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/checkInstIdRepeatOrNot.do",
				data : "inst_id="+ instId,
				dataType : "text",
				success : function(msg) {
					if(msg > 0){
						alert("该渠道ID已存在，请重新填写！");
						$("#instId").html("");
					}
				}
			});
		}
		function checkDzFileNamePattern(dz_file_name){
			if(dz_file_name.indexOf("*") >=0){
				if(dz_file_name.split("*").length > 2){
					alert("对账文件名称中必须包含一个且只能包含一个*号");
					return false;
				}else if(dz_file_name.indexOf("*") == 0){
					alert("*号不能在开始");
					return false;
				}else if(dz_file_name.lastIndexOf("*")==dz_file_name.length-1){
					alert("*号不能在末尾");
					return false;
				}
			}else{
				alert("对账文件名称中必须包含一个且只能包含一个*号");
				return false;
			}
			return true;
		}
		
		function changeFTPInfo(bankFtp_download_flag){
			if(bankFtp_download_flag == 1){
				$("#bankftp_path").attr("disabled",false);
				$("#bankftp_ip").attr("disabled",false);
				$("#bankftp_port").attr("disabled",false);
				$("#bankftp_username").attr("disabled",false);
				$("#bankftp_password").attr("disabled",false);
				$("#bankftp_path_u").attr("disabled",false);
				$("#bankftp_ip_u").attr("disabled",false);
				$("#bankftp_port_u").attr("disabled",false);
				$("#bankftp_username_u").attr("disabled",false);
				$("#bankftp_password_u").attr("disabled",false);
			}else{
				$("#bankftp_path").val("");
				$("#bankftp_ip").val("");
				$("#bankftp_port").val("");
				$("#bankftp_username").val("");
				$("#bankftp_username").val("");
				$("#bankftp_path_u").val("");
				$("#bankftp_ip_u").val("");
				$("#bankftp_port_u").val("");
				$("#bankftp_username_u").val("");
				$("#bankftp_password_u").val("");
				$("#bankftp_path").attr("disabled",true);
				$("#bankftp_ip").attr("disabled",true);
				$("#bankftp_port").attr("disabled",true);
				$("#bankftp_username").attr("disabled",true);
				$("#bankftp_password").attr("disabled",true);
				$("#bankftp_path_u").attr("disabled",true);
				$("#bankftp_ip_u").attr("disabled",true);
				$("#bankftp_port_u").attr("disabled",true);
				$("#bankftp_username_u").attr("disabled",true);
				$("#bankftp_password_u").attr("disabled",true);
			}
		}
		function queryPreviousStep(){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(10);
			}
		}
		function queryByPage(eve) {
			var e = eve || window.event;
			if (e.keyCode == 13) {
				var pageNum = $("#pageNum").val();
				if (pageNum >= 1) {
					paging(pageNum);
				} else {
					paging(1);
				}
			}
		}
	</script>
</head>
<body onload="queryAllBankInstLst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>渠道配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryInstInfo.do" target="right" name="instInfoSearch" id = "instInfoSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">渠道ID</td>
					                <td nowrap="nowrap">
					                      <span class="input_bgl">
					                     	<input maxlength="10" type="text" name="id" id="id" value="${param.id }"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">渠道名称</td>
					                <td nowrap="nowrap">
					                      <span class="input_bgl">
					                     	<input type="text" name="name_select" id = "name_select" value="${param.name_select }" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="active_select" name="active_select" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">禁用</option>
												<option value="1">开通</option>
											</select>
											<input type="hidden" id="active_hidden" value="${active }" />
					                     </span>
					                </td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap">渠道类型</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="type_select" name="type_select" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">线下</option>
												<option value="1">线上</option>
											</select>
											<input type="hidden" id="type_hidden" value="${instType }" />
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">网关</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="bank_select" name="bank_select" style="width: 150px;">
					                     		<option value="">全部</option>
											</select>
											<input type="hidden" id="bank_hidden" value="${bankId }" />
					                     </span>
					                </td>
								</tr>
								<tr>
						            <td colspan="8" align="center" style="height: 30px"> 
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
										<input type="button" class="icon_normal" value="添加" onclick="addInstInfo();"/>
						            </td>
					            </tr>
							</table>
						</center>
					</div>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(pageInstInfo.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageInstInfo.result) > 0 }">${fn:length(pageInstInfo.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageInstInfo.totalItems == null }">0</c:if>
					<c:if test="${pageInstInfo.totalItems != null }">${pageInstInfo.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageInstInfo.totalPages == null}">0</c:if>
					<c:if test="${pageInstInfo.totalPages != null}">${pageInstInfo.totalPages}</c:if>
				</font>页
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>
					每页显示
				<%-- 	<c:if test="${not empty pageSize }">
						<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="${pageSize }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					</c:if>
					<c:if test="${empty pageSize }">
						<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					</c:if> --%>
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
					<input type="hidden" id="pageSize_hidden" value="${pageSize }"/>
					条
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">渠道ID</td>
							<td align="center">渠道名称</td>
							<td align="center">是否需要内部对账</td>
							<td align="center">是否支持联机退款</td>
							<td align="center">是否需要外部对账</td>
							<td align="center">是否需要外部差错对账</td>
							<td align="center" width="10%" style="display: none">差错对账文件数据表名称</td>
							<td align="center" width="10%" style="display: none">差错原始交易数据表名称</td>
							<td align="center">是否内部结算</td>
							<td align="center">收单机构名称</td>
							<td align="center">网关号</td>	
							<td align="center">状态</td>
							<td align="center" width="8%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageInstInfo.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageInstInfo.result }" var="instInfo">
						<tr id="${instInfo.instId },${instInfo.inst_type}" class="ssss" onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${instInfo.instId }</td>
							<td align="center">${instInfo.name }</td>
							<td align="center">
								<c:if test="${instInfo.whetherInnerDz == 1}">是</c:if>
								<c:if test="${instInfo.whetherInnerDz == 0}">否</c:if>
							</td>
							<td align="center">
								<c:if test="${instInfo.whether_apply_online_tk==0 }">否</c:if>
								<c:if test="${instInfo.whether_apply_online_tk==1 }">是</c:if>
							</td>
							<td align="center">
								<c:if test="${instInfo.whetherOuterDz == 1}">是</c:if>
								<c:if test="${instInfo.whetherOuterDz == 0}">否</c:if>
							</td>
							<td align="center">
								<c:if test="${instInfo.whether_outer_error_dz == 1}">是</c:if>
								<c:if test="${instInfo.whether_outer_error_dz == 0}">否</c:if>
							</td>
							<td align="center" style="display: none">${instInfo.error_dz_data_tableName }</td>
							<td align="center" style="display: none">${instInfo.error_original_data_tableName }</td>
							<td align="center">
								<c:if test="${instInfo.whether_inner_js == 1}">是</c:if>
								<c:if test="${instInfo.whether_inner_js == 0}">否</c:if>
							</td>
							<td align="center">${instInfo.receivi_name }</td>
							<td align="center">${instInfo.gate }</td>
							<td align="center">
								<c:if test="${instInfo.active==0 }">关闭 </c:if>
								<c:if test="${instInfo.active==1 }">开通</c:if>
							</td>
							<td align="center" width="60">
								<a href="javascript:lockOrActivateData('${instInfo.instId }','${instInfo.active }','${instInfo.inst_type }')">
									<c:if test="${instInfo.active==1 }">禁用</c:if>
									<c:if test="${instInfo.active==0 }">开通</c:if>
								</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${instInfo.instId}','${instInfo.whetherInnerDz}','${instInfo.whetherOuterDz}','${instInfo.whether_apply_online_tk}','${instInfo.whether_outer_error_dz}','${instInfo.whether_inner_js}','${instInfo.inst_type }','${instInfo.dz_data_column }','${instInfo.remark }','${instInfo.gate }','${instInfo.bankftp_download }','${instInfo.bankftp_path }','${instInfo.bankftp_ip }','${instInfo.bankftp_port }','${instInfo.bankftp_username }','${instInfo.bankftp_password }','${instInfo.whether_t_1 }', '${instInfo.whether_parse_brank }','${instInfo.bank_id }')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageInstInfo.totalPages != null}">
				<div class="next">
					<c:if test="${pageInstInfo.pageNo > 1}">
						<a href="javascript:paging(${pageInstInfo.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageInstInfo.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageInstInfo.pageNo-2 > 0}">
						<a href="javascript:paging(${pageInstInfo.pageNo-2 })"><span>${pageInstInfo.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageInstInfo.pageNo-1 > 0}">
						<a href="javascript:paging(${pageInstInfo.pageNo-1 })"><span>${pageInstInfo.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageInstInfo.pageNo }</span></a>
					<c:if test="${pageInstInfo.pageNo+1 <= pageInstInfo.totalPages}">
						<a href="javascript:paging(${pageInstInfo.pageNo+1 })"><span>${pageInstInfo.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageInstInfo.pageNo+2 <= pageInstInfo.totalPages}">
						<a href="javascript:paging(${pageInstInfo.pageNo+2 })"><span>${pageInstInfo.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageInstInfo.pageNo+3 <= pageInstInfo.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageInstInfo.pageNo < pageInstInfo.totalPages}">
						<a href="javascript:paging(${pageInstInfo.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b>
						<span>共${pageInstInfo.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${pageInstInfo.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="insert" class="pop" style="display: none;OVERFLOW: auto;margin-top: 10px;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">渠道配置信息添加</span> 
				<a class="close" href="javascript:queryPreviousStep();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="instInfo">					
					<tr>
						<td width="160" align="right" bgcolor="#eeeeee">渠道ID：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="instId" name="instId" onblur="checkInstId(this.value);" value=""
						       onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">渠道名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="name" name="name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否需要内部对账：</td>
						<td>
							<span> 
								<input type="radio" id="whetherInnerDz" name="whetherInnerDz"  value="1"/> 是
								<input type="radio" id="whetherInnerDz" name="whetherInnerDz"  checked="checked" value="0"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否需要外部对账：</td>
						<td>
							<span> 
								<input type="radio" id="whetherOuterDz" name="whetherOuterDz" checked="checked" value="1"/> 是
								<input type="radio" id="whetherOuterDz" name="whetherOuterDz" value="0"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否需要外部差错对账：</td>
						<td>
							<span> 
								<input type="radio" id="whether_outer_error_dz" name="whether_outer_error_dz"  value="1"/> 是
								<input type="radio" id="whether_outer_error_dz" name="whether_outer_error_dz" checked="checked" value="0"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">收单机构名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="receivi_name" name="receivi_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">差错对账文件数据表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_dz_data_tableName" name="error_dz_data_tableName"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">差错原始交易数据表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_original_data_tableName" name="error_original_data_tableName"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否内部结算：</td>
						<td>
							<span> 
								<input type="radio" id="whether_inner_js" name="whether_inner_js" checked="checked" value="1"/> 是
								<input type="radio" id="whether_inner_js" name="whether_inner_js" value="0"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">备注：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="remark" name="remark"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">融易付网关号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="gate" name="gate" value="0"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否到银行FTP下载对账单：</td>
						<td>
						   	<span> 
								<input type="radio" name="bankftp_download" value="1" onclick="changeFTPInfo(this.value);"/> 是
								<input type="radio" name="bankftp_download" value="0" checked="checked"  onclick="changeFTPInfo(this.value);"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_path" name="bankftp_path"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">银行FTP ip地址：</td>
						<td>
						   <span class="input_bgl">
						       <input type="text" id="bankftp_ip" name="bankftp_ip" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP端口：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_port" name="bankftp_port" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">银行FTP用户名：</td>
						<td>
						   <span class="input_bgl">
						       <input type="text" id="bankftp_username" name="bankftp_username" />
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_password" name="bankftp_password" />
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">日期是否减一：</td>
						<td>
						   	<span> 
								<input type="radio" name="whether_t_1" value="1"/> 是
								<input type="radio" name="whether_t_1" value="0" checked="checked"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否解析品牌服务费：</td>
						<td>
						   	<span> 
								<input type="radio" name="whether_parse_brank" value="1"/> 是
								<input type="radio" name="whether_parse_brank" value="0" checked="checked"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">所属银行机构：</td>
						<td>
						    <span class="input_bgl">
						        <select id="bank_id" name="bank_id">
						        	<option value="">--请选择银行机构--</option>
								</select>
					   		</span>
					   		<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件数据匹配字段：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_data_column_a" name="dz_data_column_a"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否支持联机退款：</td>
						<td>
						   	<span> 
								<input type="radio" name="whether_apply_online_tk" value="1"/> 是
								<input type="radio" name="whether_apply_online_tk" value="0" checked="checked"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addInstInfoSub()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:queryPreviousStep();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none;OVERFLOW: auto;margin-top: 10px;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">渠道配置信息修改</span> 
				<a class="close" href="javascript:hide('update');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td width="160" align="right" bgcolor="#eeeeee">渠道ID：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="instId_u" name="instId_u" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">渠道名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="name_u" name="name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否需要内部对账：</td>
						<td>
							<span> 
								<input type="radio"  name="whetherInnerDz_u"  value="1" /> 是
								<input type="radio"  name="whetherInnerDz_u"  value="0" /> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否需要外部对账：</td>
						<td>
							<span> 
								<input type="radio"  name="whetherOuterDz_u" value="1"/> 是
								<input type="radio"  name="whetherOuterDz_u" value="0"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否需要外部差错对账：</td>
						<td>
							<span> 
								<input type="radio"  name="whether_outer_error_dz_u" value="1"/> 是
								<input type="radio"  name="whether_outer_error_dz_u" value="0"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">收单机构名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="receivi_name_u" name="receivi_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">差错对账文件数据表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_dz_data_tableName_u" name="error_dz_data_tableName_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">差错原始交易数据表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_original_data_tableName_u" name="error_original_data_tableName_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否内部结算：</td>
						<td>
							<span> 
								<input type="radio" id="whether_inner_js_u" name="whether_inner_js_u" checked="checked" value="1"/> 是
								<input type="radio" id="whether_inner_js_u" name="whether_inner_js_u" value="0"/> 否
							</span>
						</td>
						<td align="right" bgcolor="#eeeeee">备注：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="remark_u" name="remark_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">融易付网关号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="gate_u" name="gate_u" value="0"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否到银行FTP下载对账单：</td>
						<td>
						   	<span> 
								<input type="radio" name="bankftp_download_u" value="1" onclick="changeFTPInfo(this.value);"/> 是
								<input type="radio" name="bankftp_download_u" value="0" onclick="changeFTPInfo(this.value);"/> 否
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_path_u" name="bankftp_path_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">银行FTP ip地址：</td>
						<td>
						   <span class="input_bgl">
						       <input type="text" id="bankftp_ip_u" name="bankftp_ip_u" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP端口：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_port_u" name="bankftp_port_u" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">银行FTP用户名：</td>
						<td>
						   <span class="input_bgl">
						       <input type="text" id="bankftp_username_u" name="bankftp_username_u" />
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bankftp_password_u" name="bankftp_password_u" />
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">日期是否减一：</td>
						<td>
					   	<span> 
							<input type="radio" name="whether_t_1_u" value="1"/> 是
							<input type="radio" name="whether_t_1_u" value="0"/> 否
						</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否解析品牌服务费：</td>
						<td>
					   	<span> 
							<input type="radio" name="whether_parse_brank_u" value="1"/> 是
							<input type="radio" name="whether_parse_brank_u" value="0"/> 否
						</span>
						</td>
						<td align="right" bgcolor="#eeeeee">所属银行机构：</td>
						<td>
					    <span class="input_bgl">
					        <select id="bank_id_u" name="bank_id_u">
								<%-- <c:if test="${empty bankInstLst}">
									<option value="">--请选择银行机构--</option>
								</c:if>
								<c:forEach items="${bankInstLst}" var="bankInst">
								   <option value="${bankInst.bank_id},${bankInst.bank_type}">${bankInst.bank_name }</option>
								</c:forEach> --%>
					        </select>
				   		</span>
					   	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件数据匹配字段：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_data_column_u" name="dz_data_column_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否支持联机退款：</td>
						<td>
						   <span> 
								<input type="radio" name="whether_apply_online_tk_u" value="1"/> 是
								<input type="radio" name="whether_apply_online_tk_u" value="0"/> 否
							</span>
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
</body>
</html>
