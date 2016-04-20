<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据日志</title>
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
		function paging(pageNo) {
			var form = document.getElementById("errorAuditRecords");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryErrorAuditRecords.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("errorAuditRecords");
			with (form) {
				action = "<%=request.getContextPath()%>/queryErrorAuditRecords.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		//查询按钮
		function checkQuery(){
			var form = document.getElementById("errorAuditRecords");
			var pageSize = $("#pageSize").val();
			with (form) {
				action = "<%=request.getContextPath()%>/queryErrorAuditRecords.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
		function clearEndTime(){
			$("#endTime").val("");
		}
		
		function clearForm() {
			$("#startTime").val("");
			$("#endTime").val("");
			$("#businessType").val("");
			$("#operator").val("");
			$("#operationType").val("");
			$("#reqSysStance").val("");
		}
		
		function getSelected() {
			var business_type = $("#business_type_hidden").val();
			var businessType = document.getElementById("businessType");
			for (var i = 0; i < businessType.options.length; i++) {
				if (businessType.options[i].value == business_type) {
					businessType.options[i].selected = 'selected';
				}
			}
			var operation_type = $("#operation_type_hidden").val();
			var operationType = document.getElementById("operationType");
			for (var i = 0; i < operationType.options.length; i++) {
				if (operationType.options[i].value == operation_type) {
					operationType.options[i].selected = 'selected';
				}
			}
			var page_size = $("#pageSize_hidden").val();
			if (page_size == '') {
				page_size = 10;
			}
			document.getElementById("pageSize").value = page_size;
		}
		
		function queryDetail(trade_id, record_time) {
			$.ajax({
				type : "post",
				url : '<%=request.getContextPath()%>/queryDetail.do',
				data : {"tradeId" : trade_id, "recordTime" : record_time},
				async : false,
				success : function (errorAuditRecords) {
					if (errorAuditRecords != null) {
						$("#req_sys_stance").html(errorAuditRecords.req_sys_stance);
						$("#trade_time").html(errorAuditRecords.trade_time);
						$("#out_account").html(errorAuditRecords.out_account);
						$("#trade_amount").html(errorAuditRecords.trade_amount);
						$("#req_mer_code").html(errorAuditRecords.req_mer_code);
						$("#req_mer_term_id").html(errorAuditRecords.req_mer_term_id);
						$("#trade_fee").html(errorAuditRecords.trade_fee);
						$("#req_response").html(errorAuditRecords.req_response);
						/* $("#req_process").html(errorAuditRecords.req_process);
						$("#trademsg_type").html(errorAuditRecords.trademsg_type); */
						$("#trade_type").html(errorAuditRecords.tradeType);
						var deductRollBk = errorAuditRecords.deduct_roll_bk;
						if (0 == deductRollBk) {
							deductRollBk = "否";
						} else if (1 == deductRollBk) {
							deductRollBk = "是";
						} else {
							deductRollBk = errorAuditRecords.deduct_roll_bk;
						}
						$("#deduct_roll_bk").html(deductRollBk);
						$("#deduct_roll_bk_stance").html(errorAuditRecords.deduct_roll_bk_stance);
						$("#deduct_rollbk_sys_time").html(errorAuditRecords.deduct_rollbk_sys_time);
						$("#deduct_roll_bk_response").html(errorAuditRecords.deduct_roll_bk_response);
						var deductRollBkResult = errorAuditRecords.deduct_roll_bk_response;
						if ("00" == deductRollBkResult) {
							deductRollBkResult = "成功";
						} else if (null == deductRollBkResult) {
							deductRollBkResult = "无";
						} else {
							deductRollBkResult = "失败";
						}
						$("#deduct_roll_bk_result").html(deductRollBkResult);
						$("#deduct_sys_stance").html(errorAuditRecords.deduct_sys_stance);
						$("#deduct_sys_time").html(errorAuditRecords.deduct_sys_time);
						var deductSysResponse = errorAuditRecords.deduct_sys_response;
						$("#deduct_sys_response").html(deductSysResponse);
						if ("00" == deductSysResponse) {
							deductSysResponse = "成功";
						} else if (null == deductSysResponse) {
							deductSysResponse = "无";
						} else if ("N1" == deductSysResponse) {
							deductSysResponse = "超时";
						} else if (null != deductSysResponse && "00" != deductSysResponse) {
							deductSysResponse = "失败";
						}
						$("#deduct_result").html(deductSysResponse);
						$("#deduct_stlm_date").html(errorAuditRecords.deduct_stlm_date);
						$("#deduct_sys_id").html(errorAuditRecords.deduct_sys_id);
						$("#mer_name").html(errorAuditRecords.mer_name);
						$("#additional_data").html(errorAuditRecords.additional_data);
						$("#deduct_sys_reference").html(errorAuditRecords.deduct_sys_reference);
						$("#authorization_code").html(errorAuditRecords.authorization_code);
						$("#deduct_mer_code").html(errorAuditRecords.deduct_mer_code);
						$("#deduct_mer_term_id").html(errorAuditRecords.deduct_mer_term_id);
						$("#record_time").html(errorAuditRecords.record_time);
						//处理状态 0：未处理、1：待审核、2：已审核、3：已驳回、4：未提交、5：待提交
						var handling_status = errorAuditRecords.handling_status;
						if (0 == handling_status) {
							handling_status = "未处理";
						} else if (1 == handling_status) {
							handling_status = "待审核";
						} else if (2 == handling_status) {
							handling_status = "已审核";
						} else if (3 == handling_status) {
							handling_status = "已驳回";
						} else {
							handling_status = errorAuditRecords.handling_status;
						}
						$("#handling_status").html(handling_status);
						$("#pop1").css({display:"block"});
					} else {
						alert("查询差错审计详情失败");
						hide("pop1");
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
</script>
</head>

<body onload="getSelected();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">数据日志</a>&gt;<span>日志查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryErrorAuditRecords.do" target="right" name="errorAuditRecords" id="errorAuditRecords" method="post">
				<div class="table_2" style="background:  #dcdfe1; border: none;">
					<center>
					<table width="90%" border="0" cellspacing="0">	
				            <tr>
				            	<td align="right" nowrap="nowrap">操作日期</td>
				            	<td nowrap="nowrap">
									<span style="width: 30px;" class="input_bgl"> 
										<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime();" />
										- 
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>
								</td>
				            	<td align="right" nowrap="nowrap">业务类型</td>
				            	<td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<select name="businessType" id="businessType" style="width: 150px;">
				                     		<option value="">全部</option>
				                     		<option value="0">内部差错调整</option>
				                     		<option value="1">内部差错审批</option>
				                     		<option value="2">内部差错驳回</option>
				                     		<option value="3">银联差错录入</option>
				                     		<option value="4">银联差错提交</option>
				                     		<option value="5">银联差错审核</option>
				                     		<option value="6">银联差错驳回</option>
				                     	</select>
				                     	<input type="hidden" id="business_type_hidden" value="${business_type }" />
				                     </span>
				                </td>
				                <td align="right" nowrap="nowrap">操作员</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="operator" id="operator" value="${param.operator }"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
				            	<td align="right" nowrap="nowrap">操作类型</td>
				                <td align="left" nowrap="nowrap">
				                  <span class="input_bgl">
								  	<select name="operationType" id="operationType" style="width: 150px;">
										<option value="">不限</option>
										<option value="0">调整</option>
										<option value="1">审批</option>
										<option value="2">驳回</option>
										<option value="3">录入</option>
										<option value="4">提交</option>
										<option value="5">重录入</option>
										<option value="6">重提交</option>
									</select>
									<input type="hidden" id="operation_type_hidden" value="${operation_type }" />
								  </span>
				                </td>
				            	<td align="right" nowrap="nowrap">交易流水号</td>
				                <td nowrap="nowrap">
				                     <span class="input_bgl">
				                     	<input type="text" name="reqSysStance" id="reqSysStance" value="${param.reqSysStance }" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				                     </span>
				                </td>
				            </tr>
				            <tr>
					            <td colspan="8" align="center" style="height: 30px"> 
					                <input type="button" class="icon_normal" value="查询" onclick="checkQuery();" />
					                <input type="button" class="icon_normal" value="重置" onclick="clearForm();"/>
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
				<c:if test="${fn:length(errorAuditRecords.result) <= 0 }">0</c:if>
				<c:if test="${fn:length(errorAuditRecords.result) > 0 }">${fn:length(errorAuditRecords.result) }</c:if>
			</font>
			条数据
			</span>
			<span style="float: right">共<font color="red">
				<c:if test="${errorAuditRecords.totalItems == null }">0</c:if>
				<c:if test="${errorAuditRecords.totalItems != null }">${errorAuditRecords.totalItems }</c:if>
			</font>条数据
			<font color="red">
				<c:if test="${errorAuditRecords.totalPages == null}">0</c:if>
				<c:if test="${errorAuditRecords.totalPages != null}">${errorAuditRecords.totalPages}</c:if>
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
			<div style="width:100%; overflow:auto;overflow-x:auto;overflow-y:hidden;">
			<table width="1250px;" border="0" cellspacing="0">
				<thead>
					<tr>
						<td align="center">交易流水号</td>
						<td align="center">交易时间</td>
						<td align="center">交易金额</td>
						<td align="center">交易类型</td>
						<td align="center">交易结果</td>
						<td align="center">扣款渠道</td>
						<td align="center">数据来源</td>
						<td align="center">业务类型</td>
						<td align="center">操作员</td>
						<td align="center">操作类型</td>
						<td align="center">操作内容</td>
						<td align="center">操作时间</td>
						<td align="center">访问IP</td>
						<td>操作</td>
					</tr>
				</thead>
				<c:if test="${fn:length(errorAuditRecords.result)<=0 }">
					<tr align="center">
						<td colspan="13">对不起,暂无数据！</td>
					</tr>
				</c:if>
				<c:forEach items="${errorAuditRecords.result }" var="records">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
						<td align="center">
							<c:if test="${records.deduct_roll_bk == 0 }">
								${records.deduct_sys_stance}
							</c:if>
							<c:if test="${records.deduct_roll_bk == 1 }">
								${records.deduct_roll_bk_stance}
							</c:if>
						</td>
						<td align="center">
							<c:if test="${records.deduct_roll_bk == 0 }">
								${fn:substring(records.deduct_sys_time,0,19)}
							</c:if>
							<c:if test="${records.deduct_roll_bk == 1 }">
								${fn:substring(records.deduct_rollbk_sys_time,0,19)}
							</c:if>
						</td>
						<td align="center">
							<f:formatNumber value="${records.trade_amount }" pattern="0.00"></f:formatNumber>
						</td>
						<td align="center">
							<c:if test="${records.inst_type == 0 }">
								${records.tradeType }
							</c:if>
							<c:if test="${records.inst_type == 1 }">
								<c:if test="${records.trademsg_type == 2 }">收款交易</c:if>
								<c:if test="${records.trademsg_type == 20 }">退款交易</c:if>
							</c:if>
						</td>
						<td align="center">
							<c:if test="${records.deduct_roll_bk == 0 }">
								<c:if test="${records.deduct_sys_response == '00' }">成功</c:if>
								<c:if test="${records.deduct_sys_response == 'N1' }">超时</c:if>
								<c:if test="${records.deduct_sys_response != 'N1' && records.deduct_sys_response != '00'}">失败</c:if>
							</c:if>
							<c:if test="${records.deduct_roll_bk == 1 }">
								<c:if test="${records.deduct_roll_bk_response == '00' }">成功</c:if>
								<c:if test="${records.deduct_roll_bk_response == 'N1' }">超时</c:if>
								<c:if test="${records.deduct_roll_bk_response != 'N1' && records.deduct_roll_bk_response != '00'}">失败</c:if>
							</c:if>
						</td>
						<td align="center">${records.name_ }</td>
						<td align="center">
							<c:if test="${records.error_resource == 0 }">原始交易差错</c:if>
							<c:if test="${records.error_resource == 1 }">对账文件差错</c:if>
							<c:if test="${records.error_resource == 2 }">内部差错来源</c:if>
							<c:if test="${records.error_resource == 3 }">无原交易录入差错</c:if>
						</td>
						<td align="center">
							<c:if test="${records.business_type == 0}">内部差错调整</c:if>
							<c:if test="${records.business_type == 1}">内部差错审批</c:if>
							<c:if test="${records.business_type == 2}">内部差错驳回</c:if>
							<c:if test="${records.business_type == 3}">银联差错录入</c:if>
							<c:if test="${records.business_type == 4}">银联差错提交</c:if>
							<c:if test="${records.business_type == 5}">银联差错审核</c:if>
							<c:if test="${records.business_type == 6}">银联差错驳回</c:if>
						</td>
						<td align="center">${records.operator}</td>
						<td align="center">
							<c:if test="${records.operation_type == 0 }">调整</c:if>
							<c:if test="${records.operation_type == 1 }">审批</c:if>
							<c:if test="${records.operation_type == 2 }">驳回</c:if>
							<c:if test="${records.operation_type == 3 }">录入</c:if>
							<c:if test="${records.operation_type == 4 }">提交</c:if>
							<c:if test="${records.operation_type == 5 }">重录入</c:if>
							<c:if test="${records.operation_type == 6 }">重提交</c:if>
						</td>
						<td align="center">
							<c:if test="${records.error_resource == 0 || records.error_resource == 1}">
								<c:if test="${records.handling_id == 1 }">正常结算</c:if>
								<c:if test="${records.handling_id == 2 }">退款</c:if>
								<c:if test="${records.handling_id == 3 }">挂账</c:if>
							</c:if>
						</td>
						<td align="center">${records.record_time}</td>
						<td align="center">${records.operator_ip}</td>
						<td>
							<a class="fl lj mr10" href="#" onclick="queryDetail('${records.trade_id}', '${records.record_time }');">详情</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			<!-- 分页 -->
			<c:if test="${errorAuditRecords.totalPages != null}">
				<div class="next">
					<c:if test="${errorAuditRecords.pageNo > 1}">
						<a href="javascript:paging(1)"><span>首页</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo > 1}">
						<a href="javascript:paging(${errorAuditRecords.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo-2 > 0}">
						<a href="javascript:paging(${errorAuditRecords.pageNo-2 })"><span>${errorAuditRecords.pageNo-2
								}</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo-1 > 0}">
						<a href="javascript:paging(${errorAuditRecords.pageNo-1 })"><span>${errorAuditRecords.pageNo-1
								}</span></a>
					</c:if>
					<a href="#" class="hover"><span>${errorAuditRecords.pageNo }</span></a>
					<c:if test="${errorAuditRecords.pageNo+1 <= errorAuditRecords.totalPages}">
						<a href="javascript:paging(${errorAuditRecords.pageNo+1 })"><span>${errorAuditRecords.pageNo+1
								}</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo+2 <= errorAuditRecords.totalPages}">
						<a href="javascript:paging(${errorAuditRecords.pageNo+2 })"><span>${errorAuditRecords.pageNo+2
								}</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo+3 <= errorAuditRecords.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo < errorAuditRecords.totalPages}">
						<a href="javascript:paging(${errorAuditRecords.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<c:if test="${errorAuditRecords.pageNo > 1}">
						<a href="javascript:paging(${errorAuditRecords.totalPages })"><span>尾页</span></a>
					</c:if>
					<b>
						<span>共${errorAuditRecords.totalPages }页 跳到第
						<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
							value="${errorAuditRecords.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
						</span>
					</b>
				</div>
			</c:if>
		</div>
		
		<!--===========================弹出内容============================-->
		<div id="pop1" class="pop" style="display: none">
			<div class="pop_body">
				<h1 class="pop_tit">
					<span class="fl">差错审计详情</span> <a class="close"
						href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
				</h1>
				<div class="table_2">
					<table width="100%" border="0" cellspacing="0" id="operator">
						<tr>
							<td align="right" bgcolor="#eeeeee">交易流水号：</td>
							<td id="req_sys_stance"></td>
							<td align="right" bgcolor="#eeeeee">交易时间：</td>
							<td id="trade_time"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">转出卡号：</td>
							<td id="out_account"></td>
							<td align="right" bgcolor="#eeeeee">交易金额：</td>
							<td id="trade_amount"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">电银商户号：</td>
							<td id="req_mer_code"></td>
							<td align="right" bgcolor="#eeeeee">电银终端号：</td>
							<td id="req_mer_term_id"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">电银手续费：</td>
							<td id="trade_fee"></td>
							<td align="right" bgcolor="#eeeeee">交易应答码：</td>
							<td id="req_response"></td>
						</tr>
						<!-- <tr>
							<td align="right" bgcolor="#eeeeee">交易处理码：</td>
							<td id="req_process"></td>
							<td align="right" bgcolor="#eeeeee">交易消息类型：</td>
							<td id="trademsg_type"></td>
						</tr> -->
						<tr>
							<td align="right" bgcolor="#eeeeee">交易类型：</td>
							<td id="trade_type"></td>
							<td align="right" bgcolor="#eeeeee">是否冲正：</td>
							<td id="deduct_roll_bk"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">冲正流水：</td>
							<td id="deduct_roll_bk_stance"></td>
							<td align="right" bgcolor="#eeeeee">冲正扣款时间：</td>
							<td id="deduct_rollbk_sys_time"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">冲正应答码：</td>
							<td id="deduct_roll_bk_response"></td>
							<td align="right" bgcolor="#eeeeee">冲正结果：</td>
							<td id="deduct_roll_bk_result"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">扣款流水：</td>
							<td id="deduct_sys_stance"></td>
							<td align="right" bgcolor="#eeeeee">扣款时间：</td>
							<td id="deduct_sys_time"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">扣款应答码：</td>
							<td id="deduct_sys_response"></td>
							<td align="right" bgcolor="#eeeeee">扣款结果：</td>
							<td id="deduct_result"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">清算日期：</td>
							<td id="deduct_stlm_date"></td>
							<td align="right" bgcolor="#eeeeee">扣款机构号：</td>
							<td id="deduct_sys_id"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">商户名称：</td>
							<td id="mer_name"></td>
							<td align="right" bgcolor="#eeeeee">商户订单号：</td>
							<td id="additional_data"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银联参考号</td>
							<td id="deduct_sys_reference"></td>
							<td align="right" bgcolor="#eeeeee">授权码：</td>
							<td id="authorization_code"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">银联商户号：</td>
							<td id="deduct_mer_code"></td>
							<td align="right" bgcolor="#eeeeee">银联终端号：</td>
							<td id="deduct_mer_term_id"></td>
						</tr>
						<tr>
							<td align="right" bgcolor="#eeeeee">记录时间：</td>
							<td id="record_time"></td>
							<td align="right" bgcolor="#eeeeee">处理状态：</td>
							<td id="handling_status"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
