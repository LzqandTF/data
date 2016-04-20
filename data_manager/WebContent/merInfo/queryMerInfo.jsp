<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户信息查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	//分页查询
	function paging(pageNo) {
		var form = document.getElementById("queryMerchants");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerInfoList.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var form = document.getElementById("queryMerchants");
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerInfoList.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var form = document.getElementById("queryMerchants");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/queryMerInfoList.do?pageSize=" + pageSize;
			method = "post";
			form.submit();
		}
	}
	
	//隐藏域
	function hide(obj) {
		var o = document.getElementById(obj);	
		o.style.display = "none";
	}
	
	//获取省份信息列表
	function init() {
		var bil_status_ = $("#bil_status_hidden").val();
		var bil_status = document.getElementById("bilStatus");
		for (var i = 0; i < bil_status.length; i++) {
			if (bil_status.options[i].value == bil_status_) {
				bil_status.options[i].selected = 'selected';
			}
		}
		
		var bil_manual_ = $("#bil_manual_hidden").val();
		var bil_manual = document.getElementById("bilManual");
		for (var i = 0; i < bil_manual.length; i++) {
			if (bil_manual.options[i].value == bil_manual_) {
				bil_manual.options[i].selected = 'selected';
			}
		}
		
		var mer_category_ = $("#mer_category_hidden").val();
		var mer_category = document.getElementById("merCategory");
		for (var i = 0; i < mer_category.length; i++) {
			if (mer_category.options[i].value == mer_category_) {
				mer_category.options[i].selected = 'selected';
			}
		}
		
		var bil_type_ = $("#bil_type_hidden").val();
		var bil_type = document.getElementById("bilType");
		for (var i = 0; i < bil_type.length; i++) {
			if (bil_type.options[i].value == bil_type_) {
				bil_type.options[i].selected = 'selected';
			}
		}
		
		var mer_state_ = $("#mer_state_hidden").val();
		var mer_state = document.getElementById("merState");
		for (var i = 0; i < mer_state.length; i++) {
			if (mer_state.options[i].value == mer_state_) {
				mer_state.options[i].selected = 'selected';
			}
		}
		
		var mer_name_hidden = $("#mer_name_hidden").val();
		var merName = document.getElementById("merName");
		for(var i = 0; i < merName.options.length; i++){
			if(merName.options[i].value == mer_name_hidden){
				merName.options[i].selected = 'selected';
			}
		}
		
		if (mer_name_hidden != "") {
			queryMerNameListByMerName();
		}
		
		var mer_name = document.getElementById("mer_name");
		for(var i = 0; i < mer_name.options.length; i++){
			if(mer_name.options[i].value == mer_name_hidden){
				mer_name.options[i].selected = 'selected';
			}
		}
		
		var page_size = $("#pageSize_hidden").val();
		if (page_size == '') {
			page_size = 10;
		}
		document.getElementById("pageSize").value = page_size;
		
	}
	
	//根据ID获取详情信息
	function queryMerInfoByMerCode(merCode){
		$.ajax({
			type : "post",
			url : '<%=request.getContextPath()%>/queryAllMerInfoByMerCode.do',
			data : {"merCode": merCode},
			async:false,
			success : function(merInfo) {
				if(merInfo != null){
					var mer_category = merInfo.mer_category;
					if (mer_category == 0) {
						mer_category = "RYF商户";
					} else if (mer_category == 1) {
						mer_category = "VAS商户";
					} else {
						mer_category = "POS商户";
					}
					$("#mer_category").html(mer_category);
					$("#mer_name_").html(merInfo.mer_name);
					$("#mer_abbreviation").html(merInfo.mer_abbreviation);
					$("#mer_code").html(merInfo.mer_code);
					var mer_state = merInfo.mer_state;
					if (mer_state == 5) {
						mer_state = "正常";
					} else {
						mer_state = "关闭";
					}
					$("#mer_state").html(mer_state);
					$("#provinces").html(merInfo.provinces + merInfo.city);
					
					var mer_type = merInfo.mer_type;
					if (mer_type == 1) {
						mer_type = "企业";
					} else if (mer_type == 2) {
						mer_type = "个人";
					} else {
						mer_type = "集团";
					} 
					$("#mer_type").html(mer_type);
					$("#startDate").html(merInfo.startDate);
					$("#endDate").html(merInfo.endDate);
					$("#expand").html(merInfo.expand);
					
					var bil_type = merInfo.bil_type;
					if (bil_type == 1) {
						bil_type = "银行账户";
					} else {
						bil_type = "电银账户";
					}
					$("#bil_type").html(bil_type);
					$("#bil_bank").html(merInfo.bil_bank);
					$("#bank_branch").html(merInfo.bank_branch);
					$("#bil_accountname").html(merInfo.bil_accountname);
					$("#bil_bankaccount").html(merInfo.bil_bankaccount);
					$("#bil_account").html(merInfo.bil_account);
					
					// 是否自动结算
					var bil_manual= merInfo.bil_manual;
					if (bil_manual == 1) {
						bil_manual = "否";
					} else {
						bil_manual = "是";
					}
					$("#bil_manual").html(bil_manual);
					
					var refund_fee = merInfo.refund_fee;
					if (refund_fee == 0) {
						refund_fee = "不退还";
					} else {
						refund_fee = "退还";
					}
					$("#refund_fee").html(refund_fee);
					
					var bil_status = merInfo.bil_status;
					if (bil_status == 1) {
						bil_status = "正常";
					} else {
						bil_status = "冻结";
					}
					$("#bil_status").html(bil_status);
					
					var bil_way = merInfo.bil_way;
					if (bil_way == 1) {
						bil_way = "全额";
					} else {
						bil_way = "净额";
					}
					$("#bil_way").html(bil_way);
					
					var bil_cycle = merInfo.bil_cycle;
					if (bil_cycle == 1) {
						bil_cycle = "T+1";
					} else if (bil_cycle == 2) {
						bil_cycle = "周结";
					} else {
						bil_cycle = "月结";
					}
					$("#bil_cycle").html(bil_cycle);
					
					$("#bil_smallamt").html(merInfo.bil_smallamt);
					
					var whtherFz = merInfo.whtherFz;
					if (whtherFz == 0) {
						whtherFz = "否";
					} else {
						whtherFz = "是";
					}
					$("#whtherFz").html(whtherFz);
					$("#pop").css({display:"block"});
				}else{
					alert("该商户不存在！");
				}
			}
		});
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
	
	function queryMerNameListByMerName() {
		var mer_name = $("#merName").val();
		if (mer_name == null || mer_name == "") {
			return;
		}
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryMerNameListByMerName.do',
    		type : 'post',
    		data : {"mer_name": mer_name},
    		async : false,
    		success : function(list) {
    			if (list != null) {
        			var selectObj = document.getElementById("mer_name");
    				while(selectObj.firstChild) {
    			        selectObj.removeChild(selectObj.firstChild);
    				}
    				if (list.length >= 0) {
    					$(selectObj).append("<option value=''>--请选择--</option>");
    				}
    				for(var i = 0; i < list.length; i++){
    					$(selectObj).append("<option value="+list[i].mer_name +">" + list[i].mer_name + "</option>");
    				}
    			}
    		}
    	});
	}
