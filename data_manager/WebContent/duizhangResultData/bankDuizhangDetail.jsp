<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行对账明细查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("bankDuizhangDetailSearch");
		var pageSize = $("#pageSize").val();
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("bankDuizhangDetailSearch");
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var bank_id = $("#bank_id").val();
		if (bank_id == null || bank_id == "") {
			alert("请选择银行机构！");
			return;
		}
		var form = document.getElementById("bankDuizhangDetailSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryDuizhangData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//清空表单查询条件
	function clearForm(){
		$("#deduct_stlm_date").val("");
		$("#reqSysStance").val("");
		$("#outAccount").val("");
		$("#deductStlmDate").val("");
		$("#bank_id").val("");
	}
	//根据ID获取详细信息
	function queryDetail(id) {
		var bank_id = $("#bank_id").val();
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryDuizhangDataDetail.do',
			data : {"id": id, "bank_id":bank_id},
			async:false,
			success : function(duizhangData) {
				if(duizhangData != null){
					$("#reqSysStance1").html(duizhangData.reqSysStance);
					$("#reqTime1").html(duizhangData.reqTime);
					$("#outAccount1").html(duizhangData.outAccount);
					$("#tradeAmount1").html(duizhangData.tradeAmount);
					$("#tradeFee1").html(duizhangData.tradeFee);
					$("#deductSysReference1").html(duizhangData.deductSysReference);
					$("#process1").html(duizhangData.process);
					$("#deductSysResponse1").html(duizhangData.deductSysResponse);
					$("#fwdInstIdCode1").html(duizhangData.fwdInstIdCode);
					$("#acqInstIdCode1").html(duizhangData.acqInstIdCode);
					$("#merType1").html(duizhangData.merType);
					$("#termId1").html(duizhangData.termId);
					$("#authorizationCode1").html(duizhangData.authorizationCode);
					$("#rcvgInstIdCode1").html(duizhangData.rcvgInstIdCode);
					$("#terminalType1").html(duizhangData.terminalType);
					var whetherErroeHandle = duizhangData.whetherErroeHandle;
					if (0 == whetherErroeHandle) {
						whetherErroeHandle = "不需要";
					} else if (1 == whetherErroeHandle) {
						whetherErroeHandle = "长款处理";
					} else if (2 == whetherErroeHandle) {
						whetherErroeHandle == "短款处理";
					} else {
						whetherErroeHandle = duizhangData.whetherErroeHandle;
					}
					$("#whetherErroeHandle1").html(whetherErroeHandle);
					$("#inst_name1").html(duizhangData.inst_name);
					var bk_chk = duizhangData.bk_chk;
					if (0 == bk_chk) {
						bk_chk = "对账可疑";
					} else if (1 == bk_chk) {
						bk_chk = "对账成功";
					} else if (2 == bk_chk) {
						bk_chk = "对账失败";
					} else {
						bk_chk = duizhangData.bk_chk;
					}
					$("#bk_chk1").html(bk_chk);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
	//初始化银行机构选择下拉框
	function initBankInst() {
		$.ajax({
			url : '<%=request.getContextPath()%>/getIsWhetherOuterDzBankInstList.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] + ',' + msg[i]['dz_data_tableName'] +'">'+ msg[i]['bank_name'] + '</option>');
			}
		});
		
		var bankId = $("#bankId_hidden").val();
		var bank_id = document.getElementById("bank_id");
		for (var i = 0; i < bank_id.length; i++) {
			if (bank_id.options[i].value == bankId) {
				bank_id.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
	}
	
	function clearEndTime(){
		$("#endTime").val("");
	}
	//设置每页显示条数
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
	//分页
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
<body onload="initBankInst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>银行对账明细查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryDuizhangData.do" target="right" id="bankDuizhangDetailSearch" name="bankDuizhangDetailSearch" method="post">
					<div class="table_2" style="background: #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">清算日期</td>
									<td nowrap="nowrap">
										<span style="width: 30px;" class="input_bgl"> 
											<input id="deduct_stlm_date" name="deduct_stlm_date" value="${param.deduct_stlm_date }"
												readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
										</span>
									</td>
									<td align="right" nowrap="nowrap">转出卡号</td>
									<td nowrap="nowrap">
										<span class="input_bgl"> 
											<input type="text" id="outAccount" name="outAccount" value="${param.outAccount }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
										</span>
									</td>
									<td align="right" nowrap="nowrap">交易流水号</td>
									<td nowrap="nowrap">
										<span class="input_bgl"> 
											<input type="text" id="reqSysStance" name="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\d\.]/g,'')" />
										</span>
								    </td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap">银行机构</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="bank_id" name="bank_id" style="width: 150px;" onchange="getInstInfoByBankId(this.value);">
												<option value="">--请选择银行机构--</option>
											</select>
											<input type="hidden" id="bankId_hidden" value="${bankId }"/>
					                     </span>
					                     <font color="red">*</font>
					                </td>
								</tr>
								<tr>
									<td colspan="6" align="center" style="height: 30px">
										<input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
										<input type="button" class="icon_normal" value="重置" onclick="clearForm();" /> 
									</td>
								</tr>
								<tr>
									<td colspan="6" style="text-align:right;color: red">
										注：融易付UPMP的参考号=流水号
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
							<td align="center">流水号</td>
							<td align="center">清算时间</td>
							<td align="center">交易金额</td>
							<td align="center">转出卡号</td>
							<td align="center">参考号</td>
							<td align="center">类型</td>
							<td>操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="7">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="duizhangData">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${duizhangData.reqSysStance}</td>
							<td align="center">${duizhangData.deduct_stlm_date}</td>
							<td align="center"><f:formatNumber value="${duizhangData.tradeAmount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">${duizhangData.outAccount }</td>
							<td align="center">${duizhangData.deductSysReference}</td>
							<td align="center">
								<c:if test="${duizhangData.bk_chk == 0 }">可疑对账</c:if>
								<c:if test="${duizhangData.bk_chk == 1 }">对账成功</c:if>
								<c:if test="${duizhangData.bk_chk == 2 }">对账失败</c:if>
							</td>
							<td>
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryDetail('${duizhangData.id}')">详情</a>
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
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
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
					<c:if test="${pageDataLst.pageNo > 1}">
						<a href="javascript:paging(${pageDataLst.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${pageDataLst.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum"  
							value="${pageDataLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)"/>页
						</span>
					</b>
				</div>
			</c:if>
		</div>
		<!--===========================弹出内容============================-->
		<div id="pop1" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">详情</span> <a class="close"
					href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">
					<tr>
						<td align="right" bgcolor="#eeeeee">交易流水：</td>
						<td id="reqSysStance1"></td>
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="reqTime1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">转出卡号：</td>
						<td id="outAccount1"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行手续费：</td>
						<td id="tradeFee1"></td>
						<td align="right" bgcolor="#eeeeee">参考号：</td>
						<td id="deductSysReference1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="process1"></td>
						<td align="right" bgcolor="#eeeeee">交易应答码：</td>
						<td id="deductSysResponse1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发送机构号：</td>
						<td id="fwdInstIdCode1"></td>
						<td align="right" bgcolor="#eeeeee">接收机构号：</td>
						<td id="acqInstIdCode1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">商户类型：</td>
						<td id="merType1"></td>
						<td align="right" bgcolor="#eeeeee">受卡机终端标识码：</td>
						<td id="termId1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">授权应答码：</td>
						<td id="authorizationCode1"></td>
						<td align="right" bgcolor="#eeeeee">接收机构标识码：</td>
						<td id="rcvgInstIdCode1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">终端类型：</td>
						<td id="terminalType1"></td>
						<td align="right" bgcolor="#eeeeee">是否需要手工处理：</td>
						<td id="whetherErroeHandle1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道名称：</td>
						<td id="inst_name1"></td>
						<td align="right" bgcolor="#eeeeee">是否对账：</td>
						<td id="bk_chk1"></td>
					</tr>
				</table>
			</div>
		</div>
		</div>
	</div>
</body>
</html>
