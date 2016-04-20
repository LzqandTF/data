<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结算商户号对应表</title>
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
		var form = document.getElementById("settleMerchantConfigSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageSettleMerchantConfig.do?pageNum=" + pageNo;
			method = "post";
			form.submit();
		}
	}
	
	function checkQuery(){	
		document.getElementById("settleMerchantConfigSearch").submit();
	}

	function hide(obj) {
		var o = document.getElementById(obj);
		var inputs= o.getElementsByTagName('input');
		for(var i = 0;i<inputs.length;i++){
			if($(inputs[i]).attr("type") == "text"){
				$(inputs[i]).val("");
			}
		} 
		$("#mer_code_").empty();
		$("#mer_code_").append('<option value="">---请选择---</option>');
		o.style.display = "none";
	}
	
	function addSetleMerchant(){
		$("#insert").css({display:"block"});
	}
	
	function changeSelect(mer_code){
		$.ajax({
    		url : '<%=request.getContextPath()%>/vagueQueryMerchantInfoByMerCode.do',
    		data : "mer_code="+ mer_code,
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			if(msg.length != null && msg.length != ""){
    				$("#mer_code_").empty();
    				for (i in msg){
 	    				$("#mer_code_").append('<option value="' + msg[i]['mer_abbreviation'] + '">'+ msg[i]['mer_code'] + '</option>');
						if(i == 0){
							$("#mer_name").val(msg[i]['mer_abbreviation']);
    					}
	    			}
    			}
    		}
    	});
	}
	function showMerchantMsg(mer_name){
		$("#mer_name").val(mer_name);
	}
	
	function addSettleMerConfigCommit(){
		var settle_mer_code = $("#mer_code_").find("option:selected").text();
		var check_flag = true;
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryConfigCountBySettleMerCode.do",
			data : "settle_mer_code="+ settle_mer_code,
			dataType : "text",
			async: false,
			success : function(msg) {
				if ("0" != msg) {
					check_flag = false;
					alert("该结算商户号已配置过,不可重复添加");
					return false;
				}
			}
		});
		if(check_flag){
			var mer_name = $("#mer_code_").val();
			if(settle_mer_code == null || settle_mer_code == '' || mer_name == null || mer_name == ''){
				alert("请选择结算商户号");
				return false;
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/insertSettleMerchantConfig.do",
				data : "settle_mer_code="+ settle_mer_code + "&settle_mer_name=" + mer_name ,
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
					if ("1" == msg) {
						alert("添加成功");
						$("#insert").css({display:"none"});
						checkQuery();
					}else {					
						alert("添加失败！");
						checkQuery();
					}
				}
			});
		}
	}
	
	function deleteData(settle_mer_code){
		if(confirm("确定要进行此操作吗?")){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/deleteSettleMerchantConfig.do",
				data : "settle_mer_code="+ settle_mer_code,
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
	
	function setData(settle_mer_code){
		$("#settle_mer_code_show").html(settle_mer_code);
		$("#settleMerCode").val(settle_mer_code);
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerchantMatch.do",
			data : "settle_mer_code="+ settle_mer_code,
			dataType : "text",
			success : function(msg) {
				if(msg=='' || msg == null){
					$("#merBody > tr").each(function () {
						 var num = $("#merBody tr").length;
						 if(num > 1){
							 this.remove(); 
						 }
					 });
					 $("#merBody").find("#mer_code").each(function(){
						 $(this).val("");
					 });
					 $("#dy_mer_code_count").html(0);
				}else{
					var obj = eval("("+msg+")"); 
					$("#dy_mer_code_count").html(obj.length);
					if(obj.length == 0){
						$("#merBody > tr").each(function () {
							 var num = $("#merBody tr").length;
							 if(num > 1){
								 this.remove(); 
							 }
						 });
						 $("#merBody").find("#mer_code").each(function(){
							 $(this).val("");
						 });
					}else{
						$("#merBody > tr").each(function () {
							this.remove(); 
						});
						for(var i=0;i<obj.length;i++){
							$("#merBody").append('<tr><td align="center" width="50%"><input type="hidden" id="mer_code_update" name="mer_code_update" value="' + obj[i]['dy_mer_code'] + '" /><input type="text" id="mer_code" name="mer_code" value="' + obj[i]['dy_mer_code'] + '" onkeyup="value=value.replace(/[\^\\d]/g,\'\')" onblur="if(!checkMerCode(this))this.value=\'\';" /><input type="hidden" id="operateType" value="0" /></td><td align="center" width="50%"><input type="button" value="添加" id="addColumn" name="addColumn" onclick="addColumnForMerCode();"/><input type="button" value="删除" id="delColumn" name="delColumn" onclick="delColumnForMerCode(this);"/></td></tr>');
						}
					}
				}
			}
		});
		
		
		
		$("#merBody > tr").each(function () {
			 var num = $("#merBody tr").length;
			 if(num > 1){
				 this.remove(); 
			 }
		 });
		 $("#merBody").find("#mer_code").each(function(){
			 $(this).val("");
		 });
		$("#setInnerMerCode").css({display:"block"});
	}

	function addColumnForMerCode(){
		var num = $("#merBody tr").length;
		if(num == 100){
			return;
		}
		var tr = $("#merBody_hidden tr").eq(0).clone();   
	    tr.appendTo("#merBody");
	}
	
	var data_array = new Array();
	var data_index = 0;
	
	function delColumnForMerCode(input){
		var num = $("#merBody tr").length;
		if(num == 1){
			return;
		}
		
		var del_mer_code = $(input).parent().parent().find("#mer_code").val();
		var operateType = $(input).parent().parent().find("#operateType").val();
		if(del_mer_code != null && del_mer_code != '' && operateType != 1){
			data_array[data_index] = del_mer_code + ":" + "2";
			data_index++;
		}
		
		$(input).parent().parent().remove();
	}
	
	
	
	function commit(){
		var settle_mer_code = $("#settleMerCode").val();
		var merCode = new Array();
		var i =0 ;
		var flag = true;
		$("#merBody").find("#mer_code").each(function(){
			if($(this).val() == null || $(this).val() == ""){
				flag = false;
				alert("内部商户号不能为空！");
				return false;
				return;
			}else{
				var add_mer_code = $(this).val();
				merCode[i] = add_mer_code + ":" + $(this).parent().find("#operateType").val() + ":" + $(this).parent().find("#mer_code_update").val();
				i++;
			}
		});
		if(data_array != null && data_array.length > 0){
			for(var t=0;t<data_array.length;t++){
				merCode[i] = data_array[t];
				i++;
			}
		}
		if(flag){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addSettleMerchantMatch.do",
				data : "merCode="+ merCode + "&settle_mer_code=" + settle_mer_code,
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
					if (msg >= 0) {
						alert("添加成功");
						$("#setInnerMerCode").css({display:"none"});
						checkQuery();
					}else {					
						alert("添加失败！");
						checkQuery();
					}
				}
			});
		}
	}
	
	function checkMerCode(obj){
		
		var merCode = $(obj).val();
		
		if($(obj).parent().find("#mer_code_update").val() != null && $(obj).parent().find("#mer_code_update").val() != ""){
			$(obj).parent().find("#operateType").val("3");//修改
		}
		
		var pattern = new RegExp("^[0-9]{3,20}$");
		if(!pattern.test(merCode)){
			alert("电银商户号必须为3到20位数字！");
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
	
	function uniqueCode(code){
		var flag = false;
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerchantMatchCount.do",
			data : "code="+code,
			dataType : "text",
			async: false,
			success : function(msg) {
				if(msg != "0"){
					alert("号码已经存在不能再次添加");
				}else{
					flag = true;
				}
			}
		});
		return flag;
	}
	
