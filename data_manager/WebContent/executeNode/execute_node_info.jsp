<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账工作流节查看</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("executeNodeSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryExecuteNode.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		function checkQuery(){	
			document.getElementById("executeNodeSearch").submit();
		}
		function init(){
			$.ajax({
	    		url : '<%=request.getContextPath()%>/getAllInstInfoList.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg)
	    				$("#inst_info").append('<option value="' + msg[i]['inst_type'] + ',' + msg[i]['instId'] + '">'+ msg[i]['name'] + '</option>');
	    		},
	    		error : function(msg) {
	    			alert("获取银行列表失败!");
	    		}
	    	});
			var inst_id = $("#inst_id_hidden").val();
			var type = document.getElementById("inst_info");
			
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == inst_id){
					type.options[i].selected = 'selected';
				}
			};
			var active = $("#active_hidden").val();
			var type = document.getElementById("active_select");
			
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == active){
					type.options[i].selected = 'selected';
				}
			}
		}
	</script>
</head>
<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryExecuteNode.do" target="right" name="executeNodeSearch" id = "executeNodeSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>扣款渠道</b>
							<span class="input_bgl">
									<select id="inst_info" name="inst_info">
										<option value="">--请选择扣款渠道--</option>
									</select>
									<input type="hidden" id="inst_id_hidden" value="${inst_info }"/>
							</span>
						</li>
						<li>
							<b>清算日期：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="deduct_stml_date" id = "deduct_stml_date" value="${param.deduct_stml_date }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center" width="8%">扣款渠道</td>
							<td align="center" width="15%">清算日期</td>
							<td align="center" width="15%">节点1:交易汇总</td>
							<td align="center" width="10%">节点2:对账文件获取</td>				
							<td align="center" width="10%">节点3:对账</td>
							<td align="center" width="12%">节点4:差错处理</td>
							<td align="center" width="20%">节点5:对账文件生成</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageData.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageData.result }" var="executeNode">
						<tr class="ssss">
							<td align="center">${executeNode.inst_name }</td>
							<td align="center">
							${fn:substring(executeNode.deduct_stml_date,0,10) }
							</td>
							<td align="center">
								<c:if test="${executeNode.trade_collect == 0 }">——</c:if>
								<c:if test="${executeNode.trade_collect == 1 }">√</c:if>
								<c:if test="${executeNode.trade_collect == 2 }">×</c:if>	
							</td>
							<td align="center">
								<c:if test="${executeNode.dz_file_gain == 0 }">——</c:if>
								<c:if test="${executeNode.dz_file_gain == 1 }">√</c:if>
								<c:if test="${executeNode.dz_file_gain == 2 }">×</c:if>
							</td>
							<td align="center">
								<c:if test="${executeNode.dz_handle == 0 }">——</c:if>
								<c:if test="${executeNode.dz_handle == 1 }">√</c:if>
								<c:if test="${executeNode.dz_handle == 2 || executeNode.dz_handle == 3 || executeNode.dz_handle == 4 }">×</c:if>
							</td>
							<td align="center">
								<c:if test="${executeNode.error_handle == 0 }">——</c:if>
								<c:if test="${executeNode.error_handle == 1 }">√</c:if>
								<c:if test="${executeNode.error_handle == 2 || executeNode.error_handle == 3 || executeNode.error_handle == 4 }">×</c:if>
							</td>
							<td align="center">
								<c:if test="${executeNode.dz_file_create == 0 }">——</c:if>
								<c:if test="${executeNode.dz_file_create == 1 }">√</c:if>
								<c:if test="${executeNode.dz_file_create == 2 }">×</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageData.totalPages != null}">
				<div class="next">
					<c:if test="${pageData.pageNo > 1}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo-2 > 0}">
						<a href="javascript:paging(${pageData.pageNo-2 })"><span>${pageData.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo-1 > 0}">
						<a href="javascript:paging(${pageData.pageNo-1 })"><span>${pageData.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageData.pageNo }</span></a>
					<c:if test="${pageData.pageNo+1 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>${pageData.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+2 <= pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+2 })"><span>${pageData.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageData.pageNo+3 <= pageData.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageData.pageNo < pageData.totalPages}">
						<a href="javascript:paging(${pageData.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageData.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageData.pageNo }" />页
							<input type="hidden" value="${pageData.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>
