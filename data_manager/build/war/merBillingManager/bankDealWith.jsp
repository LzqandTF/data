<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应收银行交易款</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">

	//分页查询 
	function paging(pageNo) {
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易日期");
			return ;
		}
		var start  = new Date(startTime.replace(/-/g,"/")).getTime();
		var end = new Date(endTime.replace(/-/g,"/")).getTime();
		if(end - start  > 31*24*60*60*1000){
			alert("选择交易日期跨度超过一个月");
			return ;
		 }
		var arr = new Array();
		var sel_s=document.getElementsByName("subcheck_s");//获取checkbox的值
		for( var i=0;i<sel_s.length;i++)
		if(sel_s[i].checked==true)
		arr.push(sel_s[i].value);
		var sel_x=document.getElementsByName("subcheck_x");//获取checkbox的值
		for( var i=0;i<sel_x.length;i++)
		if(sel_x[i].checked==true)
		arr.push(sel_x[i].value);
		if(arr.length==0){
			alert("选择渠道信息为空！！！");
			return ;
		}
		var form = document.getElementById("querySumListCountInfoCount");
		var pageSize = $("#pageSize").val();
		var pagesNumber = $("#pagesNumber").val();
		
		with (form) {
			action = "<%=request.getContextPath()%>/querySumListCountInfoCount.do?pageNo=" + pageNo + "&pageSize=" + pageSize + "&arr="+arr;
			method = "post";
			form.submit();
		}
	}
	
	//根据每页显示的数据条数分页查询
	function queryByPageSize(pageSize) {
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易日期");
			return ;
		}
		var start  = new Date(startTime.replace(/-/g,"/")).getTime();
		var end = new Date(endTime.replace(/-/g,"/")).getTime();
		if(end - start  > 31*24*60*60*1000){
			alert("选择交易日期跨度超过一个月");
			return ;
		 }
		var arr = new Array();
		var sel_s=document.getElementsByName("subcheck_s");//获取checkbox的值
		for( var i=0;i<sel_s.length;i++)
		if(sel_s[i].checked==true)
		arr.push(sel_s[i].value);
		var sel_x=document.getElementsByName("subcheck_x");//获取checkbox的值
		for( var i=0;i<sel_x.length;i++)
		if(sel_x[i].checked==true)
		arr.push(sel_x[i].value);
		if(arr.length==0){
			alert("选择渠道信息为空！！！");
			return ;
		}
		var form = document.getElementById("querySumListCountInfoCount");
		with (form) {
			action = "<%=request.getContextPath()%>/querySumListCountInfoCount.do?pageSize=" + pageSize + "&arr="+arr;
			method = "post";
			form.submit();
		}
	}
	
	//查询数据
	function checkQuery(){
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易日期");
			return ;
		}
		var start  = new Date(startTime.replace(/-/g,"/")).getTime();
		var end = new Date(endTime.replace(/-/g,"/")).getTime();
		if(end - start  > 31*24*60*60*1000){
			alert("选择交易日期跨度超过一个月");
			return ;
		 } 
		
		var arr = new Array();
		var sel_s=document.getElementsByName("subcheck_s");//获取checkbox的值
		for( var i=0;i<sel_s.length;i++)
		if(sel_s[i].checked==true)
		arr.push(sel_s[i].value);
		var sel_x=document.getElementsByName("subcheck_x");//获取checkbox的值
		for( var i=0;i<sel_x.length;i++)
		if(sel_x[i].checked==true)
		arr.push(sel_x[i].value);
		if(arr.length==0){
			alert("选择渠道信息为空！！！");
			return ;
		}
		var form = document.getElementById("querySumListCountInfoCount");
		var pageSize = $("#pageSize").val();
		with (form) {
			action = "<%=request.getContextPath()%>/querySumListCountInfoCount.do?pageSize=" + pageSize + "&arr="+arr;
			method = "post";
			form.submit();
		}
	}
	
	function pagingInfo(pageNo) {
		var pageSize = $("#pageSize").val();
		$("#detailed").css({display:"block"});
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var originalDataTableName = $("#originalDataTableName").val();
		var instType = $("#instType").val();
		var name = $("#name").val();
		 $.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/queryTradingList.do",
			data : {'originalDataTableName':originalDataTableName,'startTime':startTime,'endTime':endTime,'instType':instType,'name':name,'pageNum':pageNo,'pageSize':pageSize},
			async:false,
			success : function(msg) {
				$("#selectSettlementDetailed").empty();
				$("#selectSettlementDetailed").append(msg);
			}
		});
	}
	
	
	//根据ID获取详情信息
	function selectTradingByTable(originalDataTableName,instType,name){
			$("#detailed").css({display:"block"});
			$("#originalDataTableName").val(originalDataTableName);
			$("#instType").val(instType);
			$("#name").val(name);
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			 $.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/queryTradingList.do",
				data : {'originalDataTableName':originalDataTableName,'startTime':startTime,'endTime':endTime,'instType':instType,'name':name},
				async:false,
				success : function(msg) {
					$("#selectSettlementDetailed").empty();
					$("#selectSettlementDetailed").append(msg);
				}
			});
		}
	
	//隐藏域
	function hide(obj) {
		var o = document.getElementById(obj);	
		o.style.display = "none";
	}
	
	//下载excel数据
	function checkExcel(){
		var startTime = $("#startTime").val();
		if(startTime == "" || startTime==null){
			alert("请选择交易日期");
			return ;
		}
		var endTime = $("#endTime").val();
		if(endTime == "" || endTime==null){
			alert("请选择交易日期");
			return ;
		}
		var start  = new Date(startTime.replace(/-/g,"/")).getTime();
		var end = new Date(endTime.replace(/-/g,"/")).getTime();
		if(end - start  > 31*24*60*60*1000){
			alert("选择交易日期跨度超过一个月");
			return ;
		 }
		var arr = new Array();
		var sel_s=document.getElementsByName("subcheck_s");//获取checkbox的值
		for( var i=0;i<sel_s.length;i++)
		if(sel_s[i].checked==true)
		arr.push(sel_s[i].value);
		var sel_x=document.getElementsByName("subcheck_x");//获取checkbox的值
		for( var i=0;i<sel_x.length;i++)
		if(sel_x[i].checked==true)
		arr.push(sel_x[i].value);
		if(arr.length==0){
			alert("选择渠道信息为空！！！");
			return ;
		}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var url ="<%=request.getContextPath()%>/bankdealwithExcel.do?endTime="+endTime +"&startTime="+startTime+"&arr="+arr;
		window.location=url;
	}
	
	
	
	function init(){
		$.ajax({
    		url : '<%=request.getContextPath()%>/queryInstInfoCount.do',
    		type : 'post',
    		async : false,
    		success : function(msgs) {
    			$("#xiangshangjiaoyi").append('<tr>');
    			$("#xiangxiajiaoyi").append('<tr>');
    			var online = 1;
    			var offline = 1;
    			for (i in msgs){
    				if(msgs[i]['instType']==1){
    					
    					$("#xiangshangjiaoyi").append('<td><input type="checkbox" name="subcheck_s" id="subcheck_s" onclick="setSelectAll_s();" value="'+ msgs[i]['originalDataTableName']+"-"+msgs[i]['instType']+'"/>'+ msgs[i]['name'] +'</td>');
    					if(online%6 == 0 && online != msgs.length){
    						$("#xiangshangjiaoyi").append('</tr><tr>');
    					}
    					online = online + 1;
    					
    				}else{
    					
    					$("#xiangxiajiaoyi").append('<td><input type="checkbox" name="subcheck_x" id="subcheck_x" onclick="setSelectAll_x();" value="'+ msgs[i]['originalDataTableName']+"-"+msgs[i]['instType']+'"/>'+msgs[i]['name'] +'</td>');
    					if(offline%6 == 0 && offline != msgs.length){
    						$("#xiangxiajiaoyi").append('</tr><tr>');
    					}
    					offline = offline + 1;
    				}
    			}
    			$("#xiangshangjiaoyi").append('</tr>');
    			$("#xiangxiajiaoyi").append('</tr>');
    		},
    		error : function(msgs) {
    			alert("获取线上、线下渠道数据失败!");
    			
    		}
    	});
		var hiddenInfo = $("#hiddenInfo").val();
		var arr = new Array();
		if(hiddenInfo != null){
			arr = hiddenInfo.split(",");
			for(var i=0;i<arr.length;i++){
				$("#querySumListCountInfoCount").find("input[type='checkbox']").each(function(){
					if($(this).val() == arr[i]){
						$(this).attr("checked",true);
					}
				});
			}
			setSelectAll_s();
			setSelectAll_x();
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
			};
		};
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
			};
		};
	} 
	
	//子复选框的事件  
	function setSelectAll_s(){  
	    var chsub = $("input[type='checkbox'][name='subcheck_s']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck_s']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll_s").attr("checked", true);  
	    }else{
	    	$("#SelectAll_s").attr("checked", false);
	    } 
	}
	function selectAllDetail_s(){  
	    if ($("#SelectAllDetail_s").attr("checked")) {  
	        $(":checkbox").attr("checked", true);  
	    } else {  
	        $(":checkbox").attr("checked", false);  
	    }  
	}  
	
	//子复选框的事件  
	function setSelectAllDetail_s(){  
	    var chsub = $("input[type='checkbox'][name='subcheckDetail_s']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheckDetail_s']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAllDetail_s").attr("checked", true);  
	    }else{
	    	$("#SelectAllDetail_s").attr("checked", false);
	    } 
	}
	
	//全选、取消全选的事件  
	function selectAll_s(){
		  var love_s=document.getElementsByName("subcheck_s");
		  if ($("#SelectAll_s").attr("checked")){
			  for(var i=0;i<love_s.length;i++){
				  love_s[i].checked=true;
			  }
		  }else{
			  for(var i=0;i<love_s.length;i++){
				  love_s[i].checked=false;
			 }
		  }
		}

	//子复选框的事件  
	function setSelectAll_x(){  
	    var chsub = $("input[type='checkbox'][name='subcheck_x']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck_x']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll_x").attr("checked", true);  
	    }else{
	    	$("#SelectAll_x").attr("checked", false);
	    } 
	}
	function selectAllDetail_x(){  
	    if ($("#SelectAllDetail_x").attr("checked")) {  
	        $(":checkbox").attr("checked", true);  
	    } else {  
	        $(":checkbox").attr("checked", false);  
	    }  
	}  
	
	//子复选框的事件  
	function setSelectAllDetail_x(){  
	    var chsub = $("input[type='checkbox'][name='subcheckDetail_x']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheckDetail_x']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAllDetail_x").attr("checked", true);  
	    }else{
	    	$("#SelectAllDetail_x").attr("checked", false);
	    } 
	}
	
	 //全选、取消全选的事件  
	function selectAll_x(){
	  var love_x=document.getElementsByName("subcheck_x");
	  if ($("#SelectAll_x").attr("checked")){
		  for(var i=0;i<love_x.length;i++){
			  love_x[i].checked=true;
		  }
	  }else{
		  for(var i=0;i<love_x.length;i++){
			  love_x[i].checked=false;
		 }
	  }
	}
	
