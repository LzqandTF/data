<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>电银差错对账明细查询</title>
<link href="<%=request.getContextPath()%>/css/style.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<!--   <script type="text/javascript" src="<%=request.getContextPath()%>/js/select.js"></script>-->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
	
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("searchYlcupsErrorEntry");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorOriginalData.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("searchYlcupsErrorEntry");
		var inst_name = $("#inst_name").val();
		if(inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorOriginalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var inst_name = $("#inst_name").val();
		if(inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		var form = document.getElementById("searchYlcupsErrorEntry");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryErrorOriginalData.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//清空表单输入框的元素
	function clearForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#reqSysStance").val("");
		$("#bk_chk").val("");
		$("#inst_name").val("");
		$("#deduct_sys_reference").val("");
		$("#handling_id").val("");
	}
		
	//初始化银行选择下拉框
	 function initChannelSelect() {
    	$.ajax({
    		url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#inst_name").append('<option value="' + msg[i]['instId'] + ',' + msg[i]['error_original_data_tableName'] + '">'+ msg[i]['name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取渠道列表失败!");
    		}
    	});
    	var table_name = $("#table_name__hidden").val();
		var inst_name = document.getElementById("inst_name");
		for(var i = 0;i<inst_name.options.length;i++){
			if(inst_name.options[i].value == table_name){
				inst_name.options[i].selected = 'selected';
			}
		}
	 }
	//excel表格下载
	function downExcel(){
		var inst_name = $("#inst_name").val();
		if (inst_name == null || inst_name == "") {
			alert("请选择扣款渠道！");
			return;
		}
		//得到页面上的值
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var reqSysStance = $("#reqSysStance").val();
		var bk_chk = $("#bk_chk").val();
		var inst_name = $("#inst_name").val();
		var deduct_sys_reference = $("#deduct_sys_reference").val();
		var handling_id = $("#handling_id").val();
		var url ="<%=request.getContextPath()%>/errorDuizhangDetailDownExcel.do?startTime="+startTime+"&endTime="+endTime+
				"&reqSysStance="+reqSysStance+"&bk_chk="+bk_chk+"&deduct_sys_reference="+deduct_sys_reference+
				"&handling_id="+handling_id+"&inst_name="+inst_name;
		window.location=url;
	}
			//根据ID获取详情
	function queryDetail(id) {
		var inst_name = $("#inst_name").val();
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryDyErrorDetail.do',
			data : {"id": id,"inst_name":inst_name},
			async:false,
			success : function(ylcupsErrorEntry) {
				if(ylcupsErrorEntry != null){
					$("#reqSysStance1").html(ylcupsErrorEntry.reqSysStance);
					var tradeTime = ylcupsErrorEntry.trade_time;
					if (tradeTime != null && tradeTime != '') {
						$("#trade_time1").html(tradeTime.substring(0,19));
					}
					$("#out_account1").html(ylcupsErrorEntry.out_account);
					$("#tradeAmount1").html(ylcupsErrorEntry.tradeAmount);
					$("#mer_name1").html(ylcupsErrorEntry.mer_name);
					$("#acqInstIdCode1").html(ylcupsErrorEntry.acqInstIdCode);
					$("#deductStlmDate1").html(ylcupsErrorEntry.deductStlmDate);
					$("#deduct_sys_reference1").html(ylcupsErrorEntry.deduct_sys_reference);
					var process = ylcupsErrorEntry.process;
					$("#req_process1").html(process);
					$("#tradeMsgType1").html(ylcupsErrorEntry.tradeMsgType);
					
					var result = ylcupsErrorEntry.trade_result;
					if (0 == result) {
						result = "成功";
					} else if(1 == result) {
						result = "超时";
					} else if (2 == result) {
						result = "失败";
					} else if (3 == result && process == '480000') {
						result = "受理成功";
					} else if (3 == result && process != '480000') {
						result = "冲正成功";
					} else {
						result = ylcupsErrorEntry.trade_result;
					}
					$("#trade_result1").html(result);
					$("trade_category1").html(ylcupsErrorEntry.trade_category);
					$("#trade_type1").html(ylcupsErrorEntry.tradeType);
					$("#handling_name1").html(ylcupsErrorEntry.handling_name);
					$("#entering_time1").html(ylcupsErrorEntry.entering_time);
					$("#commit_time1").html(ylcupsErrorEntry.commit_time);
					$("#trade_source1").html(ylcupsErrorEntry.trade_source);
					$("#operator1").html(ylcupsErrorEntry.operator);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询银联差错详细信息失败！");
					hide("pop1");
				}
			}
		});
	}
	//差错处理方式
	function initErrorHandlerMethod() {
		$.ajax({
    		url : '<%=request.getContextPath()%>/getErrorHandlingList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#handling_id").append('<option value="' + msg[i]['id'] + '">'+ msg[i]['handling_name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取差错处理方式列表失败!");
    		}
    	});
		var handling_name = $("#handling_name_hidden").val();
		var type = document.getElementById("handling_id");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == handling_name){
				type.options[i].selected = 'selected';
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
<body onload="initChannelSelect();initErrorHandlerMethod();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账结果查询</a>&gt;<span>电银差错对账明细查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryErrorOriginalData.do" target="right" name="searchYlcupsErrorEntry" id="searchYlcupsErrorEntry" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="100%" border="0" cellspacing="0">	
					            <tr>
					            	<td align="right" nowrap="nowrap">交易日期</td>
					                <td nowrap="nowrap">
								    	<span style="width:30px;" class="input_bgl">
											<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }" 
												readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
											 -
											<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }" 
												readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})"/>
										</span>
								    </td>
					                <td align="right" nowrap="nowrap">交易流水号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" maxlength="6" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					               	<td align="right" nowrap="nowrap">对账状态</td>
					                <td nowrap="nowrap">
					                      <span class="input_bgl">
										  	<select id="bk_chk" name="bk_chk" style="width: 150px;">
												<option value="">全部</option>
												<option value="0">未对账</option>
												<option value="1">对账成功</option>
												<option value="2">对账失败</option>
											</select>
											<input type="hidden" id="bk_chk_hidden" value="${bk_chk }"/>
										  </span>
					                </td>
					            </tr>
					            <tr>
					              <td align="right" nowrap="nowrap">扣款渠道</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="inst_name" name="inst_name">
												<option value="">--请选择扣款渠道--</option>
											</select>
											<input type="hidden" id="table_name__hidden" value="${table_name }"/>
					                     </span><font color="red">&nbsp;*</font>
					                </td>
					                <td align="right" nowrap="nowrap">参考号</td>
					               	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="deduct_sys_reference" id="deduct_sys_reference" value="${param.deduct_sys_reference }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                     </span>
					                </td>
					                <td align="right" nowrap="nowrap">交易类型</td>
					                <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<select id="handling_id" name="handling_id" style="width: 150px;">
												<option value="">全部</option>
											</select>
											<input type="hidden" id="handling_name_hidden" value="${handling_name }"/>
					                     </span>
					                </td>
					            </tr>
					            <tr>
						            <td colspan="8" align="center" style="height: 30px"> 
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" /> 
						                <input type="button" class="icon_normal" value="重置" onclick="clearForm()" />
						                <input type="button" class="icon_normal" value="下载xls报表" onclick="downExcel()" />
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
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">转出卡号</td>
							<td align="center">参考号</td>
							<td align="center">交易类型</td>
							<td align="center">来源</td>
							<td align="center">对账状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0}">
					    <tr align="center">
							<td colspan="9">对不起,暂无数据！</td>
						</tr>
					</c:if>
						<c:forEach items="${pageDataLst.result }" var="ylcupsErrorEntry">
							<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
								<td align="center">${ylcupsErrorEntry.reqSysStance }</td>
								<td align="center">${fn:substring(ylcupsErrorEntry.trade_time,0,19)}</td>
								<td align="center"><f:formatNumber value="${ylcupsErrorEntry.tradeAmount }" pattern="0.00"></f:formatNumber></td>
								<td align="center">${ylcupsErrorEntry.out_account }</td>
								<td align="center">${ylcupsErrorEntry.deduct_sys_reference}</td>
								<td align="center">${ylcupsErrorEntry.handling_name }</td>
								<td align="center">${ylcupsErrorEntry.trade_source }</td>
								<td align="center">
									<c:if test="${ylcupsErrorEntry.bk_chk == 0 }">未对账</c:if>
									<c:if test="${ylcupsErrorEntry.bk_chk == 1 }">对账成功</c:if>
									<c:if test="${ylcupsErrorEntry.bk_chk == 2 }">对账失败</c:if>
								</td>
								<td>
									<a class="fl lj mr10" href="javascript:void(0);" onclick="queryDetail('${ylcupsErrorEntry.id}')">详情</a>
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
							value="${pageDataLst.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
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
				<span class="fl">电银差错对账详情</span> <a class="close"
					href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">
					<tr>
						<td align="right" bgcolor="#eeeeee">交易流水：</td>
						<td id="reqSysStance1"></td>
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="trade_time1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">转出卡号：</td>
						<td id="out_account1"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">商户简称：</td>
						<td id="mer_name1"></td>
						<td align="right" bgcolor="#eeeeee">接收机构号：</td>
						<td id="acqInstIdCode1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">清算日期：</td>
						<td id="deductStlmDate1"></td>
						<td align="right" bgcolor="#eeeeee">参考号：</td>
						<td id="deduct_sys_reference1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="req_process1"></td>
						<td align="right" bgcolor="#eeeeee">交易消息类型：</td>
						<td id="tradeMsgType1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易结果：</td>
						<td id="trade_result1"></td>
						<td align="right" bgcolor="#eeeeee">交易类别：</td>
						<td id="trade_category1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易类型：</td>
						<td id="trade_type1"></td>
						<td align="right" bgcolor="#eeeeee">差错原因：</td>
						<td id="handling_name1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">审核时间：</td>
						<td id="entering_time1"></td>
						<td align="right" bgcolor="#eeeeee">提交时间：</td>
						<td id="commit_time1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">差错来源：</td>
						<td id="trade_source1"></td>
						<td align="right" bgcolor="#eeeeee">操作员：</td>
						<td id="operator1"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
