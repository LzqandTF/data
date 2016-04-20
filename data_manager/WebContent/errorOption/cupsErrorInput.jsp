<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银联差错录入</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox/wbox-min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("cupsErrorData");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorInputLst.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//根据每页显示数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("cupsErrorData");
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorInputLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	//查询
	function checkQuery(){
		var form = document.getElementById("cupsErrorData");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/getCupsErrorInputLst.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//重置：清空表单元素
	function clearForm(){
		$("#deductStlmDate").val("");
		$("#outAccount").val("");
		$("#reqSysStance").val("");
		$("#tradeType").val("");
	}
	//根据ID查询详情
	function queryCupsErrorInputDetail(id) {
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryCupsErrorInputDetail.do',
			data : "id="+ id,
			async:false,
			success : function(trade) {
				if(trade != null){
					$("#reqSysStance1").html(trade.reqSysStance);
					var trade_time = trade.trade_time;
					if (null != trade_time && trade_time != '') {
						$("#trade_time1").html(trade_time.substring(0,19));
					}
					$("#out_account1").html(trade.out_account);
					$("#tradeAmount1").html(trade.tradeAmount);
					$("#acqInstIdCode1").html(trade.acqInstIdCode);
					$("#deduct_sys_reference1").html(trade.deduct_sys_reference);
					var deductStlmDate = trade.deductStlmDate;
					if (deductStlmDate != null && deductStlmDate != '') {
						$("#deduct_stlm_date1").html(deductStlmDate.substring(0,10));
					}
					$("#mer_name1").html(trade.mer_name);
					$("#tradeType1").html(trade.tradeType);
					var result = trade.trade_result;
					var process = trade.process;
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
						result = trade.trade_result;
					}
					$("#trade_result1").html(result);
					$("#process1").html(process);
					$("#tradeMsgType1").html(trade.tradeMsgType);
					var tradeStatus = trade.trade_status;
					if (0 == tradeStatus) {
						tradeStatus = "未处理";
					} else if(1 == tradeStatus) {
						tradeStatus = "待审核";
					} else if (2 == tradeStatus) {
						tradeStatus = "已审核";
					} else if (3 == tradeStatus) {
						tradeStatus = "已驳回";
					} else {
						tradeStatus = trade.trade_status;
					}
					$("#trade_status1").html(tradeStatus);
					$("#commit_time1").html(trade.commit_time);
					$("#trade_source1").html(trade.trade_source);
					var bk_chk = trade.bk_chk;
					if (0 == bk_chk) {
						bk_chk = "未对账";
					} else if (1 == bk_chk) {
						bk_chk = "对账成功";
					} else if (2 == bk_chk) {
						bk_chk = "对账失败";
					} else {
						bk_chk = trade.bk_chk;
					}
					$("#bk_chk1").html(bk_chk);
					$("#handling_id1").html(trade.handling_name);
					$("#reason_code1").html(trade.reason_des);
					$("#pop1").css({display:"block"});
				}else{
					alert("查询详细信息失败");
					hide("pop1");
				}
			}
		});
	}
	function updateTradeStatus(id,operator) {
		if(!confirm("是否确认提交？")){
			return;
		}
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/updateTradeStatus.do',
			data : {"id": id,"operator":operator},
			async : false,
			success : function(msg) {
				if (msg > 0) {
					alert("提交成功");
					paging(1);
				} else {
					alert("提交失败");
				}
			}
		});
	}
	function addCupsErrorInput() {
		$.ajax({
			url : '<%=request.getContextPath()%>/addCupsErrorInput.do',
			type : 'post',
			data : "",
			async : false,
			success : function(data) {
			    $("#cupsErrorInputDialogbox").wBox({title: "无原交易录入",html:data,show:true});
			},
			error : function(msg) {
				alert("操作异常");
			}
		});
	}
	//获取原因码列表
	function getReasonCode(id) {
		if(id == ""){
			alert("请选择差错处理方式");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/getReasonCodeLstId.do',
    		type : 'post',
    		data : "id="+ id,
    		async : false,
    		dataType : "text",
    		success : function(json) {
    			var data = eval("("+json+")");
    			var selectObj = document.getElementById("reasonCode");
				while(selectObj.firstChild) {
			        selectObj.removeChild(selectObj.firstChild);
				}
				$(selectObj).append("<option value=''>请选择...</option>");
				for(var i=0;i<data.length;i++){
					$(selectObj).append("<option value="+data[i].reason_id+">"+data[i].reason_desc+"</option>");
				}
    		},
    		error : function(msg) {
    			alert("获取差错原因码列表失败!");
    		}
    	});
	}
	//差错调整
	function addCupsErrorData(operator) {
		var reqSysStance2 = $("#reqSysStance2").val();
		if (reqSysStance2 == "" || reqSysStance2 == null) {
			alert("请填写系统跟踪号！");
			return;
		}else if(reqSysStance2.length != 6){
			alert("流水号必须是6位长度");
			return;
		}
		var tradeTime2 = $("#tradeTime2").val();
		if (tradeTime2 == "") {
			alert("请填写交易时间");
			return;
		}
		var deductStlmDate2 = $("#deductStlmDate2").val();
		if (deductStlmDate2 == "") {
			alert("请填写清算日期！");
			return;
		}
		var tradeAmount2 = $("#tradeAmount2").val();
		if (tradeAmount2 == "") {
			alert("请填写交易金额！");
			return;
		}
		var out_account2 = $("#out_account2").val();
		if (out_account2 == "") {
			alert("请填写主账号！");
			return;
		}else if(out_account2.length < 12 && out_account2.length > 19){
			alert("主账号必须大于等于12、小于等于19个长度");
			return;
		}
		var acqInstIdCode2 = $("#acqInstIdCode2").val();
		if (acqInstIdCode2 == "") {
			alert("请填写受理机构代码！");
			return;
		}
		var trade_type2 = $("#trade_type2").val();
		if (trade_type2 == "") {
			alert("请选择交易类型");
			return;
		}
		var handling_id = $("#errorHandleMethodList").val();
		if (handling_id == "") {
			alert("请选择处理方式！");
			return;
		}
		var reasonCode = $("#reasonCode").val();
		if (reasonCode == "") {
			alert("请选择原因码！");
			return;
		}
		var instId = $("#inst_id").val();
		if (instId == "") {
			alert("请选择扣款渠道");
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/addCupsErrorData.do',
    		type : 'post',
    		data : {"reqSysStance2": reqSysStance2,"deductStlmDate2":deductStlmDate2,"tradeTime2":tradeTime2,"tradeAmount2":tradeAmount2, "out_account2" : out_account2,"acqInstIdCode2":acqInstIdCode2, "trade_type2":trade_type2, "handling_id": handling_id, "reasonCode":reasonCode,"operator": operator,"instId":instId},
    		async : false,
    		success : function(msg) {
    			if(msg == '1'){
					alert("无原交易录入成功");
					$("#wBox_close").click();
					paging(1);
				}else{
					alert("系统跟踪号已存在！");
				}
    		}
    	});
	}
	//获取交易类型
	 function getTradeTypeLst() {
    	$.ajax({
    		url : '<%=request.getContextPath()%>/getTradeAmountConfList.do',
    		type : 'post',
    		async : false,
    		success : function(msg) {
    			for (i in msg)
    				$("#tradeType").append('<option value="' + msg[i]['process'] + msg[i]['trademsgType']+'">'+ msg[i]['name'] + '</option>');
    		},
    		error : function(msg) {
    			alert("获取交易类型列表失败!");
    		}
    	});
    	//选中交易类型
    	var trade_type = $("#trade_type_hidden").val();
		var tradeType = document.getElementById("tradeType");
		for(var i = 0;i<tradeType.options.length;i++){
			if(tradeType.options[i].value == trade_type){
				tradeType.options[i].selected = 'selected';
			}
		}
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
    }
	//实时动态强制更改用户录入
	function amount(th) {
		var regStrs = [
			['^0(\\d+)$','$1'],//禁止录入整数部分两位以上，但首位为0
			['[^\\d\\.]+$', ''],//禁止录入任何非数字和小数点
			['\\.(\\d?)\\.+','.$1'],//禁止录入两个以上的小数点
			['^(\\d+\\.\\d{2}).+','$1']//禁止录入小数点两位以后
		];
		for (i = 0; i < regStrs.length; i++) {
			var reg = new RegExp(regStrs[i][0]);
			th.value = th.value.replace(reg, regStrs[i][1]);
		}
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
<body onload="getTradeTypeLst();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">差错处理</a>&gt;<span>银联差错录入</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/getCupsErrorInputLst.do" target="right" name="cupsErrorData" id="cupsErrorData" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">	
					            <tr>
					            	<td align="right" nowrap="nowrap">清算日期</td>
					                <td align="left" nowrap="nowrap">
					                	<span class="input_bgl">
					                		<input name="deductStlmDate" id="deductStlmDate" value="${param.deductStlmDate}"
					                			readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
					                	</span>
					                </td>
					            	<td align="right" nowrap="nowrap">扣款账号</td>
					                <td align="left" nowrap="nowrap">
					                	<span class="input_bgl">
					                		<input type="text" name="outAccount" id="outAccount" value="${param.out_account }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                	</span>
					                </td>
					                 <td align="right" nowrap="nowrap">交易流水号</td>
					                <td align="left" nowrap="nowrap">
					                	<span class="input_bgl">
					                		<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					                	</span>
					                </td>
					            </tr>
					            <tr>
					            	<td align="right" nowrap="nowrap">交易类型</td>
					                <td nowrap="nowrap">
					                	<span class="input_bgl">
					                		<select id="tradeType" name="tradeType" style="width: 150px;">
												<option value="">全部</option>
											</select>
					                	</span>
										<input type="hidden" id="trade_type_hidden" value="${tradeType }"/>
					                </td>
					            </tr>
					            <tr>
						            <td colspan="8" align="center" style="height: 30px"> 
						                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" /> 
						                <input type="button" class="icon_normal" value="重置" onclick="clearForm();" />
						                <input type="button" class="icon_normal" value="无原交易录入" onclick="addCupsErrorInput();" />
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
							<td align="center">清算日期</td>
							<td align="center">扣款账号</td>
							<td align="center">交易时间</td>
							<td align="center">交易金额</td>
							<td align="center">交易结果</td>
							<td align="center">银联参考号</td>
							<td align="center">交易类型</td>
							<td align="center">交易类别</td>
							<td align="center">处理方式</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="13">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="cupsErrorInput">
						<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">${fn:substring(cupsErrorInput.deductStlmDate,0,10)}</td>
							<td align="center">${cupsErrorInput.out_account}</td>
							<td align="center">${fn:substring(cupsErrorInput.trade_time,0,19)}</td>
							<td align="center"><f:formatNumber value="${cupsErrorInput.tradeAmount }" pattern="0.00"></f:formatNumber></td>
							<td align="center">
								<c:if test="${cupsErrorInput.trade_result == 0 }">成功</c:if>
								<c:if test="${cupsErrorInput.trade_result == 1 }">超时</c:if>
								<c:if test="${cupsErrorInput.trade_result == 2 }">失败</c:if>
								<c:if test="${cupsErrorInput.trade_result == 3 && cupsErrorInput.process == '480000' }">受理成功</c:if>
								<c:if test="${cupsErrorInput.trade_result == 3 && cupsErrorInput.process != '480000' }">冲正成功</c:if>
							</td>
							<td align="center">${cupsErrorInput.deduct_sys_reference}</td>
							<td align="center">${cupsErrorInput.tradeType}</td>
							<td align="center">${cupsErrorInput.tradeName}</td>
							<td align="center">${cupsErrorInput.handling_name}</td>
							<td align="center">
								<a class="fl lj mr10" href="javascript:void(0);" onclick="queryCupsErrorInputDetail('${cupsErrorInput.id}')">详情</a>
								<c:if test="${cupsErrorInput.trade_status == 0 || cupsErrorInput.trade_status == 3}">
									<a class="fl lj mr10" href="javascript:void(0);" onclick="updateTradeStatus('${cupsErrorInput.id}', '${sessionScope.login.loginName}')">提交</a>
								</c:if>
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
	
	<div id="cupsErrorInputDialogbox" class="table-m" style="display: none;"></div>
	
	<!--===========================弹出内容============================-->
	<div id="pop1" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">详情</span>
				 <a class="close" href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="operator">
					<tr>
						<td align="right" bgcolor="#eeeeee">流水号：</td>
						<td id="reqSysStance1"></td>
						<td align="right" bgcolor="#eeeeee">交易时间：</td>
						<td id="trade_time1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">主账号：</td>
						<td id="out_account1"></td>
						<td align="right" bgcolor="#eeeeee">交易金额：</td>
						<td id="tradeAmount1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接收机构号：</td>
						<td id="acqInstIdCode1"></td>
						<td align="right" bgcolor="#eeeeee">银联参考号：</td>
						<td id="deduct_sys_reference1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">清算日期：</td>
						<td id="deduct_stlm_date1"></td>
						<td align="right" bgcolor="#eeeeee">商户简称：</td>
						<td id="mer_name1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">交易类型：</td>
						<td id="tradeType1"></td>
						<td align="right" bgcolor="#eeeeee">交易结果：</td>
						<td id="trade_result1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理码：</td>
						<td id="process1"></td>
						<td align="right" bgcolor="#eeeeee">交易消息类型：</td>
						<td id="tradeMsgType1"></td>
					</tr>
					<tr>
						
						<td align="right" bgcolor="#eeeeee">交易状态：</td>
						<td id="trade_status1"></td>
						<td align="right" bgcolor="#eeeeee">提交时间：</td>
						<td id="commit_time1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">差错来源：</td>
						<td id="trade_source1"></td>
						<td align="right" bgcolor="#eeeeee">对账状态：</td>
						<td id="bk_chk1"></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">处理方式：</td>
						<td id="handling_id1"></td>
						<td align="right" bgcolor="#eeeeee">原因码：</td>
						<td id="reason_code1"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
