<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行机构维护</title>
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
	function paging(pageNo) {
		var form = document.getElementById("bankInstSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankInstLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function queryByPageSize(pageSize) {
		var form = document.getElementById("bankInstSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankInstLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function checkQuery(){
		var form = document.getElementById("bankInstSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryBankInstLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	
	function EnterPress(eve){ //传入 event
		var e = eve || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSize(pageSize);
			}else {
				queryByPageSize(10);
			}
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
	
	function addBankInst() {
		$("#insert").css({display:"block"});
	}
	
	function addBankInstSub() {
		var bank_name = $("#bank_name").val();
		var parse_dz_file_class = $("#parse_dz_file_class").val();
		var dz_data_tableName = $("#dz_data_tableName").val();
		var ftp_dz_file_path = $("#ftp_dz_file_path").val();
		var dz_file_path = $("#dz_file_path").val();
		var dz_file_name_pattern = $("#dz_file_name_pattern").val();
		var start_row = $("#start_row").val();
		var isTk = $("#isTk").val();
		var tk_type = $("#tk_type").val();
		var tk_context = $("#tk_context").val();
		var tk_column = $("#tk_column").val();
		var original_data_tableName = $("#original_data_tableName").val();
		var riqie_original_tableName = $("#riqie_original_tableName").val();
		var inst_entity_name = $("#inst_entity_name").val();
		var bank_entity_name = $("#bank_entity_name").val();
		var bank_type = $("#bank_type").val();
		var whether_outer_dz = $("#whether_outer_dz").val();
		var trade_dz_impl_class = $("#trade_dz_impl_class").val();
		var tk_tableName = $("#tk_tableName").val();
		
		if (bank_name == null || bank_name == "") {
			alert("银行机构名称不能为空！");
			return;
		}
		if (parse_dz_file_class == null || parse_dz_file_class == "") {
			alert("对账文件解析类不能为空！");
			return;
		}
		if (dz_data_tableName == null || dz_data_tableName == "") {
			alert("对账文件存储表不能为空！");
			return;
		}
		if (ftp_dz_file_path == null || ftp_dz_file_path == "") {
			alert("对账文件获取地址不能为空！");
			return;
		}
		if (dz_file_path == null || dz_file_path == "") {
			alert("存在系统目录地址不能为空！");
			return;
		}
		if (dz_file_name_pattern == null || dz_file_name_pattern == "") {
			alert("对账文件格式不能为空！");
			return;
		}
		if (start_row == null || start_row == "") {
			alert("对账文件解析开始行数不能为空！");
			return;
		}
		if (isTk == null || isTk == "") {
			alert("是否标记退款不能为空！");
			return;
		}
		if (original_data_tableName == null || original_data_tableName == "") {
			alert("原始交易表名不能为空！");
			return;
		}
		if (inst_entity_name == null || inst_entity_name == ""){
			alert("渠道实体名称不能为空！");
			return;
		}
		if (bank_entity_name == null || bank_entity_name == "") {
			alert("对账实体名称不能为空！");
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addBankInst.do",
			data : "bank_name="+bank_name+"&parse_dz_file_class="+parse_dz_file_class+"&dz_data_tableName="
					+dz_data_tableName+"&ftp_dz_file_path="+ftp_dz_file_path+"&dz_file_path="+dz_file_path+
					"&dz_file_name_pattern="+dz_file_name_pattern+"&start_row="+start_row+"&isTk="+isTk+"&tk_type="
					+tk_type+"&tk_context="+tk_context+"&tk_column="+tk_column+"&original_data_tableName="+original_data_tableName+
					"&riqie_original_tableName="+riqie_original_tableName+"&inst_entity_name="+inst_entity_name+"&bank_type="+bank_type+
					"&whether_outer_dz="+whether_outer_dz+"&bank_entity_name="+bank_entity_name+"&trade_dz_impl_class="+trade_dz_impl_class+
					"&tk_tableName="+tk_tableName,
			dataType : "text",
			beforeSend:function(){
				document.getElementById("loading-mask").style.display="";
		        document.getElementById("loading").style.display="";
			},
			complete:function(){
				document.getElementById("loading-mask").style.display="none";
		        document.getElementById("loading").style.display="none";
			},
			success : function(msg) {
				if (msg == '1') {
					alert("添加银行机构成功！");
					hide("insert");
					checkQuery();
				} else if (msg == '2') {
					alert("银行机构已存在！");
					return;
				} else {
					alert("添加银行机构失败！");
					return;
				}
			}
		});
	}
	
	function selectData(bank_id, bank_name, parse_dz_file_class, dz_data_tableName, ftp_dz_file_path, dz_file_name_pattern,
			dz_file_path, start_row, isTk, tk_type, tk_context, tk_column, original_data_tableName, riqie_original_tableName, inst_entity_name,
			bank_type, whether_outer_dz, bank_entity_name,trade_dz_impl_class,tk_tableName) {
		$("#bank_id").val(bank_id);
		$("#u_bank_name").val(bank_name);
		$("#u_parse_dz_file_class").val(parse_dz_file_class);
		$("#u_dz_data_tableName").val(dz_data_tableName);
		$("#u_ftp_dz_file_path").val(ftp_dz_file_path);
		$("#u_dz_file_name_pattern").val(dz_file_name_pattern);
		$("#u_dz_file_path").val(dz_file_path);
		$("#u_start_row").val(start_row);
		$("#u_isTk").val(isTk);
		$("#u_tk_type").val(tk_type);
		$("#u_tk_context").val(tk_context);
		$("#u_tk_column").val(tk_column);
		$("#u_original_data_tableName").val(original_data_tableName);
		$("#u_riqie_original_tableName").val(riqie_original_tableName);
		$("#u_inst_entity_name").val(inst_entity_name);
		$("#u_bank_entity_name").val(bank_entity_name);
		$("#u_bank_type").val(bank_type);
		$("#u_whether_outer_dz").val(whether_outer_dz);
		$("#u_trade_dz_impl_class").val(trade_dz_impl_class);
		$("#u_tk_tableName").val(tk_tableName);
		
		$("#update").css({display:"block"});
	}
	
	function updateBankInstSub() {
		var bank_id = $("#bank_id").val();
		var bank_name = $("#u_bank_name").val();
		var parse_dz_file_class = $("#u_parse_dz_file_class").val();
		var dz_data_tableName = $("#u_dz_data_tableName").val();
		var ftp_dz_file_path = $("#u_ftp_dz_file_path").val();
		var dz_file_name_pattern = $("#u_dz_file_name_pattern").val();
		var dz_file_path = $("#u_dz_file_path").val();
		var start_row = $("#u_start_row").val();
		var isTk = $("#u_isTk").val();
		var tk_type = $("#u_tk_type").val();
		var tk_context = $("#u_tk_context").val();
		var tk_column = $("#u_tk_column").val();
		var original_data_tableName = $("#u_original_data_tableName").val();
		var riqie_original_tableName = $("#u_riqie_original_tableName").val();
		var inst_entity_name = $("#u_inst_entity_name").val();
		var bank_entity_name = $("#u_bank_entity_name").val();
		var bank_type = $("#u_bank_type").val();
		var whether_outer_dz = $("#u_whether_outer_dz").val();
		var trade_dz_impl_class = $("#u_trade_dz_impl_class").val();
		var tk_tableName = $("#u_tk_tableName").val();
		
		if (bank_name == null || bank_name == "") {
			alert("银行机构名称不能为空！");
			return;
		}
		if (parse_dz_file_class == null || parse_dz_file_class == "") {
			alert("对账文件解析类不能为空！");
			return;
		}
		if (dz_data_tableName == null || dz_data_tableName == "") {
			alert("对账文件存储表不能为空！");
			return;
		}
		if (ftp_dz_file_path == null || ftp_dz_file_path == "") {
			alert("对账文件获取地址不能为空！");
			return;
		}
		if (dz_file_path == null || dz_file_path == "") {
			alert("存在系统目录地址不能为空！");
			return;
		}
		if (dz_file_name_pattern == null || dz_file_name_pattern == "") {
			alert("对账文件格式不能为空！");
			return;
		}
		if (start_row == null || start_row == "") {
			alert("对账文件解析开始行数不能为空！");
			return;
		}
		if (isTk == null || isTk == "") {
			alert("是否标记退款不能为空！");
			return;
		}
		if (original_data_tableName == null || original_data_tableName == "") {
			alert("原始交易表名不能为空！");
			return;
		}
		if (inst_entity_name == null || inst_entity_name == "") {
			alert("渠道实体名称不能为空！");
			return;
		}
		if (bank_entity_name == null || bank_entity_name == "") {
			alert("对账实体名称不能为空！");
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateBankInstByBankId.do",
			data : "bank_id="+bank_id+"&bank_name="+bank_name+"&parse_dz_file_class="+parse_dz_file_class+"&dz_data_tableName="
					+dz_data_tableName+"&ftp_dz_file_path="+ftp_dz_file_path+"&dz_file_path="+dz_file_path+
					"&dz_file_name_pattern="+dz_file_name_pattern+"&start_row="+start_row+"&isTk="+isTk+"&tk_type="
					+tk_type+"&tk_context="+tk_context+"&tk_column="+tk_column+"&original_data_tableName="+original_data_tableName+
					"&riqie_original_tableName="+riqie_original_tableName+"&inst_entity_name="+inst_entity_name+"&bank_type="+bank_type+
					"&whether_outer_dz="+whether_outer_dz+"&bank_entity_name="+bank_entity_name+"&trade_dz_impl_class="+trade_dz_impl_class+
					"&tk_tableName="+tk_tableName,
			dataType : "text",
			success : function(msg) {
				if (msg == '1') {
					alert("修改银行机构成功！");
					hide("update");
					checkQuery();
				} else if (msg == '2') {
					alert("银行机构已存在！");
					return;
				} else {
					alert("修改银行机构失败！");
					return;
				}
			}
		});
	}
	
	function deleteBankInst(bank_id) {
		if(confirm("删除该银行机构会删除该机构下的所有扣款渠道?")){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/deleteBankInstByBankId.do",
				data : "bank_id="+bank_id,
				dataType : "text",
				success : function(msg) {
					if (msg == '1') {
						alert("删除银行机构成功！");
						checkQuery();
					} else {
						alert("删除银行机构失败！");
						return;
					}
				}
			});
		}
	}
	
	function init() {
		var bank_type = $("#bank_type_hidden").val();
		var bankType = document.getElementById("bank_type_select");
		for(var i = 0;i<bankType.options.length;i++){
			if(bankType.options[i].value == bank_type){
				bankType.options[i].selected = 'selected';
			}
		}
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
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>银行机构维护</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryBankInstLst.do" target="right" name="bankInstSearch" id = "bankInstSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>银行机构名称：</b>
							<span class="input_bgl">
								<input type="text" name="bankName" id="bankName" value="${param.bankName }"/>
							</span>
						</li>
						<li>
							<b>网关类型：</b>
							<span class="input_bgl">
								<select id="bank_type_select" name="bank_type_select">
									<option value="">全部</option>
									<option value="1">线上</option>
									<option value="0">线下</option>
								</select>
								<input type="hidden" id="bank_type_hidden" value="${bank_type }"/>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addBankInst();"/>
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
					<c:if test="${fn:length(pageLst.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageLst.result) > 0 }">${fn:length(pageLst.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageLst.totalItems == null }">0</c:if>
					<c:if test="${pageLst.totalItems != null }">${pageLst.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageLst.totalPages == null}">0</c:if>
					<c:if test="${pageLst.totalPages != null}">${pageLst.totalPages}</c:if>
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
							<td align="center">银行机构编号</td>
							<td align="center">银行机构名称</td>
							<td align="center">对账文件格式</td>
							<td align="center">对账文件解析开始行数</td>
							<td align="center">是否标记退款</td>
							<td align="center" width="8%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageLst.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageLst.result }" var="bankInst">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">
								<c:if test="${bankInst.bank_id  < 10}">
									000${bankInst.bank_id}
								</c:if>
								<c:if test="${bankInst.bank_id  >= 10}">
									00${bankInst.bank_id}
								</c:if>
							</td>
							<td align="center">${bankInst.bank_name }</td>
							<td align="center">${bankInst.dz_file_name_pattern }</td>
							<td align="center">${bankInst.start_row }</td>
							<td align="center">
								<c:if test="${bankInst.isTk == 0}">否</c:if>
								<c:if test="${bankInst.isTk == 1}">是</c:if>
							</td>
							<td>
								<a class="fl lj mr10" href="#" onclick="selectData('${bankInst.bank_id }', '${bankInst.bank_name }', '${bankInst.parse_dz_file_class }',
								'${bankInst.dz_data_tableName }','${bankInst.ftp_dz_file_path }','${bankInst.dz_file_name_pattern }', '${bankInst.dz_file_path }',
								'${bankInst.start_row }', '${bankInst.isTk }', '${bankInst.tk_type }', '${bankInst.tk_context }', '${bankInst.tk_column }', '${bankInst.original_data_tableName }', 
								'${bankInst.riqie_original_tableName }', '${bankInst.inst_entity_name }', '${bankInst.bank_type }', '${bankInst.whether_outer_dz }', '${bankInst.bank_entity_name }',
								'${bankInst.trade_dz_impl_class }', '${bankInst.tk_tableName }');">修改</a>
								<a class="fl lj mr10" href="#" onclick="deleteBankInst('${bankInst.bank_id}')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageLst.totalPages != null}">
				<div class="next">
					<c:if test="${pageLst.pageNo > 1}">
						<a href="javascript:paging(${pageLst.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageLst.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageLst.pageNo-2 > 0}">
						<a href="javascript:paging(${pageLst.pageNo-2 })"><span>${pageLst.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageLst.pageNo-1 > 0}">
						<a href="javascript:paging(${pageLst.pageNo-1 })"><span>${pageLst.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageLst.pageNo }</span></a>
					<c:if test="${pageLst.pageNo+1 <= pageLst.totalPages}">
						<a href="javascript:paging(${pageLst.pageNo+1 })"><span>${pageLst.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageLst.pageNo+2 <= pageLst.totalPages}">
						<a href="javascript:paging(${pageLst.pageNo+2 })"><span>${pageLst.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageLst.pageNo+3 <= pageLst.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageLst.pageNo < pageLst.totalPages}">
						<a href="javascript:paging(${pageLst.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b>
						<span>共${pageLst.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${pageLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!-- 新增操作弹出框 -->
	<div id="insert" class="pop" style="display: none;OVERFLOW: auto;margin-top: 10px;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">添加银行机构</span> 
				<a class="close" href="javascript:void(0);" onclick="hide('insert')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="bankInst">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">银行机构编号：</td>
						<td><font style="color: grey">(系统自增,从0001开始)</font></td>
						<td align="right" bgcolor="#eeeeee">银行机构名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bank_name" name="bank_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件解析类：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="parse_dz_file_class" name="parse_dz_file_class"/>
						   	</span>
						  	 <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件存储表：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="dz_data_tableName" name="dz_data_tableName"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件获取地址：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="ftp_dz_file_path" name="ftp_dz_file_path"/>
						   	</span>
						  	 <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">存在系统目录地址：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="dz_file_path" name="dz_file_path"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件格式：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="dz_file_name_pattern" name="dz_file_name_pattern"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件解析开始行数：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="start_row" name="start_row" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否标记退款：</td>
						<td>
						  <span class="input_bgl">
					        <select id="isTk" name="isTk">
					        	<option value="1">标记</option>
					       		<option value="0">不标记</option>
							</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件退款类型：</td>
						<td>
						  <span class="input_bgl">
					        <select id="tk_type" name="tk_type">
					        	<option value="0" selected="selected">表示未知(不处理)</option>
					        	<option value="1">按金额正负</option>
					       		<option value="2">按字段内容</option>
							</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">退款字段内容：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="tk_context" name="tk_context"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">退款字段列数：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="tk_column" name="tk_column" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">原始交易表名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="original_data_tableName" name="original_data_tableName"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">日切表名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="riqie_original_tableName" name="riqie_original_tableName"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道实体名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inst_entity_name" name="inst_entity_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账实体名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="bank_entity_name" name="bank_entity_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否外部对账：</td>
						<td>
						  <span class="input_bgl">
					        <select id="whether_outer_dz" name="whether_outer_dz">
					        	<option value="1">是</option>
					       		<option value="0">否</option>
							</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">网关类型：</td>
						<td>
						  <span class="input_bgl">
					        <select id="bank_type" name="bank_type">
					        	<option value="1">线上网关</option>
					       		<option value="0">线下网关</option>
							</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账实现类：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="trade_dz_impl_class" name="trade_dz_impl_class"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">退款表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="tk_tableName" name="tk_tableName"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="addBankInstSub();" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('insert');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 修改银行机构信息弹出框 -->
	<div id="update" class="pop" style="display: none;OVERFLOW: auto;margin-top: 10px;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">修改银行机构</span> 
				<a class="close" href="javascript:void(0);" onclick="hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="bankInst">					
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">银行机构编号：</td>
						<td>
							<font style="color: grey">(系统自增,从0001开始)</font>
							<input type="hidden" id="bank_id" />
						</td>
						<td align="right" bgcolor="#eeeeee">银行机构名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_bank_name" name="u_bank_name" readonly="readonly"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件解析类：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_parse_dz_file_class" name="u_parse_dz_file_class"/>
						   	</span>
						  	 <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件存储表：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_dz_data_tableName" name="u_dz_data_tableName"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件获取地址：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_ftp_dz_file_path" name="u_ftp_dz_file_path"/>
						   	</span>
						  	 <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">存在系统目录地址：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_dz_file_path" name="u_dz_file_path"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件格式：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_dz_file_name_pattern" name="u_dz_file_name_pattern"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件解析开始行数：</td>
						<td>
							<span class="input_bgl">
						        <input type="text" id="u_start_row" name="u_start_row" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   	</span>
						  	<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否标记退款：</td>
						<td>
						  <span class="input_bgl">
					        <select id="u_isTk" name="u_isTk">
					        	<option value="1">标记</option>
					       		<option value="0">不标记</option>
							</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件退款类型：</td>
						<td>
						  <span class="input_bgl">
					        <select id="u_tk_type" name="u_tk_type">
					        	<option value="0" selected="selected">表示未知(不处理)</option>
					        	<option value="1">按金额正负</option>
					       		<option value="2">按字段内容</option>
							</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">退款字段内容：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_tk_context" name="u_tk_context"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">退款字段列数：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_tk_column" name="u_tk_column" onkeyup="value=value.replace(/[^\d]/g,'')"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">原始交易表名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_original_data_tableName" name="u_original_data_tableName"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">日切表名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_riqie_original_tableName" name="u_riqie_original_tableName"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道实体名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_inst_entity_name" name="u_inst_entity_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账实体名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_bank_entity_name" name="u_bank_entity_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否外部对账：</td>
						<td>
						  <span class="input_bgl">
					        <select id="u_whether_outer_dz" name="u_whether_outer_dz">
					        	<option value="1">是</option>
					       		<option value="0">否</option>
							</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">网关类型：</td>
						<td>
						  <span class="input_bgl">
					        <select id="u_bank_type" name="u_bank_type">
					        	<option value="1">线上网关</option>
					       		<option value="0">线下网关</option>
							</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账实现类：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_trade_dz_impl_class" name="u_trade_dz_impl_class"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">退款表名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="u_tk_tableName" name="u_tk_tableName"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="updateBankInstSub();" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('update');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
