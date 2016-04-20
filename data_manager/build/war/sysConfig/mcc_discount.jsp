<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MCC扣率查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("mccDiscountSearch");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryMccDiscountPage.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("mccDiscountSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryMccDiscountPage.do?pageSize=" + pageSize;
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
			document.getElementById("mccDiscountSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function addHandling() {	
			$("#insert").css({display:"block"});
		}
		
		function addHandlingSub(){
			var mcc_type = $("#mcc_type_insert").val();
			var issuers = $("#issuer_insert").val();
			var billToParty = $("#billToParty_insert").val();
			var unionpay = $("#unionpay_insert").val();
			var range_desc = $("#range_desc_insert").val();
			var parent_id = $("#parent_id_insert").val();
			var mcc_fee = $("#mcc_fee").val();
			if(mcc_type == null || mcc_type == ""){
				alert("Mcc码不能为空！");
				return;
			}
			if(mcc_fee == null || mcc_fee == ""){
				alert("Mcc费率不能为空！");
				return;
			}
			if(issuers == null || issuers == ""){
				alert("发卡行收益分配不能为空！");
				return;
			}
			if(billToParty == null || billToParty == ""){
				alert("收单行收益分配不能为空！");
				return;
			}
			if(unionpay == null || unionpay == ""){
				alert("银联收益分配不能为空！");
				return;
			}
			if(range_desc == null || range_desc == ""){
				alert("适用范围不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addMccDiscount.do",
				data : "mcc_type="+ mcc_type+"&issuers="+issuers+"&billToParty="+billToParty+"&unionpay="+unionpay+"&range_desc="+range_desc+"&parent_id="+parent_id+"&mcc_fee="+mcc_fee,
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
		function selectData(id,mcc_type,issuers,billToParty,unionpay,range_desc,parent_id,mcc_fee){
			$("#id_update").val(id);
			$("#mcc_type_update").val(mcc_type);
			$("#mcc_fee_update").val(mcc_fee);
			$("#issuer_update").val(issuers);
			$("#billToParty_update").val(billToParty);
			$("#unionpay_update").val(unionpay);
			$("#range_desc_update").val(range_desc);
			$("#parent_id_update option[value="+parent_id+"]").attr("selected",true);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var id = $("#id_update").val();
			var mcc_type = $("#mcc_type_update").val();
			var issuers = $("#issuer_update").val();
			var billToParty = $("#billToParty_update").val();
			var unionpay = $("#unionpay_update").val();
			var range_desc = $("#range_desc_update").val();
			var parent_id = $("#parent_id_update").val();
			var mcc_fee = $("#mcc_fee_update").val();
			if(mcc_type == null || mcc_type == ""){
				alert("商户类别码(MCC)不能为空！");
				return;
			}
			if(mcc_fee == null || mcc_fee == ""){
				alert("MCC费率不能为空！");
				return;
			}
			if(issuers == null || issuers == ""){
				alert("发卡方不能为空！");
				return;
			}
			if(billToParty == null || billToParty == ""){
				alert("收单行不能为空！");
				return;
			}
			if(unionpay == null || unionpay == ""){
				alert("银联不能为空！");
				return;
			}
			if(range_desc == null || range_desc == ""){
				alert("适用范围不能为空！");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateMccDiscount.do",
				data : "id="+ id +"&mcc_type="+mcc_type+"&issuers="+issuers+"&billToParty="+billToParty+"&unionpay="+unionpay+"&range_desc="+range_desc+"&parent_id="+parent_id+"&mcc_fee="+mcc_fee,
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
		function deleteData(id,name){
			if(confirm("确定要删除"+name+"类型的Mcc扣率配置吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteMccDiscount.do",
					data : "id="+ id,
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
		
		function queryFeePoundage(){
			var url ="<%=request.getContextPath()%>/queryFeePoundage.do";
			window.open(url,'XX',' left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',scrollbars,resizable=yes,toolbar=no');
		}
	</script>
</head>

<body>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>MCC扣率查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMccDiscountPage.do" target="right" name="mccDiscountSearch" id = "mccDiscountSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>Mcc类型</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="mcc_type" id = "mcc_type" value="${param.mcc_type }" />
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
							<td align="center" width="10%">序号</td>
							<td align="center" width="10%">商户类别码(MCC)</td>
							<td align="center" width="10%">MCC费率</td>
							<td align="center" width="10%">发卡方</td>
							<td align="center" width="10%">收单行</td>
							<td align="center" width="10%">银联</td>
							<td align="center" width="30%">适用范围</td>
							<td align="center" width="20%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="mccDiscount">
						<tr>
							<td align="center">${mccDiscount.id }</td>
							<td align="center">${mccDiscount.mcc_type }</td>
							<td align="center">${mccDiscount.mcc_fee }</td>
							<td align="center">${mccDiscount.issuers }</td>
							<td align="center">${mccDiscount.billToParty }</td>
							<td align="center">${mccDiscount.unionpay }</td>
							<td align="center">${mccDiscount.range_desc }</td>
							<td align="center" width="60">
								<a href="javascript:deleteData(${mccDiscount.id},'${mccDiscount.mcc_type }')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${mccDiscount.id}','${mccDiscount.mcc_type }','${mccDiscount.issuers }','${mccDiscount.billToParty }','${mccDiscount.unionpay }','${mccDiscount.range_desc }','${mccDiscount.parent_id }','${mccDiscount.mcc_fee }')">修改</a>
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
				<span class="fl">MCC扣率添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" >					
					<tr>
						<td align="right" bgcolor="#eeeeee">商户类别码(MCC)：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="mcc_type_insert" name="mcc_type_insert" onkeyup="value=value.replace(/[^\d]/g,'')"/> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发卡方：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="mcc_fee" name="mcc_fee" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							<span>
								<a href="javascript:queryFeePoundage()" >查看公式</a>
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发卡方：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="issuer_insert" name="issuer_insert" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">收单行：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="billToParty_insert" name="billToParty_insert" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银联：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="unionpay_insert" name="unionpay_insert" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">适用范围：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="range_desc_insert" name="range_desc_insert" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">父类：</td>
						<td>
							<span class="input_bgl" style="float: inherit;"> 
								<select id="parent_id_insert" name="parent_id_insert" >
									<c:forEach items="${mccTypeList }" var="mccType">
										<option value="${mccType.id }">${mccType.type_name }</option>
									</c:forEach>
								</select> 
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
	<!--MCC扣率修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">MCC扣率修改</span> 
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
						<td align="right" bgcolor="#eeeeee">商户类别码(MCC)：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="mcc_type_update" name="mcc_type_update" onkeyup="value=value.replace(/[^\d]/g,'')"/> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">商户类别码(MCC)：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="mcc_fee_update" name="mcc_fee_update" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							<span>
								<a href="javascript:queryFeePoundage()" >查看公式</a>
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发卡方：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="issuer_update" name="issuer_update" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">收单行：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="billToParty_update" name="billToParty_update" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银联：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="unionpay_update" name="unionpay_update" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">适用范围：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="range_desc_update" name="range_desc_update" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">父类：</td>
						<td>
							<span class="input_bgl" style="float: inherit;"> 
								<select id="parent_id_update" name="parent_id_update" >
									<c:forEach items="${mccTypeList }" var="mccType">
										<option value="${mccType.id }">${mccType.type_name }</option>
									</c:forEach>
								</select> 
							</span>
							<span>
								<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
							</span>
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