</script>
</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">商户管理</a>&gt;<span>商户信息查询</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryMerInfoList.do" target="right" name="queryMerchants" id="queryMerchants" method="post">
					<div class="table_2" style="background:  #dcdfe1; border: none;">
						<center>
							<table width="90%" border="0" cellspacing="0">
								<tr>
									<td align="right" nowrap="nowrap">电银商户号</td>
					            	<td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="merCode" id="merCode" value="${param.merCode }" onkeyup="value=value.replace(/[^\d]/g,'')" />
					                     </span>
					                </td>
						            <td align="right" nowrap="nowrap">商户状态</td>
					                <td nowrap="nowrap">
					                    <span class="input_bgl">
					                    	<select id="merState" name="merState" style="width: 150px;">
												<option value="">全部</option>
												<option value="5">正常</option>
												<option value="6">关闭</option>
											</select>
											<input type="hidden" id="mer_state_hidden" value="${mer_state }"/>
					                    </span>
					                </td>
						            <td align="right" nowrap="nowrap">结算状态</td>
					                <td nowrap="nowrap">
					                    <span class="input_bgl">
					                    	<select id="bilStatus" name="bilStatus" style="width: 150px;">
												<option value="">全部</option>
												<option value="1">正常</option>
												<option value="2">关闭</option>
											</select>
											<input type="hidden" id="bil_status_hidden" value="${bil_status }"/>
					                    </span>
					                </td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap">是否自动结算</td>
					                <td nowrap="nowrap">
					                    <span class="input_bgl">
					                    	<select id="bilManual" name="bilManual" style="width: 150px;">
												<option value="">全部</option>
												<option value="2">开启</option>
												<option value="1">关闭</option>
											</select>
											<input type="hidden" id="bil_manual_hidden" value="${bil_manual }"/>
					                    </span>
					                </td>
					                <td align="right" nowrap="nowrap">商户类别</td>
					                <td nowrap="nowrap">
					                    <span class="input_bgl">
					                    	<select id="merCategory" name="merCategory" style="width: 150px;">
												<option value="">全部</option>
												<option value="2">POS商户</option>
												<option value="1">VAS商户</option>
												<option value="0">RYF商户</option>
											</select>
											<input type="hidden" id="mer_category_hidden" value="${mer_category }"/>
					                    </span>
					                </td>
					                <td align="right" nowrap="nowrap">结算账户类型</td>
					                <td nowrap="nowrap">
					                    <span class="input_bgl">
					                    	<select id="bilType" name="bilType" style="width: 150px;">
												<option value="">全部</option>
												<option value="1">银行账户</option>
												<option value="2">电银账户</option>
											</select>
											<input type="hidden" id="bil_type_hidden" value="${bil_type }"/>
					                    </span>
					                </td>
								</tr>
								<tr>
					                 <td align="right" nowrap="nowrap">商户名称</td>
					            	 <td nowrap="nowrap">
					                     <span class="input_bgl">
					                     	<input type="text" name="merName" id="merName" value="${param.merName }" style="width: 100px;" onblur="queryMerNameListByMerName();"/>
					                     </span>
					                     <span class="input_bgl">
					                    	<select id="mer_name" name="mer_name" style="width: 120px;" onblur="">
												<option value="">--请选择--</option>
											</select>
											<input type="hidden" id="mer_name_hidden" value="${mer_name }"/>
					                    </span>
					                    <font color="red">*</font>
					                 </td>
					                
								</tr>
								<tr>
									<td colspan="8" align="center" style="height: 30px"> 
										<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
									</td>
								</tr>
					        </table>
				        </center>
					</div>
				</form>
			</div>
			
			<!-- 分页显示数据条数 -->
			<div style="font-size: 12px;">
				<span>
				本页共
				<font color="red">
					<c:if test="${fn:length(merchantsList.result) <= 0 }">0</c:if>
					<c:if test="${fn:length(merchantsList.result) > 0 }">${fn:length(merchantsList.result) }</c:if>
				</font>
				条数据
				</span>
				<span style="float: right">共<font color="red">
					<c:if test="${merchantsList.totalItems == null }">0</c:if>
					<c:if test="${merchantsList.totalItems != null }">${merchantsList.totalItems }</c:if>
				</font>条数据
				<font color="red">
					<c:if test="${merchantsList.totalPages == null}">0</c:if>
					<c:if test="${merchantsList.totalPages != null}">${merchantsList.totalPages}</c:if>
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
							<td align="center">商户类别</td>
							<td align="center">电银商户号</td>
							<td align="center">商户名称</td>
							<td align="center">商户简称</td>
							<td align="center">商户类型</td>
							<td align="center">商户状态</td>
							<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(merchantsList.result)<=0 }">
						<tr align="center">
							<td colspan="7">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${merchantsList.result}" var="merchantsList">
					<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
						<td align="center">
								<c:if test="${merchantsList.mer_category == 0}">RYF商户</c:if>
								<c:if test="${merchantsList.mer_category == 1}">VAS商户</c:if>
								<c:if test="${merchantsList.mer_category == 2}">POS商户</c:if>
							</td>
							<td align="center">${merchantsList.mer_code}</td>
							<td align="center">${merchantsList.mer_name}</td>
							<td align="center">${merchantsList.mer_abbreviation}</td>
							<td align="center">
								<c:if test="${merchantsList.mer_type == 1}">企业</c:if>
								<c:if test="${merchantsList.mer_type == 2}">个人</c:if>
								<c:if test="${merchantsList.mer_type == 3}">集团</c:if>
							</td>
							<td align="center">
								<c:if test="${merchantsList.mer_state == 5}">正常</c:if>
								<c:if test="${merchantsList.mer_state == 6}">关闭</c:if>
							</td>
							<td align="center">
								<a class="fl lj mr10" href="#" onclick="queryMerInfoByMerCode('${merchantsList.mer_code}');">详情</a>
							</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- 分页 -->
				<c:if test="${merchantsList.totalPages != null}">
					<div class="next">
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(${merchantsList.pageNo-1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantsList.pageNo-2 > 0}">
							<a href="javascript:paging(${merchantsList.pageNo-2 })"><span>${merchantsList.pageNo-2
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo-1 > 0}">
							<a href="javascript:paging(${merchantsList.pageNo-1 })"><span>${merchantsList.pageNo-1
									}</span></a>
						</c:if>
						<a href="#" class="hover"><span>${merchantsList.pageNo }</span></a>
						<c:if test="${merchantsList.pageNo+1 <= merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+1 })"><span>${merchantsList.pageNo+1
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo+2 <= merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+2 })"><span>${merchantsList.pageNo+2
									}</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo+3 <= merchantsList.totalPages}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${merchantsList.pageNo < merchantsList.totalPages}">
							<a href="javascript:paging(${merchantsList.pageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${merchantsList.pageNo > 1}">
							<a href="javascript:paging(${merchantsList.totalPages })"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${merchantsList.totalPages }页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${merchantsList.pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
						</b>
					</div>
				</c:if>
			</div>
			
	   		<div id="pop" class="pop" style="display: none;">
				<div class="pop_body" style="margin-top: 10px;">
					<h1 class="pop_tit">
						<span class="fl">商户信息配置</span> <a class="close"
							href="javascript:void(0);" onclick="hide('pop')">&nbsp;</a>
					</h1>
					<div class="table_2">
						<table width="100%" border="0" cellspacing="0">
							<tr>
								<td align="center" bgcolor="#eeeeee" colspan="6">商户基本信息</td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">商户类别：</td>
								<td id="mer_category"></td>
								<td align="right" bgcolor="#eeeeee">商户名称：</td>
								<td id="mer_name_"></td>
								<td align="right" bgcolor="#eeeeee">商户简称：</td>
								<td id="mer_abbreviation"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">电银商户号：</td>
								<td id="mer_code"></td>
								<td align="right" bgcolor="#eeeeee">商户状态：</td>
								<td id="mer_state"></td>
								<td align="right" bgcolor="#eeeeee">所在省市：</td>
								<td id="provinces"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">商户类型：</td>
								<td id="mer_type"></td>
								<td align="right" bgcolor="#eeeeee">合同起始日期：</td>
								<td id="startDate"></td>
								<td align="right" bgcolor="#eeeeee">合同结束日期：</td>
								<td id="endDate"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">商户拓展方：</td>
								<td id="expand"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
							</tr>
							<tr>
								<td align="center" bgcolor="#eeeeee" colspan="6">商户结算信息</td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">结算账户类型：</td>
								<td id="bil_type"></td>
								<td align="right" bgcolor="#eeeeee">结算银行联行号：</td>
								<td id="bil_bank"></td>
								<td align="right" bgcolor="#eeeeee">结算银行支行名称：</td>
								<td id="bank_branch"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">结算账户名称：</td>
								<td id="bil_accountname"></td>
								<td align="right" bgcolor="#eeeeee">结算银行账号：</td>
								<td id="bil_bankaccount"></td>
								<td align="right" bgcolor="#eeeeee">结算电银账号：</td>
								<td id="bil_account"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">是否自动结算：</td>
								<td id="bil_manual"></td>
								<td align="right" bgcolor="#eeeeee">退款时是否退还手续费：</td>
								<td id="refund_fee"></td>
								<td align="right" bgcolor="#eeeeee">结算状态：</td>
								<td id="bil_status"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">结算方式：</td>
								<td id="bil_way"></td>
								<td align="right" bgcolor="#eeeeee">结算周期：</td>
								<td id="bil_cycle"></td>
								<td align="right" bgcolor="#eeeeee">结算最少金额(元)：</td>
								<td id="bil_smallamt"></td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">是否资金分账：</td>
								<td id="whtherFz"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
							</tr>
							<tr>
								<td align="center" bgcolor="#eeeeee" colspan="6">手续费信息</td>
							</tr>
							<tr>
								<td align="right" bgcolor="#eeeeee">商户签约手续费：</td>
								<td></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
								<td bgcolor="#eeeeee"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
   		</div>
  	</div>
</body>
</html>