</script>
</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>应收银行交易款</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/querySumListCountInfoCount.do" target="right" name="querySumListCountInfoCount" id="querySumListCountInfoCount" method="post">
					 <div class="table-m">
						  <table width="100%" border="0" cellspacing="0">
								<tr><td><input type="checkbox" id="SelectAll_s" onclick="selectAll_s();"/>线上渠道</td></tr>
								<tbody id="xiangshangjiaoyi">
								
								</tbody>
							</table> 
							<table width="100%" border="0" cellspacing="0">
								<tr><td><input type="checkbox" id="SelectAll_x" onclick="selectAll_x();"/>线下渠道</td></tr>
								<tbody id="xiangxiajiaoyi">
								
								</tbody>
							</table>
							<input type="hidden" id="hiddenInfo" value="${shangxia }" />
						</div>
						<ul class="check-m">
						<li>
						<table>
							<tr>
								<td align="right" nowrap="nowrap">交易日期:</td>
				            	<td nowrap="nowrap">
								<span style="width: 160px;" class="input_bgl"> 
										<input style="width: 70px" id="startTime" name="startTime" value="${param.startTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'});clearEndTime(endTime);" />
										至 
										<input style="width: 70px" id="endTime" name="endTime" value="${param.endTime }"
											readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d'})" />
									</span>
									<font color='red' size="4" style="margin-left: 2px;float:right;">*</font>	
								</td>
							</tr>
						</table>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
							<input type="button" class="icon_normal" value="下载xls报表" onclick="checkExcel()"/>
						</li>
					</ul> 
				</form>
			</div>
			
			<div style="font-size: 12px;">
				<span>
					本页共
					<font color="red">
						<c:if test="${numbers == null }">0</c:if>
						<c:if test="${numbers !=null }">${numbers}</c:if>
					</font>
					条数据
				</span>
				<span style="float: right">
					&nbsp;&nbsp;&nbsp;&nbsp;
					共
					<font color="red">
						<c:if test="${size == null }">0</c:if>
						<c:if test="${size != null }">${size }</c:if>
					</font>
					条数据
					<font color="red">
						<c:if test="${pagesNumber == null}">0</c:if>
						<c:if test="${pagesNumber != null}">${pagesNumber}</c:if>
					</font>页
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span>
						每页显示
						<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="${pageNo }" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
						<input type="hidden" id="pageSize_hidden" value="${pageNo }"/>
						条
					</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<br />
				<br />
				<span>
					总计：支付金额
					<font color="red">
						<span>
							<c:if test="${sunmpayamt == null}">0.00</c:if>
							<c:if test="${sunmpayamt != null}">
								<f:formatNumber value="${sunmpayamt }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元&nbsp;&nbsp;
					退款金额
					<font color="red">
						<span>
							<c:if test="${sumarefundamt == null}">0.00</c:if>
							<c:if test="${sumarefundamt != null}">
								<f:formatNumber value="${sumarefundamt }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元&nbsp;&nbsp;
					银行手续费
					<font color="red">
						<span>
							<c:if test="${sumpayfee == null}">0.00</c:if>
							<c:if test="${sumpayfee != null}">
								<f:formatNumber value="${sumpayfee }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元&nbsp;&nbsp;
					银行退回手续费
					<font color="red">
						<span>
							<c:if test="${sumarefundfee == null}">0.00</c:if>
							<c:if test="${sumarefundfee != null}">
								<f:formatNumber value="${sumarefundfee }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元&nbsp;&nbsp;
					银行实收手续费
					<font color="red">
						<span>
							<c:if test="${bankPaidInFee == null}">0.00</c:if>
							<c:if test="${bankPaidInFee != null}">
								<f:formatNumber value="${bankPaidInFee }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元&nbsp;&nbsp;
					银行划款额
					<font color="red">
						<span>
							<c:if test="${bankamt == null}">0.00</c:if>
							<c:if test="${bankamt != null}">
								<f:formatNumber value="${bankamt }" pattern="0.00"></f:formatNumber>
							</c:if>
						</span>
					</font>
					元
				</span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							    <td align="center">渠道类型</td>
							    <td align="center">渠道名</td>
							    <td align="center">支付金额</td>
								<td align="center">退款金额</td>
								<td align="center">银行手续费</td>
								<td align="center">银行退回手续费</td>
								<td align="center">银行实收手续费</td>
								<td align="center">品牌服务费</td>
								<td align="center">银行划款额</td>
								<td align="center">操作</td>
						</tr>
					</thead>
					<c:if test="${size <=0 }">
						<tr align="center">
							<td colspan="7">对不起,暂无数据！</td>
						</tr>
					</c:if> 
				  <c:forEach items="${querySumListCountInfo}" var="SumListInfo">
				  		<tr onmouseover="this.style.background='#CBC6B1'; " onmouseout ="this.style.background=''; this.style.borderColor=''">
							<td align="center">
								<c:if test="${SumListInfo.instType == 1}">线上渠道</c:if>
								<c:if test="${SumListInfo.instType == 0}">线下渠道</c:if>
							</td>
							<td align="center">${SumListInfo.name}</td>
							<td align="center">${SumListInfo.sunmPayAmt}</td>
							<td align="center">${SumListInfo.sumArefundAmt}</td>
							<td align="center">${SumListInfo.sumPayFee}</td>
							<td align="center">${SumListInfo.sumArefundFee}</td>
							<td align="center">${SumListInfo.bankPaidInFee}</td>
							<td align="center">${SumListInfo.ppfw_fee}</td>
							<td align="center">${SumListInfo.bankAmt}</td>
							<td align="center">
								<input type="button" value="查看手续费异常" onclick="selectTradingByTable('${SumListInfo.originalDataTableName}','${SumListInfo.instType}','${SumListInfo.name}');"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<!-- 分页 -->
				<c:if test="${currentPageNo != null}">
					<div class="next">
						<c:if test="${currentPageNo > 1}">
							<a href="javascript:paging(1)"><span>首页</span></a>
						</c:if>
						<c:if test="${currentPageNo > 1}">
							<a href="javascript:paging(${currentPageNo -1 })"><span>上一页</span></a>
						</c:if>
						<c:if test="${currentPageNo-3 > 0}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${currentPageNo-2 > 0}">
							<a href="javascript:paging(${currentPageNo-2 })"><span>${currentPageNo-2
									}</span></a>
						</c:if>
						<c:if test="${currentPageNo-1 > 0}">
							<a href="javascript:paging(${currentPageNo-1 })"><span>${currentPageNo-1
									}</span></a>
						</c:if>
						
						<a href="#" class="hover"><span>${currentPageNo}</span></a>
						
						<c:if test="${currentPageNo+1 <= pagesNumber}">
							<a href="javascript:paging(${currentPageNo+1 })"><span>${currentPageNo+1
									}</span></a>
						</c:if>
						<c:if test="${currentPageNo+2 <= pagesNumber}">
							<a href="javascript:paging(${currentPageNo+2 })"><span>${currentPageNo+2
									}</span></a>
						</c:if>
						<c:if test="${currentPageNo+3 <= pagesNumber}">
							<b><span>...</span></b>
						</c:if>
						<c:if test="${currentPageNo < pagesNumber}">
							<a href="javascript:paging(${currentPageNo+1 })"><span>下一页</span></a>
						</c:if>
						<c:if test="${currentPageNo > 1}">
							<a href="javascript:paging(${pagesNumber})"><span>尾页</span></a>
						</c:if>
						<b>
							<span>共${pagesNumber}页 跳到第
							<input style="width: 24px; margin: 0 5px; text-align: center;" id="pageNum" name="pageNum" 
								value="${currentPageNo}" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="queryByPage(event)" />页
							</span>
							<input type="hidden" id="pagesNumber" value="${currentPageNo }" />
						</b>
					</div>
				</c:if>
			</div>
	   </div>
    </div>
    <!--===========================弹出内容============================-->
	<div id="detailed" class="pop" style="display: none;OVERFLOW: auto;">
		<div class="pop_body" style="margin-top: 50px;">
			<div class="table_2">
			<div>
					<input type="hidden" id="name" />
					<input type="hidden" id="instType"/>
					<input type="hidden" id="originalDataTableName"/>
			</div>
				<div id="selectSettlementDetailed" ></div>
				<div align="center">
						<input type="button" class="icon_normal" value="返回" onclick="hide('detailed')"/>
				</div>
			</div>
		</div>
	</div> 
</body>
</html>
