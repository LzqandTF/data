<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银联差错对账明细查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("cupsErrorFileSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorDzData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("cupsErrorFileSearch");
		var inst_name = $("#inst_name").val();
		if (inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorDzData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery() {
		var inst_name = $("#inst_name").val();
		if (inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		var form = document.getElementById("cupsErrorFileSearch");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorDzData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//清空表单元素
	function clearForm() {
		$("#startTime").val("");
		$("#endTime").val("");
		$("#reqSysStance").val("");
		$("#inst_name").val("");
		$("#deductSysReference").val("");
		$("#bk_chk").val("");
		$("#origDataStance").val("");
	}
	
	//初始化银行选择下拉框
	 function initChannelSelect() {
    	$.ajax({
    		url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#inst_name").append('<option value="' + msg[i]['error_dz_data_tableName'] + '">'+ msg[i]['name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取需要外部差错对账银行列表失败!");
    		}
    	});
    	var table_name = $("#table_name_hidden").val();
		var inst_name = document.getElementById("inst_name");
		for(var i = 0;i<inst_name.options.length;i++){
			if(inst_name.options[i].value == table_name){
				inst_name.options[i].selected = 'selected';
			}
		}
		var bk_chk = $("#bk_chk_hidden").val();
		var status = document.getElementById("bk_chk");
		for(var i = 0;i<status.options.length;i++){
			if(status.options[i].value == bk_chk){
				status.options[i].selected = 'selected';
			}
		}
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
    }
	//查询明细
	function queryDetail(id) {
		var inst_name = $("#inst_name").val();
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryErrorDzDataDetail.do',
			data : {"id": id,"inst_name":inst_name},
			async:false,
			success : function(duizhangErrorCupsLst) {
				if(duizhangErrorCupsLst != null){
					$("#reqSysStance1").html(duizhangErrorCupsLst.reqSysStance);
					$("#reqTime1").html(duizhangErrorCupsLst.reqTime);
					$("#outAccount1").html(duizhangErrorCupsLst.outAccount);
					$("#tradeAccount1").html(duizhangErrorCupsLst.tradeAccount);
					$("#fwdInstIdCode1").html(duizhangErrorCupsLst.fwdInstIdCode);
					$("#acqInstIdCode1").html(duizhangErrorCupsLst.acqInstIdCode);
					$("#deductSysResponse1").html(duizhangErrorCupsLst.deductSysResponse);
					$("#deductSysReference1").html(duizhangErrorCupsLst.deductSysReference);
					$("#process1").html(duizhangErrorCupsLst.process);
					$("#error_trade_flag1").html(duizhangErrorCupsLst.error_trade_flag);
					$("#tradeFee1").html(duizhangErrorCupsLst.tradeFee);
					$("#error_info1").html(duizhangErrorCupsLst.reason_desc);
					$("#onTradeTime1").html(duizhangErrorCupsLst.onTradeTime);
					$("#onTradeAccount1").html(duizhangErrorCupsLst.onTradeAccount);
					$("#onDeduct_stlm_date1").html(duizhangErrorCupsLst.onDeduct_stlm_date);
					$("#inst_name1").html(duizhangErrorCupsLst.inst_name);
					$("#merchant_code1").html(duizhangErrorCupsLst.merchant_code);
					$("#tran_code_caused_error1").html(duizhangErrorCupsLst.tran_code_caused_error);
					var whetherErroeHandle = duizhangErrorCupsLst.whetherErroeHandle;
					if (0 == whetherErroeHandle) {
						whetherErroeHandle = "不需要";
					} else if (1 == whetherErroeHandle) {
						whetherErroeHandle = "长款处理";
					} else if (2 == whetherErroeHandle) {
						whetherErroeHandle = "短款处理";
					} else {
						whetherErroeHandle = duizhangErrorCupsLst.whetherErroeHandle;
					}
					$("#whetherErroeHandle1").html(whetherErroeHandle);
					var bk_chk = duizhangErrorCupsLst.bk_chk;
					if (0 == bk_chk) {
						bk_chk = "对账可疑";
					} else if (1 == bk_chk) {
						bk_chk = "对账成功";
					} else if (2 == bk_chk) {
						bk_chk = "对账失败";
					} else {
						bk_chk = duizhangErrorCupsLst.bk_chk;
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
	function downExcel() {
		var inst_name = $("#inst_name").val();
		if (inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		//得到页面上的值
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var reqSysStance = $("#reqSysStance").val();
		var deductSysReference = $("#deductSysReference").val();
		var inst_name = $("#inst_name").val();
		var bk_chk = $("#bk_chk").val();
		var url ="<%=request.getContextPath()%>/duizhangErrorCupsLstDownExcel.do?startTime="+startTime+"&endTime="+endTime+
				"&reqSysStance="+reqSysStance+"&deductSysReference="+deductSysReference+"&bk_chk="+bk_chk+"&inst_name="+inst_name;
		window.location=url;
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

<body onload="initChannelSelect();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>银联差错对账明细查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryErrorDzData.do" target="right" id="cupsErrorFileSearch" name="cupsErrorFileSearch" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">交易日期</td>
					                <td nowrap="nowrap">
					                  	<span style="width:30px;" class="input_bgl">
											 <input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }" 
											 	readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();"/>
											 -
											 <input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
											 	readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									  	</span>
					                </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" id="reqSysStance" name="reqSysStance" value="${param.reqSysStance }" maxlength="6" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">对账状态</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="bk_chk" name="bk_chk" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">对账可疑</option>
											</select>
											<input type="hidden" id="bk_chk_hidden" value="${bk_chk }"/>
					                     </span>
					                </td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap">扣款渠道</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="inst_name" name="inst_name" style="width: 150px;">
												<option value="">--请选择扣款渠道--</option>
											</select>
											<input type="hidden" id="table_name_hidden" value="${table_name }"/>
					                     </span><font color="red">&nbsp;*</font>
					                </td>
					               	<td align="right" nowrap="nowrap">参考号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" id="deductSysReference" name="deductSysReference" value="${param.deductSysReference }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">上一笔交易流水号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" id="origDataStance" name="origDataStance" value="${param.origDataStance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
								</tr>
								<tr>
						        	<td colspan="6" align="center" style="height: 30px">
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" /> 
						                <input type="button" class="icon_normal" value="重置" onclick="clearForm();" />
						                <input type="button" class="icon_normal" value="下载xls报表" onclick="downExcel();" />
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
				<span style="float: right;">共<font color="red">
					<c:if test="${pageDataLst.totalItems == null }">0</c:if>
					<c:if test="${pageDataLst.totalItems != null }">${pageDataLst.totalItems }</c:if>
					</font>条数据&nbsp;&nbsp;
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
			<!-- 内容显示区 -->
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center">交易流水号 </td>
							<td align="center">上一笔交易流水号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">转出卡号</td>
							<td align="center">参考号</td>
							<td align="center">交易类型</td>
							<td align="center">对账状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0}">
					    <tr align="center">
							<td colspan="8">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="duizhangErrorCupsLst">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${duizhangErrorCupsLst.reqSysStance }</td>
							<td align="center">${duizhangErrorCupsLst.origDataStance }</td>
							<td align="center">${fn:substring(duizhangErrorCupsLst.reqTime,0,19)}</td>
							<td align="center"><f:formatNumber value="${duizhangErrorCupsLst.tradeAccount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">${duizhangErrorCupsLst.outAccount }</td>
							<td align="center">${duizhangErrorCupsLst.deductSysReference}</td>
							<td align="center">${duizhangErrorCupsLst.handling_name }</td>
							<td align="center">
								<c:if test="${duizhangErrorCupsLst.bk_chk == 0 }">对账可疑</c:if>
							</td>
							<td>
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryDetail('${duizhangErrorCupsLst.id}')">详情</a>
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
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="pop1" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">银联差错对账详情</span> <a class="close"
					href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">
					<tr>
						<td align="right" bgcolor="#eeeeee">流水号：</td>
						<td id="reqSysStance1"></td>
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="reqTime1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">转出卡号：</td>
						<td id="outAccount1"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAccount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">发送机构号：</td>
						<td id="fwdInstIdCode1"></td>
						<td align="right" bgcolor="#eeeeee">接收机构号：</td>
						<td id="acqInstIdCode1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款应答码：</td>
						<td id="deductSysResponse1"></td>
						<td align="right" bgcolor="#eeeeee">参考号：</td>
						<td id="deductSysReference1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="process1"></td>
						<td align="right" bgcolor="#eeeeee">差错标识：</td>
						<td id="error_trade_flag1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">银行手续费：</td>
						<td id="tradeFee1"></td>
						<td align="right" bgcolor="#eeeeee">差错原因：</td>
						<td id="error_info1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上一笔交易的日期时间：</td>
						<td id="onTradeTime1"></td>
						<td align="right" bgcolor="#eeeeee">上一笔交易金额：</td>
						<td id="onTradeAccount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上一笔交易的清算日期：</td>
						<td id="onDeduct_stlm_date1"></td>
						<td align="right" bgcolor="#eeeeee">渠道名称：</td>
						<td id="inst_name1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">商户代码：</td>
						<td id="merchant_code1"></td>
						<td align="right" bgcolor="#eeeeee">原始交易代码：</td>
						<td id="tran_code_caused_error1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否手工差错处理：</td>
						<td id="whetherErroeHandle1"></td>
						<td align="right" bgcolor="#eeeeee">对账状态：</td>
						<td id="bk_chk1"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