</script> 
</head>
<body>
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
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>结算商户对应表</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageSettleMerchantConfig.do" target="right" name="settleMerchantConfigSearch" id = "settleMerchantConfigSearch" method="post">
					<ul class="check-m">
						<li>
							<b>结算商户号:</b>
							<span class="input_bgl">
								<input id="settle_mer_code" name="settle_mer_code" onkeyup="value=value.replace(/[^\d]/g,'')" value="${param.settle_mer_code }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addSetleMerchant();"/>
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
					<c:if test="${fn:length(pageDataList.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(pageDataList.result) > 0 }">${fn:length(pageDataList.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${pageDataList.totalItems == null }">0</c:if>
					<c:if test="${pageDataList.totalItems != null }">${pageDataList.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${pageDataList.totalPages == null}">0</c:if>
					<c:if test="${pageDataList.totalPages != null}">${pageDataList.totalPages}</c:if>
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
							<td align="center" width="15%">结算商户号</td>
							<td align="center" width="25%">结算商户名称</td>
							<td align="center" width="20%">操作时间</td>
							<td align="center" width="25%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataList.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataList.result }" var="data">
						<tr class="ssss">
							<td align="center">${data.rownum }</td>
							<td align="center">${data.settle_mer_code }</td>
							<td align="center">${data.settle_mer_name }</td>
							<td align="center">${data.operate_time }</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:setData('${data.settle_mer_code}')">配置对应内部商户号</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a style="color: red" href="javascript:deleteData('${data.settle_mer_code }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDataList.totalPages != null}">
				<div class="next">
					<c:if test="${pageDataList.pageNo > 1}">
						<a href="javascript:paging(${pageDataList.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDataList.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataList.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDataList.pageNo-2 })"><span>${pageDataList.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDataList.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDataList.pageNo-1 })"><span>${pageDataList.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDataList.pageNo }</span></a>
					<c:if test="${pageDataList.pageNo+1 <= pageDataList.totalPages}">
						<a href="javascript:paging(${pageDataList.pageNo+1 })"><span>${pageDataList.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDataList.pageNo+2 <= pageDataList.totalPages}">
						<a href="javascript:paging(${pageDataList.pageNo+2 })"><span>${pageDataList.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDataList.pageNo+3 <= pageDataList.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataList.pageNo < pageDataList.totalPages}">
						<a href="javascript:paging(${pageDataList.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b>
						<span>共${pageDataList.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${pageDataList.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<!--添加结算商户号<-->
	<div id="insert" class="pop" style="display: none">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">修改显示名称</span> 
				<a class="close" href="javascript:hide('insert');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">		
					<tr>
						<td align="right" bgcolor="#eeeeee">结算商户号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="settlt_merchant_code" name="settlt_merchant_code" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="changeSelect(this.value);" value=""/>
						   </span>
						   <span class="input_bgl"> 
						       <select id="mer_code_" name="mer_code_" onclick="showMerchantMsg(this.value);">
									<option value="">--请选择--</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>			
					<tr>
						<td align="right" bgcolor="#eeeeee">结算商户名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="mer_name" readonly="readonly" name="mer_name"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="hidden" id="show_id_hidden" value=""/>
							<input type="button" class="icon_normal" value="确定" onclick="addSettleMerConfigCommit()" />
							<input type="button" class="icon_normal" value="取消" onclick="hide('insert')" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--配置内部商户号-->
	<div id="setInnerMerCode" class="pop" style="display: none;OVERFLOW: auto;">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">配置内部商户号</span> 
				<a class="close" href="javascript:hide('setInnerMerCode');">&nbsp;</a>
			</h1>
			<div>
				结算商户号:&nbsp;&nbsp;<span id="settle_mer_code_show"></span>
				<input type="hidden" id="settleMerCode" value="" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				内部商户号共&nbsp;&nbsp;<span id="dy_mer_code_count"></span>&nbsp;&nbsp;条
			</div>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">	
					<tr>
						<td align="center" bgcolor="#eeeeee" width="50%">对应内部商户号</td>
						<td align="center" bgcolor="#eeeeee" width="50%">操作</td>
					</tr>	
					<tr>
						<table width="100%" border="0" cellspacing="0">
							<tbody id="merBody">
								<tr>
									<td align="center" width="50%">
										<input type="text" id="mer_code" name="mer_code" value="" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="if(!checkMerCode(this))this.value='';"/>
										<input type="hidden" id="operateType" value="1" />
									</td>
									<td align="center" width="50%">
										<input type="button" value="添加" id="addColumn" name="addColumn" onclick="addColumnForMerCode();"/>
										<input type="button" value="删除" id="delColumn" name="delColumn" onclick="delColumnForMerCode(this);"/>
									</td>
								</tr>
							</tbody>
						</table>
					</tr>
					<tr>
						<div align="center">
							<td colspan="4" align="center">
								<input type="button" class="icon_normal" value="确定" onclick="commit()" />
								<input type="button" class="icon_normal" value="取消" onclick="hide('setInnerMerCode')" />
							</td>
						</div>
					</tr>
				</table>
			</div>
			<div style="display: none">
				<table>
					<tbody id="merBody_hidden">
						<tr>
							<td align="center" width="50%">
								<input type="text" id="mer_code" name="mer_code" value="" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="if(!checkMerCode(this))this.value='';"/>
								<input type="hidden" id="operateType" value="1" />
							</td>
							<td align="center" width="50%">
								<input type="button" value="添加" id="addColumn" name="addColumn" onclick="addColumnForMerCode();"/>
								<input type="button" value="删除" id="delColumn" name="delColumn" onclick="delColumnForMerCode(this);"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
